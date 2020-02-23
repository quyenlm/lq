package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.DeleteGlobalSecondaryIndexAction;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class DeleteGlobalSecondaryIndexActionJsonUnmarshaller implements Unmarshaller<DeleteGlobalSecondaryIndexAction, JsonUnmarshallerContext> {
    private static DeleteGlobalSecondaryIndexActionJsonUnmarshaller instance;

    DeleteGlobalSecondaryIndexActionJsonUnmarshaller() {
    }

    public DeleteGlobalSecondaryIndexAction unmarshall(JsonUnmarshallerContext context) throws Exception {
        DeleteGlobalSecondaryIndexAction deleteGlobalSecondaryIndexAction = new DeleteGlobalSecondaryIndexAction();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("IndexName")) {
                deleteGlobalSecondaryIndexAction.setIndexName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return deleteGlobalSecondaryIndexAction;
    }

    public static DeleteGlobalSecondaryIndexActionJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteGlobalSecondaryIndexActionJsonUnmarshaller();
        }
        return instance;
    }
}
