package com.google.android.gms.internal;

import android.os.Parcel;
import android.util.Base64;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@zzzn
final class zztr {
    final int zzKt;
    final String zztV;
    final zzir zzuT;

    private zztr(zzir zzir, String str, int i) {
        this.zzuT = zzir;
        this.zztV = str;
        this.zzKt = i;
    }

    zztr(zztn zztn) {
        this(zztn.zzeI(), zztn.getAdUnitId(), zztn.getNetworkType());
    }

    static zztr zzab(String str) throws IOException {
        String[] split = str.split("\u0000");
        if (split.length != 3) {
            throw new IOException("Incorrect field count for QueueSeed.");
        }
        Parcel obtain = Parcel.obtain();
        try {
            String str2 = new String(Base64.decode(split[0], 0), "UTF-8");
            int parseInt = Integer.parseInt(split[1]);
            byte[] decode = Base64.decode(split[2], 0);
            obtain.unmarshall(decode, 0, decode.length);
            obtain.setDataPosition(0);
            zztr zztr = new zztr((zzir) zzir.CREATOR.createFromParcel(obtain), str2, parseInt);
            obtain.recycle();
            return zztr;
        } catch (Throwable th) {
            obtain.recycle();
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public final String zzeW() {
        Parcel obtain = Parcel.obtain();
        try {
            String encodeToString = Base64.encodeToString(this.zztV.getBytes("UTF-8"), 0);
            String num = Integer.toString(this.zzKt);
            this.zzuT.writeToParcel(obtain, 0);
            String encodeToString2 = Base64.encodeToString(obtain.marshall(), 0);
            String sb = new StringBuilder(String.valueOf(encodeToString).length() + 2 + String.valueOf(num).length() + String.valueOf(encodeToString2).length()).append(encodeToString).append("\u0000").append(num).append("\u0000").append(encodeToString2).toString();
            obtain.recycle();
            return sb;
        } catch (UnsupportedEncodingException e) {
            zzafr.e("QueueSeed encode failed because UTF-8 is not available.");
            obtain.recycle();
            return "";
        } catch (Throwable th) {
            obtain.recycle();
            throw th;
        }
    }
}
