package com.android.internal.http.multipart;

import java.io.IOException;
import java.io.OutputStream;

public class StringPart extends PartBase {
    public static final String DEFAULT_CHARSET = "US-ASCII";
    public static final String DEFAULT_CONTENT_TYPE = "text/plain";
    public static final String DEFAULT_TRANSFER_ENCODING = "8bit";

    public StringPart(String name, String value, String charset) {
        super((String) null, (String) null, (String) null, (String) null);
        throw new RuntimeException("Stub!");
    }

    public StringPart(String name, String value) {
        super((String) null, (String) null, (String) null, (String) null);
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void sendData(OutputStream out) throws IOException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public long lengthOfData() {
        throw new RuntimeException("Stub!");
    }

    public void setCharSet(String charSet) {
        throw new RuntimeException("Stub!");
    }
}
