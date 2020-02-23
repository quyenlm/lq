package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.transform.SimpleTypeStaxUnmarshallers;
import com.amazonaws.transform.StaxUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

class CredentialsStaxUnmarshaller implements Unmarshaller<Credentials, StaxUnmarshallerContext> {
    private static CredentialsStaxUnmarshaller instance;

    CredentialsStaxUnmarshaller() {
    }

    public Credentials unmarshall(StaxUnmarshallerContext context) throws Exception {
        Credentials credentials = new Credentials();
        int originalDepth = context.getCurrentDepth();
        int targetDepth = originalDepth + 1;
        if (context.isStartOfDocument()) {
            targetDepth += 2;
        }
        while (true) {
            int xmlEvent = context.nextEvent();
            if (xmlEvent != 1) {
                if (xmlEvent != 2) {
                    if (xmlEvent == 3 && context.getCurrentDepth() < originalDepth) {
                        break;
                    }
                } else if (context.testExpression("AccessKeyId", targetDepth)) {
                    credentials.setAccessKeyId(SimpleTypeStaxUnmarshallers.StringStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("SecretAccessKey", targetDepth)) {
                    credentials.setSecretAccessKey(SimpleTypeStaxUnmarshallers.StringStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("SessionToken", targetDepth)) {
                    credentials.setSessionToken(SimpleTypeStaxUnmarshallers.StringStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("Expiration", targetDepth)) {
                    credentials.setExpiration(SimpleTypeStaxUnmarshallers.DateStaxUnmarshaller.getInstance().unmarshall(context));
                }
            } else {
                break;
            }
        }
        return credentials;
    }

    public static CredentialsStaxUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CredentialsStaxUnmarshaller();
        }
        return instance;
    }
}
