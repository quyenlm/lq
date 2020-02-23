package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.tagmanager.DataLayer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

final class zzat implements DataLayer.zzc {
    /* access modifiers changed from: private */
    public static final String zzbEn = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' STRING NOT NULL, '%s' BLOB NOT NULL, '%s' INTEGER NOT NULL);", new Object[]{"datalayer", "ID", "key", "value", "expires"});
    /* access modifiers changed from: private */
    public final Context mContext;
    private final Executor zzbEo;
    private zzax zzbEp;
    private int zzbEq;
    private zze zzvw;

    public zzat(Context context) {
        this(context, zzi.zzrY(), "google_tagmanager.db", 2000, Executors.newSingleThreadExecutor());
    }

    private zzat(Context context, zze zze, String str, int i, Executor executor) {
        this.mContext = context;
        this.zzvw = zze;
        this.zzbEq = 2000;
        this.zzbEo = executor;
        this.zzbEp = new zzax(this, this.mContext, str);
    }

    /* access modifiers changed from: private */
    public final List<DataLayer.zza> zzBa() {
        try {
            zzah(this.zzvw.currentTimeMillis());
            List<zzay> zzBb = zzBb();
            ArrayList arrayList = new ArrayList();
            for (zzay next : zzBb) {
                arrayList.add(new DataLayer.zza(next.zzBN, zzt(next.zzbEw)));
            }
            return arrayList;
        } finally {
            zzBd();
        }
    }

    /* JADX INFO: finally extract failed */
    private final List<zzay> zzBb() {
        SQLiteDatabase zzfg = zzfg("Error opening database for loadSerialized.");
        ArrayList arrayList = new ArrayList();
        if (zzfg == null) {
            return arrayList;
        }
        Cursor query = zzfg.query("datalayer", new String[]{"key", "value"}, (String) null, (String[]) null, (String) null, (String) null, "ID", (String) null);
        while (query.moveToNext()) {
            try {
                arrayList.add(new zzay(query.getString(0), query.getBlob(1)));
            } catch (Throwable th) {
                query.close();
                throw th;
            }
        }
        query.close();
        return arrayList;
    }

    private final int zzBc() {
        Cursor cursor = null;
        int i = 0;
        SQLiteDatabase zzfg = zzfg("Error opening database for getNumStoredEntries.");
        if (zzfg != null) {
            try {
                Cursor rawQuery = zzfg.rawQuery("SELECT COUNT(*) from datalayer", (String[]) null);
                if (rawQuery.moveToFirst()) {
                    i = (int) rawQuery.getLong(0);
                }
                if (rawQuery != null) {
                    rawQuery.close();
                }
            } catch (SQLiteException e) {
                zzdj.zzaT("Error getting numStoredEntries");
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }
        return i;
    }

    private final void zzBd() {
        try {
            this.zzbEp.close();
        } catch (SQLiteException e) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x002b A[SYNTHETIC, Splitter:B:18:0x002b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] zzF(java.lang.Object r5) {
        /*
            r0 = 0
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream
            r4.<init>()
            java.io.ObjectOutputStream r1 = new java.io.ObjectOutputStream     // Catch:{ IOException -> 0x0019, all -> 0x0026 }
            r1.<init>(r4)     // Catch:{ IOException -> 0x0019, all -> 0x0026 }
            r1.writeObject(r5)     // Catch:{ IOException -> 0x0038, all -> 0x0034 }
            byte[] r0 = r4.toByteArray()     // Catch:{ IOException -> 0x0038, all -> 0x0034 }
            r1.close()     // Catch:{ IOException -> 0x003a }
            r4.close()     // Catch:{ IOException -> 0x003a }
        L_0x0018:
            return r0
        L_0x0019:
            r1 = move-exception
            r1 = r0
        L_0x001b:
            if (r1 == 0) goto L_0x0020
            r1.close()     // Catch:{ IOException -> 0x0024 }
        L_0x0020:
            r4.close()     // Catch:{ IOException -> 0x0024 }
            goto L_0x0018
        L_0x0024:
            r1 = move-exception
            goto L_0x0018
        L_0x0026:
            r1 = move-exception
            r2 = r1
            r3 = r0
        L_0x0029:
            if (r3 == 0) goto L_0x002e
            r3.close()     // Catch:{ IOException -> 0x0032 }
        L_0x002e:
            r4.close()     // Catch:{ IOException -> 0x0032 }
        L_0x0031:
            throw r2
        L_0x0032:
            r0 = move-exception
            goto L_0x0031
        L_0x0034:
            r0 = move-exception
            r2 = r0
            r3 = r1
            goto L_0x0029
        L_0x0038:
            r2 = move-exception
            goto L_0x001b
        L_0x003a:
            r1 = move-exception
            goto L_0x0018
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzat.zzF(java.lang.Object):byte[]");
    }

    private final void zzah(long j) {
        SQLiteDatabase zzfg = zzfg("Error opening database for deleteOlderThan.");
        if (zzfg != null) {
            try {
                zzdj.v(new StringBuilder(33).append("Deleted ").append(zzfg.delete("datalayer", "expires <= ?", new String[]{Long.toString(j)})).append(" expired items").toString());
            } catch (SQLiteException e) {
                zzdj.zzaT("Error deleting old entries.");
            }
        }
    }

    /* access modifiers changed from: private */
    public final synchronized void zzb(List<zzay> list, long j) {
        long currentTimeMillis;
        String[] strArr;
        try {
            currentTimeMillis = this.zzvw.currentTimeMillis();
            zzah(currentTimeMillis);
            int size = list.size() + (zzBc() - this.zzbEq);
            if (size > 0) {
                List<String> zzby = zzby(size);
                zzdj.zzaS(new StringBuilder(64).append("DataLayer store full, deleting ").append(zzby.size()).append(" entries to make room.").toString());
                strArr = (String[]) zzby.toArray(new String[0]);
                if (!(strArr == null || strArr.length == 0)) {
                    SQLiteDatabase zzfg = zzfg("Error opening database for deleteEntries.");
                    if (zzfg != null) {
                        zzfg.delete("datalayer", String.format("%s in (%s)", new Object[]{"ID", TextUtils.join(",", Collections.nCopies(strArr.length, "?"))}), strArr);
                    }
                }
            }
        } catch (SQLiteException e) {
            String valueOf = String.valueOf(Arrays.toString(strArr));
            zzdj.zzaT(valueOf.length() != 0 ? "Error deleting entries ".concat(valueOf) : new String("Error deleting entries "));
        } catch (Throwable th) {
            zzBd();
            throw th;
        }
        zzc(list, currentTimeMillis + j);
        zzBd();
    }

    private final List<String> zzby(int i) {
        Cursor cursor;
        ArrayList arrayList = new ArrayList();
        if (i <= 0) {
            zzdj.zzaT("Invalid maxEntries specified. Skipping.");
            return arrayList;
        }
        SQLiteDatabase zzfg = zzfg("Error opening database for peekEntryIds.");
        if (zzfg == null) {
            return arrayList;
        }
        try {
            cursor = zzfg.query("datalayer", new String[]{"ID"}, (String) null, (String[]) null, (String) null, (String) null, String.format("%s ASC", new Object[]{"ID"}), Integer.toString(i));
            try {
                if (cursor.moveToFirst()) {
                    do {
                        arrayList.add(String.valueOf(cursor.getLong(0)));
                    } while (cursor.moveToNext());
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (SQLiteException e) {
                e = e;
                try {
                    String valueOf = String.valueOf(e.getMessage());
                    zzdj.zzaT(valueOf.length() != 0 ? "Error in peekEntries fetching entryIds: ".concat(valueOf) : new String("Error in peekEntries fetching entryIds: "));
                    if (cursor != null) {
                        cursor.close();
                    }
                    return arrayList;
                } catch (Throwable th) {
                    th = th;
                }
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursor = null;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return arrayList;
    }

    private final void zzc(List<zzay> list, long j) {
        SQLiteDatabase zzfg = zzfg("Error opening database for writeEntryToDatabase.");
        if (zzfg != null) {
            for (zzay next : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("expires", Long.valueOf(j));
                contentValues.put("key", next.zzBN);
                contentValues.put("value", next.zzbEw);
                zzfg.insert("datalayer", (String) null, contentValues);
            }
        }
    }

    /* access modifiers changed from: private */
    public final void zzff(String str) {
        SQLiteDatabase zzfg = zzfg("Error opening database for clearKeysWithPrefix.");
        if (zzfg != null) {
            try {
                zzdj.v(new StringBuilder(25).append("Cleared ").append(zzfg.delete("datalayer", "key = ? OR key LIKE ?", new String[]{str, String.valueOf(str).concat(".%")})).append(" items").toString());
            } catch (SQLiteException e) {
                String valueOf = String.valueOf(e);
                zzdj.zzaT(new StringBuilder(String.valueOf(str).length() + 44 + String.valueOf(valueOf).length()).append("Error deleting entries with key prefix: ").append(str).append(" (").append(valueOf).append(").").toString());
            } finally {
                zzBd();
            }
        }
    }

    private final SQLiteDatabase zzfg(String str) {
        try {
            return this.zzbEp.getWritableDatabase();
        } catch (SQLiteException e) {
            zzdj.zzaT(str);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0027 A[SYNTHETIC, Splitter:B:18:0x0027] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0035 A[SYNTHETIC, Splitter:B:25:0x0035] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Object zzt(byte[] r5) {
        /*
            r0 = 0
            java.io.ByteArrayInputStream r4 = new java.io.ByteArrayInputStream
            r4.<init>(r5)
            java.io.ObjectInputStream r1 = new java.io.ObjectInputStream     // Catch:{ IOException -> 0x0016, ClassNotFoundException -> 0x0023, all -> 0x0030 }
            r1.<init>(r4)     // Catch:{ IOException -> 0x0016, ClassNotFoundException -> 0x0023, all -> 0x0030 }
            java.lang.Object r0 = r1.readObject()     // Catch:{ IOException -> 0x0044, ClassNotFoundException -> 0x0042, all -> 0x003e }
            r1.close()     // Catch:{ IOException -> 0x0046 }
            r4.close()     // Catch:{ IOException -> 0x0046 }
        L_0x0015:
            return r0
        L_0x0016:
            r1 = move-exception
            r1 = r0
        L_0x0018:
            if (r1 == 0) goto L_0x001d
            r1.close()     // Catch:{ IOException -> 0x0021 }
        L_0x001d:
            r4.close()     // Catch:{ IOException -> 0x0021 }
            goto L_0x0015
        L_0x0021:
            r1 = move-exception
            goto L_0x0015
        L_0x0023:
            r1 = move-exception
            r1 = r0
        L_0x0025:
            if (r1 == 0) goto L_0x002a
            r1.close()     // Catch:{ IOException -> 0x002e }
        L_0x002a:
            r4.close()     // Catch:{ IOException -> 0x002e }
            goto L_0x0015
        L_0x002e:
            r1 = move-exception
            goto L_0x0015
        L_0x0030:
            r1 = move-exception
            r2 = r1
            r3 = r0
        L_0x0033:
            if (r3 == 0) goto L_0x0038
            r3.close()     // Catch:{ IOException -> 0x003c }
        L_0x0038:
            r4.close()     // Catch:{ IOException -> 0x003c }
        L_0x003b:
            throw r2
        L_0x003c:
            r0 = move-exception
            goto L_0x003b
        L_0x003e:
            r0 = move-exception
            r2 = r0
            r3 = r1
            goto L_0x0033
        L_0x0042:
            r2 = move-exception
            goto L_0x0025
        L_0x0044:
            r2 = move-exception
            goto L_0x0018
        L_0x0046:
            r1 = move-exception
            goto L_0x0015
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzat.zzt(byte[]):java.lang.Object");
    }

    public final void zza(zzaq zzaq) {
        this.zzbEo.execute(new zzav(this, zzaq));
    }

    public final void zza(List<DataLayer.zza> list, long j) {
        ArrayList arrayList = new ArrayList();
        for (DataLayer.zza next : list) {
            arrayList.add(new zzay(next.zzBN, zzF(next.mValue)));
        }
        this.zzbEo.execute(new zzau(this, arrayList, j));
    }

    public final void zzfe(String str) {
        this.zzbEo.execute(new zzaw(this, str));
    }
}
