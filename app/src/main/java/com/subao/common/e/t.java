package com.subao.common.e;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.JsonReader;
import com.subao.common.e;
import com.subao.common.e.s;
import com.subao.common.intf.RequestTwiceTrialCallback;
import com.tencent.midas.oversea.api.UnityPayHelper;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;

/* compiled from: HRTwiceTrialCouponExchange */
public class t extends q {
    @Nullable
    private RequestTwiceTrialCallback c;

    public t(@NonNull s.a aVar, @NonNull s.d dVar, @NonNull String str, @Nullable RequestTwiceTrialCallback requestTwiceTrialCallback) {
        super(aVar, dVar, str);
        this.c = requestTwiceTrialCallback;
    }

    /* access modifiers changed from: protected */
    public int a() {
        return 0;
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"DefaultLocale"})
    public void a(@Nullable s.b bVar) {
        super.a(bVar);
        if (bVar == null || bVar.b == null) {
            a(1006, (String) null);
        } else if (202 == bVar.b.a) {
            a(0, (String) null);
        } else {
            int a = a(bVar.b.b);
            a(1008, String.format(n.b, "%d.%d", new Object[]{Integer.valueOf(bVar.b.a), Integer.valueOf(a)}));
        }
    }

    private static int a(byte[] bArr) {
        int i = -1;
        if (bArr != null && bArr.length > 2) {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(bArr)));
            try {
                jsonReader.beginObject();
                while (true) {
                    if (!jsonReader.hasNext()) {
                        break;
                    } else if (UnityPayHelper.RES_CODE.equals(jsonReader.nextName())) {
                        i = jsonReader.nextInt();
                        break;
                    } else {
                        jsonReader.skipValue();
                    }
                }
            } catch (IOException e) {
            } finally {
                e.a((Closeable) jsonReader);
            }
        }
        return i;
    }

    private void a(int i, @Nullable String str) {
        if (this.c != null) {
            this.c.onRequestTwiceTrialResult(i, str);
        }
    }
}
