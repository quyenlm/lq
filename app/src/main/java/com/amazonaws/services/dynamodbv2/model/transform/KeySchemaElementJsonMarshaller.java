package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.util.json.AwsJsonWriter;

class KeySchemaElementJsonMarshaller {
    private static KeySchemaElementJsonMarshaller instance;

    KeySchemaElementJsonMarshaller() {
    }

    public void marshall(KeySchemaElement keySchemaElement, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (keySchemaElement.getAttributeName() != null) {
            String attributeName = keySchemaElement.getAttributeName();
            jsonWriter.name("AttributeName");
            jsonWriter.value(attributeName);
        }
        if (keySchemaElement.getKeyType() != null) {
            String keyType = keySchemaElement.getKeyType();
            jsonWriter.name("KeyType");
            jsonWriter.value(keyType);
        }
        jsonWriter.endObject();
    }

    public static KeySchemaElementJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new KeySchemaElementJsonMarshaller();
        }
        return instance;
    }
}
