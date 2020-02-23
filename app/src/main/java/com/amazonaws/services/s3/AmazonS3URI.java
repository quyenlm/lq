package com.amazonaws.services.s3;

import com.appsflyer.share.Constants;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AmazonS3URI {
    private static final Pattern ENDPOINT_PATTERN = Pattern.compile("^(.+\\.)?s3[.-]([a-z0-9-]+)\\.");
    private final String bucket;
    private final boolean isPathStyle;
    private final String key;
    private final String region;
    private final URI uri;

    public AmazonS3URI(String str) {
        this(URI.create(str));
    }

    public AmazonS3URI(URI uri2) {
        if (uri2 == null) {
            throw new IllegalArgumentException("uri cannot be null");
        }
        this.uri = uri2;
        String host = uri2.getHost();
        if (host == null) {
            throw new IllegalArgumentException("Invalid S3 URI: no hostname: " + uri2);
        }
        Matcher matcher = ENDPOINT_PATTERN.matcher(host);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid S3 URI: hostname does not appear to be a valid S3 endpoint: " + uri2);
        }
        String prefix = matcher.group(1);
        if (prefix == null || prefix.isEmpty()) {
            this.isPathStyle = true;
            String path = uri2.getRawPath();
            if (Constants.URL_PATH_DELIMITER.equals(path)) {
                this.bucket = null;
                this.key = null;
            } else {
                int index = path.indexOf(47, 1);
                if (index == -1) {
                    this.bucket = decode(path.substring(1));
                    this.key = null;
                } else if (index == path.length() - 1) {
                    this.bucket = decode(path.substring(1, index));
                    this.key = null;
                } else {
                    this.bucket = decode(path.substring(1, index));
                    this.key = decode(path.substring(index + 1));
                }
            }
        } else {
            this.isPathStyle = false;
            this.bucket = prefix.substring(0, prefix.length() - 1);
            if (Constants.URL_PATH_DELIMITER.equals(uri2.getPath())) {
                this.key = null;
            } else {
                this.key = uri2.getPath().substring(1);
            }
        }
        if ("amazonaws".equals(matcher.group(2))) {
            this.region = null;
        } else {
            this.region = matcher.group(2);
        }
    }

    public URI getURI() {
        return this.uri;
    }

    public boolean isPathStyle() {
        return this.isPathStyle;
    }

    public String getBucket() {
        return this.bucket;
    }

    public String getKey() {
        return this.key;
    }

    public String getRegion() {
        return this.region;
    }

    public String toString() {
        return this.uri.toString();
    }

    private static String decode(String str) {
        if (str == null) {
            return null;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '%') {
                return decode(str, i);
            }
        }
        return str;
    }

    private static String decode(String str, int firstPercent) {
        StringBuilder builder = new StringBuilder();
        builder.append(str.substring(0, firstPercent));
        appendDecoded(builder, str, firstPercent);
        int i = firstPercent + 3;
        while (i < str.length()) {
            if (str.charAt(i) == '%') {
                appendDecoded(builder, str, i);
                i += 2;
            } else {
                builder.append(str.charAt(i));
            }
            i++;
        }
        return builder.toString();
    }

    private static void appendDecoded(StringBuilder builder, String str, int index) {
        if (index > str.length() - 3) {
            throw new IllegalStateException("Invalid percent-encoded string:\"" + str + "\".");
        }
        builder.append((char) ((fromHex(str.charAt(index + 1)) << 4) | fromHex(str.charAt(index + 2))));
    }

    private static int fromHex(char c) {
        if (c < '0') {
            throw new IllegalStateException("Invalid percent-encoded string: bad character '" + c + "' in escape sequence.");
        } else if (c <= '9') {
            return c - '0';
        } else {
            if (c < 'A') {
                throw new IllegalStateException("Invalid percent-encoded string: bad character '" + c + "' in escape sequence.");
            } else if (c <= 'F') {
                return (c - 'A') + 10;
            } else {
                if (c < 'a') {
                    throw new IllegalStateException("Invalid percent-encoded string: bad character '" + c + "' in escape sequence.");
                } else if (c <= 'f') {
                    return (c - 'a') + 10;
                } else {
                    throw new IllegalStateException("Invalid percent-encoded string: bad character '" + c + "' in escape sequence.");
                }
            }
        }
    }
}
