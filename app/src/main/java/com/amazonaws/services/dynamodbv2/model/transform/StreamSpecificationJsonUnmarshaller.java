package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.StreamSpecification;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class StreamSpecificationJsonUnmarshaller implements Unmarshaller<StreamSpecification, JsonUnmarshallerContext> {
    private static StreamSpecificationJsonUnmarshaller instance;

    StreamSpecificationJsonUnmarshaller() {
    }

    public StreamSpecification unmarshall(JsonUnmarshallerContext context) throws Exception {
        StreamSpecification streamSpecification = new StreamSpecification();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("StreamEnabled")) {
                streamSpecification.setStreamEnabled(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("StreamViewType")) {
                streamSpecification.setStreamViewType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return streamSpecification;
    }

    public static StreamSpecificationJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new StreamSpecificationJsonUnmarshaller();
        }
        return instance;
    }
}
