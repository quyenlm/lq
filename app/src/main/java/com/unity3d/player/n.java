package com.unity3d.player;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.MediaController;
import com.tencent.imsdk.expansion.downloader.Constants;
import com.tencent.tp.a.h;
import java.io.FileInputStream;
import java.io.IOException;

public final class n extends FrameLayout implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback, MediaController.MediaPlayerControl {
    /* access modifiers changed from: private */
    public static boolean a = false;
    private final Context b;
    private final SurfaceView c;
    private final SurfaceHolder d;
    private final String e;
    private final int f;
    private final int g;
    private final boolean h;
    private final long i;
    private final long j;
    private final FrameLayout k;
    private final Display l;
    private int m;
    private int n;
    private int o;
    private int p;
    private MediaPlayer q;
    private MediaController r;
    private boolean s = false;
    private boolean t = false;
    private int u = 0;
    private boolean v = false;
    private boolean w = false;
    private a x;
    private b y;
    private volatile int z = 0;

    public interface a {
        void a(int i);
    }

    public class b implements Runnable {
        private n b;
        private boolean c = false;

        public b(n nVar) {
            this.b = nVar;
        }

        public final void a() {
            this.c = true;
        }

        public final void run() {
            try {
                Thread.sleep(Constants.ACTIVE_THREAD_WATCHDOG);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (!this.c) {
                if (n.a) {
                    n.b("Stopping the video player due to timeout.");
                }
                this.b.CancelOnPrepare();
            }
        }
    }

    protected n(Context context, String str, int i2, int i3, int i4, boolean z2, long j2, long j3, a aVar) {
        super(context);
        this.x = aVar;
        this.b = context;
        this.k = this;
        this.c = new SurfaceView(context);
        this.d = this.c.getHolder();
        this.d.addCallback(this);
        this.d.setType(3);
        this.k.setBackgroundColor(i2);
        this.k.addView(this.c);
        this.l = ((WindowManager) this.b.getSystemService("window")).getDefaultDisplay();
        this.e = str;
        this.f = i3;
        this.g = i4;
        this.h = z2;
        this.i = j2;
        this.j = j3;
        if (a) {
            b("fileName: " + this.e);
        }
        if (a) {
            b("backgroundColor: " + i2);
        }
        if (a) {
            b("controlMode: " + this.f);
        }
        if (a) {
            b("scalingMode: " + this.g);
        }
        if (a) {
            b("isURL: " + this.h);
        }
        if (a) {
            b("videoOffset: " + this.i);
        }
        if (a) {
            b("videoLength: " + this.j);
        }
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    private void a(int i2) {
        this.z = i2;
        if (this.x != null) {
            this.x.a(this.z);
        }
    }

    /* access modifiers changed from: private */
    public static void b(String str) {
        Log.i("Video", "VideoPlayer: " + str);
    }

    private void c() {
        if (this.q != null) {
            this.q.setDisplay(this.d);
            if (!this.v) {
                if (a) {
                    b("Resuming playback");
                }
                this.q.start();
                return;
            }
            return;
        }
        a(0);
        doCleanUp();
        try {
            this.q = new MediaPlayer();
            if (this.h) {
                this.q.setDataSource(this.b, Uri.parse(this.e));
            } else if (this.j != 0) {
                FileInputStream fileInputStream = new FileInputStream(this.e);
                this.q.setDataSource(fileInputStream.getFD(), this.i, this.j);
                fileInputStream.close();
            } else {
                try {
                    AssetFileDescriptor openFd = getResources().getAssets().openFd(this.e);
                    this.q.setDataSource(openFd.getFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
                    openFd.close();
                } catch (IOException e2) {
                    FileInputStream fileInputStream2 = new FileInputStream(this.e);
                    this.q.setDataSource(fileInputStream2.getFD());
                    fileInputStream2.close();
                }
            }
            this.q.setDisplay(this.d);
            this.q.setScreenOnWhilePlaying(true);
            this.q.setOnBufferingUpdateListener(this);
            this.q.setOnCompletionListener(this);
            this.q.setOnPreparedListener(this);
            this.q.setOnVideoSizeChangedListener(this);
            this.q.setAudioStreamType(3);
            this.q.prepareAsync();
            this.y = new b(this);
            new Thread(this.y).start();
        } catch (Exception e3) {
            if (a) {
                b("error: " + e3.getMessage() + e3);
            }
            a(2);
        }
    }

    private void d() {
        if (!isPlaying()) {
            a(1);
            if (a) {
                b("startVideoPlayback");
            }
            updateVideoLayout();
            if (!this.v) {
                start();
            }
        }
    }

    public final void CancelOnPrepare() {
        a(2);
    }

    /* access modifiers changed from: package-private */
    public final boolean a() {
        return this.v;
    }

    public final boolean canPause() {
        return true;
    }

    public final boolean canSeekBackward() {
        return true;
    }

    public final boolean canSeekForward() {
        return true;
    }

    /* access modifiers changed from: protected */
    public final void destroyPlayer() {
        if (a) {
            b("destroyPlayer");
        }
        if (!this.v) {
            pause();
        }
        doCleanUp();
    }

    /* access modifiers changed from: protected */
    public final void doCleanUp() {
        if (this.y != null) {
            this.y.a();
            this.y = null;
        }
        if (this.q != null) {
            this.q.release();
            this.q = null;
        }
        this.o = 0;
        this.p = 0;
        this.t = false;
        this.s = false;
    }

    public final int getBufferPercentage() {
        if (this.h) {
            return this.u;
        }
        return 100;
    }

    public final int getCurrentPosition() {
        if (this.q == null) {
            return 0;
        }
        return this.q.getCurrentPosition();
    }

    public final int getDuration() {
        if (this.q == null) {
            return 0;
        }
        return this.q.getDuration();
    }

    public final boolean isPlaying() {
        boolean z2 = this.t && this.s;
        return this.q == null ? !z2 : this.q.isPlaying() || !z2;
    }

    public final void onBufferingUpdate(MediaPlayer mediaPlayer, int i2) {
        if (a) {
            b("onBufferingUpdate percent:" + i2);
        }
        this.u = i2;
    }

    public final void onCompletion(MediaPlayer mediaPlayer) {
        if (a) {
            b("onCompletion called");
        }
        destroyPlayer();
        a(3);
    }

    public final boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (i2 != 4 && (this.f != 2 || i2 == 0 || keyEvent.isSystem())) {
            return this.r != null ? this.r.onKeyDown(i2, keyEvent) : super.onKeyDown(i2, keyEvent);
        }
        destroyPlayer();
        a(3);
        return true;
    }

    public final void onPrepared(MediaPlayer mediaPlayer) {
        if (a) {
            b("onPrepared called");
        }
        if (this.y != null) {
            this.y.a();
            this.y = null;
        }
        if (this.f == 0 || this.f == 1) {
            this.r = new MediaController(this.b);
            this.r.setMediaPlayer(this);
            this.r.setAnchorView(this);
            this.r.setEnabled(true);
            this.r.show();
        }
        this.t = true;
        if (this.t && this.s) {
            d();
        }
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (this.f != 2 || action != 0) {
            return this.r != null ? this.r.onTouchEvent(motionEvent) : super.onTouchEvent(motionEvent);
        }
        destroyPlayer();
        a(3);
        return true;
    }

    public final void onVideoSizeChanged(MediaPlayer mediaPlayer, int i2, int i3) {
        if (a) {
            b("onVideoSizeChanged called " + i2 + "x" + i3);
        }
        if (i2 != 0 && i3 != 0) {
            this.s = true;
            this.o = i2;
            this.p = i3;
            if (this.t && this.s) {
                d();
            }
        } else if (a) {
            b("invalid video width(" + i2 + ") or height(" + i3 + h.b);
        }
    }

    public final void pause() {
        if (this.q != null) {
            if (this.w) {
                this.q.pause();
            }
            this.v = true;
        }
    }

    public final void seekTo(int i2) {
        if (this.q != null) {
            this.q.seekTo(i2);
        }
    }

    public final void start() {
        if (a) {
            b("Start");
        }
        if (this.q != null) {
            if (this.w) {
                this.q.start();
            }
            this.v = false;
        }
    }

    public final void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
        if (a) {
            b("surfaceChanged called " + i2 + " " + i3 + "x" + i4);
        }
        if (this.m != i3 || this.n != i4) {
            this.m = i3;
            this.n = i4;
            if (this.w) {
                updateVideoLayout();
            }
        }
    }

    public final void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (a) {
            b("surfaceCreated called");
        }
        this.w = true;
        c();
    }

    public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (a) {
            b("surfaceDestroyed called");
        }
        this.w = false;
    }

    /* access modifiers changed from: protected */
    public final void updateVideoLayout() {
        if (a) {
            b("updateVideoLayout");
        }
        if (this.q != null) {
            if (this.m == 0 || this.n == 0) {
                WindowManager windowManager = (WindowManager) this.b.getSystemService("window");
                this.m = windowManager.getDefaultDisplay().getWidth();
                this.n = windowManager.getDefaultDisplay().getHeight();
            }
            int i2 = this.m;
            int i3 = this.n;
            if (this.s) {
                float f2 = ((float) this.o) / ((float) this.p);
                float f3 = ((float) this.m) / ((float) this.n);
                if (this.g == 1) {
                    if (f3 <= f2) {
                        i3 = (int) (((float) this.m) / f2);
                    } else {
                        i2 = (int) (((float) this.n) * f2);
                    }
                } else if (this.g == 2) {
                    if (f3 >= f2) {
                        i3 = (int) (((float) this.m) / f2);
                    } else {
                        i2 = (int) (((float) this.n) * f2);
                    }
                } else if (this.g == 0) {
                    i2 = this.o;
                    i3 = this.p;
                }
            } else if (a) {
                b("updateVideoLayout: Video size is not known yet");
            }
            if (this.m != i2 || this.n != i3) {
                if (a) {
                    b("frameWidth = " + i2 + "; frameHeight = " + i3);
                }
                this.k.updateViewLayout(this.c, new FrameLayout.LayoutParams(i2, i3, 17));
            }
        }
    }
}
