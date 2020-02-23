package com.tencent.tp.c;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

public class h {
    public static int a(String str, String str2, int i, f fVar) throws ClientProtocolException, IOException, InterruptedException {
        if (i.a(str2, i)) {
            if (fVar != null) {
                fVar.a(i, i);
                Thread.sleep(100);
            }
            return 200;
        }
        if (fVar != null) {
            fVar.a(0, 0);
        }
        HttpResponse execute = d.a().execute(new HttpGet(str));
        int statusCode = execute.getStatusLine().getStatusCode();
        InputStream content = execute.getEntity().getContent();
        String str3 = str2 + ".tmp";
        i.d(str3);
        int a = a(execute);
        FileOutputStream fileOutputStream = new FileOutputStream(str3);
        byte[] bArr = new byte[2048];
        int i2 = 0;
        while (true) {
            if (fVar != null && fVar.a()) {
                statusCode = 801;
                break;
            }
            int read = content.read(bArr);
            if (read <= 0) {
                break;
            }
            fileOutputStream.write(bArr, 0, read);
            i2 += read;
            if (fVar != null) {
                fVar.a(i2, a);
            }
        }
        fileOutputStream.close();
        content.close();
        if (fVar != null) {
            fVar.a(i2, a);
        }
        if (statusCode == 801) {
            return statusCode;
        }
        i.a(str3, str2);
        return statusCode;
    }

    private static int a(HttpResponse httpResponse) {
        Header firstHeader = httpResponse.getFirstHeader("Content-Length");
        if (firstHeader != null) {
            return Integer.valueOf(firstHeader.getValue()).intValue();
        }
        return 0;
    }

    public static e a(String str, String str2) throws ClientProtocolException, IOException {
        HttpResponse execute = d.a().execute(new HttpGet(str));
        e eVar = new e();
        eVar.b = execute.getStatusLine().getStatusCode();
        InputStream content = execute.getEntity().getContent();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            int read = content.read(bArr);
            if (read <= 0) {
                break;
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
        if (str2 == null) {
            eVar.a = byteArrayOutputStream.toString("UTF-8");
        } else {
            eVar.a = byteArrayOutputStream.toString(str2);
        }
        byteArrayOutputStream.close();
        content.close();
        return eVar;
    }

    public static boolean a(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
    }
}
