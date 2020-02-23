package com.tencent.msdk.stat;

import android.content.Context;
import com.tencent.msdk.stat.a.b;
import com.tencent.msdk.stat.a.d;
import com.tencent.msdk.stat.a.e;
import com.tencent.msdk.stat.common.j;
import com.tencent.msdk.stat.common.o;

class af {
    private static volatile long f = 0;
    /* access modifiers changed from: private */
    public d a;
    private StatReportStrategy b = null;
    /* access modifiers changed from: private */
    public boolean c = false;
    /* access modifiers changed from: private */
    public Context d = null;
    private long e = System.currentTimeMillis();

    public af(d dVar) {
        this.a = dVar;
        this.b = StatConfig.getStatSendStrategy();
        this.c = dVar.f();
        this.d = dVar.e();
    }

    private void a(j jVar) {
        k.b(StatServiceImpl.t).a(this.a, jVar);
    }

    private void b() {
        if (this.a.a() == e.CUSTOM) {
            String str = ((b) this.a).b().a;
            if (StatConfig.isEventIdInDontReportEventIdsSet(str)) {
                StatServiceImpl.q.w("eventid=" + str + " In DontReportEventIdsSet, droped.");
                return;
            }
        }
        if (this.a.d() != null && this.a.d().isSendImmediately()) {
            this.b = StatReportStrategy.INSTANT;
        }
        if (StatConfig.j && a.a(StatServiceImpl.t).e()) {
            this.b = StatReportStrategy.INSTANT;
        }
        if (StatConfig.isDebugEnable()) {
            StatServiceImpl.q.i("strategy=" + this.b.name());
        }
        switch (w.a[this.b.ordinal()]) {
            case 1:
                c();
                return;
            case 2:
                aj.a(this.d).a(this.a, (j) null, this.c, false);
                if (StatConfig.isDebugEnable()) {
                    StatServiceImpl.q.i("PERIOD currTime=" + this.e + ",nextPeriodSendTs=" + StatServiceImpl.c + ",difftime=" + (StatServiceImpl.c - this.e));
                }
                if (StatServiceImpl.c == 0) {
                    StatServiceImpl.c = o.a(this.d, "last_period_ts", 0);
                    if (this.e > StatServiceImpl.c) {
                        StatServiceImpl.d(this.d);
                    }
                    long sendPeriodMinutes = this.e + ((long) (StatConfig.getSendPeriodMinutes() * 60 * 1000));
                    if (StatServiceImpl.c > sendPeriodMinutes) {
                        StatServiceImpl.c = sendPeriodMinutes;
                    }
                    d.a(this.d).a();
                }
                if (StatConfig.isDebugEnable()) {
                    StatServiceImpl.q.i("PERIOD currTime=" + this.e + ",nextPeriodSendTs=" + StatServiceImpl.c + ",difftime=" + (StatServiceImpl.c - this.e));
                }
                if (this.e > StatServiceImpl.c) {
                    StatServiceImpl.d(this.d);
                    return;
                }
                return;
            case 3:
            case 4:
                aj.a(this.d).a(this.a, (j) null, this.c, false);
                return;
            case 5:
                aj.a(this.d).a(this.a, (j) new ag(this), this.c, true);
                return;
            case 6:
                if (a.a(StatServiceImpl.t).c() == 1) {
                    c();
                    return;
                } else {
                    aj.a(this.d).a(this.a, (j) null, this.c, false);
                    return;
                }
            case 7:
                if (j.e(this.d)) {
                    a((j) new ah(this));
                    return;
                }
                return;
            default:
                StatServiceImpl.q.error((Object) "Invalid stat strategy:" + StatConfig.getStatSendStrategy());
                return;
        }
    }

    private void c() {
        if ((aj.b().a <= 0 || !StatConfig.l) && this.a.a() != e.BACKGROUND) {
            a((j) new ai(this));
            return;
        }
        aj.b().a(this.a, (j) null, this.c, true);
        aj.b().a(-1);
    }

    private boolean d() {
        if (StatConfig.h > 0) {
            if (this.e > StatServiceImpl.h) {
                StatServiceImpl.g.clear();
                long unused = StatServiceImpl.h = this.e + StatConfig.i;
                if (StatConfig.isDebugEnable()) {
                    StatServiceImpl.q.i("clear methodsCalledLimitMap, nextLimitCallClearTime=" + StatServiceImpl.h);
                }
            }
            Integer valueOf = Integer.valueOf(this.a.a().a());
            Integer num = (Integer) StatServiceImpl.g.get(valueOf);
            if (num != null) {
                StatServiceImpl.g.put(valueOf, Integer.valueOf(num.intValue() + 1));
                if (num.intValue() > StatConfig.h) {
                    if (StatConfig.isDebugEnable()) {
                        StatServiceImpl.q.e((Object) "event " + this.a.g() + " was discard, cause of called limit, current:" + num + ", limit:" + StatConfig.h + ", period:" + StatConfig.i + " ms");
                    }
                    return true;
                }
            } else {
                StatServiceImpl.g.put(valueOf, 1);
            }
        }
        return false;
    }

    public void a() {
        if (!d()) {
            if (StatConfig.m > 0 && this.e >= f) {
                StatServiceImpl.flushDataToDB(this.d);
                f = this.e + StatConfig.n;
                if (StatConfig.isDebugEnable()) {
                    StatServiceImpl.q.i("nextFlushTime=" + f);
                }
            }
            if (a.a(this.d).f()) {
                if (StatConfig.isDebugEnable()) {
                    StatServiceImpl.q.i("sendFailedCount=" + StatServiceImpl.a);
                }
                if (!StatServiceImpl.a()) {
                    b();
                    return;
                }
                aj.a(this.d).a(this.a, (j) null, this.c, false);
                if (this.e - StatServiceImpl.b > 1800000) {
                    StatServiceImpl.e(this.d);
                    return;
                }
                return;
            }
            aj.a(this.d).a(this.a, (j) null, this.c, false);
        }
    }
}
