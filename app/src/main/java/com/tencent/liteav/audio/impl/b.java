package com.tencent.liteav.audio.impl;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.audio.c;
import com.tencent.liteav.basic.log.TXCLog;
import java.util.List;

/* compiled from: TXCHeadsetMgr */
public class b {
    /* access modifiers changed from: private */
    public static final String a = b.class.getSimpleName();
    private Context b;
    private BroadcastReceiver c;
    private boolean d = false;
    /* access modifiers changed from: private */
    public BluetoothHeadset e;
    private BluetoothProfile.ServiceListener f;
    /* access modifiers changed from: private */
    public d g;

    public b(Context context) {
        this.b = context.getApplicationContext();
        this.c = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (!intent.getAction().equals("android.intent.action.HEADSET_PLUG")) {
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    if (bluetoothDevice != null && b.this.e != null) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        b.this.a(bluetoothDevice);
                    }
                } else if (!intent.hasExtra("state")) {
                } else {
                    if (intent.getIntExtra("state", 0) == 0) {
                        if (c.a().b() != TXEAudioDef.TXE_AEC_SYSTEM) {
                            if (b.this.g != null) {
                                b.this.g.OnHeadsetState(false);
                            }
                        } else if (b.this.g != null) {
                            b.this.g.OnHeadsetState(true);
                        }
                        TXCLog.d(b.a, "耳机拔出");
                    } else if (1 == intent.getIntExtra("state", 0)) {
                        if (b.this.g != null) {
                            b.this.g.OnHeadsetState(true);
                        }
                        TXCLog.d(b.a, "耳机插入");
                    }
                }
            }
        };
        this.f = new BluetoothProfile.ServiceListener() {
            public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                if (i == 1) {
                    BluetoothHeadset unused = b.this.e = (BluetoothHeadset) bluetoothProfile;
                    List<BluetoothDevice> connectedDevices = b.this.e.getConnectedDevices();
                    if (connectedDevices != null && connectedDevices.size() > 0) {
                        b.this.a(connectedDevices.get(0));
                    }
                }
            }

            public void onServiceDisconnected(int i) {
                if (i == 1) {
                    BluetoothHeadset unused = b.this.e = null;
                }
            }
        };
        boolean isWiredHeadsetOn = ((AudioManager) this.b.getSystemService("audio")).isWiredHeadsetOn();
        if (this.g != null) {
            this.g.OnHeadsetState(isWiredHeadsetOn);
        }
        this.d = false;
    }

    public void a(d dVar) {
        this.g = dVar;
        if (this.d) {
            TXCLog.w(a, " repeate register headset, ignore");
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.HEADSET_PLUG");
        intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        this.b.registerReceiver(this.c, intentFilter);
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null) {
                defaultAdapter.getProfileProxy(this.b, this.f, 1);
            }
        } catch (Exception e2) {
            TXCLog.e(a, "BluetoothAdapter getProfileProxy: " + e2);
        }
        this.d = true;
    }

    public void a() {
        this.g = null;
        if (!this.d) {
            TXCLog.w(a, " invalid unregister headset, ignore");
            return;
        }
        this.d = false;
        this.b.unregisterReceiver(this.c);
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null) {
                defaultAdapter.closeProfileProxy(1, this.e);
            }
        } catch (Exception e2) {
            TXCLog.e(a, "BluetoothAdapter closeProfileProxy: " + e2);
        }
    }

    /* access modifiers changed from: private */
    public void a(BluetoothDevice bluetoothDevice) {
        int i;
        if (bluetoothDevice != null && this.e != null) {
            try {
                i = this.e.getConnectionState(bluetoothDevice);
            } catch (Exception e2) {
                TXCLog.e(a, "getConnectionState exception: " + e2);
                i = 0;
            }
            TXCLog.d(a, "蓝牙耳机状态：" + i);
            switch (i) {
                case 0:
                    if (c.a().b() != TXEAudioDef.TXE_AEC_SYSTEM) {
                        if (this.g != null) {
                            this.g.OnHeadsetState(false);
                        }
                    } else if (this.g != null) {
                        this.g.OnHeadsetState(true);
                    }
                    TXCLog.d(a, "蓝牙耳机拔出");
                    return;
                case 2:
                    if (this.g != null) {
                        this.g.OnHeadsetState(true);
                    }
                    TXCLog.d(a, "蓝牙耳机插入");
                    return;
                default:
                    return;
            }
        }
    }
}
