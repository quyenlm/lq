package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.util.json.AwsJsonWriter;

class AttributeDefinitionJsonMarshaller {
    private static AttributeDefinitionJsonMarshaller instance;

    AttributeDefinitionJsonMarshaller() {
    }

    public void marshall(AttributeDefinition attributeDefinition, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (attributeDefinition.getAttributeName() != null) {
            String attributeName = attributeDefinition.getAttributeName();
            jsonWriter.name("AttributeName");
            jsonWriter.value(attributeName);
        }
        if (attributeDefinition.getAttributeType() != null) {
            String attributeType = attributeDefinition.getAttributeType();
            jsonWriter.name("AttributeType");
            jsonWriter.value(attributeType);
        }
        jsonWriter.endObject();
    }

    public static AttributeDefinitionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new AttributeDefinitionJsonMarshaller();
        }
        return instance;
    }
}
