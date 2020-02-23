package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class AttributeDefinitionJsonUnmarshaller implements Unmarshaller<AttributeDefinition, JsonUnmarshallerContext> {
    private static AttributeDefinitionJsonUnmarshaller instance;

    AttributeDefinitionJsonUnmarshaller() {
    }

    public AttributeDefinition unmarshall(JsonUnmarshallerContext context) throws Exception {
        AttributeDefinition attributeDefinition = new AttributeDefinition();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("AttributeName")) {
                attributeDefinition.setAttributeName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("AttributeType")) {
                attributeDefinition.setAttributeType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return attributeDefinition;
    }

    public static AttributeDefinitionJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new AttributeDefinitionJsonUnmarshaller();
        }
        return instance;
    }
}
