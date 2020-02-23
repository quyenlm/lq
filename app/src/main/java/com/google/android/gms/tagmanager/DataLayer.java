package com.google.android.gms.tagmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLayer {
    public static final String EVENT_KEY = "event";
    public static final Object OBJECT_NOT_PRESENT = new Object();
    private static String[] zzbEd = "gtm.lifetime".toString().split("\\.");
    private static final Pattern zzbEe = Pattern.compile("(\\d+)\\s*([smhd]?)");
    private final ConcurrentHashMap<zzb, Integer> zzbEf;
    private final Map<String, Object> zzbEg;
    private final ReentrantLock zzbEh;
    private final LinkedList<Map<String, Object>> zzbEi;
    private final zzc zzbEj;
    /* access modifiers changed from: private */
    public final CountDownLatch zzbEk;

    static final class zza {
        public final Object mValue;
        public final String zzBN;

        zza(String str, Object obj) {
            this.zzBN = str;
            this.mValue = obj;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zza = (zza) obj;
            return this.zzBN.equals(zza.zzBN) && this.mValue.equals(zza.mValue);
        }

        public final int hashCode() {
            return Arrays.hashCode(new Integer[]{Integer.valueOf(this.zzBN.hashCode()), Integer.valueOf(this.mValue.hashCode())});
        }

        public final String toString() {
            String str = this.zzBN;
            String valueOf = String.valueOf(this.mValue.toString());
            return new StringBuilder(String.valueOf(str).length() + 13 + String.valueOf(valueOf).length()).append("Key: ").append(str).append(" value: ").append(valueOf).toString();
        }
    }

    interface zzb {
        void zzp(Map<String, Object> map);
    }

    interface zzc {
        void zza(zzaq zzaq);

        void zza(List<zza> list, long j);

        void zzfe(String str);
    }

    DataLayer() {
        this(new zzao());
    }

    DataLayer(zzc zzc2) {
        this.zzbEj = zzc2;
        this.zzbEf = new ConcurrentHashMap<>();
        this.zzbEg = new HashMap();
        this.zzbEh = new ReentrantLock();
        this.zzbEi = new LinkedList<>();
        this.zzbEk = new CountDownLatch(1);
        this.zzbEj.zza(new zzap(this));
    }

    public static List<Object> listOf(Object... objArr) {
        ArrayList arrayList = new ArrayList();
        for (Object add : objArr) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public static Map<String, Object> mapOf(Object... objArr) {
        if (objArr.length % 2 != 0) {
            throw new IllegalArgumentException("expected even number of key-value pairs");
        }
        HashMap hashMap = new HashMap();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= objArr.length) {
                return hashMap;
            }
            if (!(objArr[i2] instanceof String)) {
                String valueOf = String.valueOf(objArr[i2]);
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 21).append("key is not a string: ").append(valueOf).toString());
            }
            hashMap.put(objArr[i2], objArr[i2 + 1]);
            i = i2 + 2;
        }
    }

    private final void zza(Map<String, Object> map, String str, Collection<zza> collection) {
        for (Map.Entry next : map.entrySet()) {
            String str2 = str.length() == 0 ? "" : ".";
            String str3 = (String) next.getKey();
            String sb = new StringBuilder(String.valueOf(str).length() + String.valueOf(str2).length() + String.valueOf(str3).length()).append(str).append(str2).append(str3).toString();
            if (next.getValue() instanceof Map) {
                zza((Map) next.getValue(), sb, collection);
            } else if (!sb.equals("gtm.lifetime")) {
                collection.add(new zza(sb, next.getValue()));
            }
        }
    }

    private final void zzb(List<Object> list, List<Object> list2) {
        while (list2.size() < list.size()) {
            list2.add((Object) null);
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < list.size()) {
                Object obj = list.get(i2);
                if (obj instanceof List) {
                    if (!(list2.get(i2) instanceof List)) {
                        list2.set(i2, new ArrayList());
                    }
                    zzb((List) obj, (List) list2.get(i2));
                } else if (obj instanceof Map) {
                    if (!(list2.get(i2) instanceof Map)) {
                        list2.set(i2, new HashMap());
                    }
                    zzd((Map) obj, (Map) list2.get(i2));
                } else if (obj != OBJECT_NOT_PRESENT) {
                    list2.set(i2, obj);
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    private final void zzd(Map<String, Object> map, Map<String, Object> map2) {
        for (String next : map.keySet()) {
            Object obj = map.get(next);
            if (obj instanceof List) {
                if (!(map2.get(next) instanceof List)) {
                    map2.put(next, new ArrayList());
                }
                zzb((List) obj, (List) map2.get(next));
            } else if (obj instanceof Map) {
                if (!(map2.get(next) instanceof Map)) {
                    map2.put(next, new HashMap());
                }
                zzd((Map) obj, (Map) map2.get(next));
            } else {
                map2.put(next, obj);
            }
        }
    }

    private static Long zzfd(String str) {
        long j;
        Matcher matcher = zzbEe.matcher(str);
        if (!matcher.matches()) {
            String valueOf = String.valueOf(str);
            zzdj.zzaS(valueOf.length() != 0 ? "unknown _lifetime: ".concat(valueOf) : new String("unknown _lifetime: "));
            return null;
        }
        try {
            j = Long.parseLong(matcher.group(1));
        } catch (NumberFormatException e) {
            String valueOf2 = String.valueOf(str);
            zzdj.zzaT(valueOf2.length() != 0 ? "illegal number in _lifetime value: ".concat(valueOf2) : new String("illegal number in _lifetime value: "));
            j = 0;
        }
        if (j <= 0) {
            String valueOf3 = String.valueOf(str);
            zzdj.zzaS(valueOf3.length() != 0 ? "non-positive _lifetime: ".concat(valueOf3) : new String("non-positive _lifetime: "));
            return null;
        }
        String group = matcher.group(2);
        if (group.length() == 0) {
            return Long.valueOf(j);
        }
        switch (group.charAt(0)) {
            case 'd':
                return Long.valueOf(j * 1000 * 60 * 60 * 24);
            case 'h':
                return Long.valueOf(j * 1000 * 60 * 60);
            case 'm':
                return Long.valueOf(j * 1000 * 60);
            case 's':
                return Long.valueOf(j * 1000);
            default:
                String valueOf4 = String.valueOf(str);
                zzdj.zzaT(valueOf4.length() != 0 ? "unknown units in _lifetime: ".concat(valueOf4) : new String("unknown units in _lifetime: "));
                return null;
        }
    }

    static Map<String, Object> zzn(String str, Object obj) {
        HashMap hashMap = new HashMap();
        String[] split = str.toString().split("\\.");
        int i = 0;
        HashMap hashMap2 = hashMap;
        while (i < split.length - 1) {
            HashMap hashMap3 = new HashMap();
            hashMap2.put(split[i], hashMap3);
            i++;
            hashMap2 = hashMap3;
        }
        hashMap2.put(split[split.length - 1], obj);
        return hashMap;
    }

    /* access modifiers changed from: private */
    public final void zzr(Map<String, Object> map) {
        this.zzbEh.lock();
        try {
            this.zzbEi.offer(map);
            if (this.zzbEh.getHoldCount() == 1) {
                int i = 0;
                while (true) {
                    int i2 = i;
                    Map poll = this.zzbEi.poll();
                    if (poll == null) {
                        break;
                    }
                    synchronized (this.zzbEg) {
                        for (String str : poll.keySet()) {
                            zzd(zzn(str, poll.get(str)), this.zzbEg);
                        }
                    }
                    for (zzb zzp : this.zzbEf.keySet()) {
                        zzp.zzp(poll);
                    }
                    i = i2 + 1;
                    if (i > 500) {
                        this.zzbEi.clear();
                        throw new RuntimeException("Seems like an infinite loop of pushing to the data layer");
                    }
                }
            }
            Object zzs = zzs(map);
            Long zzfd = zzs == null ? null : zzfd(zzs.toString());
            if (zzfd != null) {
                ArrayList arrayList = new ArrayList();
                zza(map, "", arrayList);
                this.zzbEj.zza(arrayList, zzfd.longValue());
            }
        } finally {
            this.zzbEh.unlock();
        }
    }

    private static Object zzs(Map<String, Object> map) {
        Object obj = map;
        for (String str : zzbEd) {
            if (!(obj instanceof Map)) {
                return null;
            }
            obj = ((Map) obj).get(str);
        }
        return obj;
    }

    public Object get(String str) {
        synchronized (this.zzbEg) {
            Object obj = this.zzbEg;
            for (String str2 : str.split("\\.")) {
                if (!(obj instanceof Map)) {
                    return null;
                }
                obj = ((Map) obj).get(str2);
                if (obj == null) {
                    return null;
                }
            }
            return obj;
        }
    }

    public void push(String str, Object obj) {
        push(zzn(str, obj));
    }

    public void push(Map<String, Object> map) {
        try {
            this.zzbEk.await();
        } catch (InterruptedException e) {
            zzdj.zzaT("DataLayer.push: unexpected InterruptedException");
        }
        zzr(map);
    }

    public void pushEvent(String str, Map<String, Object> map) {
        HashMap hashMap = new HashMap(map);
        hashMap.put("event", str);
        push(hashMap);
    }

    public String toString() {
        String sb;
        synchronized (this.zzbEg) {
            StringBuilder sb2 = new StringBuilder();
            for (Map.Entry next : this.zzbEg.entrySet()) {
                sb2.append(String.format("{\n\tKey: %s\n\tValue: %s\n}\n", new Object[]{next.getKey(), next.getValue()}));
            }
            sb = sb2.toString();
        }
        return sb;
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzb zzb2) {
        this.zzbEf.put(zzb2, 0);
    }

    /* access modifiers changed from: package-private */
    public final void zzfc(String str) {
        push(str, (Object) null);
        this.zzbEj.zzfe(str);
    }
}
