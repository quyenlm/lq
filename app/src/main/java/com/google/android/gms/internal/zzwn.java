package com.google.android.gms.internal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.URLUtil;
import com.google.android.gms.R;
import com.google.android.gms.ads.internal.zzbs;
import java.util.Map;

@zzzn
public final class zzwn extends zzwu {
    /* access modifiers changed from: private */
    public final Context mContext;
    private final Map<String, String> zzHa;

    public zzwn(zzaka zzaka, Map<String, String> map) {
        super(zzaka, "storePicture");
        this.zzHa = map;
        this.mContext = zzaka.zzis();
    }

    public final void execute() {
        if (this.mContext == null) {
            zzan("Activity context is not available");
            return;
        }
        zzbs.zzbz();
        if (!zzagz.zzH(this.mContext).zzdG()) {
            zzan("Feature is not supported by the device.");
            return;
        }
        String str = this.zzHa.get("iurl");
        if (TextUtils.isEmpty(str)) {
            zzan("Image url cannot be empty.");
        } else if (!URLUtil.isValidUrl(str)) {
            String valueOf = String.valueOf(str);
            zzan(valueOf.length() != 0 ? "Invalid image url: ".concat(valueOf) : new String("Invalid image url: "));
        } else {
            String lastPathSegment = Uri.parse(str).getLastPathSegment();
            zzbs.zzbz();
            if (!zzagz.zzaK(lastPathSegment)) {
                String valueOf2 = String.valueOf(lastPathSegment);
                zzan(valueOf2.length() != 0 ? "Image type not recognized: ".concat(valueOf2) : new String("Image type not recognized: "));
                return;
            }
            Resources resources = zzbs.zzbD().getResources();
            zzbs.zzbz();
            AlertDialog.Builder zzG = zzagz.zzG(this.mContext);
            zzG.setTitle(resources != null ? resources.getString(R.string.s1) : "Save image");
            zzG.setMessage(resources != null ? resources.getString(R.string.s2) : "Allow Ad to store image in Picture gallery?");
            zzG.setPositiveButton(resources != null ? resources.getString(R.string.s3) : "Accept", new zzwo(this, str, lastPathSegment));
            zzG.setNegativeButton(resources != null ? resources.getString(R.string.s4) : "Decline", new zzwp(this));
            zzG.create().show();
        }
    }
}
