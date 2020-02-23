package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import java.util.ArrayList;

public final class zzazn {
    @Deprecated
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("ClearcutLogger.API", zzajS, zzajR);
    private static Api.zzf<zzbab> zzajR = new Api.zzf<>();
    private static Api.zza<zzbab, Api.ApiOptions.NoOptions> zzajS = new zzazo();
    private static final zzcqn[] zzazg = new zzcqn[0];
    private static final String[] zzazh = new String[0];
    private static final byte[][] zzazi = new byte[0][];
    /* access modifiers changed from: private */
    public final String packageName;
    /* access modifiers changed from: private */
    public final int zzazj;
    /* access modifiers changed from: private */
    public String zzazk;
    /* access modifiers changed from: private */
    public int zzazl;
    /* access modifiers changed from: private */
    public String zzazm;
    private String zzazn;
    /* access modifiers changed from: private */
    public final boolean zzazo;
    private int zzazp;
    /* access modifiers changed from: private */
    public final zzazt zzazq;
    /* access modifiers changed from: private */
    public final zze zzazr;
    /* access modifiers changed from: private */
    public zzazs zzazs;
    /* access modifiers changed from: private */
    public final zzazq zzazt;

    public zzazn(Context context, int i, String str, String str2, String str3, boolean z, zzazt zzazt2, zze zze, zzazs zzazs2, zzazq zzazq2) {
        this.zzazl = -1;
        this.zzazp = 0;
        this.packageName = context.getPackageName();
        this.zzazj = zzap(context);
        this.zzazl = -1;
        this.zzazk = str;
        this.zzazm = null;
        this.zzazn = null;
        this.zzazo = z;
        this.zzazq = zzazt2;
        this.zzazr = zze;
        this.zzazs = new zzazs();
        this.zzazp = 0;
        this.zzazt = zzazq2;
        if (z) {
            zzbo.zzb(true, (Object) "can't be anonymous with an upload account");
        }
    }

    public zzazn(Context context, String str, String str2) {
        this(context, -1, str, (String) null, (String) null, false, zzazw.zzaq(context), zzi.zzrY(), (zzazs) null, new zzbah(context));
    }

    private static int zzap(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.wtf("ClearcutLogger", "This can't happen.");
            return 0;
        }
    }

    /* access modifiers changed from: private */
    public static int[] zzb(ArrayList<Integer> arrayList) {
        if (arrayList == null) {
            return null;
        }
        int[] iArr = new int[arrayList.size()];
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            iArr[i2] = ((Integer) obj).intValue();
            i2++;
        }
        return iArr;
    }

    public final zzazp zze(byte[] bArr) {
        return new zzazp(this, bArr, (zzazo) null);
    }
}
