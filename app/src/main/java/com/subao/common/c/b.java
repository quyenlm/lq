package com.subao.common.c;

import android.support.annotation.NonNull;
import android.util.JsonWriter;
import com.subao.common.c;
import com.tencent.midas.oversea.api.UnityPayHelper;

/* compiled from: OrderRequestParam */
public class b implements c {
    @NonNull
    public final String a;
    public final int b;

    public b(@NonNull String str, int i) {
        this.a = str;
        this.b = i;
    }

    public void serialize(JsonWriter jsonWriter) {
        jsonWriter.beginObject();
        jsonWriter.name(UnityPayHelper.PRODUCTID).value(this.a);
        jsonWriter.name("num").value((long) this.b);
        jsonWriter.endObject();
    }
}
