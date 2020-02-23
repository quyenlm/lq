package com.tencent.tp.b;

import com.tencent.tp.TssSdkRootkitTipStr;
import com.tencent.tp.a.ac;
import com.tencent.tp.a.o;
import com.tencent.tp.m;

class o implements o.a {
    final /* synthetic */ n a;

    o(n nVar) {
        this.a = nVar;
    }

    public void a() {
        m.a("rootkit:up_0_0");
        this.a.b();
    }

    public void b() {
        String str;
        m.a("rootkit:up_0_1");
        String str2 = ac.f;
        String str3 = ac.q;
        String str4 = ac.v;
        String str5 = ac.w;
        TssSdkRootkitTipStr tssSdkRootkitTipStr = new TssSdkRootkitTipStr();
        m.b((Object) tssSdkRootkitTipStr);
        try {
            if (tssSdkRootkitTipStr.m_title_7 != null && tssSdkRootkitTipStr.m_title_7.length > 0) {
                str2 = new String(tssSdkRootkitTipStr.m_title_7, "utf-8");
            }
            if (tssSdkRootkitTipStr.m_msg_7 != null && tssSdkRootkitTipStr.m_msg_7.length > 0) {
                str3 = new String(tssSdkRootkitTipStr.m_msg_7, "utf-8");
            }
            if (tssSdkRootkitTipStr.m_left_7 != null && tssSdkRootkitTipStr.m_left_7.length > 0) {
                str4 = new String(tssSdkRootkitTipStr.m_left_7, "utf-8");
            }
            if (tssSdkRootkitTipStr.m_right_7 != null && tssSdkRootkitTipStr.m_right_7.length > 0) {
                str5 = new String(tssSdkRootkitTipStr.m_right_7, "utf-8");
            }
            str = str5;
        } catch (Exception e) {
            str2 = ac.f;
            str3 = ac.q;
            str4 = ac.v;
            str = ac.w;
        }
        if (tssSdkRootkitTipStr.m_tip_if_cancel_update == 1) {
            this.a.b.c(true);
            this.a.b.a(str2, str3, str4, str, this.a.d);
            return;
        }
        m.a();
        this.a.b.a();
    }
}
