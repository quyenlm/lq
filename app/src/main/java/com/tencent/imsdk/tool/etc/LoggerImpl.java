package com.tencent.imsdk.tool.etc;

import android.util.Log;
import com.tencent.tp.a.h;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Arrays;

class LoggerImpl {
    private static final String BOTTOM_BORDER = "╚════════════════════════════════════════════════════════════════════════════════════════";
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final int CHUNK_SIZE = 4000;
    private static final int DEBUG = 3;
    private static final String DEFAULT_TAG = "iMSDK";
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final int ERROR = 6;
    private static final char HORIZONTAL_DOUBLE_LINE = '║';
    private static final int INFO = 4;
    private static final int JSON_INDENT = 2;
    public static final int METHOD_COUNT = 1;
    private static final String MIDDLE_BORDER = "╟────────────────────────────────────────────────────────────────────────────────────────";
    private static final char MIDDLE_CORNER = '╟';
    private static final int MIN_STACK_OFFSET = 3;
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TOP_BORDER = "╔════════════════════════════════════════════════════════════════════════════════════════";
    private static final char TOP_LEFT_CORNER = '╔';
    private static final int WARN = 5;

    LoggerImpl() {
    }

    public void d(String message, Object... args) {
        log(3, message, (Throwable) null, args);
    }

    public void d(Object object) {
        String message;
        if (object == null) {
            message = "Warning : object is Null";
        } else if (object.getClass().isArray()) {
            message = Arrays.deepToString((Object[]) object);
        } else if (object instanceof Exception) {
            message = ((Exception) object).getMessage().toString();
        } else {
            message = object.toString();
        }
        log(3, message, (Throwable) null, new Object[0]);
    }

    public void e(String message, Object... args) {
        e(message, (Throwable) null, args);
    }

    public void e(String message, Throwable throwable, Object... args) {
        log(6, message, throwable, args);
    }

    public void w(String message, Object... args) {
        log(5, message, (Throwable) null, args);
    }

    public void i(String message, Object... args) {
        log(4, message, (Throwable) null, args);
    }

    private String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }
        for (Throwable t = tr; t != null; t = t.getCause()) {
            if (t instanceof UnknownHostException) {
                return "";
            }
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0030  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void log(int r4, java.lang.String r5, java.lang.Throwable r6, java.lang.Object... r7) {
        /*
            r3 = this;
            monitor-enter(r3)
            if (r7 == 0) goto L_0x0006
            int r1 = r7.length     // Catch:{ all -> 0x0046 }
            if (r1 != 0) goto L_0x0041
        L_0x0006:
            r0 = r5
        L_0x0007:
            if (r6 == 0) goto L_0x0026
            if (r0 == 0) goto L_0x0026
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0046 }
            r1.<init>()     // Catch:{ all -> 0x0046 }
            java.lang.StringBuilder r1 = r1.append(r0)     // Catch:{ all -> 0x0046 }
            java.lang.String r2 = " : "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x0046 }
            java.lang.String r2 = r3.getStackTraceString(r6)     // Catch:{ all -> 0x0046 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x0046 }
            java.lang.String r0 = r1.toString()     // Catch:{ all -> 0x0046 }
        L_0x0026:
            if (r6 == 0) goto L_0x002e
            if (r0 != 0) goto L_0x002e
            java.lang.String r0 = r3.getStackTraceString(r6)     // Catch:{ all -> 0x0046 }
        L_0x002e:
            if (r0 != 0) goto L_0x0032
            java.lang.String r0 = "No message/exception is set"
        L_0x0032:
            if (r0 == 0) goto L_0x003a
            int r1 = r0.length()     // Catch:{ all -> 0x0046 }
            if (r1 != 0) goto L_0x003c
        L_0x003a:
            java.lang.String r0 = "Empty/NULL log message"
        L_0x003c:
            r3.printConsoleLog(r4, r0)     // Catch:{ all -> 0x0046 }
            monitor-exit(r3)
            return
        L_0x0041:
            java.lang.String r0 = java.lang.String.format(r5, r7)     // Catch:{ all -> 0x0046 }
            goto L_0x0007
        L_0x0046:
            r1 = move-exception
            monitor-exit(r3)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.tool.etc.LoggerImpl.log(int, java.lang.String, java.lang.Throwable, java.lang.Object[]):void");
    }

    private void printConsoleLog(int priority, String message) {
        logChunk(priority, TOP_BORDER);
        logHeaderContent(priority);
        byte[] bytes = message.getBytes(Charset.forName("UTF-8"));
        int length = bytes.length;
        if (length <= 4000) {
            logDivider(priority);
            logContent(priority, message);
            logBottomBorder(priority);
            return;
        }
        logDivider(priority);
        for (int i = 0; i < length; i += 4000) {
            logContent(priority, new String(bytes, i, Math.min(length - i, 4000)));
        }
        logBottomBorder(priority);
    }

    private void logHeaderContent(int logType) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String level = "";
        int stackOffset = getStackOffset(trace);
        int methodCount = 1;
        if (1 + stackOffset > trace.length) {
            methodCount = (trace.length - stackOffset) - 1;
        }
        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex < trace.length) {
                StringBuilder builder = new StringBuilder();
                builder.append("║ ").append(level).append(getSimpleClassName(trace[stackIndex].getClassName())).append(".").append(trace[stackIndex].getMethodName()).append(" ").append(" (").append(trace[stackIndex].getFileName()).append(":").append(trace[stackIndex].getLineNumber()).append(h.b);
                level = level + "   ";
                logChunk(logType, builder.toString());
            }
        }
    }

    private void logBottomBorder(int logType) {
        logChunk(logType, BOTTOM_BORDER);
    }

    private void logDivider(int logType) {
        logChunk(logType, MIDDLE_BORDER);
    }

    private void logContent(int logType, String chunk) {
        String[] lines = chunk.split(System.getProperty("line.separator"));
        int length = lines.length;
        for (int i = 0; i < length; i++) {
            logChunk(logType, "║ " + lines[i]);
        }
    }

    private void logChunk(int logType, String chunk) {
        switch (logType) {
            case 4:
                Log.i(DEFAULT_TAG, chunk);
                return;
            case 5:
                Log.w(DEFAULT_TAG, chunk);
                return;
            case 6:
                Log.e(DEFAULT_TAG, chunk);
                return;
            default:
                Log.d(DEFAULT_TAG, chunk);
                return;
        }
    }

    private String getSimpleClassName(String name) {
        return name.substring(name.lastIndexOf(".") + 1);
    }

    private int getStackOffset(StackTraceElement[] trace) {
        int i = 3;
        while (i < trace.length) {
            String name = trace[i].getClassName();
            if (name.equals(LoggerImpl.class.getName()) || name.equals(IMLogger.class.getName())) {
                i++;
            } else {
                int i2 = i - 1;
                int i3 = i2;
                return i2;
            }
        }
        int i4 = i;
        return -1;
    }
}
