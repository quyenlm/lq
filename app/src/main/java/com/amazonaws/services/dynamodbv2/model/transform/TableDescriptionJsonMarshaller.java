package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputDescription;
import com.amazonaws.services.dynamodbv2.model.StreamSpecification;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Date;
import java.util.List;

class TableDescriptionJsonMarshaller {
    private static TableDescriptionJsonMarshaller instance;

    TableDescriptionJsonMarshaller() {
    }

    public void marshall(TableDescription tableDescription, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (tableDescription.getAttributeDefinitions() != null) {
            List<AttributeDefinition> attributeDefinitions = tableDescription.getAttributeDefinitions();
            jsonWriter.name("AttributeDefinitions");
            jsonWriter.beginArray();
            for (AttributeDefinition attributeDefinitionsItem : attributeDefinitions) {
                if (attributeDefinitionsItem != null) {
                    AttributeDefinitionJsonMarshaller.getInstance().marshall(attributeDefinitionsItem, jsonWriter);
                }
            }
            jsonWriter.endArray();
        }
        if (tableDescription.getTableName() != null) {
            String tableName = tableDescription.getTableName();
            jsonWriter.name("TableName");
            jsonWriter.value(tableName);
        }
        if (tableDescription.getKeySchema() != null) {
            List<KeySchemaElement> keySchema = tableDescription.getKeySchema();
            jsonWriter.name("KeySchema");
            jsonWriter.beginArray();
            for (KeySchemaElement keySchemaItem : keySchema) {
                if (keySchemaItem != null) {
                    KeySchemaElementJsonMarshaller.getInstance().marshall(keySchemaItem, jsonWriter);
                }
            }
            jsonWriter.endArray();
        }
        if (tableDescription.getTableStatus() != null) {
            String tableStatus = tableDescription.getTableStatus();
            jsonWriter.name("TableStatus");
            jsonWriter.value(tableStatus);
        }
        if (tableDescription.getCreationDateTime() != null) {
            Date creationDateTime = tableDescription.getCreationDateTime();
            jsonWriter.name("CreationDateTime");
            jsonWriter.value(creationDateTime);
        }
        if (tableDescription.getProvisionedThroughput() != null) {
            ProvisionedThroughputDescription provisionedThroughput = tableDescription.getProvisionedThroughput();
            jsonWriter.name("ProvisionedThroughput");
            ProvisionedThroughputDescriptionJsonMarshaller.getInstance().marshall(provisionedThroughput, jsonWriter);
        }
        if (tableDescription.getTableSizeBytes() != null) {
            Long tableSizeBytes = tableDescription.getTableSizeBytes();
            jsonWriter.name("TableSizeBytes");
            jsonWriter.value((Number) tableSizeBytes);
        }
        if (tableDescription.getItemCount() != null) {
            Long itemCount = tableDescription.getItemCount();
            jsonWriter.name("ItemCount");
            jsonWriter.value((Number) itemCount);
        }
        if (tableDescription.getTableArn() != null) {
            String tableArn = tableDescription.getTableArn();
            jsonWriter.name("TableArn");
            jsonWriter.value(tableArn);
        }
        if (tableDescription.getLocalSecondaryIndexes() != null) {
            List<LocalSecondaryIndexDescription> localSecondaryIndexes = tableDescription.getLocalSecondaryIndexes();
            jsonWriter.name("LocalSecondaryIndexes");
            jsonWriter.beginArray();
            for (LocalSecondaryIndexDescription localSecondaryIndexesItem : localSecondaryIndexes) {
                if (localSecondaryIndexesItem != null) {
                    LocalSecondaryIndexDescriptionJsonMarshaller.getInstance().marshall(localSecondaryIndexesItem, jsonWriter);
                }
            }
            jsonWriter.endArray();
        }
        if (tableDescription.getGlobalSecondaryIndexes() != null) {
            List<GlobalSecondaryIndexDescription> globalSecondaryIndexes = tableDescription.getGlobalSecondaryIndexes();
            jsonWriter.name("GlobalSecondaryIndexes");
            jsonWriter.beginArray();
            for (GlobalSecondaryIndexDescription globalSecondaryIndexesItem : globalSecondaryIndexes) {
                if (globalSecondaryIndexesItem != null) {
                    GlobalSecondaryIndexDescriptionJsonMarshaller.getInstance().marshall(globalSecondaryIndexesItem, jsonWriter);
                }
            }
            jsonWriter.endArray();
        }
        if (tableDescription.getStreamSpecification() != null) {
            StreamSpecification streamSpecification = tableDescription.getStreamSpecification();
            jsonWriter.name("StreamSpecification");
            StreamSpecificationJsonMarshaller.getInstance().marshall(streamSpecification, jsonWriter);
        }
        if (tableDescription.getLatestStreamLabel() != null) {
            String latestStreamLabel = tableDescription.getLatestStreamLabel();
            jsonWriter.name("LatestStreamLabel");
            jsonWriter.value(latestStreamLabel);
        }
        if (tableDescription.getLatestStreamArn() != null) {
            String latestStreamArn = tableDescription.getLatestStreamArn();
            jsonWriter.name("LatestStreamArn");
            jsonWriter.value(latestStreamArn);
        }
        jsonWriter.endObject();
    }

    public static TableDescriptionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new TableDescriptionJsonMarshaller();
        }
        return instance;
    }
}
