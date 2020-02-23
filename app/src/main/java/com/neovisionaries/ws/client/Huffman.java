package com.neovisionaries.ws.client;

class Huffman {
    private final int mMaxCodeLen;
    private final int[] mMaxCodeValsFromCodeLen;
    private final int mMinCodeLen;
    private final int[] mSymsFromCodeVal;

    public Huffman(int[] codeLensFromSym) {
        this.mMinCodeLen = Math.max(Misc.min(codeLensFromSym), 1);
        this.mMaxCodeLen = Misc.max(codeLensFromSym);
        Object[] out = new Object[2];
        this.mMaxCodeValsFromCodeLen = createMaxCodeValsFromCodeLen(createCountsFromCodeLen(codeLensFromSym, this.mMaxCodeLen), this.mMaxCodeLen, out);
        this.mSymsFromCodeVal = createSymsFromCodeVal(codeLensFromSym, (int[]) out[0], ((Integer) out[1]).intValue());
    }

    private static int[] createIntArray(int size, int initialValue) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = initialValue;
        }
        return array;
    }

    private static int[] createCountsFromCodeLen(int[] codeLensFromSym, int maxCodeLen) {
        int[] countsFromCodeLen = new int[(maxCodeLen + 1)];
        for (int codeLength : codeLensFromSym) {
            countsFromCodeLen[codeLength] = countsFromCodeLen[codeLength] + 1;
        }
        return countsFromCodeLen;
    }

    /* JADX WARNING: type inference failed for: r11v0, types: [java.lang.Object[]] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int[] createMaxCodeValsFromCodeLen(int[] r9, int r10, java.lang.Object[] r11) {
        /*
            r8 = 0
            int r6 = r10 + 1
            r7 = -1
            int[] r3 = createIntArray(r6, r7)
            r4 = 0
            r2 = 0
            r9[r8] = r8
            int r6 = r10 + 1
            int[] r1 = new int[r6]
            r0 = 1
        L_0x0011:
            int r6 = r9.length
            if (r0 >= r6) goto L_0x0028
            int r6 = r0 + -1
            r5 = r9[r6]
            int r6 = r4 + r5
            int r4 = r6 << 1
            r1[r0] = r4
            r6 = r9[r0]
            int r6 = r6 + r4
            int r2 = r6 + -1
            r3[r0] = r2
            int r0 = r0 + 1
            goto L_0x0011
        L_0x0028:
            r11[r8] = r1
            r6 = 1
            java.lang.Integer r7 = java.lang.Integer.valueOf(r2)
            r11[r6] = r7
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.neovisionaries.ws.client.Huffman.createMaxCodeValsFromCodeLen(int[], int, java.lang.Object[]):int[]");
    }

    private static int[] createSymsFromCodeVal(int[] codeLensFromSym, int[] codeValsFromCodeLen, int maxCodeVal) {
        int[] symsFromCodeVal = new int[(maxCodeVal + 1)];
        for (int sym = 0; sym < codeLensFromSym.length; sym++) {
            int codeLen = codeLensFromSym[sym];
            if (codeLen != 0) {
                int codeVal = codeValsFromCodeLen[codeLen];
                codeValsFromCodeLen[codeLen] = codeVal + 1;
                symsFromCodeVal[codeVal] = sym;
            }
        }
        return symsFromCodeVal;
    }

    public int readSym(ByteArray data, int[] bitIndex) throws FormatException {
        int codeVal;
        for (int codeLen = this.mMinCodeLen; codeLen <= this.mMaxCodeLen; codeLen++) {
            int maxCodeVal = this.mMaxCodeValsFromCodeLen[codeLen];
            if (maxCodeVal >= 0 && maxCodeVal >= (codeVal = data.getHuffmanBits(bitIndex[0], codeLen))) {
                int sym = this.mSymsFromCodeVal[codeVal];
                bitIndex[0] = bitIndex[0] + codeLen;
                return sym;
            }
        }
        throw new FormatException(String.format("[%s] Bad code at the bit index '%d'.", new Object[]{getClass().getSimpleName(), Integer.valueOf(bitIndex[0])}));
    }
}
