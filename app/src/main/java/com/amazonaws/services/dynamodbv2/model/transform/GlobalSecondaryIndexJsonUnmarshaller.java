package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class GlobalSecondaryIndexJsonUnmarshaller implements Unmarshaller<GlobalSecondaryIndex, JsonUnmarshallerContext> {
    private static GlobalSecondaryIndexJsonUnmarshaller instance;

    GlobalSecondaryIndexJsonUnmarshaller() {
    }

    public GlobalSecondaryIndex unmarshall(JsonUnmarshallerContext context) throws Exception {
        GlobalSecondaryIndex globalSecondaryIndex = new GlobalSecondaryIndex();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("IndexName")) {
                globalSecondaryIndex.setIndexName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("KeySchema")) {
                globalSecondaryIndex.setKeySchema(new ListUnmarshaller(KeySchemaElementJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("Projection")) {
                globalSecondaryIndex.setProjection(ProjectionJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ProvisionedThroughput")) {
                globalSecondaryIndex.setProvisionedThroughput(ProvisionedThroughputJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return globalSecondaryIndex;
    }

    public static GlobalSecondaryIndexJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GlobalSecondaryIndexJsonUnmarshaller();
        }
        return instance;
    }
}
