package com.tencent.liteav.renderer;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.microedition.khronos.egl.EGLContext;

/* compiled from: TXCGLRender */
public class b extends h implements SurfaceTexture.OnFrameAvailableListener {
    j a;
    a b;
    private final int k = 0;
    private final int l = 0;
    private final int m = 0;
    private final int n = 0;
    private c o;
    private SurfaceTexture p;
    private f q;
    private boolean r;
    private float[] s = new float[16];
    private f t;
    private ArrayList<Long> u = new ArrayList<>();
    private TXCYuvTextureRender v;
    private final Queue<Runnable> w = new LinkedList();

    /* compiled from: TXCGLRender */
    public interface a {
        void d(int i);
    }

    public void a(j jVar) {
        this.a = jVar;
    }

    public void a(a aVar) {
        this.b = aVar;
        if (aVar != null && this.v != null) {
            this.v.setHasFrameBuffer(this.g, this.h);
        }
    }

    public void a(long j, int i, int i2) {
        synchronized (this) {
            this.u.add(Long.valueOf(j));
        }
        super.a(j, i, i2);
    }

    public void a(int i, int i2, int i3, boolean z, int i4) {
        GLES20.glViewport(0, 0, this.e, this.f);
        if (this.t != null) {
            this.t.a(i, z, i4);
        }
        super.a(i, i2, i3, z, i4);
    }

    public SurfaceTexture a() {
        return this.p;
    }

    public EGLContext b() {
        if (this.o != null) {
            return this.o.a();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void a(SurfaceTexture surfaceTexture) {
        n();
    }

    /* access modifiers changed from: protected */
    public void b(SurfaceTexture surfaceTexture) {
        o();
    }

    /* access modifiers changed from: protected */
    public void a(int i, int i2) {
        super.a(i, i2);
        if (this.v != null) {
            this.v.setVideoSize(i, i2);
        }
        if (this.q != null) {
            this.q.a(i, i2);
        }
    }

    /* access modifiers changed from: package-private */
    public void c() {
        m();
        if (this.d != null) {
            this.d.a(this.e, this.f);
            this.d.b(this.g, this.h);
        }
        if (this.q != null) {
            this.q.b();
            this.p = new SurfaceTexture(this.q.a());
            this.p.setOnFrameAvailableListener(this);
        }
        if (this.v != null) {
            this.v.createTexture();
        }
        if (!(this.b == null || this.v == null)) {
            this.v.setHasFrameBuffer(this.g, this.h);
        }
        if (this.t != null) {
            this.t.b();
        }
        if (this.i != null) {
            this.i.a(this.p);
        }
    }

    private void m() {
        this.q = new f(true);
        this.v = new TXCYuvTextureRender();
        this.t = new f(false);
    }

    /* access modifiers changed from: package-private */
    public void d() {
        try {
            if (this.i != null) {
                this.i.b(this.p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.q = null;
        this.v = null;
        this.t = null;
    }

    /* access modifiers changed from: package-private */
    public boolean e() {
        do {
        } while (a(this.w));
        return p();
    }

    /* access modifiers changed from: package-private */
    public SurfaceTexture f() {
        if (this.c != null) {
            return this.c.getSurfaceTexture();
        }
        return null;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    private void n() {
        this.o = new c(new WeakReference(this));
        this.o.start();
        TXCLog.w("TXCVideoRender", "play:vrender: start render thread");
    }

    private void o() {
        if (this.o != null) {
            this.o.b();
            this.o = null;
            TXCLog.w("TXCVideoRender", "play:vrender: quit render thread");
        }
        this.p = null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001a, code lost:
        if (r8.p == null) goto L_0x0028;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001c, code lost:
        r8.p.updateTexImage();
        r8.p.getTransformMatrix(r8.s);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002a, code lost:
        if (r8.a == null) goto L_0x005e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002c, code lost:
        r8.a.a(r8.q.a(), r8.s);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0039, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0060, code lost:
        if (r8.q == null) goto L_0x0039;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0062, code lost:
        r8.q.a(r8.p);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x006c, code lost:
        if (r2 == 0) goto L_0x0039;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0070, code lost:
        if (r8.v == null) goto L_0x0039;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0074, code lost:
        if (r8.b == null) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0076, code lost:
        r8.b.d(r8.v.drawToTexture(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0082, code lost:
        r8.v.drawFrame(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x000f, code lost:
        android.opengl.GLES20.glViewport(0, 0, r8.e, r8.f);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0016, code lost:
        if (r0 == false) goto L_0x006a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean p() {
        /*
            r8 = this;
            r4 = 0
            r1 = 0
            monitor-enter(r8)
            boolean r0 = r8.r     // Catch:{ all -> 0x005b }
            if (r0 == 0) goto L_0x003b
            boolean r0 = r8.r     // Catch:{ all -> 0x005b }
            r2 = 0
            r8.r = r2     // Catch:{ all -> 0x005b }
            r2 = r4
        L_0x000e:
            monitor-exit(r8)     // Catch:{ all -> 0x005b }
            int r6 = r8.e
            int r7 = r8.f
            android.opengl.GLES20.glViewport(r1, r1, r6, r7)
            if (r0 == 0) goto L_0x006a
            android.graphics.SurfaceTexture r0 = r8.p
            if (r0 == 0) goto L_0x0028
            android.graphics.SurfaceTexture r0 = r8.p
            r0.updateTexImage()
            android.graphics.SurfaceTexture r0 = r8.p
            float[] r1 = r8.s
            r0.getTransformMatrix(r1)
        L_0x0028:
            com.tencent.liteav.renderer.j r0 = r8.a
            if (r0 == 0) goto L_0x005e
            com.tencent.liteav.renderer.j r0 = r8.a
            com.tencent.liteav.renderer.f r1 = r8.q
            int r1 = r1.a()
            float[] r2 = r8.s
            r0.a(r1, r2)
        L_0x0039:
            r0 = 1
        L_0x003a:
            return r0
        L_0x003b:
            java.util.ArrayList<java.lang.Long> r0 = r8.u     // Catch:{ all -> 0x005b }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x005b }
            if (r0 != 0) goto L_0x0058
            java.util.ArrayList<java.lang.Long> r0 = r8.u     // Catch:{ all -> 0x005b }
            r2 = 0
            java.lang.Object r0 = r0.get(r2)     // Catch:{ all -> 0x005b }
            java.lang.Long r0 = (java.lang.Long) r0     // Catch:{ all -> 0x005b }
            long r2 = r0.longValue()     // Catch:{ all -> 0x005b }
            java.util.ArrayList<java.lang.Long> r0 = r8.u     // Catch:{ all -> 0x005b }
            r6 = 0
            r0.remove(r6)     // Catch:{ all -> 0x005b }
            r0 = r1
            goto L_0x000e
        L_0x0058:
            monitor-exit(r8)     // Catch:{ all -> 0x005b }
            r0 = r1
            goto L_0x003a
        L_0x005b:
            r0 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x005b }
            throw r0
        L_0x005e:
            com.tencent.liteav.renderer.f r0 = r8.q
            if (r0 == 0) goto L_0x0039
            com.tencent.liteav.renderer.f r0 = r8.q
            android.graphics.SurfaceTexture r1 = r8.p
            r0.a((android.graphics.SurfaceTexture) r1)
            goto L_0x0039
        L_0x006a:
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 == 0) goto L_0x0039
            com.tencent.liteav.renderer.TXCYuvTextureRender r0 = r8.v
            if (r0 == 0) goto L_0x0039
            com.tencent.liteav.renderer.b$a r0 = r8.b
            if (r0 == 0) goto L_0x0082
            com.tencent.liteav.renderer.TXCYuvTextureRender r0 = r8.v
            int r0 = r0.drawToTexture(r2)
            com.tencent.liteav.renderer.b$a r1 = r8.b
            r1.d(r0)
            goto L_0x0039
        L_0x0082:
            com.tencent.liteav.renderer.TXCYuvTextureRender r0 = r8.v
            r0.drawFrame(r2)
            goto L_0x0039
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.renderer.b.p():boolean");
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
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.renderer.b.a(java.util.Queue):boolean");
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        synchronized (this) {
            this.r = true;
        }
    }

    public void finalize() throws Throwable {
        super.finalize();
        TXCLog.w("TXCVideoRender", "play:vrender: quit render thread when finalize");
        try {
            o();
        } catch (Exception e) {
        }
    }
}
