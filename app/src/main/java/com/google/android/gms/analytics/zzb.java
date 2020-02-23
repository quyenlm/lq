package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.appsflyer.AppsFlyerLib;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzalk;
import com.google.android.gms.internal.zzall;
import com.google.android.gms.internal.zzalm;
import com.google.android.gms.internal.zzaln;
import com.google.android.gms.internal.zzalo;
import com.google.android.gms.internal.zzalp;
import com.google.android.gms.internal.zzalq;
import com.google.android.gms.internal.zzalr;
import com.google.android.gms.internal.zzals;
import com.google.android.gms.internal.zzalt;
import com.google.android.gms.internal.zzalu;
import com.google.android.gms.internal.zzalv;
import com.google.android.gms.internal.zzalw;
import com.google.android.gms.internal.zzamg;
import com.google.android.gms.internal.zzami;
import com.google.android.gms.internal.zzamj;
import com.google.android.gms.internal.zzamm;
import com.google.android.gms.internal.zzanx;
import com.google.android.gms.internal.zzaos;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.vk.sdk.api.VKApiConst;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class zzb extends zzamg implements zzo {
    private static DecimalFormat zzadn;
    private final zzamj zzadj;
    private final String zzado;
    private final Uri zzadp;
    private final boolean zzadq;
    private final boolean zzadr;

    public zzb(zzamj zzamj, String str) {
        this(zzamj, str, true, false);
    }

    private zzb(zzamj zzamj, String str, boolean z, boolean z2) {
        super(zzamj);
        zzbo.zzcF(str);
        this.zzadj = zzamj;
        this.zzado = str;
        this.zzadq = true;
        this.zzadr = false;
        this.zzadp = zzaZ(this.zzado);
    }

    private static void zza(Map<String, String> map, String str, double d) {
        if (d != FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            map.put(str, zzb(d));
        }
    }

    private static void zza(Map<String, String> map, String str, int i, int i2) {
        if (i > 0 && i2 > 0) {
            map.put(str, new StringBuilder(23).append(i).append("x").append(i2).toString());
        }
    }

    private static void zza(Map<String, String> map, String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            map.put(str, str2);
        }
    }

    private static void zza(Map<String, String> map, String str, boolean z) {
        if (z) {
            map.put(str, "1");
        }
    }

    static Uri zzaZ(String str) {
        zzbo.zzcF(str);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(ShareConstants.MEDIA_URI);
        builder.authority("google-analytics.com");
        builder.path(str);
        return builder.build();
    }

    private static String zzb(double d) {
        if (zzadn == null) {
            zzadn = new DecimalFormat("0.######");
        }
        return zzadn.format(d);
    }

    private static Map<String, String> zzc(zzi zzi) {
        String valueOf;
        HashMap hashMap = new HashMap();
        zzalo zzalo = (zzalo) zzi.zza(zzalo.class);
        if (zzalo != null) {
            for (Map.Entry next : zzalo.zzjR().entrySet()) {
                Object value = next.getValue();
                if (value == null) {
                    valueOf = null;
                } else if (value instanceof String) {
                    valueOf = (String) value;
                    if (TextUtils.isEmpty(valueOf)) {
                        valueOf = null;
                    }
                } else if (value instanceof Double) {
                    Double d = (Double) value;
                    valueOf = d.doubleValue() != FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? zzb(d.doubleValue()) : null;
                } else {
                    valueOf = value instanceof Boolean ? value != Boolean.FALSE ? "1" : null : String.valueOf(value);
                }
                if (valueOf != null) {
                    hashMap.put((String) next.getKey(), valueOf);
                }
            }
        }
        zzalt zzalt = (zzalt) zzi.zza(zzalt.class);
        if (zzalt != null) {
            zza((Map<String, String>) hashMap, "t", zzalt.zzjW());
            zza((Map<String, String>) hashMap, "cid", zzalt.zzjX());
            zza((Map<String, String>) hashMap, GGLiveConstants.PARAM.UID, zzalt.getUserId());
            zza((Map<String, String>) hashMap, "sc", zzalt.zzka());
            zza((Map<String, String>) hashMap, "sf", zzalt.zzkc());
            zza((Map<String, String>) hashMap, "ni", zzalt.zzkb());
            zza((Map<String, String>) hashMap, HttpRequestParams.AD_ID, zzalt.zzjY());
            zza((Map<String, String>) hashMap, "ate", zzalt.zzjZ());
        }
        zzalu zzalu = (zzalu) zzi.zza(zzalu.class);
        if (zzalu != null) {
            zza((Map<String, String>) hashMap, "cd", zzalu.zzkd());
            zza((Map<String, String>) hashMap, APDataReportManager.GAMEANDMONTHSLIST_PRE, (double) zzalu.zzke());
            zza((Map<String, String>) hashMap, "dr", zzalu.zzkf());
        }
        zzalr zzalr = (zzalr) zzi.zza(zzalr.class);
        if (zzalr != null) {
            zza((Map<String, String>) hashMap, "ec", zzalr.getCategory());
            zza((Map<String, String>) hashMap, "ea", zzalr.getAction());
            zza((Map<String, String>) hashMap, "el", zzalr.getLabel());
            zza((Map<String, String>) hashMap, "ev", (double) zzalr.getValue());
        }
        zzall zzall = (zzall) zzi.zza(zzall.class);
        if (zzall != null) {
            zza((Map<String, String>) hashMap, "cn", zzall.getName());
            zza((Map<String, String>) hashMap, "cs", zzall.getSource());
            zza((Map<String, String>) hashMap, "cm", zzall.zzjJ());
            zza((Map<String, String>) hashMap, "ck", zzall.zzjK());
            zza((Map<String, String>) hashMap, "cc", zzall.getContent());
            zza((Map<String, String>) hashMap, "ci", zzall.getId());
            zza((Map<String, String>) hashMap, "anid", zzall.zzjL());
            zza((Map<String, String>) hashMap, "gclid", zzall.zzjM());
            zza((Map<String, String>) hashMap, "dclid", zzall.zzjN());
            zza((Map<String, String>) hashMap, FirebaseAnalytics.Param.ACLID, zzall.zzjO());
        }
        zzals zzals = (zzals) zzi.zza(zzals.class);
        if (zzals != null) {
            zza((Map<String, String>) hashMap, "exd", zzals.zzafa);
            zza((Map<String, String>) hashMap, "exf", zzals.zzafb);
        }
        zzalv zzalv = (zzalv) zzi.zza(zzalv.class);
        if (zzalv != null) {
            zza((Map<String, String>) hashMap, "sn", zzalv.zzafq);
            zza((Map<String, String>) hashMap, "sa", zzalv.zzaeX);
            zza((Map<String, String>) hashMap, "st", zzalv.zzafr);
        }
        zzalw zzalw = (zzalw) zzi.zza(zzalw.class);
        if (zzalw != null) {
            zza((Map<String, String>) hashMap, "utv", zzalw.zzafs);
            zza((Map<String, String>) hashMap, "utt", (double) zzalw.zzaft);
            zza((Map<String, String>) hashMap, "utc", zzalw.mCategory);
            zza((Map<String, String>) hashMap, "utl", zzalw.zzaeY);
        }
        zzalm zzalm = (zzalm) zzi.zza(zzalm.class);
        if (zzalm != null) {
            for (Map.Entry next2 : zzalm.zzjP().entrySet()) {
                String zzD = zzf.zzD(((Integer) next2.getKey()).intValue());
                if (!TextUtils.isEmpty(zzD)) {
                    hashMap.put(zzD, (String) next2.getValue());
                }
            }
        }
        zzaln zzaln = (zzaln) zzi.zza(zzaln.class);
        if (zzaln != null) {
            for (Map.Entry next3 : zzaln.zzjQ().entrySet()) {
                String zzF = zzf.zzF(((Integer) next3.getKey()).intValue());
                if (!TextUtils.isEmpty(zzF)) {
                    hashMap.put(zzF, zzb(((Double) next3.getValue()).doubleValue()));
                }
            }
        }
        zzalq zzalq = (zzalq) zzi.zza(zzalq.class);
        if (zzalq != null) {
            ProductAction zzjS = zzalq.zzjS();
            if (zzjS != null) {
                for (Map.Entry next4 : zzjS.build().entrySet()) {
                    if (((String) next4.getKey()).startsWith(HttpRequest.HTTP_REQ_ENTITY_JOIN)) {
                        hashMap.put(((String) next4.getKey()).substring(1), (String) next4.getValue());
                    } else {
                        hashMap.put((String) next4.getKey(), (String) next4.getValue());
                    }
                }
            }
            int i = 1;
            for (Promotion zzbl : zzalq.zzjV()) {
                hashMap.putAll(zzbl.zzbl(zzf.zzJ(i)));
                i++;
            }
            int i2 = 1;
            for (Product zzbl2 : zzalq.zzjT()) {
                hashMap.putAll(zzbl2.zzbl(zzf.zzH(i2)));
                i2++;
            }
            int i3 = 1;
            for (Map.Entry next5 : zzalq.zzjU().entrySet()) {
                String zzM = zzf.zzM(i3);
                int i4 = 1;
                for (Product product : (List) next5.getValue()) {
                    String valueOf2 = String.valueOf(zzM);
                    String valueOf3 = String.valueOf(zzf.zzK(i4));
                    hashMap.putAll(product.zzbl(valueOf3.length() != 0 ? valueOf2.concat(valueOf3) : new String(valueOf2)));
                    i4++;
                }
                if (!TextUtils.isEmpty((CharSequence) next5.getKey())) {
                    String valueOf4 = String.valueOf(zzM);
                    String valueOf5 = String.valueOf("nm");
                    hashMap.put(valueOf5.length() != 0 ? valueOf4.concat(valueOf5) : new String(valueOf4), (String) next5.getKey());
                }
                i3++;
            }
        }
        zzalp zzalp = (zzalp) zzi.zza(zzalp.class);
        if (zzalp != null) {
            zza((Map<String, String>) hashMap, "ul", zzalp.getLanguage());
            zza((Map<String, String>) hashMap, "sd", (double) zzalp.zzaeU);
            zza(hashMap, "sr", zzalp.zzNY, zzalp.zzNZ);
            zza(hashMap, "vp", zzalp.zzaeV, zzalp.zzaeW);
        }
        zzalk zzalk = (zzalk) zzi.zza(zzalk.class);
        if (zzalk != null) {
            zza((Map<String, String>) hashMap, "an", zzalk.zzjG());
            zza((Map<String, String>) hashMap, AppsFlyerLib.ATTRIBUTION_ID_COLUMN_NAME, zzalk.zzhl());
            zza((Map<String, String>) hashMap, "aiid", zzalk.zzjI());
            zza((Map<String, String>) hashMap, "av", zzalk.zzjH());
        }
        return hashMap;
    }

    public final void zzb(zzi zzi) {
        zzbo.zzu(zzi);
        zzbo.zzb(zzi.zzju(), (Object) "Can't deliver not submitted measurement");
        zzbo.zzcG("deliver should be called on worker thread");
        zzi zzjp = zzi.zzjp();
        zzalt zzalt = (zzalt) zzjp.zzb(zzalt.class);
        if (TextUtils.isEmpty(zzalt.zzjW())) {
            zzkr().zze(zzc(zzjp), "Ignoring measurement without type");
        } else if (TextUtils.isEmpty(zzalt.zzjX())) {
            zzkr().zze(zzc(zzjp), "Ignoring measurement without client id");
        } else if (!this.zzadj.zzkG().getAppOptOut()) {
            double zzkc = zzalt.zzkc();
            if (zzaos.zza(zzkc, zzalt.zzjX())) {
                zzb("Sampling enabled. Hit sampled out. sampling rate", Double.valueOf(zzkc));
                return;
            }
            Map<String, String> zzc = zzc(zzjp);
            zzc.put(VKApiConst.VERSION, "1");
            zzc.put("_v", zzami.zzafL);
            zzc.put("tid", this.zzado);
            if (this.zzadj.zzkG().isDryRunEnabled()) {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry next : zzc.entrySet()) {
                    if (sb.length() != 0) {
                        sb.append(", ");
                    }
                    sb.append((String) next.getKey());
                    sb.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
                    sb.append((String) next.getValue());
                }
                zzc("Dry run is enabled. GoogleAnalytics would have sent", sb.toString());
                return;
            }
            HashMap hashMap = new HashMap();
            zzaos.zzb((Map<String, String>) hashMap, GGLiveConstants.PARAM.UID, zzalt.getUserId());
            zzalk zzalk = (zzalk) zzi.zza(zzalk.class);
            if (zzalk != null) {
                zzaos.zzb((Map<String, String>) hashMap, "an", zzalk.zzjG());
                zzaos.zzb((Map<String, String>) hashMap, AppsFlyerLib.ATTRIBUTION_ID_COLUMN_NAME, zzalk.zzhl());
                zzaos.zzb((Map<String, String>) hashMap, "av", zzalk.zzjH());
                zzaos.zzb((Map<String, String>) hashMap, "aiid", zzalk.zzjI());
            }
            zzc.put("_s", String.valueOf(zzkv().zza(new zzamm(0, zzalt.zzjX(), this.zzado, !TextUtils.isEmpty(zzalt.zzjY()), 0, hashMap))));
            zzkv().zza(new zzanx(zzkr(), zzc, zzi.zzjs(), true));
        }
    }

    public final Uri zzjl() {
        return this.zzadp;
    }
}
