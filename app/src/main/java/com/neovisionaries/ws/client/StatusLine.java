package com.neovisionaries.ws.client;

public class StatusLine {
    private final String mHttpVersion;
    private final String mReasonPhrase;
    private final int mStatusCode;
    private final String mString;

    StatusLine(String line) {
        String[] elements = line.split(" +", 3);
        if (elements.length < 2) {
            throw new IllegalArgumentException();
        }
        this.mHttpVersion = elements[0];
        this.mStatusCode = Integer.parseInt(elements[1]);
        this.mReasonPhrase = elements.length == 3 ? elements[2] : null;
        this.mString = line;
    }

    public String getHttpVersion() {
        return this.mHttpVersion;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public String getReasonPhrase() {
        return this.mReasonPhrase;
    }

    public String toString() {
        return this.mString;
    }
}
