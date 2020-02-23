package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

class GlobalSecondaryIndexJsonMarshaller {
    private static GlobalSecondaryIndexJsonMarshaller instance;

    GlobalSecondaryIndexJsonMarshaller() {
    }

    public void marshall(GlobalSecondaryIndex globalSecondaryIndex, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (globalSecondaryIndex.getIndexName() != null) {
            String indexName = globalSecondaryIndex.getIndexName();
            jsonWriter.name("IndexName");
            jsonWriter.value(indexName);
        }
        if (globalSecondaryIndex.getKeySchema() != null) {
            List<KeySchemaElement> keySchema = globalSecondaryIndex.getKeySchema();
            jsonWriter.name("KeySchema");
            jsonWriter.beginArray();
            for (KeySchemaElement keySchemaItem : keySchema) {
                if (keySchemaItem != null) {
                    KeySchemaElementJsonMarshaller.getInstance().marshall(keySchemaItem, jsonWriter);
                }
            }
            jsonWriter.endArray();
        }
        if (globalSecondaryIndex.getProjection() != null) {
            Projection projection = globalSecondaryIndex.getProjection();
            jsonWriter.name("Projection");
            ProjectionJsonMarshaller.getInstance().marshall(projection, jsonWriter);
        }
        if (globalSecondaryIndex.getProvisionedThroughput() != null) {
            ProvisionedThroughput provisionedThroughput = globalSecondaryIndex.getProvisionedThroughput();
            jsonWriter.name("ProvisionedThroughput");
            ProvisionedThroughputJsonMarshaller.getInstance().marshall(provisionedThroughput, jsonWriter);
        }
        jsonWriter.endObject();
    }

    public static GlobalSecondaryIndexJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new GlobalSecondaryIndexJsonMarshaller();
        }
        return instance;
    }
}
