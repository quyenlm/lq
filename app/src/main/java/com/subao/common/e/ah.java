package com.subao.common.e;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.subao.common.d;
import com.subao.common.e.e;
import com.subao.common.e.n;
import com.subao.common.n.f;
import com.tencent.midas.oversea.network.http.APBaseHttpParam;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ServiceConfig */
public class ah extends m {
    boolean a;
    Integer b;
    e.a c;
    String d;
    ai e;
    ai f;
    ai g;
    ai h;
    String i;
    String j;
    Integer k;
    Integer l;
    String m;
    private boolean n;
    @Nullable
    private String o;

    /* access modifiers changed from: protected */
    public void a(@NonNull byte[] bArr) {
        try {
            String str = new String(bArr, "UTF-8");
            a((Reader) new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bArr)), 2048));
            this.o = str;
            this.n = true;
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String a() {
        return "com.subao.gamemaster.service.config";
    }

    /* access modifiers changed from: protected */
    public InputStream b(@NonNull Context context, n.a aVar) {
        File c2 = c(context, aVar);
        if (c2 != null) {
            try {
                return new FileInputStream(c2);
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
        }
        return super.b(context, aVar);
    }

    private File c(@NonNull Context context, n.a aVar) {
        File a2 = a(context);
        if (!a2.exists() || !a2.isDirectory()) {
            return null;
        }
        File file = new File(a2, c(aVar));
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        return file;
    }

    private String c(n.a aVar) {
        return String.format("%s.%s", new Object[]{a(), a(aVar)});
    }

    static File a(@NonNull Context context) {
        return context.getFilesDir();
    }

    public static int a(int i2) {
        if (i2 < 1) {
            return 1;
        }
        if (i2 > 5) {
            return 5;
        }
        return i2;
    }

    /* access modifiers changed from: package-private */
    public void a(Reader reader) {
        JsonReader jsonReader = new JsonReader(reader);
        try {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String nextName = jsonReader.nextName();
                if ("init".equals(nextName)) {
                    this.a = "fail".equals(f.a(jsonReader));
                } else if ("url_h5".equals(nextName)) {
                    this.d = f.a(jsonReader);
                } else if ("accel_recommend".equals(nextName)) {
                    this.k = Integer.valueOf(jsonReader.nextInt());
                } else if ("nodes_info".equals(nextName)) {
                    if (this.c == null) {
                        this.c = a(f.a(jsonReader));
                    }
                } else if ("nodes_info_v2".equals(nextName)) {
                    this.c = a(jsonReader);
                } else if ("log_level".equals(nextName)) {
                    this.b = Integer.valueOf(a(jsonReader.nextInt()));
                } else if ("url_portal".equals(nextName)) {
                    this.e = ai.a(f.a(jsonReader));
                } else if ("url_auth".equals(nextName)) {
                    this.f = ai.a(f.a(jsonReader));
                } else if ("url_hr".equals(nextName)) {
                    this.h = ai.a(f.a(jsonReader));
                } else if ("url_ticket".equals(nextName)) {
                    this.i = f.a(jsonReader);
                } else if ("url_lashou".equals(nextName)) {
                    this.j = f.a(jsonReader);
                } else if ("url_message".equals(nextName)) {
                    this.g = ai.a(f.a(jsonReader));
                } else if ("data_refresh_interval".equals(nextName)) {
                    this.l = Integer.valueOf(jsonReader.nextInt());
                } else if ("url_bonus".equals(nextName)) {
                    this.m = f.a(jsonReader);
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
        } finally {
            com.subao.common.e.a((Closeable) jsonReader);
        }
    }

    private e.a a(String str) {
        if (TextUtils.isEmpty(str)) {
            return new e.a(0, (String) null);
        }
        String[] split = str.split(",");
        JSONArray jSONArray = new JSONArray();
        for (String b2 : split) {
            JSONObject b3 = b(b2);
            if (b3 != null) {
                jSONArray.put(b3);
            }
        }
        return new e.a(split.length, jSONArray.toString());
    }

    private e.a a(JsonReader jsonReader) {
        StringBuilder sb = new StringBuilder(4096);
        sb.append('[');
        jsonReader.beginArray();
        int i2 = 0;
        while (jsonReader.hasNext()) {
            if (i2 > 0) {
                sb.append(',');
            }
            sb.append('{');
            jsonReader.beginObject();
            int i3 = 0;
            while (jsonReader.hasNext()) {
                if (i3 > 0) {
                    sb.append(',');
                }
                i3++;
                f.a(sb, jsonReader.nextName()).append(':');
                switch (AnonymousClass1.a[jsonReader.peek().ordinal()]) {
                    case 1:
                        f.a(sb, jsonReader.nextString());
                        break;
                    case 2:
                        jsonReader.nextNull();
                        f.a(sb, (String) null);
                        break;
                    case 3:
                        sb.append(jsonReader.nextBoolean() ? "true" : "false");
                        break;
                    case 4:
                        sb.append(jsonReader.nextLong());
                        break;
                    default:
                        jsonReader.skipValue();
                        Log.w("SubaoData", "Unknown field type in NodeInfoV2");
                        break;
                }
            }
            jsonReader.endObject();
            sb.append('}');
            i2++;
        }
        jsonReader.endArray();
        sb.append(']');
        return new e.a(i2, sb.toString());
    }

    /* renamed from: com.subao.common.e.ah$1  reason: invalid class name */
    /* compiled from: ServiceConfig */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[JsonToken.values().length];

        static {
            try {
                a[JsonToken.STRING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[JsonToken.NULL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[JsonToken.BOOLEAN.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[JsonToken.NUMBER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private JSONObject b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        String[] split = str.split(":");
        if (split.length < 4) {
            d.a("SubaoData", "ServiceConfig, transNodeInfoToJson info format error");
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            jSONObject.put(APBaseHttpParam.IP_ACCESS, split[0]);
            jSONObject.put("bitFlag", Integer.valueOf(split[1]));
            jSONObject.put(GGLiveConstants.PARAM.REGION, split[2]);
            for (int i2 = 3; i2 < split.length; i2++) {
                sb.append(split[i2]);
                if (i2 < split.length - 1) {
                    sb.append(",");
                }
            }
            jSONObject.put("isp", sb.toString());
            return jSONObject;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public boolean b() {
        return this.a;
    }

    @Nullable
    public ai c() {
        return this.e;
    }

    @Nullable
    public e.a d() {
        return this.c;
    }

    @Nullable
    public String e() {
        return this.d;
    }

    @Nullable
    public Integer f() {
        return this.k;
    }

    @Nullable
    public Integer g() {
        return this.l;
    }

    @Nullable
    public ai h() {
        return this.h;
    }

    @Nullable
    public ai i() {
        return this.f;
    }

    @Nullable
    public ai j() {
        return this.g;
    }
}
