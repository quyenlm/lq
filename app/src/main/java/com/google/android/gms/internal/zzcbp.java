package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ProviderInfo;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.tencent.imsdk.expansion.downloader.DownloaderServiceMarshaller;

public final class zzcbp {
    private static zzcbp zzbhq;
    private final Context mContext;

    private zzcbp(Context context) {
        this.mContext = context;
    }

    @Nullable
    public static synchronized zzcbp zzbf(Context context) {
        zzcbp zzcbp;
        ProviderInfo resolveContentProvider;
        boolean z = false;
        zzcbp zzcbp2 = null;
        synchronized (zzcbp.class) {
            if (zzbhq == null) {
                Context applicationContext = context.getApplicationContext();
                if (Build.VERSION.SDK_INT >= 16) {
                    z = true;
                }
                if (z) {
                    if (zzcbs.zzbg(applicationContext) && (resolveContentProvider = applicationContext.getPackageManager().resolveContentProvider(zzcbq.zzbhr.getAuthority(), 0)) != null) {
                        if (!resolveContentProvider.packageName.equals("com.google.android.gms")) {
                            String valueOf = String.valueOf(resolveContentProvider.packageName);
                            Log.e("IAMetadataClient", new StringBuilder(String.valueOf(valueOf).length() + 85).append("Package ").append(valueOf).append(" is invalid for instant apps content provider; instant apps will be disabled.").toString());
                        } else {
                            zzcbp2 = new zzcbp(applicationContext);
                        }
                    }
                }
                zzbhq = zzcbp2;
            }
            zzcbp = zzbhq;
        }
        return zzcbp;
    }

    @TargetApi(16)
    private final Bundle zzg(String str, Bundle bundle) throws RemoteException {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Bundle call = this.mContext.getContentResolver().call(zzcbq.zzbhr, str, (String) null, bundle);
            if (call != null) {
                return call;
            }
            throw new RemoteException();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ApplicationInfo getApplicationInfo(String str, int i) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putString("packageName", str);
        bundle.putInt(DownloaderServiceMarshaller.PARAMS_FLAGS, i);
        return (ApplicationInfo) zzg("getWHApplicationInfo", bundle).getParcelable(GGLiveConstants.PARAM.RESULT);
    }

    public final PackageInfo getPackageInfo(String str, int i) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putString("packageName", str);
        bundle.putInt(DownloaderServiceMarshaller.PARAMS_FLAGS, i);
        return (PackageInfo) zzg("getWHPackageInfo", bundle).getParcelable(GGLiveConstants.PARAM.RESULT);
    }

    public final boolean isInstantApp(String str) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putString("packageName", str);
        return zzg("isInstantApp", bundle).getBoolean(GGLiveConstants.PARAM.RESULT);
    }

    public final String zzbi(int i) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putInt(GGLiveConstants.PARAM.UID, i);
        return zzg("getAppPackageForUid", bundle).getString(GGLiveConstants.PARAM.RESULT);
    }

    public final String zzdt(String str) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putString("packageName", str);
        return zzg("getApplicationLabel", bundle).getString(GGLiveConstants.PARAM.RESULT);
    }

    public final ComponentName zzdu(String str) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putString("shadowActivity", str);
        return (ComponentName) zzg("getCallingActivity", bundle).getParcelable(GGLiveConstants.PARAM.RESULT);
    }
}
