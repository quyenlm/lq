package com.garena.network;

import android.util.Log;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.networking.CommonEventLoop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SimpleSocketClient {
    static final String LOG_FORMATTER = "<22>1 %s %s %s - - - %s\n";
    static int port = 52173;
    static final String testServerName = "logs.papertrailapp.com";

    public static void sendLog(String tag, String aMessage) {
        final String logMessage = String.format(Locale.ENGLISH, LOG_FORMATTER, new Object[]{getDateTimeInISO8601(), "Android", tag, aMessage});
        CommonEventLoop.getInstance().post(new Runnable() {
            public void run() {
                Socket socket = null;
                try {
                    socket = SimpleSocketClient.openSocket(SimpleSocketClient.testServerName, SimpleSocketClient.port);
                    SimpleSocketClient.writeToSocket(socket, logMessage);
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                } catch (Throwable th) {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            }
        });
    }

    private static String getDateTimeInISO8601() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        return df.format(new Date());
    }

    /* access modifiers changed from: private */
    public static void writeToSocket(Socket socket, String writeTo) throws Exception {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(writeTo);
            bufferedWriter.flush();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String str = bufferedReader.readLine();
                if (str != null) {
                    sb.append(str + "\n");
                } else {
                    Log.d(BBLogger.APP_LOG_FLAG, sb.toString());
                    bufferedReader.close();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /* access modifiers changed from: private */
    public static Socket openSocket(String server, int port2) throws Exception {
        try {
            SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(server), port2);
            Socket socket = new Socket();
            socket.connect(socketAddress, 10000);
            return socket;
        } catch (SocketTimeoutException ste) {
            System.err.println("Timed out waiting for the socket.");
            ste.printStackTrace();
            throw ste;
        }
    }
}
