package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Future;

@zzzn
public final class zzadw extends zzafp implements zzadv {
    private final Context mContext;
    private final Object mLock;
    private final zzafg zzQQ;
    private final long zzWC;
    private final ArrayList<Future> zzWN;
    private final ArrayList<String> zzWO;
    private final HashMap<String, zzadm> zzWP;
    private final List<zzadp> zzWQ;
    private final HashSet<String> zzWR;
    /* access modifiers changed from: private */
    public final zzacs zzWS;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public zzadw(android.content.Context r7, com.google.android.gms.internal.zzafg r8, com.google.android.gms.internal.zzacs r9) {
        /*
            r6 = this;
            com.google.android.gms.internal.zzme<java.lang.Long> r0 = com.google.android.gms.internal.zzmo.zzDI
            com.google.android.gms.internal.zzmm r1 = com.google.android.gms.ads.internal.zzbs.zzbL()
            java.lang.Object r0 = r1.zzd(r0)
            java.lang.Long r0 = (java.lang.Long) r0
            long r4 = r0.longValue()
            r0 = r6
            r1 = r7
            r2 = r8
            r3 = r9
            r0.<init>(r1, r2, r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzadw.<init>(android.content.Context, com.google.android.gms.internal.zzafg, com.google.android.gms.internal.zzacs):void");
    }

    private zzadw(Context context, zzafg zzafg, zzacs zzacs, long j) {
        this.zzWN = new ArrayList<>();
        this.zzWO = new ArrayList<>();
        this.zzWP = new HashMap<>();
        this.zzWQ = new ArrayList();
        this.zzWR = new HashSet<>();
        this.mLock = new Object();
        this.mContext = context;
        this.zzQQ = zzafg;
        this.zzWS = zzacs;
        this.zzWC = j;
    }

    private final zzaff zza(int i, @Nullable String str, @Nullable zzua zzua) {
        return new zzaff(this.zzQQ.zzUj.zzSz, (zzaka) null, this.zzQQ.zzXY.zzMa, i, this.zzQQ.zzXY.zzMb, this.zzQQ.zzXY.zzTq, this.zzQQ.zzXY.orientation, this.zzQQ.zzXY.zzMg, this.zzQQ.zzUj.zzSC, this.zzQQ.zzXY.zzTo, zzua, (zzut) null, str, this.zzQQ.zzXN, (zzud) null, this.zzQQ.zzXY.zzTp, this.zzQQ.zzvX, this.zzQQ.zzXY.zzTn, this.zzQQ.zzXR, this.zzQQ.zzXY.zzTs, this.zzQQ.zzXY.zzTt, this.zzQQ.zzXL, (zzoa) null, this.zzQQ.zzXY.zzTD, this.zzQQ.zzXY.zzTE, this.zzQQ.zzXY.zzTF, this.zzQQ.zzXY.zzTG, this.zzQQ.zzXY.zzTH, zzgV(), this.zzQQ.zzXY.zzMd, this.zzQQ.zzXY.zzTK, this.zzQQ.zzXX);
    }

    private final String zzgV() {
        int i;
        StringBuilder sb = new StringBuilder("");
        if (this.zzWQ == null) {
            return sb.toString();
        }
        for (zzadp next : this.zzWQ) {
            if (next != null && !TextUtils.isEmpty(next.zzLK)) {
                String str = next.zzLK;
                switch (next.errorCode) {
                    case 3:
                        i = 1;
                        break;
                    case 4:
                        i = 2;
                        break;
                    case 5:
                        i = 4;
                        break;
                    case 6:
                        i = 0;
                        break;
                    case 7:
                        i = 3;
                        break;
                    default:
                        i = 6;
                        break;
                }
                sb.append(String.valueOf(new StringBuilder(String.valueOf(str).length() + 33).append(str).append(".").append(i).append(".").append(next.zzML).toString()).concat("_"));
            }
        }
        return sb.substring(0, Math.max(0, sb.length() - 1));
    }

    public final void onStop() {
    }

    public final void zza(String str, int i) {
    }

    public final void zzaw(String str) {
        synchronized (this.mLock) {
            this.zzWR.add(str);
        }
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:653)
        	at java.util.ArrayList.get(ArrayList.java:429)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processExcHandler(RegionMaker.java:1043)
        	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:975)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
        */
    public final void zzbd() {
        /*
            r14 = this;
            r10 = 0
            com.google.android.gms.internal.zzafg r0 = r14.zzQQ
            com.google.android.gms.internal.zzub r0 = r0.zzXN
            java.util.List<com.google.android.gms.internal.zzua> r0 = r0.zzLY
            java.util.Iterator r11 = r0.iterator()
        L_0x000b:
            boolean r0 = r11.hasNext()
            if (r0 == 0) goto L_0x00b0
            java.lang.Object r4 = r11.next()
            com.google.android.gms.internal.zzua r4 = (com.google.android.gms.internal.zzua) r4
            java.lang.String r3 = r4.zzLP
            java.util.List<java.lang.String> r0 = r4.zzLJ
            java.util.Iterator r12 = r0.iterator()
        L_0x001f:
            boolean r0 = r12.hasNext()
            if (r0 == 0) goto L_0x000b
            java.lang.Object r0 = r12.next()
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r1 = "com.google.android.gms.ads.mediation.customevent.CustomEventAdapter"
            boolean r1 = r1.equals(r0)
            if (r1 != 0) goto L_0x003b
            java.lang.String r1 = "com.google.ads.mediation.customevent.CustomEventAdapter"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x01d1
        L_0x003b:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0085 }
            r0.<init>(r3)     // Catch:{ JSONException -> 0x0085 }
            java.lang.String r1 = "class_name"
            java.lang.String r2 = r0.getString(r1)     // Catch:{ JSONException -> 0x0085 }
        L_0x0046:
            java.lang.Object r13 = r14.mLock
            monitor-enter(r13)
            com.google.android.gms.internal.zzacs r0 = r14.zzWS     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.zzadz r6 = r0.zzav(r2)     // Catch:{ all -> 0x0082 }
            if (r6 == 0) goto L_0x005d
            com.google.android.gms.internal.zzadu r0 = r6.zzgX()     // Catch:{ all -> 0x0082 }
            if (r0 == 0) goto L_0x005d
            com.google.android.gms.internal.zzut r0 = r6.zzgW()     // Catch:{ all -> 0x0082 }
            if (r0 != 0) goto L_0x008c
        L_0x005d:
            java.util.List<com.google.android.gms.internal.zzadp> r0 = r14.zzWQ     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.zzadr r1 = new com.google.android.gms.internal.zzadr     // Catch:{ all -> 0x0082 }
            r1.<init>()     // Catch:{ all -> 0x0082 }
            java.lang.String r5 = r4.zzLK     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.zzadr r1 = r1.zzay(r5)     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.zzadr r1 = r1.zzax(r2)     // Catch:{ all -> 0x0082 }
            r6 = 0
            com.google.android.gms.internal.zzadr r1 = r1.zzg(r6)     // Catch:{ all -> 0x0082 }
            r2 = 7
            com.google.android.gms.internal.zzadr r1 = r1.zzw(r2)     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.zzadp r1 = r1.zzgU()     // Catch:{ all -> 0x0082 }
            r0.add(r1)     // Catch:{ all -> 0x0082 }
            monitor-exit(r13)     // Catch:{ all -> 0x0082 }
            goto L_0x001f
        L_0x0082:
            r0 = move-exception
            monitor-exit(r13)     // Catch:{ all -> 0x0082 }
            throw r0
        L_0x0085:
            r0 = move-exception
            java.lang.String r1 = "Unable to determine custom event class name, skipping..."
            com.google.android.gms.internal.zzafr.zzb(r1, r0)
            goto L_0x001f
        L_0x008c:
            com.google.android.gms.internal.zzadm r0 = new com.google.android.gms.internal.zzadm     // Catch:{ all -> 0x0082 }
            android.content.Context r1 = r14.mContext     // Catch:{ all -> 0x0082 }
            com.google.android.gms.internal.zzafg r5 = r14.zzQQ     // Catch:{ all -> 0x0082 }
            long r8 = r14.zzWC     // Catch:{ all -> 0x0082 }
            r7 = r14
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x0082 }
            java.util.ArrayList<java.util.concurrent.Future> r5 = r14.zzWN     // Catch:{ all -> 0x0082 }
            java.lang.Object r1 = r0.zzgp()     // Catch:{ all -> 0x0082 }
            java.util.concurrent.Future r1 = (java.util.concurrent.Future) r1     // Catch:{ all -> 0x0082 }
            r5.add(r1)     // Catch:{ all -> 0x0082 }
            java.util.ArrayList<java.lang.String> r1 = r14.zzWO     // Catch:{ all -> 0x0082 }
            r1.add(r2)     // Catch:{ all -> 0x0082 }
            java.util.HashMap<java.lang.String, com.google.android.gms.internal.zzadm> r1 = r14.zzWP     // Catch:{ all -> 0x0082 }
            r1.put(r2, r0)     // Catch:{ all -> 0x0082 }
            monitor-exit(r13)     // Catch:{ all -> 0x0082 }
            goto L_0x001f
        L_0x00b0:
            r0 = 0
            r1 = r0
        L_0x00b2:
            java.util.ArrayList<java.util.concurrent.Future> r0 = r14.zzWN
            int r0 = r0.size()
            if (r1 >= r0) goto L_0x0158
            java.util.ArrayList<java.util.concurrent.Future> r0 = r14.zzWN     // Catch:{ InterruptedException -> 0x012b, Exception -> 0x016b }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ InterruptedException -> 0x012b, Exception -> 0x016b }
            java.util.concurrent.Future r0 = (java.util.concurrent.Future) r0     // Catch:{ InterruptedException -> 0x012b, Exception -> 0x016b }
            r0.get()     // Catch:{ InterruptedException -> 0x012b, Exception -> 0x016b }
            java.lang.Object r2 = r14.mLock
            monitor-enter(r2)
            java.util.ArrayList<java.lang.String> r0 = r14.zzWO     // Catch:{ all -> 0x0128 }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ all -> 0x0128 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x0128 }
            boolean r3 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0128 }
            if (r3 != 0) goto L_0x00e9
            java.util.HashMap<java.lang.String, com.google.android.gms.internal.zzadm> r3 = r14.zzWP     // Catch:{ all -> 0x0128 }
            java.lang.Object r0 = r3.get(r0)     // Catch:{ all -> 0x0128 }
            com.google.android.gms.internal.zzadm r0 = (com.google.android.gms.internal.zzadm) r0     // Catch:{ all -> 0x0128 }
            if (r0 == 0) goto L_0x00e9
            java.util.List<com.google.android.gms.internal.zzadp> r3 = r14.zzWQ     // Catch:{ all -> 0x0128 }
            com.google.android.gms.internal.zzadp r0 = r0.zzgR()     // Catch:{ all -> 0x0128 }
            r3.add(r0)     // Catch:{ all -> 0x0128 }
        L_0x00e9:
            monitor-exit(r2)     // Catch:{ all -> 0x0128 }
            java.lang.Object r2 = r14.mLock
            monitor-enter(r2)
            java.util.HashSet<java.lang.String> r0 = r14.zzWR     // Catch:{ all -> 0x01ce }
            java.util.ArrayList<java.lang.String> r3 = r14.zzWO     // Catch:{ all -> 0x01ce }
            java.lang.Object r3 = r3.get(r1)     // Catch:{ all -> 0x01ce }
            boolean r0 = r0.contains(r3)     // Catch:{ all -> 0x01ce }
            if (r0 == 0) goto L_0x01cc
            java.util.ArrayList<java.lang.String> r0 = r14.zzWO     // Catch:{ all -> 0x01ce }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ all -> 0x01ce }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x01ce }
            java.util.HashMap<java.lang.String, com.google.android.gms.internal.zzadm> r1 = r14.zzWP     // Catch:{ all -> 0x01ce }
            java.lang.Object r1 = r1.get(r0)     // Catch:{ all -> 0x01ce }
            if (r1 == 0) goto L_0x01c9
            java.util.HashMap<java.lang.String, com.google.android.gms.internal.zzadm> r1 = r14.zzWP     // Catch:{ all -> 0x01ce }
            java.lang.Object r1 = r1.get(r0)     // Catch:{ all -> 0x01ce }
            com.google.android.gms.internal.zzadm r1 = (com.google.android.gms.internal.zzadm) r1     // Catch:{ all -> 0x01ce }
            com.google.android.gms.internal.zzua r1 = r1.zzgS()     // Catch:{ all -> 0x01ce }
        L_0x0117:
            r3 = -2
            com.google.android.gms.internal.zzaff r0 = r14.zza(r3, r0, r1)     // Catch:{ all -> 0x01ce }
            android.os.Handler r1 = com.google.android.gms.internal.zzaiy.zzaaH     // Catch:{ all -> 0x01ce }
            com.google.android.gms.internal.zzadx r3 = new com.google.android.gms.internal.zzadx     // Catch:{ all -> 0x01ce }
            r3.<init>(r14, r0)     // Catch:{ all -> 0x01ce }
            r1.post(r3)     // Catch:{ all -> 0x01ce }
            monitor-exit(r2)     // Catch:{ all -> 0x01ce }
        L_0x0127:
            return
        L_0x0128:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0128 }
            throw r0
        L_0x012b:
            r0 = move-exception
            java.lang.Thread r0 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x019e }
            r0.interrupt()     // Catch:{ all -> 0x019e }
            java.lang.Object r2 = r14.mLock
            monitor-enter(r2)
            java.util.ArrayList<java.lang.String> r0 = r14.zzWO     // Catch:{ all -> 0x0168 }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ all -> 0x0168 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x0168 }
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0168 }
            if (r1 != 0) goto L_0x0157
            java.util.HashMap<java.lang.String, com.google.android.gms.internal.zzadm> r1 = r14.zzWP     // Catch:{ all -> 0x0168 }
            java.lang.Object r0 = r1.get(r0)     // Catch:{ all -> 0x0168 }
            com.google.android.gms.internal.zzadm r0 = (com.google.android.gms.internal.zzadm) r0     // Catch:{ all -> 0x0168 }
            if (r0 == 0) goto L_0x0157
            java.util.List<com.google.android.gms.internal.zzadp> r1 = r14.zzWQ     // Catch:{ all -> 0x0168 }
            com.google.android.gms.internal.zzadp r0 = r0.zzgR()     // Catch:{ all -> 0x0168 }
            r1.add(r0)     // Catch:{ all -> 0x0168 }
        L_0x0157:
            monitor-exit(r2)     // Catch:{ all -> 0x0168 }
        L_0x0158:
            r0 = 3
            com.google.android.gms.internal.zzaff r0 = r14.zza(r0, r10, r10)
            android.os.Handler r1 = com.google.android.gms.internal.zzaiy.zzaaH
            com.google.android.gms.internal.zzady r2 = new com.google.android.gms.internal.zzady
            r2.<init>(r14, r0)
            r1.post(r2)
            goto L_0x0127
        L_0x0168:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0168 }
            throw r0
        L_0x016b:
            r0 = move-exception
            java.lang.String r2 = "Unable to resolve rewarded adapter."
            com.google.android.gms.internal.zzafr.zzc(r2, r0)     // Catch:{ all -> 0x019e }
            java.lang.Object r2 = r14.mLock
            monitor-enter(r2)
            java.util.ArrayList<java.lang.String> r0 = r14.zzWO     // Catch:{ all -> 0x019b }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ all -> 0x019b }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x019b }
            boolean r3 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x019b }
            if (r3 != 0) goto L_0x0195
            java.util.HashMap<java.lang.String, com.google.android.gms.internal.zzadm> r3 = r14.zzWP     // Catch:{ all -> 0x019b }
            java.lang.Object r0 = r3.get(r0)     // Catch:{ all -> 0x019b }
            com.google.android.gms.internal.zzadm r0 = (com.google.android.gms.internal.zzadm) r0     // Catch:{ all -> 0x019b }
            if (r0 == 0) goto L_0x0195
            java.util.List<com.google.android.gms.internal.zzadp> r3 = r14.zzWQ     // Catch:{ all -> 0x019b }
            com.google.android.gms.internal.zzadp r0 = r0.zzgR()     // Catch:{ all -> 0x019b }
            r3.add(r0)     // Catch:{ all -> 0x019b }
        L_0x0195:
            monitor-exit(r2)     // Catch:{ all -> 0x019b }
        L_0x0196:
            int r0 = r1 + 1
            r1 = r0
            goto L_0x00b2
        L_0x019b:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x019b }
            throw r0
        L_0x019e:
            r0 = move-exception
            r2 = r0
            java.lang.Object r3 = r14.mLock
            monitor-enter(r3)
            java.util.ArrayList<java.lang.String> r0 = r14.zzWO     // Catch:{ all -> 0x01c6 }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ all -> 0x01c6 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x01c6 }
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x01c6 }
            if (r1 != 0) goto L_0x01c4
            java.util.HashMap<java.lang.String, com.google.android.gms.internal.zzadm> r1 = r14.zzWP     // Catch:{ all -> 0x01c6 }
            java.lang.Object r0 = r1.get(r0)     // Catch:{ all -> 0x01c6 }
            com.google.android.gms.internal.zzadm r0 = (com.google.android.gms.internal.zzadm) r0     // Catch:{ all -> 0x01c6 }
            if (r0 == 0) goto L_0x01c4
            java.util.List<com.google.android.gms.internal.zzadp> r1 = r14.zzWQ     // Catch:{ all -> 0x01c6 }
            com.google.android.gms.internal.zzadp r0 = r0.zzgR()     // Catch:{ all -> 0x01c6 }
            r1.add(r0)     // Catch:{ all -> 0x01c6 }
        L_0x01c4:
            monitor-exit(r3)     // Catch:{ all -> 0x01c6 }
            throw r2
        L_0x01c6:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x01c6 }
            throw r0
        L_0x01c9:
            r1 = r10
            goto L_0x0117
        L_0x01cc:
            monitor-exit(r2)     // Catch:{ all -> 0x01ce }
            goto L_0x0196
        L_0x01ce:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x01ce }
            throw r0
        L_0x01d1:
            r2 = r0
            goto L_0x0046
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzadw.zzbd():void");
    }
}
