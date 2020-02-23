package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import com.appsflyer.share.Constants;
import java.io.StringWriter;
import java.util.Map;

public class DeleteItemRequestMarshaller implements Marshaller<Request<DeleteItemRequest>, DeleteItemRequest> {
    public Request<DeleteItemRequest> marshall(DeleteItemRequest deleteItemRequest) {
        if (deleteItemRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteItemRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(deleteItemRequest, "AmazonDynamoDB");
        defaultRequest.addHeader("X-Amz-Target", "DynamoDB_20120810.DeleteItem");
        defaultRequest.setHttpMethod(HttpMethodName.POST);
        defaultRequest.setResourcePath(Constants.URL_PATH_DELIMITER);
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            jsonWriter.beginObject();
            if (deleteItemRequest.getTableName() != null) {
                String tableName = deleteItemRequest.getTableName();
                jsonWriter.name("TableName");
                jsonWriter.value(tableName);
            }
            if (deleteItemRequest.getKey() != null) {
                Map<String, AttributeValue> key = deleteItemRequest.getKey();
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
            if (deleteItemRequest.getExpected() != null) {
                Map<String, ExpectedAttributeValue> expected = deleteItemRequest.getExpected();
                jsonWriter.name("Expected");
                jsonWriter.beginObject();
                for (Map.Entry<String, ExpectedAttributeValue> expectedEntry : expected.entrySet()) {
                    ExpectedAttributeValue expectedValue = expectedEntry.getValue();
                    if (expectedValue != null) {
                        jsonWriter.name(expectedEntry.getKey());
                        ExpectedAttributeValueJsonMarshaller.getInstance().marshall(expectedValue, jsonWriter);
                    }
                }
                jsonWriter.endObject();
            }
            if (deleteItemRequest.getConditionalOperator() != null) {
                String conditionalOperator = deleteItemRequest.getConditionalOperator();
                jsonWriter.name("ConditionalOperator");
                jsonWriter.value(conditionalOperator);
            }
            if (deleteItemRequest.getReturnValues() != null) {
                String returnValues = deleteItemRequest.getReturnValues();
                jsonWriter.name("ReturnValues");
                jsonWriter.value(returnValues);
            }
            if (deleteItemRequest.getReturnConsumedCapacity() != null) {
                String returnConsumedCapacity = deleteItemRequest.getReturnConsumedCapacity();
                jsonWriter.name("ReturnConsumedCapacity");
                jsonWriter.value(returnConsumedCapacity);
            }
            if (deleteItemRequest.getReturnItemCollectionMetrics() != null) {
                String returnItemCollectionMetrics = deleteItemRequest.getReturnItemCollectionMetrics();
                jsonWriter.name("ReturnItemCollectionMetrics");
                jsonWriter.value(returnItemCollectionMetrics);
            }
            if (deleteItemRequest.getConditionExpression() != null) {
                String conditionExpression = deleteItemRequest.getConditionExpression();
                jsonWriter.name("ConditionExpression");
                jsonWriter.value(conditionExpression);
            }
            if (deleteItemRequest.getExpressionAttributeNames() != null) {
                Map<String, String> expressionAttributeNames = deleteItemRequest.getExpressionAttributeNames();
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
            if (deleteItemRequest.getExpressionAttributeValues() != null) {
                Map<String, AttributeValue> expressionAttributeValues = deleteItemRequest.getExpressionAttributeValues();
                jsonWriter.name("ExpressionAttributeValues");
                jsonWriter.beginObject();
                for (Map.Entry<String, AttributeValue> expressionAttributeValuesEntry : expressionAttributeValues.entrySet()) {
                    AttributeValue expressionAttributeValuesValue = expressionAttributeValuesEntry.getValue();
                    if (expressionAttributeValuesValue != null) {
                        jsonWriter.name(expressionAttributeValuesEntry.getKey());
                        AttributeValueJsonMarshaller.getInstance().marshall(expressionAttributeValuesValue, jsonWriter);
                    }
                }
                jsonWriter.endObject();
            }
            jsonWriter.endObject();
            jsonWriter.close();
            String snippet = stringWriter.toString();
            byte[] content = snippet.getBytes(StringUtils.UTF8);
            defaultRequest.setContent(new StringInputStream(snippet));
            defaultRequest.addHeader("Content-Length", Integer.toString(content.length));
            if (!defaultRequest.getHeaders().containsKey("Content-Type")) {
                defaultRequest.addHeader("Content-Type", "application/x-amz-json-1.0");
            }
            return defaultRequest;
        } catch (Throwable t) {
            throw new AmazonClientException("Unable to marshall request to JSON: " + t.getMessage(), t);
        }
    }
}
