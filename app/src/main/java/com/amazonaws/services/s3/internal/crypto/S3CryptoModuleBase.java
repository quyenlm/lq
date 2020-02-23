package com.amazonaws.services.s3.internal.crypto;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.internal.RepeatableFileInputStream;
import com.amazonaws.services.s3.internal.S3Direct;
import com.amazonaws.services.s3.internal.crypto.MultipartUploadContext;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.CryptoConfiguration;
import com.amazonaws.services.s3.model.EncryptionMaterials;
import com.amazonaws.services.s3.model.EncryptionMaterialsProvider;
import com.amazonaws.services.s3.model.MaterialsDescriptionProvider;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.util.Mimetypes;
import com.amazonaws.util.LengthCheckInputStream;
import com.amazonaws.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class S3CryptoModuleBase<T extends MultipartUploadContext> extends S3CryptoModule<T> {
    protected static final int DEFAULT_BUFFER_SIZE = 2048;
    protected final ContentCryptoScheme contentCryptoScheme;
    protected final CryptoConfiguration cryptoConfig;
    protected final S3CryptoScheme cryptoScheme;
    protected final EncryptionMaterialsProvider kekMaterialsProvider;
    protected final Log log = LogFactory.getLog((Class) getClass());
    protected final Map<String, T> multipartUploadContexts = Collections.synchronizedMap(new HashMap());
    protected final S3Direct s3;

    /* access modifiers changed from: protected */
    public abstract long ciphertextLength(long j);

    protected S3CryptoModuleBase(S3Direct s32, AWSCredentialsProvider credentialsProvider, EncryptionMaterialsProvider kekMaterialsProvider2, ClientConfiguration clientConfig, CryptoConfiguration cryptoConfig2, S3CryptoScheme cryptoScheme2) {
        this.kekMaterialsProvider = kekMaterialsProvider2;
        this.cryptoConfig = cryptoConfig2;
        this.s3 = s32;
        this.cryptoScheme = cryptoScheme2;
        this.contentCryptoScheme = cryptoScheme2.getContentCryptoScheme();
    }

    public final void abortMultipartUploadSecurely(AbortMultipartUploadRequest req) {
        this.s3.abortMultipartUpload(req);
        this.multipartUploadContexts.remove(req.getUploadId());
    }

    /* access modifiers changed from: protected */
    public final ObjectMetadata updateMetadataWithContentCryptoMaterial(ObjectMetadata metadata, File file, ContentCryptoMaterial instruction) {
        if (metadata == null) {
            metadata = new ObjectMetadata();
        }
        if (file != null) {
            metadata.setContentType(Mimetypes.getInstance().getMimetype(file));
        }
        return instruction.toObjectMetadata(metadata);
    }

    /* access modifiers changed from: protected */
    public final ContentCryptoMaterial createContentCryptoMaterial(AmazonWebServiceRequest req) {
        if (req instanceof MaterialsDescriptionProvider) {
            return newContentCryptoMaterial(this.kekMaterialsProvider, ((MaterialsDescriptionProvider) req).getMaterialsDescription(), this.cryptoConfig.getCryptoProvider());
        }
        return newContentCryptoMaterial(this.kekMaterialsProvider, this.cryptoConfig.getCryptoProvider());
    }

    private ContentCryptoMaterial newContentCryptoMaterial(EncryptionMaterialsProvider kekMaterialProvider, Map<String, String> materialsDescription, Provider provider) {
        return buildContentCryptoMaterial(kekMaterialProvider.getEncryptionMaterials(materialsDescription), provider);
    }

    private ContentCryptoMaterial newContentCryptoMaterial(EncryptionMaterialsProvider kekMaterialProvider, Provider provider) {
        return buildContentCryptoMaterial(kekMaterialProvider.getEncryptionMaterials(), provider);
    }

    private ContentCryptoMaterial buildContentCryptoMaterial(EncryptionMaterials kekMaterials, Provider provider) {
        SecretKey cek = generateCEK(kekMaterials, provider);
        byte[] iv = new byte[this.contentCryptoScheme.getIVLengthInBytes()];
        this.cryptoScheme.getSecureRandom().nextBytes(iv);
        SecuredCEK cekSecured = secureCEK(cek, kekMaterials, provider);
        return new ContentCryptoMaterial(kekMaterials.getMaterialsDescription(), cekSecured.encrypted, cekSecured.keyWrapAlgorithm, this.contentCryptoScheme.createCipherLite(cek, iv, 1, provider));
    }

    /* access modifiers changed from: protected */
    public final SecretKey generateCEK(EncryptionMaterials kekMaterials, Provider providerIn) {
        KeyGenerator generator;
        String providerName;
        String keygenAlgo = this.contentCryptoScheme.getKeyGeneratorAlgorithm();
        if (providerIn == null) {
            try {
                generator = KeyGenerator.getInstance(keygenAlgo);
            } catch (NoSuchAlgorithmException e) {
                throw new AmazonClientException("Unable to generate envelope symmetric key:" + e.getMessage(), e);
            }
        } else {
            generator = KeyGenerator.getInstance(keygenAlgo, providerIn);
        }
        generator.init(this.contentCryptoScheme.getKeyLengthInBits(), this.cryptoScheme.getSecureRandom());
        boolean involvesBcPublicKey = false;
        KeyPair keyPair = kekMaterials.getKeyPair();
        if (keyPair != null && this.cryptoScheme.getKeyWrapScheme().getKeyWrapAlgorithm(keyPair.getPublic()) == null) {
            Provider provider = generator.getProvider();
            if (provider == null) {
                providerName = null;
            } else {
                providerName = provider.getName();
            }
            involvesBcPublicKey = "BC".equals(providerName);
        }
        if (!involvesBcPublicKey) {
            return generator.generateKey();
        }
        for (int retry = 0; retry < 10; retry++) {
            SecretKey secretKey = generator.generateKey();
            if (secretKey.getEncoded()[0] != 0) {
                return secretKey;
            }
        }
        throw new AmazonClientException("Failed to generate secret key");
    }

    /* access modifiers changed from: protected */
    public final SecuredCEK secureCEK(SecretKey toBeEncrypted, EncryptionMaterials materials, Provider cryptoProvider) {
        Key kek;
        Cipher cipher;
        Cipher cipher2;
        if (materials.getKeyPair() != null) {
            kek = materials.getKeyPair().getPublic();
        } else {
            kek = materials.getSymmetricKey();
        }
        String keyWrapAlgo = this.cryptoScheme.getKeyWrapScheme().getKeyWrapAlgorithm(kek);
        if (keyWrapAlgo != null) {
            if (cryptoProvider == null) {
                try {
                    cipher2 = Cipher.getInstance(keyWrapAlgo);
                } catch (Exception e) {
                    throw new AmazonClientException("Unable to encrypt symmetric key: " + e.getMessage(), e);
                }
            } else {
                cipher2 = Cipher.getInstance(keyWrapAlgo, cryptoProvider);
            }
            cipher2.init(3, kek, this.cryptoScheme.getSecureRandom());
            return new SecuredCEK(cipher2.wrap(toBeEncrypted), keyWrapAlgo);
        }
        byte[] toBeEncryptedBytes = toBeEncrypted.getEncoded();
        String algo = kek.getAlgorithm();
        if (cryptoProvider != null) {
            cipher = Cipher.getInstance(algo, cryptoProvider);
        } else {
            cipher = Cipher.getInstance(algo);
        }
        cipher.init(1, kek);
        return new SecuredCEK(cipher.doFinal(toBeEncryptedBytes), (String) null);
    }

    private static class SecuredCEK {
        final byte[] encrypted;
        final String keyWrapAlgorithm;

        SecuredCEK(byte[] encryptedKey, String keyWrapAlgorithm2) {
            this.encrypted = encryptedKey;
            this.keyWrapAlgorithm = keyWrapAlgorithm2;
        }
    }

    /* access modifiers changed from: protected */
    public final PutObjectRequest wrapWithCipher(PutObjectRequest request, ContentCryptoMaterial cekMaterial) {
        ObjectMetadata metadata = request.getMetadata();
        if (metadata == null) {
            metadata = new ObjectMetadata();
        }
        if (metadata.getContentMD5() != null) {
            metadata.addUserMetadata(Headers.UNENCRYPTED_CONTENT_MD5, metadata.getContentMD5());
        }
        metadata.setContentMD5((String) null);
        long plaintextLength = plaintextLength(request, metadata);
        if (plaintextLength >= 0) {
            metadata.addUserMetadata(Headers.UNENCRYPTED_CONTENT_LENGTH, Long.toString(plaintextLength));
            metadata.setContentLength(ciphertextLength(plaintextLength));
        }
        request.setMetadata(metadata);
        request.setInputStream(newS3CipherLiteInputStream(request, cekMaterial, plaintextLength));
        request.setFile((File) null);
        return request;
    }

    private CipherLiteInputStream newS3CipherLiteInputStream(PutObjectRequest req, ContentCryptoMaterial cekMaterial, long plaintextLength) {
        InputStream is;
        InputStream is2;
        try {
            InputStream is3 = req.getInputStream();
            if (req.getFile() != null) {
                is = new RepeatableFileInputStream(req.getFile());
            } else {
                is = is3;
            }
            if (plaintextLength > -1) {
                is2 = new LengthCheckInputStream(is, plaintextLength, false);
            } else {
                is2 = is;
            }
            return new CipherLiteInputStream(is2, cekMaterial.getCipherLite(), 2048);
        } catch (Exception e) {
            throw new AmazonClientException("Unable to create cipher input stream: " + e.getMessage(), e);
        }
    }

    /* access modifiers changed from: protected */
    public final long plaintextLength(PutObjectRequest request, ObjectMetadata metadata) {
        if (request.getFile() != null) {
            return request.getFile().length();
        }
        if (request.getInputStream() == null || metadata.getRawMetadataValue("Content-Length") == null) {
            return -1;
        }
        return metadata.getContentLength();
    }

    public final S3CryptoScheme getS3CryptoScheme() {
        return this.cryptoScheme;
    }

    /* access modifiers changed from: protected */
    public final PutObjectRequest upateInstructionPutRequest(PutObjectRequest request, ContentCryptoMaterial cekMaterial) {
        byte[] bytes = cekMaterial.toJsonString().getBytes(StringUtils.UTF8);
        InputStream is = new ByteArrayInputStream(bytes);
        ObjectMetadata metadata = request.getMetadata();
        if (metadata == null) {
            metadata = new ObjectMetadata();
            request.setMetadata(metadata);
        }
        metadata.setContentLength((long) bytes.length);
        metadata.addUserMetadata(Headers.CRYPTO_INSTRUCTION_FILE, "");
        request.setKey(request.getKey() + ".instruction");
        request.setMetadata(metadata);
        request.setInputStream(is);
        request.setFile((File) null);
        return request;
    }

    /* access modifiers changed from: protected */
    public final PutObjectRequest createInstructionPutRequest(String bucketName, String key, ContentCryptoMaterial cekMaterial) {
        byte[] bytes = cekMaterial.toJsonString().getBytes(StringUtils.UTF8);
        InputStream is = new ByteArrayInputStream(bytes);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength((long) bytes.length);
        metadata.addUserMetadata(Headers.CRYPTO_INSTRUCTION_FILE, "");
        return new PutObjectRequest(bucketName, key + ".instruction", is, metadata);
    }

    /* access modifiers changed from: package-private */
    public final <X extends AmazonWebServiceRequest> X appendUserAgent(X request, String userAgent) {
        request.getRequestClientOptions().appendUserAgent(userAgent);
        return request;
    }
}
