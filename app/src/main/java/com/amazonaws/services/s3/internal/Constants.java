package com.amazonaws.services.s3.internal;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.services.s3.AmazonS3Client;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Constants {
    public static final int BUCKET_ACCESS_FORBIDDEN_STATUS_CODE = 403;
    public static final int BUCKET_REDIRECT_STATUS_CODE = 301;
    public static String DEFAULT_ENCODING = "UTF-8";
    public static final int DEFAULT_STREAM_BUFFER_SIZE = 131072;
    public static final int FAILED_PRECONDITION_STATUS_CODE = 412;
    public static final long GB = 1073741824;
    public static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    public static final int KB = 1024;
    public static final int MAXIMUM_UPLOAD_PARTS = 10000;
    public static final int MB = 1048576;
    public static final int NO_SUCH_BUCKET_STATUS_CODE = 404;
    public static final String NULL_VERSION_ID = "null";
    public static final String REQUESTER_PAYS = "requester";
    public static final String S3_ACCELERATE_HOSTNAME = "s3-accelerate.amazonaws.com";
    public static String S3_HOSTNAME = "s3.amazonaws.com";
    public static String S3_SERVICE_NAME = "Amazon S3";
    public static final String XML_NAMESPACE = "http://s3.amazonaws.com/doc/2006-03-01/";
    private static Log log = LogFactory.getLog(AmazonS3Client.class);

    public static int getStreamBufferSize() {
        String bufferSizeOverride = System.getProperty(SDKGlobalConfiguration.DEFAULT_S3_STREAM_BUFFER_SIZE);
        if (bufferSizeOverride == null) {
            return 131072;
        }
        try {
            return Integer.parseInt(bufferSizeOverride);
        } catch (Exception e) {
            log.warn("Unable to parse buffer size override from value: " + bufferSizeOverride);
            return 131072;
        }
    }
}
