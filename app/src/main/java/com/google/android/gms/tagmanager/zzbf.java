package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.tagmanager.ModuleDescriptor;
import com.google.android.gms.measurement.AppMeasurement;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

final class zzbf {
    private static volatile DynamiteModule zzbEA;
    private static volatile zzcq zzbEB;
    /* access modifiers changed from: private */
    public static final Map<String, CustomTagProvider> zzbEC = new HashMap();
    /* access modifiers changed from: private */
    public static final Map<String, CustomVariableProvider> zzbED = new HashMap();

    private zzbf() {
    }

    static void zza(Intent intent, Context context) {
        zzcq zzbp = zzbp(context);
        synchronized (zzbf.class) {
            try {
                zzbp.previewIntent(intent, zzn.zzw(context), zzn.zzw(zzbEA.zztC()), zzbr(context), new zzbj());
            } catch (RemoteException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /* access modifiers changed from: private */
    public static Object zzb(String str, Class<?> cls) {
        boolean z = false;
        try {
            Class<?> cls2 = Class.forName(str);
            Class[] interfaces = cls2.getInterfaces();
            int length = interfaces.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                } else if (interfaces[i].equals(cls)) {
                    z = true;
                    break;
                } else {
                    i++;
                }
            }
            if (!z) {
                String valueOf = String.valueOf(cls.getCanonicalName());
                Log.e("GoogleTagManagerAPI", new StringBuilder(String.valueOf(str).length() + 30 + String.valueOf(valueOf).length()).append(str).append(" doesn't implement ").append(valueOf).append(" interface.").toString());
                return null;
            }
            try {
                return cls2.getConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (NoSuchMethodException e) {
                Log.e("GoogleTagManagerAPI", String.valueOf(str).concat(" doesn't have a valid no-arg constructor"));
                return null;
            } catch (SecurityException e2) {
                Log.e("GoogleTagManagerAPI", String.valueOf(str).concat(" doesn't have an accessible no-arg constructor"));
                return null;
            } catch (InvocationTargetException e3) {
                Log.e("GoogleTagManagerAPI", String.valueOf(str).concat(" construction threw an exception."));
                return null;
            } catch (InstantiationException e4) {
                Log.e("GoogleTagManagerAPI", String.valueOf(str).concat(" is an abstract class."));
                return null;
            } catch (IllegalAccessException e5) {
                Log.e("GoogleTagManagerAPI", String.valueOf(str).concat(" doesn't have an accessible no-arg constructor"));
                return null;
            }
        } catch (ClassNotFoundException e6) {
            Log.e("GoogleTagManagerAPI", String.valueOf(str).concat(" can't be found in the application."));
            return null;
        }
    }

    static IBinder zzbn(Context context) {
        try {
            return zzcu.asInterface(zzbq(context).zzcV("com.google.android.gms.tagmanager.TagManagerServiceProviderImpl")).getService(zzn.zzw(context), zzbr(context), new zzbj()).asBinder();
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        } catch (DynamiteModule.zzc e2) {
            throw new RuntimeException(e2);
        }
    }

    static void zzbo(Context context) {
        zzcq zzbp = zzbp(context);
        synchronized (zzbf.class) {
            try {
                zzbp.initialize(zzn.zzw(context), zzbr(context), new zzbj());
            } catch (RemoteException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static zzcq zzbp(Context context) {
        zzcq zzcq = zzbEB;
        if (zzcq == null) {
            synchronized (zzbf.class) {
                zzcq = zzbEB;
                if (zzcq == null) {
                    try {
                        zzcq = zzcr.asInterface(zzbq(context).zzcV("com.google.android.gms.tagmanager.TagManagerApiImpl"));
                        zzbEB = zzcq;
                    } catch (DynamiteModule.zzc e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return zzcq;
    }

    private static DynamiteModule zzbq(Context context) throws DynamiteModule.zzc {
        DynamiteModule dynamiteModule = zzbEA;
        if (dynamiteModule == null) {
            synchronized (zzbf.class) {
                dynamiteModule = zzbEA;
                if (zzbEA == null) {
                    dynamiteModule = DynamiteModule.zza(context, DynamiteModule.zzaSO, ModuleDescriptor.MODULE_ID);
                    zzbEA = dynamiteModule;
                }
            }
        }
        return dynamiteModule;
    }

    private static zzcn zzbr(Context context) {
        return new zzbg(AppMeasurement.getInstance(context));
    }
}
