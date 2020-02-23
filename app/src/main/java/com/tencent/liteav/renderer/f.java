package com.tencent.liteav.renderer;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.Matrix;
import com.google.android.gms.gcm.Task;
import com.tencent.liteav.basic.log.TXCLog;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* compiled from: TXCOesTextureRender */
public class f {
    private final float[] a = {-1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};
    private final float[] b = {1.0f, -1.0f, 0.0f, 1.0f, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f};
    private FloatBuffer c;
    private float[] d = new float[16];
    private float[] e = new float[16];
    private int f;
    private int g = -12345;
    private int h;
    private int i;
    private int j;
    private int k;
    private boolean l = false;
    private boolean m = true;
    private boolean n = false;
    private int o = -1;
    private int p = 0;
    private int q = 0;

    public f(boolean z) {
        this.m = z;
        if (this.m) {
            this.c = ByteBuffer.allocateDirect(this.a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            this.c.put(this.a).position(0);
        } else {
            this.c = ByteBuffer.allocateDirect(this.b.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            this.c.put(this.b).position(0);
        }
        Matrix.setIdentityM(this.e, 0);
    }

    public int a() {
        return this.g;
    }

    public void a(SurfaceTexture surfaceTexture) {
        if (surfaceTexture != null) {
            a("onDrawFrame start");
            surfaceTexture.getTransformMatrix(this.e);
            b(36197, this.g);
        }
    }

    public void a(int i2, int i3) {
        this.p = i2;
        this.q = i3;
    }

    public void a(int i2, boolean z, int i3) {
        if (!(this.n == z && this.o == i3)) {
            this.n = z;
            this.o = i3;
            float[] fArr = new float[20];
            for (int i4 = 0; i4 < 20; i4++) {
                fArr[i4] = this.b[i4];
            }
            if (this.n) {
                fArr[0] = -fArr[0];
                fArr[5] = -fArr[5];
                fArr[10] = -fArr[10];
                fArr[15] = -fArr[15];
            }
            int i5 = i3 / 90;
            for (int i6 = 0; i6 < i5; i6++) {
                float f2 = fArr[3];
                float f3 = fArr[4];
                fArr[3] = fArr[8];
                fArr[4] = fArr[9];
                fArr[8] = fArr[18];
                fArr[9] = fArr[19];
                fArr[18] = fArr[13];
                fArr[19] = fArr[14];
                fArr[13] = f2;
                fArr[14] = f3;
            }
            this.c.clear();
            this.c.put(fArr).position(0);
        }
        b(3553, i2);
    }

    private void b(int i2, int i3) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16640);
        if (this.l) {
            this.l = false;
            return;
        }
        GLES20.glUseProgram(this.f);
        a("glUseProgram");
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(i2, i3);
        this.c.position(0);
        GLES20.glVertexAttribPointer(this.j, 3, 5126, false, 20, this.c);
        a("glVertexAttribPointer maPosition");
        GLES20.glEnableVertexAttribArray(this.j);
        a("glEnableVertexAttribArray maPositionHandle");
        this.c.position(3);
        GLES20.glVertexAttribPointer(this.k, 2, 5126, false, 20, this.c);
        a("glVertexAttribPointer maTextureHandle");
        GLES20.glEnableVertexAttribArray(this.k);
        a("glEnableVertexAttribArray maTextureHandle");
        Matrix.setIdentityM(this.d, 0);
        GLES20.glUniformMatrix4fv(this.h, 1, false, this.d, 0);
        if (this.p % 8 != 0) {
            Matrix.scaleM(this.e, 0, (((float) (this.p - 1)) * 1.0f) / ((float) (((this.p + 7) / 8) * 8)), 1.0f, 1.0f);
        }
        if (this.q % 8 != 0) {
            Matrix.scaleM(this.e, 0, 1.0f, (((float) (this.q - 1)) * 1.0f) / ((float) (((this.q + 7) / 8) * 8)), 1.0f);
        }
        GLES20.glUniformMatrix4fv(this.i, 1, false, this.e, 0);
        GLES20.glDrawArrays(5, 0, 4);
        a("glDrawArrays");
        GLES20.glFinish();
    }

    public void b() {
        if (this.m) {
            this.f = a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n  gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
        } else {
            this.f = a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "varying highp vec2 vTextureCoord;\n \nuniform sampler2D sTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(sTexture, vTextureCoord);\n}");
        }
        if (this.f == 0) {
            throw new RuntimeException("failed creating program");
        }
        this.j = GLES20.glGetAttribLocation(this.f, "aPosition");
        a("glGetAttribLocation aPosition");
        if (this.j == -1) {
            throw new RuntimeException("Could not get attrib location for aPosition");
        }
        this.k = GLES20.glGetAttribLocation(this.f, "aTextureCoord");
        a("glGetAttribLocation aTextureCoord");
        if (this.k == -1) {
            throw new RuntimeException("Could not get attrib location for aTextureCoord");
        }
        this.h = GLES20.glGetUniformLocation(this.f, "uMVPMatrix");
        a("glGetUniformLocation uMVPMatrix");
        if (this.h == -1) {
            throw new RuntimeException("Could not get attrib location for uMVPMatrix");
        }
        this.i = GLES20.glGetUniformLocation(this.f, "uSTMatrix");
        a("glGetUniformLocation uSTMatrix");
        if (this.i == -1) {
            throw new RuntimeException("Could not get attrib location for uSTMatrix");
        }
        if (this.m) {
            c();
        }
        GLES20.glTexParameterf(36197, 10241, 9729.0f);
        GLES20.glTexParameterf(36197, Task.EXTRAS_LIMIT_BYTES, 9729.0f);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        a("glTexParameter");
    }

    private void c() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        this.g = iArr[0];
        GLES20.glBindTexture(36197, this.g);
        a("glBindTexture mTextureID");
    }

    private int a(int i2, String str) {
        int glCreateShader = GLES20.glCreateShader(i2);
        a("glCreateShader type=" + i2);
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return glCreateShader;
        }
        TXCLog.e("TXCOesTextureRender", "Could not compile shader " + i2 + ":");
        TXCLog.e("TXCOesTextureRender", " " + GLES20.glGetShaderInfoLog(glCreateShader));
        GLES20.glDeleteShader(glCreateShader);
        return 0;
    }

    private int a(String str, String str2) {
        int a2;
        int a3 = a(35633, str);
        if (a3 == 0 || (a2 = a(35632, str2)) == 0) {
            return 0;
        }
        int glCreateProgram = GLES20.glCreateProgram();
        a("glCreateProgram");
        if (glCreateProgram == 0) {
            TXCLog.e("TXCOesTextureRender", "Could not create program");
        }
        GLES20.glAttachShader(glCreateProgram, a3);
        a("glAttachShader");
        GLES20.glAttachShader(glCreateProgram, a2);
        a("glAttachShader");
        GLES20.glLinkProgram(glCreateProgram);
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
        if (iArr[0] == 1) {
            return glCreateProgram;
        }
        TXCLog.e("TXCOesTextureRender", "Could not link program: ");
        TXCLog.e("TXCOesTextureRender", GLES20.glGetProgramInfoLog(glCreateProgram));
        GLES20.glDeleteProgram(glCreateProgram);
        return 0;
    }

    public void a(String str) {
        int glGetError = GLES20.glGetError();
        if (glGetError != 0) {
            TXCLog.e("TXCOesTextureRender", str + ": glError " + glGetError);
        }
    }
}
