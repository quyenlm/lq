package com.neovisionaries.ws.client;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.media.MediaRouter;
import com.tencent.imsdk.expansion.downloader.impl.DownloaderService;
import com.tencent.rtmp2.TXLiveConstants;
import com.tencent.smtt.sdk.TbsListener;

class DeflateUtil {
    private static int[] INDICES_FROM_CODE_LENGTH_ORDER = {16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15};

    DeflateUtil() {
    }

    public static void readDynamicTables(ByteArray input, int[] bitIndex, Huffman[] tables) throws FormatException {
        int hlit = input.readBits(bitIndex, 5) + 257;
        int hdist = input.readBits(bitIndex, 5) + 1;
        int hclen = input.readBits(bitIndex, 4) + 4;
        int[] codeLengthsFromCodeLengthValue = new int[19];
        for (int i = 0; i < hclen; i++) {
            codeLengthsFromCodeLengthValue[codeLengthOrderToIndex(i)] = (byte) input.readBits(bitIndex, 3);
        }
        Huffman codeLengthHuffman = new Huffman(codeLengthsFromCodeLengthValue);
        int[] codeLengthsFromLiteralLengthCode = new int[hlit];
        readCodeLengths(input, bitIndex, codeLengthsFromLiteralLengthCode, codeLengthHuffman);
        Huffman literalLengthHuffman = new Huffman(codeLengthsFromLiteralLengthCode);
        int[] codeLengthsFromDistanceCode = new int[hdist];
        readCodeLengths(input, bitIndex, codeLengthsFromDistanceCode, codeLengthHuffman);
        Huffman distanceHuffman = new Huffman(codeLengthsFromDistanceCode);
        tables[0] = literalLengthHuffman;
        tables[1] = distanceHuffman;
    }

    private static void readCodeLengths(ByteArray input, int[] bitIndex, int[] codeLengths, Huffman codeLengthHuffman) throws FormatException {
        int codeLength;
        int repeatCount;
        int i = 0;
        while (i < codeLengths.length) {
            int codeLength2 = codeLengthHuffman.readSym(input, bitIndex);
            if (codeLength2 < 0 || codeLength2 > 15) {
                switch (codeLength2) {
                    case 16:
                        codeLength = codeLengths[i - 1];
                        repeatCount = input.readBits(bitIndex, 2) + 3;
                        break;
                    case 17:
                        codeLength = 0;
                        repeatCount = input.readBits(bitIndex, 3) + 3;
                        break;
                    case 18:
                        codeLength = 0;
                        repeatCount = input.readBits(bitIndex, 7) + 11;
                        break;
                    default:
                        throw new FormatException(String.format("[%s] Bad code length '%d' at the bit index '%d'.", new Object[]{DeflateUtil.class.getSimpleName(), Integer.valueOf(codeLength2), bitIndex}));
                }
                for (int j = 0; j < repeatCount; j++) {
                    codeLengths[i + j] = codeLength;
                }
                i += repeatCount - 1;
            } else {
                codeLengths[i] = codeLength2;
            }
            i++;
        }
    }

    private static int codeLengthOrderToIndex(int order) {
        return INDICES_FROM_CODE_LENGTH_ORDER[order];
    }

    public static int readLength(ByteArray input, int[] bitIndex, int literalLength) throws FormatException {
        int baseValue;
        int nBits;
        switch (literalLength) {
            case 257:
            case MediaRouter.GlobalMediaRouter.CallbackHandler.MSG_ROUTE_REMOVED /*258*/:
            case MediaRouter.GlobalMediaRouter.CallbackHandler.MSG_ROUTE_CHANGED /*259*/:
            case MediaRouter.GlobalMediaRouter.CallbackHandler.MSG_ROUTE_VOLUME_CHANGED /*260*/:
            case MediaRouter.GlobalMediaRouter.CallbackHandler.MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED /*261*/:
            case MediaRouter.GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED /*262*/:
            case MediaRouter.GlobalMediaRouter.CallbackHandler.MSG_ROUTE_UNSELECTED /*263*/:
            case 264:
                return literalLength - 254;
            case 265:
                baseValue = 11;
                nBits = 1;
                break;
            case 266:
                baseValue = 13;
                nBits = 1;
                break;
            case 267:
                baseValue = 15;
                nBits = 1;
                break;
            case 268:
                baseValue = 17;
                nBits = 1;
                break;
            case 269:
                baseValue = 19;
                nBits = 2;
                break;
            case TXLiveConstants.RENDER_ROTATION_LANDSCAPE:
                baseValue = 23;
                nBits = 2;
                break;
            case 271:
                baseValue = 27;
                nBits = 2;
                break;
            case 272:
                baseValue = 31;
                nBits = 2;
                break;
            case 273:
                baseValue = 35;
                nBits = 3;
                break;
            case 274:
                baseValue = 43;
                nBits = 3;
                break;
            case 275:
                baseValue = 51;
                nBits = 3;
                break;
            case 276:
                baseValue = 59;
                nBits = 3;
                break;
            case 277:
                baseValue = 67;
                nBits = 4;
                break;
            case 278:
                baseValue = 83;
                nBits = 4;
                break;
            case 279:
                baseValue = 99;
                nBits = 4;
                break;
            case 280:
                baseValue = 115;
                nBits = 4;
                break;
            case 281:
                baseValue = 131;
                nBits = 5;
                break;
            case 282:
                baseValue = 163;
                nBits = 5;
                break;
            case 283:
                baseValue = DownloaderService.STATUS_WAITING_FOR_NETWORK;
                nBits = 5;
                break;
            case 284:
                baseValue = TbsListener.ErrorCode.HOST_CONTEXT_IS_NULL;
                nBits = 5;
                break;
            case 285:
                return MediaRouter.GlobalMediaRouter.CallbackHandler.MSG_ROUTE_REMOVED;
            default:
                throw new FormatException(String.format("[%s] Bad literal/length code '%d' at the bit index '%d'.", new Object[]{DeflateUtil.class.getSimpleName(), Integer.valueOf(literalLength), Integer.valueOf(bitIndex[0])}));
        }
        return baseValue + input.readBits(bitIndex, nBits);
    }

    public static int readDistance(ByteArray input, int[] bitIndex, Huffman distanceHuffman) throws FormatException {
        int baseValue;
        int nBits;
        int code = distanceHuffman.readSym(input, bitIndex);
        switch (code) {
            case 0:
            case 1:
            case 2:
            case 3:
                return code + 1;
            case 4:
                baseValue = 5;
                nBits = 1;
                break;
            case 5:
                baseValue = 7;
                nBits = 1;
                break;
            case 6:
                baseValue = 9;
                nBits = 2;
                break;
            case 7:
                baseValue = 13;
                nBits = 2;
                break;
            case 8:
                baseValue = 17;
                nBits = 3;
                break;
            case 9:
                baseValue = 25;
                nBits = 3;
                break;
            case 10:
                baseValue = 33;
                nBits = 4;
                break;
            case 11:
                baseValue = 49;
                nBits = 4;
                break;
            case 12:
                baseValue = 65;
                nBits = 5;
                break;
            case 13:
                baseValue = 97;
                nBits = 5;
                break;
            case 14:
                baseValue = 129;
                nBits = 6;
                break;
            case 15:
                baseValue = DownloaderService.STATUS_PAUSED_BY_APP;
                nBits = 6;
                break;
            case 16:
                baseValue = 257;
                nBits = 7;
                break;
            case 17:
                baseValue = 385;
                nBits = 7;
                break;
            case 18:
                baseValue = 513;
                nBits = 8;
                break;
            case 19:
                baseValue = 769;
                nBits = 8;
                break;
            case 20:
                baseValue = 1025;
                nBits = 9;
                break;
            case 21:
                baseValue = 1537;
                nBits = 9;
                break;
            case 22:
                baseValue = 2049;
                nBits = 10;
                break;
            case 23:
                baseValue = 3073;
                nBits = 10;
                break;
            case 24:
                baseValue = FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
                nBits = 11;
                break;
            case 25:
                baseValue = 6145;
                nBits = 11;
                break;
            case 26:
                baseValue = 8193;
                nBits = 12;
                break;
            case 27:
                baseValue = 12289;
                nBits = 12;
                break;
            case 28:
                baseValue = 16385;
                nBits = 13;
                break;
            case 29:
                baseValue = 24577;
                nBits = 13;
                break;
            default:
                throw new FormatException(String.format("[%s] Bad distance code '%d' at the bit index '%d'.", new Object[]{DeflateUtil.class.getSimpleName(), Integer.valueOf(code), Integer.valueOf(bitIndex[0])}));
        }
        return baseValue + input.readBits(bitIndex, nBits);
    }
}
