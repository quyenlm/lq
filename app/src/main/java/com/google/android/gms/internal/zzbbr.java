package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.zza;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Collections;
import java.util.Map;

final class zzbbr implements OnCompleteListener<Void> {
    private /* synthetic */ zzbbp zzaCP;

    private zzbbr(zzbbp zzbbp) {
        this.zzaCP = zzbbp;
    }

    public final void onComplete(@NonNull Task<Void> task) {
        this.zzaCP.zzaCv.lock();
        try {
            if (this.zzaCP.zzaCK) {
                if (task.isSuccessful()) {
                    Map unused = this.zzaCP.zzaCL = new ArrayMap(this.zzaCP.zzaCB.size());
                    for (zzbbo zzph : this.zzaCP.zzaCB.values()) {
                        this.zzaCP.zzaCL.put(zzph.zzph(), ConnectionResult.zzazX);
                    }
                } else if (task.getException() instanceof zza) {
                    zza zza = (zza) task.getException();
                    if (this.zzaCP.zzaCI) {
                        Map unused2 = this.zzaCP.zzaCL = new ArrayMap(this.zzaCP.zzaCB.size());
                        for (zzbbo zzbbo : this.zzaCP.zzaCB.values()) {
                            zzbat zzph2 = zzbbo.zzph();
                            ConnectionResult zza2 = zza.zza(zzbbo);
                            if (this.zzaCP.zza((zzbbo<?>) zzbbo, zza2)) {
                                this.zzaCP.zzaCL.put(zzph2, new ConnectionResult(16));
                            } else {
                                this.zzaCP.zzaCL.put(zzph2, zza2);
                            }
                        }
                    } else {
                        Map unused3 = this.zzaCP.zzaCL = zza.zzpf();
                    }
                    ConnectionResult unused4 = this.zzaCP.zzaCO = this.zzaCP.zzpN();
                } else {
                    Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                    Map unused5 = this.zzaCP.zzaCL = Collections.emptyMap();
                    ConnectionResult unused6 = this.zzaCP.zzaCO = new ConnectionResult(8);
                }
                if (this.zzaCP.zzaCM != null) {
                    this.zzaCP.zzaCL.putAll(this.zzaCP.zzaCM);
                    ConnectionResult unused7 = this.zzaCP.zzaCO = this.zzaCP.zzpN();
                }
                if (this.zzaCP.zzaCO == null) {
                    this.zzaCP.zzpL();
                    this.zzaCP.zzpM();
                } else {
                    boolean unused8 = this.zzaCP.zzaCK = false;
                    this.zzaCP.zzaCE.zzc(this.zzaCP.zzaCO);
                }
                this.zzaCP.zzaCG.signalAll();
                this.zzaCP.zzaCv.unlock();
            }
        } finally {
            this.zzaCP.zzaCv.unlock();
        }
    }
}
