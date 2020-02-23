package com.tencent.mna.b.d;

import com.tencent.component.debug.TraceFormat;
import com.tencent.component.net.download.multiplex.download.DownloadDBHelper;
import com.tencent.imsdk.config.ConfigDBHelper;
import com.tencent.imsdk.sns.base.IMFriendInfoExViber;
import com.tencent.imsdk.tool.etc.APNUtil;
import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.mna.KartinRet;
import com.tencent.mna.base.c.d;
import com.tencent.mna.base.c.f;
import com.tencent.mna.base.d.b;
import com.tencent.mna.base.utils.g;
import com.tencent.mna.base.utils.j;
import com.tencent.mna.base.utils.k;
import com.tencent.mna.base.utils.o;
import com.tencent.mna.base.utils.q;
import com.tencent.tp.a.h;
import com.vk.sdk.VKScope;
import com.vk.sdk.api.VKApiConst;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: DiagnoseRecord */
class c {
    private long A = -1;
    private long B = -1;
    private int C;
    private int D;
    private int E = 0;
    private String F = "";
    private String G = "";
    private String H = "";
    private String I;
    private boolean J = false;
    private int K;
    private String L;
    private String M;
    private String N;
    private String O;
    private int P;
    private int Q;
    private int R;
    private int S;
    private int T;
    private String U = UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
    private KartinRet V;
    final String a;
    protected int b;
    protected int c;
    protected int d;
    protected int e;
    protected int f = -1;
    protected int g;
    protected int h = -1;
    protected long i = -1;
    protected long j = -1;
    protected long k = -1;
    protected long l = -1;
    protected long m = -1;
    protected long n = -1;
    protected long o = -1;
    protected long p = -1;
    protected long q = -1;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    private int z;

    c(String str, String str2) {
        this.I = str;
        this.a = str2 == null ? String.valueOf(System.currentTimeMillis()) : str2;
        this.V = new KartinRet(this.a);
    }

    /* access modifiers changed from: package-private */
    public void a(String str, String str2, int i2, String str3, String str4, int i3, int i4) {
        this.L = str;
        this.M = str2 + ":" + i2;
        this.N = str3;
        this.O = str4;
        this.Q = i4;
        this.P = i3;
        this.J = true;
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        this.K = i2;
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, j.b bVar, int i3, int i4, q.a aVar, b.a aVar2, int i5, int i6, int i7, int i8, String str, int i9, String str2) {
        a(i2, bVar, aVar, i5, i6, i7);
        a(i2, i3, i4, aVar, aVar2, i7, i8, str, i9, str2);
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, int i3) {
        this.b = i2;
        this.c = i3;
    }

    /* access modifiers changed from: package-private */
    public KartinRet a() {
        this.V.flag = -1;
        this.V.desc = KartinRet.KARTIN_REASON_NO_NET_FAILED_ENGLISH;
        return this.V;
    }

    /* access modifiers changed from: package-private */
    public KartinRet b(int i2) {
        this.V.flag = -5;
        this.V.desc = KartinRet.KARTIN_REASON_2G_FAILED_ENGLISH;
        this.V.jump_network = 1;
        this.V.jump_signal = i2;
        return this.V;
    }

    /* access modifiers changed from: package-private */
    public KartinRet a(boolean z2) {
        this.V.flag = -2;
        this.V.desc = z2 ? KartinRet.KARTIN_REASON_MASTER_REQ_FAILED_ENGLISH : KartinRet.KARTIN_REASON_CLOUD_REQ_FAILED_ENGLISH;
        return this.V;
    }

    /* access modifiers changed from: package-private */
    public KartinRet b() {
        this.V.flag = KartinRet.KARTIN_FLAG_GET_DGN_SPEED_TESTER_FAILED;
        this.V.desc = KartinRet.KARTIN_REASON_GET_DGN_SPEED_TESTER_FAILED_ENGLISH;
        return this.V;
    }

    /* access modifiers changed from: package-private */
    public KartinRet c() {
        this.V.flag = -4;
        this.V.desc = KartinRet.KARTIN_REASON_NETWORK_CHANGE_FAILED_ENGLISH;
        return this.V;
    }

    /* access modifiers changed from: package-private */
    public KartinRet d() {
        this.V.flag = -6;
        this.V.desc = KartinRet.KARTIN_REASON_FAILED_ENGLISH;
        return this.V;
    }

    /* access modifiers changed from: package-private */
    public KartinRet a(boolean z2, int i2, g[] gVarArr, g[] gVarArr2, g[] gVarArr3, g[] gVarArr4, g[] gVarArr5, g[] gVarArr6, g[] gVarArr7) {
        this.V.flag = 0;
        this.V.desc = KartinRet.KARTIN_REASON_NORMAL_ENGLISH;
        this.V.jump_network = this.b;
        this.V.jump_signal = this.c;
        this.V.jump_router = this.e;
        this.V.jump_proxy = this.f;
        this.V.jump_edge = this.g;
        this.V.jump_export = this.g;
        this.V.jump_terminal = this.h;
        this.V.jump_direct = this.d;
        a(gVarArr, gVarArr2, gVarArr3, gVarArr4, gVarArr5, gVarArr6, gVarArr7);
        a(z2, i2);
        f();
        return this.V;
    }

    /* access modifiers changed from: package-private */
    public void a(String str, String str2) {
        d a2 = f.a(com.tencent.mna.base.c.c.DIAGNOSE);
        a2.a("openid", this.I);
        a2.a("errno", String.valueOf(this.K));
        if (this.J) {
            a2.a(ConfigDBHelper.DATABASE_TABLE_NAME, this.L).a("vipsvr", this.M).a("speedip", this.N).a("cdnproxy", this.O).a("plat_flag", String.valueOf(this.P)).a("rproxy_flag", String.valueOf(this.Q));
        }
        a2.a("mnaver", "5.5.2_" + com.tencent.mna.a.b.d).a(IMFriendInfoExViber.TAG, this.V.tag).a(DownloadDBHelper.FLAG, String.valueOf(this.V.flag)).a("desc", this.V.desc).a(APNUtil.ANP_NAME_NET, String.valueOf(this.V.jump_network)).a(VKApiConst.SIG, String.valueOf(this.V.jump_signal)).a("sc", this.V.signal_desc).a("router", String.valueOf(this.V.jump_router)).a("rs", String.valueOf(this.V.router_status)).a("rd", this.V.router_desc).a(VKApiConst.OUT, this.C + "_" + String.valueOf(this.V.jump_export)).a("es", String.valueOf(this.V.export_status)).a("ed", this.V.export_desc).a("terminal", String.valueOf(this.V.jump_terminal)).a("ts", String.valueOf(this.V.terminal_status)).a("td", this.V.terminal_desc).a("tproxy", String.valueOf(this.V.jump_proxy)).a("tedge", String.valueOf(this.V.jump_edge)).a(VKScope.DIRECT, this.D + "_" + String.valueOf(this.V.jump_direct)).a("ds", String.valueOf(this.V.direct_status)).a("dd", this.V.direct_desc).a("nets", String.valueOf(this.V.netinfo_status)).a("netd", this.V.netinfo_desc).a("network_star", String.valueOf(this.V.network_star)).a("wifinum", String.valueOf(this.V.wifi_num));
        a2.a("modified", String.valueOf(this.E)).a("speeddetail", this.G).a("edgedetail", this.F).a("pushlose", this.H).a("routenext", this.R + "_" + this.S).a("useping", this.T + "_" + this.U);
        a2.a("netinfo", new StringBuilder().append(this.i).append('_').append(this.A).append('_').append(this.k).append('_').append(this.l).append('_').append(this.j).append('_').append(this.B).append('_').append(this.m).append('_').append(this.n).toString());
        a2.a("directvalue", new StringBuilder().append(this.o).append('_').append(this.p).append('_').append(this.q).toString());
        a2.a("wmac", str).a("pcell", str2).a("location", g.a() + "_" + g.b());
        a2.g();
    }

    public String toString() {
        return "DiagnoseRecord(" + this.b + "," + this.c + "," + this.d + "," + this.e + "," + this.f + "," + this.g + "," + this.t + "," + this.u + "," + this.v + "," + this.w + "," + this.h + "," + this.d + h.b;
    }

    private void a(int i2, j.b bVar, q.a aVar, int i3, int i4, int i5) {
        if (bVar != null) {
            com.tencent.mna.base.utils.h.a("DiagnoseRecord updateSndRcvPkStats " + j.a(bVar));
            j.a a2 = bVar.a();
            if (a2 != null) {
                if (k.d(i2)) {
                    this.m = a2.i;
                    this.n = a2.j;
                    this.k = a2.e;
                    this.l = a2.f;
                    this.B = a2.h;
                    this.A = a2.d;
                } else if (k.c(i2)) {
                    this.m = a2.q;
                    this.n = a2.r;
                    this.k = a2.m;
                    this.l = a2.n;
                    this.B = a2.p;
                    this.A = a2.l;
                } else if (k.e(i2)) {
                    this.m = a2.y;
                    this.n = a2.z;
                    this.k = a2.u;
                    this.l = a2.v;
                    this.B = a2.x;
                    this.A = a2.t;
                }
                com.tencent.mna.base.utils.h.a("DiagnoseRecord updateSndRcvPkStats " + "mSndErrs:" + this.m + ", mSndDrops:" + this.n + ", mRcvErrs:" + this.k + ", mRcvDrops:" + this.l + ", mOrigSndPk:" + this.B + ", mOrigRcvPk:" + this.A);
                a(i2, aVar, i3, i4, i5);
            }
        }
    }

    private void a(int i2, int i3, int i4, q.a aVar, b.a aVar2, int i5, int i6, String str, int i7, String str2) {
        if (i2 == 4) {
            this.e = i3;
            if (i4 > 0 && i4 > i3) {
                this.e = i4;
            }
            this.R = i4;
            this.S = i3;
            com.tencent.mna.base.utils.h.a("DiagnoseRecord updateDiagnoseResultStats ping: mJumpRouter:" + this.e);
            this.h = aVar.a;
            com.tencent.mna.base.utils.h.a("DiagnoseRecord updateDiagnoseResultStats terminal: mJumpTerminal:" + this.h);
        }
        if (aVar2 != null) {
            int i8 = aVar2.d;
            this.d = i8;
            this.D = i8;
            this.G = aVar2.e;
            this.H = aVar2.f;
            this.o = aVar2.a;
            this.p = aVar2.b;
            this.q = aVar2.c;
            com.tencent.mna.base.utils.h.a("DiagnoseRecord updateDiagnoseResultStats direct: " + "mRealDirect:" + this.D + ", mDirectDelayStat:" + this.G + ", mPushLossStat:" + this.H + ", mDirectSndDrops:" + this.o + ", mDirectSndJumps:" + this.p + ", mDirectPushDrops:" + this.q);
        }
        this.T = i7;
        this.U = str2;
        if (i5 <= 0) {
            this.g = -2;
            this.C = -2;
        } else {
            this.g = i6;
            this.C = i6;
        }
        this.F = str;
        com.tencent.mna.base.utils.h.a("DiagnoseRecord updateDiagnoseResultStats export: mRealExport:" + this.C + ", mWlanNextHopIp:" + this.U);
        c(i2);
    }

    private void a(int i2, q.a aVar, int i3, int i4, int i5) {
        int i6;
        int i7;
        if (i2 != 4 || aVar.a <= 0) {
            i6 = 0;
            i7 = 0;
        } else {
            int i8 = ((aVar.b - aVar.a) * 3) + aVar.a;
            i6 = aVar.a;
            i7 = i8;
        }
        this.j = ((this.B - ((long) i7)) - ((long) i3)) - ((long) i5);
        this.i = ((this.A - ((long) i6)) - ((long) i4)) - ((long) i5);
        com.tencent.mna.base.utils.h.a("DiagnoseRecord correctSndRcvPkStats " + "mSndPk:" + this.j + ", getRouterInfoSndPk:" + i7 + ", directSndCount:" + i3 + ", exportCount:" + i5);
        com.tencent.mna.base.utils.h.a("DiagnoseRecord correctSndRcvPkStats " + "mRcvPk:" + this.i + ", getRouterInfoRcvPk:" + i6 + ", directRcvCount:" + i4 + ", exportCount:" + i5);
    }

    private void c(int i2) {
        if (i2 == 4) {
            if (this.e >= this.g && this.g > 0) {
                this.g = this.e + com.tencent.mna.base.utils.c.a(1, 10);
                this.E = 1;
            }
            if (this.g >= this.d && this.d > 0) {
                this.d = this.g + com.tencent.mna.base.utils.c.a(5, 15);
                this.E = 1;
            }
            if (this.e >= this.d && this.d > 0) {
                this.d = this.e + com.tencent.mna.base.utils.c.a(10, 15);
                this.E = 1;
            }
        } else if (this.g >= this.d && this.d > 0) {
            this.d = this.g + com.tencent.mna.base.utils.c.a(5, 15);
            this.E = 1;
        }
        com.tencent.mna.base.utils.h.a("DiagnoseRecord correctDiagnoseResultStats " + "mJumpRouter:" + this.e + ", mJumpEdge:" + this.g + ", mJumpDirect:" + this.d);
    }

    private void a(g[] gVarArr, g[] gVarArr2, g[] gVarArr3, g[] gVarArr4, g[] gVarArr5, g[] gVarArr6, g[] gVarArr7) {
        f fVar = new f(e());
        int length = gVarArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            g gVar = gVarArr[i2];
            com.tencent.mna.base.utils.h.a("KartinRule routerRule:" + gVar);
            if (fVar.a(gVar.b) == 1) {
                this.V.router_status = gVar.d;
                this.V.router_desc = gVar.c;
                break;
            }
            i2++;
        }
        int length2 = gVarArr2.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length2) {
                break;
            }
            g gVar2 = gVarArr2[i3];
            com.tencent.mna.base.utils.h.a("KartinRule terminalRule:" + gVar2);
            if (fVar.a(gVar2.b) == 1) {
                this.V.terminal_status = gVar2.d;
                this.V.terminal_desc = gVar2.c;
                break;
            }
            i3++;
        }
        int length3 = gVarArr3.length;
        int i4 = 0;
        while (true) {
            if (i4 >= length3) {
                break;
            }
            g gVar3 = gVarArr3[i4];
            com.tencent.mna.base.utils.h.a("KartinRule directRule:" + gVar3);
            if (fVar.a(gVar3.b) == 1) {
                this.V.direct_status = gVar3.d;
                this.V.direct_desc = gVar3.c;
                break;
            }
            i4++;
        }
        int length4 = gVarArr4.length;
        int i5 = 0;
        while (true) {
            if (i5 >= length4) {
                break;
            }
            g gVar4 = gVarArr4[i5];
            com.tencent.mna.base.utils.h.a("KartinRule exportRule:" + gVar4);
            if (fVar.a(gVar4.b) == 1) {
                this.V.export_status = gVar4.d;
                this.V.export_desc = gVar4.c;
                break;
            }
            i5++;
        }
        int length5 = gVarArr5.length;
        int i6 = 0;
        while (true) {
            if (i6 >= length5) {
                break;
            }
            g gVar5 = gVarArr5[i6];
            com.tencent.mna.base.utils.h.a("KartinRule networkRule:" + gVar5);
            if (fVar.a(gVar5.b) == 1) {
                this.V.netinfo_status = gVar5.d;
                this.V.netinfo_desc = gVar5.c;
                break;
            }
            i6++;
        }
        int length6 = gVarArr6.length;
        int i7 = 0;
        while (true) {
            if (i7 >= length6) {
                break;
            }
            g gVar6 = gVarArr6[i7];
            com.tencent.mna.base.utils.h.a("KartinRule signalRule:" + gVar6);
            if (fVar.a(gVar6.b) == 1) {
                this.V.signal_status = gVar6.d;
                this.V.signal_desc = gVar6.c;
                break;
            }
            i7++;
        }
        for (g gVar7 : gVarArr7) {
            com.tencent.mna.base.utils.h.a("KartinRule queryRule:" + gVar7);
            if (fVar.a(gVar7.b) == 1) {
                this.V.network_star = gVar7.d;
                return;
            }
        }
    }

    private Map<String, Long> e() {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(26);
        concurrentHashMap.put(TraceFormat.STR_ASSERT, Long.valueOf((long) this.r));
        concurrentHashMap.put("B", Long.valueOf((long) this.b));
        concurrentHashMap.put("C", Long.valueOf((long) this.c));
        concurrentHashMap.put(TraceFormat.STR_DEBUG, Long.valueOf((long) this.d));
        concurrentHashMap.put(TraceFormat.STR_ERROR, Long.valueOf((long) this.s));
        concurrentHashMap.put("F", Long.valueOf((long) this.e));
        concurrentHashMap.put("G", Long.valueOf((long) this.f));
        concurrentHashMap.put("H", Long.valueOf((long) this.g));
        concurrentHashMap.put(TraceFormat.STR_INFO, Long.valueOf((long) this.t));
        concurrentHashMap.put("J", Long.valueOf((long) this.u));
        concurrentHashMap.put("K", Long.valueOf((long) this.v));
        concurrentHashMap.put("L", Long.valueOf((long) this.w));
        concurrentHashMap.put("M", Long.valueOf((long) this.h));
        concurrentHashMap.put("N", Long.valueOf((long) this.x));
        concurrentHashMap.put("O", Long.valueOf((long) this.y));
        concurrentHashMap.put("P", Long.valueOf((long) this.z));
        concurrentHashMap.put("R", Long.valueOf(this.i));
        concurrentHashMap.put("S", Long.valueOf(this.j));
        concurrentHashMap.put("T", Long.valueOf(this.m));
        concurrentHashMap.put("U", Long.valueOf(this.n));
        concurrentHashMap.put(TraceFormat.STR_VERBOSE, Long.valueOf(this.k));
        concurrentHashMap.put(TraceFormat.STR_WARN, Long.valueOf(this.l));
        concurrentHashMap.put("X", Long.valueOf(this.o));
        concurrentHashMap.put("Y", Long.valueOf(this.p));
        concurrentHashMap.put("Z", Long.valueOf(this.q));
        return concurrentHashMap;
    }

    private void a(boolean z2, int i2) {
        int i3 = -100;
        if (z2 && this.V.network_star < i2) {
            o.b();
            try {
                Thread.sleep(3000);
            } catch (Exception e2) {
            }
            i3 = o.c();
        }
        this.V.wifi_num = i3;
    }

    private void f() {
        this.V.jump_network = Math.max(this.V.jump_network, 0);
        this.V.jump_signal = Math.max(this.V.jump_signal, 0);
        this.V.jump_terminal = Math.max(this.V.jump_terminal, 0);
        this.V.jump_router = Math.max(this.V.jump_router, 0);
        this.V.jump_export = Math.max(this.V.jump_export, 0);
        this.V.jump_direct = Math.max(this.V.jump_direct, 0);
        this.V.jump_router = Math.min(this.V.jump_router, 500);
        this.V.jump_export = Math.min(this.V.jump_export, 500);
        this.V.jump_direct = Math.min(this.V.jump_direct, 500);
    }
}
