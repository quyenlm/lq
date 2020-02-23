package com.subao.common.e;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.subao.common.e;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpHost;

/* compiled from: ServiceLocation */
public class ai {
    @NonNull
    public final String a;
    @NonNull
    public final String b;
    public final int c;

    public ai(@Nullable String str, @NonNull String str2, int i) {
        this.a = TextUtils.isEmpty(str) ? HttpHost.DEFAULT_SCHEME_NAME : str;
        this.b = str2;
        this.c = i;
    }

    @Nullable
    public static ai a(String str) {
        int i;
        if (str == null || str.length() == 0) {
            return null;
        }
        Matcher matcher = Pattern.compile("^(https?)://([^/:?]+)(?::(\\d{1,5}))?/?", 2).matcher(str);
        if (!matcher.find()) {
            return null;
        }
        String group = matcher.group(2);
        if (TextUtils.isEmpty(group)) {
            return null;
        }
        String group2 = matcher.group(3);
        if (group2 != null) {
            i = Integer.parseInt(group2);
        } else {
            i = -1;
        }
        if (i == -1 || (i > 0 && i < 65536)) {
            return new ai(matcher.group(1), group, i);
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ai)) {
            return false;
        }
        ai aiVar = (ai) obj;
        if (this.c != aiVar.c || !e.a(this.b, aiVar.b) || !e.a(this.a, aiVar.a)) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.a.length() + this.b.length() + 64);
        sb.append(this.a).append("://").append(this.b);
        if (this.c >= 0) {
            sb.append(':').append(this.c);
        }
        return sb.toString();
    }
}
