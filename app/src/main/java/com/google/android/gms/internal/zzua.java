package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.ServerProtocol;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.ads.internal.zzbs;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzua {
    public final String zzLH;
    public final String zzLI;
    public final List<String> zzLJ;
    public final String zzLK;
    public final String zzLL;
    public final List<String> zzLM;
    public final List<String> zzLN;
    public final List<String> zzLO;
    public final String zzLP;
    public final List<String> zzLQ;
    public final List<String> zzLR;
    @Nullable
    public final String zzLS;
    @Nullable
    public final String zzLT;
    public final String zzLU;
    @Nullable
    public final List<String> zzLV;
    public final String zzLW;
    @Nullable
    private String zzLX;

    public zzua(String str, String str2, List<String> list, String str3, String str4, List<String> list2, List<String> list3, String str5, String str6, List<String> list4, List<String> list5, String str7, String str8, String str9, List<String> list6, String str10, List<String> list7, String str11) {
        this.zzLH = str;
        this.zzLI = null;
        this.zzLJ = list;
        this.zzLK = null;
        this.zzLL = null;
        this.zzLM = list2;
        this.zzLN = list3;
        this.zzLP = str5;
        this.zzLQ = list4;
        this.zzLR = list5;
        this.zzLS = null;
        this.zzLT = null;
        this.zzLU = null;
        this.zzLV = null;
        this.zzLW = null;
        this.zzLO = list7;
        this.zzLX = null;
    }

    public zzua(JSONObject jSONObject) throws JSONException {
        this.zzLI = jSONObject.optString("id");
        JSONArray jSONArray = jSONObject.getJSONArray("adapters");
        ArrayList arrayList = new ArrayList(jSONArray.length());
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(jSONArray.getString(i));
        }
        this.zzLJ = Collections.unmodifiableList(arrayList);
        this.zzLK = jSONObject.optString("allocation_id", (String) null);
        zzbs.zzbS();
        this.zzLM = zzuj.zza(jSONObject, "clickurl");
        zzbs.zzbS();
        this.zzLN = zzuj.zza(jSONObject, "imp_urls");
        zzbs.zzbS();
        this.zzLO = zzuj.zza(jSONObject, "fill_urls");
        zzbs.zzbS();
        this.zzLQ = zzuj.zza(jSONObject, "video_start_urls");
        zzbs.zzbS();
        this.zzLR = zzuj.zza(jSONObject, "video_complete_urls");
        JSONObject optJSONObject = jSONObject.optJSONObject("ad");
        this.zzLH = optJSONObject != null ? optJSONObject.toString() : null;
        JSONObject optJSONObject2 = jSONObject.optJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA);
        this.zzLP = optJSONObject2 != null ? optJSONObject2.toString() : null;
        this.zzLL = optJSONObject2 != null ? optJSONObject2.optString("class_name") : null;
        this.zzLS = jSONObject.optString("html_template", (String) null);
        this.zzLT = jSONObject.optString("ad_base_url", (String) null);
        JSONObject optJSONObject3 = jSONObject.optJSONObject("assets");
        this.zzLU = optJSONObject3 != null ? optJSONObject3.toString() : null;
        zzbs.zzbS();
        this.zzLV = zzuj.zza(jSONObject, "template_ids");
        JSONObject optJSONObject4 = jSONObject.optJSONObject("ad_loader_options");
        this.zzLW = optJSONObject4 != null ? optJSONObject4.toString() : null;
        this.zzLX = jSONObject.optString(ServerProtocol.DIALOG_PARAM_RESPONSE_TYPE, (String) null);
    }

    public final boolean zzfh() {
        return "banner".equalsIgnoreCase(this.zzLX);
    }

    public final boolean zzfi() {
        return AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_NATIVE.equalsIgnoreCase(this.zzLX);
    }
}
