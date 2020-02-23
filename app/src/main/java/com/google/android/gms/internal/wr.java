package com.google.android.gms.internal;

import com.tencent.imsdk.framework.request.HttpRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class wr implements xm {
    public static Comparator<wp> zzchR = new ws();
    private final nh<wp, xm> zzcgs;
    private final xm zzchS;
    private String zzchT;

    protected wr() {
        this.zzchT = null;
        this.zzcgs = ni.zza(zzchR);
        this.zzchS = xd.zzJb();
    }

    protected wr(nh<wp, xm> nhVar, xm xmVar) {
        this.zzchT = null;
        if (!nhVar.isEmpty() || xmVar.isEmpty()) {
            this.zzchS = xmVar;
            this.zzcgs = nhVar;
            return;
        }
        throw new IllegalArgumentException("Can't create empty ChildrenNode with priority!");
    }

    private static void zzb(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(" ");
        }
    }

    private final void zzc(StringBuilder sb, int i) {
        if (!this.zzcgs.isEmpty() || !this.zzchS.isEmpty()) {
            sb.append("{\n");
            Iterator<Map.Entry<wp, xm>> it = this.zzcgs.iterator();
            while (it.hasNext()) {
                Map.Entry next = it.next();
                zzb(sb, i + 2);
                sb.append(((wp) next.getKey()).asString());
                sb.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
                if (next.getValue() instanceof wr) {
                    ((wr) next.getValue()).zzc(sb, i + 2);
                } else {
                    sb.append(((xm) next.getValue()).toString());
                }
                sb.append("\n");
            }
            if (!this.zzchS.isEmpty()) {
                zzb(sb, i + 2);
                sb.append(".priority=");
                sb.append(this.zzchS.toString());
                sb.append("\n");
            }
            zzb(sb, i);
            sb.append("}");
            return;
        }
        sb.append("{ }");
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x004a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r9) {
        /*
            r8 = this;
            r4 = 1
            r3 = 0
            if (r9 != 0) goto L_0x0006
            r0 = r3
        L_0x0005:
            return r0
        L_0x0006:
            if (r9 != r8) goto L_0x000a
            r0 = r4
            goto L_0x0005
        L_0x000a:
            boolean r0 = r9 instanceof com.google.android.gms.internal.wr
            if (r0 != 0) goto L_0x0010
            r0 = r3
            goto L_0x0005
        L_0x0010:
            com.google.android.gms.internal.wr r9 = (com.google.android.gms.internal.wr) r9
            com.google.android.gms.internal.xm r0 = r8.zzIR()
            com.google.android.gms.internal.xm r1 = r9.zzIR()
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0022
            r0 = r3
            goto L_0x0005
        L_0x0022:
            com.google.android.gms.internal.nh<com.google.android.gms.internal.wp, com.google.android.gms.internal.xm> r0 = r8.zzcgs
            int r0 = r0.size()
            com.google.android.gms.internal.nh<com.google.android.gms.internal.wp, com.google.android.gms.internal.xm> r1 = r9.zzcgs
            int r1 = r1.size()
            if (r0 == r1) goto L_0x0032
            r0 = r3
            goto L_0x0005
        L_0x0032:
            com.google.android.gms.internal.nh<com.google.android.gms.internal.wp, com.google.android.gms.internal.xm> r0 = r8.zzcgs
            java.util.Iterator r5 = r0.iterator()
            com.google.android.gms.internal.nh<com.google.android.gms.internal.wp, com.google.android.gms.internal.xm> r0 = r9.zzcgs
            java.util.Iterator r6 = r0.iterator()
        L_0x003e:
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto L_0x0078
            boolean r0 = r6.hasNext()
            if (r0 == 0) goto L_0x0078
            java.lang.Object r0 = r5.next()
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            java.lang.Object r1 = r6.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            java.lang.Object r2 = r0.getKey()
            com.google.android.gms.internal.wp r2 = (com.google.android.gms.internal.wp) r2
            java.lang.Object r7 = r1.getKey()
            boolean r2 = r2.equals(r7)
            if (r2 == 0) goto L_0x0076
            java.lang.Object r0 = r0.getValue()
            com.google.android.gms.internal.xm r0 = (com.google.android.gms.internal.xm) r0
            java.lang.Object r1 = r1.getValue()
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x003e
        L_0x0076:
            r0 = r3
            goto L_0x0005
        L_0x0078:
            boolean r0 = r5.hasNext()
            if (r0 != 0) goto L_0x0084
            boolean r0 = r6.hasNext()
            if (r0 == 0) goto L_0x008c
        L_0x0084:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Something went wrong internally."
            r0.<init>(r1)
            throw r0
        L_0x008c:
            r0 = r4
            goto L_0x0005
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.wr.equals(java.lang.Object):boolean");
    }

    public int getChildCount() {
        return this.zzcgs.size();
    }

    public Object getValue() {
        return getValue(false);
    }

    public Object getValue(boolean z) {
        boolean z2;
        Integer zzha;
        if (isEmpty()) {
            return null;
        }
        HashMap hashMap = new HashMap();
        Iterator<Map.Entry<wp, xm>> it = this.zzcgs.iterator();
        boolean z3 = true;
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            Map.Entry next = it.next();
            String asString = ((wp) next.getKey()).asString();
            hashMap.put(asString, ((xm) next.getValue()).getValue(z));
            int i3 = i2 + 1;
            if (z3) {
                if ((asString.length() > 1 && asString.charAt(0) == '0') || (zzha = zd.zzha(asString)) == null || zzha.intValue() < 0) {
                    z2 = false;
                    z3 = z2;
                    i2 = i3;
                } else if (zzha.intValue() > i) {
                    i = zzha.intValue();
                    i2 = i3;
                }
            }
            z2 = z3;
            z3 = z2;
            i2 = i3;
        }
        if (z || !z3 || i >= i2 * 2) {
            if (z && !this.zzchS.isEmpty()) {
                hashMap.put(".priority", this.zzchS.getValue());
            }
            return hashMap;
        }
        ArrayList arrayList = new ArrayList(i + 1);
        for (int i4 = 0; i4 <= i; i4++) {
            arrayList.add(hashMap.get(new StringBuilder(11).append(i4).toString()));
        }
        return arrayList;
    }

    public int hashCode() {
        int i = 0;
        Iterator<xl> it = iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return i2;
            }
            xl next = it.next();
            i = next.zzFn().hashCode() + (((i2 * 31) + next.zzJk().hashCode()) * 17);
        }
    }

    public boolean isEmpty() {
        return this.zzcgs.isEmpty();
    }

    public Iterator<xl> iterator() {
        return new wv(this.zzcgs.iterator());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        zzc(sb, 0);
        return sb.toString();
    }

    public Iterator<xl> zzFz() {
        return new wv(this.zzcgs.zzFz());
    }

    public String zzIP() {
        if (this.zzchT == null) {
            String zza = zza(xo.V1);
            this.zzchT = zza.isEmpty() ? "" : zd.zzgY(zza);
        }
        return this.zzchT;
    }

    public boolean zzIQ() {
        return false;
    }

    public xm zzIR() {
        return this.zzchS;
    }

    public final wp zzIS() {
        return this.zzcgs.zzFx();
    }

    public final wp zzIT() {
        return this.zzcgs.zzFy();
    }

    public xm zzN(qr qrVar) {
        wp zzHc = qrVar.zzHc();
        return zzHc == null ? this : zzm(zzHc).zzN(qrVar.zzHd());
    }

    public String zza(xo xoVar) {
        int i = 0;
        if (xoVar != xo.V1) {
            throw new IllegalArgumentException("Hashes on children nodes only supported for V1");
        }
        StringBuilder sb = new StringBuilder();
        if (!this.zzchS.isEmpty()) {
            sb.append("priority:");
            sb.append(this.zzchS.zza(xo.V1));
            sb.append(":");
        }
        ArrayList arrayList = new ArrayList();
        Iterator<xl> it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            xl next = it.next();
            arrayList.add(next);
            z = z || !next.zzFn().zzIR().isEmpty();
        }
        if (z) {
            Collections.sort(arrayList, xr.zzJl());
        }
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            xl xlVar = (xl) obj;
            String zzIP = xlVar.zzFn().zzIP();
            if (!zzIP.equals("")) {
                sb.append(":");
                sb.append(xlVar.zzJk().asString());
                sb.append(":");
                sb.append(zzIP);
            }
        }
        return sb.toString();
    }

    public final void zza(wu wuVar, boolean z) {
        if (!z || zzIR().isEmpty()) {
            this.zzcgs.zza(wuVar);
        } else {
            this.zzcgs.zza(new wt(this, wuVar));
        }
    }

    public xm zze(wp wpVar, xm xmVar) {
        if (wpVar.zzIN()) {
            return zzf(xmVar);
        }
        nh<wp, xm> nhVar = this.zzcgs;
        if (nhVar.containsKey(wpVar)) {
            nhVar = nhVar.zzX(wpVar);
        }
        if (!xmVar.isEmpty()) {
            nhVar = nhVar.zzg(wpVar, xmVar);
        }
        return nhVar.isEmpty() ? xd.zzJb() : new wr(nhVar, this.zzchS);
    }

    public xm zzf(xm xmVar) {
        return this.zzcgs.isEmpty() ? xd.zzJb() : new wr(this.zzcgs, xmVar);
    }

    /* renamed from: zzg */
    public int compareTo(xm xmVar) {
        if (isEmpty()) {
            return xmVar.isEmpty() ? 0 : -1;
        }
        if (xmVar.zzIQ()) {
            return 1;
        }
        if (xmVar.isEmpty()) {
            return 1;
        }
        return xmVar == xm.zzciw ? -1 : 0;
    }

    public boolean zzk(wp wpVar) {
        return !zzm(wpVar).isEmpty();
    }

    public wp zzl(wp wpVar) {
        return this.zzcgs.zzY(wpVar);
    }

    public xm zzl(qr qrVar, xm xmVar) {
        wp zzHc = qrVar.zzHc();
        return zzHc == null ? xmVar : zzHc.zzIN() ? zzf(xmVar) : zze(zzHc, zzm(zzHc).zzl(qrVar.zzHd(), xmVar));
    }

    public xm zzm(wp wpVar) {
        return (!wpVar.zzIN() || this.zzchS.isEmpty()) ? this.zzcgs.containsKey(wpVar) ? this.zzcgs.get(wpVar) : xd.zzJb() : this.zzchS;
    }
}
