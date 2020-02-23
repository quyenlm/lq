package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.PutRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class PutRequestJsonUnmarshaller implements Unmarshaller<PutRequest, JsonUnmarshallerContext> {
    private static PutRequestJsonUnmarshaller instance;

    PutRequestJsonUnmarshaller() {
    }

    public PutRequest unmarshall(JsonUnmarshallerContext context) throws Exception {
        PutRequest putRequest = new PutRequest();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("Item")) {
                putRequest.setItem(new MapUnmarshaller(AttributeValueJsonUnmarshaller.getInstance()).unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return putRequest;
    }

    public static PutRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new PutRequestJsonUnmarshaller();
        }
        return instance;
    }
}
