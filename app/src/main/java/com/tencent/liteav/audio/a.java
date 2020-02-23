package com.tencent.liteav.audio;

import android.content.Context;
import android.media.AudioManager;
import com.tencent.liteav.basic.log.TXCLog;

/* compiled from: TXCAudioConfig */
public class a {
    static {
        com.tencent.liteav.basic.util.a.d();
    }

    public static void a(Context context, int i) {
        if (context != null) {
            AudioManager audioManager = (AudioManager) context.getSystemService("audio");
            if (i == TXEAudioDef.TXE_AUDIO_MODE_RECEIVER) {
                audioManager.setMode(3);
                audioManager.setSpeakerphoneOn(false);
                TXCLog.i("TXCAudioConfig", "AudioCenter setAudioMode to receiver");
                return;
            }
            audioManager.setMode(0);
            audioManager.setSpeakerphoneOn(true);
            TXCLog.i("TXCAudioConfig", "AudioCenter setAudioMode to speaker");
        }
    }
}
