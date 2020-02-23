package com.subao.common.k;

import MTT.EFvrECode;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import com.subao.common.k.b;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.DatagramSocket;

/* compiled from: NetworkWatcherNetworkImpl */
public class e implements b.C0018b {
    private final Network a;

    public e(Network network) {
        if (network == null) {
            throw new NullPointerException("Null network");
        }
        this.a = network;
    }

    static int a(Network network) {
        Object obj;
        try {
            Field declaredField = network.getClass().getDeclaredField("netId");
            if (!(declaredField == null || (obj = declaredField.get(network)) == null || !(obj instanceof Integer))) {
                return ((Integer) obj).intValue();
            }
        } catch (Exception e) {
        }
        throw new b.d(2010);
    }

    static void a(DatagramSocket datagramSocket, int i, int i2) {
        Method declaredMethod;
        try {
            Class<?> cls = Class.forName("android.net.NetworkUtils");
            if (!(cls == null || (declaredMethod = cls.getDeclaredMethod("bindSocketToNetwork", new Class[]{Integer.TYPE, Integer.TYPE})) == null)) {
                datagramSocket.getReuseAddress();
                if (((Integer) declaredMethod.invoke((Object) null, new Object[]{Integer.valueOf(i), Integer.valueOf(i2)})).intValue() == 0) {
                    return;
                }
            }
        } catch (Exception e) {
        }
        throw new b.d(EFvrECode._ERR_FVR_NONSUPPORT);
    }

    @TargetApi(14)
    public void a(DatagramSocket datagramSocket) {
        if (Build.VERSION.SDK_INT < 21) {
            throw new b.d(2000);
        } else if (Build.VERSION.SDK_INT > 21) {
            try {
                this.a.bindSocket(datagramSocket);
            } catch (Exception e) {
                e.printStackTrace();
                throw new b.d(2009);
            } catch (Error e2) {
                e2.printStackTrace();
                throw new b.d(EFvrECode._ERR_FVR_URLINFO_EXCEPTION);
            }
        } else {
            int a2 = a(this.a);
            ParcelFileDescriptor fromDatagramSocket = ParcelFileDescriptor.fromDatagramSocket(datagramSocket);
            if (fromDatagramSocket == null) {
                throw new b.d(2011);
            }
            try {
                a(datagramSocket, fromDatagramSocket.getFd(), a2);
            } catch (RuntimeException e3) {
                throw new b.d(2012);
            }
        }
    }

    @TargetApi(21)
    public NetworkInfo a(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return null;
        }
        return connectivityManager.getNetworkInfo(this.a);
    }

    @TargetApi(21)
    public String toString() {
        return this.a.toString();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof e) {
            return com.subao.common.e.a(this.a, ((e) obj).a);
        }
        return false;
    }
}
