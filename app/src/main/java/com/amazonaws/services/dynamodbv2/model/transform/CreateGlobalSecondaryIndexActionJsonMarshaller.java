package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.CreateGlobalSecondaryIndexAction;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

class CreateGlobalSecondaryIndexActionJsonMarshaller {
    private static CreateGlobalSecondaryIndexActionJsonMarshaller instance;

    CreateGlobalSecondaryIndexActionJsonMarshaller() {
    }

    public void marshall(CreateGlobalSecondaryIndexAction createGlobalSecondaryIndexAction, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (createGlobalSecondaryIndexAction.getIndexName() != null) {
            String indexName = createGlobalSecondaryIndexAction.getIndexName();
            jsonWriter.name("IndexName");
            jsonWriter.value(indexName);
        }
        if (createGlobalSecondaryIndexAction.getKeySchema() != null) {
            List<KeySchemaElement> keySchema = createGlobalSecondaryIndexAction.getKeySchema();
            jsonWriter.name("KeySchema");
            jsonWriter.beginArray();
            for (KeySchemaElement keySchemaItem : keySchema) {
                if (keySchemaItem != null) {
                    KeySchemaElementJsonMarshaller.getInstance().marshall(keySchemaItem, jsonWriter);
                }
            }
            jsonWriter.endArray();
        }
        if (createGlobalSecondaryIndexAction.getProjection() != null) {
            Projection projection = createGlobalSecondaryIndexAction.getProjection();
            jsonWriter.name("Projection");
            ProjectionJsonMarshaller.getInstance().marshall(projection, jsonWriter);
        }
        if (createGlobalSecondaryIndexAction.getProvisionedThroughput() != null) {
            ProvisionedThroughput provisionedThroughput = createGlobalSecondaryIndexAction.getProvisionedThroughput();
            jsonWriter.name("ProvisionedThroughput");
            ProvisionedThroughputJsonMarshaller.getInstance().marshall(provisionedThroughput, jsonWriter);
        }
        jsonWriter.endObject();
    }

    public static CreateGlobalSecondaryIndexActionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new CreateGlobalSecondaryIndexActionJsonMarshaller();
        }
        return instance;
    }
}
