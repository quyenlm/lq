package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.BatchWriteItemResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class BatchWriteItemResultJsonUnmarshaller implements Unmarshaller<BatchWriteItemResult, JsonUnmarshallerContext> {
    private static BatchWriteItemResultJsonUnmarshaller instance;

    public BatchWriteItemResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        BatchWriteItemResult batchWriteItemResult = new BatchWriteItemResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("UnprocessedItems")) {
                batchWriteItemResult.setUnprocessedItems(new MapUnmarshaller(new ListUnmarshaller(WriteRequestJsonUnmarshaller.getInstance())).unmarshall(context));
            } else if (name.equals("ItemCollectionMetrics")) {
                batchWriteItemResult.setItemCollectionMetrics(new MapUnmarshaller(new ListUnmarshaller(ItemCollectionMetricsJsonUnmarshaller.getInstance())).unmarshall(context));
            } else if (name.equals("ConsumedCapacity")) {
                batchWriteItemResult.setConsumedCapacity(new ListUnmarshaller(ConsumedCapacityJsonUnmarshaller.getInstance()).unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return batchWriteItemResult;
    }

    public static BatchWriteItemResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new BatchWriteItemResultJsonUnmarshaller();
        }
        return instance;
    }
}
