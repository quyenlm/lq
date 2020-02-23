package com.garena.overlay;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.networking.UILoop;
import com.garena.msdk.R;
import com.garena.overlay.CountDownView;
import com.tencent.qqgamemi.util.GlobalUtil;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@TargetApi(21)
final class RecordingSession {
    static final /* synthetic */ boolean $assertionsDisabled = (!RecordingSession.class.desiredAssertionStatus());
    private static final String DISPLAY_NAME = "virtual";
    private static final int MAX_PREVIEW_HEIGHT = 1080;
    private static final int MAX_PREVIEW_WIDTH = 1920;
    private static final int VIDEO_480 = 480;
    private static final int VIDEO_720 = 720;
    /* access modifiers changed from: private */
    public ViewGroup cameraHolderView;
    /* access modifiers changed from: private */
    public WindowManager.LayoutParams cameraLayoutParams;
    /* access modifiers changed from: private */
    public TextureView cameraView;
    /* access modifiers changed from: private */
    public int clickCount = 0;
    /* access modifiers changed from: private */
    public final Context context;
    private CountDownView countDownView;
    private final Intent data;
    private VirtualDisplay display;
    private final DateFormat fileFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss'.mp4'", Locale.US);
    /* access modifiers changed from: private */
    public Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    /* access modifiers changed from: private */
    public CameraDevice mCameraDevice;
    private String mCameraId;
    /* access modifiers changed from: private */
    public Semaphore mCameraOpenCloseLock = new Semaphore(1);
    /* access modifiers changed from: private */
    public CameraCaptureSession.CaptureCallback mCaptureCallback = new CameraCaptureSession.CaptureCallback() {
        private void process(CaptureResult result) {
        }

        public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult partialResult) {
            process(partialResult);
        }

        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
            process(result);
        }
    };
    /* access modifiers changed from: private */
    public CameraCaptureSession mCaptureSession;
    /* access modifiers changed from: private */
    public long mDeBounce = 0;
    /* access modifiers changed from: private */
    public CaptureRequest mPreviewRequest;
    /* access modifiers changed from: private */
    public CaptureRequest.Builder mPreviewRequestBuilder;
    private Size mPreviewSize;
    /* access modifiers changed from: private */
    public int mRotation = 0;
    private final CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            RecordingSession.this.mCameraOpenCloseLock.release();
            CameraDevice unused = RecordingSession.this.mCameraDevice = cameraDevice;
            RecordingSession.this.createCameraPreviewSession();
        }

        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            RecordingSession.this.mCameraOpenCloseLock.release();
            cameraDevice.close();
            CameraDevice unused = RecordingSession.this.mCameraDevice = null;
        }

        public void onError(@NonNull CameraDevice cameraDevice, int error) {
            RecordingSession.this.mCameraOpenCloseLock.release();
            cameraDevice.close();
            CameraDevice unused = RecordingSession.this.mCameraDevice = null;
        }
    };
    private final TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            RecordingSession.this.openCamera(width, height);
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
            RecordingSession.this.configureTransform(width, height);
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            return true;
        }

        public void onSurfaceTextureUpdated(SurfaceTexture texture) {
        }
    };
    private OrientationEventListener orientationEventListener;
    private String outputFile;
    private final File outputRoot;
    private MediaProjection projection;
    private final MediaProjectionManager projectionManager;
    private View recordView;
    private MediaRecorder recorder;
    private final int resultCode;
    /* access modifiers changed from: private */
    public RecordResultView resultView;
    private boolean running;
    private Point szWindow = new Point();
    /* access modifiers changed from: private */
    public final WindowManager windowManager;

    static /* synthetic */ int access$608(RecordingSession x0) {
        int i = x0.clickCount;
        x0.clickCount = i + 1;
        return i;
    }

    /* JADX WARNING: type inference failed for: r6v20, types: [java.lang.CharSequence] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    RecordingSession(android.content.Context r11, int r12, android.content.Intent r13) {
        /*
            r10 = this;
            r9 = 0
            r10.<init>()
            android.graphics.Point r6 = new android.graphics.Point
            r6.<init>()
            r10.szWindow = r6
            java.text.SimpleDateFormat r6 = new java.text.SimpleDateFormat
            java.lang.String r7 = "yyyy-MM-dd-HH-mm-ss'.mp4'"
            java.util.Locale r8 = java.util.Locale.US
            r6.<init>(r7, r8)
            r10.fileFormat = r6
            r6 = 0
            r10.mDeBounce = r6
            r10.clickCount = r9
            r10.mRotation = r9
            com.garena.overlay.RecordingSession$6 r6 = new com.garena.overlay.RecordingSession$6
            r6.<init>()
            r10.mSurfaceTextureListener = r6
            com.garena.overlay.RecordingSession$7 r6 = new com.garena.overlay.RecordingSession$7
            r6.<init>()
            r10.mStateCallback = r6
            java.util.concurrent.Semaphore r6 = new java.util.concurrent.Semaphore
            r7 = 1
            r6.<init>(r7)
            r10.mCameraOpenCloseLock = r6
            com.garena.overlay.RecordingSession$8 r6 = new com.garena.overlay.RecordingSession$8
            r6.<init>()
            r10.mCaptureCallback = r6
            r10.context = r11
            r10.resultCode = r12
            r10.data = r13
            java.lang.String r2 = "Garena"
            java.lang.String r6 = android.os.Environment.DIRECTORY_DCIM
            java.io.File r5 = android.os.Environment.getExternalStoragePublicDirectory(r6)
            android.content.pm.PackageManager r4 = r11.getPackageManager()
            android.content.pm.ApplicationInfo r6 = r11.getApplicationInfo()     // Catch:{ NameNotFoundException -> 0x0089 }
            java.lang.String r6 = r6.packageName     // Catch:{ NameNotFoundException -> 0x0089 }
            r7 = 0
            android.content.pm.ApplicationInfo r3 = r4.getApplicationInfo(r6, r7)     // Catch:{ NameNotFoundException -> 0x0089 }
            if (r3 == 0) goto L_0x0062
            java.lang.CharSequence r6 = r4.getApplicationLabel(r3)     // Catch:{ NameNotFoundException -> 0x0089 }
            r0 = r6
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ NameNotFoundException -> 0x0089 }
            r2 = r0
        L_0x0062:
            java.io.File r6 = new java.io.File
            r6.<init>(r5, r2)
            r10.outputRoot = r6
            java.lang.String r6 = "window"
            java.lang.Object r6 = r11.getSystemService(r6)
            android.view.WindowManager r6 = (android.view.WindowManager) r6
            r10.windowManager = r6
            java.lang.String r6 = "media_projection"
            java.lang.Object r6 = r11.getSystemService(r6)
            android.media.projection.MediaProjectionManager r6 = (android.media.projection.MediaProjectionManager) r6
            r10.projectionManager = r6
            android.view.WindowManager r6 = r10.windowManager
            android.view.Display r6 = r6.getDefaultDisplay()
            android.graphics.Point r7 = r10.szWindow
            r6.getSize(r7)
            return
        L_0x0089:
            r6 = move-exception
            goto L_0x0062
        */
        throw new UnsupportedOperationException("Method not decompiled: com.garena.overlay.RecordingSession.<init>(android.content.Context, int, android.content.Intent):void");
    }

    static WindowManager.LayoutParams countDownLayoutParams() {
        return new WindowManager.LayoutParams(-1, -1, 2005, 262200, -3);
    }

    static WindowManager.LayoutParams cameraLayoutParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(-2, -2, 2005, 262184, -3);
        params.gravity = 51;
        params.horizontalMargin = 1.0f;
        return params;
    }

    public void showCountDownView() {
        if (this.countDownView == null) {
            this.countDownView = CountDownView.create(this.context, new CountDownView.Listener() {
                public void onCountDownComplete() {
                    RecordingSession.this.hideCountDownView();
                    RecordingSession.this.startRecording();
                }
            });
        }
        this.windowManager.addView(this.countDownView, countDownLayoutParams());
        showFrontCameraView();
    }

    /* access modifiers changed from: private */
    public void hideCountDownView() {
        if (this.countDownView != null) {
            this.windowManager.removeView(this.countDownView);
            this.countDownView = null;
        }
    }

    private void showFrontCameraView() {
        if (ContextCompat.checkSelfPermission(this.context, "android.permission.CAMERA") == 0) {
            if (this.cameraHolderView == null) {
                this.cameraHolderView = (ViewGroup) View.inflate(this.context, R.layout.msdk_video_camera_view, (ViewGroup) null);
                this.cameraView = (TextureView) this.cameraHolderView.findViewById(R.id.camera_view);
                this.cameraView.setClipToOutline(true);
                this.cameraView.setOutlineProvider(new OvalViewOutlineProvider());
            }
            this.cameraLayoutParams = cameraLayoutParams();
            this.windowManager.addView(this.cameraHolderView, this.cameraLayoutParams);
            startCamera();
            this.cameraHolderView.setOnTouchListener(new View.OnTouchListener() {
                private float initialTouchX;
                private float initialTouchY;
                private int initialX;
                private int initialY;

                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case 0:
                            this.initialX = RecordingSession.this.cameraLayoutParams.x;
                            this.initialY = RecordingSession.this.cameraLayoutParams.y;
                            this.initialTouchX = event.getRawX();
                            this.initialTouchY = event.getRawY();
                            break;
                        case 1:
                            if (Math.abs(event.getRawX() - this.initialTouchX) < 10.0f && Math.abs(event.getRawY() - this.initialTouchY) < 10.0f) {
                                if (RecordingSession.this.mDeBounce > event.getDownTime()) {
                                    return true;
                                }
                                RecordingSession.access$608(RecordingSession.this);
                                if (RecordingSession.this.clickCount >= 2) {
                                    RecordingSession.this.stopCamera();
                                    RecordingSession.this.hideCameraView();
                                    int unused = RecordingSession.this.clickCount = 0;
                                }
                                long unused2 = RecordingSession.this.mDeBounce = event.getEventTime();
                                return true;
                            }
                        case 2:
                            RecordingSession.this.cameraLayoutParams.x = this.initialX + ((int) (event.getRawX() - this.initialTouchX));
                            RecordingSession.this.cameraLayoutParams.y = this.initialY + ((int) (event.getRawY() - this.initialTouchY));
                            if (RecordingSession.this.cameraHolderView != null) {
                                RecordingSession.this.windowManager.updateViewLayout(RecordingSession.this.cameraHolderView, RecordingSession.this.cameraLayoutParams);
                                break;
                            }
                            break;
                    }
                    return false;
                }
            });
            this.mRotation = this.windowManager.getDefaultDisplay().getRotation();
            if (this.orientationEventListener == null) {
                this.orientationEventListener = new OrientationEventListener(this.context) {
                    public void onOrientationChanged(int orientation) {
                        int rotation = RecordingSession.this.windowManager.getDefaultDisplay().getRotation();
                        if (rotation != RecordingSession.this.mRotation) {
                            int unused = RecordingSession.this.mRotation = rotation;
                            RecordingSession.this.configureTransform(RecordingSession.this.cameraView.getWidth(), RecordingSession.this.cameraView.getHeight());
                        }
                    }
                };
            }
            this.orientationEventListener.enable();
        }
    }

    /* access modifiers changed from: private */
    public void hideCameraView() {
        if (this.cameraHolderView != null) {
            if (this.orientationEventListener != null) {
                this.orientationEventListener.disable();
            }
            this.cameraView.setSurfaceTextureListener((TextureView.SurfaceTextureListener) null);
            this.windowManager.removeView(this.cameraHolderView);
            this.cameraHolderView = null;
        }
    }

    static WindowManager.LayoutParams recordLayoutParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(-2, -2, 2005, 262184, -3);
        params.gravity = 49;
        return params;
    }

    private void showRecordView() {
        if (this.recordView == null) {
            this.recordView = View.inflate(this.context, R.layout.msdk_video_record_view, (ViewGroup) null);
            this.recordView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    RecordingSession.this.stopRecording();
                }
            });
        }
        this.windowManager.addView(this.recordView, recordLayoutParams());
    }

    private void hideRecordView() {
        if (this.recordView != null) {
            this.windowManager.removeView(this.recordView);
            this.recordView = null;
        }
    }

    /* access modifiers changed from: private */
    public void showResultView(boolean result) {
        if (this.resultView == null) {
            this.resultView = new RecordResultView(this.context);
        }
        this.resultView.setResult(result);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(-2, -2, 2005, 262184, -3);
        params.gravity = 17;
        this.windowManager.addView(this.resultView, params);
    }

    /* access modifiers changed from: private */
    public void hideResultView() {
        if (this.resultView != null) {
            this.windowManager.removeView(this.resultView);
            this.resultView = null;
        }
    }

    @SuppressLint({"NewApi"})
    private RecordingInfo getRecordingInfo() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return calculateRecordingInfo(displayMetrics.widthPixels, displayMetrics.heightPixels, displayMetrics.densityDpi, this.context.getResources().getConfiguration().orientation == 2);
    }

    /* access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public void startRecording() {
        boolean audioEnabled = true;
        RecordingInfo recordingInfo = getRecordingInfo();
        if (recordingInfo == null) {
            stopCamera();
            hideCameraView();
            Toast.makeText(this.context, "Your device doesn't support", 1).show();
            this.context.stopService(new Intent(this.context, RecordingService.class));
            return;
        }
        if (!this.outputRoot.mkdirs()) {
        }
        try {
            this.recorder = new MediaRecorder();
            this.recorder.setVideoSource(2);
            if (ContextCompat.checkSelfPermission(this.context, "android.permission.RECORD_AUDIO") != 0) {
                audioEnabled = false;
            }
            if (audioEnabled) {
                this.recorder.setAudioSource(1);
            }
            this.recorder.setOutputFormat(2);
            if (audioEnabled) {
                this.recorder.setAudioSamplingRate(44100);
                this.recorder.setAudioEncodingBitRate(128000);
                this.recorder.setAudioEncoder(3);
            }
            this.recorder.setVideoFrameRate(30);
            this.recorder.setVideoEncoder(2);
            this.recorder.setVideoSize(recordingInfo.width, recordingInfo.height);
            this.recorder.setVideoEncodingBitRate(4000000);
            this.outputFile = new File(this.outputRoot, this.fileFormat.format(new Date())).getAbsolutePath();
            this.recorder.setOutputFile(this.outputFile);
            this.recorder.prepare();
            this.projection = this.projectionManager.getMediaProjection(this.resultCode, this.data);
            this.display = this.projection.createVirtualDisplay(DISPLAY_NAME, recordingInfo.width, recordingInfo.height, recordingInfo.density, 2, this.recorder.getSurface(), (VirtualDisplay.Callback) null, (Handler) null);
            this.recorder.start();
            this.running = true;
            showRecordView();
        } catch (IOException e) {
            this.context.stopService(new Intent(this.context, RecordingService.class));
            throw new RuntimeException("Unable to prepare MediaRecorder.", e);
        } catch (Exception e2) {
            BBLogger.e(e2);
        }
    }

    /* access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public void stopRecording() {
        if (!this.running) {
            throw new IllegalStateException("Not running.");
        }
        this.running = false;
        stopCamera();
        hideCameraView();
        hideRecordView();
        try {
            this.projection.stop();
            this.recorder.stop();
            this.recorder.reset();
            this.recorder.release();
            this.display.release();
            MediaScannerConnection.scanFile(this.context, new String[]{this.outputFile}, (String[]) null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    UILoop.getInstance().post(new Runnable() {
                        public void run() {
                            RecordingSession.this.showResultView(true);
                            RecordingSession.this.resultView.postDelayed(new Runnable() {
                                public void run() {
                                    RecordingSession.this.hideResultView();
                                    Intent intent = new Intent(RecordingSession.this.context, RecordingService.class);
                                    intent.putExtra("command", RecordingService.SERVICE_ACTION_VIDEO_END);
                                    RecordingSession.this.context.startService(intent);
                                }
                            }, 3000);
                        }
                    });
                }
            });
        } catch (Exception e) {
            BBLogger.e(e);
        }
    }

    @SuppressLint({"NewApi"})
    private static MediaCodecInfo getMediaCodecInfo() {
        for (MediaCodecInfo mediaCodecInfo : new MediaCodecList(0).getCodecInfos()) {
            if (mediaCodecInfo.isEncoder()) {
                for (String equalsIgnoreCase : mediaCodecInfo.getSupportedTypes()) {
                    if (equalsIgnoreCase.equalsIgnoreCase(GlobalUtil.AVC_MIME_TYPE)) {
                        return mediaCodecInfo;
                    }
                }
                continue;
            }
        }
        return null;
    }

    @SuppressLint({"NewApi"})
    static RecordingInfo calculateRecordingInfo(int displayWidth, int displayHeight, int displayDensity, boolean isLandscapeDevice) {
        int videoWidth = VIDEO_480;
        MediaCodecInfo mediaCodecInfo = getMediaCodecInfo();
        if (mediaCodecInfo == null) {
            return null;
        }
        MediaCodecInfo.CodecCapabilities codecCapabilities = mediaCodecInfo.getCapabilitiesForType(GlobalUtil.AVC_MIME_TYPE);
        if (displayWidth >= VIDEO_480) {
            if (displayWidth > VIDEO_480) {
                videoWidth = VIDEO_720;
            }
            int videoHeight = (int) Math.floor((double) (((float) videoWidth) * (((float) displayHeight) / ((float) displayWidth))));
            if (videoHeight % 2 == 1) {
                videoHeight++;
            }
            if (!codecCapabilities.isFormatSupported(MediaFormat.createVideoFormat(GlobalUtil.AVC_MIME_TYPE, videoHeight, videoWidth))) {
                return null;
            }
            if (isLandscapeDevice) {
                return new RecordingInfo(videoHeight, videoWidth, displayDensity);
            }
            return new RecordingInfo(videoWidth, videoHeight, displayDensity);
        } else if (!codecCapabilities.isFormatSupported(MediaFormat.createVideoFormat(GlobalUtil.AVC_MIME_TYPE, displayHeight, displayWidth))) {
            return null;
        } else {
            if (isLandscapeDevice) {
                return new RecordingInfo(displayHeight, displayWidth, displayDensity);
            }
            return new RecordingInfo(displayWidth, displayHeight, displayDensity);
        }
    }

    static final class RecordingInfo {
        final int density;
        final int height;
        final int width;

        RecordingInfo(int width2, int height2, int density2) {
            this.width = width2;
            this.height = height2;
            this.density = density2;
        }
    }

    private static Size chooseOptimalSize(Size[] choices, int textureViewWidth, int textureViewHeight, int maxWidth, int maxHeight, Size aspectRatio) {
        List<Size> bigEnough = new ArrayList<>();
        List<Size> notBigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getWidth() <= maxWidth && option.getHeight() <= maxHeight && option.getHeight() == (option.getWidth() * h) / w) {
                if (option.getWidth() < textureViewWidth || option.getHeight() < textureViewHeight) {
                    notBigEnough.add(option);
                } else {
                    bigEnough.add(option);
                }
            }
        }
        if (bigEnough.size() > 0) {
            return (Size) Collections.min(bigEnough, new CompareSizesByArea());
        }
        if (notBigEnough.size() > 0) {
            return (Size) Collections.max(notBigEnough, new CompareSizesByArea());
        }
        BBLogger.e("Couldn't find any suitable preview size", new Object[0]);
        return choices[0];
    }

    private void startCamera() {
        startBackgroundThread();
        if (this.cameraView.isAvailable()) {
            openCamera(this.cameraView.getWidth(), this.cameraView.getHeight());
        } else {
            this.cameraView.setSurfaceTextureListener(this.mSurfaceTextureListener);
        }
    }

    /* access modifiers changed from: private */
    public void stopCamera() {
        closeCamera();
        stopBackgroundThread();
    }

    private void setUpCameraOutputs(int width, int height) {
        StreamConfigurationMap map;
        CameraManager manager = (CameraManager) this.context.getSystemService("camera");
        if (manager == null) {
            BBLogger.i("This device doesn't support Camera2 API.", new Object[0]);
            return;
        }
        try {
            for (String cameraId : manager.getCameraIdList()) {
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                Integer facing = (Integer) characteristics.get(CameraCharacteristics.LENS_FACING);
                if ((facing == null || facing.intValue() == 0) && (map = (StreamConfigurationMap) characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)) != null) {
                    Size largest = (Size) Collections.max(Arrays.asList(map.getOutputSizes(256)), new CompareSizesByArea());
                    int displayRotation = this.windowManager.getDefaultDisplay().getRotation();
                    int sensorOrientation = ((Integer) characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
                    boolean swappedDimensions = false;
                    switch (displayRotation) {
                        case 0:
                        case 2:
                            if (sensorOrientation == 90 || sensorOrientation == 270) {
                                swappedDimensions = true;
                                break;
                            }
                        case 1:
                        case 3:
                            if (sensorOrientation == 0 || sensorOrientation == 180) {
                                swappedDimensions = true;
                                break;
                            }
                        default:
                            BBLogger.e("Display rotation is invalid: " + displayRotation, new Object[0]);
                            break;
                    }
                    Point displaySize = new Point();
                    this.windowManager.getDefaultDisplay().getSize(displaySize);
                    int rotatedPreviewWidth = width;
                    int rotatedPreviewHeight = height;
                    int maxPreviewWidth = displaySize.x;
                    int maxPreviewHeight = displaySize.y;
                    if (swappedDimensions) {
                        rotatedPreviewWidth = height;
                        rotatedPreviewHeight = width;
                        maxPreviewWidth = displaySize.y;
                        maxPreviewHeight = displaySize.x;
                    }
                    if (maxPreviewWidth > MAX_PREVIEW_WIDTH) {
                        maxPreviewWidth = MAX_PREVIEW_WIDTH;
                    }
                    if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) {
                        maxPreviewHeight = MAX_PREVIEW_HEIGHT;
                    }
                    this.mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class), rotatedPreviewWidth, rotatedPreviewHeight, maxPreviewWidth, maxPreviewHeight, largest);
                    BBLogger.i("Preview Size: %d, %d", Integer.valueOf(this.mPreviewSize.getWidth()), Integer.valueOf(this.mPreviewSize.getHeight()));
                    this.mCameraId = cameraId;
                    return;
                }
            }
        } catch (CameraAccessException e) {
            BBLogger.e(e);
        } catch (NullPointerException e2) {
        }
    }

    /* access modifiers changed from: private */
    public void openCamera(int width, int height) {
        if (ContextCompat.checkSelfPermission(this.context, "android.permission.CAMERA") == 0) {
            setUpCameraOutputs(width, height);
            configureTransform(width, height);
            CameraManager manager = (CameraManager) this.context.getSystemService("camera");
            try {
                if (!this.mCameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                    throw new RuntimeException("Time out waiting to lock camera opening.");
                }
                manager.openCamera(this.mCameraId, this.mStateCallback, this.mBackgroundHandler);
            } catch (CameraAccessException e) {
                BBLogger.e(e);
            } catch (InterruptedException e2) {
                throw new RuntimeException("Interrupted while trying to lock camera opening.", e2);
            }
        }
    }

    private void closeCamera() {
        try {
            this.mCameraOpenCloseLock.acquire();
            if (this.mCaptureSession != null) {
                this.mCaptureSession.close();
                this.mCaptureSession = null;
            }
            if (this.mCameraDevice != null) {
                this.mCameraDevice.close();
                this.mCameraDevice = null;
            }
            this.mCameraOpenCloseLock.release();
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera closing.", e);
        } catch (Throwable th) {
            this.mCameraOpenCloseLock.release();
            throw th;
        }
    }

    private void startBackgroundThread() {
        this.mBackgroundThread = new HandlerThread("CameraBackground");
        this.mBackgroundThread.start();
        this.mBackgroundHandler = new Handler(this.mBackgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
        if (this.mBackgroundThread != null) {
            this.mBackgroundThread.quitSafely();
            try {
                this.mBackgroundThread.join();
                this.mBackgroundThread = null;
                this.mBackgroundHandler = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public void createCameraPreviewSession() {
        try {
            SurfaceTexture texture = this.cameraView.getSurfaceTexture();
            if ($assertionsDisabled || texture != null) {
                texture.setDefaultBufferSize(this.mPreviewSize.getWidth(), this.mPreviewSize.getHeight());
                Surface surface = new Surface(texture);
                this.mPreviewRequestBuilder = this.mCameraDevice.createCaptureRequest(1);
                this.mPreviewRequestBuilder.addTarget(surface);
                this.mCameraDevice.createCaptureSession(Collections.singletonList(surface), new CameraCaptureSession.StateCallback() {
                    public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                        if (RecordingSession.this.mCameraDevice != null) {
                            CameraCaptureSession unused = RecordingSession.this.mCaptureSession = cameraCaptureSession;
                            try {
                                RecordingSession.this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, 4);
                                RecordingSession.this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, 2);
                                RecordingSession.this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_MODE, 1);
                                CaptureRequest unused2 = RecordingSession.this.mPreviewRequest = RecordingSession.this.mPreviewRequestBuilder.build();
                                RecordingSession.this.mCaptureSession.setRepeatingRequest(RecordingSession.this.mPreviewRequest, RecordingSession.this.mCaptureCallback, RecordingSession.this.mBackgroundHandler);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    }
                }, (Handler) null);
                return;
            }
            throw new AssertionError();
        } catch (CameraAccessException e) {
            BBLogger.e(e);
        }
    }

    /* access modifiers changed from: private */
    public void configureTransform(int viewWidth, int viewHeight) {
        if (this.cameraView != null && this.mPreviewSize != null && this.cameraView.isAvailable()) {
            this.windowManager.getDefaultDisplay().getSize(this.szWindow);
            int rotation = this.windowManager.getDefaultDisplay().getRotation();
            Matrix matrix = new Matrix();
            RectF viewRect = new RectF(0.0f, 0.0f, (float) viewWidth, (float) viewHeight);
            RectF bufferRect = new RectF(0.0f, 0.0f, (float) this.mPreviewSize.getHeight(), (float) this.mPreviewSize.getWidth());
            float centerX = viewRect.centerX();
            float centerY = viewRect.centerY();
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
            float scale = Math.max(((float) viewHeight) / ((float) this.mPreviewSize.getHeight()), ((float) viewWidth) / ((float) this.mPreviewSize.getWidth()));
            matrix.postScale(scale, scale, centerX, centerY);
            if (1 == rotation || 3 == rotation) {
                matrix.postRotate((float) ((rotation - 2) * 90), centerX, centerY);
            } else if (2 == rotation) {
                matrix.postRotate(180.0f, centerX, centerY);
            }
            this.cameraView.setTransform(matrix);
        }
    }

    static class CompareSizesByArea implements Comparator<Size> {
        CompareSizesByArea() {
        }

        public int compare(Size lhs, Size rhs) {
            return Long.signum((((long) lhs.getWidth()) * ((long) lhs.getHeight())) - (((long) rhs.getWidth()) * ((long) rhs.getHeight())));
        }
    }
}
