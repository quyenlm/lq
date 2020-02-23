package com.tencent.smtt.utils;

import com.tencent.smtt.utils.e;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

final class j extends Thread {
    final /* synthetic */ String a;
    final /* synthetic */ e.a b;

    j(String str, e.a aVar) {
        this.a = str;
        this.b = aVar;
    }

    public void run() {
        FileOutputStream fileOutputStream;
        InputStream inputStream;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("http://soft.tbs.imtt.qq.com/17421/tbs_res_imtt_tbs_DebugPlugin_DebugPlugin.tbs").openConnection();
            int contentLength = httpURLConnection.getContentLength();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            try {
                fileOutputStream = k.d(new File(this.a));
            } catch (Exception e) {
                e = e;
                fileOutputStream = null;
                try {
                    e.printStackTrace();
                    this.b.a((Throwable) e);
                    try {
                        inputStream.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    try {
                        fileOutputStream.close();
                        return;
                    } catch (Exception e3) {
                        e = e3;
                    }
                } catch (Throwable th) {
                    th = th;
                    try {
                        inputStream.close();
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                    try {
                        fileOutputStream.close();
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = null;
                inputStream.close();
                fileOutputStream.close();
                throw th;
            }
            try {
                byte[] bArr = new byte[8192];
                int i = 0;
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    i += read;
                    fileOutputStream.write(bArr, 0, read);
                    this.b.a((i * 100) / contentLength);
                }
                this.b.a();
                try {
                    inputStream.close();
                } catch (Exception e6) {
                    e6.printStackTrace();
                }
                try {
                    fileOutputStream.close();
                    return;
                } catch (Exception e7) {
                    e = e7;
                }
            } catch (Exception e8) {
                e = e8;
                e.printStackTrace();
                this.b.a((Throwable) e);
                inputStream.close();
                fileOutputStream.close();
                return;
            }
            e.printStackTrace();
        } catch (Exception e9) {
            e = e9;
            fileOutputStream = null;
            inputStream = null;
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
            inputStream = null;
            inputStream.close();
            fileOutputStream.close();
            throw th;
        }
    }
}
