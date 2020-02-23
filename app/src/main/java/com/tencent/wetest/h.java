package com.tencent.wetest;

import android.util.Log;
import java.lang.Thread;

class h implements Thread.UncaughtExceptionHandler {
    h() {
    }

    public void uncaughtException(Thread thread, Throwable th) {
        try {
            String canonicalName = th.getClass().getCanonicalName();
            String message = th.getMessage();
            StackTraceElement[] stackTrace = th.getStackTrace();
            StringBuilder sb = new StringBuilder();
            sb.append(th.toString()).append("\n");
            for (StackTraceElement stackTraceElement : stackTrace) {
                sb.append(stackTraceElement.toString()).append("\n");
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("*****************<WetestReport>*********************\n");
            sb2.append("EXCEPTION TYPE: ");
            sb2.append(canonicalName).append("\n");
            sb2.append("MESSAGE: ");
            sb2.append(message).append("\n");
            sb2.append("STACKTRACE: \n");
            sb2.append(sb).append("\n\n");
            sb2.append("SCENE: ");
            sb2.append("").append("\n");
            sb2.append("UNCAUGHT: ");
            sb2.append("True").append("\n");
            sb2.append("CRASH TYPE: ");
            sb2.append("JAVA CRASH\n");
            sb2.append("INDEX: ");
            int a = WetestReport.b;
            WetestReport.b = a + 1;
            sb2.append(a);
            sb2.append("\n");
            sb2.append("***************************************************\n");
            Log.e(WetestReport.TAG, sb2.toString());
            Thread.sleep(100);
        } catch (Exception e) {
            Log.d("wetest", "WetestReport error handler uncaught exception");
        }
        WetestReport.e.uncaughtException(thread, th);
    }
}
