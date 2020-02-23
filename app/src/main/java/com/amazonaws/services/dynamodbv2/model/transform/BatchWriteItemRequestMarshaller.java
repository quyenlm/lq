package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import com.appsflyer.share.Constants;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

public class BatchWriteItemRequestMarshaller implements Marshaller<Request<BatchWriteItemRequest>, BatchWriteItemRequest> {
    public Request<BatchWriteItemRequest> marshall(BatchWriteItemRequest batchWriteItemRequest) {
        if (batchWriteItemRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(BatchWriteItemRequest)");
        }
        Request<BatchWriteItemRequest> request = new DefaultRequest<>(batchWriteItemRequest, "AmazonDynamoDB");
        request.addHeader("X-Amz-Target", "DynamoDB_20120810.BatchWriteItem");
        request.setHttpMethod(HttpMethodName.POST);
        request.setResourcePath(Constants.URL_PATH_DELIMITER);
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            jsonWriter.beginObject();
            if (batchWriteItemRequest.getRequestItems() != null) {
                Map<String, List<WriteRequest>> requestItems = batchWriteItemRequest.getRequestItems();
                jsonWriter.name("RequestItems");
                jsonWriter.beginObject();
                for (Map.Entry<String, List<WriteRequest>> requestItemsEntry : requestItems.entrySet()) {
                    List<WriteRequest> requestItemsValue = requestItemsEntry.getValue();
                    if (requestItemsValue != null) {
                        jsonWriter.name(requestItemsEntry.getKey());
                        jsonWriter.beginArray();
                        for (WriteRequest requestItemsValueItem : requestItemsValue) {
                            if (requestItemsValueItem != null) {
                                WriteRequestJsonMarshaller.getInstance().marshall(requestItemsValueItem, jsonWriter);
                            }
                        }
                        jsonWriter.endArray();
                    }
                }
                jsonWriter.endObject();
            }
            if (batchWriteItemRequest.getReturnConsumedCapacity() != null) {
                String returnConsumedCapacity = batchWriteItemRequest.getReturnConsumedCapacity();
                jsonWriter.name("ReturnConsumedCapacity");
                jsonWriter.value(returnConsumedCapacity);
            }
            if (batchWriteItemRequest.getReturnItemCollectionMetrics() != null) {
                String returnItemCollectionMetrics = batchWriteItemRequest.getReturnItemCollectionMetrics();
                jsonWriter.name("ReturnItemCollectionMetrics");
                jsonWriter.value(returnItemCollectionMetrics);
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
