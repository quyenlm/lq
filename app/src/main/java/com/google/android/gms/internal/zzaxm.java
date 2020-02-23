package com.google.android.gms.internal;

import com.google.android.gms.games.Games;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzaxm {
    private static final zzayo zzapq = new zzayo("GameManagerMessage");
    protected final zzaxl zzawU;
    protected final String zzaxA;
    protected final String zzaxB;
    protected final String zzaxn;
    protected final long zzaxo;
    protected final JSONObject zzaxp;
    protected final int zzaxt;
    protected final int zzaxu;
    protected final String zzaxv;
    protected final int zzaxw;
    protected final int zzaxx;
    protected final List<zzaxp> zzaxy;
    protected final JSONObject zzaxz;

    private zzaxm(int i, int i2, String str, JSONObject jSONObject, int i3, int i4, List<zzaxp> list, JSONObject jSONObject2, String str2, String str3, long j, String str4, zzaxl zzaxl) {
        this.zzaxt = i;
        this.zzaxu = i2;
        this.zzaxv = str;
        this.zzaxp = jSONObject;
        this.zzaxw = i3;
        this.zzaxx = i4;
        this.zzaxy = list;
        this.zzaxz = jSONObject2;
        this.zzaxA = str2;
        this.zzaxn = str3;
        this.zzaxo = j;
        this.zzaxB = str4;
        this.zzawU = zzaxl;
    }

    private static List<zzaxp> zzb(JSONArray jSONArray) {
        zzaxp zzaxp2;
        ArrayList arrayList = new ArrayList();
        if (jSONArray == null) {
            return arrayList;
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject optJSONObject = jSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                try {
                    zzaxp2 = new zzaxp(optJSONObject);
                } catch (JSONException e) {
                    zzapq.zzc(e, "Exception when attempting to parse PlayerInfoMessageComponent at index %d", Integer.valueOf(i));
                    zzaxp2 = null;
                }
                if (zzaxp2 != null) {
                    arrayList.add(zzaxp2);
                }
            }
        }
        return arrayList;
    }

    protected static zzaxm zzo(JSONObject jSONObject) {
        int optInt = jSONObject.optInt("type", -1);
        switch (optInt) {
            case 1:
                zzaxl zzaxl = null;
                JSONObject optJSONObject = jSONObject.optJSONObject("gameManagerConfig");
                if (optJSONObject != null) {
                    zzaxl = new zzaxl(optJSONObject);
                }
                return new zzaxm(optInt, jSONObject.optInt("statusCode"), jSONObject.optString("errorDescription"), jSONObject.optJSONObject("extraMessageData"), jSONObject.optInt("gameplayState"), jSONObject.optInt("lobbyState"), zzb(jSONObject.optJSONArray(Games.EXTRA_PLAYER_IDS)), jSONObject.optJSONObject("gameData"), jSONObject.optString("gameStatusText"), jSONObject.optString("playerId"), jSONObject.optLong("requestId"), jSONObject.optString("playerToken"), zzaxl);
            case 2:
                return new zzaxm(optInt, jSONObject.optInt("statusCode"), jSONObject.optString("errorDescription"), jSONObject.optJSONObject("extraMessageData"), jSONObject.optInt("gameplayState"), jSONObject.optInt("lobbyState"), zzb(jSONObject.optJSONArray(Games.EXTRA_PLAYER_IDS)), jSONObject.optJSONObject("gameData"), jSONObject.optString("gameStatusText"), jSONObject.optString("playerId"), -1, (String) null, (zzaxl) null);
            default:
                try {
                    zzapq.zzf("Unrecognized Game Message type %d", Integer.valueOf(optInt));
                    break;
                } catch (JSONException e) {
                    zzapq.zzc(e, "Exception while parsing GameManagerMessage from json", new Object[0]);
                    break;
                }
        }
        return null;
    }
}
