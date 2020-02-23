package com.tencent.apollo;

public interface IGCloudVoiceNotify {

    public static final class GCloudVoiceEvent {
        public static final int EVENT_AUDIO_INTERRUPT_BEGIN = 50;
        public static final int EVENT_AUDIO_INTERRUPT_END = 51;
        public static final int EVENT_BLUETOOTH_HEADSET_CONNECTED = 21;
        public static final int EVENT_BLUETOOTH_HEADSET_DISCONNECTED = 20;
        public static final int EVENT_HEADSET_CONNECTED = 11;
        public static final int EVENT_HEADSET_DISCONNECTED = 10;
        public static final int EVENT_MIC_STATE_NO_OPEN = 32;
        public static final int EVENT_MIC_STATE_OPEN_ERR = 31;
        public static final int EVENT_MIC_STATE_OPEN_SUCC = 30;
        public static final int EVENT_NO_DEVICE_CONNECTED = 0;
        public static final int EVENT_SPEAKER_STATE_NO_OPEN = 42;
        public static final int EVENT_SPEAKER_STATE_OPEN_ERR = 41;
        public static final int EVENT_SPEAKER_STATE_OPEN_SUCC = 40;
    }

    void OnEvent(int i, String str);
}
