package com.google.android.gms.internal;

@zzzn
public final class zzhj {
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0055, code lost:
        r0 = r0 ^ r4;
        r0 = (r0 ^ (r0 >>> 16)) * -2048144789;
        r0 = (r0 ^ (r0 >>> 13)) * -1028477387;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0067, code lost:
        return r0 ^ (r0 >>> 16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0070, code lost:
        r1 = r1 | ((r0[r5 + 1] & 255) << 8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0079, code lost:
        r0 = ((r0[r5] & 255) | r1) * -862048943;
        r0 = (((r0 >>> 17) | (r0 << 15)) * 461845907) ^ r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int zzA(java.lang.String r10) {
        /*
            r9 = 461845907(0x1b873593, float:2.2368498E-22)
            r8 = -862048943(0xffffffffcc9e2d51, float:-8.2930312E7)
            r1 = 0
            java.lang.String r0 = "UTF-8"
            byte[] r0 = r10.getBytes(r0)     // Catch:{ UnsupportedEncodingException -> 0x0049 }
        L_0x000d:
            int r4 = r0.length
            r2 = r4 & -4
            int r5 = r2 + 0
            r3 = r1
            r2 = r1
        L_0x0014:
            if (r3 >= r5) goto L_0x004f
            byte r6 = r0[r3]
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r7 = r3 + 1
            byte r7 = r0[r7]
            r7 = r7 & 255(0xff, float:3.57E-43)
            int r7 = r7 << 8
            r6 = r6 | r7
            int r7 = r3 + 2
            byte r7 = r0[r7]
            r7 = r7 & 255(0xff, float:3.57E-43)
            int r7 = r7 << 16
            r6 = r6 | r7
            int r7 = r3 + 3
            byte r7 = r0[r7]
            int r7 = r7 << 24
            r6 = r6 | r7
            int r6 = r6 * r8
            int r7 = r6 << 15
            int r6 = r6 >>> 17
            r6 = r6 | r7
            int r6 = r6 * r9
            r2 = r2 ^ r6
            int r6 = r2 << 13
            int r2 = r2 >>> 19
            r2 = r2 | r6
            int r2 = r2 * 5
            r6 = -430675100(0xffffffffe6546b64, float:-2.5078068E23)
            int r2 = r2 + r6
            int r3 = r3 + 4
            goto L_0x0014
        L_0x0049:
            r0 = move-exception
            byte[] r0 = r10.getBytes()
            goto L_0x000d
        L_0x004f:
            r3 = r4 & 3
            switch(r3) {
                case 1: goto L_0x0079;
                case 2: goto L_0x0070;
                case 3: goto L_0x0068;
                default: goto L_0x0054;
            }
        L_0x0054:
            r0 = r2
        L_0x0055:
            r0 = r0 ^ r4
            int r1 = r0 >>> 16
            r0 = r0 ^ r1
            r1 = -2048144789(0xffffffff85ebca6b, float:-2.217365E-35)
            int r0 = r0 * r1
            int r1 = r0 >>> 13
            r0 = r0 ^ r1
            r1 = -1028477387(0xffffffffc2b2ae35, float:-89.34025)
            int r0 = r0 * r1
            int r1 = r0 >>> 16
            r0 = r0 ^ r1
            return r0
        L_0x0068:
            int r1 = r5 + 2
            byte r1 = r0[r1]
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 << 16
        L_0x0070:
            int r3 = r5 + 1
            byte r3 = r0[r3]
            r3 = r3 & 255(0xff, float:3.57E-43)
            int r3 = r3 << 8
            r1 = r1 | r3
        L_0x0079:
            byte r0 = r0[r5]
            r0 = r0 & 255(0xff, float:3.57E-43)
            r0 = r0 | r1
            int r0 = r0 * r8
            int r1 = r0 << 15
            int r0 = r0 >>> 17
            r0 = r0 | r1
            int r0 = r0 * r9
            r0 = r0 ^ r2
            goto L_0x0055
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzhj.zzA(java.lang.String):int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:46:0x007a, code lost:
        if (((r9 >= 65382 && r9 <= 65437) || (r9 >= 65441 && r9 <= 65500)) != false) goto L_0x007c;
     */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x007f  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x009c  */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String[] zzd(@android.support.annotation.Nullable java.lang.String r12, boolean r13) {
        /*
            r4 = 1
            r3 = 0
            if (r12 != 0) goto L_0x0006
            r0 = 0
        L_0x0005:
            return r0
        L_0x0006:
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            char[] r7 = r12.toCharArray()
            int r8 = r12.length()
            r2 = r3
            r0 = r3
            r1 = r3
        L_0x0016:
            if (r1 >= r8) goto L_0x00db
            int r9 = java.lang.Character.codePointAt(r7, r1)
            int r10 = java.lang.Character.charCount(r9)
            boolean r5 = java.lang.Character.isLetter(r9)
            if (r5 == 0) goto L_0x009a
            java.lang.Character$UnicodeBlock r5 = java.lang.Character.UnicodeBlock.of(r9)
            java.lang.Character$UnicodeBlock r11 = java.lang.Character.UnicodeBlock.BOPOMOFO
            if (r5 == r11) goto L_0x0062
            java.lang.Character$UnicodeBlock r11 = java.lang.Character.UnicodeBlock.BOPOMOFO_EXTENDED
            if (r5 == r11) goto L_0x0062
            java.lang.Character$UnicodeBlock r11 = java.lang.Character.UnicodeBlock.CJK_COMPATIBILITY
            if (r5 == r11) goto L_0x0062
            java.lang.Character$UnicodeBlock r11 = java.lang.Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
            if (r5 == r11) goto L_0x0062
            java.lang.Character$UnicodeBlock r11 = java.lang.Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT
            if (r5 == r11) goto L_0x0062
            java.lang.Character$UnicodeBlock r11 = java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
            if (r5 == r11) goto L_0x0062
            java.lang.Character$UnicodeBlock r11 = java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
            if (r5 == r11) goto L_0x0062
            java.lang.Character$UnicodeBlock r11 = java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
            if (r5 == r11) goto L_0x0062
            java.lang.Character$UnicodeBlock r11 = java.lang.Character.UnicodeBlock.ENCLOSED_CJK_LETTERS_AND_MONTHS
            if (r5 == r11) goto L_0x0062
            java.lang.Character$UnicodeBlock r11 = java.lang.Character.UnicodeBlock.HANGUL_JAMO
            if (r5 == r11) goto L_0x0062
            java.lang.Character$UnicodeBlock r11 = java.lang.Character.UnicodeBlock.HANGUL_SYLLABLES
            if (r5 == r11) goto L_0x0062
            java.lang.Character$UnicodeBlock r11 = java.lang.Character.UnicodeBlock.HIRAGANA
            if (r5 == r11) goto L_0x0062
            java.lang.Character$UnicodeBlock r11 = java.lang.Character.UnicodeBlock.KATAKANA
            if (r5 == r11) goto L_0x0062
            java.lang.Character$UnicodeBlock r11 = java.lang.Character.UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS
            if (r5 != r11) goto L_0x0096
        L_0x0062:
            r5 = r4
        L_0x0063:
            if (r5 != 0) goto L_0x007c
            r5 = 65382(0xff66, float:9.162E-41)
            if (r9 < r5) goto L_0x006f
            r5 = 65437(0xff9d, float:9.1697E-41)
            if (r9 <= r5) goto L_0x0079
        L_0x006f:
            r5 = 65441(0xffa1, float:9.1702E-41)
            if (r9 < r5) goto L_0x0098
            r5 = 65500(0xffdc, float:9.1785E-41)
            if (r9 > r5) goto L_0x0098
        L_0x0079:
            r5 = r4
        L_0x007a:
            if (r5 == 0) goto L_0x009a
        L_0x007c:
            r5 = r4
        L_0x007d:
            if (r5 == 0) goto L_0x009c
            if (r2 == 0) goto L_0x008b
            java.lang.String r2 = new java.lang.String
            int r5 = r1 - r0
            r2.<init>(r7, r0, r5)
            r6.add(r2)
        L_0x008b:
            java.lang.String r2 = new java.lang.String
            r2.<init>(r7, r1, r10)
            r6.add(r2)
            r2 = r3
        L_0x0094:
            int r1 = r1 + r10
            goto L_0x0016
        L_0x0096:
            r5 = r3
            goto L_0x0063
        L_0x0098:
            r5 = r3
            goto L_0x007a
        L_0x009a:
            r5 = r3
            goto L_0x007d
        L_0x009c:
            boolean r5 = java.lang.Character.isLetterOrDigit(r9)
            if (r5 != 0) goto L_0x00b1
            int r5 = java.lang.Character.getType(r9)
            r11 = 6
            if (r5 == r11) goto L_0x00b1
            int r5 = java.lang.Character.getType(r9)
            r11 = 8
            if (r5 != r11) goto L_0x00b6
        L_0x00b1:
            if (r2 != 0) goto L_0x00b4
            r0 = r1
        L_0x00b4:
            r2 = r4
            goto L_0x0094
        L_0x00b6:
            if (r13 == 0) goto L_0x00cd
            int r5 = java.lang.Character.charCount(r9)
            if (r5 != r4) goto L_0x00cd
            char[] r5 = java.lang.Character.toChars(r9)
            char r5 = r5[r3]
            r9 = 39
            if (r5 != r9) goto L_0x00cd
            if (r2 != 0) goto L_0x00cb
            r0 = r1
        L_0x00cb:
            r2 = r4
            goto L_0x0094
        L_0x00cd:
            if (r2 == 0) goto L_0x0094
            java.lang.String r2 = new java.lang.String
            int r5 = r1 - r0
            r2.<init>(r7, r0, r5)
            r6.add(r2)
            r2 = r3
            goto L_0x0094
        L_0x00db:
            if (r2 == 0) goto L_0x00e6
            java.lang.String r2 = new java.lang.String
            int r1 = r1 - r0
            r2.<init>(r7, r0, r1)
            r6.add(r2)
        L_0x00e6:
            int r0 = r6.size()
            java.lang.String[] r0 = new java.lang.String[r0]
            java.lang.Object[] r0 = r6.toArray(r0)
            java.lang.String[] r0 = (java.lang.String[]) r0
            goto L_0x0005
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzhj.zzd(java.lang.String, boolean):java.lang.String[]");
    }
}
