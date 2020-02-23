package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class AttributeValueJsonUnmarshaller implements Unmarshaller<AttributeValue, JsonUnmarshallerContext> {
    private static AttributeValueJsonUnmarshaller instance;

    AttributeValueJsonUnmarshaller() {
    }

    public AttributeValue unmarshall(JsonUnmarshallerContext context) throws Exception {
        AttributeValue attributeValue = new AttributeValue();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("S")) {
                attributeValue.setS(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("N")) {
                attributeValue.setN(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("B")) {
                attributeValue.setB(SimpleTypeJsonUnmarshallers.ByteBufferJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("SS")) {
                attributeValue.setSS(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("NS")) {
                attributeValue.setNS(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("BS")) {
                attributeValue.setBS(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.ByteBufferJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("M")) {
                attributeValue.setM(new MapUnmarshaller(getInstance()).unmarshall(context));
            } else if (name.equals("L")) {
                attributeValue.setL(new ListUnmarshaller(getInstance()).unmarshall(context));
            } else if (name.equals("NULL")) {
                attributeValue.setNULL(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("BOOL")) {
                attributeValue.setBOOL(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return attributeValue;
    }

    public static AttributeValueJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new AttributeValueJsonUnmarshaller();
        }
        return instance;
    }
}
