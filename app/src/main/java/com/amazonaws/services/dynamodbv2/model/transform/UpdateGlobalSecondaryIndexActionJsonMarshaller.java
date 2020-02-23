package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.UpdateGlobalSecondaryIndexAction;
import com.amazonaws.util.json.AwsJsonWriter;

class UpdateGlobalSecondaryIndexActionJsonMarshaller {
    private static UpdateGlobalSecondaryIndexActionJsonMarshaller instance;

    UpdateGlobalSecondaryIndexActionJsonMarshaller() {
    }

    public void marshall(UpdateGlobalSecondaryIndexAction updateGlobalSecondaryIndexAction, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (updateGlobalSecondaryIndexAction.getIndexName() != null) {
            String indexName = updateGlobalSecondaryIndexAction.getIndexName();
            jsonWriter.name("IndexName");
            jsonWriter.value(indexName);
        }
        if (updateGlobalSecondaryIndexAction.getProvisionedThroughput() != null) {
            ProvisionedThroughput provisionedThroughput = updateGlobalSecondaryIndexAction.getProvisionedThroughput();
            jsonWriter.name("ProvisionedThroughput");
            ProvisionedThroughputJsonMarshaller.getInstance().marshall(provisionedThroughput, jsonWriter);
        }
        jsonWriter.endObject();
    }

    public static UpdateGlobalSecondaryIndexActionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateGlobalSecondaryIndexActionJsonMarshaller();
        }
        return instance;
    }
}
