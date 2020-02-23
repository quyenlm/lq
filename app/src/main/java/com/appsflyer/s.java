package com.appsflyer;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.appsflyer.share.Constants;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

final class s extends OneLinkHttpTask {

    /* renamed from: ˎ  reason: contains not printable characters */
    private static List<String> f198 = Arrays.asList(new String[]{"onelink.me", "onelnk.com", "app.aflink.com"});

    /* renamed from: ˊ  reason: contains not printable characters */
    private String f199;

    /* renamed from: ˋ  reason: contains not printable characters */
    private c f200;

    interface c {
        /* renamed from: ˊ  reason: contains not printable characters */
        void m148(Map<String, String> map);

        /* renamed from: ˎ  reason: contains not printable characters */
        void m149(String str);
    }

    s(Uri uri, AppsFlyerLib appsFlyerLib) {
        super(appsFlyerLib);
        boolean z;
        if (!TextUtils.isEmpty(uri.getHost()) && !TextUtils.isEmpty(uri.getPath())) {
            boolean z2 = false;
            Iterator<String> it = f198.iterator();
            while (true) {
                z = z2;
                if (!it.hasNext()) {
                    break;
                }
                if (uri.getHost().contains(it.next())) {
                    z2 = true;
                } else {
                    z2 = z;
                }
            }
            String[] split = uri.getPath().split(Constants.URL_PATH_DELIMITER);
            if (z && split.length == 3) {
                this.f62 = split[1];
                this.f199 = split[2];
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public final void m145(@NonNull c cVar) {
        this.f200 = cVar;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ॱ  reason: contains not printable characters */
    public final boolean m147() {
        return !TextUtils.isEmpty(this.f62) && !TextUtils.isEmpty(this.f199);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˊ  reason: contains not printable characters */
    public final void m142(HttpsURLConnection httpsURLConnection) throws JSONException, IOException {
        httpsURLConnection.setRequestMethod("GET");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˎ  reason: contains not printable characters */
    public final String m144() {
        return new StringBuilder().append(ServerConfigHandler.getUrl("https://onelink.%s/shortlink-sdk/v1")).append(Constants.URL_PATH_DELIMITER).append(this.f62).append("?id=").append(this.f199).toString();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public final void m146(String str) {
        try {
            HashMap hashMap = new HashMap();
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                hashMap.put(next, jSONObject.optString(next));
            }
            this.f200.m148(hashMap);
        } catch (JSONException e) {
            this.f200.m149("Can't parse one link data");
            AFLogger.afErrorLog("Error while parsing to json ".concat(String.valueOf(str)), e);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public final void m143() {
        this.f200.m149("Can't get one link data");
    }
}
