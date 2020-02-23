package com.appsflyer.share;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.drive.DriveFile;

final class a {

    /* renamed from: ˋ  reason: contains not printable characters */
    private String f217;

    a() {
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˏ  reason: contains not printable characters */
    public final void m157(String str) {
        this.f217 = str;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˎ  reason: contains not printable characters */
    public final void m156(Context context) {
        if (this.f217 != null) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.f217)).setFlags(DriveFile.MODE_READ_ONLY));
        }
    }
}
