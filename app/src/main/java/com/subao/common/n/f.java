package com.subao.common.n;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.JsonWriter;
import com.amazonaws.services.s3.internal.Constants;
import com.subao.common.c;
import com.subao.common.e;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/* compiled from: JsonUtils */
public class f {

    /* compiled from: JsonUtils */
    public interface a<E> {
        @Nullable
        E b(@NonNull JsonReader jsonReader);
    }

    public static byte[] a(c cVar) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
        JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(byteArrayOutputStream, "UTF-8"));
        cVar.serialize(jsonWriter);
        e.a((Closeable) jsonWriter);
        return byteArrayOutputStream.toByteArray();
    }

    public static <T extends c> JsonWriter a(@NonNull JsonWriter jsonWriter, @NonNull String str, @Nullable Iterable<T> iterable) {
        jsonWriter.name(str);
        if (iterable != null) {
            jsonWriter.beginArray();
            for (T t : iterable) {
                if (t != null) {
                    t.serialize(jsonWriter);
                }
            }
            jsonWriter.endArray();
        } else {
            jsonWriter.nullValue();
        }
        return jsonWriter;
    }

    public static JsonWriter a(JsonWriter jsonWriter, String str, c cVar) {
        if (cVar != null) {
            if (!TextUtils.isEmpty(str)) {
                jsonWriter.name(str);
            }
            cVar.serialize(jsonWriter);
        }
        return jsonWriter;
    }

    public static JsonWriter a(JsonWriter jsonWriter, String str, String str2) {
        if (str2 != null) {
            jsonWriter.name(str).value(str2);
        }
        return jsonWriter;
    }

    public static JsonWriter a(JsonWriter jsonWriter, String str, Integer num) {
        if (num != null) {
            jsonWriter.name(str).value(((long) num.intValue()) & 4294967295L);
        }
        return jsonWriter;
    }

    @Nullable
    public static String a(JsonReader jsonReader) {
        if (jsonReader.peek() != JsonToken.NULL) {
            return jsonReader.nextString();
        }
        jsonReader.skipValue();
        return null;
    }

    @Nullable
    public static <E> List<E> a(JsonReader jsonReader, a<E> aVar) {
        return a(jsonReader, aVar, false);
    }

    @Nullable
    public static <E> List<E> a(JsonReader jsonReader, a<E> aVar, boolean z) {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.skipValue();
            return null;
        }
        ArrayList arrayList = new ArrayList();
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            E b = aVar.b(jsonReader);
            if (z || b != null) {
                arrayList.add(b);
            }
        }
        jsonReader.endArray();
        return arrayList;
    }

    public static StringBuilder a(StringBuilder sb, String str) {
        if (str == null) {
            return sb.append(Constants.NULL_VERSION_ID);
        }
        sb.append('\"');
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            switch (charAt) {
                case 8:
                    sb.append("\\b");
                    break;
                case 9:
                    sb.append("\\t");
                    break;
                case 10:
                    sb.append("\\n");
                    break;
                case 12:
                    sb.append("\\f");
                    break;
                case 13:
                    sb.append("\\r");
                    break;
                case '\"':
                case '\\':
                    sb.append('\\');
                    sb.append(charAt);
                    break;
                case 8232:
                case 8233:
                    sb.append(String.format("\\u%04x", new Object[]{Integer.valueOf(charAt)}));
                    break;
                default:
                    if (charAt > 31) {
                        sb.append(charAt);
                        break;
                    } else {
                        sb.append(String.format("\\u%04x", new Object[]{Integer.valueOf(charAt)}));
                        break;
                    }
            }
        }
        sb.append('\"');
        return sb;
    }
}
