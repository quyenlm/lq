package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class WriteRequestJsonUnmarshaller implements Unmarshaller<WriteRequest, JsonUnmarshallerContext> {
    private static WriteRequestJsonUnmarshaller instance;

    WriteRequestJsonUnmarshaller() {
    }

    public WriteRequest unmarshall(JsonUnmarshallerContext context) throws Exception {
        WriteRequest writeRequest = new WriteRequest();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("PutRequest")) {
                writeRequest.setPutRequest(PutRequestJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("DeleteRequest")) {
                writeRequest.setDeleteRequest(DeleteRequestJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return writeRequest;
    }

    public static WriteRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new WriteRequestJsonUnmarshaller();
        }
        return instance;
    }
}
