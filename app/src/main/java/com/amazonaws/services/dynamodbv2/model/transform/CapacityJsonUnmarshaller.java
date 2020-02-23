package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.Capacity;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class CapacityJsonUnmarshaller implements Unmarshaller<Capacity, JsonUnmarshallerContext> {
    private static CapacityJsonUnmarshaller instance;

    CapacityJsonUnmarshaller() {
    }

    public Capacity unmarshall(JsonUnmarshallerContext context) throws Exception {
        Capacity capacity = new Capacity();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("CapacityUnits")) {
                capacity.setCapacityUnits(SimpleTypeJsonUnmarshallers.DoubleJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return capacity;
    }

    public static CapacityJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CapacityJsonUnmarshaller();
        }
        return instance;
    }
}
