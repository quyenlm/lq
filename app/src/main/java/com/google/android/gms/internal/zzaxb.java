package com.google.android.gms.internal;

import org.json.JSONObject;

final class zzaxb extends zzaxe {
    private /* synthetic */ zzawy zzaxd;
    private /* synthetic */ int zzaxf;
    private /* synthetic */ String zzaxg;
    private /* synthetic */ JSONObject zzaxh;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzaxb(zzawy zzawy, int i, String str, JSONObject jSONObject) {
        super(zzawy);
        this.zzaxd = zzawy;
        this.zzaxf = i;
        this.zzaxg = str;
        this.zzaxh = jSONObject;
    }

    public final void execute() {
        int i;
        switch (this.zzaxf) {
            case 2:
                i = 5;
                break;
            case 3:
                i = 1;
                break;
            case 4:
                i = 2;
                break;
            case 5:
                i = 3;
                break;
            case 6:
                i = 4;
                break;
            default:
                i = 0;
                break;
        }
        if (i == 0) {
            this.zzarw.zza(-1, 2001, (Object) null);
            zzawy.zzapq.zzf("sendPlayerRequest for unsupported playerState: %d", Integer.valueOf(this.zzaxf));
            return;
        }
        this.zzaxd.zza(this.zzaxg, i, this.zzaxh, this.zzarw);
    }
}
