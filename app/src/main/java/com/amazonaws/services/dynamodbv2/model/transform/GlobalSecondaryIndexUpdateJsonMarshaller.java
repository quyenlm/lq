package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.CreateGlobalSecondaryIndexAction;
import com.amazonaws.services.dynamodbv2.model.DeleteGlobalSecondaryIndexAction;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexUpdate;
import com.amazonaws.services.dynamodbv2.model.UpdateGlobalSecondaryIndexAction;
import com.amazonaws.util.json.AwsJsonWriter;

class GlobalSecondaryIndexUpdateJsonMarshaller {
    private static GlobalSecondaryIndexUpdateJsonMarshaller instance;

    GlobalSecondaryIndexUpdateJsonMarshaller() {
    }

    public void marshall(GlobalSecondaryIndexUpdate globalSecondaryIndexUpdate, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (globalSecondaryIndexUpdate.getUpdate() != null) {
            UpdateGlobalSecondaryIndexAction update = globalSecondaryIndexUpdate.getUpdate();
            jsonWriter.name("Update");
            UpdateGlobalSecondaryIndexActionJsonMarshaller.getInstance().marshall(update, jsonWriter);
        }
        if (globalSecondaryIndexUpdate.getCreate() != null) {
            CreateGlobalSecondaryIndexAction create = globalSecondaryIndexUpdate.getCreate();
            jsonWriter.name("Create");
            CreateGlobalSecondaryIndexActionJsonMarshaller.getInstance().marshall(create, jsonWriter);
        }
        if (globalSecondaryIndexUpdate.getDelete() != null) {
            DeleteGlobalSecondaryIndexAction delete = globalSecondaryIndexUpdate.getDelete();
            jsonWriter.name("Delete");
            DeleteGlobalSecondaryIndexActionJsonMarshaller.getInstance().marshall(delete, jsonWriter);
        }
        jsonWriter.endObject();
    }

    public static GlobalSecondaryIndexUpdateJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new GlobalSecondaryIndexUpdateJsonMarshaller();
        }
        return instance;
    }
}
