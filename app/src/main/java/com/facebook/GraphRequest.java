package com.facebook;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.net.http.Headers;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Pair;
import com.amazonaws.services.s3.internal.Constants;
import com.facebook.GraphRequestBatch;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.InternalSettings;
import com.facebook.internal.Logger;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.games.request.Requests;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.imsdk.tool.net.AsyncHttpClient;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.model.VKApiCommunityFull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GraphRequest {
    private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";
    public static final String ACCESS_TOKEN_PARAM = "access_token";
    private static final String ATTACHED_FILES_PARAM = "attached_files";
    private static final String ATTACHMENT_FILENAME_PREFIX = "file";
    private static final String BATCH_APP_ID_PARAM = "batch_app_id";
    private static final String BATCH_BODY_PARAM = "body";
    private static final String BATCH_ENTRY_DEPENDS_ON_PARAM = "depends_on";
    private static final String BATCH_ENTRY_NAME_PARAM = "name";
    private static final String BATCH_ENTRY_OMIT_RESPONSE_ON_SUCCESS_PARAM = "omit_response_on_success";
    private static final String BATCH_METHOD_PARAM = "method";
    private static final String BATCH_PARAM = "batch";
    private static final String BATCH_RELATIVE_URL_PARAM = "relative_url";
    private static final String CAPTION_PARAM = "caption";
    private static final String CONTENT_ENCODING_HEADER = "Content-Encoding";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String DEBUG_KEY = "__debug__";
    private static final String DEBUG_MESSAGES_KEY = "messages";
    private static final String DEBUG_MESSAGE_KEY = "message";
    private static final String DEBUG_MESSAGE_LINK_KEY = "link";
    private static final String DEBUG_MESSAGE_TYPE_KEY = "type";
    private static final String DEBUG_PARAM = "debug";
    private static final String DEBUG_SEVERITY_INFO = "info";
    private static final String DEBUG_SEVERITY_WARNING = "warning";
    public static final String FIELDS_PARAM = "fields";
    private static final String FORMAT_JSON = "json";
    private static final String FORMAT_PARAM = "format";
    private static final String GRAPH_PATH_FORMAT = "%s/%s";
    private static final String ISO_8601_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final int MAXIMUM_BATCH_SIZE = 50;
    private static final String ME = "me";
    private static final String MIME_BOUNDARY = "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f";
    private static final String MY_FRIENDS = "me/friends";
    private static final String MY_PHOTOS = "me/photos";
    private static final String PICTURE_PARAM = "picture";
    private static final String SDK_ANDROID = "android";
    private static final String SDK_PARAM = "sdk";
    private static final String SEARCH = "search";
    public static final String TAG = GraphRequest.class.getSimpleName();
    private static final String USER_AGENT_BASE = "FBAndroidSDK";
    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String VIDEOS_SUFFIX = "/videos";
    private static String defaultBatchApplicationId;
    private static volatile String userAgent;
    private static Pattern versionPattern = Pattern.compile("^/?v\\d+\\.\\d+/(.*)");
    private AccessToken accessToken;
    private String batchEntryDependsOn;
    private String batchEntryName;
    private boolean batchEntryOmitResultOnSuccess;
    private Callback callback;
    private JSONObject graphObject;
    private String graphPath;
    private HttpMethod httpMethod;
    private String overriddenURL;
    private Bundle parameters;
    private boolean skipClientToken;
    private Object tag;
    private String version;

    public interface Callback {
        void onCompleted(GraphResponse graphResponse);
    }

    public interface GraphJSONArrayCallback {
        void onCompleted(JSONArray jSONArray, GraphResponse graphResponse);
    }

    public interface GraphJSONObjectCallback {
        void onCompleted(JSONObject jSONObject, GraphResponse graphResponse);
    }

    private interface KeyValueSerializer {
        void writeString(String str, String str2) throws IOException;
    }

    public interface OnProgressCallback extends Callback {
        void onProgress(long j, long j2);
    }

    public GraphRequest() {
        this((AccessToken) null, (String) null, (Bundle) null, (HttpMethod) null, (Callback) null);
    }

    public GraphRequest(AccessToken accessToken2, String graphPath2) {
        this(accessToken2, graphPath2, (Bundle) null, (HttpMethod) null, (Callback) null);
    }

    public GraphRequest(AccessToken accessToken2, String graphPath2, Bundle parameters2, HttpMethod httpMethod2) {
        this(accessToken2, graphPath2, parameters2, httpMethod2, (Callback) null);
    }

    public GraphRequest(AccessToken accessToken2, String graphPath2, Bundle parameters2, HttpMethod httpMethod2, Callback callback2) {
        this(accessToken2, graphPath2, parameters2, httpMethod2, callback2, (String) null);
    }

    public GraphRequest(AccessToken accessToken2, String graphPath2, Bundle parameters2, HttpMethod httpMethod2, Callback callback2, String version2) {
        this.batchEntryOmitResultOnSuccess = true;
        this.skipClientToken = false;
        this.accessToken = accessToken2;
        this.graphPath = graphPath2;
        this.version = version2;
        setCallback(callback2);
        setHttpMethod(httpMethod2);
        if (parameters2 != null) {
            this.parameters = new Bundle(parameters2);
        } else {
            this.parameters = new Bundle();
        }
        if (this.version == null) {
            this.version = FacebookSdk.getGraphApiVersion();
        }
    }

    GraphRequest(AccessToken accessToken2, URL overriddenURL2) {
        this.batchEntryOmitResultOnSuccess = true;
        this.skipClientToken = false;
        this.accessToken = accessToken2;
        this.overriddenURL = overriddenURL2.toString();
        setHttpMethod(HttpMethod.GET);
        this.parameters = new Bundle();
    }

    public static GraphRequest newDeleteObjectRequest(AccessToken accessToken2, String id, Callback callback2) {
        return new GraphRequest(accessToken2, id, (Bundle) null, HttpMethod.DELETE, callback2);
    }

    public static GraphRequest newMeRequest(AccessToken accessToken2, final GraphJSONObjectCallback callback2) {
        return new GraphRequest(accessToken2, ME, (Bundle) null, (HttpMethod) null, new Callback() {
            public void onCompleted(GraphResponse response) {
                if (callback2 != null) {
                    callback2.onCompleted(response.getJSONObject(), response);
                }
            }
        });
    }

    public static GraphRequest newPostRequest(AccessToken accessToken2, String graphPath2, JSONObject graphObject2, Callback callback2) {
        GraphRequest request = new GraphRequest(accessToken2, graphPath2, (Bundle) null, HttpMethod.POST, callback2);
        request.setGraphObject(graphObject2);
        return request;
    }

    public static GraphRequest newMyFriendsRequest(AccessToken accessToken2, final GraphJSONArrayCallback callback2) {
        return new GraphRequest(accessToken2, MY_FRIENDS, (Bundle) null, (HttpMethod) null, new Callback() {
            public void onCompleted(GraphResponse response) {
                if (callback2 != null) {
                    JSONObject result = response.getJSONObject();
                    callback2.onCompleted(result != null ? result.optJSONArray(ShareConstants.WEB_DIALOG_PARAM_DATA) : null, response);
                }
            }
        });
    }

    public static GraphRequest newGraphPathRequest(AccessToken accessToken2, String graphPath2, Callback callback2) {
        return new GraphRequest(accessToken2, graphPath2, (Bundle) null, (HttpMethod) null, callback2);
    }

    public static GraphRequest newPlacesSearchRequest(AccessToken accessToken2, Location location, int radiusInMeters, int resultsLimit, String searchText, final GraphJSONArrayCallback callback2) {
        if (location != null || !Utility.isNullOrEmpty(searchText)) {
            Bundle parameters2 = new Bundle(5);
            parameters2.putString("type", VKApiCommunityFull.PLACE);
            parameters2.putInt("limit", resultsLimit);
            if (location != null) {
                parameters2.putString("center", String.format(Locale.US, "%f,%f", new Object[]{Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude())}));
                parameters2.putInt("distance", radiusInMeters);
            }
            if (!Utility.isNullOrEmpty(searchText)) {
                parameters2.putString(VKApiConst.Q, searchText);
            }
            return new GraphRequest(accessToken2, "search", parameters2, HttpMethod.GET, new Callback() {
                public void onCompleted(GraphResponse response) {
                    if (callback2 != null) {
                        JSONObject result = response.getJSONObject();
                        callback2.onCompleted(result != null ? result.optJSONArray(ShareConstants.WEB_DIALOG_PARAM_DATA) : null, response);
                    }
                }
            });
        }
        throw new FacebookException("Either location or searchText must be specified.");
    }

    public static GraphRequest newUploadPhotoRequest(AccessToken accessToken2, String graphPath2, Bitmap image, String caption, Bundle params, Callback callback2) {
        String graphPath3 = getDefaultPhotoPathIfNull(graphPath2);
        Bundle parameters2 = new Bundle();
        if (params != null) {
            parameters2.putAll(params);
        }
        parameters2.putParcelable("picture", image);
        if (caption != null && !caption.isEmpty()) {
            parameters2.putString("caption", caption);
        }
        return new GraphRequest(accessToken2, graphPath3, parameters2, HttpMethod.POST, callback2);
    }

    public static GraphRequest newUploadPhotoRequest(AccessToken accessToken2, String graphPath2, File file, String caption, Bundle params, Callback callback2) throws FileNotFoundException {
        String graphPath3 = getDefaultPhotoPathIfNull(graphPath2);
        ParcelFileDescriptor descriptor = ParcelFileDescriptor.open(file, DriveFile.MODE_READ_ONLY);
        Bundle parameters2 = new Bundle();
        if (params != null) {
            parameters2.putAll(params);
        }
        parameters2.putParcelable("picture", descriptor);
        if (caption != null && !caption.isEmpty()) {
            parameters2.putString("caption", caption);
        }
        return new GraphRequest(accessToken2, graphPath3, parameters2, HttpMethod.POST, callback2);
    }

    public static GraphRequest newUploadPhotoRequest(AccessToken accessToken2, String graphPath2, Uri photoUri, String caption, Bundle params, Callback callback2) throws FileNotFoundException {
        String graphPath3 = getDefaultPhotoPathIfNull(graphPath2);
        if (Utility.isFileUri(photoUri)) {
            return newUploadPhotoRequest(accessToken2, graphPath3, new File(photoUri.getPath()), caption, params, callback2);
        } else if (!Utility.isContentUri(photoUri)) {
            throw new FacebookException("The photo Uri must be either a file:// or content:// Uri");
        } else {
            Bundle parameters2 = new Bundle();
            if (params != null) {
                parameters2.putAll(params);
            }
            parameters2.putParcelable("picture", photoUri);
            if (caption != null && !caption.isEmpty()) {
                parameters2.putString("caption", caption);
            }
            return new GraphRequest(accessToken2, graphPath3, parameters2, HttpMethod.POST, callback2);
        }
    }

    public static GraphRequest newCustomAudienceThirdPartyIdRequest(AccessToken accessToken2, Context context, String applicationId, Callback callback2) {
        String udid;
        if (applicationId == null && accessToken2 != null) {
            applicationId = accessToken2.getApplicationId();
        }
        if (applicationId == null) {
            applicationId = Utility.getMetadataApplicationId(context);
        }
        if (applicationId == null) {
            throw new FacebookException("Facebook App ID cannot be determined");
        }
        String endpoint = applicationId + "/custom_audience_third_party_id";
        AttributionIdentifiers attributionIdentifiers = AttributionIdentifiers.getAttributionIdentifiers(context);
        Bundle parameters2 = new Bundle();
        if (accessToken2 == null) {
            if (attributionIdentifiers == null) {
                throw new FacebookException("There is no access token and attribution identifiers could not be retrieved");
            }
            if (attributionIdentifiers.getAttributionId() != null) {
                udid = attributionIdentifiers.getAttributionId();
            } else {
                udid = attributionIdentifiers.getAndroidAdvertiserId();
            }
            if (attributionIdentifiers.getAttributionId() != null) {
                parameters2.putString("udid", udid);
            }
        }
        if (FacebookSdk.getLimitEventAndDataUsage(context) || (attributionIdentifiers != null && attributionIdentifiers.isTrackingLimited())) {
            parameters2.putString("limit_event_usage", "1");
        }
        return new GraphRequest(accessToken2, endpoint, parameters2, HttpMethod.GET, callback2);
    }

    public static GraphRequest newCustomAudienceThirdPartyIdRequest(AccessToken accessToken2, Context context, Callback callback2) {
        return newCustomAudienceThirdPartyIdRequest(accessToken2, context, (String) null, callback2);
    }

    public final JSONObject getGraphObject() {
        return this.graphObject;
    }

    public final void setGraphObject(JSONObject graphObject2) {
        this.graphObject = graphObject2;
    }

    public final String getGraphPath() {
        return this.graphPath;
    }

    public final void setGraphPath(String graphPath2) {
        this.graphPath = graphPath2;
    }

    public final HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public final void setHttpMethod(HttpMethod httpMethod2) {
        if (this.overriddenURL == null || httpMethod2 == HttpMethod.GET) {
            if (httpMethod2 == null) {
                httpMethod2 = HttpMethod.GET;
            }
            this.httpMethod = httpMethod2;
            return;
        }
        throw new FacebookException("Can't change HTTP method on request with overridden URL.");
    }

    public final String getVersion() {
        return this.version;
    }

    public final void setVersion(String version2) {
        this.version = version2;
    }

    public final void setSkipClientToken(boolean skipClientToken2) {
        this.skipClientToken = skipClientToken2;
    }

    public final Bundle getParameters() {
        return this.parameters;
    }

    public final void setParameters(Bundle parameters2) {
        this.parameters = parameters2;
    }

    public final AccessToken getAccessToken() {
        return this.accessToken;
    }

    public final void setAccessToken(AccessToken accessToken2) {
        this.accessToken = accessToken2;
    }

    public final String getBatchEntryName() {
        return this.batchEntryName;
    }

    public final void setBatchEntryName(String batchEntryName2) {
        this.batchEntryName = batchEntryName2;
    }

    public final String getBatchEntryDependsOn() {
        return this.batchEntryDependsOn;
    }

    public final void setBatchEntryDependsOn(String batchEntryDependsOn2) {
        this.batchEntryDependsOn = batchEntryDependsOn2;
    }

    public final boolean getBatchEntryOmitResultOnSuccess() {
        return this.batchEntryOmitResultOnSuccess;
    }

    public final void setBatchEntryOmitResultOnSuccess(boolean batchEntryOmitResultOnSuccess2) {
        this.batchEntryOmitResultOnSuccess = batchEntryOmitResultOnSuccess2;
    }

    public static final String getDefaultBatchApplicationId() {
        return defaultBatchApplicationId;
    }

    public static final void setDefaultBatchApplicationId(String applicationId) {
        defaultBatchApplicationId = applicationId;
    }

    public final Callback getCallback() {
        return this.callback;
    }

    public final void setCallback(final Callback callback2) {
        if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_INFO) || FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_WARNING)) {
            this.callback = new Callback() {
                public void onCompleted(GraphResponse response) {
                    JSONObject debug;
                    JSONArray debugMessages;
                    String debugMessage;
                    String debugMessageType;
                    String debugMessageLink;
                    JSONObject responseObject = response.getJSONObject();
                    if (responseObject != null) {
                        debug = responseObject.optJSONObject(GraphRequest.DEBUG_KEY);
                    } else {
                        debug = null;
                    }
                    if (debug != null) {
                        debugMessages = debug.optJSONArray("messages");
                    } else {
                        debugMessages = null;
                    }
                    if (debugMessages != null) {
                        for (int i = 0; i < debugMessages.length(); i++) {
                            JSONObject debugMessageObject = debugMessages.optJSONObject(i);
                            if (debugMessageObject != null) {
                                debugMessage = debugMessageObject.optString("message");
                            } else {
                                debugMessage = null;
                            }
                            if (debugMessageObject != null) {
                                debugMessageType = debugMessageObject.optString("type");
                            } else {
                                debugMessageType = null;
                            }
                            if (debugMessageObject != null) {
                                debugMessageLink = debugMessageObject.optString("link");
                            } else {
                                debugMessageLink = null;
                            }
                            if (!(debugMessage == null || debugMessageType == null)) {
                                LoggingBehavior behavior = LoggingBehavior.GRAPH_API_DEBUG_INFO;
                                if (debugMessageType.equals(GraphRequest.DEBUG_SEVERITY_WARNING)) {
                                    behavior = LoggingBehavior.GRAPH_API_DEBUG_WARNING;
                                }
                                if (!Utility.isNullOrEmpty(debugMessageLink)) {
                                    debugMessage = debugMessage + " Link: " + debugMessageLink;
                                }
                                Logger.log(behavior, GraphRequest.TAG, debugMessage);
                            }
                        }
                    }
                    if (callback2 != null) {
                        callback2.onCompleted(response);
                    }
                }
            };
        } else {
            this.callback = callback2;
        }
    }

    public final void setTag(Object tag2) {
        this.tag = tag2;
    }

    public final Object getTag() {
        return this.tag;
    }

    public final GraphResponse executeAndWait() {
        return executeAndWait(this);
    }

    public final GraphRequestAsyncTask executeAsync() {
        return executeBatchAsync(this);
    }

    public static HttpURLConnection toHttpConnection(GraphRequest... requests) {
        return toHttpConnection((Collection<GraphRequest>) Arrays.asList(requests));
    }

    public static HttpURLConnection toHttpConnection(Collection<GraphRequest> requests) {
        Validate.notEmptyAndContainsNoNulls(requests, Requests.EXTRA_REQUESTS);
        return toHttpConnection(new GraphRequestBatch(requests));
    }

    public static HttpURLConnection toHttpConnection(GraphRequestBatch requests) {
        URL url;
        validateFieldsParamForGetRequests(requests);
        try {
            if (requests.size() == 1) {
                url = new URL(requests.get(0).getUrlForSingleRequest());
            } else {
                url = new URL(ServerProtocol.getGraphUrlBase());
            }
            HttpURLConnection connection = null;
            try {
                connection = createConnection(url);
                serializeToUrlConnection(requests, connection);
                return connection;
            } catch (IOException | JSONException e) {
                Utility.disconnectQuietly(connection);
                throw new FacebookException("could not construct request body", (Throwable) e);
            }
        } catch (MalformedURLException e2) {
            throw new FacebookException("could not construct URL for request", (Throwable) e2);
        }
    }

    public static GraphResponse executeAndWait(GraphRequest request) {
        List<GraphResponse> responses = executeBatchAndWait(request);
        if (responses != null && responses.size() == 1) {
            return responses.get(0);
        }
        throw new FacebookException("invalid state: expected a single response");
    }

    public static List<GraphResponse> executeBatchAndWait(GraphRequest... requests) {
        Validate.notNull(requests, Requests.EXTRA_REQUESTS);
        return executeBatchAndWait((Collection<GraphRequest>) Arrays.asList(requests));
    }

    public static List<GraphResponse> executeBatchAndWait(Collection<GraphRequest> requests) {
        return executeBatchAndWait(new GraphRequestBatch(requests));
    }

    public static List<GraphResponse> executeBatchAndWait(GraphRequestBatch requests) {
        List<GraphResponse> responses;
        Validate.notEmptyAndContainsNoNulls(requests, Requests.EXTRA_REQUESTS);
        HttpURLConnection connection = null;
        try {
            connection = toHttpConnection(requests);
            responses = executeConnectionAndWait(connection, requests);
        } catch (Exception ex) {
            responses = GraphResponse.constructErrorResponses(requests.getRequests(), (HttpURLConnection) null, new FacebookException((Throwable) ex));
            runCallbacks(requests, responses);
        } finally {
            Utility.disconnectQuietly(connection);
        }
        return responses;
    }

    public static GraphRequestAsyncTask executeBatchAsync(GraphRequest... requests) {
        Validate.notNull(requests, Requests.EXTRA_REQUESTS);
        return executeBatchAsync((Collection<GraphRequest>) Arrays.asList(requests));
    }

    public static GraphRequestAsyncTask executeBatchAsync(Collection<GraphRequest> requests) {
        return executeBatchAsync(new GraphRequestBatch(requests));
    }

    public static GraphRequestAsyncTask executeBatchAsync(GraphRequestBatch requests) {
        Validate.notEmptyAndContainsNoNulls(requests, Requests.EXTRA_REQUESTS);
        GraphRequestAsyncTask asyncTask = new GraphRequestAsyncTask(requests);
        asyncTask.executeOnExecutor(FacebookSdk.getExecutor(), new Void[0]);
        return asyncTask;
    }

    public static List<GraphResponse> executeConnectionAndWait(HttpURLConnection connection, Collection<GraphRequest> requests) {
        return executeConnectionAndWait(connection, new GraphRequestBatch(requests));
    }

    public static List<GraphResponse> executeConnectionAndWait(HttpURLConnection connection, GraphRequestBatch requests) {
        List<GraphResponse> responses = GraphResponse.fromHttpConnection(connection, requests);
        Utility.disconnectQuietly(connection);
        int numRequests = requests.size();
        if (numRequests != responses.size()) {
            throw new FacebookException(String.format(Locale.US, "Received %d responses while expecting %d", new Object[]{Integer.valueOf(responses.size()), Integer.valueOf(numRequests)}));
        }
        runCallbacks(requests, responses);
        AccessTokenManager.getInstance().extendAccessTokenIfNeeded();
        return responses;
    }

    public static GraphRequestAsyncTask executeConnectionAsync(HttpURLConnection connection, GraphRequestBatch requests) {
        return executeConnectionAsync((Handler) null, connection, requests);
    }

    public static GraphRequestAsyncTask executeConnectionAsync(Handler callbackHandler, HttpURLConnection connection, GraphRequestBatch requests) {
        Validate.notNull(connection, Headers.CONN_DIRECTIVE);
        GraphRequestAsyncTask asyncTask = new GraphRequestAsyncTask(connection, requests);
        requests.setCallbackHandler(callbackHandler);
        asyncTask.executeOnExecutor(FacebookSdk.getExecutor(), new Void[0]);
        return asyncTask;
    }

    public String toString() {
        return "{Request: " + " accessToken: " + (this.accessToken == null ? Constants.NULL_VERSION_ID : this.accessToken) + ", graphPath: " + this.graphPath + ", graphObject: " + this.graphObject + ", httpMethod: " + this.httpMethod + ", parameters: " + this.parameters + "}";
    }

    static void runCallbacks(final GraphRequestBatch requests, List<GraphResponse> responses) {
        int numRequests = requests.size();
        final ArrayList<Pair<Callback, GraphResponse>> callbacks = new ArrayList<>();
        for (int i = 0; i < numRequests; i++) {
            GraphRequest request = requests.get(i);
            if (request.callback != null) {
                callbacks.add(new Pair(request.callback, responses.get(i)));
            }
        }
        if (callbacks.size() > 0) {
            Runnable runnable = new Runnable() {
                public void run() {
                    Iterator it = callbacks.iterator();
                    while (it.hasNext()) {
                        Pair<Callback, GraphResponse> pair = (Pair) it.next();
                        ((Callback) pair.first).onCompleted((GraphResponse) pair.second);
                    }
                    for (GraphRequestBatch.Callback batchCallback : requests.getCallbacks()) {
                        batchCallback.onBatchCompleted(requests);
                    }
                }
            };
            Handler callbackHandler = requests.getCallbackHandler();
            if (callbackHandler == null) {
                runnable.run();
            } else {
                callbackHandler.post(runnable);
            }
        }
    }

    private static String getDefaultPhotoPathIfNull(String graphPath2) {
        return graphPath2 == null ? "me/photos" : graphPath2;
    }

    private static HttpURLConnection createConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", getUserAgent());
        connection.setRequestProperty("Accept-Language", Locale.getDefault().toString());
        connection.setChunkedStreamingMode(0);
        return connection;
    }

    private void addCommonParameters() {
        if (this.accessToken != null) {
            if (!this.parameters.containsKey("access_token")) {
                String token = this.accessToken.getToken();
                Logger.registerAccessToken(token);
                this.parameters.putString("access_token", token);
            }
        } else if (!this.skipClientToken && !this.parameters.containsKey("access_token")) {
            String appID = FacebookSdk.getApplicationId();
            String clientToken = FacebookSdk.getClientToken();
            if (Utility.isNullOrEmpty(appID) || Utility.isNullOrEmpty(clientToken)) {
                Utility.logd(TAG, "Warning: Request without access token missing application ID or client token.");
            } else {
                this.parameters.putString("access_token", appID + "|" + clientToken);
            }
        }
        this.parameters.putString("sdk", "android");
        this.parameters.putString(FORMAT_PARAM, FORMAT_JSON);
        if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_INFO)) {
            this.parameters.putString("debug", "info");
        } else if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_WARNING)) {
            this.parameters.putString("debug", DEBUG_SEVERITY_WARNING);
        }
    }

    private String appendParametersToBaseUrl(String baseUrl, Boolean isBatch) {
        if (!isBatch.booleanValue() && this.httpMethod == HttpMethod.POST) {
            return baseUrl;
        }
        Uri.Builder uriBuilder = Uri.parse(baseUrl).buildUpon();
        for (String key : this.parameters.keySet()) {
            Object value = this.parameters.get(key);
            if (value == null) {
                value = "";
            }
            if (isSupportedParameterType(value)) {
                uriBuilder.appendQueryParameter(key, parameterToString(value).toString());
            } else if (this.httpMethod == HttpMethod.GET) {
                throw new IllegalArgumentException(String.format(Locale.US, "Unsupported parameter type for GET request: %s", new Object[]{value.getClass().getSimpleName()}));
            }
        }
        return uriBuilder.toString();
    }

    /* access modifiers changed from: package-private */
    public final String getRelativeUrlForBatchedRequest() {
        if (this.overriddenURL != null) {
            throw new FacebookException("Can't override URL for a batch request");
        }
        String baseUrl = String.format(GRAPH_PATH_FORMAT, new Object[]{ServerProtocol.getGraphUrlBase(), getGraphPathWithVersion()});
        addCommonParameters();
        Uri uri = Uri.parse(appendParametersToBaseUrl(baseUrl, true));
        return String.format("%s?%s", new Object[]{uri.getPath(), uri.getQuery()});
    }

    /* access modifiers changed from: package-private */
    public final String getUrlForSingleRequest() {
        String graphBaseUrlBase;
        if (this.overriddenURL != null) {
            return this.overriddenURL.toString();
        }
        if (getHttpMethod() != HttpMethod.POST || this.graphPath == null || !this.graphPath.endsWith(VIDEOS_SUFFIX)) {
            graphBaseUrlBase = ServerProtocol.getGraphUrlBase();
        } else {
            graphBaseUrlBase = ServerProtocol.getGraphVideoUrlBase();
        }
        String baseUrl = String.format(GRAPH_PATH_FORMAT, new Object[]{graphBaseUrlBase, getGraphPathWithVersion()});
        addCommonParameters();
        return appendParametersToBaseUrl(baseUrl, false);
    }

    private String getGraphPathWithVersion() {
        if (versionPattern.matcher(this.graphPath).matches()) {
            return this.graphPath;
        }
        return String.format(GRAPH_PATH_FORMAT, new Object[]{this.version, this.graphPath});
    }

    private static class Attachment {
        private final GraphRequest request;
        private final Object value;

        public Attachment(GraphRequest request2, Object value2) {
            this.request = request2;
            this.value = value2;
        }

        public GraphRequest getRequest() {
            return this.request;
        }

        public Object getValue() {
            return this.value;
        }
    }

    private void serializeToBatch(JSONArray batch, Map<String, Attachment> attachments) throws JSONException, IOException {
        JSONObject batchEntry = new JSONObject();
        if (this.batchEntryName != null) {
            batchEntry.put("name", this.batchEntryName);
            batchEntry.put(BATCH_ENTRY_OMIT_RESPONSE_ON_SUCCESS_PARAM, this.batchEntryOmitResultOnSuccess);
        }
        if (this.batchEntryDependsOn != null) {
            batchEntry.put(BATCH_ENTRY_DEPENDS_ON_PARAM, this.batchEntryDependsOn);
        }
        String relativeURL = getRelativeUrlForBatchedRequest();
        batchEntry.put(BATCH_RELATIVE_URL_PARAM, relativeURL);
        batchEntry.put(BATCH_METHOD_PARAM, this.httpMethod);
        if (this.accessToken != null) {
            Logger.registerAccessToken(this.accessToken.getToken());
        }
        ArrayList<String> attachmentNames = new ArrayList<>();
        for (String key : this.parameters.keySet()) {
            Object value = this.parameters.get(key);
            if (isSupportedAttachmentType(value)) {
                String name = String.format(Locale.ROOT, "%s%d", new Object[]{"file", Integer.valueOf(attachments.size())});
                attachmentNames.add(name);
                attachments.put(name, new Attachment(this, value));
            }
        }
        if (!attachmentNames.isEmpty()) {
            batchEntry.put(ATTACHED_FILES_PARAM, TextUtils.join(",", attachmentNames));
        }
        if (this.graphObject != null) {
            final ArrayList<String> keysAndValues = new ArrayList<>();
            processGraphObject(this.graphObject, relativeURL, new KeyValueSerializer() {
                public void writeString(String key, String value) throws IOException {
                    keysAndValues.add(String.format(Locale.US, "%s=%s", new Object[]{key, URLEncoder.encode(value, "UTF-8")}));
                }
            });
            batchEntry.put(BATCH_BODY_PARAM, TextUtils.join(HttpRequest.HTTP_REQ_ENTITY_JOIN, keysAndValues));
        }
        batch.put(batchEntry);
    }

    private static boolean hasOnProgressCallbacks(GraphRequestBatch requests) {
        for (GraphRequestBatch.Callback callback2 : requests.getCallbacks()) {
            if (callback2 instanceof GraphRequestBatch.OnProgressCallback) {
                return true;
            }
        }
        Iterator it = requests.iterator();
        while (it.hasNext()) {
            if (((GraphRequest) it.next()).getCallback() instanceof OnProgressCallback) {
                return true;
            }
        }
        return false;
    }

    private static void setConnectionContentType(HttpURLConnection connection, boolean shouldUseGzip) {
        if (shouldUseGzip) {
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Encoding", AsyncHttpClient.ENCODING_GZIP);
            return;
        }
        connection.setRequestProperty("Content-Type", getMimeContentType());
    }

    private static boolean isGzipCompressible(GraphRequestBatch requests) {
        Iterator it = requests.iterator();
        while (it.hasNext()) {
            GraphRequest request = (GraphRequest) it.next();
            Iterator it2 = request.parameters.keySet().iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (isSupportedAttachmentType(request.parameters.get((String) it2.next()))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    static final boolean shouldWarnOnMissingFieldsParam(GraphRequest request) {
        boolean z = false;
        String version2 = request.getVersion();
        if (Utility.isNullOrEmpty(version2)) {
            return true;
        }
        if (version2.startsWith(VKApiConst.VERSION)) {
            version2 = version2.substring(1);
        }
        String[] versionParts = version2.split("\\.");
        if ((versionParts.length >= 2 && Integer.parseInt(versionParts[0]) > 2) || (Integer.parseInt(versionParts[0]) >= 2 && Integer.parseInt(versionParts[1]) >= 4)) {
            z = true;
        }
        return z;
    }

    static final void validateFieldsParamForGetRequests(GraphRequestBatch requests) {
        Iterator it = requests.iterator();
        while (it.hasNext()) {
            GraphRequest request = (GraphRequest) it.next();
            if (HttpMethod.GET.equals(request.getHttpMethod()) && shouldWarnOnMissingFieldsParam(request)) {
                Bundle params = request.getParameters();
                if (!params.containsKey("fields") || Utility.isNullOrEmpty(params.getString("fields"))) {
                    Logger.log(LoggingBehavior.DEVELOPER_ERRORS, 5, "Request", "starting with Graph API v2.4, GET requests for /%s should contain an explicit \"fields\" parameter.", request.getGraphPath());
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x00f4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static final void serializeToUrlConnection(com.facebook.GraphRequestBatch r22, java.net.HttpURLConnection r23) throws java.io.IOException, org.json.JSONException {
        /*
            com.facebook.internal.Logger r20 = new com.facebook.internal.Logger
            com.facebook.LoggingBehavior r2 = com.facebook.LoggingBehavior.REQUESTS
            java.lang.String r3 = "Request"
            r0 = r20
            r0.<init>(r2, r3)
            int r4 = r22.size()
            boolean r7 = isGzipCompressible(r22)
            r2 = 1
            if (r4 != r2) goto L_0x0095
            r2 = 0
            r0 = r22
            com.facebook.GraphRequest r2 = r0.get((int) r2)
            com.facebook.HttpMethod r0 = r2.httpMethod
            r18 = r0
        L_0x0021:
            java.lang.String r2 = r18.name()
            r0 = r23
            r0.setRequestMethod(r2)
            r0 = r23
            setConnectionContentType(r0, r7)
            java.net.URL r5 = r23.getURL()
            java.lang.String r2 = "Request:\n"
            r0 = r20
            r0.append((java.lang.String) r2)
            java.lang.String r2 = "Id"
            java.lang.String r3 = r22.getId()
            r0 = r20
            r0.appendKeyValue(r2, r3)
            java.lang.String r2 = "URL"
            r0 = r20
            r0.appendKeyValue(r2, r5)
            java.lang.String r2 = "Method"
            java.lang.String r3 = r23.getRequestMethod()
            r0 = r20
            r0.appendKeyValue(r2, r3)
            java.lang.String r2 = "User-Agent"
            java.lang.String r3 = "User-Agent"
            r0 = r23
            java.lang.String r3 = r0.getRequestProperty(r3)
            r0 = r20
            r0.appendKeyValue(r2, r3)
            java.lang.String r2 = "Content-Type"
            java.lang.String r3 = "Content-Type"
            r0 = r23
            java.lang.String r3 = r0.getRequestProperty(r3)
            r0 = r20
            r0.appendKeyValue(r2, r3)
            int r2 = r22.getTimeout()
            r0 = r23
            r0.setConnectTimeout(r2)
            int r2 = r22.getTimeout()
            r0 = r23
            r0.setReadTimeout(r2)
            com.facebook.HttpMethod r2 = com.facebook.HttpMethod.POST
            r0 = r18
            if (r0 != r2) goto L_0x0098
            r19 = 1
        L_0x008f:
            if (r19 != 0) goto L_0x009b
            r20.log()
        L_0x0094:
            return
        L_0x0095:
            com.facebook.HttpMethod r18 = com.facebook.HttpMethod.POST
            goto L_0x0021
        L_0x0098:
            r19 = 0
            goto L_0x008f
        L_0x009b:
            r2 = 1
            r0 = r23
            r0.setDoOutput(r2)
            r8 = 0
            java.io.BufferedOutputStream r9 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x00f1 }
            java.io.OutputStream r2 = r23.getOutputStream()     // Catch:{ all -> 0x00f1 }
            r9.<init>(r2)     // Catch:{ all -> 0x00f1 }
            if (r7 == 0) goto L_0x00b3
            java.util.zip.GZIPOutputStream r8 = new java.util.zip.GZIPOutputStream     // Catch:{ all -> 0x00f8 }
            r8.<init>(r9)     // Catch:{ all -> 0x00f8 }
            r9 = r8
        L_0x00b3:
            boolean r2 = hasOnProgressCallbacks(r22)     // Catch:{ all -> 0x00f8 }
            if (r2 == 0) goto L_0x00fb
            r6 = 0
            com.facebook.ProgressNoopOutputStream r6 = new com.facebook.ProgressNoopOutputStream     // Catch:{ all -> 0x00f8 }
            android.os.Handler r2 = r22.getCallbackHandler()     // Catch:{ all -> 0x00f8 }
            r6.<init>(r2)     // Catch:{ all -> 0x00f8 }
            r3 = 0
            r2 = r22
            processRequest(r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x00f8 }
            int r21 = r6.getMaxProgress()     // Catch:{ all -> 0x00f8 }
            java.util.Map r11 = r6.getProgressMap()     // Catch:{ all -> 0x00f8 }
            com.facebook.ProgressOutputStream r8 = new com.facebook.ProgressOutputStream     // Catch:{ all -> 0x00f8 }
            r0 = r21
            long r12 = (long) r0     // Catch:{ all -> 0x00f8 }
            r10 = r22
            r8.<init>(r9, r10, r11, r12)     // Catch:{ all -> 0x00f8 }
        L_0x00db:
            r12 = r22
            r13 = r20
            r14 = r4
            r15 = r5
            r16 = r8
            r17 = r7
            processRequest(r12, r13, r14, r15, r16, r17)     // Catch:{ all -> 0x00f1 }
            if (r8 == 0) goto L_0x00ed
            r8.close()
        L_0x00ed:
            r20.log()
            goto L_0x0094
        L_0x00f1:
            r2 = move-exception
        L_0x00f2:
            if (r8 == 0) goto L_0x00f7
            r8.close()
        L_0x00f7:
            throw r2
        L_0x00f8:
            r2 = move-exception
            r8 = r9
            goto L_0x00f2
        L_0x00fb:
            r8 = r9
            goto L_0x00db
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.GraphRequest.serializeToUrlConnection(com.facebook.GraphRequestBatch, java.net.HttpURLConnection):void");
    }

    private static void processRequest(GraphRequestBatch requests, Logger logger, int numRequests, URL url, OutputStream outputStream, boolean shouldUseGzip) throws IOException, JSONException {
        Serializer serializer = new Serializer(outputStream, logger, shouldUseGzip);
        if (numRequests == 1) {
            GraphRequest request = requests.get(0);
            Map<String, Attachment> attachments = new HashMap<>();
            for (String key : request.parameters.keySet()) {
                Object value = request.parameters.get(key);
                if (isSupportedAttachmentType(value)) {
                    attachments.put(key, new Attachment(request, value));
                }
            }
            if (logger != null) {
                logger.append("  Parameters:\n");
            }
            serializeParameters(request.parameters, serializer, request);
            if (logger != null) {
                logger.append("  Attachments:\n");
            }
            serializeAttachments(attachments, serializer);
            if (request.graphObject != null) {
                processGraphObject(request.graphObject, url.getPath(), serializer);
                return;
            }
            return;
        }
        String batchAppID = getBatchAppId(requests);
        if (Utility.isNullOrEmpty(batchAppID)) {
            throw new FacebookException("App ID was not specified at the request or Settings.");
        }
        serializer.writeString(BATCH_APP_ID_PARAM, batchAppID);
        Map<String, Attachment> attachments2 = new HashMap<>();
        serializeRequestsAsJSON(serializer, requests, attachments2);
        if (logger != null) {
            logger.append("  Attachments:\n");
        }
        serializeAttachments(attachments2, serializer);
    }

    private static boolean isMeRequest(String path) {
        Matcher matcher = versionPattern.matcher(path);
        if (matcher.matches()) {
            path = matcher.group(1);
        }
        if (path.startsWith("me/") || path.startsWith("/me/")) {
            return true;
        }
        return false;
    }

    private static void processGraphObject(JSONObject graphObject2, String path, KeyValueSerializer serializer) throws IOException {
        boolean passByValue;
        boolean isOGAction = false;
        if (isMeRequest(path)) {
            int colonLocation = path.indexOf(":");
            int questionMarkLocation = path.indexOf("?");
            if (colonLocation <= 3 || (questionMarkLocation != -1 && colonLocation >= questionMarkLocation)) {
                isOGAction = false;
            } else {
                isOGAction = true;
            }
        }
        Iterator<String> keyIterator = graphObject2.keys();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            Object value = graphObject2.opt(key);
            if (!isOGAction || !key.equalsIgnoreCase("image")) {
                passByValue = false;
            } else {
                passByValue = true;
            }
            processGraphObjectProperty(key, value, serializer, passByValue);
        }
    }

    private static void processGraphObjectProperty(String key, Object value, KeyValueSerializer serializer, boolean passByValue) throws IOException {
        Class<?> valueClass = value.getClass();
        if (JSONObject.class.isAssignableFrom(valueClass)) {
            JSONObject jsonObject = (JSONObject) value;
            if (passByValue) {
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String propertyName = keys.next();
                    processGraphObjectProperty(String.format("%s[%s]", new Object[]{key, propertyName}), jsonObject.opt(propertyName), serializer, passByValue);
                }
            } else if (jsonObject.has("id")) {
                processGraphObjectProperty(key, jsonObject.optString("id"), serializer, passByValue);
            } else if (jsonObject.has("url")) {
                processGraphObjectProperty(key, jsonObject.optString("url"), serializer, passByValue);
            } else if (jsonObject.has(NativeProtocol.OPEN_GRAPH_CREATE_OBJECT_KEY)) {
                processGraphObjectProperty(key, jsonObject.toString(), serializer, passByValue);
            }
        } else if (JSONArray.class.isAssignableFrom(valueClass)) {
            JSONArray jsonArray = (JSONArray) value;
            int length = jsonArray.length();
            for (int i = 0; i < length; i++) {
                processGraphObjectProperty(String.format(Locale.ROOT, "%s[%d]", new Object[]{key, Integer.valueOf(i)}), jsonArray.opt(i), serializer, passByValue);
            }
        } else if (String.class.isAssignableFrom(valueClass) || Number.class.isAssignableFrom(valueClass) || Boolean.class.isAssignableFrom(valueClass)) {
            serializer.writeString(key, value.toString());
        } else if (Date.class.isAssignableFrom(valueClass)) {
            KeyValueSerializer keyValueSerializer = serializer;
            String str = key;
            keyValueSerializer.writeString(str, new SimpleDateFormat(ISO_8601_FORMAT_STRING, Locale.US).format((Date) value));
        }
    }

    private static void serializeParameters(Bundle bundle, Serializer serializer, GraphRequest request) throws IOException {
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            if (isSupportedParameterType(value)) {
                serializer.writeObject(key, value, request);
            }
        }
    }

    private static void serializeAttachments(Map<String, Attachment> attachments, Serializer serializer) throws IOException {
        for (String key : attachments.keySet()) {
            Attachment attachment = attachments.get(key);
            if (isSupportedAttachmentType(attachment.getValue())) {
                serializer.writeObject(key, attachment.getValue(), attachment.getRequest());
            }
        }
    }

    private static void serializeRequestsAsJSON(Serializer serializer, Collection<GraphRequest> requests, Map<String, Attachment> attachments) throws JSONException, IOException {
        JSONArray batch = new JSONArray();
        for (GraphRequest request : requests) {
            request.serializeToBatch(batch, attachments);
        }
        serializer.writeRequestsAsJson(BATCH_PARAM, batch, requests);
    }

    private static String getMimeContentType() {
        return String.format("multipart/form-data; boundary=%s", new Object[]{MIME_BOUNDARY});
    }

    private static String getUserAgent() {
        if (userAgent == null) {
            userAgent = String.format("%s.%s", new Object[]{USER_AGENT_BASE, FacebookSdkVersion.BUILD});
            String customUserAgent = InternalSettings.getCustomUserAgent();
            if (!Utility.isNullOrEmpty(customUserAgent)) {
                userAgent = String.format(Locale.ROOT, GRAPH_PATH_FORMAT, new Object[]{userAgent, customUserAgent});
            }
        }
        return userAgent;
    }

    private static String getBatchAppId(GraphRequestBatch batch) {
        String applicationId;
        if (!Utility.isNullOrEmpty(batch.getBatchApplicationId())) {
            return batch.getBatchApplicationId();
        }
        Iterator it = batch.iterator();
        while (it.hasNext()) {
            AccessToken accessToken2 = ((GraphRequest) it.next()).accessToken;
            if (accessToken2 != null && (applicationId = accessToken2.getApplicationId()) != null) {
                return applicationId;
            }
        }
        if (!Utility.isNullOrEmpty(defaultBatchApplicationId)) {
            return defaultBatchApplicationId;
        }
        return FacebookSdk.getApplicationId();
    }

    private static boolean isSupportedAttachmentType(Object value) {
        return (value instanceof Bitmap) || (value instanceof byte[]) || (value instanceof Uri) || (value instanceof ParcelFileDescriptor) || (value instanceof ParcelableResourceWithMimeType);
    }

    /* access modifiers changed from: private */
    public static boolean isSupportedParameterType(Object value) {
        return (value instanceof String) || (value instanceof Boolean) || (value instanceof Number) || (value instanceof Date);
    }

    /* access modifiers changed from: private */
    public static String parameterToString(Object value) {
        if (value instanceof String) {
            return (String) value;
        }
        if ((value instanceof Boolean) || (value instanceof Number)) {
            return value.toString();
        }
        if (value instanceof Date) {
            return new SimpleDateFormat(ISO_8601_FORMAT_STRING, Locale.US).format(value);
        }
        throw new IllegalArgumentException("Unsupported parameter type.");
    }

    private static class Serializer implements KeyValueSerializer {
        private boolean firstWrite = true;
        private final Logger logger;
        private final OutputStream outputStream;
        private boolean useUrlEncode = false;

        public Serializer(OutputStream outputStream2, Logger logger2, boolean useUrlEncode2) {
            this.outputStream = outputStream2;
            this.logger = logger2;
            this.useUrlEncode = useUrlEncode2;
        }

        public void writeObject(String key, Object value, GraphRequest request) throws IOException {
            if (this.outputStream instanceof RequestOutputStream) {
                ((RequestOutputStream) this.outputStream).setCurrentRequest(request);
            }
            if (GraphRequest.isSupportedParameterType(value)) {
                writeString(key, GraphRequest.parameterToString(value));
            } else if (value instanceof Bitmap) {
                writeBitmap(key, (Bitmap) value);
            } else if (value instanceof byte[]) {
                writeBytes(key, (byte[]) value);
            } else if (value instanceof Uri) {
                writeContentUri(key, (Uri) value, (String) null);
            } else if (value instanceof ParcelFileDescriptor) {
                writeFile(key, (ParcelFileDescriptor) value, (String) null);
            } else if (value instanceof ParcelableResourceWithMimeType) {
                ParcelableResourceWithMimeType resourceWithMimeType = (ParcelableResourceWithMimeType) value;
                Parcelable resource = resourceWithMimeType.getResource();
                String mimeType = resourceWithMimeType.getMimeType();
                if (resource instanceof ParcelFileDescriptor) {
                    writeFile(key, (ParcelFileDescriptor) resource, mimeType);
                } else if (resource instanceof Uri) {
                    writeContentUri(key, (Uri) resource, mimeType);
                } else {
                    throw getInvalidTypeError();
                }
            } else {
                throw getInvalidTypeError();
            }
        }

        private RuntimeException getInvalidTypeError() {
            return new IllegalArgumentException("value is not a supported type.");
        }

        public void writeRequestsAsJson(String key, JSONArray requestJsonArray, Collection<GraphRequest> requests) throws IOException, JSONException {
            if (!(this.outputStream instanceof RequestOutputStream)) {
                writeString(key, requestJsonArray.toString());
                return;
            }
            RequestOutputStream requestOutputStream = (RequestOutputStream) this.outputStream;
            writeContentDisposition(key, (String) null, (String) null);
            write("[", new Object[0]);
            int i = 0;
            for (GraphRequest request : requests) {
                JSONObject requestJson = requestJsonArray.getJSONObject(i);
                requestOutputStream.setCurrentRequest(request);
                if (i > 0) {
                    write(",%s", requestJson.toString());
                } else {
                    write("%s", requestJson.toString());
                }
                i++;
            }
            write("]", new Object[0]);
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + key, requestJsonArray.toString());
            }
        }

        public void writeString(String key, String value) throws IOException {
            writeContentDisposition(key, (String) null, (String) null);
            writeLine("%s", value);
            writeRecordBoundary();
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + key, value);
            }
        }

        public void writeBitmap(String key, Bitmap bitmap) throws IOException {
            writeContentDisposition(key, key, "image/png");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, this.outputStream);
            writeLine("", new Object[0]);
            writeRecordBoundary();
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + key, "<Image>");
            }
        }

        public void writeBytes(String key, byte[] bytes) throws IOException {
            writeContentDisposition(key, key, "content/unknown");
            this.outputStream.write(bytes);
            writeLine("", new Object[0]);
            writeRecordBoundary();
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + key, String.format(Locale.ROOT, "<Data: %d>", new Object[]{Integer.valueOf(bytes.length)}));
            }
        }

        public void writeContentUri(String key, Uri contentUri, String mimeType) throws IOException {
            if (mimeType == null) {
                mimeType = "content/unknown";
            }
            writeContentDisposition(key, key, mimeType);
            int totalBytes = 0;
            if (this.outputStream instanceof ProgressNoopOutputStream) {
                ((ProgressNoopOutputStream) this.outputStream).addProgress(Utility.getContentSize(contentUri));
            } else {
                totalBytes = 0 + Utility.copyAndCloseInputStream(FacebookSdk.getApplicationContext().getContentResolver().openInputStream(contentUri), this.outputStream);
            }
            writeLine("", new Object[0]);
            writeRecordBoundary();
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + key, String.format(Locale.ROOT, "<Data: %d>", new Object[]{Integer.valueOf(totalBytes)}));
            }
        }

        public void writeFile(String key, ParcelFileDescriptor descriptor, String mimeType) throws IOException {
            if (mimeType == null) {
                mimeType = "content/unknown";
            }
            writeContentDisposition(key, key, mimeType);
            int totalBytes = 0;
            if (this.outputStream instanceof ProgressNoopOutputStream) {
                ((ProgressNoopOutputStream) this.outputStream).addProgress(descriptor.getStatSize());
            } else {
                totalBytes = 0 + Utility.copyAndCloseInputStream(new ParcelFileDescriptor.AutoCloseInputStream(descriptor), this.outputStream);
            }
            writeLine("", new Object[0]);
            writeRecordBoundary();
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + key, String.format(Locale.ROOT, "<Data: %d>", new Object[]{Integer.valueOf(totalBytes)}));
            }
        }

        public void writeRecordBoundary() throws IOException {
            if (!this.useUrlEncode) {
                writeLine("--%s", GraphRequest.MIME_BOUNDARY);
                return;
            }
            this.outputStream.write(HttpRequest.HTTP_REQ_ENTITY_JOIN.getBytes());
        }

        public void writeContentDisposition(String name, String filename, String contentType) throws IOException {
            if (!this.useUrlEncode) {
                write("Content-Disposition: form-data; name=\"%s\"", name);
                if (filename != null) {
                    write("; filename=\"%s\"", filename);
                }
                writeLine("", new Object[0]);
                if (contentType != null) {
                    writeLine("%s: %s", "Content-Type", contentType);
                }
                writeLine("", new Object[0]);
                return;
            }
            this.outputStream.write(String.format("%s=", new Object[]{name}).getBytes());
        }

        public void write(String format, Object... args) throws IOException {
            if (!this.useUrlEncode) {
                if (this.firstWrite) {
                    this.outputStream.write("--".getBytes());
                    this.outputStream.write(GraphRequest.MIME_BOUNDARY.getBytes());
                    this.outputStream.write("\r\n".getBytes());
                    this.firstWrite = false;
                }
                this.outputStream.write(String.format(format, args).getBytes());
                return;
            }
            this.outputStream.write(URLEncoder.encode(String.format(Locale.US, format, args), "UTF-8").getBytes());
        }

        public void writeLine(String format, Object... args) throws IOException {
            write(format, args);
            if (!this.useUrlEncode) {
                write("\r\n", new Object[0]);
            }
        }
    }

    public static class ParcelableResourceWithMimeType<RESOURCE extends Parcelable> implements Parcelable {
        public static final Parcelable.Creator<ParcelableResourceWithMimeType> CREATOR = new Parcelable.Creator<ParcelableResourceWithMimeType>() {
            public ParcelableResourceWithMimeType createFromParcel(Parcel in) {
                return new ParcelableResourceWithMimeType(in);
            }

            public ParcelableResourceWithMimeType[] newArray(int size) {
                return new ParcelableResourceWithMimeType[size];
            }
        };
        private final String mimeType;
        private final RESOURCE resource;

        public String getMimeType() {
            return this.mimeType;
        }

        public RESOURCE getResource() {
            return this.resource;
        }

        public int describeContents() {
            return 1;
        }

        public void writeToParcel(Parcel out, int flags) {
            out.writeString(this.mimeType);
            out.writeParcelable(this.resource, flags);
        }

        public ParcelableResourceWithMimeType(RESOURCE resource2, String mimeType2) {
            this.mimeType = mimeType2;
            this.resource = resource2;
        }

        private ParcelableResourceWithMimeType(Parcel in) {
            this.mimeType = in.readString();
            this.resource = in.readParcelable(FacebookSdk.getApplicationContext().getClassLoader());
        }
    }
}
