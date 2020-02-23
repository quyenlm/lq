package com.tencent.liteav.network;

import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.midas.oversea.network.http.APBaseHttpParam;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: TXCIntelligentRoute */
class c {
    public b a = null;
    public int b = 5;
    private final String c = "http://tcdns.myqcloud.com/queryip";
    private final String d = "forward_stream";
    private final String e = "forward_num";
    private final String f = "request_type";
    private final String g = "sdk_version";
    private Thread h = null;

    c() {
    }

    public void a(final String str, final int i) {
        this.h = new Thread("TXCPushRoute") {
            public void run() {
                if (c.this.a != null) {
                    ArrayList arrayList = new ArrayList();
                    int i = 0;
                    while (i < 5) {
                        try {
                            String a2 = c.this.b(str, i);
                            try {
                                JSONObject jSONObject = new JSONObject(a2);
                                if (jSONObject.has("use") && jSONObject.getInt("use") == 0) {
                                    break;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            arrayList = c.this.a(a2);
                            if (arrayList != null && arrayList.size() > 0) {
                                break;
                            }
                            sleep(1000, 0);
                            i++;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    c.this.a.onFetchDone(0, arrayList);
                }
            }
        };
        this.h.start();
    }

    /* access modifiers changed from: private */
    public String b(String str, int i) {
        StringBuffer stringBuffer = new StringBuffer("");
        try {
            InputStream c2 = c(str, i);
            if (c2 == null) {
                return "";
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(c2));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                stringBuffer.append(readLine);
            }
            return stringBuffer.toString();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private InputStream c(String str, int i) throws IOException {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("http://tcdns.myqcloud.com/queryip").openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("forward_stream", str);
            httpURLConnection.setRequestProperty("forward_num", "2");
            httpURLConnection.setRequestProperty("sdk_version", TXCCommonUtil.getSDKVersionStr());
            if (i == 1) {
                httpURLConnection.setRequestProperty("request_type", "1");
            } else if (i == 2) {
                httpURLConnection.setRequestProperty("request_type", "2");
            } else {
                httpURLConnection.setRequestProperty("request_type", "3");
            }
            if (this.b > 0) {
                httpURLConnection.setConnectTimeout(this.b * 1000);
                httpURLConnection.setReadTimeout(this.b * 1000);
            }
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                return httpURLConnection.getInputStream();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return null;
    }

    /* access modifiers changed from: private */
    public ArrayList<a> a(String str) {
        JSONArray jSONArray;
        ArrayList<a> arrayList = new ArrayList<>();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.getInt("state") != 0 || (jSONArray = jSONObject.getJSONObject("content").getJSONArray(HttpRequestParams.NOTICE_LIST)) == null) {
                return null;
            }
            for (int i = 0; i < jSONArray.length(); i++) {
                a a2 = a((JSONObject) jSONArray.opt(i));
                if (a2 != null && a2.c) {
                    arrayList.add(a2);
                }
            }
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                a a3 = a((JSONObject) jSONArray.opt(i2));
                if (a3 != null && !a3.c) {
                    arrayList.add(a3);
                }
            }
            Iterator<a> it = arrayList.iterator();
            while (it.hasNext()) {
                a next = it.next();
                TXCLog.e("TXCIntelligentRoute", "Nearest IP: " + next.a + " Port: " + next.b + " Q Channel: " + next.c);
            }
            return arrayList;
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    private a a(JSONObject jSONObject) {
        a aVar = new a();
        try {
            aVar.a = jSONObject.getString(APBaseHttpParam.IP_ACCESS);
            aVar.b = jSONObject.getString("port");
            aVar.d = 0;
            aVar.c = false;
            if (!jSONObject.has("type") || jSONObject.getInt("type") != 2) {
                return aVar;
            }
            aVar.c = true;
            return aVar;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
