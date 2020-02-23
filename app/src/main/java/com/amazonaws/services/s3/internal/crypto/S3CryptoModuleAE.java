package com.amazonaws.services.s3.internal.crypto;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3EncryptionClient;
import com.amazonaws.services.s3.internal.InputSubstream;
import com.amazonaws.services.s3.internal.RepeatableFileInputStream;
import com.amazonaws.services.s3.internal.S3Direct;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.CopyPartRequest;
import com.amazonaws.services.s3.model.CopyPartResult;
import com.amazonaws.services.s3.model.CryptoConfiguration;
import com.amazonaws.services.s3.model.CryptoStorageMode;
import com.amazonaws.services.s3.model.EncryptionMaterialsProvider;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;
import com.amazonaws.util.json.JsonUtils;
import com.google.android.gms.gcm.Task;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

class S3CryptoModuleAE extends S3CryptoModuleBase<MultipartUploadCryptoContext> {
    private static final boolean IS_MULTI_PART = true;

    static {
        CryptoRuntime.enableBouncyCastle();
    }

    S3CryptoModuleAE(S3Direct s3, AWSCredentialsProvider credentialsProvider, EncryptionMaterialsProvider encryptionMaterialsProvider, ClientConfiguration clientConfig, CryptoConfiguration cryptoConfig) {
        super(s3, credentialsProvider, encryptionMaterialsProvider, clientConfig, cryptoConfig, new S3CryptoScheme(ContentCryptoScheme.AES_GCM));
    }

    S3CryptoModuleAE(S3Direct s3, EncryptionMaterialsProvider encryptionMaterialsProvider, CryptoConfiguration cryptoConfig) {
        this(s3, new DefaultAWSCredentialsProviderChain(), encryptionMaterialsProvider, new ClientConfiguration(), cryptoConfig);
    }

    /* access modifiers changed from: protected */
    public boolean isStrict() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void securityCheck(ContentCryptoMaterial cekMaterial, S3ObjectWrapper retrieved) {
    }

    public PutObjectResult putObjectSecurely(PutObjectRequest putObjectRequest) throws AmazonClientException, AmazonServiceException {
        appendUserAgent(putObjectRequest, AmazonS3EncryptionClient.USER_AGENT);
        if (this.cryptoConfig.getStorageMode() == CryptoStorageMode.InstructionFile) {
            return putObjectUsingInstructionFile(putObjectRequest);
        }
        return putObjectUsingMetadata(putObjectRequest);
    }

    private PutObjectResult putObjectUsingMetadata(PutObjectRequest req) throws AmazonClientException, AmazonServiceException {
        ContentCryptoMaterial cekMaterial = createContentCryptoMaterial(req);
        PutObjectRequest wrappedReq = wrapWithCipher(req, cekMaterial);
        req.setMetadata(updateMetadataWithContentCryptoMaterial(req.getMetadata(), req.getFile(), cekMaterial));
        return this.s3.putObject(wrappedReq);
    }

    public S3Object getObjectSecurely(GetObjectRequest req) throws AmazonClientException, AmazonServiceException {
        appendUserAgent(req, AmazonS3EncryptionClient.USER_AGENT);
        long[] desiredRange = req.getRange();
        if (!isStrict() || desiredRange == null) {
            long[] adjustedCryptoRange = EncryptionUtils.getAdjustedCryptoRange(desiredRange);
            if (adjustedCryptoRange != null) {
                req.setRange(adjustedCryptoRange[0], adjustedCryptoRange[1]);
            }
            S3Object retrieved = this.s3.getObject(req);
            if (retrieved == null) {
                return null;
            }
            try {
                return decipher(req, desiredRange, adjustedCryptoRange, retrieved);
            } catch (AmazonClientException ace) {
                try {
                    retrieved.getObjectContent().close();
                } catch (Exception e) {
                    this.log.debug("Safely ignoring", e);
                }
                throw ace;
            }
        } else {
            throw new SecurityException("Range get is not allowed in strict crypto mode");
        }
    }

    private S3Object decipher(GetObjectRequest req, long[] desiredRange, long[] cryptoRange, S3Object retrieved) {
        S3ObjectWrapper wrapped = new S3ObjectWrapper(retrieved);
        if (wrapped.hasEncryptionInfo()) {
            return decipherWithMetadata(desiredRange, cryptoRange, wrapped);
        }
        S3ObjectWrapper instructionFile = fetchInstructionFile(req);
        if (instructionFile != null) {
            try {
                if (instructionFile.isInstructionFile()) {
                    S3Object decipherWithInstructionFile = decipherWithInstructionFile(desiredRange, cryptoRange, wrapped, instructionFile);
                    try {
                        return decipherWithInstructionFile;
                    } catch (Exception e) {
                        return decipherWithInstructionFile;
                    }
                } else {
                    try {
                        instructionFile.getObjectContent().close();
                    } catch (Exception e2) {
                    }
                }
            } finally {
                try {
                    instructionFile.getObjectContent().close();
                } catch (Exception e3) {
                }
            }
        }
        if (isStrict()) {
            try {
                wrapped.close();
            } catch (IOException e4) {
            }
            throw new SecurityException("S3 object with bucket name: " + retrieved.getBucketName() + ", key: " + retrieved.getKey() + " is not encrypted");
        }
        this.log.warn(String.format("Unable to detect encryption information for object '%s' in bucket '%s'. Returning object without decryption.", new Object[]{retrieved.getKey(), retrieved.getBucketName()}));
        return adjustToDesiredRange(wrapped, desiredRange, (Map<String, String>) null).getS3Object();
    }

    private S3Object decipherWithInstructionFile(long[] desiredRange, long[] cryptoRange, S3ObjectWrapper retrieved, S3ObjectWrapper instructionFile) {
        Map<String, String> instruction = JsonUtils.jsonToMap(instructionFile.toJsonString());
        ContentCryptoMaterial cekMaterial = ContentCryptoMaterial.fromInstructionFile(instruction, this.kekMaterialsProvider, this.cryptoConfig.getCryptoProvider(), cryptoRange);
        securityCheck(cekMaterial, retrieved);
        return adjustToDesiredRange(decrypt(retrieved, cekMaterial, cryptoRange), desiredRange, instruction).getS3Object();
    }

    private S3Object decipherWithMetadata(long[] desiredRange, long[] cryptoRange, S3ObjectWrapper retrieved) {
        ContentCryptoMaterial cekMaterial = ContentCryptoMaterial.fromObjectMetadata(retrieved.getObjectMetadata(), this.kekMaterialsProvider, this.cryptoConfig.getCryptoProvider(), cryptoRange);
        securityCheck(cekMaterial, retrieved);
        return adjustToDesiredRange(decrypt(retrieved, cekMaterial, cryptoRange), desiredRange, (Map<String, String>) null).getS3Object();
    }

    /* access modifiers changed from: protected */
    public final S3ObjectWrapper adjustToDesiredRange(S3ObjectWrapper s3object, long[] range, Map<String, String> instruction) {
        if (range != null) {
            long maxOffset = (s3object.getObjectMetadata().getInstanceLength() - ((long) (s3object.encryptionSchemeOf(instruction).getTagLengthInBits() / 8))) - 1;
            if (range[1] > maxOffset) {
                range[1] = maxOffset;
                if (range[0] > range[1]) {
                    try {
                        s3object.getObjectContent().close();
                    } catch (IOException ignore) {
                        this.log.trace("", ignore);
                    }
                    s3object.setObjectContent((InputStream) new ByteArrayInputStream(new byte[0]));
                }
            }
            if (range[0] <= range[1]) {
                try {
                    S3ObjectInputStream objectContent = s3object.getObjectContent();
                    s3object.setObjectContent(new S3ObjectInputStream(new AdjustedRangeInputStream(objectContent, range[0], range[1]), objectContent.getHttpRequest()));
                } catch (IOException e) {
                    throw new AmazonClientException("Error adjusting output to desired byte range: " + e.getMessage());
                }
            }
        }
        return s3object;
    }

    public ObjectMetadata getObjectSecurely(GetObjectRequest getObjectRequest, File destinationFile) throws AmazonClientException, AmazonServiceException {
        assertParameterNotNull(destinationFile, "The destination file parameter must be specified when downloading an object directly to a file");
        S3Object s3Object = getObjectSecurely(getObjectRequest);
        if (s3Object == null) {
            return null;
        }
        OutputStream outputStream = null;
        try {
            OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(destinationFile));
            try {
                byte[] buffer = new byte[Task.EXTRAS_LIMIT_BYTES];
                while (true) {
                    int bytesRead = s3Object.getObjectContent().read(buffer);
                    if (bytesRead > -1) {
                        outputStream2.write(buffer, 0, bytesRead);
                    } else {
                        try {
                            break;
                        } catch (Exception e) {
                            this.log.debug(e.getMessage());
                        }
                    }
                }
                outputStream2.close();
                try {
                    s3Object.getObjectContent().close();
                } catch (Exception e2) {
                    this.log.debug(e2.getMessage());
                }
                return s3Object.getObjectMetadata();
            } catch (IOException e3) {
                e = e3;
                outputStream = outputStream2;
                try {
                    throw new AmazonClientException("Unable to store object contents to disk: " + e.getMessage(), e);
                } catch (Throwable th) {
                    th = th;
                    try {
                        outputStream.close();
                    } catch (Exception e4) {
                        this.log.debug(e4.getMessage());
                    }
                    try {
                        s3Object.getObjectContent().close();
                    } catch (Exception e5) {
                        this.log.debug(e5.getMessage());
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                outputStream = outputStream2;
                outputStream.close();
                s3Object.getObjectContent().close();
                throw th;
            }
        } catch (IOException e6) {
            e = e6;
            throw new AmazonClientException("Unable to store object contents to disk: " + e.getMessage(), e);
        }
    }

    public CompleteMultipartUploadResult completeMultipartUploadSecurely(CompleteMultipartUploadRequest req) throws AmazonClientException, AmazonServiceException {
        appendUserAgent(req, AmazonS3EncryptionClient.USER_AGENT);
        String uploadId = req.getUploadId();
        MultipartUploadCryptoContext uploadContext = (MultipartUploadCryptoContext) this.multipartUploadContexts.get(uploadId);
        if (!uploadContext.hasFinalPartBeenSeen()) {
            throw new AmazonClientException("Unable to complete an encrypted multipart upload without being told which part was the last.  Without knowing which part was the last, the encrypted data in Amazon S3 is incomplete and corrupt.");
        }
        CompleteMultipartUploadResult result = this.s3.completeMultipartUpload(req);
        if (this.cryptoConfig.getStorageMode() == CryptoStorageMode.InstructionFile) {
            this.s3.putObject(createInstructionPutRequest(uploadContext.getBucketName(), uploadContext.getKey(), uploadContext.getContentCryptoMaterial()));
        }
        this.multipartUploadContexts.remove(uploadId);
        return result;
    }

    public InitiateMultipartUploadResult initiateMultipartUploadSecurely(InitiateMultipartUploadRequest req) throws AmazonClientException, AmazonServiceException {
        appendUserAgent(req, AmazonS3EncryptionClient.USER_AGENT);
        ContentCryptoMaterial cekMaterial = createContentCryptoMaterial(req);
        if (this.cryptoConfig.getStorageMode() == CryptoStorageMode.ObjectMetadata) {
            ObjectMetadata metadata = req.getObjectMetadata();
            if (metadata == null) {
                metadata = new ObjectMetadata();
            }
            req.setObjectMetadata(updateMetadataWithContentCryptoMaterial(metadata, (File) null, cekMaterial));
        }
        InitiateMultipartUploadResult result = this.s3.initiateMultipartUpload(req);
        this.multipartUploadContexts.put(result.getUploadId(), new MultipartUploadCryptoContext(req.getBucketName(), req.getKey(), cekMaterial));
        return result;
    }

    public UploadPartResult uploadPartSecurely(UploadPartRequest req) throws AmazonClientException, AmazonServiceException {
        appendUserAgent(req, AmazonS3EncryptionClient.USER_AGENT);
        int blockSize = this.contentCryptoScheme.getBlockSizeInBytes();
        boolean isLastPart = req.isLastPart();
        String uploadId = req.getUploadId();
        long partSize = req.getPartSize();
        boolean partSizeMultipleOfCipherBlockSize = 0 == partSize % ((long) blockSize);
        if (isLastPart || partSizeMultipleOfCipherBlockSize) {
            MultipartUploadCryptoContext uploadContext = (MultipartUploadCryptoContext) this.multipartUploadContexts.get(uploadId);
            if (uploadContext == null) {
                throw new AmazonClientException("No client-side information available on upload ID " + uploadId);
            }
            req.setInputStream(newMultipartS3CipherInputStream(req, uploadContext.getCipherLite()));
            req.setFile((File) null);
            req.setFileOffset(0);
            if (req.isLastPart()) {
                req.setPartSize(((long) (this.contentCryptoScheme.getTagLengthInBits() / 8)) + partSize);
                if (uploadContext.hasFinalPartBeenSeen()) {
                    throw new AmazonClientException("This part was specified as the last part in a multipart upload, but a previous part was already marked as the last part.  Only the last part of the upload should be marked as the last part.");
                }
                uploadContext.setHasFinalPartBeenSeen(true);
            }
            return this.s3.uploadPart(req);
        }
        throw new AmazonClientException("Invalid part size: part sizes for encrypted multipart uploads must be multiples of the cipher block size (" + blockSize + ") with the exception of the last part.");
    }

    /* access modifiers changed from: protected */
    public final CipherLiteInputStream newMultipartS3CipherInputStream(UploadPartRequest req, CipherLite cipherLite) {
        try {
            InputStream is = req.getInputStream();
            if (req.getFile() != null) {
                is = new InputSubstream(new RepeatableFileInputStream(req.getFile()), req.getFileOffset(), req.getPartSize(), req.isLastPart());
            }
            return new CipherLiteInputStream(is, cipherLite, 2048, true, req.isLastPart());
        } catch (Exception e) {
            throw new AmazonClientException("Unable to create cipher input stream: " + e.getMessage(), e);
        }
    }

    public CopyPartResult copyPartSecurely(CopyPartRequest copyPartRequest) {
        MultipartUploadCryptoContext uploadContext = (MultipartUploadCryptoContext) this.multipartUploadContexts.get(copyPartRequest.getUploadId());
        if (!uploadContext.hasFinalPartBeenSeen()) {
            uploadContext.setHasFinalPartBeenSeen(true);
        }
        return this.s3.copyPart(copyPartRequest);
    }

    private PutObjectResult putObjectUsingInstructionFile(PutObjectRequest putObjectRequest) throws AmazonClientException, AmazonServiceException {
        PutObjectRequest putInstFileRequest = putObjectRequest.clone();
        ContentCryptoMaterial cekMaterial = createContentCryptoMaterial(putObjectRequest);
        PutObjectResult result = this.s3.putObject(wrapWithCipher(putObjectRequest, cekMaterial));
        this.s3.putObject(upateInstructionPutRequest(putInstFileRequest, cekMaterial));
        return result;
    }

    private S3ObjectWrapper decrypt(S3ObjectWrapper wrapper, ContentCryptoMaterial cekMaterial, long[] range) {
        S3ObjectInputStream objectContent = wrapper.getObjectContent();
        wrapper.setObjectContent(new S3ObjectInputStream(new CipherLiteInputStream(objectContent, cekMaterial.getCipherLite(), 2048), objectContent.getHttpRequest()));
        return wrapper;
    }

    private S3ObjectWrapper fetchInstructionFile(GetObjectRequest getObjectRequest) {
        try {
            S3Object o = this.s3.getObject(EncryptionUtils.createInstructionGetRequest(getObjectRequest));
            if (o == null) {
                return null;
            }
            return new S3ObjectWrapper(o);
        } catch (AmazonServiceException e) {
            this.log.debug("Unable to retrieve instruction file : " + e.getMessage());
            return null;
        }
    }

    private void assertParameterNotNull(Object parameterValue, String errorMessage) {
        if (parameterValue == null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /* access modifiers changed from: protected */
    public final long ciphertextLength(long originalContentLength) {
        return ((long) (this.contentCryptoScheme.getTagLengthInBits() / 8)) + originalContentLength;
    }
}
