package com.amazonaws.services.s3.model;

import com.amazonaws.internal.MetricAware;
import com.amazonaws.internal.SdkFilterInputStream;
import com.amazonaws.metrics.AwsSdkMetrics;
import com.amazonaws.metrics.MetricFilterInputStream;
import com.amazonaws.services.s3.metrics.S3ServiceMetric;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.HttpRequestBase;

public class S3ObjectInputStream extends SdkFilterInputStream {
    private final HttpRequestBase httpRequest;

    public S3ObjectInputStream(InputStream in) {
        this(in, (HttpRequestBase) null);
    }

    @Deprecated
    public S3ObjectInputStream(InputStream in, HttpRequestBase httpRequest2) {
        this(in, httpRequest2, wrapWithByteCounting(in));
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    @Deprecated
    public S3ObjectInputStream(InputStream in, HttpRequestBase httpRequest2, boolean collectMetrics) {
        super(collectMetrics ? new MetricFilterInputStream(S3ServiceMetric.S3DownloadThroughput, in) : in);
        this.httpRequest = httpRequest2;
    }

    private static boolean wrapWithByteCounting(InputStream in) {
        if (!AwsSdkMetrics.isMetricsEnabled()) {
            return false;
        }
        if (!(in instanceof MetricAware) || !((MetricAware) in).isMetricActivated()) {
            return true;
        }
        return false;
    }

    public void abort() {
        try {
            close();
        } catch (IOException e) {
            LogFactory.getLog((Class) getClass()).debug("FYI", e);
        }
    }

    @Deprecated
    public HttpRequestBase getHttpRequest() {
        return this.httpRequest;
    }
}
