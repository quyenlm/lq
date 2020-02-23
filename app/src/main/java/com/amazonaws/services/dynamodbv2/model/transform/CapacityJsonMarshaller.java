package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.Capacity;
import com.amazonaws.util.json.AwsJsonWriter;

class CapacityJsonMarshaller {
    private static CapacityJsonMarshaller instance;

    CapacityJsonMarshaller() {
    }

    public void marshall(Capacity capacity, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (capacity.getCapacityUnits() != null) {
            Double capacityUnits = capacity.getCapacityUnits();
            jsonWriter.name("CapacityUnits");
            jsonWriter.value((Number) capacityUnits);
        }
        jsonWriter.endObject();
    }

    public static CapacityJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new CapacityJsonMarshaller();
        }
        return instance;
    }
}
