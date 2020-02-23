package com.garena.network;

import com.amazonaws.util.DateUtils;
import com.beetalk.sdk.networking.CommonEventLoop;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SimpleDatagramSocket {
    public static final String ENCODING = "UTF-8";
    static final String LOG_FORMATTER = "<22>1 %s %s %s - - - %s";
    static final String paperTrailHost = "logs.papertrailapp.com";
    static int port = 52173;

    public static void sendLog(String tag, String message) {
        CommonEventLoop.getInstance().delayPost(new LoggerTask(tag, message), 200);
    }

    /* access modifiers changed from: private */
    public static String getDateTimeInISO8601() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat(DateUtils.ALTERNATE_ISO8601_DATE_PATTERN);
        df.setTimeZone(tz);
        return df.format(new Date());
    }

    private static class LoggerTask implements Runnable {
        private String finalMessage;
        private String message;
        private String tag;

        private LoggerTask(String tag2, String message2) {
            this.tag = tag2;
            this.message = message2;
            this.finalMessage = String.format(Locale.ENGLISH, SimpleDatagramSocket.LOG_FORMATTER, new Object[]{SimpleDatagramSocket.getDateTimeInISO8601(), "Android", tag2, message2});
        }

        /* JADX WARNING: Removed duplicated region for block: B:12:0x002c  */
        /* JADX WARNING: Removed duplicated region for block: B:15:0x0033  */
        /* JADX WARNING: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r8 = this;
                r2 = 0
                java.net.DatagramSocket r3 = new java.net.DatagramSocket     // Catch:{ Exception -> 0x0026 }
                r3.<init>()     // Catch:{ Exception -> 0x0026 }
                java.lang.String r6 = "logs.papertrailapp.com"
                java.net.InetAddress r0 = java.net.InetAddress.getByName(r6)     // Catch:{ Exception -> 0x003a, all -> 0x0037 }
                java.lang.String r6 = r8.finalMessage     // Catch:{ Exception -> 0x003a, all -> 0x0037 }
                java.lang.String r7 = "UTF-8"
                byte[] r1 = r6.getBytes(r7)     // Catch:{ Exception -> 0x003a, all -> 0x0037 }
                java.net.DatagramPacket r5 = new java.net.DatagramPacket     // Catch:{ Exception -> 0x003a, all -> 0x0037 }
                int r6 = r1.length     // Catch:{ Exception -> 0x003a, all -> 0x0037 }
                int r7 = com.garena.network.SimpleDatagramSocket.port     // Catch:{ Exception -> 0x003a, all -> 0x0037 }
                r5.<init>(r1, r6, r0, r7)     // Catch:{ Exception -> 0x003a, all -> 0x0037 }
                r3.send(r5)     // Catch:{ Exception -> 0x003a, all -> 0x0037 }
                if (r3 == 0) goto L_0x003d
                r3.close()
                r2 = r3
            L_0x0025:
                return
            L_0x0026:
                r4 = move-exception
            L_0x0027:
                r4.printStackTrace()     // Catch:{ all -> 0x0030 }
                if (r2 == 0) goto L_0x0025
                r2.close()
                goto L_0x0025
            L_0x0030:
                r6 = move-exception
            L_0x0031:
                if (r2 == 0) goto L_0x0036
                r2.close()
            L_0x0036:
                throw r6
            L_0x0037:
                r6 = move-exception
                r2 = r3
                goto L_0x0031
            L_0x003a:
                r4 = move-exception
                r2 = r3
                goto L_0x0027
            L_0x003d:
                r2 = r3
                goto L_0x0025
            */
            throw new UnsupportedOperationException("Method not decompiled: com.garena.network.SimpleDatagramSocket.LoggerTask.run():void");
        }
    }
}
