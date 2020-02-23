package com.amazonaws.auth;

import com.amazonaws.AmazonClientException;
import com.appsflyer.share.Constants;
import com.tencent.tp.a.h;
import java.io.IOException;
import java.io.InputStream;

@Deprecated
public class ClasspathPropertiesFileCredentialsProvider implements AWSCredentialsProvider {
    private static String DEFAULT_PROPERTIES_FILE = "AwsCredentials.properties";
    private final String credentialsFilePath;

    public ClasspathPropertiesFileCredentialsProvider() {
        this(DEFAULT_PROPERTIES_FILE);
    }

    public ClasspathPropertiesFileCredentialsProvider(String credentialsFilePath2) {
        if (credentialsFilePath2 == null) {
            throw new IllegalArgumentException("Credentials file path cannot be null");
        } else if (!credentialsFilePath2.startsWith(Constants.URL_PATH_DELIMITER)) {
            this.credentialsFilePath = Constants.URL_PATH_DELIMITER + credentialsFilePath2;
        } else {
            this.credentialsFilePath = credentialsFilePath2;
        }
    }

    public AWSCredentials getCredentials() {
        InputStream inputStream = getClass().getResourceAsStream(this.credentialsFilePath);
        if (inputStream == null) {
            throw new AmazonClientException("Unable to load AWS credentials from the " + this.credentialsFilePath + " file on the classpath");
        }
        try {
            return new PropertiesCredentials(inputStream);
        } catch (IOException e) {
            throw new AmazonClientException("Unable to load AWS credentials from the " + this.credentialsFilePath + " file on the classpath", e);
        }
    }

    public void refresh() {
    }

    public String toString() {
        return getClass().getSimpleName() + h.a + this.credentialsFilePath + h.b;
    }
}
