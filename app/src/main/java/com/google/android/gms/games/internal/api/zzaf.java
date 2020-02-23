package com.google.android.gms.games.internal.api;

import android.content.Intent;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.internal.zze;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.internal.zzbaz;

public final class zzaf implements Leaderboards {
    public final Intent getAllLeaderboardsIntent(GoogleApiClient googleApiClient) {
        return Games.zzf(googleApiClient).zzuv();
    }

    public final Intent getLeaderboardIntent(GoogleApiClient googleApiClient, String str) {
        return getLeaderboardIntent(googleApiClient, str, -1);
    }

    public final Intent getLeaderboardIntent(GoogleApiClient googleApiClient, String str, int i) {
        return getLeaderboardIntent(googleApiClient, str, i, -1);
    }

    public final Intent getLeaderboardIntent(GoogleApiClient googleApiClient, String str, int i, int i2) {
        return Games.zzf(googleApiClient).zzj(str, i, i2);
    }

    public final PendingResult<Leaderboards.LoadPlayerScoreResult> loadCurrentPlayerLeaderboardScore(GoogleApiClient googleApiClient, String str, int i, int i2) {
        return googleApiClient.zzd(new zzai(this, googleApiClient, str, i, i2));
    }

    public final PendingResult<Leaderboards.LeaderboardMetadataResult> loadLeaderboardMetadata(GoogleApiClient googleApiClient, String str, boolean z) {
        return googleApiClient.zzd(new zzah(this, googleApiClient, str, z));
    }

    public final PendingResult<Leaderboards.LeaderboardMetadataResult> loadLeaderboardMetadata(GoogleApiClient googleApiClient, boolean z) {
        return googleApiClient.zzd(new zzag(this, googleApiClient, z));
    }

    public final PendingResult<Leaderboards.LoadScoresResult> loadMoreScores(GoogleApiClient googleApiClient, LeaderboardScoreBuffer leaderboardScoreBuffer, int i, int i2) {
        return googleApiClient.zzd(new zzal(this, googleApiClient, leaderboardScoreBuffer, i, i2));
    }

    public final PendingResult<Leaderboards.LoadScoresResult> loadPlayerCenteredScores(GoogleApiClient googleApiClient, String str, int i, int i2, int i3) {
        return loadPlayerCenteredScores(googleApiClient, str, i, i2, i3, false);
    }

    public final PendingResult<Leaderboards.LoadScoresResult> loadPlayerCenteredScores(GoogleApiClient googleApiClient, String str, int i, int i2, int i3, boolean z) {
        return googleApiClient.zzd(new zzak(this, googleApiClient, str, i, i2, i3, z));
    }

    public final PendingResult<Leaderboards.LoadScoresResult> loadTopScores(GoogleApiClient googleApiClient, String str, int i, int i2, int i3) {
        return loadTopScores(googleApiClient, str, i, i2, i3, false);
    }

    public final PendingResult<Leaderboards.LoadScoresResult> loadTopScores(GoogleApiClient googleApiClient, String str, int i, int i2, int i3, boolean z) {
        return googleApiClient.zzd(new zzaj(this, googleApiClient, str, i, i2, i3, z));
    }

    public final void submitScore(GoogleApiClient googleApiClient, String str, long j) {
        submitScore(googleApiClient, str, j, (String) null);
    }

    public final void submitScore(GoogleApiClient googleApiClient, String str, long j, String str2) {
        GamesClientImpl zza = Games.zza(googleApiClient, false);
        if (zza != null) {
            try {
                zza.zza((zzbaz<Leaderboards.SubmitScoreResult>) null, str, j, str2);
            } catch (RemoteException e) {
                zze.zzy("LeaderboardsImpl", "service died");
            }
        }
    }

    public final PendingResult<Leaderboards.SubmitScoreResult> submitScoreImmediate(GoogleApiClient googleApiClient, String str, long j) {
        return submitScoreImmediate(googleApiClient, str, j, (String) null);
    }

    public final PendingResult<Leaderboards.SubmitScoreResult> submitScoreImmediate(GoogleApiClient googleApiClient, String str, long j, String str2) {
        return googleApiClient.zze(new zzam(this, googleApiClient, str, j, str2));
    }
}
