package com.vk.sdk.api;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKObject;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKServiceActivity;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.httpClient.VKAbstractOperation;
import com.vk.sdk.api.httpClient.VKHttpClient;
import com.vk.sdk.api.httpClient.VKHttpOperation;
import com.vk.sdk.api.httpClient.VKJsonOperation;
import com.vk.sdk.api.httpClient.VKModelOperation;
import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.util.VKStringJoiner;
import com.vk.sdk.util.VKUtil;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class VKRequest extends VKObject {
    public int attempts;
    public final Context context;
    /* access modifiers changed from: private */
    public int mAttemptsUsed;
    /* access modifiers changed from: private */
    public VKAbstractOperation mLoadingOperation;
    private Looper mLooper;
    private final VKParameters mMethodParameters;
    private Class<? extends VKApiModel> mModelClass;
    private VKParser mModelParser;
    /* access modifiers changed from: private */
    public ArrayList<VKRequest> mPostRequestsQueue;
    private String mPreferredLang;
    private VKParameters mPreparedParameters;
    private boolean mUseLooperForCallListener;
    public final String methodName;
    public boolean parseModel;
    @Nullable
    public VKRequestListener requestListener;
    public WeakReference<VKResponse> response;
    public boolean secure;
    public boolean shouldInterruptUI;
    public boolean useSystemLanguage;

    public enum VKProgressType {
        Download,
        Upload
    }

    @Deprecated
    public enum HttpMethod {
        GET,
        POST
    }

    static /* synthetic */ int access$404(VKRequest x0) {
        int i = x0.mAttemptsUsed + 1;
        x0.mAttemptsUsed = i;
        return i;
    }

    public VKParameters getMethodParameters() {
        return this.mMethodParameters;
    }

    public VKRequest(String method) {
        this(method, (VKParameters) null);
    }

    public VKRequest(String method, VKParameters parameters) {
        this(method, parameters, (Class<? extends VKApiModel>) null);
    }

    @Deprecated
    public VKRequest(String method, VKParameters parameters, HttpMethod httpMethod, Class<? extends VKApiModel> modelClass) {
        this(method, parameters, modelClass);
    }

    public VKRequest(String method, VKParameters parameters, Class<? extends VKApiModel> modelClass) {
        this.mUseLooperForCallListener = true;
        this.context = VKUIHelper.getApplicationContext();
        this.methodName = method;
        this.mMethodParameters = new VKParameters(parameters == null ? new VKParameters() : parameters);
        this.mAttemptsUsed = 0;
        this.secure = true;
        this.attempts = 1;
        this.mPreferredLang = "en";
        this.useSystemLanguage = true;
        this.shouldInterruptUI = true;
        setModelClass(modelClass);
    }

    public void setUseLooperForCallListener(boolean useLooperForCallListener) {
        this.mUseLooperForCallListener = useLooperForCallListener;
    }

    public void executeWithListener(VKRequestListener listener) {
        this.requestListener = listener;
        start();
    }

    public void executeSyncWithListener(VKRequestListener listener) {
        VKSyncRequestUtil.executeSyncWithListener(this, listener);
    }

    public void setRequestListener(@Nullable VKRequestListener listener) {
        this.requestListener = listener;
    }

    public void executeAfterRequest(VKRequest request, VKRequestListener listener) {
        this.requestListener = listener;
        request.addPostRequest(this);
    }

    private void addPostRequest(VKRequest postRequest) {
        if (this.mPostRequestsQueue == null) {
            this.mPostRequestsQueue = new ArrayList<>();
        }
        this.mPostRequestsQueue.add(postRequest);
    }

    public VKParameters getPreparedParameters() {
        if (this.mPreparedParameters == null) {
            this.mPreparedParameters = new VKParameters(this.mMethodParameters);
            VKAccessToken token = VKAccessToken.currentToken();
            if (token != null) {
                this.mPreparedParameters.put("access_token", token.accessToken);
                if (token.httpsRequired) {
                    this.secure = true;
                }
            }
            this.mPreparedParameters.put(VKApiConst.VERSION, VKSdk.getApiVersion());
            this.mPreparedParameters.put(VKApiConst.LANG, getLang());
            if (this.secure) {
                this.mPreparedParameters.put(VKApiConst.HTTPS, "1");
            }
            if (!(token == null || token.secret == null)) {
                this.mPreparedParameters.put(VKApiConst.SIG, generateSig(token));
            }
        }
        return this.mPreparedParameters;
    }

    public VKHttpClient.VKHTTPRequest getPreparedRequest() {
        VKHttpClient.VKHTTPRequest request = VKHttpClient.requestWithVkRequest(this);
        if (request != null) {
            return request;
        }
        provideError(new VKError(-103));
        return null;
    }

    /* access modifiers changed from: package-private */
    public VKAbstractOperation getOperation() {
        if (this.parseModel) {
            if (this.mModelClass != null) {
                this.mLoadingOperation = new VKModelOperation(getPreparedRequest(), this.mModelClass);
            } else if (this.mModelParser != null) {
                this.mLoadingOperation = new VKModelOperation(getPreparedRequest(), this.mModelParser);
            }
        }
        if (this.mLoadingOperation == null) {
            this.mLoadingOperation = new VKJsonOperation(getPreparedRequest());
        }
        if (this.mLoadingOperation instanceof VKHttpOperation) {
            ((VKHttpOperation) this.mLoadingOperation).setHttpOperationListener(getHttpListener());
        }
        return this.mLoadingOperation;
    }

    private VKJsonOperation.VKJSONOperationCompleteListener getHttpListener() {
        return new VKJsonOperation.VKJSONOperationCompleteListener() {
            public void onComplete(VKJsonOperation operation, JSONObject response) {
                if (response.has("error")) {
                    try {
                        VKError error = new VKError(response.getJSONObject("error"));
                        if (!VKRequest.this.processCommonError(error)) {
                            VKRequest.this.provideError(error);
                        }
                    } catch (JSONException e) {
                    }
                } else {
                    VKRequest.this.provideResponse(response, VKRequest.this.mLoadingOperation instanceof VKModelOperation ? ((VKModelOperation) VKRequest.this.mLoadingOperation).parsedModel : null);
                }
            }

            public void onError(VKJsonOperation operation, VKError error) {
                if (error.errorCode != -102 && error.errorCode != -101 && operation != null && operation.response != null && operation.response.statusCode == 200) {
                    VKRequest.this.provideResponse(operation.getResponseJson(), (Object) null);
                } else if (VKRequest.this.attempts == 0 || VKRequest.access$404(VKRequest.this) < VKRequest.this.attempts) {
                    if (VKRequest.this.requestListener != null) {
                        VKRequest.this.requestListener.attemptFailed(VKRequest.this, VKRequest.this.mAttemptsUsed, VKRequest.this.attempts);
                    }
                    VKRequest.this.runOnLooper(new Runnable() {
                        public void run() {
                            VKRequest.this.start();
                        }
                    }, 300);
                } else {
                    VKRequest.this.provideError(error);
                }
            }
        };
    }

    public void start() {
        VKAbstractOperation operation = getOperation();
        this.mLoadingOperation = operation;
        if (operation != null) {
            if (this.mLooper == null) {
                this.mLooper = Looper.myLooper();
            }
            VKHttpClient.enqueueOperation(this.mLoadingOperation);
        }
    }

    public void repeat() {
        this.mAttemptsUsed = 0;
        this.mPreparedParameters = null;
        this.mLoadingOperation = null;
        start();
    }

    public void cancel() {
        if (this.mLoadingOperation != null) {
            this.mLoadingOperation.cancel();
        } else {
            provideError(new VKError(-102));
        }
    }

    /* access modifiers changed from: private */
    public void provideError(final VKError error) {
        error.request = this;
        final boolean useLooperForCallListener = this.mUseLooperForCallListener;
        if (!useLooperForCallListener && this.requestListener != null) {
            this.requestListener.onError(error);
        }
        runOnLooper(new Runnable() {
            public void run() {
                if (useLooperForCallListener && VKRequest.this.requestListener != null) {
                    VKRequest.this.requestListener.onError(error);
                }
                if (VKRequest.this.mPostRequestsQueue != null && VKRequest.this.mPostRequestsQueue.size() > 0) {
                    Iterator it = VKRequest.this.mPostRequestsQueue.iterator();
                    while (it.hasNext()) {
                        VKRequest postRequest = (VKRequest) it.next();
                        if (postRequest.requestListener != null) {
                            postRequest.requestListener.onError(error);
                        }
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void provideResponse(JSONObject jsonResponse, Object parsedModel) {
        final VKResponse response2 = new VKResponse();
        response2.request = this;
        response2.json = jsonResponse;
        response2.parsedModel = parsedModel;
        this.response = new WeakReference<>(response2);
        if (this.mLoadingOperation instanceof VKHttpOperation) {
            response2.responseString = ((VKHttpOperation) this.mLoadingOperation).getResponseString();
        }
        final boolean useLooperForCallListener = this.mUseLooperForCallListener;
        runOnLooper(new Runnable() {
            public void run() {
                if (VKRequest.this.mPostRequestsQueue != null && VKRequest.this.mPostRequestsQueue.size() > 0) {
                    Iterator it = VKRequest.this.mPostRequestsQueue.iterator();
                    while (it.hasNext()) {
                        ((VKRequest) it.next()).start();
                    }
                }
                if (useLooperForCallListener && VKRequest.this.requestListener != null) {
                    VKRequest.this.requestListener.onComplete(response2);
                }
            }
        });
        if (!useLooperForCallListener && this.requestListener != null) {
            this.requestListener.onComplete(response2);
        }
    }

    public void addExtraParameter(String key, Object value) {
        this.mMethodParameters.put(key, value);
    }

    public void addExtraParameters(VKParameters extraParameters) {
        this.mMethodParameters.putAll(extraParameters);
    }

    private String generateSig(VKAccessToken token) {
        String queryString = VKStringJoiner.joinParams(this.mPreparedParameters);
        return VKUtil.md5(String.format(Locale.US, "/method/%s?%s", new Object[]{this.methodName, queryString}) + token.secret);
    }

    /* access modifiers changed from: private */
    public boolean processCommonError(VKError error) {
        if (error.errorCode == -101) {
            VKError apiError = error.apiError;
            VKSdk.notifySdkAboutApiError(apiError);
            if (apiError.errorCode == 16) {
                VKAccessToken token = VKAccessToken.currentToken();
                if (token != null) {
                    token.httpsRequired = true;
                    token.save();
                }
                repeat();
                return true;
            } else if (this.shouldInterruptUI) {
                apiError.request = this;
                if (error.apiError.errorCode == 14) {
                    this.mLoadingOperation = null;
                    VKServiceActivity.interruptWithError(this.context, apiError, VKServiceActivity.VKServiceType.Captcha);
                    return true;
                } else if (apiError.errorCode == 17) {
                    VKServiceActivity.interruptWithError(this.context, apiError, VKServiceActivity.VKServiceType.Validation);
                    return true;
                }
            }
        }
        return false;
    }

    private String getLang() {
        String result = this.mPreferredLang;
        Resources res = Resources.getSystem();
        if (!this.useSystemLanguage || res == null) {
            return result;
        }
        String result2 = res.getConfiguration().locale.getLanguage();
        if (result2.equals("uk")) {
            result2 = "ua";
        }
        if (!Arrays.asList(new String[]{"ru", "en", "ua", "es", "fi", "de", "it"}).contains(result2)) {
            return this.mPreferredLang;
        }
        return result2;
    }

    public void setPreferredLang(String lang) {
        this.useSystemLanguage = false;
        this.mPreferredLang = lang;
    }

    public void setModelClass(Class<? extends VKApiModel> modelClass) {
        this.mModelClass = modelClass;
        if (this.mModelClass != null) {
            this.parseModel = true;
        }
    }

    public void setResponseParser(VKParser parser) {
        this.mModelParser = parser;
        if (this.mModelParser != null) {
            this.parseModel = true;
        }
    }

    private void runOnLooper(Runnable block) {
        runOnLooper(block, 0);
    }

    /* access modifiers changed from: private */
    public void runOnLooper(Runnable block, int delay) {
        if (this.mLooper == null) {
            this.mLooper = Looper.getMainLooper();
        }
        if (delay > 0) {
            new Handler(this.mLooper).postDelayed(block, (long) delay);
        } else {
            new Handler(this.mLooper).post(block);
        }
    }

    private void runOnMainLooper(Runnable block) {
        new Handler(Looper.getMainLooper()).post(block);
    }

    public static abstract class VKRequestListener {
        public void onComplete(VKResponse response) {
        }

        public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
        }

        public void onError(VKError error) {
        }

        public void onProgress(VKProgressType progressType, long bytesLoaded, long bytesTotal) {
        }
    }

    public static VKRequest getRegisteredRequest(long requestId) {
        return (VKRequest) getRegisteredObject(requestId);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());
        builder.append("{").append(this.methodName).append(" ");
        VKParameters parameters = getMethodParameters();
        for (String key : parameters.keySet()) {
            builder.append(key).append(HttpRequest.HTTP_REQ_ENTITY_MERGE).append(parameters.get(key)).append(" ");
        }
        builder.append("}");
        return builder.toString();
    }
}
