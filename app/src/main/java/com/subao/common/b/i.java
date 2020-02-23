package com.subao.common.b;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import com.subao.common.d;
import com.subao.common.e;
import com.subao.common.e.ai;
import com.subao.common.e.n;
import com.subao.common.intf.QueryThirdPartyAuthInfoCallback;
import com.subao.common.intf.ThirdPartyAuthInfo;
import com.subao.common.intf.UserInfo;
import com.subao.common.j.a;
import com.subao.common.n.f;
import com.tencent.midas.oversea.api.UnityPayHelper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

/* compiled from: ThirdPartyAuthInfoRequester */
public class i implements Runnable {
    @NonNull
    private final String a;
    @NonNull
    private final ai b;
    @NonNull
    private final String c;
    private final int d;
    @NonNull
    private final UserInfo e;
    @NonNull
    private final QueryThirdPartyAuthInfoCallback f;

    public i(@NonNull String str, @Nullable ai aiVar, @NonNull String str2, @NonNull UserInfo userInfo, int i, @NonNull QueryThirdPartyAuthInfoCallback queryThirdPartyAuthInfoCallback) {
        this.a = str;
        this.b = aiVar == null ? n.d : aiVar;
        this.c = str2;
        this.d = i;
        this.e = userInfo;
        this.f = queryThirdPartyAuthInfoCallback;
    }

    @WorkerThread
    public void run() {
        try {
            a(a.a(new a(this.d, this.d).a(a(), a.b.POST, a.C0013a.JSON.e), b()));
        } catch (IOException | RuntimeException e2) {
            e2.printStackTrace();
            a((a.c) null);
        }
    }

    private void a(@Nullable a.c cVar) {
        ThirdPartyAuthInfo thirdPartyAuthInfo;
        int i = 1008;
        if (cVar == null) {
            i = 1006;
            thirdPartyAuthInfo = null;
        } else if (cVar.a == 201) {
            try {
                thirdPartyAuthInfo = a(cVar.b);
                i = 0;
            } catch (IOException | RuntimeException e2) {
                e2.printStackTrace();
                thirdPartyAuthInfo = null;
            }
        } else {
            thirdPartyAuthInfo = null;
        }
        if (d.a("SubaoAuth")) {
            a(thirdPartyAuthInfo);
        }
        this.f.onThirdPartyAuthInfoResult(i, thirdPartyAuthInfo);
    }

    private static void a(ThirdPartyAuthInfo thirdPartyAuthInfo) {
        if (thirdPartyAuthInfo == null) {
            Log.d("SubaoAuth", "Third party auth info is null");
            return;
        }
        Log.d("SubaoAuth", String.format(n.b, "Third party auth info: %s, %s, openId=%s, expiresIn=%d", new Object[]{thirdPartyAuthInfo.getAccessToken(), thirdPartyAuthInfo.getRefreshToken(), thirdPartyAuthInfo.getOpenId(), Long.valueOf(thirdPartyAuthInfo.getExpiresIn())}));
    }

    @Nullable
    private static ThirdPartyAuthInfo a(@NonNull byte[] bArr) {
        ThirdPartyAuthInfo thirdPartyAuthInfo = null;
        JsonReader jsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(bArr), "UTF-8"));
        long j = 0;
        try {
            jsonReader.beginObject();
            String str = null;
            String str2 = null;
            String str3 = null;
            while (jsonReader.hasNext()) {
                String nextName = jsonReader.nextName();
                if ("accessToken".equals(nextName)) {
                    str3 = f.a(jsonReader);
                } else if ("expiresIn".equals(nextName)) {
                    j = jsonReader.nextLong();
                } else if ("refreshToken".equals(nextName)) {
                    str2 = f.a(jsonReader);
                } else if (UnityPayHelper.OPENID.equals(nextName)) {
                    str = f.a(jsonReader);
                } else {
                    jsonReader.skipValue();
                }
            }
            if (!TextUtils.isEmpty(str3)) {
                thirdPartyAuthInfo = new ThirdPartyAuthInfo(str3, str2, str, j);
            }
            return thirdPartyAuthInfo;
        } finally {
            e.a((Closeable) jsonReader);
        }
    }

    @NonNull
    private URL a() {
        return new URL(this.b.a, this.b.b, this.b.c, String.format("/api/v1/%s/sessions?grant_type=client_credentials&version=%s", new Object[]{e.a(this.a), e.a(this.c)}));
    }

    /* JADX INFO: finally extract failed */
    @NonNull
    private byte[] b() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(byteArrayOutputStream, "UTF-8"));
        try {
            jsonWriter.beginObject();
            jsonWriter.name("token").value(this.e.getToken());
            jsonWriter.name("authType").value(1);
            f.a(jsonWriter, "userId", this.e.getUserId());
            f.a(jsonWriter, "appId", this.e.getAppId());
            jsonWriter.endObject();
            e.a((Closeable) jsonWriter);
            return byteArrayOutputStream.toByteArray();
        } catch (Throwable th) {
            e.a((Closeable) jsonWriter);
            throw th;
        }
    }
}
