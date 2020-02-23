package com.tencent.liteav.renderer;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import com.tencent.liteav.basic.d.b;
import com.tencent.liteav.basic.d.c;
import com.tencent.liteav.basic.d.e;
import com.tencent.liteav.basic.log.TXCLog;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.Queue;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: TXCGLSurfaceView */
public class d extends e implements SurfaceTexture.OnFrameAvailableListener, GLSurfaceView.Renderer {
    i a;
    j b;
    WeakReference<com.tencent.liteav.basic.c.a> c;
    private SurfaceTexture i;
    private EGLContext j;
    private b k;
    private boolean l;
    private int[] m;
    private float[] n;
    /* access modifiers changed from: private */
    public int o;
    /* access modifiers changed from: private */
    public long p;
    /* access modifiers changed from: private */
    public long q;
    private int r;
    private boolean s;
    private boolean t;
    private Handler u;
    private int v;
    private int w;
    private boolean x;
    /* access modifiers changed from: private */
    public a y;
    private final Queue<Runnable> z;

    /* compiled from: TXCGLSurfaceView */
    public interface a {
        void a(Bitmap bitmap);
    }

    public void setFPS(final int i2) {
        a((Runnable) new Runnable() {
            public void run() {
                int unused = d.this.o = i2;
                if (d.this.o <= 0) {
                    int unused2 = d.this.o = 1;
                } else if (d.this.o > 60) {
                    int unused3 = d.this.o = 60;
                }
                long unused4 = d.this.q = 0;
                long unused5 = d.this.p = 0;
            }
        });
    }

    /* access modifiers changed from: protected */
    public void a() {
        if (this.a != null) {
            this.a.b((SurfaceTexture) null);
        }
    }

    /* access modifiers changed from: protected */
    public void setRunInBackground(boolean z2) {
        if (z2) {
            synchronized (this) {
                TXCLog.d("TXCGLSurfaceView", "background capture enter background");
                this.e = true;
            }
            return;
        }
        a((Runnable) new Runnable() {
            public void run() {
                synchronized (this) {
                    TXCLog.d("TXCGLSurfaceView", "background capture exit background");
                    d.this.e = false;
                }
            }
        });
    }

    public void setListener(i iVar) {
        this.a = iVar;
    }

    public void setTextureListener(j jVar) {
        this.b = jVar;
    }

    public void setNotifyListener(com.tencent.liteav.basic.c.a aVar) {
        this.c = new WeakReference<>(aVar);
    }

    public SurfaceTexture getSurfaceTexture() {
        return this.i;
    }

    public EGLContext getGLContext() {
        return this.j;
    }

    /* access modifiers changed from: protected */
    public int b() {
        if (this.r != 12288) {
            TXCLog.e("TXCGLSurfaceView", "background capture swapbuffer error : " + this.r);
        }
        return this.r;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT >= 21 && this.u != null) {
            this.u.getLooper().quitSafely();
        }
    }

    public void a(Runnable runnable) {
        synchronized (this.z) {
            this.z.add(runnable);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0019, code lost:
        r0.run();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
        if (r0 != null) goto L_0x0019;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(java.util.Queue<java.lang.Runnable> r3) {
        /*
            r2 = this;
            r1 = 0
            monitor-enter(r3)
            boolean r0 = r3.isEmpty()     // Catch:{ all -> 0x0016 }
            if (r0 == 0) goto L_0x000b
            monitor-exit(r3)     // Catch:{ all -> 0x0016 }
            r0 = r1
        L_0x000a:
            return r0
        L_0x000b:
            java.lang.Object r0 = r3.poll()     // Catch:{ all -> 0x0016 }
            java.lang.Runnable r0 = (java.lang.Runnable) r0     // Catch:{ all -> 0x0016 }
            monitor-exit(r3)     // Catch:{ all -> 0x0016 }
            if (r0 != 0) goto L_0x0019
            r0 = r1
            goto L_0x000a
        L_0x0016:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0016 }
            throw r0
        L_0x0019:
            r0.run()
            r0 = 1
            goto L_0x000a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.renderer.d.a(java.util.Queue):boolean");
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        this.j = ((EGL10) EGLContext.getEGL()).eglGetCurrentContext();
        this.m = new int[1];
        this.m[0] = c.a();
        if (this.m[0] <= 0) {
            this.m = null;
            TXCLog.e("TXCGLSurfaceView", "create oes texture error!! at glsurfaceview");
            return;
        }
        this.i = new SurfaceTexture(this.m[0]);
        if (Build.VERSION.SDK_INT >= 21) {
            if (this.u != null) {
                this.u.getLooper().quitSafely();
            }
            HandlerThread handlerThread = new HandlerThread("VideoCaptureThread");
            handlerThread.start();
            this.u = new Handler(handlerThread.getLooper());
            this.i.setOnFrameAvailableListener(this, this.u);
        } else {
            this.i.setOnFrameAvailableListener(this);
        }
        this.k = new b();
        if (this.k.a()) {
            this.k.a(e.e, e.a(com.tencent.liteav.basic.d.d.NORMAL, false, false));
            if (this.a != null) {
                this.a.a(this.i);
            }
        }
    }

    public void onSurfaceChanged(GL10 gl10, int i2, int i3) {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0066, code lost:
        if (r12.b == null) goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0068, code lost:
        r12.b.a(r12.m[0], r12.n);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0087, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0088, code lost:
        r0.printStackTrace();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDrawFrame(javax.microedition.khronos.opengles.GL10 r13) {
        /*
            r12 = this;
            r10 = 1
            r1 = 0
            java.util.Queue<java.lang.Runnable> r0 = r12.z
            r12.a((java.util.Queue<java.lang.Runnable>) r0)
        L_0x0008:
            long r2 = java.lang.System.currentTimeMillis()
            long r4 = r12.q
            r6 = 0
            int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r0 != 0) goto L_0x0016
            r12.q = r2
        L_0x0016:
            long r4 = r12.q
            long r4 = r2 - r4
            long r6 = r12.p
            r8 = 1000(0x3e8, double:4.94E-321)
            long r6 = r6 * r8
            int r0 = r12.o
            long r8 = (long) r0
            long r6 = r6 / r8
            int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r0 >= 0) goto L_0x002b
            r12.f()
            goto L_0x0008
        L_0x002b:
            long r4 = r12.p
            long r4 = r4 + r10
            r12.p = r4
            long r4 = r12.q
            long r2 = r2 - r4
            r4 = 2000(0x7d0, double:9.88E-321)
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 <= 0) goto L_0x0041
            r12.p = r10
            long r2 = java.lang.System.currentTimeMillis()
            r12.q = r2
        L_0x0041:
            boolean r0 = r12.s
            if (r0 == 0) goto L_0x0046
        L_0x0045:
            return
        L_0x0046:
            monitor-enter(r12)
            boolean r0 = r12.t     // Catch:{ all -> 0x004d }
            if (r0 != 0) goto L_0x0050
            monitor-exit(r12)     // Catch:{ all -> 0x004d }
            goto L_0x0045
        L_0x004d:
            r0 = move-exception
            monitor-exit(r12)     // Catch:{ all -> 0x004d }
            throw r0
        L_0x0050:
            android.graphics.SurfaceTexture r0 = r12.i     // Catch:{ all -> 0x004d }
            if (r0 == 0) goto L_0x0060
            android.graphics.SurfaceTexture r0 = r12.i     // Catch:{ all -> 0x004d }
            r0.updateTexImage()     // Catch:{ all -> 0x004d }
            android.graphics.SurfaceTexture r0 = r12.i     // Catch:{ all -> 0x004d }
            float[] r2 = r12.n     // Catch:{ all -> 0x004d }
            r0.getTransformMatrix(r2)     // Catch:{ all -> 0x004d }
        L_0x0060:
            r0 = 0
            r12.t = r0     // Catch:{ all -> 0x004d }
            monitor-exit(r12)     // Catch:{ all -> 0x004d }
            com.tencent.liteav.renderer.j r0 = r12.b     // Catch:{ Exception -> 0x0087 }
            if (r0 == 0) goto L_0x0074
            com.tencent.liteav.renderer.j r0 = r12.b     // Catch:{ Exception -> 0x0087 }
            int[] r2 = r12.m     // Catch:{ Exception -> 0x0087 }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Exception -> 0x0087 }
            float[] r3 = r12.n     // Catch:{ Exception -> 0x0087 }
            r0.a(r2, r3)     // Catch:{ Exception -> 0x0087 }
        L_0x0074:
            r12.e()
            monitor-enter(r12)
            boolean r0 = r12.e     // Catch:{ all -> 0x008e }
            if (r0 != 0) goto L_0x008c
            r0 = 1
        L_0x007d:
            monitor-exit(r12)     // Catch:{ all -> 0x008e }
            if (r0 == 0) goto L_0x0045
            int r0 = r12.c()
            r12.r = r0
            goto L_0x0045
        L_0x0087:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0074
        L_0x008c:
            r0 = r1
            goto L_0x007d
        L_0x008e:
            r0 = move-exception
            monitor-exit(r12)     // Catch:{ all -> 0x008e }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.renderer.d.onDrawFrame(javax.microedition.khronos.opengles.GL10):void");
    }

    private void e() {
        int i2;
        int i3;
        if (this.x) {
            if (!(this.v == 0 || this.w == 0)) {
                boolean z2 = getWidth() <= getHeight();
                int i4 = this.w >= this.v ? this.w : this.v;
                int i5 = this.w >= this.v ? this.v : this.w;
                if (z2) {
                    i2 = i4;
                    i3 = i5;
                } else {
                    i2 = i5;
                    i3 = i4;
                }
                final ByteBuffer allocate = ByteBuffer.allocate(i3 * i2 * 4);
                final Bitmap createBitmap = Bitmap.createBitmap(i3, i2, Bitmap.Config.ARGB_8888);
                allocate.position(0);
                GLES20.glReadPixels(0, 0, i3, i2, 6408, 5121, allocate);
                final int i6 = i3;
                final int i7 = i2;
                new Thread(new Runnable() {
                    public void run() {
                        allocate.position(0);
                        createBitmap.copyPixelsFromBuffer(allocate);
                        Matrix matrix = new Matrix();
                        matrix.setScale(1.0f, -1.0f);
                        Bitmap createBitmap = Bitmap.createBitmap(createBitmap, 0, 0, i6, i7, matrix, false);
                        if (d.this.y != null) {
                            d.this.y.a(createBitmap);
                            a unused = d.this.y = null;
                        }
                        createBitmap.recycle();
                    }
                }).start();
            }
            this.x = false;
        }
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        if (!this.l) {
            com.tencent.liteav.basic.util.a.a(this.c, 1007, "首帧画面采集完成");
            this.l = true;
        }
        this.s = false;
        synchronized (this) {
            this.t = true;
        }
    }

    private void f() {
        try {
            Thread.sleep(15);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
