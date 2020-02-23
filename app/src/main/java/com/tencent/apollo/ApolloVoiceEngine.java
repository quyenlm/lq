package com.tencent.apollo;

public class ApolloVoiceEngine {

    public static final class BluetoothState {
        public static final int SCO_CONNECTED = 1;
        public static final int SCO_CONNECTING = 1;
        public static final int SCO_DIS_CONNECTED = 0;
        public static final int SCO_ERROR = -1;
        public static final int SCO_STATED = 10;
        public static final int SCO_STOPED = 20;
        public static final int UNINITIALIZED = -100;
    }

    public static final class DeviceState {
        public static final int AUDIO_DEVICE_BLUETOOTH_CONNECCTED = 2;
        public static final int AUDIO_DEVICE_UNCONNECTED = 0;
        public static final int AUDIO_DEVICE_WIREDHEADSET_CONNECCTED = 1;
    }

    public static final native boolean GetHeadsetVoipState();

    public static final native void OnEvent(int i, String str);

    public static final native int Pause();

    public static final native int Resume();

    public static final native void SetBluetoothState(boolean z);

    public static final native void SetHeadSetState(boolean z);

    public static final native int StartBlueTooth();

    public static final native int StopBlueTooth();

    static {
        try {
            System.loadLibrary("apollo_voice");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("load library failed!!!");
        }
    }
}
