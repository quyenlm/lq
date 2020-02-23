package com.tencent.tp.b;

import android.content.Context;
import com.tencent.tp.TssSdkRootkitTipStr;
import com.tencent.tp.a.ac;
import com.tencent.tp.a.o;
import com.tencent.tp.c.h;
import com.tencent.tp.m;

public class g {
    private Context a;
    /* access modifiers changed from: private */
    public o b;
    private o.a c = new h(this);
    /* access modifiers changed from: private */
    public o.a d = new i(this);

    public g(Context context) {
        this.a = context;
    }

    /* access modifiers changed from: private */
    public void b() {
        if (h.a(this.a)) {
            new a(this.a).a(ac.x);
        } else {
            new o(this.a, ac.e, ac.y, ac.g, (String) null, new j(this)).n();
        }
    }

    public void a() {
        TssSdkRootkitTipStr tssSdkRootkitTipStr = new TssSdkRootkitTipStr();
        m.b((Object) tssSdkRootkitTipStr);
        if (tssSdkRootkitTipStr.m_state != 1) {
            String str = ac.f;
            String str2 = ac.l;
            String str3 = ac.r;
            String str4 = ac.s;
            try {
                if (tssSdkRootkitTipStr.m_title_1 != null && tssSdkRootkitTipStr.m_title_1.length > 0) {
                    str = new String(tssSdkRootkitTipStr.m_title_1, "utf-8");
                }
                if (tssSdkRootkitTipStr.m_msg_1 != null && tssSdkRootkitTipStr.m_msg_1.length > 0) {
                    str2 = new String(tssSdkRootkitTipStr.m_msg_1, "utf-8");
                }
                if (tssSdkRootkitTipStr.m_left_1 != null && tssSdkRootkitTipStr.m_left_1.length > 0) {
                    str3 = new String(tssSdkRootkitTipStr.m_left_1, "utf-8");
                }
                if (tssSdkRootkitTipStr.m_right_1 != null && tssSdkRootkitTipStr.m_right_1.length > 0) {
                    str4 = new String(tssSdkRootkitTipStr.m_right_1, "utf-8");
                }
            } catch (Exception e) {
                str = ac.f;
                str2 = ac.l;
                str3 = ac.r;
                str4 = ac.s;
            }
            this.b = new o(this.a, str, str2, str3, tssSdkRootkitTipStr.m_allow_cancel == 0 ? null : str4, this.c);
            this.b.b(true);
            this.b.n();
        }
    }
}
