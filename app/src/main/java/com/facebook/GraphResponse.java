package com.facebook;

import android.util.Log;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class GraphResponse {
    private static final String BODY_KEY = "body";
    private static final String CODE_KEY = "code";
    public static final String NON_JSON_RESPONSE_PROPERTY = "FACEBOOK_NON_JSON_RESULT";
    private static final String RESPONSE_LOG_TAG = "Response";
    public static final String SUCCESS_KEY = "success";
    private static final String TAG = GraphResponse.class.getSimpleName();
    private final HttpURLConnection connection;
    private final FacebookRequestError error;
    private final JSONObject graphObject;
    private final JSONArray graphObjectArray;
    private final String rawResponse;
    private final GraphRequest request;

    public enum PagingDirection {
        NEXT,
        PREVIOUS
    }

    GraphResponse(GraphRequest request2, HttpURLConnection connection2, String rawResponse2, JSONObject graphObject2) {
        this(request2, connection2, rawResponse2, graphObject2, (JSONArray) null, (FacebookRequestError) null);
    }

    GraphResponse(GraphRequest request2, HttpURLConnection connection2, String rawResponse2, JSONArray graphObjects) {
        this(request2, connection2, rawResponse2, (JSONObject) null, graphObjects, (FacebookRequestError) null);
    }

    GraphResponse(GraphRequest request2, HttpURLConnection connection2, FacebookRequestError error2) {
        this(request2, connection2, (String) null, (JSONObject) null, (JSONArray) null, error2);
    }

    GraphResponse(GraphRequest request2, HttpURLConnection connection2, String rawResponse2, JSONObject graphObject2, JSONArray graphObjects, FacebookRequestError error2) {
        this.request = request2;
        this.connection = connection2;
        this.rawResponse = rawResponse2;
        this.graphObject = graphObject2;
        this.graphObjectArray = graphObjects;
        this.error = error2;
    }

    public final FacebookRequestError getError() {
        return this.error;
    }

    public final JSONObject getJSONObject() {
        return this.graphObject;
    }

    public final JSONArray getJSONArray() {
        return this.graphObjectArray;
    }

    public final HttpURLConnection getConnection() {
        return this.connection;
    }

    public GraphRequest getRequest() {
        return this.request;
    }

    public String getRawResponse() {
        return this.rawResponse;
    }

    public GraphRequest getRequestForPagedResults(PagingDirection direction) {
        JSONObject pagingInfo;
        String link = null;
        if (!(this.graphObject == null || (pagingInfo = this.graphObject.optJSONObject("paging")) == null)) {
            link = direction == PagingDirection.NEXT ? pagingInfo.optString("next") : pagingInfo.optString("previous");
        }
        if (Utility.isNullOrEmpty(link)) {
            return null;
        }
        if (link != null && link.equals(this.request.getUrlForSingleRequest())) {
            return null;
        }
        try {
            return new GraphRequest(this.request.getAccessToken(), new URL(link));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public String toString() {
        String responseCode;
        try {
            Locale locale = Locale.US;
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(this.connection != null ? this.connection.getResponseCode() : 200);
            responseCode = String.format(locale, "%d", objArr);
        } catch (IOException e) {
            responseCode = "unknown";
        }
        return "{Response: " + " responseCode: " + responseCode + ", graphObject: " + this.graphObject + ", error: " + this.error + "}";
    }

    static List<GraphResponse> fromHttpConnection(HttpURLConnection connection2, GraphRequestBatch requests) {
        List<GraphResponse> constructErrorResponses;
        InputStream stream;
        InputStream stream2 = null;
        try {
            if (connection2.getResponseCode() >= 400) {
                stream = connection2.getErrorStream();
            } else {
                stream = connection2.getInputStream();
            }
            constructErrorResponses = createResponsesFromStream(stream2, connection2, requests);
        } catch (FacebookException facebookException) {
            Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response <Error>: %s", facebookException);
            constructErrorResponses = constructErrorResponses(requests, connection2, facebookException);
        } catch (Exception exception) {
            Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response <Error>: %s", exception);
            constructErrorResponses = constructErrorResponses(requests, connection2, new FacebookException((Throwable) exception));
        } finally {
            Utility.closeQuietly(stream2);
        }
        return constructErrorResponses;
    }

    static List<GraphResponse> createResponsesFromStream(InputStream stream, HttpURLConnection connection2, GraphRequestBatch requests) throws FacebookException, JSONException, IOException {
        String responseString = Utility.readStreamToString(stream);
        Logger.log(LoggingBehavior.INCLUDE_RAW_RESPONSES, RESPONSE_LOG_TAG, "Response (raw)\n  Size: %d\n  Response:\n%s\n", Integer.valueOf(responseString.length()), responseString);
        return createResponsesFromString(responseString, connection2, requests);
    }

    static List<GraphResponse> createResponsesFromString(String responseString, HttpURLConnection connection2, GraphRequestBatch requests) throws FacebookException, JSONException, IOException {
        List<GraphResponse> responses = createResponsesFromObject(connection2, requests, new JSONTokener(responseString).nextValue());
        Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response\n  Id: %s\n  Size: %d\n  Responses:\n%s\n", requests.getId(), Integer.valueOf(responseString.length()), responses);
        return responses;
    }

    private static List<GraphResponse> createResponsesFromObject(HttpURLConnection connection2, List<GraphRequest> requests, Object object) throws FacebookException, JSONException {
        int numRequests = requests.size();
        List<GraphResponse> responses = new ArrayList<>(numRequests);
        Object originalResult = object;
        if (numRequests == 1) {
            GraphRequest request2 = requests.get(0);
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(BODY_KEY, object);
                jsonObject.put("code", connection2 != null ? connection2.getResponseCode() : 200);
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(jsonObject);
                object = jsonArray;
            } catch (JSONException e) {
                responses.add(new GraphResponse(request2, connection2, new FacebookRequestError(connection2, (Exception) e)));
            } catch (IOException e2) {
                responses.add(new GraphResponse(request2, connection2, new FacebookRequestError(connection2, (Exception) e2)));
            }
        }
        if (!(object instanceof JSONArray) || ((JSONArray) object).length() != numRequests) {
            throw new FacebookException("Unexpected number of results");
        }
        JSONArray jsonArray2 = (JSONArray) object;
        for (int i = 0; i < jsonArray2.length(); i++) {
            GraphRequest request3 = requests.get(i);
            try {
                responses.add(createResponseFromObject(request3, connection2, jsonArray2.get(i), originalResult));
            } catch (JSONException e3) {
                responses.add(new GraphResponse(request3, connection2, new FacebookRequestError(connection2, (Exception) e3)));
            } catch (FacebookException e4) {
                responses.add(new GraphResponse(request3, connection2, new FacebookRequestError(connection2, (Exception) e4)));
            }
        }
        return responses;
    }

    private static GraphResponse createResponseFromObject(GraphRequest request2, HttpURLConnection connection2, Object object, Object originalResult) throws JSONException {
        if (object instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) object;
            FacebookRequestError error2 = FacebookRequestError.checkResponseAndCreateError(jsonObject, originalResult, connection2);
            if (error2 != null) {
                Log.e(TAG, error2.toString());
                if (error2.getErrorCode() == 190 && Utility.isCurrentAccessToken(request2.getAccessToken())) {
                    if (error2.getSubErrorCode() != 493) {
                        AccessToken.setCurrentAccessToken((AccessToken) null);
                    } else if (!AccessToken.getCurrentAccessToken().isExpired()) {
                        AccessToken.expireCurrentAccessToken();
                    }
                }
                return new GraphResponse(request2, connection2, error2);
            }
            Object body = Utility.getStringPropertyAsJSON(jsonObject, BODY_KEY, NON_JSON_RESPONSE_PROPERTY);
            if (body instanceof JSONObject) {
                return new GraphResponse(request2, connection2, body.toString(), (JSONObject) body);
            }
            if (body instanceof JSONArray) {
                return new GraphResponse(request2, connection2, body.toString(), (JSONArray) body);
            }
            object = JSONObject.NULL;
        }
        if (object == JSONObject.NULL) {
            return new GraphResponse(request2, connection2, object.toString(), (JSONObject) null);
        }
        throw new FacebookException("Got unexpected object type in response, class: " + object.getClass().getSimpleName());
    }

    static List<GraphResponse> constructErrorResponses(List<GraphRequest> requests, HttpURLConnection connection2, FacebookException error2) {
        int count = requests.size();
        List<GraphResponse> responses = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            responses.add(new GraphResponse(requests.get(i), connection2, new FacebookRequestError(connection2, (Exception) error2)));
        }
        return responses;
    }
}
