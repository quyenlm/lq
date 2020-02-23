package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.common.internal.zzbo;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class cb {
    public static zzcxn zzO(Object obj) throws JSONException {
        String string;
        JSONArray jSONArray;
        JSONArray jSONArray2;
        if (obj instanceof JSONObject) {
            JSONObject jSONObject = (JSONObject) obj;
            String string2 = jSONObject.getString("name");
            JSONArray jSONArray3 = jSONObject.getJSONArray(NativeProtocol.WEB_DIALOG_PARAMS);
            jSONArray = jSONObject.getJSONArray("instructions");
            jSONArray2 = jSONArray3;
            string = string2;
        } else if (obj instanceof JSONArray) {
            JSONArray jSONArray4 = (JSONArray) obj;
            zzbo.zzaf(jSONArray4.length() >= 3);
            string = jSONArray4.getString(1);
            JSONArray jSONArray5 = jSONArray4.getJSONArray(2);
            JSONArray jSONArray6 = new JSONArray();
            for (int i = 1; i < jSONArray5.length(); i++) {
                zzbo.zzaf(jSONArray5.get(i) instanceof String);
                jSONArray6.put(jSONArray5.get(i));
            }
            JSONArray jSONArray7 = new JSONArray();
            for (int i2 = 3; i2 < jSONArray4.length(); i2++) {
                jSONArray7.put(jSONArray4.get(i2));
            }
            jSONArray = jSONArray7;
            jSONArray2 = jSONArray6;
        } else {
            throw new IllegalArgumentException("invalid JSON in runtime section");
        }
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
            arrayList.add(jSONArray2.getString(i3));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i4 = 0; i4 < jSONArray.length(); i4++) {
            JSONArray jSONArray8 = jSONArray.getJSONArray(i4);
            if (jSONArray8.length() != 0) {
                arrayList2.add(zzd(jSONArray8));
            }
        }
        return new zzcxn((zzcwa) null, string, arrayList, arrayList2);
    }

    private static dg zza(JSONArray jSONArray, List<dd> list, List<dd> list2) throws ca, JSONException {
        di diVar = new di();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONArray jSONArray2 = jSONArray.getJSONArray(i);
            if (jSONArray2.getString(0).equals("if")) {
                for (int i2 = 1; i2 < jSONArray2.length(); i2++) {
                    diVar.zzc(list2.get(jSONArray2.getInt(i2)));
                }
            } else if (jSONArray2.getString(0).equals("unless")) {
                for (int i3 = 1; i3 < jSONArray2.length(); i3++) {
                    diVar.zzd(list2.get(jSONArray2.getInt(i3)));
                }
            } else if (jSONArray2.getString(0).equals(ProductAction.ACTION_ADD)) {
                for (int i4 = 1; i4 < jSONArray2.length(); i4++) {
                    diVar.zze(list.get(jSONArray2.getInt(i4)));
                }
            } else if (jSONArray2.getString(0).equals("block")) {
                for (int i5 = 1; i5 < jSONArray2.length(); i5++) {
                    diVar.zzf(list.get(jSONArray2.getInt(i5)));
                }
            } else {
                String valueOf = String.valueOf(jSONArray2.getString(0));
                zzfQ(valueOf.length() != 0 ? "Unknown Rule property: ".concat(valueOf) : new String("Unknown Rule property: "));
            }
        }
        return diVar.zzDf();
    }

    private static dd zzb(JSONObject jSONObject, List<String> list) throws ca, JSONException {
        df dfVar = new df();
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            dm zzDj = zzb(jSONObject.get(next), list).zzDj();
            if ("push_after_evaluate".equals(next)) {
                dfVar.zzb(zzDj);
            } else {
                dfVar.zza(next, zzDj);
            }
        }
        return dfVar.zzDa();
    }

    private static Cdo zzb(Object obj, List<String> list) throws ca, JSONException {
        if (obj instanceof JSONArray) {
            JSONArray jSONArray = (JSONArray) obj;
            String string = jSONArray.getString(0);
            if (string.equals("escape")) {
                Cdo zzb = zzb(jSONArray.get(1), list);
                for (int i = 2; i < jSONArray.length(); i++) {
                    zzb.zzbF(jSONArray.getInt(i));
                }
                return zzb;
            } else if (string.equals(HttpRequestParams.NOTICE_LIST)) {
                ArrayList arrayList = new ArrayList();
                for (int i2 = 1; i2 < jSONArray.length(); i2++) {
                    arrayList.add(zzb(jSONArray.get(i2), list).zzDj());
                }
                Cdo doVar = new Cdo(2, arrayList);
                doVar.zzau(true);
                return doVar;
            } else if (string.equals("map")) {
                HashMap hashMap = new HashMap();
                for (int i3 = 1; i3 < jSONArray.length(); i3 += 2) {
                    hashMap.put(zzb(jSONArray.get(i3), list).zzDj(), zzb(jSONArray.get(i3 + 1), list).zzDj());
                }
                Cdo doVar2 = new Cdo(3, hashMap);
                doVar2.zzau(true);
                return doVar2;
            } else if (string.equals("macro")) {
                Cdo doVar3 = new Cdo(4, list.get(jSONArray.getInt(1)));
                doVar3.zzau(true);
                return doVar3;
            } else if (string.equals(MessengerShareContentUtility.ATTACHMENT_TEMPLATE_TYPE)) {
                ArrayList arrayList2 = new ArrayList();
                for (int i4 = 1; i4 < jSONArray.length(); i4++) {
                    arrayList2.add(zzb(jSONArray.get(i4), list).zzDj());
                }
                Cdo doVar4 = new Cdo(7, arrayList2);
                doVar4.zzau(true);
                return doVar4;
            } else {
                String valueOf = String.valueOf(obj);
                zzfQ(new StringBuilder(String.valueOf(valueOf).length() + 20).append("Invalid value type: ").append(valueOf).toString());
                return null;
            }
        } else if (obj instanceof Boolean) {
            return new Cdo(8, obj);
        } else {
            if (obj instanceof Integer) {
                return new Cdo(6, obj);
            }
            if (obj instanceof String) {
                return new Cdo(1, obj);
            }
            String valueOf2 = String.valueOf(obj);
            zzfQ(new StringBuilder(String.valueOf(valueOf2).length() + 20).append("Invalid value type: ").append(valueOf2).toString());
            return null;
        }
    }

    private static List<dd> zzb(JSONArray jSONArray, List<String> list) throws JSONException, ca {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(zzb(jSONArray.getJSONObject(i), list));
        }
        return arrayList;
    }

    private static List<String> zzc(JSONArray jSONArray) throws JSONException {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(jSONArray.getJSONObject(i).getString("instance_name"));
        }
        return arrayList;
    }

    private static ea zzd(JSONArray jSONArray) throws JSONException {
        zzbo.zzaf(jSONArray.length() > 0);
        String string = jSONArray.getString(0);
        ArrayList arrayList = new ArrayList();
        for (int i = 1; i < jSONArray.length(); i++) {
            Object obj = jSONArray.get(i);
            if (obj instanceof JSONArray) {
                JSONArray jSONArray2 = (JSONArray) obj;
                if (jSONArray2.length() != 0) {
                    arrayList.add(zzd(jSONArray2));
                }
            } else if (obj == JSONObject.NULL) {
                arrayList.add(dv.zzbLt);
            } else {
                arrayList.add(ed.zzQ(obj));
            }
        }
        return new ea(string, arrayList);
    }

    static db zzfO(String str) throws JSONException, ca {
        Object obj = new JSONObject(str).get("resource");
        if (obj instanceof JSONObject) {
            JSONObject jSONObject = (JSONObject) obj;
            dc dcVar = new dc();
            dcVar.zzfW(jSONObject.optString("version"));
            List<String> zzc = zzc(jSONObject.getJSONArray("macros"));
            List<dd> zzb = zzb(jSONObject.getJSONArray("tags"), zzc);
            List<dd> zzb2 = zzb(jSONObject.getJSONArray("predicates"), zzc);
            for (dd zzb3 : zzb(jSONObject.getJSONArray("macros"), zzc)) {
                dcVar.zzb(zzb3);
            }
            JSONArray jSONArray = jSONObject.getJSONArray("rules");
            for (int i = 0; i < jSONArray.length(); i++) {
                dcVar.zza(zza(jSONArray.getJSONArray(i), zzb, zzb2));
            }
            return dcVar.zzCY();
        }
        throw new ca("Resource map not found");
    }

    @Nullable
    static dj zzfP(String str) throws JSONException, ca {
        JSONObject jSONObject = new JSONObject(str);
        JSONArray optJSONArray = jSONObject.optJSONArray("runtime");
        if (optJSONArray == null) {
            return null;
        }
        dl dlVar = new dl();
        Object obj = jSONObject.get("resource");
        if (obj instanceof JSONObject) {
            dlVar.zzfX(((JSONObject) obj).optString("version"));
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= optJSONArray.length()) {
                    return dlVar.zzDh();
                }
                Object obj2 = optJSONArray.get(i2);
                if (!(obj2 instanceof JSONArray) || ((JSONArray) obj2).length() != 0) {
                    dlVar.zza(zzO(obj2));
                }
                i = i2 + 1;
            }
        } else {
            throw new ca("Resource map not found");
        }
    }

    private static void zzfQ(String str) throws ca {
        zzcvk.e(str);
        throw new ca(str);
    }
}
