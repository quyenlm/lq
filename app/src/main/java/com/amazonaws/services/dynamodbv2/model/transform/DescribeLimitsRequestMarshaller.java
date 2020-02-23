package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.dynamodbv2.model.DescribeLimitsRequest;
import com.amazonaws.transform.Marshaller;
import com.appsflyer.share.Constants;
import java.io.ByteArrayInputStream;

public class DescribeLimitsRequestMarshaller implements Marshaller<Request<DescribeLimitsRequest>, DescribeLimitsRequest> {
    public Request<DescribeLimitsRequest> marshall(DescribeLimitsRequest describeLimitsRequest) {
        if (describeLimitsRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(DescribeLimitsRequest)");
        }
        Request<DescribeLimitsRequest> request = new DefaultRequest<>(describeLimitsRequest, "AmazonDynamoDB");
        request.addHeader("X-Amz-Target", "DynamoDB_20120810.DescribeLimits");
        request.setHttpMethod(HttpMethodName.POST);
        request.setResourcePath(Constants.URL_PATH_DELIMITER);
        request.addHeader("Content-Length", "0");
        request.setContent(new ByteArrayInputStream(new byte[0]));
        if (!request.getHeaders().containsKey("Content-Type")) {
            request.addHeader("Content-Type", "application/x-amz-json-1.0");
        }
        return request;
    }
}
