package com.vk.sdk.api.httpClient;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.imsdk.tool.net.AsyncHttpClient;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdkVersion;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.util.VKUtil;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import org.apache.http.protocol.HTTP;

public class VKHttpClient {
    private static final ExecutorService mBackgroundExecutor = Executors.newFixedThreadPool(3);
    /* access modifiers changed from: private */
    public static final ExecutorService mResponseService = Executors.newSingleThreadExecutor();
    public static final String sDefaultStringEncoding = "UTF-8";

    public interface VKHttpProgressCallback {
        void onProgress(long j, long j2);
    }

    public static VKHTTPRequest requestWithVkRequest(@NonNull VKRequest vkRequest) {
        VKAccessToken token = VKAccessToken.currentToken();
        Locale locale = Locale.US;
        Object[] objArr = new Object[2];
        objArr[0] = (vkRequest.secure || (token != null && token.httpsRequired)) ? "s" : "";
        objArr[1] = vkRequest.methodName;
        VKHTTPRequest result = new VKHTTPRequest(String.format(locale, "http%s://api.vk.com/method/%s", objArr));
        result.headers = getDefaultHeaders();
        result.setVkParameters(vkRequest.getPreparedParameters());
        return result;
    }

    public static VKHTTPRequest fileUploadRequest(@NonNull String uploadUrl, File... files) {
        VKHTTPRequest request = new VKHTTPRequest(uploadUrl);
        request.entity = new VKMultipartEntity(files);
        return request;
    }

    public static VKHTTPRequest docUploadRequest(@NonNull String uploadUrl, File file) {
        VKHTTPRequest request = new VKHTTPRequest(uploadUrl);
        request.entity = new VKMultipartEntity(new File[]{file}, VKAttachments.TYPE_DOC);
        return request;
    }

    private static Map<String, String> getDefaultHeaders() {
        return new HashMap<String, String>() {
            private static final long serialVersionUID = 200199014417610665L;

            {
                put("Accept-Encoding", AsyncHttpClient.ENCODING_GZIP);
            }
        };
    }

    public static void enqueueOperation(final VKAbstractOperation operation) {
        mBackgroundExecutor.execute(new Runnable() {
            public void run() {
                operation.start(VKHttpClient.mResponseService);
            }
        });
    }

    public static void cancelHttpOperation(final VKHttpOperation operation) {
        mBackgroundExecutor.execute(new Runnable() {
            public void run() {
                operation.getUriRequest().abort();
            }
        });
    }

    public static VKHttpResponse execute(VKHTTPRequest request) throws IOException {
        VKHttpResponse response = new VKHttpResponse(request.createConnection(), (VKHttpProgressCallback) null);
        if (request.isAborted) {
            return null;
        }
        return response;
    }

    public static class VKHTTPRequest {
        public HttpURLConnection connection;
        public VKMultipartEntity entity = null;
        public Map<String, String> headers = null;
        public boolean isAborted = false;
        public URL methodUrl = null;
        public List<Pair<String, String>> parameters = null;
        public int timeout = 20000;

        public VKHTTPRequest(@Nullable String url) {
            if (url != null) {
                try {
                    this.methodUrl = new URL(url);
                } catch (MalformedURLException e) {
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void writeParams(@NonNull OutputStream os) throws IOException {
            if (this.entity != null) {
                this.entity.writeTo(os);
                return;
            }
            String query = getQuery();
            if (query != null && query.length() > 0) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
            }
        }

        /* access modifiers changed from: package-private */
        public void setVkParameters(VKParameters params) {
            ArrayList<Pair<String, String>> pairs = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof VKAttachments) {
                    pairs.add(new Pair(entry.getKey(), ((VKAttachments) value).toAttachmentsString()));
                } else if (value instanceof Collection) {
                    pairs.add(new Pair(entry.getKey(), TextUtils.join(",", (Collection) value)));
                } else {
                    pairs.add(new Pair(entry.getKey(), value == null ? null : String.valueOf(value)));
                }
            }
            this.parameters = pairs;
        }

        public String getQuery() throws UnsupportedEncodingException {
            if (this.parameters == null) {
                return null;
            }
            ArrayList<String> params = new ArrayList<>(this.parameters.size());
            for (Pair<String, String> pair : this.parameters) {
                if (!(pair.first == null || pair.second == null)) {
                    params.add(String.format("%s=%s", new Object[]{URLEncoder.encode((String) pair.first, "UTF-8"), URLEncoder.encode((String) pair.second, "UTF-8")}));
                }
            }
            return TextUtils.join(HttpRequest.HTTP_REQ_ENTITY_JOIN, params);
        }

        public void abort() {
            if (this.connection != null) {
                this.connection.disconnect();
            }
            this.isAborted = true;
        }

        /* access modifiers changed from: package-private */
        public HttpURLConnection createConnection() throws IOException {
            PackageManager packageManager;
            this.connection = (HttpURLConnection) this.methodUrl.openConnection();
            this.connection.setReadTimeout(this.timeout);
            this.connection.setConnectTimeout(this.timeout + 5000);
            this.connection.setRequestMethod("POST");
            this.connection.setUseCaches(false);
            this.connection.setDoInput(true);
            this.connection.setDoOutput(true);
            try {
                Context ctx = VKUIHelper.getApplicationContext();
                if (!(ctx == null || (packageManager = ctx.getPackageManager()) == null)) {
                    PackageInfo info = packageManager.getPackageInfo(ctx.getPackageName(), 0);
                    this.connection.setRequestProperty("User-Agent", String.format(Locale.US, "%s/%s (%s; Android %d; Scale/%.2f; VK SDK %s; %s)", new Object[]{VKUtil.getApplicationName(ctx), info.versionName, Build.MODEL, Integer.valueOf(Build.VERSION.SDK_INT), Float.valueOf(ctx.getResources().getDisplayMetrics().density), VKSdkVersion.SDK_VERSION, info.packageName}));
                }
            } catch (Exception e) {
            }
            this.connection.setRequestProperty("Connection", HTTP.CONN_KEEP_ALIVE);
            if (this.headers != null) {
                for (Map.Entry<String, String> entry : this.headers.entrySet()) {
                    this.connection.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            if (this.entity != null) {
                this.connection.addRequestProperty("Content-length", this.entity.getContentLength() + "");
                Pair<String, String> contentType = this.entity.getContentType();
                this.connection.addRequestProperty((String) contentType.first, (String) contentType.second);
            }
            OutputStream os = this.connection.getOutputStream();
            writeParams(os);
            os.close();
            this.connection.connect();
            return this.connection;
        }
    }

    public static class VKHttpResponse {
        public final long contentLength;
        public final byte[] responseBytes;
        public Map<String, String> responseHeaders = null;
        public final int statusCode;

        public VKHttpResponse(HttpURLConnection connection, VKHttpProgressCallback progress) throws IOException {
            String contentEncoding;
            this.statusCode = connection.getResponseCode();
            this.contentLength = (long) connection.getContentLength();
            if (connection.getHeaderFields() != null) {
                this.responseHeaders = new HashMap();
                for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
                    this.responseHeaders.put(header.getKey(), TextUtils.join(",", header.getValue()));
                }
            }
            InputStream inputStream = connection.getInputStream();
            if (!(this.responseHeaders == null || (contentEncoding = this.responseHeaders.get("Content-Encoding")) == null || !contentEncoding.equalsIgnoreCase(AsyncHttpClient.ENCODING_GZIP))) {
                inputStream = new GZIPInputStream(inputStream);
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            long totalBytesRead = 0;
            progress = this.contentLength <= 0 ? null : progress;
            while (true) {
                int bytesRead = inputStream.read(buffer);
                if (bytesRead != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    totalBytesRead += (long) bytesRead;
                    if (progress != null) {
                        progress.onProgress(totalBytesRead, this.contentLength);
                    }
                } else {
                    inputStream.close();
                    outputStream.flush();
                    this.responseBytes = outputStream.toByteArray();
                    outputStream.close();
                    return;
                }
            }
        }
    }
}
