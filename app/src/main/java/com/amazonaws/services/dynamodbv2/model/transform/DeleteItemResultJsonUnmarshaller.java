package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class DeleteItemResultJsonUnmarshaller implements Unmarshaller<DeleteItemResult, JsonUnmarshallerContext> {
    private static DeleteItemResultJsonUnmarshaller instance;

    public DeleteItemResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        DeleteItemResult deleteItemResult = new DeleteItemResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Attributes")) {
                deleteItemResult.setAttributes(new MapUnmarshaller(AttributeValueJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("ConsumedCapacity")) {
                deleteItemResult.setConsumedCapacity(ConsumedCapacityJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ItemCollectionMetrics")) {
                deleteItemResult.setItemCollectionMetrics(ItemCollectionMetricsJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return deleteItemResult;
    }

    public static DeleteItemResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteItemResultJsonUnmarshaller();
        }
        return instance;
    }
}
