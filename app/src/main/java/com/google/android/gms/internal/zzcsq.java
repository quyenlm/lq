package com.google.android.gms.internal;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Pattern;

final class zzcsq {
    private static final Pattern zzbCf = Pattern.compile("[.]");
    private static final Inet4Address zzbCg = ((Inet4Address) zzeN("127.0.0.1"));
    private static final Inet4Address zzbCh = ((Inet4Address) zzeN("0.0.0.0"));

    static String zza(InetAddress inetAddress) {
        int i;
        if (inetAddress instanceof Inet4Address) {
            return inetAddress.getHostAddress();
        }
        byte[] address = inetAddress.getAddress();
        int[] iArr = new int[8];
        for (int i2 = 0; i2 < 8; i2++) {
            iArr[i2] = ((address[i2 * 2] & 255) << 8) | 0 | (address[(i2 * 2) + 1] & 255);
        }
        int i3 = 0;
        int i4 = -1;
        int i5 = -1;
        int i6 = -1;
        while (i3 < 9) {
            if (i3 >= 8 || iArr[i3] != 0) {
                if (i4 >= 0) {
                    int i7 = i3 - i4;
                    if (i7 <= i5) {
                        i7 = i5;
                        i4 = i6;
                    }
                    i = -1;
                    i5 = i7;
                    i6 = i4;
                }
                i = i4;
            } else {
                if (i4 < 0) {
                    i = i3;
                }
                i = i4;
            }
            i3++;
            i4 = i;
        }
        if (i5 >= 2) {
            Arrays.fill(iArr, i6, i6 + i5, -1);
        }
        StringBuilder sb = new StringBuilder(39);
        int i8 = 0;
        boolean z = false;
        while (i8 < 8) {
            boolean z2 = iArr[i8] >= 0;
            if (z2) {
                if (z) {
                    sb.append(':');
                }
                sb.append(Integer.toHexString(iArr[i8]));
            } else if (i8 == 0 || z) {
                sb.append("::");
            }
            i8++;
            z = z2;
        }
        return sb.toString();
    }

    static boolean zzeI(String str) {
        return zzeJ(str) != null;
    }

    private static byte[] zzeJ(String str) {
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '.') {
                z = true;
            } else if (charAt == ':') {
                if (z) {
                    return null;
                }
                z2 = true;
            } else if (Character.digit(charAt, 16) == -1) {
                return null;
            }
        }
        if (z2) {
            if (z) {
                int lastIndexOf = str.lastIndexOf(58);
                String substring = str.substring(0, lastIndexOf + 1);
                byte[] zzeK = zzeK(str.substring(lastIndexOf + 1));
                if (zzeK == null) {
                    str = null;
                } else {
                    String hexString = Integer.toHexString(((zzeK[0] & 255) << 8) | (zzeK[1] & 255));
                    String hexString2 = Integer.toHexString((zzeK[3] & 255) | ((zzeK[2] & 255) << 8));
                    str = new StringBuilder(String.valueOf(substring).length() + 1 + String.valueOf(hexString).length() + String.valueOf(hexString2).length()).append(substring).append(hexString).append(":").append(hexString2).toString();
                }
                if (str == null) {
                    return null;
                }
            }
            return zzeL(str);
        } else if (z) {
            return zzeK(str);
        } else {
            return null;
        }
    }

    private static byte[] zzeK(String str) {
        byte[] bArr = new byte[4];
        try {
            String[] split = zzbCf.split(str, 4);
            int length = split.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                String str2 = split[i];
                int i3 = i2 + 1;
                int parseInt = Integer.parseInt(str2);
                if (parseInt > 255 || (str2.startsWith("0") && str2.length() > 1)) {
                    throw new NumberFormatException();
                }
                bArr[i2] = (byte) parseInt;
                i++;
                i2 = i3;
            }
            if (i2 != 4) {
                return null;
            }
            return bArr;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static byte[] zzeL(String str) {
        int i;
        int i2;
        int i3;
        String[] split = str.split(":", 10);
        if (split.length < 3 || split.length > 9) {
            return null;
        }
        int i4 = -1;
        for (int i5 = 1; i5 < split.length - 1; i5++) {
            if (split[i5].length() == 0) {
                if (i4 >= 0) {
                    return null;
                }
                i4 = i5;
            }
        }
        if (i4 >= 0) {
            i2 = (split.length - i4) - 1;
            if (split[0].length() == 0) {
                i3 = i4 - 1;
                if (i3 != 0) {
                    return null;
                }
            } else {
                i3 = i4;
            }
            if (split[split.length - 1].length() == 0 && i2 - 1 != 0) {
                return null;
            }
            i = i3;
        } else {
            i2 = 0;
            i = split.length;
        }
        int i6 = 8 - (i + i2);
        if (i4 < 0 ? i6 != 0 : i6 <= 0) {
            return null;
        }
        ByteBuffer allocate = ByteBuffer.allocate(16);
        int i7 = 0;
        while (i7 < i) {
            try {
                allocate.putShort(zzeM(split[i7]));
                i7++;
            } catch (NumberFormatException e) {
                return null;
            }
        }
        for (int i8 = 0; i8 < i6; i8++) {
            allocate.putShort(0);
        }
        for (int i9 = i2; i9 > 0; i9--) {
            allocate.putShort(zzeM(split[split.length - i9]));
        }
        return allocate.array();
    }

    private static short zzeM(String str) {
        int parseInt = Integer.parseInt(str, 16);
        if (parseInt <= 65535) {
            return (short) parseInt;
        }
        throw new NumberFormatException();
    }

    static InetAddress zzeN(String str) {
        byte[] zzeJ = zzeJ(str);
        if (zzeJ != null) {
            return zzq(zzeJ);
        }
        throw new IllegalArgumentException(String.format(Locale.ROOT, "'%s' is not an IP string literal.", new Object[]{str}));
    }

    private static InetAddress zzq(byte[] bArr) {
        try {
            return InetAddress.getByAddress(bArr);
        } catch (UnknownHostException e) {
            throw new AssertionError(e);
        }
    }
}
