package com.tencent.liteav.network;

import android.content.Context;
import android.os.Handler;
import com.appsflyer.share.Constants;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.mna.KartinRet;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Vector;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/* compiled from: TXRTMPAccUrlFetcher */
public class f {
    /* access modifiers changed from: private */
    public String a = "";
    /* access modifiers changed from: private */
    public String b = "";
    /* access modifiers changed from: private */
    public int c = 0;
    /* access modifiers changed from: private */
    public String d = "";
    /* access modifiers changed from: private */
    public Handler e;

    /* compiled from: TXRTMPAccUrlFetcher */
    public interface a {
        void a(int i, String str, Vector<d> vector);
    }

    public f(Context context) {
        if (context != null) {
            this.e = new Handler(context.getMainLooper());
        }
    }

    public String a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public int c() {
        return this.c;
    }

    public String d() {
        return this.d;
    }

    public int a(String str, int i, a aVar) {
        this.a = "";
        this.b = "";
        this.c = 0;
        this.d = "";
        if (str == null || str.isEmpty()) {
            return -1;
        }
        final String a2 = a(str);
        if (a2 == null || a2.isEmpty()) {
            return -2;
        }
        final String a3 = a("bizid", str);
        final String a4 = a("txSecret", str);
        final String a5 = a("txTime", str);
        if (a3 == null || a3.isEmpty() || a4 == null || a4.isEmpty() || a5 == null || a5.isEmpty()) {
            return -3;
        }
        final a aVar2 = aVar;
        a(a2, a3, a4, a5, i, new a() {
            public void a(int i, String str, Vector<d> vector) {
                String unused = f.this.a = a2;
                String unused2 = f.this.b = a3;
                int unused3 = f.this.c = i;
                String unused4 = f.this.d = str;
                if (vector != null && !vector.isEmpty()) {
                    Vector vector2 = new Vector();
                    Iterator<d> it = vector.iterator();
                    while (it.hasNext()) {
                        d next = it.next();
                        String str2 = next.a;
                        if (str2.indexOf("?") != -1) {
                            str2 = str2.substring(0, str2.indexOf("?"));
                        }
                        vector2.add(new d(str2 + "?txSecret=" + a4 + "&txTime=" + a5 + "&bizid=" + a3, next.b));
                    }
                    if (aVar2 != null) {
                        Iterator it2 = vector2.iterator();
                        while (it2.hasNext()) {
                            d dVar = (d) it2.next();
                            TXCLog.e("TXRTMPAccUrlFetcher", "accurl = " + dVar.a + " quic = " + dVar.b);
                        }
                        aVar2.a(i, str, vector2);
                    }
                } else if (aVar2 != null) {
                    aVar2.a(i, str, (Vector<d>) null);
                }
            }
        });
        return 0;
    }

    private void a(String str, String str2, String str3, String str4, int i, a aVar) {
        final String str5 = str2;
        final String str6 = str;
        final String str7 = str3;
        final String str8 = str4;
        final int i2 = i;
        final a aVar2 = aVar;
        new Thread("getRTMPAccUrl") {
            public void run() {
                boolean z;
                final int i = -1;
                final String str = "";
                for (int i2 = 5; i2 >= 1; i2--) {
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("bizid", Integer.valueOf(str5).intValue());
                        jSONObject.put("stream_id", str6);
                        jSONObject.put("txSecret", str7);
                        jSONObject.put("txTime", str8);
                        jSONObject.put("type", 1);
                        String jSONObject2 = jSONObject.toString();
                        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL("https://livepull.myqcloud.com/getpulladdr").openConnection();
                        httpsURLConnection.setDoOutput(true);
                        httpsURLConnection.setDoInput(true);
                        httpsURLConnection.setUseCaches(false);
                        httpsURLConnection.setConnectTimeout(5000);
                        httpsURLConnection.setReadTimeout(5000);
                        httpsURLConnection.setRequestMethod("POST");
                        httpsURLConnection.setRequestProperty("Charsert", "UTF-8");
                        httpsURLConnection.setRequestProperty("Content-Type", "text/plain;");
                        httpsURLConnection.setRequestProperty("Content-Length", String.valueOf(jSONObject2.length()));
                        TXCLog.e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl: sendHttpRequest[ " + jSONObject2 + "] retryIndex = " + i2);
                        new DataOutputStream(httpsURLConnection.getOutputStream()).write(jSONObject2.getBytes());
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                        String str2 = "";
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            str2 = str2 + readLine;
                        }
                        TXCLog.e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl: receive response, strResponse = " + str2);
                        JSONObject jSONObject3 = (JSONObject) new JSONTokener(str2).nextValue();
                        if (jSONObject3.has("code")) {
                            i = jSONObject3.getInt("code");
                        }
                        if (i != 0) {
                            if (jSONObject3.has("message")) {
                                str = jSONObject3.getString("message");
                            }
                            TXCLog.e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl: errorCode = " + i + " errorMessage = " + str);
                        }
                        final Vector vector = new Vector();
                        final Vector vector2 = new Vector();
                        JSONArray jSONArray = jSONObject3.getJSONArray("pull_addr");
                        if (jSONArray == null || jSONArray.length() == 0) {
                            TXCLog.e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl: no pull_addr");
                        } else {
                            for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                                JSONObject jSONObject4 = (JSONObject) jSONArray.get(i3);
                                if (jSONObject4 != null) {
                                    String string = jSONObject4.getString("rtmp_url");
                                    if (jSONObject4.getInt("proto") == 1) {
                                        z = true;
                                    } else {
                                        z = false;
                                    }
                                    TXCLog.e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl: streamUrl = " + string + " Q channel = " + z);
                                    String d2 = f.this.a(string);
                                    if (d2 != null && d2.equalsIgnoreCase(str6)) {
                                        if (z) {
                                            vector.add(new d(string, z));
                                        } else {
                                            vector2.add(new d(string, z));
                                        }
                                    }
                                }
                            }
                        }
                        if (i2 == 1) {
                            if (vector2.size() > 0) {
                                f.this.e.post(new Runnable() {
                                    public void run() {
                                        if (aVar2 != null) {
                                            aVar2.a(0, KartinRet.KARTIN_REASON_NORMAL_ENGLISH, vector2);
                                        }
                                    }
                                });
                                return;
                            }
                        } else if (i2 != 2) {
                            Iterator it = vector2.iterator();
                            while (it.hasNext()) {
                                vector.add((d) it.next());
                            }
                            if (vector.size() > 0) {
                                f.this.e.post(new Runnable() {
                                    public void run() {
                                        if (aVar2 != null) {
                                            aVar2.a(0, KartinRet.KARTIN_REASON_NORMAL_ENGLISH, vector);
                                        }
                                    }
                                });
                                return;
                            }
                        } else if (vector.size() > 0) {
                            f.this.e.post(new Runnable() {
                                public void run() {
                                    if (aVar2 != null) {
                                        aVar2.a(0, KartinRet.KARTIN_REASON_NORMAL_ENGLISH, vector);
                                    }
                                }
                            });
                            return;
                        }
                    } catch (Exception e2) {
                        TXCLog.e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl exception");
                        e2.printStackTrace();
                    }
                    try {
                        sleep(1000, 0);
                    } catch (Exception e3) {
                        TXCLog.e("TXRTMPAccUrlFetcher", "getAccelerateStreamPlayUrl exception sleep");
                        e3.printStackTrace();
                    }
                }
                f.this.e.post(new Runnable() {
                    public void run() {
                        if (aVar2 != null) {
                            aVar2.a(i, str, (Vector<d>) null);
                        }
                    }
                });
            }
        }.start();
    }

    /* access modifiers changed from: private */
    public String a(String str) {
        String str2;
        if (str == null || str.length() == 0) {
            return null;
        }
        int indexOf = str.indexOf("?");
        if (indexOf != -1) {
            str2 = str.substring(0, indexOf);
        } else {
            str2 = str;
        }
        if (str2 == null || str2.length() == 0) {
            return null;
        }
        int lastIndexOf = str2.lastIndexOf(Constants.URL_PATH_DELIMITER);
        if (lastIndexOf != -1) {
            str2 = str2.substring(lastIndexOf + 1);
        }
        if (str2 == null || str2.length() == 0) {
            return null;
        }
        int indexOf2 = str2.indexOf(".");
        if (indexOf2 != -1) {
            str2 = str2.substring(0, indexOf2);
        }
        if (str2 == null || str2.length() == 0) {
            return null;
        }
        return str2;
    }

    private String a(String str, String str2) {
        if (str == null || str.length() == 0 || str2 == null || str2.length() == 0) {
            return null;
        }
        String lowerCase = str.toLowerCase();
        for (String str3 : str2.split("[?&]")) {
            if (str3.indexOf(HttpRequest.HTTP_REQ_ENTITY_MERGE) != -1) {
                String[] split = str3.split("[=]");
                if (split.length == 2) {
                    String str4 = split[0];
                    String str5 = split[1];
                    if (str4 != null && str4.toLowerCase().equalsIgnoreCase(lowerCase)) {
                        return str5;
                    }
                } else {
                    continue;
                }
            }
        }
        return null;
    }
}
