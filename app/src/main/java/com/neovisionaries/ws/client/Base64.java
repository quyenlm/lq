package com.neovisionaries.ws.client;

import com.tencent.component.net.download.multiplex.http.MttRequest;

class Base64 {
    private static final byte[] INDEX_TABLE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, MttRequest.REQUEST_NORMAL, MttRequest.REQUEST_DIRECT, MttRequest.REQUEST_BROKER, MttRequest.REQUEST_FILE_DOWNLOAD, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};

    Base64() {
    }

    public static String encode(String data) {
        if (data == null) {
            return null;
        }
        return encode(Misc.getBytesUTF8(data));
    }

    public static String encode(byte[] data) {
        if (data == null) {
            return null;
        }
        int capacity = (((((data.length * 8) + 5) / 6) + 3) / 4) * 4;
        StringBuilder builder = new StringBuilder(capacity);
        int bitIndex = 0;
        while (true) {
            int bits = extractBits(data, bitIndex);
            if (bits < 0) {
                break;
            }
            builder.append((char) INDEX_TABLE[bits]);
            bitIndex += 6;
        }
        for (int i = builder.length(); i < capacity; i++) {
            builder.append('=');
        }
        return builder.toString();
    }

    private static int extractBits(byte[] data, int bitIndex) {
        byte nextByte;
        int byteIndex = bitIndex / 8;
        if (data.length <= byteIndex) {
            return -1;
        }
        if (data.length - 1 == byteIndex) {
            nextByte = 0;
        } else {
            nextByte = data[byteIndex + 1];
        }
        switch ((bitIndex % 24) / 6) {
            case 0:
                return (data[byteIndex] >> 2) & 63;
            case 1:
                return ((data[byteIndex] << 4) & 48) | ((nextByte >> 4) & 15);
            case 2:
                return ((data[byteIndex] << 2) & 60) | ((nextByte >> 6) & 3);
            case 3:
                return data[byteIndex] & 63;
            default:
                return 0;
        }
    }
}
