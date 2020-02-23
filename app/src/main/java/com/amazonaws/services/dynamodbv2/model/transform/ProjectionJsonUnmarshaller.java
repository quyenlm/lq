package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class ProjectionJsonUnmarshaller implements Unmarshaller<Projection, JsonUnmarshallerContext> {
    private static ProjectionJsonUnmarshaller instance;

    ProjectionJsonUnmarshaller() {
    }

    public Projection unmarshall(JsonUnmarshallerContext context) throws Exception {
        Projection projection = new Projection();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("ProjectionType")) {
                projection.setProjectionType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("NonKeyAttributes")) {
                projection.setNonKeyAttributes(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return projection;
    }

    public static ProjectionJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ProjectionJsonUnmarshaller();
        }
        return instance;
    }
}
