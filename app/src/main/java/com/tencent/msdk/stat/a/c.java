package com.tencent.msdk.stat.a;

import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONObject;

public class c {
    public String a;
    public JSONArray b;
    public JSONObject c = null;

    public c() {
    }

    public c(String str, String[] strArr, Properties properties) {
        this.a = str;
        if (properties != null) {
            this.c = new JSONObject(properties);
        } else if (strArr != null) {
            this.b = new JSONArray();
            for (String put : strArr) {
                this.b.put(put);
            }
        } else {
            this.c = new JSONObject();
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof c) {
            return toString().equals(((c) obj).toString());
        }
        return false;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(this.a).append(",");
        if (this.b != null) {
            sb.append(this.b.toString());
        }
        if (this.c != null) {
            sb.append(this.c.toString());
        }
        return sb.toString();
    }
}
