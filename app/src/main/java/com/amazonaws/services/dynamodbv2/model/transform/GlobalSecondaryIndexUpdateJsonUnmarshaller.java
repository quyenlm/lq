package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexUpdate;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class GlobalSecondaryIndexUpdateJsonUnmarshaller implements Unmarshaller<GlobalSecondaryIndexUpdate, JsonUnmarshallerContext> {
    private static GlobalSecondaryIndexUpdateJsonUnmarshaller instance;

    GlobalSecondaryIndexUpdateJsonUnmarshaller() {
    }

    public GlobalSecondaryIndexUpdate unmarshall(JsonUnmarshallerContext context) throws Exception {
        GlobalSecondaryIndexUpdate globalSecondaryIndexUpdate = new GlobalSecondaryIndexUpdate();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Update")) {
                globalSecondaryIndexUpdate.setUpdate(UpdateGlobalSecondaryIndexActionJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Create")) {
                globalSecondaryIndexUpdate.setCreate(CreateGlobalSecondaryIndexActionJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Delete")) {
                globalSecondaryIndexUpdate.setDelete(DeleteGlobalSecondaryIndexActionJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return globalSecondaryIndexUpdate;
    }

    public static GlobalSecondaryIndexUpdateJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GlobalSecondaryIndexUpdateJsonUnmarshaller();
        }
        return instance;
    }
}
