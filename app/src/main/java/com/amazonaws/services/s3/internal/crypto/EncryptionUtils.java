package com.amazonaws.services.s3.internal.crypto;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.internal.InputSubstream;
import com.amazonaws.services.s3.internal.RepeatableCipherInputStream;
import com.amazonaws.services.s3.internal.RepeatableFileInputStream;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.EncryptionMaterials;
import com.amazonaws.services.s3.model.EncryptionMaterialsAccessor;
import com.amazonaws.services.s3.model.EncryptionMaterialsProvider;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.StaticEncryptionMaterialsProvider;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.util.Mimetypes;
import com.amazonaws.util.Base64;
import com.amazonaws.util.LengthCheckInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.JsonUtils;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtils {
    static final String INSTRUCTION_SUFFIX = ".instruction";

    @Deprecated
    public static PutObjectRequest encryptRequestUsingMetadata(PutObjectRequest request, EncryptionMaterials materials, Provider cryptoProvider) {
        EncryptionInstruction instruction = generateInstruction(materials, cryptoProvider);
        PutObjectRequest encryptedObjectRequest = encryptRequestUsingInstruction(request, instruction);
        updateMetadataWithEncryptionInstruction(request, instruction);
        return encryptedObjectRequest;
    }

    @Deprecated
    public static S3Object decryptObjectUsingMetadata(S3Object object, EncryptionMaterials materials, Provider cryptoProvider) {
        return decryptObjectUsingInstruction(object, buildInstructionFromObjectMetadata(object, materials, cryptoProvider));
    }

    @Deprecated
    public static EncryptionInstruction generateInstruction(EncryptionMaterials materials, Provider cryptoProvider) {
        return generateInstruction((EncryptionMaterialsProvider) new StaticEncryptionMaterialsProvider(materials), cryptoProvider);
    }

    public static EncryptionInstruction generateInstruction(EncryptionMaterialsProvider materialsProvider, Provider cryptoProvider) {
        return buildInstruction(materialsProvider.getEncryptionMaterials(), cryptoProvider);
    }

    public static EncryptionInstruction generateInstruction(EncryptionMaterialsProvider materialsProvider, Map<String, String> materialsDescription, Provider cryptoProvider) {
        return buildInstruction(materialsProvider.getEncryptionMaterials(materialsDescription), cryptoProvider);
    }

    private static EncryptionInstruction buildInstruction(EncryptionMaterials materials, Provider cryptoProvider) {
        SecretKey envelopeSymmetricKey = generateOneTimeUseSymmetricKey();
        CipherFactory cipherFactory = new CipherFactory(envelopeSymmetricKey, 1, (byte[]) null, cryptoProvider);
        return new EncryptionInstruction(materials.getMaterialsDescription(), getEncryptedSymmetricKey(envelopeSymmetricKey, materials, cryptoProvider), envelopeSymmetricKey, cipherFactory);
    }

    @Deprecated
    public static EncryptionInstruction buildInstructionFromInstructionFile(S3Object instructionFile, EncryptionMaterials materials, Provider cryptoProvider) {
        return buildInstructionFromInstructionFile(instructionFile, (EncryptionMaterialsProvider) new StaticEncryptionMaterialsProvider(materials), cryptoProvider);
    }

    public static EncryptionInstruction buildInstructionFromInstructionFile(S3Object instructionFile, EncryptionMaterialsProvider materialsProvider, Provider cryptoProvider) {
        Map<String, String> instructionJSON = parseJSONInstruction(instructionFile);
        Map<String, String> materialsDescription = convertJSONToMap(instructionJSON.get(Headers.MATERIALS_DESCRIPTION));
        byte[] encryptedSymmetricKey = Base64.decode(instructionJSON.get(Headers.CRYPTO_KEY));
        byte[] iv = Base64.decode(instructionJSON.get(Headers.CRYPTO_IV));
        if (encryptedSymmetricKey == null || iv == null) {
            throw new AmazonClientException(String.format("Necessary encryption info not found in the instruction file '%s' in bucket '%s'", new Object[]{instructionFile.getKey(), instructionFile.getBucketName()}));
        }
        EncryptionMaterials materials = retrieveOriginalMaterials(materialsDescription, materialsProvider);
        if (materials == null) {
            throw new AmazonClientException(String.format("Unable to retrieve the encryption materials that originally encrypted object corresponding to instruction file '%s' in bucket '%s'.", new Object[]{instructionFile.getKey(), instructionFile.getBucketName()}));
        }
        SecretKey symmetricKey = getDecryptedSymmetricKey(encryptedSymmetricKey, materials, cryptoProvider);
        return new EncryptionInstruction(materialsDescription, encryptedSymmetricKey, symmetricKey, new CipherFactory(symmetricKey, 2, iv, cryptoProvider));
    }

    @Deprecated
    public static EncryptionInstruction buildInstructionFromObjectMetadata(S3Object object, EncryptionMaterials materials, Provider cryptoProvider) {
        return buildInstructionFromObjectMetadata(object, (EncryptionMaterialsProvider) new StaticEncryptionMaterialsProvider(materials), cryptoProvider);
    }

    public static EncryptionInstruction buildInstructionFromObjectMetadata(S3Object object, EncryptionMaterialsProvider materialsProvider, Provider cryptoProvider) {
        ObjectMetadata metadata = object.getObjectMetadata();
        byte[] encryptedSymmetricKeyBytes = getCryptoBytesFromMetadata(Headers.CRYPTO_KEY, metadata);
        byte[] initVectorBytes = getCryptoBytesFromMetadata(Headers.CRYPTO_IV, metadata);
        Map<String, String> materialsDescription = convertJSONToMap(getStringFromMetadata(Headers.MATERIALS_DESCRIPTION, metadata));
        if (encryptedSymmetricKeyBytes == null || initVectorBytes == null) {
            throw new AmazonClientException(String.format("Necessary encryption info not found in the headers of file '%s' in bucket '%s'", new Object[]{object.getKey(), object.getBucketName()}));
        }
        EncryptionMaterials materials = retrieveOriginalMaterials(materialsDescription, materialsProvider);
        if (materials == null) {
            throw new AmazonClientException(String.format("Unable to retrieve the encryption materials that originally encrypted file '%s' in bucket '%s'.", new Object[]{object.getKey(), object.getBucketName()}));
        }
        SecretKey symmetricKey = getDecryptedSymmetricKey(encryptedSymmetricKeyBytes, materials, cryptoProvider);
        return new EncryptionInstruction(materialsDescription, encryptedSymmetricKeyBytes, symmetricKey, new CipherFactory(symmetricKey, 2, initVectorBytes, cryptoProvider));
    }

    public static PutObjectRequest encryptRequestUsingInstruction(PutObjectRequest request, EncryptionInstruction instruction) {
        ObjectMetadata metadata = request.getMetadata();
        if (metadata == null) {
            metadata = new ObjectMetadata();
        }
        if (metadata.getContentMD5() != null) {
            metadata.addUserMetadata(Headers.UNENCRYPTED_CONTENT_MD5, metadata.getContentMD5());
        }
        metadata.setContentMD5((String) null);
        long plaintextLength = getUnencryptedContentLength(request, metadata);
        if (plaintextLength >= 0) {
            metadata.addUserMetadata(Headers.UNENCRYPTED_CONTENT_LENGTH, Long.toString(plaintextLength));
        }
        long cryptoContentLength = calculateCryptoContentLength(instruction.getSymmetricCipher(), request, metadata);
        if (cryptoContentLength >= 0) {
            metadata.setContentLength(cryptoContentLength);
        }
        request.setMetadata(metadata);
        request.setInputStream(getEncryptedInputStream(request, instruction.getCipherFactory(), plaintextLength));
        request.setFile((File) null);
        return request;
    }

    public static S3Object decryptObjectUsingInstruction(S3Object object, EncryptionInstruction instruction) {
        S3ObjectInputStream objectContent = object.getObjectContent();
        object.setObjectContent(new S3ObjectInputStream(new RepeatableCipherInputStream(objectContent, instruction.getCipherFactory()), objectContent.getHttpRequest()));
        return object;
    }

    public static PutObjectRequest createInstructionPutRequest(PutObjectRequest request, EncryptionInstruction instruction) {
        byte[] instructionBytes = JsonUtils.mapToString(convertInstructionToJSONObject(instruction)).getBytes(StringUtils.UTF8);
        InputStream instructionInputStream = new ByteArrayInputStream(instructionBytes);
        ObjectMetadata metadata = request.getMetadata();
        metadata.setContentLength((long) instructionBytes.length);
        metadata.addUserMetadata(Headers.CRYPTO_INSTRUCTION_FILE, "");
        request.setKey(request.getKey() + INSTRUCTION_SUFFIX);
        request.setMetadata(metadata);
        request.setInputStream(instructionInputStream);
        return request;
    }

    public static PutObjectRequest createInstructionPutRequest(String bucketName, String key, EncryptionInstruction instruction) {
        byte[] instructionBytes = JsonUtils.mapToString(convertInstructionToJSONObject(instruction)).getBytes(StringUtils.UTF8);
        InputStream instructionInputStream = new ByteArrayInputStream(instructionBytes);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength((long) instructionBytes.length);
        metadata.addUserMetadata(Headers.CRYPTO_INSTRUCTION_FILE, "");
        return new PutObjectRequest(bucketName, key + INSTRUCTION_SUFFIX, instructionInputStream, metadata);
    }

    public static GetObjectRequest createInstructionGetRequest(GetObjectRequest request) {
        return new GetObjectRequest(request.getBucketName(), request.getKey() + INSTRUCTION_SUFFIX, request.getVersionId());
    }

    public static DeleteObjectRequest createInstructionDeleteObjectRequest(DeleteObjectRequest request) {
        return new DeleteObjectRequest(request.getBucketName(), request.getKey() + INSTRUCTION_SUFFIX);
    }

    public static boolean isEncryptionInfoInMetadata(S3Object retrievedObject) {
        Map<String, String> metadata = retrievedObject.getObjectMetadata().getUserMetadata();
        return metadata != null && metadata.containsKey(Headers.CRYPTO_IV) && metadata.containsKey(Headers.CRYPTO_KEY);
    }

    public static boolean isEncryptionInfoInInstructionFile(S3Object instructionFile) {
        Map<String, String> metadata;
        if (instructionFile == null || (metadata = instructionFile.getObjectMetadata().getUserMetadata()) == null) {
            return false;
        }
        return metadata.containsKey(Headers.CRYPTO_INSTRUCTION_FILE);
    }

    public static long[] getAdjustedCryptoRange(long[] range) {
        if (range == null || range[0] > range[1]) {
            return null;
        }
        return new long[]{getCipherBlockLowerBound(range[0]), getCipherBlockUpperBound(range[1])};
    }

    public static S3Object adjustOutputToDesiredRange(S3Object object, long[] range) {
        if (range != null && range[0] <= range[1]) {
            try {
                S3ObjectInputStream objectContent = object.getObjectContent();
                object.setObjectContent(new S3ObjectInputStream(new AdjustedRangeInputStream(objectContent, range[0], range[1]), objectContent.getHttpRequest()));
            } catch (IOException e) {
                throw new AmazonClientException("Error adjusting output to desired byte range: " + e.getMessage());
            }
        }
        return object;
    }

    public static SecretKey generateOneTimeUseSymmetricKey() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(JceEncryptionConstants.SYMMETRIC_KEY_ALGORITHM);
            generator.init(JceEncryptionConstants.SYMMETRIC_KEY_LENGTH, new SecureRandom());
            return generator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new AmazonClientException("Unable to generate envelope symmetric key:" + e.getMessage(), e);
        }
    }

    public static Cipher createSymmetricCipher(SecretKey symmetricCryptoKey, int encryptMode, Provider cryptoProvider, byte[] initVector) {
        Cipher cipher;
        if (cryptoProvider != null) {
            try {
                cipher = Cipher.getInstance(JceEncryptionConstants.SYMMETRIC_CIPHER_METHOD, cryptoProvider);
            } catch (Exception e) {
                throw new AmazonClientException("Unable to build cipher: " + e.getMessage() + "\nMake sure you have the JCE unlimited strength policy files installed and configured for your JVM: http://www.ngs.ac.uk/tools/jcepolicyfiles", e);
            }
        } else {
            cipher = Cipher.getInstance(JceEncryptionConstants.SYMMETRIC_CIPHER_METHOD);
        }
        if (initVector != null) {
            cipher.init(encryptMode, symmetricCryptoKey, new IvParameterSpec(initVector));
        } else {
            cipher.init(encryptMode, symmetricCryptoKey);
        }
        return cipher;
    }

    public static byte[] getEncryptedSymmetricKey(SecretKey toBeEncrypted, EncryptionMaterials materials, Provider cryptoProvider) {
        Key keyToDoEncryption;
        Cipher cipher;
        if (materials.getKeyPair() != null) {
            keyToDoEncryption = materials.getKeyPair().getPublic();
        } else {
            keyToDoEncryption = materials.getSymmetricKey();
        }
        try {
            byte[] toBeEncryptedBytes = toBeEncrypted.getEncoded();
            if (cryptoProvider != null) {
                cipher = Cipher.getInstance(keyToDoEncryption.getAlgorithm(), cryptoProvider);
            } else {
                cipher = Cipher.getInstance(keyToDoEncryption.getAlgorithm());
            }
            cipher.init(1, keyToDoEncryption);
            return cipher.doFinal(toBeEncryptedBytes);
        } catch (Exception e) {
            throw new AmazonClientException("Unable to encrypt symmetric key: " + e.getMessage(), e);
        }
    }

    private static SecretKey getDecryptedSymmetricKey(byte[] encryptedSymmetricKeyBytes, EncryptionMaterials materials, Provider cryptoProvider) {
        Key keyToDoDecryption;
        Cipher cipher;
        if (materials.getKeyPair() != null) {
            keyToDoDecryption = materials.getKeyPair().getPrivate();
        } else {
            keyToDoDecryption = materials.getSymmetricKey();
        }
        if (cryptoProvider != null) {
            try {
                cipher = Cipher.getInstance(keyToDoDecryption.getAlgorithm(), cryptoProvider);
            } catch (Exception e) {
                throw new AmazonClientException("Unable to decrypt symmetric key from object metadata : " + e.getMessage(), e);
            }
        } else {
            cipher = Cipher.getInstance(keyToDoDecryption.getAlgorithm());
        }
        cipher.init(2, keyToDoDecryption);
        return new SecretKeySpec(cipher.doFinal(encryptedSymmetricKeyBytes), JceEncryptionConstants.SYMMETRIC_KEY_ALGORITHM);
    }

    private static InputStream getEncryptedInputStream(PutObjectRequest request, CipherFactory cipherFactory, long plaintextLength) {
        InputStream is;
        InputStream is2;
        try {
            InputStream is3 = request.getInputStream();
            if (request.getFile() != null) {
                is = new RepeatableFileInputStream(request.getFile());
            } else {
                is = is3;
            }
            if (plaintextLength > -1) {
                is2 = new LengthCheckInputStream(is, plaintextLength, false);
            } else {
                is2 = is;
            }
            return new RepeatableCipherInputStream(is2, cipherFactory);
        } catch (Exception e) {
            throw new AmazonClientException("Unable to create cipher input stream: " + e.getMessage(), e);
        }
    }

    public static ByteRangeCapturingInputStream getEncryptedInputStream(UploadPartRequest request, CipherFactory cipherFactory) {
        InputStream originalInputStream;
        try {
            InputStream originalInputStream2 = request.getInputStream();
            if (request.getFile() != null) {
                originalInputStream = new InputSubstream(new RepeatableFileInputStream(request.getFile()), request.getFileOffset(), request.getPartSize(), request.isLastPart());
            } else {
                originalInputStream = originalInputStream2;
            }
            InputStream originalInputStream3 = new RepeatableCipherInputStream(originalInputStream, cipherFactory);
            if (!request.isLastPart()) {
                originalInputStream3 = new InputSubstream(originalInputStream3, 0, request.getPartSize(), false);
            }
            long partSize = request.getPartSize();
            return new ByteRangeCapturingInputStream(originalInputStream3, partSize - ((long) cipherFactory.createCipher().getBlockSize()), partSize);
        } catch (Exception e) {
            throw new AmazonClientException("Unable to create cipher input stream: " + e.getMessage(), e);
        }
    }

    private static byte[] getCryptoBytesFromMetadata(String headerName, ObjectMetadata metadata) throws NullPointerException {
        Map<String, String> userMetadata = metadata.getUserMetadata();
        if (userMetadata == null || !userMetadata.containsKey(headerName)) {
            return null;
        }
        return Base64.decode(userMetadata.get(headerName));
    }

    private static String getStringFromMetadata(String headerName, ObjectMetadata metadata) throws NullPointerException {
        Map<String, String> userMetadata = metadata.getUserMetadata();
        if (userMetadata == null || !userMetadata.containsKey(headerName)) {
            return null;
        }
        return userMetadata.get(headerName);
    }

    private static Map<String, String> convertJSONToMap(String descriptionJSONString) {
        if (descriptionJSONString == null) {
            return null;
        }
        try {
            return JsonUtils.jsonToMap(descriptionJSONString);
        } catch (AmazonClientException ace) {
            throw new AmazonClientException("Unable to parse encryption materials description from metadata :" + ace.getMessage());
        }
    }

    public static void updateMetadataWithEncryptionInstruction(PutObjectRequest request, EncryptionInstruction instruction) {
        byte[] keyBytesToStoreInMetadata = instruction.getEncryptedSymmetricKey();
        Cipher symmetricCipher = instruction.getSymmetricCipher();
        Map<String, String> materialsDescription = instruction.getMaterialsDescription();
        ObjectMetadata metadata = request.getMetadata();
        if (metadata == null) {
            metadata = new ObjectMetadata();
        }
        if (request.getFile() != null) {
            metadata.setContentType(Mimetypes.getInstance().getMimetype(request.getFile()));
        }
        updateMetadata(metadata, keyBytesToStoreInMetadata, symmetricCipher, materialsDescription);
        request.setMetadata(metadata);
    }

    private static void updateMetadata(ObjectMetadata metadata, byte[] keyBytesToStoreInMetadata, Cipher symmetricCipher, Map<String, String> materialsDescription) {
        if (keyBytesToStoreInMetadata != null) {
            metadata.addUserMetadata(Headers.CRYPTO_KEY, Base64.encodeAsString(keyBytesToStoreInMetadata));
        }
        metadata.addUserMetadata(Headers.CRYPTO_IV, Base64.encodeAsString(symmetricCipher.getIV()));
        metadata.addUserMetadata(Headers.MATERIALS_DESCRIPTION, JsonUtils.mapToString(materialsDescription));
    }

    public static ObjectMetadata updateMetadataWithEncryptionInfo(InitiateMultipartUploadRequest request, byte[] keyBytesToStoreInMetadata, Cipher symmetricCipher, Map<String, String> materialsDescription) {
        ObjectMetadata metadata = request.getObjectMetadata();
        if (metadata == null) {
            metadata = new ObjectMetadata();
        }
        updateMetadata(metadata, keyBytesToStoreInMetadata, symmetricCipher, materialsDescription);
        return metadata;
    }

    private static EncryptionMaterials retrieveOriginalMaterials(Map<String, String> materialsDescription, EncryptionMaterialsAccessor accessor) {
        if (accessor == null) {
            return null;
        }
        return accessor.getEncryptionMaterials(materialsDescription);
    }

    private static long calculateCryptoContentLength(Cipher symmetricCipher, PutObjectRequest request, ObjectMetadata metadata) {
        long plaintextLength = getUnencryptedContentLength(request, metadata);
        if (plaintextLength < 0) {
            return -1;
        }
        long cipherBlockSize = (long) symmetricCipher.getBlockSize();
        return plaintextLength + (cipherBlockSize - (plaintextLength % cipherBlockSize));
    }

    public static long calculateCryptoContentLength(Cipher symmetricCipher, UploadPartRequest request) {
        long plaintextLength;
        if (request.getFile() != null) {
            if (request.getPartSize() > 0) {
                plaintextLength = request.getPartSize();
            } else {
                plaintextLength = request.getFile().length();
            }
        } else if (request.getInputStream() == null) {
            return -1;
        } else {
            plaintextLength = request.getPartSize();
        }
        long cipherBlockSize = (long) symmetricCipher.getBlockSize();
        return plaintextLength + (cipherBlockSize - (plaintextLength % cipherBlockSize));
    }

    private static long getUnencryptedContentLength(PutObjectRequest request, ObjectMetadata metadata) {
        if (request.getFile() != null) {
            return request.getFile().length();
        }
        if (request.getInputStream() == null || metadata.getRawMetadataValue("Content-Length") == null) {
            return -1;
        }
        return metadata.getContentLength();
    }

    private static Map<String, String> convertInstructionToJSONObject(EncryptionInstruction instruction) {
        Map<String, String> instructionJSON = new HashMap<>();
        instructionJSON.put(Headers.MATERIALS_DESCRIPTION, JsonUtils.mapToString(instruction.getMaterialsDescription()));
        instructionJSON.put(Headers.CRYPTO_KEY, Base64.encodeAsString(instruction.getEncryptedSymmetricKey()));
        instructionJSON.put(Headers.CRYPTO_IV, Base64.encodeAsString(instruction.getSymmetricCipher().getIV()));
        return instructionJSON;
    }

    private static Map<String, String> parseJSONInstruction(S3Object instructionObject) {
        try {
            return JsonUtils.jsonToMap(convertStreamToString(instructionObject.getObjectContent()));
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

    private static long getCipherBlockLowerBound(long leftmostBytePosition) {
        long cipherBlockSize = (long) JceEncryptionConstants.SYMMETRIC_CIPHER_BLOCK_SIZE;
        long lowerBound = (leftmostBytePosition - (leftmostBytePosition % cipherBlockSize)) - cipherBlockSize;
        if (lowerBound < 0) {
            return 0;
        }
        return lowerBound;
    }

    private static long getCipherBlockUpperBound(long rightmostBytePosition) {
        long cipherBlockSize = (long) JceEncryptionConstants.SYMMETRIC_CIPHER_BLOCK_SIZE;
        return rightmostBytePosition + (cipherBlockSize - (rightmostBytePosition % cipherBlockSize)) + cipherBlockSize;
    }
}
