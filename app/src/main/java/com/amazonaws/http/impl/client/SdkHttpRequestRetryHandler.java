package com.amazonaws.http.impl.client;

import com.amazonaws.metrics.MetricType;
import com.amazonaws.util.AWSRequestMetrics;
import java.io.IOException;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

public class SdkHttpRequestRetryHandler extends DefaultHttpRequestRetryHandler {
    public static final SdkHttpRequestRetryHandler Singleton = new SdkHttpRequestRetryHandler();

    private SdkHttpRequestRetryHandler() {
    }

    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        AWSRequestMetrics awsRequestMetrics;
        boolean retry = super.retryRequest(exception, executionCount, context);
        if (retry && (awsRequestMetrics = (AWSRequestMetrics) context.getAttribute(AWSRequestMetrics.class.getSimpleName())) != null) {
            awsRequestMetrics.incrementCounter((MetricType) AWSRequestMetrics.Field.HttpClientRetryCount);
        }
        return retry;
    }
}
