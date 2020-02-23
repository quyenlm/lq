package com.tencent.ngame.utility;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import com.unity3d.player.UnityPlayer;

public class AudioUtility {
    private static final String NGAME_AUDIO_HEADSET_PLUG_METHOD_NAME = "OnHeadSetPlug";
    private static final String NGAME_AUDIO_MANAGER_OBJECT_NAME = "CSoundManager";
    private static final String NGAME_AUDIO_VOLUME_CHANGED_METHOD_NAME = "OnVolumeChanged";
    /* access modifiers changed from: private */
    public static ContentObserver m_Observer = null;
    private static BroadcastReceiver m_Receiver = null;

    private static class VolumeChangeObserver extends ContentObserver {
        private AudioManager m_AudioManager = null;
        private int m_Volume = -1;

        public VolumeChangeObserver(Activity activity) {
            super((Handler) null);
            this.m_AudioManager = (AudioManager) activity.getSystemService("audio");
            measure();
        }

        public boolean deliverSelfNotifications() {
            return false;
        }

        public void onChange(boolean selfChange) {
            measure();
        }

        public void measure() {
            int volume;
            if (this.m_AudioManager != null && (volume = this.m_AudioManager.getStreamVolume(3)) != this.m_Volume) {
                UnityPlayer.UnitySendMessage(AudioUtility.NGAME_AUDIO_MANAGER_OBJECT_NAME, AudioUtility.NGAME_AUDIO_VOLUME_CHANGED_METHOD_NAME, String.valueOf(volume));
                this.m_Volume = volume;
            }
        }
    }

    private static class HeadSetPlugReceiver extends BroadcastReceiver {
        private HeadSetPlugReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            measure(intent.getIntExtra("state", 0));
            if (AudioUtility.m_Observer != null) {
                AudioUtility.m_Observer.onChange(false);
            }
        }

        public void measure(int state) {
            UnityPlayer.UnitySendMessage(AudioUtility.NGAME_AUDIO_MANAGER_OBJECT_NAME, AudioUtility.NGAME_AUDIO_HEADSET_PLUG_METHOD_NAME, String.valueOf(state));
        }
    }

    public static void registerVolumeChange(Activity activity) {
        if (m_Observer == null) {
            m_Observer = new VolumeChangeObserver(activity);
            activity.getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, m_Observer);
            Log.d("NGameAudioUtility", "register volume change");
        }
    }

    public static void unregisterVolumeChange(Activity activity) {
        if (m_Observer != null) {
            activity.getContentResolver().unregisterContentObserver(m_Observer);
            m_Observer = null;
        }
    }

    public static void registerHeadSetPlug(Activity activity) {
        if (m_Receiver == null) {
            m_Receiver = new HeadSetPlugReceiver();
            activity.registerReceiver(m_Receiver, new IntentFilter("android.intent.action.HEADSET_PLUG"));
            Log.d("NGameAudioUtility", "register headset plug");
        }
    }

    public static void unregisterHeadSetPlug(Activity activity) {
        if (m_Receiver != null) {
            activity.unregisterReceiver(m_Receiver);
            m_Receiver = null;
        }
    }
}
