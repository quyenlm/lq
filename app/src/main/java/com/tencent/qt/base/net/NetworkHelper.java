package com.tencent.qt.base.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NetworkHelper {
    private static final String TAG = "NetworkHelper";
    List<WeakReference<NetworkInductor>> mInductors;
    NetworkBroadcastReceiver mReceiver;
    boolean mRegistered;
    NetworkStatus mStatus;

    public interface NetworkInductor {
        void onNetworkChanged(NetworkStatus networkStatus);
    }

    public enum NetworkStatus {
        NetworkNotReachable,
        NetworkReachableViaWWAN,
        NetworkReachableViaWiFi
    }

    /* access modifiers changed from: private */
    public static native void native_set_network_status(int i);

    private static class HelperHolder {
        /* access modifiers changed from: private */
        public static final NetworkHelper helper = new NetworkHelper();

        private HelperHolder() {
        }
    }

    public static NetworkHelper sharedHelper() {
        return HelperHolder.helper;
    }

    private static void load() {
        GlobalPref.getInstant().loadLibary();
    }

    private NetworkHelper() {
        this.mRegistered = false;
        this.mStatus = NetworkStatus.NetworkNotReachable;
        this.mReceiver = new NetworkBroadcastReceiver();
        load();
        this.mInductors = new LinkedList();
    }

    public void registerNetworkSensor(Context context) {
        PLog.v(TAG, "registerNetworkSensor", new Object[0]);
        if (!this.mRegistered && context != null) {
            this.mRegistered = true;
            NetworkInfo info = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (info == null || !info.isAvailable()) {
                PLog.i(TAG, "network not reachable", new Object[0]);
                this.mStatus = NetworkStatus.NetworkNotReachable;
            } else if (info.getType() == 0) {
                PLog.i(TAG, "network reachable via wwan", new Object[0]);
                this.mStatus = NetworkStatus.NetworkReachableViaWWAN;
            } else if (info.getType() == 1) {
                PLog.i(TAG, "network reachable via wifi", new Object[0]);
                this.mStatus = NetworkStatus.NetworkReachableViaWiFi;
            }
            try {
                native_set_network_status(this.mStatus.ordinal());
            } catch (UnsatisfiedLinkError e) {
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            context.registerReceiver(this.mReceiver, intentFilter);
        }
    }

    public void unregisterNetworkSensor(Context context) {
        if (this.mRegistered) {
            this.mRegistered = false;
            context.unregisterReceiver(this.mReceiver);
        }
    }

    public NetworkStatus getNetworkStatus() {
        return this.mStatus;
    }

    public void addNetworkInductor(NetworkInductor inductor) {
        List<WeakReference<NetworkInductor>> list = new ArrayList<>(this.mInductors);
        int i = 0;
        while (i < list.size()) {
            WeakReference<NetworkInductor> inductorRef = list.get(i);
            NetworkInductor ind = (NetworkInductor) inductorRef.get();
            if (ind != inductor) {
                if (ind == null) {
                    this.mInductors.remove(inductorRef);
                }
                i++;
            } else {
                return;
            }
        }
        this.mInductors.add(new WeakReference(inductor));
    }

    public void removeNetworkInductor(NetworkInductor inductor) {
        List<WeakReference<NetworkInductor>> list = new ArrayList<>(this.mInductors);
        for (int i = 0; i < list.size(); i++) {
            WeakReference<NetworkInductor> inductorRef = list.get(i);
            NetworkInductor ind = (NetworkInductor) inductorRef.get();
            if (ind == inductor) {
                this.mInductors.remove(inductorRef);
                return;
            }
            if (ind == null) {
                this.mInductors.remove(inductorRef);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onNetworkChanged() {
        if (this.mInductors.size() != 0) {
            List<WeakReference<NetworkInductor>> list = new ArrayList<>(this.mInductors);
            for (int i = 0; i < list.size(); i++) {
                WeakReference<NetworkInductor> inductorRef = list.get(i);
                NetworkInductor inductor = (NetworkInductor) inductorRef.get();
                if (inductor != null) {
                    inductor.onNetworkChanged(this.mStatus);
                } else {
                    this.mInductors.remove(inductorRef);
                }
            }
        }
    }

    protected class NetworkBroadcastReceiver extends BroadcastReceiver {
        protected NetworkBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            PLog.v("NetworkBroadcastReceiver", "onReceive", new Object[0]);
            if (intent != null && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                NetworkInfo info = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                NetworkStatus ns = NetworkStatus.NetworkNotReachable;
                if (info == null || !info.isAvailable()) {
                    PLog.i("NetworkBroadcastReceiver", "network not reachable", new Object[0]);
                    ns = NetworkStatus.NetworkNotReachable;
                } else if (info.getType() == 0) {
                    PLog.i("NetworkBroadcastReceiver", "network reachable via wwan", new Object[0]);
                    ns = NetworkStatus.NetworkReachableViaWWAN;
                } else if (info.getType() == 1) {
                    PLog.i("NetworkBroadcastReceiver", "network reachable via wifi", new Object[0]);
                    ns = NetworkStatus.NetworkReachableViaWiFi;
                }
                if (!NetworkHelper.this.mStatus.equals(ns)) {
                    NetworkHelper.this.mStatus = ns;
                    try {
                        NetworkHelper.native_set_network_status(NetworkHelper.this.mStatus.ordinal());
                    } catch (UnsatisfiedLinkError e) {
                    }
                    NetworkHelper.this.onNetworkChanged();
                }
            }
        }
    }
}
