package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class LocalSecondaryIndexJsonUnmarshaller implements Unmarshaller<LocalSecondaryIndex, JsonUnmarshallerContext> {
    private static LocalSecondaryIndexJsonUnmarshaller instance;

    LocalSecondaryIndexJsonUnmarshaller() {
    }

    public LocalSecondaryIndex unmarshall(JsonUnmarshallerContext context) throws Exception {
        LocalSecondaryIndex localSecondaryIndex = new LocalSecondaryIndex();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("IndexName")) {
                localSecondaryIndex.setIndexName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("KeySchema")) {
                localSecondaryIndex.setKeySchema(new ListUnmarshaller(KeySchemaElementJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("Projection")) {
                localSecondaryIndex.setProjection(ProjectionJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return localSecondaryIndex;
    }

    public static LocalSecondaryIndexJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new LocalSecondaryIndexJsonUnmarshaller();
        }
        return instance;
    }
}
