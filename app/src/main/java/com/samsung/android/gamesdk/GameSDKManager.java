package com.samsung.android.gamesdk;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.google.firebase.database.DatabaseError;
import com.samsung.android.gamesdk.IGameSDKListener;
import com.samsung.android.gamesdk.IGameSDKService;

public class GameSDKManager {
    private static final String TAG = "GameSDKManager";
    /* access modifiers changed from: private */
    public Listener mListener = null;
    private IGameSDKService mService = null;

    public interface Listener {
        void onHighTempWarning(int i);

        void onReleasedByTimeout();
    }

    public GameSDKManager() {
        IBinder service = ServiceManager.getService("gamesdk");
        if (service != null) {
            this.mService = IGameSDKService.Stub.asInterface(service);
        }
    }

    public static boolean isAvailable() {
        return ServiceManager.getService("gamesdk") != null;
    }

    public int getTempLevel() {
        if (this.mService == null) {
            Log.w(TAG, "gamesdk system service is not available");
            return DatabaseError.UNKNOWN_ERROR;
        }
        try {
            return this.mService.getTempLevel();
        } catch (RemoteException e) {
            e.printStackTrace();
            return DatabaseError.UNKNOWN_ERROR;
        }
    }

    public String getVersion() {
        if (this.mService == null) {
            Log.w(TAG, "gamesdk system service is not available");
            return "0";
        }
        try {
            return this.mService.getVersion();
        } catch (RemoteException e) {
            e.printStackTrace();
            return "0";
        }
    }

    public boolean initialize() {
        if (this.mService == null) {
            Log.w(TAG, "gamesdk system service is not available");
            return false;
        }
        try {
            return this.mService.initGameSDK();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setLevelWithScene(String str, int i, int i2) {
        if (this.mService == null) {
            Log.w(TAG, "gamesdk system service is not available");
            return false;
        }
        try {
            return this.mService.setLevelWithScene(str, i, i2);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setListener(Listener listener) {
        this.mListener = listener;
        if (this.mService != null) {
            if (this.mListener == null) {
                try {
                    return this.mService.setGameSDKListener((IGameSDKListener) null);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    return this.mService.setGameSDKListener(new IGameSDKListener.Stub() {
                        public void onHighTempWarning(int i) {
                            GameSDKManager.this.mListener.onHighTempWarning(i);
                        }

                        public void onReleasedByTimeout() {
                            GameSDKManager.this.mListener.onReleasedByTimeout();
                        }
                    });
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return false;
    }
}
