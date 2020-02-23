package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.DeleteRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class DeleteRequestJsonUnmarshaller implements Unmarshaller<DeleteRequest, JsonUnmarshallerContext> {
    private static DeleteRequestJsonUnmarshaller instance;

    DeleteRequestJsonUnmarshaller() {
    }

    public DeleteRequest unmarshall(JsonUnmarshallerContext context) throws Exception {
        DeleteRequest deleteRequest = new DeleteRequest();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("Key")) {
                deleteRequest.setKey(new MapUnmarshaller(AttributeValueJsonUnmarshaller.getInstance()).unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return deleteRequest;
    }

    public static DeleteRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteRequestJsonUnmarshaller();
        }
        return instance;
    }
}
