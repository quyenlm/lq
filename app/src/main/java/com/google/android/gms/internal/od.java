package com.google.android.gms.internal;

import com.tencent.midas.oversea.comm.APDataReportManager;
import java.util.HashMap;
import java.util.Map;

final class od implements pj {
    private static long zzcaq = 0;
    private final wl zzbZE;
    private ol zzcar;
    private pg zzcas;
    private oe zzcat;
    private int zzcau = og.zzcay;

    public od(oj ojVar, ol olVar, String str, oe oeVar, String str2) {
        long j = zzcaq;
        zzcaq = 1 + j;
        this.zzcar = olVar;
        this.zzcat = oeVar;
        this.zzbZE = new wl(ojVar.zzFT(), "Connection", new StringBuilder(25).append("conn_").append(j).toString());
        this.zzcas = new pg(ojVar, olVar, str, this, str2);
    }

    private final void zza(of ofVar) {
        if (this.zzcau != og.zzcaA) {
            if (this.zzbZE.zzIH()) {
                this.zzbZE.zzb("closing realtime connection", (Throwable) null, new Object[0]);
            }
            this.zzcau = og.zzcaA;
            if (this.zzcas != null) {
                this.zzcas.close();
                this.zzcas = null;
            }
            this.zzcat.zzb(ofVar);
        }
    }

    public final void close() {
        zza(of.OTHER);
    }

    public final void open() {
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb("Opening a connection", (Throwable) null, new Object[0]);
        }
        this.zzcas.open();
    }

    public final void zza(Map<String, Object> map, boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put("t", APDataReportManager.GOODSANDMONTHSINPUT_PRE);
        hashMap.put(APDataReportManager.GOODSANDMONTHSINPUT_PRE, map);
        if (this.zzcau != og.zzcaz) {
            this.zzbZE.zzb("Tried to send on an unconnected connection", (Throwable) null, new Object[0]);
            return;
        }
        if (z) {
            this.zzbZE.zzb("Sending data (contents hidden)", (Throwable) null, new Object[0]);
        } else {
            this.zzbZE.zzb("Sending data: %s", (Throwable) null, hashMap);
        }
        this.zzcas.send(hashMap);
    }

    public final void zzaA(boolean z) {
        this.zzcas = null;
        if (z || this.zzcau != og.zzcay) {
            if (this.zzbZE.zzIH()) {
                this.zzbZE.zzb("Realtime connection lost", (Throwable) null, new Object[0]);
            }
        } else if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb("Realtime connection failed", (Throwable) null, new Object[0]);
        }
        zza(of.OTHER);
    }

    public final void zzz(Map<String, Object> map) {
        try {
            String str = (String) map.get("t");
            if (str == null) {
                if (this.zzbZE.zzIH()) {
                    wl wlVar = this.zzbZE;
                    String valueOf = String.valueOf(map.toString());
                    wlVar.zzb(valueOf.length() != 0 ? "Failed to parse server message: missing message type:".concat(valueOf) : new String("Failed to parse server message: missing message type:"), (Throwable) null, new Object[0]);
                }
                zza(of.OTHER);
            } else if (str.equals(APDataReportManager.GOODSANDMONTHSINPUT_PRE)) {
                Map map2 = (Map) map.get(APDataReportManager.GOODSANDMONTHSINPUT_PRE);
                if (this.zzbZE.zzIH()) {
                    wl wlVar2 = this.zzbZE;
                    String valueOf2 = String.valueOf(map2.toString());
                    wlVar2.zzb(valueOf2.length() != 0 ? "received data message: ".concat(valueOf2) : new String("received data message: "), (Throwable) null, new Object[0]);
                }
                this.zzcat.zzA(map2);
            } else if (str.equals("c")) {
                Map map3 = (Map) map.get(APDataReportManager.GOODSANDMONTHSINPUT_PRE);
                if (this.zzbZE.zzIH()) {
                    wl wlVar3 = this.zzbZE;
                    String valueOf3 = String.valueOf(map3.toString());
                    wlVar3.zzb(valueOf3.length() != 0 ? "Got control message: ".concat(valueOf3) : new String("Got control message: "), (Throwable) null, new Object[0]);
                }
                try {
                    String str2 = (String) map3.get("t");
                    if (str2 == null) {
                        if (this.zzbZE.zzIH()) {
                            wl wlVar4 = this.zzbZE;
                            String valueOf4 = String.valueOf(map3.toString());
                            wlVar4.zzb(valueOf4.length() != 0 ? "Got invalid control message: ".concat(valueOf4) : new String("Got invalid control message: "), (Throwable) null, new Object[0]);
                        }
                        zza(of.OTHER);
                    } else if (str2.equals("s")) {
                        String str3 = (String) map3.get(APDataReportManager.GOODSANDMONTHSINPUT_PRE);
                        if (this.zzbZE.zzIH()) {
                            this.zzbZE.zzb("Connection shutdown command received. Shutting down...", (Throwable) null, new Object[0]);
                        }
                        this.zzcat.zzgE(str3);
                        zza(of.OTHER);
                    } else if (str2.equals("r")) {
                        String str4 = (String) map3.get(APDataReportManager.GOODSANDMONTHSINPUT_PRE);
                        if (this.zzbZE.zzIH()) {
                            wl wlVar5 = this.zzbZE;
                            String valueOf5 = String.valueOf(this.zzcar.getHost());
                            wlVar5.zzb(new StringBuilder(String.valueOf(valueOf5).length() + 62 + String.valueOf(str4).length()).append("Got a reset; killing connection to ").append(valueOf5).append("; Updating internalHost to ").append(str4).toString(), (Throwable) null, new Object[0]);
                        }
                        this.zzcat.zzgD(str4);
                        zza(of.SERVER_RESET);
                    } else if (str2.equals("h")) {
                        Map map4 = (Map) map3.get(APDataReportManager.GOODSANDMONTHSINPUT_PRE);
                        long longValue = ((Long) map4.get("ts")).longValue();
                        this.zzcat.zzgD((String) map4.get("h"));
                        String str5 = (String) map4.get("s");
                        if (this.zzcau == og.zzcay) {
                            if (this.zzbZE.zzIH()) {
                                this.zzbZE.zzb("realtime connection established", (Throwable) null, new Object[0]);
                            }
                            this.zzcau = og.zzcaz;
                            this.zzcat.zzc(longValue, str5);
                        }
                    } else if (this.zzbZE.zzIH()) {
                        wl wlVar6 = this.zzbZE;
                        String valueOf6 = String.valueOf(str2);
                        wlVar6.zzb(valueOf6.length() != 0 ? "Ignoring unknown control message: ".concat(valueOf6) : new String("Ignoring unknown control message: "), (Throwable) null, new Object[0]);
                    }
                } catch (ClassCastException e) {
                    if (this.zzbZE.zzIH()) {
                        wl wlVar7 = this.zzbZE;
                        String valueOf7 = String.valueOf(e.toString());
                        wlVar7.zzb(valueOf7.length() != 0 ? "Failed to parse control message: ".concat(valueOf7) : new String("Failed to parse control message: "), (Throwable) null, new Object[0]);
                    }
                    zza(of.OTHER);
                }
            } else if (this.zzbZE.zzIH()) {
                wl wlVar8 = this.zzbZE;
                String valueOf8 = String.valueOf(str);
                wlVar8.zzb(valueOf8.length() != 0 ? "Ignoring unknown server message type: ".concat(valueOf8) : new String("Ignoring unknown server message type: "), (Throwable) null, new Object[0]);
            }
        } catch (ClassCastException e2) {
            if (this.zzbZE.zzIH()) {
                wl wlVar9 = this.zzbZE;
                String valueOf9 = String.valueOf(e2.toString());
                wlVar9.zzb(valueOf9.length() != 0 ? "Failed to parse server message: ".concat(valueOf9) : new String("Failed to parse server message: "), (Throwable) null, new Object[0]);
            }
            zza(of.OTHER);
        }
    }
}
