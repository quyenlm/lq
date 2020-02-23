package com.tencent.tp.c;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import java.io.File;
import java.io.IOException;

public class a {
    private Context a;

    public a(Context context) {
        this.a = context;
    }

    public void a(String str) throws IOException {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(new File(str)), "application/vnd.android.package-archive");
        this.a.startActivity(intent);
    }
}
