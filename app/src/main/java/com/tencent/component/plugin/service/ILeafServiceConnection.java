package com.tencent.component.plugin.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILeafServiceConnection extends IInterface {
    void connected(String str, IBinder iBinder) throws RemoteException;

    void disconnected(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ILeafServiceConnection {
        private static final String DESCRIPTOR = "com.tencent.component.plugin.service.ILeafServiceConnection";
        static final int TRANSACTION_connected = 1;
        static final int TRANSACTION_disconnected = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILeafServiceConnection asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ILeafServiceConnection)) {
                return new Proxy(obj);
            }
            return (ILeafServiceConnection) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    connected(data.readString(), data.readStrongBinder());
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    disconnected(data.readString());
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ILeafServiceConnection {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void connected(String name, IBinder service) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeStrongBinder(service);
                    this.mRemote.transact(1, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void disconnected(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(2, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}
