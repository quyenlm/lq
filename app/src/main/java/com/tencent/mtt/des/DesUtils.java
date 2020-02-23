package com.tencent.mtt.des;

import java.lang.reflect.Array;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DesUtils {
    private static final int[] E = {32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1};
    public static final int FLAG_DECRYPT = 0;
    public static final int FLAG_ENCRYPT = 1;
    private static final int[] IP = {58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7};
    private static final int[] IP_1 = {40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25};
    public static final byte[] KEY = {-25, -101, -115, 1, 47, 7, -27, -59, 18, Byte.MIN_VALUE, 123, 79, -44, 37, 46, 115};
    private static final int[] LeftMove = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
    public static final byte[] MAC_KEY = {37, -110, 60, Byte.MAX_VALUE, 42, -27, -17, -110};
    public static final byte[] MTT_KEY = {-122, -8, -23, -84, -125, 113, 84, 99};
    private static final int[] P = {16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4, 25};
    private static final int[] PC_1 = {57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4};
    private static final int[] PC_2 = {14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32};
    public static final byte[] QQMARKET_KEY = "AL!#$AC9Ahg@KLJ1".getBytes();
    public static final byte[] REPORT_KEY_TEA = {98, -24, 57, -84, -115, 117, 55, 121};
    private static final int[][][] S_Box;

    static {
        int[] iArr = new int[16];
        iArr[0] = 14;
        iArr[1] = 4;
        iArr[2] = 13;
        iArr[3] = 1;
        iArr[4] = 2;
        iArr[5] = 15;
        iArr[6] = 11;
        iArr[7] = 8;
        iArr[8] = 3;
        iArr[9] = 10;
        iArr[10] = 6;
        iArr[11] = 12;
        iArr[12] = 5;
        iArr[13] = 9;
        iArr[15] = 7;
        int[] iArr2 = new int[16];
        iArr2[1] = 15;
        iArr2[2] = 7;
        iArr2[3] = 4;
        iArr2[4] = 14;
        iArr2[5] = 2;
        iArr2[6] = 13;
        iArr2[7] = 1;
        iArr2[8] = 10;
        iArr2[9] = 6;
        iArr2[10] = 12;
        iArr2[11] = 11;
        iArr2[12] = 9;
        iArr2[13] = 5;
        iArr2[14] = 3;
        iArr2[15] = 8;
        int[] iArr3 = new int[16];
        iArr3[0] = 4;
        iArr3[1] = 1;
        iArr3[2] = 14;
        iArr3[3] = 8;
        iArr3[4] = 13;
        iArr3[5] = 6;
        iArr3[6] = 2;
        iArr3[7] = 11;
        iArr3[8] = 15;
        iArr3[9] = 12;
        iArr3[10] = 9;
        iArr3[11] = 7;
        iArr3[12] = 3;
        iArr3[13] = 10;
        iArr3[14] = 5;
        int[] iArr4 = new int[16];
        iArr4[0] = 15;
        iArr4[1] = 12;
        iArr4[2] = 8;
        iArr4[3] = 2;
        iArr4[4] = 4;
        iArr4[5] = 9;
        iArr4[6] = 1;
        iArr4[7] = 7;
        iArr4[8] = 5;
        iArr4[9] = 11;
        iArr4[10] = 3;
        iArr4[11] = 14;
        iArr4[12] = 10;
        iArr4[14] = 6;
        iArr4[15] = 13;
        int[][] iArr5 = {iArr, iArr2, iArr3, iArr4};
        int[] iArr6 = new int[16];
        iArr6[0] = 15;
        iArr6[1] = 1;
        iArr6[2] = 8;
        iArr6[3] = 14;
        iArr6[4] = 6;
        iArr6[5] = 11;
        iArr6[6] = 3;
        iArr6[7] = 4;
        iArr6[8] = 9;
        iArr6[9] = 7;
        iArr6[10] = 2;
        iArr6[11] = 13;
        iArr6[12] = 12;
        iArr6[14] = 5;
        iArr6[15] = 10;
        int[] iArr7 = new int[16];
        iArr7[0] = 3;
        iArr7[1] = 13;
        iArr7[2] = 4;
        iArr7[3] = 7;
        iArr7[4] = 15;
        iArr7[5] = 2;
        iArr7[6] = 8;
        iArr7[7] = 14;
        iArr7[8] = 12;
        iArr7[10] = 1;
        iArr7[11] = 10;
        iArr7[12] = 6;
        iArr7[13] = 9;
        iArr7[14] = 11;
        iArr7[15] = 5;
        int[] iArr8 = new int[16];
        iArr8[1] = 14;
        iArr8[2] = 7;
        iArr8[3] = 11;
        iArr8[4] = 10;
        iArr8[5] = 4;
        iArr8[6] = 13;
        iArr8[7] = 1;
        iArr8[8] = 5;
        iArr8[9] = 8;
        iArr8[10] = 12;
        iArr8[11] = 6;
        iArr8[12] = 9;
        iArr8[13] = 3;
        iArr8[14] = 2;
        iArr8[15] = 15;
        int[] iArr9 = new int[16];
        iArr9[0] = 13;
        iArr9[1] = 8;
        iArr9[2] = 10;
        iArr9[3] = 1;
        iArr9[4] = 3;
        iArr9[5] = 15;
        iArr9[6] = 4;
        iArr9[7] = 2;
        iArr9[8] = 11;
        iArr9[9] = 6;
        iArr9[10] = 7;
        iArr9[11] = 12;
        iArr9[13] = 5;
        iArr9[14] = 14;
        iArr9[15] = 9;
        int[][] iArr10 = {iArr6, iArr7, iArr8, iArr9};
        int[] iArr11 = new int[16];
        iArr11[0] = 10;
        iArr11[2] = 9;
        iArr11[3] = 14;
        iArr11[4] = 6;
        iArr11[5] = 3;
        iArr11[6] = 15;
        iArr11[7] = 5;
        iArr11[8] = 1;
        iArr11[9] = 13;
        iArr11[10] = 12;
        iArr11[11] = 7;
        iArr11[12] = 11;
        iArr11[13] = 4;
        iArr11[14] = 2;
        iArr11[15] = 8;
        int[] iArr12 = new int[16];
        iArr12[0] = 13;
        iArr12[1] = 7;
        iArr12[3] = 9;
        iArr12[4] = 3;
        iArr12[5] = 4;
        iArr12[6] = 6;
        iArr12[7] = 10;
        iArr12[8] = 2;
        iArr12[9] = 8;
        iArr12[10] = 5;
        iArr12[11] = 14;
        iArr12[12] = 12;
        iArr12[13] = 11;
        iArr12[14] = 15;
        iArr12[15] = 1;
        int[] iArr13 = new int[16];
        iArr13[0] = 13;
        iArr13[1] = 6;
        iArr13[2] = 4;
        iArr13[3] = 9;
        iArr13[4] = 8;
        iArr13[5] = 15;
        iArr13[6] = 3;
        iArr13[8] = 11;
        iArr13[9] = 1;
        iArr13[10] = 2;
        iArr13[11] = 12;
        iArr13[12] = 5;
        iArr13[13] = 10;
        iArr13[14] = 14;
        iArr13[15] = 7;
        int[] iArr14 = new int[16];
        iArr14[0] = 1;
        iArr14[1] = 10;
        iArr14[2] = 13;
        iArr14[4] = 6;
        iArr14[5] = 9;
        iArr14[6] = 8;
        iArr14[7] = 7;
        iArr14[8] = 4;
        iArr14[9] = 15;
        iArr14[10] = 14;
        iArr14[11] = 3;
        iArr14[12] = 11;
        iArr14[13] = 5;
        iArr14[14] = 2;
        iArr14[15] = 12;
        int[][] iArr15 = {iArr11, iArr12, iArr13, iArr14};
        int[] iArr16 = new int[16];
        iArr16[0] = 7;
        iArr16[1] = 13;
        iArr16[2] = 14;
        iArr16[3] = 3;
        iArr16[5] = 6;
        iArr16[6] = 9;
        iArr16[7] = 10;
        iArr16[8] = 1;
        iArr16[9] = 2;
        iArr16[10] = 8;
        iArr16[11] = 5;
        iArr16[12] = 11;
        iArr16[13] = 12;
        iArr16[14] = 4;
        iArr16[15] = 15;
        int[] iArr17 = new int[16];
        iArr17[0] = 13;
        iArr17[1] = 8;
        iArr17[2] = 11;
        iArr17[3] = 5;
        iArr17[4] = 6;
        iArr17[5] = 15;
        iArr17[7] = 3;
        iArr17[8] = 4;
        iArr17[9] = 7;
        iArr17[10] = 2;
        iArr17[11] = 12;
        iArr17[12] = 1;
        iArr17[13] = 10;
        iArr17[14] = 14;
        iArr17[15] = 9;
        int[] iArr18 = new int[16];
        iArr18[0] = 10;
        iArr18[1] = 6;
        iArr18[2] = 9;
        iArr18[4] = 12;
        iArr18[5] = 11;
        iArr18[6] = 7;
        iArr18[7] = 13;
        iArr18[8] = 15;
        iArr18[9] = 1;
        iArr18[10] = 3;
        iArr18[11] = 14;
        iArr18[12] = 5;
        iArr18[13] = 2;
        iArr18[14] = 8;
        iArr18[15] = 4;
        int[] iArr19 = new int[16];
        iArr19[0] = 3;
        iArr19[1] = 15;
        iArr19[3] = 6;
        iArr19[4] = 10;
        iArr19[5] = 1;
        iArr19[6] = 13;
        iArr19[7] = 8;
        iArr19[8] = 9;
        iArr19[9] = 4;
        iArr19[10] = 5;
        iArr19[11] = 11;
        iArr19[12] = 12;
        iArr19[13] = 7;
        iArr19[14] = 2;
        iArr19[15] = 14;
        int[][] iArr20 = {iArr16, iArr17, iArr18, iArr19};
        int[] iArr21 = new int[16];
        iArr21[0] = 2;
        iArr21[1] = 12;
        iArr21[2] = 4;
        iArr21[3] = 1;
        iArr21[4] = 7;
        iArr21[5] = 10;
        iArr21[6] = 11;
        iArr21[7] = 6;
        iArr21[8] = 8;
        iArr21[9] = 5;
        iArr21[10] = 3;
        iArr21[11] = 15;
        iArr21[12] = 13;
        iArr21[14] = 14;
        iArr21[15] = 9;
        int[] iArr22 = new int[16];
        iArr22[0] = 14;
        iArr22[1] = 11;
        iArr22[2] = 2;
        iArr22[3] = 12;
        iArr22[4] = 4;
        iArr22[5] = 7;
        iArr22[6] = 13;
        iArr22[7] = 1;
        iArr22[8] = 5;
        iArr22[10] = 15;
        iArr22[11] = 10;
        iArr22[12] = 3;
        iArr22[13] = 9;
        iArr22[14] = 8;
        iArr22[15] = 6;
        int[] iArr23 = new int[16];
        iArr23[0] = 4;
        iArr23[1] = 2;
        iArr23[2] = 1;
        iArr23[3] = 11;
        iArr23[4] = 10;
        iArr23[5] = 13;
        iArr23[6] = 7;
        iArr23[7] = 8;
        iArr23[8] = 15;
        iArr23[9] = 9;
        iArr23[10] = 12;
        iArr23[11] = 5;
        iArr23[12] = 6;
        iArr23[13] = 3;
        iArr23[15] = 14;
        int[] iArr24 = new int[16];
        iArr24[0] = 11;
        iArr24[1] = 8;
        iArr24[2] = 12;
        iArr24[3] = 7;
        iArr24[4] = 1;
        iArr24[5] = 14;
        iArr24[6] = 2;
        iArr24[7] = 13;
        iArr24[8] = 6;
        iArr24[9] = 15;
        iArr24[11] = 9;
        iArr24[12] = 10;
        iArr24[13] = 4;
        iArr24[14] = 5;
        iArr24[15] = 3;
        int[][] iArr25 = {iArr21, iArr22, iArr23, iArr24};
        int[] iArr26 = new int[16];
        iArr26[0] = 12;
        iArr26[1] = 1;
        iArr26[2] = 10;
        iArr26[3] = 15;
        iArr26[4] = 9;
        iArr26[5] = 2;
        iArr26[6] = 6;
        iArr26[7] = 8;
        iArr26[9] = 13;
        iArr26[10] = 3;
        iArr26[11] = 4;
        iArr26[12] = 14;
        iArr26[13] = 7;
        iArr26[14] = 5;
        iArr26[15] = 11;
        int[] iArr27 = new int[16];
        iArr27[0] = 10;
        iArr27[1] = 15;
        iArr27[2] = 4;
        iArr27[3] = 2;
        iArr27[4] = 7;
        iArr27[5] = 12;
        iArr27[6] = 9;
        iArr27[7] = 5;
        iArr27[8] = 6;
        iArr27[9] = 1;
        iArr27[10] = 13;
        iArr27[11] = 14;
        iArr27[13] = 11;
        iArr27[14] = 3;
        iArr27[15] = 8;
        int[] iArr28 = new int[16];
        iArr28[0] = 9;
        iArr28[1] = 14;
        iArr28[2] = 15;
        iArr28[3] = 5;
        iArr28[4] = 2;
        iArr28[5] = 8;
        iArr28[6] = 12;
        iArr28[7] = 3;
        iArr28[8] = 7;
        iArr28[10] = 4;
        iArr28[11] = 10;
        iArr28[12] = 1;
        iArr28[13] = 13;
        iArr28[14] = 11;
        iArr28[15] = 6;
        int[] iArr29 = new int[16];
        iArr29[0] = 4;
        iArr29[1] = 3;
        iArr29[2] = 2;
        iArr29[3] = 12;
        iArr29[4] = 9;
        iArr29[5] = 5;
        iArr29[6] = 15;
        iArr29[7] = 10;
        iArr29[8] = 11;
        iArr29[9] = 14;
        iArr29[10] = 1;
        iArr29[11] = 7;
        iArr29[12] = 6;
        iArr29[14] = 8;
        iArr29[15] = 13;
        int[][] iArr30 = {iArr26, iArr27, iArr28, iArr29};
        int[] iArr31 = new int[16];
        iArr31[0] = 4;
        iArr31[1] = 11;
        iArr31[2] = 2;
        iArr31[3] = 14;
        iArr31[4] = 15;
        iArr31[6] = 8;
        iArr31[7] = 13;
        iArr31[8] = 3;
        iArr31[9] = 12;
        iArr31[10] = 9;
        iArr31[11] = 7;
        iArr31[12] = 5;
        iArr31[13] = 10;
        iArr31[14] = 6;
        iArr31[15] = 1;
        int[] iArr32 = new int[16];
        iArr32[0] = 13;
        iArr32[2] = 11;
        iArr32[3] = 7;
        iArr32[4] = 4;
        iArr32[5] = 9;
        iArr32[6] = 1;
        iArr32[7] = 10;
        iArr32[8] = 14;
        iArr32[9] = 3;
        iArr32[10] = 5;
        iArr32[11] = 12;
        iArr32[12] = 2;
        iArr32[13] = 15;
        iArr32[14] = 8;
        iArr32[15] = 6;
        int[] iArr33 = new int[16];
        iArr33[0] = 1;
        iArr33[1] = 4;
        iArr33[2] = 11;
        iArr33[3] = 13;
        iArr33[4] = 12;
        iArr33[5] = 3;
        iArr33[6] = 7;
        iArr33[7] = 14;
        iArr33[8] = 10;
        iArr33[9] = 15;
        iArr33[10] = 6;
        iArr33[11] = 8;
        iArr33[13] = 5;
        iArr33[14] = 9;
        iArr33[15] = 2;
        int[] iArr34 = new int[16];
        iArr34[0] = 6;
        iArr34[1] = 11;
        iArr34[2] = 13;
        iArr34[3] = 8;
        iArr34[4] = 1;
        iArr34[5] = 4;
        iArr34[6] = 10;
        iArr34[7] = 7;
        iArr34[8] = 9;
        iArr34[9] = 5;
        iArr34[11] = 15;
        iArr34[12] = 14;
        iArr34[13] = 2;
        iArr34[14] = 3;
        iArr34[15] = 12;
        int[][] iArr35 = {iArr31, iArr32, iArr33, iArr34};
        int[] iArr36 = new int[16];
        iArr36[0] = 13;
        iArr36[1] = 2;
        iArr36[2] = 8;
        iArr36[3] = 4;
        iArr36[4] = 6;
        iArr36[5] = 15;
        iArr36[6] = 11;
        iArr36[7] = 1;
        iArr36[8] = 10;
        iArr36[9] = 9;
        iArr36[10] = 3;
        iArr36[11] = 14;
        iArr36[12] = 5;
        iArr36[14] = 12;
        iArr36[15] = 7;
        int[] iArr37 = new int[16];
        iArr37[0] = 1;
        iArr37[1] = 15;
        iArr37[2] = 13;
        iArr37[3] = 8;
        iArr37[4] = 10;
        iArr37[5] = 3;
        iArr37[6] = 7;
        iArr37[7] = 4;
        iArr37[8] = 12;
        iArr37[9] = 5;
        iArr37[10] = 6;
        iArr37[11] = 11;
        iArr37[13] = 14;
        iArr37[14] = 9;
        iArr37[15] = 2;
        int[] iArr38 = new int[16];
        iArr38[0] = 7;
        iArr38[1] = 11;
        iArr38[2] = 4;
        iArr38[3] = 1;
        iArr38[4] = 9;
        iArr38[5] = 12;
        iArr38[6] = 14;
        iArr38[7] = 2;
        iArr38[9] = 6;
        iArr38[10] = 10;
        iArr38[11] = 13;
        iArr38[12] = 15;
        iArr38[13] = 3;
        iArr38[14] = 5;
        iArr38[15] = 8;
        int[] iArr39 = new int[16];
        iArr39[0] = 2;
        iArr39[1] = 1;
        iArr39[2] = 14;
        iArr39[3] = 7;
        iArr39[4] = 4;
        iArr39[5] = 10;
        iArr39[6] = 8;
        iArr39[7] = 13;
        iArr39[8] = 15;
        iArr39[9] = 12;
        iArr39[10] = 9;
        iArr39[12] = 3;
        iArr39[13] = 5;
        iArr39[14] = 6;
        iArr39[15] = 11;
        S_Box = new int[][][]{iArr5, iArr10, iArr15, iArr20, iArr25, iArr30, iArr35, new int[][]{iArr36, iArr37, iArr38, iArr39}};
    }

    private static byte[] UnitDes(byte[] des_key, byte[] des_data, int flag) {
        if (des_key.length == 8 && des_data.length == 8 && (flag == 1 || flag == 0)) {
            int[] iArr = new int[64];
            int[] iArr2 = new int[64];
            byte[] bArr = new byte[8];
            int[][] KeyArray = (int[][]) Array.newInstance(Integer.TYPE, new int[]{16, 48});
            int[] keydata = ReadDataToBirnaryIntArray(des_key);
            int[] encryptdata = ReadDataToBirnaryIntArray(des_data);
            KeyInitialize(keydata, KeyArray);
            return Encrypt(encryptdata, flag, KeyArray);
        }
        throw new RuntimeException("Data Format Error !");
    }

    private static void KeyInitialize(int[] key, int[][] keyarray) {
        int[] K0 = new int[56];
        for (int i = 0; i < 56; i++) {
            K0[i] = key[PC_1[i] - 1];
        }
        for (int i2 = 0; i2 < 16; i2++) {
            LeftBitMove(K0, LeftMove[i2]);
            for (int j = 0; j < 48; j++) {
                keyarray[i2][j] = K0[PC_2[j] - 1];
            }
        }
    }

    private static byte[] Encrypt(int[] timeData, int flag, int[][] keyarray) {
        byte[] encrypt = new byte[8];
        int flags = flag;
        int[] M = new int[64];
        int[] MIP_1 = new int[64];
        for (int i = 0; i < 64; i++) {
            M[i] = timeData[IP[i] - 1];
        }
        if (flags == 1) {
            for (int i2 = 0; i2 < 16; i2++) {
                LoopF(M, i2, flags, keyarray);
            }
        } else if (flags == 0) {
            for (int i3 = 15; i3 > -1; i3--) {
                LoopF(M, i3, flags, keyarray);
            }
        }
        for (int i4 = 0; i4 < 64; i4++) {
            MIP_1[i4] = M[IP_1[i4] - 1];
        }
        GetEncryptResultOfByteArray(MIP_1, encrypt);
        return encrypt;
    }

    /* JADX WARNING: type inference failed for: r7v0, types: [byte[]] */
    /* JADX WARNING: type inference failed for: r4v6, types: [byte] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int[] ReadDataToBirnaryIntArray(byte[] r7) {
        /*
            r6 = 8
            int[] r0 = new int[r6]
            r2 = 0
        L_0x0005:
            if (r2 < r6) goto L_0x000f
            r4 = 64
            int[] r1 = new int[r4]
            r2 = 0
        L_0x000c:
            if (r2 < r6) goto L_0x0026
            return r1
        L_0x000f:
            byte r4 = r7[r2]
            r0[r2] = r4
            r4 = r0[r2]
            if (r4 >= 0) goto L_0x0023
            r4 = r0[r2]
            int r4 = r4 + 256
            r0[r2] = r4
            r4 = r0[r2]
            int r4 = r4 % 256
            r0[r2] = r4
        L_0x0023:
            int r2 = r2 + 1
            goto L_0x0005
        L_0x0026:
            r3 = 0
        L_0x0027:
            if (r3 < r6) goto L_0x002c
            int r2 = r2 + 1
            goto L_0x000c
        L_0x002c:
            int r4 = r2 * 8
            int r4 = r4 + 7
            int r4 = r4 - r3
            r5 = r0[r2]
            int r5 = r5 % 2
            r1[r4] = r5
            r4 = r0[r2]
            int r4 = r4 / 2
            r0[r2] = r4
            int r3 = r3 + 1
            goto L_0x0027
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mtt.des.DesUtils.ReadDataToBirnaryIntArray(byte[]):int[]");
    }

    private static void LeftBitMove(int[] k, int offset) {
        int[] c0 = new int[28];
        int[] d0 = new int[28];
        int[] c1 = new int[28];
        int[] d1 = new int[28];
        for (int i = 0; i < 28; i++) {
            c0[i] = k[i];
            d0[i] = k[i + 28];
        }
        if (offset == 1) {
            for (int i2 = 0; i2 < 27; i2++) {
                c1[i2] = c0[i2 + 1];
                d1[i2] = d0[i2 + 1];
            }
            c1[27] = c0[0];
            d1[27] = d0[0];
        } else if (offset == 2) {
            for (int i3 = 0; i3 < 26; i3++) {
                c1[i3] = c0[i3 + 2];
                d1[i3] = d0[i3 + 2];
            }
            c1[26] = c0[0];
            d1[26] = d0[0];
            c1[27] = c0[1];
            d1[27] = d0[1];
        }
        for (int i4 = 0; i4 < 28; i4++) {
            k[i4] = c1[i4];
            k[i4 + 28] = d1[i4];
        }
    }

    private static void LoopF(int[] M, int times, int flag, int[][] keyarray) {
        int[] L0 = new int[32];
        int[] R0 = new int[32];
        int[] L1 = new int[32];
        int[] R1 = new int[32];
        int[] RE = new int[48];
        int[][] S = (int[][]) Array.newInstance(Integer.TYPE, new int[]{8, 6});
        int[] sBoxData = new int[8];
        int[] sValue = new int[32];
        int[] RP = new int[32];
        for (int i = 0; i < 32; i++) {
            L0[i] = M[i];
            R0[i] = M[i + 32];
        }
        for (int i2 = 0; i2 < 48; i2++) {
            RE[i2] = R0[E[i2] - 1];
            RE[i2] = RE[i2] + keyarray[times][i2];
            if (RE[i2] == 2) {
                RE[i2] = 0;
            }
        }
        for (int i3 = 0; i3 < 8; i3++) {
            for (int j = 0; j < 6; j++) {
                S[i3][j] = RE[(i3 * 6) + j];
            }
            sBoxData[i3] = S_Box[i3][(S[i3][0] << 1) + S[i3][5]][(S[i3][1] << 3) + (S[i3][2] << 2) + (S[i3][3] << 1) + S[i3][4]];
            for (int j2 = 0; j2 < 4; j2++) {
                sValue[((i3 * 4) + 3) - j2] = sBoxData[i3] % 2;
                sBoxData[i3] = sBoxData[i3] / 2;
            }
        }
        for (int i4 = 0; i4 < 32; i4++) {
            RP[i4] = sValue[P[i4] - 1];
            L1[i4] = R0[i4];
            R1[i4] = L0[i4] + RP[i4];
            if (R1[i4] == 2) {
                R1[i4] = 0;
            }
            if ((flag == 0 && times == 0) || (flag == 1 && times == 15)) {
                M[i4] = R1[i4];
                M[i4 + 32] = L1[i4];
            } else {
                M[i4] = L1[i4];
                M[i4 + 32] = R1[i4];
            }
        }
    }

    private static void GetEncryptResultOfByteArray(int[] data, byte[] value) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                value[i] = (byte) (value[i] + (data[(i << 3) + j] << (7 - j)));
            }
        }
    }

    private static byte[] ByteDataFormat(byte[] data) {
        int len = data.length;
        int padlen = 8 - (len % 8);
        int newlen = len + padlen;
        byte[] newdata = new byte[newlen];
        System.arraycopy(data, 0, newdata, 0, len);
        for (int i = len; i < newlen; i++) {
            newdata[i] = (byte) padlen;
        }
        return newdata;
    }

    private static byte[] KeyDataFormat(byte[] key) {
        byte[] fixkey = new byte[8];
        for (int i = 0; i < fixkey.length; i++) {
            fixkey[i] = 0;
        }
        if (key.length > 8) {
            System.arraycopy(key, 0, fixkey, 0, fixkey.length);
        } else {
            System.arraycopy(key, 0, fixkey, 0, key.length);
        }
        return fixkey;
    }

    public static byte[] DesEncrypt(byte[] des_key, byte[] des_data, int flag) {
        if (des_data == null || des_key == null) {
            return des_data;
        }
        try {
            byte[] format_key = KeyDataFormat(des_key);
            byte[] format_data = ByteDataFormat(des_data);
            int datalen = format_data.length;
            int unitcount = datalen / 8;
            byte[] result_data = new byte[datalen];
            for (int i = 0; i < unitcount; i++) {
                byte[] tmpkey = new byte[8];
                byte[] tmpdata = new byte[8];
                System.arraycopy(format_key, 0, tmpkey, 0, 8);
                System.arraycopy(format_data, i * 8, tmpdata, 0, 8);
                System.arraycopy(UnitDes(tmpkey, tmpdata, flag), 0, result_data, i * 8, 8);
            }
            if (flag == 0) {
                byte[] tempResData = new byte[des_data.length];
                System.arraycopy(result_data, 0, tempResData, 0, tempResData.length);
                byte padlen = tempResData[tempResData.length - 1];
                if (padlen > 0 && padlen <= 8) {
                    boolean bDesCheckResult = true;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= padlen) {
                            break;
                        } else if (padlen != tempResData[(tempResData.length - 1) - i2]) {
                            bDesCheckResult = false;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (bDesCheckResult) {
                        result_data = new byte[(tempResData.length - padlen)];
                        System.arraycopy(tempResData, 0, result_data, 0, result_data.length);
                    }
                }
            }
            return result_data;
        } catch (Exception e) {
            return des_data;
        }
    }

    public static byte[] Des3Encrypt(byte[] mykey, byte[] str, int mode) throws Exception {
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(mode, new SecretKeySpec(mykey, "DESede"));
        return cipher.doFinal(str);
    }
}
