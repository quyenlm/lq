package com.subao.common.j;

/* compiled from: RequestProperty */
public class m {
    public final String a;
    public final String b;

    public m(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(512);
        sb.append("RequestProperty [field=");
        sb.append(this.a);
        sb.append(", newValue=");
        sb.append(this.b);
        sb.append(']');
        return sb.toString();
    }
}
