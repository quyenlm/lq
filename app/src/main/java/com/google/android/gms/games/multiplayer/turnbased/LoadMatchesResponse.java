package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.internal.zze;
import com.google.android.gms.games.multiplayer.InvitationBuffer;

public final class LoadMatchesResponse {
    private final InvitationBuffer zzbdQ;
    private final TurnBasedMatchBuffer zzbdR;
    private final TurnBasedMatchBuffer zzbdS;
    private final TurnBasedMatchBuffer zzbdT;

    public LoadMatchesResponse(Bundle bundle) {
        DataHolder zzc = zzc(bundle, 0);
        if (zzc != null) {
            this.zzbdQ = new InvitationBuffer(zzc);
        } else {
            this.zzbdQ = null;
        }
        DataHolder zzc2 = zzc(bundle, 1);
        if (zzc2 != null) {
            this.zzbdR = new TurnBasedMatchBuffer(zzc2);
        } else {
            this.zzbdR = null;
        }
        DataHolder zzc3 = zzc(bundle, 2);
        if (zzc3 != null) {
            this.zzbdS = new TurnBasedMatchBuffer(zzc3);
        } else {
            this.zzbdS = null;
        }
        DataHolder zzc4 = zzc(bundle, 3);
        if (zzc4 != null) {
            this.zzbdT = new TurnBasedMatchBuffer(zzc4);
        } else {
            this.zzbdT = null;
        }
    }

    private static DataHolder zzc(Bundle bundle, int i) {
        String str;
        switch (i) {
            case 0:
                str = "TURN_STATUS_INVITED";
                break;
            case 1:
                str = "TURN_STATUS_MY_TURN";
                break;
            case 2:
                str = "TURN_STATUS_THEIR_TURN";
                break;
            case 3:
                str = "TURN_STATUS_COMPLETE";
                break;
            default:
                zze.zzz("MatchTurnStatus", new StringBuilder(38).append("Unknown match turn status: ").append(i).toString());
                str = "TURN_STATUS_UNKNOWN";
                break;
        }
        if (!bundle.containsKey(str)) {
            return null;
        }
        return (DataHolder) bundle.getParcelable(str);
    }

    @Deprecated
    public final void close() {
        release();
    }

    public final TurnBasedMatchBuffer getCompletedMatches() {
        return this.zzbdT;
    }

    public final InvitationBuffer getInvitations() {
        return this.zzbdQ;
    }

    public final TurnBasedMatchBuffer getMyTurnMatches() {
        return this.zzbdR;
    }

    public final TurnBasedMatchBuffer getTheirTurnMatches() {
        return this.zzbdS;
    }

    public final boolean hasData() {
        if (this.zzbdQ != null && this.zzbdQ.getCount() > 0) {
            return true;
        }
        if (this.zzbdR != null && this.zzbdR.getCount() > 0) {
            return true;
        }
        if (this.zzbdS == null || this.zzbdS.getCount() <= 0) {
            return this.zzbdT != null && this.zzbdT.getCount() > 0;
        }
        return true;
    }

    public final void release() {
        if (this.zzbdQ != null) {
            this.zzbdQ.release();
        }
        if (this.zzbdR != null) {
            this.zzbdR.release();
        }
        if (this.zzbdS != null) {
            this.zzbdS.release();
        }
        if (this.zzbdT != null) {
            this.zzbdT.release();
        }
    }
}
