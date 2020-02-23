package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.util.json.AwsJsonWriter;

class AttributeValueUpdateJsonMarshaller {
    private static AttributeValueUpdateJsonMarshaller instance;

    AttributeValueUpdateJsonMarshaller() {
    }

    public void marshall(AttributeValueUpdate attributeValueUpdate, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (attributeValueUpdate.getValue() != null) {
            AttributeValue value = attributeValueUpdate.getValue();
            jsonWriter.name("Value");
            AttributeValueJsonMarshaller.getInstance().marshall(value, jsonWriter);
        }
        if (attributeValueUpdate.getAction() != null) {
            String action = attributeValueUpdate.getAction();
            jsonWriter.name(JsonDocumentFields.ACTION);
            jsonWriter.value(action);
        }
        jsonWriter.endObject();
    }

    public static AttributeValueUpdateJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new AttributeValueUpdateJsonMarshaller();
        }
        return instance;
    }
}
