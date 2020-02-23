package org.apache.http.message;

import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.util.CharArrayBuffer;

@Deprecated
public class BasicHeaderValueParser implements HeaderValueParser {
    public static final BasicHeaderValueParser DEFAULT = null;

    public BasicHeaderValueParser() {
        throw new RuntimeException("Stub!");
    }

    public static final HeaderElement[] parseElements(String value, HeaderValueParser parser) throws ParseException {
        throw new RuntimeException("Stub!");
    }

    public HeaderElement[] parseElements(CharArrayBuffer buffer, ParserCursor cursor) {
        throw new RuntimeException("Stub!");
    }

    public static final HeaderElement parseHeaderElement(String value, HeaderValueParser parser) throws ParseException {
        throw new RuntimeException("Stub!");
    }

    public HeaderElement parseHeaderElement(CharArrayBuffer buffer, ParserCursor cursor) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public HeaderElement createHeaderElement(String name, String value, NameValuePair[] params) {
        throw new RuntimeException("Stub!");
    }

    public static final NameValuePair[] parseParameters(String value, HeaderValueParser parser) throws ParseException {
        throw new RuntimeException("Stub!");
    }

    public NameValuePair[] parseParameters(CharArrayBuffer buffer, ParserCursor cursor) {
        throw new RuntimeException("Stub!");
    }

    public static final NameValuePair parseNameValuePair(String value, HeaderValueParser parser) throws ParseException {
        throw new RuntimeException("Stub!");
    }

    public NameValuePair parseNameValuePair(CharArrayBuffer buffer, ParserCursor cursor) {
        throw new RuntimeException("Stub!");
    }

    public NameValuePair parseNameValuePair(CharArrayBuffer buffer, ParserCursor cursor, char[] delimiters) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public NameValuePair createNameValuePair(String name, String value) {
        throw new RuntimeException("Stub!");
    }
}
