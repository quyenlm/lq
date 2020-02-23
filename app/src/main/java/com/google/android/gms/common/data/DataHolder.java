package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.beetalk.sdk.ShareConstants;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.internal.zzc;
import com.tencent.tp.a.h;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@KeepName
public final class DataHolder extends com.google.android.gms.common.internal.safeparcel.zza implements Closeable {
    public static final Parcelable.Creator<DataHolder> CREATOR = new zzf();
    private static final zza zzaFI = new zze(new String[0], (String) null);
    private boolean mClosed;
    private final String[] zzaFB;
    private Bundle zzaFC;
    private final CursorWindow[] zzaFD;
    private final Bundle zzaFE;
    private int[] zzaFF;
    int zzaFG;
    private boolean zzaFH;
    private int zzaku;
    private final int zzaxu;

    public static class zza {
        /* access modifiers changed from: private */
        public final String[] zzaFB;
        /* access modifiers changed from: private */
        public final ArrayList<HashMap<String, Object>> zzaFJ;
        private final String zzaFK;
        private final HashMap<Object, Integer> zzaFL;
        private boolean zzaFM;
        private String zzaFN;

        private zza(String[] strArr, String str) {
            this.zzaFB = (String[]) zzbo.zzu(strArr);
            this.zzaFJ = new ArrayList<>();
            this.zzaFK = str;
            this.zzaFL = new HashMap<>();
            this.zzaFM = false;
            this.zzaFN = null;
        }

        /* synthetic */ zza(String[] strArr, String str, zze zze) {
            this(strArr, (String) null);
        }

        public zza zza(ContentValues contentValues) {
            zzc.zzr(contentValues);
            HashMap hashMap = new HashMap(contentValues.size());
            for (Map.Entry next : contentValues.valueSet()) {
                hashMap.put((String) next.getKey(), next.getValue());
            }
            return zza((HashMap<String, Object>) hashMap);
        }

        public zza zza(HashMap<String, Object> hashMap) {
            int intValue;
            zzc.zzr(hashMap);
            if (this.zzaFK == null) {
                intValue = -1;
            } else {
                Object obj = hashMap.get(this.zzaFK);
                if (obj == null) {
                    intValue = -1;
                } else {
                    Integer num = this.zzaFL.get(obj);
                    if (num == null) {
                        this.zzaFL.put(obj, Integer.valueOf(this.zzaFJ.size()));
                        intValue = -1;
                    } else {
                        intValue = num.intValue();
                    }
                }
            }
            if (intValue == -1) {
                this.zzaFJ.add(hashMap);
            } else {
                this.zzaFJ.remove(intValue);
                this.zzaFJ.add(intValue, hashMap);
            }
            this.zzaFM = false;
            return this;
        }

        public final DataHolder zzav(int i) {
            return new DataHolder(this, 0, (Bundle) null, (zze) null);
        }
    }

    public static class zzb extends RuntimeException {
        public zzb(String str) {
            super(str);
        }
    }

    DataHolder(int i, String[] strArr, CursorWindow[] cursorWindowArr, int i2, Bundle bundle) {
        this.mClosed = false;
        this.zzaFH = true;
        this.zzaku = i;
        this.zzaFB = strArr;
        this.zzaFD = cursorWindowArr;
        this.zzaxu = i2;
        this.zzaFE = bundle;
    }

    private DataHolder(zza zza2, int i, Bundle bundle) {
        this(zza2.zzaFB, zza(zza2, -1), i, (Bundle) null);
    }

    /* synthetic */ DataHolder(zza zza2, int i, Bundle bundle, zze zze) {
        this(zza2, 0, (Bundle) null);
    }

    private DataHolder(String[] strArr, CursorWindow[] cursorWindowArr, int i, Bundle bundle) {
        this.mClosed = false;
        this.zzaFH = true;
        this.zzaku = 1;
        this.zzaFB = (String[]) zzbo.zzu(strArr);
        this.zzaFD = (CursorWindow[]) zzbo.zzu(cursorWindowArr);
        this.zzaxu = i;
        this.zzaFE = bundle;
        zzqR();
    }

    public static zza zza(String[] strArr) {
        return new zza(strArr, (String) null, (zze) null);
    }

    private static CursorWindow[] zza(zza zza2, int i) {
        int i2;
        boolean z;
        if (zza2.zzaFB.length == 0) {
            return new CursorWindow[0];
        }
        ArrayList zzb2 = zza2.zzaFJ;
        int size = zzb2.size();
        CursorWindow cursorWindow = new CursorWindow(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(cursorWindow);
        cursorWindow.setNumColumns(zza2.zzaFB.length);
        int i3 = 0;
        boolean z2 = false;
        while (i3 < size) {
            try {
                if (!cursorWindow.allocRow()) {
                    Log.d("DataHolder", new StringBuilder(72).append("Allocating additional cursor window for large data set (row ").append(i3).append(h.b).toString());
                    cursorWindow = new CursorWindow(false);
                    cursorWindow.setStartPosition(i3);
                    cursorWindow.setNumColumns(zza2.zzaFB.length);
                    arrayList.add(cursorWindow);
                    if (!cursorWindow.allocRow()) {
                        Log.e("DataHolder", "Unable to allocate row to hold data.");
                        arrayList.remove(cursorWindow);
                        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
                    }
                }
                Map map = (Map) zzb2.get(i3);
                boolean z3 = true;
                for (int i4 = 0; i4 < zza2.zzaFB.length && z3; i4++) {
                    String str = zza2.zzaFB[i4];
                    Object obj = map.get(str);
                    if (obj == null) {
                        z3 = cursorWindow.putNull(i3, i4);
                    } else if (obj instanceof String) {
                        z3 = cursorWindow.putString((String) obj, i3, i4);
                    } else if (obj instanceof Long) {
                        z3 = cursorWindow.putLong(((Long) obj).longValue(), i3, i4);
                    } else if (obj instanceof Integer) {
                        z3 = cursorWindow.putLong((long) ((Integer) obj).intValue(), i3, i4);
                    } else if (obj instanceof Boolean) {
                        z3 = cursorWindow.putLong(((Boolean) obj).booleanValue() ? 1 : 0, i3, i4);
                    } else if (obj instanceof byte[]) {
                        z3 = cursorWindow.putBlob((byte[]) obj, i3, i4);
                    } else if (obj instanceof Double) {
                        z3 = cursorWindow.putDouble(((Double) obj).doubleValue(), i3, i4);
                    } else if (obj instanceof Float) {
                        z3 = cursorWindow.putDouble((double) ((Float) obj).floatValue(), i3, i4);
                    } else {
                        String valueOf = String.valueOf(obj);
                        throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 32 + String.valueOf(valueOf).length()).append("Unsupported object for column ").append(str).append(": ").append(valueOf).toString());
                    }
                }
                if (z3) {
                    i2 = i3;
                    z = false;
                } else if (z2) {
                    throw new zzb("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
                } else {
                    Log.d("DataHolder", new StringBuilder(74).append("Couldn't populate window data for row ").append(i3).append(" - allocating new window.").toString());
                    cursorWindow.freeLastRow();
                    cursorWindow = new CursorWindow(false);
                    cursorWindow.setStartPosition(i3);
                    cursorWindow.setNumColumns(zza2.zzaFB.length);
                    arrayList.add(cursorWindow);
                    i2 = i3 - 1;
                    z = true;
                }
                i3 = i2 + 1;
                z2 = z;
            } catch (RuntimeException e) {
                RuntimeException runtimeException = e;
                int size2 = arrayList.size();
                for (int i5 = 0; i5 < size2; i5++) {
                    ((CursorWindow) arrayList.get(i5)).close();
                }
                throw runtimeException;
            }
        }
        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
    }

    public static DataHolder zzau(int i) {
        return new DataHolder(zzaFI, i, (Bundle) null);
    }

    private final void zzh(String str, int i) {
        if (this.zzaFC == null || !this.zzaFC.containsKey(str)) {
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? "No such column: ".concat(valueOf) : new String("No such column: "));
        } else if (isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        } else if (i < 0 || i >= this.zzaFG) {
            throw new CursorIndexOutOfBoundsException(i, this.zzaFG);
        }
    }

    public final void close() {
        synchronized (this) {
            if (!this.mClosed) {
                this.mClosed = true;
                for (CursorWindow close : this.zzaFD) {
                    close.close();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void finalize() throws Throwable {
        try {
            if (this.zzaFH && this.zzaFD.length > 0 && !isClosed()) {
                close();
                String valueOf = String.valueOf(toString());
                Log.e("DataBuffer", new StringBuilder(String.valueOf(valueOf).length() + ShareConstants.ERROR_CODE.GG_RESULT_NETWORK_ERROR).append("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (internal object: ").append(valueOf).append(h.b).toString());
            }
        } finally {
            super.finalize();
        }
    }

    public final int getCount() {
        return this.zzaFG;
    }

    public final int getStatusCode() {
        return this.zzaxu;
    }

    public final boolean isClosed() {
        boolean z;
        synchronized (this) {
            z = this.mClosed;
        }
        return z;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzaFB, false);
        zzd.zza(parcel, 2, (T[]) this.zzaFD, i, false);
        zzd.zzc(parcel, 3, this.zzaxu);
        zzd.zza(parcel, 4, this.zzaFE, false);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zzI(parcel, zze);
    }

    public final void zza(String str, int i, int i2, CharArrayBuffer charArrayBuffer) {
        zzh(str, i);
        this.zzaFD[i2].copyStringToBuffer(i, this.zzaFC.getInt(str), charArrayBuffer);
    }

    public final int zzat(int i) {
        int i2 = 0;
        zzbo.zzae(i >= 0 && i < this.zzaFG);
        while (true) {
            if (i2 >= this.zzaFF.length) {
                break;
            } else if (i < this.zzaFF[i2]) {
                i2--;
                break;
            } else {
                i2++;
            }
        }
        return i2 == this.zzaFF.length ? i2 - 1 : i2;
    }

    public final long zzb(String str, int i, int i2) {
        zzh(str, i);
        return this.zzaFD[i2].getLong(i, this.zzaFC.getInt(str));
    }

    public final int zzc(String str, int i, int i2) {
        zzh(str, i);
        return this.zzaFD[i2].getInt(i, this.zzaFC.getInt(str));
    }

    public final boolean zzcv(String str) {
        return this.zzaFC.containsKey(str);
    }

    public final String zzd(String str, int i, int i2) {
        zzh(str, i);
        return this.zzaFD[i2].getString(i, this.zzaFC.getInt(str));
    }

    public final boolean zze(String str, int i, int i2) {
        zzh(str, i);
        return Long.valueOf(this.zzaFD[i2].getLong(i, this.zzaFC.getInt(str))).longValue() == 1;
    }

    public final float zzf(String str, int i, int i2) {
        zzh(str, i);
        return this.zzaFD[i2].getFloat(i, this.zzaFC.getInt(str));
    }

    public final byte[] zzg(String str, int i, int i2) {
        zzh(str, i);
        return this.zzaFD[i2].getBlob(i, this.zzaFC.getInt(str));
    }

    public final boolean zzh(String str, int i, int i2) {
        zzh(str, i);
        return this.zzaFD[i2].isNull(i, this.zzaFC.getInt(str));
    }

    public final Bundle zzqN() {
        return this.zzaFE;
    }

    public final void zzqR() {
        this.zzaFC = new Bundle();
        for (int i = 0; i < this.zzaFB.length; i++) {
            this.zzaFC.putInt(this.zzaFB[i], i);
        }
        this.zzaFF = new int[this.zzaFD.length];
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzaFD.length; i3++) {
            this.zzaFF[i3] = i2;
            i2 += this.zzaFD[i3].getNumRows() - (i2 - this.zzaFD[i3].getStartPosition());
        }
        this.zzaFG = i2;
    }
}
