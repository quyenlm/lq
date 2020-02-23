package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class DescribeTableResultJsonUnmarshaller implements Unmarshaller<DescribeTableResult, JsonUnmarshallerContext> {
    private static DescribeTableResultJsonUnmarshaller instance;

    public DescribeTableResult unmarshall(JsonUnmarshallerContext context) throws Exception {
        DescribeTableResult describeTableResult = new DescribeTableResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("Table")) {
                describeTableResult.setTable(TableDescriptionJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return describeTableResult;
    }

    public static DescribeTableResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DescribeTableResultJsonUnmarshaller();
        }
        return instance;
    }
}
