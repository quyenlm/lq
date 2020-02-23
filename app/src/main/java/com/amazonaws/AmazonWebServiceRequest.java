package com.amazonaws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.metrics.RequestMetricCollector;

public abstract class AmazonWebServiceRequest {
    private AWSCredentials credentials;
    private final RequestClientOptions requestClientOptions = new RequestClientOptions();
    @Deprecated
    private RequestMetricCollector requestMetricCollector;

    public void setRequestCredentials(AWSCredentials credentials2) {
        this.credentials = credentials2;
    }

    public AWSCredentials getRequestCredentials() {
        return this.credentials;
    }

    public RequestClientOptions getRequestClientOptions() {
        return this.requestClientOptions;
    }

    @Deprecated
    public RequestMetricCollector getRequestMetricCollector() {
        return this.requestMetricCollector;
    }

    @Deprecated
    public void setRequestMetricCollector(RequestMetricCollector requestMetricCollector2) {
        this.requestMetricCollector = requestMetricCollector2;
    }

    @Deprecated
    public <T extends AmazonWebServiceRequest> T withRequestMetricCollector(RequestMetricCollector metricCollector) {
        setRequestMetricCollector(metricCollector);
        return this;
    }
}
