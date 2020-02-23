package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class AttributeValueUpdateJsonUnmarshaller implements Unmarshaller<AttributeValueUpdate, JsonUnmarshallerContext> {
    private static AttributeValueUpdateJsonUnmarshaller instance;

    AttributeValueUpdateJsonUnmarshaller() {
    }

    public AttributeValueUpdate unmarshall(JsonUnmarshallerContext context) throws Exception {
        AttributeValueUpdate attributeValueUpdate = new AttributeValueUpdate();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Value")) {
                attributeValueUpdate.setValue(AttributeValueJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals(JsonDocumentFields.ACTION)) {
                attributeValueUpdate.setAction(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return attributeValueUpdate;
    }

    public static AttributeValueUpdateJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new AttributeValueUpdateJsonUnmarshaller();
        }
        return instance;
    }
}
