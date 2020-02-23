package com.tencent.tp.b;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.tp.TssSdkRootkitTipStr;
import com.tencent.tp.a.ac;
import com.tencent.tp.a.o;
import com.tencent.tp.m;

public class n {
    private Context a;
    /* access modifiers changed from: private */
    public o b;
    private o.a c = new o(this);
    /* access modifiers changed from: private */
    public o.a d = new p(this);

    public n(Context context) {
        this.a = context;
    }

    /* access modifiers changed from: private */
    public void b() {
        Intent intent = new Intent();
        intent.setAction(ac.d);
        Bundle bundle = new Bundle();
        bundle.putString("force_update", "1");
        intent.putExtras(bundle);
        this.a.startActivity(intent);
        m.b();
    }

    public void a() {
        String str = ac.f;
        String str2 = ac.n;
        String str3 = ac.v;
        String str4 = ac.w;
        TssSdkRootkitTipStr tssSdkRootkitTipStr = new TssSdkRootkitTipStr();
        m.b((Object) tssSdkRootkitTipStr);
        try {
            if (tssSdkRootkitTipStr.m_title_5 != null && tssSdkRootkitTipStr.m_title_5.length > 0) {
                str = new String(tssSdkRootkitTipStr.m_title_5, "utf-8");
            }
            if (tssSdkRootkitTipStr.m_msg_5 != null && tssSdkRootkitTipStr.m_msg_5.length > 0) {
                str2 = new String(tssSdkRootkitTipStr.m_msg_5, "utf-8");
            }
            if (tssSdkRootkitTipStr.m_left_5 != null && tssSdkRootkitTipStr.m_left_5.length > 0) {
                str3 = new String(tssSdkRootkitTipStr.m_left_5, "utf-8");
            }
            if (tssSdkRootkitTipStr.m_right_5 != null && tssSdkRootkitTipStr.m_right_5.length > 0) {
                str4 = new String(tssSdkRootkitTipStr.m_right_5, "utf-8");
            }
        } catch (Exception e) {
            str = ac.f;
            str2 = ac.n;
            str3 = ac.v;
            str4 = ac.w;
        }
        String str5 = tssSdkRootkitTipStr.m_force_update == 1 ? null : str4;
        if (tssSdkRootkitTipStr.m_update_canceled != 1) {
            this.b = new o(this.a, str, str2, str3, str5, this.c);
            this.b.b(true);
            this.b.n();
        }
    }
}
