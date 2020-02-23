package com.google.android.gms.tagmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class PreviewActivity extends Activity {
    public void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            zzdj.zzaS("Preview activity");
            Uri data = getIntent().getData();
            if (!TagManager.getInstance(this).zzr(data)) {
                String valueOf = String.valueOf(data);
                String sb = new StringBuilder(String.valueOf(valueOf).length() + 73).append("Cannot preview the app with the uri: ").append(valueOf).append(". Launching current version instead.").toString();
                zzdj.zzaT(sb);
                AlertDialog create = new AlertDialog.Builder(this).create();
                create.setTitle("Preview failure");
                create.setMessage(sb);
                create.setButton(-1, "Continue", new zzeh(this));
                create.show();
            }
            Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(getPackageName());
            if (launchIntentForPackage != null) {
                String valueOf2 = String.valueOf(getPackageName());
                zzdj.zzaS(valueOf2.length() != 0 ? "Invoke the launch activity for package name: ".concat(valueOf2) : new String("Invoke the launch activity for package name: "));
                startActivity(launchIntentForPackage);
                return;
            }
            String valueOf3 = String.valueOf(getPackageName());
            zzdj.zzaS(valueOf3.length() != 0 ? "No launch activity found for package name: ".concat(valueOf3) : new String("No launch activity found for package name: "));
        } catch (Exception e) {
            String valueOf4 = String.valueOf(e.getMessage());
            zzdj.e(valueOf4.length() != 0 ? "Calling preview threw an exception: ".concat(valueOf4) : new String("Calling preview threw an exception: "));
        }
    }
}
