package com.amazonaws.services.s3.internal;

import com.amazonaws.services.s3.internal.crypto.CipherFactory;
import java.io.FilterInputStream;
import java.io.InputStream;
import javax.crypto.CipherInputStream;

public class RepeatableCipherInputStream extends AbstractRepeatableCipherInputStream<CipherFactory> {
    public RepeatableCipherInputStream(InputStream input, CipherFactory cipherFactory) {
        super(input, newCipherInputStream(input, cipherFactory), cipherFactory);
    }

    /* access modifiers changed from: protected */
    public FilterInputStream createCipherInputStream(InputStream unencryptedDataStream, CipherFactory cipherFactory) {
        return newCipherInputStream(unencryptedDataStream, cipherFactory);
    }

    private static FilterInputStream newCipherInputStream(InputStream unencryptedDataStream, CipherFactory cipherFactory) {
        return new CipherInputStream(unencryptedDataStream, cipherFactory.createCipher());
    }
}
