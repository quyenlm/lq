package com.amazonaws.http;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Request;
import com.amazonaws.RequestClientOptions;
import com.amazonaws.Response;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.Signer;
import com.amazonaws.handlers.CredentialsRequestHandler;
import com.amazonaws.handlers.RequestHandler2;
import com.amazonaws.internal.CRC32MismatchException;
import com.amazonaws.metrics.MetricType;
import com.amazonaws.metrics.RequestMetricCollector;
import com.amazonaws.retry.RetryPolicy;
import com.amazonaws.retry.RetryUtils;
import com.amazonaws.util.AWSRequestMetrics;
import com.amazonaws.util.DateUtils;
import com.amazonaws.util.TimingInfo;
import com.appsflyer.share.Constants;
import com.tencent.tp.a.h;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AmazonHttpClient {
    private static final String HEADER_SDK_RETRY_INFO = "aws-sdk-retry";
    private static final String HEADER_SDK_TRANSACTION_ID = "aws-sdk-invocation-id";
    private static final String HEADER_USER_AGENT = "User-Agent";
    static final Log log = LogFactory.getLog(AmazonHttpClient.class);
    private static final Log requestLog = LogFactory.getLog("com.amazonaws.request");
    final ClientConfiguration config;
    final HttpClient httpClient;
    private final HttpRequestFactory requestFactory;
    private final RequestMetricCollector requestMetricCollector;

    public AmazonHttpClient(ClientConfiguration config2) {
        this(config2, (HttpClient) new UrlHttpClient(config2));
    }

    @Deprecated
    public AmazonHttpClient(ClientConfiguration config2, RequestMetricCollector requestMetricCollector2) {
        this(config2, new UrlHttpClient(config2), requestMetricCollector2);
    }

    public AmazonHttpClient(ClientConfiguration config2, HttpClient httpClient2) {
        this.requestFactory = new HttpRequestFactory();
        this.config = config2;
        this.httpClient = httpClient2;
        this.requestMetricCollector = null;
    }

    @Deprecated
    public AmazonHttpClient(ClientConfiguration config2, HttpClient httpClient2, RequestMetricCollector requestMetricCollector2) {
        this.requestFactory = new HttpRequestFactory();
        this.config = config2;
        this.httpClient = httpClient2;
        this.requestMetricCollector = requestMetricCollector2;
    }

    @Deprecated
    public ResponseMetadata getResponseMetadataForRequest(AmazonWebServiceRequest request) {
        return null;
    }

    public <T> Response<T> execute(Request<?> request, HttpResponseHandler<AmazonWebServiceResponse<T>> responseHandler, HttpResponseHandler<AmazonServiceException> errorResponseHandler, ExecutionContext executionContext) throws AmazonClientException, AmazonServiceException {
        if (executionContext == null) {
            throw new AmazonClientException("Internal SDK Error: No execution context parameter specified.");
        }
        List<RequestHandler2> requestHandler2s = requestHandler2s(request, executionContext);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        Response<T> response = null;
        try {
            response = executeHelper(request, responseHandler, errorResponseHandler, executionContext);
            afterResponse(request, requestHandler2s, response, awsRequestMetrics.getTimingInfo().endTiming());
            return response;
        } catch (AmazonClientException e) {
            afterError(request, response, requestHandler2s, e);
            throw e;
        }
    }

    /* access modifiers changed from: package-private */
    public void afterError(Request<?> request, Response<?> response, List<RequestHandler2> requestHandler2s, AmazonClientException e) {
        for (RequestHandler2 handler2 : requestHandler2s) {
            handler2.afterError(request, response, e);
        }
    }

    /* access modifiers changed from: package-private */
    public <T> void afterResponse(Request<?> request, List<RequestHandler2> requestHandler2s, Response<T> response, TimingInfo timingInfo) {
        for (RequestHandler2 handler2 : requestHandler2s) {
            handler2.afterResponse(request, response);
        }
    }

    /* access modifiers changed from: package-private */
    public List<RequestHandler2> requestHandler2s(Request<?> request, ExecutionContext executionContext) {
        List<RequestHandler2> requestHandler2s = executionContext.getRequestHandler2s();
        if (requestHandler2s == null) {
            return Collections.emptyList();
        }
        for (RequestHandler2 requestHandler2 : requestHandler2s) {
            if (requestHandler2 instanceof CredentialsRequestHandler) {
                ((CredentialsRequestHandler) requestHandler2).setCredentials(executionContext.getCredentials());
            }
            requestHandler2.beforeRequest(request);
        }
        return requestHandler2s;
    }

    /* access modifiers changed from: package-private */
    public <T> Response<T> executeHelper(Request<?> request, HttpResponseHandler<AmazonWebServiceResponse<T>> responseHandler, HttpResponseHandler<AmazonServiceException> errorResponseHandler, ExecutionContext executionContext) throws AmazonClientException, AmazonServiceException {
        boolean leaveHttpConnectionOpen = false;
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.addProperty((MetricType) AWSRequestMetrics.Field.ServiceName, (Object) request.getServiceName());
        awsRequestMetrics.addProperty((MetricType) AWSRequestMetrics.Field.ServiceEndpoint, (Object) request.getEndpoint());
        setUserAgent(request);
        request.addHeader(HEADER_SDK_TRANSACTION_ID, UUID.randomUUID().toString());
        int requestCount = 0;
        long lastBackoffDelay = 0;
        URI redirectedURI = null;
        AmazonClientException retriedException = null;
        LinkedHashMap linkedHashMap = new LinkedHashMap(request.getParameters());
        HashMap hashMap = new HashMap(request.getHeaders());
        InputStream originalContent = request.getContent();
        if (originalContent != null && originalContent.markSupported()) {
            originalContent.mark(-1);
        }
        AWSCredentials credentials = executionContext.getCredentials();
        Signer signer = null;
        HttpResponse httpResponse = null;
        HttpRequest httpRequest = null;
        while (true) {
            requestCount++;
            awsRequestMetrics.setCounter((MetricType) AWSRequestMetrics.Field.RequestCount, (long) requestCount);
            if (requestCount > 1) {
                request.setParameters(linkedHashMap);
                request.setHeaders(hashMap);
                request.setContent(originalContent);
            }
            if (redirectedURI != null) {
                request.setEndpoint(URI.create(redirectedURI.getScheme() + "://" + redirectedURI.getAuthority()));
                request.setResourcePath(redirectedURI.getPath());
            }
            if (requestCount > 1) {
                try {
                    awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.RetryPauseTime);
                    lastBackoffDelay = pauseBeforeNextRetry(request.getOriginalRequest(), retriedException, requestCount, this.config.getRetryPolicy());
                    awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.RetryPauseTime);
                    InputStream content = request.getContent();
                    if (content != null && content.markSupported()) {
                        content.reset();
                    }
                } catch (IOException ioe) {
                    try {
                        if (log.isDebugEnabled()) {
                            log.debug("Unable to execute HTTP request: " + ioe.getMessage(), ioe);
                        }
                        awsRequestMetrics.incrementCounter((MetricType) AWSRequestMetrics.Field.Exception);
                        awsRequestMetrics.addProperty((MetricType) AWSRequestMetrics.Field.Exception, (Object) ioe);
                        awsRequestMetrics.addProperty((MetricType) AWSRequestMetrics.Field.AWSRequestID, (Object) null);
                        AmazonClientException ace = new AmazonClientException("Unable to execute HTTP request: " + ioe.getMessage(), ioe);
                        if (!shouldRetry(request.getOriginalRequest(), httpRequest.getContent(), ace, requestCount, this.config.getRetryPolicy())) {
                            throw ace;
                        }
                        retriedException = ace;
                        resetRequestAfterError(request, ioe);
                        if (!leaveHttpConnectionOpen && httpResponse != null) {
                            try {
                            } catch (IOException e) {
                                log.warn("Cannot close the response content.", e);
                            }
                        }
                    } finally {
                        if (!leaveHttpConnectionOpen && httpResponse != null) {
                            try {
                                if (httpResponse.getRawContent() != null) {
                                    httpResponse.getRawContent().close();
                                }
                            } catch (IOException e2) {
                                log.warn("Cannot close the response content.", e2);
                            }
                        }
                    }
                } catch (RuntimeException e3) {
                    throw ((RuntimeException) handleUnexpectedFailure(e3, awsRequestMetrics));
                } catch (Error e4) {
                    throw ((Error) handleUnexpectedFailure(e4, awsRequestMetrics));
                } catch (Throwable th) {
                    awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.RetryPauseTime);
                    throw th;
                }
            }
            request.addHeader(HEADER_SDK_RETRY_INFO, (requestCount - 1) + Constants.URL_PATH_DELIMITER + lastBackoffDelay);
            if (signer == null) {
                signer = executionContext.getSignerByURI(request.getEndpoint());
            }
            if (!(signer == null || credentials == null)) {
                awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.RequestSigningTime);
                signer.sign(request, credentials);
                awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.RequestSigningTime);
            }
            if (requestLog.isDebugEnabled()) {
                requestLog.debug("Sending Request: " + request.toString());
            }
            httpRequest = this.requestFactory.createHttpRequest(request, this.config, executionContext);
            retriedException = null;
            awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.HttpRequestTime);
            httpResponse = this.httpClient.execute(httpRequest);
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.HttpRequestTime);
            if (isRequestSuccessful(httpResponse)) {
                awsRequestMetrics.addProperty((MetricType) AWSRequestMetrics.Field.StatusCode, (Object) Integer.valueOf(httpResponse.getStatusCode()));
                boolean leaveHttpConnectionOpen2 = responseHandler.needsConnectionLeftOpen();
                Response<T> response = new Response<>(handleResponse(request, responseHandler, httpResponse, executionContext), httpResponse);
                if (!leaveHttpConnectionOpen2 && httpResponse != null) {
                    try {
                        if (httpResponse.getRawContent() != null) {
                            httpResponse.getRawContent().close();
                        }
                    } catch (IOException e5) {
                        log.warn("Cannot close the response content.", e5);
                    }
                }
                return response;
            }
            if (isTemporaryRedirect(httpResponse)) {
                String redirectedLocation = httpResponse.getHeaders().get("Location");
                log.debug("Redirecting to: " + redirectedLocation);
                redirectedURI = URI.create(redirectedLocation);
                awsRequestMetrics.addProperty((MetricType) AWSRequestMetrics.Field.StatusCode, (Object) Integer.valueOf(httpResponse.getStatusCode()));
                awsRequestMetrics.addProperty((MetricType) AWSRequestMetrics.Field.RedirectLocation, (Object) redirectedLocation);
                awsRequestMetrics.addProperty((MetricType) AWSRequestMetrics.Field.AWSRequestID, (Object) null);
            } else {
                leaveHttpConnectionOpen = errorResponseHandler.needsConnectionLeftOpen();
                AmazonServiceException ase = handleErrorResponse(request, errorResponseHandler, httpResponse);
                awsRequestMetrics.addProperty((MetricType) AWSRequestMetrics.Field.AWSRequestID, (Object) ase.getRequestId());
                awsRequestMetrics.addProperty((MetricType) AWSRequestMetrics.Field.AWSErrorCode, (Object) ase.getErrorCode());
                awsRequestMetrics.addProperty((MetricType) AWSRequestMetrics.Field.StatusCode, (Object) Integer.valueOf(ase.getStatusCode()));
                if (!shouldRetry(request.getOriginalRequest(), httpRequest.getContent(), ase, requestCount, this.config.getRetryPolicy())) {
                    throw ase;
                }
                retriedException = ase;
                if (RetryUtils.isClockSkewError(ase)) {
                    SDKGlobalConfiguration.setGlobalTimeOffset(parseClockSkewOffset(httpResponse, ase));
                }
                resetRequestAfterError(request, ase);
            }
            if (!leaveHttpConnectionOpen && httpResponse != null) {
                try {
                    if (httpResponse.getRawContent() != null) {
                        httpResponse.getRawContent().close();
                    }
                } catch (IOException e6) {
                    log.warn("Cannot close the response content.", e6);
                }
            }
        }
    }

    private <T extends Throwable> T handleUnexpectedFailure(T t, AWSRequestMetrics awsRequestMetrics) {
        awsRequestMetrics.incrementCounter((MetricType) AWSRequestMetrics.Field.Exception);
        awsRequestMetrics.addProperty((MetricType) AWSRequestMetrics.Field.Exception, (Object) t);
        return t;
    }

    /* access modifiers changed from: package-private */
    public void resetRequestAfterError(Request<?> request, Exception cause) throws AmazonClientException {
        if (request.getContent() != null) {
            if (!request.getContent().markSupported()) {
                throw new AmazonClientException("Encountered an exception and stream is not resettable", cause);
            }
            try {
                request.getContent().reset();
            } catch (IOException e) {
                throw new AmazonClientException("Encountered an exception and couldn't reset the stream to retry", cause);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setUserAgent(Request<?> request) {
        RequestClientOptions opts;
        String userAgentMarker;
        String userAgent = ClientConfiguration.DEFAULT_USER_AGENT;
        AmazonWebServiceRequest awsreq = request.getOriginalRequest();
        if (!(awsreq == null || (opts = awsreq.getRequestClientOptions()) == null || (userAgentMarker = opts.getClientMarker(RequestClientOptions.Marker.USER_AGENT)) == null)) {
            userAgent = createUserAgentString(userAgent, userAgentMarker);
        }
        if (!ClientConfiguration.DEFAULT_USER_AGENT.equals(this.config.getUserAgent())) {
            userAgent = createUserAgentString(userAgent, this.config.getUserAgent());
        }
        request.addHeader("User-Agent", userAgent);
    }

    static String createUserAgentString(String existingUserAgentString, String userAgent) {
        return existingUserAgentString.contains(userAgent) ? existingUserAgentString : existingUserAgentString.trim() + " " + userAgent.trim();
    }

    public void shutdown() {
        this.httpClient.shutdown();
    }

    private boolean shouldRetry(AmazonWebServiceRequest originalRequest, InputStream inputStream, AmazonClientException exception, int requestCount, RetryPolicy retryPolicy) {
        int retries = requestCount - 1;
        int maxErrorRetry = this.config.getMaxErrorRetry();
        if (maxErrorRetry < 0 || !retryPolicy.isMaxErrorRetryInClientConfigHonored()) {
            maxErrorRetry = retryPolicy.getMaxErrorRetry();
        }
        if (retries >= maxErrorRetry) {
            return false;
        }
        if (inputStream == null || inputStream.markSupported()) {
            return retryPolicy.getRetryCondition().shouldRetry(originalRequest, exception, retries);
        }
        if (!log.isDebugEnabled()) {
            return false;
        }
        log.debug("Content not repeatable");
        return false;
    }

    private static boolean isTemporaryRedirect(HttpResponse response) {
        int statusCode = response.getStatusCode();
        String location = response.getHeaders().get("Location");
        return statusCode == 307 && location != null && !location.isEmpty();
    }

    private boolean isRequestSuccessful(HttpResponse response) {
        int statusCode = response.getStatusCode();
        return statusCode >= 200 && statusCode < 300;
    }

    /* access modifiers changed from: package-private */
    public <T> T handleResponse(Request<?> request, HttpResponseHandler<AmazonWebServiceResponse<T>> responseHandler, HttpResponse response, ExecutionContext executionContext) throws IOException {
        AWSRequestMetrics awsRequestMetrics;
        try {
            awsRequestMetrics = executionContext.getAwsRequestMetrics();
            awsRequestMetrics.startEvent((MetricType) AWSRequestMetrics.Field.ResponseProcessingTime);
            AmazonWebServiceResponse<? extends T> awsResponse = responseHandler.handle(response);
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ResponseProcessingTime);
            if (awsResponse == null) {
                throw new RuntimeException("Unable to unmarshall response metadata. Response Code: " + response.getStatusCode() + ", Response Text: " + response.getStatusText());
            }
            if (requestLog.isDebugEnabled()) {
                requestLog.debug("Received successful response: " + response.getStatusCode() + ", AWS Request ID: " + awsResponse.getRequestId());
            }
            awsRequestMetrics.addProperty((MetricType) AWSRequestMetrics.Field.AWSRequestID, (Object) awsResponse.getRequestId());
            return awsResponse.getResult();
        } catch (CRC32MismatchException e) {
            throw e;
        } catch (IOException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new AmazonClientException("Unable to unmarshall response (" + e3.getMessage() + "). Response Code: " + response.getStatusCode() + ", Response Text: " + response.getStatusText(), e3);
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ResponseProcessingTime);
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public AmazonServiceException handleErrorResponse(Request<?> request, HttpResponseHandler<AmazonServiceException> errorResponseHandler, HttpResponse response) throws IOException {
        AmazonServiceException exception;
        int status = response.getStatusCode();
        try {
            exception = errorResponseHandler.handle(response);
            requestLog.debug("Received error response: " + exception.toString());
        } catch (Exception e) {
            if (status == 413) {
                exception = new AmazonServiceException("Request entity too large");
                exception.setServiceName(request.getServiceName());
                exception.setStatusCode(413);
                exception.setErrorType(AmazonServiceException.ErrorType.Client);
                exception.setErrorCode("Request entity too large");
            } else if (status == 503 && "Service Unavailable".equalsIgnoreCase(response.getStatusText())) {
                exception = new AmazonServiceException("Service unavailable");
                exception.setServiceName(request.getServiceName());
                exception.setStatusCode(503);
                exception.setErrorType(AmazonServiceException.ErrorType.Service);
                exception.setErrorCode("Service unavailable");
            } else if (e instanceof IOException) {
                throw ((IOException) e);
            } else {
                throw new AmazonClientException("Unable to unmarshall error response (" + e.getMessage() + "). Response Code: " + status + ", Response Text: " + response.getStatusText(), e);
            }
        }
        exception.setStatusCode(status);
        exception.setServiceName(request.getServiceName());
        exception.fillInStackTrace();
        return exception;
    }

    private long pauseBeforeNextRetry(AmazonWebServiceRequest originalRequest, AmazonClientException previousException, int requestCount, RetryPolicy retryPolicy) {
        int retries = (requestCount - 1) - 1;
        long delay = retryPolicy.getBackoffStrategy().delayBeforeNextRetry(originalRequest, previousException, retries);
        if (log.isDebugEnabled()) {
            log.debug("Retriable error detected, will retry in " + delay + "ms, attempt number: " + retries);
        }
        try {
            Thread.sleep(delay);
            return delay;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AmazonClientException(e.getMessage(), e);
        }
    }

    private String getServerDateFromException(String body) {
        int endPos;
        int startPos = body.indexOf(h.a);
        if (body.contains(" + 15")) {
            endPos = body.indexOf(" + 15");
        } else {
            endPos = body.indexOf(" - 15");
        }
        return body.substring(startPos + 1, endPos);
    }

    /* access modifiers changed from: package-private */
    public int parseClockSkewOffset(HttpResponse response, AmazonServiceException exception) {
        Date serverDate;
        Date deviceDate = new Date();
        String serverDateStr = null;
        String responseDateHeader = response.getHeaders().get("Date");
        if (responseDateHeader != null) {
            try {
                if (!responseDateHeader.isEmpty()) {
                    serverDateStr = responseDateHeader;
                    serverDate = DateUtils.parseRFC822Date(serverDateStr);
                    return (int) ((deviceDate.getTime() - serverDate.getTime()) / 1000);
                }
            } catch (RuntimeException e) {
                log.warn("Unable to parse clock skew offset from response: " + serverDateStr, e);
                return 0;
            }
        }
        serverDate = DateUtils.parseCompressedISO8601Date(getServerDateFromException(exception.getMessage()));
        return (int) ((deviceDate.getTime() - serverDate.getTime()) / 1000);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        shutdown();
        super.finalize();
    }

    public RequestMetricCollector getRequestMetricCollector() {
        return this.requestMetricCollector;
    }
}
