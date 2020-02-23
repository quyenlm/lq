package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import com.appsflyer.share.Constants;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

public class GetItemRequestMarshaller implements Marshaller<Request<GetItemRequest>, GetItemRequest> {
    public Request<GetItemRequest> marshall(GetItemRequest getItemRequest) {
        if (getItemRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(GetItemRequest)");
        }
        Request<GetItemRequest> request = new DefaultRequest<>(getItemRequest, "AmazonDynamoDB");
        request.addHeader("X-Amz-Target", "DynamoDB_20120810.GetItem");
        request.setHttpMethod(HttpMethodName.POST);
        request.setResourcePath(Constants.URL_PATH_DELIMITER);
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            jsonWriter.beginObject();
            if (getItemRequest.getTableName() != null) {
                String tableName = getItemRequest.getTableName();
                jsonWriter.name("TableName");
                jsonWriter.value(tableName);
            }
            if (getItemRequest.getKey() != null) {
                Map<String, AttributeValue> key = getItemRequest.getKey();
                jsonWriter.name("Key");
                jsonWriter.beginObject();
                for (Map.Entry<String, AttributeValue> keyEntry : key.entrySet()) {
                    AttributeValue keyValue = keyEntry.getValue();
                    if (keyValue != null) {
                        jsonWriter.name(keyEntry.getKey());
                        AttributeValueJsonMarshaller.getInstance().marshall(keyValue, jsonWriter);
                    }
                }
                jsonWriter.endObject();
            }
            if (getItemRequest.getAttributesToGet() != null) {
                List<String> attributesToGet = getItemRequest.getAttributesToGet();
                jsonWriter.name("AttributesToGet");
                jsonWriter.beginArray();
                for (String attributesToGetItem : attributesToGet) {
                    if (attributesToGetItem != null) {
                        jsonWriter.value(attributesToGetItem);
                    }
                }
                jsonWriter.endArray();
            }
            if (getItemRequest.getConsistentRead() != null) {
                Boolean consistentRead = getItemRequest.getConsistentRead();
                jsonWriter.name("ConsistentRead");
                jsonWriter.value(consistentRead.booleanValue());
            }
            if (getItemRequest.getReturnConsumedCapacity() != null) {
                String returnConsumedCapacity = getItemRequest.getReturnConsumedCapacity();
                jsonWriter.name("ReturnConsumedCapacity");
                jsonWriter.value(returnConsumedCapacity);
            }
            if (getItemRequest.getProjectionExpression() != null) {
                String projectionExpression = getItemRequest.getProjectionExpression();
                jsonWriter.name("ProjectionExpression");
                jsonWriter.value(projectionExpression);
            }
            if (getItemRequest.getExpressionAttributeNames() != null) {
                Map<String, String> expressionAttributeNames = getItemRequest.getExpressionAttributeNames();
                jsonWriter.name("ExpressionAttributeNames");
                jsonWriter.beginObject();
                for (Map.Entry<String, String> expressionAttributeNamesEntry : expressionAttributeNames.entrySet()) {
                    String expressionAttributeNamesValue = expressionAttributeNamesEntry.getValue();
                    if (expressionAttributeNamesValue != null) {
                        jsonWriter.name(expressionAttributeNamesEntry.getKey());
                        jsonWriter.value(expressionAttributeNamesValue);
                    }
                }
                jsonWriter.endObject();
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
