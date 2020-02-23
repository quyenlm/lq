package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.google.android.gms.appindexing.AppIndexApi;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.zip.CRC32;

public final class zzapl extends zza {
    public static final Parcelable.Creator<zzapl> CREATOR = new zzapn();
    private String zzBI;
    private zzaoz zzajE;
    private long zzajF;
    private int zzajG;
    private zzaow zzajH;
    private boolean zzajI;
    private int zzajJ;
    private int zzajK;

    zzapl(zzaoz zzaoz, long j, int i, String str, zzaow zzaow, boolean z, int i2, int i3) {
        this.zzajE = zzaoz;
        this.zzajF = j;
        this.zzajG = i;
        this.zzBI = str;
        this.zzajH = zzaow;
        this.zzajI = z;
        this.zzajJ = i2;
        this.zzajK = i3;
    }

    public zzapl(String str, Intent intent, String str2, Uri uri, String str3, List<AppIndexApi.AppIndexingLink> list, int i) {
        this(zza(str, intent), System.currentTimeMillis(), 0, (String) null, zza(intent, str2, uri, (String) null, list).zzmj(), false, -1, 1);
    }

    public static zzaox zza(Intent intent, String str, Uri uri, String str2, List<AppIndexApi.AppIndexingLink> list) {
        String string;
        zzaox zzaox = new zzaox();
        zzaox.zza(new zzapb(str, new zzapj("title").zzR(1).zzL(true).zzbJ("name").zzml(), "text1"));
        if (uri != null) {
            zzaox.zza(new zzapb(uri.toString(), new zzapj(MessengerShareContentUtility.BUTTON_URL_TYPE).zzR(4).zzK(true).zzbJ("url").zzml()));
        }
        if (list != null) {
            zzcaz zzcaz = new zzcaz();
            zzcba[] zzcbaArr = new zzcba[list.size()];
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= zzcbaArr.length) {
                    break;
                }
                zzcbaArr[i2] = new zzcba();
                AppIndexApi.AppIndexingLink appIndexingLink = list.get(i2);
                zzcbaArr[i2].zzbgr = appIndexingLink.appIndexingUrl.toString();
                zzcbaArr[i2].viewId = appIndexingLink.viewId;
                if (appIndexingLink.webUrl != null) {
                    zzcbaArr[i2].zzbgs = appIndexingLink.webUrl.toString();
                }
                i = i2 + 1;
            }
            zzcaz.zzbgp = zzcbaArr;
            zzaox.zza(new zzapb(adp.zzc(zzcaz), new zzapj("outlinks").zzK(true).zzbJ(".private:outLinks").zzbI("blob").zzml()));
        }
        String action = intent.getAction();
        if (action != null) {
            zzaox.zza(zzo("intent_action", action));
        }
        String dataString = intent.getDataString();
        if (dataString != null) {
            zzaox.zza(zzo("intent_data", dataString));
        }
        ComponentName component = intent.getComponent();
        if (component != null) {
            zzaox.zza(zzo("intent_activity", component.getClassName()));
        }
        Bundle extras = intent.getExtras();
        if (!(extras == null || (string = extras.getString("intent_extra_data_key")) == null)) {
            zzaox.zza(zzo("intent_extra_data", string));
        }
        return zzaox.zzbG(str2).zzJ(true);
    }

    public static zzaoz zza(String str, Intent intent) {
        return new zzaoz(str, "", zzc(intent));
    }

    private static String zzc(Intent intent) {
        String uri = intent.toUri(1);
        CRC32 crc32 = new CRC32();
        try {
            crc32.update(uri.getBytes("UTF-8"));
            return Long.toHexString(crc32.getValue());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    private static zzapb zzo(String str, String str2) {
        return new zzapb(str2, new zzapj(str).zzK(true).zzml(), str);
    }

    public final String toString() {
        return String.format(Locale.US, "UsageInfo[documentId=%s, timestamp=%d, usageType=%d, status=%d]", new Object[]{this.zzajE, Long.valueOf(this.zzajF), Integer.valueOf(this.zzajG), Integer.valueOf(this.zzajK)});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, (Parcelable) this.zzajE, i, false);
        zzd.zza(parcel, 2, this.zzajF);
        zzd.zzc(parcel, 3, this.zzajG);
        zzd.zza(parcel, 4, this.zzBI, false);
        zzd.zza(parcel, 5, (Parcelable) this.zzajH, i, false);
        zzd.zza(parcel, 6, this.zzajI);
        zzd.zzc(parcel, 7, this.zzajJ);
        zzd.zzc(parcel, 8, this.zzajK);
        zzd.zzI(parcel, zze);
    }
}
