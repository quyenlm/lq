package com.google.android.gms.internal;

import com.appsflyer.share.Constants;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public final class ry {
    private static final ry zzcer = new ry();
    /* access modifiers changed from: private */
    public final Map<qd, Map<String, qu>> zzces = new HashMap();

    public static qu zza(qd qdVar, rx rxVar, FirebaseDatabase firebaseDatabase) throws DatabaseException {
        return zzcer.zzb(qdVar, rxVar, firebaseDatabase);
    }

    private final qu zzb(qd qdVar, rx rxVar, FirebaseDatabase firebaseDatabase) throws DatabaseException {
        qu quVar;
        qdVar.zzGD();
        String str = rxVar.host;
        String str2 = rxVar.zzbxU;
        String sb = new StringBuilder(String.valueOf(str).length() + 9 + String.valueOf(str2).length()).append("https://").append(str).append(Constants.URL_PATH_DELIMITER).append(str2).toString();
        synchronized (this.zzces) {
            if (!this.zzces.containsKey(qdVar)) {
                this.zzces.put(qdVar, new HashMap());
            }
            Map map = this.zzces.get(qdVar);
            if (!map.containsKey(sb)) {
                quVar = new qu(rxVar, qdVar, firebaseDatabase);
                map.put(sb, quVar);
            } else {
                throw new IllegalStateException("createLocalRepo() called for existing repo.");
            }
        }
        return quVar;
    }

    public static void zzd(qd qdVar) {
        ry ryVar = zzcer;
        sd sdVar = qdVar.zzccQ;
        if (sdVar != null) {
            sdVar.zzq(new sb(ryVar, qdVar));
        }
    }

    public static void zze(qd qdVar) {
        ry ryVar = zzcer;
        sd sdVar = qdVar.zzccQ;
        if (sdVar != null) {
            sdVar.zzq(new sc(ryVar, qdVar));
        }
    }

    public static void zzk(qu quVar) {
        quVar.zzq(new rz(quVar));
    }

    public static void zzl(qu quVar) {
        quVar.zzq(new sa(quVar));
    }
}
