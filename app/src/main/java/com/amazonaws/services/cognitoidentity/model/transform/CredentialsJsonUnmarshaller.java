package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class CredentialsJsonUnmarshaller implements Unmarshaller<Credentials, JsonUnmarshallerContext> {
    private static CredentialsJsonUnmarshaller instance;

    CredentialsJsonUnmarshaller() {
    }

    public Credentials unmarshall(JsonUnmarshallerContext context) throws Exception {
        Credentials credentials = new Credentials();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("AccessKeyId")) {
                credentials.setAccessKeyId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("SecretKey")) {
                credentials.setSecretKey(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("SessionToken")) {
                credentials.setSessionToken(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Expiration")) {
                credentials.setExpiration(SimpleTypeJsonUnmarshallers.DateJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return credentials;
    }

    public static CredentialsJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CredentialsJsonUnmarshaller();
        }
        return instance;
    }
}
