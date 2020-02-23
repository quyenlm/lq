package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.tencent.imsdk.expansion.downloader.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

public final class zzchl extends zzchj {
    protected zzchy zzbto;
    private AppMeasurement.EventInterceptor zzbtp;
    private final Set<AppMeasurement.OnEventListener> zzbtq = new CopyOnWriteArraySet();
    private boolean zzbtr;
    private final AtomicReference<String> zzbts = new AtomicReference<>();

    protected zzchl(zzcgl zzcgl) {
        super(zzcgl);
    }

    public static int getMaxUserProperties(String str) {
        zzbo.zzcF(str);
        return zzcem.zzxu();
    }

    private final void zza(AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        long currentTimeMillis = super.zzkq().currentTimeMillis();
        zzbo.zzu(conditionalUserProperty);
        zzbo.zzcF(conditionalUserProperty.mName);
        zzbo.zzcF(conditionalUserProperty.mOrigin);
        zzbo.zzu(conditionalUserProperty.mValue);
        conditionalUserProperty.mCreationTimestamp = currentTimeMillis;
        String str = conditionalUserProperty.mName;
        Object obj = conditionalUserProperty.mValue;
        if (super.zzwB().zzes(str) != 0) {
            super.zzwF().zzyx().zzj("Invalid conditional user property name", super.zzwA().zzdY(str));
        } else if (super.zzwB().zzl(str, obj) != 0) {
            super.zzwF().zzyx().zze("Invalid conditional user property value", super.zzwA().zzdY(str), obj);
        } else {
            Object zzm = super.zzwB().zzm(str, obj);
            if (zzm == null) {
                super.zzwF().zzyx().zze("Unable to normalize conditional user property value", super.zzwA().zzdY(str), obj);
                return;
            }
            conditionalUserProperty.mValue = zzm;
            long j = conditionalUserProperty.mTriggerTimeout;
            if (TextUtils.isEmpty(conditionalUserProperty.mTriggerEventName) || (j <= zzcem.zzxw() && j >= 1)) {
                long j2 = conditionalUserProperty.mTimeToLive;
                if (j2 > zzcem.zzxx() || j2 < 1) {
                    super.zzwF().zzyx().zze("Invalid conditional user property time to live", super.zzwA().zzdY(str), Long.valueOf(j2));
                } else {
                    super.zzwE().zzj(new zzchn(this, conditionalUserProperty));
                }
            } else {
                super.zzwF().zzyx().zze("Invalid conditional user property timeout", super.zzwA().zzdY(str), Long.valueOf(j));
            }
        }
    }

    private final void zza(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        Bundle bundle2;
        if (bundle == null) {
            bundle2 = new Bundle();
        } else {
            bundle2 = new Bundle(bundle);
            for (String str4 : bundle2.keySet()) {
                Object obj = bundle2.get(str4);
                if (obj instanceof Bundle) {
                    bundle2.putBundle(str4, new Bundle((Bundle) obj));
                } else if (obj instanceof Parcelable[]) {
                    Parcelable[] parcelableArr = (Parcelable[]) obj;
                    int i = 0;
                    while (true) {
                        int i2 = i;
                        if (i2 >= parcelableArr.length) {
                            break;
                        }
                        if (parcelableArr[i2] instanceof Bundle) {
                            parcelableArr[i2] = new Bundle((Bundle) parcelableArr[i2]);
                        }
                        i = i2 + 1;
                    }
                } else if (obj instanceof ArrayList) {
                    ArrayList arrayList = (ArrayList) obj;
                    int i3 = 0;
                    while (true) {
                        int i4 = i3;
                        if (i4 >= arrayList.size()) {
                            break;
                        }
                        Object obj2 = arrayList.get(i4);
                        if (obj2 instanceof Bundle) {
                            arrayList.set(i4, new Bundle((Bundle) obj2));
                        }
                        i3 = i4 + 1;
                    }
                }
            }
        }
        super.zzwE().zzj(new zzcht(this, str, str2, j, bundle2, z, z2, z3, str3));
    }

    private final void zza(String str, String str2, long j, Object obj) {
        super.zzwE().zzj(new zzchu(this, str, str2, obj, j));
    }

    private final void zza(String str, String str2, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        zza(str, str2, super.zzkq().currentTimeMillis(), bundle, true, z2, z3, (String) null);
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zza(String str, String str2, Object obj, long j) {
        zzbo.zzcF(str);
        zzbo.zzcF(str2);
        super.zzjC();
        super.zzwp();
        zzkD();
        if (!this.zzboe.isEnabled()) {
            super.zzwF().zzyC().log("User property not set since app measurement is disabled");
        } else if (this.zzboe.zzyP()) {
            super.zzwF().zzyC().zze("Setting user property (FE)", super.zzwA().zzdW(str2), obj);
            super.zzww().zzb(new zzcji(str2, j, obj, str));
        }
    }

    private final void zza(String str, String str2, String str3, Bundle bundle) {
        long currentTimeMillis = super.zzkq().currentTimeMillis();
        zzbo.zzcF(str2);
        AppMeasurement.ConditionalUserProperty conditionalUserProperty = new AppMeasurement.ConditionalUserProperty();
        conditionalUserProperty.mAppId = str;
        conditionalUserProperty.mName = str2;
        conditionalUserProperty.mCreationTimestamp = currentTimeMillis;
        if (str3 != null) {
            conditionalUserProperty.mExpiredEventName = str3;
            conditionalUserProperty.mExpiredEventParams = bundle;
        }
        super.zzwE().zzj(new zzcho(this, conditionalUserProperty));
    }

    @Nullable
    private final String zzad(long j) {
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            super.zzwE().zzj(new zzchx(this, atomicReference));
            try {
                atomicReference.wait(j);
            } catch (InterruptedException e) {
                super.zzwF().zzyz().log("Interrupted waiting for app instance id");
                return null;
            }
        }
        return (String) atomicReference.get();
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzan(boolean z) {
        super.zzjC();
        super.zzwp();
        zzkD();
        super.zzwF().zzyC().zzj("Setting app measurement enabled (FE)", Boolean.valueOf(z));
        super.zzwG().setMeasurementEnabled(z);
        super.zzww().zzzj();
    }

    private final Map<String, Object> zzb(String str, String str2, String str3, boolean z) {
        if (super.zzwE().zzyM()) {
            super.zzwF().zzyx().log("Cannot get user properties from analytics worker thread");
            return Collections.emptyMap();
        }
        super.zzwE();
        if (zzcgg.zzS()) {
            super.zzwF().zzyx().log("Cannot get user properties from main thread");
            return Collections.emptyMap();
        }
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            this.zzboe.zzwE().zzj(new zzchq(this, atomicReference, str, str2, str3, z));
            try {
                atomicReference.wait(Constants.ACTIVE_THREAD_WATCHDOG);
            } catch (InterruptedException e) {
                super.zzwF().zzyz().zzj("Interrupted waiting for get user properties", e);
            }
        }
        List<zzcji> list = (List) atomicReference.get();
        if (list == null) {
            super.zzwF().zzyz().log("Timed out waiting for get user properties");
            return Collections.emptyMap();
        }
        ArrayMap arrayMap = new ArrayMap(list.size());
        for (zzcji zzcji : list) {
            arrayMap.put(zzcji.name, zzcji.getValue());
        }
        return arrayMap;
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzb(AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        super.zzjC();
        zzkD();
        zzbo.zzu(conditionalUserProperty);
        zzbo.zzcF(conditionalUserProperty.mName);
        zzbo.zzcF(conditionalUserProperty.mOrigin);
        zzbo.zzu(conditionalUserProperty.mValue);
        if (!this.zzboe.isEnabled()) {
            super.zzwF().zzyC().log("Conditional property not sent since Firebase Analytics is disabled");
            return;
        }
        zzcji zzcji = new zzcji(conditionalUserProperty.mName, conditionalUserProperty.mTriggeredTimestamp, conditionalUserProperty.mValue, conditionalUserProperty.mOrigin);
        try {
            zzcez zza = super.zzwB().zza(conditionalUserProperty.mTriggeredEventName, conditionalUserProperty.mTriggeredEventParams, conditionalUserProperty.mOrigin, 0, true, false);
            super.zzww().zzf(new zzcek(conditionalUserProperty.mAppId, conditionalUserProperty.mOrigin, zzcji, conditionalUserProperty.mCreationTimestamp, false, conditionalUserProperty.mTriggerEventName, super.zzwB().zza(conditionalUserProperty.mTimedOutEventName, conditionalUserProperty.mTimedOutEventParams, conditionalUserProperty.mOrigin, 0, true, false), conditionalUserProperty.mTriggerTimeout, zza, conditionalUserProperty.mTimeToLive, super.zzwB().zza(conditionalUserProperty.mExpiredEventName, conditionalUserProperty.mExpiredEventParams, conditionalUserProperty.mOrigin, 0, true, false)));
        } catch (IllegalArgumentException e) {
        }
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzb(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        int i;
        zzbo.zzcF(str);
        zzbo.zzcF(str2);
        zzbo.zzu(bundle);
        super.zzjC();
        zzkD();
        if (!this.zzboe.isEnabled()) {
            super.zzwF().zzyC().log("Event not sent since app measurement is disabled");
            return;
        }
        if (!this.zzbtr) {
            this.zzbtr = true;
            try {
                try {
                    Class.forName("com.google.android.gms.tagmanager.TagManagerService").getDeclaredMethod("initialize", new Class[]{Context.class}).invoke((Object) null, new Object[]{super.getContext()});
                } catch (Exception e) {
                    super.zzwF().zzyz().zzj("Failed to invoke Tag Manager's initialize() method", e);
                }
            } catch (ClassNotFoundException e2) {
                super.zzwF().zzyB().log("Tag Manager is not found and thus will not be used");
            }
        }
        boolean equals = "am".equals(str);
        boolean zzex = zzcjl.zzex(str2);
        if (z && this.zzbtp != null && !zzex && !equals) {
            super.zzwF().zzyC().zze("Passing event to registered event handler (FE)", super.zzwA().zzdW(str2), super.zzwA().zzA(bundle));
            this.zzbtp.interceptEvent(str, str2, bundle, j);
        } else if (this.zzboe.zzyP()) {
            int zzeq = super.zzwB().zzeq(str2);
            if (zzeq != 0) {
                super.zzwB();
                this.zzboe.zzwB().zza(str3, zzeq, "_ev", zzcjl.zza(str2, zzcem.zzxh(), true), str2 != null ? str2.length() : 0);
                return;
            }
            List singletonList = Collections.singletonList("_o");
            Bundle zza = super.zzwB().zza(str2, bundle, (List<String>) singletonList, z3, true);
            ArrayList arrayList = new ArrayList();
            arrayList.add(zza);
            long nextLong = super.zzwB().zzzt().nextLong();
            int i2 = 0;
            String[] strArr = (String[]) zza.keySet().toArray(new String[bundle.size()]);
            Arrays.sort(strArr);
            int length = strArr.length;
            int i3 = 0;
            while (i3 < length) {
                String str4 = strArr[i3];
                Object obj = zza.get(str4);
                super.zzwB();
                Bundle[] zzC = zzcjl.zzC(obj);
                if (zzC != null) {
                    zza.putInt(str4, zzC.length);
                    int i4 = 0;
                    while (true) {
                        int i5 = i4;
                        if (i5 >= zzC.length) {
                            break;
                        }
                        Bundle zza2 = super.zzwB().zza("_ep", zzC[i5], (List<String>) singletonList, z3, false);
                        zza2.putString("_en", str2);
                        zza2.putLong("_eid", nextLong);
                        zza2.putString("_gn", str4);
                        zza2.putInt("_ll", zzC.length);
                        zza2.putInt("_i", i5);
                        arrayList.add(zza2);
                        i4 = i5 + 1;
                    }
                    i = zzC.length + i2;
                } else {
                    i = i2;
                }
                i3++;
                i2 = i;
            }
            if (i2 != 0) {
                zza.putLong("_eid", nextLong);
                zza.putInt("_epc", i2);
            }
            zzcem.zzxE();
            zzcic zzzh = super.zzwx().zzzh();
            if (zzzh != null && !zza.containsKey("_sc")) {
                zzzh.zzbtS = true;
            }
            int i6 = 0;
            while (true) {
                int i7 = i6;
                if (i7 >= arrayList.size()) {
                    break;
                }
                Bundle bundle2 = (Bundle) arrayList.get(i7);
                String str5 = i7 != 0 ? "_ep" : str2;
                bundle2.putString("_o", str);
                if (!bundle2.containsKey("_sc")) {
                    zzchz.zza((AppMeasurement.zzb) zzzh, bundle2);
                }
                Bundle zzB = z2 ? super.zzwB().zzB(bundle2) : bundle2;
                super.zzwF().zzyC().zze("Logging event (FE)", super.zzwA().zzdW(str2), super.zzwA().zzA(zzB));
                super.zzww().zzc(new zzcez(str5, new zzcew(zzB), str, j), str3);
                if (!equals) {
                    for (AppMeasurement.OnEventListener onEvent : this.zzbtq) {
                        onEvent.onEvent(str, str2, new Bundle(zzB), j);
                    }
                }
                i6 = i7 + 1;
            }
            zzcem.zzxE();
            if (super.zzwx().zzzh() != null && AppMeasurement.Event.APP_EXCEPTION.equals(str2)) {
                super.zzwD().zzap(true);
            }
        }
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzc(AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        super.zzjC();
        zzkD();
        zzbo.zzu(conditionalUserProperty);
        zzbo.zzcF(conditionalUserProperty.mName);
        if (!this.zzboe.isEnabled()) {
            super.zzwF().zzyC().log("Conditional property not cleared since Firebase Analytics is disabled");
            return;
        }
        zzcji zzcji = new zzcji(conditionalUserProperty.mName, 0, (Object) null, (String) null);
        try {
            super.zzww().zzf(new zzcek(conditionalUserProperty.mAppId, conditionalUserProperty.mOrigin, zzcji, conditionalUserProperty.mCreationTimestamp, conditionalUserProperty.mActive, conditionalUserProperty.mTriggerEventName, (zzcez) null, conditionalUserProperty.mTriggerTimeout, (zzcez) null, conditionalUserProperty.mTimeToLive, super.zzwB().zza(conditionalUserProperty.mExpiredEventName, conditionalUserProperty.mExpiredEventParams, conditionalUserProperty.mOrigin, conditionalUserProperty.mCreationTimestamp, true, false)));
        } catch (IllegalArgumentException e) {
        }
    }

    private final List<AppMeasurement.ConditionalUserProperty> zzl(String str, String str2, String str3) {
        if (super.zzwE().zzyM()) {
            super.zzwF().zzyx().log("Cannot get conditional user properties from analytics worker thread");
            return Collections.emptyList();
        }
        super.zzwE();
        if (zzcgg.zzS()) {
            super.zzwF().zzyx().log("Cannot get conditional user properties from main thread");
            return Collections.emptyList();
        }
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            this.zzboe.zzwE().zzj(new zzchp(this, atomicReference, str, str2, str3));
            try {
                atomicReference.wait(Constants.ACTIVE_THREAD_WATCHDOG);
            } catch (InterruptedException e) {
                super.zzwF().zzyz().zze("Interrupted waiting for get conditional user properties", str, e);
            }
        }
        List<zzcek> list = (List) atomicReference.get();
        if (list == null) {
            super.zzwF().zzyz().zzj("Timed out waiting for get conditional user properties", str);
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (zzcek zzcek : list) {
            AppMeasurement.ConditionalUserProperty conditionalUserProperty = new AppMeasurement.ConditionalUserProperty();
            conditionalUserProperty.mAppId = str;
            conditionalUserProperty.mOrigin = str2;
            conditionalUserProperty.mCreationTimestamp = zzcek.zzbpe;
            conditionalUserProperty.mName = zzcek.zzbpd.name;
            conditionalUserProperty.mValue = zzcek.zzbpd.getValue();
            conditionalUserProperty.mActive = zzcek.zzbpf;
            conditionalUserProperty.mTriggerEventName = zzcek.zzbpg;
            if (zzcek.zzbph != null) {
                conditionalUserProperty.mTimedOutEventName = zzcek.zzbph.name;
                if (zzcek.zzbph.zzbpM != null) {
                    conditionalUserProperty.mTimedOutEventParams = zzcek.zzbph.zzbpM.zzyt();
                }
            }
            conditionalUserProperty.mTriggerTimeout = zzcek.zzbpi;
            if (zzcek.zzbpj != null) {
                conditionalUserProperty.mTriggeredEventName = zzcek.zzbpj.name;
                if (zzcek.zzbpj.zzbpM != null) {
                    conditionalUserProperty.mTriggeredEventParams = zzcek.zzbpj.zzbpM.zzyt();
                }
            }
            conditionalUserProperty.mTriggeredTimestamp = zzcek.zzbpd.zzbuy;
            conditionalUserProperty.mTimeToLive = zzcek.zzbpk;
            if (zzcek.zzbpl != null) {
                conditionalUserProperty.mExpiredEventName = zzcek.zzbpl.name;
                if (zzcek.zzbpl.zzbpM != null) {
                    conditionalUserProperty.mExpiredEventParams = zzcek.zzbpl.zzbpM.zzyt();
                }
            }
            arrayList.add(conditionalUserProperty);
        }
        return arrayList;
    }

    public final void clearConditionalUserProperty(String str, String str2, Bundle bundle) {
        super.zzwp();
        zza((String) null, str, str2, bundle);
    }

    public final void clearConditionalUserPropertyAs(String str, String str2, String str3, Bundle bundle) {
        zzbo.zzcF(str);
        super.zzwo();
        zza(str, str2, str3, bundle);
    }

    public final Task<String> getAppInstanceId() {
        try {
            String zzyH = super.zzwG().zzyH();
            return zzyH != null ? Tasks.forResult(zzyH) : Tasks.call(super.zzwE().zzyN(), new zzchw(this));
        } catch (Exception e) {
            super.zzwF().zzyz().log("Failed to schedule task for getAppInstanceId");
            return Tasks.forException(e);
        }
    }

    public final List<AppMeasurement.ConditionalUserProperty> getConditionalUserProperties(String str, String str2) {
        super.zzwp();
        return zzl((String) null, str, str2);
    }

    public final List<AppMeasurement.ConditionalUserProperty> getConditionalUserPropertiesAs(String str, String str2, String str3) {
        zzbo.zzcF(str);
        super.zzwo();
        return zzl(str, str2, str3);
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final Map<String, Object> getUserProperties(String str, String str2, boolean z) {
        super.zzwp();
        return zzb((String) null, str, str2, z);
    }

    public final Map<String, Object> getUserPropertiesAs(String str, String str2, String str3, boolean z) {
        zzbo.zzcF(str);
        super.zzwo();
        return zzb(str, str2, str3, z);
    }

    public final void registerOnMeasurementEventListener(AppMeasurement.OnEventListener onEventListener) {
        super.zzwp();
        zzkD();
        zzbo.zzu(onEventListener);
        if (!this.zzbtq.add(onEventListener)) {
            super.zzwF().zzyz().log("OnEventListener already registered");
        }
    }

    public final void setConditionalUserProperty(AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        zzbo.zzu(conditionalUserProperty);
        super.zzwp();
        AppMeasurement.ConditionalUserProperty conditionalUserProperty2 = new AppMeasurement.ConditionalUserProperty(conditionalUserProperty);
        if (!TextUtils.isEmpty(conditionalUserProperty2.mAppId)) {
            super.zzwF().zzyz().log("Package name should be null when calling setConditionalUserProperty");
        }
        conditionalUserProperty2.mAppId = null;
        zza(conditionalUserProperty2);
    }

    public final void setConditionalUserPropertyAs(AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        zzbo.zzu(conditionalUserProperty);
        zzbo.zzcF(conditionalUserProperty.mAppId);
        super.zzwo();
        zza(new AppMeasurement.ConditionalUserProperty(conditionalUserProperty));
    }

    @WorkerThread
    public final void setEventInterceptor(AppMeasurement.EventInterceptor eventInterceptor) {
        super.zzjC();
        super.zzwp();
        zzkD();
        if (!(eventInterceptor == null || eventInterceptor == this.zzbtp)) {
            zzbo.zza(this.zzbtp == null, (Object) "EventInterceptor already set.");
        }
        this.zzbtp = eventInterceptor;
    }

    public final void setMeasurementEnabled(boolean z) {
        zzkD();
        super.zzwp();
        super.zzwE().zzj(new zzchm(this, z));
    }

    public final void setMinimumSessionDuration(long j) {
        super.zzwp();
        super.zzwE().zzj(new zzchr(this, j));
    }

    public final void setSessionTimeoutDuration(long j) {
        super.zzwp();
        super.zzwE().zzj(new zzchs(this, j));
    }

    public final void unregisterOnMeasurementEventListener(AppMeasurement.OnEventListener onEventListener) {
        super.zzwp();
        zzkD();
        zzbo.zzu(onEventListener);
        if (!this.zzbtq.remove(onEventListener)) {
            super.zzwF().zzyz().log("OnEventListener had not been registered");
        }
    }

    public final void zza(String str, String str2, Bundle bundle, long j) {
        super.zzwp();
        zza(str, str2, j, bundle, false, true, true, (String) null);
    }

    public final void zza(String str, String str2, Bundle bundle, boolean z) {
        super.zzwp();
        zza(str, str2, bundle, true, this.zzbtp == null || zzcjl.zzex(str2), true, (String) null);
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final String zzac(long j) {
        if (super.zzwE().zzyM()) {
            super.zzwF().zzyx().log("Cannot retrieve app instance id from analytics worker thread");
            return null;
        }
        super.zzwE();
        if (zzcgg.zzS()) {
            super.zzwF().zzyx().log("Cannot retrieve app instance id from main thread");
            return null;
        }
        long elapsedRealtime = super.zzkq().elapsedRealtime();
        String zzad = zzad(120000);
        long elapsedRealtime2 = super.zzkq().elapsedRealtime() - elapsedRealtime;
        return (zzad != null || elapsedRealtime2 >= 120000) ? zzad : zzad(120000 - elapsedRealtime2);
    }

    public final List<zzcji> zzao(boolean z) {
        super.zzwp();
        zzkD();
        super.zzwF().zzyC().log("Fetching user attributes (FE)");
        if (super.zzwE().zzyM()) {
            super.zzwF().zzyx().log("Cannot get all user properties from analytics worker thread");
            return Collections.emptyList();
        }
        super.zzwE();
        if (zzcgg.zzS()) {
            super.zzwF().zzyx().log("Cannot get all user properties from main thread");
            return Collections.emptyList();
        }
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            this.zzboe.zzwE().zzj(new zzchv(this, atomicReference, z));
            try {
                atomicReference.wait(Constants.ACTIVE_THREAD_WATCHDOG);
            } catch (InterruptedException e) {
                super.zzwF().zzyz().zzj("Interrupted waiting for get user properties", e);
            }
        }
        List<zzcji> list = (List) atomicReference.get();
        if (list != null) {
            return list;
        }
        super.zzwF().zzyz().log("Timed out waiting for get user properties");
        return Collections.emptyList();
    }

    public final void zzb(String str, String str2, Object obj) {
        int i = 0;
        zzbo.zzcF(str);
        long currentTimeMillis = super.zzkq().currentTimeMillis();
        int zzes = super.zzwB().zzes(str2);
        if (zzes != 0) {
            super.zzwB();
            String zza = zzcjl.zza(str2, zzcem.zzxi(), true);
            if (str2 != null) {
                i = str2.length();
            }
            this.zzboe.zzwB().zza(zzes, "_ev", zza, i);
        } else if (obj != null) {
            int zzl = super.zzwB().zzl(str2, obj);
            if (zzl != 0) {
                super.zzwB();
                String zza2 = zzcjl.zza(str2, zzcem.zzxi(), true);
                if ((obj instanceof String) || (obj instanceof CharSequence)) {
                    i = String.valueOf(obj).length();
                }
                this.zzboe.zzwB().zza(zzl, "_ev", zza2, i);
                return;
            }
            Object zzm = super.zzwB().zzm(str2, obj);
            if (zzm != null) {
                zza(str, str2, currentTimeMillis, zzm);
            }
        } else {
            zza(str, str2, currentTimeMillis, (Object) null);
        }
    }

    public final void zzd(String str, String str2, Bundle bundle) {
        super.zzwp();
        zza(str, str2, bundle, true, this.zzbtp == null || zzcjl.zzex(str2), false, (String) null);
    }

    /* access modifiers changed from: package-private */
    public final void zzee(@Nullable String str) {
        this.zzbts.set(str);
    }

    public final /* bridge */ /* synthetic */ void zzjC() {
        super.zzjC();
    }

    /* access modifiers changed from: protected */
    public final void zzjD() {
    }

    public final /* bridge */ /* synthetic */ zze zzkq() {
        return super.zzkq();
    }

    public final /* bridge */ /* synthetic */ zzcfj zzwA() {
        return super.zzwA();
    }

    public final /* bridge */ /* synthetic */ zzcjl zzwB() {
        return super.zzwB();
    }

    public final /* bridge */ /* synthetic */ zzcgf zzwC() {
        return super.zzwC();
    }

    public final /* bridge */ /* synthetic */ zzcja zzwD() {
        return super.zzwD();
    }

    public final /* bridge */ /* synthetic */ zzcgg zzwE() {
        return super.zzwE();
    }

    public final /* bridge */ /* synthetic */ zzcfl zzwF() {
        return super.zzwF();
    }

    public final /* bridge */ /* synthetic */ zzcfw zzwG() {
        return super.zzwG();
    }

    public final /* bridge */ /* synthetic */ zzcem zzwH() {
        return super.zzwH();
    }

    public final /* bridge */ /* synthetic */ void zzwo() {
        super.zzwo();
    }

    public final /* bridge */ /* synthetic */ void zzwp() {
        super.zzwp();
    }

    public final /* bridge */ /* synthetic */ void zzwq() {
        super.zzwq();
    }

    public final /* bridge */ /* synthetic */ zzcec zzwr() {
        return super.zzwr();
    }

    public final /* bridge */ /* synthetic */ zzcej zzws() {
        return super.zzws();
    }

    public final /* bridge */ /* synthetic */ zzchl zzwt() {
        return super.zzwt();
    }

    public final /* bridge */ /* synthetic */ zzcfg zzwu() {
        return super.zzwu();
    }

    public final /* bridge */ /* synthetic */ zzcet zzwv() {
        return super.zzwv();
    }

    public final /* bridge */ /* synthetic */ zzcid zzww() {
        return super.zzww();
    }

    public final /* bridge */ /* synthetic */ zzchz zzwx() {
        return super.zzwx();
    }

    public final /* bridge */ /* synthetic */ zzcfh zzwy() {
        return super.zzwy();
    }

    public final /* bridge */ /* synthetic */ zzcen zzwz() {
        return super.zzwz();
    }

    @Nullable
    public final String zzyH() {
        super.zzwp();
        return this.zzbts.get();
    }
}
