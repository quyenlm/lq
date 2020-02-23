package com.google.android.gms.internal;

import android.util.Base64;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.net.URI;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

final class yk {
    private String protocol = null;
    private URI zzciP = null;
    private String zzcje = null;
    private Map<String, String> zzcjf = null;

    public yk(URI uri, String str, Map<String, String> map) {
        this.zzciP = uri;
        this.protocol = null;
        this.zzcjf = map;
        this.zzcje = zzJw();
    }

    private static String zzJw() {
        byte[] bArr = new byte[16];
        for (int i = 0; i < 16; i++) {
            bArr[i] = (byte) ((int) ((Math.random() * 255.0d) + FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE));
        }
        return Base64.encodeToString(bArr, 2);
    }

    private static String zza(LinkedHashMap<String, String> linkedHashMap) {
        String str = new String();
        Iterator<String> it = linkedHashMap.keySet().iterator();
        while (true) {
            String str2 = str;
            if (!it.hasNext()) {
                return str2;
            }
            String next = it.next();
            String valueOf = String.valueOf(str2);
            String str3 = linkedHashMap.get(next);
            str = new StringBuilder(String.valueOf(valueOf).length() + 4 + String.valueOf(next).length() + String.valueOf(str3).length()).append(valueOf).append(next).append(": ").append(str3).append("\r\n").toString();
        }
    }

    public final byte[] zzJv() {
        String concat;
        String path = this.zzciP.getPath();
        String query = this.zzciP.getQuery();
        String valueOf = String.valueOf(path);
        if (query == null) {
            concat = "";
        } else {
            String valueOf2 = String.valueOf(query);
            concat = valueOf2.length() != 0 ? "?".concat(valueOf2) : new String("?");
        }
        String valueOf3 = String.valueOf(concat);
        String concat2 = valueOf3.length() != 0 ? valueOf.concat(valueOf3) : new String(valueOf);
        String host = this.zzciP.getHost();
        if (this.zzciP.getPort() != -1) {
            String valueOf4 = String.valueOf(host);
            host = new StringBuilder(String.valueOf(valueOf4).length() + 12).append(valueOf4).append(":").append(this.zzciP.getPort()).toString();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("Host", host);
        linkedHashMap.put("Upgrade", "websocket");
        linkedHashMap.put("Connection", "Upgrade");
        linkedHashMap.put("Sec-WebSocket-Version", "13");
        linkedHashMap.put("Sec-WebSocket-Key", this.zzcje);
        if (this.protocol != null) {
            linkedHashMap.put("Sec-WebSocket-Protocol", this.protocol);
        }
        if (this.zzcjf != null) {
            for (String next : this.zzcjf.keySet()) {
                if (!linkedHashMap.containsKey(next)) {
                    linkedHashMap.put(next, this.zzcjf.get(next));
                }
            }
        }
        String valueOf5 = String.valueOf(new StringBuilder(String.valueOf(concat2).length() + 15).append("GET ").append(concat2).append(" HTTP/1.1\r\n").toString());
        String valueOf6 = String.valueOf(zza(linkedHashMap));
        String concat3 = String.valueOf(valueOf6.length() != 0 ? valueOf5.concat(valueOf6) : new String(valueOf5)).concat("\r\n");
        byte[] bArr = new byte[concat3.getBytes().length];
        System.arraycopy(concat3.getBytes(), 0, bArr, 0, concat3.getBytes().length);
        return bArr;
    }
}
