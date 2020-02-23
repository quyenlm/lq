package com.facebook;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.facebook.internal.Utility;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.List;

public class GraphRequestAsyncTask extends AsyncTask<Void, Void, List<GraphResponse>> {
    private static final String TAG = GraphRequestAsyncTask.class.getCanonicalName();
    private final HttpURLConnection connection;
    private Exception exception;
    private final GraphRequestBatch requests;

    public GraphRequestAsyncTask(GraphRequest... requests2) {
        this((HttpURLConnection) null, new GraphRequestBatch(requests2));
    }

    public GraphRequestAsyncTask(Collection<GraphRequest> requests2) {
        this((HttpURLConnection) null, new GraphRequestBatch(requests2));
    }

    public GraphRequestAsyncTask(GraphRequestBatch requests2) {
        this((HttpURLConnection) null, requests2);
    }

    public GraphRequestAsyncTask(HttpURLConnection connection2, GraphRequest... requests2) {
        this(connection2, new GraphRequestBatch(requests2));
    }

    public GraphRequestAsyncTask(HttpURLConnection connection2, Collection<GraphRequest> requests2) {
        this(connection2, new GraphRequestBatch(requests2));
    }

    public GraphRequestAsyncTask(HttpURLConnection connection2, GraphRequestBatch requests2) {
        this.requests = requests2;
        this.connection = connection2;
    }

    /* access modifiers changed from: protected */
    public final Exception getException() {
        return this.exception;
    }

    /* access modifiers changed from: protected */
    public final GraphRequestBatch getRequests() {
        return this.requests;
    }

    public String toString() {
        return "{RequestAsyncTask: " + " connection: " + this.connection + ", requests: " + this.requests + "}";
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        Handler handler;
        super.onPreExecute();
        if (FacebookSdk.isDebugEnabled()) {
            Utility.logd(TAG, String.format("execute async task: %s", new Object[]{this}));
        }
        if (this.requests.getCallbackHandler() == null) {
            if (Thread.currentThread() instanceof HandlerThread) {
                handler = new Handler();
            } else {
                handler = new Handler(Looper.getMainLooper());
            }
            this.requests.setCallbackHandler(handler);
        }
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(List<GraphResponse> result) {
        super.onPostExecute(result);
        if (this.exception != null) {
            Utility.logd(TAG, String.format("onPostExecute: exception encountered during request: %s", new Object[]{this.exception.getMessage()}));
        }
    }

    /* access modifiers changed from: protected */
    public List<GraphResponse> doInBackground(Void... params) {
        try {
            if (this.connection == null) {
                return this.requests.executeAndWait();
            }
            return GraphRequest.executeConnectionAndWait(this.connection, this.requests);
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }
}
