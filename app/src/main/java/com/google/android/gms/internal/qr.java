package com.google.android.gms.internal;

import com.appsflyer.share.Constants;
import com.google.firebase.database.DatabaseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class qr implements Comparable<qr>, Iterable<wp> {
    private static final qr zzcdh = new qr("");
    /* access modifiers changed from: private */
    public final int end;
    /* access modifiers changed from: private */
    public final int start;
    /* access modifiers changed from: private */
    public final wp[] zzcdg;

    public qr(String str) {
        int i;
        String[] split = str.split(Constants.URL_PATH_DELIMITER);
        int i2 = 0;
        for (String length : split) {
            if (length.length() > 0) {
                i2++;
            }
        }
        this.zzcdg = new wp[i2];
        int length2 = split.length;
        int i3 = 0;
        int i4 = 0;
        while (i3 < length2) {
            String str2 = split[i3];
            if (str2.length() > 0) {
                i = i4 + 1;
                this.zzcdg[i4] = wp.zzgT(str2);
            } else {
                i = i4;
            }
            i3++;
            i4 = i;
        }
        this.start = 0;
        this.end = this.zzcdg.length;
    }

    public qr(List<String> list) {
        this.zzcdg = new wp[list.size()];
        int i = 0;
        for (String zzgT : list) {
            this.zzcdg[i] = wp.zzgT(zzgT);
            i++;
        }
        this.start = 0;
        this.end = list.size();
    }

    public qr(wp... wpVarArr) {
        this.zzcdg = (wp[]) Arrays.copyOf(wpVarArr, wpVarArr.length);
        this.start = 0;
        this.end = wpVarArr.length;
    }

    private qr(wp[] wpVarArr, int i, int i2) {
        this.zzcdg = wpVarArr;
        this.start = i;
        this.end = i2;
    }

    public static qr zzGZ() {
        return zzcdh;
    }

    public static qr zza(qr qrVar, qr qrVar2) {
        while (true) {
            wp zzHc = qrVar.zzHc();
            wp zzHc2 = qrVar2.zzHc();
            if (zzHc == null) {
                return qrVar2;
            }
            if (zzHc.equals(zzHc2)) {
                qrVar = qrVar.zzHd();
                qrVar2 = qrVar2.zzHd();
            } else {
                String valueOf = String.valueOf(qrVar2);
                String valueOf2 = String.valueOf(qrVar);
                throw new DatabaseException(new StringBuilder(String.valueOf(valueOf).length() + 37 + String.valueOf(valueOf2).length()).append("INTERNAL ERROR: ").append(valueOf).append(" is not contained in ").append(valueOf2).toString());
            }
        }
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof qr)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        qr qrVar = (qr) obj;
        if (size() != qrVar.size()) {
            return false;
        }
        int i = this.start;
        int i2 = qrVar.start;
        while (i < this.end && i2 < qrVar.end) {
            if (!this.zzcdg[i].equals(qrVar.zzcdg[i2])) {
                return false;
            }
            i++;
            i2++;
        }
        return true;
    }

    public final int hashCode() {
        int i = 0;
        for (int i2 = this.start; i2 < this.end; i2++) {
            i = (i * 37) + this.zzcdg[i2].hashCode();
        }
        return i;
    }

    public final boolean isEmpty() {
        return this.start >= this.end;
    }

    public final Iterator<wp> iterator() {
        return new qs(this);
    }

    public final int size() {
        return this.end - this.start;
    }

    public final String toString() {
        if (isEmpty()) {
            return Constants.URL_PATH_DELIMITER;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = this.start; i < this.end; i++) {
            sb.append(Constants.URL_PATH_DELIMITER);
            sb.append(this.zzcdg[i].asString());
        }
        return sb.toString();
    }

    public final String zzHa() {
        if (isEmpty()) {
            return Constants.URL_PATH_DELIMITER;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = this.start; i < this.end; i++) {
            if (i > this.start) {
                sb.append(Constants.URL_PATH_DELIMITER);
            }
            sb.append(this.zzcdg[i].asString());
        }
        return sb.toString();
    }

    public final List<String> zzHb() {
        ArrayList arrayList = new ArrayList(size());
        Iterator<wp> it = iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().asString());
        }
        return arrayList;
    }

    public final wp zzHc() {
        if (isEmpty()) {
            return null;
        }
        return this.zzcdg[this.start];
    }

    public final qr zzHd() {
        int i = this.start;
        if (!isEmpty()) {
            i++;
        }
        return new qr(this.zzcdg, i, this.end);
    }

    public final qr zzHe() {
        if (isEmpty()) {
            return null;
        }
        return new qr(this.zzcdg, this.start, this.end - 1);
    }

    public final wp zzHf() {
        if (!isEmpty()) {
            return this.zzcdg[this.end - 1];
        }
        return null;
    }

    public final qr zza(wp wpVar) {
        int size = size();
        wp[] wpVarArr = new wp[(size + 1)];
        System.arraycopy(this.zzcdg, this.start, wpVarArr, 0, size);
        wpVarArr[size] = wpVar;
        return new qr(wpVarArr, 0, size + 1);
    }

    public final qr zzh(qr qrVar) {
        int size = size() + qrVar.size();
        wp[] wpVarArr = new wp[size];
        System.arraycopy(this.zzcdg, this.start, wpVarArr, 0, size());
        System.arraycopy(qrVar.zzcdg, qrVar.start, wpVarArr, size(), qrVar.size());
        return new qr(wpVarArr, 0, size);
    }

    public final boolean zzi(qr qrVar) {
        if (size() > qrVar.size()) {
            return false;
        }
        int i = this.start;
        int i2 = qrVar.start;
        while (i < this.end) {
            if (!this.zzcdg[i].equals(qrVar.zzcdg[i2])) {
                return false;
            }
            i++;
            i2++;
        }
        return true;
    }

    /* renamed from: zzj */
    public final int compareTo(qr qrVar) {
        int i = this.start;
        int i2 = qrVar.start;
        while (i < this.end && i2 < qrVar.end) {
            int zzi = this.zzcdg[i].compareTo(qrVar.zzcdg[i2]);
            if (zzi != 0) {
                return zzi;
            }
            i++;
            i2++;
        }
        if (i == this.end && i2 == qrVar.end) {
            return 0;
        }
        return i == this.end ? -1 : 1;
    }
}
