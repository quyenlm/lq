package com.subao.common.b;

import android.util.JsonWriter;
import com.subao.common.c;
import com.tencent.midas.oversea.api.UnityPayHelper;

/* compiled from: OrdersReq */
public class f implements c {
    public final String a;
    public final int b;

    public f(String str, int i) {
        this.a = str;
        this.b = i;
    }

    public void serialize(JsonWriter jsonWriter) {
        jsonWriter.beginObject();
        com.subao.common.n.f.a(jsonWriter, UnityPayHelper.PRODUCTID, this.a);
        jsonWriter.name("num").value((long) this.b);
        jsonWriter.endObject();
    }
}
