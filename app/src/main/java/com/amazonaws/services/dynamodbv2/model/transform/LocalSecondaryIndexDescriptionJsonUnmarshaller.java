package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndexDescription;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class LocalSecondaryIndexDescriptionJsonUnmarshaller implements Unmarshaller<LocalSecondaryIndexDescription, JsonUnmarshallerContext> {
    private static LocalSecondaryIndexDescriptionJsonUnmarshaller instance;

    LocalSecondaryIndexDescriptionJsonUnmarshaller() {
    }

    public LocalSecondaryIndexDescription unmarshall(JsonUnmarshallerContext context) throws Exception {
        LocalSecondaryIndexDescription localSecondaryIndexDescription = new LocalSecondaryIndexDescription();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("IndexName")) {
                localSecondaryIndexDescription.setIndexName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("KeySchema")) {
                localSecondaryIndexDescription.setKeySchema(new ListUnmarshaller(KeySchemaElementJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("Projection")) {
                localSecondaryIndexDescription.setProjection(ProjectionJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("IndexSizeBytes")) {
                localSecondaryIndexDescription.setIndexSizeBytes(SimpleTypeJsonUnmarshallers.LongJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ItemCount")) {
                localSecondaryIndexDescription.setItemCount(SimpleTypeJsonUnmarshallers.LongJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("IndexArn")) {
                localSecondaryIndexDescription.setIndexArn(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return localSecondaryIndexDescription;
    }

    public static LocalSecondaryIndexDescriptionJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new LocalSecondaryIndexDescriptionJsonUnmarshaller();
        }
        return instance;
    }
}
