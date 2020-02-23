package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class ProvisionedThroughputJsonUnmarshaller implements Unmarshaller<ProvisionedThroughput, JsonUnmarshallerContext> {
    private static ProvisionedThroughputJsonUnmarshaller instance;

    ProvisionedThroughputJsonUnmarshaller() {
    }

    public ProvisionedThroughput unmarshall(JsonUnmarshallerContext context) throws Exception {
        ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("ReadCapacityUnits")) {
                provisionedThroughput.setReadCapacityUnits(SimpleTypeJsonUnmarshallers.LongJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("WriteCapacityUnits")) {
                provisionedThroughput.setWriteCapacityUnits(SimpleTypeJsonUnmarshallers.LongJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return provisionedThroughput;
    }

    public static ProvisionedThroughputJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ProvisionedThroughputJsonUnmarshaller();
        }
        return instance;
    }
}
