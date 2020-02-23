package com.google.android.gms.nearby.messages.audio;

import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.nearby.messages.Message;
import java.util.Arrays;

public final class AudioBytes {
    public static final int MAX_SIZE = 10;
    private final byte[] zzbyC;

    public AudioBytes(byte[] bArr) {
        boolean z = true;
        zzbo.zzu(bArr);
        zzbo.zzb(bArr.length <= 10, (Object) "Given byte array longer than 10 bytes, given by AudioBytes.MAX_SIZE.");
        zzbo.zzb(bArr.length <= 0 ? false : z, (Object) "Given byte array is of zero length.");
        this.zzbyC = bArr;
    }

    public static AudioBytes from(Message message) {
        zzbo.zzu(message);
        boolean zzeD = message.zzeD(Message.MESSAGE_TYPE_AUDIO_BYTES);
        String valueOf = String.valueOf(message.getType());
        zzbo.zzb(zzeD, (Object) new StringBuilder(String.valueOf(valueOf).length() + 56).append("Message type '").append(valueOf).append("' is not Message.MESSAGE_TYPE_AUDIO_BYTES.").toString());
        return new AudioBytes(message.getContent());
    }

    public final byte[] getBytes() {
        return this.zzbyC;
    }

    public final Message toMessage() {
        return new Message(this.zzbyC, Message.MESSAGE_NAMESPACE_RESERVED, Message.MESSAGE_TYPE_AUDIO_BYTES);
    }

    public final String toString() {
        String valueOf = String.valueOf(Arrays.toString(this.zzbyC));
        return new StringBuilder(String.valueOf(valueOf).length() + 14).append("AudioBytes [").append(valueOf).append(" ]").toString();
    }
}
