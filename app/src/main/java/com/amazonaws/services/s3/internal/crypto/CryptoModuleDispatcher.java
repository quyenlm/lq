package com.amazonaws.services.s3.internal.crypto;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.internal.S3Direct;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.CopyPartRequest;
import com.amazonaws.services.s3.model.CopyPartResult;
import com.amazonaws.services.s3.model.CryptoConfiguration;
import com.amazonaws.services.s3.model.CryptoMode;
import com.amazonaws.services.s3.model.EncryptionMaterialsProvider;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;
import java.io.File;

public class CryptoModuleDispatcher extends S3CryptoModule<MultipartUploadContext> {
    private final S3CryptoModuleAE ae;
    private final CryptoMode defaultCryptoMode;
    private final S3CryptoModuleEO eo;

    public CryptoModuleDispatcher(S3Direct s3, AWSCredentialsProvider credentialsProvider, EncryptionMaterialsProvider encryptionMaterialsProvider, ClientConfiguration clientConfig, CryptoConfiguration cryptoConfig) {
        CryptoMode cryptoMode = cryptoConfig.getCryptoMode();
        this.defaultCryptoMode = cryptoMode == null ? CryptoMode.EncryptionOnly : cryptoMode;
        switch (this.defaultCryptoMode) {
            case StrictAuthenticatedEncryption:
                this.ae = new S3CryptoModuleAEStrict(s3, credentialsProvider, encryptionMaterialsProvider, clientConfig, cryptoConfig);
                this.eo = null;
                return;
            case AuthenticatedEncryption:
                this.ae = new S3CryptoModuleAE(s3, credentialsProvider, encryptionMaterialsProvider, clientConfig, cryptoConfig);
                this.eo = null;
                return;
            default:
                this.eo = new S3CryptoModuleEO(s3, credentialsProvider, encryptionMaterialsProvider, clientConfig, cryptoConfig);
                this.ae = new S3CryptoModuleAE(s3, credentialsProvider, encryptionMaterialsProvider, clientConfig, cryptoConfig);
                return;
        }
    }

    public PutObjectResult putObjectSecurely(PutObjectRequest putObjectRequest) throws AmazonClientException, AmazonServiceException {
        if (this.defaultCryptoMode == CryptoMode.EncryptionOnly) {
            return this.eo.putObjectSecurely(putObjectRequest);
        }
        return this.ae.putObjectSecurely(putObjectRequest);
    }

    public S3Object getObjectSecurely(GetObjectRequest req) throws AmazonClientException, AmazonServiceException {
        return this.ae.getObjectSecurely(req);
    }

    public ObjectMetadata getObjectSecurely(GetObjectRequest req, File destinationFile) throws AmazonClientException, AmazonServiceException {
        return this.ae.getObjectSecurely(req, destinationFile);
    }

    public CompleteMultipartUploadResult completeMultipartUploadSecurely(CompleteMultipartUploadRequest req) throws AmazonClientException, AmazonServiceException {
        if (this.defaultCryptoMode == CryptoMode.EncryptionOnly) {
            return this.eo.completeMultipartUploadSecurely(req);
        }
        return this.ae.completeMultipartUploadSecurely(req);
    }

    public void abortMultipartUploadSecurely(AbortMultipartUploadRequest req) {
        if (this.defaultCryptoMode == CryptoMode.EncryptionOnly) {
            this.eo.abortMultipartUploadSecurely(req);
        } else {
            this.ae.abortMultipartUploadSecurely(req);
        }
    }

    public InitiateMultipartUploadResult initiateMultipartUploadSecurely(InitiateMultipartUploadRequest req) throws AmazonClientException, AmazonServiceException {
        if (this.defaultCryptoMode == CryptoMode.EncryptionOnly) {
            return this.eo.initiateMultipartUploadSecurely(req);
        }
        return this.ae.initiateMultipartUploadSecurely(req);
    }

    public UploadPartResult uploadPartSecurely(UploadPartRequest req) throws AmazonClientException, AmazonServiceException {
        if (this.defaultCryptoMode == CryptoMode.EncryptionOnly) {
            return this.eo.uploadPartSecurely(req);
        }
        return this.ae.uploadPartSecurely(req);
    }

    public CopyPartResult copyPartSecurely(CopyPartRequest req) {
        if (this.defaultCryptoMode == CryptoMode.EncryptionOnly) {
            return this.eo.copyPartSecurely(req);
        }
        return this.ae.copyPartSecurely(req);
    }
}
