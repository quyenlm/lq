package com.amazonaws.services.s3.internal.crypto;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3EncryptionClient;
import com.amazonaws.services.s3.internal.S3Direct;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.CopyPartRequest;
import com.amazonaws.services.s3.model.CopyPartResult;
import com.amazonaws.services.s3.model.CryptoConfiguration;
import com.amazonaws.services.s3.model.CryptoStorageMode;
import com.amazonaws.services.s3.model.EncryptedInitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.EncryptionMaterials;
import com.amazonaws.services.s3.model.EncryptionMaterialsProvider;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.MaterialsDescriptionProvider;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;
import java.io.File;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

class S3CryptoModuleEO extends S3CryptoModuleBase<EncryptedUploadContext> {
    S3CryptoModuleEO(S3Direct s3, AWSCredentialsProvider credentialsProvider, EncryptionMaterialsProvider encryptionMaterialsProvider, ClientConfiguration clientConfig, CryptoConfiguration cryptoConfig) {
        super(s3, credentialsProvider, encryptionMaterialsProvider, clientConfig, cryptoConfig, new S3CryptoScheme(ContentCryptoScheme.AES_CBC));
    }

    S3CryptoModuleEO(S3Direct s3, EncryptionMaterialsProvider encryptionMaterialsProvider, CryptoConfiguration cryptoConfig) {
        this(s3, new DefaultAWSCredentialsProviderChain(), encryptionMaterialsProvider, new ClientConfiguration(), cryptoConfig);
    }

    public PutObjectResult putObjectSecurely(PutObjectRequest putObjectRequest) throws AmazonClientException, AmazonServiceException {
        appendUserAgent(putObjectRequest, AmazonS3EncryptionClient.USER_AGENT);
        if (this.cryptoConfig.getStorageMode() == CryptoStorageMode.InstructionFile) {
            return putObjectUsingInstructionFile(putObjectRequest);
        }
        return putObjectUsingMetadata(putObjectRequest);
    }

    public S3Object getObjectSecurely(GetObjectRequest getObjectRequest) throws AmazonClientException, AmazonServiceException {
        throw new IllegalStateException();
    }

    public ObjectMetadata getObjectSecurely(GetObjectRequest getObjectRequest, File destinationFile) throws AmazonClientException, AmazonServiceException {
        throw new IllegalStateException();
    }

    public CompleteMultipartUploadResult completeMultipartUploadSecurely(CompleteMultipartUploadRequest completeMultipartUploadRequest) throws AmazonClientException, AmazonServiceException {
        EncryptionMaterials encryptionMaterials;
        appendUserAgent(completeMultipartUploadRequest, AmazonS3EncryptionClient.USER_AGENT);
        String uploadId = completeMultipartUploadRequest.getUploadId();
        EncryptedUploadContext encryptedUploadContext = (EncryptedUploadContext) this.multipartUploadContexts.get(uploadId);
        if (!encryptedUploadContext.hasFinalPartBeenSeen()) {
            throw new AmazonClientException("Unable to complete an encrypted multipart upload without being told which part was the last.  Without knowing which part was the last, the encrypted data in Amazon S3 is incomplete and corrupt.");
        }
        CompleteMultipartUploadResult result = this.s3.completeMultipartUpload(completeMultipartUploadRequest);
        if (this.cryptoConfig.getStorageMode() == CryptoStorageMode.InstructionFile) {
            Cipher symmetricCipher = EncryptionUtils.createSymmetricCipher(encryptedUploadContext.getEnvelopeEncryptionKey(), 1, this.cryptoConfig.getCryptoProvider(), encryptedUploadContext.getFirstInitializationVector());
            if (encryptedUploadContext.getMaterialsDescription() != null) {
                encryptionMaterials = this.kekMaterialsProvider.getEncryptionMaterials(encryptedUploadContext.getMaterialsDescription());
            } else {
                encryptionMaterials = this.kekMaterialsProvider.getEncryptionMaterials();
            }
            this.s3.putObject(EncryptionUtils.createInstructionPutRequest(encryptedUploadContext.getBucketName(), encryptedUploadContext.getKey(), new EncryptionInstruction(encryptionMaterials.getMaterialsDescription(), EncryptionUtils.getEncryptedSymmetricKey(encryptedUploadContext.getEnvelopeEncryptionKey(), encryptionMaterials, this.cryptoConfig.getCryptoProvider()), encryptedUploadContext.getEnvelopeEncryptionKey(), symmetricCipher)));
        }
        this.multipartUploadContexts.remove(uploadId);
        return result;
    }

    public InitiateMultipartUploadResult initiateMultipartUploadSecurely(InitiateMultipartUploadRequest initiateMultipartUploadRequest) throws AmazonClientException, AmazonServiceException {
        EncryptionMaterials encryptionMaterials;
        appendUserAgent(initiateMultipartUploadRequest, AmazonS3EncryptionClient.USER_AGENT);
        SecretKey envelopeSymmetricKey = EncryptionUtils.generateOneTimeUseSymmetricKey();
        Cipher symmetricCipher = EncryptionUtils.createSymmetricCipher(envelopeSymmetricKey, 1, this.cryptoConfig.getCryptoProvider(), (byte[]) null);
        if (this.cryptoConfig.getStorageMode() == CryptoStorageMode.ObjectMetadata) {
            if (initiateMultipartUploadRequest instanceof EncryptedInitiateMultipartUploadRequest) {
                encryptionMaterials = this.kekMaterialsProvider.getEncryptionMaterials(((EncryptedInitiateMultipartUploadRequest) initiateMultipartUploadRequest).getMaterialsDescription());
            } else {
                encryptionMaterials = this.kekMaterialsProvider.getEncryptionMaterials();
            }
            initiateMultipartUploadRequest.setObjectMetadata(EncryptionUtils.updateMetadataWithEncryptionInfo(initiateMultipartUploadRequest, EncryptionUtils.getEncryptedSymmetricKey(envelopeSymmetricKey, encryptionMaterials, this.cryptoConfig.getCryptoProvider()), symmetricCipher, encryptionMaterials.getMaterialsDescription()));
        }
        InitiateMultipartUploadResult result = this.s3.initiateMultipartUpload(initiateMultipartUploadRequest);
        EncryptedUploadContext encryptedUploadContext = new EncryptedUploadContext(initiateMultipartUploadRequest.getBucketName(), initiateMultipartUploadRequest.getKey(), envelopeSymmetricKey);
        encryptedUploadContext.setNextInitializationVector(symmetricCipher.getIV());
        encryptedUploadContext.setFirstInitializationVector(symmetricCipher.getIV());
        if (initiateMultipartUploadRequest instanceof EncryptedInitiateMultipartUploadRequest) {
            encryptedUploadContext.setMaterialsDescription(((EncryptedInitiateMultipartUploadRequest) initiateMultipartUploadRequest).getMaterialsDescription());
        }
        this.multipartUploadContexts.put(result.getUploadId(), encryptedUploadContext);
        return result;
    }

    public UploadPartResult uploadPartSecurely(UploadPartRequest uploadPartRequest) throws AmazonClientException, AmazonServiceException {
        appendUserAgent(uploadPartRequest, AmazonS3EncryptionClient.USER_AGENT);
        boolean isLastPart = uploadPartRequest.isLastPart();
        String uploadId = uploadPartRequest.getUploadId();
        boolean partSizeMultipleOfCipherBlockSize = uploadPartRequest.getPartSize() % ((long) JceEncryptionConstants.SYMMETRIC_CIPHER_BLOCK_SIZE) == 0;
        if (isLastPart || partSizeMultipleOfCipherBlockSize) {
            EncryptedUploadContext encryptedUploadContext = (EncryptedUploadContext) this.multipartUploadContexts.get(uploadId);
            if (encryptedUploadContext == null) {
                throw new AmazonClientException("No client-side information available on upload ID " + uploadId);
            }
            CipherFactory cipherFactory = new CipherFactory(encryptedUploadContext.getEnvelopeEncryptionKey(), 1, encryptedUploadContext.getNextInitializationVector(), this.cryptoConfig.getCryptoProvider());
            ByteRangeCapturingInputStream encryptedInputStream = EncryptionUtils.getEncryptedInputStream(uploadPartRequest, cipherFactory);
            uploadPartRequest.setInputStream(encryptedInputStream);
            if (uploadPartRequest.isLastPart()) {
                long cryptoContentLength = EncryptionUtils.calculateCryptoContentLength(cipherFactory.createCipher(), uploadPartRequest);
                if (cryptoContentLength > 0) {
                    uploadPartRequest.setPartSize(cryptoContentLength);
                }
                if (encryptedUploadContext.hasFinalPartBeenSeen()) {
                    throw new AmazonClientException("This part was specified as the last part in a multipart upload, but a previous part was already marked as the last part.  Only the last part of the upload should be marked as the last part, otherwise it will cause the encrypted data to be corrupted.");
                }
                encryptedUploadContext.setHasFinalPartBeenSeen(true);
            }
            uploadPartRequest.setFile((File) null);
            uploadPartRequest.setFileOffset(0);
            UploadPartResult result = this.s3.uploadPart(uploadPartRequest);
            encryptedUploadContext.setNextInitializationVector(encryptedInputStream.getBlock());
            return result;
        }
        throw new AmazonClientException("Invalid part size: part sizes for encrypted multipart uploads must be multiples of the cipher block size (" + JceEncryptionConstants.SYMMETRIC_CIPHER_BLOCK_SIZE + ") with the exception of the last part.  Otherwise encryption adds extra padding that will corrupt the final object.");
    }

    public CopyPartResult copyPartSecurely(CopyPartRequest copyPartRequest) {
        EncryptedUploadContext encryptedUploadContext = (EncryptedUploadContext) this.multipartUploadContexts.get(copyPartRequest.getUploadId());
        if (!encryptedUploadContext.hasFinalPartBeenSeen()) {
            encryptedUploadContext.setHasFinalPartBeenSeen(true);
        }
        return this.s3.copyPart(copyPartRequest);
    }

    private PutObjectResult putObjectUsingMetadata(PutObjectRequest putObjectRequest) throws AmazonClientException, AmazonServiceException {
        EncryptionInstruction instruction = encryptionInstructionOf(putObjectRequest);
        PutObjectRequest encryptedObjectRequest = EncryptionUtils.encryptRequestUsingInstruction(putObjectRequest, instruction);
        EncryptionUtils.updateMetadataWithEncryptionInstruction(putObjectRequest, instruction);
        return this.s3.putObject(encryptedObjectRequest);
    }

    private PutObjectResult putObjectUsingInstructionFile(PutObjectRequest putObjectRequest) throws AmazonClientException, AmazonServiceException {
        EncryptionInstruction instruction = encryptionInstructionOf(putObjectRequest);
        PutObjectResult encryptedObjectResult = this.s3.putObject(EncryptionUtils.encryptRequestUsingInstruction(putObjectRequest, instruction));
        this.s3.putObject(EncryptionUtils.createInstructionPutRequest(putObjectRequest, instruction));
        return encryptedObjectResult;
    }

    private EncryptionInstruction encryptionInstructionOf(AmazonWebServiceRequest req) {
        if (req instanceof MaterialsDescriptionProvider) {
            return EncryptionUtils.generateInstruction(this.kekMaterialsProvider, ((MaterialsDescriptionProvider) req).getMaterialsDescription(), this.cryptoConfig.getCryptoProvider());
        }
        return EncryptionUtils.generateInstruction(this.kekMaterialsProvider, this.cryptoConfig.getCryptoProvider());
    }

    /* access modifiers changed from: protected */
    public final long ciphertextLength(long plaintextLength) {
        long cipherBlockSize = (long) this.contentCryptoScheme.getBlockSizeInBytes();
        return plaintextLength + (cipherBlockSize - (plaintextLength % cipherBlockSize));
    }
}
