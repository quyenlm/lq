package com.amazonaws.services.s3.internal;

public enum BucketNameUtils {
    ;
    
    private static final int MAX_BUCKET_NAME_LENGTH = 63;
    private static final int MIN_BUCKET_NAME_LENGTH = 3;

    public static void validateBucketName(String bucketName) {
        isValidV2BucketName(bucketName, true);
    }

    public static boolean isValidV2BucketName(String bucketName) {
        return isValidV2BucketName(bucketName, false);
    }

    public static boolean isDNSBucketName(String bucketName) {
        return isValidV2BucketName(bucketName);
    }

    private static boolean isValidV2BucketName(String bucketName, boolean throwOnError) {
        if (bucketName == null) {
            return exception(throwOnError, "Bucket name cannot be null");
        }
        if (bucketName.length() < 3 || bucketName.length() > 63) {
            return exception(throwOnError, "Bucket name should be between 3 and 63 characters long");
        }
        char previous = 0;
        int i = 0;
        while (i < bucketName.length()) {
            char next = bucketName.charAt(i);
            if (next >= 'A' && next <= 'Z') {
                return exception(throwOnError, "Bucket name should not contain uppercase characters");
            }
            if (next == ' ' || next == 9 || next == 13 || next == 10) {
                return exception(throwOnError, "Bucket name should not contain white space");
            }
            if (next != '.') {
                if (next == '-') {
                    if (previous == '.') {
                        return exception(throwOnError, "Bucket name should not contain dashes next to periods");
                    }
                } else if (next < '0' || ((next > '9' && next < 'a') || next > 'z')) {
                    return exception(throwOnError, "Bucket name should not contain '" + next + "'");
                }
                previous = next;
                i++;
            } else if (previous == '.') {
                return exception(throwOnError, "Bucket name should not contain two adjacent periods");
            } else {
                if (previous == '-') {
                    return exception(throwOnError, "Bucket name should not contain dashes next to periods");
                }
                return false;
            }
        }
        if (previous == '.' || previous == '-') {
            return exception(throwOnError, "Bucket name should not end with '-' or '.'");
        }
        return true;
    }

    private static boolean exception(boolean exception, String message) {
        if (!exception) {
            return false;
        }
        throw new IllegalArgumentException(message);
    }
}
