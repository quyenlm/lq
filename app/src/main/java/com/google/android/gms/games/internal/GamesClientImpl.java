package com.google.android.gms.games.internal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.BinderWrapper;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameBuffer;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesMetadata;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerBuffer;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.Players;
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.games.event.EventBuffer;
import com.google.android.gms.games.event.Events;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardEntity;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardScoreEntity;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.games.leaderboard.ScoreSubmissionData;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.InvitationBuffer;
import com.google.android.gms.games.multiplayer.Invitations;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMultiplayer;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomEntity;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.android.gms.games.multiplayer.turnbased.LoadMatchesResponse;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchUpdateReceivedListener;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchBuffer;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.android.gms.games.quest.Milestone;
import com.google.android.gms.games.quest.Quest;
import com.google.android.gms.games.quest.QuestBuffer;
import com.google.android.gms.games.quest.QuestEntity;
import com.google.android.gms.games.quest.QuestUpdateListener;
import com.google.android.gms.games.quest.Quests;
import com.google.android.gms.games.request.GameRequest;
import com.google.android.gms.games.request.GameRequestBuffer;
import com.google.android.gms.games.request.OnRequestReceivedListener;
import com.google.android.gms.games.request.Requests;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotContents;
import com.google.android.gms.games.snapshot.SnapshotEntity;
import com.google.android.gms.games.snapshot.SnapshotMetadata;
import com.google.android.gms.games.snapshot.SnapshotMetadataBuffer;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.games.snapshot.SnapshotMetadataEntity;
import com.google.android.gms.games.snapshot.Snapshots;
import com.google.android.gms.games.stats.PlayerStats;
import com.google.android.gms.games.stats.PlayerStatsBuffer;
import com.google.android.gms.games.stats.Stats;
import com.google.android.gms.games.video.CaptureState;
import com.google.android.gms.games.video.VideoCapabilities;
import com.google.android.gms.games.video.Videos;
import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.internal.zzbbx;
import com.google.android.gms.internal.zzbby;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.internal.zzbdz;
import com.google.android.gms.internal.zzcaj;
import com.google.android.gms.internal.zzcal;
import com.google.android.gms.internal.zzcam;
import com.google.android.gms.internal.zzctu;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class GamesClientImpl extends com.google.android.gms.common.internal.zzz<zzj> {
    private zzcal zzaZp = new zzd(this);
    private final String zzaZq;
    private PlayerEntity zzaZr;
    private GameEntity zzaZs;
    private final zzn zzaZt;
    private boolean zzaZu = false;
    private final Binder zzaZv;
    private final long zzaZw;
    private final Games.GamesOptions zzaZx;
    private boolean zzaZy = false;

    static final class CaptureStreamingUrlResultImpl implements Videos.CaptureStreamingUrlResult {
        private final Status mStatus;
        private final String zzD;

        public final Status getStatus() {
            return this.mStatus;
        }

        public final String getUrl() {
            return this.zzD;
        }
    }

    static abstract class zza extends zzc {
        private final ArrayList<String> zzaZA = new ArrayList<>();

        zza(DataHolder dataHolder, String[] strArr) {
            super(dataHolder);
            for (String add : strArr) {
                this.zzaZA.add(add);
            }
        }

        /* access modifiers changed from: protected */
        public final void zza(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            zza(roomStatusUpdateListener, room, this.zzaZA);
        }

        /* access modifiers changed from: protected */
        public abstract void zza(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList);
    }

    static final class zzaa extends zzcr implements TurnBasedMultiplayer.InitiateMatchResult {
        zzaa(DataHolder dataHolder) {
            super(dataHolder);
        }
    }

    static final class zzab extends zza {
        private final zzbdw<OnInvitationReceivedListener> zzaOo;

        zzab(zzbdw<OnInvitationReceivedListener> zzbdw) {
            this.zzaOo = zzbdw;
        }

        public final void onInvitationRemoved(String str) {
            this.zzaOo.zza(new zzad(str));
        }

        public final void zzn(DataHolder dataHolder) {
            InvitationBuffer invitationBuffer = new InvitationBuffer(dataHolder);
            Invitation invitation = null;
            try {
                if (invitationBuffer.getCount() > 0) {
                    invitation = (Invitation) ((Invitation) invitationBuffer.get(0)).freeze();
                }
                if (invitation != null) {
                    this.zzaOo.zza(new zzac(invitation));
                }
            } finally {
                invitationBuffer.release();
            }
        }
    }

    static final class zzac implements zzbdz<OnInvitationReceivedListener> {
        private final Invitation zzaZL;

        zzac(Invitation invitation) {
            this.zzaZL = invitation;
        }

        public final void zzpT() {
        }

        public final /* synthetic */ void zzq(Object obj) {
            ((OnInvitationReceivedListener) obj).onInvitationReceived(this.zzaZL);
        }
    }

    static final class zzad implements zzbdz<OnInvitationReceivedListener> {
        private final String zzajX;

        zzad(String str) {
            this.zzajX = str;
        }

        public final void zzpT() {
        }

        public final /* synthetic */ void zzq(Object obj) {
            ((OnInvitationReceivedListener) obj).onInvitationRemoved(this.zzajX);
        }
    }

    static final class zzae extends zza {
        private final zzbaz<Invitations.LoadInvitationsResult> zzaIz;

        zzae(zzbaz<Invitations.LoadInvitationsResult> zzbaz) {
            this.zzaIz = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzm(DataHolder dataHolder) {
            this.zzaIz.setResult(new zzao(dataHolder));
        }
    }

    static final class zzaf extends zzb {
        public zzaf(DataHolder dataHolder) {
            super(dataHolder);
        }

        public final void zza(RoomUpdateListener roomUpdateListener, Room room, int i) {
            roomUpdateListener.onJoinedRoom(i, room);
        }
    }

    static final class zzag extends zzw implements Leaderboards.LeaderboardMetadataResult {
        private final LeaderboardBuffer zzaZM;

        zzag(DataHolder dataHolder) {
            super(dataHolder);
            this.zzaZM = new LeaderboardBuffer(dataHolder);
        }

        public final LeaderboardBuffer getLeaderboards() {
            return this.zzaZM;
        }
    }

    static final class zzah extends zza {
        private final zzbaz<Leaderboards.LoadScoresResult> zzaIz;

        zzah(zzbaz<Leaderboards.LoadScoresResult> zzbaz) {
            this.zzaIz = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zza(DataHolder dataHolder, DataHolder dataHolder2) {
            this.zzaIz.setResult(new zzaw(dataHolder, dataHolder2));
        }
    }

    static final class zzai extends zza {
        private final zzbaz<Leaderboards.LeaderboardMetadataResult> zzaIz;

        zzai(zzbaz<Leaderboards.LeaderboardMetadataResult> zzbaz) {
            this.zzaIz = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzh(DataHolder dataHolder) {
            this.zzaIz.setResult(new zzag(dataHolder));
        }
    }

    static final class zzaj extends zzcr implements TurnBasedMultiplayer.LeaveMatchResult {
        zzaj(DataHolder dataHolder) {
            super(dataHolder);
        }
    }

    static final class zzak implements zzbdz<RoomUpdateListener> {
        private final String zzaZN;
        private final int zzaxu;

        zzak(int i, String str) {
            this.zzaxu = i;
            this.zzaZN = str;
        }

        public final void zzpT() {
        }

        public final /* synthetic */ void zzq(Object obj) {
            ((RoomUpdateListener) obj).onLeftRoom(this.zzaxu, this.zzaZN);
        }
    }

    static final class zzal extends zzw implements Achievements.LoadAchievementsResult {
        private final AchievementBuffer zzaZO;

        zzal(DataHolder dataHolder) {
            super(dataHolder);
            this.zzaZO = new AchievementBuffer(dataHolder);
        }

        public final AchievementBuffer getAchievements() {
            return this.zzaZO;
        }
    }

    static final class zzam extends zzw implements Events.LoadEventsResult {
        private final EventBuffer zzaZP;

        zzam(DataHolder dataHolder) {
            super(dataHolder);
            this.zzaZP = new EventBuffer(dataHolder);
        }

        public final EventBuffer getEvents() {
            return this.zzaZP;
        }
    }

    static final class zzan extends zzw implements GamesMetadata.LoadGamesResult {
        private final GameBuffer zzaZQ;

        zzan(DataHolder dataHolder) {
            super(dataHolder);
            this.zzaZQ = new GameBuffer(dataHolder);
        }

        public final GameBuffer getGames() {
            return this.zzaZQ;
        }
    }

    static final class zzao extends zzw implements Invitations.LoadInvitationsResult {
        private final InvitationBuffer zzaZR;

        zzao(DataHolder dataHolder) {
            super(dataHolder);
            this.zzaZR = new InvitationBuffer(dataHolder);
        }

        public final InvitationBuffer getInvitations() {
            return this.zzaZR;
        }
    }

    static final class zzap extends zzcr implements TurnBasedMultiplayer.LoadMatchResult {
        zzap(DataHolder dataHolder) {
            super(dataHolder);
        }
    }

    static final class zzaq implements TurnBasedMultiplayer.LoadMatchesResult {
        private final Status mStatus;
        private final LoadMatchesResponse zzaZS;

        zzaq(Status status, Bundle bundle) {
            this.mStatus = status;
            this.zzaZS = new LoadMatchesResponse(bundle);
        }

        public final LoadMatchesResponse getMatches() {
            return this.zzaZS;
        }

        public final Status getStatus() {
            return this.mStatus;
        }

        public final void release() {
            this.zzaZS.release();
        }
    }

    static final class zzar extends zzw implements Leaderboards.LoadPlayerScoreResult {
        private final LeaderboardScoreEntity zzaZT;

        zzar(DataHolder dataHolder) {
            super(dataHolder);
            LeaderboardScoreBuffer leaderboardScoreBuffer = new LeaderboardScoreBuffer(dataHolder);
            try {
                if (leaderboardScoreBuffer.getCount() > 0) {
                    this.zzaZT = (LeaderboardScoreEntity) ((LeaderboardScore) leaderboardScoreBuffer.get(0)).freeze();
                } else {
                    this.zzaZT = null;
                }
            } finally {
                leaderboardScoreBuffer.release();
            }
        }

        public final LeaderboardScore getScore() {
            return this.zzaZT;
        }
    }

    static final class zzas extends zzw implements Stats.LoadPlayerStatsResult {
        private final PlayerStats zzaZU;

        zzas(DataHolder dataHolder) {
            super(dataHolder);
            PlayerStatsBuffer playerStatsBuffer = new PlayerStatsBuffer(dataHolder);
            try {
                if (playerStatsBuffer.getCount() > 0) {
                    this.zzaZU = new com.google.android.gms.games.stats.zza((PlayerStats) playerStatsBuffer.get(0));
                } else {
                    this.zzaZU = null;
                }
            } finally {
                playerStatsBuffer.release();
            }
        }

        public final PlayerStats getPlayerStats() {
            return this.zzaZU;
        }
    }

    static final class zzat extends zzw implements Players.LoadPlayersResult {
        private final PlayerBuffer zzaZV;

        zzat(DataHolder dataHolder) {
            super(dataHolder);
            this.zzaZV = new PlayerBuffer(dataHolder);
        }

        public final PlayerBuffer getPlayers() {
            return this.zzaZV;
        }
    }

    static final class zzau extends zzw implements Quests.LoadQuestsResult {
        private final DataHolder zzaCX;

        zzau(DataHolder dataHolder) {
            super(dataHolder);
            this.zzaCX = dataHolder;
        }

        public final QuestBuffer getQuests() {
            return new QuestBuffer(this.zzaCX);
        }
    }

    static final class zzav implements Requests.LoadRequestsResult {
        private final Status mStatus;
        private final Bundle zzaZW;

        zzav(Status status, Bundle bundle) {
            this.mStatus = status;
            this.zzaZW = bundle;
        }

        public final GameRequestBuffer getRequests(int i) {
            String str;
            switch (i) {
                case 1:
                    str = "GIFT";
                    break;
                case 2:
                    str = "WISH";
                    break;
                default:
                    zze.zzz("RequestType", new StringBuilder(33).append("Unknown request type: ").append(i).toString());
                    str = "UNKNOWN_TYPE";
                    break;
            }
            if (!this.zzaZW.containsKey(str)) {
                return null;
            }
            return new GameRequestBuffer((DataHolder) this.zzaZW.get(str));
        }

        public final Status getStatus() {
            return this.mStatus;
        }

        public final void release() {
            for (String parcelable : this.zzaZW.keySet()) {
                DataHolder dataHolder = (DataHolder) this.zzaZW.getParcelable(parcelable);
                if (dataHolder != null) {
                    dataHolder.close();
                }
            }
        }
    }

    static final class zzaw extends zzw implements Leaderboards.LoadScoresResult {
        private final LeaderboardEntity zzaZX;
        private final LeaderboardScoreBuffer zzaZY;

        /* JADX INFO: finally extract failed */
        zzaw(DataHolder dataHolder, DataHolder dataHolder2) {
            super(dataHolder2);
            LeaderboardBuffer leaderboardBuffer = new LeaderboardBuffer(dataHolder);
            try {
                if (leaderboardBuffer.getCount() > 0) {
                    this.zzaZX = (LeaderboardEntity) ((Leaderboard) leaderboardBuffer.get(0)).freeze();
                } else {
                    this.zzaZX = null;
                }
                leaderboardBuffer.release();
                this.zzaZY = new LeaderboardScoreBuffer(dataHolder2);
            } catch (Throwable th) {
                leaderboardBuffer.release();
                throw th;
            }
        }

        public final Leaderboard getLeaderboard() {
            return this.zzaZX;
        }

        public final LeaderboardScoreBuffer getScores() {
            return this.zzaZY;
        }
    }

    static final class zzax extends zzw implements Snapshots.LoadSnapshotsResult {
        zzax(DataHolder dataHolder) {
            super(dataHolder);
        }

        public final SnapshotMetadataBuffer getSnapshots() {
            return new SnapshotMetadataBuffer(this.zzaCX);
        }
    }

    static final class zzay implements zzbdz<OnTurnBasedMatchUpdateReceivedListener> {
        private final String zzaZZ;

        zzay(String str) {
            this.zzaZZ = str;
        }

        public final void zzpT() {
        }

        public final /* synthetic */ void zzq(Object obj) {
            ((OnTurnBasedMatchUpdateReceivedListener) obj).onTurnBasedMatchRemoved(this.zzaZZ);
        }
    }

    static final class zzaz extends zza {
        private final zzbdw<OnTurnBasedMatchUpdateReceivedListener> zzaOo;

        zzaz(zzbdw<OnTurnBasedMatchUpdateReceivedListener> zzbdw) {
            this.zzaOo = zzbdw;
        }

        public final void onTurnBasedMatchRemoved(String str) {
            this.zzaOo.zza(new zzay(str));
        }

        public final void zzt(DataHolder dataHolder) {
            TurnBasedMatchBuffer turnBasedMatchBuffer = new TurnBasedMatchBuffer(dataHolder);
            TurnBasedMatch turnBasedMatch = null;
            try {
                if (turnBasedMatchBuffer.getCount() > 0) {
                    turnBasedMatch = (TurnBasedMatch) ((TurnBasedMatch) turnBasedMatchBuffer.get(0)).freeze();
                }
                if (turnBasedMatch != null) {
                    this.zzaOo.zza(new zzba(turnBasedMatch));
                }
            } finally {
                turnBasedMatchBuffer.release();
            }
        }
    }

    static abstract class zzb extends zzbbx<RoomUpdateListener> {
        zzb(DataHolder dataHolder) {
            super(dataHolder);
        }

        /* access modifiers changed from: protected */
        public abstract void zza(RoomUpdateListener roomUpdateListener, Room room, int i);

        /* access modifiers changed from: protected */
        public final /* synthetic */ void zza(Object obj, DataHolder dataHolder) {
            zza((RoomUpdateListener) obj, GamesClientImpl.zzK(dataHolder), dataHolder.getStatusCode());
        }
    }

    static final class zzba implements zzbdz<OnTurnBasedMatchUpdateReceivedListener> {
        private final TurnBasedMatch zzbaa;

        zzba(TurnBasedMatch turnBasedMatch) {
            this.zzbaa = turnBasedMatch;
        }

        public final void zzpT() {
        }

        public final /* synthetic */ void zzq(Object obj) {
            ((OnTurnBasedMatchUpdateReceivedListener) obj).onTurnBasedMatchReceived(this.zzbaa);
        }
    }

    static final class zzbb implements zzbdz<RealTimeMessageReceivedListener> {
        private final RealTimeMessage zzbab;

        zzbb(RealTimeMessage realTimeMessage) {
            this.zzbab = realTimeMessage;
        }

        public final void zzpT() {
        }

        public final /* synthetic */ void zzq(Object obj) {
            ((RealTimeMessageReceivedListener) obj).onRealTimeMessageReceived(this.zzbab);
        }
    }

    static final class zzbc extends zzw implements Snapshots.OpenSnapshotResult {
        private final Snapshot zzbac;
        private final String zzbad;
        private final Snapshot zzbae;
        private final com.google.android.gms.drive.zzc zzbaf;
        private final SnapshotContents zzbag;

        zzbc(DataHolder dataHolder, com.google.android.gms.drive.zzc zzc) {
            this(dataHolder, (String) null, zzc, (com.google.android.gms.drive.zzc) null, (com.google.android.gms.drive.zzc) null);
        }

        /* JADX INFO: finally extract failed */
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        zzbc(DataHolder dataHolder, String str, com.google.android.gms.drive.zzc zzc, com.google.android.gms.drive.zzc zzc2, com.google.android.gms.drive.zzc zzc3) {
            super(dataHolder);
            boolean z = true;
            SnapshotMetadataBuffer snapshotMetadataBuffer = new SnapshotMetadataBuffer(dataHolder);
            try {
                if (snapshotMetadataBuffer.getCount() == 0) {
                    this.zzbac = null;
                    this.zzbae = null;
                } else if (snapshotMetadataBuffer.getCount() == 1) {
                    com.google.android.gms.common.internal.zzc.zzae(dataHolder.getStatusCode() == 4004 ? false : z);
                    this.zzbac = new SnapshotEntity(new SnapshotMetadataEntity((SnapshotMetadata) snapshotMetadataBuffer.get(0)), new com.google.android.gms.games.snapshot.zza(zzc));
                    this.zzbae = null;
                } else {
                    this.zzbac = new SnapshotEntity(new SnapshotMetadataEntity((SnapshotMetadata) snapshotMetadataBuffer.get(0)), new com.google.android.gms.games.snapshot.zza(zzc));
                    this.zzbae = new SnapshotEntity(new SnapshotMetadataEntity((SnapshotMetadata) snapshotMetadataBuffer.get(1)), new com.google.android.gms.games.snapshot.zza(zzc2));
                }
                snapshotMetadataBuffer.release();
                this.zzbad = str;
                this.zzbaf = zzc3;
                this.zzbag = new com.google.android.gms.games.snapshot.zza(zzc3);
            } catch (Throwable th) {
                snapshotMetadataBuffer.release();
                throw th;
            }
        }

        public final String getConflictId() {
            return this.zzbad;
        }

        public final Snapshot getConflictingSnapshot() {
            return this.zzbae;
        }

        public final SnapshotContents getResolutionSnapshotContents() {
            return this.zzbag;
        }

        public final Snapshot getSnapshot() {
            return this.zzbac;
        }
    }

    static final class zzbd implements zzbdz<RoomStatusUpdateListener> {
        private final String zzbah;

        zzbd(String str) {
            this.zzbah = str;
        }

        public final void zzpT() {
        }

        public final /* synthetic */ void zzq(Object obj) {
            ((RoomStatusUpdateListener) obj).onP2PConnected(this.zzbah);
        }
    }

    static final class zzbe implements zzbdz<RoomStatusUpdateListener> {
        private final String zzbah;

        zzbe(String str) {
            this.zzbah = str;
        }

        public final void zzpT() {
        }

        public final /* synthetic */ void zzq(Object obj) {
            ((RoomStatusUpdateListener) obj).onP2PDisconnected(this.zzbah);
        }
    }

    static final class zzbf extends zza {
        zzbf(DataHolder dataHolder, String[] strArr) {
            super(dataHolder, strArr);
        }

        /* access modifiers changed from: protected */
        public final void zza(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeersConnected(room, arrayList);
        }
    }

    static final class zzbg extends zza {
        zzbg(DataHolder dataHolder, String[] strArr) {
            super(dataHolder, strArr);
        }

        /* access modifiers changed from: protected */
        public final void zza(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerDeclined(room, arrayList);
        }
    }

    static final class zzbh extends zza {
        zzbh(DataHolder dataHolder, String[] strArr) {
            super(dataHolder, strArr);
        }

        /* access modifiers changed from: protected */
        public final void zza(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeersDisconnected(room, arrayList);
        }
    }

    static final class zzbi extends zza {
        zzbi(DataHolder dataHolder, String[] strArr) {
            super(dataHolder, strArr);
        }

        /* access modifiers changed from: protected */
        public final void zza(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerInvitedToRoom(room, arrayList);
        }
    }

    static final class zzbj extends zza {
        zzbj(DataHolder dataHolder, String[] strArr) {
            super(dataHolder, strArr);
        }

        /* access modifiers changed from: protected */
        public final void zza(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerJoined(room, arrayList);
        }
    }

    static final class zzbk extends zza {
        zzbk(DataHolder dataHolder, String[] strArr) {
            super(dataHolder, strArr);
        }

        /* access modifiers changed from: protected */
        public final void zza(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
            roomStatusUpdateListener.onPeerLeft(room, arrayList);
        }
    }

    static final class zzbl extends zza {
        private final zzbaz<Leaderboards.LoadPlayerScoreResult> zzaIz;

        zzbl(zzbaz<Leaderboards.LoadPlayerScoreResult> zzbaz) {
            this.zzaIz = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzB(DataHolder dataHolder) {
            this.zzaIz.setResult(new zzar(dataHolder));
        }
    }

    static final class zzbm extends zza {
        private final zzbaz<Stats.LoadPlayerStatsResult> zzaIz;

        public zzbm(zzbaz<Stats.LoadPlayerStatsResult> zzbaz) {
            this.zzaIz = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzJ(DataHolder dataHolder) {
            this.zzaIz.setResult(new zzas(dataHolder));
        }
    }

    static final class zzbn extends zza {
        private final zzbaz<Players.LoadPlayersResult> zzaIz;

        zzbn(zzbaz<Players.LoadPlayersResult> zzbaz) {
            this.zzaIz = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzj(DataHolder dataHolder) {
            this.zzaIz.setResult(new zzat(dataHolder));
        }

        public final void zzk(DataHolder dataHolder) {
            this.zzaIz.setResult(new zzat(dataHolder));
        }
    }

    static final class zzbo extends zzb {
        private final zzn zzaZt;

        public zzbo(zzn zzn) {
            this.zzaZt = zzn;
        }

        public final zzl zzur() {
            return new zzl(this.zzaZt.zzbaL);
        }
    }

    static final class zzbp extends zza {
        private final zzbaz<Quests.AcceptQuestResult> zzbai;

        public zzbp(zzbaz<Quests.AcceptQuestResult> zzbaz) {
            this.zzbai = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzG(DataHolder dataHolder) {
            this.zzbai.setResult(new zzd(dataHolder));
        }
    }

    static final class zzbq implements zzbdz<QuestUpdateListener> {
        private final Quest zzaZB;

        zzbq(Quest quest) {
            this.zzaZB = quest;
        }

        public final void zzpT() {
        }

        public final /* synthetic */ void zzq(Object obj) {
            ((QuestUpdateListener) obj).onQuestCompleted(this.zzaZB);
        }
    }

    static final class zzbr extends zza {
        private final zzbaz<Quests.ClaimMilestoneResult> zzbaj;
        private final String zzbak;

        public zzbr(zzbaz<Quests.ClaimMilestoneResult> zzbaz, String str) {
            this.zzbaj = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
            this.zzbak = (String) com.google.android.gms.common.internal.zzbo.zzb(str, (Object) "MilestoneId must not be null");
        }

        public final void zzF(DataHolder dataHolder) {
            this.zzbaj.setResult(new zzp(dataHolder, this.zzbak));
        }
    }

    static final class zzbs extends zza {
        private final zzbdw<QuestUpdateListener> zzaOo;

        zzbs(zzbdw<QuestUpdateListener> zzbdw) {
            this.zzaOo = zzbdw;
        }

        private static Quest zzM(DataHolder dataHolder) {
            QuestBuffer questBuffer = new QuestBuffer(dataHolder);
            Quest quest = null;
            try {
                if (questBuffer.getCount() > 0) {
                    quest = (Quest) ((Quest) questBuffer.get(0)).freeze();
                }
                return quest;
            } finally {
                questBuffer.release();
            }
        }

        public final void zzH(DataHolder dataHolder) {
            Quest zzM = zzM(dataHolder);
            if (zzM != null) {
                this.zzaOo.zza(new zzbq(zzM));
            }
        }
    }

    static final class zzbt extends zza {
        private final zzbaz<Quests.LoadQuestsResult> zzbal;

        public zzbt(zzbaz<Quests.LoadQuestsResult> zzbaz) {
            this.zzbal = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzI(DataHolder dataHolder) {
            this.zzbal.setResult(new zzau(dataHolder));
        }
    }

    static final class zzbu implements zzbdz<RealTimeMultiplayer.ReliableMessageSentCallback> {
        private final int zzaxu;
        private final String zzbam;
        private final int zzban;

        zzbu(int i, int i2, String str) {
            this.zzaxu = i;
            this.zzban = i2;
            this.zzbam = str;
        }

        public final void zzpT() {
        }

        public final /* synthetic */ void zzq(Object obj) {
            RealTimeMultiplayer.ReliableMessageSentCallback reliableMessageSentCallback = (RealTimeMultiplayer.ReliableMessageSentCallback) obj;
            if (reliableMessageSentCallback != null) {
                reliableMessageSentCallback.onRealTimeMessageSent(this.zzaxu, this.zzban, this.zzbam);
            }
        }
    }

    static final class zzbv extends zza {
        private zzbdw<RealTimeMultiplayer.ReliableMessageSentCallback> zzbao;

        public zzbv(zzbdw<RealTimeMultiplayer.ReliableMessageSentCallback> zzbdw) {
            this.zzbao = zzbdw;
        }

        public final void zzb(int i, int i2, String str) {
            if (this.zzbao != null) {
                this.zzbao.zza(new zzbu(i, i2, str));
            }
        }
    }

    static final class zzbw extends zza {
        private final zzbdw<OnRequestReceivedListener> zzaOo;

        zzbw(zzbdw<OnRequestReceivedListener> zzbdw) {
            this.zzaOo = zzbdw;
        }

        public final void onRequestRemoved(String str) {
            this.zzaOo.zza(new zzby(str));
        }

        public final void zzo(DataHolder dataHolder) {
            GameRequestBuffer gameRequestBuffer = new GameRequestBuffer(dataHolder);
            GameRequest gameRequest = null;
            try {
                if (gameRequestBuffer.getCount() > 0) {
                    gameRequest = (GameRequest) ((GameRequest) gameRequestBuffer.get(0)).freeze();
                }
                if (gameRequest != null) {
                    this.zzaOo.zza(new zzbx(gameRequest));
                }
            } finally {
                gameRequestBuffer.release();
            }
        }
    }

    static final class zzbx implements zzbdz<OnRequestReceivedListener> {
        private final GameRequest zzbap;

        zzbx(GameRequest gameRequest) {
            this.zzbap = gameRequest;
        }

        public final void zzpT() {
        }

        public final /* synthetic */ void zzq(Object obj) {
            ((OnRequestReceivedListener) obj).onRequestReceived(this.zzbap);
        }
    }

    static final class zzby implements zzbdz<OnRequestReceivedListener> {
        private final String zzQx;

        zzby(String str) {
            this.zzQx = str;
        }

        public final void zzpT() {
        }

        public final /* synthetic */ void zzq(Object obj) {
            ((OnRequestReceivedListener) obj).onRequestRemoved(this.zzQx);
        }
    }

    static final class zzbz extends zza {
        private final zzbaz<Requests.LoadRequestsResult> zzbaq;

        public zzbz(zzbaz<Requests.LoadRequestsResult> zzbaz) {
            this.zzbaq = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzc(int i, Bundle bundle) {
            bundle.setClassLoader(getClass().getClassLoader());
            this.zzbaq.setResult(new zzav(GamesStatusCodes.zzaY(i), bundle));
        }
    }

    static abstract class zzc extends zzbbx<RoomStatusUpdateListener> {
        zzc(DataHolder dataHolder) {
            super(dataHolder);
        }

        /* access modifiers changed from: protected */
        public abstract void zza(RoomStatusUpdateListener roomStatusUpdateListener, Room room);

        /* access modifiers changed from: protected */
        public final /* synthetic */ void zza(Object obj, DataHolder dataHolder) {
            zza((RoomStatusUpdateListener) obj, GamesClientImpl.zzK(dataHolder));
        }
    }

    static final class zzca extends zza {
        private final zzbaz<Requests.UpdateRequestsResult> zzbar;

        public zzca(zzbaz<Requests.UpdateRequestsResult> zzbaz) {
            this.zzbar = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzC(DataHolder dataHolder) {
            this.zzbar.setResult(new zzcw(dataHolder));
        }
    }

    static final class zzcb extends zzc {
        zzcb(DataHolder dataHolder) {
            super(dataHolder);
        }

        public final void zza(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onRoomAutoMatching(room);
        }
    }

    static final class zzcc extends zza {
        private final zzbdw<? extends RoomUpdateListener> zzbas;
        private final zzbdw<? extends RoomStatusUpdateListener> zzbat;
        private final zzbdw<RealTimeMessageReceivedListener> zzbau;

        public zzcc(zzbdw<RoomUpdateListener> zzbdw) {
            this.zzbas = (zzbdw) com.google.android.gms.common.internal.zzbo.zzb(zzbdw, (Object) "Callbacks must not be null");
            this.zzbat = null;
            this.zzbau = null;
        }

        public zzcc(zzbdw<? extends RoomUpdateListener> zzbdw, zzbdw<? extends RoomStatusUpdateListener> zzbdw2, zzbdw<RealTimeMessageReceivedListener> zzbdw3) {
            this.zzbas = (zzbdw) com.google.android.gms.common.internal.zzbo.zzb(zzbdw, (Object) "Callbacks must not be null");
            this.zzbat = zzbdw2;
            this.zzbau = zzbdw3;
        }

        public final void onLeftRoom(int i, String str) {
            this.zzbas.zza(new zzak(i, str));
        }

        public final void onP2PConnected(String str) {
            if (this.zzbat != null) {
                this.zzbat.zza(new zzbd(str));
            }
        }

        public final void onP2PDisconnected(String str) {
            if (this.zzbat != null) {
                this.zzbat.zza(new zzbe(str));
            }
        }

        public final void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) {
            if (this.zzbau != null) {
                this.zzbau.zza(new zzbb(realTimeMessage));
            }
        }

        public final void zzA(DataHolder dataHolder) {
            if (this.zzbat != null) {
                this.zzbat.zza(new zzt(dataHolder));
            }
        }

        public final void zza(DataHolder dataHolder, String[] strArr) {
            if (this.zzbat != null) {
                this.zzbat.zza(new zzbi(dataHolder, strArr));
            }
        }

        public final void zzb(DataHolder dataHolder, String[] strArr) {
            if (this.zzbat != null) {
                this.zzbat.zza(new zzbj(dataHolder, strArr));
            }
        }

        public final void zzc(DataHolder dataHolder, String[] strArr) {
            if (this.zzbat != null) {
                this.zzbat.zza(new zzbk(dataHolder, strArr));
            }
        }

        public final void zzd(DataHolder dataHolder, String[] strArr) {
            if (this.zzbat != null) {
                this.zzbat.zza(new zzbg(dataHolder, strArr));
            }
        }

        public final void zze(DataHolder dataHolder, String[] strArr) {
            if (this.zzbat != null) {
                this.zzbat.zza(new zzbf(dataHolder, strArr));
            }
        }

        public final void zzf(DataHolder dataHolder, String[] strArr) {
            if (this.zzbat != null) {
                this.zzbat.zza(new zzbh(dataHolder, strArr));
            }
        }

        public final void zzu(DataHolder dataHolder) {
            this.zzbas.zza(new zzcf(dataHolder));
        }

        public final void zzv(DataHolder dataHolder) {
            this.zzbas.zza(new zzaf(dataHolder));
        }

        public final void zzw(DataHolder dataHolder) {
            if (this.zzbat != null) {
                this.zzbat.zza(new zzce(dataHolder));
            }
        }

        public final void zzx(DataHolder dataHolder) {
            if (this.zzbat != null) {
                this.zzbat.zza(new zzcb(dataHolder));
            }
        }

        public final void zzy(DataHolder dataHolder) {
            this.zzbas.zza(new zzcd(dataHolder));
        }

        public final void zzz(DataHolder dataHolder) {
            if (this.zzbat != null) {
                this.zzbat.zza(new zzr(dataHolder));
            }
        }
    }

    static final class zzcd extends zzb {
        zzcd(DataHolder dataHolder) {
            super(dataHolder);
        }

        public final void zza(RoomUpdateListener roomUpdateListener, Room room, int i) {
            roomUpdateListener.onRoomConnected(i, room);
        }
    }

    static final class zzce extends zzc {
        zzce(DataHolder dataHolder) {
            super(dataHolder);
        }

        public final void zza(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onRoomConnecting(room);
        }
    }

    static final class zzcf extends zzb {
        public zzcf(DataHolder dataHolder) {
            super(dataHolder);
        }

        public final void zza(RoomUpdateListener roomUpdateListener, Room room, int i) {
            roomUpdateListener.onRoomCreated(i, room);
        }
    }

    static final class zzcg extends zza {
        private final zzbaz<Status> zzaIz;

        public zzcg(zzbaz<Status> zzbaz) {
            this.zzaIz = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzuq() {
            this.zzaIz.setResult(GamesStatusCodes.zzaY(0));
        }
    }

    static final class zzch extends zza {
        private final zzbaz<Snapshots.CommitSnapshotResult> zzbav;

        public zzch(zzbaz<Snapshots.CommitSnapshotResult> zzbaz) {
            this.zzbav = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzE(DataHolder dataHolder) {
            this.zzbav.setResult(new zzq(dataHolder));
        }
    }

    static final class zzci extends zza {
        private final zzbaz<Snapshots.DeleteSnapshotResult> zzaIz;

        public zzci(zzbaz<Snapshots.DeleteSnapshotResult> zzbaz) {
            this.zzaIz = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzj(int i, String str) {
            this.zzaIz.setResult(new zzs(i, str));
        }
    }

    static final class zzcj extends zza {
        private final zzbaz<Snapshots.OpenSnapshotResult> zzbaw;

        public zzcj(zzbaz<Snapshots.OpenSnapshotResult> zzbaz) {
            this.zzbaw = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zza(DataHolder dataHolder, com.google.android.gms.drive.zzc zzc) {
            this.zzbaw.setResult(new zzbc(dataHolder, zzc));
        }

        public final void zza(DataHolder dataHolder, String str, com.google.android.gms.drive.zzc zzc, com.google.android.gms.drive.zzc zzc2, com.google.android.gms.drive.zzc zzc3) {
            this.zzbaw.setResult(new zzbc(dataHolder, str, zzc, zzc2, zzc3));
        }
    }

    static final class zzck extends zza {
        private final zzbaz<Snapshots.LoadSnapshotsResult> zzbax;

        public zzck(zzbaz<Snapshots.LoadSnapshotsResult> zzbaz) {
            this.zzbax = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzD(DataHolder dataHolder) {
            this.zzbax.setResult(new zzax(dataHolder));
        }
    }

    static final class zzcl extends zza {
        private final zzbaz<Leaderboards.SubmitScoreResult> zzaIz;

        public zzcl(zzbaz<Leaderboards.SubmitScoreResult> zzbaz) {
            this.zzaIz = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzi(DataHolder dataHolder) {
            this.zzaIz.setResult(new zzcm(dataHolder));
        }
    }

    static final class zzcm extends zzw implements Leaderboards.SubmitScoreResult {
        private final ScoreSubmissionData zzbay;

        public zzcm(DataHolder dataHolder) {
            super(dataHolder);
            try {
                this.zzbay = new ScoreSubmissionData(dataHolder);
            } finally {
                dataHolder.close();
            }
        }

        public final ScoreSubmissionData getScoreData() {
            return this.zzbay;
        }
    }

    static final class zzcn extends zza {
        private final zzbaz<TurnBasedMultiplayer.CancelMatchResult> zzbaz;

        public zzcn(zzbaz<TurnBasedMultiplayer.CancelMatchResult> zzbaz2) {
            this.zzbaz = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz2, (Object) "Holder must not be null");
        }

        public final void zzi(int i, String str) {
            this.zzbaz.setResult(new zzg(GamesStatusCodes.zzaY(i), str));
        }
    }

    static final class zzco extends zza {
        private final zzbaz<TurnBasedMultiplayer.InitiateMatchResult> zzbaA;

        public zzco(zzbaz<TurnBasedMultiplayer.InitiateMatchResult> zzbaz) {
            this.zzbaA = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzq(DataHolder dataHolder) {
            this.zzbaA.setResult(new zzaa(dataHolder));
        }
    }

    static final class zzcp extends zza {
        private final zzbaz<TurnBasedMultiplayer.LeaveMatchResult> zzbaB;

        public zzcp(zzbaz<TurnBasedMultiplayer.LeaveMatchResult> zzbaz) {
            this.zzbaB = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzs(DataHolder dataHolder) {
            this.zzbaB.setResult(new zzaj(dataHolder));
        }
    }

    static final class zzcq extends zza {
        private final zzbaz<TurnBasedMultiplayer.LoadMatchResult> zzbaC;

        public zzcq(zzbaz<TurnBasedMultiplayer.LoadMatchResult> zzbaz) {
            this.zzbaC = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzp(DataHolder dataHolder) {
            this.zzbaC.setResult(new zzap(dataHolder));
        }
    }

    static abstract class zzcr extends zzw {
        private TurnBasedMatch zzbaa;

        zzcr(DataHolder dataHolder) {
            super(dataHolder);
            TurnBasedMatchBuffer turnBasedMatchBuffer = new TurnBasedMatchBuffer(dataHolder);
            try {
                if (turnBasedMatchBuffer.getCount() > 0) {
                    this.zzbaa = (TurnBasedMatch) ((TurnBasedMatch) turnBasedMatchBuffer.get(0)).freeze();
                } else {
                    this.zzbaa = null;
                }
            } finally {
                turnBasedMatchBuffer.release();
            }
        }

        public TurnBasedMatch getMatch() {
            return this.zzbaa;
        }
    }

    static final class zzcs extends zza {
        private final zzbaz<TurnBasedMultiplayer.UpdateMatchResult> zzbaD;

        public zzcs(zzbaz<TurnBasedMultiplayer.UpdateMatchResult> zzbaz) {
            this.zzbaD = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzr(DataHolder dataHolder) {
            this.zzbaD.setResult(new zzcv(dataHolder));
        }
    }

    static final class zzct extends zza {
        private final zzbaz<TurnBasedMultiplayer.LoadMatchesResult> zzbaE;

        public zzct(zzbaz<TurnBasedMultiplayer.LoadMatchesResult> zzbaz) {
            this.zzbaE = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzb(int i, Bundle bundle) {
            bundle.setClassLoader(getClass().getClassLoader());
            this.zzbaE.setResult(new zzaq(GamesStatusCodes.zzaY(i), bundle));
        }
    }

    static final class zzcu implements Achievements.UpdateAchievementResult {
        private final Status mStatus;
        private final String zzaZb;

        zzcu(int i, String str) {
            this.mStatus = GamesStatusCodes.zzaY(i);
            this.zzaZb = str;
        }

        public final String getAchievementId() {
            return this.zzaZb;
        }

        public final Status getStatus() {
            return this.mStatus;
        }
    }

    static final class zzcv extends zzcr implements TurnBasedMultiplayer.UpdateMatchResult {
        zzcv(DataHolder dataHolder) {
            super(dataHolder);
        }
    }

    static final class zzcw extends zzw implements Requests.UpdateRequestsResult {
        private final zzcam zzbaF;

        zzcw(DataHolder dataHolder) {
            super(dataHolder);
            this.zzbaF = zzcam.zzN(dataHolder);
        }

        public final Set<String> getRequestIds() {
            return this.zzbaF.getRequestIds();
        }

        public final int getRequestOutcome(String str) {
            return this.zzbaF.getRequestOutcome(str);
        }
    }

    static final class zzd extends zzw implements Quests.AcceptQuestResult {
        private final Quest zzaZB;

        zzd(DataHolder dataHolder) {
            super(dataHolder);
            QuestBuffer questBuffer = new QuestBuffer(dataHolder);
            try {
                if (questBuffer.getCount() > 0) {
                    this.zzaZB = new QuestEntity((Quest) questBuffer.get(0));
                } else {
                    this.zzaZB = null;
                }
            } finally {
                questBuffer.release();
            }
        }

        public final Quest getQuest() {
            return this.zzaZB;
        }
    }

    static final class zze extends zza {
        private final zzbaz<Achievements.UpdateAchievementResult> zzaIz;

        zze(zzbaz<Achievements.UpdateAchievementResult> zzbaz) {
            this.zzaIz = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzh(int i, String str) {
            this.zzaIz.setResult(new zzcu(i, str));
        }
    }

    static final class zzf extends zza {
        private final zzbaz<Achievements.LoadAchievementsResult> zzaIz;

        zzf(zzbaz<Achievements.LoadAchievementsResult> zzbaz) {
            this.zzaIz = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzf(DataHolder dataHolder) {
            this.zzaIz.setResult(new zzal(dataHolder));
        }
    }

    static final class zzg implements TurnBasedMultiplayer.CancelMatchResult {
        private final Status mStatus;
        private final String zzaZC;

        zzg(Status status, String str) {
            this.mStatus = status;
            this.zzaZC = str;
        }

        public final String getMatchId() {
            return this.zzaZC;
        }

        public final Status getStatus() {
            return this.mStatus;
        }
    }

    static final class zzh extends zza {
        private final zzbaz<Videos.CaptureAvailableResult> zzaIz;

        zzh(zzbaz<Videos.CaptureAvailableResult> zzbaz) {
            this.zzaIz = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzh(int i, boolean z) {
            this.zzaIz.setResult(new zzi(new Status(i), z));
        }
    }

    static final class zzi implements Videos.CaptureAvailableResult {
        private final Status mStatus;
        private final boolean zzaZD;

        zzi(Status status, boolean z) {
            this.mStatus = status;
            this.zzaZD = z;
        }

        public final Status getStatus() {
            return this.mStatus;
        }

        public final boolean isAvailable() {
            return this.zzaZD;
        }
    }

    static final class zzj extends zza {
        private final zzbaz<Videos.CaptureCapabilitiesResult> zzaIz;

        zzj(zzbaz<Videos.CaptureCapabilitiesResult> zzbaz) {
            this.zzaIz = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zza(int i, VideoCapabilities videoCapabilities) {
            this.zzaIz.setResult(new zzk(new Status(i), videoCapabilities));
        }
    }

    static final class zzk implements Videos.CaptureCapabilitiesResult {
        private final Status mStatus;
        private final VideoCapabilities zzaZE;

        zzk(Status status, VideoCapabilities videoCapabilities) {
            this.mStatus = status;
            this.zzaZE = videoCapabilities;
        }

        public final VideoCapabilities getCapabilities() {
            return this.zzaZE;
        }

        public final Status getStatus() {
            return this.mStatus;
        }
    }

    static final class zzl extends zza {
        private final zzbdw<Videos.CaptureOverlayStateListener> zzaOo;

        zzl(zzbdw<Videos.CaptureOverlayStateListener> zzbdw) {
            this.zzaOo = (zzbdw) com.google.android.gms.common.internal.zzbo.zzb(zzbdw, (Object) "Callback must not be null");
        }

        public final void onCaptureOverlayStateChanged(int i) {
            this.zzaOo.zza(new zzm(i));
        }
    }

    static final class zzm implements zzbdz<Videos.CaptureOverlayStateListener> {
        private final int zzaZF;

        zzm(int i) {
            this.zzaZF = i;
        }

        public final void zzpT() {
        }

        public final /* synthetic */ void zzq(Object obj) {
            ((Videos.CaptureOverlayStateListener) obj).onCaptureOverlayStateChanged(this.zzaZF);
        }
    }

    static final class zzn extends zza {
        private final zzbaz<Videos.CaptureStateResult> zzaIz;

        public zzn(zzbaz<Videos.CaptureStateResult> zzbaz) {
            this.zzaIz = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzd(int i, Bundle bundle) {
            this.zzaIz.setResult(new zzo(new Status(i), CaptureState.zzs(bundle)));
        }
    }

    static final class zzo implements Videos.CaptureStateResult {
        private final Status mStatus;
        private final CaptureState zzaZG;

        zzo(Status status, CaptureState captureState) {
            this.mStatus = status;
            this.zzaZG = captureState;
        }

        public final CaptureState getCaptureState() {
            return this.zzaZG;
        }

        public final Status getStatus() {
            return this.mStatus;
        }
    }

    static final class zzp extends zzw implements Quests.ClaimMilestoneResult {
        private final Quest zzaZB;
        private final Milestone zzaZH;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        zzp(DataHolder dataHolder, String str) {
            super(dataHolder);
            QuestBuffer questBuffer = new QuestBuffer(dataHolder);
            try {
                if (questBuffer.getCount() > 0) {
                    this.zzaZB = new QuestEntity((Quest) questBuffer.get(0));
                    List<Milestone> zzvt = this.zzaZB.zzvt();
                    int size = zzvt.size();
                    for (int i = 0; i < size; i++) {
                        if (zzvt.get(i).getMilestoneId().equals(str)) {
                            this.zzaZH = zzvt.get(i);
                            return;
                        }
                    }
                    this.zzaZH = null;
                } else {
                    this.zzaZH = null;
                    this.zzaZB = null;
                }
                questBuffer.release();
            } finally {
                questBuffer.release();
            }
        }

        public final Milestone getMilestone() {
            return this.zzaZH;
        }

        public final Quest getQuest() {
            return this.zzaZB;
        }
    }

    static final class zzq extends zzw implements Snapshots.CommitSnapshotResult {
        private final SnapshotMetadata zzaZI;

        zzq(DataHolder dataHolder) {
            super(dataHolder);
            SnapshotMetadataBuffer snapshotMetadataBuffer = new SnapshotMetadataBuffer(dataHolder);
            try {
                if (snapshotMetadataBuffer.getCount() > 0) {
                    this.zzaZI = new SnapshotMetadataEntity((SnapshotMetadata) snapshotMetadataBuffer.get(0));
                } else {
                    this.zzaZI = null;
                }
            } finally {
                snapshotMetadataBuffer.release();
            }
        }

        public final SnapshotMetadata getSnapshotMetadata() {
            return this.zzaZI;
        }
    }

    static final class zzr extends zzc {
        zzr(DataHolder dataHolder) {
            super(dataHolder);
        }

        public final void zza(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onConnectedToRoom(room);
        }
    }

    static final class zzs implements Snapshots.DeleteSnapshotResult {
        private final Status mStatus;
        private final String zzaZJ;

        zzs(int i, String str) {
            this.mStatus = GamesStatusCodes.zzaY(i);
            this.zzaZJ = str;
        }

        public final String getSnapshotId() {
            return this.zzaZJ;
        }

        public final Status getStatus() {
            return this.mStatus;
        }
    }

    static final class zzt extends zzc {
        zzt(DataHolder dataHolder) {
            super(dataHolder);
        }

        public final void zza(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
            roomStatusUpdateListener.onDisconnectedFromRoom(room);
        }
    }

    static final class zzu extends zza {
        private final zzbaz<Events.LoadEventsResult> zzaIz;

        zzu(zzbaz<Events.LoadEventsResult> zzbaz) {
            this.zzaIz = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzg(DataHolder dataHolder) {
            this.zzaIz.setResult(new zzam(dataHolder));
        }
    }

    class zzv extends zzcaj {
        public zzv() {
            super(GamesClientImpl.this.getContext().getMainLooper(), 1000);
        }

        /* access modifiers changed from: protected */
        public final void zzq(String str, int i) {
            try {
                if (GamesClientImpl.this.isConnected()) {
                    ((zzj) GamesClientImpl.this.zzrf()).zzn(str, i);
                } else {
                    zze.zzz("GamesClientImpl", new StringBuilder(String.valueOf(str).length() + 89).append("Unable to increment event ").append(str).append(" by ").append(i).append(" because the games client is no longer connected").toString());
                }
            } catch (RemoteException e) {
                GamesClientImpl.zzd(e);
            }
        }
    }

    static abstract class zzw extends zzbby {
        protected zzw(DataHolder dataHolder) {
            super(dataHolder, GamesStatusCodes.zzaY(dataHolder.getStatusCode()));
        }
    }

    static final class zzx extends zza {
        private final zzbaz<GamesMetadata.LoadGamesResult> zzaIz;

        zzx(zzbaz<GamesMetadata.LoadGamesResult> zzbaz) {
            this.zzaIz = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzl(DataHolder dataHolder) {
            this.zzaIz.setResult(new zzan(dataHolder));
        }
    }

    static final class zzy extends zza {
        private final zzbaz<Games.GetServerAuthCodeResult> zzaIz;

        public zzy(zzbaz<Games.GetServerAuthCodeResult> zzbaz) {
            this.zzaIz = (zzbaz) com.google.android.gms.common.internal.zzbo.zzb(zzbaz, (Object) "Holder must not be null");
        }

        public final void zzg(int i, String str) {
            this.zzaIz.setResult(new zzz(GamesStatusCodes.zzaY(i), str));
        }
    }

    static final class zzz implements Games.GetServerAuthCodeResult {
        private final Status mStatus;
        private final String zzaZK;

        zzz(Status status, String str) {
            this.mStatus = status;
            this.zzaZK = str;
        }

        public final String getCode() {
            return this.zzaZK;
        }

        public final Status getStatus() {
            return this.mStatus;
        }
    }

    public GamesClientImpl(Context context, Looper looper, com.google.android.gms.common.internal.zzq zzq2, Games.GamesOptions gamesOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 1, zzq2, connectionCallbacks, onConnectionFailedListener);
        this.zzaZq = zzq2.zzrq();
        this.zzaZv = new Binder();
        this.zzaZt = new zzq(this, zzq2.zzrm());
        this.zzaZw = (long) hashCode();
        this.zzaZx = gamesOptions;
        if (!this.zzaZx.zzaYC) {
            zzs(zzq2.zzrs());
        }
    }

    /* access modifiers changed from: private */
    public static Room zzK(DataHolder dataHolder) {
        com.google.android.gms.games.multiplayer.realtime.zzb zzb2 = new com.google.android.gms.games.multiplayer.realtime.zzb(dataHolder);
        Room room = null;
        try {
            if (zzb2.getCount() > 0) {
                room = (Room) ((Room) zzb2.get(0)).freeze();
            }
            return room;
        } finally {
            zzb2.release();
        }
    }

    /* access modifiers changed from: private */
    public static void zzd(RemoteException remoteException) {
        zze.zzc("GamesClientImpl", "service died", remoteException);
    }

    public final void disconnect() {
        this.zzaZu = false;
        if (isConnected()) {
            try {
                zzj zzj2 = (zzj) zzrf();
                zzj2.zzuP();
                this.zzaZp.flush();
                zzj2.zzC(this.zzaZw);
            } catch (RemoteException e) {
                zze.zzy("GamesClientImpl", "Failed to notify client disconnect.");
            }
        }
        super.disconnect();
    }

    public final void onConnectionFailed(ConnectionResult connectionResult) {
        super.onConnectionFailed(connectionResult);
        this.zzaZu = false;
    }

    public final int zza(zzbdw<RealTimeMultiplayer.ReliableMessageSentCallback> zzbdw, byte[] bArr, String str, String str2) {
        try {
            return ((zzj) zzrf()).zza((zzf) new zzbv(zzbdw), bArr, str, str2);
        } catch (RemoteException e) {
            zzd(e);
            return -1;
        }
    }

    public final int zza(byte[] bArr, String str, String[] strArr) {
        com.google.android.gms.common.internal.zzbo.zzb(strArr, (Object) "Participant IDs must not be null");
        try {
            return ((zzj) zzrf()).zzb(bArr, str, strArr);
        } catch (RemoteException e) {
            zzd(e);
            return -1;
        }
    }

    public final Intent zza(int i, byte[] bArr, int i2, Bitmap bitmap, String str) {
        try {
            Intent zza2 = ((zzj) zzrf()).zza(i, bArr, i2, str);
            com.google.android.gms.common.internal.zzbo.zzb(bitmap, (Object) "Must provide a non null icon");
            zza2.putExtra("com.google.android.gms.games.REQUEST_ITEM_ICON", bitmap);
            return zza2;
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    public final Intent zza(PlayerEntity playerEntity) {
        try {
            return ((zzj) zzrf()).zza(playerEntity);
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    public final Intent zza(Room room, int i) {
        try {
            return ((zzj) zzrf()).zza((RoomEntity) room.freeze(), i);
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    public final Intent zza(String str, boolean z, boolean z2, int i) {
        try {
            return ((zzj) zzrf()).zza(str, z, z2, i);
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public final void zza(int i, IBinder iBinder, Bundle bundle, int i2) {
        if (i == 0 && bundle != null) {
            bundle.setClassLoader(GamesClientImpl.class.getClassLoader());
            this.zzaZu = bundle.getBoolean("show_welcome_popup");
            this.zzaZy = this.zzaZu;
            this.zzaZr = (PlayerEntity) bundle.getParcelable("com.google.android.gms.games.current_player");
            this.zzaZs = (GameEntity) bundle.getParcelable("com.google.android.gms.games.current_game");
        }
        super.zza(i, iBinder, bundle, i2);
    }

    public final void zza(IBinder iBinder, Bundle bundle) {
        if (isConnected()) {
            try {
                ((zzj) zzrf()).zza(iBinder, bundle);
            } catch (RemoteException e) {
                zzd(e);
            }
        }
    }

    public final /* synthetic */ void zza(@NonNull IInterface iInterface) {
        zzj zzj2 = (zzj) iInterface;
        super.zza(zzj2);
        if (this.zzaZu) {
            this.zzaZt.zzuV();
            this.zzaZu = false;
        }
        if (!this.zzaZx.zzaYu && !this.zzaZx.zzaYC) {
            try {
                zzj2.zza((zzh) new zzbo(this.zzaZt), this.zzaZw);
            } catch (RemoteException e) {
                zzd(e);
            }
        }
    }

    public final void zza(com.google.android.gms.common.internal.zzj zzj2) {
        this.zzaZr = null;
        this.zzaZs = null;
        super.zza(zzj2);
    }

    public final void zza(Snapshot snapshot) {
        SnapshotContents snapshotContents = snapshot.getSnapshotContents();
        com.google.android.gms.common.internal.zzbo.zza(!snapshotContents.isClosed(), (Object) "Snapshot already closed");
        com.google.android.gms.drive.zzc zzsM = snapshotContents.zzsM();
        snapshotContents.close();
        try {
            ((zzj) zzrf()).zza(zzsM);
        } catch (RemoteException e) {
            zzd(e);
        }
    }

    public final void zza(zzbaz<Invitations.LoadInvitationsResult> zzbaz, int i) throws RemoteException {
        ((zzj) zzrf()).zza((zzf) new zzae(zzbaz), i);
    }

    public final void zza(zzbaz<Requests.LoadRequestsResult> zzbaz, int i, int i2, int i3) throws RemoteException {
        ((zzj) zzrf()).zza((zzf) new zzbz(zzbaz), i, i2, i3);
    }

    public final void zza(zzbaz<Players.LoadPlayersResult> zzbaz, int i, boolean z, boolean z2) throws RemoteException {
        ((zzj) zzrf()).zza((zzf) new zzbn(zzbaz), i, z, z2);
    }

    public final void zza(zzbaz<TurnBasedMultiplayer.LoadMatchesResult> zzbaz, int i, int[] iArr) throws RemoteException {
        ((zzj) zzrf()).zza((zzf) new zzct(zzbaz), i, iArr);
    }

    public final void zza(zzbaz<Leaderboards.LoadScoresResult> zzbaz, LeaderboardScoreBuffer leaderboardScoreBuffer, int i, int i2) throws RemoteException {
        ((zzj) zzrf()).zza((zzf) new zzah(zzbaz), leaderboardScoreBuffer.zzvn().asBundle(), i, i2);
    }

    public final void zza(zzbaz<TurnBasedMultiplayer.InitiateMatchResult> zzbaz, TurnBasedMatchConfig turnBasedMatchConfig) throws RemoteException {
        ((zzj) zzrf()).zza((zzf) new zzco(zzbaz), turnBasedMatchConfig.getVariant(), turnBasedMatchConfig.zzvs(), turnBasedMatchConfig.getInvitedPlayerIds(), turnBasedMatchConfig.getAutoMatchCriteria());
    }

    public final void zza(zzbaz<Snapshots.CommitSnapshotResult> zzbaz, Snapshot snapshot, SnapshotMetadataChange snapshotMetadataChange) throws RemoteException {
        SnapshotContents snapshotContents = snapshot.getSnapshotContents();
        com.google.android.gms.common.internal.zzbo.zza(!snapshotContents.isClosed(), (Object) "Snapshot already closed");
        BitmapTeleporter zzvv = snapshotMetadataChange.zzvv();
        if (zzvv != null) {
            zzvv.zzc(getContext().getCacheDir());
        }
        com.google.android.gms.drive.zzc zzsM = snapshotContents.zzsM();
        snapshotContents.close();
        ((zzj) zzrf()).zza((zzf) new zzch(zzbaz), snapshot.getMetadata().getSnapshotId(), (com.google.android.gms.games.snapshot.zze) snapshotMetadataChange, zzsM);
    }

    public final void zza(zzbaz<Achievements.UpdateAchievementResult> zzbaz, String str) throws RemoteException {
        ((zzj) zzrf()).zza((zzf) zzbaz == null ? null : new zze(zzbaz), str, this.zzaZt.zzbaL.zzbaM, this.zzaZt.zzbaL.zzuW());
    }

    public final void zza(zzbaz<Achievements.UpdateAchievementResult> zzbaz, String str, int i) throws RemoteException {
        ((zzj) zzrf()).zza((zzf) zzbaz == null ? null : new zze(zzbaz), str, i, this.zzaZt.zzbaL.zzbaM, this.zzaZt.zzbaL.zzuW());
    }

    public final void zza(zzbaz<Leaderboards.LoadScoresResult> zzbaz, String str, int i, int i2, int i3, boolean z) throws RemoteException {
        ((zzj) zzrf()).zza(new zzah(zzbaz), str, i, i2, i3, z);
    }

    public final void zza(zzbaz<Players.LoadPlayersResult> zzbaz, String str, int i, boolean z, boolean z2) throws RemoteException {
        char c = 65535;
        switch (str.hashCode()) {
            case 156408498:
                if (str.equals("played_with")) {
                    c = 0;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                ((zzj) zzrf()).zza((zzf) new zzbn(zzbaz), str, i, z, z2);
                return;
            default:
                String valueOf = String.valueOf(str);
                throw new IllegalArgumentException(valueOf.length() != 0 ? "Invalid player collection: ".concat(valueOf) : new String("Invalid player collection: "));
        }
    }

    public final void zza(zzbaz<Leaderboards.SubmitScoreResult> zzbaz, String str, long j, String str2) throws RemoteException {
        ((zzj) zzrf()).zza((zzf) zzbaz == null ? null : new zzcl(zzbaz), str, j, str2);
    }

    public final void zza(zzbaz<TurnBasedMultiplayer.LeaveMatchResult> zzbaz, String str, String str2) throws RemoteException {
        ((zzj) zzrf()).zza((zzf) new zzcp(zzbaz), str, str2);
    }

    public final void zza(zzbaz<Leaderboards.LoadPlayerScoreResult> zzbaz, String str, String str2, int i, int i2) throws RemoteException {
        ((zzj) zzrf()).zza((zzf) new zzbl(zzbaz), (String) null, str2, i, i2);
    }

    public final void zza(zzbaz<Snapshots.OpenSnapshotResult> zzbaz, String str, String str2, SnapshotMetadataChange snapshotMetadataChange, SnapshotContents snapshotContents) throws RemoteException {
        com.google.android.gms.common.internal.zzbo.zza(!snapshotContents.isClosed(), (Object) "SnapshotContents already closed");
        BitmapTeleporter zzvv = snapshotMetadataChange.zzvv();
        if (zzvv != null) {
            zzvv.zzc(getContext().getCacheDir());
        }
        com.google.android.gms.drive.zzc zzsM = snapshotContents.zzsM();
        snapshotContents.close();
        ((zzj) zzrf()).zza((zzf) new zzcj(zzbaz), str, str2, (com.google.android.gms.games.snapshot.zze) snapshotMetadataChange, zzsM);
    }

    public final void zza(zzbaz<Players.LoadPlayersResult> zzbaz, String str, boolean z) throws RemoteException {
        ((zzj) zzrf()).zzb((zzf) new zzbn(zzbaz), str, z);
    }

    public final void zza(zzbaz<Snapshots.OpenSnapshotResult> zzbaz, String str, boolean z, int i) throws RemoteException {
        ((zzj) zzrf()).zza((zzf) new zzcj(zzbaz), str, z, i);
    }

    public final void zza(zzbaz<TurnBasedMultiplayer.UpdateMatchResult> zzbaz, String str, byte[] bArr, String str2, ParticipantResult[] participantResultArr) throws RemoteException {
        ((zzj) zzrf()).zza((zzf) new zzcs(zzbaz), str, bArr, str2, participantResultArr);
    }

    public final void zza(zzbaz<TurnBasedMultiplayer.UpdateMatchResult> zzbaz, String str, byte[] bArr, ParticipantResult[] participantResultArr) throws RemoteException {
        ((zzj) zzrf()).zza((zzf) new zzcs(zzbaz), str, bArr, participantResultArr);
    }

    public final void zza(zzbaz<Players.LoadPlayersResult> zzbaz, boolean z) throws RemoteException {
        ((zzj) zzrf()).zzc((zzf) new zzbn(zzbaz), z);
    }

    public final void zza(zzbaz<Events.LoadEventsResult> zzbaz, boolean z, String... strArr) throws RemoteException {
        this.zzaZp.flush();
        ((zzj) zzrf()).zza((zzf) new zzu(zzbaz), z, strArr);
    }

    public final void zza(zzbaz<Quests.LoadQuestsResult> zzbaz, int[] iArr, int i, boolean z) throws RemoteException {
        this.zzaZp.flush();
        ((zzj) zzrf()).zza((zzf) new zzbt(zzbaz), iArr, i, z);
    }

    public final void zza(zzbaz<Requests.UpdateRequestsResult> zzbaz, String[] strArr) throws RemoteException {
        ((zzj) zzrf()).zza((zzf) new zzca(zzbaz), strArr);
    }

    public final void zza(zzbdw<OnInvitationReceivedListener> zzbdw) {
        try {
            ((zzj) zzrf()).zza((zzf) new zzab(zzbdw), this.zzaZw);
        } catch (RemoteException e) {
            zzd(e);
        }
    }

    public final void zza(zzbdw<RoomUpdateListener> zzbdw, zzbdw<RoomStatusUpdateListener> zzbdw2, zzbdw<RealTimeMessageReceivedListener> zzbdw3, RoomConfig roomConfig) {
        try {
            ((zzj) zzrf()).zza(new zzcc(zzbdw, zzbdw2, zzbdw3), this.zzaZv, roomConfig.getVariant(), roomConfig.getInvitedPlayerIds(), roomConfig.getAutoMatchCriteria(), false, this.zzaZw);
        } catch (RemoteException e) {
            zzd(e);
        }
    }

    public final void zza(zzbdw<RoomUpdateListener> zzbdw, String str) {
        try {
            ((zzj) zzrf()).zza((zzf) new zzcc(zzbdw), str);
        } catch (RemoteException e) {
            zzd(e);
        }
    }

    public final void zzaZ(int i) {
        this.zzaZt.zzbaL.gravity = i;
    }

    public final String zzah(boolean z) {
        if (this.zzaZr != null) {
            return this.zzaZr.getPlayerId();
        }
        try {
            return ((zzj) zzrf()).zzuR();
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    public final Intent zzb(int i, int i2, boolean z) {
        try {
            return ((zzj) zzrf()).zzb(i, i2, z);
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    public final Intent zzb(int[] iArr) {
        try {
            return ((zzj) zzrf()).zzb(iArr);
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public final Set<Scope> zzb(Set<Scope> set) {
        Scope scope = new Scope(Scopes.GAMES);
        Scope scope2 = new Scope("https://www.googleapis.com/auth/games.firstparty");
        boolean z = false;
        boolean z2 = false;
        for (Scope next : set) {
            if (next.equals(scope)) {
                z2 = true;
            } else {
                z = next.equals(scope2) ? true : z;
            }
        }
        if (z) {
            com.google.android.gms.common.internal.zzbo.zza(!z2, "Cannot have both %s and %s!", Scopes.GAMES, "https://www.googleapis.com/auth/games.firstparty");
        } else {
            com.google.android.gms.common.internal.zzbo.zza(z2, "Games APIs requires %s to function.", Scopes.GAMES);
        }
        return set;
    }

    public final void zzb(zzbaz<Videos.CaptureAvailableResult> zzbaz, int i) throws RemoteException {
        ((zzj) zzrf()).zzb((zzf) new zzh(zzbaz), i);
    }

    public final void zzb(zzbaz<Achievements.UpdateAchievementResult> zzbaz, String str) throws RemoteException {
        ((zzj) zzrf()).zzb(zzbaz == null ? null : new zze(zzbaz), str, this.zzaZt.zzbaL.zzbaM, this.zzaZt.zzbaL.zzuW());
    }

    public final void zzb(zzbaz<Achievements.UpdateAchievementResult> zzbaz, String str, int i) throws RemoteException {
        ((zzj) zzrf()).zzb(zzbaz == null ? null : new zze(zzbaz), str, i, this.zzaZt.zzbaL.zzbaM, this.zzaZt.zzbaL.zzuW());
    }

    public final void zzb(zzbaz<Leaderboards.LoadScoresResult> zzbaz, String str, int i, int i2, int i3, boolean z) throws RemoteException {
        ((zzj) zzrf()).zzb(new zzah(zzbaz), str, i, i2, i3, z);
    }

    public final void zzb(zzbaz<Quests.ClaimMilestoneResult> zzbaz, String str, String str2) throws RemoteException {
        this.zzaZp.flush();
        ((zzj) zzrf()).zzb((zzf) new zzbr(zzbaz, str2), str, str2);
    }

    public final void zzb(zzbaz<Leaderboards.LeaderboardMetadataResult> zzbaz, String str, boolean z) throws RemoteException {
        ((zzj) zzrf()).zza((zzf) new zzai(zzbaz), str, z);
    }

    public final void zzb(zzbaz<Leaderboards.LeaderboardMetadataResult> zzbaz, boolean z) throws RemoteException {
        ((zzj) zzrf()).zzb((zzf) new zzai(zzbaz), z);
    }

    public final void zzb(zzbaz<Quests.LoadQuestsResult> zzbaz, boolean z, String[] strArr) throws RemoteException {
        this.zzaZp.flush();
        ((zzj) zzrf()).zza((zzf) new zzbt(zzbaz), strArr, z);
    }

    public final void zzb(zzbaz<Requests.UpdateRequestsResult> zzbaz, String[] strArr) throws RemoteException {
        ((zzj) zzrf()).zzb((zzf) new zzca(zzbaz), strArr);
    }

    public final void zzb(zzbdw<OnTurnBasedMatchUpdateReceivedListener> zzbdw) {
        try {
            ((zzj) zzrf()).zzb((zzf) new zzaz(zzbdw), this.zzaZw);
        } catch (RemoteException e) {
            zzd(e);
        }
    }

    public final void zzb(zzbdw<RoomUpdateListener> zzbdw, zzbdw<RoomStatusUpdateListener> zzbdw2, zzbdw<RealTimeMessageReceivedListener> zzbdw3, RoomConfig roomConfig) {
        try {
            ((zzj) zzrf()).zza((zzf) new zzcc(zzbdw, zzbdw2, zzbdw3), (IBinder) this.zzaZv, roomConfig.getInvitationId(), false, this.zzaZw);
        } catch (RemoteException e) {
            zzd(e);
        }
    }

    public final void zzb(String str, zzbaz<Games.GetServerAuthCodeResult> zzbaz) throws RemoteException {
        com.google.android.gms.common.internal.zzbo.zzh(str, "Please provide a valid serverClientId");
        ((zzj) zzrf()).zza(str, (zzf) new zzy(zzbaz));
    }

    public final void zzba(int i) {
        try {
            ((zzj) zzrf()).zzba(i);
        } catch (RemoteException e) {
            zzd(e);
        }
    }

    public final int zzc(byte[] bArr, String str) {
        try {
            return ((zzj) zzrf()).zzb(bArr, str, (String[]) null);
        } catch (RemoteException e) {
            zzd(e);
            return -1;
        }
    }

    public final Intent zzc(int i, int i2, boolean z) {
        try {
            return ((zzj) zzrf()).zzc(i, i2, z);
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    public final void zzc(zzbaz<TurnBasedMultiplayer.InitiateMatchResult> zzbaz, String str) throws RemoteException {
        ((zzj) zzrf()).zzb((zzf) new zzco(zzbaz), str);
    }

    public final void zzc(zzbaz<Achievements.LoadAchievementsResult> zzbaz, boolean z) throws RemoteException {
        ((zzj) zzrf()).zza((zzf) new zzf(zzbaz), z);
    }

    public final void zzc(zzbdw<QuestUpdateListener> zzbdw) {
        try {
            ((zzj) zzrf()).zzd((zzf) new zzbs(zzbdw), this.zzaZw);
        } catch (RemoteException e) {
            zzd(e);
        }
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.games.internal.IGamesService");
        return queryLocalInterface instanceof zzj ? (zzj) queryLocalInterface : new zzk(iBinder);
    }

    public final void zzd(zzbaz<TurnBasedMultiplayer.InitiateMatchResult> zzbaz, String str) throws RemoteException {
        ((zzj) zzrf()).zzc((zzf) new zzco(zzbaz), str);
    }

    public final void zzd(zzbaz<Events.LoadEventsResult> zzbaz, boolean z) throws RemoteException {
        this.zzaZp.flush();
        ((zzj) zzrf()).zze((zzf) new zzu(zzbaz), z);
    }

    public final void zzd(zzbdw<OnRequestReceivedListener> zzbdw) {
        try {
            ((zzj) zzrf()).zzc((zzf) new zzbw(zzbdw), this.zzaZw);
        } catch (RemoteException e) {
            zzd(e);
        }
    }

    /* access modifiers changed from: protected */
    public final String zzdb() {
        return "com.google.android.gms.games.service.START";
    }

    /* access modifiers changed from: protected */
    public final String zzdc() {
        return "com.google.android.gms.games.internal.IGamesService";
    }

    public final void zzdj(String str) {
        try {
            ((zzj) zzrf()).zzdm(str);
        } catch (RemoteException e) {
            zzd(e);
        }
    }

    public final Intent zzdk(String str) {
        try {
            return ((zzj) zzrf()).zzdk(str);
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    public final void zzdl(String str) {
        try {
            ((zzj) zzrf()).zza(str, this.zzaZt.zzbaL.zzbaM, this.zzaZt.zzbaL.zzuW());
        } catch (RemoteException e) {
            zzd(e);
        }
    }

    public final void zze(zzbaz<TurnBasedMultiplayer.LeaveMatchResult> zzbaz, String str) throws RemoteException {
        ((zzj) zzrf()).zze((zzf) new zzcp(zzbaz), str);
    }

    public final void zze(zzbaz<Stats.LoadPlayerStatsResult> zzbaz, boolean z) throws RemoteException {
        ((zzj) zzrf()).zzf((zzf) new zzbm(zzbaz), z);
    }

    public final void zze(zzbdw<Videos.CaptureOverlayStateListener> zzbdw) {
        try {
            ((zzj) zzrf()).zze((zzf) new zzl(zzbdw), this.zzaZw);
        } catch (RemoteException e) {
            zzd(e);
        }
    }

    public final void zzf(zzbaz<GamesMetadata.LoadGamesResult> zzbaz) throws RemoteException {
        ((zzj) zzrf()).zzb((zzf) new zzx(zzbaz));
    }

    public final void zzf(zzbaz<TurnBasedMultiplayer.CancelMatchResult> zzbaz, String str) throws RemoteException {
        ((zzj) zzrf()).zzd((zzf) new zzcn(zzbaz), str);
    }

    public final void zzf(zzbaz<Snapshots.LoadSnapshotsResult> zzbaz, boolean z) throws RemoteException {
        ((zzj) zzrf()).zzd((zzf) new zzck(zzbaz), z);
    }

    public final void zzg(zzbaz<Status> zzbaz) throws RemoteException {
        this.zzaZp.flush();
        ((zzj) zzrf()).zza((zzf) new zzcg(zzbaz));
    }

    public final void zzg(zzbaz<TurnBasedMultiplayer.LoadMatchResult> zzbaz, String str) throws RemoteException {
        ((zzj) zzrf()).zzf((zzf) new zzcq(zzbaz), str);
    }

    public final void zzh(zzbaz<Videos.CaptureCapabilitiesResult> zzbaz) throws RemoteException {
        ((zzj) zzrf()).zzc(new zzj(zzbaz));
    }

    public final void zzh(zzbaz<Quests.AcceptQuestResult> zzbaz, String str) throws RemoteException {
        this.zzaZp.flush();
        ((zzj) zzrf()).zzh(new zzbp(zzbaz), str);
    }

    public final String zzhl() {
        try {
            return ((zzj) zzrf()).zzhl();
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    public final void zzi(zzbaz<Videos.CaptureStateResult> zzbaz) throws RemoteException {
        ((zzj) zzrf()).zzd(new zzn(zzbaz));
    }

    public final void zzi(zzbaz<Snapshots.DeleteSnapshotResult> zzbaz, String str) throws RemoteException {
        ((zzj) zzrf()).zzg(new zzci(zzbaz), str);
    }

    public final Intent zzj(String str, int i, int i2) {
        try {
            return ((zzj) zzrf()).zzk(str, i, i2);
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public final Bundle zzmo() {
        String locale = getContext().getResources().getConfiguration().locale.toString();
        Bundle zzui = this.zzaZx.zzui();
        zzui.putString("com.google.android.gms.games.key.gamePackageName", this.zzaZq);
        zzui.putString("com.google.android.gms.games.key.desiredLocale", locale);
        zzui.putParcelable("com.google.android.gms.games.key.popupWindowToken", new BinderWrapper(this.zzaZt.zzbaL.zzbaM));
        zzui.putInt("com.google.android.gms.games.key.API_VERSION", 6);
        zzui.putBundle("com.google.android.gms.games.key.signInOptions", zzctu.zza(zzry()));
        return zzui;
    }

    public final boolean zzmv() {
        return true;
    }

    public final void zzn(String str, int i) {
        this.zzaZp.zzn(str, i);
    }

    public final void zzo(String str, int i) {
        try {
            ((zzj) zzrf()).zzo(str, i);
        } catch (RemoteException e) {
            zzd(e);
        }
    }

    public final Bundle zzoC() {
        try {
            Bundle zzoC = ((zzj) zzrf()).zzoC();
            if (zzoC == null) {
                return zzoC;
            }
            zzoC.setClassLoader(GamesClientImpl.class.getClassLoader());
            return zzoC;
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    public final void zzp(String str, int i) {
        try {
            ((zzj) zzrf()).zzp(str, i);
        } catch (RemoteException e) {
            zzd(e);
        }
    }

    public final void zzs(View view) {
        this.zzaZt.zzt(view);
    }

    public final void zzuA() {
        try {
            ((zzj) zzrf()).zzE(this.zzaZw);
        } catch (RemoteException e) {
            zzd(e);
        }
    }

    public final void zzuB() {
        try {
            ((zzj) zzrf()).zzG(this.zzaZw);
        } catch (RemoteException e) {
            zzd(e);
        }
    }

    public final void zzuC() {
        try {
            ((zzj) zzrf()).zzF(this.zzaZw);
        } catch (RemoteException e) {
            zzd(e);
        }
    }

    public final Intent zzuD() {
        try {
            return ((zzj) zzrf()).zzuD();
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    public final Intent zzuE() {
        try {
            return ((zzj) zzrf()).zzuE();
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    public final int zzuF() {
        try {
            return ((zzj) zzrf()).zzuF();
        } catch (RemoteException e) {
            zzd(e);
            return 4368;
        }
    }

    public final int zzuG() {
        try {
            return ((zzj) zzrf()).zzuG();
        } catch (RemoteException e) {
            zzd(e);
            return -1;
        }
    }

    public final Intent zzuH() {
        try {
            return ((zzj) zzrf()).zzuH();
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    public final int zzuI() {
        try {
            return ((zzj) zzrf()).zzuI();
        } catch (RemoteException e) {
            zzd(e);
            return -1;
        }
    }

    public final int zzuJ() {
        try {
            return ((zzj) zzrf()).zzuJ();
        } catch (RemoteException e) {
            zzd(e);
            return -1;
        }
    }

    public final int zzuK() {
        try {
            return ((zzj) zzrf()).zzuK();
        } catch (RemoteException e) {
            zzd(e);
            return -1;
        }
    }

    public final int zzuL() {
        try {
            return ((zzj) zzrf()).zzuL();
        } catch (RemoteException e) {
            zzd(e);
            return -1;
        }
    }

    public final Intent zzuM() {
        try {
            return ((zzj) zzrf()).zzuU();
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    public final boolean zzuN() {
        try {
            return ((zzj) zzrf()).zzuN();
        } catch (RemoteException e) {
            zzd(e);
            return false;
        }
    }

    public final void zzuO() {
        try {
            ((zzj) zzrf()).zzH(this.zzaZw);
        } catch (RemoteException e) {
            zzd(e);
        }
    }

    public final void zzuP() {
        if (isConnected()) {
            try {
                ((zzj) zzrf()).zzuP();
            } catch (RemoteException e) {
                zzd(e);
            }
        }
    }

    public final String zzus() {
        try {
            return ((zzj) zzrf()).zzus();
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    public final Player zzut() {
        PlayerBuffer playerBuffer;
        zzre();
        synchronized (this) {
            if (this.zzaZr == null) {
                try {
                    playerBuffer = new PlayerBuffer(((zzj) zzrf()).zzuS());
                    if (playerBuffer.getCount() > 0) {
                        this.zzaZr = (PlayerEntity) ((Player) playerBuffer.get(0)).freeze();
                    }
                    playerBuffer.release();
                } catch (RemoteException e) {
                    zzd(e);
                } catch (Throwable th) {
                    playerBuffer.release();
                    throw th;
                }
            }
        }
        return this.zzaZr;
    }

    public final Game zzuu() {
        GameBuffer gameBuffer;
        zzre();
        synchronized (this) {
            if (this.zzaZs == null) {
                try {
                    gameBuffer = new GameBuffer(((zzj) zzrf()).zzuT());
                    if (gameBuffer.getCount() > 0) {
                        this.zzaZs = (GameEntity) ((Game) gameBuffer.get(0)).freeze();
                    }
                    gameBuffer.release();
                } catch (RemoteException e) {
                    zzd(e);
                } catch (Throwable th) {
                    gameBuffer.release();
                    throw th;
                }
            }
        }
        return this.zzaZs;
    }

    public final Intent zzuv() {
        try {
            return ((zzj) zzrf()).zzuv();
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    public final Intent zzuw() {
        try {
            return ((zzj) zzrf()).zzuw();
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    public final Intent zzux() {
        try {
            return ((zzj) zzrf()).zzux();
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    public final Intent zzuy() {
        try {
            return ((zzj) zzrf()).zzuy();
        } catch (RemoteException e) {
            zzd(e);
            return null;
        }
    }

    public final void zzuz() {
        try {
            ((zzj) zzrf()).zzD(this.zzaZw);
        } catch (RemoteException e) {
            zzd(e);
        }
    }
}
