package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class ScanResultJsonUnmarshaller implements Unmarshaller<ScanResult, JsonUnmarshallerContext> {
    private static ScanResultJsonUnmarshaller instance;

    public ScanResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        ScanResult scanResult = new ScanResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Items")) {
                scanResult.setItems(new ListUnmarshaller(new MapUnmarshaller(AttributeValueJsonUnmarshaller.getInstance())).unmarshall(context));
            } else if (name.equals("Count")) {
                scanResult.setCount(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ScannedCount")) {
                scanResult.setScannedCount(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("LastEvaluatedKey")) {
                scanResult.setLastEvaluatedKey(new MapUnmarshaller(AttributeValueJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("ConsumedCapacity")) {
                scanResult.setConsumedCapacity(ConsumedCapacityJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return scanResult;
    }

    public static ScanResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ScanResultJsonUnmarshaller();
        }
        return instance;
    }
}
