package com.subao.common.f;

import android.content.Context;
import com.subao.common.e.n;
import java.io.File;

/* compiled from: FileOperator */
public class a {
    private static File a;

    public static File a(Context context, n.a aVar) {
        if (aVar == n.a.SDK || aVar == n.a.ROM) {
            a = context.getDir("cn.wsds.sdk.game.data", 0);
        } else {
            a = context.getFilesDir();
        }
        return a;
    }

    public static File a() {
        return a;
    }

    public static File a(String str) {
        return new File(a, str);
    }
}
