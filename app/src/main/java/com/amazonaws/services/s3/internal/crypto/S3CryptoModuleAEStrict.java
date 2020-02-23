package com.amazonaws.services.s3.internal.crypto;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.internal.S3Direct;
import com.amazonaws.services.s3.model.CryptoConfiguration;
import com.amazonaws.services.s3.model.EncryptionMaterialsProvider;

class S3CryptoModuleAEStrict extends S3CryptoModuleAE {
    S3CryptoModuleAEStrict(S3Direct s3, AWSCredentialsProvider credentialsProvider, EncryptionMaterialsProvider encryptionMaterialsProvider, ClientConfiguration clientConfig, CryptoConfiguration cryptoConfig) {
        super(s3, credentialsProvider, encryptionMaterialsProvider, clientConfig, cryptoConfig);
    }

    S3CryptoModuleAEStrict(S3Direct s3, EncryptionMaterialsProvider encryptionMaterialsProvider, CryptoConfiguration cryptoConfig) {
        super(s3, encryptionMaterialsProvider, cryptoConfig);
    }

    /* access modifiers changed from: protected */
    public final boolean isStrict() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void securityCheck(ContentCryptoMaterial cekMaterial, S3ObjectWrapper retrieved) {
        if (!ContentCryptoScheme.AES_GCM.equals(cekMaterial.getContentCryptoScheme())) {
            throw new SecurityException("S3 object [bucket: " + retrieved.getBucketName() + ", key: " + retrieved.getKey() + "] not encrypted using authenticated encryption");
        }
    }
}
