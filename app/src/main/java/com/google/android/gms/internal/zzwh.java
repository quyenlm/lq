package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.provider.CalendarContract;
import android.text.TextUtils;
import com.google.android.gms.R;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.drive.DriveFile;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import java.util.Map;

@zzzn
public final class zzwh extends zzwu {
    /* access modifiers changed from: private */
    public final Context mContext;
    private final Map<String, String> zzHa;
    private String zzNs = zzal("description");
    private long zzNt = zzam("start_ticks");
    private long zzNu = zzam("end_ticks");
    private String zzNv = zzal("summary");
    private String zzNw = zzal("location");

    public zzwh(zzaka zzaka, Map<String, String> map) {
        super(zzaka, "createCalendarEvent");
        this.zzHa = map;
        this.mContext = zzaka.zzis();
    }

    private final String zzal(String str) {
        return TextUtils.isEmpty(this.zzHa.get(str)) ? "" : this.zzHa.get(str);
    }

    private final long zzam(String str) {
        String str2 = this.zzHa.get(str);
        if (str2 == null) {
            return -1;
        }
        try {
            return Long.parseLong(str2);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /* access modifiers changed from: package-private */
    @TargetApi(14)
    public final Intent createIntent() {
        Intent data = new Intent("android.intent.action.EDIT").setData(CalendarContract.Events.CONTENT_URI);
        data.putExtra("title", this.zzNs);
        data.putExtra("eventLocation", this.zzNw);
        data.putExtra("description", this.zzNv);
        if (this.zzNt > -1) {
            data.putExtra(HttpRequestParams.NOTICE_START_TIME, this.zzNt);
        }
        if (this.zzNu > -1) {
            data.putExtra(HttpRequestParams.NOTICE_END_TIME, this.zzNu);
        }
        data.setFlags(DriveFile.MODE_READ_ONLY);
        return data;
    }

    public final void execute() {
        if (this.mContext == null) {
            zzan("Activity context is not available.");
            return;
        }
        zzbs.zzbz();
        if (!zzagz.zzH(this.mContext).zzdH()) {
            zzan("This feature is not available on the device.");
            return;
        }
        zzbs.zzbz();
        AlertDialog.Builder zzG = zzagz.zzG(this.mContext);
        Resources resources = zzbs.zzbD().getResources();
        zzG.setTitle(resources != null ? resources.getString(R.string.s5) : "Create calendar event");
        zzG.setMessage(resources != null ? resources.getString(R.string.s6) : "Allow Ad to create a calendar event?");
        zzG.setPositiveButton(resources != null ? resources.getString(R.string.s3) : "Accept", new zzwi(this));
        zzG.setNegativeButton(resources != null ? resources.getString(R.string.s4) : "Decline", new zzwj(this));
        zzG.create().show();
    }
}
