package com.amazonaws.services.s3.internal.crypto;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.EncryptionMaterials;
import com.amazonaws.services.s3.model.EncryptionMaterialsAccessor;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.Base64;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.JsonUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.Provider;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

final class ContentCryptoMaterial {
    private final CipherLite cipherLite;
    private final byte[] encryptedCEK;
    private final Map<String, String> kekMaterialsDescription;
    private final String keyWrappingAlgorithm;

    ContentCryptoMaterial(Map<String, String> kekMaterialsDescription2, byte[] encryptedCEK2, String keyWrappingAlgorithm2, CipherLite cipherLite2) {
        this.cipherLite = cipherLite2;
        this.keyWrappingAlgorithm = keyWrappingAlgorithm2;
        this.encryptedCEK = (byte[]) encryptedCEK2.clone();
        this.kekMaterialsDescription = kekMaterialsDescription2;
    }

    /* access modifiers changed from: package-private */
    public String getKeyWrappingAlgorithm() {
        return this.keyWrappingAlgorithm;
    }

    /* access modifiers changed from: package-private */
    public ContentCryptoScheme getContentCryptoScheme() {
        return this.cipherLite.getContentCryptoScheme();
    }

    /* access modifiers changed from: package-private */
    public ObjectMetadata toObjectMetadata(ObjectMetadata metadata) {
        metadata.addUserMetadata(Headers.CRYPTO_KEY_V2, Base64.encodeAsString(getEncryptedCEK()));
        metadata.addUserMetadata(Headers.CRYPTO_IV, Base64.encodeAsString(this.cipherLite.getIV()));
        metadata.addUserMetadata(Headers.MATERIALS_DESCRIPTION, kekMaterialDescAsJson());
        ContentCryptoScheme scheme = getContentCryptoScheme();
        metadata.addUserMetadata(Headers.CRYPTO_CEK_ALGORITHM, scheme.getCipherAlgorithm());
        int tagLen = scheme.getTagLengthInBits();
        if (tagLen > 0) {
            metadata.addUserMetadata(Headers.CRYPTO_TAG_LENGTH, String.valueOf(tagLen));
        }
        String keyWrapAlgo = getKeyWrappingAlgorithm();
        if (keyWrapAlgo != null) {
            metadata.addUserMetadata(Headers.CRYPTO_KEYWRAP_ALGORITHM, keyWrapAlgo);
        }
        return metadata;
    }

    /* access modifiers changed from: package-private */
    public String toJsonString() {
        Map<String, String> map = new HashMap<>();
        map.put(Headers.CRYPTO_KEY_V2, Base64.encodeAsString(getEncryptedCEK()));
        map.put(Headers.CRYPTO_IV, Base64.encodeAsString(this.cipherLite.getIV()));
        map.put(Headers.MATERIALS_DESCRIPTION, kekMaterialDescAsJson());
        ContentCryptoScheme scheme = getContentCryptoScheme();
        map.put(Headers.CRYPTO_CEK_ALGORITHM, scheme.getCipherAlgorithm());
        int tagLen = scheme.getTagLengthInBits();
        if (tagLen > 0) {
            map.put(Headers.CRYPTO_TAG_LENGTH, String.valueOf(tagLen));
        }
        String keyWrapAlgo = getKeyWrappingAlgorithm();
        if (keyWrapAlgo != null) {
            map.put(Headers.CRYPTO_KEYWRAP_ALGORITHM, keyWrapAlgo);
        }
        return JsonUtils.mapToString(map);
    }

    private String kekMaterialDescAsJson() {
        Map<String, String> kekMaterialDesc = getKEKMaterialsDescription();
        if (kekMaterialDesc == null) {
            kekMaterialDesc = Collections.emptyMap();
        }
        return JsonUtils.mapToString(kekMaterialDesc);
    }

    private static Map<String, String> matdescFromJson(String json) {
        Map<String, String> map;
        if (json == null || (map = JsonUtils.jsonToMap(json)) == null) {
            return null;
        }
        return Collections.unmodifiableMap(map);
    }

    private static SecretKey cek(byte[] cekSecured, String keyWrapAlgo, EncryptionMaterials materials, Provider securityProvider) {
        Key kek;
        Cipher cipher;
        Cipher cipher2;
        if (materials.getKeyPair() != null) {
            kek = materials.getKeyPair().getPrivate();
        } else {
            kek = materials.getSymmetricKey();
        }
        if (keyWrapAlgo != null) {
            if (securityProvider == null) {
                try {
                    cipher2 = Cipher.getInstance(keyWrapAlgo);
                } catch (Exception e) {
                    throw new AmazonClientException("Unable to decrypt symmetric key from object metadata : " + e.getMessage(), e);
                }
            } else {
                cipher2 = Cipher.getInstance(keyWrapAlgo, securityProvider);
            }
            cipher2.init(4, kek);
            return (SecretKey) cipher2.unwrap(cekSecured, keyWrapAlgo, 3);
        }
        if (securityProvider != null) {
            cipher = Cipher.getInstance(kek.getAlgorithm(), securityProvider);
        } else {
            cipher = Cipher.getInstance(kek.getAlgorithm());
        }
        cipher.init(2, kek);
        return new SecretKeySpec(cipher.doFinal(cekSecured), JceEncryptionConstants.SYMMETRIC_KEY_ALGORITHM);
    }

    static ContentCryptoMaterial fromObjectMetadata(ObjectMetadata metadata, EncryptionMaterialsAccessor kekMaterialAccessor, Provider securityProvider, long[] range) {
        EncryptionMaterials materials;
        int tagLenActual;
        Map<String, String> userMeta = metadata.getUserMetadata();
        String b64key = userMeta.get(Headers.CRYPTO_KEY_V2);
        if (b64key == null && (b64key = userMeta.get(Headers.CRYPTO_KEY)) == null) {
            throw new AmazonClientException("Content encrypting key not found.");
        }
        byte[] cekWrapped = Base64.decode(b64key);
        byte[] iv = Base64.decode(userMeta.get(Headers.CRYPTO_IV));
        if (cekWrapped == null || iv == null) {
            throw new AmazonClientException("Content encrypting key or IV not found.");
        }
        Map<String, String> matdesc = matdescFromJson(userMeta.get(Headers.MATERIALS_DESCRIPTION));
        if (kekMaterialAccessor == null) {
            materials = null;
        } else {
            materials = kekMaterialAccessor.getEncryptionMaterials(matdesc);
        }
        if (materials == null) {
            throw new AmazonClientException("Unable to retrieve the client encryption materials");
        }
        String cekAlgo = userMeta.get(Headers.CRYPTO_CEK_ALGORITHM);
        boolean isRangeGet = range != null;
        ContentCryptoScheme contentCryptoScheme = ContentCryptoScheme.fromCEKAlgo(cekAlgo, isRangeGet);
        if (isRangeGet) {
            iv = contentCryptoScheme.adjustIV(iv, range[0]);
        } else {
            int tagLenExpected = contentCryptoScheme.getTagLengthInBits();
            if (tagLenExpected > 0 && tagLenExpected != (tagLenActual = Integer.parseInt(userMeta.get(Headers.CRYPTO_TAG_LENGTH)))) {
                throw new AmazonClientException("Unsupported tag length: " + tagLenActual + ", expected: " + tagLenExpected);
            }
        }
        String keyWrapAlgo = userMeta.get(Headers.CRYPTO_KEYWRAP_ALGORITHM);
        return new ContentCryptoMaterial(matdesc, cekWrapped, keyWrapAlgo, contentCryptoScheme.createCipherLite(cek(cekWrapped, keyWrapAlgo, materials, securityProvider), iv, 2, securityProvider));
    }

    static ContentCryptoMaterial fromInstructionFile(Map<String, String> instFile, EncryptionMaterialsAccessor kekMaterialAccessor, Provider securityProvider, long[] range) {
        return fromInstructionFile0(instFile, kekMaterialAccessor, securityProvider, range);
    }

    private static ContentCryptoMaterial fromInstructionFile0(Map<String, String> map, EncryptionMaterialsAccessor kekMaterialAccessor, Provider securityProvider, long[] range) {
        EncryptionMaterials materials;
        int tagLenActual;
        String b64key = map.get(Headers.CRYPTO_KEY_V2);
        if (b64key == null && (b64key = map.get(Headers.CRYPTO_KEY)) == null) {
            throw new AmazonClientException("Content encrypting key not found.");
        }
        byte[] cekWrapped = Base64.decode(b64key);
        byte[] iv = Base64.decode(map.get(Headers.CRYPTO_IV));
        if (cekWrapped == null || iv == null) {
            throw new AmazonClientException("Necessary encryption info not found in the instruction file " + map);
        }
        Map<String, String> matdesc = matdescFromJson(map.get(Headers.MATERIALS_DESCRIPTION));
        if (kekMaterialAccessor == null) {
            materials = null;
        } else {
            materials = kekMaterialAccessor.getEncryptionMaterials(matdesc);
        }
        if (materials == null) {
            throw new AmazonClientException("Unable to retrieve the encryption materials that originally encrypted object corresponding to instruction file " + map);
        }
        String cekAlgo = map.get(Headers.CRYPTO_CEK_ALGORITHM);
        boolean isRangeGet = range != null;
        ContentCryptoScheme contentCryptoScheme = ContentCryptoScheme.fromCEKAlgo(cekAlgo, isRangeGet);
        if (isRangeGet) {
            iv = contentCryptoScheme.adjustIV(iv, range[0]);
        } else {
            int tagLenExpected = contentCryptoScheme.getTagLengthInBits();
            if (tagLenExpected > 0 && tagLenExpected != (tagLenActual = Integer.parseInt(map.get(Headers.CRYPTO_TAG_LENGTH)))) {
                throw new AmazonClientException("Unsupported tag length: " + tagLenActual + ", expected: " + tagLenExpected);
            }
        }
        String keyWrapAlgo = map.get(Headers.CRYPTO_KEYWRAP_ALGORITHM);
        return new ContentCryptoMaterial(matdesc, cekWrapped, keyWrapAlgo, contentCryptoScheme.createCipherLite(cek(cekWrapped, keyWrapAlgo, materials, securityProvider), iv, 2, securityProvider));
    }

    static String parseInstructionFile(S3Object instructionFile) {
        try {
            return convertStreamToString(instructionFile.getObjectContent());
        } catch (Exception e) {
            throw new AmazonClientException("Error parsing JSON instruction file: " + e.getMessage());
        }
    }

    /* JADX INFO: finally extract failed */
    private static String convertStreamToString(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StringUtils.UTF8));
            while (true) {
                String line = reader.readLine();
                if (line != null) {
                    stringBuilder.append(line);
                } else {
                    inputStream.close();
                    return stringBuilder.toString();
                }
            }
        } catch (Throwable th) {
            inputStream.close();
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public CipherLite getCipherLite() {
        return this.cipherLite;
    }

    /* access modifiers changed from: package-private */
    public Map<String, String> getKEKMaterialsDescription() {
        return this.kekMaterialsDescription;
    }

    /* access modifiers changed from: package-private */
    public byte[] getEncryptedCEK() {
        return (byte[]) this.encryptedCEK.clone();
    }
}
