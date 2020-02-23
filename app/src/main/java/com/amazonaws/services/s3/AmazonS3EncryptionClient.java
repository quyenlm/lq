package com.amazonaws.services.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.services.s3.internal.S3Direct;
import com.amazonaws.services.s3.internal.crypto.CryptoModuleDispatcher;
import com.amazonaws.services.s3.internal.crypto.EncryptionUtils;
import com.amazonaws.services.s3.internal.crypto.S3CryptoModule;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.CopyPartRequest;
import com.amazonaws.services.s3.model.CopyPartResult;
import com.amazonaws.services.s3.model.CryptoConfiguration;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.EncryptionMaterials;
import com.amazonaws.services.s3.model.EncryptionMaterialsProvider;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.StaticEncryptionMaterialsProvider;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;
import com.amazonaws.util.VersionInfoUtils;
import com.appsflyer.share.Constants;
import java.io.File;

public class AmazonS3EncryptionClient extends AmazonS3Client {
    public static final String USER_AGENT = (AmazonS3EncryptionClient.class.getName() + Constants.URL_PATH_DELIMITER + VersionInfoUtils.getVersion());
    private final S3CryptoModule<?> crypto;

    public AmazonS3EncryptionClient(EncryptionMaterials encryptionMaterials) {
        this((EncryptionMaterialsProvider) new StaticEncryptionMaterialsProvider(encryptionMaterials));
    }

    public AmazonS3EncryptionClient(EncryptionMaterialsProvider encryptionMaterialsProvider) {
        this((AWSCredentialsProvider) null, encryptionMaterialsProvider, new ClientConfiguration(), new CryptoConfiguration());
    }

    public AmazonS3EncryptionClient(EncryptionMaterials encryptionMaterials, CryptoConfiguration cryptoConfig) {
        this((EncryptionMaterialsProvider) new StaticEncryptionMaterialsProvider(encryptionMaterials), cryptoConfig);
    }

    public AmazonS3EncryptionClient(EncryptionMaterialsProvider encryptionMaterialsProvider, CryptoConfiguration cryptoConfig) {
        this((AWSCredentialsProvider) null, encryptionMaterialsProvider, new ClientConfiguration(), cryptoConfig);
    }

    public AmazonS3EncryptionClient(AWSCredentials credentials, EncryptionMaterials encryptionMaterials) {
        this(credentials, (EncryptionMaterialsProvider) new StaticEncryptionMaterialsProvider(encryptionMaterials));
    }

    public AmazonS3EncryptionClient(AWSCredentials credentials, EncryptionMaterialsProvider encryptionMaterialsProvider) {
        this(credentials, encryptionMaterialsProvider, new ClientConfiguration(), new CryptoConfiguration());
    }

    public AmazonS3EncryptionClient(AWSCredentialsProvider credentialsProvider, EncryptionMaterialsProvider encryptionMaterialsProvider) {
        this(credentialsProvider, encryptionMaterialsProvider, new ClientConfiguration(), new CryptoConfiguration());
    }

    public AmazonS3EncryptionClient(AWSCredentials credentials, EncryptionMaterials encryptionMaterials, CryptoConfiguration cryptoConfig) {
        this(credentials, (EncryptionMaterialsProvider) new StaticEncryptionMaterialsProvider(encryptionMaterials), cryptoConfig);
    }

    public AmazonS3EncryptionClient(AWSCredentials credentials, EncryptionMaterialsProvider encryptionMaterialsProvider, CryptoConfiguration cryptoConfig) {
        this(credentials, encryptionMaterialsProvider, new ClientConfiguration(), cryptoConfig);
    }

    public AmazonS3EncryptionClient(AWSCredentialsProvider credentialsProvider, EncryptionMaterialsProvider encryptionMaterialsProvider, CryptoConfiguration cryptoConfig) {
        this(credentialsProvider, encryptionMaterialsProvider, new ClientConfiguration(), cryptoConfig);
    }

    public AmazonS3EncryptionClient(AWSCredentials credentials, EncryptionMaterials encryptionMaterials, ClientConfiguration clientConfig, CryptoConfiguration cryptoConfig) {
        this(credentials, (EncryptionMaterialsProvider) new StaticEncryptionMaterialsProvider(encryptionMaterials), clientConfig, cryptoConfig);
    }

    public AmazonS3EncryptionClient(AWSCredentials credentials, EncryptionMaterialsProvider encryptionMaterialsProvider, ClientConfiguration clientConfig, CryptoConfiguration cryptoConfig) {
        this((AWSCredentialsProvider) new StaticCredentialsProvider(credentials), encryptionMaterialsProvider, clientConfig, cryptoConfig);
    }

    public AmazonS3EncryptionClient(AWSCredentialsProvider credentialsProvider, EncryptionMaterialsProvider kekMaterialsProvider, ClientConfiguration clientConfig, CryptoConfiguration cryptoConfig) {
        super(credentialsProvider, clientConfig);
        assertParameterNotNull(kekMaterialsProvider, "EncryptionMaterialsProvider parameter must not be null.");
        assertParameterNotNull(cryptoConfig, "CryptoConfiguration parameter must not be null.");
        this.crypto = new CryptoModuleDispatcher(new S3DirectImpl(), credentialsProvider, kekMaterialsProvider, clientConfig, cryptoConfig);
    }

    private void assertParameterNotNull(Object parameterValue, String errorMessage) {
        if (parameterValue == null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public PutObjectResult putObject(PutObjectRequest req) {
        return this.crypto.putObjectSecurely(req);
    }

    public S3Object getObject(GetObjectRequest req) {
        return this.crypto.getObjectSecurely(req);
    }

    public ObjectMetadata getObject(GetObjectRequest req, File dest) {
        return this.crypto.getObjectSecurely(req, dest);
    }

    public void deleteObject(DeleteObjectRequest req) {
        req.getRequestClientOptions().appendUserAgent(USER_AGENT);
        super.deleteObject(req);
        super.deleteObject(EncryptionUtils.createInstructionDeleteObjectRequest(req));
    }

    public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest req) {
        return this.crypto.completeMultipartUploadSecurely(req);
    }

    public InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest req) {
        return this.crypto.initiateMultipartUploadSecurely(req);
    }

    public UploadPartResult uploadPart(UploadPartRequest uploadPartRequest) throws AmazonClientException, AmazonServiceException {
        return this.crypto.uploadPartSecurely(uploadPartRequest);
    }

    public CopyPartResult copyPart(CopyPartRequest copyPartRequest) {
        return this.crypto.copyPartSecurely(copyPartRequest);
    }

    public void abortMultipartUpload(AbortMultipartUploadRequest req) {
        this.crypto.abortMultipartUploadSecurely(req);
    }

    private final class S3DirectImpl extends S3Direct {
        private S3DirectImpl() {
        }

        public PutObjectResult putObject(PutObjectRequest req) {
            return AmazonS3EncryptionClient.super.putObject(req);
        }

        public S3Object getObject(GetObjectRequest req) {
            return AmazonS3EncryptionClient.super.getObject(req);
        }

        public ObjectMetadata getObject(GetObjectRequest req, File dest) {
            return AmazonS3EncryptionClient.super.getObject(req, dest);
        }

        public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest req) {
            return AmazonS3EncryptionClient.super.completeMultipartUpload(req);
        }

        public InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest req) {
            return AmazonS3EncryptionClient.super.initiateMultipartUpload(req);
        }

        public UploadPartResult uploadPart(UploadPartRequest req) throws AmazonClientException, AmazonServiceException {
            return AmazonS3EncryptionClient.super.uploadPart(req);
        }

        public CopyPartResult copyPart(CopyPartRequest req) {
            return AmazonS3EncryptionClient.super.copyPart(req);
        }

        public void abortMultipartUpload(AbortMultipartUploadRequest req) {
            AmazonS3EncryptionClient.super.abortMultipartUpload(req);
        }
    }
}
