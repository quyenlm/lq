package com.amazonaws.services.s3.model;

import com.amazonaws.services.s3.internal.crypto.CryptoRuntime;
import java.security.Provider;

public class CryptoConfiguration {
    private CryptoMode cryptoMode;
    private Provider cryptoProvider;
    private CryptoStorageMode storageMode;

    public CryptoConfiguration() {
        this(CryptoMode.EncryptionOnly);
    }

    public CryptoConfiguration(CryptoMode cryptoMode2) {
        check(cryptoMode2);
        this.storageMode = CryptoStorageMode.ObjectMetadata;
        this.cryptoProvider = null;
        this.cryptoMode = cryptoMode2;
    }

    public void setStorageMode(CryptoStorageMode storageMode2) {
        this.storageMode = storageMode2;
    }

    public CryptoConfiguration withStorageMode(CryptoStorageMode storageMode2) {
        this.storageMode = storageMode2;
        return this;
    }

    public CryptoStorageMode getStorageMode() {
        return this.storageMode;
    }

    public void setCryptoProvider(Provider cryptoProvider2) {
        this.cryptoProvider = cryptoProvider2;
    }

    public CryptoConfiguration withCryptoProvider(Provider cryptoProvider2) {
        this.cryptoProvider = cryptoProvider2;
        return this;
    }

    public Provider getCryptoProvider() {
        return this.cryptoProvider;
    }

    public CryptoMode getCryptoMode() {
        return this.cryptoMode;
    }

    public void setCryptoMode(CryptoMode cryptoMode2) {
        check(cryptoMode2);
        this.cryptoMode = cryptoMode2;
    }

    public CryptoConfiguration withCryptoMode(CryptoMode cryptoMode2) {
        check(cryptoMode2);
        this.cryptoMode = cryptoMode2;
        return this;
    }

    private void check(CryptoMode cryptoMode2) {
        if (cryptoMode2 == CryptoMode.AuthenticatedEncryption || cryptoMode2 == CryptoMode.StrictAuthenticatedEncryption) {
            if (!CryptoRuntime.isBouncyCastleAvailable()) {
                CryptoRuntime.enableBouncyCastle();
                if (!CryptoRuntime.isBouncyCastleAvailable()) {
                    throw new UnsupportedOperationException("The Bouncy castle library jar is required on the classpath to enable authenticated encryption");
                }
            }
            if (!CryptoRuntime.isAesGcmAvailable()) {
                throw new UnsupportedOperationException("More recent version of the Bouncy castle library is required to enable authenticated encryption");
            }
        }
    }
}
