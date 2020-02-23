package com.tencent.tp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.facebook.internal.AnalyticsEvents;
import java.util.Map;
import java.util.TreeMap;

public final class t {
    Map a = new TreeMap();
    Context b = null;
    String c;
    int d;

    private void a(String str) {
        try {
            m.c(str);
        } catch (Throwable th) {
        }
    }

    public void a(long j, long j2) {
    }

    public boolean a() {
        if (this.b == null) {
            return false;
        }
        if (this.a == null) {
            return false;
        }
        h a2 = h.a();
        this.a.put("AndroidId", a2.g(this.b));
        this.a.put("ApiLevel", r.c());
        this.a.put("Serial", a2.f(this.b));
        this.a.put("AppName", r.i(this.b));
        this.a.put("PackageName", this.b.getPackageName());
        this.a.put("AppVer", r.h(this.b));
        this.a.put("AppPubkeySha1", r.g(this.b));
        this.a.put("LauncherPubkeySha1", a2.l(this.b));
        this.a.put("BootTime", r.b());
        this.a.put("Brand", a2.i(this.b));
        this.a.put("Fp", a2.h(this.b));
        this.a.put("Country", r.e());
        this.a.put("CpuProductor", r.i());
        this.a.put("CpuAbi", r.f());
        this.a.put("CpuAbis", r.g());
        String a3 = r.a(this.b);
        if (a3 == null || a3.length() == 0) {
            a3 = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
        this.a.put("CpuModel", a3);
        m.a("cpu_model:" + a3);
        this.a.put("OsArch", r.h());
        this.a.put("DeviceName", r.j());
        this.a.put("DisplayMetrics", r.c(this.b).heightPixels + "X" + r.c(this.b).widthPixels);
        this.a.put("FreeMemory", String.valueOf(r.d(this.b)));
        this.a.put("FreeStorage", String.valueOf(r.l()));
        this.a.put("IMEI", a2.a(this.b));
        this.a.put("IMSI", a2.e(this.b));
        this.a.put("Language", r.m());
        this.a.put("MacAddress", a2.c(this.b));
        this.a.put("NetworkName", r.f(this.b));
        this.a.put("NetworkType", r.e(this.b));
        this.a.put("Platform", r.o());
        this.a.put("RamSize", r.p());
        this.a.put("RomSize", r.q());
        this.a.put("SensorStates", a2.k(this.b));
        this.a.put(JsonDocumentFields.VERSION, r.r());
        this.a.put("WifiHotspotMacAddress", a2.d(this.b));
        this.a.put("WifiHotspotSSID", a2.b(this.b));
        this.a.put("SDCardState", String.valueOf(r.a()));
        this.a.put("KernelVer", r.n());
        this.a.put("SimulatorName", r.b(this.b));
        this.a.put("Battery", "" + r.k(this.b));
        this.a.put("TotalMemory", "" + r.m(this.b));
        if (r.a()) {
            this.a.put("SDCARDFreeSpace", String.valueOf(r.k()));
        } else {
            this.a.put("SDCARDFreeSpace", String.valueOf(-1));
        }
        for (String str : this.a.keySet()) {
            if (str != null) {
                String str2 = (String) this.a.get(str);
                if (str2 == null) {
                    str2 = String.valueOf(str2);
                }
                String str3 = str + ":" + str2;
                if (str3.length() > 63) {
                    str3 = str3.substring(0, 63);
                }
                a(str3);
            }
        }
        return true;
    }

    public boolean a(Context context) {
        if (context == null || this.b != null) {
            return false;
        }
        this.b = context;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                this.c = packageInfo.versionName;
                this.d = packageInfo.versionCode;
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return true;
    }
}
