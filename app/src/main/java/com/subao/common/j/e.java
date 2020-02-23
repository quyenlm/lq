package com.subao.common.j;

import android.support.annotation.NonNull;
import com.subao.common.e.n;
import java.nio.ByteOrder;

/* compiled from: IPv4 */
public class e {
    public static final boolean a = ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN);

    @NonNull
    public static String a(byte[] bArr) {
        if (bArr == null || bArr.length != 4) {
            return "";
        }
        return String.format(n.b, "%d.%d.%d.%d", new Object[]{Integer.valueOf(bArr[0] & 255), Integer.valueOf(bArr[1] & 255), Integer.valueOf(bArr[2] & 255), Integer.valueOf(bArr[3] & 255)});
    }
}
