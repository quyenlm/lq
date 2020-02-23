package com.google.android.gms.internal;

import android.os.RemoteException;

final class zzaku implements Runnable {
    private /* synthetic */ zzaks zzacC;
    private /* synthetic */ int zzacD;
    private /* synthetic */ int zzacE;
    private /* synthetic */ boolean zzacF;
    private /* synthetic */ boolean zzacG;

    zzaku(zzaks zzaks, int i, int i2, boolean z, boolean z2) {
        this.zzacC = zzaks;
        this.zzacD = i;
        this.zzacE = i2;
        this.zzacF = z;
        this.zzacG = z2;
    }

    public final void run() {
        boolean z = false;
        synchronized (this.zzacC.mLock) {
            boolean z2 = this.zzacD != this.zzacE;
            boolean z3 = !this.zzacC.zzacx && this.zzacE == 1;
            boolean z4 = z2 && this.zzacE == 1;
            boolean z5 = z2 && this.zzacE == 2;
            boolean z6 = z2 && this.zzacE == 3;
            boolean z7 = this.zzacF != this.zzacG;
            zzaks zzaks = this.zzacC;
            if (this.zzacC.zzacx || z3) {
                z = true;
            }
            boolean unused = zzaks.zzacx = z;
            if (this.zzacC.zzacw != null) {
                if (z3) {
                    try {
                        this.zzacC.zzacw.onVideoStart();
                    } catch (RemoteException e) {
                        zzafr.zzc("Unable to call onVideoStart()", e);
                    }
                }
                if (z4) {
                    try {
                        this.zzacC.zzacw.onVideoPlay();
                    } catch (RemoteException e2) {
                        zzafr.zzc("Unable to call onVideoPlay()", e2);
                    }
                }
                if (z5) {
                    try {
                        this.zzacC.zzacw.onVideoPause();
                    } catch (RemoteException e3) {
                        zzafr.zzc("Unable to call onVideoPause()", e3);
                    }
                }
                if (z6) {
                    try {
                        this.zzacC.zzacw.onVideoEnd();
                    } catch (RemoteException e4) {
                        zzafr.zzc("Unable to call onVideoEnd()", e4);
                    }
                }
                if (z7) {
                    try {
                        this.zzacC.zzacw.onVideoMute(this.zzacG);
                    } catch (RemoteException e5) {
                        zzafr.zzc("Unable to call onVideoMute()", e5);
                    }
                }
            }
        }
    }
}
