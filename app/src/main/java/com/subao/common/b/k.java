package com.subao.common.b;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.subao.common.intf.UserInfo;
import com.subao.common.intf.XunyouUserStateCallback;
import java.util.ArrayList;
import java.util.List;

/* compiled from: XunyouUserStateRequester */
public class k {
    private final a a = new a();

    public int a(@NonNull UserInfo userInfo, @Nullable XunyouUserStateCallback xunyouUserStateCallback, @Nullable Object obj) {
        return this.a.a(userInfo, xunyouUserStateCallback, obj);
    }

    public void a(int i, int i2, int i3, String str) {
        XunyouUserStateCallback xunyouUserStateCallback;
        a.C0004a a2 = this.a.a(i);
        if (a2 != null && (xunyouUserStateCallback = a2.c) != null) {
            xunyouUserStateCallback.onXunyouUserState(a2.b, a2.d, i2, i3, str);
        }
    }

    /* compiled from: XunyouUserStateRequester */
    private static class a {
        private static int a = 1000;
        private final List<C0004a> b;

        private a() {
            this.b = new ArrayList(8);
        }

        /* access modifiers changed from: package-private */
        public int a(@NonNull UserInfo userInfo, @Nullable XunyouUserStateCallback xunyouUserStateCallback, @Nullable Object obj) {
            int i;
            synchronized (this.b) {
                i = a + 1;
                a = i;
                this.b.add(new C0004a(i, userInfo, xunyouUserStateCallback, obj));
            }
            return i;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public C0004a a(int i) {
            C0004a aVar;
            synchronized (this.b) {
                int size = this.b.size() - 1;
                while (true) {
                    if (size < 0) {
                        aVar = null;
                        break;
                    } else if (this.b.get(size).a == i) {
                        aVar = this.b.remove(size);
                        break;
                    } else {
                        size--;
                    }
                }
            }
            return aVar;
        }

        /* renamed from: com.subao.common.b.k$a$a  reason: collision with other inner class name */
        /* compiled from: XunyouUserStateRequester */
        static class C0004a {
            final int a;
            @NonNull
            final UserInfo b;
            @Nullable
            final XunyouUserStateCallback c;
            @Nullable
            final Object d;

            C0004a(int i, @NonNull UserInfo userInfo, @Nullable XunyouUserStateCallback xunyouUserStateCallback, @Nullable Object obj) {
                this.a = i;
                this.b = userInfo;
                this.c = xunyouUserStateCallback;
                this.d = obj;
            }
        }
    }
}
