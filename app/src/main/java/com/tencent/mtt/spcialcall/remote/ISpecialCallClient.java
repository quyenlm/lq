package com.tencent.mtt.spcialcall.remote;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISpecialCallClient extends IInterface {
    String onJsCall(long j, String str, String str2, String str3) throws RemoteException;

    void onWebViewDestroy(long j) throws RemoteException;

    boolean shouldOverrideUrlLoading(long j, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISpecialCallClient {
        private static final String DESCRIPTOR = "com.tencent.mtt.spcialcall.remote.ISpecialCallClient";
        static final int TRANSACTION_onJsCall = 2;
        static final int TRANSACTION_onWebViewDestroy = 1;
        static final int TRANSACTION_shouldOverrideUrlLoading = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISpecialCallClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ISpecialCallClient)) {
                return new Proxy(obj);
            }
            return (ISpecialCallClient) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    onWebViewDestroy(data.readLong());
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    String _result = onJsCall(data.readLong(), data.readString(), data.readString(), data.readString());
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result2 = shouldOverrideUrlLoading(data.readLong(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result2 ? 1 : 0);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISpecialCallClient {
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

            public void onWebViewDestroy(long viewId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(viewId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String onJsCall(long viewId, String service, String method, String args) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(viewId);
                    _data.writeString(service);
                    _data.writeString(method);
                    _data.writeString(args);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean shouldOverrideUrlLoading(long viewId, String url) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(viewId);
                    _data.writeString(url);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
