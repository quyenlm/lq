package com.tencent.liteav;

import MTT.EFvrECode;
import android.content.Context;
import com.garena.android.gpns.utility.CONSTANT;
import com.google.android.gms.games.GamesStatusCodes;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.datareport.TXCDRExtInfo;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.TXCStatus;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* compiled from: TXCDataReport */
public class a {
    private static String a = "TXCDataReport";
    private HashMap b = new HashMap(100);
    private String c;
    private Context d;
    private String e;
    private long f;
    private int g;
    private long h;
    private boolean i;
    private long j;
    private int k;
    private boolean l = false;
    private long m = 0;
    private long n = 0;
    private long o = 0;
    private long p = 0;
    private long q = 0;
    private String r = "";

    public a(Context context) {
        this.d = context.getApplicationContext();
        this.e = TXCCommonUtil.getAppVersion();
        this.k = 5000;
        this.q = 0;
    }

    public void a() {
        i();
        this.h = -1;
        this.m = System.currentTimeMillis();
    }

    public void b() {
        if (!this.i) {
            TXCLog.e(a, "play " + this.c + " failed");
            if (this.l) {
                b(false);
            } else {
                e();
            }
        } else if (this.l) {
            j();
        } else {
            f();
        }
        if (this.l) {
            h();
        }
    }

    public void a(boolean z) {
        this.l = z;
    }

    public void a(String str) {
        this.c = str;
    }

    public void b(String str) {
        this.r = str;
    }

    public void c() {
        if (!this.i) {
            long b2 = TXCStatus.b(this.r, GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER);
            long b3 = TXCStatus.b(this.r, 7104);
            if (!(b2 == 0 && b3 == 0)) {
                if (this.l) {
                    b(true);
                } else {
                    d();
                }
                this.k = 5000;
                this.i = true;
            }
        }
        if (this.j <= 0) {
            this.j = TXCTimeUtil.getTimeTick();
        }
        if (this.i && TXCTimeUtil.getTimeTick() > this.j + ((long) this.k)) {
            if (this.l) {
                k();
                this.k = 5000;
            } else {
                g();
                this.k = TXCDRApi.getStatusReportInterval();
                if (this.k < 5000) {
                    this.k = 5000;
                }
                if (this.k > 300000) {
                    this.k = CONSTANT.TIME.MIN_5;
                }
            }
            this.h = TXCStatus.b(this.r, GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_INVALID_OPERATION);
            this.j = TXCTimeUtil.getTimeTick();
        }
    }

    private void d() {
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.url = this.c;
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = false;
        String str = (String) this.b.get("token");
        TXCDRApi.InitEvent(this.d, str, com.tencent.liteav.basic.datareport.a.T, com.tencent.liteav.basic.datareport.a.al, tXCDRExtInfo);
        long utcTimeTick = TXCTimeUtil.getUtcTimeTick();
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.T, "u64_timestamp", utcTimeTick);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.T, "str_device_type", (String) this.b.get("str_device_type"));
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.T, "u32_network_type", (long) c("u32_network_type"));
        long b2 = TXCStatus.b(this.r, 7107);
        long b3 = TXCStatus.b(this.r, 7108);
        if (b3 != -1) {
            b3 -= b2;
        }
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.T, "u32_dns_time", b3);
        String c2 = TXCStatus.c(this.r, 7110);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.T, "u32_server_ip", c2);
        long b4 = TXCStatus.b(this.r, 7109);
        if (b4 != -1) {
            b4 -= b2;
        }
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.T, "u32_connect_server_time", b4);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.T, "u32_stream_begin", -1);
        this.f = TXCStatus.b(this.r, GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER) - b2;
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.T, "u32_first_i_frame", this.f);
        long b5 = TXCStatus.b(this.r, 7103) - b2;
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.T, "u32_first_frame_down", b5);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.T, "str_user_id", (String) this.b.get("str_user_id"));
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.T, "str_package_name", (String) this.b.get("str_package_name"));
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.T, "str_app_version", this.e);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.T, "dev_uuid", (String) this.b.get("dev_uuid"));
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.T, "u32_isp2p", (long) this.g);
        TXCDRApi.nativeReportEvent(str, com.tencent.liteav.basic.datareport.a.T);
        TXCLog.d(a, "report evt 40101: token=" + str + " " + "u64_timestamp" + HttpRequest.HTTP_REQ_ENTITY_MERGE + utcTimeTick + " " + "str_device_type" + HttpRequest.HTTP_REQ_ENTITY_MERGE + ((String) this.b.get("str_device_type")) + " " + "u32_network_type" + HttpRequest.HTTP_REQ_ENTITY_MERGE + c("u32_network_type") + " " + "u32_dns_time" + HttpRequest.HTTP_REQ_ENTITY_MERGE + b3 + " " + "u32_server_ip" + HttpRequest.HTTP_REQ_ENTITY_MERGE + c2 + " " + "u32_connect_server_time" + HttpRequest.HTTP_REQ_ENTITY_MERGE + b4 + " " + "u32_stream_begin" + "=-1 " + "u32_first_i_frame" + HttpRequest.HTTP_REQ_ENTITY_MERGE + this.f + " " + "u32_first_frame_down" + HttpRequest.HTTP_REQ_ENTITY_MERGE + b5 + " " + "str_user_id" + HttpRequest.HTTP_REQ_ENTITY_MERGE + ((String) this.b.get("str_user_id")) + " " + "str_package_name" + HttpRequest.HTTP_REQ_ENTITY_MERGE + ((String) this.b.get("str_package_name")) + " " + "str_app_version" + HttpRequest.HTTP_REQ_ENTITY_MERGE + this.e + " " + "dev_uuid" + HttpRequest.HTTP_REQ_ENTITY_MERGE + ((String) this.b.get("dev_uuid")) + " " + "u32_isp2p" + HttpRequest.HTTP_REQ_ENTITY_MERGE + this.g);
    }

    private void e() {
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.url = this.c;
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = false;
        String str = (String) this.b.get("token");
        TXCDRApi.InitEvent(this.d, str, com.tencent.liteav.basic.datareport.a.T, com.tencent.liteav.basic.datareport.a.al, tXCDRExtInfo);
        long utcTimeTick = TXCTimeUtil.getUtcTimeTick();
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.T, "u64_timestamp", utcTimeTick);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.T, "str_device_type", (String) this.b.get("str_device_type"));
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.T, "u32_network_type", (long) c("u32_network_type"));
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.T, "u32_dns_time", -1);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.T, "u32_server_ip", "");
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.T, "u32_connect_server_time", -1);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.T, "u32_stream_begin", -1);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.T, "u32_first_i_frame", -1);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.T, "u32_first_frame_down", -1);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.T, "str_user_id", (String) this.b.get("str_user_id"));
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.T, "str_package_name", (String) this.b.get("str_package_name"));
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.T, "str_app_version", this.e);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.T, "dev_uuid", (String) this.b.get("dev_uuid"));
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.T, "u32_isp2p", (long) this.g);
        TXCDRApi.nativeReportEvent(str, com.tencent.liteav.basic.datareport.a.T);
        TXCLog.d(a, "report evt 40101: token=" + str + " " + "u64_timestamp" + HttpRequest.HTTP_REQ_ENTITY_MERGE + utcTimeTick + " " + "str_device_type" + HttpRequest.HTTP_REQ_ENTITY_MERGE + ((String) this.b.get("str_device_type")) + " " + "u32_network_type" + HttpRequest.HTTP_REQ_ENTITY_MERGE + c("u32_network_type") + " " + "u32_dns_time" + "=-1 " + "u32_server_ip" + "= " + "u32_connect_server_time" + "=-1 " + "u32_stream_begin" + "=-1 " + "u32_first_i_frame" + "=-1 " + "u32_first_frame_down" + "=-1 " + "str_user_id" + HttpRequest.HTTP_REQ_ENTITY_MERGE + ((String) this.b.get("str_user_id")) + " " + "str_package_name" + HttpRequest.HTTP_REQ_ENTITY_MERGE + ((String) this.b.get("str_package_name")) + " " + "str_app_version" + HttpRequest.HTTP_REQ_ENTITY_MERGE + this.e + " " + "dev_uuid" + HttpRequest.HTTP_REQ_ENTITY_MERGE + ((String) this.b.get("dev_uuid")) + " " + "u32_isp2p" + HttpRequest.HTTP_REQ_ENTITY_MERGE + this.g);
    }

    private void f() {
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.url = this.c;
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = false;
        String str = (String) this.b.get("token");
        TXCDRApi.InitEvent(this.d, str, com.tencent.liteav.basic.datareport.a.V, com.tencent.liteav.basic.datareport.a.al, tXCDRExtInfo);
        long utcTimeTick = TXCTimeUtil.getUtcTimeTick();
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.V, "u64_timestamp", utcTimeTick);
        long timeTick = (TXCTimeUtil.getTimeTick() - TXCStatus.b(this.r, 7107)) / 1000;
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.V, "u32_result", timeTick);
        long b2 = TXCStatus.b(this.r, GamesStatusCodes.STATUS_MULTIPLAYER_DISABLED);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.V, "u32_avg_block_time", b2);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.V, "str_app_version", this.e);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.V, "u32_isp2p", (long) this.g);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.V, "u32_avg_load", TXCStatus.b(this.r, 2001));
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.V, "u32_load_cnt", TXCStatus.b(this.r, 2002));
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.V, "u32_max_load", TXCStatus.b(this.r, 2003));
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.V, "u32_first_i_frame", this.f);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.V, "u32_speed_cnt", TXCStatus.b(this.r, 2004));
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.V, "u32_nodata_cnt", TXCStatus.b(this.r, 2005));
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.V, "u32_avg_cache_time", TXCStatus.b(this.r, 2007));
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.V, "u32_is_real_time", TXCStatus.b(this.r, 2008));
        TXCDRApi.nativeReportEvent(str, com.tencent.liteav.basic.datareport.a.V);
        TXCLog.d(a, "report evt 40102: token=" + str + " " + "str_stream_url" + HttpRequest.HTTP_REQ_ENTITY_MERGE + this.c + " " + "u64_timestamp" + HttpRequest.HTTP_REQ_ENTITY_MERGE + utcTimeTick + " " + "u32_result" + HttpRequest.HTTP_REQ_ENTITY_MERGE + timeTick + " " + "u32_avg_block_time" + HttpRequest.HTTP_REQ_ENTITY_MERGE + b2 + " " + "str_app_version" + HttpRequest.HTTP_REQ_ENTITY_MERGE + this.e + " " + "u32_isp2p" + HttpRequest.HTTP_REQ_ENTITY_MERGE + this.g + " " + "u32_avg_load" + HttpRequest.HTTP_REQ_ENTITY_MERGE + TXCStatus.b(this.r, 2001) + " " + "u32_load_cnt" + HttpRequest.HTTP_REQ_ENTITY_MERGE + TXCStatus.b(this.r, 2002) + " " + "u32_max_load" + HttpRequest.HTTP_REQ_ENTITY_MERGE + TXCStatus.b(this.r, 2003) + " " + "u32_first_i_frame" + HttpRequest.HTTP_REQ_ENTITY_MERGE + this.f + " " + "u32_speed_cnt" + HttpRequest.HTTP_REQ_ENTITY_MERGE + TXCStatus.b(this.r, 2004) + " " + "u32_nodata_cnt" + HttpRequest.HTTP_REQ_ENTITY_MERGE + TXCStatus.b(this.r, 2005) + " " + "u32_avg_cache_time" + HttpRequest.HTTP_REQ_ENTITY_MERGE + TXCStatus.b(this.r, 2007) + " " + "u32_is_real_time" + HttpRequest.HTTP_REQ_ENTITY_MERGE + TXCStatus.b(this.r, 2008));
    }

    private void g() {
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.url = this.c;
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = true;
        String str = (String) this.b.get("token");
        TXCDRApi.InitEvent(this.d, str, com.tencent.liteav.basic.datareport.a.U, com.tencent.liteav.basic.datareport.a.al, tXCDRExtInfo);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.U, "u32_avg_net_speed", (long) (TXCStatus.d(this.r, 7102) + TXCStatus.d(this.r, 7101)));
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.U, "u32_fps", (long) ((int) TXCStatus.e(this.r, GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_INVALID_MULTIPLAYER_TYPE)));
        long b2 = TXCStatus.b(this.r, GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_INVALID_OPERATION);
        if (this.h == -1) {
            TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.U, "u32_avg_block_count", 0);
        } else if (b2 >= this.h) {
            TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.U, "u32_avg_block_count", b2 - this.h);
        } else {
            TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.U, "u32_avg_block_count", 0);
        }
        this.h = b2;
        int[] a2 = com.tencent.liteav.basic.util.a.a();
        long b3 = TXCStatus.b(this.r, 2006);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.U, "u32_avg_cache_count", b3);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.U, "u32_cpu_usage", (long) a2[1]);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.U, "u32_app_cpu_usage", (long) a2[0]);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.U, "str_app_version", this.e);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.U, "u32_isp2p", (long) this.g);
        TXCDRApi.nativeReportEvent(str, com.tencent.liteav.basic.datareport.a.U);
        if (this.l) {
            this.p++;
            this.o += b3;
            if (b3 > this.n) {
                this.n = b3;
            }
        }
    }

    private void h() {
        long j2;
        long j3;
        HashMap hashMap = new HashMap();
        String c2 = TXCStatus.c(this.r, 7113);
        String c3 = TXCStatus.c(this.r, 7114);
        String c4 = TXCStatus.c(this.r, 7115);
        int d2 = TXCStatus.d(this.r, 7105);
        String c5 = TXCStatus.c(this.r, 7106);
        int d3 = TXCStatus.d(this.r, 7111);
        hashMap.put("stream_url", c2);
        hashMap.put("stream_id", c3);
        hashMap.put("bizid", c4);
        hashMap.put("err_code", String.valueOf(d2));
        hashMap.put("err_info", c5);
        hashMap.put("channel_type", String.valueOf(d3));
        long currentTimeMillis = System.currentTimeMillis();
        long j4 = currentTimeMillis - this.m;
        hashMap.put("start_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date(this.m)));
        hashMap.put("end_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date(currentTimeMillis)));
        hashMap.put("total_time", String.valueOf(j4));
        long b2 = TXCStatus.b(this.r, GamesStatusCodes.STATUS_MULTIPLAYER_DISABLED);
        long b3 = TXCStatus.b(this.r, 6006);
        long b4 = TXCStatus.b(this.r, 6005);
        if (b2 != 0) {
            j2 = b3 / b2;
        } else {
            j2 = 0;
        }
        hashMap.put("block_count", String.valueOf(b2));
        hashMap.put("block_duration_max", String.valueOf(b4));
        hashMap.put("block_duration_avg", String.valueOf(j2));
        if (this.p != 0) {
            j3 = this.o / this.p;
        } else {
            j3 = 0;
        }
        hashMap.put("jitter_cache_max", String.valueOf(this.n));
        hashMap.put("jitter_cache_avg", String.valueOf(j3));
        String txCreateToken = TXCDRApi.txCreateToken();
        int i2 = com.tencent.liteav.basic.datareport.a.ae;
        int i3 = com.tencent.liteav.basic.datareport.a.ak;
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.command_id_comment = "LINKMIC";
        TXCDRApi.InitEvent(this.d, txCreateToken, i2, i3, tXCDRExtInfo);
        TXCLog.d(a, "report evt 40402: token=" + txCreateToken);
        for (Map.Entry entry : hashMap.entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            TXCLog.e(a, "RealTimePlayStatisticInfo: " + str + " = " + str2);
            if (!(str == null || str.length() <= 0 || str2 == null)) {
                TXCDRApi.txSetEventValue(txCreateToken, i2, str, str2);
            }
        }
        TXCDRApi.nativeReportEvent(txCreateToken, i2);
        this.l = false;
        this.m = 0;
        this.p = 0;
        this.o = 0;
        this.n = 0;
    }

    private int c(String str) {
        Number number = (Number) this.b.get(str);
        if (number != null) {
            return number.intValue();
        }
        return 0;
    }

    private void i() {
        this.i = false;
        this.j = 0;
        this.b.put("str_user_id", com.tencent.liteav.basic.util.a.a(this.d));
        this.b.put("str_device_type", com.tencent.liteav.basic.util.a.b());
        this.b.put("str_device_type", com.tencent.liteav.basic.util.a.b());
        this.b.put("u32_network_type", Integer.valueOf(com.tencent.liteav.basic.util.a.c(this.d)));
        this.b.put("token", com.tencent.liteav.basic.util.a.c());
        this.b.put("str_package_name", com.tencent.liteav.basic.util.a.b(this.d));
        this.b.put("dev_uuid", com.tencent.liteav.basic.util.a.d(this.d));
    }

    private void b(boolean z) {
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.url = this.c;
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = false;
        String str = (String) this.b.get("token");
        TXCDRApi.InitEvent(this.d, str, com.tencent.liteav.basic.datareport.a.W, com.tencent.liteav.basic.datareport.a.al, tXCDRExtInfo);
        this.q = TXCTimeUtil.getUtcTimeTick();
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.W, "u64_timestamp", String.valueOf(this.q));
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.W, "str_device_type", (String) this.b.get("str_device_type"));
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.W, "u32_network_type", (long) c("u32_network_type"));
        long b2 = TXCStatus.b(this.r, 7107);
        long b3 = TXCStatus.b(this.r, 7108);
        if (b3 != -1) {
            b3 -= b2;
        }
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.W, "u32_dns_time", z ? b3 : -1);
        String c2 = TXCStatus.c(this.r, 7110);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.W, "u32_server_ip", z ? c2 : "");
        long b4 = TXCStatus.b(this.r, 7109);
        if (b4 != -1) {
            b4 -= b2;
        }
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.W, "u32_connect_server_time", z ? b4 : -1);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.W, "u32_stream_begin", -1);
        this.f = TXCStatus.b(this.r, GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER) - b2;
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.W, "u32_first_i_frame", this.f);
        long b5 = TXCStatus.b(this.r, 7103) - b2;
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.W, "u32_first_frame_down", b5);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.W, "str_user_id", (String) this.b.get("str_user_id"));
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.W, "str_package_name", (String) this.b.get("str_package_name"));
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.W, "str_app_version", this.e);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.W, "dev_uuid", (String) this.b.get("dev_uuid"));
        int d2 = TXCStatus.d(this.r, EFvrECode._ERR_FVR_NONSUPPORT);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.W, "u32_max_cache_time", String.valueOf(d2));
        int d3 = TXCStatus.d(this.r, 2012);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.W, "u32_min_cache_time", String.valueOf(d3));
        int d4 = TXCStatus.d(this.r, 7105);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.W, "u64_err_code", String.valueOf(d4));
        String c3 = TXCStatus.c(this.r, 7106);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.W, "str_err_info", c3);
        int d5 = TXCStatus.d(this.r, 7112);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.W, "u32_link_type", String.valueOf(d5));
        int d6 = TXCStatus.d(this.r, 7111);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.W, "u32_channel_type", String.valueOf(d6));
        TXCDRApi.nativeReportEvent(str, com.tencent.liteav.basic.datareport.a.W);
        TXCLog.d(a, "report evt 40101: token=" + str + " " + "u64_timestamp" + HttpRequest.HTTP_REQ_ENTITY_MERGE + this.q + " " + "str_device_type" + HttpRequest.HTTP_REQ_ENTITY_MERGE + ((String) this.b.get("str_device_type")) + " " + "u32_network_type" + HttpRequest.HTTP_REQ_ENTITY_MERGE + c("u32_network_type") + " " + "u32_dns_time" + HttpRequest.HTTP_REQ_ENTITY_MERGE + b3 + " " + "u32_server_ip" + HttpRequest.HTTP_REQ_ENTITY_MERGE + c2 + " " + "u32_connect_server_time" + HttpRequest.HTTP_REQ_ENTITY_MERGE + b4 + " " + "u32_stream_begin" + "=-1 " + "u32_first_i_frame" + HttpRequest.HTTP_REQ_ENTITY_MERGE + this.f + " " + "u32_first_frame_down" + HttpRequest.HTTP_REQ_ENTITY_MERGE + b5 + " " + "str_user_id" + HttpRequest.HTTP_REQ_ENTITY_MERGE + ((String) this.b.get("str_user_id")) + " " + "str_package_name" + HttpRequest.HTTP_REQ_ENTITY_MERGE + ((String) this.b.get("str_package_name")) + " " + "str_app_version" + HttpRequest.HTTP_REQ_ENTITY_MERGE + this.e + " " + "dev_uuid" + HttpRequest.HTTP_REQ_ENTITY_MERGE + ((String) this.b.get("dev_uuid")) + " " + "u32_max_cache_time" + HttpRequest.HTTP_REQ_ENTITY_MERGE + d2 + " " + "u32_min_cache_time" + HttpRequest.HTTP_REQ_ENTITY_MERGE + d3 + " " + "u64_err_code" + HttpRequest.HTTP_REQ_ENTITY_MERGE + d4 + " " + "str_err_info" + HttpRequest.HTTP_REQ_ENTITY_MERGE + c3 + " " + "u32_link_type" + HttpRequest.HTTP_REQ_ENTITY_MERGE + d5 + " " + "u32_channel_type" + HttpRequest.HTTP_REQ_ENTITY_MERGE + d6);
    }

    private void j() {
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.url = this.c;
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = false;
        String str = (String) this.b.get("token");
        TXCDRApi.InitEvent(this.d, str, com.tencent.liteav.basic.datareport.a.Y, com.tencent.liteav.basic.datareport.a.al, tXCDRExtInfo);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.Y, "u64_begin_timestamp", String.valueOf(this.q));
        long utcTimeTick = TXCTimeUtil.getUtcTimeTick();
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.Y, "u64_end_timestamp", utcTimeTick);
        long j2 = (utcTimeTick - this.q) / 1000;
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.Y, "u64_playtime", j2);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.Y, "str_device_type", (String) this.b.get("str_device_type"));
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.Y, "u32_network_type", (long) c("u32_network_type"));
        String c2 = TXCStatus.c(this.r, 7110);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.Y, "u32_server_ip", c2);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.Y, "str_user_id", (String) this.b.get("str_user_id"));
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.Y, "str_package_name", (String) this.b.get("str_package_name"));
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.Y, "str_app_version", this.e);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.Y, "dev_uuid", (String) this.b.get("dev_uuid"));
        long b2 = TXCStatus.b(this.r, GamesStatusCodes.STATUS_MULTIPLAYER_DISABLED);
        long b3 = TXCStatus.b(this.r, 6005);
        long b4 = TXCStatus.b(this.r, 6006);
        long j3 = 0;
        if (b2 > 0) {
            j3 = b4 / b2;
        }
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.Y, "u64_block_count", b2);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.Y, "u64_block_duration_max", b3);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.Y, "u64_block_duration_avg", j3);
        long b5 = TXCStatus.b(this.r, 6009);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.Y, "u64_jitter_cache_max", b5);
        long b6 = TXCStatus.b(this.r, 6008);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.Y, "u64_jitter_cache_avg", b6);
        long b7 = TXCStatus.b(this.r, 2007);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.Y, "u64_audio_cache_avg", b7);
        int d2 = TXCStatus.d(this.r, 7112);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.Y, "u32_link_type", String.valueOf(d2));
        long d3 = (long) TXCStatus.d(this.r, 2001);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.Y, "u32_avg_load", String.valueOf(d3));
        long d4 = (long) TXCStatus.d(this.r, 2002);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.Y, "u32_load_cnt", String.valueOf(d4));
        long d5 = (long) TXCStatus.d(this.r, 2003);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.Y, "u32_max_load", String.valueOf(d5));
        int d6 = TXCStatus.d(this.r, 7111);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.Y, "u32_channel_type", String.valueOf(d6));
        int d7 = TXCStatus.d(this.r, 7116);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.Y, "u32_ip_count_quic", String.valueOf(d7));
        int d8 = TXCStatus.d(this.r, 7117);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.Y, "u32_connect_count_quic", String.valueOf(d8));
        int d9 = TXCStatus.d(this.r, 7118);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.Y, "u32_connect_count_tcp", String.valueOf(d9));
        TXCDRApi.nativeReportEvent(str, com.tencent.liteav.basic.datareport.a.Y);
        TXCLog.d(a, "report evt 40102: token=" + str + " " + "str_stream_url" + HttpRequest.HTTP_REQ_ENTITY_MERGE + this.c + " " + "u64_begin_timestamp" + HttpRequest.HTTP_REQ_ENTITY_MERGE + this.q + " " + "u64_end_timestamp" + HttpRequest.HTTP_REQ_ENTITY_MERGE + utcTimeTick + " " + "u64_playtime" + HttpRequest.HTTP_REQ_ENTITY_MERGE + j2 + " " + "str_device_type" + HttpRequest.HTTP_REQ_ENTITY_MERGE + ((String) this.b.get("str_device_type")) + " " + "u32_network_type" + HttpRequest.HTTP_REQ_ENTITY_MERGE + c("u32_network_type") + " " + "u32_server_ip" + HttpRequest.HTTP_REQ_ENTITY_MERGE + c2 + " " + "str_user_id" + HttpRequest.HTTP_REQ_ENTITY_MERGE + ((String) this.b.get("str_user_id")) + " " + "str_package_name" + HttpRequest.HTTP_REQ_ENTITY_MERGE + ((String) this.b.get("str_package_name")) + " " + "str_app_version" + HttpRequest.HTTP_REQ_ENTITY_MERGE + this.e + " " + "dev_uuid" + HttpRequest.HTTP_REQ_ENTITY_MERGE + ((String) this.b.get("dev_uuid")) + " " + "u64_block_count" + HttpRequest.HTTP_REQ_ENTITY_MERGE + b2 + " " + "u64_block_duration_max" + HttpRequest.HTTP_REQ_ENTITY_MERGE + b3 + " " + "u64_block_duration_avg" + HttpRequest.HTTP_REQ_ENTITY_MERGE + j3 + " " + "u64_jitter_cache_max" + HttpRequest.HTTP_REQ_ENTITY_MERGE + b5 + " " + "u64_jitter_cache_avg" + HttpRequest.HTTP_REQ_ENTITY_MERGE + b6 + " " + "u64_audio_cache_avg" + HttpRequest.HTTP_REQ_ENTITY_MERGE + b7 + " " + "u32_link_type" + HttpRequest.HTTP_REQ_ENTITY_MERGE + d2 + " " + "u32_avg_load" + HttpRequest.HTTP_REQ_ENTITY_MERGE + d3 + " " + "u32_load_cnt" + HttpRequest.HTTP_REQ_ENTITY_MERGE + d4 + " " + "u32_max_load" + HttpRequest.HTTP_REQ_ENTITY_MERGE + d5 + " " + "u32_channel_type" + HttpRequest.HTTP_REQ_ENTITY_MERGE + d6);
    }

    private void k() {
        int i2;
        int i3 = 2;
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.url = this.c;
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = true;
        String str = (String) this.b.get("token");
        TXCDRApi.InitEvent(this.d, str, com.tencent.liteav.basic.datareport.a.X, com.tencent.liteav.basic.datareport.a.al, tXCDRExtInfo);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.X, "u32_net_speed", (long) (TXCStatus.d(this.r, 7102) + TXCStatus.d(this.r, 7101)));
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.X, "u32_fps", (long) ((int) TXCStatus.e(this.r, GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_INVALID_MULTIPLAYER_TYPE)));
        long b2 = TXCStatus.b(this.r, GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_INVALID_OPERATION);
        if (this.h == -1) {
            TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.X, "u32_video_block_count", 0);
        } else if (b2 >= this.h) {
            TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.X, "u32_video_block_count", b2 - this.h);
        } else {
            TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.X, "u32_video_block_count", 0);
        }
        this.h = b2;
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.X, "u32_video_cache_count", TXCStatus.b(this.r, 2006));
        int[] a2 = com.tencent.liteav.basic.util.a.a();
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.X, "u32_cpu_usage", (long) a2[1]);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.X, "u32_app_cpu_usage", (long) a2[0]);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.X, "str_app_version", this.e);
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.X, "str_device_type", (String) this.b.get("str_device_type"));
        if (TXCStatus.d(this.r, 5002) == 0) {
            i2 = 2;
        } else {
            i2 = 1;
        }
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.X, "u32_video_decode_type", (long) i2);
        if (TXCStatus.d(this.r, EFvrECode._ERR_FVR_IMGCVT_ERR) != 0) {
            i3 = 1;
        }
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.X, "u32_audio_decode_type", (long) i3);
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.X, "u32_network_type", (long) c("u32_network_type"));
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.X, "u32_video_cache_time", (long) TXCStatus.d(this.r, 6007));
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.X, "u32_audio_cache_time", (long) TXCStatus.d(this.r, 2010));
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.X, "u32_audio_jitter", (long) TXCStatus.d(this.r, 2011));
        TXCDRApi.txSetEventIntValue(str, com.tencent.liteav.basic.datareport.a.X, "u32_audio_drop", (long) TXCStatus.d(this.r, EFvrECode._ERR_FVR_IMGCVT_EXCEPTION));
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.X, "u64_playtime", String.valueOf((TXCTimeUtil.getUtcTimeTick() - this.q) / 1000));
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.X, "u32_link_type", String.valueOf(TXCStatus.d(this.r, 7112)));
        TXCDRApi.txSetEventValue(str, com.tencent.liteav.basic.datareport.a.X, "u32_channel_type", String.valueOf(TXCStatus.d(this.r, 7111)));
        TXCDRApi.nativeReportEvent(str, com.tencent.liteav.basic.datareport.a.X);
    }
}
