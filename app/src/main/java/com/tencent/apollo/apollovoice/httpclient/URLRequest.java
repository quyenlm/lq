package com.tencent.apollo.apollovoice.httpclient;

import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class URLRequest {
    private final int AV_HTTP_STATUS_FAIL = 1;
    private final int AV_HTTP_STATUS_GET_CREATEFILE = 8;
    private final int AV_HTTP_STATUS_GET_WRITEFILE = 9;
    private final int AV_HTTP_STATUS_INVALIED_HOST = 3;
    private final int AV_HTTP_STATUS_INVALIED_URL = 4;
    private final int AV_HTTP_STATUS_NOHEADERS = 5;
    private final int AV_HTTP_STATUS_POST_READFILE = 10;
    private final int AV_HTTP_STATUS_READBODY = 6;
    private final int AV_HTTP_STATUS_SEND_INCOMPLETE = 7;
    private final int AV_HTTP_STATUS_SUCC = 0;
    private final int AV_HTTP_STATUS_TIMEOUT = 2;
    /* access modifiers changed from: private */
    public byte[] body;
    private long delegate;
    private String getFilePath;
    /* access modifiers changed from: private */
    public String method;
    private String postFilePath;
    private URL reqConnURL;
    /* access modifiers changed from: private */
    public URLResponse response;
    private int timeout;
    /* access modifiers changed from: private */
    public HttpURLConnection urlConn;

    public static native void response(int i, long j, int i2, String str, String str2, String str3, byte[] bArr, String[] strArr);

    public int initWithURL(String reqURL, int timeout2) {
        this.response = new URLResponse();
        this.response.URL = reqURL;
        this.method = "GET";
        this.timeout = timeout2;
        try {
            this.reqConnURL = new URL(this.response.URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (this.reqConnURL == null) {
            Log.e("ApolloVoice", "reqConnURL");
            return -1;
        }
        try {
            this.urlConn = (HttpURLConnection) this.reqConnURL.openConnection();
            Log.i("ApolloVoice", "After open Connection With URL:" + reqURL);
            if (this.urlConn == null) {
                Log.i("cz", "urlConn == null");
                return -1;
            }
            try {
                this.urlConn.setRequestMethod(this.method);
                this.urlConn.setReadTimeout(timeout2);
                if (this.method == "POST") {
                    this.urlConn.setDoOutput(true);
                    this.urlConn.setUseCaches(false);
                }
                this.urlConn.setConnectTimeout(timeout2);
                return 0;
            } catch (ProtocolException e2) {
                e2.printStackTrace();
                return -1;
            }
        } catch (IOException e3) {
            e3.printStackTrace();
            return -1;
        }
    }

    public void getFile(String filepath) {
        setMethod("GET");
        sendRequest(filepath);
    }

    public void postFile(String filepath) {
        setMethod("POST");
        sendRequest(filepath);
    }

    public void sendRequest() {
        this.method = "GET";
        sendRequest("");
    }

    public void sendRequest(String filepath) {
        new Thread(new RequestTask(filepath)).start();
    }

    public void response2cpp(int result) {
        Log.i("ApolloVoice", "url[" + this.response.URL + "]response2cpp with result :" + result);
        if (result != 0) {
            response(result, this.delegate, 0, "", this.response.URL, "", (byte[]) null, (String[]) null);
            return;
        }
        ArrayList<String> headers = new ArrayList<>();
        for (Map.Entry entry : this.response.headers.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (!(value == null || key == null)) {
                headers.add(key);
                headers.add(value);
            }
        }
        response(result, this.delegate, this.response.status, this.response.statusMsg, this.response.URL, this.response.version, this.response.body, (String[]) headers.toArray(new String[0]));
    }

    public void setMethod(String method2) {
        this.method = method2;
        try {
            this.urlConn.setRequestMethod(method2);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    public void setDelegate(long delegate2) {
        this.delegate = delegate2;
    }

    public void addHead(String key, String value) {
        this.urlConn.setRequestProperty(key, value);
    }

    public void setBody(byte[] body2) {
        this.body = body2;
    }

    class RequestTask implements Runnable {
        private String filepath;

        public RequestTask(String filepath2) {
            this.filepath = filepath2;
        }

        public void run() {
            FileOutputStream outputStream = null;
            FileInputStream inputStream = null;
            if (this.filepath != "" && URLRequest.this.method == "GET") {
                File fd = new File(this.filepath);
                try {
                    fd.createNewFile();
                    outputStream = new FileOutputStream(fd);
                } catch (IOException e) {
                    Log.e("ApolloVoice", "Get File With Create File Error");
                    e.printStackTrace();
                    URLRequest.this.response2cpp(8);
                    return;
                }
            }
            if (this.filepath != "" && URLRequest.this.method == "POST") {
                try {
                    inputStream = new FileInputStream(this.filepath);
                } catch (FileNotFoundException e2) {
                    Log.e("ApolloVoice", "Post File With Open File Error");
                    e2.printStackTrace();
                    URLRequest.this.response2cpp(10);
                    return;
                }
            }
            try {
                if (URLRequest.this.body != null) {
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(URLRequest.this.urlConn.getOutputStream());
                    bufferedOutputStream.write(URLRequest.this.body);
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                } else if (this.filepath != "" && URLRequest.this.method == "POST" && inputStream != null) {
                    byte[] buf = new byte[1024];
                    BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(URLRequest.this.urlConn.getOutputStream());
                    while (true) {
                        int len = inputStream.read(buf);
                        if (len == -1) {
                            break;
                        }
                        bufferedOutputStream2.write(buf, 0, len);
                        bufferedOutputStream2.flush();
                    }
                    bufferedOutputStream2.close();
                } else if (URLRequest.this.urlConn == null) {
                    Log.e("ApolloVice", "urlConn is null");
                    return;
                } else {
                    URLRequest.this.urlConn.connect();
                }
                Map<String, List<String>> headerFields = URLRequest.this.urlConn.getHeaderFields();
                if (headerFields == null || headerFields.entrySet() == null) {
                    Log.e("ApolloVoice", "headerFields == null || headerFields.entrySet() == null");
                    URLRequest.this.response2cpp(5);
                    return;
                }
                for (Map.Entry entry : headerFields.entrySet()) {
                    String key = (String) entry.getKey();
                    List<String> value = (List) entry.getValue();
                    String header = "";
                    if (value != null) {
                        for (String v : value) {
                            header = header + v;
                        }
                    }
                    if (key == null) {
                        URLRequest.this.response.version = header.split("\\ ")[0];
                    } else {
                        URLRequest.this.response.headers.put(key, header);
                    }
                }
                try {
                    URLRequest.this.response.status = URLRequest.this.urlConn.getResponseCode();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    URLRequest.this.response.status = 0;
                }
                try {
                    URLRequest.this.response.statusMsg = URLRequest.this.urlConn.getResponseMessage();
                } catch (IOException e12) {
                    e12.printStackTrace();
                    URLRequest.this.response.statusMsg = "No Status Message!";
                }
                try {
                    InputStream in = new BufferedInputStream(URLRequest.this.urlConn.getInputStream());
                    ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                    byte[] buf2 = new byte[1024];
                    while (true) {
                        try {
                            int len2 = in.read(buf2);
                            if (len2 == -1) {
                                try {
                                    break;
                                } catch (IOException e3) {
                                    Log.e("ApolloVoice", "ByteArrayOutputStream Exception");
                                    return;
                                }
                            } else if (outputStream == null) {
                                byteArrayOut.write(buf2, 0, len2);
                            } else if (outputStream != null) {
                                outputStream.write(buf2, 0, len2);
                            }
                        } catch (IOException e4) {
                            e4.printStackTrace();
                            URLRequest.this.response2cpp(6);
                            return;
                        }
                    }
                    byteArrayOut.flush();
                    byteArrayOut.close();
                    URLRequest.this.response.body = byteArrayOut.toByteArray();
                    Log.i("ApolloVoice", "Java body size is " + URLRequest.this.response.body.length);
                    if (!(this.filepath == "" || URLRequest.this.method != "GET" || outputStream == null)) {
                        try {
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    URLRequest.this.response2cpp(0);
                } catch (FileNotFoundException e6) {
                    if (URLRequest.this.response.status == 404) {
                        URLRequest.this.response.body = "404".getBytes();
                        URLRequest.this.response2cpp(0);
                        return;
                    }
                    Log.e("ApolloVoice", "response status = " + URLRequest.this.response.status + " " + URLRequest.this.response.statusMsg);
                    Log.e("ApolloVoice", "" + URLRequest.this.urlConn.getErrorStream());
                    e6.printStackTrace();
                    URLRequest.this.response2cpp(1);
                } catch (IOException e13) {
                    e13.printStackTrace();
                    URLRequest.this.response2cpp(1);
                }
            } catch (UnknownHostException e7) {
                Log.i("ApolloVoice", "UnknownHost");
                URLRequest.this.response2cpp(3);
            } catch (SocketTimeoutException e8) {
                Log.i("ApolloVoice", "SocketTimeoutException");
                URLRequest.this.response2cpp(2);
            } catch (IOException e9) {
                e9.printStackTrace();
                URLRequest.this.response2cpp(1);
            }
        }
    }
}
