package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class TableDescriptionJsonUnmarshaller implements Unmarshaller<TableDescription, JsonUnmarshallerContext> {
    private static TableDescriptionJsonUnmarshaller instance;

    TableDescriptionJsonUnmarshaller() {
    }

    public TableDescription unmarshall(JsonUnmarshallerContext context) throws Exception {
        TableDescription tableDescription = new TableDescription();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("AttributeDefinitions")) {
                tableDescription.setAttributeDefinitions(new ListUnmarshaller(AttributeDefinitionJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("TableName")) {
                tableDescription.setTableName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("KeySchema")) {
                tableDescription.setKeySchema(new ListUnmarshaller(KeySchemaElementJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("TableStatus")) {
                tableDescription.setTableStatus(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("CreationDateTime")) {
                tableDescription.setCreationDateTime(SimpleTypeJsonUnmarshallers.DateJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ProvisionedThroughput")) {
                tableDescription.setProvisionedThroughput(ProvisionedThroughputDescriptionJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("TableSizeBytes")) {
                tableDescription.setTableSizeBytes(SimpleTypeJsonUnmarshallers.LongJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ItemCount")) {
                tableDescription.setItemCount(SimpleTypeJsonUnmarshallers.LongJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("TableArn")) {
                tableDescription.setTableArn(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("LocalSecondaryIndexes")) {
                tableDescription.setLocalSecondaryIndexes(new ListUnmarshaller(LocalSecondaryIndexDescriptionJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("GlobalSecondaryIndexes")) {
                tableDescription.setGlobalSecondaryIndexes(new ListUnmarshaller(GlobalSecondaryIndexDescriptionJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("StreamSpecification")) {
                tableDescription.setStreamSpecification(StreamSpecificationJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("LatestStreamLabel")) {
                tableDescription.setLatestStreamLabel(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("LatestStreamArn")) {
                tableDescription.setLatestStreamArn(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return tableDescription;
    }

    public static TableDescriptionJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new TableDescriptionJsonUnmarshaller();
        }
        return instance;
    }
}
