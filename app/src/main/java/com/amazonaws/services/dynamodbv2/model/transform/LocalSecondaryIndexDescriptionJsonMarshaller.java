package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

class LocalSecondaryIndexDescriptionJsonMarshaller {
    private static LocalSecondaryIndexDescriptionJsonMarshaller instance;

    LocalSecondaryIndexDescriptionJsonMarshaller() {
    }

    public void marshall(LocalSecondaryIndexDescription localSecondaryIndexDescription, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (localSecondaryIndexDescription.getIndexName() != null) {
            String indexName = localSecondaryIndexDescription.getIndexName();
            jsonWriter.name("IndexName");
            jsonWriter.value(indexName);
        }
        if (localSecondaryIndexDescription.getKeySchema() != null) {
            List<KeySchemaElement> keySchema = localSecondaryIndexDescription.getKeySchema();
            jsonWriter.name("KeySchema");
            jsonWriter.beginArray();
            for (KeySchemaElement keySchemaItem : keySchema) {
                if (keySchemaItem != null) {
                    KeySchemaElementJsonMarshaller.getInstance().marshall(keySchemaItem, jsonWriter);
                }
            }
            jsonWriter.endArray();
        }
        if (localSecondaryIndexDescription.getProjection() != null) {
            Projection projection = localSecondaryIndexDescription.getProjection();
            jsonWriter.name("Projection");
            ProjectionJsonMarshaller.getInstance().marshall(projection, jsonWriter);
        }
        if (localSecondaryIndexDescription.getIndexSizeBytes() != null) {
            Long indexSizeBytes = localSecondaryIndexDescription.getIndexSizeBytes();
            jsonWriter.name("IndexSizeBytes");
            jsonWriter.value((Number) indexSizeBytes);
        }
        if (localSecondaryIndexDescription.getItemCount() != null) {
            Long itemCount = localSecondaryIndexDescription.getItemCount();
            jsonWriter.name("ItemCount");
            jsonWriter.value((Number) itemCount);
        }
        if (localSecondaryIndexDescription.getIndexArn() != null) {
            String indexArn = localSecondaryIndexDescription.getIndexArn();
            jsonWriter.name("IndexArn");
            jsonWriter.value(indexArn);
        }
        jsonWriter.endObject();
    }

    public static LocalSecondaryIndexDescriptionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new LocalSecondaryIndexDescriptionJsonMarshaller();
        }
        return instance;
    }
}
