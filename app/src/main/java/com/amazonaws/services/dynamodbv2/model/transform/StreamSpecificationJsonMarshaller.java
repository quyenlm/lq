package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.StreamSpecification;
import com.amazonaws.util.json.AwsJsonWriter;

class StreamSpecificationJsonMarshaller {
    private static StreamSpecificationJsonMarshaller instance;

    StreamSpecificationJsonMarshaller() {
    }

    public void marshall(StreamSpecification streamSpecification, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (streamSpecification.getStreamEnabled() != null) {
            Boolean streamEnabled = streamSpecification.getStreamEnabled();
            jsonWriter.name("StreamEnabled");
            jsonWriter.value(streamEnabled.booleanValue());
        }
        if (streamSpecification.getStreamViewType() != null) {
            String streamViewType = streamSpecification.getStreamViewType();
            jsonWriter.name("StreamViewType");
            jsonWriter.value(streamViewType);
        }
        jsonWriter.endObject();
    }

    public static StreamSpecificationJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new StreamSpecificationJsonMarshaller();
        }
        return instance;
    }
}
