package com.amazonaws.services.s3.internal;

import com.amazonaws.AmazonClientException;
import com.amazonaws.Request;
import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AwsChunkedEncodingInputStream;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.util.BinaryUtils;
import java.io.IOException;
import java.io.InputStream;

public class AWSS3V4Signer extends AWS4Signer {
    private static final String CONTENT_SHA_256 = "STREAMING-AWS4-HMAC-SHA256-PAYLOAD";

    public AWSS3V4Signer() {
        super(false);
    }

    /* access modifiers changed from: protected */
    public void processRequestPayload(Request<?> request, AWS4Signer.HeaderSigningResult headerSigningResult) {
        if (useChunkEncoding(request)) {
            request.setContent(new AwsChunkedEncodingInputStream(request.getContent(), headerSigningResult.getKSigning(), headerSigningResult.getDateTime(), headerSigningResult.getScope(), BinaryUtils.toHex(headerSigningResult.getSignature()), this));
        }
    }

    /* access modifiers changed from: protected */
    public String calculateContentHashPresign(Request<?> request) {
        return "UNSIGNED-PAYLOAD";
    }

    /* access modifiers changed from: protected */
    public String calculateContentHash(Request<?> request) {
        long originalContentLength;
        request.addHeader("x-amz-content-sha256", "required");
        if (!useChunkEncoding(request)) {
            return super.calculateContentHash(request);
        }
        String contentLength = request.getHeaders().get("Content-Length");
        if (contentLength != null) {
            originalContentLength = Long.parseLong(contentLength);
        } else {
            try {
                originalContentLength = getContentLength(request);
            } catch (IOException e) {
                throw new AmazonClientException("Cannot get the content-lenght of the request content.", e);
            }
        }
        request.addHeader("x-amz-decoded-content-length", Long.toString(originalContentLength));
        request.addHeader("Content-Length", Long.toString(AwsChunkedEncodingInputStream.calculateStreamContentLength(originalContentLength)));
        return CONTENT_SHA_256;
    }

    private static boolean useChunkEncoding(Request<?> request) {
        if ((request.getOriginalRequest() instanceof PutObjectRequest) || (request.getOriginalRequest() instanceof UploadPartRequest)) {
            return true;
        }
        return false;
    }

    static long getContentLength(Request<?> request) throws IOException {
        InputStream content = request.getContent();
        if (!content.markSupported()) {
            throw new AmazonClientException("Failed to get content length");
        }
        long contentLength = 0;
        byte[] tmp = new byte[4096];
        content.mark(-1);
        while (true) {
            int read = content.read(tmp);
            if (read != -1) {
                contentLength += (long) read;
            } else {
                content.reset();
                return contentLength;
            }
        }
    }
}
