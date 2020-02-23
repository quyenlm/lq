package com.tencent.liteav.basic.d;

import android.opengl.GLES20;
import com.google.android.gms.gcm.Task;
import com.tencent.liteav.basic.log.TXCLog;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/* compiled from: TXCOpenGlUtils */
public class c {
    public static FloatBuffer a = a(f);
    public static FloatBuffer b = a(g);
    public static FloatBuffer c = a(h);
    public static FloatBuffer d = a(i);
    public static FloatBuffer e = a(j);
    private static float[] f = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
    private static float[] g = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};
    private static float[] h = {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f};
    private static float[] i = {1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    private static float[] j = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f};
    private static int k = 2;

    public static FloatBuffer a(float[] fArr) {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(fArr.length * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer = allocateDirect.asFloatBuffer();
        asFloatBuffer.put(fArr);
        asFloatBuffer.position(0);
        return asFloatBuffer;
    }

    public static int a(int i2, int i3, int i4, int i5, IntBuffer intBuffer) {
        int b2 = b();
        GLES20.glBindTexture(3553, b2);
        GLES20.glTexImage2D(3553, 0, i4, i2, i3, 0, i5, 5121, intBuffer);
        GLES20.glBindTexture(3553, 0);
        return b2;
    }

    public static int a(int i2, int i3, int i4, int i5) {
        return a(i2, i3, i4, i5, (IntBuffer) null);
    }

    public static int a() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glBindTexture(36197, iArr[0]);
        GLES20.glTexParameterf(36197, 10241, 9729.0f);
        GLES20.glTexParameterf(36197, Task.EXTRAS_LIMIT_BYTES, 9729.0f);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        return iArr[0];
    }

    public static int b() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glBindTexture(3553, iArr[0]);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, Task.EXTRAS_LIMIT_BYTES, 9729.0f);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glBindTexture(3553, 0);
        return iArr[0];
    }

    public static int a(String str, int i2) {
        int[] iArr = new int[1];
        int glCreateShader = GLES20.glCreateShader(i2);
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return glCreateShader;
        }
        TXCLog.d("Load Shader Failed", "Compilation\n" + GLES20.glGetShaderInfoLog(glCreateShader));
        return 0;
    }

    public static int a(String str, String str2) {
        int[] iArr = new int[1];
        int a2 = a(str, 35633);
        if (a2 == 0) {
            TXCLog.d("Load Program", "Vertex Shader Failed");
            return 0;
        }
        int a3 = a(str2, 35632);
        if (a3 == 0) {
            TXCLog.d("Load Program", "Fragment Shader Failed");
            return 0;
        }
        int glCreateProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(glCreateProgram, a2);
        GLES20.glAttachShader(glCreateProgram, a3);
        GLES20.glLinkProgram(glCreateProgram);
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
        if (iArr[0] <= 0) {
            TXCLog.d("Load Program", "Linking Failed");
            return 0;
        }
        GLES20.glDeleteShader(a2);
        GLES20.glDeleteShader(a3);
        return glCreateProgram;
    }
}
