package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.facebook.applinks.AppLinkData;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.google.android.gms.ads.internal.zzbs;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.tencent.imsdk.tool.etc.APNUtil;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.vk.sdk.api.VKApiConst;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.apache.http.client.methods.HttpHead;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzabt {
    private static final SimpleDateFormat zzUK = new SimpleDateFormat("yyyyMMdd", Locale.US);

    /* JADX WARNING: Removed duplicated region for block: B:53:0x0144 A[Catch:{ JSONException -> 0x023c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.internal.zzaai zza(android.content.Context r48, com.google.android.gms.internal.zzaae r49, java.lang.String r50) {
        /*
            org.json.JSONObject r28 = new org.json.JSONObject     // Catch:{ JSONException -> 0x023c }
            r0 = r28
            r1 = r50
            r0.<init>(r1)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "ad_base_url"
            r5 = 0
            r0 = r28
            java.lang.String r6 = r0.optString(r4, r5)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "ad_url"
            r5 = 0
            r0 = r28
            java.lang.String r7 = r0.optString(r4, r5)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "ad_size"
            r5 = 0
            r0 = r28
            java.lang.String r19 = r0.optString(r4, r5)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "ad_slot_size"
            r0 = r28
            r1 = r19
            java.lang.String r43 = r0.optString(r4, r1)     // Catch:{ JSONException -> 0x023c }
            if (r49 == 0) goto L_0x00cf
            r0 = r49
            int r4 = r0.zzSF     // Catch:{ JSONException -> 0x023c }
            if (r4 == 0) goto L_0x00cf
            r27 = 1
        L_0x0038:
            java.lang.String r4 = "ad_json"
            r5 = 0
            r0 = r28
            java.lang.String r5 = r0.optString(r4, r5)     // Catch:{ JSONException -> 0x023c }
            if (r5 != 0) goto L_0x004c
            java.lang.String r4 = "ad_html"
            r5 = 0
            r0 = r28
            java.lang.String r5 = r0.optString(r4, r5)     // Catch:{ JSONException -> 0x023c }
        L_0x004c:
            if (r5 != 0) goto L_0x0057
            java.lang.String r4 = "body"
            r5 = 0
            r0 = r28
            java.lang.String r5 = r0.optString(r4, r5)     // Catch:{ JSONException -> 0x023c }
        L_0x0057:
            r20 = -1
            java.lang.String r4 = "debug_dialog"
            r8 = 0
            r0 = r28
            java.lang.String r22 = r0.optString(r4, r8)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "debug_signals"
            r8 = 0
            r0 = r28
            java.lang.String r45 = r0.optString(r4, r8)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "interstitial_timeout"
            r0 = r28
            boolean r4 = r0.has(r4)     // Catch:{ JSONException -> 0x023c }
            if (r4 == 0) goto L_0x00d3
            java.lang.String r4 = "interstitial_timeout"
            r0 = r28
            double r8 = r0.getDouble(r4)     // Catch:{ JSONException -> 0x023c }
            r10 = 4652007308841189376(0x408f400000000000, double:1000.0)
            double r8 = r8 * r10
            long r12 = (long) r8     // Catch:{ JSONException -> 0x023c }
        L_0x0084:
            java.lang.String r4 = "orientation"
            r8 = 0
            r0 = r28
            java.lang.String r4 = r0.optString(r4, r8)     // Catch:{ JSONException -> 0x023c }
            r18 = -1
            java.lang.String r8 = "portrait"
            boolean r8 = r8.equals(r4)     // Catch:{ JSONException -> 0x023c }
            if (r8 == 0) goto L_0x00d6
            com.google.android.gms.internal.zzahe r4 = com.google.android.gms.ads.internal.zzbs.zzbB()     // Catch:{ JSONException -> 0x023c }
            int r18 = r4.zzhU()     // Catch:{ JSONException -> 0x023c }
        L_0x009f:
            r4 = 0
            boolean r8 = android.text.TextUtils.isEmpty(r5)     // Catch:{ JSONException -> 0x023c }
            if (r8 == 0) goto L_0x0271
            boolean r8 = android.text.TextUtils.isEmpty(r7)     // Catch:{ JSONException -> 0x023c }
            if (r8 != 0) goto L_0x0271
            r0 = r49
            com.google.android.gms.internal.zzaje r4 = r0.zzvT     // Catch:{ JSONException -> 0x023c }
            java.lang.String r6 = r4.zzaP     // Catch:{ JSONException -> 0x023c }
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r4 = r49
            r5 = r48
            com.google.android.gms.internal.zzaai r4 = com.google.android.gms.internal.zzabm.zza(r4, r5, r6, r7, r8, r9, r10, r11)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r6 = r4.zzPi     // Catch:{ JSONException -> 0x023c }
            java.lang.String r7 = r4.body     // Catch:{ JSONException -> 0x023c }
            long r0 = r4.zzTs     // Catch:{ JSONException -> 0x023c }
            r20 = r0
        L_0x00c6:
            if (r7 != 0) goto L_0x00e7
            com.google.android.gms.internal.zzaai r4 = new com.google.android.gms.internal.zzaai     // Catch:{ JSONException -> 0x023c }
            r5 = 0
            r4.<init>(r5)     // Catch:{ JSONException -> 0x023c }
        L_0x00ce:
            return r4
        L_0x00cf:
            r27 = 0
            goto L_0x0038
        L_0x00d3:
            r12 = -1
            goto L_0x0084
        L_0x00d6:
            java.lang.String r8 = "landscape"
            boolean r4 = r8.equals(r4)     // Catch:{ JSONException -> 0x023c }
            if (r4 == 0) goto L_0x009f
            com.google.android.gms.internal.zzahe r4 = com.google.android.gms.ads.internal.zzbs.zzbB()     // Catch:{ JSONException -> 0x023c }
            int r18 = r4.zzhT()     // Catch:{ JSONException -> 0x023c }
            goto L_0x009f
        L_0x00e7:
            java.lang.String r5 = "click_urls"
            r0 = r28
            org.json.JSONArray r5 = r0.optJSONArray(r5)     // Catch:{ JSONException -> 0x023c }
            if (r4 != 0) goto L_0x025c
            r8 = 0
        L_0x00f2:
            if (r5 == 0) goto L_0x00f8
            java.util.List r8 = zza((org.json.JSONArray) r5, (java.util.List<java.lang.String>) r8)     // Catch:{ JSONException -> 0x023c }
        L_0x00f8:
            java.lang.String r5 = "impression_urls"
            r0 = r28
            org.json.JSONArray r5 = r0.optJSONArray(r5)     // Catch:{ JSONException -> 0x023c }
            if (r4 != 0) goto L_0x0260
            r9 = 0
        L_0x0103:
            if (r5 == 0) goto L_0x0109
            java.util.List r9 = zza((org.json.JSONArray) r5, (java.util.List<java.lang.String>) r9)     // Catch:{ JSONException -> 0x023c }
        L_0x0109:
            java.lang.String r5 = "manual_impression_urls"
            r0 = r28
            org.json.JSONArray r5 = r0.optJSONArray(r5)     // Catch:{ JSONException -> 0x023c }
            if (r4 != 0) goto L_0x0264
            r15 = 0
        L_0x0114:
            if (r5 == 0) goto L_0x011a
            java.util.List r15 = zza((org.json.JSONArray) r5, (java.util.List<java.lang.String>) r15)     // Catch:{ JSONException -> 0x023c }
        L_0x011a:
            if (r4 == 0) goto L_0x026e
            int r5 = r4.orientation     // Catch:{ JSONException -> 0x023c }
            r10 = -1
            if (r5 == r10) goto L_0x0125
            int r0 = r4.orientation     // Catch:{ JSONException -> 0x023c }
            r18 = r0
        L_0x0125:
            long r10 = r4.zzTn     // Catch:{ JSONException -> 0x023c }
            r16 = 0
            int r5 = (r10 > r16 ? 1 : (r10 == r16 ? 0 : -1))
            if (r5 <= 0) goto L_0x026e
            long r10 = r4.zzTn     // Catch:{ JSONException -> 0x023c }
        L_0x012f:
            java.lang.String r4 = "active_view"
            r0 = r28
            java.lang.String r25 = r0.optString(r4)     // Catch:{ JSONException -> 0x023c }
            r24 = 0
            java.lang.String r4 = "ad_is_javascript"
            r5 = 0
            r0 = r28
            boolean r23 = r0.optBoolean(r4, r5)     // Catch:{ JSONException -> 0x023c }
            if (r23 == 0) goto L_0x014d
            java.lang.String r4 = "ad_passback_url"
            r5 = 0
            r0 = r28
            java.lang.String r24 = r0.optString(r4, r5)     // Catch:{ JSONException -> 0x023c }
        L_0x014d:
            java.lang.String r4 = "mediation"
            r5 = 0
            r0 = r28
            boolean r12 = r0.optBoolean(r4, r5)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "custom_render_allowed"
            r5 = 0
            r0 = r28
            boolean r26 = r0.optBoolean(r4, r5)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "content_url_opted_out"
            r5 = 1
            r0 = r28
            boolean r29 = r0.optBoolean(r4, r5)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "content_vertical_opted_out"
            r5 = 1
            r0 = r28
            boolean r46 = r0.optBoolean(r4, r5)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "prefetch"
            r5 = 0
            r0 = r28
            boolean r30 = r0.optBoolean(r4, r5)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "refresh_interval_milliseconds"
            r16 = -1
            r0 = r28
            r1 = r16
            long r16 = r0.optLong(r4, r1)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "mediation_config_cache_time_milliseconds"
            r32 = -1
            r0 = r28
            r1 = r32
            long r13 = r0.optLong(r4, r1)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "gws_query_id"
            java.lang.String r5 = ""
            r0 = r28
            java.lang.String r31 = r0.optString(r4, r5)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "height"
            java.lang.String r5 = "fluid"
            java.lang.String r32 = ""
            r0 = r28
            r1 = r32
            java.lang.String r5 = r0.optString(r5, r1)     // Catch:{ JSONException -> 0x023c }
            boolean r32 = r4.equals(r5)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "native_express"
            r5 = 0
            r0 = r28
            boolean r33 = r0.optBoolean(r4, r5)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "video_start_urls"
            r0 = r28
            org.json.JSONArray r4 = r0.optJSONArray(r4)     // Catch:{ JSONException -> 0x023c }
            r5 = 0
            java.util.List r35 = zza((org.json.JSONArray) r4, (java.util.List<java.lang.String>) r5)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "video_complete_urls"
            r0 = r28
            org.json.JSONArray r4 = r0.optJSONArray(r4)     // Catch:{ JSONException -> 0x023c }
            r5 = 0
            java.util.List r36 = zza((org.json.JSONArray) r4, (java.util.List<java.lang.String>) r5)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "rewards"
            r0 = r28
            org.json.JSONArray r4 = r0.optJSONArray(r4)     // Catch:{ JSONException -> 0x023c }
            com.google.android.gms.internal.zzaee r34 = com.google.android.gms.internal.zzaee.zza(r4)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "use_displayed_impression"
            r5 = 0
            r0 = r28
            boolean r37 = r0.optBoolean(r4, r5)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "auto_protection_configuration"
            r0 = r28
            org.json.JSONObject r4 = r0.optJSONObject(r4)     // Catch:{ JSONException -> 0x023c }
            com.google.android.gms.internal.zzaak r38 = com.google.android.gms.internal.zzaak.zze(r4)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "set_cookie"
            java.lang.String r5 = ""
            r0 = r28
            java.lang.String r40 = r0.optString(r4, r5)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "remote_ping_urls"
            r0 = r28
            org.json.JSONArray r4 = r0.optJSONArray(r4)     // Catch:{ JSONException -> 0x023c }
            r5 = 0
            java.util.List r41 = zza((org.json.JSONArray) r4, (java.util.List<java.lang.String>) r5)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "safe_browsing"
            r0 = r28
            org.json.JSONObject r4 = r0.optJSONObject(r4)     // Catch:{ JSONException -> 0x023c }
            com.google.android.gms.internal.zzaeq r44 = com.google.android.gms.internal.zzaeq.zzf(r4)     // Catch:{ JSONException -> 0x023c }
            java.lang.String r4 = "render_in_browser"
            r0 = r49
            boolean r5 = r0.zzMe     // Catch:{ JSONException -> 0x023c }
            r0 = r28
            boolean r42 = r0.optBoolean(r4, r5)     // Catch:{ JSONException -> 0x023c }
            com.google.android.gms.internal.zzaai r4 = new com.google.android.gms.internal.zzaai     // Catch:{ JSONException -> 0x023c }
            r0 = r49
            boolean r0 = r0.zzSH     // Catch:{ JSONException -> 0x023c }
            r28 = r0
            r0 = r49
            boolean r0 = r0.zzSV     // Catch:{ JSONException -> 0x023c }
            r39 = r0
            r0 = r49
            boolean r0 = r0.zzTh     // Catch:{ JSONException -> 0x023c }
            r47 = r0
            r5 = r49
            r4.<init>(r5, r6, r7, r8, r9, r10, r12, r13, r15, r16, r18, r19, r20, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33, r34, r35, r36, r37, r38, r39, r40, r41, r42, r43, r44, r45, r46, r47)     // Catch:{ JSONException -> 0x023c }
            goto L_0x00ce
        L_0x023c:
            r4 = move-exception
            java.lang.String r5 = "Could not parse the inline ad response: "
            java.lang.String r4 = r4.getMessage()
            java.lang.String r4 = java.lang.String.valueOf(r4)
            int r6 = r4.length()
            if (r6 == 0) goto L_0x0268
            java.lang.String r4 = r5.concat(r4)
        L_0x0251:
            com.google.android.gms.internal.zzafr.zzaT(r4)
            com.google.android.gms.internal.zzaai r4 = new com.google.android.gms.internal.zzaai
            r5 = 0
            r4.<init>(r5)
            goto L_0x00ce
        L_0x025c:
            java.util.List<java.lang.String> r8 = r4.zzMa     // Catch:{ JSONException -> 0x023c }
            goto L_0x00f2
        L_0x0260:
            java.util.List<java.lang.String> r9 = r4.zzMb     // Catch:{ JSONException -> 0x023c }
            goto L_0x0103
        L_0x0264:
            java.util.List<java.lang.String> r15 = r4.zzTq     // Catch:{ JSONException -> 0x023c }
            goto L_0x0114
        L_0x0268:
            java.lang.String r4 = new java.lang.String
            r4.<init>(r5)
            goto L_0x0251
        L_0x026e:
            r10 = r12
            goto L_0x012f
        L_0x0271:
            r7 = r5
            goto L_0x00c6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzabt.zza(android.content.Context, com.google.android.gms.internal.zzaae, java.lang.String):com.google.android.gms.internal.zzaai");
    }

    @Nullable
    private static List<String> zza(@Nullable JSONArray jSONArray, @Nullable List<String> list) throws JSONException {
        if (jSONArray == null) {
            return null;
        }
        if (list == null) {
            list = new LinkedList<>();
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            list.add(jSONArray.getString(i));
        }
        return list;
    }

    @Nullable
    public static JSONObject zza(Context context, zzabk zzabk) {
        String str;
        String str2;
        String str3;
        zzaae zzaae = zzabk.zzUj;
        Location location = zzabk.zzzV;
        zzacb zzacb = zzabk.zzUk;
        Bundle bundle = zzabk.zzSG;
        JSONObject jSONObject = zzabk.zzUl;
        try {
            HashMap hashMap = new HashMap();
            hashMap.put("extra_caps", zzbs.zzbL().zzd(zzmo.zzFl));
            if (zzabk.zzSN.size() > 0) {
                hashMap.put("eid", TextUtils.join(",", zzabk.zzSN));
            }
            if (zzaae.zzSy != null) {
                hashMap.put("ad_pos", zzaae.zzSy);
            }
            zzir zzir = zzaae.zzSz;
            String zzhK = zzafo.zzhK();
            if (zzhK != null) {
                hashMap.put("abf", zzhK);
            }
            if (zzir.zzzN != -1) {
                hashMap.put("cust_age", zzUK.format(new Date(zzir.zzzN)));
            }
            if (zzir.extras != null) {
                hashMap.put(AppLinkData.ARGUMENTS_EXTRAS_KEY, zzir.extras);
            }
            if (zzir.zzzO != -1) {
                hashMap.put("cust_gender", Integer.valueOf(zzir.zzzO));
            }
            if (zzir.zzzP != null) {
                hashMap.put("kw", zzir.zzzP);
            }
            if (zzir.zzzR != -1) {
                hashMap.put("tag_for_child_directed_treatment", Integer.valueOf(zzir.zzzR));
            }
            if (zzir.zzzQ) {
                hashMap.put("adtest", "on");
            }
            if (zzir.versionCode >= 2) {
                if (zzir.zzzS) {
                    hashMap.put("d_imp_hdr", 1);
                }
                if (!TextUtils.isEmpty(zzir.zzzT)) {
                    hashMap.put("ppid", zzir.zzzT);
                }
                if (zzir.zzzU != null) {
                    zzlt zzlt = zzir.zzzU;
                    if (Color.alpha(zzlt.zzBw) != 0) {
                        hashMap.put("acolor", zzu(zzlt.zzBw));
                    }
                    if (Color.alpha(zzlt.backgroundColor) != 0) {
                        hashMap.put("bgcolor", zzu(zzlt.backgroundColor));
                    }
                    if (!(Color.alpha(zzlt.zzBx) == 0 || Color.alpha(zzlt.zzBy) == 0)) {
                        hashMap.put("gradientto", zzu(zzlt.zzBx));
                        hashMap.put("gradientfrom", zzu(zzlt.zzBy));
                    }
                    if (Color.alpha(zzlt.zzBz) != 0) {
                        hashMap.put("bcolor", zzu(zzlt.zzBz));
                    }
                    hashMap.put("bthick", Integer.toString(zzlt.zzBA));
                    switch (zzlt.zzBB) {
                        case 0:
                            str2 = APNUtil.ANP_NAME_NONE;
                            break;
                        case 1:
                            str2 = "dashed";
                            break;
                        case 2:
                            str2 = "dotted";
                            break;
                        case 3:
                            str2 = "solid";
                            break;
                        default:
                            str2 = null;
                            break;
                    }
                    if (str2 != null) {
                        hashMap.put("btype", str2);
                    }
                    switch (zzlt.zzBC) {
                        case 0:
                            str3 = "light";
                            break;
                        case 1:
                            str3 = FirebaseAnalytics.Param.MEDIUM;
                            break;
                        case 2:
                            str3 = "dark";
                            break;
                        default:
                            str3 = null;
                            break;
                    }
                    if (str3 != null) {
                        hashMap.put("callbuttoncolor", str3);
                    }
                    if (zzlt.zzBD != null) {
                        hashMap.put("channel", zzlt.zzBD);
                    }
                    if (Color.alpha(zzlt.zzBE) != 0) {
                        hashMap.put("dcolor", zzu(zzlt.zzBE));
                    }
                    if (zzlt.zzBF != null) {
                        hashMap.put("font", zzlt.zzBF);
                    }
                    if (Color.alpha(zzlt.zzBG) != 0) {
                        hashMap.put("hcolor", zzu(zzlt.zzBG));
                    }
                    hashMap.put("headersize", Integer.toString(zzlt.zzBH));
                    if (zzlt.zzBI != null) {
                        hashMap.put(VKApiConst.Q, zzlt.zzBI);
                    }
                }
            }
            if (zzir.versionCode >= 3 && zzir.zzzW != null) {
                hashMap.put("url", zzir.zzzW);
            }
            if (zzir.versionCode >= 5) {
                if (zzir.zzzY != null) {
                    hashMap.put("custom_targeting", zzir.zzzY);
                }
                if (zzir.zzzZ != null) {
                    hashMap.put("category_exclusions", zzir.zzzZ);
                }
                if (zzir.zzAa != null) {
                    hashMap.put("request_agent", zzir.zzAa);
                }
            }
            if (zzir.versionCode >= 6 && zzir.zzAb != null) {
                hashMap.put("request_pkg", zzir.zzAb);
            }
            if (zzir.versionCode >= 7) {
                hashMap.put("is_designed_for_families", Boolean.valueOf(zzir.zzAc));
            }
            if (zzaae.zzvX.zzAu == null) {
                hashMap.put("format", zzaae.zzvX.zzAs);
                if (zzaae.zzvX.zzAw) {
                    hashMap.put("fluid", "height");
                }
            } else {
                boolean z = false;
                boolean z2 = false;
                zziv[] zzivArr = zzaae.zzvX.zzAu;
                int length = zzivArr.length;
                int i = 0;
                while (i < length) {
                    zziv zziv = zzivArr[i];
                    if (!zziv.zzAw && !z) {
                        hashMap.put("format", zziv.zzAs);
                        z = true;
                    }
                    if (zziv.zzAw && !z2) {
                        hashMap.put("fluid", "height");
                        z2 = true;
                    }
                    if (!z || !z2) {
                        i++;
                    }
                }
            }
            if (zzaae.zzvX.width == -1) {
                hashMap.put("smart_w", MessengerShareContentUtility.WEBVIEW_RATIO_FULL);
            }
            if (zzaae.zzvX.height == -2) {
                hashMap.put("smart_h", "auto");
            }
            if (zzaae.zzvX.zzAu != null) {
                StringBuilder sb = new StringBuilder();
                boolean z3 = false;
                for (zziv zziv2 : zzaae.zzvX.zzAu) {
                    if (zziv2.zzAw) {
                        z3 = true;
                    } else {
                        if (sb.length() != 0) {
                            sb.append("|");
                        }
                        sb.append(zziv2.width == -1 ? (int) (((float) zziv2.widthPixels) / zzacb.zzxR) : zziv2.width);
                        sb.append("x");
                        sb.append(zziv2.height == -2 ? (int) (((float) zziv2.heightPixels) / zzacb.zzxR) : zziv2.height);
                    }
                }
                if (z3) {
                    if (sb.length() != 0) {
                        sb.insert(0, "|");
                    }
                    sb.insert(0, "320x50");
                }
                hashMap.put("sz", sb);
            }
            if (zzaae.zzSF != 0) {
                hashMap.put("native_version", Integer.valueOf(zzaae.zzSF));
                hashMap.put("native_templates", zzaae.zzwq);
                zzon zzon = zzaae.zzwj;
                switch (zzon != null ? zzon.zzIo : 0) {
                    case 0:
                        str = "any";
                        break;
                    case 1:
                        str = "portrait";
                        break;
                    case 2:
                        str = "landscape";
                        break;
                    default:
                        str = "not_set";
                        break;
                }
                hashMap.put("native_image_orientation", str);
                if (!zzaae.zzSO.isEmpty()) {
                    hashMap.put("native_custom_templates", zzaae.zzSO);
                }
                if (!TextUtils.isEmpty(zzaae.zzTi)) {
                    try {
                        hashMap.put("native_advanced_settings", new JSONArray(zzaae.zzTi));
                    } catch (JSONException e) {
                        zzafr.zzc("Problem creating json from native advanced settings", e);
                    }
                }
            }
            if (zzaae.zzwn != null && zzaae.zzwn.size() > 0) {
                for (Integer next : zzaae.zzwn) {
                    if (next.intValue() == 2) {
                        hashMap.put("iba", true);
                    } else if (next.intValue() == 1) {
                        hashMap.put("ina", true);
                    }
                }
            }
            if (zzaae.zzvX.zzAx) {
                hashMap.put("ene", true);
            }
            if (zzaae.zzwl != null) {
                hashMap.put("is_icon_ad", true);
                hashMap.put("icon_ad_expansion_behavior", Integer.valueOf(zzaae.zzwl.zzAR));
            }
            hashMap.put("slotname", zzaae.zzvR);
            hashMap.put("pn", zzaae.applicationInfo.packageName);
            if (zzaae.zzSA != null) {
                hashMap.put("vc", Integer.valueOf(zzaae.zzSA.versionCode));
            }
            hashMap.put("ms", zzabk.zzSB);
            hashMap.put("seq_num", zzaae.zzSC);
            hashMap.put("session_id", zzaae.zzSD);
            hashMap.put("js", zzaae.zzvT.zzaP);
            zzacn zzacn = zzabk.zzUh;
            Bundle bundle2 = zzaae.zzTa;
            Bundle bundle3 = zzabk.zzUg;
            hashMap.put("am", Integer.valueOf(zzacb.zzVF));
            hashMap.put("cog", zzt(zzacb.zzVG));
            hashMap.put("coh", zzt(zzacb.zzVH));
            if (!TextUtils.isEmpty(zzacb.zzVI)) {
                hashMap.put("carrier", zzacb.zzVI);
            }
            hashMap.put("gl", zzacb.zzVJ);
            if (zzacb.zzVK) {
                hashMap.put("simulator", 1);
            }
            if (zzacb.zzVL) {
                hashMap.put("is_sidewinder", 1);
            }
            hashMap.put("ma", zzt(zzacb.zzVM));
            hashMap.put("sp", zzt(zzacb.zzVN));
            hashMap.put("hl", zzacb.zzVO);
            if (!TextUtils.isEmpty(zzacb.zzVP)) {
                hashMap.put("mv", zzacb.zzVP);
            }
            hashMap.put("muv", Integer.valueOf(zzacb.zzVR));
            if (zzacb.zzVS != -2) {
                hashMap.put("cnt", Integer.valueOf(zzacb.zzVS));
            }
            hashMap.put("gnt", Integer.valueOf(zzacb.zzVT));
            hashMap.put(APDataReportManager.TENPAYSUCC_PRE, Integer.valueOf(zzacb.zzVU));
            hashMap.put("rm", Integer.valueOf(zzacb.zzVV));
            hashMap.put("riv", Integer.valueOf(zzacb.zzVW));
            Bundle bundle4 = new Bundle();
            bundle4.putString("build_build", zzacb.zzWb);
            bundle4.putString("build_device", zzacb.zzWc);
            Bundle bundle5 = new Bundle();
            bundle5.putBoolean("is_charging", zzacb.zzVY);
            bundle5.putDouble("battery_level", zzacb.zzVX);
            bundle4.putBundle("battery", bundle5);
            Bundle bundle6 = new Bundle();
            bundle6.putInt("active_network_state", zzacb.zzWa);
            bundle6.putBoolean("active_network_metered", zzacb.zzVZ);
            if (zzacn != null) {
                Bundle bundle7 = new Bundle();
                bundle7.putInt("predicted_latency_micros", zzacn.zzWm);
                bundle7.putLong("predicted_down_throughput_bps", zzacn.zzWn);
                bundle7.putLong("predicted_up_throughput_bps", zzacn.zzWo);
                bundle6.putBundle("predictions", bundle7);
            }
            bundle4.putBundle("network", bundle6);
            Bundle bundle8 = new Bundle();
            bundle8.putBoolean("is_browser_custom_tabs_capable", zzacb.zzWd);
            bundle4.putBundle("browser", bundle8);
            if (bundle2 != null) {
                Bundle bundle9 = new Bundle();
                bundle9.putString("runtime_free", Long.toString(bundle2.getLong("runtime_free_memory", -1)));
                bundle9.putString("runtime_max", Long.toString(bundle2.getLong("runtime_max_memory", -1)));
                bundle9.putString("runtime_total", Long.toString(bundle2.getLong("runtime_total_memory", -1)));
                bundle9.putString("web_view_count", Integer.toString(bundle2.getInt("web_view_count", 0)));
                Debug.MemoryInfo memoryInfo = (Debug.MemoryInfo) bundle2.getParcelable("debug_memory_info");
                if (memoryInfo != null) {
                    bundle9.putString("debug_info_dalvik_private_dirty", Integer.toString(memoryInfo.dalvikPrivateDirty));
                    bundle9.putString("debug_info_dalvik_pss", Integer.toString(memoryInfo.dalvikPss));
                    bundle9.putString("debug_info_dalvik_shared_dirty", Integer.toString(memoryInfo.dalvikSharedDirty));
                    bundle9.putString("debug_info_native_private_dirty", Integer.toString(memoryInfo.nativePrivateDirty));
                    bundle9.putString("debug_info_native_pss", Integer.toString(memoryInfo.nativePss));
                    bundle9.putString("debug_info_native_shared_dirty", Integer.toString(memoryInfo.nativeSharedDirty));
                    bundle9.putString("debug_info_other_private_dirty", Integer.toString(memoryInfo.otherPrivateDirty));
                    bundle9.putString("debug_info_other_pss", Integer.toString(memoryInfo.otherPss));
                    bundle9.putString("debug_info_other_shared_dirty", Integer.toString(memoryInfo.otherSharedDirty));
                }
                bundle4.putBundle("android_mem_info", bundle9);
            }
            Bundle bundle10 = new Bundle();
            bundle10.putBundle("parental_controls", bundle3);
            if (!TextUtils.isEmpty(zzacb.zzVQ)) {
                bundle10.putString("package_version", zzacb.zzVQ);
            }
            bundle4.putBundle("play_store", bundle10);
            hashMap.put("device", bundle4);
            Bundle bundle11 = new Bundle();
            bundle11.putString("doritos", zzabk.zzUi);
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzDM)).booleanValue()) {
                String str4 = null;
                boolean z4 = false;
                if (zzabk.zzqi != null) {
                    str4 = zzabk.zzqi.getId();
                    z4 = zzabk.zzqi.isLimitAdTrackingEnabled();
                }
                if (!TextUtils.isEmpty(str4)) {
                    bundle11.putString("rdid", str4);
                    bundle11.putBoolean("is_lat", z4);
                    bundle11.putString("idtype", HttpRequestParams.AD_ID);
                } else {
                    zzji.zzds();
                    bundle11.putString("pdid", zzaiy.zzW(context));
                    bundle11.putString("pdidtype", "ssaid");
                }
            }
            hashMap.put("pii", bundle11);
            hashMap.put("platform", Build.MANUFACTURER);
            hashMap.put("submodel", Build.MODEL);
            if (location != null) {
                zza((HashMap<String, Object>) hashMap, location);
            } else if (zzaae.zzSz.versionCode >= 2 && zzaae.zzSz.zzzV != null) {
                zza((HashMap<String, Object>) hashMap, zzaae.zzSz.zzzV);
            }
            if (zzaae.versionCode >= 2) {
                hashMap.put("quality_signals", zzaae.zzSE);
            }
            if (zzaae.versionCode >= 4 && zzaae.zzSH) {
                hashMap.put("forceHttps", Boolean.valueOf(zzaae.zzSH));
            }
            if (bundle != null) {
                hashMap.put("content_info", bundle);
            }
            if (zzaae.versionCode >= 5) {
                hashMap.put("u_sd", Float.valueOf(zzaae.zzxR));
                hashMap.put("sh", Integer.valueOf(zzaae.zzSJ));
                hashMap.put("sw", Integer.valueOf(zzaae.zzSI));
            } else {
                hashMap.put("u_sd", Float.valueOf(zzacb.zzxR));
                hashMap.put("sh", Integer.valueOf(zzacb.zzSJ));
                hashMap.put("sw", Integer.valueOf(zzacb.zzSI));
            }
            if (zzaae.versionCode >= 6) {
                if (!TextUtils.isEmpty(zzaae.zzSK)) {
                    try {
                        hashMap.put("view_hierarchy", new JSONObject(zzaae.zzSK));
                    } catch (JSONException e2) {
                        zzafr.zzc("Problem serializing view hierarchy to JSON", e2);
                    }
                }
                hashMap.put("correlation_id", Long.valueOf(zzaae.zzSL));
            }
            if (zzaae.versionCode >= 7) {
                hashMap.put("request_id", zzaae.zzSM);
            }
            if (zzaae.versionCode >= 12 && !TextUtils.isEmpty(zzaae.zzSQ)) {
                hashMap.put("anchor", zzaae.zzSQ);
            }
            if (zzaae.versionCode >= 13) {
                hashMap.put("android_app_volume", Float.valueOf(zzaae.zzSR));
            }
            if (zzaae.versionCode >= 18) {
                hashMap.put("android_app_muted", Boolean.valueOf(zzaae.zzSX));
            }
            if (zzaae.versionCode >= 14 && zzaae.zzSS > 0) {
                hashMap.put("target_api", Integer.valueOf(zzaae.zzSS));
            }
            if (zzaae.versionCode >= 15) {
                hashMap.put("scroll_index", Integer.valueOf(zzaae.zzST == -1 ? -1 : zzaae.zzST));
            }
            if (zzaae.versionCode >= 16) {
                hashMap.put("_activity_context", Boolean.valueOf(zzaae.zzSU));
            }
            if (zzaae.versionCode >= 18) {
                if (!TextUtils.isEmpty(zzaae.zzSY)) {
                    try {
                        hashMap.put("app_settings", new JSONObject(zzaae.zzSY));
                    } catch (JSONException e3) {
                        zzafr.zzc("Problem creating json from app settings", e3);
                    }
                }
                hashMap.put("render_in_browser", Boolean.valueOf(zzaae.zzMe));
            }
            if (zzaae.versionCode >= 18) {
                hashMap.put("android_num_video_cache_tasks", Integer.valueOf(zzaae.zzSZ));
            }
            zzaje zzaje = zzaae.zzvT;
            boolean z5 = zzabk.zzUm;
            Bundle bundle12 = new Bundle();
            Bundle bundle13 = new Bundle();
            bundle13.putString("cl", "162978962");
            bundle13.putString("rapid_rc", APGlobalInfo.DevEnv);
            bundle13.putString("rapid_rollup", HttpHead.METHOD_NAME);
            bundle12.putBundle("build_meta", bundle13);
            bundle12.putString("mf", Boolean.toString(((Boolean) zzbs.zzbL().zzd(zzmo.zzFn)).booleanValue()));
            bundle12.putBoolean("instant_app", zzbha.zzaP(context).zzsl());
            bundle12.putBoolean("lite", zzaje.zzaaR);
            bundle12.putBoolean("local_service", z5);
            hashMap.put("sdk_env", bundle12);
            hashMap.put("cache_state", jSONObject);
            if (zzaae.versionCode >= 19) {
                hashMap.put("gct", zzaae.zzTb);
            }
            if (zzaae.versionCode >= 21 && zzaae.zzTc) {
                hashMap.put("de", "1");
            }
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzDY)).booleanValue()) {
                String str5 = zzaae.zzvX.zzAs;
                boolean z6 = str5.equals("interstitial_mb") || str5.equals("reward_mb");
                Bundle bundle14 = zzaae.zzTd;
                boolean z7 = bundle14 != null;
                if (z6 && z7) {
                    Bundle bundle15 = new Bundle();
                    bundle15.putBundle("interstitial_pool", bundle14);
                    hashMap.put("counters", bundle15);
                }
            }
            if (zzaae.versionCode >= 22 && zzbs.zzbY().zzp(context)) {
                hashMap.put("gmp_app_id", zzaae.zzTe);
                if ("TIME_OUT".equals(zzaae.zzTf)) {
                    hashMap.put("sai_timeout", zzbs.zzbL().zzd(zzmo.zzDB));
                } else if (zzaae.zzTf == null) {
                    hashMap.put("fbs_aiid", "");
                } else {
                    hashMap.put("fbs_aiid", zzaae.zzTf);
                }
                hashMap.put("fbs_aeid", zzaae.zzTg);
            }
            String str6 = (String) zzbs.zzbL().zzd(zzmo.zzCE);
            if (str6 != null && !str6.isEmpty()) {
                if (Build.VERSION.SDK_INT >= ((Integer) zzbs.zzbL().zzd(zzmo.zzCF)).intValue()) {
                    HashMap hashMap2 = new HashMap();
                    for (String str7 : str6.split(",")) {
                        hashMap2.put(str7, zzaiw.zzaQ(str7));
                    }
                    hashMap.put("video_decoders", hashMap2);
                }
            }
            if (zzafr.zzz(2)) {
                String valueOf = String.valueOf(zzbs.zzbz().zzj(hashMap).toString(2));
                zzafr.v(valueOf.length() != 0 ? "Ad Request JSON: ".concat(valueOf) : new String("Ad Request JSON: "));
            }
            return zzbs.zzbz().zzj(hashMap);
        } catch (JSONException e4) {
            String valueOf2 = String.valueOf(e4.getMessage());
            zzafr.zzaT(valueOf2.length() != 0 ? "Problem serializing ad request to JSON: ".concat(valueOf2) : new String("Problem serializing ad request to JSON: "));
            return null;
        }
    }

    private static void zza(HashMap<String, Object> hashMap, Location location) {
        HashMap hashMap2 = new HashMap();
        Float valueOf = Float.valueOf(location.getAccuracy() * 1000.0f);
        Long valueOf2 = Long.valueOf(location.getTime() * 1000);
        Long valueOf3 = Long.valueOf((long) (location.getLatitude() * 1.0E7d));
        Long valueOf4 = Long.valueOf((long) (location.getLongitude() * 1.0E7d));
        hashMap2.put("radius", valueOf);
        hashMap2.put(VKApiConst.LAT, valueOf3);
        hashMap2.put(VKApiConst.LONG, valueOf4);
        hashMap2.put("time", valueOf2);
        hashMap.put("uule", hashMap2);
    }

    public static JSONObject zzb(zzaai zzaai) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (zzaai.zzPi != null) {
            jSONObject.put("ad_base_url", zzaai.zzPi);
        }
        if (zzaai.zzTr != null) {
            jSONObject.put("ad_size", zzaai.zzTr);
        }
        jSONObject.put(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_NATIVE, zzaai.zzAv);
        if (zzaai.zzAv) {
            jSONObject.put("ad_json", zzaai.body);
        } else {
            jSONObject.put("ad_html", zzaai.body);
        }
        if (zzaai.zzTt != null) {
            jSONObject.put("debug_dialog", zzaai.zzTt);
        }
        if (zzaai.zzTK != null) {
            jSONObject.put("debug_signals", zzaai.zzTK);
        }
        if (zzaai.zzTn != -1) {
            jSONObject.put("interstitial_timeout", ((double) zzaai.zzTn) / 1000.0d);
        }
        if (zzaai.orientation == zzbs.zzbB().zzhU()) {
            jSONObject.put("orientation", "portrait");
        } else if (zzaai.orientation == zzbs.zzbB().zzhT()) {
            jSONObject.put("orientation", "landscape");
        }
        if (zzaai.zzMa != null) {
            jSONObject.put("click_urls", zzm(zzaai.zzMa));
        }
        if (zzaai.zzMb != null) {
            jSONObject.put("impression_urls", zzm(zzaai.zzMb));
        }
        if (zzaai.zzTq != null) {
            jSONObject.put("manual_impression_urls", zzm(zzaai.zzTq));
        }
        if (zzaai.zzTw != null) {
            jSONObject.put("active_view", zzaai.zzTw);
        }
        jSONObject.put("ad_is_javascript", zzaai.zzTu);
        if (zzaai.zzTv != null) {
            jSONObject.put("ad_passback_url", zzaai.zzTv);
        }
        jSONObject.put("mediation", zzaai.zzTo);
        jSONObject.put("custom_render_allowed", zzaai.zzTx);
        jSONObject.put("content_url_opted_out", zzaai.zzTy);
        jSONObject.put("content_vertical_opted_out", zzaai.zzTL);
        jSONObject.put("prefetch", zzaai.zzTz);
        if (zzaai.zzMg != -1) {
            jSONObject.put("refresh_interval_milliseconds", zzaai.zzMg);
        }
        if (zzaai.zzTp != -1) {
            jSONObject.put("mediation_config_cache_time_milliseconds", zzaai.zzTp);
        }
        if (!TextUtils.isEmpty(zzaai.zzTC)) {
            jSONObject.put("gws_query_id", zzaai.zzTC);
        }
        jSONObject.put("fluid", zzaai.zzAw ? "height" : "");
        jSONObject.put("native_express", zzaai.zzAx);
        if (zzaai.zzTE != null) {
            jSONObject.put("video_start_urls", zzm(zzaai.zzTE));
        }
        if (zzaai.zzTF != null) {
            jSONObject.put("video_complete_urls", zzm(zzaai.zzTF));
        }
        if (zzaai.zzTD != null) {
            zzaee zzaee = zzaai.zzTD;
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("rb_type", zzaee.type);
            jSONObject2.put("rb_amount", zzaee.zzWW);
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(jSONObject2);
            jSONObject.put("rewards", jSONArray);
        }
        jSONObject.put("use_displayed_impression", zzaai.zzTG);
        jSONObject.put("auto_protection_configuration", zzaai.zzTH);
        jSONObject.put("render_in_browser", zzaai.zzMe);
        return jSONObject;
    }

    @Nullable
    private static JSONArray zzm(List<String> list) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (String put : list) {
            jSONArray.put(put);
        }
        return jSONArray;
    }

    private static Integer zzt(boolean z) {
        return Integer.valueOf(z ? 1 : 0);
    }

    private static String zzu(int i) {
        return String.format(Locale.US, "#%06x", new Object[]{Integer.valueOf(16777215 & i)});
    }
}
