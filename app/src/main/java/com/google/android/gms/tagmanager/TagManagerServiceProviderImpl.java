package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.internal.zzcvg;
import com.google.android.gms.internal.zzcxa;

@DynamiteApi
public class TagManagerServiceProviderImpl extends zzcu {
    private static volatile zzcxa zzbHa;

    public zzcvg getService(IObjectWrapper iObjectWrapper, zzcn zzcn, zzce zzce) throws RemoteException {
        zzcxa zzcxa = zzbHa;
        if (zzcxa == null) {
            synchronized (TagManagerServiceProviderImpl.class) {
                zzcxa = zzbHa;
                if (zzcxa == null) {
                    zzcxa zzcxa2 = new zzcxa((Context) zzn.zzE(iObjectWrapper), zzcn, zzce);
                    zzbHa = zzcxa2;
                    zzcxa = zzcxa2;
                }
            }
        }
        return zzcxa;
    }
}
