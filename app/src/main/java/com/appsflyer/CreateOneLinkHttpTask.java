package com.appsflyer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import com.appsflyer.share.Constants;
import com.appsflyer.share.LinkGenerator;
import com.facebook.share.internal.ShareConstants;
import com.tencent.midas.oversea.api.UnityPayHelper;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

public class CreateOneLinkHttpTask extends OneLinkHttpTask {

    /* renamed from: ʻ  reason: contains not printable characters */
    private boolean f54 = false;

    /* renamed from: ʽ  reason: contains not printable characters */
    private Context f55;

    /* renamed from: ˊ  reason: contains not printable characters */
    private String f56 = "";

    /* renamed from: ˋ  reason: contains not printable characters */
    private Map<String, String> f57;

    /* renamed from: ˎ  reason: contains not printable characters */
    private String f58;

    /* renamed from: ˏ  reason: contains not printable characters */
    private ResponseListener f59;

    public interface ResponseListener {
        @WorkerThread
        void onResponse(String str);

        @WorkerThread
        void onResponseError(String str);
    }

    public CreateOneLinkHttpTask(@NonNull String str, @NonNull Map<String, String> map, AppsFlyerLib appsFlyerLib, @NonNull Context context, boolean z) {
        super(appsFlyerLib);
        this.f54 = z;
        this.f55 = context;
        if (this.f55 != null) {
            this.f56 = context.getPackageName();
        } else {
            AFLogger.afWarnLog("CreateOneLinkHttpTask: context can't be null");
        }
        this.f62 = str;
        this.f58 = UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
        this.f57 = map;
    }

    public void setListener(@NonNull ResponseListener responseListener) {
        this.f59 = responseListener;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˊ  reason: contains not printable characters */
    public final void m28(HttpsURLConnection httpsURLConnection) throws JSONException, IOException {
        if (!this.f54) {
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setUseCaches(false);
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject(this.f57);
            jSONObject.put("ttl", this.f58);
            jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_DATA, jSONObject2);
            httpsURLConnection.connect();
            DataOutputStream dataOutputStream = new DataOutputStream(httpsURLConnection.getOutputStream());
            dataOutputStream.writeBytes(jSONObject.toString());
            dataOutputStream.flush();
            dataOutputStream.close();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˎ  reason: contains not printable characters */
    public final String m30() {
        return new StringBuilder().append(ServerConfigHandler.getUrl("https://onelink.%s/shortlink-sdk/v1")).append(Constants.URL_PATH_DELIMITER).append(this.f62).toString();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public final void m31(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                this.f59.onResponse(jSONObject.optString(keys.next()));
            }
        } catch (JSONException e) {
            this.f59.onResponseError("Can't parse one link data");
            AFLogger.afErrorLog("Error while parsing to json ".concat(String.valueOf(str)), e);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public final void m29() {
        LinkGenerator addParameters = new LinkGenerator(Constants.USER_INVITE_LINK_TYPE).setBaseURL(this.f62, AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.ONELINK_DOMAIN), this.f56).addParameter(Constants.URL_SITE_ID, this.f56).addParameters(this.f57);
        String string = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.APP_USER_ID);
        if (string != null) {
            addParameters.setReferrerCustomerId(string);
        }
        this.f59.onResponse(addParameters.generateLink());
    }
}
