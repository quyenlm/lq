package com.amazonaws.services.s3.internal;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.Request;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.util.BinaryUtils;
import com.amazonaws.util.DateUtils;
import com.amazonaws.util.HttpUtils;
import com.amazonaws.util.Md5Utils;
import com.amazonaws.util.StringUtils;
import com.appsflyer.share.Constants;
import com.google.android.gms.gcm.Task;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.tp.a.h;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.net.ssl.SSLProtocolException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServiceUtils {
    public static final boolean APPEND_MODE = true;
    public static final boolean OVERWRITE_MODE = false;
    @Deprecated
    protected static final DateUtils dateUtils = new DateUtils();
    private static final Log log = LogFactory.getLog(ServiceUtils.class);

    public interface RetryableS3DownloadTask {
        S3Object getS3ObjectStream();

        boolean needIntegrityCheck();
    }

    public static Date parseIso8601Date(String dateString) {
        return DateUtils.parseISO8601Date(dateString);
    }

    public static String formatIso8601Date(Date date) {
        return DateUtils.formatISO8601Date(date);
    }

    public static Date parseRfc822Date(String dateString) {
        return DateUtils.parseRFC822Date(dateString);
    }

    public static String formatRfc822Date(Date date) {
        return DateUtils.formatRFC822Date(date);
    }

    public static boolean isMultipartUploadETag(String eTag) {
        return eTag.contains("-");
    }

    public static byte[] toByteArray(String s) {
        return s.getBytes(StringUtils.UTF8);
    }

    public static String removeQuotes(String s) {
        if (s == null) {
            return null;
        }
        String s2 = s.trim();
        if (s2.startsWith("\"")) {
            s2 = s2.substring(1);
        }
        if (s2.endsWith("\"")) {
            return s2.substring(0, s2.length() - 1);
        }
        return s2;
    }

    public static URL convertRequestToUrl(Request<?> request) {
        return convertRequestToUrl(request, false);
    }

    public static URL convertRequestToUrl(Request<?> request, boolean removeLeadingSlashInResourcePath) {
        String urlString;
        String resourcePath = HttpUtils.urlEncode(request.getResourcePath(), true);
        if (removeLeadingSlashInResourcePath && resourcePath.startsWith(Constants.URL_PATH_DELIMITER)) {
            resourcePath = resourcePath.substring(1);
        }
        String urlString2 = request.getEndpoint() + (Constants.URL_PATH_DELIMITER + resourcePath).replaceAll("(?<=/)/", "%2F");
        boolean firstParam = true;
        for (String param : request.getParameters().keySet()) {
            if (firstParam) {
                urlString = urlString2 + "?";
                firstParam = false;
            } else {
                urlString = urlString2 + HttpRequest.HTTP_REQ_ENTITY_JOIN;
            }
            urlString2 = urlString + param + HttpRequest.HTTP_REQ_ENTITY_MERGE + HttpUtils.urlEncode(request.getParameters().get(param), false);
        }
        try {
            return new URL(urlString2);
        } catch (MalformedURLException e) {
            throw new AmazonClientException("Unable to convert request to well formed URL: " + e.getMessage(), e);
        }
    }

    public static String join(List<String> strings) {
        String result = "";
        boolean first = true;
        for (String s : strings) {
            if (!first) {
                result = result + ", ";
            }
            result = result + s;
            first = false;
        }
        return result;
    }

    public static void downloadObjectToFile(S3Object s3Object, File destinationFile, boolean performIntegrityCheck, boolean appendData) {
        File parentDirectory = destinationFile.getParentFile();
        if (parentDirectory != null && !parentDirectory.exists()) {
            parentDirectory.mkdirs();
        }
        OutputStream outputStream = null;
        try {
            OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(destinationFile, appendData));
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
                        }
                    }
                }
                outputStream2.close();
                try {
                    s3Object.getObjectContent().close();
                } catch (Exception e2) {
                }
                byte[] clientSideHash = null;
                byte[] serverSideHash = null;
                try {
                    if (!isMultipartUploadETag(s3Object.getObjectMetadata().getETag())) {
                        clientSideHash = Md5Utils.computeMD5Hash((InputStream) new FileInputStream(destinationFile));
                        serverSideHash = BinaryUtils.fromHex(s3Object.getObjectMetadata().getETag());
                    }
                } catch (Exception e3) {
                    log.warn("Unable to calculate MD5 hash to validate download: " + e3.getMessage(), e3);
                }
                if (performIntegrityCheck && clientSideHash != null && serverSideHash != null && !Arrays.equals(clientSideHash, serverSideHash)) {
                    throw new AmazonClientException("Unable to verify integrity of data download.  Client calculated content hash didn't match hash calculated by Amazon S3.  The data stored in '" + destinationFile.getAbsolutePath() + "' may be corrupt.");
                }
            } catch (IOException e4) {
                e = e4;
                outputStream = outputStream2;
                try {
                    s3Object.getObjectContent().abort();
                    throw new AmazonClientException("Unable to store object contents to disk: " + e.getMessage(), e);
                } catch (Throwable th) {
                    th = th;
                    try {
                        outputStream.close();
                    } catch (Exception e5) {
                    }
                    try {
                        s3Object.getObjectContent().close();
                    } catch (Exception e6) {
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
        } catch (IOException e7) {
            e = e7;
            s3Object.getObjectContent().abort();
            throw new AmazonClientException("Unable to store object contents to disk: " + e.getMessage(), e);
        }
    }

    public static S3Object retryableDownloadS3ObjectToFile(File file, RetryableS3DownloadTask retryableS3DownloadTask, boolean appendData) {
        boolean needRetry;
        S3Object s3Object;
        boolean hasRetried = false;
        do {
            needRetry = false;
            s3Object = retryableS3DownloadTask.getS3ObjectStream();
            if (s3Object == null) {
                return null;
            }
            try {
                downloadObjectToFile(s3Object, file, retryableS3DownloadTask.needIntegrityCheck(), appendData);
                continue;
            } catch (AmazonClientException ace) {
                if (!ace.isRetryable()) {
                    throw ace;
                } else if ((ace.getCause() instanceof SocketException) || (ace.getCause() instanceof SSLProtocolException)) {
                    throw ace;
                } else {
                    needRetry = true;
                    if (hasRetried) {
                        throw ace;
                    }
                    log.info("Retry the download of object " + s3Object.getKey() + " (bucket " + s3Object.getBucketName() + h.b, ace);
                    hasRetried = true;
                    continue;
                }
            } finally {
                s3Object.getObjectContent().abort();
            }
        } while (needRetry);
        return s3Object;
    }

    public static boolean skipMd5CheckPerResponse(ObjectMetadata metadata) {
        if (metadata == null) {
            return false;
        }
        boolean sseKMS = ObjectMetadata.KMS_SERVER_SIDE_ENCRYPTION.equals(metadata.getSSEAlgorithm());
        if (metadata.getSSECustomerAlgorithm() != null || sseKMS) {
            return true;
        }
        return false;
    }

    public static boolean skipMd5CheckPerRequest(AmazonWebServiceRequest request) {
        if (System.getProperty("com.amazonaws.services.s3.disableGetObjectMD5Validation") != null) {
            return true;
        }
        if (request instanceof GetObjectRequest) {
            GetObjectRequest getObjectRequest = (GetObjectRequest) request;
            if (!(getObjectRequest.getRange() == null && getObjectRequest.getSSECustomerKey() == null)) {
                return true;
            }
        } else if (request instanceof PutObjectRequest) {
            PutObjectRequest putObjectRequest = (PutObjectRequest) request;
            ObjectMetadata om = putObjectRequest.getMetadata();
            if ((om == null || om.getSSEAlgorithm() == null) && putObjectRequest.getSSECustomerKey() == null) {
                return false;
            }
            return true;
        } else if (request instanceof UploadPartRequest) {
            if (((UploadPartRequest) request).getSSECustomerKey() == null) {
                return false;
            }
            return true;
        }
        return false;
    }
}
