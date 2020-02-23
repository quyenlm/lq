package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.zzbs;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@zzzn
public final class zzaji {
    public static <A, B> zzajm<B> zza(zzajm<A> zzajm, zzajl<A, B> zzajl) {
        zzajg zzajg = new zzajg();
        zzajm.zzc(new zzajj(zzajg, zzajl, zzajm));
        return zzajg;
    }

    public static <T> T zza(Future<T> future, T t) {
        try {
            return future.get(((Long) zzbs.zzbL().zzd(zzmo.zzEJ)).longValue(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            future.cancel(true);
            zzafr.zzc("InterruptedException caught while resolving future.", e);
            Thread.currentThread().interrupt();
            zzbs.zzbD().zza((Throwable) e, "Futures.resolveFuture");
            return t;
        } catch (Exception e2) {
            future.cancel(true);
            zzafr.zzb("Error waiting for future.", e2);
            zzbs.zzbD().zza((Throwable) e2, "Futures.resolveFuture");
            return t;
        }
    }

    public static <T> T zza(Future<T> future, T t, long j, TimeUnit timeUnit) {
        try {
            return future.get(j, timeUnit);
        } catch (InterruptedException e) {
            future.cancel(true);
            zzafr.zzc("InterruptedException caught while resolving future.", e);
            Thread.currentThread().interrupt();
            zzbs.zzbD().zza((Throwable) e, "Futures.resolveFuture");
            return t;
        } catch (Exception e2) {
            future.cancel(true);
            zzafr.zzb("Error waiting for future.", e2);
            zzbs.zzbD().zza((Throwable) e2, "Futures.resolveFuture");
            return t;
        }
    }

    public static <V> zzajm<List<V>> zzp(List<zzajm<V>> list) {
        zzajg zzajg = new zzajg();
        int size = list.size();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (zzajm<V> zzc : list) {
            zzc.zzc(new zzajk(atomicInteger, size, zzajg, list));
        }
        return zzajg;
    }

    /* access modifiers changed from: private */
    public static <V> List<V> zzq(List<zzajm<V>> list) throws ExecutionException, InterruptedException {
        ArrayList arrayList = new ArrayList();
        for (zzajm<V> zzajm : list) {
            Object obj = zzajm.get();
            if (obj != null) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }
}
