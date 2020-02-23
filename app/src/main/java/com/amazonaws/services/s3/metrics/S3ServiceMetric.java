package com.amazonaws.services.s3.metrics;

import com.amazonaws.metrics.ServiceMetricType;
import com.amazonaws.metrics.SimpleMetricType;
import com.amazonaws.metrics.ThroughputMetricType;
import com.amazonaws.services.s3.internal.Constants;

public class S3ServiceMetric extends SimpleMetricType implements ServiceMetricType {
    public static final S3ServiceMetric S3DownloadByteCount = new S3ServiceMetric(metricName(ServiceMetricType.DOWNLOAD_BYTE_COUNT_NAME_SUFFIX));
    public static final S3ThroughputMetric S3DownloadThroughput = new S3ThroughputMetric(metricName(ServiceMetricType.DOWNLOAD_THROUGHPUT_NAME_SUFFIX)) {
        public ServiceMetricType getByteCountMetricType() {
            return S3DownloadByteCount;
        }
    };
    public static final S3ServiceMetric S3UploadByteCount = new S3ServiceMetric(metricName(ServiceMetricType.UPLOAD_BYTE_COUNT_NAME_SUFFIX));
    public static final S3ThroughputMetric S3UploadThroughput = new S3ThroughputMetric(metricName(ServiceMetricType.UPLOAD_THROUGHPUT_NAME_SUFFIX)) {
        public ServiceMetricType getByteCountMetricType() {
            return S3UploadByteCount;
        }
    };
    static final String SERVICE_NAME_PREFIX = "S3";
    private static final S3ServiceMetric[] values = {S3DownloadThroughput, S3DownloadByteCount, S3UploadThroughput, S3UploadByteCount};
    private final String name;

    private static final String metricName(String suffix) {
        return SERVICE_NAME_PREFIX + suffix;
    }

    private S3ServiceMetric(String name2) {
        this.name = name2;
    }

    public String name() {
        return this.name;
    }

    public String getServiceName() {
        return Constants.S3_SERVICE_NAME;
    }

    private static abstract class S3ThroughputMetric extends S3ServiceMetric implements ThroughputMetricType {
        private S3ThroughputMetric(String name) {
            super(name);
        }
    }

    public static S3ServiceMetric[] values() {
        return (S3ServiceMetric[]) values.clone();
    }

    public static S3ServiceMetric valueOf(String name2) {
        for (S3ServiceMetric e : values()) {
            if (e.name().equals(name2)) {
                return e;
            }
        }
        throw new IllegalArgumentException("No S3ServiceMetric defined for the name " + name2);
    }
}
