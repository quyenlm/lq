package com.amazonaws.services.s3.internal;

import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.util.StringUtils;
import java.io.InputStream;

public class S3StringResponseHandler extends AbstractS3ResponseHandler<String> {
    public AmazonWebServiceResponse<String> handle(HttpResponse response) throws Exception {
        AmazonWebServiceResponse<String> awsResponse = parseResponseMetadata(response);
        byte[] buffer = new byte[1024];
        StringBuilder builder = new StringBuilder();
        InputStream content = response.getContent();
        while (true) {
            int bytesRead = content.read(buffer);
            if (bytesRead > 0) {
                builder.append(new String(buffer, 0, bytesRead, StringUtils.UTF8));
            } else {
                awsResponse.setResult(builder.toString());
                return awsResponse;
            }
        }
    }
}
