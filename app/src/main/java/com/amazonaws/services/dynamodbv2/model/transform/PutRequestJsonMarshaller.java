package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutRequest;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Map;

class PutRequestJsonMarshaller {
    private static PutRequestJsonMarshaller instance;

    PutRequestJsonMarshaller() {
    }

    public void marshall(PutRequest putRequest, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (putRequest.getItem() != null) {
            Map<String, AttributeValue> item = putRequest.getItem();
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
        jsonWriter.endObject();
    }

    public static PutRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new PutRequestJsonMarshaller();
        }
        return instance;
    }
}
