package com.tencent.apollo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

public class ApolloVoiceDeviceMgr {
    private static final int MODE_RESET = -2;
    private static final int MODE_SET_AUTO = -1;
    private static final int SCO_CHECK_INTERL = 2000;
    private static final int SCO_CHECK_TIME_MAX = 2;
    private static final String TAG = "apolloVoice";
    private static boolean bPermissionOK = false;
    private static String dataPath = null;
    private static Activity mActivity = null;
    /* access modifiers changed from: private */
    public static AudioDeviceListener mAudioDeviceListener = new AudioDeviceListener() {
        public synchronized void onStatus(int audioStatusEvent, String info) {
            Log.i(ApolloVoiceDeviceMgr.TAG, "--mAudioStatusEvent---:" + audioStatusEvent);
            ApolloVoiceEngine.OnEvent(audioStatusEvent, info);
            if (ApolloVoiceDeviceMgr.mGCloudVoiceNotify != null) {
                ApolloVoiceDeviceMgr.mGCloudVoiceNotify.OnEvent(audioStatusEvent, info);
            }
        }
    };
    private static AudioFocusChangeListener mAudioFocusChangeListener;
    private static AudioManager mAudioManager = null;
    /* access modifiers changed from: private */
    public static int mAudioStatusEvent = 0;
    /* access modifiers changed from: private */
    public static BluetoothAdapter mBluetoothAdapter = null;
    /* access modifiers changed from: private */
    public static boolean mBluetoothSCO = false;
    private static boolean mBluetoothSCOEnable = false;
    /* access modifiers changed from: private */
    public static int mBluetoothState = -100;
    /* access modifiers changed from: private */
    public static boolean mCheckDeviceFlag = false;
    private static Context mContext = null;
    private static boolean mCurrVoipState = false;
    /* access modifiers changed from: private */
    public static IGCloudVoiceNotify mGCloudVoiceNotify = null;
    private static GVoiceHandler mGVoiceHandler;
    private static GVoiceThread mGVoiceThread;
    private static BroadcastReceiver mHeadSetReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String info = "";
            try {
                if (intent.getAction().equals("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED")) {
                    int state = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                    if (state == 2) {
                        Log.i(ApolloVoiceDeviceMgr.TAG, "bluetooth connect ,cur state is " + state);
                        boolean unused = ApolloVoiceDeviceMgr.mIsBluetoothConnected = true;
                        boolean unused2 = ApolloVoiceDeviceMgr.mIsHeadsetConnected = false;
                        int unused3 = ApolloVoiceDeviceMgr.mAudioStatusEvent = 21;
                        info = "bluetooth headset connect";
                        ApolloVoiceEngine.SetBluetoothState(true);
                    } else if (state == 0) {
                        Log.i(ApolloVoiceDeviceMgr.TAG, "bluetooth disconnect,cur state is " + state);
                        boolean unused4 = ApolloVoiceDeviceMgr.mIsBluetoothConnected = false;
                        int unused5 = ApolloVoiceDeviceMgr.mAudioStatusEvent = 20;
                        info = "bluetooth headset disconnect";
                        ApolloVoiceEngine.SetBluetoothState(false);
                    } else if (state == 1) {
                        Log.i(ApolloVoiceDeviceMgr.TAG, "bluetoothHeadset connecting...");
                        return;
                    }
                    boolean unused6 = ApolloVoiceDeviceMgr.mCheckDeviceFlag = true;
                    if (!ApolloVoiceDeviceMgr.mIsMicOpen) {
                        ApolloVoiceDeviceMgr.ApolloVoiceSetDeviceConnection(ApolloVoiceDeviceMgr.mAudioStatusEvent);
                    }
                } else if (intent.getAction().equals("android.intent.action.HEADSET_PLUG")) {
                    if (intent.hasExtra("state")) {
                        int state2 = intent.getIntExtra("state", -1);
                        switch (state2) {
                            case 0:
                                Log.i(ApolloVoiceDeviceMgr.TAG, "headset disconnect ,cur state is " + state2);
                                boolean unused7 = ApolloVoiceDeviceMgr.mIsHeadsetConnected = false;
                                int unused8 = ApolloVoiceDeviceMgr.mAudioStatusEvent = 10;
                                info = "headset disconnect";
                                if (!ApolloVoiceDeviceMgr.mCheckDeviceFlag) {
                                    try {
                                        if (ApolloVoiceDeviceMgr.mBluetoothAdapter == null) {
                                            BluetoothAdapter unused9 = ApolloVoiceDeviceMgr.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                                        }
                                        if (2 == ApolloVoiceDeviceMgr.mBluetoothAdapter.getProfileConnectionState(1) || 2 == ApolloVoiceDeviceMgr.mBluetoothAdapter.getProfileConnectionState(2)) {
                                            int unused10 = ApolloVoiceDeviceMgr.mAudioStatusEvent = 21;
                                            boolean unused11 = ApolloVoiceDeviceMgr.mIsBluetoothConnected = true;
                                            boolean unused12 = ApolloVoiceDeviceMgr.mIsHeadsetConnected = false;
                                            info = "bluetooth headset connect";
                                            Log.e(ApolloVoiceDeviceMgr.TAG, "resetStatus:bluetooth headset connect,cur state is " + state2);
                                        } else {
                                            int unused13 = ApolloVoiceDeviceMgr.mAudioStatusEvent = 0;
                                            boolean unused14 = ApolloVoiceDeviceMgr.mIsBluetoothConnected = false;
                                            boolean unused15 = ApolloVoiceDeviceMgr.mIsHeadsetConnected = false;
                                            info = "no any devices is connected";
                                            Log.e(ApolloVoiceDeviceMgr.TAG, "no device,cur state is " + state2);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (!ApolloVoiceDeviceMgr.mIsBluetoothConnected) {
                                    if (!ApolloVoiceDeviceMgr.mIsHeadsetConnected) {
                                        ApolloVoiceEngine.SetHeadSetState(false);
                                        break;
                                    }
                                } else {
                                    ApolloVoiceEngine.SetBluetoothState(true);
                                    break;
                                }
                                break;
                            case 1:
                                Log.i(ApolloVoiceDeviceMgr.TAG, "headset connect ,cur state is " + state2);
                                boolean unused16 = ApolloVoiceDeviceMgr.mIsHeadsetConnected = true;
                                boolean unused17 = ApolloVoiceDeviceMgr.mIsBluetoothConnected = false;
                                int unused18 = ApolloVoiceDeviceMgr.mAudioStatusEvent = 11;
                                info = "headset connect";
                                ApolloVoiceEngine.SetHeadSetState(true);
                                break;
                        }
                        Log.e("framework", "BroadcastReceiver ACTION_HEADSET_PLUG onReceive bSetValue=" + ApolloVoiceDeviceMgr.mSpeakerphoneOn);
                    }
                    boolean unused19 = ApolloVoiceDeviceMgr.mCheckDeviceFlag = true;
                    if (!ApolloVoiceDeviceMgr.mIsBluetoothConnected && !ApolloVoiceDeviceMgr.mIsMicOpen) {
                        ApolloVoiceDeviceMgr.ApolloVoiceSetDeviceConnection(ApolloVoiceDeviceMgr.mAudioStatusEvent);
                    }
                } else if (intent.getAction().equals("android.media.ACTION_SCO_AUDIO_STATE_UPDATED")) {
                    int state3 = intent.getIntExtra("android.media.extra.SCO_AUDIO_STATE", 0);
                    Log.i(ApolloVoiceDeviceMgr.TAG, "ApolloVoiceDeviceManager ::SCO cur state is " + state3);
                    int unused20 = ApolloVoiceDeviceMgr.mBluetoothState = state3;
                    if (state3 == 1) {
                        boolean unused21 = ApolloVoiceDeviceMgr.mBluetoothSCO = true;
                        int unused22 = ApolloVoiceDeviceMgr.mSCOReConnecteTimes = 0;
                        return;
                    } else if (state3 == 0) {
                        boolean unused23 = ApolloVoiceDeviceMgr.mBluetoothSCO = false;
                        ApolloVoiceDeviceMgr.checkBluetoothSco();
                        return;
                    } else {
                        return;
                    }
                }
                if (intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                    if (10 == intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0)) {
                        Log.i(ApolloVoiceDeviceMgr.TAG, "bluetooth turn off,bluetooth disconnect,");
                        boolean unused24 = ApolloVoiceDeviceMgr.mIsBluetoothConnected = false;
                        int unused25 = ApolloVoiceDeviceMgr.mAudioStatusEvent = 20;
                        info = "bluetooth turn off,bluetooth headset disconnect";
                        ApolloVoiceEngine.SetBluetoothState(false);
                        if (!ApolloVoiceDeviceMgr.mIsMicOpen) {
                            ApolloVoiceDeviceMgr.ApolloVoiceSetDeviceConnection(ApolloVoiceDeviceMgr.mAudioStatusEvent);
                        }
                    } else {
                        return;
                    }
                }
                ApolloVoiceDeviceMgr.mAudioDeviceListener.onStatus(ApolloVoiceDeviceMgr.mAudioStatusEvent, info);
                int unused26 = ApolloVoiceDeviceMgr.mAudioStatusEvent = 0;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    };
    /* access modifiers changed from: private */
    public static boolean mIsBluetoothConnected = false;
    /* access modifiers changed from: private */
    public static boolean mIsHeadsetConnected = false;
    /* access modifiers changed from: private */
    public static boolean mIsMicOpen = false;
    private static boolean mIsMultiDeviceConnected = false;
    private static int mMode = -1;
    /* access modifiers changed from: private */
    public static int mSCOReConnecteTimes = 0;
    /* access modifiers changed from: private */
    public static boolean mScoThreadRunning = false;
    protected static boolean mSpeakerphoneOn = true;
    private static int maxVolCall = 0;
    private static int maxVolMusic = 0;

    static {
        try {
            System.loadLibrary("apollo_voice");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("load library failed!!!");
        }
    }

    public static boolean CheckManifestPermission() {
        if (mContext == null) {
            return false;
        }
        try {
            for (String x : mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 4096).requestedPermissions) {
                if (x.equals("android.permission.MODIFY_AUDIO_SETTINGS")) {
                    Log.i(TAG, "Check permission ok.");
                    bPermissionOK = true;
                    return true;
                }
                Log.e(TAG, x);
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "getPackageName throw an exception !");
            return true;
        }
    }

    public static void ApolloVoiceDeviceInit(Context ctx, Activity activity) {
        boolean z = false;
        Log.i(TAG, "GCloudVoice ApolloVoiceDeviceInit");
        if (mContext == null) {
            mContext = ctx;
            if (!CheckManifestPermission()) {
                Log.e(TAG, "Check the permissions GVoice needed!");
                return;
            }
            mActivity = activity;
            if (checkAudioManagerIsInit()) {
                try {
                    dataPath = mContext.getFilesDir().getAbsolutePath() + "/gcTestConfig.txt";
                } catch (Exception e) {
                    Log.i(TAG, "get path error,The exception is " + e.toString());
                }
                if (mAudioManager != null) {
                    try {
                        mAudioManager.setSpeakerphoneOn(true);
                        mSpeakerphoneOn = true;
                        AudioManager audioManager = mAudioManager;
                        AudioManager audioManager2 = mAudioManager;
                        maxVolMusic = audioManager.getStreamMaxVolume(3);
                        AudioManager audioManager3 = mAudioManager;
                        AudioManager audioManager4 = mAudioManager;
                        maxVolCall = audioManager3.getStreamMaxVolume(0);
                    } catch (Exception e2) {
                        Log.i(TAG, "Init failed!!! The exception is " + e2.toString());
                    }
                    Log.i(TAG, "GCloudVoice::max music " + maxVolMusic + "max call =  " + maxVolCall);
                }
                registerHeadsetPlugReceiver();
                ApolloVoiceConfig.SetContext(ctx);
                ApolloVoiceUDID.SetContext(ctx);
                ApolloVoiceNetStatus.SetContext(ctx);
                if (getAudioDeviceConnectionState() == 2) {
                    z = true;
                }
                mIsBluetoothConnected = z;
                if (mIsBluetoothConnected) {
                    ApolloVoiceEngine.SetBluetoothState(true);
                }
                Log.i(TAG, "apollovoicemanager:: getMode: " + mAudioManager.getMode());
                mAudioFocusChangeListener = new AudioFocusChangeListener();
                if (mAudioManager.requestAudioFocus(mAudioFocusChangeListener, 3, 1) == 1) {
                    Log.i(TAG, "requestAudioFocus successfully.");
                } else {
                    Log.e(TAG, "requestAudioFocus failed.");
                }
            }
        }
    }

    public static void ApolloVoiceDeviceUninit() {
        mActivity = null;
        if (mContext != null) {
            unregisterHeadsetPlugReceiver();
            mAudioManager.setMode(0);
            mAudioManager = null;
            mContext = null;
        }
    }

    public static void ApolloVoiceDeviceExitVoipMode() {
        Log.i(TAG, "apolloVoice ApolloVoiceDeviceExitVoipMode");
        if (checkAudioManagerIsInit()) {
            mAudioManager.setMode(0);
            if (IsHeadSet()) {
                mAudioManager.setSpeakerphoneOn(false);
            } else {
                mAudioManager.setSpeakerphoneOn(mSpeakerphoneOn);
            }
        }
    }

    public static void ApolloVoiceSetBluetooth(boolean bluetoothStart) {
        if (checkAudioManagerIsInit()) {
            try {
                if (mAudioManager.isBluetoothScoAvailableOffCall()) {
                    Log.i(TAG, "ApolloVoiceSetBluetooth,bluetoothStart:" + bluetoothStart + ",Mode:" + mAudioManager.getMode() + ",ScoOn:" + mAudioManager.isBluetoothScoOn() + " mBluetoothState:" + mBluetoothState);
                }
                if (bluetoothStart) {
                    mAudioManager.setSpeakerphoneOn(false);
                    if (mAudioManager.isBluetoothScoAvailableOffCall()) {
                        if (mAudioManager.isBluetoothScoOn()) {
                            mAudioManager.setBluetoothScoOn(false);
                            mAudioManager.stopBluetoothSco();
                        }
                        if (mAudioManager.getMode() != 3) {
                            mAudioManager.setMode(3);
                        }
                        if (!mAudioManager.isBluetoothScoOn()) {
                            mAudioManager.setBluetoothScoOn(true);
                            mAudioManager.startBluetoothSco();
                            mBluetoothState = 10;
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (mAudioManager.getMode() != 0) {
                    mAudioManager.setMode(0);
                }
                if (mAudioManager.isBluetoothScoAvailableOffCall() && mAudioManager.isBluetoothScoOn()) {
                    mAudioManager.setBluetoothScoOn(false);
                    mAudioManager.stopBluetoothSco();
                    mBluetoothState = 20;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean IsHeadSet() {
        if (mAudioManager != null) {
            return mAudioManager.isWiredHeadsetOn();
        }
        return false;
    }

    public static void ApolloVoiceSetDeviceConnection(int audioStatusEvent) {
        int state = getAudioDeviceConnectionState();
        Log.d(TAG, "ApolloVoiceSetDeviceConnection,mMode:" + mMode + " state:" + state + " audioStatusEvent:" + audioStatusEvent + " isMicOpen:" + mIsMicOpen + " multi:" + mIsMultiDeviceConnected);
        if (checkAudioManagerIsInit()) {
            try {
                if (-2 == mMode) {
                    mMode = -1;
                    if (mAudioManager.getMode() != 0) {
                        mAudioManager.setMode(0);
                    }
                    if (state == 2) {
                        if (!mIsMicOpen) {
                            ApolloVoiceSetBluetooth(false);
                            return;
                        }
                        return;
                    }
                }
                switch (audioStatusEvent) {
                    case 10:
                        if (state == 2) {
                            if (mIsMicOpen && mBluetoothSCOEnable) {
                                ApolloVoiceSetBluetooth(true);
                                break;
                            } else {
                                ApolloVoiceSetBluetooth(false);
                                break;
                            }
                        }
                        break;
                    case 11:
                        if (mIsMultiDeviceConnected) {
                            Log.d(TAG, "ApolloVoiceSetDeviceConnection, headsetConcected mIsMultiDeviceConnected:" + mIsMultiDeviceConnected);
                            ApolloVoiceSetBluetooth(false);
                            break;
                        }
                        break;
                    case 20:
                        ApolloVoiceSetBluetooth(false);
                        break;
                    case 21:
                        if (mIsMicOpen && mBluetoothSCOEnable) {
                            ApolloVoiceSetBluetooth(true);
                            break;
                        } else {
                            ApolloVoiceSetBluetooth(false);
                            break;
                        }
                        break;
                }
                if (state == 0) {
                    if (mMode > -1) {
                        if (mAudioManager.getMode() != mMode) {
                            mAudioManager.setMode(mMode);
                        }
                    } else if (mIsMicOpen) {
                        if (mAudioManager.getMode() != 3) {
                            mAudioManager.setMode(3);
                        }
                    } else if (mAudioManager.getMode() != 0) {
                        mAudioManager.setMode(0);
                    }
                    if (mSpeakerphoneOn) {
                        if (mAudioManager.isBluetoothScoAvailableOffCall()) {
                            mAudioManager.setBluetoothScoOn(false);
                        }
                        mAudioManager.setWiredHeadsetOn(false);
                        mAudioManager.setSpeakerphoneOn(true);
                    } else {
                        mAudioManager.setSpeakerphoneOn(false);
                    }
                } else if (state == 1) {
                    if (mMode > -1) {
                        if (mAudioManager.getMode() != mMode) {
                            mAudioManager.setMode(mMode);
                        }
                    } else if (mAudioManager.getMode() != 0) {
                        mAudioManager.setMode(0);
                    }
                    mAudioManager.setSpeakerphoneOn(false);
                    if (mAudioManager.isBluetoothScoAvailableOffCall()) {
                        mAudioManager.setBluetoothScoOn(false);
                    }
                    mAudioManager.setWiredHeadsetOn(true);
                } else if (state == 2) {
                    if (mMode > -1) {
                        if (mAudioManager.getMode() != mMode) {
                            mAudioManager.setMode(mMode);
                        }
                    } else if (!mIsMicOpen || !mBluetoothSCOEnable) {
                        ApolloVoiceSetBluetooth(false);
                    } else {
                        ApolloVoiceSetBluetooth(true);
                    }
                    mAudioManager.setSpeakerphoneOn(false);
                    mAudioManager.setWiredHeadsetOn(false);
                }
                Log.d(TAG, "ApolloVoiceSetDeviceConnection after,mode:" + mAudioManager.getMode() + ",ScoOn:" + mAudioManager.isBluetoothScoOn() + " speaker:" + mAudioManager.isSpeakerphoneOn() + " mBluetoothState:" + mBluetoothState);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int ApolloVoiceGetCurrMode() {
        if (mAudioManager != null) {
            return mAudioManager.getMode();
        }
        return -2;
    }

    public static void ApolloVoiceDeviceEnterVoipMode(int nMode) {
        Log.i(TAG, "apolloVoice ApolloVoiceDeviceEnterVoipMode nMode=" + nMode);
        if (checkAudioManagerIsInit()) {
            mAudioManager.setMode(3);
            mAudioManager.setSpeakerphoneOn(mSpeakerphoneOn);
        }
    }

    private static void unregisterHeadsetPlugReceiver() {
        if (mContext != null) {
            try {
                mContext.unregisterReceiver(mHeadSetReceiver);
            } catch (Exception e) {
                Log.i(TAG, "Registe headset failed!!! The exception is " + e.toString());
            }
        }
    }

    private static void registerHeadsetPlugReceiver() {
        if (mContext != null) {
            try {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.HEADSET_PLUG");
                intentFilter.addAction("android.media.ACTION_SCO_AUDIO_STATE_UPDATED");
                intentFilter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
                if ("EVA-AL00".equals(Build.MODEL) || "Nexus 6P".equals(Build.MODEL)) {
                    intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
                }
                mContext.registerReceiver(mHeadSetReceiver, intentFilter);
            } catch (Exception e) {
                Log.i(TAG, "Registe headset failed!!! The exception is " + e.toString());
            }
        }
    }

    public static void SetpreVoipMode(int mode) {
        boolean z = true;
        if (mode != 1) {
            z = false;
        }
        mCurrVoipState = z;
    }

    public static void ApolloVoiceSetSpeakerOn(boolean bSet) {
        Log.i(TAG, "apolloVoiceDevice::SetSpeakerOn is " + bSet);
        if (checkAudioManagerIsInit()) {
            if (getAudioDeviceConnectionState() != 0) {
                mAudioManager.setSpeakerphoneOn(false);
                return;
            }
            mAudioManager.setSpeakerphoneOn(bSet);
            mSpeakerphoneOn = bSet;
        }
    }

    public static boolean ApolloVoiceDeviceSetMode(int mode) {
        Log.i(TAG, "ApolloVoiceDeviceSetMode mode:" + mode);
        if (!checkAudioManagerIsInit()) {
            return false;
        }
        mMode = mode;
        ApolloVoiceSetDeviceConnection(-1);
        return true;
    }

    public static void ApolloVoiceSetMicState(boolean isMicOpen) {
        mIsMicOpen = isMicOpen;
        Log.i(TAG, "SetMicOpen:" + isMicOpen);
        if (!isMicOpen) {
            try {
                if (mGVoiceHandler != null) {
                    mGVoiceHandler.removeCallbacksAndMessages((Object) null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean HaveMicrophonePermission() {
        if (mContext == null || !bPermissionOK) {
            return false;
        }
        int taragetSdkVersion = 23;
        try {
            taragetSdkVersion = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).applicationInfo.targetSdkVersion;
            Log.i(TAG, "targetSdkVersion = " + taragetSdkVersion);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Can't find package : " + mContext.getPackageName());
        }
        if (taragetSdkVersion >= 23 || CheckPermiss()) {
            Log.i(TAG, "buildVersion = " + Build.VERSION.SDK_INT);
            if (Build.VERSION.SDK_INT < 23) {
                return true;
            }
            if (ContextCompat.checkSelfPermission(mContext, "android.permission.RECORD_AUDIO") != 0) {
                Log.e(TAG, "No microphone permission");
                ActivityCompat.requestPermissions(mActivity, new String[]{"android.permission.RECORD_AUDIO"}, 100);
                return false;
            }
            Log.i(TAG, "Have microphone permission");
            return true;
        }
        Log.e(TAG, "NO Have microphone permission");
        return false;
    }

    public static boolean CheckPermiss() {
        try {
            if (PermissionChecker.checkSelfPermission(mContext, "android.permission.RECORD_AUDIO") == 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.w(TAG, "CheckPermiss get an exception !");
            return false;
        }
    }

    public static boolean checkAudioManagerIsInit() {
        if (mAudioManager != null) {
            return true;
        }
        if (mContext != null) {
            mAudioManager = (AudioManager) mContext.getSystemService("audio");
            if (mAudioManager != null) {
                return true;
            }
            Log.i(TAG, "apolloVoiceDevice::get AudioManager null....\n");
            return false;
        }
        Log.i(TAG, "apolloVoiceDevice::context is null....\n");
        return false;
    }

    public static void setGCloudVoiceNotify(IGCloudVoiceNotify gCloudVoiceNotify) {
        mGCloudVoiceNotify = gCloudVoiceNotify;
    }

    private static class AudioFocusChangeListener implements AudioManager.OnAudioFocusChangeListener {
        private AudioFocusChangeListener() {
        }

        public void onAudioFocusChange(int focusChange) {
            Log.i(ApolloVoiceDeviceMgr.TAG, "mAudioStatusEvent focusChange:" + focusChange);
            if (focusChange == -2 || focusChange == -3) {
                ApolloVoiceDeviceMgr.mAudioDeviceListener.onStatus(50, "audio interrupt start.");
            } else if (focusChange == 1) {
                ApolloVoiceDeviceMgr.ApolloVoiceSetDeviceConnection(-1);
                ApolloVoiceDeviceMgr.checkBluetoothSco();
                ApolloVoiceDeviceMgr.mAudioDeviceListener.onStatus(51, "audio interrupt end.");
            }
        }
    }

    public static int getAudioDeviceConnectionState() {
        int state = 0;
        boolean isWiredHeadSet = false;
        boolean isBluebooth = false;
        if (checkAudioManagerIsInit()) {
            if (mAudioManager.isWiredHeadsetOn()) {
                state = 1;
                isWiredHeadSet = true;
            }
            if (mBluetoothAdapter == null) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            }
            if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
                int a2dp = mBluetoothAdapter.getProfileConnectionState(2);
                int headset = mBluetoothAdapter.getProfileConnectionState(1);
                int health = mBluetoothAdapter.getProfileConnectionState(3);
                if (headset == 2) {
                    state = 2;
                    isBluebooth = true;
                }
                if (!isWiredHeadSet || !isBluebooth) {
                    mIsMultiDeviceConnected = false;
                } else {
                    mIsMultiDeviceConnected = true;
                    if (mIsHeadsetConnected && !mIsBluetoothConnected) {
                        state = 1;
                        Log.w(TAG, "getHeadsetDeviceStatus: wiredheadset actually!");
                    } else if (!mIsHeadsetConnected && mIsBluetoothConnected) {
                        state = 2;
                        Log.w(TAG, "getHeadsetDeviceStatus: bluetooth actually!");
                    }
                }
                Log.v(TAG, "getHeadsetDeviceStatus state:" + state + " a2dp:" + a2dp + " headset:" + headset + " health:" + health + " mIsHeadsetConnected:" + mIsHeadsetConnected + " mIsBluetoothConnected:" + mIsBluetoothConnected);
            }
        }
        return state;
    }

    private static final class GVoiceHandler extends Handler {
        private GVoiceHandler() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    /* access modifiers changed from: private */
    public static void checkBluetoothSco() {
        if (2 == getAudioDeviceConnectionState() && mIsMicOpen && mBluetoothSCOEnable) {
            if (mSCOReConnecteTimes > 2) {
                ApolloVoiceSetBluetooth(false);
            } else if (!mScoThreadRunning) {
                Log.i(TAG, "checkBluetoothSco Thread Start! times:" + mSCOReConnecteTimes);
                if (mGVoiceHandler == null) {
                    mGVoiceHandler = new GVoiceHandler();
                }
                if (mGVoiceThread == null) {
                    mGVoiceThread = new GVoiceThread();
                }
                mGVoiceHandler.postDelayed(mGVoiceThread, 2000);
                mScoThreadRunning = true;
                mSCOReConnecteTimes++;
            }
        }
    }

    private static final class GVoiceThread implements Runnable {
        private GVoiceThread() {
        }

        public void run() {
            if (!ApolloVoiceDeviceMgr.mBluetoothSCO) {
                Log.w(ApolloVoiceDeviceMgr.TAG, "thread bluethooth sco check!");
                ApolloVoiceDeviceMgr.ApolloVoiceSetDeviceConnection(-1);
                boolean unused = ApolloVoiceDeviceMgr.mScoThreadRunning = false;
            }
        }
    }

    public static void SetBluetoothSCOEnable(boolean bluetoothSCOEnable) {
        Log.i(TAG, "bluetoothSCOEnable:" + bluetoothSCOEnable);
        mBluetoothSCOEnable = bluetoothSCOEnable;
        if (!bluetoothSCOEnable) {
            ApolloVoiceSetBluetooth(false);
        }
    }
}
