package com.amazonaws.retry;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.retry.RetryPolicy;
import java.util.Random;

public class PredefinedRetryPolicies {
    private static final int BASE_DELAY_IN_MILLISECONDS = 100;
    public static final RetryPolicy DEFAULT = getDefaultRetryPolicy();
    public static final RetryPolicy.BackoffStrategy DEFAULT_BACKOFF_STRATEGY = new SDKDefaultBackoffStrategy(100, 20000);
    public static final int DEFAULT_MAX_ERROR_RETRY = 3;
    public static final RetryPolicy.RetryCondition DEFAULT_RETRY_CONDITION = new SDKDefaultRetryCondition();
    public static final RetryPolicy DYNAMODB_DEFAULT = getDynamoDBDefaultRetryPolicy();
    public static final int DYNAMODB_DEFAULT_MAX_ERROR_RETRY = 10;
    private static final int MAX_BACKOFF_IN_MILLISECONDS = 20000;

    public static RetryPolicy getDefaultRetryPolicy() {
        return new RetryPolicy(DEFAULT_RETRY_CONDITION, DEFAULT_BACKOFF_STRATEGY, 3, true);
    }

    public static RetryPolicy getDynamoDBDefaultRetryPolicy() {
        return new RetryPolicy(DEFAULT_RETRY_CONDITION, DEFAULT_BACKOFF_STRATEGY, 10, true);
    }

    public static RetryPolicy getDefaultRetryPolicyWithCustomMaxRetries(int maxErrorRetry) {
        return new RetryPolicy(DEFAULT_RETRY_CONDITION, DEFAULT_BACKOFF_STRATEGY, maxErrorRetry, false);
    }

    public static RetryPolicy getDynamoDBDefaultRetryPolicyWithCustomMaxRetries(int maxErrorRetry) {
        return new RetryPolicy(DEFAULT_RETRY_CONDITION, DEFAULT_BACKOFF_STRATEGY, maxErrorRetry, false);
    }

    public static class SDKDefaultRetryCondition implements RetryPolicy.RetryCondition {
        /* JADX WARNING: Code restructure failed: missing block: B:6:0x0016, code lost:
            r0 = (com.amazonaws.AmazonServiceException) r6;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean shouldRetry(com.amazonaws.AmazonWebServiceRequest r5, com.amazonaws.AmazonClientException r6, int r7) {
            /*
                r4 = this;
                r2 = 1
                java.lang.Throwable r3 = r6.getCause()
                boolean r3 = r3 instanceof java.io.IOException
                if (r3 == 0) goto L_0x0012
                java.lang.Throwable r3 = r6.getCause()
                boolean r3 = r3 instanceof java.io.InterruptedIOException
                if (r3 != 0) goto L_0x0012
            L_0x0011:
                return r2
            L_0x0012:
                boolean r3 = r6 instanceof com.amazonaws.AmazonServiceException
                if (r3 == 0) goto L_0x0039
                r0 = r6
                com.amazonaws.AmazonServiceException r0 = (com.amazonaws.AmazonServiceException) r0
                int r1 = r0.getStatusCode()
                r3 = 500(0x1f4, float:7.0E-43)
                if (r1 == r3) goto L_0x0011
                r3 = 503(0x1f7, float:7.05E-43)
                if (r1 == r3) goto L_0x0011
                r3 = 502(0x1f6, float:7.03E-43)
                if (r1 == r3) goto L_0x0011
                r3 = 504(0x1f8, float:7.06E-43)
                if (r1 == r3) goto L_0x0011
                boolean r3 = com.amazonaws.retry.RetryUtils.isThrottlingException(r0)
                if (r3 != 0) goto L_0x0011
                boolean r3 = com.amazonaws.retry.RetryUtils.isClockSkewError(r0)
                if (r3 != 0) goto L_0x0011
            L_0x0039:
                r2 = 0
                goto L_0x0011
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.retry.PredefinedRetryPolicies.SDKDefaultRetryCondition.shouldRetry(com.amazonaws.AmazonWebServiceRequest, com.amazonaws.AmazonClientException, int):boolean");
        }
    }

    private static class SDKDefaultBackoffStrategy implements RetryPolicy.BackoffStrategy {
        private final int baseDelayMs;
        private final int maxDelayMs;
        private final Random random;

        private SDKDefaultBackoffStrategy(int baseDelayMs2, int maxDelayMs2) {
            this.random = new Random();
            this.baseDelayMs = baseDelayMs2;
            this.maxDelayMs = maxDelayMs2;
        }

        public final long delayBeforeNextRetry(AmazonWebServiceRequest originalRequest, AmazonClientException exception, int retries) {
            if (retries <= 0) {
                return 0;
            }
            return (long) this.random.nextInt(Math.min(this.maxDelayMs, (1 << retries) * this.baseDelayMs));
        }
    }
}
