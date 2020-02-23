package com.google.firebase.iid;

import android.support.annotation.Nullable;
import android.text.TextUtils;

public final class zzk {
    private static final Object zzuF = new Object();
    private final zzr zzckH;

    zzk(zzr zzr) {
        this.zzckH = zzr;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final String zzJV() {
        String str = null;
        synchronized (zzuF) {
            String string = this.zzckH.zzbho.getString("topic_operaion_queue", (String) null);
            if (string != null) {
                String[] split = string.split(",");
                if (split.length > 1 && !TextUtils.isEmpty(split[1])) {
                    str = split[1];
                }
            }
        }
        return str;
    }

    /* access modifiers changed from: package-private */
    public final void zzhf(String str) {
        synchronized (zzuF) {
            String string = this.zzckH.zzbho.getString("topic_operaion_queue", "");
            String valueOf = String.valueOf(",");
            this.zzckH.zzbho.edit().putString("topic_operaion_queue", new StringBuilder(String.valueOf(string).length() + String.valueOf(valueOf).length() + String.valueOf(str).length()).append(string).append(valueOf).append(str).toString()).apply();
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean zzhj(String str) {
        boolean z;
        synchronized (zzuF) {
            String string = this.zzckH.zzbho.getString("topic_operaion_queue", "");
            String valueOf = String.valueOf(",");
            String valueOf2 = String.valueOf(str);
            if (string.startsWith(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf))) {
                String valueOf3 = String.valueOf(",");
                String valueOf4 = String.valueOf(str);
                this.zzckH.zzbho.edit().putString("topic_operaion_queue", string.substring((valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3)).length())).apply();
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }
}
