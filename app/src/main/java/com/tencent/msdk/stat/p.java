package com.tencent.msdk.stat;

import com.tencent.msdk.stat.a.c;

final class p implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ c b;

    p(String str, c cVar) {
        this.a = str;
        this.b = cVar;
    }

    public void run() {
        try {
            if (StatServiceImpl.a(this.a)) {
                StatServiceImpl.q.error((Object) "The event_id of StatService.trackCustomBeginEvent() can not be null or empty.");
                return;
            }
            if (StatConfig.isDebugEnable()) {
                StatServiceImpl.q.i("add begin key:" + this.b.toString());
            }
            if (StatServiceImpl.e.containsKey(this.b)) {
                StatServiceImpl.q.error((Object) "Duplicate CustomEvent key: " + this.b.toString() + ", trackCustomBeginEvent() repeated?");
            } else if (StatServiceImpl.e.size() <= StatConfig.getMaxParallelTimmingEvents()) {
                StatServiceImpl.e.put(this.b, Long.valueOf(System.currentTimeMillis()));
            } else {
                StatServiceImpl.q.error((Object) "The number of timedEvent exceeds the maximum value " + Integer.toString(StatConfig.getMaxParallelTimmingEvents()));
            }
        } catch (Throwable th) {
            StatServiceImpl.q.e(th);
        }
    }
}
