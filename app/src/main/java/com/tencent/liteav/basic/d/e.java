package com.tencent.liteav.basic.d;

/* compiled from: TXCTextureRotationUtil */
public class e {
    public static final float[] a = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    public static final float[] b = {1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f};
    public static final float[] c = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f};
    public static final float[] d = {0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};
    public static final float[] e = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};

    public static float[] a(d dVar, boolean z, boolean z2) {
        float[] fArr;
        switch (dVar) {
            case ROTATION_90:
                fArr = (float[]) b.clone();
                break;
            case ROTATION_180:
                fArr = (float[]) c.clone();
                break;
            case ROTATION_270:
                fArr = (float[]) d.clone();
                break;
            default:
                fArr = (float[]) a.clone();
                break;
        }
        float[] fArr2 = z ? new float[]{a(fArr[0]), fArr[1], a(fArr[2]), fArr[3], a(fArr[4]), fArr[5], a(fArr[6]), fArr[7]} : fArr;
        if (!z2) {
            return fArr2;
        }
        return new float[]{fArr2[0], a(fArr2[1]), fArr2[2], a(fArr2[3]), fArr2[4], a(fArr2[5]), fArr2[6], a(fArr2[7])};
    }

    private static float a(float f) {
        if (f == 0.0f) {
            return 1.0f;
        }
        return 0.0f;
    }
}
