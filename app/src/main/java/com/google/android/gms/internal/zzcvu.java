package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.appsflyer.ServerParameters;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.tagmanager.zzce;
import com.google.android.gms.tagmanager.zzcn;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class zzcvu {
    private final Context mContext;
    private final String zzbDw;
    private int zzbGp;
    private final zzcn zzbHN;
    /* access modifiers changed from: private */
    public final zzce zzbHW;
    private final db zzbII;
    private final zzcwa zzbIJ = new zzcwa();
    private final dz zzbIK = new dz(new HashMap(50));
    private final dz zzbIL = new dz(new HashMap(10));
    private final Set<String> zzbIM = new HashSet();
    private ao zzbIN;
    /* access modifiers changed from: private */
    public zzcut zzbIO;
    private final zzcvy zzbIP = new zzcvv(this);

    public zzcvu(Context context, String str, db dbVar, dj djVar, zzcn zzcn, zzce zzce) {
        zzbo.zzb(dbVar, (Object) "Internal Error: Container resource cannot be null");
        zzbo.zzb(djVar, (Object) "Internal Error: Runtime resource cannot be null");
        zzbo.zzh(str, "Internal Error: ContainerId cannot be empty");
        zzbo.zzu(zzcn);
        zzbo.zzu(zzce);
        this.mContext = context;
        this.zzbDw = str;
        this.zzbII = dbVar;
        this.zzbHN = zzcn;
        this.zzbHW = zzce;
        this.zzbIJ.zza("1", new du(new zzcyn()));
        this.zzbIJ.zza("12", new du(new zzcyo()));
        this.zzbIJ.zza("18", new du(new zzcyp()));
        this.zzbIJ.zza("19", new du(new zzcyq()));
        this.zzbIJ.zza("20", new du(new zzcyr()));
        this.zzbIJ.zza("21", new du(new zzcys()));
        this.zzbIJ.zza("23", new du(new zzcyt()));
        this.zzbIJ.zza("24", new du(new zzcyu()));
        this.zzbIJ.zza("27", new du(new zzcyv()));
        this.zzbIJ.zza("28", new du(new zzcyw()));
        this.zzbIJ.zza("29", new du(new zzcyx()));
        this.zzbIJ.zza("30", new du(new zzcyy()));
        this.zzbIJ.zza("32", new du(new zzcyz()));
        this.zzbIJ.zza("33", new du(new zzcyz()));
        this.zzbIJ.zza("34", new du(new zzcza()));
        this.zzbIJ.zza("35", new du(new zzcza()));
        this.zzbIJ.zza("39", new du(new zzczb()));
        this.zzbIJ.zza("40", new du(new zzczc()));
        this.zzbIJ.zza("0", new du(new zzczz()));
        this.zzbIJ.zza("10", new du(new a()));
        this.zzbIJ.zza("25", new du(new b()));
        this.zzbIJ.zza("26", new du(new c()));
        this.zzbIJ.zza("37", new du(new d()));
        this.zzbIJ.zza("2", new du(new zzczd()));
        this.zzbIJ.zza("3", new du(new zzcze()));
        this.zzbIJ.zza("4", new du(new zzczf()));
        this.zzbIJ.zza("5", new du(new zzczg()));
        this.zzbIJ.zza("6", new du(new zzczh()));
        this.zzbIJ.zza("7", new du(new zzczi()));
        this.zzbIJ.zza("8", new du(new zzczj()));
        this.zzbIJ.zza("9", new du(new zzczg()));
        this.zzbIJ.zza("13", new du(new zzczk()));
        this.zzbIJ.zza("47", new du(new zzczl()));
        this.zzbIJ.zza("15", new du(new zzczm()));
        this.zzbIJ.zza("48", new du(new zzczn(this)));
        zzczo zzczo = new zzczo();
        this.zzbIJ.zza("16", new du(zzczo));
        this.zzbIJ.zza("17", new du(zzczo));
        this.zzbIJ.zza("22", new du(new zzczq()));
        this.zzbIJ.zza("45", new du(new zzczr()));
        this.zzbIJ.zza("46", new du(new zzczs()));
        this.zzbIJ.zza("36", new du(new zzczt()));
        this.zzbIJ.zza("43", new du(new zzczu()));
        this.zzbIJ.zza("38", new du(new zzczv()));
        this.zzbIJ.zza("44", new du(new zzczw()));
        this.zzbIJ.zza("41", new du(new zzczx()));
        this.zzbIJ.zza("42", new du(new zzczy()));
        zza(zzbf.CONTAINS, (zzcxo) new bl());
        zza(zzbf.ENDS_WITH, (zzcxo) new bm());
        zza(zzbf.EQUALS, (zzcxo) new bn());
        zza(zzbf.GREATER_EQUALS, (zzcxo) new bo());
        zza(zzbf.GREATER_THAN, (zzcxo) new bp());
        zza(zzbf.LESS_EQUALS, (zzcxo) new bq());
        zza(zzbf.LESS_THAN, (zzcxo) new br());
        zza(zzbf.REGEX, (zzcxo) new bt());
        zza(zzbf.STARTS_WITH, (zzcxo) new bu());
        this.zzbIK.zzc(ServerParameters.ADVERTISING_ID_PARAM, new du(new ae(this.mContext)));
        this.zzbIK.zzc("advertiserTrackingEnabled", new du(new af(this.mContext)));
        this.zzbIK.zzc("adwordsClickReferrer", new du(new ag(this.mContext, this.zzbIP)));
        this.zzbIK.zzc("applicationId", new du(new ah(this.mContext)));
        this.zzbIK.zzc("applicationName", new du(new ai(this.mContext)));
        this.zzbIK.zzc("applicationVersion", new du(new aj(this.mContext)));
        this.zzbIK.zzc("applicationVersionName", new du(new ak(this.mContext)));
        this.zzbIK.zzc("arbitraryPixieMacro", new du(new ab(1, this.zzbIJ)));
        this.zzbIK.zzc("carrier", new du(new al(this.mContext)));
        this.zzbIK.zzc("constant", new du(new zzczt()));
        this.zzbIK.zzc("containerId", new du(new am(new eb(this.zzbDw))));
        this.zzbIK.zzc("containerVersion", new du(new am(new eb(this.zzbII.getVersion()))));
        this.zzbIK.zzc("customMacro", new du(new z(new zzcvx(this, (zzcvv) null))));
        this.zzbIK.zzc("deviceBrand", new du(new ap()));
        this.zzbIK.zzc("deviceId", new du(new aq(this.mContext)));
        this.zzbIK.zzc("deviceModel", new du(new ar()));
        this.zzbIK.zzc("deviceName", new du(new as()));
        this.zzbIK.zzc("encode", new du(new at()));
        this.zzbIK.zzc("encrypt", new du(new au()));
        this.zzbIK.zzc("event", new du(new an()));
        this.zzbIK.zzc("eventParameters", new du(new av(this.zzbIP)));
        this.zzbIK.zzc("version", new du(new aw()));
        this.zzbIK.zzc("hashcode", new du(new ax()));
        this.zzbIK.zzc("installReferrer", new du(new ay(this.mContext)));
        this.zzbIK.zzc("join", new du(new az()));
        this.zzbIK.zzc("language", new du(new ba()));
        this.zzbIK.zzc("locale", new du(new bb()));
        this.zzbIK.zzc("adWordsUniqueId", new du(new bd(this.mContext)));
        this.zzbIK.zzc(HttpRequestParams.OS_VERSION, new du(new be()));
        this.zzbIK.zzc("platform", new du(new bf()));
        this.zzbIK.zzc("random", new du(new bg()));
        this.zzbIK.zzc("regexGroup", new du(new bh()));
        this.zzbIK.zzc(HttpRequestParams.RESOLUTION, new du(new bj(this.mContext)));
        this.zzbIK.zzc("runtimeVersion", new du(new bi()));
        this.zzbIK.zzc("sdkVersion", new du(new bk()));
        this.zzbIN = new ao();
        this.zzbIK.zzc("currentTime", new du(this.zzbIN));
        this.zzbIK.zzc("userProperty", new du(new bc(this.mContext, this.zzbIP)));
        this.zzbIK.zzc("arbitraryPixel", new du(new bx(zzcur.zzbv(this.mContext))));
        this.zzbIK.zzc("customTag", new du(new z(new zzcvw(this, (zzcvv) null))));
        this.zzbIK.zzc("universalAnalytics", new du(new by(this.mContext, this.zzbIP)));
        this.zzbIK.zzc("queueRequest", new du(new bv(zzcur.zzbv(this.mContext))));
        this.zzbIK.zzc("sendMeasurement", new du(new bw(this.zzbHN, this.zzbIP)));
        this.zzbIK.zzc("arbitraryPixieTag", new du(new ab(0, this.zzbIJ)));
        this.zzbIK.zzc("suppressPassthrough", new du(new ad(this.mContext, this.zzbIP)));
        this.zzbIL.zzc("decodeURI", new du(new u()));
        this.zzbIL.zzc("decodeURIComponent", new du(new v()));
        this.zzbIL.zzc("encodeURI", new du(new w()));
        this.zzbIL.zzc("encodeURIComponent", new du(new x()));
        this.zzbIL.zzc("log", new du(new ac()));
        this.zzbIL.zzc("isArray", new du(new y()));
        zza(djVar);
        dz dzVar = new dz(new HashMap(1));
        dzVar.zzc("mobile", this.zzbIK);
        dzVar.zzc("common", this.zzbIL);
        this.zzbIJ.zza("gtmUtils", dzVar);
        dz dzVar2 = new dz(new HashMap(this.zzbIK.zzDt()));
        dzVar2.zzDu();
        dz dzVar3 = new dz(new HashMap(this.zzbIL.zzDt()));
        dzVar3.zzDu();
        if (this.zzbIJ.has("main") && (this.zzbIJ.zzfK("main") instanceof du)) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(dzVar);
            ed.zza(this.zzbIJ, new ea("main", arrayList));
        }
        this.zzbIK.zzc("base", dzVar2);
        this.zzbIL.zzc("base", dzVar3);
        dzVar.zzDu();
        this.zzbIK.zzDu();
        this.zzbIL.zzDu();
    }

    private final String zzBL() {
        if (this.zzbGp <= 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(this.zzbGp));
        for (int i = 2; i < this.zzbGp; i++) {
            sb.append(' ');
        }
        sb.append(": ");
        return sb.toString();
    }

    private final dp<?> zza(dd ddVar) {
        this.zzbIM.clear();
        try {
            dp<?> zzw = zzw(zzv(ddVar.zzCZ()));
            if (zzw instanceof ds) {
                return zzw;
            }
            zzcup.zzc("Predicate must return a boolean value", this.mContext);
            return new ds(false);
        } catch (IllegalStateException e) {
            zzcvk.e("Error evaluating predicate.");
            return dv.zzbLt;
        }
    }

    private final dp<?> zza(dg dgVar, Map<dd, dp<?>> map) {
        String valueOf = String.valueOf(dgVar);
        zzcvk.v(new StringBuilder(String.valueOf(valueOf).length() + 19).append("Evaluating trigger ").append(valueOf).toString());
        for (dd next : dgVar.zzDc()) {
            dp<?> dpVar = map.get(next);
            if (dpVar == null) {
                dpVar = zza(next);
                map.put(next, dpVar);
            }
            dp<?> dpVar2 = dpVar;
            if (dpVar2 == dv.zzbLt) {
                return dv.zzbLt;
            }
            if (((ds) dpVar2).zzDn().booleanValue()) {
                return new ds(false);
            }
        }
        for (dd next2 : dgVar.zzDb()) {
            dp<?> dpVar3 = map.get(next2);
            if (dpVar3 == null) {
                dpVar3 = zza(next2);
                map.put(next2, dpVar3);
            }
            dp<?> dpVar4 = dpVar3;
            if (dpVar4 == dv.zzbLt) {
                return dv.zzbLt;
            }
            if (!((ds) dpVar4).zzDn().booleanValue()) {
                return new ds(false);
            }
        }
        return new ds(true);
    }

    private final dp<?> zza(dm dmVar) {
        switch (dmVar.getType()) {
            case 1:
                try {
                    return new dt(Double.valueOf(Double.parseDouble((String) dmVar.getValue())));
                } catch (NumberFormatException e) {
                    return new eb((String) dmVar.getValue());
                }
            case 2:
                List<dm> list = (List) dmVar.getValue();
                ArrayList arrayList = new ArrayList(list.size());
                for (dm zza : list) {
                    arrayList.add(zza(zza));
                }
                return new dw(arrayList);
            case 3:
                Map map = (Map) dmVar.getValue();
                HashMap hashMap = new HashMap(map.size());
                for (Map.Entry entry : map.entrySet()) {
                    hashMap.put(zzcxp.zzd(zza((dm) entry.getKey())), zza((dm) entry.getValue()));
                }
                return new dz(hashMap);
            case 4:
                dp<?> zzfI = zzfI((String) dmVar.getValue());
                return (!(zzfI instanceof eb) || dmVar.zzDi().isEmpty()) ? zzfI : new eb(zzd(((eb) zzfI).value(), dmVar.zzDi()));
            case 5:
                return new eb((String) dmVar.getValue());
            case 6:
                return new dt(Double.valueOf(((Integer) dmVar.getValue()).doubleValue()));
            case 7:
                StringBuilder sb = new StringBuilder();
                for (dm zza2 : (List) dmVar.getValue()) {
                    sb.append(zzcxp.zzd(zza(zza2)));
                }
                return new eb(sb.toString());
            case 8:
                return new ds((Boolean) dmVar.getValue());
            default:
                throw new IllegalStateException(new StringBuilder(52).append("Attempting to expand unknown Value type ").append(dmVar.getType()).append(".").toString());
        }
    }

    private final void zza(dj djVar) {
        for (zzcxn next : djVar.zzDg()) {
            next.zza(this.zzbIJ);
            this.zzbIJ.zza(next.getName(), new du(next));
        }
    }

    private final void zza(zzbf zzbf, zzcxo zzcxo) {
        this.zzbIK.zzc(zzcxl.zza(zzbf), new du(zzcxo));
    }

    private final String zzd(String str, List<Integer> list) {
        for (Integer intValue : list) {
            int intValue2 = intValue.intValue();
            switch (intValue2) {
                case 12:
                    str = zzfJ(str);
                    break;
                default:
                    zzcvk.e(new StringBuilder(39).append("Unsupported Value Escaping: ").append(intValue2).toString());
                    break;
            }
        }
        return str;
    }

    private final dp<?> zzfI(String str) {
        this.zzbGp++;
        String valueOf = String.valueOf(zzBL());
        zzcvk.v(new StringBuilder(String.valueOf(valueOf).length() + 31 + String.valueOf(str).length()).append(valueOf).append("Beginning to evaluate variable ").append(str).toString());
        if (this.zzbIM.contains(str)) {
            this.zzbGp--;
            String valueOf2 = String.valueOf(this.zzbIM.toString());
            throw new IllegalStateException(new StringBuilder(String.valueOf(str).length() + 77 + String.valueOf(valueOf2).length()).append("Macro cycle detected.  Current macro reference: ").append(str).append(". Previous macro references: ").append(valueOf2).toString());
        }
        this.zzbIM.add(str);
        dd zzfV = this.zzbII.zzfV(str);
        if (zzfV == null) {
            this.zzbGp--;
            this.zzbIM.remove(str);
            String valueOf3 = String.valueOf(zzBL());
            throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf3).length() + 36 + String.valueOf(str).length()).append(valueOf3).append("Attempting to resolve unknown macro ").append(str).toString());
        }
        dp<?> zzw = zzw(zzv(zzfV.zzCZ()));
        String valueOf4 = String.valueOf(zzBL());
        zzcvk.v(new StringBuilder(String.valueOf(valueOf4).length() + 25 + String.valueOf(str).length()).append(valueOf4).append("Done evaluating variable ").append(str).toString());
        this.zzbGp--;
        this.zzbIM.remove(str);
        return zzw;
    }

    private static String zzfJ(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            zzcvk.zzb("Escape URI: unsupported encoding", e);
            return str;
        }
    }

    private final ea zzg(String str, Map<String, dp<?>> map) {
        try {
            return zzcxl.zza(str, map, this.zzbIJ);
        } catch (RuntimeException e) {
            String valueOf = String.valueOf(e.getMessage());
            zzcvk.e(new StringBuilder(String.valueOf(str).length() + 30 + String.valueOf(valueOf).length()).append("Incorrect keys for function ").append(str).append(". ").append(valueOf).toString());
            return null;
        }
    }

    private final Map<String, dp<?>> zzv(Map<String, dm> map) {
        HashMap hashMap = new HashMap();
        for (Map.Entry next : map.entrySet()) {
            hashMap.put((String) next.getKey(), zza((dm) next.getValue()));
        }
        return hashMap;
    }

    private final dp zzw(Map<String, dp<?>> map) {
        ea zzg;
        if (map == null) {
            zzcup.zzc("executeFunctionCall: cannot access the function parameters.", this.mContext);
            return dv.zzbLu;
        }
        dp dpVar = map.get(zzbg.FUNCTION.toString());
        if (!(dpVar instanceof eb)) {
            zzcup.zzc("No function id in properties", this.mContext);
            return dv.zzbLu;
        }
        String value = ((eb) dpVar).value();
        if (this.zzbIJ.has(value)) {
            HashMap hashMap = new HashMap();
            for (Map.Entry next : map.entrySet()) {
                if (((String) next.getKey()).startsWith("vtp_")) {
                    hashMap.put(((String) next.getKey()).substring(4), (dp) next.getValue());
                }
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(new dz(hashMap));
            zzg = new ea(value, arrayList);
        } else {
            String zzfM = zzcxl.zzfM(value);
            if (zzfM != null && this.zzbIK.zzfY(zzfM)) {
                zzg = zzg(value, map);
            } else {
                zzcup.zzc(new StringBuilder(String.valueOf(value).length() + 30).append("functionId '").append(value).append("' is not supported").toString(), this.mContext);
                return dv.zzbLu;
            }
        }
        if (zzg == null) {
            zzcup.zzc("Internal error: failed to convert function to a valid statement", this.mContext);
            return dv.zzbLu;
        }
        String valueOf = String.valueOf(zzg.zzDv());
        zzcvk.v(valueOf.length() != 0 ? "Executing: ".concat(valueOf) : new String("Executing: "));
        dp zza = ed.zza(this.zzbIJ, zzg);
        return (!(zza instanceof dv) || !((dv) zza).zzDr()) ? zza : ((dv) zza).zzDq();
    }

    public final void dispatch() {
        zzcur.zzbv(this.mContext).dispatch();
    }

    public final void zzb(zzcut zzcut) {
        boolean z;
        boolean z2;
        this.zzbIJ.zza("gtm.globals.eventName", new eb(zzcut.zzCk()));
        this.zzbIN.zza(zzcut);
        this.zzbIO = zzcut;
        HashSet<dd> hashSet = new HashSet<>();
        HashSet hashSet2 = new HashSet();
        HashMap hashMap = new HashMap();
        for (dg next : this.zzbII.zzCX()) {
            if (!next.zzDd().isEmpty() || !next.zzDe().isEmpty()) {
                dp<?> zza = zza(next, (Map<dd, dp<?>>) hashMap);
                if (zza == dv.zzbLt) {
                    String valueOf = String.valueOf(next);
                    zzcup.zzd(new StringBuilder(String.valueOf(valueOf).length() + 41).append("Error encounted while evaluating trigger ").append(valueOf).toString(), this.mContext);
                    if (!next.zzDe().isEmpty()) {
                        String valueOf2 = String.valueOf(next.zzDe());
                        zzcvk.v(new StringBuilder(String.valueOf(valueOf2).length() + 15).append("Blocking tags: ").append(valueOf2).toString());
                        hashSet2.addAll(next.zzDe());
                    }
                } else if (((ds) zza).zzDn().booleanValue()) {
                    String valueOf3 = String.valueOf(next);
                    zzcvk.v(new StringBuilder(String.valueOf(valueOf3).length() + 19).append("Trigger is firing: ").append(valueOf3).toString());
                    if (!next.zzDd().isEmpty()) {
                        String valueOf4 = String.valueOf(next.zzDd());
                        zzcvk.v(new StringBuilder(String.valueOf(valueOf4).length() + 34).append("Adding tags to firing candidates: ").append(valueOf4).toString());
                        hashSet.addAll(next.zzDd());
                    }
                    if (!next.zzDe().isEmpty()) {
                        String valueOf5 = String.valueOf(next.zzDe());
                        zzcvk.v(new StringBuilder(String.valueOf(valueOf5).length() + 24).append("Blocking disabled tags: ").append(valueOf5).toString());
                        hashSet2.addAll(next.zzDe());
                    }
                }
            } else {
                String valueOf6 = String.valueOf(next);
                zzcvk.v(new StringBuilder(String.valueOf(valueOf6).length() + 64).append("Trigger is not being evaluated since it has no associated tags: ").append(valueOf6).toString());
            }
        }
        hashSet.removeAll(hashSet2);
        boolean z3 = false;
        for (dd ddVar : hashSet) {
            this.zzbIM.clear();
            String valueOf7 = String.valueOf(ddVar);
            zzcvk.v(new StringBuilder(String.valueOf(valueOf7).length() + 21).append("Executing firing tag ").append(valueOf7).toString());
            try {
                zzw(zzv(ddVar.zzCZ()));
                dm dmVar = ddVar.zzCZ().get(zzbg.DISPATCH_ON_FIRE.toString());
                if (dmVar != null && dmVar.getType() == 8 && ((Boolean) dmVar.getValue()).booleanValue()) {
                    try {
                        String valueOf8 = String.valueOf(ddVar);
                        zzcvk.v(new StringBuilder(String.valueOf(valueOf8).length() + 36).append("Tag configured to dispatch on fire: ").append(valueOf8).toString());
                        z2 = true;
                    } catch (IllegalStateException e) {
                        e = e;
                        z = true;
                        String valueOf9 = String.valueOf(ddVar);
                        zzcup.zza(new StringBuilder(String.valueOf(valueOf9).length() + 19).append("Error firing tag ").append(valueOf9).append(": ").toString(), e, this.mContext);
                        z3 = z;
                    }
                } else {
                    z2 = z3;
                }
                z3 = z2;
            } catch (IllegalStateException e2) {
                e = e2;
                z = z3;
                String valueOf92 = String.valueOf(ddVar);
                zzcup.zza(new StringBuilder(String.valueOf(valueOf92).length() + 19).append("Error firing tag ").append(valueOf92).append(": ").toString(), e, this.mContext);
                z3 = z;
            }
        }
        this.zzbIJ.remove("gtm.globals.eventName");
        if (zzcut.zzCn()) {
            String valueOf10 = String.valueOf(zzcut.zzCk());
            zzcvk.v(new StringBuilder(String.valueOf(valueOf10).length() + 35).append("Log passthrough event ").append(valueOf10).append(" to Firebase.").toString());
            try {
                this.zzbHN.logEventInternalNoInterceptor(zzcut.zzCm(), zzcut.zzCk(), zzcut.zzCl(), zzcut.currentTimeMillis());
            } catch (RemoteException e3) {
                zzcup.zza("Error calling measurement proxy: ", e3, this.mContext);
            }
        } else {
            String valueOf11 = String.valueOf(zzcut.zzCk());
            zzcvk.v(new StringBuilder(String.valueOf(valueOf11).length() + 63).append("Non-passthrough event ").append(valueOf11).append(" doesn't get logged to Firebase directly.").toString());
        }
        if (z3) {
            zzcvk.v("Dispatch called for dispatchOnFire tags.");
            dispatch();
        }
    }

    public final dp<?> zzfH(String str) {
        if (this.zzbIM.contains(str)) {
            String valueOf = String.valueOf(this.zzbIM.toString());
            throw new IllegalStateException(new StringBuilder(String.valueOf(str).length() + 77 + String.valueOf(valueOf).length()).append("Macro cycle detected.  Current macro reference: ").append(str).append(". Previous macro references: ").append(valueOf).toString());
        }
        this.zzbGp = 0;
        return zzfI(str);
    }
}
