package com.amazonaws.retry;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonWebServiceRequest;

public final class RetryPolicy {
    private final BackoffStrategy backoffStrategy;
    private final boolean honorMaxErrorRetryInClientConfig;
    private final int maxErrorRetry;
    private final RetryCondition retryCondition;

    public interface BackoffStrategy {
        long delayBeforeNextRetry(AmazonWebServiceRequest amazonWebServiceRequest, AmazonClientException amazonClientException, int i);
    }

    public interface RetryCondition {
        boolean shouldRetry(AmazonWebServiceRequest amazonWebServiceRequest, AmazonClientException amazonClientException, int i);
    }

    public RetryPolicy(RetryCondition retryCondition2, BackoffStrategy backoffStrategy2, int maxErrorRetry2, boolean honorMaxErrorRetryInClientConfig2) {
        retryCondition2 = retryCondition2 == null ? PredefinedRetryPolicies.DEFAULT_RETRY_CONDITION : retryCondition2;
        backoffStrategy2 = backoffStrategy2 == null ? PredefinedRetryPolicies.DEFAULT_BACKOFF_STRATEGY : backoffStrategy2;
        if (maxErrorRetry2 < 0) {
            throw new IllegalArgumentException("Please provide a non-negative value for maxErrorRetry.");
        }
        this.retryCondition = retryCondition2;
        this.backoffStrategy = backoffStrategy2;
        this.maxErrorRetry = maxErrorRetry2;
        this.honorMaxErrorRetryInClientConfig = honorMaxErrorRetryInClientConfig2;
    }

    public RetryCondition getRetryCondition() {
        return this.retryCondition;
    }

    public BackoffStrategy getBackoffStrategy() {
        return this.backoffStrategy;
    }

    public int getMaxErrorRetry() {
        return this.maxErrorRetry;
    }

    public boolean isMaxErrorRetryInClientConfigHonored() {
        return this.honorMaxErrorRetryInClientConfig;
    }
}
