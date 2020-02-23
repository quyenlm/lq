package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.CreateGlobalSecondaryIndexAction;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class CreateGlobalSecondaryIndexActionJsonUnmarshaller implements Unmarshaller<CreateGlobalSecondaryIndexAction, JsonUnmarshallerContext> {
    private static CreateGlobalSecondaryIndexActionJsonUnmarshaller instance;

    CreateGlobalSecondaryIndexActionJsonUnmarshaller() {
    }

    public CreateGlobalSecondaryIndexAction unmarshall(JsonUnmarshallerContext context) throws Exception {
        CreateGlobalSecondaryIndexAction createGlobalSecondaryIndexAction = new CreateGlobalSecondaryIndexAction();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("IndexName")) {
                createGlobalSecondaryIndexAction.setIndexName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("KeySchema")) {
                createGlobalSecondaryIndexAction.setKeySchema(new ListUnmarshaller(KeySchemaElementJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("Projection")) {
                createGlobalSecondaryIndexAction.setProjection(ProjectionJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ProvisionedThroughput")) {
                createGlobalSecondaryIndexAction.setProvisionedThroughput(ProvisionedThroughputJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return createGlobalSecondaryIndexAction;
    }

    public static CreateGlobalSecondaryIndexActionJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CreateGlobalSecondaryIndexActionJsonUnmarshaller();
        }
        return instance;
    }
}
