package com.tencent.liteav.basic.d;

import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/* compiled from: EGL10Helper */
public class a {
    public static final String a = a.class.getSimpleName();
    private static int[] l = {12339, 1, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12344};
    private static int[] m = {12339, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12326, 0, 12352, 4, 12610, 1, 12344};
    private EGL10 b;
    private EGLDisplay c;
    private EGLConfig d;
    private boolean e;
    private EGLContext f;
    private boolean g;
    private EGLSurface h;
    private int i = 0;
    private int j = 0;
    private int[] k = new int[2];

    private a() {
    }

    public static a a(EGLConfig eGLConfig, EGLContext eGLContext, Surface surface, int i2, int i3) {
        a aVar = new a();
        aVar.i = i2;
        aVar.j = i3;
        if (aVar.a(eGLConfig, eGLContext, surface)) {
            return aVar;
        }
        return null;
    }

    public boolean a() {
        boolean eglSwapBuffers = this.b.eglSwapBuffers(this.c, this.h);
        d();
        return eglSwapBuffers;
    }

    public void b() {
        this.b.eglMakeCurrent(this.c, this.h, this.h, this.f);
        d();
    }

    public void c() {
        this.b.eglMakeCurrent(this.c, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
        if (this.h != null) {
            this.b.eglDestroySurface(this.c, this.h);
        }
        if (this.f != null) {
            this.b.eglDestroyContext(this.c, this.f);
        }
        this.b.eglTerminate(this.c);
        d();
        this.c = null;
        this.h = null;
        this.c = null;
    }

    private boolean a(EGLConfig eGLConfig, EGLContext eGLContext, Surface surface) {
        this.b = (EGL10) EGLContext.getEGL();
        this.c = this.b.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        this.b.eglInitialize(this.c, this.k);
        if (eGLConfig == null) {
            EGLConfig[] eGLConfigArr = new EGLConfig[1];
            this.b.eglChooseConfig(this.c, surface == null ? l : m, eGLConfigArr, 1, new int[1]);
            this.d = eGLConfigArr[0];
            this.e = true;
        } else {
            this.d = eGLConfig;
        }
        int[] iArr = {12440, 2, 12344};
        if (eGLContext == null) {
            this.f = this.b.eglCreateContext(this.c, this.d, EGL10.EGL_NO_CONTEXT, iArr);
        } else {
            this.f = this.b.eglCreateContext(this.c, this.d, eGLContext, iArr);
            this.g = true;
        }
        if (this.f == EGL10.EGL_NO_CONTEXT) {
            d();
            return false;
        }
        int[] iArr2 = {12375, this.i, 12374, this.j, 12344};
        if (surface == null) {
            this.h = this.b.eglCreatePbufferSurface(this.c, this.d, iArr2);
        } else {
            this.h = this.b.eglCreateWindowSurface(this.c, this.d, surface, (int[]) null);
        }
        if (this.h == EGL10.EGL_NO_SURFACE) {
            d();
            return false;
        } else if (this.b.eglMakeCurrent(this.c, this.h, this.h, this.f)) {
            return true;
        } else {
            d();
            return false;
        }
    }

    public void d() {
        int eglGetError = this.b.eglGetError();
        if (eglGetError != 12288) {
            TXCLog.e(a, "EGL error: 0x" + Integer.toHexString(eglGetError));
        }
    }
}
