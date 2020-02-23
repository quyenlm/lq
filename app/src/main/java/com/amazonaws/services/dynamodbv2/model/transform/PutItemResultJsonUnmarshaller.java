package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class PutItemResultJsonUnmarshaller implements Unmarshaller<PutItemResult, JsonUnmarshallerContext> {
    private static PutItemResultJsonUnmarshaller instance;

    public PutItemResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        PutItemResult putItemResult = new PutItemResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Attributes")) {
                putItemResult.setAttributes(new MapUnmarshaller(AttributeValueJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("ConsumedCapacity")) {
                putItemResult.setConsumedCapacity(ConsumedCapacityJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ItemCollectionMetrics")) {
                putItemResult.setItemCollectionMetrics(ItemCollectionMetricsJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return putItemResult;
    }

    public static PutItemResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new PutItemResultJsonUnmarshaller();
        }
        return instance;
    }
}
