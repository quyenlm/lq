package com.google.android.gms.internal;

import android.net.Uri;
import android.text.TextUtils;
import com.appsflyer.share.Constants;
import com.tencent.imsdk.unity.stat.StatHelper;
import com.vk.sdk.api.VKApiConst;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpHost;

public class zzcss {
    private static final String TAG = zzcss.class.getSimpleName();
    private static final char[] zzbCj = "0123456789ABCDEF".toCharArray();
    private static final Pattern zzbCk = Pattern.compile("/\\.\\.");
    private static final Pattern zzbCl = Pattern.compile("0[1-7][0-7]*");
    private static final Pattern zzbCm = Pattern.compile("0x[0-9a-f]*", 2);
    private static final Pattern zzbCn = Pattern.compile("^((?:0x[0-9a-f]+|[0-9\\\\.])+)$", 2);
    private final String mPath;
    private final String zzD;
    private final String zzbCo;
    private final String zzbCp;
    private final int zzbCq;
    private final String zzvi;

    static class zza {
        private static final String[] zzbCr = {HttpHost.DEFAULT_SCHEME_NAME, VKApiConst.HTTPS, "ftp"};
        private final String zzajd;
        private final String zzbCo;
        private final Uri zzbCs;
        private final URI zzbCt;
        private final Boolean zzbCu;
        private final Integer zzbCv;

        private zza(String str) {
            int i;
            this.zzajd = str;
            this.zzbCs = Uri.parse(this.zzajd);
            try {
                new URI(this.zzajd);
            } catch (URISyntaxException e) {
            } finally {
                this.zzbCt = null;
            }
            this.zzbCo = getScheme();
            this.zzbCu = Boolean.valueOf(zzAn());
            if (this.zzbCv != null) {
                i = this.zzbCv.intValue();
            } else if (this.zzbCs == null || (i = this.zzbCs.getPort()) == -1) {
                i = -1;
            }
            this.zzbCv = Integer.valueOf(i);
        }

        /* access modifiers changed from: private */
        public final int getPort() {
            return this.zzbCv.intValue();
        }

        /* access modifiers changed from: private */
        public final String getScheme() {
            if (this.zzbCo != null) {
                return this.zzbCo;
            }
            String scheme = this.zzbCs != null ? this.zzbCs.getScheme() : null;
            TextUtils.isEmpty(scheme);
            if (TextUtils.isEmpty(scheme) && !TextUtils.isEmpty(this.zzajd)) {
                int indexOf = this.zzajd.indexOf(":");
                if (indexOf != -1) {
                    String lowerCase = this.zzajd.substring(0, indexOf).toLowerCase(Locale.US);
                    if (zzeU(lowerCase)) {
                        scheme = lowerCase;
                    }
                }
                if (TextUtils.isEmpty(scheme)) {
                    this.zzajd.startsWith("www.");
                    scheme = HttpHost.DEFAULT_SCHEME_NAME;
                }
            }
            if (scheme != null) {
                return scheme.toLowerCase(Locale.US);
            }
            return null;
        }

        /* access modifiers changed from: private */
        public final boolean zzAn() {
            return this.zzbCu != null ? this.zzbCu.booleanValue() : zzeU(this.zzbCo);
        }

        private static boolean zzeU(String str) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            for (String equals : zzbCr) {
                if (equals.equals(str)) {
                    return true;
                }
            }
            return false;
        }
    }

    public zzcss(String str) {
        String str2;
        String lowerCase;
        if (!(!TextUtils.isEmpty(str))) {
            this.zzD = null;
            this.zzbCo = null;
            this.zzbCp = null;
            this.zzbCq = -1;
            this.mPath = null;
            this.zzvi = null;
            return;
        }
        String replaceAll = str.replaceAll("^\\s+", "").replaceAll("\\s+$", "").replaceAll("[\\t\\n\\r]", "");
        int indexOf = replaceAll.indexOf(35);
        replaceAll = indexOf != -1 ? replaceAll.substring(0, indexOf) : replaceAll;
        zza zza2 = new zza(replaceAll);
        if (!zza2.zzAn()) {
            this.zzD = null;
            this.zzbCo = null;
            this.zzbCp = null;
            this.zzbCq = -1;
            this.mPath = null;
            this.zzvi = null;
            return;
        }
        this.zzbCo = zza2.getScheme();
        this.zzbCq = zza2.getPort();
        if (this.zzbCo != null) {
            String str3 = this.zzbCo;
            replaceAll = replaceAll.replaceAll(new StringBuilder(String.valueOf(str3).length() + 2).append("^").append(str3).append(":").toString(), "");
        }
        String zzeS = zzeS(replaceAll.replaceAll("^/+", ""));
        int indexOf2 = zzeS.indexOf(63);
        if (indexOf2 != -1) {
            int i = indexOf2 + 1;
            str2 = i < zzeS.length() ? zzeS.substring(i) : "";
            zzeS = zzeS.substring(0, indexOf2);
        } else {
            str2 = null;
        }
        if (TextUtils.isEmpty(zzeS)) {
            lowerCase = null;
        } else {
            int indexOf3 = zzeS.indexOf(47);
            String substring = indexOf3 != -1 ? zzeS.substring(0, indexOf3) : zzeS;
            int indexOf4 = substring.indexOf(64);
            substring = indexOf4 != -1 ? substring.substring(indexOf4 + 1) : substring;
            String replaceAll2 = (this.zzbCq != -1 ? substring.replaceAll(new StringBuilder(13).append(":").append(this.zzbCq).append("$").toString(), "") : substring).replaceAll("^\\.*", "").replaceAll("\\.*$", "").replaceAll("\\.+", ".");
            String zzeO = zzeO(replaceAll2);
            lowerCase = (zzeO == null ? replaceAll2 : zzeO).toLowerCase(Locale.getDefault());
        }
        if (TextUtils.isEmpty(lowerCase)) {
            this.zzD = null;
            this.zzbCp = null;
            this.mPath = null;
            this.zzvi = null;
            return;
        }
        String zzeQ = zzeQ(zzeS);
        this.zzbCp = zzeR(lowerCase);
        this.mPath = zzeR(zzeQ);
        this.zzvi = !TextUtils.isEmpty(str2) ? zzeR(str2) : str2;
        this.zzD = zzeS;
    }

    private static boolean isHexDigit(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
    }

    private final List<String> zzAl() {
        if (TextUtils.isEmpty(this.zzbCp)) {
            return null;
        }
        boolean z = false;
        ArrayList arrayList = new ArrayList();
        char[] charArray = this.zzbCp.toCharArray();
        for (int length = charArray.length - 2; length > 0 && arrayList.size() < 4; length--) {
            if (charArray[length] == '.') {
                if (z) {
                    arrayList.add(this.zzbCp.substring(length + 1));
                } else {
                    z = true;
                }
            }
        }
        arrayList.add(this.zzbCp);
        return arrayList;
    }

    private final List<String> zzAm() {
        if (TextUtils.isEmpty(this.mPath)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        char[] charArray = this.mPath.toCharArray();
        for (int i = 0; i < charArray.length && arrayList.size() < 4; i++) {
            if (charArray[i] == '/') {
                arrayList.add(this.mPath.substring(0, i + 1));
            }
        }
        if (!arrayList.isEmpty() && !((String) arrayList.get(arrayList.size() - 1)).equals(this.mPath)) {
            arrayList.add(this.mPath);
        }
        if (!TextUtils.isEmpty(this.zzvi)) {
            String str = this.mPath;
            String str2 = this.zzvi;
            arrayList.add(new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length()).append(str).append("?").append(str2).toString());
        }
        return arrayList;
    }

    private static String zzeO(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String replaceAll = str.replaceAll("^\\[", "").replaceAll("\\]$", "");
        if (zzcsq.zzeI(replaceAll)) {
            String zza2 = zzcsq.zza(zzcsq.zzeN(replaceAll));
            if (!zza2.contains(":")) {
                return zza2;
            }
            return String.format("[%s]", new Object[]{zza2});
        } else if (TextUtils.isDigitsOnly(str)) {
            String zzeP = zzeP(str);
            if (!TextUtils.isEmpty(zzeP)) {
                return zzeP;
            }
            return null;
        } else if (!zzbCn.matcher(replaceAll).find()) {
            return null;
        } else {
            Matcher matcher = zzbCl.matcher(replaceAll);
            StringBuffer stringBuffer = new StringBuffer();
            while (matcher.find()) {
                matcher.appendReplacement(stringBuffer, new StringBuilder(11).append(Integer.parseInt(matcher.group(), 8)).toString());
            }
            matcher.appendTail(stringBuffer);
            Matcher matcher2 = zzbCm.matcher(stringBuffer.toString());
            StringBuffer stringBuffer2 = new StringBuffer();
            while (matcher2.find()) {
                matcher2.appendReplacement(stringBuffer2, new StringBuilder(11).append(Integer.parseInt(matcher2.group().substring(2), 16)).toString());
            }
            matcher2.appendTail(stringBuffer2);
            String stringBuffer3 = stringBuffer2.toString();
            if (!stringBuffer3.contains(":")) {
                return stringBuffer3;
            }
            return String.format("[%s]", new Object[]{stringBuffer3});
        }
    }

    private static String zzeP(String str) {
        byte[] bArr;
        int i = 1;
        int i2 = 0;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            BigInteger bigInteger = new BigInteger(str);
            byte[] byteArray = bigInteger.toByteArray();
            if (byteArray.length < 4) {
                return null;
            }
            byte[] copyOfRange = Arrays.copyOfRange(byteArray, byteArray.length - 4, byteArray.length);
            if (bigInteger.equals(new BigInteger(new byte[]{0, copyOfRange[0], copyOfRange[1], copyOfRange[2], copyOfRange[3]}))) {
                return Inet4Address.getByAddress(copyOfRange).getHostAddress();
            }
            if (byteArray.length >= 16) {
                bArr = Arrays.copyOfRange(byteArray, byteArray.length - 16, byteArray.length);
            } else {
                bArr = new byte[16];
                int length = 16 - byteArray.length;
                int i3 = 0;
                while (i <= length) {
                    bArr[i3] = 0;
                    i++;
                    i3++;
                }
                while (i2 < byteArray.length) {
                    int i4 = i3 + 1;
                    bArr[i3] = byteArray[i2];
                    i2++;
                    i3 = i4;
                }
            }
            return String.format("[%s]", new Object[]{Inet6Address.getByAddress(bArr).getHostAddress()});
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | UnknownHostException e) {
            return null;
        }
    }

    private final String zzeQ(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int indexOf = str.indexOf(47);
        String replaceAll = (indexOf != -1 ? str.substring(indexOf) : Constants.URL_PATH_DELIMITER).replaceAll("/\\./", Constants.URL_PATH_DELIMITER).replaceAll("/\\.$", Constants.URL_PATH_DELIMITER);
        if (zzbCk.matcher(replaceAll).find()) {
            try {
                replaceAll = new URI(this.zzbCo, StatHelper.StatParams.HOST, replaceAll, (String) null).normalize().getRawPath();
            } catch (URISyntaxException e) {
            }
        }
        return replaceAll.replaceAll("/+", Constants.URL_PATH_DELIMITER);
    }

    private static String zzeR(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            byte[] bytes = str.getBytes("UTF-8");
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                char c = (char) (b & 255);
                if (c <= ' ' || c > '~' || c == '#' || c == '%') {
                    sb.append("%");
                    sb.append(zzbCj[c >>> 4]);
                    sb.append(zzbCj[c & 15]);
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private static String zzeS(String str) {
        String str2;
        String str3 = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int i = 0;
        while (true) {
            str2 = str;
            if (!str2.equals(str3) && i < 1024) {
                str = zzeT(str2);
                i++;
                str3 = str2;
            }
        }
        return str2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0079  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String zzeT(java.lang.String r10) {
        /*
            r9 = 16
            r8 = 1
            r1 = 0
            java.lang.String r0 = "\\x"
            java.lang.String r2 = "%"
            java.lang.String r0 = r10.replace(r0, r2)     // Catch:{ UnsupportedEncodingException -> 0x0085 }
            java.lang.String r2 = "UTF-8"
            byte[] r3 = r0.getBytes(r2)     // Catch:{ UnsupportedEncodingException -> 0x0085 }
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream
            int r0 = r3.length
            r4.<init>(r0)
            r0 = 0
        L_0x0019:
            int r2 = r3.length
            if (r0 >= r2) goto L_0x008e
            byte r5 = r3[r0]
            r2 = r5 & 255(0xff, float:3.57E-43)
            char r2 = (char) r2
            r6 = 128(0x80, float:1.794E-43)
            if (r2 >= r6) goto L_0x008a
            int r2 = r0 + 2
            int r6 = r3.length
            if (r2 >= r6) goto L_0x0088
            byte r2 = r3[r0]
            r2 = r2 & 255(0xff, float:3.57E-43)
            char r2 = (char) r2
            r6 = 37
            if (r2 != r6) goto L_0x0088
            int r2 = r0 + 1
            byte r2 = r3[r2]
            r2 = r2 & 255(0xff, float:3.57E-43)
            char r2 = (char) r2
            int r6 = r0 + 2
            byte r6 = r3[r6]
            r6 = r6 & 255(0xff, float:3.57E-43)
            char r6 = (char) r6
            boolean r7 = isHexDigit(r2)
            if (r7 == 0) goto L_0x0088
            boolean r7 = isHexDigit(r6)
            if (r7 == 0) goto L_0x0088
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>(r8)
            java.lang.StringBuilder r2 = r7.append(r2)
            java.lang.String r2 = r2.toString()
            int r2 = java.lang.Integer.parseInt(r2, r9)
            int r2 = r2 << 4
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>(r8)
            java.lang.StringBuilder r6 = r7.append(r6)
            java.lang.String r6 = r6.toString()
            int r6 = java.lang.Integer.parseInt(r6, r9)
            int r2 = r2 + r6
            byte r2 = (byte) r2
            java.lang.Byte r2 = java.lang.Byte.valueOf(r2)
        L_0x0077:
            if (r2 == 0) goto L_0x008a
            byte r2 = r2.byteValue()
            r4.write(r2)
            int r0 = r0 + 2
        L_0x0082:
            int r0 = r0 + 1
            goto L_0x0019
        L_0x0085:
            r0 = move-exception
            r0 = r1
        L_0x0087:
            return r0
        L_0x0088:
            r2 = r1
            goto L_0x0077
        L_0x008a:
            r4.write(r5)
            goto L_0x0082
        L_0x008e:
            java.lang.String r0 = new java.lang.String     // Catch:{ UnsupportedEncodingException -> 0x009a }
            byte[] r2 = r4.toByteArray()     // Catch:{ UnsupportedEncodingException -> 0x009a }
            java.lang.String r3 = "UTF-8"
            r0.<init>(r2, r3)     // Catch:{ UnsupportedEncodingException -> 0x009a }
            goto L_0x0087
        L_0x009a:
            r0 = move-exception
            r0 = r1
            goto L_0x0087
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcss.zzeT(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002a  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x008a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<com.google.android.gms.internal.zzcsp> zzAk() {
        /*
            r9 = this;
            r2 = 0
            java.lang.String r0 = r9.zzD
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0088
            java.lang.String r0 = r9.zzbCp
            java.lang.String r1 = zzeO(r0)
            if (r1 == 0) goto L_0x002c
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r0.add(r1)
        L_0x0019:
            if (r0 == 0) goto L_0x0021
            boolean r1 = r0.isEmpty()
            if (r1 == 0) goto L_0x0031
        L_0x0021:
            r4 = r2
        L_0x0022:
            if (r4 == 0) goto L_0x002a
            boolean r0 = r4.isEmpty()
            if (r0 == 0) goto L_0x008a
        L_0x002a:
            r0 = r2
        L_0x002b:
            return r0
        L_0x002c:
            java.util.List r0 = r9.zzAl()
            goto L_0x0019
        L_0x0031:
            java.util.List r4 = r9.zzAm()
            if (r4 == 0) goto L_0x003d
            boolean r1 = r4.isEmpty()
            if (r1 == 0) goto L_0x003f
        L_0x003d:
            r4 = r2
            goto L_0x0022
        L_0x003f:
            java.util.LinkedList r3 = new java.util.LinkedList
            r3.<init>()
            java.util.Iterator r5 = r0.iterator()
        L_0x0048:
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto L_0x0080
            java.lang.Object r0 = r5.next()
            java.lang.String r0 = (java.lang.String) r0
            java.util.Iterator r6 = r4.iterator()
        L_0x0058:
            boolean r1 = r6.hasNext()
            if (r1 == 0) goto L_0x0048
            java.lang.Object r1 = r6.next()
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r7 = java.lang.String.valueOf(r0)
            java.lang.String r1 = java.lang.String.valueOf(r1)
            int r8 = r1.length()
            if (r8 == 0) goto L_0x007a
            java.lang.String r1 = r7.concat(r1)
        L_0x0076:
            r3.add(r1)
            goto L_0x0058
        L_0x007a:
            java.lang.String r1 = new java.lang.String
            r1.<init>(r7)
            goto L_0x0076
        L_0x0080:
            boolean r0 = r3.isEmpty()
            if (r0 != 0) goto L_0x0088
            r4 = r3
            goto L_0x0022
        L_0x0088:
            r4 = r2
            goto L_0x0022
        L_0x008a:
            java.lang.String r0 = "SHA-256"
            java.security.MessageDigest r0 = java.security.MessageDigest.getInstance(r0)     // Catch:{ NoSuchAlgorithmException -> 0x0095 }
            r3 = r0
        L_0x0091:
            if (r3 != 0) goto L_0x0098
            r0 = r2
            goto L_0x002b
        L_0x0095:
            r0 = move-exception
            r3 = r2
            goto L_0x0091
        L_0x0098:
            java.util.ArrayList r1 = new java.util.ArrayList
            int r0 = r4.size()
            r1.<init>(r0)
            java.util.Iterator r4 = r4.iterator()
        L_0x00a5:
            boolean r0 = r4.hasNext()
            if (r0 == 0) goto L_0x00cd
            java.lang.Object r0 = r4.next()
            java.lang.String r0 = (java.lang.String) r0
            boolean r5 = android.text.TextUtils.isEmpty(r0)
            if (r5 != 0) goto L_0x00a5
            java.lang.String r5 = "UTF-8"
            byte[] r0 = r0.getBytes(r5)     // Catch:{ UnsupportedEncodingException -> 0x00d9 }
            byte[] r0 = r3.digest(r0)     // Catch:{ UnsupportedEncodingException -> 0x00d9 }
            com.google.android.gms.internal.zzcsp r5 = new com.google.android.gms.internal.zzcsp     // Catch:{ UnsupportedEncodingException -> 0x00d9 }
            r5.<init>(r0)     // Catch:{ UnsupportedEncodingException -> 0x00d9 }
            r1.add(r5)     // Catch:{ UnsupportedEncodingException -> 0x00d9 }
        L_0x00c9:
            r3.reset()
            goto L_0x00a5
        L_0x00cd:
            boolean r0 = r1.isEmpty()
            if (r0 != 0) goto L_0x00d6
            r0 = r1
            goto L_0x002b
        L_0x00d6:
            r0 = r2
            goto L_0x002b
        L_0x00d9:
            r0 = move-exception
            goto L_0x00c9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcss.zzAk():java.util.List");
    }
}
