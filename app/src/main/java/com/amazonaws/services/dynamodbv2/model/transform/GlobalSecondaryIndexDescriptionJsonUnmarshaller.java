package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class GlobalSecondaryIndexDescriptionJsonUnmarshaller implements Unmarshaller<GlobalSecondaryIndexDescription, JsonUnmarshallerContext> {
    private static GlobalSecondaryIndexDescriptionJsonUnmarshaller instance;

    GlobalSecondaryIndexDescriptionJsonUnmarshaller() {
    }

    public GlobalSecondaryIndexDescription unmarshall(JsonUnmarshallerContext context) throws Exception {
        GlobalSecondaryIndexDescription globalSecondaryIndexDescription = new GlobalSecondaryIndexDescription();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("IndexName")) {
                globalSecondaryIndexDescription.setIndexName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("KeySchema")) {
                globalSecondaryIndexDescription.setKeySchema(new ListUnmarshaller(KeySchemaElementJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("Projection")) {
                globalSecondaryIndexDescription.setProjection(ProjectionJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("IndexStatus")) {
                globalSecondaryIndexDescription.setIndexStatus(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Backfilling")) {
                globalSecondaryIndexDescription.setBackfilling(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ProvisionedThroughput")) {
                globalSecondaryIndexDescription.setProvisionedThroughput(ProvisionedThroughputDescriptionJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("IndexSizeBytes")) {
                globalSecondaryIndexDescription.setIndexSizeBytes(SimpleTypeJsonUnmarshallers.LongJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ItemCount")) {
                globalSecondaryIndexDescription.setItemCount(SimpleTypeJsonUnmarshallers.LongJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("IndexArn")) {
                globalSecondaryIndexDescription.setIndexArn(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return globalSecondaryIndexDescription;
    }

    public static GlobalSecondaryIndexDescriptionJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GlobalSecondaryIndexDescriptionJsonUnmarshaller();
        }
        return instance;
    }
}
