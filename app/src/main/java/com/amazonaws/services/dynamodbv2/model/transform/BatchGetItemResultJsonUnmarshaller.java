package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.BatchGetItemResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class BatchGetItemResultJsonUnmarshaller implements Unmarshaller<BatchGetItemResult, JsonUnmarshallerContext> {
    private static BatchGetItemResultJsonUnmarshaller instance;

    public BatchGetItemResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        BatchGetItemResult batchGetItemResult = new BatchGetItemResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Responses")) {
                batchGetItemResult.setResponses(new MapUnmarshaller(new ListUnmarshaller(new MapUnmarshaller(AttributeValueJsonUnmarshaller.getInstance()))).unmarshall(context));
            } else if (name.equals("UnprocessedKeys")) {
                batchGetItemResult.setUnprocessedKeys(new MapUnmarshaller(KeysAndAttributesJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("ConsumedCapacity")) {
                batchGetItemResult.setConsumedCapacity(new ListUnmarshaller(ConsumedCapacityJsonUnmarshaller.getInstance()).unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return batchGetItemResult;
    }

    public static BatchGetItemResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new BatchGetItemResultJsonUnmarshaller();
        }
        return instance;
    }
}
