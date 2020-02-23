package com.google.android.gms.internal;

import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.tencent.imsdk.expansion.downloader.Constants;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.tp.a.h;
import com.vk.sdk.api.VKApiConst;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class op implements oe, on {
    private static long zzcaq = 0;
    /* access modifiers changed from: private */
    public final wl zzbZE;
    private final ScheduledExecutorService zzbZs;
    /* access modifiers changed from: private */
    public final oh zzcaC;
    /* access modifiers changed from: private */
    public final oo zzcaH;
    private String zzcaI;
    private HashSet<String> zzcaJ = new HashSet<>();
    private boolean zzcaK = true;
    private long zzcaL;
    /* access modifiers changed from: private */
    public od zzcaM;
    /* access modifiers changed from: private */
    public oz zzcaN = oz.Disconnected;
    private long zzcaO = 0;
    private long zzcaP = 0;
    private Map<Long, oy> zzcaQ;
    private List<pb> zzcaR;
    /* access modifiers changed from: private */
    public Map<Long, pd> zzcaS;
    /* access modifiers changed from: private */
    public Map<pa, pc> zzcaT;
    /* access modifiers changed from: private */
    public String zzcaU;
    /* access modifiers changed from: private */
    public boolean zzcaV;
    private final oj zzcaW;
    /* access modifiers changed from: private */
    public final pq zzcaX;
    private String zzcaY;
    /* access modifiers changed from: private */
    public long zzcaZ = 0;
    private final ol zzcar;
    /* access modifiers changed from: private */
    public int zzcba = 0;
    /* access modifiers changed from: private */
    public ScheduledFuture<?> zzcbb = null;
    private long zzcbc;
    private boolean zzcbd;

    public op(oj ojVar, ol olVar, oo ooVar) {
        this.zzcaH = ooVar;
        this.zzcaW = ojVar;
        this.zzbZs = ojVar.zzFV();
        this.zzcaC = ojVar.zzFU();
        this.zzcar = olVar;
        this.zzcaT = new HashMap();
        this.zzcaQ = new HashMap();
        this.zzcaS = new HashMap();
        this.zzcaR = new ArrayList();
        this.zzcaX = new ps(this.zzbZs, ojVar.zzFT(), "ConnectionRetryHelper").zzas(1000).zzh(1.3d).zzat(NotificationOptions.SKIP_STEP_THIRTY_SECONDS_IN_MS).zzi(0.7d).zzGC();
        long j = zzcaq;
        zzcaq = 1 + j;
        this.zzbZE = new wl(ojVar.zzFT(), "PersistentConnection", new StringBuilder(23).append("pc_").append(j).toString());
        this.zzcaY = null;
        zzGi();
    }

    private final boolean isIdle() {
        return this.zzcaT.isEmpty() && this.zzcaQ.isEmpty() && !this.zzcbd && this.zzcaS.isEmpty();
    }

    private final boolean zzGc() {
        return this.zzcaN == oz.Authenticating || this.zzcaN == oz.Connected;
    }

    private final boolean zzGd() {
        return this.zzcaN == oz.Connected;
    }

    private final boolean zzGe() {
        return this.zzcaJ.size() == 0;
    }

    /* access modifiers changed from: private */
    public final void zzGf() {
        if (zzGe()) {
            ok.zzc(this.zzcaN == oz.Disconnected, "Not in disconnected state: %s", this.zzcaN);
            boolean z = this.zzcaV;
            this.zzbZE.zzb("Scheduling connection attempt", (Throwable) null, new Object[0]);
            this.zzcaV = false;
            this.zzcaX.zzp(new oq(this, z));
        }
    }

    private final void zzGg() {
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<Long, pd>> it = this.zzcaS.entrySet().iterator();
        while (it.hasNext()) {
            pd pdVar = (pd) it.next().getValue();
            if (pdVar.zzGp().containsKey("h") && pdVar.zzGr()) {
                arrayList.add(pdVar);
                it.remove();
            }
        }
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            ((pd) obj).zzGl().zzaa("disconnected", (String) null);
        }
    }

    /* access modifiers changed from: private */
    public final void zzGh() {
        int i = 0;
        ok.zzc(this.zzcaN == oz.Connected, "Should be connected if we're restoring state, but we are: %s", this.zzcaN);
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb("Restoring outstanding listens", (Throwable) null, new Object[0]);
        }
        for (pc next : this.zzcaT.values()) {
            if (this.zzbZE.zzIH()) {
                wl wlVar = this.zzbZE;
                String valueOf = String.valueOf(next.zzGm());
                wlVar.zzb(new StringBuilder(String.valueOf(valueOf).length() + 17).append("Restoring listen ").append(valueOf).toString(), (Throwable) null, new Object[0]);
            }
            zza(next);
        }
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb("Restoring writes.", (Throwable) null, new Object[0]);
        }
        ArrayList arrayList = new ArrayList(this.zzcaS.keySet());
        Collections.sort(arrayList);
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            zzap(((Long) obj).longValue());
        }
        for (pb next2 : this.zzcaR) {
            zza(next2.getAction(), next2.zzGk(), next2.getData(), next2.zzGl());
        }
        this.zzcaR.clear();
    }

    /* access modifiers changed from: private */
    public final void zzGi() {
        if (isIdle()) {
            if (this.zzcbb != null) {
                this.zzcbb.cancel(false);
            }
            this.zzcbb = this.zzbZs.schedule(new ox(this), Constants.WATCHDOG_WAKE_TIMER, TimeUnit.MILLISECONDS);
        } else if (isInterrupted("connection_idle")) {
            ok.zzc(!isIdle(), "", new Object[0]);
            resume("connection_idle");
        }
    }

    /* access modifiers changed from: private */
    public final boolean zzGj() {
        return isIdle() && System.currentTimeMillis() > this.zzcbc + Constants.WATCHDOG_WAKE_TIMER;
    }

    private final void zzS(List<String> list) {
        if (this.zzbZE.zzIH()) {
            wl wlVar = this.zzbZE;
            String valueOf = String.valueOf(list);
            wlVar.zzb(new StringBuilder(String.valueOf(valueOf).length() + 29).append("removing all listens at path ").append(valueOf).toString(), (Throwable) null, new Object[0]);
        }
        ArrayList arrayList = new ArrayList();
        for (Map.Entry next : this.zzcaT.entrySet()) {
            pc pcVar = (pc) next.getValue();
            if (((pa) next.getKey()).zzcbt.equals(list)) {
                arrayList.add(pcVar);
            }
        }
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            this.zzcaT.remove(((pc) obj).zzGm());
        }
        zzGi();
        ArrayList arrayList3 = arrayList;
        int size2 = arrayList3.size();
        int i2 = 0;
        while (i2 < size2) {
            Object obj2 = arrayList3.get(i2);
            i2++;
            ((pc) obj2).zzcbx.zzaa("permission_denied", (String) null);
        }
    }

    /* access modifiers changed from: private */
    public final pc zza(pa paVar) {
        if (this.zzbZE.zzIH()) {
            wl wlVar = this.zzbZE;
            String valueOf = String.valueOf(paVar);
            wlVar.zzb(new StringBuilder(String.valueOf(valueOf).length() + 15).append("removing query ").append(valueOf).toString(), (Throwable) null, new Object[0]);
        }
        if (this.zzcaT.containsKey(paVar)) {
            pc pcVar = this.zzcaT.get(paVar);
            this.zzcaT.remove(paVar);
            zzGi();
            return pcVar;
        } else if (!this.zzbZE.zzIH()) {
            return null;
        } else {
            wl wlVar2 = this.zzbZE;
            String valueOf2 = String.valueOf(paVar);
            wlVar2.zzb(new StringBuilder(String.valueOf(valueOf2).length() + 64).append("Trying to remove listener for QuerySpec ").append(valueOf2).append(" but no listener exists.").toString(), (Throwable) null, new Object[0]);
            return null;
        }
    }

    private final void zza(pc pcVar) {
        HashMap hashMap = new HashMap();
        hashMap.put("p", ok.zzR(pcVar.zzGm().zzcbt));
        Long zzGn = pcVar.zzGn();
        if (zzGn != null) {
            hashMap.put(VKApiConst.Q, pcVar.zzcby.zzcbu);
            hashMap.put("t", zzGn);
        }
        om zzGo = pcVar.zzGo();
        hashMap.put("h", zzGo.zzFY());
        if (zzGo.zzFZ()) {
            oc zzGa = zzGo.zzGa();
            ArrayList arrayList = new ArrayList();
            for (List<String> zzR : zzGa.zzFR()) {
                arrayList.add(ok.zzR(zzR));
            }
            HashMap hashMap2 = new HashMap();
            hashMap2.put("hs", zzGa.zzFS());
            hashMap2.put("ps", arrayList);
            hashMap.put("ch", hashMap2);
        }
        zza(VKApiConst.Q, (Map<String, Object>) hashMap, (oy) new ov(this, pcVar));
    }

    private final void zza(String str, List<String> list, Object obj, pf pfVar) {
        HashMap hashMap = new HashMap();
        hashMap.put("p", ok.zzR(list));
        hashMap.put(APDataReportManager.GOODSANDMONTHSINPUT_PRE, obj);
        zza(str, (Map<String, Object>) hashMap, (oy) new os(this, pfVar));
    }

    private final void zza(String str, List<String> list, Object obj, String str2, pf pfVar) {
        HashMap hashMap = new HashMap();
        hashMap.put("p", ok.zzR(list));
        hashMap.put(APDataReportManager.GOODSANDMONTHSINPUT_PRE, obj);
        if (str2 != null) {
            hashMap.put("h", str2);
        }
        long j = this.zzcaO;
        this.zzcaO = 1 + j;
        this.zzcaS.put(Long.valueOf(j), new pd(str, hashMap, pfVar, (oq) null));
        if (zzGd()) {
            zzap(j);
        }
        this.zzcbc = System.currentTimeMillis();
        zzGi();
    }

    private final void zza(String str, Map<String, Object> map, oy oyVar) {
        zza(str, false, map, oyVar);
    }

    private final void zza(String str, boolean z, Map<String, Object> map, oy oyVar) {
        long j = this.zzcaP;
        this.zzcaP = 1 + j;
        HashMap hashMap = new HashMap();
        hashMap.put("r", Long.valueOf(j));
        hashMap.put(APDataReportManager.GAMEANDMONTHSLIST_PRE, str);
        hashMap.put(APDataReportManager.GAMEANDMONTHSINPUT_PRE, map);
        this.zzcaM.zza(hashMap, z);
        this.zzcaQ.put(Long.valueOf(j), oyVar);
    }

    /* access modifiers changed from: private */
    public final void zza(List<String> list, pa paVar) {
        if (list.contains("no_index")) {
            String valueOf = String.valueOf(paVar.zzcbu.get("i"));
            String sb = new StringBuilder(String.valueOf(valueOf).length() + 14).append("\".indexOn\": \"").append(valueOf).append("\"").toString();
            wl wlVar = this.zzbZE;
            String valueOf2 = String.valueOf(ok.zzR(paVar.zzcbt));
            wlVar.zze(new StringBuilder(String.valueOf(sb).length() + 118 + String.valueOf(valueOf2).length()).append("Using an unspecified index. Consider adding '").append(sb).append("' at ").append(valueOf2).append(" to your security and Firebase Database rules for better performance").toString(), (Throwable) null);
        }
    }

    private final void zzaC(boolean z) {
        ok.zzc(zzGc(), "Must be connected to send auth, but was: %s", this.zzcaN);
        ok.zzc(this.zzcaU != null, "Auth token must be set to authenticate!", new Object[0]);
        ot otVar = new ot(this, z);
        HashMap hashMap = new HashMap();
        yq zzgU = yq.zzgU(this.zzcaU);
        if (zzgU != null) {
            hashMap.put("cred", zzgU.getToken());
            if (zzgU.zzJE() != null) {
                hashMap.put("authvar", zzgU.zzJE());
            }
            zza("gauth", true, (Map<String, Object>) hashMap, (oy) otVar);
            return;
        }
        hashMap.put("cred", this.zzcaU);
        zza("auth", true, (Map<String, Object>) hashMap, (oy) otVar);
    }

    private final void zzap(long j) {
        pd pdVar = this.zzcaS.get(Long.valueOf(j));
        pf zzGl = pdVar.zzGl();
        String action = pdVar.getAction();
        pdVar.zzGq();
        zza(action, pdVar.zzGp(), (oy) new ou(this, action, j, pdVar, zzGl));
    }

    static /* synthetic */ long zzc(op opVar) {
        long j = opVar.zzcaZ;
        opVar.zzcaZ = 1 + j;
        return j;
    }

    static /* synthetic */ int zzj(op opVar) {
        int i = opVar.zzcba;
        opVar.zzcba = i + 1;
        return i;
    }

    public final void initialize() {
        zzGf();
    }

    public final void interrupt(String str) {
        if (this.zzbZE.zzIH()) {
            wl wlVar = this.zzbZE;
            String valueOf = String.valueOf(str);
            wlVar.zzb(valueOf.length() != 0 ? "Connection interrupted for: ".concat(valueOf) : new String("Connection interrupted for: "), (Throwable) null, new Object[0]);
        }
        this.zzcaJ.add(str);
        if (this.zzcaM != null) {
            this.zzcaM.close();
            this.zzcaM = null;
        } else {
            this.zzcaX.cancel();
            this.zzcaN = oz.Disconnected;
        }
        this.zzcaX.zzGA();
    }

    public final boolean isInterrupted(String str) {
        return this.zzcaJ.contains(str);
    }

    public final void purgeOutstandingWrites() {
        for (pd next : this.zzcaS.values()) {
            if (next.zzcbw != null) {
                next.zzcbw.zzaa("write_canceled", (String) null);
            }
        }
        for (pb next2 : this.zzcaR) {
            if (next2.zzcbw != null) {
                next2.zzcbw.zzaa("write_canceled", (String) null);
            }
        }
        this.zzcaS.clear();
        this.zzcaR.clear();
        if (!zzGc()) {
            this.zzcbd = false;
        }
        zzGi();
    }

    public final void refreshAuthToken() {
        this.zzbZE.zzb("Auth token refresh requested", (Throwable) null, new Object[0]);
        interrupt("token_refresh");
        resume("token_refresh");
    }

    public final void resume(String str) {
        if (this.zzbZE.zzIH()) {
            wl wlVar = this.zzbZE;
            String valueOf = String.valueOf(str);
            wlVar.zzb(valueOf.length() != 0 ? "Connection no longer interrupted for: ".concat(valueOf) : new String("Connection no longer interrupted for: "), (Throwable) null, new Object[0]);
        }
        this.zzcaJ.remove(str);
        if (zzGe() && this.zzcaN == oz.Disconnected) {
            zzGf();
        }
    }

    public final void shutdown() {
        interrupt("shutdown");
    }

    public final void zzA(Map<String, Object> map) {
        if (map.containsKey("r")) {
            oy remove = this.zzcaQ.remove(Long.valueOf((long) ((Integer) map.get("r")).intValue()));
            if (remove != null) {
                remove.zzC((Map) map.get(APDataReportManager.GAMEANDMONTHSINPUT_PRE));
            }
        } else if (map.containsKey("error")) {
        } else {
            if (map.containsKey(APDataReportManager.GAMEANDMONTHSLIST_PRE)) {
                String str = (String) map.get(APDataReportManager.GAMEANDMONTHSLIST_PRE);
                Map map2 = (Map) map.get(APDataReportManager.GAMEANDMONTHSINPUT_PRE);
                if (this.zzbZE.zzIH()) {
                    wl wlVar = this.zzbZE;
                    String valueOf = String.valueOf(map2);
                    wlVar.zzb(new StringBuilder(String.valueOf(str).length() + 22 + String.valueOf(valueOf).length()).append("handleServerMessage: ").append(str).append(" ").append(valueOf).toString(), (Throwable) null, new Object[0]);
                }
                if (str.equals(APDataReportManager.GOODSANDMONTHSINPUT_PRE) || str.equals("m")) {
                    boolean equals = str.equals("m");
                    String str2 = (String) map2.get("p");
                    Object obj = map2.get(APDataReportManager.GOODSANDMONTHSINPUT_PRE);
                    Long zzah = ok.zzah(map2.get("t"));
                    if (!equals || !(obj instanceof Map) || ((Map) obj).size() != 0) {
                        this.zzcaH.zza(ok.zzgG(str2), obj, equals, zzah);
                    } else if (this.zzbZE.zzIH()) {
                        wl wlVar2 = this.zzbZE;
                        String valueOf2 = String.valueOf(str2);
                        wlVar2.zzb(valueOf2.length() != 0 ? "ignoring empty merge for path ".concat(valueOf2) : new String("ignoring empty merge for path "), (Throwable) null, new Object[0]);
                    }
                } else if (str.equals("rm")) {
                    String str3 = (String) map2.get("p");
                    List<String> zzgG = ok.zzgG(str3);
                    Object obj2 = map2.get(APDataReportManager.GOODSANDMONTHSINPUT_PRE);
                    Long zzah2 = ok.zzah(map2.get("t"));
                    ArrayList arrayList = new ArrayList();
                    for (Map map3 : (List) obj2) {
                        String str4 = (String) map3.get("s");
                        String str5 = (String) map3.get(APDataReportManager.ACCOUNTINPUT_PRE);
                        arrayList.add(new pe(str4 != null ? ok.zzgG(str4) : null, str5 != null ? ok.zzgG(str5) : null, map3.get("m")));
                    }
                    if (!arrayList.isEmpty()) {
                        this.zzcaH.zza(zzgG, arrayList, zzah2);
                    } else if (this.zzbZE.zzIH()) {
                        wl wlVar3 = this.zzbZE;
                        String valueOf3 = String.valueOf(str3);
                        wlVar3.zzb(valueOf3.length() != 0 ? "Ignoring empty range merge for path ".concat(valueOf3) : new String("Ignoring empty range merge for path "), (Throwable) null, new Object[0]);
                    }
                } else if (str.equals("c")) {
                    zzS(ok.zzgG((String) map2.get("p")));
                } else if (str.equals("ac")) {
                    String str6 = (String) map2.get("s");
                    String str7 = (String) map2.get(APDataReportManager.GOODSANDMONTHSINPUT_PRE);
                    this.zzbZE.zzb(new StringBuilder(String.valueOf(str6).length() + 23 + String.valueOf(str7).length()).append("Auth token revoked: ").append(str6).append(" (").append(str7).append(h.b).toString(), (Throwable) null, new Object[0]);
                    this.zzcaU = null;
                    this.zzcaV = true;
                    this.zzcaH.zzaB(false);
                    this.zzcaM.close();
                } else if (str.equals("sd")) {
                    this.zzbZE.info((String) map2.get("msg"));
                } else if (this.zzbZE.zzIH()) {
                    wl wlVar4 = this.zzbZE;
                    String valueOf4 = String.valueOf(str);
                    wlVar4.zzb(valueOf4.length() != 0 ? "Unrecognized action from server: ".concat(valueOf4) : new String("Unrecognized action from server: "), (Throwable) null, new Object[0]);
                }
            } else if (this.zzbZE.zzIH()) {
                wl wlVar5 = this.zzbZE;
                String valueOf5 = String.valueOf(map);
                wlVar5.zzb(new StringBuilder(String.valueOf(valueOf5).length() + 26).append("Ignoring unknown message: ").append(valueOf5).toString(), (Throwable) null, new Object[0]);
            }
        }
    }

    public final void zza(List<String> list, pf pfVar) {
        if (zzGd()) {
            zza("oc", list, (Object) null, pfVar);
        } else {
            this.zzcaR.add(new pb("oc", list, (Object) null, pfVar, (oq) null));
        }
        zzGi();
    }

    public final void zza(List<String> list, Object obj, pf pfVar) {
        zza("p", list, obj, (String) null, pfVar);
    }

    public final void zza(List<String> list, Object obj, String str, pf pfVar) {
        zza("p", list, obj, str, pfVar);
    }

    public final void zza(List<String> list, Map<String, Object> map) {
        pa paVar = new pa(list, map);
        if (this.zzbZE.zzIH()) {
            wl wlVar = this.zzbZE;
            String valueOf = String.valueOf(paVar);
            wlVar.zzb(new StringBuilder(String.valueOf(valueOf).length() + 15).append("unlistening on ").append(valueOf).toString(), (Throwable) null, new Object[0]);
        }
        pc zza = zza(paVar);
        if (zza != null && zzGc()) {
            HashMap hashMap = new HashMap();
            hashMap.put("p", ok.zzR(zza.zzcby.zzcbt));
            Long zzGn = zza.zzGn();
            if (zzGn != null) {
                hashMap.put(VKApiConst.Q, zza.zzGm().zzcbu);
                hashMap.put("t", zzGn);
            }
            zza("n", (Map<String, Object>) hashMap, (oy) null);
        }
        zzGi();
    }

    public final void zza(List<String> list, Map<String, Object> map, om omVar, Long l, pf pfVar) {
        pa paVar = new pa(list, map);
        if (this.zzbZE.zzIH()) {
            wl wlVar = this.zzbZE;
            String valueOf = String.valueOf(paVar);
            wlVar.zzb(new StringBuilder(String.valueOf(valueOf).length() + 13).append("Listening on ").append(valueOf).toString(), (Throwable) null, new Object[0]);
        }
        ok.zzc(!this.zzcaT.containsKey(paVar), "listen() called twice for same QuerySpec.", new Object[0]);
        if (this.zzbZE.zzIH()) {
            wl wlVar2 = this.zzbZE;
            String valueOf2 = String.valueOf(paVar);
            wlVar2.zzb(new StringBuilder(String.valueOf(valueOf2).length() + 21).append("Adding listen query: ").append(valueOf2).toString(), (Throwable) null, new Object[0]);
        }
        pc pcVar = new pc(pfVar, paVar, l, omVar, (oq) null);
        this.zzcaT.put(paVar, pcVar);
        if (zzGc()) {
            zza(pcVar);
        }
        zzGi();
    }

    public final void zza(List<String> list, Map<String, Object> map, pf pfVar) {
        zza("m", list, (Object) map, (String) null, pfVar);
    }

    public final void zzb(of ofVar) {
        if (this.zzbZE.zzIH()) {
            wl wlVar = this.zzbZE;
            String valueOf = String.valueOf(ofVar.name());
            wlVar.zzb(valueOf.length() != 0 ? "Got on disconnect due to ".concat(valueOf) : new String("Got on disconnect due to "), (Throwable) null, new Object[0]);
        }
        this.zzcaN = oz.Disconnected;
        this.zzcaM = null;
        this.zzcbd = false;
        this.zzcaQ.clear();
        zzGg();
        if (zzGe()) {
            boolean z = this.zzcaL > 0 ? System.currentTimeMillis() - this.zzcaL > NotificationOptions.SKIP_STEP_THIRTY_SECONDS_IN_MS : false;
            if (ofVar == of.SERVER_RESET || z) {
                this.zzcaX.zzGA();
            }
            zzGf();
        }
        this.zzcaL = 0;
        this.zzcaH.onDisconnect();
    }

    public final void zzb(List<String> list, Object obj, pf pfVar) {
        this.zzcbd = true;
        if (zzGd()) {
            zza("o", list, obj, pfVar);
        } else {
            this.zzcaR.add(new pb("o", list, obj, pfVar, (oq) null));
        }
        zzGi();
    }

    public final void zzb(List<String> list, Map<String, Object> map, pf pfVar) {
        this.zzcbd = true;
        if (zzGd()) {
            zza("om", list, (Object) map, pfVar);
        } else {
            this.zzcaR.add(new pb("om", list, map, pfVar, (oq) null));
        }
        zzGi();
    }

    public final void zzc(long j, String str) {
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb("onReady", (Throwable) null, new Object[0]);
        }
        this.zzcaL = System.currentTimeMillis();
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb("handling timestamp", (Throwable) null, new Object[0]);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("serverTimeOffset", Long.valueOf(j - System.currentTimeMillis()));
        this.zzcaH.zzB(hashMap);
        if (this.zzcaK) {
            HashMap hashMap2 = new HashMap();
            if (yp.zzJD()) {
                if (this.zzcaW.zzFW()) {
                    hashMap2.put("persistence.android.enabled", 1);
                }
                String valueOf = String.valueOf(this.zzcaW.zzFX().replace('.', '-'));
                hashMap2.put(valueOf.length() != 0 ? "sdk.android.".concat(valueOf) : new String("sdk.android."), 1);
            } else {
                String valueOf2 = String.valueOf(this.zzcaW.zzFX().replace('.', '-'));
                hashMap2.put(valueOf2.length() != 0 ? "sdk.java.".concat(valueOf2) : new String("sdk.java."), 1);
            }
            if (this.zzbZE.zzIH()) {
                this.zzbZE.zzb("Sending first connection stats", (Throwable) null, new Object[0]);
            }
            if (!hashMap2.isEmpty()) {
                HashMap hashMap3 = new HashMap();
                hashMap3.put("c", hashMap2);
                zza("s", (Map<String, Object>) hashMap3, (oy) new ow(this));
            } else if (this.zzbZE.zzIH()) {
                this.zzbZE.zzb("Not sending stats because stats are empty", (Throwable) null, new Object[0]);
            }
        }
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb("calling restore state", (Throwable) null, new Object[0]);
        }
        ok.zzc(this.zzcaN == oz.Connecting, "Wanted to restore auth, but was in wrong state: %s", this.zzcaN);
        if (this.zzcaU == null) {
            if (this.zzbZE.zzIH()) {
                this.zzbZE.zzb("Not restoring auth because token is null.", (Throwable) null, new Object[0]);
            }
            this.zzcaN = oz.Connected;
            zzGh();
        } else {
            if (this.zzbZE.zzIH()) {
                this.zzbZE.zzb("Restoring auth.", (Throwable) null, new Object[0]);
            }
            this.zzcaN = oz.Authenticating;
            zzaC(true);
        }
        this.zzcaK = false;
        this.zzcaY = str;
        this.zzcaH.zzGb();
    }

    public final void zzgD(String str) {
        this.zzcaI = str;
    }

    public final void zzgE(String str) {
        if (this.zzbZE.zzIH()) {
            wl wlVar = this.zzbZE;
            String valueOf = String.valueOf(str);
            wlVar.zzb(valueOf.length() != 0 ? "Firebase Database connection was forcefully killed by the server. Will not attempt reconnect. Reason: ".concat(valueOf) : new String("Firebase Database connection was forcefully killed by the server. Will not attempt reconnect. Reason: "), (Throwable) null, new Object[0]);
        }
        interrupt("server_kill");
    }

    public final void zzgH(String str) {
        this.zzbZE.zzb("Auth token refreshed.", (Throwable) null, new Object[0]);
        this.zzcaU = str;
        if (!zzGc()) {
            return;
        }
        if (str != null) {
            zzaC(false);
            return;
        }
        ok.zzc(zzGc(), "Must be connected to send unauth.", new Object[0]);
        ok.zzc(this.zzcaU == null, "Auth token must not be set.", new Object[0]);
        zza("unauth", (Map<String, Object>) Collections.emptyMap(), (oy) null);
    }

    public final void zzgI(String str) {
        ok.zzc(this.zzcaN == oz.GettingToken, "Trying to open network connection while in the wrong state: %s", this.zzcaN);
        if (str == null) {
            this.zzcaH.zzaB(false);
        }
        this.zzcaU = str;
        this.zzcaN = oz.Connecting;
        this.zzcaM = new od(this.zzcaW, this.zzcar, this.zzcaI, this, this.zzcaY);
        this.zzcaM.open();
    }
}
