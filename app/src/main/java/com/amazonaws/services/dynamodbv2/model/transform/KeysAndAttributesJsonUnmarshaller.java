package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.KeysAndAttributes;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class KeysAndAttributesJsonUnmarshaller implements Unmarshaller<KeysAndAttributes, JsonUnmarshallerContext> {
    private static KeysAndAttributesJsonUnmarshaller instance;

    KeysAndAttributesJsonUnmarshaller() {
    }

    public KeysAndAttributes unmarshall(JsonUnmarshallerContext context) throws Exception {
        KeysAndAttributes keysAndAttributes = new KeysAndAttributes();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Keys")) {
                keysAndAttributes.setKeys(new ListUnmarshaller(new MapUnmarshaller(AttributeValueJsonUnmarshaller.getInstance())).unmarshall(context));
            } else if (name.equals("AttributesToGet")) {
                keysAndAttributes.setAttributesToGet(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("ConsistentRead")) {
                keysAndAttributes.setConsistentRead(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ProjectionExpression")) {
                keysAndAttributes.setProjectionExpression(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ExpressionAttributeNames")) {
                keysAndAttributes.setExpressionAttributeNames(new MapUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return keysAndAttributes;
    }

    public static KeysAndAttributesJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new KeysAndAttributesJsonUnmarshaller();
        }
        return instance;
    }
}
