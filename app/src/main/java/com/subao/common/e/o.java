package com.subao.common.e;

import android.support.annotation.NonNull;
import com.subao.common.e.v;
import com.subao.common.e.z;
import com.subao.common.g.c;

/* compiled from: GameServerIpDownloader */
public class o extends v {
    private o(z.a aVar, c cVar) {
        super(aVar, cVar);
    }

    /* access modifiers changed from: protected */
    public String d() {
        return "key_game_server_ip";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String a() {
        return "configs/gip";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String b() {
        return "game-ip";
    }

    public static v.a e() {
        return new v.a() {
            public v a(z.a aVar, c cVar) {
                return new o(aVar, cVar);
            }
        };
    }
}
