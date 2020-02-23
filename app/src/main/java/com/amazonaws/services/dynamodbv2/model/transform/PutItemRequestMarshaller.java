package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import com.appsflyer.share.Constants;
import java.io.StringWriter;
import java.util.Map;

public class PutItemRequestMarshaller implements Marshaller<Request<PutItemRequest>, PutItemRequest> {
    public Request<PutItemRequest> marshall(PutItemRequest putItemRequest) {
        if (putItemRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(PutItemRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(putItemRequest, "AmazonDynamoDB");
        defaultRequest.addHeader("X-Amz-Target", "DynamoDB_20120810.PutItem");
        defaultRequest.setHttpMethod(HttpMethodName.POST);
        defaultRequest.setResourcePath(Constants.URL_PATH_DELIMITER);
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            jsonWriter.beginObject();
            if (putItemRequest.getTableName() != null) {
                String tableName = putItemRequest.getTableName();
                jsonWriter.name("TableName");
                jsonWriter.value(tableName);
            }
            if (putItemRequest.getItem() != null) {
                Map<String, AttributeValue> item = putItemRequest.getItem();
                jsonWriter.name("Item");
                jsonWriter.beginObject();
                for (Map.Entry<String, AttributeValue> itemEntry : item.entrySet()) {
                    AttributeValue itemValue = itemEntry.getValue();
                    if (itemValue != null) {
                        jsonWriter.name(itemEntry.getKey());
                        AttributeValueJsonMarshaller.getInstance().marshall(itemValue, jsonWriter);
                    }
                }
                jsonWriter.endObject();
            }
            if (putItemRequest.getExpected() != null) {
                Map<String, ExpectedAttributeValue> expected = putItemRequest.getExpected();
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
            if (putItemRequest.getReturnValues() != null) {
                String returnValues = putItemRequest.getReturnValues();
                jsonWriter.name("ReturnValues");
                jsonWriter.value(returnValues);
            }
            if (putItemRequest.getReturnConsumedCapacity() != null) {
                String returnConsumedCapacity = putItemRequest.getReturnConsumedCapacity();
                jsonWriter.name("ReturnConsumedCapacity");
                jsonWriter.value(returnConsumedCapacity);
            }
            if (putItemRequest.getReturnItemCollectionMetrics() != null) {
                String returnItemCollectionMetrics = putItemRequest.getReturnItemCollectionMetrics();
                jsonWriter.name("ReturnItemCollectionMetrics");
                jsonWriter.value(returnItemCollectionMetrics);
            }
            if (putItemRequest.getConditionalOperator() != null) {
                String conditionalOperator = putItemRequest.getConditionalOperator();
                jsonWriter.name("ConditionalOperator");
                jsonWriter.value(conditionalOperator);
            }
            if (putItemRequest.getConditionExpression() != null) {
                String conditionExpression = putItemRequest.getConditionExpression();
                jsonWriter.name("ConditionExpression");
                jsonWriter.value(conditionExpression);
            }
            if (putItemRequest.getExpressionAttributeNames() != null) {
                Map<String, String> expressionAttributeNames = putItemRequest.getExpressionAttributeNames();
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
            if (putItemRequest.getExpressionAttributeValues() != null) {
                Map<String, AttributeValue> expressionAttributeValues = putItemRequest.getExpressionAttributeValues();
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
