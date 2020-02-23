package com.google.android.gms.internal;

import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.vk.sdk.api.VKApiConst;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

final class pg {
    private static long zzcbG = 0;
    /* access modifiers changed from: private */
    public final wl zzbZE;
    /* access modifiers changed from: private */
    public final ScheduledExecutorService zzbZs;
    /* access modifiers changed from: private */
    public pk zzcbH;
    /* access modifiers changed from: private */
    public boolean zzcbI = false;
    private boolean zzcbJ = false;
    private long zzcbK = 0;
    private pt zzcbL;
    private pj zzcbM;
    private ScheduledFuture<?> zzcbN;
    /* access modifiers changed from: private */
    public ScheduledFuture<?> zzcbO;
    private final oj zzcbP;

    public pg(oj ojVar, ol olVar, String str, pj pjVar, String str2) {
        this.zzcbP = ojVar;
        this.zzbZs = ojVar.zzFV();
        this.zzcbM = pjVar;
        long j = zzcbG;
        zzcbG = 1 + j;
        this.zzbZE = new wl(ojVar.zzFT(), "WebSocket", new StringBuilder(23).append("ws_").append(j).toString());
        str = str == null ? olVar.getHost() : str;
        boolean isSecure = olVar.isSecure();
        String namespace = olVar.getNamespace();
        String str3 = isSecure ? "wss" : "ws";
        String valueOf = String.valueOf(VKApiConst.VERSION);
        String valueOf2 = String.valueOf("5");
        String sb = new StringBuilder(String.valueOf(str3).length() + 13 + String.valueOf(str).length() + String.valueOf(namespace).length() + String.valueOf(valueOf).length() + String.valueOf(valueOf2).length()).append(str3).append("://").append(str).append("/.ws?ns=").append(namespace).append(HttpRequest.HTTP_REQ_ENTITY_JOIN).append(valueOf).append(HttpRequest.HTTP_REQ_ENTITY_MERGE).append(valueOf2).toString();
        if (str2 != null) {
            String valueOf3 = String.valueOf(sb);
            String valueOf4 = String.valueOf("&ls=");
            sb = new StringBuilder(String.valueOf(valueOf3).length() + String.valueOf(valueOf4).length() + String.valueOf(str2).length()).append(valueOf3).append(valueOf4).append(str2).toString();
        }
        URI create = URI.create(sb);
        HashMap hashMap = new HashMap();
        hashMap.put("User-Agent", this.zzcbP.zzht());
        this.zzcbH = new pl(this, new yd(create, (String) null, hashMap), (ph) null);
    }

    private final void shutdown() {
        this.zzcbJ = true;
        this.zzcbM.zzaA(this.zzcbI);
    }

    /* access modifiers changed from: private */
    public final void zzGv() {
        if (!this.zzcbJ) {
            if (this.zzcbN != null) {
                this.zzcbN.cancel(false);
                if (this.zzbZE.zzIH()) {
                    this.zzbZE.zzb(new StringBuilder(48).append("Reset keepAlive. Remaining: ").append(this.zzcbN.getDelay(TimeUnit.MILLISECONDS)).toString(), (Throwable) null, new Object[0]);
                }
            } else if (this.zzbZE.zzIH()) {
                this.zzbZE.zzb("Reset keepAlive", (Throwable) null, new Object[0]);
            }
            this.zzcbN = this.zzbZs.schedule(new pi(this), 45000, TimeUnit.MILLISECONDS);
        }
    }

    /* access modifiers changed from: private */
    public final void zzGw() {
        if (!this.zzcbJ) {
            if (this.zzbZE.zzIH()) {
                this.zzbZE.zzb("closing itself", (Throwable) null, new Object[0]);
            }
            shutdown();
        }
        this.zzcbH = null;
        if (this.zzcbN != null) {
            this.zzcbN.cancel(false);
        }
    }

    /* access modifiers changed from: private */
    public final void zzGx() {
        if (!this.zzcbI && !this.zzcbJ) {
            if (this.zzbZE.zzIH()) {
                this.zzbZE.zzb("timed out on connect", (Throwable) null, new Object[0]);
            }
            this.zzcbH.close();
        }
    }

    private final void zzbV(int i) {
        this.zzcbK = (long) i;
        this.zzcbL = new pt();
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(new StringBuilder(41).append("HandleNewFrameCount: ").append(this.zzcbK).toString(), (Throwable) null, new Object[0]);
        }
    }

    private final void zzgJ(String str) {
        this.zzcbL.zzgN(str);
        this.zzcbK--;
        if (this.zzcbK == 0) {
            try {
                this.zzcbL.zzGD();
                Map<String, Object> zzgV = yr.zzgV(this.zzcbL.toString());
                this.zzcbL = null;
                if (this.zzbZE.zzIH()) {
                    wl wlVar = this.zzbZE;
                    String valueOf = String.valueOf(zzgV);
                    wlVar.zzb(new StringBuilder(String.valueOf(valueOf).length() + 36).append("handleIncomingFrame complete frame: ").append(valueOf).toString(), (Throwable) null, new Object[0]);
                }
                this.zzcbM.zzz(zzgV);
            } catch (IOException e) {
                IOException iOException = e;
                wl wlVar2 = this.zzbZE;
                String valueOf2 = String.valueOf(this.zzcbL.toString());
                wlVar2.zzd(valueOf2.length() != 0 ? "Error parsing frame: ".concat(valueOf2) : new String("Error parsing frame: "), iOException);
                close();
                shutdown();
            } catch (ClassCastException e2) {
                ClassCastException classCastException = e2;
                wl wlVar3 = this.zzbZE;
                String valueOf3 = String.valueOf(this.zzcbL.toString());
                wlVar3.zzd(valueOf3.length() != 0 ? "Error parsing frame (cast error): ".concat(valueOf3) : new String("Error parsing frame (cast error): "), classCastException);
                close();
                shutdown();
            }
        }
    }

    private final String zzgK(String str) {
        if (str.length() <= 6) {
            try {
                int parseInt = Integer.parseInt(str);
                if (parseInt > 0) {
                    zzbV(parseInt);
                }
                return null;
            } catch (NumberFormatException e) {
            }
        }
        zzbV(1);
        return str;
    }

    /* access modifiers changed from: private */
    public final void zzgL(String str) {
        if (!this.zzcbJ) {
            zzGv();
            if (this.zzcbL != null) {
                zzgJ(str);
                return;
            }
            String zzgK = zzgK(str);
            if (zzgK != null) {
                zzgJ(zzgK);
            }
        }
    }

    public final void close() {
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb("websocket is being closed", (Throwable) null, new Object[0]);
        }
        this.zzcbJ = true;
        this.zzcbH.close();
        if (this.zzcbO != null) {
            this.zzcbO.cancel(true);
        }
        if (this.zzcbN != null) {
            this.zzcbN.cancel(true);
        }
    }

    public final void open() {
        this.zzcbH.connect();
        this.zzcbO = this.zzbZs.schedule(new ph(this), NotificationOptions.SKIP_STEP_THIRTY_SECONDS_IN_MS, TimeUnit.MILLISECONDS);
    }

    public final void send(Map<String, Object> map) {
        String[] strArr;
        zzGv();
        try {
            String zzak = yr.zzak(map);
            if (zzak.length() <= 16384) {
                strArr = new String[]{zzak};
            } else {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < zzak.length(); i += 16384) {
                    arrayList.add(zzak.substring(i, Math.min(i + 16384, zzak.length())));
                }
                strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
            }
            if (strArr.length > 1) {
                this.zzcbH.zzgM(new StringBuilder(11).append(strArr.length).toString());
            }
            for (String zzgM : strArr) {
                this.zzcbH.zzgM(zzgM);
            }
        } catch (IOException e) {
            IOException iOException = e;
            wl wlVar = this.zzbZE;
            String valueOf = String.valueOf(map.toString());
            wlVar.zzd(valueOf.length() != 0 ? "Failed to serialize message: ".concat(valueOf) : new String("Failed to serialize message: "), iOException);
            shutdown();
        }
    }
}
