package com.tencent.liteav.renderer;

import android.opengl.GLDebugHelper;
import android.opengl.GLSurfaceView;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.tencent.liteav.basic.log.TXCLog;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: TXCGLSurfaceViewBase */
public class e extends SurfaceView implements SurfaceHolder.Callback {
    /* access modifiers changed from: private */
    public static final j a = new j();
    private i b;
    /* access modifiers changed from: private */
    public GLSurfaceView.Renderer c;
    protected boolean d;
    protected boolean e;
    protected final WeakReference<e> f;
    protected boolean g;
    protected boolean h;
    private boolean i;
    /* access modifiers changed from: private */
    public C0025e j;
    /* access modifiers changed from: private */
    public f k;
    /* access modifiers changed from: private */
    public g l;
    /* access modifiers changed from: private */
    public k m;
    /* access modifiers changed from: private */
    public int n;
    /* access modifiers changed from: private */
    public int o;
    /* access modifiers changed from: private */
    public boolean p;

    /* renamed from: com.tencent.liteav.renderer.e$e  reason: collision with other inner class name */
    /* compiled from: TXCGLSurfaceViewBase */
    public interface C0025e {
        EGLConfig a(EGL10 egl10, EGLDisplay eGLDisplay);
    }

    /* compiled from: TXCGLSurfaceViewBase */
    public interface f {
        EGLContext a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig);

        void a(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext);
    }

    /* compiled from: TXCGLSurfaceViewBase */
    public interface g {
        EGLSurface a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj);

        void a(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface);
    }

    /* compiled from: TXCGLSurfaceViewBase */
    public interface k {
        GL a(GL gl);
    }

    /* access modifiers changed from: protected */
    public int b() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            if (this.b != null) {
                this.b.g();
            }
        } finally {
            super.finalize();
        }
    }

    public void setGLWrapper(k kVar) {
        this.m = kVar;
    }

    public void setDebugFlags(int i2) {
        this.n = i2;
    }

    public int getDebugFlags() {
        return this.n;
    }

    public void setPreserveEGLContextOnPause(boolean z) {
        this.p = z;
    }

    public boolean getPreserveEGLContextOnPause() {
        return this.p;
    }

    public void setRenderer(GLSurfaceView.Renderer renderer) {
        e();
        if (this.j == null) {
            this.j = new m(true);
        }
        if (this.k == null) {
            this.k = new c();
        }
        if (this.l == null) {
            this.l = new d();
        }
        this.c = renderer;
        this.b = new i(this.f);
        this.b.start();
    }

    public void setEGLContextFactory(f fVar) {
        e();
        this.k = fVar;
    }

    public void setEGLWindowSurfaceFactory(g gVar) {
        e();
        this.l = gVar;
    }

    public void setEGLConfigChooser(C0025e eVar) {
        e();
        this.j = eVar;
    }

    public void setEGLConfigChooser(boolean z) {
        setEGLConfigChooser((C0025e) new m(z));
    }

    public void setEGLContextClientVersion(int i2) {
        e();
        this.o = i2;
    }

    public void setRenderMode(int i2) {
        this.b.a(i2);
    }

    public int getRenderMode() {
        return this.b.d();
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.b.e();
        setRunInBackground(false);
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        setRunInBackground(true);
        if (!this.d) {
            this.b.a((Runnable) new Runnable() {
                public void run() {
                    e.this.a();
                }
            });
            this.b.f();
        }
    }

    /* access modifiers changed from: protected */
    public void a() {
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
        this.b.a(i3, i4);
    }

    /* access modifiers changed from: protected */
    public void setRunInBackground(boolean z) {
        this.e = z;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        int i2;
        super.onAttachedToWindow();
        if (this.i && this.c != null) {
            if (this.b != null) {
                i2 = this.b.d();
            } else {
                i2 = 1;
            }
            this.b = new i(this.f);
            if (i2 != 1) {
                this.b.a(i2);
            }
            this.b.start();
        }
        this.i = false;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (this.b != null) {
            this.b.g();
        }
        this.i = true;
        super.onDetachedFromWindow();
    }

    /* compiled from: TXCGLSurfaceViewBase */
    static class i extends Thread {
        private boolean a;
        /* access modifiers changed from: private */
        public boolean b;
        private boolean c;
        private boolean d;
        private boolean e;
        private boolean f;
        private boolean g;
        private boolean h;
        private boolean i;
        private boolean j;
        private boolean k;
        private int l = 0;
        private int m = 0;
        private int n = 1;
        private boolean o = true;
        private boolean p;
        private ArrayList<Runnable> q = new ArrayList<>();
        private boolean r = true;
        private h s;
        private WeakReference<e> t;

        i(WeakReference<e> weakReference) {
            this.t = weakReference;
        }

        public void run() {
            setName("GLThread " + getId());
            try {
                i();
            } catch (InterruptedException e2) {
            } finally {
                e.a.a(this);
            }
        }

        public int a() {
            return this.s.c();
        }

        public h b() {
            return this.s;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:113:0x01c6, code lost:
            if (r3 == false) goto L_0x01e6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:117:0x01d0, code lost:
            if (r17.s.b() == false) goto L_0x026c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:118:0x01d2, code lost:
            r3 = com.tencent.liteav.renderer.e.d();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:119:0x01d6, code lost:
            monitor-enter(r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:122:?, code lost:
            r17.j = true;
            com.tencent.liteav.renderer.e.d().notifyAll();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:123:0x01e3, code lost:
            monitor-exit(r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:124:0x01e4, code lost:
            r3 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:125:0x01e6, code lost:
            if (r11 == false) goto L_0x0290;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:128:?, code lost:
            r1 = (javax.microedition.khronos.opengles.GL10) r17.s.d();
            com.tencent.liteav.renderer.e.d().a(r1);
            r11 = false;
            r13 = r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:129:0x01fb, code lost:
            if (r12 == false) goto L_0x0217;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:130:0x01fd, code lost:
            r1 = (com.tencent.liteav.renderer.e) r17.t.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:131:0x0207, code lost:
            if (r1 == null) goto L_0x0216;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:132:0x0209, code lost:
            com.tencent.liteav.renderer.e.b(r1).onSurfaceCreated(r13, r17.s.d);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:133:0x0216, code lost:
            r12 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:134:0x0217, code lost:
            if (r9 == false) goto L_0x022d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:135:0x0219, code lost:
            r1 = (com.tencent.liteav.renderer.e) r17.t.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:136:0x0223, code lost:
            if (r1 == null) goto L_0x022c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:137:0x0225, code lost:
            com.tencent.liteav.renderer.e.b(r1).onSurfaceChanged(r13, r6, r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:138:0x022c, code lost:
            r9 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:139:0x022d, code lost:
            r1 = (com.tencent.liteav.renderer.e) r17.t.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:140:0x0239, code lost:
            if (r1 == null) goto L_0x029b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:141:0x023b, code lost:
            com.tencent.liteav.renderer.e.b(r1).onDrawFrame(r13);
            r1 = r1.b();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:142:0x0246, code lost:
            switch(r1) {
                case 12288: goto L_0x0262;
                case 12302: goto L_0x0288;
                default: goto L_0x0249;
            };
         */
        /* JADX WARNING: Code restructure failed: missing block: B:143:0x0249, code lost:
            com.tencent.liteav.renderer.e.h.a("GLThread", "eglSwapBuffers", r1);
            r14 = com.tencent.liteav.renderer.e.d();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:144:0x0254, code lost:
            monitor-enter(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:147:?, code lost:
            r17.f = true;
            com.tencent.liteav.renderer.e.d().notifyAll();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:148:0x0261, code lost:
            monitor-exit(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:149:0x0262, code lost:
            if (r8 == false) goto L_0x0299;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:150:0x0264, code lost:
            r1 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:151:0x0265, code lost:
            r2 = r1;
            r14 = r13;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:157:0x026c, code lost:
            r13 = com.tencent.liteav.renderer.e.d();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:158:0x0270, code lost:
            monitor-enter(r13);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:161:?, code lost:
            r17.j = true;
            r17.f = true;
            com.tencent.liteav.renderer.e.d().notifyAll();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:162:0x0282, code lost:
            monitor-exit(r13);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:168:0x0288, code lost:
            r10 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:178:0x0290, code lost:
            r13 = r14;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:181:0x0299, code lost:
            r1 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:182:0x029b, code lost:
            r1 = 12288;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0057, code lost:
            if (r4 == null) goto L_0x01c6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
            r4.run();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x005c, code lost:
            r4 = null;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void i() throws java.lang.InterruptedException {
            /*
                r17 = this;
                com.tencent.liteav.renderer.e$h r1 = new com.tencent.liteav.renderer.e$h
                r0 = r17
                java.lang.ref.WeakReference<com.tencent.liteav.renderer.e> r2 = r0.t
                r1.<init>(r2)
                r0 = r17
                r0.s = r1
                r1 = 0
                r0 = r17
                r0.h = r1
                r1 = 0
                r0 = r17
                r0.i = r1
                r1 = 0
                r12 = 0
                r3 = 0
                r11 = 0
                r10 = 0
                r9 = 0
                r8 = 0
                r2 = 0
                r7 = 0
                r6 = 0
                r5 = 0
                r4 = 0
                r14 = r1
            L_0x0024:
                com.tencent.liteav.renderer.e$j r15 = com.tencent.liteav.renderer.e.a     // Catch:{ all -> 0x01a4 }
                monitor-enter(r15)     // Catch:{ all -> 0x01a4 }
            L_0x0029:
                r0 = r17
                boolean r1 = r0.a     // Catch:{ all -> 0x01a1 }
                if (r1 == 0) goto L_0x0040
                monitor-exit(r15)     // Catch:{ all -> 0x01a1 }
                com.tencent.liteav.renderer.e$j r2 = com.tencent.liteav.renderer.e.a
                monitor-enter(r2)
                r17.j()     // Catch:{ all -> 0x003d }
                r17.k()     // Catch:{ all -> 0x003d }
                monitor-exit(r2)     // Catch:{ all -> 0x003d }
                return
            L_0x003d:
                r1 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x003d }
                throw r1
            L_0x0040:
                r0 = r17
                java.util.ArrayList<java.lang.Runnable> r1 = r0.q     // Catch:{ all -> 0x01a1 }
                boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x01a1 }
                if (r1 != 0) goto L_0x005e
                r0 = r17
                java.util.ArrayList<java.lang.Runnable> r1 = r0.q     // Catch:{ all -> 0x01a1 }
                r4 = 0
                java.lang.Object r1 = r1.remove(r4)     // Catch:{ all -> 0x01a1 }
                java.lang.Runnable r1 = (java.lang.Runnable) r1     // Catch:{ all -> 0x01a1 }
                r4 = r1
            L_0x0056:
                monitor-exit(r15)     // Catch:{ all -> 0x01a1 }
                if (r4 == 0) goto L_0x01c6
                r4.run()     // Catch:{ all -> 0x01a4 }
                r4 = 0
                goto L_0x0024
            L_0x005e:
                r1 = 0
                r0 = r17
                boolean r13 = r0.d     // Catch:{ all -> 0x01a1 }
                r0 = r17
                boolean r0 = r0.c     // Catch:{ all -> 0x01a1 }
                r16 = r0
                r0 = r16
                if (r13 == r0) goto L_0x0296
                r0 = r17
                boolean r1 = r0.c     // Catch:{ all -> 0x01a1 }
                r0 = r17
                boolean r13 = r0.c     // Catch:{ all -> 0x01a1 }
                r0 = r17
                r0.d = r13     // Catch:{ all -> 0x01a1 }
                com.tencent.liteav.renderer.e$j r13 = com.tencent.liteav.renderer.e.a     // Catch:{ all -> 0x01a1 }
                r13.notifyAll()     // Catch:{ all -> 0x01a1 }
                r13 = r1
            L_0x0081:
                r0 = r17
                boolean r1 = r0.k     // Catch:{ all -> 0x01a1 }
                if (r1 == 0) goto L_0x0093
                r17.j()     // Catch:{ all -> 0x01a1 }
                r17.k()     // Catch:{ all -> 0x01a1 }
                r1 = 0
                r0 = r17
                r0.k = r1     // Catch:{ all -> 0x01a1 }
                r7 = 1
            L_0x0093:
                if (r10 == 0) goto L_0x009c
                r17.j()     // Catch:{ all -> 0x01a1 }
                r17.k()     // Catch:{ all -> 0x01a1 }
                r10 = 0
            L_0x009c:
                if (r13 == 0) goto L_0x00a7
                r0 = r17
                boolean r1 = r0.i     // Catch:{ all -> 0x01a1 }
                if (r1 == 0) goto L_0x00a7
                r17.j()     // Catch:{ all -> 0x01a1 }
            L_0x00a7:
                if (r13 == 0) goto L_0x00cb
                r0 = r17
                boolean r1 = r0.h     // Catch:{ all -> 0x01a1 }
                if (r1 == 0) goto L_0x00cb
                r0 = r17
                java.lang.ref.WeakReference<com.tencent.liteav.renderer.e> r1 = r0.t     // Catch:{ all -> 0x01a1 }
                java.lang.Object r1 = r1.get()     // Catch:{ all -> 0x01a1 }
                com.tencent.liteav.renderer.e r1 = (com.tencent.liteav.renderer.e) r1     // Catch:{ all -> 0x01a1 }
                if (r1 != 0) goto L_0x017a
                r1 = 0
            L_0x00bc:
                if (r1 == 0) goto L_0x00c8
                com.tencent.liteav.renderer.e$j r1 = com.tencent.liteav.renderer.e.a     // Catch:{ all -> 0x01a1 }
                boolean r1 = r1.a()     // Catch:{ all -> 0x01a1 }
                if (r1 == 0) goto L_0x00cb
            L_0x00c8:
                r17.k()     // Catch:{ all -> 0x01a1 }
            L_0x00cb:
                if (r13 == 0) goto L_0x00de
                com.tencent.liteav.renderer.e$j r1 = com.tencent.liteav.renderer.e.a     // Catch:{ all -> 0x01a1 }
                boolean r1 = r1.b()     // Catch:{ all -> 0x01a1 }
                if (r1 == 0) goto L_0x00de
                r0 = r17
                com.tencent.liteav.renderer.e$h r1 = r0.s     // Catch:{ all -> 0x01a1 }
                r1.g()     // Catch:{ all -> 0x01a1 }
            L_0x00de:
                r0 = r17
                boolean r1 = r0.e     // Catch:{ all -> 0x01a1 }
                if (r1 != 0) goto L_0x0104
                r0 = r17
                boolean r1 = r0.g     // Catch:{ all -> 0x01a1 }
                if (r1 != 0) goto L_0x0104
                r0 = r17
                boolean r1 = r0.i     // Catch:{ all -> 0x01a1 }
                if (r1 == 0) goto L_0x00f3
                r17.j()     // Catch:{ all -> 0x01a1 }
            L_0x00f3:
                r1 = 1
                r0 = r17
                r0.g = r1     // Catch:{ all -> 0x01a1 }
                r1 = 0
                r0 = r17
                r0.f = r1     // Catch:{ all -> 0x01a1 }
                com.tencent.liteav.renderer.e$j r1 = com.tencent.liteav.renderer.e.a     // Catch:{ all -> 0x01a1 }
                r1.notifyAll()     // Catch:{ all -> 0x01a1 }
            L_0x0104:
                r0 = r17
                boolean r1 = r0.e     // Catch:{ all -> 0x01a1 }
                if (r1 == 0) goto L_0x011c
                r0 = r17
                boolean r1 = r0.g     // Catch:{ all -> 0x01a1 }
                if (r1 == 0) goto L_0x011c
                r1 = 0
                r0 = r17
                r0.g = r1     // Catch:{ all -> 0x01a1 }
                com.tencent.liteav.renderer.e$j r1 = com.tencent.liteav.renderer.e.a     // Catch:{ all -> 0x01a1 }
                r1.notifyAll()     // Catch:{ all -> 0x01a1 }
            L_0x011c:
                if (r2 == 0) goto L_0x012c
                r8 = 0
                r2 = 0
                r1 = 1
                r0 = r17
                r0.p = r1     // Catch:{ all -> 0x01a1 }
                com.tencent.liteav.renderer.e$j r1 = com.tencent.liteav.renderer.e.a     // Catch:{ all -> 0x01a1 }
                r1.notifyAll()     // Catch:{ all -> 0x01a1 }
            L_0x012c:
                boolean r1 = r17.l()     // Catch:{ all -> 0x01a1 }
                if (r1 == 0) goto L_0x01bd
                r0 = r17
                boolean r1 = r0.h     // Catch:{ all -> 0x01a1 }
                if (r1 != 0) goto L_0x013b
                if (r7 == 0) goto L_0x0180
                r7 = 0
            L_0x013b:
                r0 = r17
                boolean r1 = r0.h     // Catch:{ all -> 0x01a1 }
                if (r1 == 0) goto L_0x014f
                r0 = r17
                boolean r1 = r0.i     // Catch:{ all -> 0x01a1 }
                if (r1 != 0) goto L_0x014f
                r1 = 1
                r0 = r17
                r0.i = r1     // Catch:{ all -> 0x01a1 }
                r3 = 1
                r11 = 1
                r9 = 1
            L_0x014f:
                r0 = r17
                boolean r1 = r0.i     // Catch:{ all -> 0x01a1 }
                if (r1 == 0) goto L_0x01bd
                r0 = r17
                boolean r1 = r0.r     // Catch:{ all -> 0x01a1 }
                if (r1 == 0) goto L_0x0293
                r9 = 1
                r0 = r17
                int r6 = r0.l     // Catch:{ all -> 0x01a1 }
                r0 = r17
                int r1 = r0.m     // Catch:{ all -> 0x01a1 }
                r8 = 1
                r3 = 1
                r5 = 0
                r0 = r17
                r0.r = r5     // Catch:{ all -> 0x01a1 }
            L_0x016b:
                r5 = 0
                r0 = r17
                r0.o = r5     // Catch:{ all -> 0x01a1 }
                com.tencent.liteav.renderer.e$j r5 = com.tencent.liteav.renderer.e.a     // Catch:{ all -> 0x01a1 }
                r5.notifyAll()     // Catch:{ all -> 0x01a1 }
                r5 = r1
                goto L_0x0056
            L_0x017a:
                boolean r1 = r1.p     // Catch:{ all -> 0x01a1 }
                goto L_0x00bc
            L_0x0180:
                com.tencent.liteav.renderer.e$j r1 = com.tencent.liteav.renderer.e.a     // Catch:{ all -> 0x01a1 }
                r0 = r17
                boolean r1 = r1.b(r0)     // Catch:{ all -> 0x01a1 }
                if (r1 == 0) goto L_0x013b
                r0 = r17
                com.tencent.liteav.renderer.e$h r1 = r0.s     // Catch:{ RuntimeException -> 0x01b2 }
                r1.a()     // Catch:{ RuntimeException -> 0x01b2 }
                r1 = 1
                r0 = r17
                r0.h = r1     // Catch:{ all -> 0x01a1 }
                r12 = 1
                com.tencent.liteav.renderer.e$j r1 = com.tencent.liteav.renderer.e.a     // Catch:{ all -> 0x01a1 }
                r1.notifyAll()     // Catch:{ all -> 0x01a1 }
                goto L_0x013b
            L_0x01a1:
                r1 = move-exception
                monitor-exit(r15)     // Catch:{ all -> 0x01a1 }
                throw r1     // Catch:{ all -> 0x01a4 }
            L_0x01a4:
                r1 = move-exception
                com.tencent.liteav.renderer.e$j r2 = com.tencent.liteav.renderer.e.a
                monitor-enter(r2)
                r17.j()     // Catch:{ all -> 0x028d }
                r17.k()     // Catch:{ all -> 0x028d }
                monitor-exit(r2)     // Catch:{ all -> 0x028d }
                throw r1
            L_0x01b2:
                r1 = move-exception
                com.tencent.liteav.renderer.e$j r2 = com.tencent.liteav.renderer.e.a     // Catch:{ all -> 0x01a1 }
                r0 = r17
                r2.c(r0)     // Catch:{ all -> 0x01a1 }
                throw r1     // Catch:{ all -> 0x01a1 }
            L_0x01bd:
                com.tencent.liteav.renderer.e$j r1 = com.tencent.liteav.renderer.e.a     // Catch:{ all -> 0x01a1 }
                r1.wait()     // Catch:{ all -> 0x01a1 }
                goto L_0x0029
            L_0x01c6:
                if (r3 == 0) goto L_0x01e6
                r0 = r17
                com.tencent.liteav.renderer.e$h r1 = r0.s     // Catch:{ all -> 0x01a4 }
                boolean r1 = r1.b()     // Catch:{ all -> 0x01a4 }
                if (r1 == 0) goto L_0x026c
                com.tencent.liteav.renderer.e$j r3 = com.tencent.liteav.renderer.e.a     // Catch:{ all -> 0x01a4 }
                monitor-enter(r3)     // Catch:{ all -> 0x01a4 }
                r1 = 1
                r0 = r17
                r0.j = r1     // Catch:{ all -> 0x0269 }
                com.tencent.liteav.renderer.e$j r1 = com.tencent.liteav.renderer.e.a     // Catch:{ all -> 0x0269 }
                r1.notifyAll()     // Catch:{ all -> 0x0269 }
                monitor-exit(r3)     // Catch:{ all -> 0x0269 }
                r1 = 0
                r3 = r1
            L_0x01e6:
                if (r11 == 0) goto L_0x0290
                r0 = r17
                com.tencent.liteav.renderer.e$h r1 = r0.s     // Catch:{ all -> 0x01a4 }
                javax.microedition.khronos.opengles.GL r1 = r1.d()     // Catch:{ all -> 0x01a4 }
                javax.microedition.khronos.opengles.GL10 r1 = (javax.microedition.khronos.opengles.GL10) r1     // Catch:{ all -> 0x01a4 }
                com.tencent.liteav.renderer.e$j r11 = com.tencent.liteav.renderer.e.a     // Catch:{ all -> 0x01a4 }
                r11.a((javax.microedition.khronos.opengles.GL10) r1)     // Catch:{ all -> 0x01a4 }
                r11 = 0
                r13 = r1
            L_0x01fb:
                if (r12 == 0) goto L_0x0217
                r0 = r17
                java.lang.ref.WeakReference<com.tencent.liteav.renderer.e> r1 = r0.t     // Catch:{ all -> 0x01a4 }
                java.lang.Object r1 = r1.get()     // Catch:{ all -> 0x01a4 }
                com.tencent.liteav.renderer.e r1 = (com.tencent.liteav.renderer.e) r1     // Catch:{ all -> 0x01a4 }
                if (r1 == 0) goto L_0x0216
                android.opengl.GLSurfaceView$Renderer r1 = r1.c     // Catch:{ all -> 0x01a4 }
                r0 = r17
                com.tencent.liteav.renderer.e$h r12 = r0.s     // Catch:{ all -> 0x01a4 }
                javax.microedition.khronos.egl.EGLConfig r12 = r12.d     // Catch:{ all -> 0x01a4 }
                r1.onSurfaceCreated(r13, r12)     // Catch:{ all -> 0x01a4 }
            L_0x0216:
                r12 = 0
            L_0x0217:
                if (r9 == 0) goto L_0x022d
                r0 = r17
                java.lang.ref.WeakReference<com.tencent.liteav.renderer.e> r1 = r0.t     // Catch:{ all -> 0x01a4 }
                java.lang.Object r1 = r1.get()     // Catch:{ all -> 0x01a4 }
                com.tencent.liteav.renderer.e r1 = (com.tencent.liteav.renderer.e) r1     // Catch:{ all -> 0x01a4 }
                if (r1 == 0) goto L_0x022c
                android.opengl.GLSurfaceView$Renderer r1 = r1.c     // Catch:{ all -> 0x01a4 }
                r1.onSurfaceChanged(r13, r6, r5)     // Catch:{ all -> 0x01a4 }
            L_0x022c:
                r9 = 0
            L_0x022d:
                r14 = 12288(0x3000, float:1.7219E-41)
                r0 = r17
                java.lang.ref.WeakReference<com.tencent.liteav.renderer.e> r1 = r0.t     // Catch:{ all -> 0x01a4 }
                java.lang.Object r1 = r1.get()     // Catch:{ all -> 0x01a4 }
                com.tencent.liteav.renderer.e r1 = (com.tencent.liteav.renderer.e) r1     // Catch:{ all -> 0x01a4 }
                if (r1 == 0) goto L_0x029b
                android.opengl.GLSurfaceView$Renderer r14 = r1.c     // Catch:{ all -> 0x01a4 }
                r14.onDrawFrame(r13)     // Catch:{ all -> 0x01a4 }
                int r1 = r1.b()     // Catch:{ all -> 0x01a4 }
            L_0x0246:
                switch(r1) {
                    case 12288: goto L_0x0262;
                    case 12302: goto L_0x0288;
                    default: goto L_0x0249;
                }     // Catch:{ all -> 0x01a4 }
            L_0x0249:
                java.lang.String r14 = "GLThread"
                java.lang.String r15 = "eglSwapBuffers"
                com.tencent.liteav.renderer.e.h.a(r14, r15, r1)     // Catch:{ all -> 0x01a4 }
                com.tencent.liteav.renderer.e$j r14 = com.tencent.liteav.renderer.e.a     // Catch:{ all -> 0x01a4 }
                monitor-enter(r14)     // Catch:{ all -> 0x01a4 }
                r1 = 1
                r0 = r17
                r0.f = r1     // Catch:{ all -> 0x028a }
                com.tencent.liteav.renderer.e$j r1 = com.tencent.liteav.renderer.e.a     // Catch:{ all -> 0x028a }
                r1.notifyAll()     // Catch:{ all -> 0x028a }
                monitor-exit(r14)     // Catch:{ all -> 0x028a }
            L_0x0262:
                if (r8 == 0) goto L_0x0299
                r1 = 1
            L_0x0265:
                r2 = r1
                r14 = r13
                goto L_0x0024
            L_0x0269:
                r1 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x0269 }
                throw r1     // Catch:{ all -> 0x01a4 }
            L_0x026c:
                com.tencent.liteav.renderer.e$j r13 = com.tencent.liteav.renderer.e.a     // Catch:{ all -> 0x01a4 }
                monitor-enter(r13)     // Catch:{ all -> 0x01a4 }
                r1 = 1
                r0 = r17
                r0.j = r1     // Catch:{ all -> 0x0285 }
                r1 = 1
                r0 = r17
                r0.f = r1     // Catch:{ all -> 0x0285 }
                com.tencent.liteav.renderer.e$j r1 = com.tencent.liteav.renderer.e.a     // Catch:{ all -> 0x0285 }
                r1.notifyAll()     // Catch:{ all -> 0x0285 }
                monitor-exit(r13)     // Catch:{ all -> 0x0285 }
                goto L_0x0024
            L_0x0285:
                r1 = move-exception
                monitor-exit(r13)     // Catch:{ all -> 0x0285 }
                throw r1     // Catch:{ all -> 0x01a4 }
            L_0x0288:
                r10 = 1
                goto L_0x0262
            L_0x028a:
                r1 = move-exception
                monitor-exit(r14)     // Catch:{ all -> 0x028a }
                throw r1     // Catch:{ all -> 0x01a4 }
            L_0x028d:
                r1 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x028d }
                throw r1
            L_0x0290:
                r13 = r14
                goto L_0x01fb
            L_0x0293:
                r1 = r5
                goto L_0x016b
            L_0x0296:
                r13 = r1
                goto L_0x0081
            L_0x0299:
                r1 = r2
                goto L_0x0265
            L_0x029b:
                r1 = r14
                goto L_0x0246
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.renderer.e.i.i():void");
        }

        private void j() {
            if (this.i) {
                this.i = false;
                this.s.f();
            }
        }

        private void k() {
            if (this.h) {
                this.s.g();
                this.h = false;
                e eVar = (e) this.t.get();
                if (eVar != null) {
                    eVar.h = false;
                }
                e.a.c(this);
            }
        }

        public boolean c() {
            return this.h && this.i && l();
        }

        private boolean l() {
            return !this.d && this.e && !this.f && this.l > 0 && this.m > 0 && (this.o || this.n == 1);
        }

        public void a(int i2) {
            if (i2 < 0 || i2 > 1) {
                throw new IllegalArgumentException("renderMode");
            }
            synchronized (e.a) {
                this.n = i2;
                e.a.notifyAll();
            }
        }

        public int d() {
            int i2;
            synchronized (e.a) {
                i2 = this.n;
            }
            return i2;
        }

        public void e() {
            synchronized (e.a) {
                this.e = true;
                this.j = false;
                e.a.notifyAll();
                while (this.g && !this.j && !this.b) {
                    try {
                        e.a.wait();
                    } catch (InterruptedException e2) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void f() {
            synchronized (e.a) {
                this.e = false;
                e.a.notifyAll();
                while (!this.g && !this.b) {
                    try {
                        e.a.wait();
                    } catch (InterruptedException e2) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void a(int i2, int i3) {
            synchronized (e.a) {
                this.l = i2;
                this.m = i3;
                this.r = true;
                this.o = true;
                this.p = false;
                e.a.notifyAll();
                while (!this.b && !this.d && !this.p && c()) {
                    try {
                        e.a.wait();
                    } catch (InterruptedException e2) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void g() {
            synchronized (e.a) {
                this.a = true;
                e.a.notifyAll();
                while (!this.b) {
                    try {
                        e.a.wait();
                    } catch (InterruptedException e2) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void h() {
            this.k = true;
            e.a.notifyAll();
        }

        public void a(Runnable runnable) {
            if (runnable == null) {
                throw new IllegalArgumentException("r must not be null");
            }
            synchronized (e.a) {
                this.q.add(runnable);
                e.a.notifyAll();
            }
        }
    }

    /* compiled from: TXCGLSurfaceViewBase */
    static class l extends Writer {
        private StringBuilder a = new StringBuilder();

        l() {
        }

        public void close() {
            a();
        }

        public void flush() {
            a();
        }

        public void write(char[] cArr, int i, int i2) {
            for (int i3 = 0; i3 < i2; i3++) {
                char c = cArr[i + i3];
                if (c == 10) {
                    a();
                } else {
                    this.a.append(c);
                }
            }
        }

        private void a() {
            if (this.a.length() > 0) {
                TXCLog.v("TXCGLSurfaceViewBase", this.a.toString());
                this.a.delete(0, this.a.length());
            }
        }
    }

    private void e() {
        if (this.b != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
    }

    /* compiled from: TXCGLSurfaceViewBase */
    private static class j {
        private static String a = "GLThreadManager";
        private boolean b;
        private int c;
        private boolean d;
        private boolean e;
        private boolean f;
        private i g;

        private j() {
        }

        public synchronized void a(i iVar) {
            boolean unused = iVar.b = true;
            if (this.g == iVar) {
                this.g = null;
            }
            notifyAll();
        }

        public boolean b(i iVar) {
            if (this.g == iVar || this.g == null) {
                this.g = iVar;
                notifyAll();
                return true;
            }
            c();
            if (this.e) {
                return true;
            }
            if (this.g != null) {
                this.g.h();
            }
            return false;
        }

        public void c(i iVar) {
            if (this.g == iVar) {
                this.g = null;
            }
            notifyAll();
        }

        public synchronized boolean a() {
            return this.f;
        }

        public synchronized boolean b() {
            c();
            return !this.e;
        }

        public synchronized void a(GL10 gl10) {
            boolean z = true;
            synchronized (this) {
                if (!this.d) {
                    c();
                    String glGetString = gl10.glGetString(7937);
                    if (this.c < 131072) {
                        this.e = !glGetString.startsWith("Q3Dimension MSM7500 ");
                        notifyAll();
                    }
                    if (this.e) {
                        z = false;
                    }
                    this.f = z;
                    this.d = true;
                }
            }
        }

        private void c() {
            this.c = 131072;
            this.e = true;
            this.b = true;
        }
    }

    /* compiled from: TXCGLSurfaceViewBase */
    public static class h {
        EGL10 a;
        EGLDisplay b;
        EGLSurface c;
        EGLConfig d;
        EGLContext e;
        private WeakReference<e> f;

        public h(WeakReference<e> weakReference) {
            this.f = weakReference;
        }

        public void a() {
            this.a = (EGL10) EGLContext.getEGL();
            this.b = this.a.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            if (this.b == EGL10.EGL_NO_DISPLAY) {
                throw new RuntimeException("eglGetDisplay failed");
            }
            if (!this.a.eglInitialize(this.b, new int[2])) {
                throw new RuntimeException("eglInitialize failed");
            }
            e eVar = (e) this.f.get();
            if (eVar == null) {
                this.d = null;
                this.e = null;
            } else {
                this.d = eVar.j.a(this.a, this.b);
                this.e = eVar.k.a(this.a, this.b, this.d);
            }
            if (this.e == null || this.e == EGL10.EGL_NO_CONTEXT) {
                this.e = null;
                a("createContext");
            }
            if (eVar != null) {
                eVar.h = true;
            }
            this.c = null;
        }

        public boolean b() {
            if (this.a == null) {
                throw new RuntimeException("egl not initialized");
            } else if (this.b == null) {
                throw new RuntimeException("eglDisplay not initialized");
            } else if (this.d == null) {
                throw new RuntimeException("mEglConfig not initialized");
            } else {
                h();
                e eVar = (e) this.f.get();
                if (eVar != null) {
                    this.c = eVar.l.a(this.a, this.b, this.d, eVar.getHolder());
                } else {
                    this.c = null;
                }
                if (this.c == null || this.c == EGL10.EGL_NO_SURFACE) {
                    if (this.a.eglGetError() == 12299) {
                        TXCLog.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
                    }
                    return false;
                } else if (!this.a.eglMakeCurrent(this.b, this.c, this.c, this.e)) {
                    a("EGLHelper", "eglMakeCurrent", this.a.eglGetError());
                    return false;
                } else {
                    if (eVar != null) {
                        eVar.g = true;
                    }
                    return true;
                }
            }
        }

        public int c() {
            return e();
        }

        /* access modifiers changed from: package-private */
        public GL d() {
            l lVar;
            GL gl = this.e.getGL();
            e eVar = (e) this.f.get();
            if (eVar == null) {
                return gl;
            }
            if (eVar.m != null) {
                gl = eVar.m.a(gl);
            }
            if ((eVar.n & 3) == 0) {
                return gl;
            }
            int i = 0;
            if ((eVar.n & 1) != 0) {
                i = 1;
            }
            if ((eVar.n & 2) != 0) {
                lVar = new l();
            } else {
                lVar = null;
            }
            return GLDebugHelper.wrap(gl, i, lVar);
        }

        public int e() {
            if (!this.a.eglSwapBuffers(this.b, this.c)) {
                return this.a.eglGetError();
            }
            return 12288;
        }

        public void f() {
            h();
        }

        private void h() {
            if (this.c != null && this.c != EGL10.EGL_NO_SURFACE) {
                this.a.eglMakeCurrent(this.b, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                e eVar = (e) this.f.get();
                if (eVar != null) {
                    eVar.l.a(this.a, this.b, this.c);
                    eVar.g = false;
                }
                this.c = null;
            }
        }

        public void g() {
            if (this.e != null) {
                e eVar = (e) this.f.get();
                if (eVar != null) {
                    eVar.k.a(this.a, this.b, this.e);
                }
                this.e = null;
            }
            if (this.b != null) {
                this.a.eglTerminate(this.b);
                this.b = null;
            }
        }

        private void a(String str) {
            a(str, this.a.eglGetError());
        }

        public static void a(String str, int i) {
            throw new RuntimeException(b(str, i));
        }

        public static void a(String str, String str2, int i) {
            TXCLog.w(str, b(str2, i));
        }

        public static String b(String str, int i) {
            return str + " failed: " + i;
        }
    }

    /* compiled from: TXCGLSurfaceViewBase */
    private class b extends a {
        protected int c;
        protected int d;
        protected int e;
        protected int f;
        protected int g;
        protected int h;
        private int[] j = new int[1];

        public b(int i2, int i3, int i4, int i5, int i6, int i7) {
            super(new int[]{12324, i2, 12323, i3, 12322, i4, 12321, i5, 12325, i6, 12326, i7, 12344});
            this.c = i2;
            this.d = i3;
            this.e = i4;
            this.f = i5;
            this.g = i6;
            this.h = i7;
        }

        public EGLConfig a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr) {
            for (EGLConfig eGLConfig : eGLConfigArr) {
                int a = a(egl10, eGLDisplay, eGLConfig, 12325, 0);
                int a2 = a(egl10, eGLDisplay, eGLConfig, 12326, 0);
                if (a >= this.g && a2 >= this.h) {
                    int a3 = a(egl10, eGLDisplay, eGLConfig, 12324, 0);
                    int a4 = a(egl10, eGLDisplay, eGLConfig, 12323, 0);
                    int a5 = a(egl10, eGLDisplay, eGLConfig, 12322, 0);
                    int a6 = a(egl10, eGLDisplay, eGLConfig, 12321, 0);
                    if (a3 == this.c && a4 == this.d && a5 == this.e && a6 == this.f) {
                        return eGLConfig;
                    }
                }
            }
            return null;
        }

        private int a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i2, int i3) {
            if (egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i2, this.j)) {
                return this.j[0];
            }
            return i3;
        }
    }

    /* compiled from: TXCGLSurfaceViewBase */
    private class m extends b {
        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public m(boolean r10) {
            /*
                r8 = this;
                r2 = 8
                r5 = 0
                com.tencent.liteav.renderer.e.this = r9
                if (r10 == 0) goto L_0x0012
                r6 = 16
            L_0x0009:
                r0 = r8
                r1 = r9
                r3 = r2
                r4 = r2
                r7 = r5
                r0.<init>(r2, r3, r4, r5, r6, r7)
                return
            L_0x0012:
                r6 = r5
                goto L_0x0009
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.renderer.e.m.<init>(com.tencent.liteav.renderer.e, boolean):void");
        }
    }

    /* compiled from: TXCGLSurfaceViewBase */
    private class c implements f {
        private int b;

        private c() {
            this.b = 12440;
        }

        public EGLContext a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
            int[] iArr = {this.b, e.this.o, 12344};
            EGLContext eGLContext = EGL10.EGL_NO_CONTEXT;
            if (e.this.o == 0) {
                iArr = null;
            }
            return egl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr);
        }

        public void a(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext) {
            if (!egl10.eglDestroyContext(eGLDisplay, eGLContext)) {
                TXCLog.e("DefaultContextFactory", "display:" + eGLDisplay + " context: " + eGLContext);
                h.a("eglDestroyContex", egl10.eglGetError());
            }
        }
    }

    /* compiled from: TXCGLSurfaceViewBase */
    private static class d implements g {
        private d() {
        }

        public EGLSurface a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj) {
            try {
                return egl10.eglCreateWindowSurface(eGLDisplay, eGLConfig, obj, (int[]) null);
            } catch (IllegalArgumentException e) {
                TXCLog.e("TXCGLSurfaceViewBase", "eglCreateWindowSurface");
                TXCLog.e("TXCGLSurfaceViewBase", e.toString());
                return null;
            }
        }

        public void a(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface) {
            egl10.eglDestroySurface(eGLDisplay, eGLSurface);
        }
    }

    /* compiled from: TXCGLSurfaceViewBase */
    private abstract class a implements C0025e {
        protected int[] a;

        /* access modifiers changed from: package-private */
        public abstract EGLConfig a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr);

        public a(int[] iArr) {
            this.a = a(iArr);
        }

        public EGLConfig a(EGL10 egl10, EGLDisplay eGLDisplay) {
            int[] iArr = new int[1];
            if (!egl10.eglChooseConfig(eGLDisplay, this.a, (EGLConfig[]) null, 0, iArr)) {
                throw new IllegalArgumentException("eglChooseConfig failed");
            }
            int i = iArr[0];
            if (i <= 0) {
                throw new IllegalArgumentException("No configs match configSpec");
            }
            EGLConfig[] eGLConfigArr = new EGLConfig[i];
            if (!egl10.eglChooseConfig(eGLDisplay, this.a, eGLConfigArr, i, iArr)) {
                throw new IllegalArgumentException("eglChooseConfig#2 failed");
            }
            EGLConfig a2 = a(egl10, eGLDisplay, eGLConfigArr);
            if (a2 != null) {
                return a2;
            }
            throw new IllegalArgumentException("No config chosen");
        }

        private int[] a(int[] iArr) {
            if (e.this.o != 2) {
                return iArr;
            }
            int length = iArr.length;
            int[] iArr2 = new int[(length + 2)];
            System.arraycopy(iArr, 0, iArr2, 0, length - 1);
            iArr2[length - 1] = 12352;
            iArr2[length] = 4;
            iArr2[length + 1] = 12344;
            return iArr2;
        }
    }

    public int c() {
        return this.b.a();
    }

    public h getEGLHelper() {
        return this.b.b();
    }
}
