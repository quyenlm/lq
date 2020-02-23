package com.google.android.gms.vision;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.SystemClock;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import com.beetalk.sdk.ShareConstants;
import com.google.android.gms.common.images.Size;
import com.tencent.rtmp2.TXLiveConstants;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CameraSource {
    @SuppressLint({"InlinedApi"})
    public static final int CAMERA_FACING_BACK = 0;
    @SuppressLint({"InlinedApi"})
    public static final int CAMERA_FACING_FRONT = 1;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public int zzOa;
    /* access modifiers changed from: private */
    public Map<byte[], ByteBuffer> zzbMA;
    /* access modifiers changed from: private */
    public final Object zzbMo;
    /* access modifiers changed from: private */
    public Camera zzbMp;
    /* access modifiers changed from: private */
    public int zzbMq;
    /* access modifiers changed from: private */
    public Size zzbMr;
    /* access modifiers changed from: private */
    public float zzbMs;
    /* access modifiers changed from: private */
    public int zzbMt;
    /* access modifiers changed from: private */
    public int zzbMu;
    /* access modifiers changed from: private */
    public boolean zzbMv;
    private SurfaceTexture zzbMw;
    private boolean zzbMx;
    private Thread zzbMy;
    /* access modifiers changed from: private */
    public zzb zzbMz;

    public static class Builder {
        private final Detector<?> zzbMB;
        private CameraSource zzbMC = new CameraSource();

        public Builder(Context context, Detector<?> detector) {
            if (context == null) {
                throw new IllegalArgumentException("No context supplied.");
            } else if (detector == null) {
                throw new IllegalArgumentException("No detector supplied.");
            } else {
                this.zzbMB = detector;
                Context unused = this.zzbMC.mContext = context;
            }
        }

        public CameraSource build() {
            CameraSource cameraSource = this.zzbMC;
            CameraSource cameraSource2 = this.zzbMC;
            cameraSource2.getClass();
            zzb unused = cameraSource.zzbMz = new zzb(this.zzbMB);
            return this.zzbMC;
        }

        public Builder setAutoFocusEnabled(boolean z) {
            boolean unused = this.zzbMC.zzbMv = z;
            return this;
        }

        public Builder setFacing(int i) {
            if (i == 0 || i == 1) {
                int unused = this.zzbMC.zzbMq = i;
                return this;
            }
            throw new IllegalArgumentException(new StringBuilder(27).append("Invalid camera: ").append(i).toString());
        }

        public Builder setRequestedFps(float f) {
            if (f <= 0.0f) {
                throw new IllegalArgumentException(new StringBuilder(28).append("Invalid fps: ").append(f).toString());
            }
            float unused = this.zzbMC.zzbMs = f;
            return this;
        }

        public Builder setRequestedPreviewSize(int i, int i2) {
            if (i <= 0 || i > 1000000 || i2 <= 0 || i2 > 1000000) {
                throw new IllegalArgumentException(new StringBuilder(45).append("Invalid preview size: ").append(i).append("x").append(i2).toString());
            }
            int unused = this.zzbMC.zzbMt = i;
            int unused2 = this.zzbMC.zzbMu = i2;
            return this;
        }
    }

    public interface PictureCallback {
        void onPictureTaken(byte[] bArr);
    }

    public interface ShutterCallback {
        void onShutter();
    }

    class zza implements Camera.PreviewCallback {
        private zza() {
        }

        public final void onPreviewFrame(byte[] bArr, Camera camera) {
            CameraSource.this.zzbMz.zza(bArr, camera);
        }
    }

    class zzb implements Runnable {
        private boolean mActive = true;
        private final Object mLock = new Object();
        private long zzagZ = SystemClock.elapsedRealtime();
        private Detector<?> zzbMB;
        private long zzbME;
        private int zzbMF = 0;
        private ByteBuffer zzbMG;

        zzb(Detector<?> detector) {
            this.zzbMB = detector;
        }

        /* access modifiers changed from: package-private */
        @SuppressLint({"Assert"})
        public final void release() {
            this.zzbMB.release();
            this.zzbMB = null;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            r6.zzbMB.receiveFrame(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0078, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
            android.util.Log.e("CameraSource", "Exception thrown from receiver.", r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x008f, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0090, code lost:
            com.google.android.gms.vision.CameraSource.zzb(r6.zzbMD).addCallbackBuffer(r2.array());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x009d, code lost:
            throw r0;
         */
        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        @android.annotation.SuppressLint({"InlinedApi"})
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void run() {
            /*
                r6 = this;
            L_0x0000:
                java.lang.Object r1 = r6.mLock
                monitor-enter(r1)
            L_0x0003:
                boolean r0 = r6.mActive     // Catch:{ all -> 0x0021 }
                if (r0 == 0) goto L_0x001b
                java.nio.ByteBuffer r0 = r6.zzbMG     // Catch:{ all -> 0x0021 }
                if (r0 != 0) goto L_0x001b
                java.lang.Object r0 = r6.mLock     // Catch:{ InterruptedException -> 0x0011 }
                r0.wait()     // Catch:{ InterruptedException -> 0x0011 }
                goto L_0x0003
            L_0x0011:
                r0 = move-exception
                java.lang.String r2 = "CameraSource"
                java.lang.String r3 = "Frame processing loop terminated."
                android.util.Log.d(r2, r3, r0)     // Catch:{ all -> 0x0021 }
                monitor-exit(r1)     // Catch:{ all -> 0x0021 }
            L_0x001a:
                return
            L_0x001b:
                boolean r0 = r6.mActive     // Catch:{ all -> 0x0021 }
                if (r0 != 0) goto L_0x0024
                monitor-exit(r1)     // Catch:{ all -> 0x0021 }
                goto L_0x001a
            L_0x0021:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0021 }
                throw r0
            L_0x0024:
                com.google.android.gms.vision.Frame$Builder r0 = new com.google.android.gms.vision.Frame$Builder     // Catch:{ all -> 0x0021 }
                r0.<init>()     // Catch:{ all -> 0x0021 }
                java.nio.ByteBuffer r2 = r6.zzbMG     // Catch:{ all -> 0x0021 }
                com.google.android.gms.vision.CameraSource r3 = com.google.android.gms.vision.CameraSource.this     // Catch:{ all -> 0x0021 }
                com.google.android.gms.common.images.Size r3 = r3.zzbMr     // Catch:{ all -> 0x0021 }
                int r3 = r3.getWidth()     // Catch:{ all -> 0x0021 }
                com.google.android.gms.vision.CameraSource r4 = com.google.android.gms.vision.CameraSource.this     // Catch:{ all -> 0x0021 }
                com.google.android.gms.common.images.Size r4 = r4.zzbMr     // Catch:{ all -> 0x0021 }
                int r4 = r4.getHeight()     // Catch:{ all -> 0x0021 }
                r5 = 17
                com.google.android.gms.vision.Frame$Builder r0 = r0.setImageData(r2, r3, r4, r5)     // Catch:{ all -> 0x0021 }
                int r2 = r6.zzbMF     // Catch:{ all -> 0x0021 }
                com.google.android.gms.vision.Frame$Builder r0 = r0.setId(r2)     // Catch:{ all -> 0x0021 }
                long r2 = r6.zzbME     // Catch:{ all -> 0x0021 }
                com.google.android.gms.vision.Frame$Builder r0 = r0.setTimestampMillis(r2)     // Catch:{ all -> 0x0021 }
                com.google.android.gms.vision.CameraSource r2 = com.google.android.gms.vision.CameraSource.this     // Catch:{ all -> 0x0021 }
                int r2 = r2.zzOa     // Catch:{ all -> 0x0021 }
                com.google.android.gms.vision.Frame$Builder r0 = r0.setRotation(r2)     // Catch:{ all -> 0x0021 }
                com.google.android.gms.vision.Frame r0 = r0.build()     // Catch:{ all -> 0x0021 }
                java.nio.ByteBuffer r2 = r6.zzbMG     // Catch:{ all -> 0x0021 }
                r3 = 0
                r6.zzbMG = r3     // Catch:{ all -> 0x0021 }
                monitor-exit(r1)     // Catch:{ all -> 0x0021 }
                com.google.android.gms.vision.Detector<?> r1 = r6.zzbMB     // Catch:{ Throwable -> 0x0078 }
                r1.receiveFrame(r0)     // Catch:{ Throwable -> 0x0078 }
                com.google.android.gms.vision.CameraSource r0 = com.google.android.gms.vision.CameraSource.this
                android.hardware.Camera r0 = r0.zzbMp
                byte[] r1 = r2.array()
                r0.addCallbackBuffer(r1)
                goto L_0x0000
            L_0x0078:
                r0 = move-exception
                java.lang.String r1 = "CameraSource"
                java.lang.String r3 = "Exception thrown from receiver."
                android.util.Log.e(r1, r3, r0)     // Catch:{ all -> 0x008f }
                com.google.android.gms.vision.CameraSource r0 = com.google.android.gms.vision.CameraSource.this
                android.hardware.Camera r0 = r0.zzbMp
                byte[] r1 = r2.array()
                r0.addCallbackBuffer(r1)
                goto L_0x0000
            L_0x008f:
                r0 = move-exception
                com.google.android.gms.vision.CameraSource r1 = com.google.android.gms.vision.CameraSource.this
                android.hardware.Camera r1 = r1.zzbMp
                byte[] r2 = r2.array()
                r1.addCallbackBuffer(r2)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.vision.CameraSource.zzb.run():void");
        }

        /* access modifiers changed from: package-private */
        public final void setActive(boolean z) {
            synchronized (this.mLock) {
                this.mActive = z;
                this.mLock.notifyAll();
            }
        }

        /* access modifiers changed from: package-private */
        public final void zza(byte[] bArr, Camera camera) {
            synchronized (this.mLock) {
                if (this.zzbMG != null) {
                    camera.addCallbackBuffer(this.zzbMG.array());
                    this.zzbMG = null;
                }
                if (!CameraSource.this.zzbMA.containsKey(bArr)) {
                    Log.d("CameraSource", "Skipping frame. Could not find ByteBuffer associated with the image data from the camera.");
                    return;
                }
                this.zzbME = SystemClock.elapsedRealtime() - this.zzagZ;
                this.zzbMF++;
                this.zzbMG = (ByteBuffer) CameraSource.this.zzbMA.get(bArr);
                this.mLock.notifyAll();
            }
        }
    }

    class zzc implements Camera.PictureCallback {
        /* access modifiers changed from: private */
        public PictureCallback zzbMH;

        private zzc() {
        }

        public final void onPictureTaken(byte[] bArr, Camera camera) {
            if (this.zzbMH != null) {
                this.zzbMH.onPictureTaken(bArr);
            }
            synchronized (CameraSource.this.zzbMo) {
                if (CameraSource.this.zzbMp != null) {
                    CameraSource.this.zzbMp.startPreview();
                }
            }
        }
    }

    static class zzd implements Camera.ShutterCallback {
        /* access modifiers changed from: private */
        public ShutterCallback zzbMI;

        private zzd() {
        }

        public final void onShutter() {
            if (this.zzbMI != null) {
                this.zzbMI.onShutter();
            }
        }
    }

    static class zze {
        private Size zzbMJ;
        private Size zzbMK;

        public zze(Camera.Size size, Camera.Size size2) {
            this.zzbMJ = new Size(size.width, size.height);
            if (size2 != null) {
                this.zzbMK = new Size(size2.width, size2.height);
            }
        }

        public final Size zzDL() {
            return this.zzbMJ;
        }

        public final Size zzDM() {
            return this.zzbMK;
        }
    }

    private CameraSource() {
        this.zzbMo = new Object();
        this.zzbMq = 0;
        this.zzbMs = 30.0f;
        this.zzbMt = 1024;
        this.zzbMu = 768;
        this.zzbMv = false;
        this.zzbMA = new HashMap();
    }

    @SuppressLint({"InlinedApi"})
    private final Camera zzDK() throws IOException {
        int i;
        int i2;
        int i3;
        int i4 = 0;
        int i5 = this.zzbMq;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int i6 = 0;
        while (true) {
            if (i6 >= Camera.getNumberOfCameras()) {
                i = -1;
                break;
            }
            Camera.getCameraInfo(i6, cameraInfo);
            if (cameraInfo.facing == i5) {
                i = i6;
                break;
            }
            i6++;
        }
        if (i == -1) {
            throw new IOException("Could not find requested camera.");
        }
        Camera open = Camera.open(i);
        zze zza2 = zza(open, this.zzbMt, this.zzbMu);
        if (zza2 == null) {
            throw new IOException("Could not find suitable preview size.");
        }
        Size zzDM = zza2.zzDM();
        this.zzbMr = zza2.zzDL();
        int[] zza3 = zza(open, this.zzbMs);
        if (zza3 == null) {
            throw new IOException("Could not find suitable preview frames per second range.");
        }
        Camera.Parameters parameters = open.getParameters();
        if (zzDM != null) {
            parameters.setPictureSize(zzDM.getWidth(), zzDM.getHeight());
        }
        parameters.setPreviewSize(this.zzbMr.getWidth(), this.zzbMr.getHeight());
        parameters.setPreviewFpsRange(zza3[0], zza3[1]);
        parameters.setPreviewFormat(17);
        int rotation = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getRotation();
        switch (rotation) {
            case 0:
                break;
            case 1:
                i4 = 90;
                break;
            case 2:
                i4 = ShareConstants.ERROR_CODE.GG_RESULT_UNKNOWN_ERROR;
                break;
            case 3:
                i4 = TXLiveConstants.RENDER_ROTATION_LANDSCAPE;
                break;
            default:
                Log.e("CameraSource", new StringBuilder(31).append("Bad rotation value: ").append(rotation).toString());
                break;
        }
        Camera.CameraInfo cameraInfo2 = new Camera.CameraInfo();
        Camera.getCameraInfo(i, cameraInfo2);
        if (cameraInfo2.facing == 1) {
            int i7 = (cameraInfo2.orientation + i4) % 360;
            i2 = (360 - i7) % 360;
            i3 = i7;
        } else {
            int i8 = ((cameraInfo2.orientation - i4) + 360) % 360;
            i2 = i8;
            i3 = i8;
        }
        this.zzOa = i3 / 90;
        open.setDisplayOrientation(i2);
        parameters.setRotation(i3);
        if (this.zzbMv) {
            if (parameters.getSupportedFocusModes().contains("continuous-video")) {
                parameters.setFocusMode("continuous-video");
            } else {
                Log.i("CameraSource", "Camera auto focus is not supported on this device.");
            }
        }
        open.setParameters(parameters);
        open.setPreviewCallbackWithBuffer(new zza());
        open.addCallbackBuffer(zza(this.zzbMr));
        open.addCallbackBuffer(zza(this.zzbMr));
        open.addCallbackBuffer(zza(this.zzbMr));
        open.addCallbackBuffer(zza(this.zzbMr));
        return open;
    }

    private static zze zza(Camera camera, int i, int i2) {
        zze zze2 = null;
        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        List<Camera.Size> supportedPictureSizes = parameters.getSupportedPictureSizes();
        ArrayList arrayList = new ArrayList();
        for (Camera.Size next : supportedPreviewSizes) {
            float f = ((float) next.width) / ((float) next.height);
            Iterator<Camera.Size> it = supportedPictureSizes.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Camera.Size next2 = it.next();
                if (Math.abs(f - (((float) next2.width) / ((float) next2.height))) < 0.01f) {
                    arrayList.add(new zze(next, next2));
                    break;
                }
            }
        }
        if (arrayList.size() == 0) {
            Log.w("CameraSource", "No preview sizes have a corresponding same-aspect-ratio picture size");
            for (Camera.Size zze3 : supportedPreviewSizes) {
                arrayList.add(new zze(zze3, (Camera.Size) null));
            }
        }
        int i3 = Integer.MAX_VALUE;
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i4 = 0;
        while (i4 < size) {
            Object obj = arrayList2.get(i4);
            i4++;
            zze zze4 = (zze) obj;
            Size zzDL = zze4.zzDL();
            int abs = Math.abs(zzDL.getHeight() - i2) + Math.abs(zzDL.getWidth() - i);
            if (abs >= i3) {
                abs = i3;
                zze4 = zze2;
            }
            i3 = abs;
            zze2 = zze4;
        }
        return zze2;
    }

    @SuppressLint({"InlinedApi"})
    private final byte[] zza(Size size) {
        byte[] bArr = new byte[(((int) Math.ceil(((double) ((long) (ImageFormat.getBitsPerPixel(17) * (size.getHeight() * size.getWidth())))) / 8.0d)) + 1)];
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        if (!wrap.hasArray() || wrap.array() != bArr) {
            throw new IllegalStateException("Failed to create valid buffer for camera source.");
        }
        this.zzbMA.put(bArr, wrap);
        return bArr;
    }

    @SuppressLint({"InlinedApi"})
    private static int[] zza(Camera camera, float f) {
        int i = (int) (1000.0f * f);
        int[] iArr = null;
        int i2 = Integer.MAX_VALUE;
        for (int[] next : camera.getParameters().getSupportedPreviewFpsRange()) {
            int abs = Math.abs(i - next[0]) + Math.abs(i - next[1]);
            if (abs >= i2) {
                abs = i2;
                next = iArr;
            }
            i2 = abs;
            iArr = next;
        }
        return iArr;
    }

    public int getCameraFacing() {
        return this.zzbMq;
    }

    public Size getPreviewSize() {
        return this.zzbMr;
    }

    public void release() {
        synchronized (this.zzbMo) {
            stop();
            this.zzbMz.release();
        }
    }

    @RequiresPermission("android.permission.CAMERA")
    public CameraSource start() throws IOException {
        synchronized (this.zzbMo) {
            if (this.zzbMp == null) {
                this.zzbMp = zzDK();
                this.zzbMw = new SurfaceTexture(100);
                this.zzbMp.setPreviewTexture(this.zzbMw);
                this.zzbMx = true;
                this.zzbMp.startPreview();
                this.zzbMy = new Thread(this.zzbMz);
                this.zzbMz.setActive(true);
                this.zzbMy.start();
            }
        }
        return this;
    }

    @RequiresPermission("android.permission.CAMERA")
    public CameraSource start(SurfaceHolder surfaceHolder) throws IOException {
        synchronized (this.zzbMo) {
            if (this.zzbMp == null) {
                this.zzbMp = zzDK();
                this.zzbMp.setPreviewDisplay(surfaceHolder);
                this.zzbMp.startPreview();
                this.zzbMy = new Thread(this.zzbMz);
                this.zzbMz.setActive(true);
                this.zzbMy.start();
                this.zzbMx = false;
            }
        }
        return this;
    }

    public void stop() {
        synchronized (this.zzbMo) {
            this.zzbMz.setActive(false);
            if (this.zzbMy != null) {
                try {
                    this.zzbMy.join();
                } catch (InterruptedException e) {
                    Log.d("CameraSource", "Frame processing thread interrupted on release.");
                }
                this.zzbMy = null;
            }
            if (this.zzbMp != null) {
                this.zzbMp.stopPreview();
                this.zzbMp.setPreviewCallbackWithBuffer((Camera.PreviewCallback) null);
                try {
                    if (this.zzbMx) {
                        this.zzbMp.setPreviewTexture((SurfaceTexture) null);
                    } else {
                        this.zzbMp.setPreviewDisplay((SurfaceHolder) null);
                    }
                } catch (Exception e2) {
                    String valueOf = String.valueOf(e2);
                    Log.e("CameraSource", new StringBuilder(String.valueOf(valueOf).length() + 32).append("Failed to clear camera preview: ").append(valueOf).toString());
                }
                this.zzbMp.release();
                this.zzbMp = null;
            }
            this.zzbMA.clear();
        }
        return;
    }

    public void takePicture(ShutterCallback shutterCallback, PictureCallback pictureCallback) {
        synchronized (this.zzbMo) {
            if (this.zzbMp != null) {
                zzd zzd2 = new zzd();
                ShutterCallback unused = zzd2.zzbMI = shutterCallback;
                zzc zzc2 = new zzc();
                PictureCallback unused2 = zzc2.zzbMH = pictureCallback;
                this.zzbMp.takePicture(zzd2, (Camera.PictureCallback) null, (Camera.PictureCallback) null, zzc2);
            }
        }
    }
}
