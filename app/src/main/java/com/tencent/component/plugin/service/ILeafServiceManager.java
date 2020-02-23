package com.tencent.component.plugin.service;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.tencent.component.plugin.service.ILeafServiceConnection;

public interface ILeafServiceManager extends IInterface {
    void bindService(String str, String str2, String str3, ILeafServiceConnection iLeafServiceConnection, Bundle bundle) throws RemoteException;

    void startService(String str, String str2, String str3, Bundle bundle) throws RemoteException;

    void stopService(String str, String str2, String str3) throws RemoteException;

    void unbindService(String str, String str2, String str3, ILeafServiceConnection iLeafServiceConnection) throws RemoteException;

    public static abstract class Stub extends Binder implements ILeafServiceManager {
        private static final String DESCRIPTOR = "com.tencent.component.plugin.service.ILeafServiceManager";
        static final int TRANSACTION_bindService = 1;
        static final int TRANSACTION_startService = 3;
        static final int TRANSACTION_stopService = 4;
        static final int TRANSACTION_unbindService = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILeafServiceManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ILeafServiceManager)) {
                return new Proxy(obj);
            }
            return (ILeafServiceManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Bundle _arg3;
            Bundle _arg4;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0 = data.readString();
                    String _arg1 = data.readString();
                    String _arg2 = data.readString();
                    ILeafServiceConnection _arg32 = ILeafServiceConnection.Stub.asInterface(data.readStrongBinder());
                    if (data.readInt() != 0) {
                        _arg4 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg4 = null;
                    }
                    bindService(_arg0, _arg1, _arg2, _arg32, _arg4);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    unbindService(data.readString(), data.readString(), data.readString(), ILeafServiceConnection.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg02 = data.readString();
                    String _arg12 = data.readString();
                    String _arg22 = data.readString();
                    if (data.readInt() != 0) {
                        _arg3 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg3 = null;
                    }
                    startService(_arg02, _arg12, _arg22, _arg3);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    stopService(data.readString(), data.readString(), data.readString());
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ILeafServiceManager {
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

            public void bindService(String platformId, String pluginId, String leafServiceClassName, ILeafServiceConnection connection, Bundle args) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(platformId);
                    _data.writeString(pluginId);
                    _data.writeString(leafServiceClassName);
                    if (connection != null) {
                        iBinder = connection.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    if (args != null) {
                        _data.writeInt(1);
                        args.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(1, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void unbindService(String platformId, String pluginId, String leafServiceClassName, ILeafServiceConnection connection) throws RemoteException {
                IBinder iBinder = null;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(platformId);
                    _data.writeString(pluginId);
                    _data.writeString(leafServiceClassName);
                    if (connection != null) {
                        iBinder = connection.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void startService(String platformId, String pluginId, String leafServiceClassName, Bundle args) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(platformId);
                    _data.writeString(pluginId);
                    _data.writeString(leafServiceClassName);
                    if (args != null) {
                        _data.writeInt(1);
                        args.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(3, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void stopService(String platformId, String pluginId, String leafServiceClassName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(platformId);
                    _data.writeString(pluginId);
                    _data.writeString(leafServiceClassName);
                    this.mRemote.transact(4, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}
