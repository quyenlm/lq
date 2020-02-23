package com.tencent.mna.base.c;

import com.tencent.mna.b.a.c.b;
import com.tencent.mna.b.a.c.c;
import com.tencent.mna.b.a.c.e;
import com.tencent.mna.b.a.c.f;
import com.tencent.mna.base.utils.h;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* compiled from: AccNormalReporter */
public class a extends g {
    private f c = new f(com.tencent.mna.base.a.a.v());
    private b d = new b(com.tencent.mna.base.a.a.v());
    private e e = new e();
    private c f = new c(500);
    private c g = new c(500);
    private String h = "";
    private String i = "";
    private String j = "";
    private String k = "";
    private long l = 0;

    /* renamed from: com.tencent.mna.base.c.a$a  reason: collision with other inner class name */
    /* compiled from: AccNormalReporter */
    public enum C0030a {
        PVPID("pvpid"),
        DEVID("devid"),
        MNAVER("mnaver"),
        OPENID("openid"),
        ORIGTIME("origtime"),
        ENDTIME("endtime"),
        LOADMAPTIME("loadmaptime"),
        MNA_FLAG("mna_flag"),
        PLAT_FLAG("plat_flag"),
        EX_CLIENT_IP("ex_client_ip"),
        CTL_ERRORNO("ctl_errorno"),
        CONTROL_IP("control_ip"),
        GAME_IP("game_ip"),
        SPEED_IP("speed_ip"),
        PVP("pvp"),
        PRE_SMART("pre_smart"),
        TOS("tos"),
        MASTERIP("masterip"),
        NEGIP("negip"),
        PROXYIPS("proxyips"),
        EXPORTIP("exportip"),
        PREPARE("prepare"),
        TOKEN("token"),
        CLIENTKEY("clientkey"),
        QOS("qos"),
        QOSSID("qossid"),
        QOSWORKED("qosworked"),
        QOSIP("qosip"),
        QOSDELAY("qosDelay"),
        SDIRECT("sdirect"),
        SFORWARD("sforward"),
        SEDGE("sedge"),
        PRE_XMLVER("pre_xmlver"),
        PRE_ERRNO("pre_errno"),
        PRE_ERRMSG("pre_errmsg"),
        PRE_PROB("pre_prob"),
        ROUT_AUTH("rout_auth"),
        ROUT_QOS("rout_qos"),
        ROUT_MULTI("rout_multi"),
        ROUT_END("rout_end"),
        ROUT_RFLAG("rout_rflag"),
        ROUT_RVER("rout_rver"),
        ROUT_RPORT("rout_rport"),
        JUMPVALUE("jumpvalue", ";", 100),
        FIRST_NET("first_net"),
        END_NET("end_net"),
        FIRST_NIC("first_nic"),
        END_NIC("end_nic"),
        ALL_MEMORY("all_memory"),
        WMAC("wmac"),
        PCELL("pcell"),
        FRONTBACK("frontback", ","),
        ZONEID("zoneid"),
        PHONE_MAC("phone_mac"),
        BATTERY("battery", "_"),
        END_GPS_LON("end_gps_lon"),
        END_GPS_LAT("end_gps_lat"),
        USEPING("useping"),
        INNERIP("innerip"),
        NORMALVALUE("normalvalue", ","),
        CPU("cpu", ","),
        MEMORY("memory", ","),
        FPS("fps", ","),
        MOVE("move", ","),
        CLICK("click", ","),
        GPU("gpu", ","),
        GAMEEXTRA("gameextra"),
        W2M_FLAG("w2m_flag", ";", 100),
        VIVODIAGNOSE("vivodiagnose", ";", 100),
        TOS_FLAG("tos_flag"),
        NOR_CNT("nor_cnt"),
        NOR_I("nor_i");
        
        /* access modifiers changed from: private */
        public int mAppendCount;
        /* access modifiers changed from: private */
        public int mAppendCountMax;
        /* access modifiers changed from: private */
        public String mFieldName;
        /* access modifiers changed from: private */
        public String mFieldSeparator;

        private C0030a(String str) {
            this(r7, r8, str, (String) null, -1);
        }

        private C0030a(String str, String str2) {
            this(r7, r8, str, str2, -1);
        }

        private C0030a(String str, String str2, int i) {
            this.mFieldName = null;
            this.mFieldSeparator = null;
            this.mAppendCount = 0;
            this.mAppendCountMax = -1;
            this.mFieldName = str;
            this.mFieldSeparator = str2;
            this.mAppendCountMax = i;
        }
    }

    public a(c cVar) {
        super(cVar);
    }

    public void a(String str, String str2, String str3, String str4, long j2) {
        if (h() && this.l <= 0) {
            this.h = str;
            this.i = str2;
            this.j = str3;
            this.k = str4;
            this.l = j2;
        }
    }

    public a a(C0030a aVar, String str) {
        if (!(!h() || aVar == null || aVar.mFieldName == null)) {
            if (str == null) {
                str = "";
            }
            if (aVar.mFieldSeparator == null) {
                a(aVar.mFieldName, str);
            } else if (aVar.mAppendCountMax <= 0 || aVar.mAppendCount < aVar.mAppendCountMax) {
                a(aVar.mFieldName, str, aVar.mFieldSeparator);
                C0030a.access$308(aVar);
            }
        }
        return this;
    }

    private void a(long j2) {
        a(C0030a.PVPID, this.h);
        a(C0030a.DEVID, this.i);
        a(C0030a.MNAVER, this.j);
        a(C0030a.OPENID, this.k);
        a(C0030a.ORIGTIME, String.valueOf(this.l));
        a(C0030a.ENDTIME, String.valueOf(j2));
    }

    public void a() {
        int i2;
        try {
            if (this.a == null || this.a.isEmpty()) {
                h.b("[N]ino_newacc_p上报失败，map为空");
                return;
            }
            this.d.a(true);
            List<String> b = this.d.b();
            List<String> c2 = this.d.c();
            List<String> d2 = this.d.d();
            this.c.a(true);
            List<String> a = this.c.a();
            this.e.a(true);
            List<String> a2 = this.e.a();
            List<String> b2 = this.e.b();
            List<String> c3 = this.e.c();
            int u = com.tencent.mna.base.a.a.u();
            int D = com.tencent.mna.base.a.a.D();
            if (u > 0) {
                i2 = ((a.size() - 1) / u) + 1;
            } else {
                i2 = 0;
            }
            a(C0030a.NOR_CNT, String.valueOf(i2));
            long currentTimeMillis = System.currentTimeMillis();
            a(currentTimeMillis);
            boolean a3 = b.a(this.b.getName(), true, 0, -1, this.a, this.b.isRealTime());
            a(this.a);
            h.b("[N]上报ino_newacc_p第一类, endTime: " + currentTimeMillis + ", 结果: " + a3);
            if (com.tencent.mna.base.a.a.I() > 0) {
                com.tencent.mna.base.utils.e.a(com.tencent.mna.a.a.e, com.tencent.mna.a.b.a, this.l, b(this.a).toString(), com.tencent.mna.base.a.a.I(), this.k);
            }
            long j2 = this.l;
            int i3 = 0;
            List<String> list = c3;
            List<String> list2 = b2;
            List<String> list3 = a2;
            List<String> list4 = a;
            List<String> list5 = d2;
            List<String> list6 = c2;
            List<String> list7 = b;
            while (i3 < i2) {
                this.a = new ConcurrentHashMap();
                a(C0030a.NOR_I, String.valueOf(i3));
                list7 = a(C0030a.CPU, list7, u);
                list6 = a(C0030a.MEMORY, list6, u);
                List<String> a4 = a(C0030a.GPU, list5, u);
                List<String> a5 = a(C0030a.FPS, list3, u);
                List<String> a6 = a(C0030a.MOVE, list2, u);
                List<String> a7 = a(C0030a.CLICK, list, u);
                int size = list4.size();
                List<String> a8 = a(C0030a.NORMALVALUE, list4, u);
                int size2 = a8.size();
                if (this.a.isEmpty()) {
                    break;
                }
                int i4 = size - size2;
                long j3 = j2 + ((long) (i4 * D));
                a(j3);
                boolean a9 = b.a(this.b.getName(), true, 0, -1, this.a, this.b.isRealTime());
                a(this.a);
                h.b("[N]上报ino_newacc_p第二类(" + i3 + "), endTime: " + j3 + ", 样点数: " + i4 + ", 结果: " + a9);
                if (com.tencent.mna.base.a.a.I() > 0) {
                    com.tencent.mna.base.utils.e.a(com.tencent.mna.a.a.e, com.tencent.mna.a.b.a, this.l, b(this.a).toString(), com.tencent.mna.base.a.a.I(), this.k);
                }
                i3++;
                j2 = j3;
                list = a7;
                list2 = a6;
                list3 = a5;
                list4 = a8;
                list5 = a4;
            }
            i();
        } catch (Exception e2) {
            h.a("ino_newacc_p report exception!");
        }
    }

    private List<String> a(C0030a aVar, List<String> list, int i2) {
        if (list == null || list.size() == 0) {
            return list;
        }
        StringBuilder sb = new StringBuilder();
        int size = list.size();
        int min = Math.min(i2, size);
        for (int i3 = 0; i3 < min; i3++) {
            sb.append(list.get(i3));
            if (i3 < min - 1 && aVar.mFieldSeparator != null) {
                sb.append(aVar.mFieldSeparator);
            }
        }
        if (sb.length() <= 0) {
            return list;
        }
        sb.append(';');
        this.a.put(aVar.mFieldName, sb.toString());
        if (min < size) {
            return list.subList(min, size);
        }
        list.clear();
        return list;
    }

    private JSONObject b(Map<String, String> map) {
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry next : map.entrySet()) {
            jSONObject.put((String) next.getKey(), next.getValue());
        }
        return jSONObject;
    }

    private void i() {
        this.d.e();
        this.e.e();
        this.c.e();
        this.f.a();
        this.g.a();
    }

    public f b() {
        return this.c;
    }

    public b c() {
        return this.d;
    }

    public c d() {
        return this.f;
    }

    public c e() {
        return this.g;
    }

    public e f() {
        return this.e;
    }
}
