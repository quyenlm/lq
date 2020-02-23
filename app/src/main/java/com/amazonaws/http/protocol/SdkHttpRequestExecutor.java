package com.amazonaws.http.protocol;

import com.amazonaws.metrics.MetricType;
import com.amazonaws.util.AWSRequestMetrics;
import java.io.IOException;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestExecutor;

public class SdkHttpRequestExecutor extends HttpRequestExecutor {
    /* access modifiers changed from: protected */
    public HttpResponse doSendRequest(HttpRequest request, HttpClientConnection conn, HttpContext context) throws IOException, HttpException {
        AWSRequestMetrics awsRequestMetrics = (AWSRequestMetrics) context.getAttribute(AWSRequestMetrics.class.getSimpleName());
        if (awsRequestMetrics == null) {
            return super.doSendRequest(request, conn, context);
        }
        awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.HttpClientSendRequestTime);
        try {
            return super.doSendRequest(request, conn, context);
        } finally {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.HttpClientSendRequestTime);
        }
    }

    /* access modifiers changed from: protected */
    public HttpResponse doReceiveResponse(HttpRequest request, HttpClientConnection conn, HttpContext context) throws HttpException, IOException {
        AWSRequestMetrics awsRequestMetrics = (AWSRequestMetrics) context.getAttribute(AWSRequestMetrics.class.getSimpleName());
        if (awsRequestMetrics == null) {
            return super.doReceiveResponse(request, conn, context);
        }
        awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.HttpClientReceiveResponseTime);
        try {
            return super.doReceiveResponse(request, conn, context);
        } finally {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.HttpClientReceiveResponseTime);
        }
    }
}
