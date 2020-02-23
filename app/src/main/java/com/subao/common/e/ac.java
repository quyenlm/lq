package com.subao.common.e;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.JsonReader;
import com.subao.common.e.z;
import com.subao.common.n.f;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/* compiled from: PortalKeyValuesDownloader */
public abstract class ac extends z {
    /* access modifiers changed from: protected */
    public abstract void a(@NonNull String str, @Nullable String str2);

    protected ac(z.a aVar) {
        super(aVar);
    }

    public static void a(ac acVar) {
        aa l = acVar.l();
        if (l != null) {
            if (acVar.d(l)) {
                acVar.b(l);
            } else {
                l = null;
            }
        }
        acVar.e(l);
    }

    /* access modifiers changed from: protected */
    public void a(aa aaVar) {
        super.a(aaVar);
        if (aaVar != null && aaVar.d) {
            b(aaVar);
        }
    }

    /* access modifiers changed from: protected */
    public void a(boolean z) {
    }

    /* access modifiers changed from: protected */
    public void b(aa aaVar) {
        boolean z = true;
        try {
            boolean h = h();
            if (aaVar == null || aaVar.b() <= 2) {
                if (h) {
                    a("config is null");
                }
                if (aaVar == null || !aaVar.d) {
                    z = false;
                }
                a(z);
                return;
            }
            JsonReader jsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(aaVar.c)));
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String nextName = jsonReader.nextName();
                String a = f.a(jsonReader);
                if (h) {
                    a(String.format("process \"%s\":\"%s\"", new Object[]{nextName, a}));
                }
                a(nextName, a);
            }
            jsonReader.endObject();
            if (aaVar == null || !aaVar.d) {
                z = false;
            }
            a(z);
        } catch (IOException | AssertionError | RuntimeException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            if (aaVar == null || !aaVar.d) {
                z = false;
            }
            a(z);
            throw th;
        }
    }
}
