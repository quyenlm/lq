package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest;
import com.amazonaws.services.dynamodbv2.model.KeysAndAttributes;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import com.appsflyer.share.Constants;
import java.io.StringWriter;
import java.util.Map;

public class BatchGetItemRequestMarshaller implements Marshaller<Request<BatchGetItemRequest>, BatchGetItemRequest> {
    public Request<BatchGetItemRequest> marshall(BatchGetItemRequest batchGetItemRequest) {
        if (batchGetItemRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(BatchGetItemRequest)");
        }
        Request<BatchGetItemRequest> request = new DefaultRequest<>(batchGetItemRequest, "AmazonDynamoDB");
        request.addHeader("X-Amz-Target", "DynamoDB_20120810.BatchGetItem");
        request.setHttpMethod(HttpMethodName.POST);
        request.setResourcePath(Constants.URL_PATH_DELIMITER);
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            jsonWriter.beginObject();
            if (batchGetItemRequest.getRequestItems() != null) {
                Map<String, KeysAndAttributes> requestItems = batchGetItemRequest.getRequestItems();
                jsonWriter.name("RequestItems");
                jsonWriter.beginObject();
                for (Map.Entry<String, KeysAndAttributes> requestItemsEntry : requestItems.entrySet()) {
                    KeysAndAttributes requestItemsValue = requestItemsEntry.getValue();
                    if (requestItemsValue != null) {
                        jsonWriter.name(requestItemsEntry.getKey());
                        KeysAndAttributesJsonMarshaller.getInstance().marshall(requestItemsValue, jsonWriter);
                    }
                }
                jsonWriter.endObject();
            }
            if (batchGetItemRequest.getReturnConsumedCapacity() != null) {
                String returnConsumedCapacity = batchGetItemRequest.getReturnConsumedCapacity();
                jsonWriter.name("ReturnConsumedCapacity");
                jsonWriter.value(returnConsumedCapacity);
            }
            jsonWriter.endObject();
            jsonWriter.close();
            String snippet = stringWriter.toString();
            byte[] content = snippet.getBytes(StringUtils.UTF8);
            request.setContent(new StringInputStream(snippet));
            request.addHeader("Content-Length", Integer.toString(content.length));
            if (!request.getHeaders().containsKey("Content-Type")) {
                request.addHeader("Content-Type", "application/x-amz-json-1.0");
            }
            return request;
        } catch (Throwable t) {
            throw new AmazonClientException("Unable to marshall request to JSON: " + t.getMessage(), t);
        }
    }
}
