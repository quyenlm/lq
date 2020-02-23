package com.tencent.mna.base.utils;

import com.amazonaws.services.s3.util.Mimetypes;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: TitleExtractor */
public final class p {
    private static final Pattern a = Pattern.compile("\\<title>(.*)\\</title>", 34);

    public static String a(String str) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.89 Safari/537.36");
            httpURLConnection.setConnectTimeout(1000);
            httpURLConnection.setReadTimeout(1000);
            a a2 = a((URLConnection) httpURLConnection);
            if (a2 == null || !a2.b.equals(Mimetypes.MIMETYPE_HTML)) {
                return "unknown(-1)";
            }
            Charset a3 = a(a2);
            if (a3 == null) {
                a3 = Charset.defaultCharset();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), a3));
            char[] cArr = new char[1024];
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (true) {
                int read = bufferedReader.read(cArr, 0, cArr.length);
                if (read == -1) {
                    break;
                }
                sb.append(cArr, 0, read);
                i += read;
            }
            bufferedReader.close();
            Matcher matcher = a.matcher(sb);
            return matcher.find() ? matcher.group(1).replaceAll("[\\s\\<>]+", " ").trim() : "unknown(-2)";
        } catch (Exception e) {
            return "unknown(-3)";
        }
    }

    private static a a(URLConnection uRLConnection) {
        int i = 0;
        while (true) {
            try {
                String headerFieldKey = uRLConnection.getHeaderFieldKey(i);
                String headerField = uRLConnection.getHeaderField(i);
                if (headerFieldKey != null && headerFieldKey.equalsIgnoreCase("Content-Type")) {
                    return new a(headerField);
                }
                int i2 = i + 1;
                if (!((headerFieldKey == null && headerField == null) ? false : true)) {
                    break;
                }
                i = i2;
            } catch (Exception e) {
            }
        }
        return null;
    }

    private static Charset a(a aVar) {
        if (aVar == null) {
            return null;
        }
        try {
            if (aVar.c == null || !Charset.isSupported(aVar.c)) {
                return null;
            }
            return Charset.forName(aVar.c);
        } catch (Exception e) {
            return null;
        }
    }

    /* compiled from: TitleExtractor */
    private static final class a {
        private static final Pattern a = Pattern.compile("charset=([-_a-zA-Z0-9]+)", 34);
        /* access modifiers changed from: private */
        public String b;
        /* access modifiers changed from: private */
        public String c;

        private a(String str) {
            if (str == null) {
                try {
                    throw new IllegalArgumentException("ContentType must be constructed with a not-null headerValue");
                } catch (Exception e) {
                }
            } else {
                int indexOf = str.indexOf(";");
                if (indexOf != -1) {
                    this.b = str.substring(0, indexOf);
                    Matcher matcher = a.matcher(str);
                    if (matcher.find()) {
                        this.c = matcher.group(1);
                        return;
                    }
                    return;
                }
                this.b = str;
            }
        }
    }
}
