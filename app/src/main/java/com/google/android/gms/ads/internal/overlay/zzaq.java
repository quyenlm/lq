package com.google.android.gms.ads.internal.overlay;

import com.google.android.gms.internal.zzmo;
import com.google.android.gms.internal.zzzn;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzaq {
    private String zzQt;
    private boolean zzQu;
    private int zzQv;
    private int zzQw;

    public zzaq(String str) {
        JSONObject jSONObject;
        JSONObject jSONObject2 = null;
        if (str != null) {
            try {
                jSONObject = new JSONObject(str);
            } catch (JSONException e) {
            }
        } else {
            jSONObject = null;
        }
        jSONObject2 = jSONObject;
        this.zzQu = zza(jSONObject2, "acquire_decoder_before_play", zzmo.zzCB);
        this.zzQt = zzc(jSONObject2, "exo_player_version", zzmo.zzCk);
        this.zzQw = zzb(jSONObject2, "exo_cache_buffer_size", zzmo.zzCp);
        this.zzQv = zzb(jSONObject2, "exo_allocator_segment_size", zzmo.zzCo);
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [com.google.android.gms.internal.zzme<java.lang.Boolean>, com.google.android.gms.internal.zzme] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean zza(org.json.JSONObject r1, java.lang.String r2, com.google.android.gms.internal.zzme<java.lang.Boolean> r3) {
        /*
            if (r1 == 0) goto L_0x0008
            boolean r0 = r1.getBoolean(r2)     // Catch:{ JSONException -> 0x0007 }
        L_0x0006:
            return r0
        L_0x0007:
            r0 = move-exception
        L_0x0008:
            com.google.android.gms.internal.zzmm r0 = com.google.android.gms.ads.internal.zzbs.zzbL()
            java.lang.Object r0 = r0.zzd(r3)
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.internal.overlay.zzaq.zza(org.json.JSONObject, java.lang.String, com.google.android.gms.internal.zzme):boolean");
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [com.google.android.gms.internal.zzme<java.lang.Integer>, com.google.android.gms.internal.zzme] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int zzb(org.json.JSONObject r1, java.lang.String r2, com.google.android.gms.internal.zzme<java.lang.Integer> r3) {
        /*
            if (r1 == 0) goto L_0x0008
            int r0 = r1.getInt(r2)     // Catch:{ JSONException -> 0x0007 }
        L_0x0006:
            return r0
        L_0x0007:
            r0 = move-exception
        L_0x0008:
            com.google.android.gms.internal.zzmm r0 = com.google.android.gms.ads.internal.zzbs.zzbL()
            java.lang.Object r0 = r0.zzd(r3)
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.internal.overlay.zzaq.zzb(org.json.JSONObject, java.lang.String, com.google.android.gms.internal.zzme):int");
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [com.google.android.gms.internal.zzme, com.google.android.gms.internal.zzme<java.lang.String>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String zzc(org.json.JSONObject r1, java.lang.String r2, com.google.android.gms.internal.zzme<java.lang.String> r3) {
        /*
            if (r1 == 0) goto L_0x0008
            java.lang.String r0 = r1.getString(r2)     // Catch:{ JSONException -> 0x0007 }
        L_0x0006:
            return r0
        L_0x0007:
            r0 = move-exception
        L_0x0008:
            com.google.android.gms.internal.zzmm r0 = com.google.android.gms.ads.internal.zzbs.zzbL()
            java.lang.Object r0 = r0.zzd(r3)
            java.lang.String r0 = (java.lang.String) r0
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.internal.overlay.zzaq.zzc(org.json.JSONObject, java.lang.String, com.google.android.gms.internal.zzme):java.lang.String");
    }
}
