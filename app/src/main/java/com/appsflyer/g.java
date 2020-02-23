package com.appsflyer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

final class g {
    g() {
    }

    static final class d {

        /* renamed from: ˊ  reason: contains not printable characters */
        private final String f95;

        /* renamed from: ˎ  reason: contains not printable characters */
        private final boolean f96;

        d(String str, boolean z) {
            this.f95 = str;
            this.f96 = z;
        }

        /* renamed from: ˊ  reason: contains not printable characters */
        public final String m74() {
            return this.f95;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: ॱ  reason: contains not printable characters */
        public final boolean m75() {
            return this.f96;
        }
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    static d m70(Context context) throws Exception {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Cannot be called from the main thread");
        }
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
            b bVar = new b((byte) 0);
            Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
            intent.setPackage("com.google.android.gms");
            try {
                if (context.bindService(intent, bVar, 1)) {
                    c cVar = new c(bVar.m71());
                    d dVar = new d(cVar.m72(), cVar.m73());
                    if (context != null) {
                        context.unbindService(bVar);
                    }
                    return dVar;
                }
                if (context != null) {
                    context.unbindService(bVar);
                }
                throw new IOException("Google Play connection failed");
            } catch (Exception e) {
                throw e;
            } catch (Throwable th) {
                if (context != null) {
                    context.unbindService(bVar);
                }
                throw th;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    static final class b implements ServiceConnection {

        /* renamed from: ˊ  reason: contains not printable characters */
        private boolean f92;

        /* renamed from: ˏ  reason: contains not printable characters */
        private final LinkedBlockingQueue<IBinder> f93;

        private b() {
            this.f92 = false;
            this.f93 = new LinkedBlockingQueue<>(1);
        }

        /* synthetic */ b(byte b) {
            this();
        }

        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.f93.put(iBinder);
            } catch (InterruptedException e) {
            }
        }

        public final void onServiceDisconnected(ComponentName componentName) {
        }

        /* renamed from: ˏ  reason: contains not printable characters */
        public final IBinder m71() throws InterruptedException {
            if (this.f92) {
                throw new IllegalStateException();
            }
            this.f92 = true;
            return this.f93.take();
        }
    }

    static final class c implements IInterface {

        /* renamed from: ˎ  reason: contains not printable characters */
        private IBinder f94;

        c(IBinder iBinder) {
            this.f94 = iBinder;
        }

        public final IBinder asBinder() {
            return this.f94;
        }

        /* renamed from: ˋ  reason: contains not printable characters */
        public final String m72() throws RemoteException {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                this.f94.transact(1, obtain, obtain2, 0);
                obtain2.readException();
                return obtain2.readString();
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: ˎ  reason: contains not printable characters */
        public final boolean m73() throws RemoteException {
            boolean z = true;
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                obtain.writeInt(1);
                this.f94.transact(2, obtain, obtain2, 0);
                obtain2.readException();
                if (obtain2.readInt() == 0) {
                    z = false;
                }
                return z;
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        }
    }
}
