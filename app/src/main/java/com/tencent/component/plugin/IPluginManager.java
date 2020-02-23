package com.tencent.component.plugin;

import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.tencent.component.plugin.InstallPluginListener;
import com.tencent.component.plugin.PluginManageHandler;
import com.tencent.component.plugin.UninstallPluginListener;
import com.tencent.component.plugin.server.PluginServerBroadcast;
import java.util.List;

public interface IPluginManager extends IInterface {
    boolean disablePlugin(String str, String str2) throws RemoteException;

    boolean enablePlugin(String str, String str2) throws RemoteException;

    List<PluginInfo> getAllPluginInfos(String str) throws RemoteException;

    PluginInfo getPluginInfo(String str, String str2) throws RemoteException;

    Intent handlePluginUri(String str, String str2, Uri uri) throws RemoteException;

    void hello(PluginPlatformConfig pluginPlatformConfig, PluginServerBroadcast pluginServerBroadcast) throws RemoteException;

    void install(String str, String str2, InstallPluginListener installPluginListener) throws RemoteException;

    boolean isPluginEnabled(String str, String str2) throws RemoteException;

    boolean isPluginRegistered(String str, String str2) throws RemoteException;

    PluginInfo loadPluginInfo(String str, String str2) throws RemoteException;

    void markPluginSurviveable(String str, String str2, boolean z) throws RemoteException;

    boolean registerPlugin(String str, String str2, PluginInfo pluginInfo) throws RemoteException;

    void setPluginHandler(String str, PluginManageHandler pluginManageHandler) throws RemoteException;

    void uninstall(String str, PluginInfo pluginInfo, UninstallPluginListener uninstallPluginListener) throws RemoteException;

    boolean unregisterPlugin(String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IPluginManager {
        private static final String DESCRIPTOR = "com.tencent.component.plugin.IPluginManager";
        static final int TRANSACTION_disablePlugin = 6;
        static final int TRANSACTION_enablePlugin = 5;
        static final int TRANSACTION_getAllPluginInfos = 10;
        static final int TRANSACTION_getPluginInfo = 9;
        static final int TRANSACTION_handlePluginUri = 12;
        static final int TRANSACTION_hello = 1;
        static final int TRANSACTION_install = 13;
        static final int TRANSACTION_isPluginEnabled = 7;
        static final int TRANSACTION_isPluginRegistered = 4;
        static final int TRANSACTION_loadPluginInfo = 8;
        static final int TRANSACTION_markPluginSurviveable = 15;
        static final int TRANSACTION_registerPlugin = 2;
        static final int TRANSACTION_setPluginHandler = 11;
        static final int TRANSACTION_uninstall = 14;
        static final int TRANSACTION_unregisterPlugin = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPluginManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPluginManager)) {
                return new Proxy(obj);
            }
            return (IPluginManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean _arg2;
            PluginInfo _arg1;
            Uri _arg22;
            PluginInfo _arg23;
            PluginPlatformConfig _arg0;
            int i = 0;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = PluginPlatformConfig.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    hello(_arg0, PluginServerBroadcast.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg02 = data.readString();
                    String _arg12 = data.readString();
                    if (data.readInt() != 0) {
                        _arg23 = PluginInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg23 = null;
                    }
                    boolean _result = registerPlugin(_arg02, _arg12, _arg23);
                    reply.writeNoException();
                    if (_result) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result2 = unregisterPlugin(data.readString(), data.readString());
                    reply.writeNoException();
                    if (_result2) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result3 = isPluginRegistered(data.readString(), data.readString());
                    reply.writeNoException();
                    if (_result3) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result4 = enablePlugin(data.readString(), data.readString());
                    reply.writeNoException();
                    if (_result4) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result5 = disablePlugin(data.readString(), data.readString());
                    reply.writeNoException();
                    if (_result5) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result6 = isPluginEnabled(data.readString(), data.readString());
                    reply.writeNoException();
                    if (_result6) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    PluginInfo _result7 = loadPluginInfo(data.readString(), data.readString());
                    reply.writeNoException();
                    if (_result7 != null) {
                        reply.writeInt(1);
                        _result7.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    PluginInfo _result8 = getPluginInfo(data.readString(), data.readString());
                    reply.writeNoException();
                    if (_result8 != null) {
                        reply.writeInt(1);
                        _result8.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    List<PluginInfo> _result9 = getAllPluginInfos(data.readString());
                    reply.writeNoException();
                    reply.writeTypedList(_result9);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    setPluginHandler(data.readString(), PluginManageHandler.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg03 = data.readString();
                    String _arg13 = data.readString();
                    if (data.readInt() != 0) {
                        _arg22 = (Uri) Uri.CREATOR.createFromParcel(data);
                    } else {
                        _arg22 = null;
                    }
                    Intent _result10 = handlePluginUri(_arg03, _arg13, _arg22);
                    reply.writeNoException();
                    if (_result10 != null) {
                        reply.writeInt(1);
                        _result10.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    install(data.readString(), data.readString(), InstallPluginListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg04 = data.readString();
                    if (data.readInt() != 0) {
                        _arg1 = PluginInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    uninstall(_arg04, _arg1, UninstallPluginListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg05 = data.readString();
                    String _arg14 = data.readString();
                    if (data.readInt() != 0) {
                        _arg2 = true;
                    } else {
                        _arg2 = false;
                    }
                    markPluginSurviveable(_arg05, _arg14, _arg2);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IPluginManager {
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

            public void hello(PluginPlatformConfig platformConfig, PluginServerBroadcast broadcast) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (platformConfig != null) {
                        _data.writeInt(1);
                        platformConfig.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(broadcast != null ? broadcast.asBinder() : null);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean registerPlugin(String platformId, String id, PluginInfo pluginInfo) throws RemoteException {
                boolean _result = true;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(platformId);
                    _data.writeString(id);
                    if (pluginInfo != null) {
                        _data.writeInt(1);
                        pluginInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean unregisterPlugin(String platformId, String id) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(platformId);
                    _data.writeString(id);
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

            public boolean isPluginRegistered(String platformId, String id) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(platformId);
                    _data.writeString(id);
                    this.mRemote.transact(4, _data, _reply, 0);
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

            public boolean enablePlugin(String platformId, String id) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(platformId);
                    _data.writeString(id);
                    this.mRemote.transact(5, _data, _reply, 0);
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

            public boolean disablePlugin(String platformId, String id) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(platformId);
                    _data.writeString(id);
                    this.mRemote.transact(6, _data, _reply, 0);
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

            public boolean isPluginEnabled(String platformId, String id) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(platformId);
                    _data.writeString(id);
                    this.mRemote.transact(7, _data, _reply, 0);
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

            public PluginInfo loadPluginInfo(String platformId, String id) throws RemoteException {
                PluginInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(platformId);
                    _data.writeString(id);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PluginInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public PluginInfo getPluginInfo(String platformId, String id) throws RemoteException {
                PluginInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(platformId);
                    _data.writeString(id);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PluginInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<PluginInfo> getAllPluginInfos(String platformId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(platformId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    return _reply.createTypedArrayList(PluginInfo.CREATOR);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPluginHandler(String platformId, PluginManageHandler handler) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(platformId);
                    _data.writeStrongBinder(handler != null ? handler.asBinder() : null);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Intent handlePluginUri(String platformId, String id, Uri uri) throws RemoteException {
                Intent _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(platformId);
                    _data.writeString(id);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = (Intent) Intent.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void install(String platformId, String pluginLocation, InstallPluginListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(platformId);
                    _data.writeString(pluginLocation);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void uninstall(String platformId, PluginInfo pluginInfo, UninstallPluginListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(platformId);
                    if (pluginInfo != null) {
                        _data.writeInt(1);
                        pluginInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void markPluginSurviveable(String platformId, String pluginId, boolean surviveable) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(platformId);
                    _data.writeString(pluginId);
                    if (surviveable) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
