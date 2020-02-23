package com.tencent.tp.b;

import com.tencent.tp.TssSdkRootkitTipStr;
import com.tencent.tp.a.ac;
import com.tencent.tp.a.o;
import com.tencent.tp.m;

class h implements o.a {
    final /* synthetic */ g a;

    h(g gVar) {
        this.a = gVar;
    }

    public void a() {
        this.a.b.a();
        m.a("rootkit:dl_0_0");
        this.a.b();
    }

    public void b() {
        String str;
        m.a("rootkit:dl_0_1");
        String str2 = ac.f;
        String str3 = ac.o;
        String str4 = ac.r;
        String str5 = ac.s;
        TssSdkRootkitTipStr tssSdkRootkitTipStr = new TssSdkRootkitTipStr();
        m.b((Object) tssSdkRootkitTipStr);
        try {
            if (tssSdkRootkitTipStr.m_title_3 != null && tssSdkRootkitTipStr.m_title_3.length > 0) {
                str2 = new String(tssSdkRootkitTipStr.m_title_3, "utf-8");
            }
            if (tssSdkRootkitTipStr.m_msg_3 != null && tssSdkRootkitTipStr.m_msg_3.length > 0) {
                str3 = new String(tssSdkRootkitTipStr.m_msg_3, "utf-8");
            }
            if (tssSdkRootkitTipStr.m_left_3 != null && tssSdkRootkitTipStr.m_left_3.length > 0) {
                str4 = new String(tssSdkRootkitTipStr.m_left_3, "utf-8");
            }
            if (tssSdkRootkitTipStr.m_right_3 != null && tssSdkRootkitTipStr.m_right_3.length > 0) {
                str5 = new String(tssSdkRootkitTipStr.m_right_3, "utf-8");
            }
            str = str5;
        } catch (Exception e) {
            str2 = ac.f;
            str3 = ac.o;
            str4 = ac.r;
            str = ac.s;
        }
        this.a.b.b(false);
        this.a.b.a(str2, str3, str4, str, this.a.d);
    }
}
