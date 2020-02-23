package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class KeySchemaElementJsonUnmarshaller implements Unmarshaller<KeySchemaElement, JsonUnmarshallerContext> {
    private static KeySchemaElementJsonUnmarshaller instance;

    KeySchemaElementJsonUnmarshaller() {
    }

    public KeySchemaElement unmarshall(JsonUnmarshallerContext context) throws Exception {
        KeySchemaElement keySchemaElement = new KeySchemaElement();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("AttributeName")) {
                keySchemaElement.setAttributeName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("KeyType")) {
                keySchemaElement.setKeyType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return keySchemaElement;
    }

    public static KeySchemaElementJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new KeySchemaElementJsonUnmarshaller();
        }
        return instance;
    }
}
