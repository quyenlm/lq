package com.amazonaws.services.s3.internal.crypto;

public class JceEncryptionConstants {
    public static int SYMMETRIC_CIPHER_BLOCK_SIZE = 16;
    public static String SYMMETRIC_CIPHER_METHOD = "AES/CBC/PKCS5Padding";
    public static String SYMMETRIC_KEY_ALGORITHM = "AES";
    public static int SYMMETRIC_KEY_LENGTH = 256;
}
