package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DeleteRequest;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Map;

class DeleteRequestJsonMarshaller {
    private static DeleteRequestJsonMarshaller instance;

    DeleteRequestJsonMarshaller() {
    }

    public void marshall(DeleteRequest deleteRequest, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (deleteRequest.getKey() != null) {
            Map<String, AttributeValue> key = deleteRequest.getKey();
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
        jsonWriter.endObject();
    }

    public static DeleteRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteRequestJsonMarshaller();
        }
        return instance;
    }
}
