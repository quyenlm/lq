package com.unity3d.player;

import android.net.http.Headers;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.cert.CertPathValidatorException;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLKeyException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;

class UnityWebRequest implements Runnable {
    private long a;
    private String b;
    private String c;
    private Map d;
    private int e;
    private long f;

    static {
        if (CookieHandler.getDefault() == null) {
            CookieHandler.setDefault(new CookieManager());
        }
    }

    UnityWebRequest(long j, String str, Map map, String str2, int i) {
        this.a = j;
        this.b = str2;
        this.c = str;
        this.d = map;
        this.e = i;
    }

    private static native void contentLengthCallback(long j, int i);

    private static native boolean downloadCallback(long j, ByteBuffer byteBuffer, int i);

    private static native void errorCallback(long j, int i, String str);

    private boolean hasTimedOut() {
        return this.e > 0 && System.currentTimeMillis() - this.f >= ((long) this.e);
    }

    private static native void headerCallback(long j, String str, String str2);

    private static native void responseCodeCallback(long j, int i);

    private void runSafe() {
        InputStream inputStream;
        SSLSocketFactory a2;
        this.f = System.currentTimeMillis();
        try {
            URL url = new URL(this.b);
            URLConnection openConnection = url.openConnection();
            openConnection.setConnectTimeout(this.e);
            openConnection.setReadTimeout(this.e);
            if ((openConnection instanceof HttpsURLConnection) && (a2 = a.a()) != null) {
                ((HttpsURLConnection) openConnection).setSSLSocketFactory(a2);
            }
            if (!url.getProtocol().equalsIgnoreCase(TransferTable.COLUMN_FILE) || url.getHost().isEmpty()) {
                if (openConnection instanceof HttpURLConnection) {
                    try {
                        HttpURLConnection httpURLConnection = (HttpURLConnection) openConnection;
                        httpURLConnection.setRequestMethod(this.c);
                        httpURLConnection.setInstanceFollowRedirects(false);
                    } catch (ProtocolException e2) {
                        badProtocolCallback(e2.toString());
                        return;
                    }
                }
                if (this.d != null) {
                    for (Map.Entry entry : this.d.entrySet()) {
                        openConnection.addRequestProperty((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                ByteBuffer allocateDirect = ByteBuffer.allocateDirect(131072);
                if (uploadCallback((ByteBuffer) null) > 0) {
                    openConnection.setDoOutput(true);
                    try {
                        OutputStream outputStream = openConnection.getOutputStream();
                        int uploadCallback = uploadCallback(allocateDirect);
                        while (uploadCallback > 0) {
                            if (hasTimedOut()) {
                                outputStream.close();
                                errorCallback(this.a, 14, "WebRequest timed out.");
                                return;
                            }
                            outputStream.write(allocateDirect.array(), allocateDirect.arrayOffset(), uploadCallback);
                            uploadCallback = uploadCallback(allocateDirect);
                        }
                    } catch (Exception e3) {
                        errorCallback(e3.toString());
                        return;
                    }
                }
                if (openConnection instanceof HttpURLConnection) {
                    try {
                        responseCodeCallback(((HttpURLConnection) openConnection).getResponseCode());
                    } catch (UnknownHostException e4) {
                        unknownHostCallback(e4.toString());
                        return;
                    } catch (SSLException e5) {
                        sslCannotConnectCallback(e5);
                        return;
                    } catch (SocketTimeoutException e6) {
                        errorCallback(this.a, 14, e6.toString());
                        return;
                    } catch (IOException e7) {
                        errorCallback(e7.toString());
                        return;
                    }
                }
                Map<String, List<String>> headerFields = openConnection.getHeaderFields();
                headerCallback(headerFields);
                if ((headerFields == null || !headerFields.containsKey(Headers.CONTENT_LEN)) && openConnection.getContentLength() != -1) {
                    headerCallback(Headers.CONTENT_LEN, String.valueOf(openConnection.getContentLength()));
                }
                if ((headerFields == null || !headerFields.containsKey(Headers.CONTENT_TYPE)) && openConnection.getContentType() != null) {
                    headerCallback(Headers.CONTENT_TYPE, openConnection.getContentType());
                }
                contentLengthCallback(openConnection.getContentLength());
                try {
                    if (openConnection instanceof HttpURLConnection) {
                        HttpURLConnection httpURLConnection2 = (HttpURLConnection) openConnection;
                        responseCodeCallback(httpURLConnection2.getResponseCode());
                        inputStream = httpURLConnection2.getErrorStream();
                    } else {
                        inputStream = null;
                    }
                    if (inputStream == null) {
                        inputStream = openConnection.getInputStream();
                    }
                    ReadableByteChannel newChannel = Channels.newChannel(inputStream);
                    int read = newChannel.read(allocateDirect);
                    while (read != -1) {
                        if (!hasTimedOut()) {
                            if (!downloadCallback(allocateDirect, read)) {
                                break;
                            }
                            allocateDirect.clear();
                            read = newChannel.read(allocateDirect);
                        } else {
                            newChannel.close();
                            errorCallback(this.a, 14, "WebRequest timed out.");
                            return;
                        }
                    }
                    newChannel.close();
                } catch (UnknownHostException e8) {
                    unknownHostCallback(e8.toString());
                } catch (SSLException e9) {
                    sslCannotConnectCallback(e9);
                } catch (SocketTimeoutException e10) {
                    errorCallback(this.a, 14, e10.toString());
                } catch (IOException e11) {
                    errorCallback(this.a, 14, e11.toString());
                } catch (Exception e12) {
                    errorCallback(e12.toString());
                }
            } else {
                malformattedUrlCallback("file:// must use an absolute path");
            }
        } catch (MalformedURLException e13) {
            malformattedUrlCallback(e13.toString());
        } catch (IOException e14) {
            errorCallback(e14.toString());
        }
    }

    private static native int uploadCallback(long j, ByteBuffer byteBuffer);

    /* access modifiers changed from: protected */
    public void badProtocolCallback(String str) {
        errorCallback(this.a, 4, str);
    }

    /* access modifiers changed from: protected */
    public void contentLengthCallback(int i) {
        contentLengthCallback(this.a, i);
    }

    /* access modifiers changed from: protected */
    public boolean downloadCallback(ByteBuffer byteBuffer, int i) {
        return downloadCallback(this.a, byteBuffer, i);
    }

    /* access modifiers changed from: protected */
    public void errorCallback(String str) {
        errorCallback(this.a, 2, str);
    }

    /* access modifiers changed from: protected */
    public void headerCallback(String str, String str2) {
        headerCallback(this.a, str, str2);
    }

    /* access modifiers changed from: protected */
    public void headerCallback(Map map) {
        if (map != null && map.size() != 0) {
            for (Map.Entry entry : map.entrySet()) {
                String str = (String) entry.getKey();
                if (str == null) {
                    str = "Status";
                }
                for (String headerCallback : (List) entry.getValue()) {
                    headerCallback(str, headerCallback);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void malformattedUrlCallback(String str) {
        errorCallback(this.a, 5, str);
    }

    /* access modifiers changed from: protected */
    public void responseCodeCallback(int i) {
        responseCodeCallback(this.a, i);
    }

    public void run() {
        try {
            runSafe();
        } catch (Exception e2) {
            errorCallback(e2.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void sslCannotConnectCallback(SSLException sSLException) {
        String sSLException2 = sSLException.toString();
        int i = 16;
        Throwable th = sSLException;
        while (true) {
            if (th == null) {
                break;
            } else if (th instanceof SSLKeyException) {
                i = 23;
                break;
            } else if ((th instanceof SSLPeerUnverifiedException) || (th instanceof CertPathValidatorException)) {
                i = 25;
            } else {
                th = th.getCause();
            }
        }
        errorCallback(this.a, i, sSLException2);
    }

    /* access modifiers changed from: protected */
    public void unknownHostCallback(String str) {
        errorCallback(this.a, 7, str);
    }

    /* access modifiers changed from: protected */
    public int uploadCallback(ByteBuffer byteBuffer) {
        return uploadCallback(this.a, byteBuffer);
    }
}
