package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import com.appsflyer.share.Constants;
import java.io.StringWriter;
import java.util.Map;

public class UpdateItemRequestMarshaller implements Marshaller<Request<UpdateItemRequest>, UpdateItemRequest> {
    public Request<UpdateItemRequest> marshall(UpdateItemRequest updateItemRequest) {
        if (updateItemRequest == null) {
            throw new AmazonClientException("Invalid argument passed to marshall(UpdateItemRequest)");
        }
        DefaultRequest defaultRequest = new DefaultRequest(updateItemRequest, "AmazonDynamoDB");
        defaultRequest.addHeader("X-Amz-Target", "DynamoDB_20120810.UpdateItem");
        defaultRequest.setHttpMethod(HttpMethodName.POST);
        defaultRequest.setResourcePath(Constants.URL_PATH_DELIMITER);
        try {
            StringWriter stringWriter = new StringWriter();
            AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
            jsonWriter.beginObject();
            if (updateItemRequest.getTableName() != null) {
                String tableName = updateItemRequest.getTableName();
                jsonWriter.name("TableName");
                jsonWriter.value(tableName);
            }
            if (updateItemRequest.getKey() != null) {
                Map<String, AttributeValue> key = updateItemRequest.getKey();
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
            if (updateItemRequest.getAttributeUpdates() != null) {
                Map<String, AttributeValueUpdate> attributeUpdates = updateItemRequest.getAttributeUpdates();
                jsonWriter.name("AttributeUpdates");
                jsonWriter.beginObject();
                for (Map.Entry<String, AttributeValueUpdate> attributeUpdatesEntry : attributeUpdates.entrySet()) {
                    AttributeValueUpdate attributeUpdatesValue = attributeUpdatesEntry.getValue();
                    if (attributeUpdatesValue != null) {
                        jsonWriter.name(attributeUpdatesEntry.getKey());
                        AttributeValueUpdateJsonMarshaller.getInstance().marshall(attributeUpdatesValue, jsonWriter);
                    }
                }
                jsonWriter.endObject();
            }
            if (updateItemRequest.getExpected() != null) {
                Map<String, ExpectedAttributeValue> expected = updateItemRequest.getExpected();
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
            if (updateItemRequest.getConditionalOperator() != null) {
                String conditionalOperator = updateItemRequest.getConditionalOperator();
                jsonWriter.name("ConditionalOperator");
                jsonWriter.value(conditionalOperator);
            }
            if (updateItemRequest.getReturnValues() != null) {
                String returnValues = updateItemRequest.getReturnValues();
                jsonWriter.name("ReturnValues");
                jsonWriter.value(returnValues);
            }
            if (updateItemRequest.getReturnConsumedCapacity() != null) {
                String returnConsumedCapacity = updateItemRequest.getReturnConsumedCapacity();
                jsonWriter.name("ReturnConsumedCapacity");
                jsonWriter.value(returnConsumedCapacity);
            }
            if (updateItemRequest.getReturnItemCollectionMetrics() != null) {
                String returnItemCollectionMetrics = updateItemRequest.getReturnItemCollectionMetrics();
                jsonWriter.name("ReturnItemCollectionMetrics");
                jsonWriter.value(returnItemCollectionMetrics);
            }
            if (updateItemRequest.getUpdateExpression() != null) {
                String updateExpression = updateItemRequest.getUpdateExpression();
                jsonWriter.name("UpdateExpression");
                jsonWriter.value(updateExpression);
            }
            if (updateItemRequest.getConditionExpression() != null) {
                String conditionExpression = updateItemRequest.getConditionExpression();
                jsonWriter.name("ConditionExpression");
                jsonWriter.value(conditionExpression);
            }
            if (updateItemRequest.getExpressionAttributeNames() != null) {
                Map<String, String> expressionAttributeNames = updateItemRequest.getExpressionAttributeNames();
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
            if (updateItemRequest.getExpressionAttributeValues() != null) {
                Map<String, AttributeValue> expressionAttributeValues = updateItemRequest.getExpressionAttributeValues();
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
