package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.zzbs;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@zzzn
public final class zzgu {
    private final Object mLock = new Object();
    private int zzyl;
    private List<zzgt> zzym = new LinkedList();

    public final boolean zza(zzgt zzgt) {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzym.contains(zzgt);
        }
        return z;
    }

    public final boolean zzb(zzgt zzgt) {
        synchronized (this.mLock) {
            Iterator<zzgt> it = this.zzym.iterator();
            while (it.hasNext()) {
                zzgt next = it.next();
                if (!((Boolean) zzbs.zzbL().zzd(zzmo.zzCZ)).booleanValue() || zzbs.zzbD().zzhn()) {
                    if (((Boolean) zzbs.zzbL().zzd(zzmo.zzDb)).booleanValue() && !zzbs.zzbD().zzho() && zzgt != next && next.zzcF().equals(zzgt.zzcF())) {
                        it.remove();
                        return true;
                    }
                } else if (zzgt != next && next.zzcD().equals(zzgt.zzcD())) {
                    it.remove();
                    return true;
                }
            }
            return false;
        }
    }

    public final void zzc(zzgt zzgt) {
        synchronized (this.mLock) {
            if (this.zzym.size() >= 10) {
                zzafr.zzaC(new StringBuilder(41).append("Queue is full, current size = ").append(this.zzym.size()).toString());
                this.zzym.remove(0);
            }
            int i = this.zzyl;
            this.zzyl = i + 1;
            zzgt.zzj(i);
            this.zzym.add(zzgt);
        }
    }

    @Nullable
    public final zzgt zzcL() {
        zzgt zzgt = null;
        synchronized (this.mLock) {
            if (this.zzym.size() == 0) {
                zzafr.zzaC("Queue empty");
                return null;
            } else if (this.zzym.size() >= 2) {
                int i = Integer.MIN_VALUE;
                int i2 = 0;
                int i3 = 0;
                for (zzgt next : this.zzym) {
                    int score = next.getScore();
                    if (score > i) {
                        i2 = i3;
                    } else {
                        score = i;
                        next = zzgt;
                    }
                    i3++;
                    i = score;
                    zzgt = next;
                }
                this.zzym.remove(i2);
                return zzgt;
            } else {
                zzgt zzgt2 = this.zzym.get(0);
                zzgt2.zzcG();
                return zzgt2;
            }
        }
    }
}
