package com.garena.network;

import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.networking.HttpFactory;
import com.beetalk.sdk.networking.UILoop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.concurrent.Future;
import org.json.JSONObject;

public class AsyncHttpClient {
    private static AsyncHttpClient asyncHttpClient;

    public interface BaseRequestCallback<T> extends ResultCallback<AsyncHttpResponse, T> {
    }

    public interface JSONObjectCallback extends BaseRequestCallback<JSONObject> {
    }

    private AsyncHttpClient() {
    }

    public static AsyncHttpClient getInstance() {
        if (asyncHttpClient == null) {
            asyncHttpClient = new AsyncHttpClient();
        }
        return asyncHttpClient;
    }

    public Future<JSONObject> getJSONObject(AsyncNetworkRequest req, JSONObjectCallback callback) {
        return getOrPost(req, new JSONObjectParser(), callback);
    }

    private <T> Future<T> getOrPost(final AsyncNetworkRequest request, final Parser<AsyncHttpResponse, T> parser, final BaseRequestCallback<T> callback) {
        return AsyncNetworkLoop.getInstance().delayPost(new Runnable() {
            public void run() {
                String s;
                try {
                    if (request.getRequestMethod().equals("GET")) {
                        s = makeGetRequest();
                    } else {
                        s = makePostRequest();
                    }
                    final String finalStringResponse = s;
                    final AsyncHttpResponse source = new AsyncHttpResponse() {
                        public String getResponse() {
                            return finalStringResponse;
                        }
                    };
                    final T parsedObject = parser.parse(source);
                    UILoop.getInstance().post(new Runnable() {
                        public void run() {
                            callback.onCompleted((Exception) null, source, parsedObject);
                        }
                    });
                } catch (Exception e) {
                    UILoop.getInstance().post(new Runnable() {
                        public void run() {
                            callback.onCompleted(e, null, null);
                        }
                    });
                }
            }

            private String makePostRequest() {
                try {
                    String urlString = request.getUri().toExternalForm().trim();
                    HttpURLConnection conn = HttpFactory.newConnection(urlString);
                    if (conn == null) {
                        return "";
                    }
                    conn.setRequestMethod("POST");
                    if (request.getHeaders().size() > 0) {
                        for (String key : request.getHeaders().keySet()) {
                            conn.addRequestProperty(key, request.getHeaders().get(key));
                        }
                    }
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setDoOutput(true);
                    BBLogger.d("Making POST Request for url:" + urlString + "and params" + request.getHttpParams().toString(), new Object[0]);
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(request.getQuery());
                    writer.flush();
                    writer.close();
                    os.close();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    BBLogger.d("Request Code " + conn.getResponseCode(), new Object[0]);
                    BufferedReader r = new BufferedReader(new InputStreamReader(is));
                    StringBuilder total = new StringBuilder();
                    while (true) {
                        String line = r.readLine();
                        if (line != null) {
                            total.append(line);
                        } else {
                            r.close();
                            conn.disconnect();
                            return total.toString();
                        }
                    }
                } catch (IOException e) {
                    BBLogger.e(e);
                    return "";
                }
            }

            private String makeGetRequest() {
                try {
                    String urlString = request.getUri().toExternalForm().trim() + "?" + request.getQuery();
                    BBLogger.i("[URL]" + urlString, new Object[0]);
                    HttpURLConnection conn = HttpFactory.newConnection(urlString);
                    if (conn == null) {
                        return "";
                    }
                    if (request.getHeaders().size() > 0) {
                        for (String key : request.getHeaders().keySet()) {
                            conn.addRequestProperty(key, request.getHeaders().get(key));
                        }
                    }
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    BBLogger.i("response code %d", Integer.valueOf(conn.getResponseCode()));
                    BufferedReader r = new BufferedReader(new InputStreamReader(is));
                    StringBuilder total = new StringBuilder();
                    while (true) {
                        String line = r.readLine();
                        if (line != null) {
                            total.append(line);
                        } else {
                            r.close();
                            conn.disconnect();
                            return total.toString();
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    BBLogger.e(e);
                } catch (ProtocolException e2) {
                    BBLogger.e(e2);
                } catch (MalformedURLException e3) {
                    BBLogger.e(e3);
                } catch (IOException e4) {
                    BBLogger.e(e4);
                }
                return "";
            }
        }, 0);
    }
}
