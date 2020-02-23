package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.ads.internal.zzw;
import java.util.Map;

@zzzn
public final class zzrq implements zzrd {
    private final zzw zzJE;
    private final zzwk zzJF;

    public zzrq(zzw zzw, zzwk zzwk) {
        this.zzJE = zzw;
        this.zzJF = zzwk;
    }

    private static boolean zze(Map<String, String> map) {
        return "1".equals(map.get("custom_close"));
    }

    private static int zzf(Map<String, String> map) {
        String str = map.get("o");
        if (str != null) {
            if ("p".equalsIgnoreCase(str)) {
                return zzbs.zzbB().zzhU();
            }
            if ("l".equalsIgnoreCase(str)) {
                return zzbs.zzbB().zzhT();
            }
            if ("c".equalsIgnoreCase(str)) {
                return zzbs.zzbB().zzhV();
            }
        }
        return -1;
    }

    private final void zzj(boolean z) {
        if (this.zzJF != null) {
            this.zzJF.zzk(z);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x0113  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0123  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0165  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(com.google.android.gms.internal.zzaka r10, java.util.Map<java.lang.String, java.lang.String> r11) {
        /*
            r9 = this;
            r4 = 1
            r3 = 0
            java.lang.String r0 = "u"
            java.lang.Object r0 = r11.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            android.content.Context r1 = r10.getContext()
            java.lang.String r2 = com.google.android.gms.internal.zzaez.zzb(r0, r1)
            java.lang.String r0 = "a"
            java.lang.Object r0 = r11.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 != 0) goto L_0x0022
            java.lang.String r0 = "Action missing from an open GMSG."
            com.google.android.gms.internal.zzafr.zzaT(r0)
        L_0x0021:
            return
        L_0x0022:
            com.google.android.gms.ads.internal.zzw r1 = r9.zzJE
            if (r1 == 0) goto L_0x0034
            com.google.android.gms.ads.internal.zzw r1 = r9.zzJE
            boolean r1 = r1.zzaR()
            if (r1 != 0) goto L_0x0034
            com.google.android.gms.ads.internal.zzw r0 = r9.zzJE
            r0.zzt(r2)
            goto L_0x0021
        L_0x0034:
            com.google.android.gms.internal.zzakb r8 = r10.zziw()
            java.lang.String r1 = "expand"
            boolean r1 = r1.equalsIgnoreCase(r0)
            if (r1 == 0) goto L_0x005b
            boolean r0 = r10.zziA()
            if (r0 == 0) goto L_0x004c
            java.lang.String r0 = "Cannot expand WebView that is already expanded."
            com.google.android.gms.internal.zzafr.zzaT(r0)
            goto L_0x0021
        L_0x004c:
            r9.zzj(r3)
            boolean r0 = zze(r11)
            int r1 = zzf(r11)
            r8.zza((boolean) r0, (int) r1)
            goto L_0x0021
        L_0x005b:
            java.lang.String r1 = "webapp"
            boolean r1 = r1.equalsIgnoreCase(r0)
            if (r1 == 0) goto L_0x0090
            r9.zzj(r3)
            if (r2 == 0) goto L_0x0074
            boolean r0 = zze(r11)
            int r1 = zzf(r11)
            r8.zza((boolean) r0, (int) r1, (java.lang.String) r2)
            goto L_0x0021
        L_0x0074:
            boolean r2 = zze(r11)
            int r3 = zzf(r11)
            java.lang.String r0 = "html"
            java.lang.Object r0 = r11.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r1 = "baseurl"
            java.lang.Object r1 = r11.get(r1)
            java.lang.String r1 = (java.lang.String) r1
            r8.zza((boolean) r2, (int) r3, (java.lang.String) r0, (java.lang.String) r1)
            goto L_0x0021
        L_0x0090:
            java.lang.String r1 = "in_app_purchase"
            boolean r1 = r1.equalsIgnoreCase(r0)
            if (r1 != 0) goto L_0x0021
            java.lang.String r1 = "app"
            boolean r0 = r1.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x00e5
            java.lang.String r1 = "true"
            java.lang.String r0 = "system_browser"
            java.lang.Object r0 = r11.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            boolean r0 = r1.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x00e5
            r9.zzj(r4)
            android.content.Context r0 = r10.getContext()
            boolean r1 = android.text.TextUtils.isEmpty(r2)
            if (r1 == 0) goto L_0x00c4
            java.lang.String r0 = "Destination url cannot be empty."
            com.google.android.gms.internal.zzafr.zzaT(r0)
            goto L_0x0021
        L_0x00c4:
            com.google.android.gms.internal.zzakb r1 = r10.zziw()
            com.google.android.gms.internal.zzrr r2 = new com.google.android.gms.internal.zzrr
            r2.<init>(r10)
            android.content.Intent r0 = r2.zza((android.content.Context) r0, (java.util.Map<java.lang.String, java.lang.String>) r11)
            com.google.android.gms.ads.internal.overlay.zzc r2 = new com.google.android.gms.ads.internal.overlay.zzc     // Catch:{ ActivityNotFoundException -> 0x00db }
            r2.<init>(r0)     // Catch:{ ActivityNotFoundException -> 0x00db }
            r1.zza((com.google.android.gms.ads.internal.overlay.zzc) r2)     // Catch:{ ActivityNotFoundException -> 0x00db }
            goto L_0x0021
        L_0x00db:
            r0 = move-exception
            java.lang.String r0 = r0.getMessage()
            com.google.android.gms.internal.zzafr.zzaT(r0)
            goto L_0x0021
        L_0x00e5:
            r9.zzj(r4)
            java.lang.String r0 = "intent_url"
            java.lang.Object r0 = r11.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            r1 = 0
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            if (r3 != 0) goto L_0x0141
            r3 = 0
            android.content.Intent r0 = android.content.Intent.parseUri(r0, r3)     // Catch:{ URISyntaxException -> 0x012d }
            r3 = r0
        L_0x00fd:
            if (r3 == 0) goto L_0x0121
            android.net.Uri r0 = r3.getData()
            if (r0 == 0) goto L_0x0121
            android.net.Uri r1 = r3.getData()
            java.lang.String r0 = r1.toString()
            boolean r4 = android.text.TextUtils.isEmpty(r0)
            if (r4 != 0) goto L_0x015d
            com.google.android.gms.ads.internal.zzbs.zzbz()
            java.lang.String r0 = com.google.android.gms.internal.zzagz.zzb((com.google.android.gms.internal.zzaka) r10, (java.lang.String) r0)
            android.net.Uri r0 = android.net.Uri.parse(r0)     // Catch:{ Exception -> 0x0149 }
        L_0x011e:
            r3.setData(r0)
        L_0x0121:
            if (r3 == 0) goto L_0x0165
            com.google.android.gms.ads.internal.overlay.zzc r0 = new com.google.android.gms.ads.internal.overlay.zzc
            r0.<init>(r3)
            r8.zza((com.google.android.gms.ads.internal.overlay.zzc) r0)
            goto L_0x0021
        L_0x012d:
            r3 = move-exception
            java.lang.String r4 = "Error parsing the url: "
            java.lang.String r0 = java.lang.String.valueOf(r0)
            int r5 = r0.length()
            if (r5 == 0) goto L_0x0143
            java.lang.String r0 = r4.concat(r0)
        L_0x013e:
            com.google.android.gms.internal.zzafr.zzb(r0, r3)
        L_0x0141:
            r3 = r1
            goto L_0x00fd
        L_0x0143:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r4)
            goto L_0x013e
        L_0x0149:
            r4 = move-exception
            java.lang.String r5 = "Error parsing the uri: "
            java.lang.String r0 = java.lang.String.valueOf(r0)
            int r6 = r0.length()
            if (r6 == 0) goto L_0x015f
            java.lang.String r0 = r5.concat(r0)
        L_0x015a:
            com.google.android.gms.internal.zzafr.zzb(r0, r4)
        L_0x015d:
            r0 = r1
            goto L_0x011e
        L_0x015f:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r5)
            goto L_0x015a
        L_0x0165:
            boolean r0 = android.text.TextUtils.isEmpty(r2)
            if (r0 != 0) goto L_0x0172
            com.google.android.gms.ads.internal.zzbs.zzbz()
            java.lang.String r2 = com.google.android.gms.internal.zzagz.zzb((com.google.android.gms.internal.zzaka) r10, (java.lang.String) r2)
        L_0x0172:
            com.google.android.gms.ads.internal.overlay.zzc r0 = new com.google.android.gms.ads.internal.overlay.zzc
            java.lang.String r1 = "i"
            java.lang.Object r1 = r11.get(r1)
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r3 = "m"
            java.lang.Object r3 = r11.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r4 = "p"
            java.lang.Object r4 = r11.get(r4)
            java.lang.String r4 = (java.lang.String) r4
            java.lang.String r5 = "c"
            java.lang.Object r5 = r11.get(r5)
            java.lang.String r5 = (java.lang.String) r5
            java.lang.String r6 = "f"
            java.lang.Object r6 = r11.get(r6)
            java.lang.String r6 = (java.lang.String) r6
            java.lang.String r7 = "e"
            java.lang.Object r7 = r11.get(r7)
            java.lang.String r7 = (java.lang.String) r7
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            r8.zza((com.google.android.gms.ads.internal.overlay.zzc) r0)
            goto L_0x0021
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrq.zza(com.google.android.gms.internal.zzaka, java.util.Map):void");
    }
}
