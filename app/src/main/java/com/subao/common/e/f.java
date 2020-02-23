package com.subao.common.e;

import android.support.annotation.NonNull;
import com.subao.common.e;
import com.vk.sdk.api.VKApiConst;

/* compiled from: Address */
public class f {
    public static final ai a = new ai(VKApiConst.HTTPS, "drone.xunyou.mobi", 504);
    public static final ai b = new ai(VKApiConst.HTTPS, "api.xunyou.mobi", -1);
    public static final ai c = new ai(VKApiConst.HTTPS, "portal-xunyou.qingcdn.com", -1);
    public static final ai d = b;

    /* compiled from: Address */
    public static class a {
        @NonNull
        public final String a;
        public final int b;

        public a(@NonNull String str, int i) {
            this.a = str;
            this.b = i;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            if (this.b != aVar.b || !e.a(this.a, aVar.a)) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return this.b ^ this.a.hashCode();
        }

        public String toString() {
            return String.format(n.b, "[%s:%d]", new Object[]{this.a, Integer.valueOf(this.b)});
        }
    }
}
