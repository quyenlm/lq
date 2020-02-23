package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.DescribeLimitsResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class DescribeLimitsResultJsonUnmarshaller implements Unmarshaller<DescribeLimitsResult, JsonUnmarshallerContext> {
    private static DescribeLimitsResultJsonUnmarshaller instance;

    public DescribeLimitsResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        DescribeLimitsResult describeLimitsResult = new DescribeLimitsResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("AccountMaxReadCapacityUnits")) {
                describeLimitsResult.setAccountMaxReadCapacityUnits(SimpleTypeJsonUnmarshallers.LongJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("AccountMaxWriteCapacityUnits")) {
                describeLimitsResult.setAccountMaxWriteCapacityUnits(SimpleTypeJsonUnmarshallers.LongJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("TableMaxReadCapacityUnits")) {
                describeLimitsResult.setTableMaxReadCapacityUnits(SimpleTypeJsonUnmarshallers.LongJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("TableMaxWriteCapacityUnits")) {
                describeLimitsResult.setTableMaxWriteCapacityUnits(SimpleTypeJsonUnmarshallers.LongJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return describeLimitsResult;
    }

    public static DescribeLimitsResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DescribeLimitsResultJsonUnmarshaller();
        }
        return instance;
    }
}
