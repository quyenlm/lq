package org.apache.http.message;

import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.util.CharArrayBuffer;

@Deprecated
public class BasicHeaderValueFormatter implements HeaderValueFormatter {
    public static final BasicHeaderValueFormatter DEFAULT = null;
    public static final String SEPARATORS = " ;,:@()<>\\\"/[]?={}\t";
    public static final String UNSAFE_CHARS = "\"\\";

    public BasicHeaderValueFormatter() {
        throw new RuntimeException("Stub!");
    }

    public static final String formatElements(HeaderElement[] elems, boolean quote, HeaderValueFormatter formatter) {
        throw new RuntimeException("Stub!");
    }

    public CharArrayBuffer formatElements(CharArrayBuffer buffer, HeaderElement[] elems, boolean quote) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public int estimateElementsLen(HeaderElement[] elems) {
        throw new RuntimeException("Stub!");
    }

    public static final String formatHeaderElement(HeaderElement elem, boolean quote, HeaderValueFormatter formatter) {
        throw new RuntimeException("Stub!");
    }

    public CharArrayBuffer formatHeaderElement(CharArrayBuffer buffer, HeaderElement elem, boolean quote) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public int estimateHeaderElementLen(HeaderElement elem) {
        throw new RuntimeException("Stub!");
    }

    public static final String formatParameters(NameValuePair[] nvps, boolean quote, HeaderValueFormatter formatter) {
        throw new RuntimeException("Stub!");
    }

    public CharArrayBuffer formatParameters(CharArrayBuffer buffer, NameValuePair[] nvps, boolean quote) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public int estimateParametersLen(NameValuePair[] nvps) {
        throw new RuntimeException("Stub!");
    }

    public static final String formatNameValuePair(NameValuePair nvp, boolean quote, HeaderValueFormatter formatter) {
        throw new RuntimeException("Stub!");
    }

    public CharArrayBuffer formatNameValuePair(CharArrayBuffer buffer, NameValuePair nvp, boolean quote) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public int estimateNameValuePairLen(NameValuePair nvp) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void doFormatValue(CharArrayBuffer buffer, String value, boolean quote) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public boolean isSeparator(char ch) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public boolean isUnsafe(char ch) {
        throw new RuntimeException("Stub!");
    }
}
