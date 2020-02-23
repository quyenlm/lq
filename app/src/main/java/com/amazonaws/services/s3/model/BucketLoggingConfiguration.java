package com.amazonaws.services.s3.model;

public class BucketLoggingConfiguration {
    private String destinationBucketName = null;
    private String logFilePrefix = null;

    public BucketLoggingConfiguration() {
    }

    public BucketLoggingConfiguration(String destinationBucketName2, String logFilePrefix2) {
        setLogFilePrefix(logFilePrefix2);
        setDestinationBucketName(destinationBucketName2);
    }

    public boolean isLoggingEnabled() {
        return (this.destinationBucketName == null || this.logFilePrefix == null) ? false : true;
    }

    public String getLogFilePrefix() {
        return this.logFilePrefix;
    }

    public void setLogFilePrefix(String logFilePrefix2) {
        if (logFilePrefix2 == null) {
            logFilePrefix2 = "";
        }
        this.logFilePrefix = logFilePrefix2;
    }

    public String getDestinationBucketName() {
        return this.destinationBucketName;
    }

    public void setDestinationBucketName(String destinationBucketName2) {
        this.destinationBucketName = destinationBucketName2;
    }

    public String toString() {
        String result = "LoggingConfiguration enabled=" + isLoggingEnabled();
        if (isLoggingEnabled()) {
            return result + ", destinationBucketName=" + getDestinationBucketName() + ", logFilePrefix=" + getLogFilePrefix();
        }
        return result;
    }
}
