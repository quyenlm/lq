package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

class ProjectionJsonMarshaller {
    private static ProjectionJsonMarshaller instance;

    ProjectionJsonMarshaller() {
    }

    public void marshall(Projection projection, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (projection.getProjectionType() != null) {
            String projectionType = projection.getProjectionType();
            jsonWriter.name("ProjectionType");
            jsonWriter.value(projectionType);
        }
        if (projection.getNonKeyAttributes() != null) {
            List<String> nonKeyAttributes = projection.getNonKeyAttributes();
            jsonWriter.name("NonKeyAttributes");
            jsonWriter.beginArray();
            for (String nonKeyAttributesItem : nonKeyAttributes) {
                if (nonKeyAttributesItem != null) {
                    jsonWriter.value(nonKeyAttributesItem);
                }
            }
            jsonWriter.endArray();
        }
        jsonWriter.endObject();
    }

    public static ProjectionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ProjectionJsonMarshaller();
        }
        return instance;
    }
}
