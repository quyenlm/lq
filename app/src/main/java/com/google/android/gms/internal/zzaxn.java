package com.google.android.gms.internal;

import com.google.android.gms.cast.games.GameManagerState;
import com.google.android.gms.cast.games.PlayerInfo;
import com.google.android.gms.common.util.zzo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public final class zzaxn implements GameManagerState {
    private final String zzaxA;
    private final Map<String, PlayerInfo> zzaxC;
    private final String zzaxq;
    private final int zzaxr;
    private final int zzaxw;
    private final int zzaxx;
    private final JSONObject zzaxz;

    public zzaxn(int i, int i2, String str, JSONObject jSONObject, Collection<PlayerInfo> collection, String str2, int i3) {
        this.zzaxx = i;
        this.zzaxw = i2;
        this.zzaxA = str;
        this.zzaxz = jSONObject;
        this.zzaxq = str2;
        this.zzaxr = i3;
        this.zzaxC = new HashMap(collection.size());
        for (PlayerInfo next : collection) {
            this.zzaxC.put(next.getPlayerId(), next);
        }
    }

    public final boolean equals(Object obj) {
        boolean z;
        if (obj == null || !(obj instanceof GameManagerState)) {
            return false;
        }
        GameManagerState gameManagerState = (GameManagerState) obj;
        if (getPlayers().size() != gameManagerState.getPlayers().size()) {
            return false;
        }
        for (PlayerInfo next : getPlayers()) {
            boolean z2 = false;
            for (PlayerInfo next2 : gameManagerState.getPlayers()) {
                if (!zzaye.zza(next.getPlayerId(), next2.getPlayerId())) {
                    z = z2;
                } else if (!zzaye.zza(next, next2)) {
                    return false;
                } else {
                    z = true;
                }
                z2 = z;
            }
            if (!z2) {
                return false;
            }
        }
        return this.zzaxx == gameManagerState.getLobbyState() && this.zzaxw == gameManagerState.getGameplayState() && this.zzaxr == gameManagerState.getMaxPlayers() && zzaye.zza(this.zzaxq, gameManagerState.getApplicationName()) && zzaye.zza(this.zzaxA, gameManagerState.getGameStatusText()) && zzo.zzc(this.zzaxz, gameManagerState.getGameData());
    }

    public final CharSequence getApplicationName() {
        return this.zzaxq;
    }

    public final List<PlayerInfo> getConnectedControllablePlayers() {
        ArrayList arrayList = new ArrayList();
        for (PlayerInfo next : getPlayers()) {
            if (next.isConnected() && next.isControllable()) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public final List<PlayerInfo> getConnectedPlayers() {
        ArrayList arrayList = new ArrayList();
        for (PlayerInfo next : getPlayers()) {
            if (next.isConnected()) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public final List<PlayerInfo> getControllablePlayers() {
        ArrayList arrayList = new ArrayList();
        for (PlayerInfo next : getPlayers()) {
            if (next.isControllable()) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public final JSONObject getGameData() {
        return this.zzaxz;
    }

    public final CharSequence getGameStatusText() {
        return this.zzaxA;
    }

    public final int getGameplayState() {
        return this.zzaxw;
    }

    public final Collection<String> getListOfChangedPlayers(GameManagerState gameManagerState) {
        HashSet hashSet = new HashSet();
        for (PlayerInfo next : getPlayers()) {
            PlayerInfo player = gameManagerState.getPlayer(next.getPlayerId());
            if (player == null || !next.equals(player)) {
                hashSet.add(next.getPlayerId());
            }
        }
        for (PlayerInfo next2 : gameManagerState.getPlayers()) {
            if (getPlayer(next2.getPlayerId()) == null) {
                hashSet.add(next2.getPlayerId());
            }
        }
        return hashSet;
    }

    public final int getLobbyState() {
        return this.zzaxx;
    }

    public final int getMaxPlayers() {
        return this.zzaxr;
    }

    public final PlayerInfo getPlayer(String str) {
        if (str == null) {
            return null;
        }
        return this.zzaxC.get(str);
    }

    public final Collection<PlayerInfo> getPlayers() {
        return Collections.unmodifiableCollection(this.zzaxC.values());
    }

    public final List<PlayerInfo> getPlayersInState(int i) {
        ArrayList arrayList = new ArrayList();
        for (PlayerInfo next : getPlayers()) {
            if (next.getPlayerState() == i) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public final boolean hasGameDataChanged(GameManagerState gameManagerState) {
        return !zzo.zzc(this.zzaxz, gameManagerState.getGameData());
    }

    public final boolean hasGameStatusTextChanged(GameManagerState gameManagerState) {
        return !zzaye.zza(this.zzaxA, gameManagerState.getGameStatusText());
    }

    public final boolean hasGameplayStateChanged(GameManagerState gameManagerState) {
        return this.zzaxw != gameManagerState.getGameplayState();
    }

    public final boolean hasLobbyStateChanged(GameManagerState gameManagerState) {
        return this.zzaxx != gameManagerState.getLobbyState();
    }

    public final boolean hasPlayerChanged(String str, GameManagerState gameManagerState) {
        return !zzaye.zza(getPlayer(str), gameManagerState.getPlayer(str));
    }

    public final boolean hasPlayerDataChanged(String str, GameManagerState gameManagerState) {
        PlayerInfo player = getPlayer(str);
        PlayerInfo player2 = gameManagerState.getPlayer(str);
        if (player == null && player2 == null) {
            return false;
        }
        if (player == null || player2 == null) {
            return true;
        }
        return !zzo.zzc(player.getPlayerData(), player2.getPlayerData());
    }

    public final boolean hasPlayerStateChanged(String str, GameManagerState gameManagerState) {
        PlayerInfo player = getPlayer(str);
        PlayerInfo player2 = gameManagerState.getPlayer(str);
        if (player == null && player2 == null) {
            return false;
        }
        if (player == null || player2 == null) {
            return true;
        }
        return player.getPlayerState() != player2.getPlayerState();
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzaxx), Integer.valueOf(this.zzaxw), this.zzaxC, this.zzaxA, this.zzaxz, this.zzaxq, Integer.valueOf(this.zzaxr)});
    }
}
