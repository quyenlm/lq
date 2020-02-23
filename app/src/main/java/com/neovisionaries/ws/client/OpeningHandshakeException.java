package com.neovisionaries.ws.client;

import java.util.List;
import java.util.Map;

public class OpeningHandshakeException extends WebSocketException {
    private static final long serialVersionUID = 1;
    private final byte[] mBody;
    private final Map<String, List<String>> mHeaders;
    private final StatusLine mStatusLine;

    OpeningHandshakeException(WebSocketError error, String message, StatusLine statusLine, Map<String, List<String>> headers) {
        this(error, message, statusLine, headers, (byte[]) null);
    }

    OpeningHandshakeException(WebSocketError error, String message, StatusLine statusLine, Map<String, List<String>> headers, byte[] body) {
        super(error, message);
        this.mStatusLine = statusLine;
        this.mHeaders = headers;
        this.mBody = body;
    }

    public StatusLine getStatusLine() {
        return this.mStatusLine;
    }

    public Map<String, List<String>> getHeaders() {
        return this.mHeaders;
    }

    public byte[] getBody() {
        return this.mBody;
    }
}
