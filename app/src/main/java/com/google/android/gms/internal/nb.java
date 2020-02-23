package com.google.android.gms.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.support.v4.media.session.PlaybackStateCompat;
import com.appsflyer.share.Constants;
import com.google.firebase.database.DatabaseException;
import com.tencent.tp.a.h;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.cookie.ClientCookie;

public final class nb implements ui {
    private static final Charset zzayt = Charset.forName("UTF-8");
    private final SQLiteDatabase zzbZD;
    private final wl zzbZE;
    private boolean zzbZF;
    private long zzbZG = 0;

    public nb(Context context, qd qdVar, String str) {
        try {
            String encode = URLEncoder.encode(str, "utf-8");
            this.zzbZE = qdVar.zzgP("Persistence");
            this.zzbZD = zzN(context, encode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static xm zzB(byte[] bArr) {
        try {
            return xp.zza(yr.zzgW(new String(bArr, zzayt)), xd.zzJb());
        } catch (IOException e) {
            IOException iOException = e;
            String valueOf = String.valueOf(new String(bArr, zzayt));
            throw new RuntimeException(valueOf.length() != 0 ? "Could not deserialize node: ".concat(valueOf) : new String("Could not deserialize node: "), iOException);
        }
    }

    private final void zzFw() {
        zd.zzb(this.zzbZF, "Transaction expected to already be in progress.");
    }

    private static SQLiteDatabase zzN(Context context, String str) {
        try {
            SQLiteDatabase writableDatabase = new ne(context, str).getWritableDatabase();
            writableDatabase.rawQuery("PRAGMA locking_mode = EXCLUSIVE", (String[]) null).close();
            writableDatabase.beginTransaction();
            writableDatabase.endTransaction();
            return writableDatabase;
        } catch (SQLiteException e) {
            if (e instanceof SQLiteDatabaseLockedException) {
                throw new DatabaseException("Failed to gain exclusive lock to Firebase Database's offline persistence. This generally means you are using Firebase Database from multiple processes in your app. Keep in mind that multi-process Android apps execute the code in your Application class in all processes, so you may need to avoid initializing FirebaseDatabase in your Application class. If you are intentionally using Firebase Database from multiple processes, you can only enable offline persistence (i.e. call setPersistenceEnabled(true)) in one of them.", e);
            }
            throw e;
        }
    }

    private static byte[] zzQ(List<byte[]> list) {
        int i = 0;
        for (byte[] length : list) {
            i = length.length + i;
        }
        byte[] bArr = new byte[i];
        int i2 = 0;
        for (byte[] next : list) {
            System.arraycopy(next, 0, bArr, i2, next.length);
            i2 = next.length + i2;
        }
        return bArr;
    }

    private static byte[] zzW(Object obj) {
        try {
            return yr.zzak(obj).getBytes(zzayt);
        } catch (IOException e) {
            throw new RuntimeException("Could not serialize leaf node", e);
        }
    }

    private static int zza(qr qrVar, List<String> list, int i) {
        int i2 = i + 1;
        String zzc = zzc(qrVar);
        if (!list.get(i).startsWith(zzc)) {
            throw new IllegalStateException("Extracting split nodes needs to start with path prefix");
        }
        while (i2 < list.size() && list.get(i2).equals(zza(qrVar, i2 - i))) {
            i2++;
        }
        if (i2 < list.size()) {
            String str = list.get(i2);
            String valueOf = String.valueOf(zzc);
            String valueOf2 = String.valueOf(".part-");
            if (str.startsWith(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf))) {
                throw new IllegalStateException("Run did not finish with all parts");
            }
        }
        return i2 - i;
    }

    private final int zza(String str, qr qrVar) {
        String zzc = zzc(qrVar);
        String zzgC = zzgC(zzc);
        return this.zzbZD.delete(str, "path >= ? AND path < ?", new String[]{zzc, zzgC});
    }

    private final Cursor zza(qr qrVar, String[] strArr) {
        String zzc = zzc(qrVar);
        String zzgC = zzgC(zzc);
        String[] strArr2 = new String[(qrVar.size() + 3)];
        int i = 0;
        StringBuilder sb = new StringBuilder(h.a);
        qr qrVar2 = qrVar;
        while (!qrVar2.isEmpty()) {
            sb.append(ClientCookie.PATH_ATTR);
            sb.append(" = ? OR ");
            strArr2[i] = zzc(qrVar2);
            qrVar2 = qrVar2.zzHe();
            i++;
        }
        sb.append(ClientCookie.PATH_ATTR);
        sb.append(" = ?)");
        strArr2[i] = zzc(qr.zzGZ());
        String valueOf = String.valueOf(sb.toString());
        String valueOf2 = String.valueOf(" OR (path > ? AND path < ?)");
        String concat = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        strArr2[qrVar.size() + 1] = zzc;
        strArr2[qrVar.size() + 2] = zzgC;
        return this.zzbZD.query("serverCache", strArr, concat, strArr2, (String) null, (String) null, ClientCookie.PATH_ATTR);
    }

    private static String zza(qr qrVar, int i) {
        String valueOf = String.valueOf(zzc(qrVar));
        String valueOf2 = String.valueOf(String.format(".part-%04d", new Object[]{Integer.valueOf(i)}));
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    private final void zza(qr qrVar, long j, String str, byte[] bArr) {
        int i = 0;
        zzFw();
        this.zzbZD.delete("writes", "id = ?", new String[]{String.valueOf(j)});
        if (bArr.length >= 262144) {
            List<byte[]> zzd = zzd(bArr, 262144);
            while (true) {
                int i2 = i;
                if (i2 < zzd.size()) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("id", Long.valueOf(j));
                    contentValues.put(ClientCookie.PATH_ATTR, zzc(qrVar));
                    contentValues.put("type", str);
                    contentValues.put("part", Integer.valueOf(i2));
                    contentValues.put("node", zzd.get(i2));
                    this.zzbZD.insertWithOnConflict("writes", (String) null, contentValues, 5);
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        } else {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("id", Long.valueOf(j));
            contentValues2.put(ClientCookie.PATH_ATTR, zzc(qrVar));
            contentValues2.put("type", str);
            contentValues2.put("part", (Integer) null);
            contentValues2.put("node", bArr);
            this.zzbZD.insertWithOnConflict("writes", (String) null, contentValues2, 5);
        }
    }

    private final void zza(qr qrVar, qr qrVar2, uv<Long> uvVar, uv<Long> uvVar2, uj ujVar, List<za<qr, xm>> list) {
        if (uvVar.getValue() != null) {
            int intValue = ((Integer) ujVar.zza(0, new nc(this, uvVar2))).intValue();
            if (intValue > 0) {
                qr zzh = qrVar.zzh(qrVar2);
                if (this.zzbZE.zzIH()) {
                    this.zzbZE.zzb(String.format("Need to rewrite %d nodes below path %s", new Object[]{Integer.valueOf(intValue), zzh}), (Throwable) null, new Object[0]);
                }
                ujVar.zza(null, new nd(this, uvVar2, list, qrVar2, zzb(zzh)));
                return;
            }
            return;
        }
        Iterator<Map.Entry<wp, uv<Long>>> it = uvVar.zzHS().iterator();
        while (it.hasNext()) {
            Map.Entry next = it.next();
            wp wpVar = (wp) next.getKey();
            uj zzd = ujVar.zzd((wp) next.getKey());
            uv<Long> zze = uvVar2.zze(wpVar);
            zza(qrVar, qrVar2.zza(wpVar), (uv) next.getValue(), zze, zzd, list);
        }
    }

    private final void zza(qr qrVar, xm xmVar, boolean z) {
        int i;
        int i2;
        long currentTimeMillis = System.currentTimeMillis();
        if (!z) {
            i2 = zza("serverCache", qrVar);
            i = zzc(qrVar, xmVar);
        } else {
            Iterator it = xmVar.iterator();
            i = 0;
            i2 = 0;
            while (it.hasNext()) {
                xl xlVar = (xl) it.next();
                i2 += zza("serverCache", qrVar.zza(xlVar.zzJk()));
                i = zzc(qrVar.zza(xlVar.zzJk()), xlVar.zzFn()) + i;
            }
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(String.format("Persisted a total of %d rows and deleted %d rows for a set at %s in %dms", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), qrVar.toString(), Long.valueOf(currentTimeMillis2)}), (Throwable) null, new Object[0]);
        }
    }

    /* JADX INFO: finally extract failed */
    private final xm zzb(qr qrVar) {
        xm zzB;
        qr qrVar2;
        boolean z;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        long currentTimeMillis = System.currentTimeMillis();
        Cursor zza = zza(qrVar, new String[]{ClientCookie.PATH_ATTR, "value"});
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        long currentTimeMillis3 = System.currentTimeMillis();
        while (zza.moveToNext()) {
            try {
                arrayList.add(zza.getString(0));
                arrayList2.add(zza.getBlob(1));
            } catch (Throwable th) {
                zza.close();
                throw th;
            }
        }
        zza.close();
        long currentTimeMillis4 = System.currentTimeMillis() - currentTimeMillis3;
        long currentTimeMillis5 = System.currentTimeMillis();
        xm zzJb = xd.zzJb();
        boolean z2 = false;
        HashMap hashMap = new HashMap();
        int i = 0;
        while (i < arrayList2.size()) {
            if (((String) arrayList.get(i)).endsWith(".part-0000")) {
                String str = (String) arrayList.get(i);
                qrVar2 = new qr(str.substring(0, str.length() - 10));
                int zza2 = zza(qrVar2, (List<String>) arrayList, i);
                if (this.zzbZE.zzIH()) {
                    this.zzbZE.zzb(new StringBuilder(42).append("Loading split node with ").append(zza2).append(" parts.").toString(), (Throwable) null, new Object[0]);
                }
                i = (i + zza2) - 1;
                zzB = zzB(zzQ(arrayList2.subList(i, i + zza2)));
            } else {
                zzB = zzB((byte[]) arrayList2.get(i));
                qrVar2 = new qr((String) arrayList.get(i));
            }
            if (qrVar2.zzHf() != null && qrVar2.zzHf().zzIN()) {
                hashMap.put(qrVar2, zzB);
                z = z2;
            } else if (qrVar2.zzi(qrVar)) {
                zd.zzb(!z2, "Descendants of path must come after ancestors.");
                zzJb = zzB.zzN(qr.zza(qrVar2, qrVar));
                z = z2;
            } else if (qrVar.zzi(qrVar2)) {
                z = true;
                zzJb = zzJb.zzl(qr.zza(qrVar, qrVar2), zzB);
            } else {
                throw new IllegalStateException(String.format("Loading an unrelated row with path %s for %s", new Object[]{qrVar2, qrVar}));
            }
            i++;
            z2 = z;
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            zzJb = zzJb.zzl(qr.zza(qrVar, (qr) entry.getKey()), (xm) entry.getValue());
        }
        long currentTimeMillis6 = System.currentTimeMillis() - currentTimeMillis5;
        long currentTimeMillis7 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(String.format("Loaded a total of %d rows for a total of %d nodes at %s in %dms (Query: %dms, Loading: %dms, Serializing: %dms)", new Object[]{Integer.valueOf(arrayList2.size()), Integer.valueOf(yy.zzo(zzJb)), qrVar, Long.valueOf(currentTimeMillis7), Long.valueOf(currentTimeMillis2), Long.valueOf(currentTimeMillis4), Long.valueOf(currentTimeMillis6)}), (Throwable) null, new Object[0]);
        }
        return zzJb;
    }

    private final int zzc(qr qrVar, xm xmVar) {
        int i;
        int i2 = 0;
        long zzn = yy.zzn(xmVar);
        if (!(xmVar instanceof wr) || zzn <= PlaybackStateCompat.ACTION_PREPARE) {
            zzd(qrVar, xmVar);
            return 1;
        }
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(String.format("Node estimated serialized size at path %s of %d bytes exceeds limit of %d bytes. Splitting up.", new Object[]{qrVar, Long.valueOf(zzn), 16384}), (Throwable) null, new Object[0]);
        }
        Iterator it = xmVar.iterator();
        while (true) {
            i = i2;
            if (!it.hasNext()) {
                break;
            }
            xl xlVar = (xl) it.next();
            i2 = zzc(qrVar.zza(xlVar.zzJk()), xlVar.zzFn()) + i;
        }
        if (!xmVar.zzIR().isEmpty()) {
            zzd(qrVar.zza(wp.zzIL()), xmVar.zzIR());
            i++;
        }
        zzd(qrVar, (xm) xd.zzJb());
        return i + 1;
    }

    private static String zzc(qr qrVar) {
        return qrVar.isEmpty() ? Constants.URL_PATH_DELIMITER : String.valueOf(qrVar.toString()).concat(Constants.URL_PATH_DELIMITER);
    }

    private static List<byte[]> zzd(byte[] bArr, int i) {
        int length = ((bArr.length - 1) / 262144) + 1;
        ArrayList arrayList = new ArrayList(length);
        for (int i2 = 0; i2 < length; i2++) {
            int min = Math.min(262144, bArr.length - (i2 * 262144));
            byte[] bArr2 = new byte[min];
            System.arraycopy(bArr, i2 * 262144, bArr2, 0, min);
            arrayList.add(bArr2);
        }
        return arrayList;
    }

    private final void zzd(qr qrVar, xm xmVar) {
        int i = 0;
        byte[] zzW = zzW(xmVar.getValue(true));
        if (zzW.length >= 262144) {
            List<byte[]> zzd = zzd(zzW, 262144);
            if (this.zzbZE.zzIH()) {
                this.zzbZE.zzb(new StringBuilder(45).append("Saving huge leaf node with ").append(zzd.size()).append(" parts.").toString(), (Throwable) null, new Object[0]);
            }
            while (true) {
                int i2 = i;
                if (i2 < zzd.size()) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ClientCookie.PATH_ATTR, zza(qrVar, i2));
                    contentValues.put("value", zzd.get(i2));
                    this.zzbZD.insertWithOnConflict("serverCache", (String) null, contentValues, 5);
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        } else {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put(ClientCookie.PATH_ATTR, zzc(qrVar));
            contentValues2.put("value", zzW);
            this.zzbZD.insertWithOnConflict("serverCache", (String) null, contentValues2, 5);
        }
    }

    private static String zzgC(String str) {
        String valueOf = String.valueOf(str.substring(0, str.length() - 1));
        return new StringBuilder(String.valueOf(valueOf).length() + 1).append(valueOf).append('0').toString();
    }

    private static String zzj(Collection<Long> collection) {
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        Iterator<Long> it = collection.iterator();
        while (true) {
            boolean z2 = z;
            if (!it.hasNext()) {
                return sb.toString();
            }
            long longValue = it.next().longValue();
            if (!z2) {
                sb.append(",");
            }
            z = false;
            sb.append(longValue);
        }
    }

    public final void beginTransaction() {
        zd.zzb(!this.zzbZF, "runInTransaction called when an existing transaction is already in progress.");
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb("Starting transaction.", (Throwable) null, new Object[0]);
        }
        this.zzbZD.beginTransaction();
        this.zzbZF = true;
        this.zzbZG = System.currentTimeMillis();
    }

    public final void endTransaction() {
        this.zzbZD.endTransaction();
        this.zzbZF = false;
        long currentTimeMillis = System.currentTimeMillis() - this.zzbZG;
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(String.format("Transaction completed. Elapsed: %dms", new Object[]{Long.valueOf(currentTimeMillis)}), (Throwable) null, new Object[0]);
        }
    }

    public final void setTransactionSuccessful() {
        this.zzbZD.setTransactionSuccessful();
    }

    public final List<tm> zzFs() {
        byte[] zzQ;
        tm tmVar;
        String[] strArr = {"id", ClientCookie.PATH_ATTR, "type", "part", "node"};
        long currentTimeMillis = System.currentTimeMillis();
        Cursor query = this.zzbZD.query("writes", strArr, (String) null, (String[]) null, (String) null, (String) null, "id, part");
        ArrayList arrayList = new ArrayList();
        while (query.moveToNext()) {
            try {
                long j = query.getLong(0);
                qr qrVar = new qr(query.getString(1));
                String string = query.getString(2);
                if (query.isNull(3)) {
                    zzQ = query.getBlob(4);
                } else {
                    ArrayList arrayList2 = new ArrayList();
                    do {
                        arrayList2.add(query.getBlob(4));
                        if (!query.moveToNext() || query.getLong(0) != j) {
                            query.moveToPrevious();
                            zzQ = zzQ(arrayList2);
                        }
                        arrayList2.add(query.getBlob(4));
                        break;
                    } while (query.getLong(0) != j);
                    query.moveToPrevious();
                    zzQ = zzQ(arrayList2);
                }
                Object zzgW = yr.zzgW(new String(zzQ, zzayt));
                if ("o".equals(string)) {
                    tmVar = new tm(j, qrVar, xp.zza(zzgW, xd.zzJb()), true);
                } else if ("m".equals(string)) {
                    tmVar = new tm(j, qrVar, pz.zzD((Map) zzgW));
                } else {
                    String valueOf = String.valueOf(string);
                    throw new IllegalStateException(valueOf.length() != 0 ? "Got invalid write type: ".concat(valueOf) : new String("Got invalid write type: "));
                }
                arrayList.add(tmVar);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load writes", e);
            } catch (Throwable th) {
                query.close();
                throw th;
            }
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(String.format("Loaded %d writes in %dms", new Object[]{Integer.valueOf(arrayList.size()), Long.valueOf(currentTimeMillis2)}), (Throwable) null, new Object[0]);
        }
        query.close();
        return arrayList;
    }

    public final long zzFt() {
        Cursor rawQuery = this.zzbZD.rawQuery(String.format("SELECT sum(length(%s) + length(%s)) FROM %s", new Object[]{"value", ClientCookie.PATH_ATTR, "serverCache"}), (String[]) null);
        try {
            if (rawQuery.moveToFirst()) {
                return rawQuery.getLong(0);
            }
            throw new IllegalStateException("Couldn't read database result!");
        } finally {
            rawQuery.close();
        }
    }

    public final List<un> zzFu() {
        String[] strArr = {"id", ClientCookie.PATH_ATTR, "queryParams", "lastUse", "complete", "active"};
        long currentTimeMillis = System.currentTimeMillis();
        Cursor query = this.zzbZD.query("trackedQueries", strArr, (String) null, (String[]) null, (String) null, (String) null, "id");
        ArrayList arrayList = new ArrayList();
        while (query.moveToNext()) {
            try {
                arrayList.add(new un(query.getLong(0), new vt(new qr(query.getString(1)), vq.zzF(yr.zzgV(query.getString(2)))), query.getLong(3), query.getInt(4) != 0, query.getInt(5) != 0));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Throwable th) {
                query.close();
                throw th;
            }
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(String.format("Loaded %d tracked queries in %dms", new Object[]{Integer.valueOf(arrayList.size()), Long.valueOf(currentTimeMillis2)}), (Throwable) null, new Object[0]);
        }
        query.close();
        return arrayList;
    }

    public final void zzFv() {
        zzFw();
        long currentTimeMillis = System.currentTimeMillis();
        int delete = this.zzbZD.delete("writes", (String) null, (String[]) null);
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(String.format("Deleted %d (all) write(s) in %dms", new Object[]{Integer.valueOf(delete), Long.valueOf(currentTimeMillis2)}), (Throwable) null, new Object[0]);
        }
    }

    public final xm zza(qr qrVar) {
        return zzb(qrVar);
    }

    public final void zza(long j, Set<wp> set) {
        zzFw();
        long currentTimeMillis = System.currentTimeMillis();
        String valueOf = String.valueOf(j);
        this.zzbZD.delete("trackedKeys", "id = ?", new String[]{valueOf});
        for (wp asString : set) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", Long.valueOf(j));
            contentValues.put("key", asString.asString());
            this.zzbZD.insertWithOnConflict("trackedKeys", (String) null, contentValues, 5);
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(String.format("Set %d tracked query keys for tracked query %d in %dms", new Object[]{Integer.valueOf(set.size()), Long.valueOf(j), Long.valueOf(currentTimeMillis2)}), (Throwable) null, new Object[0]);
        }
    }

    public final void zza(long j, Set<wp> set, Set<wp> set2) {
        zzFw();
        long currentTimeMillis = System.currentTimeMillis();
        String valueOf = String.valueOf(j);
        for (wp asString : set2) {
            this.zzbZD.delete("trackedKeys", "id = ? AND key = ?", new String[]{valueOf, asString.asString()});
        }
        for (wp asString2 : set) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", Long.valueOf(j));
            contentValues.put("key", asString2.asString());
            this.zzbZD.insertWithOnConflict("trackedKeys", (String) null, contentValues, 5);
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(String.format("Updated tracked query keys (%d added, %d removed) for tracked query id %d in %dms", new Object[]{Integer.valueOf(set.size()), Integer.valueOf(set2.size()), Long.valueOf(j), Long.valueOf(currentTimeMillis2)}), (Throwable) null, new Object[0]);
        }
    }

    public final void zza(qr qrVar, pz pzVar) {
        zzFw();
        long currentTimeMillis = System.currentTimeMillis();
        Iterator<Map.Entry<qr, xm>> it = pzVar.iterator();
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            Map.Entry next = it.next();
            i += zza("serverCache", qrVar.zzh((qr) next.getKey()));
            i2 = zzc(qrVar.zzh((qr) next.getKey()), (xm) next.getValue()) + i2;
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(String.format("Persisted a total of %d rows and deleted %d rows for a merge at %s in %dms", new Object[]{Integer.valueOf(i2), Integer.valueOf(i), qrVar.toString(), Long.valueOf(currentTimeMillis2)}), (Throwable) null, new Object[0]);
        }
    }

    public final void zza(qr qrVar, pz pzVar, long j) {
        zzFw();
        long currentTimeMillis = System.currentTimeMillis();
        zza(qrVar, j, "m", zzW(pzVar.zzaD(true)));
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(String.format("Persisted user merge in %dms", new Object[]{Long.valueOf(currentTimeMillis2)}), (Throwable) null, new Object[0]);
        }
    }

    public final void zza(qr qrVar, uj ujVar) {
        if (ujVar.zzHN()) {
            zzFw();
            long currentTimeMillis = System.currentTimeMillis();
            Cursor zza = zza(qrVar, new String[]{"rowid", ClientCookie.PATH_ATTR});
            uv uvVar = new uv(null);
            uv uvVar2 = new uv(null);
            while (zza.moveToNext()) {
                long j = zza.getLong(0);
                qr qrVar2 = new qr(zza.getString(1));
                if (!qrVar.zzi(qrVar2)) {
                    wl wlVar = this.zzbZE;
                    String valueOf = String.valueOf(qrVar);
                    String valueOf2 = String.valueOf(qrVar2);
                    wlVar.zze(new StringBuilder(String.valueOf(valueOf).length() + 67 + String.valueOf(valueOf2).length()).append("We are pruning at ").append(valueOf).append(" but we have data stored higher up at ").append(valueOf2).append(". Ignoring.").toString(), (Throwable) null);
                } else {
                    qr zza2 = qr.zza(qrVar, qrVar2);
                    if (ujVar.zzv(zza2)) {
                        uvVar = uvVar.zzb(zza2, Long.valueOf(j));
                    } else if (ujVar.zzw(zza2)) {
                        uvVar2 = uvVar2.zzb(zza2, Long.valueOf(j));
                    } else {
                        wl wlVar2 = this.zzbZE;
                        String valueOf3 = String.valueOf(qrVar);
                        String valueOf4 = String.valueOf(qrVar2);
                        wlVar2.zze(new StringBuilder(String.valueOf(valueOf3).length() + 88 + String.valueOf(valueOf4).length()).append("We are pruning at ").append(valueOf3).append(" and have data at ").append(valueOf4).append(" that isn't marked for pruning or keeping. Ignoring.").toString(), (Throwable) null);
                    }
                }
            }
            int i = 0;
            int i2 = 0;
            if (!uvVar.isEmpty()) {
                ArrayList arrayList = new ArrayList();
                zza(qrVar, qr.zzGZ(), uvVar, uvVar2, ujVar, arrayList);
                Collection values = uvVar.values();
                String valueOf5 = String.valueOf(zzj(values));
                this.zzbZD.delete("serverCache", new StringBuilder(String.valueOf(valueOf5).length() + 11).append("rowid IN (").append(valueOf5).append(h.b).toString(), (String[]) null);
                ArrayList arrayList2 = arrayList;
                int size = arrayList2.size();
                for (int i3 = 0; i3 < size; i3++) {
                    za zaVar = (za) arrayList2.get(i3);
                    zzc(qrVar.zzh((qr) zaVar.getFirst()), (xm) zaVar.zzJG());
                }
                i = values.size();
                i2 = arrayList.size();
            }
            long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
            if (this.zzbZE.zzIH()) {
                this.zzbZE.zzb(String.format("Pruned %d rows with %d nodes resaved in %dms", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Long.valueOf(currentTimeMillis2)}), (Throwable) null, new Object[0]);
            }
        }
    }

    public final void zza(qr qrVar, xm xmVar) {
        zzFw();
        zza(qrVar, xmVar, false);
    }

    public final void zza(qr qrVar, xm xmVar, long j) {
        zzFw();
        long currentTimeMillis = System.currentTimeMillis();
        zza(qrVar, j, "o", zzW(xmVar.getValue(true)));
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(String.format("Persisted user overwrite in %dms", new Object[]{Long.valueOf(currentTimeMillis2)}), (Throwable) null, new Object[0]);
        }
    }

    public final void zza(un unVar) {
        zzFw();
        long currentTimeMillis = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", Long.valueOf(unVar.id));
        contentValues.put(ClientCookie.PATH_ATTR, zzc(unVar.zzcgi.zzFq()));
        contentValues.put("queryParams", unVar.zzcgi.zzIu().zzIr());
        contentValues.put("lastUse", Long.valueOf(unVar.zzcgj));
        contentValues.put("complete", Boolean.valueOf(unVar.complete));
        contentValues.put("active", Boolean.valueOf(unVar.zzbpf));
        this.zzbZD.insertWithOnConflict("trackedQueries", (String) null, contentValues, 5);
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(String.format("Saved new tracked query in %dms", new Object[]{Long.valueOf(currentTimeMillis2)}), (Throwable) null, new Object[0]);
        }
    }

    public final void zzal(long j) {
        zzFw();
        long currentTimeMillis = System.currentTimeMillis();
        int delete = this.zzbZD.delete("writes", "id = ?", new String[]{String.valueOf(j)});
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(String.format("Deleted %d write(s) with writeId %d in %dms", new Object[]{Integer.valueOf(delete), Long.valueOf(j), Long.valueOf(currentTimeMillis2)}), (Throwable) null, new Object[0]);
        }
    }

    public final void zzam(long j) {
        zzFw();
        String valueOf = String.valueOf(j);
        this.zzbZD.delete("trackedQueries", "id = ?", new String[]{valueOf});
        this.zzbZD.delete("trackedKeys", "id = ?", new String[]{valueOf});
    }

    public final void zzan(long j) {
        zzFw();
        long currentTimeMillis = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put("active", false);
        contentValues.put("lastUse", Long.valueOf(j));
        this.zzbZD.updateWithOnConflict("trackedQueries", contentValues, "active = 1", new String[0], 5);
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(String.format("Reset active tracked queries in %dms", new Object[]{Long.valueOf(currentTimeMillis2)}), (Throwable) null, new Object[0]);
        }
    }

    public final Set<wp> zzao(long j) {
        return zze(Collections.singleton(Long.valueOf(j)));
    }

    public final void zzb(qr qrVar, xm xmVar) {
        zzFw();
        zza(qrVar, xmVar, true);
    }

    public final Set<wp> zze(Set<Long> set) {
        long currentTimeMillis = System.currentTimeMillis();
        String valueOf = String.valueOf("id IN (");
        String valueOf2 = String.valueOf(zzj(set));
        Cursor query = this.zzbZD.query(true, "trackedKeys", new String[]{"key"}, new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(valueOf2).length()).append(valueOf).append(valueOf2).append(h.b).toString(), (String[]) null, (String) null, (String) null, (String) null, (String) null);
        HashSet hashSet = new HashSet();
        while (query.moveToNext()) {
            try {
                hashSet.add(wp.zzgT(query.getString(0)));
            } finally {
                query.close();
            }
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(String.format("Loaded %d tracked queries keys for tracked queries %s in %dms", new Object[]{Integer.valueOf(hashSet.size()), set.toString(), Long.valueOf(currentTimeMillis2)}), (Throwable) null, new Object[0]);
        }
        return hashSet;
    }
}
