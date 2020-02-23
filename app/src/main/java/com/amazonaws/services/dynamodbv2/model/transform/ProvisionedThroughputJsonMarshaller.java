package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.util.json.AwsJsonWriter;

class ProvisionedThroughputJsonMarshaller {
    private static ProvisionedThroughputJsonMarshaller instance;

    ProvisionedThroughputJsonMarshaller() {
    }

    public void marshall(ProvisionedThroughput provisionedThroughput, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (provisionedThroughput.getReadCapacityUnits() != null) {
            Long readCapacityUnits = provisionedThroughput.getReadCapacityUnits();
            jsonWriter.name("ReadCapacityUnits");
            jsonWriter.value((Number) readCapacityUnits);
        }
        if (provisionedThroughput.getWriteCapacityUnits() != null) {
            Long writeCapacityUnits = provisionedThroughput.getWriteCapacityUnits();
            jsonWriter.name("WriteCapacityUnits");
            jsonWriter.value((Number) writeCapacityUnits);
        }
        jsonWriter.endObject();
    }

    public static ProvisionedThroughputJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ProvisionedThroughputJsonMarshaller();
        }
        return instance;
    }
}
