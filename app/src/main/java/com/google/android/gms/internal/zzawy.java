package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.google.android.gms.cast.games.GameManagerClient;
import com.google.android.gms.cast.games.GameManagerState;
import com.google.android.gms.cast.games.PlayerInfo;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzawy extends zzaxt {
    private static String NAMESPACE = zzaye.zzcj("com.google.cast.games");
    /* access modifiers changed from: private */
    public static final zzayo zzapq = new zzayo("GameManagerChannel");
    private final SharedPreferences zzBT;
    /* access modifiers changed from: private */
    public final GoogleApiClient zzXj;
    /* access modifiers changed from: private */
    public final Cast.CastApi zzasb;
    private final Map<String, String> zzawR = new ConcurrentHashMap();
    private final List<zzayu> zzawS;
    private final String zzawT;
    /* access modifiers changed from: private */
    public zzaxl zzawU;
    private boolean zzawV = false;
    private GameManagerState zzawW;
    private GameManagerState zzawX;
    private String zzawY;
    private JSONObject zzawZ;
    private long zzaxa = 0;
    private GameManagerClient.Listener zzaxb;
    /* access modifiers changed from: private */
    public String zzaxc;
    private final zze zzvw;

    public zzawy(GoogleApiClient googleApiClient, String str, Cast.CastApi castApi) throws IllegalArgumentException, IllegalStateException {
        super(NAMESPACE, "CastGameManagerChannel", (String) null);
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("castSessionId cannot be null.");
        } else if (googleApiClient == null || !googleApiClient.isConnected() || !googleApiClient.hasConnectedApi(Cast.API)) {
            throw new IllegalArgumentException("googleApiClient needs to be connected and contain the Cast.API API.");
        } else {
            this.zzvw = zzi.zzrY();
            this.zzawS = new ArrayList();
            this.zzawT = str;
            this.zzasb = castApi;
            this.zzXj = googleApiClient;
            Context applicationContext = googleApiClient.getContext().getApplicationContext();
            this.zzBT = applicationContext.getApplicationContext().getSharedPreferences(String.format(Locale.ROOT, "%s.%s", new Object[]{applicationContext.getPackageName(), "game_manager_channel_data"}), 0);
            this.zzawX = null;
            this.zzawW = new zzaxn(0, 0, "", (JSONObject) null, new ArrayList(), "", -1);
        }
    }

    private final synchronized boolean isInitialized() {
        return this.zzawU != null;
    }

    private final JSONObject zza(long j, String str, int i, JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("requestId", j);
            jSONObject2.put("type", i);
            jSONObject2.put("extraMessageData", jSONObject);
            jSONObject2.put("playerId", str);
            jSONObject2.put("playerToken", zzcg(str));
            return jSONObject2;
        } catch (JSONException e) {
            zzapq.zzf("JSONException when trying to create a message: %s", e.getMessage());
            return null;
        }
    }

    private final synchronized void zza(zzaxm zzaxm) {
        boolean z = true;
        synchronized (this) {
            if (zzaxm.zzaxt != 1) {
                z = false;
            }
            this.zzawX = this.zzawW;
            if (z && zzaxm.zzawU != null) {
                this.zzawU = zzaxm.zzawU;
            }
            if (isInitialized()) {
                ArrayList arrayList = new ArrayList();
                for (zzaxp next : zzaxm.zzaxy) {
                    String playerId = next.getPlayerId();
                    arrayList.add(new zzaxo(playerId, next.getPlayerState(), next.getPlayerData(), this.zzawR.containsKey(playerId)));
                }
                this.zzawW = new zzaxn(zzaxm.zzaxx, zzaxm.zzaxw, zzaxm.zzaxA, zzaxm.zzaxz, arrayList, this.zzawU.zzox(), this.zzawU.getMaxPlayers());
                PlayerInfo player = this.zzawW.getPlayer(zzaxm.zzaxn);
                if (player != null && player.isControllable() && zzaxm.zzaxt == 2) {
                    this.zzawY = zzaxm.zzaxn;
                    this.zzawZ = zzaxm.zzaxp;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public final void zza(String str, int i, JSONObject jSONObject, zzayt zzayt) {
        long j = 1 + this.zzaxa;
        this.zzaxa = j;
        JSONObject zza = zza(j, str, i, jSONObject);
        if (zza == null) {
            zzayt.zza(-1, 2001, (Object) null);
            zzapq.zzf("Not sending request because it was invalid.", new Object[0]);
            return;
        }
        zzayu zzayu = new zzayu(this.zzvw, NotificationOptions.SKIP_STEP_THIRTY_SECONDS_IN_MS);
        zzayu.zza(j, zzayt);
        this.zzawS.add(zzayu);
        zzZ(true);
        this.zzasb.sendMessage(this.zzXj, getNamespace(), zza.toString()).setResultCallback(new zzaxd(this, j));
    }

    private final void zzb(long j, int i, Object obj) {
        Iterator<zzayu> it = this.zzawS.iterator();
        while (it.hasNext()) {
            if (it.next().zzc(j, i, obj)) {
                it.remove();
            }
        }
    }

    private final synchronized String zzcg(String str) throws IllegalStateException {
        return str == null ? null : this.zzawR.get(str);
    }

    private final synchronized void zzot() throws IllegalStateException {
        if (!isInitialized()) {
            throw new IllegalStateException("Attempted to perform an operation on the GameManagerChannel before it is initialized.");
        } else if (isDisposed()) {
            throw new IllegalStateException("Attempted to perform an operation on the GameManagerChannel after it has been disposed.");
        }
    }

    /* access modifiers changed from: private */
    public final synchronized void zzou() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("castSessionId", this.zzawT);
            jSONObject.put("playerTokenMap", new JSONObject(this.zzawR));
            this.zzBT.edit().putString("save_data", jSONObject.toString()).commit();
        } catch (JSONException e) {
            zzapq.zzf("Error while saving data: %s", e.getMessage());
        }
        return;
    }

    /* access modifiers changed from: private */
    public final synchronized void zzov() {
        String string = this.zzBT.getString("save_data", (String) null);
        if (string != null) {
            try {
                JSONObject jSONObject = new JSONObject(string);
                if (this.zzawT.equals(jSONObject.getString("castSessionId"))) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("playerTokenMap");
                    Iterator<String> keys = jSONObject2.keys();
                    while (keys.hasNext()) {
                        String next = keys.next();
                        this.zzawR.put(next, jSONObject2.getString(next));
                    }
                    this.zzaxa = 0;
                }
            } catch (JSONException e) {
                zzapq.zzf("Error while loading data: %s", e.getMessage());
            }
        }
        return;
    }

    public final synchronized void dispose() throws IllegalStateException {
        if (!this.zzawV) {
            this.zzawW = null;
            this.zzawX = null;
            this.zzawY = null;
            this.zzawZ = null;
            this.zzawV = true;
            try {
                this.zzasb.removeMessageReceivedCallbacks(this.zzXj, getNamespace());
            } catch (IOException e) {
                zzapq.zzf("Exception while detaching game manager channel.", e);
            }
        }
        return;
    }

    public final synchronized GameManagerState getCurrentState() throws IllegalStateException {
        zzot();
        return this.zzawW;
    }

    public final synchronized String getLastUsedPlayerId() throws IllegalStateException {
        zzot();
        return this.zzaxc;
    }

    public final synchronized boolean isDisposed() {
        return this.zzawV;
    }

    public final synchronized void sendGameMessage(String str, JSONObject jSONObject) throws IllegalStateException {
        zzot();
        long j = 1 + this.zzaxa;
        this.zzaxa = j;
        JSONObject zza = zza(j, str, 7, jSONObject);
        if (zza != null) {
            this.zzasb.sendMessage(this.zzXj, getNamespace(), zza.toString());
        }
    }

    public final synchronized PendingResult<GameManagerClient.GameManagerResult> sendGameRequest(String str, JSONObject jSONObject) throws IllegalStateException {
        zzot();
        return this.zzXj.zze(new zzaxc(this, str, jSONObject));
    }

    public final synchronized void setListener(GameManagerClient.Listener listener) {
        this.zzaxb = listener;
    }

    public final synchronized PendingResult<GameManagerClient.GameManagerInstanceResult> zza(GameManagerClient gameManagerClient) throws IllegalArgumentException {
        return this.zzXj.zze(new zzawz(this, gameManagerClient));
    }

    public final synchronized PendingResult<GameManagerClient.GameManagerResult> zza(String str, int i, JSONObject jSONObject) throws IllegalStateException {
        zzot();
        return this.zzXj.zze(new zzaxb(this, i, str, jSONObject));
    }

    public final void zzc(long j, int i) {
        zzb(j, i, (Object) null);
    }

    public final void zzch(String str) {
        int i;
        zzapq.zzb("message received: %s", str);
        try {
            zzaxm zzo = zzaxm.zzo(new JSONObject(str));
            if (zzo == null) {
                zzapq.zzf("Could not parse game manager message from string: %s", str);
            } else if ((isInitialized() || zzo.zzawU != null) && !isDisposed()) {
                boolean z = zzo.zzaxt == 1;
                if (z && !TextUtils.isEmpty(zzo.zzaxB)) {
                    this.zzawR.put(zzo.zzaxn, zzo.zzaxB);
                    zzou();
                }
                if (zzo.zzaxu == 0) {
                    zza(zzo);
                } else {
                    zzapq.zzf("Not updating from game message because the message contains error code: %d", Integer.valueOf(zzo.zzaxu));
                }
                int i2 = zzo.zzaxu;
                switch (i2) {
                    case 0:
                        i = 0;
                        break;
                    case 1:
                        i = 2001;
                        break;
                    case 2:
                        i = 2003;
                        break;
                    case 3:
                        i = GameManagerClient.STATUS_INCORRECT_VERSION;
                        break;
                    case 4:
                        i = GameManagerClient.STATUS_TOO_MANY_PLAYERS;
                        break;
                    default:
                        zzapq.zzf(new StringBuilder(53).append("Unknown GameManager protocol status code: ").append(i2).toString(), new Object[0]);
                        i = 13;
                        break;
                }
                if (z) {
                    zzb(zzo.zzaxo, i, zzo);
                }
                if (isInitialized() && i == 0) {
                    if (this.zzaxb != null) {
                        if (this.zzawX != null && !this.zzawW.equals(this.zzawX)) {
                            this.zzaxb.onStateChanged(this.zzawW, this.zzawX);
                        }
                        if (!(this.zzawZ == null || this.zzawY == null)) {
                            this.zzaxb.onGameMessageReceived(this.zzawY, this.zzawZ);
                        }
                    }
                    this.zzawX = null;
                    this.zzawY = null;
                    this.zzawZ = null;
                }
            }
        } catch (JSONException e) {
            zzapq.zzf("Message is malformed (%s); ignoring: %s", e.getMessage(), str);
        }
    }

    /* access modifiers changed from: protected */
    public final boolean zzz(long j) {
        boolean z;
        Iterator<zzayu> it = this.zzawS.iterator();
        while (it.hasNext()) {
            if (it.next().zzd(j, 15)) {
                it.remove();
            }
        }
        synchronized (zzayu.zzrl) {
            Iterator<zzayu> it2 = this.zzawS.iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (it2.next().zzoO()) {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
        }
        return z;
    }
}
