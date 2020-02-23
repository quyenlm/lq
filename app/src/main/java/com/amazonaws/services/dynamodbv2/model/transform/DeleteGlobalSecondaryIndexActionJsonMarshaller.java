package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.DeleteGlobalSecondaryIndexAction;
import com.amazonaws.util.json.AwsJsonWriter;

class DeleteGlobalSecondaryIndexActionJsonMarshaller {
    private static DeleteGlobalSecondaryIndexActionJsonMarshaller instance;

    DeleteGlobalSecondaryIndexActionJsonMarshaller() {
    }

    public void marshall(DeleteGlobalSecondaryIndexAction deleteGlobalSecondaryIndexAction, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (deleteGlobalSecondaryIndexAction.getIndexName() != null) {
            String indexName = deleteGlobalSecondaryIndexAction.getIndexName();
            jsonWriter.name("IndexName");
            jsonWriter.value(indexName);
        }
        jsonWriter.endObject();
    }

    public static DeleteGlobalSecondaryIndexActionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteGlobalSecondaryIndexActionJsonMarshaller();
        }
        return instance;
    }
}
