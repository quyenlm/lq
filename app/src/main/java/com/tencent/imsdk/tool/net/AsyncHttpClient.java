package com.tencent.imsdk.tool.net;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.vk.sdk.api.VKApiConst;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

public class AsyncHttpClient {
    public static final int DEFAULT_MAX_CONNECTIONS = 10;
    public static final int DEFAULT_MAX_RETRIES = 5;
    public static final int DEFAULT_RETRY_SLEEP_TIME_MILLIS = 1500;
    public static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
    public static final int DEFAULT_SOCKET_TIMEOUT = 10000;
    public static final String ENCODING_GZIP = "gzip";
    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String HEADER_AGENT = "User-Agent";
    public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
    public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";
    public static final String HEADER_CONTENT_RANGE = "Content-Range";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_HOST = "Host";
    public static final String LOG_TAG = "AsyncHttpClient";
    /* access modifiers changed from: private */
    public final Map<String, String> clientHeaderMap;
    private final DefaultHttpClient httpClient;
    private final HttpContext httpContext;
    private boolean isUrlEncodingEnabled;
    private int maxConnections;
    /* access modifiers changed from: private */
    public final Map<Context, List<RequestHandle>> requestMap;
    private ExecutorService threadPool;
    private int timeout;

    public AsyncHttpClient() {
        this(false, 80, 443);
    }

    public AsyncHttpClient(int httpPort) {
        this(false, httpPort, 443);
    }

    public AsyncHttpClient(int httpPort, int httpsPort) {
        this(false, httpPort, httpsPort);
    }

    public AsyncHttpClient(boolean fixNoHttpResponseException, int httpPort, int httpsPort) {
        this(getDefaultSchemeRegistry(fixNoHttpResponseException, httpPort, httpsPort));
    }

    private static SchemeRegistry getDefaultSchemeRegistry(boolean fixNoHttpResponseException, int httpPort, int httpsPort) {
        SSLSocketFactory sslSocketFactory;
        if (httpPort < 1) {
            httpPort = 80;
            Log.d(LOG_TAG, "Invalid HTTP port number specified, defaulting to 80");
        }
        if (httpsPort < 1) {
            httpsPort = 443;
            Log.d(LOG_TAG, "Invalid HTTPS port number specified, defaulting to 443");
        }
        if (fixNoHttpResponseException) {
            sslSocketFactory = MySSLSocketFactory.getFixedSocketFactory(IMConfig.SERVER_CERTIFICATE_FILE_DEFAULT);
        } else {
            sslSocketFactory = SSLSocketFactory.getSocketFactory();
        }
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), httpPort));
        schemeRegistry.register(new Scheme(VKApiConst.HTTPS, sslSocketFactory, httpsPort));
        return schemeRegistry;
    }

    public AsyncHttpClient(SchemeRegistry schemeRegistry) {
        this.maxConnections = 10;
        this.timeout = 10000;
        this.isUrlEncodingEnabled = true;
        BasicHttpParams httpParams = new BasicHttpParams();
        ConnManagerParams.setTimeout(httpParams, (long) this.timeout);
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, new ConnPerRouteBean(this.maxConnections));
        ConnManagerParams.setMaxTotalConnections(httpParams, 10);
        HttpConnectionParams.setSoTimeout(httpParams, this.timeout);
        HttpConnectionParams.setConnectionTimeout(httpParams, this.timeout);
        HttpConnectionParams.setTcpNoDelay(httpParams, true);
        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
        HttpProtocolParams.setUseExpectContinue(httpParams, false);
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(httpParams, schemeRegistry);
        this.threadPool = getDefaultThreadPool();
        this.requestMap = Collections.synchronizedMap(new WeakHashMap());
        this.clientHeaderMap = new HashMap();
        this.httpContext = new SyncBasicHttpContext(new BasicHttpContext());
        this.httpClient = new DefaultHttpClient(cm, httpParams);
        setGzip();
        this.httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
                Credentials creds;
                AuthState authState = (AuthState) context.getAttribute(ClientContext.TARGET_AUTH_STATE);
                CredentialsProvider credsProvider = (CredentialsProvider) context.getAttribute(ClientContext.CREDS_PROVIDER);
                HttpHost targetHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
                if (authState.getAuthScheme() == null && (creds = credsProvider.getCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()))) != null) {
                    authState.setAuthScheme(new BasicScheme());
                    authState.setCredentials(creds);
                }
            }
        }, 0);
        this.httpClient.setHttpRequestRetryHandler(new RetryHandler(5, 1500));
    }

    private void setGzip() {
        this.httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(HttpRequest request, HttpContext context) {
                if (!request.containsHeader("Accept-Encoding")) {
                    request.addHeader("Accept-Encoding", AsyncHttpClient.ENCODING_GZIP);
                }
                for (String header : AsyncHttpClient.this.clientHeaderMap.keySet()) {
                    if (request.containsHeader(header)) {
                        Header overwritten = request.getFirstHeader(header);
                        Log.d(AsyncHttpClient.LOG_TAG, String.format("Headers were overwritten! (%s | %s) overwrites (%s | %s)", new Object[]{header, AsyncHttpClient.this.clientHeaderMap.get(header), overwritten.getName(), overwritten.getValue()}));
                    }
                    request.addHeader(header, (String) AsyncHttpClient.this.clientHeaderMap.get(header));
                }
            }
        });
        this.httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
            public void process(HttpResponse response, HttpContext context) {
                Header encoding;
                HttpEntity entity = response.getEntity();
                if (entity != null && (encoding = entity.getContentEncoding()) != null) {
                    for (HeaderElement element : encoding.getElements()) {
                        if (element.getName().equalsIgnoreCase(AsyncHttpClient.ENCODING_GZIP)) {
                            response.setEntity(new InflatingEntity(entity));
                            return;
                        }
                    }
                }
            }
        });
    }

    public static void allowRetryExceptionClass(Class<?> cls) {
        if (cls != null) {
            RetryHandler.addClassToWhitelist(cls);
        }
    }

    public static void blockRetryExceptionClass(Class<?> cls) {
        if (cls != null) {
            RetryHandler.addClassToBlacklist(cls);
        }
    }

    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    public HttpContext getHttpContext() {
        return this.httpContext;
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
    }

    public void setThreadPool(ExecutorService threadPool2) {
        this.threadPool = threadPool2;
    }

    public ExecutorService getThreadPool() {
        return this.threadPool;
    }

    /* access modifiers changed from: protected */
    public ExecutorService getDefaultThreadPool() {
        return Executors.newCachedThreadPool();
    }

    public void setRedirectHandler(RedirectHandler customRedirectHandler) {
        this.httpClient.setRedirectHandler(customRedirectHandler);
    }

    public void setUserAgent(String userAgent) {
        HttpProtocolParams.setUserAgent(this.httpClient.getParams(), userAgent);
    }

    public int getMaxConnections() {
        return this.maxConnections;
    }

    public void setMaxConnections(int maxConnections2) {
        if (maxConnections2 < 1) {
            maxConnections2 = 10;
        }
        this.maxConnections = maxConnections2;
        ConnManagerParams.setMaxConnectionsPerRoute(this.httpClient.getParams(), new ConnPerRouteBean(this.maxConnections));
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout2) {
        if (timeout2 < 1000) {
            timeout2 = 10000;
        }
        this.timeout = timeout2;
        HttpParams httpParams = this.httpClient.getParams();
        ConnManagerParams.setTimeout(httpParams, (long) this.timeout);
        HttpConnectionParams.setSoTimeout(httpParams, this.timeout);
        HttpConnectionParams.setConnectionTimeout(httpParams, this.timeout);
    }

    public void setProxy(String hostname, int port) {
        this.httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(hostname, port));
    }

    public void setProxy(String hostname, int port, String username, String password) {
        this.httpClient.getCredentialsProvider().setCredentials(new AuthScope(hostname, port), new UsernamePasswordCredentials(username, password));
        this.httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(hostname, port));
    }

    public void setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme(VKApiConst.HTTPS, sslSocketFactory, 443));
    }

    public void setMaxRetriesAndTimeout(int retries, int timeout2) {
        this.httpClient.setHttpRequestRetryHandler(new RetryHandler(retries, timeout2));
    }

    public void removeAllHeaders() {
        this.clientHeaderMap.clear();
    }

    public void addHeader(String header, String value) {
        this.clientHeaderMap.put(header, value);
    }

    public void removeHeader(String header) {
        this.clientHeaderMap.remove(header);
    }

    public void clearBasicAuth() {
        this.httpClient.getCredentialsProvider().clear();
    }

    public void cancelRequests(final Context context, final boolean mayInterruptIfRunning) {
        if (context == null) {
            Log.e(LOG_TAG, "Passed null Context to cancelRequests");
            return;
        }
        Runnable r = new Runnable() {
            public void run() {
                List<RequestHandle> requestList = (List) AsyncHttpClient.this.requestMap.get(context);
                if (requestList != null) {
                    for (RequestHandle requestHandle : requestList) {
                        requestHandle.cancel(mayInterruptIfRunning);
                    }
                    AsyncHttpClient.this.requestMap.remove(context);
                }
            }
        };
        if (Looper.myLooper() == Looper.getMainLooper()) {
            new Thread(r).start();
        } else {
            r.run();
        }
    }

    public void cancelAllRequests(boolean mayInterruptIfRunning) {
        for (List<RequestHandle> requestList : this.requestMap.values()) {
            if (requestList != null) {
                for (RequestHandle requestHandle : requestList) {
                    requestHandle.cancel(mayInterruptIfRunning);
                }
            }
        }
        this.requestMap.clear();
    }

    public RequestHandle head(String url, ResponseHandlerInterface responseHandler) {
        return head((Context) null, url, (RequestParams) null, responseHandler);
    }

    public RequestHandle head(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return head((Context) null, url, params, responseHandler);
    }

    public RequestHandle head(Context context, String url, ResponseHandlerInterface responseHandler) {
        return head(context, url, (RequestParams) null, responseHandler);
    }

    public RequestHandle head(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return sendRequest(this.httpClient, this.httpContext, new HttpHead(getUrlWithQueryString(this.isUrlEncodingEnabled, url, params)), (String) null, responseHandler, context);
    }

    public RequestHandle head(Context context, String url, Header[] headers, RequestParams params, ResponseHandlerInterface responseHandler) {
        HttpUriRequest request = new HttpHead(getUrlWithQueryString(this.isUrlEncodingEnabled, url, params));
        if (headers != null) {
            request.setHeaders(headers);
        }
        return sendRequest(this.httpClient, this.httpContext, request, (String) null, responseHandler, context);
    }

    public RequestHandle get(String url, ResponseHandlerInterface responseHandler) {
        return get((Context) null, url, (RequestParams) null, responseHandler);
    }

    public RequestHandle get(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return get((Context) null, url, params, responseHandler);
    }

    public RequestHandle get(Context context, String url, ResponseHandlerInterface responseHandler) {
        return get(context, url, (RequestParams) null, responseHandler);
    }

    public RequestHandle get(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return sendRequest(this.httpClient, this.httpContext, new HttpGet(getUrlWithQueryString(this.isUrlEncodingEnabled, url, params)), (String) null, responseHandler, context);
    }

    public RequestHandle get(Context context, String url, Header[] headers, RequestParams params, ResponseHandlerInterface responseHandler) {
        HttpUriRequest request = new HttpGet(getUrlWithQueryString(this.isUrlEncodingEnabled, url, params));
        if (headers != null) {
            request.setHeaders(headers);
        }
        return sendRequest(this.httpClient, this.httpContext, request, (String) null, responseHandler, context);
    }

    public RequestHandle post(String url, ResponseHandlerInterface responseHandler) {
        return post((Context) null, url, (RequestParams) null, responseHandler);
    }

    public RequestHandle post(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return post((Context) null, url, params, responseHandler);
    }

    public RequestHandle post(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return post(context, url, paramsToEntity(params, responseHandler), (String) null, responseHandler);
    }

    public RequestHandle post(Context context, String url, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler) {
        return sendRequest(this.httpClient, this.httpContext, addEntityToRequestBase(new HttpPost(URI.create(url).normalize()), entity), contentType, responseHandler, context);
    }

    public RequestHandle post(Context context, String url, Header[] headers, RequestParams params, String contentType, ResponseHandlerInterface responseHandler) {
        HttpEntityEnclosingRequestBase request = new HttpPost(URI.create(url).normalize());
        if (params != null) {
            request.setEntity(paramsToEntity(params, responseHandler));
        }
        if (headers != null) {
            request.setHeaders(headers);
        }
        return sendRequest(this.httpClient, this.httpContext, request, contentType, responseHandler, context);
    }

    public RequestHandle post(Context context, String url, Header[] headers, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler) {
        HttpEntityEnclosingRequestBase request = addEntityToRequestBase(new HttpPost(URI.create(url).normalize()), entity);
        if (headers != null) {
            request.setHeaders(headers);
        }
        return sendRequest(this.httpClient, this.httpContext, request, contentType, responseHandler, context);
    }

    public RequestHandle put(String url, ResponseHandlerInterface responseHandler) {
        return put((Context) null, url, (RequestParams) null, responseHandler);
    }

    public RequestHandle put(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return put((Context) null, url, params, responseHandler);
    }

    public RequestHandle put(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return put(context, url, paramsToEntity(params, responseHandler), (String) null, responseHandler);
    }

    public RequestHandle put(Context context, String url, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler) {
        return sendRequest(this.httpClient, this.httpContext, addEntityToRequestBase(new HttpPut(URI.create(url).normalize()), entity), contentType, responseHandler, context);
    }

    public RequestHandle put(Context context, String url, Header[] headers, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler) {
        HttpEntityEnclosingRequestBase request = addEntityToRequestBase(new HttpPut(URI.create(url).normalize()), entity);
        if (headers != null) {
            request.setHeaders(headers);
        }
        return sendRequest(this.httpClient, this.httpContext, request, contentType, responseHandler, context);
    }

    public RequestHandle delete(String url, ResponseHandlerInterface responseHandler) {
        return delete((Context) null, url, responseHandler);
    }

    public RequestHandle delete(Context context, String url, ResponseHandlerInterface responseHandler) {
        return sendRequest(this.httpClient, this.httpContext, new HttpDelete(URI.create(url).normalize()), (String) null, responseHandler, context);
    }

    public RequestHandle delete(Context context, String url, Header[] headers, ResponseHandlerInterface responseHandler) {
        HttpDelete delete = new HttpDelete(URI.create(url).normalize());
        if (headers != null) {
            delete.setHeaders(headers);
        }
        return sendRequest(this.httpClient, this.httpContext, delete, (String) null, responseHandler, context);
    }

    public RequestHandle delete(Context context, String url, Header[] headers, RequestParams params, ResponseHandlerInterface responseHandler) {
        HttpDelete httpDelete = new HttpDelete(getUrlWithQueryString(this.isUrlEncodingEnabled, url, params));
        if (headers != null) {
            httpDelete.setHeaders(headers);
        }
        return sendRequest(this.httpClient, this.httpContext, httpDelete, (String) null, responseHandler, context);
    }

    /* access modifiers changed from: protected */
    public AsyncHttpRequest newAsyncHttpRequest(DefaultHttpClient client, HttpContext httpContext2, HttpUriRequest uriRequest, String contentType, ResponseHandlerInterface responseHandler, Context context) {
        return new AsyncHttpRequest(client, httpContext2, uriRequest, responseHandler);
    }

    /* access modifiers changed from: protected */
    public HttpUriRequest addIMSDKHeaders(HttpUriRequest uriRequest) {
        String host = IMConfig.getDebugServerHost();
        if (host != null && host.length() > 0) {
            IMLogger.d("you are using debug server host config : " + host);
            uriRequest.addHeader("Host", host);
        }
        if (uriRequest.getHeaders("User-Agent").length <= 0) {
            uriRequest.addHeader("User-Agent", Build.FINGERPRINT);
        }
        IMLogger.d("request url : " + uriRequest.getURI());
        return uriRequest;
    }

    /* access modifiers changed from: protected */
    public RequestHandle sendRequest(DefaultHttpClient client, HttpContext httpContext2, HttpUriRequest uriRequest, String contentType, ResponseHandlerInterface responseHandler, Context context) {
        if (uriRequest == null) {
            throw new IllegalArgumentException("HttpUriRequest must not be null");
        }
        HttpUriRequest uriRequest2 = addIMSDKHeaders(uriRequest);
        if (responseHandler == null) {
            throw new IllegalArgumentException("ResponseHandler must not be null");
        } else if (responseHandler.getUseSynchronousMode()) {
            throw new IllegalArgumentException("Synchronous ResponseHandler used in AsyncHttpClient. You should create your response handler in a looper thread or use SyncHttpClient instead.");
        } else {
            if (contentType != null) {
                uriRequest2.setHeader("Content-Type", contentType);
            }
            responseHandler.setRequestHeaders(uriRequest2.getAllHeaders());
            responseHandler.setRequestURI(uriRequest2.getURI());
            AsyncHttpRequest request = newAsyncHttpRequest(client, httpContext2, uriRequest2, contentType, responseHandler, context);
            this.threadPool.submit(request);
            RequestHandle requestHandle = new RequestHandle(request);
            if (context != null) {
                List<RequestHandle> requestList = this.requestMap.get(context);
                synchronized (this.requestMap) {
                    if (requestList == null) {
                        requestList = Collections.synchronizedList(new LinkedList());
                        this.requestMap.put(context, requestList);
                    }
                }
                requestList.add(requestHandle);
                Iterator<RequestHandle> iterator = requestList.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().shouldBeGarbageCollected()) {
                        iterator.remove();
                    }
                }
            }
            return requestHandle;
        }
    }

    public void setURLEncodingEnabled(boolean enabled) {
        this.isUrlEncodingEnabled = enabled;
    }

    public static String getUrlWithQueryString(boolean shouldEncodeUrl, String url, RequestParams params) {
        if (url == null) {
            String str = url;
            return null;
        }
        if (shouldEncodeUrl) {
            url = url.replace(" ", "%20");
        }
        if (params != null) {
            String paramString = params.getParamString().trim();
            if (!paramString.equals("") && !paramString.equals("?")) {
                url = (url + (url.contains("?") ? com.tencent.imsdk.framework.request.HttpRequest.HTTP_REQ_ENTITY_JOIN : "?")) + paramString;
            }
        }
        String str2 = url;
        return url;
    }

    public static boolean isInputStreamGZIPCompressed(PushbackInputStream inputStream) throws IOException {
        boolean z = true;
        if (inputStream == null) {
            return false;
        }
        byte[] signature = new byte[2];
        int count = 0;
        while (count < 2) {
            try {
                int readCount = inputStream.read(signature, count, 2 - count);
                if (readCount < 0) {
                    return false;
                }
                count += readCount;
            } finally {
                inputStream.unread(signature, 0, count);
            }
        }
        inputStream.unread(signature, 0, count);
        if (35615 != ((signature[0] & 255) | ((signature[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK))) {
            z = false;
        }
        return z;
    }

    public static void silentCloseInputStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                Log.w(LOG_TAG, "Cannot close input stream", e);
            }
        }
    }

    public static void silentCloseOutputStream(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                Log.w(LOG_TAG, "Cannot close output stream", e);
            }
        }
    }

    private HttpEntity paramsToEntity(RequestParams params, ResponseHandlerInterface responseHandler) {
        if (params == null) {
            return null;
        }
        try {
            return params.getEntity(responseHandler);
        } catch (IOException e) {
            if (responseHandler != null) {
                responseHandler.sendFailureMessage(0, (Header[]) null, (byte[]) null, e);
                return null;
            }
            e.printStackTrace();
            return null;
        }
    }

    public boolean isUrlEncodingEnabled() {
        return this.isUrlEncodingEnabled;
    }

    private HttpEntityEnclosingRequestBase addEntityToRequestBase(HttpEntityEnclosingRequestBase requestBase, HttpEntity entity) {
        if (entity != null) {
            requestBase.setEntity(entity);
        }
        return requestBase;
    }

    public static void endEntityViaReflection(HttpEntity entity) {
        if (entity instanceof HttpEntityWrapper) {
            Field f = null;
            try {
                Field[] fields = HttpEntityWrapper.class.getDeclaredFields();
                int length = fields.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    Field ff = fields[i];
                    if (ff.getName().equals("wrappedEntity")) {
                        f = ff;
                        break;
                    }
                    i++;
                }
                if (f != null) {
                    f.setAccessible(true);
                    HttpEntity wrapped = (HttpEntity) f.get(entity);
                    if (wrapped != null) {
                        wrapped.consumeContent();
                    }
                }
            } catch (Throwable t) {
                Log.e(LOG_TAG, "wrappedEntity consume", t);
            }
        }
    }

    private static class InflatingEntity extends HttpEntityWrapper {
        GZIPInputStream gzippedStream;
        PushbackInputStream pushbackStream;
        InputStream wrappedStream;

        public InflatingEntity(HttpEntity wrapped) {
            super(wrapped);
        }

        public InputStream getContent() throws IOException {
            this.wrappedStream = this.wrappedEntity.getContent();
            this.pushbackStream = new PushbackInputStream(this.wrappedStream, 2);
            if (!AsyncHttpClient.isInputStreamGZIPCompressed(this.pushbackStream)) {
                return this.pushbackStream;
            }
            this.gzippedStream = new GZIPInputStream(this.pushbackStream);
            return this.gzippedStream;
        }

        public long getContentLength() {
            return -1;
        }

        public void consumeContent() throws IOException {
            AsyncHttpClient.silentCloseInputStream(this.wrappedStream);
            AsyncHttpClient.silentCloseInputStream(this.pushbackStream);
            AsyncHttpClient.silentCloseInputStream(this.gzippedStream);
            super.consumeContent();
        }
    }
}
