package org.apache.http.impl;

import java.util.Locale;
import org.apache.http.ReasonPhraseCatalog;

@Deprecated
public class EnglishReasonPhraseCatalog implements ReasonPhraseCatalog {
    public static final EnglishReasonPhraseCatalog INSTANCE = null;

    protected EnglishReasonPhraseCatalog() {
        throw new RuntimeException("Stub!");
    }

    public String getReason(int status, Locale loc) {
        throw new RuntimeException("Stub!");
    }
}
