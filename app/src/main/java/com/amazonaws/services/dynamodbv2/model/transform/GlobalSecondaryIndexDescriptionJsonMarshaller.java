package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputDescription;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

class GlobalSecondaryIndexDescriptionJsonMarshaller {
    private static GlobalSecondaryIndexDescriptionJsonMarshaller instance;

    GlobalSecondaryIndexDescriptionJsonMarshaller() {
    }

    public void marshall(GlobalSecondaryIndexDescription globalSecondaryIndexDescription, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (globalSecondaryIndexDescription.getIndexName() != null) {
            String indexName = globalSecondaryIndexDescription.getIndexName();
            jsonWriter.name("IndexName");
            jsonWriter.value(indexName);
        }
        if (globalSecondaryIndexDescription.getKeySchema() != null) {
            List<KeySchemaElement> keySchema = globalSecondaryIndexDescription.getKeySchema();
            jsonWriter.name("KeySchema");
            jsonWriter.beginArray();
            for (KeySchemaElement keySchemaItem : keySchema) {
                if (keySchemaItem != null) {
                    KeySchemaElementJsonMarshaller.getInstance().marshall(keySchemaItem, jsonWriter);
                }
            }
            jsonWriter.endArray();
        }
        if (globalSecondaryIndexDescription.getProjection() != null) {
            Projection projection = globalSecondaryIndexDescription.getProjection();
            jsonWriter.name("Projection");
            ProjectionJsonMarshaller.getInstance().marshall(projection, jsonWriter);
        }
        if (globalSecondaryIndexDescription.getIndexStatus() != null) {
            String indexStatus = globalSecondaryIndexDescription.getIndexStatus();
            jsonWriter.name("IndexStatus");
            jsonWriter.value(indexStatus);
        }
        if (globalSecondaryIndexDescription.getBackfilling() != null) {
            Boolean backfilling = globalSecondaryIndexDescription.getBackfilling();
            jsonWriter.name("Backfilling");
            jsonWriter.value(backfilling.booleanValue());
        }
        if (globalSecondaryIndexDescription.getProvisionedThroughput() != null) {
            ProvisionedThroughputDescription provisionedThroughput = globalSecondaryIndexDescription.getProvisionedThroughput();
            jsonWriter.name("ProvisionedThroughput");
            ProvisionedThroughputDescriptionJsonMarshaller.getInstance().marshall(provisionedThroughput, jsonWriter);
        }
        if (globalSecondaryIndexDescription.getIndexSizeBytes() != null) {
            Long indexSizeBytes = globalSecondaryIndexDescription.getIndexSizeBytes();
            jsonWriter.name("IndexSizeBytes");
            jsonWriter.value((Number) indexSizeBytes);
        }
        if (globalSecondaryIndexDescription.getItemCount() != null) {
            Long itemCount = globalSecondaryIndexDescription.getItemCount();
            jsonWriter.name("ItemCount");
            jsonWriter.value((Number) itemCount);
        }
        if (globalSecondaryIndexDescription.getIndexArn() != null) {
            String indexArn = globalSecondaryIndexDescription.getIndexArn();
            jsonWriter.name("IndexArn");
            jsonWriter.value(indexArn);
        }
        jsonWriter.endObject();
    }

    public static GlobalSecondaryIndexDescriptionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new GlobalSecondaryIndexDescriptionJsonMarshaller();
        }
        return instance;
    }
}
