package com.tencent.liteav.basic.d;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import com.vk.sdk.api.VKApiConst;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.LinkedList;

/* compiled from: TXCGPUFilter */
public class b {
    protected int a;
    protected int b;
    protected int c;
    protected int d;
    protected int e;
    protected int f;
    protected boolean g;
    protected FloatBuffer h;
    protected FloatBuffer i;
    protected float[] j;
    protected float[] k;
    protected a l;
    protected int m;
    protected int n;
    protected boolean o;
    protected boolean p;
    protected boolean q;
    private final LinkedList<Runnable> r;
    private final String s;
    private final String t;
    private boolean u;
    private int v;
    private float[] w;
    private String x;

    /* compiled from: TXCGPUFilter */
    public interface a {
        void a(int i);
    }

    public b() {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}", false);
    }

    public b(String str, String str2) {
        this(str, str2, false);
    }

    public b(String str, String str2, boolean z) {
        this.u = false;
        this.v = -1;
        this.w = null;
        this.m = -1;
        this.n = -1;
        this.o = false;
        this.p = false;
        this.q = false;
        this.x = "TXCGPUFilter";
        this.r = new LinkedList<>();
        this.s = str;
        this.t = str2;
        this.q = z;
        if (true == z) {
            TXCLog.i(this.x, "set Oes fileter");
        }
        this.h = ByteBuffer.allocateDirect(e.e.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.j = e.e;
        this.h.put(this.j).position(0);
        this.i = ByteBuffer.allocateDirect(e.a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.k = e.a(d.NORMAL, false, true);
        this.i.put(this.k).position(0);
    }

    public boolean a() {
        this.a = c.a(this.s, this.t);
        if (this.a == 0 || !b()) {
            this.g = false;
        } else {
            this.g = true;
        }
        c();
        return this.g;
    }

    public void a(boolean z) {
        this.o = z;
    }

    public void a(a aVar) {
        this.u = aVar != null;
        this.l = aVar;
    }

    public boolean b() {
        this.b = GLES20.glGetAttribLocation(this.a, VKApiConst.POSITION);
        this.c = GLES20.glGetUniformLocation(this.a, "inputImageTexture");
        this.v = GLES20.glGetUniformLocation(this.a, "textureTransform");
        this.d = GLES20.glGetAttribLocation(this.a, "inputTextureCoordinate");
        return true;
    }

    public void c() {
    }

    public void d() {
        GLES20.glDeleteProgram(this.a);
        e();
        this.g = false;
    }

    public void e() {
        f();
        this.f = -1;
        this.e = -1;
    }

    public void f() {
        if (this.m != -1) {
            GLES20.glDeleteFramebuffers(1, new int[]{this.m}, 0);
            this.m = -1;
        }
        if (this.n != -1) {
            GLES20.glDeleteTextures(1, new int[]{this.n}, 0);
            this.n = -1;
        }
    }

    public void a(int i2, int i3) {
        if (this.f != i3 || this.e != i2) {
            this.e = i2;
            this.f = i3;
            if (this.o) {
                if (this.m != -1) {
                    f();
                }
                int[] iArr = new int[1];
                GLES20.glGenFramebuffers(1, iArr, 0);
                this.m = iArr[0];
                this.n = c.a(i2, i3, 6408, 6408);
                GLES20.glBindFramebuffer(36160, this.m);
                GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.n, 0);
                GLES20.glBindFramebuffer(36160, 0);
            }
        }
    }

    public void a(int i2, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        GLES20.glUseProgram(this.a);
        i();
        if (this.g) {
            floatBuffer.position(0);
            GLES20.glVertexAttribPointer(this.b, 2, 5126, false, 0, floatBuffer);
            GLES20.glEnableVertexAttribArray(this.b);
            floatBuffer2.position(0);
            GLES20.glVertexAttribPointer(this.d, 2, 5126, false, 0, floatBuffer2);
            GLES20.glEnableVertexAttribArray(this.d);
            if (this.v >= 0 && this.w != null) {
                GLES20.glUniformMatrix4fv(this.v, 1, false, this.w, 0);
            }
            if (i2 != -1) {
                GLES20.glActiveTexture(33984);
                if (true == this.q) {
                    GLES20.glBindTexture(36197, i2);
                } else {
                    GLES20.glBindTexture(3553, i2);
                }
                GLES20.glUniform1i(this.c, 0);
            }
            g();
            GLES20.glDrawArrays(5, 0, 4);
            GLES20.glDisableVertexAttribArray(this.b);
            GLES20.glDisableVertexAttribArray(this.d);
            h();
            if (true == this.q) {
                GLES20.glBindTexture(36197, 0);
            } else {
                GLES20.glBindTexture(3553, 0);
            }
        }
    }

    public int b(int i2, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        if (!this.g) {
            return -1;
        }
        a(i2, floatBuffer, floatBuffer2);
        if (this.l instanceof a) {
            this.l.a(i2);
        }
        return 1;
    }

    public int a(int i2) {
        return b(i2, this.h, this.i);
    }

    /* access modifiers changed from: protected */
    public void g() {
    }

    /* access modifiers changed from: protected */
    public void h() {
    }

    /* access modifiers changed from: protected */
    public void i() {
        while (!this.r.isEmpty()) {
            this.r.removeFirst().run();
        }
    }

    public int a(int i2, int i3, int i4) {
        if (!this.g) {
            return -1;
        }
        GLES20.glBindFramebuffer(36160, i3);
        a(i2, this.h, this.i);
        if (this.l instanceof a) {
            this.l.a(i4);
        }
        GLES20.glBindFramebuffer(36160, 0);
        return i4;
    }

    public int b(int i2) {
        return a(i2, this.m, this.n);
    }

    public int j() {
        return this.n;
    }

    public void a(float[] fArr, float[] fArr2) {
        this.j = fArr;
        this.h = ByteBuffer.allocateDirect(e.e.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.h.put(fArr).position(0);
        this.k = fArr2;
        this.i = ByteBuffer.allocateDirect(e.a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.i.put(fArr2).position(0);
    }
}
