package com.neovisionaries.ws.client;

class FixedDistanceHuffman extends Huffman {
    private static final FixedDistanceHuffman INSTANCE = new FixedDistanceHuffman();

    private FixedDistanceHuffman() {
        super(buildCodeLensFromSym());
    }

    private static int[] buildCodeLensFromSym() {
        int[] codeLengths = new int[32];
        for (int symbol = 0; symbol < 32; symbol++) {
            codeLengths[symbol] = 5;
        }
        return codeLengths;
    }

    public static FixedDistanceHuffman getInstance() {
        return INSTANCE;
    }
}
