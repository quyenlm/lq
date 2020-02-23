package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.UpdateGlobalSecondaryIndexAction;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class UpdateGlobalSecondaryIndexActionJsonUnmarshaller implements Unmarshaller<UpdateGlobalSecondaryIndexAction, JsonUnmarshallerContext> {
    private static UpdateGlobalSecondaryIndexActionJsonUnmarshaller instance;

    UpdateGlobalSecondaryIndexActionJsonUnmarshaller() {
    }

    public UpdateGlobalSecondaryIndexAction unmarshall(JsonUnmarshallerContext context) throws Exception {
        UpdateGlobalSecondaryIndexAction updateGlobalSecondaryIndexAction = new UpdateGlobalSecondaryIndexAction();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("IndexName")) {
                updateGlobalSecondaryIndexAction.setIndexName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ProvisionedThroughput")) {
                updateGlobalSecondaryIndexAction.setProvisionedThroughput(ProvisionedThroughputJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return updateGlobalSecondaryIndexAction;
    }

    public static UpdateGlobalSecondaryIndexActionJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateGlobalSecondaryIndexActionJsonUnmarshaller();
        }
        return instance;
    }
}
