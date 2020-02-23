package com.tencent.imsdk.tool.net;

import android.util.Log;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.imsdk.tool.etc.T;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class RequestParams {
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
    protected static final String LOG_TAG = "RequestParams";
    protected boolean autoCloseInputStreams;
    protected String contentEncoding;
    protected final ConcurrentHashMap<String, FileWrapper> fileParams;
    protected boolean isRepeatable;
    protected final ConcurrentHashMap<String, StreamWrapper> streamParams;
    protected final ConcurrentHashMap<String, String> urlParams;
    protected final ConcurrentHashMap<String, Object> urlParamsWithObjects;

    public void setContentEncoding(String encoding) {
        if (encoding != null) {
            this.contentEncoding = encoding;
        } else {
            Log.d(LOG_TAG, "setContentEncoding called with null attribute");
        }
    }

    public RequestParams() {
        this((Map<String, String>) null);
    }

    public RequestParams(Map<String, String> source) {
        this.urlParams = new ConcurrentHashMap<>();
        this.streamParams = new ConcurrentHashMap<>();
        this.fileParams = new ConcurrentHashMap<>();
        this.urlParamsWithObjects = new ConcurrentHashMap<>();
        this.contentEncoding = "UTF-8";
        if (source != null) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    public RequestParams(final String key, final String value) {
        this((Map<String, String>) new HashMap<String, String>() {
            {
                put(key, value);
            }
        });
    }

    public RequestParams(Object... keysAndValues) {
        this.urlParams = new ConcurrentHashMap<>();
        this.streamParams = new ConcurrentHashMap<>();
        this.fileParams = new ConcurrentHashMap<>();
        this.urlParamsWithObjects = new ConcurrentHashMap<>();
        this.contentEncoding = "UTF-8";
        int len = keysAndValues.length;
        if (len % 2 != 0) {
            throw new IllegalArgumentException("Supplied arguments must be even");
        }
        for (int i = 0; i < len; i += 2) {
            put(String.valueOf(keysAndValues[i]), String.valueOf(keysAndValues[i + 1]));
        }
    }

    public void put(String key, String value) {
        if (key != null && value != null) {
            this.urlParams.put(key, value);
        }
    }

    public void put(String key, File file) throws FileNotFoundException {
        put(key, file, (String) null);
    }

    public void put(String key, File file, String contentType) throws FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException();
        } else if (key != null) {
            this.fileParams.put(key, new FileWrapper(file, contentType));
        }
    }

    public void put(String key, InputStream stream) {
        put(key, stream, (String) null);
    }

    public void put(String key, InputStream stream, String name) {
        put(key, stream, name, (String) null);
    }

    public void put(String key, InputStream stream, String name, String contentType) {
        put(key, stream, name, contentType, this.autoCloseInputStreams);
    }

    public void put(String key, InputStream stream, String name, String contentType, boolean autoClose) {
        if (key != null && stream != null) {
            this.streamParams.put(key, StreamWrapper.newInstance(stream, name, contentType, autoClose));
        }
    }

    public void put(String key, Object value) {
        if (key != null && value != null) {
            this.urlParamsWithObjects.put(key, value);
        }
    }

    public void put(String key, int value) {
        if (key != null) {
            this.urlParams.put(key, String.valueOf(value));
        }
    }

    public void put(String key, long value) {
        if (key != null) {
            this.urlParams.put(key, String.valueOf(value));
        }
    }

    public void add(String key, String value) {
        if (key != null && value != null) {
            Object params = this.urlParamsWithObjects.get(key);
            if (params == null) {
                params = new HashSet();
                put(key, params);
            }
            if (params instanceof List) {
                ((List) params).add(value);
            } else if (params instanceof Set) {
                ((Set) params).add(value);
            }
        }
    }

    public void remove(String key) {
        this.urlParams.remove(key);
        this.streamParams.remove(key);
        this.fileParams.remove(key);
        this.urlParamsWithObjects.remove(key);
    }

    public boolean has(String key) {
        return (this.urlParams.get(key) == null && this.streamParams.get(key) == null && this.fileParams.get(key) == null && this.urlParamsWithObjects.get(key) == null) ? false : true;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : this.urlParams.entrySet()) {
            if (result.length() > 0) {
                result.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            }
            result.append(entry.getKey());
            result.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
            result.append(entry.getValue());
        }
        for (Map.Entry<String, StreamWrapper> entry2 : this.streamParams.entrySet()) {
            if (result.length() > 0) {
                result.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            }
            result.append(entry2.getKey());
            result.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
            result.append("STREAM");
        }
        for (Map.Entry<String, FileWrapper> entry3 : this.fileParams.entrySet()) {
            if (result.length() > 0) {
                result.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            }
            result.append(entry3.getKey());
            result.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
            result.append("FILE");
        }
        for (BasicNameValuePair kv : getParamsList((String) null, this.urlParamsWithObjects)) {
            if (result.length() > 0) {
                result.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            }
            result.append(kv.getName());
            result.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
            result.append(kv.getValue());
        }
        if (!T.ckIsEmpty(result.toString())) {
            return result.toString();
        }
        return "RequestParams{isRepeatable=" + this.isRepeatable + ", autoCloseInputStreams=" + this.autoCloseInputStreams + ", urlParams=" + this.urlParams + ", streamParams=" + this.streamParams + ", fileParams=" + this.fileParams + ", urlParamsWithObjects=" + this.urlParamsWithObjects + ", contentEncoding='" + this.contentEncoding + '\'' + '}';
    }

    public void setHttpEntityIsRepeatable(boolean isRepeatable2) {
        this.isRepeatable = isRepeatable2;
    }

    public void setAutoCloseInputStreams(boolean flag) {
        this.autoCloseInputStreams = flag;
    }

    public HttpEntity getEntity(ResponseHandlerInterface progressHandler) throws IOException {
        return createFormEntity();
    }

    private HttpEntity createFormEntity() {
        try {
            return new UrlEncodedFormEntity(getParamsList(), this.contentEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "createFormEntity failed", e);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public List<BasicNameValuePair> getParamsList() {
        List<BasicNameValuePair> lparams = new LinkedList<>();
        for (Map.Entry<String, String> entry : this.urlParams.entrySet()) {
            lparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        lparams.addAll(getParamsList((String) null, this.urlParamsWithObjects));
        return lparams;
    }

    private List<BasicNameValuePair> getParamsList(String key, Object value) {
        Object nestedValue;
        String format;
        List<BasicNameValuePair> params = new LinkedList<>();
        if (value instanceof Map) {
            Map map = (Map) value;
            List list = new ArrayList(map.keySet());
            if (list.size() > 0 && (list.get(0) instanceof Comparable)) {
                Collections.sort(list);
            }
            for (Object nestedKey : list) {
                if ((nestedKey instanceof String) && (nestedValue = map.get(nestedKey)) != null) {
                    if (key == null) {
                        format = (String) nestedKey;
                    } else {
                        format = String.format("%s[%s]", new Object[]{key, nestedKey});
                    }
                    params.addAll(getParamsList(format, nestedValue));
                }
            }
        } else if (value instanceof List) {
            List list2 = (List) value;
            int listSize = list2.size();
            for (int nestedValueIndex = 0; nestedValueIndex < listSize; nestedValueIndex++) {
                params.addAll(getParamsList(String.format(Locale.US, "%s[%d]", new Object[]{key, Integer.valueOf(nestedValueIndex)}), list2.get(nestedValueIndex)));
            }
        } else if (value instanceof Object[]) {
            Object[] array = (Object[]) value;
            int arrayLength = array.length;
            for (int nestedValueIndex2 = 0; nestedValueIndex2 < arrayLength; nestedValueIndex2++) {
                params.addAll(getParamsList(String.format(Locale.US, "%s[%d]", new Object[]{key, Integer.valueOf(nestedValueIndex2)}), array[nestedValueIndex2]));
            }
        } else if (value instanceof Set) {
            for (Object nestedValue2 : (Set) value) {
                params.addAll(getParamsList(key, nestedValue2));
            }
        } else {
            params.add(new BasicNameValuePair(key, value.toString()));
        }
        return params;
    }

    /* access modifiers changed from: protected */
    public String getParamString() {
        return URLEncodedUtils.format(getParamsList(), this.contentEncoding);
    }

    public static class FileWrapper {
        public final String contentType;
        public final File file;

        public FileWrapper(File file2, String contentType2) {
            this.file = file2;
            this.contentType = contentType2;
        }
    }

    public static class StreamWrapper {
        public final boolean autoClose;
        public final String contentType;
        public final InputStream inputStream;
        public final String name;

        public StreamWrapper(InputStream inputStream2, String name2, String contentType2, boolean autoClose2) {
            this.inputStream = inputStream2;
            this.name = name2;
            this.contentType = contentType2;
            this.autoClose = autoClose2;
        }

        static StreamWrapper newInstance(InputStream inputStream2, String name2, String contentType2, boolean autoClose2) {
            if (contentType2 == null) {
                contentType2 = "application/octet-stream";
            }
            return new StreamWrapper(inputStream2, name2, contentType2, autoClose2);
        }
    }
}
