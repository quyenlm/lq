package com.tencent.tp.b;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.gms.drive.DriveFile;
import com.tencent.tp.TssSdkRootkitTipStr;
import com.tencent.tp.a.ac;
import com.tencent.tp.a.o;
import com.tencent.tp.m;
import com.tencent.tp.q;

public class k extends AsyncTask {
    protected Context a;
    protected o b;
    private boolean c;
    /* access modifiers changed from: private */
    public boolean d;
    private o.a e = new l(this);
    /* access modifiers changed from: private */
    public o.a f = new m(this);

    public k(Context context) {
        this.a = context;
        this.d = false;
        a();
    }

    private void a() {
        TssSdkRootkitTipStr tssSdkRootkitTipStr = new TssSdkRootkitTipStr();
        m.b((Object) tssSdkRootkitTipStr);
        if (tssSdkRootkitTipStr.m_state == 1) {
            this.d = true;
            return;
        }
        String str = ac.f;
        String str2 = ac.m;
        String str3 = ac.t;
        String str4 = ac.u;
        try {
            if (tssSdkRootkitTipStr.m_title_2 != null && tssSdkRootkitTipStr.m_title_2.length > 0) {
                str = new String(tssSdkRootkitTipStr.m_title_2, "utf-8");
            }
            if (tssSdkRootkitTipStr.m_msg_2 != null && tssSdkRootkitTipStr.m_msg_2.length > 0) {
                str2 = new String(tssSdkRootkitTipStr.m_msg_2, "utf-8");
            }
            if (tssSdkRootkitTipStr.m_left_2 != null && tssSdkRootkitTipStr.m_left_2.length > 0) {
                str3 = new String(tssSdkRootkitTipStr.m_left_2, "utf-8");
            }
            if (tssSdkRootkitTipStr.m_right_2 != null && tssSdkRootkitTipStr.m_right_2.length > 0) {
                str4 = new String(tssSdkRootkitTipStr.m_right_2, "utf-8");
            }
        } catch (Exception e2) {
            str = ac.f;
            str2 = ac.m;
            str3 = ac.t;
            str4 = ac.u;
        }
        this.b = new o(this.a, str, str2, str3, tssSdkRootkitTipStr.m_allow_cancel == 0 ? null : str4, this.e);
        this.b.b(true);
        this.b.n();
    }

    private void b() throws InterruptedException {
        while (!this.d) {
            Thread.sleep(1000);
            if (m.d() == 1) {
                this.c = true;
                return;
            } else if (this.d) {
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public void c() {
        Intent intent = new Intent();
        intent.setAction(ac.d);
        intent.setFlags(DriveFile.MODE_READ_ONLY);
        String packageName = this.a.getPackageName();
        if (packageName != null) {
            Bundle bundle = new Bundle();
            bundle.putString("game-apk-name", packageName);
            bundle.putString("new-task", "1");
            intent.putExtras(bundle);
        }
        this.a.startActivity(intent);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Void doInBackground(Void... voidArr) {
        try {
            b();
            return null;
        } catch (Exception e2) {
            q.a(e2.toString());
            return null;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void onPostExecute(Void voidR) {
        if (this.c) {
            this.b.a();
        }
    }
}
