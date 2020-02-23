package com.google.android.gms.ads.internal.overlay;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzme;
import com.google.android.gms.internal.zzmo;
import com.google.android.gms.internal.zzzn;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.concurrent.CountDownLatch;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

@zzzn
@TargetApi(14)
public final class zzap extends Thread implements SurfaceTexture.OnFrameAvailableListener, zzao {
    private static final float[] zzPU = {-1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f};
    private final float[] zzPR;
    private final zzam zzPV;
    private final float[] zzPW;
    private final float[] zzPX;
    private final float[] zzPY;
    private final float[] zzPZ;
    private final float[] zzQa;
    private final float[] zzQb;
    private float zzQc;
    private float zzQd;
    private float zzQe;
    private SurfaceTexture zzQf;
    private SurfaceTexture zzQg;
    private int zzQh;
    private int zzQi;
    private int zzQj;
    private FloatBuffer zzQk = ByteBuffer.allocateDirect(zzPU.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
    private final CountDownLatch zzQl;
    private final Object zzQm;
    private EGL10 zzQn;
    private EGLDisplay zzQo;
    private EGLContext zzQp;
    private EGLSurface zzQq;
    private volatile boolean zzQr;
    private volatile boolean zzQs;
    private int zzrW;
    private int zzrX;

    public zzap(Context context) {
        super("SphericalVideoProcessor");
        this.zzQk.put(zzPU).position(0);
        this.zzPR = new float[9];
        this.zzPW = new float[9];
        this.zzPX = new float[9];
        this.zzPY = new float[9];
        this.zzPZ = new float[9];
        this.zzQa = new float[9];
        this.zzQb = new float[9];
        this.zzQc = Float.NaN;
        this.zzPV = new zzam(context);
        this.zzPV.zza((zzao) this);
        this.zzQl = new CountDownLatch(1);
        this.zzQm = new Object();
    }

    private static void zza(float[] fArr, float f) {
        fArr[0] = 1.0f;
        fArr[1] = 0.0f;
        fArr[2] = 0.0f;
        fArr[3] = 0.0f;
        fArr[4] = (float) Math.cos((double) f);
        fArr[5] = (float) (-Math.sin((double) f));
        fArr[6] = 0.0f;
        fArr[7] = (float) Math.sin((double) f);
        fArr[8] = (float) Math.cos((double) f);
    }

    private static void zza(float[] fArr, float[] fArr2, float[] fArr3) {
        fArr[0] = (fArr2[0] * fArr3[0]) + (fArr2[1] * fArr3[3]) + (fArr2[2] * fArr3[6]);
        fArr[1] = (fArr2[0] * fArr3[1]) + (fArr2[1] * fArr3[4]) + (fArr2[2] * fArr3[7]);
        fArr[2] = (fArr2[0] * fArr3[2]) + (fArr2[1] * fArr3[5]) + (fArr2[2] * fArr3[8]);
        fArr[3] = (fArr2[3] * fArr3[0]) + (fArr2[4] * fArr3[3]) + (fArr2[5] * fArr3[6]);
        fArr[4] = (fArr2[3] * fArr3[1]) + (fArr2[4] * fArr3[4]) + (fArr2[5] * fArr3[7]);
        fArr[5] = (fArr2[3] * fArr3[2]) + (fArr2[4] * fArr3[5]) + (fArr2[5] * fArr3[8]);
        fArr[6] = (fArr2[6] * fArr3[0]) + (fArr2[7] * fArr3[3]) + (fArr2[8] * fArr3[6]);
        fArr[7] = (fArr2[6] * fArr3[1]) + (fArr2[7] * fArr3[4]) + (fArr2[8] * fArr3[7]);
        fArr[8] = (fArr2[6] * fArr3[2]) + (fArr2[7] * fArr3[5]) + (fArr2[8] * fArr3[8]);
    }

    private static void zzar(String str) {
        int glGetError = GLES20.glGetError();
        if (glGetError != 0) {
            Log.e("SphericalVideoRenderer", new StringBuilder(String.valueOf(str).length() + 21).append(str).append(": glError ").append(glGetError).toString());
        }
    }

    private static void zzb(float[] fArr, float f) {
        fArr[0] = (float) Math.cos((double) f);
        fArr[1] = (float) (-Math.sin((double) f));
        fArr[2] = 0.0f;
        fArr[3] = (float) Math.sin((double) f);
        fArr[4] = (float) Math.cos((double) f);
        fArr[5] = 0.0f;
        fArr[6] = 0.0f;
        fArr[7] = 0.0f;
        fArr[8] = 1.0f;
    }

    private static int zzc(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        zzar("createShader");
        if (glCreateShader != 0) {
            GLES20.glShaderSource(glCreateShader, str);
            zzar("shaderSource");
            GLES20.glCompileShader(glCreateShader);
            zzar("compileShader");
            int[] iArr = new int[1];
            GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
            zzar("getShaderiv");
            if (iArr[0] == 0) {
                Log.e("SphericalVideoRenderer", new StringBuilder(37).append("Could not compile shader ").append(i).append(":").toString());
                Log.e("SphericalVideoRenderer", GLES20.glGetShaderInfoLog(glCreateShader));
                GLES20.glDeleteShader(glCreateShader);
                zzar("deleteShader");
                return 0;
            }
        }
        return glCreateShader;
    }

    private final void zzgh() {
        while (this.zzQj > 0) {
            this.zzQf.updateTexImage();
            this.zzQj--;
        }
        if (this.zzPV.zza(this.zzPR)) {
            if (Float.isNaN(this.zzQc)) {
                float[] fArr = this.zzPR;
                float[] fArr2 = {0.0f, 1.0f, 0.0f};
                float[] fArr3 = {(fArr[0] * fArr2[0]) + (fArr[1] * fArr2[1]) + (fArr[2] * fArr2[2]), (fArr[3] * fArr2[0]) + (fArr[4] * fArr2[1]) + (fArr[5] * fArr2[2]), (fArr[8] * fArr2[2]) + (fArr[6] * fArr2[0]) + (fArr[7] * fArr2[1])};
                this.zzQc = -(((float) Math.atan2((double) fArr3[1], (double) fArr3[0])) - 1.5707964f);
            }
            zzb(this.zzQa, this.zzQc + this.zzQd);
        } else {
            zza(this.zzPR, -1.5707964f);
            zzb(this.zzQa, this.zzQd);
        }
        zza(this.zzPW, 1.5707964f);
        zza(this.zzPX, this.zzQa, this.zzPW);
        zza(this.zzPY, this.zzPR, this.zzPX);
        zza(this.zzPZ, this.zzQe);
        zza(this.zzQb, this.zzPZ, this.zzPY);
        GLES20.glUniformMatrix3fv(this.zzQi, 1, false, this.zzQb, 0);
        GLES20.glDrawArrays(5, 0, 4);
        zzar("drawArrays");
        GLES20.glFinish();
        this.zzQn.eglSwapBuffers(this.zzQo, this.zzQq);
    }

    private final boolean zzgi() {
        boolean z = false;
        if (!(this.zzQq == null || this.zzQq == EGL10.EGL_NO_SURFACE)) {
            z = this.zzQn.eglMakeCurrent(this.zzQo, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT) | false | this.zzQn.eglDestroySurface(this.zzQo, this.zzQq);
            this.zzQq = null;
        }
        if (this.zzQp != null) {
            z |= this.zzQn.eglDestroyContext(this.zzQo, this.zzQp);
            this.zzQp = null;
        }
        if (this.zzQo == null) {
            return z;
        }
        boolean eglTerminate = z | this.zzQn.eglTerminate(this.zzQo);
        this.zzQo = null;
        return eglTerminate;
    }

    public final void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.zzQj++;
        synchronized (this.zzQm) {
            this.zzQm.notifyAll();
        }
    }

    public final void run() {
        boolean z;
        int glCreateProgram;
        boolean z2 = true;
        if (this.zzQg == null) {
            zzafr.e("SphericalVideoProcessor started with no output texture.");
            this.zzQl.countDown();
            return;
        }
        this.zzQn = (EGL10) EGLContext.getEGL();
        this.zzQo = this.zzQn.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        if (this.zzQo == EGL10.EGL_NO_DISPLAY) {
            z = false;
        } else {
            if (!this.zzQn.eglInitialize(this.zzQo, new int[2])) {
                z = false;
            } else {
                int[] iArr = new int[1];
                EGLConfig[] eGLConfigArr = new EGLConfig[1];
                EGLConfig eGLConfig = (!this.zzQn.eglChooseConfig(this.zzQo, new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12325, 16, 12344}, eGLConfigArr, 1, iArr) || iArr[0] <= 0) ? null : eGLConfigArr[0];
                if (eGLConfig == null) {
                    z = false;
                } else {
                    this.zzQp = this.zzQn.eglCreateContext(this.zzQo, eGLConfig, EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
                    if (this.zzQp == null || this.zzQp == EGL10.EGL_NO_CONTEXT) {
                        z = false;
                    } else {
                        this.zzQq = this.zzQn.eglCreateWindowSurface(this.zzQo, eGLConfig, this.zzQg, (int[]) null);
                        z = (this.zzQq == null || this.zzQq == EGL10.EGL_NO_SURFACE) ? false : this.zzQn.eglMakeCurrent(this.zzQo, this.zzQq, this.zzQq, this.zzQp);
                    }
                }
            }
        }
        zzme zzme = zzmo.zzEn;
        int zzc = zzc(35633, !((String) zzbs.zzbL().zzd(zzme)).equals(zzme.zzdI()) ? (String) zzbs.zzbL().zzd(zzme) : "attribute highp vec3 aPosition;varying vec3 pos;void main() {  gl_Position = vec4(aPosition, 1.0);  pos = aPosition;}");
        if (zzc == 0) {
            glCreateProgram = 0;
        } else {
            zzme zzme2 = zzmo.zzEo;
            int zzc2 = zzc(35632, !((String) zzbs.zzbL().zzd(zzme2)).equals(zzme2.zzdI()) ? (String) zzbs.zzbL().zzd(zzme2) : "#extension GL_OES_EGL_image_external : require\n#define INV_PI 0.3183\nprecision highp float;varying vec3 pos;uniform samplerExternalOES uSplr;uniform mat3 uVMat;uniform float uFOVx;uniform float uFOVy;void main() {  vec3 ray = vec3(pos.x * tan(uFOVx), pos.y * tan(uFOVy), -1);  ray = (uVMat * ray).xyz;  ray = normalize(ray);  vec2 texCrd = vec2(    0.5 + atan(ray.x, - ray.z) * INV_PI * 0.5, acos(ray.y) * INV_PI);  gl_FragColor = vec4(texture2D(uSplr, texCrd).xyz, 1.0);}");
            if (zzc2 == 0) {
                glCreateProgram = 0;
            } else {
                glCreateProgram = GLES20.glCreateProgram();
                zzar("createProgram");
                if (glCreateProgram != 0) {
                    GLES20.glAttachShader(glCreateProgram, zzc);
                    zzar("attachShader");
                    GLES20.glAttachShader(glCreateProgram, zzc2);
                    zzar("attachShader");
                    GLES20.glLinkProgram(glCreateProgram);
                    zzar("linkProgram");
                    int[] iArr2 = new int[1];
                    GLES20.glGetProgramiv(glCreateProgram, 35714, iArr2, 0);
                    zzar("getProgramiv");
                    if (iArr2[0] != 1) {
                        Log.e("SphericalVideoRenderer", "Could not link program: ");
                        Log.e("SphericalVideoRenderer", GLES20.glGetProgramInfoLog(glCreateProgram));
                        GLES20.glDeleteProgram(glCreateProgram);
                        zzar("deleteProgram");
                        glCreateProgram = 0;
                    } else {
                        GLES20.glValidateProgram(glCreateProgram);
                        zzar("validateProgram");
                    }
                }
            }
        }
        this.zzQh = glCreateProgram;
        GLES20.glUseProgram(this.zzQh);
        zzar("useProgram");
        int glGetAttribLocation = GLES20.glGetAttribLocation(this.zzQh, "aPosition");
        GLES20.glVertexAttribPointer(glGetAttribLocation, 3, 5126, false, 12, this.zzQk);
        zzar("vertexAttribPointer");
        GLES20.glEnableVertexAttribArray(glGetAttribLocation);
        zzar("enableVertexAttribArray");
        int[] iArr3 = new int[1];
        GLES20.glGenTextures(1, iArr3, 0);
        zzar("genTextures");
        int i = iArr3[0];
        GLES20.glBindTexture(36197, i);
        zzar("bindTextures");
        GLES20.glTexParameteri(36197, Task.EXTRAS_LIMIT_BYTES, 9729);
        zzar("texParameteri");
        GLES20.glTexParameteri(36197, 10241, 9729);
        zzar("texParameteri");
        GLES20.glTexParameteri(36197, 10242, 33071);
        zzar("texParameteri");
        GLES20.glTexParameteri(36197, 10243, 33071);
        zzar("texParameteri");
        this.zzQi = GLES20.glGetUniformLocation(this.zzQh, "uVMat");
        GLES20.glUniformMatrix3fv(this.zzQi, 1, false, new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f}, 0);
        if (this.zzQh == 0) {
            z2 = false;
        }
        if (!z || !z2) {
            String valueOf = String.valueOf(GLUtils.getEGLErrorString(this.zzQn.eglGetError()));
            String concat = valueOf.length() != 0 ? "EGL initialization failed: ".concat(valueOf) : new String("EGL initialization failed: ");
            zzafr.e(concat);
            zzbs.zzbD().zza(new Throwable(concat), "SphericalVideoProcessor.run.1");
            zzgi();
            this.zzQl.countDown();
            return;
        }
        this.zzQf = new SurfaceTexture(i);
        this.zzQf.setOnFrameAvailableListener(this);
        this.zzQl.countDown();
        this.zzPV.start();
        try {
            this.zzQr = true;
            while (!this.zzQs) {
                zzgh();
                if (this.zzQr) {
                    GLES20.glViewport(0, 0, this.zzrW, this.zzrX);
                    zzar("viewport");
                    int glGetUniformLocation = GLES20.glGetUniformLocation(this.zzQh, "uFOVx");
                    int glGetUniformLocation2 = GLES20.glGetUniformLocation(this.zzQh, "uFOVy");
                    if (this.zzrW > this.zzrX) {
                        GLES20.glUniform1f(glGetUniformLocation, 0.87266463f);
                        GLES20.glUniform1f(glGetUniformLocation2, (((float) this.zzrX) * 0.87266463f) / ((float) this.zzrW));
                    } else {
                        GLES20.glUniform1f(glGetUniformLocation, (((float) this.zzrW) * 0.87266463f) / ((float) this.zzrX));
                        GLES20.glUniform1f(glGetUniformLocation2, 0.87266463f);
                    }
                    this.zzQr = false;
                }
                try {
                    synchronized (this.zzQm) {
                        if (!this.zzQs && !this.zzQr && this.zzQj == 0) {
                            this.zzQm.wait();
                        }
                    }
                } catch (InterruptedException e) {
                }
            }
        } catch (IllegalStateException e2) {
            zzafr.zzaT("SphericalVideoProcessor halted unexpectedly.");
        } catch (Throwable th) {
            zzafr.zzb("SphericalVideoProcessor died.", th);
            zzbs.zzbD().zza(th, "SphericalVideoProcessor.run.2");
        } finally {
            this.zzPV.stop();
            this.zzQf.setOnFrameAvailableListener((SurfaceTexture.OnFrameAvailableListener) null);
            this.zzQf = null;
            zzgi();
        }
    }

    public final void zza(SurfaceTexture surfaceTexture, int i, int i2) {
        this.zzrW = i;
        this.zzrX = i2;
        this.zzQg = surfaceTexture;
    }

    public final void zzb(float f, float f2) {
        float f3;
        float f4;
        if (this.zzrW > this.zzrX) {
            f3 = (1.7453293f * f) / ((float) this.zzrW);
            f4 = (1.7453293f * f2) / ((float) this.zzrW);
        } else {
            f3 = (1.7453293f * f) / ((float) this.zzrX);
            f4 = (1.7453293f * f2) / ((float) this.zzrX);
        }
        this.zzQd -= f3;
        this.zzQe -= f4;
        if (this.zzQe < -1.5707964f) {
            this.zzQe = -1.5707964f;
        }
        if (this.zzQe > 1.5707964f) {
            this.zzQe = 1.5707964f;
        }
    }

    public final void zzf(int i, int i2) {
        synchronized (this.zzQm) {
            this.zzrW = i;
            this.zzrX = i2;
            this.zzQr = true;
            this.zzQm.notifyAll();
        }
    }

    public final void zzfO() {
        synchronized (this.zzQm) {
            this.zzQm.notifyAll();
        }
    }

    public final void zzgf() {
        synchronized (this.zzQm) {
            this.zzQs = true;
            this.zzQg = null;
            this.zzQm.notifyAll();
        }
    }

    public final SurfaceTexture zzgg() {
        if (this.zzQg == null) {
            return null;
        }
        try {
            this.zzQl.await();
        } catch (InterruptedException e) {
        }
        return this.zzQf;
    }
}
