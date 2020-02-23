package com.google.android.gms.internal;

import com.google.firebase.database.DatabaseException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public final class zf {
    private static final Pattern zzcjD = Pattern.compile("[\\[\\]\\.#$]");
    private static final Pattern zzcjE = Pattern.compile("[\\[\\]\\.#\\$\\/\\u0000-\\u001F\\u007F]");

    public static void zzO(qr qrVar) throws DatabaseException {
        wp zzHc = qrVar.zzHc();
        if (!(zzHc == null || !zzHc.asString().startsWith("."))) {
            String valueOf = String.valueOf(qrVar.toString());
            throw new DatabaseException(valueOf.length() != 0 ? "Invalid write location: ".concat(valueOf) : new String("Invalid write location: "));
        }
    }

    public static void zzam(Object obj) {
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (!map.containsKey(".sv")) {
                for (Map.Entry entry : map.entrySet()) {
                    String str = (String) entry.getKey();
                    if (!(str != null && str.length() > 0 && (str.equals(".value") || str.equals(".priority") || (!str.startsWith(".") && !zzcjE.matcher(str).find())))) {
                        throw new DatabaseException(new StringBuilder(String.valueOf(str).length() + 68).append("Invalid key: ").append(str).append(". Keys must not contain '/', '.', '#', '$', '[', or ']'").toString());
                    }
                    zzam(entry.getValue());
                }
            }
        } else if (obj instanceof List) {
            for (Object zzam : (List) obj) {
                zzam(zzam);
            }
        } else if ((obj instanceof Double) || (obj instanceof Float)) {
            double doubleValue = ((Double) obj).doubleValue();
            if (Double.isInfinite(doubleValue) || Double.isNaN(doubleValue)) {
                throw new DatabaseException("Invalid value: Value cannot be NaN, Inf or -Inf.");
            }
        }
    }

    public static Map<qr, xm> zzb(qr qrVar, Map<String, Object> map) throws DatabaseException {
        TreeMap treeMap = new TreeMap();
        for (Map.Entry next : map.entrySet()) {
            qr qrVar2 = new qr((String) next.getKey());
            Object value = next.getValue();
            tn.zza(qrVar.zzh(qrVar2), value);
            String asString = !qrVar2.isEmpty() ? qrVar2.zzHf().asString() : "";
            if (asString.equals(".sv") || asString.equals(".value")) {
                String valueOf = String.valueOf(qrVar2);
                throw new DatabaseException(new StringBuilder(String.valueOf(valueOf).length() + 40 + String.valueOf(asString).length()).append("Path '").append(valueOf).append("' contains disallowed child name: ").append(asString).toString());
            }
            xm zzc = asString.equals(".priority") ? xs.zzc(qrVar2, value) : xp.zza(value, xd.zzJb());
            zzam(value);
            treeMap.put(qrVar2, zzc);
        }
        qr qrVar3 = null;
        Iterator it = treeMap.keySet().iterator();
        while (true) {
            qr qrVar4 = qrVar3;
            if (!it.hasNext()) {
                return treeMap;
            }
            qrVar3 = (qr) it.next();
            zd.zzaF(qrVar4 == null || qrVar4.compareTo(qrVar3) < 0);
            if (qrVar4 != null && qrVar4.zzi(qrVar3)) {
                String valueOf2 = String.valueOf(qrVar4);
                String valueOf3 = String.valueOf(qrVar3);
                throw new DatabaseException(new StringBuilder(String.valueOf(valueOf2).length() + 42 + String.valueOf(valueOf3).length()).append("Path '").append(valueOf2).append("' is an ancestor of '").append(valueOf3).append("' in an update.").toString());
            }
        }
    }

    public static void zzhb(String str) throws DatabaseException {
        if (!(!zzcjD.matcher(str).find())) {
            throw new DatabaseException(new StringBuilder(String.valueOf(str).length() + 101).append("Invalid Firebase Database path: ").append(str).append(". Firebase Database paths must not contain '.', '#', '$', '[', or ']'").toString());
        }
    }

    public static void zzhc(String str) throws DatabaseException {
        if (str.startsWith(".info")) {
            zzhb(str.substring(5));
        } else if (str.startsWith("/.info")) {
            zzhb(str.substring(6));
        } else {
            zzhb(str);
        }
    }

    public static void zzhd(String str) throws DatabaseException {
        if (str != null) {
            if (!(str.equals(".info") || !zzcjE.matcher(str).find())) {
                throw new DatabaseException(new StringBuilder(String.valueOf(str).length() + 68).append("Invalid key: ").append(str).append(". Keys must not contain '/', '.', '#', '$', '[', or ']'").toString());
            }
        }
    }
}
