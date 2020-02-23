package com.amazonaws.services.s3.internal.crypto;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.StringUtils;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

class S3ObjectWrapper implements Closeable {
    private final S3Object s3obj;

    S3ObjectWrapper(S3Object s3obj2) {
        if (s3obj2 == null) {
            throw new IllegalArgumentException();
        }
        this.s3obj = s3obj2;
    }

    /* access modifiers changed from: package-private */
    public ObjectMetadata getObjectMetadata() {
        return this.s3obj.getObjectMetadata();
    }

    /* access modifiers changed from: package-private */
    public void setObjectMetadata(ObjectMetadata metadata) {
        this.s3obj.setObjectMetadata(metadata);
    }

    /* access modifiers changed from: package-private */
    public S3ObjectInputStream getObjectContent() {
        return this.s3obj.getObjectContent();
    }

    /* access modifiers changed from: package-private */
    public void setObjectContent(S3ObjectInputStream objectContent) {
        this.s3obj.setObjectContent(objectContent);
    }

    /* access modifiers changed from: package-private */
    public void setObjectContent(InputStream objectContent) {
        this.s3obj.setObjectContent(objectContent);
    }

    /* access modifiers changed from: package-private */
    public String getBucketName() {
        return this.s3obj.getBucketName();
    }

    /* access modifiers changed from: package-private */
    public void setBucketName(String bucketName) {
        this.s3obj.setBucketName(bucketName);
    }

    /* access modifiers changed from: package-private */
    public String getKey() {
        return this.s3obj.getKey();
    }

    /* access modifiers changed from: package-private */
    public void setKey(String key) {
        this.s3obj.setKey(key);
    }

    /* access modifiers changed from: package-private */
    public String getRedirectLocation() {
        return this.s3obj.getRedirectLocation();
    }

    /* access modifiers changed from: package-private */
    public void setRedirectLocation(String redirectLocation) {
        this.s3obj.setRedirectLocation(redirectLocation);
    }

    public String toString() {
        return this.s3obj.toString();
    }

    /* access modifiers changed from: package-private */
    public final boolean isInstructionFile() {
        Map<String, String> userMeta = this.s3obj.getObjectMetadata().getUserMetadata();
        return userMeta != null && userMeta.containsKey(Headers.CRYPTO_INSTRUCTION_FILE);
    }

    /* access modifiers changed from: package-private */
    public final boolean hasEncryptionInfo() {
        Map<String, String> userMeta = this.s3obj.getObjectMetadata().getUserMetadata();
        return userMeta != null && userMeta.containsKey(Headers.CRYPTO_IV) && (userMeta.containsKey(Headers.CRYPTO_KEY_V2) || userMeta.containsKey(Headers.CRYPTO_KEY));
    }

    /* access modifiers changed from: package-private */
    public String toJsonString() {
        try {
            return from(this.s3obj.getObjectContent());
        } catch (Exception e) {
            throw new AmazonClientException("Error parsing JSON: " + e.getMessage());
        }
    }

    /* JADX INFO: finally extract failed */
    private static String from(InputStream is) throws IOException {
        if (is == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StringUtils.UTF8));
            while (true) {
                String line = reader.readLine();
                if (line != null) {
                    stringBuilder.append(line);
                } else {
                    is.close();
                    return stringBuilder.toString();
                }
            }
        } catch (Throwable th) {
            is.close();
            throw th;
        }
    }

    public void close() throws IOException {
        this.s3obj.close();
    }

    /* access modifiers changed from: package-private */
    public S3Object getS3Object() {
        return this.s3obj;
    }

    /* access modifiers changed from: package-private */
    public ContentCryptoScheme encryptionSchemeOf(Map<String, String> instructionFile) {
        if (instructionFile != null) {
            return ContentCryptoScheme.fromCEKAlgo(instructionFile.get(Headers.CRYPTO_CEK_ALGORITHM));
        }
        return ContentCryptoScheme.fromCEKAlgo(this.s3obj.getObjectMetadata().getUserMetadata().get(Headers.CRYPTO_CEK_ALGORITHM));
    }
}
