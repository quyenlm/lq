package com.google.android.gms.internal;

import com.appsflyer.share.Constants;
import com.google.firebase.database.DatabaseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class tn {
    private final List<String> zzcfp = new ArrayList();
    private int zzcfq = 0;

    private tn(qr qrVar) throws DatabaseException {
        Iterator<wp> it = qrVar.iterator();
        while (it.hasNext()) {
            this.zzcfp.add(it.next().asString());
        }
        this.zzcfq = Math.max(1, this.zzcfp.size());
        for (int i = 0; i < this.zzcfp.size(); i++) {
            this.zzcfq = zza(this.zzcfp.get(i)) + this.zzcfq;
        }
        zzHy();
    }

    private final String zzHx() {
        String remove = this.zzcfp.remove(this.zzcfp.size() - 1);
        this.zzcfq -= zza(remove);
        if (this.zzcfp.size() > 0) {
            this.zzcfq--;
        }
        return remove;
    }

    private final void zzHy() throws DatabaseException {
        String sb;
        if (this.zzcfq > 768) {
            String valueOf = String.valueOf("Data has a key path longer than 768 bytes (");
            throw new DatabaseException(new StringBuilder(String.valueOf(valueOf).length() + 13).append(valueOf).append(this.zzcfq).append(").").toString());
        } else if (this.zzcfp.size() > 32) {
            String valueOf2 = String.valueOf("Path specified exceeds the maximum depth that can be written (32) or object contains a cycle ");
            if (this.zzcfp.size() == 0) {
                sb = "";
            } else {
                String valueOf3 = String.valueOf(zze(Constants.URL_PATH_DELIMITER, this.zzcfp));
                sb = new StringBuilder(String.valueOf(valueOf3).length() + 10).append("in path '").append(valueOf3).append("'").toString();
            }
            String valueOf4 = String.valueOf(sb);
            throw new DatabaseException(valueOf4.length() != 0 ? valueOf2.concat(valueOf4) : new String(valueOf2));
        }
    }

    private static int zza(CharSequence charSequence) {
        int i;
        int length = charSequence.length();
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            char charAt = charSequence.charAt(i2);
            if (charAt <= 127) {
                i = i3 + 1;
            } else if (charAt <= 2047) {
                i = i3 + 2;
            } else if (Character.isHighSurrogate(charAt)) {
                i = i3 + 4;
                i2++;
            } else {
                i = i3 + 3;
            }
            i2++;
            i3 = i;
        }
        return i3;
    }

    public static void zza(qr qrVar, Object obj) throws DatabaseException {
        new tn(qrVar).zzai(obj);
    }

    private final void zzai(Object obj) throws DatabaseException {
        if (obj instanceof Map) {
            Map map = (Map) obj;
            for (String str : map.keySet()) {
                if (!str.startsWith(".")) {
                    zzgS(str);
                    zzai(map.get(str));
                    zzHx();
                }
            }
        } else if (obj instanceof List) {
            List list = (List) obj;
            for (int i = 0; i < list.size(); i++) {
                zzgS(Integer.toString(i));
                zzai(list.get(i));
                zzHx();
            }
        }
    }

    private static String zze(String str, List<String> list) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= list.size()) {
                return sb.toString();
            }
            if (i2 > 0) {
                sb.append(str);
            }
            sb.append(list.get(i2));
            i = i2 + 1;
        }
    }

    private final void zzgS(String str) throws DatabaseException {
        if (this.zzcfp.size() > 0) {
            this.zzcfq++;
        }
        this.zzcfp.add(str);
        this.zzcfq += zza(str);
        zzHy();
    }
}
