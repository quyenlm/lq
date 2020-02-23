package com.tencent.mna.base.e;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: UpnpDevice */
public class a {
    private String a;
    private String b;
    private URL c;
    private String d;
    private String e;
    private Map<String, String> f;

    private a() {
    }

    public String a() {
        if (this.e == null) {
            try {
                b();
            } catch (Exception e2) {
            }
        }
        return this.e;
    }

    public static a a(String str) {
        try {
            Map<String, String> b2 = b(str);
            a aVar = new a();
            aVar.a = str;
            aVar.f = b2;
            aVar.c = new URL(b2.get("upnp_location"));
            aVar.d = b2.get("upnp_server");
            return aVar;
        } catch (Exception e2) {
            return null;
        }
    }

    private static Map<String, String> b(String str) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        for (String str2 : str.split("\r\n")) {
            int indexOf = str2.indexOf(":");
            if (indexOf != -1) {
                concurrentHashMap.put("upnp_" + str2.substring(0, indexOf).trim().toLowerCase(), str2.substring(indexOf + 1).trim());
            }
        }
        return concurrentHashMap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x009f  */
    /* JADX WARNING: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b() {
        /*
            r6 = this;
            java.net.URL r0 = r6.c
            if (r0 != 0) goto L_0x0005
        L_0x0004:
            return
        L_0x0005:
            r1 = 0
            java.net.URL r0 = r6.c     // Catch:{ Exception -> 0x00a7, all -> 0x009a }
            java.net.URLConnection r0 = r0.openConnection()     // Catch:{ Exception -> 0x00a7, all -> 0x009a }
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ Exception -> 0x00a7, all -> 0x009a }
            java.lang.String r1 = "GET"
            r0.setRequestMethod(r1)     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            r1 = 5000(0x1388, float:7.006E-42)
            r0.setReadTimeout(r1)     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            r1 = 10000(0x2710, float:1.4013E-41)
            r0.setConnectTimeout(r1)     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            int r1 = r0.getResponseCode()     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            r2 = 200(0xc8, float:2.8E-43)
            if (r1 != r2) goto L_0x0079
            java.io.InputStream r1 = r0.getInputStream()     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            java.lang.String r1 = a((java.io.InputStream) r1)     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            r6.b = r1     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            javax.xml.parsers.DocumentBuilderFactory r1 = javax.xml.parsers.DocumentBuilderFactory.newInstance()     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            javax.xml.parsers.DocumentBuilder r1 = r1.newDocumentBuilder()     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            org.xml.sax.InputSource r2 = new org.xml.sax.InputSource     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            java.io.StringReader r3 = new java.io.StringReader     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            java.lang.String r4 = r6.b     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            org.w3c.dom.Document r1 = r1.parse(r2)     // Catch:{ SAXParseException -> 0x0072 }
            javax.xml.xpath.XPathFactory r2 = javax.xml.xpath.XPathFactory.newInstance()     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            javax.xml.xpath.XPath r2 = r2.newXPath()     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            java.util.Map<java.lang.String, java.lang.String> r3 = r6.f     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            java.lang.String r4 = "xml_friendly_name"
            java.lang.String r5 = "//friendlyName"
            javax.xml.xpath.XPathExpression r2 = r2.compile(r5)     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            java.lang.String r1 = r2.evaluate(r1)     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            r3.put(r4, r1)     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            java.util.Map<java.lang.String, java.lang.String> r1 = r6.f     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            java.lang.String r2 = "xml_friendly_name"
            java.lang.Object r1 = r1.get(r2)     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            r6.e = r1     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            if (r0 == 0) goto L_0x0004
            r0.disconnect()
            goto L_0x0004
        L_0x0072:
            r1 = move-exception
            if (r0 == 0) goto L_0x0004
            r0.disconnect()
            goto L_0x0004
        L_0x0079:
            android.accounts.NetworkErrorException r2 = new android.accounts.NetworkErrorException     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            r3.<init>()     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            java.lang.String r4 = "response status is "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            java.lang.StringBuilder r1 = r3.append(r1)     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
            throw r2     // Catch:{ Exception -> 0x0092, all -> 0x00a3 }
        L_0x0092:
            r1 = move-exception
        L_0x0093:
            if (r0 == 0) goto L_0x0004
            r0.disconnect()
            goto L_0x0004
        L_0x009a:
            r0 = move-exception
            r2 = r0
            r3 = r1
        L_0x009d:
            if (r3 == 0) goto L_0x00a2
            r3.disconnect()
        L_0x00a2:
            throw r2
        L_0x00a3:
            r1 = move-exception
            r2 = r1
            r3 = r0
            goto L_0x009d
        L_0x00a7:
            r0 = move-exception
            r0 = r1
            goto L_0x0093
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.base.e.a.b():void");
    }

    private static String a(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                inputStream.close();
                String byteArrayOutputStream2 = byteArrayOutputStream.toString("UTF-8");
                byteArrayOutputStream.close();
                return byteArrayOutputStream2;
            }
        }
    }

    public String toString() {
        return "UpnpDevice{\nmRawUPnP='" + this.a + '\'' + ", mRawXml='" + this.b + '\'' + ", mLocation=" + this.c + ", mServer='" + this.d + '\'' + ", mFriendlyName='" + this.e + '\'' + ", mProperties=" + this.f + '}';
    }
}
