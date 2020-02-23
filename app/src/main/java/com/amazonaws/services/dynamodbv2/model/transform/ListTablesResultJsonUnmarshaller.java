package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class ListTablesResultJsonUnmarshaller implements Unmarshaller<ListTablesResult, JsonUnmarshallerContext> {
    private static ListTablesResultJsonUnmarshaller instance;

    public ListTablesResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        ListTablesResult listTablesResult = new ListTablesResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("TableNames")) {
                listTablesResult.setTableNames(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("LastEvaluatedTableName")) {
                listTablesResult.setLastEvaluatedTableName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return listTablesResult;
    }

    public static ListTablesResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ListTablesResultJsonUnmarshaller();
        }
        return instance;
    }
}
