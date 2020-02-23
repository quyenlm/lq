package com.tencent.liteav.audio.impl;

import com.google.android.gms.safetynet.SafetyNetStatusCodes;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.audio.impl.Record.f;

/* compiled from: TXCAudioUtil */
public class a {
    private static int[] a = {96000, 88200, 64000, 48000, 44100, 32000, 24000, 22050, 16000, SafetyNetStatusCodes.SAFE_BROWSING_UNSUPPORTED_THREAT_TYPES, 11025, 8000, 7350};

    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder("");
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append("0x");
            sb.append(hexString);
        }
        return sb.toString();
    }

    public static int a(int i) {
        if (i >= a.length || i < 0) {
            return 0;
        }
        return a[i];
    }

    public static int b(int i) {
        if (i == TXEAudioDef.TXE_AEC_TRAE) {
            if (f.a().c()) {
                return TXEAudioDef.TXE_AUDIO_RECORD_ERR_CUR_RECORDER_INVALID;
            }
        } else if (TXCTraeJNI.nativeTraeIsRecording()) {
            return TXEAudioDef.TXE_AUDIO_RECORD_ERR_CUR_RECORDER_INVALID;
        }
        return TXEAudioDef.TXE_AUDIO_COMMON_ERR_OK;
    }
}
