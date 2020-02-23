package com.subao.common.n;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.amazonaws.services.s3.internal.Constants;
import java.io.Reader;
import java.util.List;

/* compiled from: StringUtils */
public class g {
    @NonNull
    public static String a(@Nullable String str) {
        return str == null ? "" : str;
    }

    public static boolean a(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == charSequence2) {
            return true;
        }
        if (charSequence == null || charSequence2 == null) {
            return false;
        }
        return charSequence.equals(charSequence2);
    }

    private static char a(int i, int i2) {
        if (i < 10) {
            return (char) (i + 48);
        }
        return (char) ((i - 10) + i2);
    }

    public static String a(byte[] bArr, int i, int i2, boolean z) {
        if (bArr == null || i >= i2 || bArr.length == 0 || i >= bArr.length) {
            return "";
        }
        return a(new StringBuilder(bArr.length << 1), bArr, i, i2, z ? 'A' : 'a').toString();
    }

    public static String a(byte[] bArr, boolean z) {
        if (bArr == null || bArr.length == 0) {
            return "";
        }
        return a(bArr, 0, bArr.length, z);
    }

    private static StringBuilder a(StringBuilder sb, byte[] bArr, int i, int i2, char c) {
        while (i < i2) {
            byte b = bArr[i];
            sb.append(a((b >> 4) & 15, (int) c));
            sb.append(a((int) b & 15, (int) c));
            i++;
        }
        return sb;
    }

    public static int a(Reader reader, List<String> list, char c) {
        int size = list.size();
        StringBuilder sb = new StringBuilder(32);
        while (true) {
            int read = reader.read();
            if (read < 0) {
                break;
            }
            char c2 = (char) read;
            if (c2 == '\\') {
                int read2 = reader.read();
                if (read2 < 0) {
                    sb.append(c2);
                    break;
                }
                char c3 = (char) read2;
                if (!(c3 == '\\' || c3 == c)) {
                    sb.append('\\');
                }
                sb.append(c3);
            } else if (c2 != c) {
                sb.append(c2);
            } else if (sb.length() > 0) {
                list.add(sb.toString());
                sb.delete(0, sb.length());
            }
        }
        if (sb.length() > 0) {
            list.add(sb.toString());
        }
        return list.size() - size;
    }

    public static int a(Reader reader, List<String> list) {
        return a(reader, list, ',');
    }

    public static String a(Object obj) {
        return obj == null ? Constants.NULL_VERSION_ID : obj.toString();
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return Constants.NULL_VERSION_ID;
        }
        int length = bArr.length;
        int min = Math.min(8, length);
        StringBuilder sb = new StringBuilder(128);
        sb.append('[');
        for (int i = 0; i < min; i++) {
            sb.append("0x");
            a(sb, bArr, i, i + 1, 'A');
            if (i >= min - 1) {
                break;
            }
            sb.append(", ");
        }
        if (min < length) {
            sb.append(", ... (Total ").append(length).append(" bytes)");
        }
        sb.append(']');
        return sb.toString();
    }
}
