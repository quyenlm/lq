package com.google.android.gms.tagmanager;

import android.net.Uri;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

class zzei {
    private static zzei zzbFF;
    private volatile String zzbDw = null;
    private volatile zza zzbFG = zza.zzbFJ;
    private volatile String zzbFH = null;
    private volatile String zzbFI = null;

    enum zza {
        zzbFJ,
        CONTAINER,
        CONTAINER_DEBUG
    }

    zzei() {
    }

    static zzei zzBD() {
        zzei zzei;
        synchronized (zzei.class) {
            if (zzbFF == null) {
                zzbFF = new zzei();
            }
            zzei = zzbFF;
        }
        return zzei;
    }

    private static String zzfq(String str) {
        return str.split(HttpRequest.HTTP_REQ_ENTITY_JOIN)[0].split(HttpRequest.HTTP_REQ_ENTITY_MERGE)[1];
    }

    /* access modifiers changed from: package-private */
    public final String getContainerId() {
        return this.zzbDw;
    }

    /* access modifiers changed from: package-private */
    public final zza zzBE() {
        return this.zzbFG;
    }

    /* access modifiers changed from: package-private */
    public final String zzBF() {
        return this.zzbFH;
    }

    /* access modifiers changed from: package-private */
    public final synchronized boolean zzr(Uri uri) {
        boolean z = true;
        synchronized (this) {
            try {
                String decode = URLDecoder.decode(uri.toString(), "UTF-8");
                if (decode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_auth=\\S+&gtm_preview=\\d+(&gtm_debug=x)?$")) {
                    String valueOf = String.valueOf(decode);
                    zzdj.v(valueOf.length() != 0 ? "Container preview url: ".concat(valueOf) : new String("Container preview url: "));
                    if (decode.matches(".*?&gtm_debug=x$")) {
                        this.zzbFG = zza.CONTAINER_DEBUG;
                    } else {
                        this.zzbFG = zza.CONTAINER;
                    }
                    this.zzbFI = uri.getQuery().replace("&gtm_debug=x", "");
                    if (this.zzbFG == zza.CONTAINER || this.zzbFG == zza.CONTAINER_DEBUG) {
                        String valueOf2 = String.valueOf("/r?");
                        String valueOf3 = String.valueOf(this.zzbFI);
                        this.zzbFH = valueOf3.length() != 0 ? valueOf2.concat(valueOf3) : new String(valueOf2);
                    }
                    this.zzbDw = zzfq(this.zzbFI);
                } else if (!decode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_preview=$")) {
                    String valueOf4 = String.valueOf(decode);
                    zzdj.zzaT(valueOf4.length() != 0 ? "Invalid preview uri: ".concat(valueOf4) : new String("Invalid preview uri: "));
                    z = false;
                } else if (zzfq(uri.getQuery()).equals(this.zzbDw)) {
                    String valueOf5 = String.valueOf(this.zzbDw);
                    zzdj.v(valueOf5.length() != 0 ? "Exit preview mode for container: ".concat(valueOf5) : new String("Exit preview mode for container: "));
                    this.zzbFG = zza.zzbFJ;
                    this.zzbFH = null;
                } else {
                    z = false;
                }
            } catch (UnsupportedEncodingException e) {
                z = false;
            }
        }
        return z;
    }
}
