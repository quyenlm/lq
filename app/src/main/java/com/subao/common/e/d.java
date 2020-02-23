package com.subao.common.e;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.util.Log;
import com.subao.common.e;
import com.subao.common.e.b;
import com.subao.common.e.z;
import com.vk.sdk.api.model.VKApiUserFull;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/* compiled from: AccelGamesDownloader */
public class d extends z {
    private final String a;
    private final int b;
    @Nullable
    private final b c;

    /* compiled from: AccelGamesDownloader */
    public interface b {
        void a(@Nullable List<b> list);
    }

    protected d(z.a aVar, int i, @Nullable b bVar) {
        super(aVar);
        this.a = SystemMediaRouteProvider.PACKAGE_NAME.equals(aVar.a) ? "v1" : "v2";
        this.b = i;
        this.c = bVar;
    }

    public static List<b> a(z.a aVar, int i, @Nullable b bVar, @Nullable byte[] bArr) {
        d dVar = new d(aVar, i, bVar);
        aa l = dVar.l();
        dVar.e(l);
        List<b> a2 = a(l, i);
        if (a2 == null) {
            return a(bArr, i);
        }
        return a2;
    }

    private static List<b> a(aa aaVar, int i) {
        if (aaVar == null || aaVar.a() == null) {
            return null;
        }
        return a(aaVar.a(), i);
    }

    private static List<b> a(byte[] bArr, int i) {
        List<b> list = null;
        if (bArr != null && bArr.length > 2) {
            try {
                list = a.a((InputStream) new ByteArrayInputStream(bArr), i);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (list == null) {
                Log.w("SubaoData", "Parse accel game list fail");
            } else if (com.subao.common.d.a("SubaoData")) {
                Log.d("SubaoData", String.format(n.b, "Parse %d games from JSON", new Object[]{Integer.valueOf(list.size())}));
            }
        }
        return list;
    }

    /* access modifiers changed from: protected */
    public void a(aa aaVar) {
        List<b> a2;
        super.a(aaVar);
        if (this.c != null && aaVar != null && aaVar.d && (a2 = a(aaVar, this.b)) != null) {
            this.c.a(a2);
        }
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String a() {
        return VKApiUserFull.GAMES;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String b() {
        return "AccelGames";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String c() {
        return this.a;
    }

    /* compiled from: AccelGamesDownloader */
    public static class a {
        @Nullable
        public static List<b> a(InputStream inputStream, int i) {
            List<b> list = null;
            JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            try {
                jsonReader.beginObject();
                while (true) {
                    if (!jsonReader.hasNext()) {
                        break;
                    } else if ("gameList".equals(jsonReader.nextName())) {
                        list = a(jsonReader, i);
                        break;
                    } else {
                        jsonReader.skipValue();
                    }
                }
                e.a((Closeable) jsonReader);
                return list;
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw new IOException(e);
            } catch (Throwable th) {
                e.a((Closeable) jsonReader);
                throw th;
            }
        }

        @NonNull
        private static List<b> a(@NonNull JsonReader jsonReader, int i) {
            ArrayList arrayList = new ArrayList(i);
            jsonReader.beginArray();
            int i2 = 0;
            while (jsonReader.hasNext()) {
                b a = b.C0007b.a(jsonReader, i2);
                if (a != null) {
                    i2++;
                    arrayList.add(a);
                }
            }
            jsonReader.endArray();
            return arrayList;
        }
    }
}
