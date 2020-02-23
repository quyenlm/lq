package com.subao.common.e;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.JsonReader;
import com.subao.common.e;
import com.subao.common.e.z;
import com.subao.common.g.c;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;

/* compiled from: IpInfoDownloader */
public abstract class v extends z {
    private final c a;

    /* compiled from: IpInfoDownloader */
    public interface a {
        v a(z.a aVar, c cVar);
    }

    /* access modifiers changed from: protected */
    public abstract String d();

    protected v(z.a aVar, c cVar) {
        super(aVar);
        this.a = cVar;
    }

    public static String a(z.a aVar, c cVar, a aVar2) {
        v a2 = aVar2.a(aVar, cVar);
        aa l = a2.l();
        a2.e(l);
        if (a2.d(l)) {
            return b(l);
        }
        return null;
    }

    static String b(aa aaVar) {
        if (aaVar == null) {
            return null;
        }
        byte[] a2 = aaVar.a();
        if (a2 == null || a2.length == 0) {
            return null;
        }
        JsonReader jsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(a2)));
        try {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                if (HttpRequestParams.NOTICE_LIST.equals(jsonReader.nextName())) {
                    String nextString = jsonReader.nextString();
                    if (!TextUtils.isEmpty(nextString) && nextString.charAt(nextString.length() - 1) != ',') {
                        nextString = nextString + ',';
                    }
                    return nextString;
                }
                jsonReader.skipValue();
            }
            e.a((Closeable) jsonReader);
        } catch (IOException | AssertionError | RuntimeException e) {
            e.printStackTrace();
        } finally {
            e.a((Closeable) jsonReader);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void a(@Nullable aa aaVar) {
        super.a(aaVar);
        if (aaVar != null && aaVar.d) {
            this.a.b(0, d(), b(aaVar));
        }
    }
}
