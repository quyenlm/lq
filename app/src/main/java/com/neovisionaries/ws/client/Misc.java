package com.neovisionaries.ws.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Misc {
    private static final SecureRandom sRandom = new SecureRandom();

    private Misc() {
    }

    public static byte[] getBytesUTF8(String string) {
        if (string == null) {
            return null;
        }
        try {
            return string.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String toStringUTF8(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return toStringUTF8(bytes, 0, bytes.length);
    }

    public static String toStringUTF8(byte[] bytes, int offset, int length) {
        if (bytes == null) {
            return null;
        }
        try {
            return new String(bytes, offset, length, "UTF-8");
        } catch (UnsupportedEncodingException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    public static byte[] nextBytes(byte[] buffer) {
        sRandom.nextBytes(buffer);
        return buffer;
    }

    public static byte[] nextBytes(int nBytes) {
        return nextBytes(new byte[nBytes]);
    }

    public static String toOpcodeName(int opcode) {
        switch (opcode) {
            case 0:
                return "CONTINUATION";
            case 1:
                return "TEXT";
            case 2:
                return "BINARY";
            case 8:
                return "CLOSE";
            case 9:
                return "PING";
            case 10:
                return "PONG";
            default:
                if (1 <= opcode && opcode <= 7) {
                    return String.format("DATA(0x%X)", new Object[]{Integer.valueOf(opcode)});
                } else if (8 > opcode || opcode > 15) {
                    return String.format("0x%X", new Object[]{Integer.valueOf(opcode)});
                } else {
                    return String.format("CONTROL(0x%X)", new Object[]{Integer.valueOf(opcode)});
                }
        }
    }

    public static String readLine(InputStream in, String charset) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (true) {
            int b = in.read();
            if (b != -1) {
                if (b != 10) {
                    if (b == 13) {
                        int b2 = in.read();
                        if (b2 != -1) {
                            if (b2 == 10) {
                                break;
                            }
                            baos.write(b);
                            baos.write(b2);
                        } else {
                            baos.write(b);
                            break;
                        }
                    } else {
                        baos.write(b);
                    }
                } else {
                    break;
                }
            } else if (baos.size() == 0) {
                return null;
            }
        }
        return baos.toString(charset);
    }

    public static int min(int[] values) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < values.length; i++) {
            if (values[i] < min) {
                min = values[i];
            }
        }
        return min;
    }

    public static int max(int[] values) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < values.length; i++) {
            if (max < values[i]) {
                max = values[i];
            }
        }
        return max;
    }

    public static String join(Collection<?> values, String delimiter) {
        StringBuilder builder = new StringBuilder();
        join(builder, values, delimiter);
        return builder.toString();
    }

    private static void join(StringBuilder builder, Collection<?> values, String delimiter) {
        boolean first = true;
        for (Object value : values) {
            if (first) {
                first = false;
            } else {
                builder.append(delimiter);
            }
            builder.append(value.toString());
        }
    }

    public static String extractHost(URI uri) {
        String host = uri.getHost();
        if (host != null) {
            String str = host;
            return host;
        }
        String host2 = extractHostFromAuthorityPart(uri.getRawAuthority());
        if (host2 != null) {
            String str2 = host2;
            return host2;
        }
        String str3 = host2;
        return extractHostFromEntireUri(uri.toString());
    }

    static String extractHostFromAuthorityPart(String authority) {
        Matcher matcher;
        if (authority == null || (matcher = Pattern.compile("^(.*@)?([^:]+)(:\\d+)?$").matcher(authority)) == null || !matcher.matches()) {
            return null;
        }
        return matcher.group(2);
    }

    static String extractHostFromEntireUri(String uri) {
        Matcher matcher;
        if (uri == null || (matcher = Pattern.compile("^\\w+://([^@/]*@)?([^:/]+)(:\\d+)?(/.*)?$").matcher(uri)) == null || !matcher.matches()) {
            return null;
        }
        return matcher.group(2);
    }
}
