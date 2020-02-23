package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class QueryResultJsonUnmarshaller implements Unmarshaller<QueryResult, JsonUnmarshallerContext> {
    private static QueryResultJsonUnmarshaller instance;

    public QueryResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        QueryResult queryResult = new QueryResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Items")) {
                queryResult.setItems(new ListUnmarshaller(new MapUnmarshaller(AttributeValueJsonUnmarshaller.getInstance())).unmarshall(context));
            } else if (name.equals("Count")) {
                queryResult.setCount(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ScannedCount")) {
                queryResult.setScannedCount(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("LastEvaluatedKey")) {
                queryResult.setLastEvaluatedKey(new MapUnmarshaller(AttributeValueJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("ConsumedCapacity")) {
                queryResult.setConsumedCapacity(ConsumedCapacityJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return queryResult;
    }

    public static QueryResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new QueryResultJsonUnmarshaller();
        }
        return instance;
    }
}
