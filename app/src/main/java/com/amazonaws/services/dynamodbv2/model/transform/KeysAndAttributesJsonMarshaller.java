package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.KeysAndAttributes;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;
import java.util.Map;

class KeysAndAttributesJsonMarshaller {
    private static KeysAndAttributesJsonMarshaller instance;

    KeysAndAttributesJsonMarshaller() {
    }

    public void marshall(KeysAndAttributes keysAndAttributes, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (keysAndAttributes.getKeys() != null) {
            List<Map<String, AttributeValue>> keys = keysAndAttributes.getKeys();
            jsonWriter.name("Keys");
            jsonWriter.beginArray();
            for (Map<String, AttributeValue> keysItem : keys) {
                if (keysItem != null) {
                    jsonWriter.beginObject();
                    for (Map.Entry<String, AttributeValue> keysItemEntry : keysItem.entrySet()) {
                        AttributeValue keysItemValue = keysItemEntry.getValue();
                        if (keysItemValue != null) {
                            jsonWriter.name(keysItemEntry.getKey());
                            AttributeValueJsonMarshaller.getInstance().marshall(keysItemValue, jsonWriter);
                        }
                    }
                    jsonWriter.endObject();
                }
            }
            jsonWriter.endArray();
        }
        if (keysAndAttributes.getAttributesToGet() != null) {
            List<String> attributesToGet = keysAndAttributes.getAttributesToGet();
            jsonWriter.name("AttributesToGet");
            jsonWriter.beginArray();
            for (String attributesToGetItem : attributesToGet) {
                if (attributesToGetItem != null) {
                    jsonWriter.value(attributesToGetItem);
                }
            }
            jsonWriter.endArray();
        }
        if (keysAndAttributes.getConsistentRead() != null) {
            Boolean consistentRead = keysAndAttributes.getConsistentRead();
            jsonWriter.name("ConsistentRead");
            jsonWriter.value(consistentRead.booleanValue());
        }
        if (keysAndAttributes.getProjectionExpression() != null) {
            String projectionExpression = keysAndAttributes.getProjectionExpression();
            jsonWriter.name("ProjectionExpression");
            jsonWriter.value(projectionExpression);
        }
        if (keysAndAttributes.getExpressionAttributeNames() != null) {
            Map<String, String> expressionAttributeNames = keysAndAttributes.getExpressionAttributeNames();
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
    }

    public static KeysAndAttributesJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new KeysAndAttributesJsonMarshaller();
        }
        return instance;
    }
}
