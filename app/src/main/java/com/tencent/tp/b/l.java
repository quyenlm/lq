package com.tencent.tp.b;

import com.tencent.tp.TssSdkRootkitTipStr;
import com.tencent.tp.a.ac;
import com.tencent.tp.a.o;
import com.tencent.tp.m;

class l implements o.a {
    final /* synthetic */ k a;

    l(k kVar) {
        this.a = kVar;
    }

    public void a() {
        m.a("rootkit:launch_0_0");
        this.a.c();
    }

    public void b() {
        String str;
        m.a("rootkit:launch_0_1");
        String str2 = ac.f;
        String str3 = ac.p;
        String str4 = ac.t;
        String str5 = ac.u;
        TssSdkRootkitTipStr tssSdkRootkitTipStr = new TssSdkRootkitTipStr();
        m.b((Object) tssSdkRootkitTipStr);
        try {
            if (tssSdkRootkitTipStr.m_title_4 != null && tssSdkRootkitTipStr.m_title_4.length > 0) {
                str2 = new String(tssSdkRootkitTipStr.m_title_4, "utf-8");
            }
            if (tssSdkRootkitTipStr.m_msg_4 != null && tssSdkRootkitTipStr.m_msg_4.length > 0) {
                str3 = new String(tssSdkRootkitTipStr.m_msg_4, "utf-8");
            }
            if (tssSdkRootkitTipStr.m_left_4 != null && tssSdkRootkitTipStr.m_left_4.length > 0) {
                str4 = new String(tssSdkRootkitTipStr.m_left_4, "utf-8");
            }
            if (tssSdkRootkitTipStr.m_right_4 != null && tssSdkRootkitTipStr.m_right_4.length > 0) {
                str5 = new String(tssSdkRootkitTipStr.m_right_4, "utf-8");
            }
            str = str5;
        } catch (Exception e) {
            str2 = ac.f;
            str3 = ac.p;
            str4 = ac.t;
            str = ac.u;
        }
        this.a.b.c(true);
        this.a.b.a(str2, str3, str4, str, this.a.f);
    }
}
