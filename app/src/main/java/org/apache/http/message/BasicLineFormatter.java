package org.apache.http.message;

import org.apache.http.Header;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.util.CharArrayBuffer;

@Deprecated
public class BasicLineFormatter implements LineFormatter {
    public static final BasicLineFormatter DEFAULT = null;

    public BasicLineFormatter() {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public CharArrayBuffer initBuffer(CharArrayBuffer buffer) {
        throw new RuntimeException("Stub!");
    }

    public static final String formatProtocolVersion(ProtocolVersion version, LineFormatter formatter) {
        throw new RuntimeException("Stub!");
    }

    public CharArrayBuffer appendProtocolVersion(CharArrayBuffer buffer, ProtocolVersion version) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public int estimateProtocolVersionLen(ProtocolVersion version) {
        throw new RuntimeException("Stub!");
    }

    public static final String formatRequestLine(RequestLine reqline, LineFormatter formatter) {
        throw new RuntimeException("Stub!");
    }

    public CharArrayBuffer formatRequestLine(CharArrayBuffer buffer, RequestLine reqline) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void doFormatRequestLine(CharArrayBuffer buffer, RequestLine reqline) {
        throw new RuntimeException("Stub!");
    }

    public static final String formatStatusLine(StatusLine statline, LineFormatter formatter) {
        throw new RuntimeException("Stub!");
    }

    public CharArrayBuffer formatStatusLine(CharArrayBuffer buffer, StatusLine statline) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void doFormatStatusLine(CharArrayBuffer buffer, StatusLine statline) {
        throw new RuntimeException("Stub!");
    }

    public static final String formatHeader(Header header, LineFormatter formatter) {
        throw new RuntimeException("Stub!");
    }

    public CharArrayBuffer formatHeader(CharArrayBuffer buffer, Header header) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void doFormatHeader(CharArrayBuffer buffer, Header header) {
        throw new RuntimeException("Stub!");
    }
}
