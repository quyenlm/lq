package com.beetalk.sdk.networking;

import bolts.Continuation;
import bolts.Task;
import com.beetalk.sdk.helper.BBLogger;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.json.JSONObject;

public class HttpRequestTask {
    private static final int TIMEOUT_IN_MILIS = 10000;
    /* access modifiers changed from: private */
    public final BaseCallback callback;
    /* access modifiers changed from: private */
    public final DataType dataType;
    /* access modifiers changed from: private */
    public final List<HttpParam> headers;
    /* access modifiers changed from: private */
    public JSONObject jsonData;
    /* access modifiers changed from: private */
    public Map<String, String> mapData;
    /* access modifiers changed from: private */
    public final RequestType type;

    interface BaseCallback {
        void onTimeout();
    }

    private enum DataType {
        MAP,
        JSON
    }

    public interface JsonCallback extends BaseCallback {
        void onResultObtained(JSONObject jSONObject);
    }

    public enum RequestType {
        GET,
        POST
    }

    public interface StringCallback extends BaseCallback {
        void onResultObtained(String str);
    }

    public HttpRequestTask(JSONObject jsonData2) {
        this((List<HttpParam>) null, jsonData2, (BaseCallback) null);
    }

    public HttpRequestTask(JSONObject jsonData2, BaseCallback callback2) {
        this((List<HttpParam>) null, jsonData2, callback2);
    }

    public HttpRequestTask(List<HttpParam> headers2, JSONObject jsonData2, BaseCallback callback2) {
        this.type = RequestType.POST;
        this.headers = headers2;
        this.jsonData = jsonData2;
        this.dataType = DataType.JSON;
        this.callback = callback2;
    }

    public HttpRequestTask(RequestType type2, Map<String, String> data) {
        this(type2, (List<HttpParam>) null, data);
    }

    public HttpRequestTask(RequestType type2, List<HttpParam> headers2, Map<String, String> data) {
        this(type2, headers2, data, (BaseCallback) null);
    }

    public HttpRequestTask(RequestType type2, Map<String, String> data, BaseCallback callback2) {
        this(type2, (List<HttpParam>) null, data, callback2);
    }

    public HttpRequestTask(RequestType type2, List<HttpParam> headers2, Map<String, String> data, BaseCallback callback2) {
        this.type = type2;
        this.headers = headers2;
        this.mapData = data;
        this.dataType = DataType.MAP;
        this.callback = callback2;
    }

    public void executeParallel(final String url) {
        Task.callInBackground(new Callable<JsonResult>() {
            public JsonResult call() throws Exception {
                return HttpRequestTask.this.onRequest(url);
            }
        }).continueWith(new Continuation<JsonResult, Void>() {
            public Void then(Task<JsonResult> task) throws Exception {
                if (!task.isFaulted() && task.getResult() != null) {
                    JsonResult result = task.getResult();
                    if (result.hasTimeOut) {
                        HttpRequestTask.this.callback.onTimeout();
                    } else if (HttpRequestTask.this.callback instanceof StringCallback) {
                        ((StringCallback) HttpRequestTask.this.callback).onResultObtained(result.response != null ? result.response.toString() : "");
                    } else if (HttpRequestTask.this.callback instanceof JsonCallback) {
                        ((JsonCallback) HttpRequestTask.this.callback).onResultObtained(result.response);
                    }
                } else if (HttpRequestTask.this.callback instanceof StringCallback) {
                    ((StringCallback) HttpRequestTask.this.callback).onResultObtained("");
                } else if (HttpRequestTask.this.callback instanceof JsonCallback) {
                    ((JsonCallback) HttpRequestTask.this.callback).onResultObtained((JSONObject) null);
                }
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR).continueWith(new Continuation<Void, Object>() {
            public Object then(Task<Void> task) throws Exception {
                if (task.isFaulted()) {
                    BBLogger.e(task.getError());
                    if (HttpRequestTask.this.callback instanceof StringCallback) {
                        ((StringCallback) HttpRequestTask.this.callback).onResultObtained("");
                    } else if (HttpRequestTask.this.callback instanceof JsonCallback) {
                        ((JsonCallback) HttpRequestTask.this.callback).onResultObtained((JSONObject) null);
                    }
                }
                return null;
            }
        });
    }

    public StringResult executeStringSync(String url) {
        JsonResult result = onRequest(url);
        return new StringResult(result.response != null ? result.response.toString() : "", result.hasTimeOut);
    }

    public JsonResult executeJsonResultSync(String url) {
        return onRequest(url);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: org.json.JSONObject} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.beetalk.sdk.networking.HttpRequestTask.JsonResult onRequest(final java.lang.String r10) {
        /*
            r9 = this;
            java.util.concurrent.ExecutorService r6 = bolts.Task.BACKGROUND_EXECUTOR
            com.beetalk.sdk.networking.HttpRequestTask$4 r7 = new com.beetalk.sdk.networking.HttpRequestTask$4
            r7.<init>(r10)
            java.util.concurrent.Future r3 = r6.submit(r7)
            r5 = 0
            r4 = 0
            r6 = 10000(0x2710, double:4.9407E-320)
            java.util.concurrent.TimeUnit r8 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ InterruptedException -> 0x001f, ExecutionException -> 0x0024, TimeoutException -> 0x0029 }
            java.lang.Object r6 = r3.get(r6, r8)     // Catch:{ InterruptedException -> 0x001f, ExecutionException -> 0x0024, TimeoutException -> 0x0029 }
            r0 = r6
            org.json.JSONObject r0 = (org.json.JSONObject) r0     // Catch:{ InterruptedException -> 0x001f, ExecutionException -> 0x0024, TimeoutException -> 0x0029 }
            r4 = r0
        L_0x0019:
            com.beetalk.sdk.networking.HttpRequestTask$JsonResult r6 = new com.beetalk.sdk.networking.HttpRequestTask$JsonResult
            r6.<init>(r4, r5)
            return r6
        L_0x001f:
            r2 = move-exception
            com.beetalk.sdk.helper.BBLogger.e(r2)
            goto L_0x0019
        L_0x0024:
            r2 = move-exception
            com.beetalk.sdk.helper.BBLogger.e(r2)
            goto L_0x0019
        L_0x0029:
            r2 = move-exception
            com.beetalk.sdk.helper.BBLogger.e(r2)
            r5 = 1
            goto L_0x0019
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beetalk.sdk.networking.HttpRequestTask.onRequest(java.lang.String):com.beetalk.sdk.networking.HttpRequestTask$JsonResult");
    }

    /* renamed from: com.beetalk.sdk.networking.HttpRequestTask$5  reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$beetalk$sdk$networking$HttpRequestTask$RequestType = new int[RequestType.values().length];

        static {
            try {
                $SwitchMap$com$beetalk$sdk$networking$HttpRequestTask$RequestType[RequestType.GET.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$beetalk$sdk$networking$HttpRequestTask$RequestType[RequestType.POST.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public static class StringResult {
        public final boolean hasTimedOut;
        public final String response;

        private StringResult(String response2, boolean hasTimedOut2) {
            this.response = response2;
            this.hasTimedOut = hasTimedOut2;
        }
    }

    public static class JsonResult {
        public final boolean hasTimeOut;
        public final JSONObject response;

        public JsonResult(JSONObject response2, boolean hasTimeOut2) {
            this.response = response2;
            this.hasTimeOut = hasTimeOut2;
        }
    }
}
