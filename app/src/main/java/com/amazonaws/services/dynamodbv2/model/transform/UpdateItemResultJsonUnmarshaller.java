package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class UpdateItemResultJsonUnmarshaller implements Unmarshaller<UpdateItemResult, JsonUnmarshallerContext> {
    private static UpdateItemResultJsonUnmarshaller instance;

    public UpdateItemResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        UpdateItemResult updateItemResult = new UpdateItemResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Attributes")) {
                updateItemResult.setAttributes(new MapUnmarshaller(AttributeValueJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("ConsumedCapacity")) {
                updateItemResult.setConsumedCapacity(ConsumedCapacityJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ItemCollectionMetrics")) {
                updateItemResult.setItemCollectionMetrics(ItemCollectionMetricsJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return updateItemResult;
    }

    public static UpdateItemResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateItemResultJsonUnmarshaller();
        }
        return instance;
    }
}
