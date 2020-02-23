package com.samsung.android.game.gamelib;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.game.tencentsdk.ISceneSdkListener;
import com.samsung.android.game.tencentsdk.ISceneSdkService;

public class GameServiceHelper {
    private static final String TAG = "GameServiceHelper";
    /* access modifiers changed from: private */
    public BindListener mBindListener = null;
    private Context mContext = null;
    /* access modifiers changed from: private */
    public ISceneSdkService mGameServiceBinder = null;
    /* access modifiers changed from: private */
    public Listener mListener = null;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(GameServiceHelper.TAG, "game service connect");
            ISceneSdkService unused = GameServiceHelper.this.mGameServiceBinder = ISceneSdkService.Stub.asInterface(iBinder);
            if (GameServiceHelper.this.mBindListener != null) {
                GameServiceHelper.this.mBindListener.bindCallBack();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(GameServiceHelper.TAG, "game service disconnect");
            ISceneSdkService unused = GameServiceHelper.this.mGameServiceBinder = null;
        }
    };

    public interface BindListener {
        void bindCallBack();
    }

    public interface Listener {
        void resultCallBack(int i, int i2);

        void systemCallBack(int i);
    }

    public int applyHardwareResource(String str) {
        if (this.mGameServiceBinder == null) {
            Log.d(TAG, "game service is not available");
            return -1;
        }
        try {
            return this.mGameServiceBinder.applyHardwareResource(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int applyThreadGuarantee(String str) {
        if (this.mGameServiceBinder == null) {
            Log.d(TAG, "game service is not available");
            return -1;
        }
        try {
            return this.mGameServiceBinder.applyThreadGuarantee(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void bind(Context context) {
        this.mContext = context;
        String str = "com.enhance.gameservice";
        PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager != null) {
            try {
                packageManager.getPackageInfo("com.samsung.android.graphics.optimizingservice", 128);
                str = "com.samsung.android.graphics.optimizingservice";
            } catch (PackageManager.NameNotFoundException e) {
                Log.d(TAG, "New package doesn't exist.");
            }
        }
        Log.d(TAG, "targetPkgName: " + str);
        Intent intent = new Intent("com.samsung.android.game.tencentsdk.SceneSdkService");
        intent.setPackage(str);
        if (this.mContext != null) {
            Log.i(TAG, "bindService. ret: " + this.mContext.bindService(intent, this.mServiceConnection, 1));
        }
    }

    public float getVersion() {
        if (this.mGameServiceBinder == null) {
            Log.d(TAG, "game service is not available");
            return 0.0f;
        }
        try {
            return this.mGameServiceBinder.getVersion();
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public boolean init() {
        if (this.mGameServiceBinder == null) {
            Log.d(TAG, "game service is not available");
            return false;
        }
        try {
            return this.mGameServiceBinder.initSceneSdk();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void registerBindListener(BindListener bindListener) {
        this.mBindListener = bindListener;
    }

    public boolean registerListener(Listener listener) {
        this.mListener = listener;
        if (this.mGameServiceBinder != null) {
            if (this.mListener == null) {
                try {
                    return this.mGameServiceBinder.setSceneSdkListener((ISceneSdkListener) null);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    return this.mGameServiceBinder.setSceneSdkListener(new ISceneSdkListener.Stub() {
                        public void resultCallBack(int i, int i2) {
                            GameServiceHelper.this.mListener.resultCallBack(i, i2);
                        }

                        public void systemCallBack(int i) {
                            GameServiceHelper.this.mListener.systemCallBack(i);
                        }
                    });
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return false;
    }

    public void unbind() {
        if (this.mContext != null && this.mGameServiceBinder != null) {
            this.mGameServiceBinder = null;
            try {
                this.mContext.unbindService(this.mServiceConnection);
            } catch (IllegalArgumentException e) {
                Log.w(TAG, "unbind can't be called. Error: " + e.getMessage());
            }
        }
    }

    public int updateGameInfo(String str) {
        if (this.mGameServiceBinder == null) {
            Log.d(TAG, "game service is not available");
            return -1;
        }
        try {
            return this.mGameServiceBinder.updateGameInfo(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
