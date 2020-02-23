package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import bolts.MeasurementEvent;
import com.amazonaws.services.s3.internal.Constants;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.measurement.AppMeasurement;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.tp.a.h;
import com.vk.sdk.api.VKApiConst;

public final class zzcfj extends zzchj {
    private static String[] zzbqI = new String[AppMeasurement.Event.zzbof.length];
    private static String[] zzbqJ = new String[AppMeasurement.Param.zzboh.length];
    private static String[] zzbqK = new String[AppMeasurement.UserProperty.zzbom.length];

    zzcfj(zzcgl zzcgl) {
        super(zzcgl);
    }

    @Nullable
    private static String zza(String str, String[] strArr, String[] strArr2, String[] strArr3) {
        boolean z = true;
        int i = 0;
        zzbo.zzu(strArr);
        zzbo.zzu(strArr2);
        zzbo.zzu(strArr3);
        zzbo.zzaf(strArr.length == strArr2.length);
        if (strArr.length != strArr3.length) {
            z = false;
        }
        zzbo.zzaf(z);
        while (true) {
            if (i >= strArr.length) {
                break;
            } else if (zzcjl.zzR(str, strArr[i])) {
                synchronized (strArr3) {
                    if (strArr3[i] == null) {
                        strArr3[i] = strArr2[i] + h.a + strArr[i] + h.b;
                    }
                    str = strArr3[i];
                }
            } else {
                i++;
            }
        }
        return str;
    }

    private static void zza(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("  ");
        }
    }

    private final void zza(StringBuilder sb, int i, zzcjo zzcjo) {
        if (zzcjo != null) {
            zza(sb, i);
            sb.append("filter {\n");
            zza(sb, i, "complement", (Object) zzcjo.zzbuU);
            zza(sb, i, "param_name", (Object) zzdX(zzcjo.zzbuV));
            int i2 = i + 1;
            zzcjr zzcjr = zzcjo.zzbuS;
            if (zzcjr != null) {
                zza(sb, i2);
                sb.append("string_filter");
                sb.append(" {\n");
                if (zzcjr.zzbve != null) {
                    String str = "UNKNOWN_MATCH_TYPE";
                    switch (zzcjr.zzbve.intValue()) {
                        case 1:
                            str = "REGEXP";
                            break;
                        case 2:
                            str = "BEGINS_WITH";
                            break;
                        case 3:
                            str = "ENDS_WITH";
                            break;
                        case 4:
                            str = "PARTIAL";
                            break;
                        case 5:
                            str = "EXACT";
                            break;
                        case 6:
                            str = "IN_LIST";
                            break;
                    }
                    zza(sb, i2, "match_type", (Object) str);
                }
                zza(sb, i2, "expression", (Object) zzcjr.zzbvf);
                zza(sb, i2, "case_sensitive", (Object) zzcjr.zzbvg);
                if (zzcjr.zzbvh.length > 0) {
                    zza(sb, i2 + 1);
                    sb.append("expression_list {\n");
                    for (String append : zzcjr.zzbvh) {
                        zza(sb, i2 + 2);
                        sb.append(append);
                        sb.append("\n");
                    }
                    sb.append("}\n");
                }
                zza(sb, i2);
                sb.append("}\n");
            }
            zza(sb, i + 1, "number_filter", zzcjo.zzbuT);
            zza(sb, i);
            sb.append("}\n");
        }
    }

    private final void zza(StringBuilder sb, int i, String str, zzcjp zzcjp) {
        if (zzcjp != null) {
            zza(sb, i);
            sb.append(str);
            sb.append(" {\n");
            if (zzcjp.zzbuW != null) {
                String str2 = "UNKNOWN_COMPARISON_TYPE";
                switch (zzcjp.zzbuW.intValue()) {
                    case 1:
                        str2 = "LESS_THAN";
                        break;
                    case 2:
                        str2 = "GREATER_THAN";
                        break;
                    case 3:
                        str2 = "EQUAL";
                        break;
                    case 4:
                        str2 = "BETWEEN";
                        break;
                }
                zza(sb, i, "comparison_type", (Object) str2);
            }
            zza(sb, i, "match_as_float", (Object) zzcjp.zzbuX);
            zza(sb, i, "comparison_value", (Object) zzcjp.zzbuY);
            zza(sb, i, "min_comparison_value", (Object) zzcjp.zzbuZ);
            zza(sb, i, "max_comparison_value", (Object) zzcjp.zzbva);
            zza(sb, i);
            sb.append("}\n");
        }
    }

    private static void zza(StringBuilder sb, int i, String str, zzcka zzcka) {
        if (zzcka != null) {
            int i2 = i + 1;
            zza(sb, i2);
            sb.append(str);
            sb.append(" {\n");
            if (zzcka.zzbwf != null) {
                zza(sb, i2 + 1);
                sb.append("results: ");
                long[] jArr = zzcka.zzbwf;
                int length = jArr.length;
                int i3 = 0;
                int i4 = 0;
                while (i3 < length) {
                    Long valueOf = Long.valueOf(jArr[i3]);
                    int i5 = i4 + 1;
                    if (i4 != 0) {
                        sb.append(", ");
                    }
                    sb.append(valueOf);
                    i3++;
                    i4 = i5;
                }
                sb.append(10);
            }
            if (zzcka.zzbwe != null) {
                zza(sb, i2 + 1);
                sb.append("status: ");
                long[] jArr2 = zzcka.zzbwe;
                int length2 = jArr2.length;
                int i6 = 0;
                int i7 = 0;
                while (i6 < length2) {
                    Long valueOf2 = Long.valueOf(jArr2[i6]);
                    int i8 = i7 + 1;
                    if (i7 != 0) {
                        sb.append(", ");
                    }
                    sb.append(valueOf2);
                    i6++;
                    i7 = i8;
                }
                sb.append(10);
            }
            zza(sb, i2);
            sb.append("}\n");
        }
    }

    private static void zza(StringBuilder sb, int i, String str, Object obj) {
        if (obj != null) {
            zza(sb, i + 1);
            sb.append(str);
            sb.append(": ");
            sb.append(obj);
            sb.append(10);
        }
    }

    private final void zza(StringBuilder sb, int i, zzcjv[] zzcjvArr) {
        if (zzcjvArr != null) {
            for (zzcjv zzcjv : zzcjvArr) {
                if (zzcjv != null) {
                    zza(sb, 2);
                    sb.append("audience_membership {\n");
                    zza(sb, 2, "audience_id", (Object) zzcjv.zzbuI);
                    zza(sb, 2, "new_audience", (Object) zzcjv.zzbvu);
                    zza(sb, 2, "current_data", zzcjv.zzbvs);
                    zza(sb, 2, "previous_data", zzcjv.zzbvt);
                    zza(sb, 2);
                    sb.append("}\n");
                }
            }
        }
    }

    private final void zza(StringBuilder sb, int i, zzcjw[] zzcjwArr) {
        if (zzcjwArr != null) {
            for (zzcjw zzcjw : zzcjwArr) {
                if (zzcjw != null) {
                    zza(sb, 2);
                    sb.append("event {\n");
                    zza(sb, 2, "name", (Object) zzdW(zzcjw.name));
                    zza(sb, 2, "timestamp_millis", (Object) zzcjw.zzbvx);
                    zza(sb, 2, "previous_timestamp_millis", (Object) zzcjw.zzbvy);
                    zza(sb, 2, VKApiConst.COUNT, (Object) zzcjw.count);
                    zzcjx[] zzcjxArr = zzcjw.zzbvw;
                    if (zzcjxArr != null) {
                        for (zzcjx zzcjx : zzcjxArr) {
                            if (zzcjx != null) {
                                zza(sb, 3);
                                sb.append("param {\n");
                                zza(sb, 3, "name", (Object) zzdX(zzcjx.name));
                                zza(sb, 3, "string_value", (Object) zzcjx.zzaIF);
                                zza(sb, 3, "int_value", (Object) zzcjx.zzbvA);
                                zza(sb, 3, "double_value", (Object) zzcjx.zzbuB);
                                zza(sb, 3);
                                sb.append("}\n");
                            }
                        }
                    }
                    zza(sb, 2);
                    sb.append("}\n");
                }
            }
        }
    }

    private final void zza(StringBuilder sb, int i, zzckb[] zzckbArr) {
        if (zzckbArr != null) {
            for (zzckb zzckb : zzckbArr) {
                if (zzckb != null) {
                    zza(sb, 2);
                    sb.append("user_property {\n");
                    zza(sb, 2, "set_timestamp_millis", (Object) zzckb.zzbwh);
                    zza(sb, 2, "name", (Object) zzdY(zzckb.name));
                    zza(sb, 2, "string_value", (Object) zzckb.zzaIF);
                    zza(sb, 2, "int_value", (Object) zzckb.zzbvA);
                    zza(sb, 2, "double_value", (Object) zzckb.zzbuB);
                    zza(sb, 2);
                    sb.append("}\n");
                }
            }
        }
    }

    @Nullable
    private final String zzb(zzcew zzcew) {
        if (zzcew == null) {
            return null;
        }
        return !zzyw() ? zzcew.toString() : zzA(zzcew.zzyt());
    }

    private final boolean zzyw() {
        return this.zzboe.zzwF().zzz(3);
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final String zzA(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        if (!zzyw()) {
            return bundle.toString();
        }
        StringBuilder sb = new StringBuilder();
        for (String str : bundle.keySet()) {
            if (sb.length() != 0) {
                sb.append(", ");
            } else {
                sb.append("Bundle[{");
            }
            sb.append(zzdX(str));
            sb.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
            sb.append(bundle.get(str));
        }
        sb.append("}]");
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final String zza(zzceu zzceu) {
        if (zzceu == null) {
            return null;
        }
        if (!zzyw()) {
            return zzceu.toString();
        }
        return "Event{appId='" + zzceu.mAppId + "', name='" + zzdW(zzceu.mName) + "', params=" + zzb(zzceu.zzbpF) + "}";
    }

    /* access modifiers changed from: protected */
    public final String zza(zzcjn zzcjn) {
        if (zzcjn == null) {
            return Constants.NULL_VERSION_ID;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nevent_filter {\n");
        zza(sb, 0, "filter_id", (Object) zzcjn.zzbuM);
        zza(sb, 0, MeasurementEvent.MEASUREMENT_EVENT_NAME_KEY, (Object) zzdW(zzcjn.zzbuN));
        zza(sb, 1, "event_count_filter", zzcjn.zzbuQ);
        sb.append("  filters {\n");
        for (zzcjo zza : zzcjn.zzbuO) {
            zza(sb, 2, zza);
        }
        zza(sb, 1);
        sb.append("}\n}\n");
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public final String zza(zzcjq zzcjq) {
        if (zzcjq == null) {
            return Constants.NULL_VERSION_ID;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nproperty_filter {\n");
        zza(sb, 0, "filter_id", (Object) zzcjq.zzbuM);
        zza(sb, 0, "property_name", (Object) zzdY(zzcjq.zzbvc));
        zza(sb, 1, zzcjq.zzbvd);
        sb.append("}\n");
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public final String zza(zzcjy zzcjy) {
        StringBuilder sb = new StringBuilder();
        sb.append("\nbatch {\n");
        if (zzcjy.zzbvB != null) {
            for (zzcjz zzcjz : zzcjy.zzbvB) {
                if (!(zzcjz == null || zzcjz == null)) {
                    zza(sb, 1);
                    sb.append("bundle {\n");
                    zza(sb, 1, "protocol_version", (Object) zzcjz.zzbvD);
                    zza(sb, 1, "platform", (Object) zzcjz.zzbvL);
                    zza(sb, 1, "gmp_version", (Object) zzcjz.zzbvP);
                    zza(sb, 1, "uploading_gmp_version", (Object) zzcjz.zzbvQ);
                    zza(sb, 1, "config_version", (Object) zzcjz.zzbwb);
                    zza(sb, 1, "gmp_app_id", (Object) zzcjz.zzboQ);
                    zza(sb, 1, "app_id", (Object) zzcjz.zzaH);
                    zza(sb, 1, "app_version", (Object) zzcjz.zzbgW);
                    zza(sb, 1, "app_version_major", (Object) zzcjz.zzbvY);
                    zza(sb, 1, "firebase_instance_id", (Object) zzcjz.zzboY);
                    zza(sb, 1, "dev_cert_hash", (Object) zzcjz.zzbvU);
                    zza(sb, 1, "app_store", (Object) zzcjz.zzboR);
                    zza(sb, 1, "upload_timestamp_millis", (Object) zzcjz.zzbvG);
                    zza(sb, 1, "start_timestamp_millis", (Object) zzcjz.zzbvH);
                    zza(sb, 1, "end_timestamp_millis", (Object) zzcjz.zzbvI);
                    zza(sb, 1, "previous_bundle_start_timestamp_millis", (Object) zzcjz.zzbvJ);
                    zza(sb, 1, "previous_bundle_end_timestamp_millis", (Object) zzcjz.zzbvK);
                    zza(sb, 1, "app_instance_id", (Object) zzcjz.zzbvT);
                    zza(sb, 1, "resettable_device_id", (Object) zzcjz.zzbvR);
                    zza(sb, 1, "limited_ad_tracking", (Object) zzcjz.zzbvS);
                    zza(sb, 1, "os_version", (Object) zzcjz.zzaY);
                    zza(sb, 1, "device_model", (Object) zzcjz.zzbvM);
                    zza(sb, 1, "user_default_language", (Object) zzcjz.zzbvN);
                    zza(sb, 1, "time_zone_offset_minutes", (Object) zzcjz.zzbvO);
                    zza(sb, 1, "bundle_sequential_index", (Object) zzcjz.zzbvV);
                    zza(sb, 1, "service_upload", (Object) zzcjz.zzbvW);
                    zza(sb, 1, "health_monitor", (Object) zzcjz.zzboU);
                    if (zzcjz.zzbwc.longValue() != 0) {
                        zza(sb, 1, "android_id", (Object) zzcjz.zzbwc);
                    }
                    zza(sb, 1, zzcjz.zzbvF);
                    zza(sb, 1, zzcjz.zzbvX);
                    zza(sb, 1, zzcjz.zzbvE);
                    zza(sb, 1);
                    sb.append("}\n");
                }
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final String zzb(zzcez zzcez) {
        if (zzcez == null) {
            return null;
        }
        if (!zzyw()) {
            return zzcez.toString();
        }
        return "origin=" + zzcez.zzbpc + ",name=" + zzdW(zzcez.name) + ",params=" + zzb(zzcez.zzbpM);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final String zzdW(String str) {
        if (str == null) {
            return null;
        }
        return zzyw() ? zza(str, AppMeasurement.Event.zzbog, AppMeasurement.Event.zzbof, zzbqI) : str;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final String zzdX(String str) {
        if (str == null) {
            return null;
        }
        return zzyw() ? zza(str, AppMeasurement.Param.zzboi, AppMeasurement.Param.zzboh, zzbqJ) : str;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final String zzdY(String str) {
        if (str == null) {
            return null;
        }
        if (!zzyw()) {
            return str;
        }
        if (!str.startsWith("_exp_")) {
            return zza(str, AppMeasurement.UserProperty.zzbon, AppMeasurement.UserProperty.zzbom, zzbqK);
        }
        return "experiment_id" + h.a + str + h.b;
    }

    public final /* bridge */ /* synthetic */ void zzjC() {
        super.zzjC();
    }

    /* access modifiers changed from: protected */
    public final void zzjD() {
    }

    public final /* bridge */ /* synthetic */ zze zzkq() {
        return super.zzkq();
    }

    public final /* bridge */ /* synthetic */ zzcfj zzwA() {
        return super.zzwA();
    }

    public final /* bridge */ /* synthetic */ zzcjl zzwB() {
        return super.zzwB();
    }

    public final /* bridge */ /* synthetic */ zzcgf zzwC() {
        return super.zzwC();
    }

    public final /* bridge */ /* synthetic */ zzcja zzwD() {
        return super.zzwD();
    }

    public final /* bridge */ /* synthetic */ zzcgg zzwE() {
        return super.zzwE();
    }

    public final /* bridge */ /* synthetic */ zzcfl zzwF() {
        return super.zzwF();
    }

    public final /* bridge */ /* synthetic */ zzcfw zzwG() {
        return super.zzwG();
    }

    public final /* bridge */ /* synthetic */ zzcem zzwH() {
        return super.zzwH();
    }

    public final /* bridge */ /* synthetic */ void zzwo() {
        super.zzwo();
    }

    public final /* bridge */ /* synthetic */ void zzwp() {
        super.zzwp();
    }

    public final /* bridge */ /* synthetic */ void zzwq() {
        super.zzwq();
    }

    public final /* bridge */ /* synthetic */ zzcec zzwr() {
        return super.zzwr();
    }

    public final /* bridge */ /* synthetic */ zzcej zzws() {
        return super.zzws();
    }

    public final /* bridge */ /* synthetic */ zzchl zzwt() {
        return super.zzwt();
    }

    public final /* bridge */ /* synthetic */ zzcfg zzwu() {
        return super.zzwu();
    }

    public final /* bridge */ /* synthetic */ zzcet zzwv() {
        return super.zzwv();
    }

    public final /* bridge */ /* synthetic */ zzcid zzww() {
        return super.zzww();
    }

    public final /* bridge */ /* synthetic */ zzchz zzwx() {
        return super.zzwx();
    }

    public final /* bridge */ /* synthetic */ zzcfh zzwy() {
        return super.zzwy();
    }

    public final /* bridge */ /* synthetic */ zzcen zzwz() {
        return super.zzwz();
    }
}
