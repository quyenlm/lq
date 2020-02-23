package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputDescription;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Date;

class ProvisionedThroughputDescriptionJsonMarshaller {
    private static ProvisionedThroughputDescriptionJsonMarshaller instance;

    ProvisionedThroughputDescriptionJsonMarshaller() {
    }

    public void marshall(ProvisionedThroughputDescription provisionedThroughputDescription, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (provisionedThroughputDescription.getLastIncreaseDateTime() != null) {
            Date lastIncreaseDateTime = provisionedThroughputDescription.getLastIncreaseDateTime();
            jsonWriter.name("LastIncreaseDateTime");
            jsonWriter.value(lastIncreaseDateTime);
        }
        if (provisionedThroughputDescription.getLastDecreaseDateTime() != null) {
            Date lastDecreaseDateTime = provisionedThroughputDescription.getLastDecreaseDateTime();
            jsonWriter.name("LastDecreaseDateTime");
            jsonWriter.value(lastDecreaseDateTime);
        }
        if (provisionedThroughputDescription.getNumberOfDecreasesToday() != null) {
            Long numberOfDecreasesToday = provisionedThroughputDescription.getNumberOfDecreasesToday();
            jsonWriter.name("NumberOfDecreasesToday");
            jsonWriter.value((Number) numberOfDecreasesToday);
        }
        if (provisionedThroughputDescription.getReadCapacityUnits() != null) {
            Long readCapacityUnits = provisionedThroughputDescription.getReadCapacityUnits();
            jsonWriter.name("ReadCapacityUnits");
            jsonWriter.value((Number) readCapacityUnits);
        }
        if (provisionedThroughputDescription.getWriteCapacityUnits() != null) {
            Long writeCapacityUnits = provisionedThroughputDescription.getWriteCapacityUnits();
            jsonWriter.name("WriteCapacityUnits");
            jsonWriter.value((Number) writeCapacityUnits);
        }
        jsonWriter.endObject();
    }

    public static ProvisionedThroughputDescriptionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ProvisionedThroughputDescriptionJsonMarshaller();
        }
        return instance;
    }
}
