package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

class LocalSecondaryIndexJsonMarshaller {
    private static LocalSecondaryIndexJsonMarshaller instance;

    LocalSecondaryIndexJsonMarshaller() {
    }

    public void marshall(LocalSecondaryIndex localSecondaryIndex, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (localSecondaryIndex.getIndexName() != null) {
            String indexName = localSecondaryIndex.getIndexName();
            jsonWriter.name("IndexName");
            jsonWriter.value(indexName);
        }
        if (localSecondaryIndex.getKeySchema() != null) {
            List<KeySchemaElement> keySchema = localSecondaryIndex.getKeySchema();
            jsonWriter.name("KeySchema");
            jsonWriter.beginArray();
            for (KeySchemaElement keySchemaItem : keySchema) {
                if (keySchemaItem != null) {
                    KeySchemaElementJsonMarshaller.getInstance().marshall(keySchemaItem, jsonWriter);
                }
            }
            jsonWriter.endArray();
        }
        if (localSecondaryIndex.getProjection() != null) {
            Projection projection = localSecondaryIndex.getProjection();
            jsonWriter.name("Projection");
            ProjectionJsonMarshaller.getInstance().marshall(projection, jsonWriter);
        }
        jsonWriter.endObject();
    }

    public static LocalSecondaryIndexJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new LocalSecondaryIndexJsonMarshaller();
        }
        return instance;
    }
}
