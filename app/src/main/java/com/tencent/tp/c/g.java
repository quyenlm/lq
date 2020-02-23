package com.tencent.tp.c;

import android.content.Context;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class g {
    protected Context a;
    private String b;
    private boolean c;
    private Map d = new HashMap();

    public g(Context context, String str, boolean z) {
        this.a = context;
        this.b = str;
        if (z) {
            a(str);
        }
    }

    private void a(String str) {
        try {
            b(str);
            this.c = true;
        } catch (Exception e) {
        }
    }

    private void a(String str, String str2, String str3) {
        Map map;
        if (!this.d.containsKey(str)) {
            map = new HashMap();
            this.d.put(str, map);
        } else {
            map = (Map) this.d.get(str);
        }
        map.put(str2, str3);
    }

    private void b(String str) throws FileNotFoundException, IOException {
        if (i.a(str)) {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(str));
            String str2 = null;
            while (true) {
                String readLine = dataInputStream.readLine();
                if (readLine == null) {
                    dataInputStream.close();
                    return;
                }
                String trim = new String(readLine.getBytes("ISO-8859-1"), "UTF-8").trim();
                int indexOf = trim.indexOf("[");
                int indexOf2 = trim.indexOf("]");
                if (indexOf == -1 || indexOf2 == -1) {
                    int indexOf3 = trim.indexOf(HttpRequest.HTTP_REQ_ENTITY_MERGE);
                    if (indexOf3 != -1) {
                        a(str2, trim.substring(0, indexOf3), trim.substring(indexOf3 + 1));
                    }
                } else {
                    str2 = trim.substring(indexOf + 1, indexOf2);
                }
            }
        }
    }

    public String a(String str, String str2) {
        if (!this.c) {
            a(this.b);
        }
        if (this.d.containsKey(str)) {
            Map map = (Map) this.d.get(str);
            if (map.containsKey(str2)) {
                return (String) map.get(str2);
            }
        }
        return null;
    }
}
