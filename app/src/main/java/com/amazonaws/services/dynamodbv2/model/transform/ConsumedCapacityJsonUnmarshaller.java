package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.ConsumedCapacity;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class ConsumedCapacityJsonUnmarshaller implements Unmarshaller<ConsumedCapacity, JsonUnmarshallerContext> {
    private static ConsumedCapacityJsonUnmarshaller instance;

    ConsumedCapacityJsonUnmarshaller() {
    }

    public ConsumedCapacity unmarshall(JsonUnmarshallerContext context) throws Exception {
        ConsumedCapacity consumedCapacity = new ConsumedCapacity();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("TableName")) {
                consumedCapacity.setTableName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("CapacityUnits")) {
                consumedCapacity.setCapacityUnits(SimpleTypeJsonUnmarshallers.DoubleJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Table")) {
                consumedCapacity.setTable(CapacityJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("LocalSecondaryIndexes")) {
                consumedCapacity.setLocalSecondaryIndexes(new MapUnmarshaller(CapacityJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("GlobalSecondaryIndexes")) {
                consumedCapacity.setGlobalSecondaryIndexes(new MapUnmarshaller(CapacityJsonUnmarshaller.getInstance()).unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return consumedCapacity;
    }

    public static ConsumedCapacityJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ConsumedCapacityJsonUnmarshaller();
        }
        return instance;
    }
}
