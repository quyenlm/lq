package com.google.android.gms.internal;

import java.io.IOException;
import java.lang.Thread;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public final class yd {
    private static final AtomicInteger zzciL = new AtomicInteger(0);
    private static final Charset zzciM = Charset.forName("UTF-8");
    private static ThreadFactory zzciU = Executors.defaultThreadFactory();
    private static yc zzciV = new ye();
    private volatile Socket socket = null;
    private final int zzaBC = zzciL.incrementAndGet();
    private volatile int zzciN = yh.zzciY;
    private yi zzciO = null;
    private final URI zzciP;
    private final ym zzciQ;
    private final yn zzciR;
    private final yk zzciS;
    private final Thread zzciT = zzciU.newThread(new yf(this));

    public yd(URI uri, String str, Map<String, String> map) {
        this.zzciP = uri;
        this.zzciS = new yk(uri, (String) null, map);
        this.zzciQ = new ym(this);
        this.zzciR = new yn(this, "TubeSock", this.zzaBC);
    }

    private final Socket createSocket() {
        String scheme = this.zzciP.getScheme();
        String host = this.zzciP.getHost();
        int port = this.zzciP.getPort();
        if (scheme != null && scheme.equals("ws")) {
            try {
                return new Socket(host, port == -1 ? 80 : port);
            } catch (UnknownHostException e) {
                UnknownHostException unknownHostException = e;
                String valueOf = String.valueOf(host);
                throw new yj(valueOf.length() != 0 ? "unknown host: ".concat(valueOf) : new String("unknown host: "), unknownHostException);
            } catch (IOException e2) {
                String valueOf2 = String.valueOf(this.zzciP);
                throw new yj(new StringBuilder(String.valueOf(valueOf2).length() + 31).append("error while creating socket to ").append(valueOf2).toString(), e2);
            }
        } else if (scheme == null || !scheme.equals("wss")) {
            String valueOf3 = String.valueOf(scheme);
            throw new yj(valueOf3.length() != 0 ? "unsupported protocol: ".concat(valueOf3) : new String("unsupported protocol: "));
        } else {
            if (port == -1) {
                port = 443;
            }
            try {
                SSLSocket sSLSocket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(host, port);
                if (HttpsURLConnection.getDefaultHostnameVerifier().verify(host, sSLSocket.getSession())) {
                    return sSLSocket;
                }
                String valueOf4 = String.valueOf(this.zzciP);
                throw new yj(new StringBuilder(String.valueOf(valueOf4).length() + 39).append("Error while verifying secure socket to ").append(valueOf4).toString());
            } catch (UnknownHostException e3) {
                UnknownHostException unknownHostException2 = e3;
                String valueOf5 = String.valueOf(host);
                throw new yj(valueOf5.length() != 0 ? "unknown host: ".concat(valueOf5) : new String("unknown host: "), unknownHostException2);
            } catch (IOException e4) {
                String valueOf6 = String.valueOf(this.zzciP);
                throw new yj(new StringBuilder(String.valueOf(valueOf6).length() + 38).append("error while creating secure socket to ").append(valueOf6).toString(), e4);
            }
        }
    }

    static ThreadFactory getThreadFactory() {
        return zzciU;
    }

    static yc zzJo() {
        return zzciV;
    }

    private final synchronized void zzJr() {
        if (this.zzciN != yh.zzcjc) {
            this.zzciQ.zzJx();
            this.zzciR.zzJz();
            if (this.socket != null) {
                try {
                    this.socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            this.zzciN = yh.zzcjc;
            this.zzciO.onClose();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:100:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        r7 = new java.io.DataInputStream(r1.getInputStream());
        r8 = r1.getOutputStream();
        r8.write(r12.zzciS.zzJv());
        r6 = new byte[1000];
        r2 = new java.util.ArrayList();
        r5 = 0;
        r1 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0054, code lost:
        if (r1 != false) goto L_0x00e9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0056, code lost:
        r9 = r7.read();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x005b, code lost:
        if (r9 != -1) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0064, code lost:
        throw new com.google.android.gms.internal.yj("Connection closed before handshake was complete");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        r6[r5] = (byte) r9;
        r5 = r5 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0093, code lost:
        if (r6[r5 - 1] != 10) goto L_0x00c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x009b, code lost:
        if (r6[r5 - 2] != 13) goto L_0x00c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x009d, code lost:
        r5 = new java.lang.String(r6, zzciM);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00ae, code lost:
        if (r5.trim().equals("") == false) goto L_0x00b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00b0, code lost:
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00b1, code lost:
        r6 = new byte[1000];
        r5 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00b7, code lost:
        r2.add(r5.trim());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00c4, code lost:
        if (r5 != 1000) goto L_0x0054;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
        r1 = java.lang.String.valueOf(new java.lang.String(r6, zzciM));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00d9, code lost:
        if (r1.length() == 0) goto L_0x00e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00db, code lost:
        r1 = "Unexpected long line in handshake: ".concat(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00e2, code lost:
        throw new com.google.android.gms.internal.yj(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00e3, code lost:
        r1 = new java.lang.String("Unexpected long line in handshake: ");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00e9, code lost:
        r1 = java.lang.Integer.valueOf(((java.lang.String) r2.get(0)).substring(9, 12)).intValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0102, code lost:
        if (r1 != 407) goto L_0x010c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x010b, code lost:
        throw new com.google.android.gms.internal.yj("connection failed: proxy authentication not supported");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x010e, code lost:
        if (r1 != 404) goto L_0x0118;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0117, code lost:
        throw new com.google.android.gms.internal.yj("connection failed: 404 not found");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x011a, code lost:
        if (r1 == 101) goto L_0x0137;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0136, code lost:
        throw new com.google.android.gms.internal.yj(new java.lang.StringBuilder(50).append("connection failed: unknown status code ").append(r1).toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0137, code lost:
        r2.remove(0);
        r4 = new java.util.HashMap();
        r1 = r2;
        r5 = r1.size();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0148, code lost:
        if (r3 >= r5) goto L_0x0163;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x014a, code lost:
        r2 = r1.get(r3);
        r3 = r3 + 1;
        r2 = ((java.lang.String) r2).split(": ", 2);
        r4.put(r2[0], r2[1]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0177, code lost:
        if (((java.lang.String) r4.get("Upgrade")).toLowerCase(java.util.Locale.US).equals("websocket") != false) goto L_0x0181;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0180, code lost:
        throw new com.google.android.gms.internal.yj("connection failed: missing header field in server handshake: Upgrade");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x0195, code lost:
        if (((java.lang.String) r4.get("Connection")).toLowerCase(java.util.Locale.US).equals("upgrade") != false) goto L_0x019f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x019e, code lost:
        throw new com.google.android.gms.internal.yj("connection failed: missing header field in server handshake: Connection");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x019f, code lost:
        r12.zzciR.zzb(r8);
        r12.zzciQ.zza(r7);
        r12.zzciN = com.google.android.gms.internal.yh.zzcja;
        r12.zzciR.zzJB().start();
        r12.zzciO.zzGy();
        r12.zzciQ.run();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01c0, code lost:
        close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzJt() {
        /*
            r12 = this;
            r4 = 1
            r11 = 1000(0x3e8, float:1.401E-42)
            r3 = 0
            java.net.Socket r1 = r12.createSocket()     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            monitor-enter(r12)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r12.socket = r1     // Catch:{ all -> 0x0025 }
            int r2 = r12.zzciN     // Catch:{ all -> 0x0025 }
            int r5 = com.google.android.gms.internal.yh.zzcjc     // Catch:{ all -> 0x0025 }
            if (r2 != r5) goto L_0x0032
            java.net.Socket r1 = r12.socket     // Catch:{ IOException -> 0x001e }
            r1.close()     // Catch:{ IOException -> 0x001e }
            r1 = 0
            r12.socket = r1     // Catch:{ all -> 0x0025 }
            monitor-exit(r12)     // Catch:{ all -> 0x0025 }
            r12.close()
        L_0x001d:
            return
        L_0x001e:
            r1 = move-exception
            java.lang.RuntimeException r2 = new java.lang.RuntimeException     // Catch:{ all -> 0x0025 }
            r2.<init>(r1)     // Catch:{ all -> 0x0025 }
            throw r2     // Catch:{ all -> 0x0025 }
        L_0x0025:
            r1 = move-exception
            monitor-exit(r12)     // Catch:{ all -> 0x0025 }
            throw r1     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
        L_0x0028:
            r1 = move-exception
            com.google.android.gms.internal.yi r2 = r12.zzciO     // Catch:{ all -> 0x00bf }
            r2.zza((com.google.android.gms.internal.yj) r1)     // Catch:{ all -> 0x00bf }
            r12.close()
            goto L_0x001d
        L_0x0032:
            monitor-exit(r12)     // Catch:{ all -> 0x0025 }
            java.io.DataInputStream r7 = new java.io.DataInputStream     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.io.InputStream r2 = r1.getInputStream()     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r7.<init>(r2)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.io.OutputStream r8 = r1.getOutputStream()     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            com.google.android.gms.internal.yk r1 = r12.zzciS     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            byte[] r1 = r1.zzJv()     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r8.write(r1)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r1 = 1000(0x3e8, float:1.401E-42)
            byte[] r6 = new byte[r1]     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r2.<init>()     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r5 = r3
            r1 = r3
        L_0x0054:
            if (r1 != 0) goto L_0x00e9
            int r9 = r7.read()     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r10 = -1
            if (r9 != r10) goto L_0x0088
            com.google.android.gms.internal.yj r1 = new com.google.android.gms.internal.yj     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.String r2 = "Connection closed before handshake was complete"
            r1.<init>(r2)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            throw r1     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
        L_0x0065:
            r1 = move-exception
            com.google.android.gms.internal.yi r3 = r12.zzciO     // Catch:{ all -> 0x00bf }
            com.google.android.gms.internal.yj r4 = new com.google.android.gms.internal.yj     // Catch:{ all -> 0x00bf }
            java.lang.String r5 = "error while connecting: "
            java.lang.String r2 = r1.getMessage()     // Catch:{ all -> 0x00bf }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x00bf }
            int r6 = r2.length()     // Catch:{ all -> 0x00bf }
            if (r6 == 0) goto L_0x01c5
            java.lang.String r2 = r5.concat(r2)     // Catch:{ all -> 0x00bf }
        L_0x007e:
            r4.<init>(r2, r1)     // Catch:{ all -> 0x00bf }
            r3.zza((com.google.android.gms.internal.yj) r4)     // Catch:{ all -> 0x00bf }
            r12.close()
            goto L_0x001d
        L_0x0088:
            byte r9 = (byte) r9
            r6[r5] = r9     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            int r5 = r5 + 1
            int r9 = r5 + -1
            byte r9 = r6[r9]     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r10 = 10
            if (r9 != r10) goto L_0x00c4
            int r9 = r5 + -2
            byte r9 = r6[r9]     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r10 = 13
            if (r9 != r10) goto L_0x00c4
            java.lang.String r5 = new java.lang.String     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.nio.charset.Charset r9 = zzciM     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r5.<init>(r6, r9)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.String r6 = r5.trim()     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.String r9 = ""
            boolean r6 = r6.equals(r9)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            if (r6 == 0) goto L_0x00b7
            r1 = r4
        L_0x00b1:
            r5 = 1000(0x3e8, float:1.401E-42)
            byte[] r6 = new byte[r5]     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r5 = r3
            goto L_0x0054
        L_0x00b7:
            java.lang.String r5 = r5.trim()     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r2.add(r5)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            goto L_0x00b1
        L_0x00bf:
            r1 = move-exception
            r12.close()
            throw r1
        L_0x00c4:
            if (r5 != r11) goto L_0x0054
            java.lang.String r1 = new java.lang.String     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.nio.charset.Charset r2 = zzciM     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r1.<init>(r6, r2)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            com.google.android.gms.internal.yj r2 = new com.google.android.gms.internal.yj     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.String r3 = "Unexpected long line in handshake: "
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            int r4 = r1.length()     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            if (r4 == 0) goto L_0x00e3
            java.lang.String r1 = r3.concat(r1)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
        L_0x00df:
            r2.<init>(r1)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            throw r2     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
        L_0x00e3:
            java.lang.String r1 = new java.lang.String     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r1.<init>(r3)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            goto L_0x00df
        L_0x00e9:
            r1 = 0
            java.lang.Object r1 = r2.get(r1)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r4 = 9
            r5 = 12
            java.lang.String r1 = r1.substring(r4, r5)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            int r1 = r1.intValue()     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r4 = 407(0x197, float:5.7E-43)
            if (r1 != r4) goto L_0x010c
            com.google.android.gms.internal.yj r1 = new com.google.android.gms.internal.yj     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.String r2 = "connection failed: proxy authentication not supported"
            r1.<init>(r2)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            throw r1     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
        L_0x010c:
            r4 = 404(0x194, float:5.66E-43)
            if (r1 != r4) goto L_0x0118
            com.google.android.gms.internal.yj r1 = new com.google.android.gms.internal.yj     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.String r2 = "connection failed: 404 not found"
            r1.<init>(r2)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            throw r1     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
        L_0x0118:
            r4 = 101(0x65, float:1.42E-43)
            if (r1 == r4) goto L_0x0137
            com.google.android.gms.internal.yj r2 = new com.google.android.gms.internal.yj     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r3 = 50
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r4.<init>(r3)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.String r3 = "connection failed: unknown status code "
            java.lang.StringBuilder r3 = r4.append(r3)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.StringBuilder r1 = r3.append(r1)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.String r1 = r1.toString()     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r2.<init>(r1)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            throw r2     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
        L_0x0137:
            r1 = 0
            r2.remove(r1)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.util.HashMap r4 = new java.util.HashMap     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r4.<init>()     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r0 = r2
            java.util.ArrayList r0 = (java.util.ArrayList) r0     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r1 = r0
            int r5 = r1.size()     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
        L_0x0148:
            if (r3 >= r5) goto L_0x0163
            java.lang.Object r2 = r1.get(r3)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            int r3 = r3 + 1
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.String r6 = ": "
            r9 = 2
            java.lang.String[] r2 = r2.split(r6, r9)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r6 = 0
            r6 = r2[r6]     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r9 = 1
            r2 = r2[r9]     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r4.put(r6, r2)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            goto L_0x0148
        L_0x0163:
            java.lang.String r1 = "Upgrade"
            java.lang.Object r1 = r4.get(r1)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.util.Locale r2 = java.util.Locale.US     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.String r1 = r1.toLowerCase(r2)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.String r2 = "websocket"
            boolean r1 = r1.equals(r2)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            if (r1 != 0) goto L_0x0181
            com.google.android.gms.internal.yj r1 = new com.google.android.gms.internal.yj     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.String r2 = "connection failed: missing header field in server handshake: Upgrade"
            r1.<init>(r2)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            throw r1     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
        L_0x0181:
            java.lang.String r1 = "Connection"
            java.lang.Object r1 = r4.get(r1)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.util.Locale r2 = java.util.Locale.US     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.String r1 = r1.toLowerCase(r2)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.String r2 = "upgrade"
            boolean r1 = r1.equals(r2)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            if (r1 != 0) goto L_0x019f
            com.google.android.gms.internal.yj r1 = new com.google.android.gms.internal.yj     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.String r2 = "connection failed: missing header field in server handshake: Connection"
            r1.<init>(r2)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            throw r1     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
        L_0x019f:
            com.google.android.gms.internal.yn r1 = r12.zzciR     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r1.zzb(r8)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            com.google.android.gms.internal.ym r1 = r12.zzciQ     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r1.zza(r7)     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            int r1 = com.google.android.gms.internal.yh.zzcja     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r12.zzciN = r1     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            com.google.android.gms.internal.yn r1 = r12.zzciR     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            java.lang.Thread r1 = r1.zzJB()     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r1.start()     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            com.google.android.gms.internal.yi r1 = r12.zzciO     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r1.zzGy()     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            com.google.android.gms.internal.ym r1 = r12.zzciQ     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r1.run()     // Catch:{ yj -> 0x0028, IOException -> 0x0065 }
            r12.close()
            goto L_0x001d
        L_0x01c5:
            java.lang.String r2 = new java.lang.String     // Catch:{ all -> 0x00bf }
            r2.<init>(r5)     // Catch:{ all -> 0x00bf }
            goto L_0x007e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.yd.zzJt():void");
    }

    private final synchronized void zza(byte b, byte[] bArr) {
        if (this.zzciN != yh.zzcja) {
            this.zzciO.zza(new yj("error while sending data: not connected"));
        } else {
            try {
                this.zzciR.zza(b, true, bArr);
            } catch (IOException e) {
                this.zzciO.zza(new yj("Failed to send frame", e));
                close();
            }
        }
        return;
    }

    public static void zza(ThreadFactory threadFactory, yc ycVar) {
        zzciU = threadFactory;
        zzciV = ycVar;
    }

    public final synchronized void close() {
        switch (yg.zzciX[this.zzciN - 1]) {
            case 1:
                this.zzciN = yh.zzcjc;
                break;
            case 2:
                zzJr();
                break;
            case 3:
                try {
                    this.zzciN = yh.zzcjb;
                    this.zzciR.zzJz();
                    this.zzciR.zza((byte) 8, true, new byte[0]);
                    break;
                } catch (IOException e) {
                    this.zzciO.zza(new yj("Failed to send close frame", e));
                    break;
                }
        }
        return;
    }

    public final synchronized void connect() {
        if (this.zzciN != yh.zzciY) {
            this.zzciO.zza(new yj("connect() already called"));
            close();
        } else {
            yc ycVar = zzciV;
            Thread thread = this.zzciT;
            String valueOf = String.valueOf("TubeSockReader-");
            ycVar.zza(thread, new StringBuilder(String.valueOf(valueOf).length() + 11).append(valueOf).append(this.zzaBC).toString());
            this.zzciN = yh.zzciZ;
            this.zzciT.start();
        }
    }

    /* access modifiers changed from: package-private */
    public final synchronized void zzE(byte[] bArr) {
        zza((byte) 10, bArr);
    }

    /* access modifiers changed from: package-private */
    public final yi zzJp() {
        return this.zzciO;
    }

    /* access modifiers changed from: package-private */
    public final void zzJq() {
        zzJr();
    }

    public final void zzJs() throws InterruptedException {
        if (this.zzciR.zzJB().getState() != Thread.State.NEW) {
            this.zzciR.zzJB().join();
        }
        this.zzciT.join();
    }

    public final void zza(yi yiVar) {
        this.zzciO = yiVar;
    }

    /* access modifiers changed from: package-private */
    public final void zzb(yj yjVar) {
        this.zzciO.zza(yjVar);
        if (this.zzciN == yh.zzcja) {
            close();
        }
        zzJr();
    }

    public final synchronized void zzgM(String str) {
        zza((byte) 1, str.getBytes(zzciM));
    }
}
