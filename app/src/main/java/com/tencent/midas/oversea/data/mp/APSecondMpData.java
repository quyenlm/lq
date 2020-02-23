package com.tencent.midas.oversea.data.mp;

import com.tencent.midas.oversea.comm.APLog;

public class APSecondMpData {
    private static APSecondMpData a = null;
    private String b = "";
    private String c = "";
    private String d = "";
    private boolean e = false;

    public static void init() {
        a = new APSecondMpData();
    }

    public static APSecondMpData singleton() {
        if (a == null) {
            a = new APSecondMpData();
        }
        return a;
    }

    public String getDrm_act_type() {
        APLog.i("getDrm_act_type", this.c);
        return a.c;
    }

    public String getDrm_resource() {
        APLog.i("getDrm_resource", this.d);
        return a.d;
    }

    public String getPre_uuid() {
        return a.b;
    }

    public boolean isSecondMp() {
        return a.e;
    }

    public void setDrm_act_type(String str) {
        APLog.i("setDrm_act_type", str);
        a.c = str;
    }

    public void setDrm_resource(String str) {
        APLog.i("setDrm_resource", str);
        a.d = str;
    }

    public void setPre_uuid(String str) {
        a.b = str;
    }

    public void setSecondMp(boolean z) {
        a.e = z;
    }
}
