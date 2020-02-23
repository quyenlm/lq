package com.tencent.liteav.audio.impl.Record;

/* compiled from: TXIAudioPcmRecordListener */
public interface g {
    void onAudioRecordError(int i, String str);

    void onAudioRecordPCM(byte[] bArr, int i, long j);

    void onAudioRecordStart();

    void onAudioRecordStop();
}
