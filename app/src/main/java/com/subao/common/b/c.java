package com.subao.common.b;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.subao.common.e.ai;
import com.subao.common.intf.UserStateListener;

/* compiled from: AuthResultReceiverImpl */
public class c implements b {
    @NonNull
    private final a a;
    private final ai b;
    private final k c;

    /* compiled from: AuthResultReceiverImpl */
    public interface a {
        void a(@Nullable e eVar);

        @Nullable
        UserStateListener e();
    }

    public c(@NonNull a aVar, ai aiVar, k kVar) {
        this.a = aVar;
        this.b = aiVar;
        this.c = kVar;
    }

    private void a(int i, int i2, @Nullable e eVar) {
        int i3;
        String str;
        if (eVar == null) {
            i3 = 0;
            str = "";
        } else {
            i3 = eVar.e;
            str = eVar.f;
        }
        this.a.a(eVar);
        this.c.a(i, i2, i3, str);
        a(i3, str);
    }

    public void a(int i, @NonNull e eVar) {
        a(i, 0, eVar);
    }

    public void a(int i, int i2) {
        a(i, a(i2), (e) null);
    }

    static int a(int i) {
        switch (i) {
            case -2:
                return 1006;
            case 401:
                return 1009;
            default:
                return 1008;
        }
    }

    private void a(int i, String str) {
        UserStateListener e = this.a.e();
        if (e != null) {
            e.onUserStateUpdate(i, str);
        }
    }
}
