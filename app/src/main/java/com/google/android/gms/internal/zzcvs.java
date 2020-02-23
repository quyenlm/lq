package com.google.android.gms.internal;

import android.net.Uri;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class zzcvs {
    private static zzcvs zzbID;
    private volatile String zzbDw = null;
    private volatile String zzbFI = null;
    private volatile int zzbIE = zza.zzbIF;

    /* 'enum' modifier removed */
    static final class zza {
        public static final int zzbIF = 1;
        public static final int zzbIG = 2;
        private static final /* synthetic */ int[] zzbIH = {1, 2};
    }

    zzcvs() {
    }

    public static zzcvs zzCw() {
        zzcvs zzcvs;
        synchronized (zzcvs.class) {
            if (zzbID == null) {
                zzbID = new zzcvs();
            }
            zzcvs = zzbID;
        }
        return zzcvs;
    }

    public final String getContainerId() {
        return this.zzbDw;
    }

    public final boolean isPreview() {
        return this.zzbIE == zza.zzbIG;
    }

    public final String zzCx() {
        return this.zzbFI;
    }

    public final synchronized boolean zzc(String str, Uri uri) {
        boolean z = false;
        synchronized (this) {
            try {
                String decode = URLDecoder.decode(uri.toString(), "UTF-8");
                if (!decode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\S+")) {
                    String valueOf = String.valueOf(decode);
                    zzcvk.zzaT(valueOf.length() != 0 ? "Bad preview url: ".concat(valueOf) : new String("Bad preview url: "));
                } else {
                    String queryParameter = uri.getQueryParameter("id");
                    String queryParameter2 = uri.getQueryParameter("gtm_auth");
                    String queryParameter3 = uri.getQueryParameter("gtm_preview");
                    if (!str.equals(queryParameter)) {
                        zzcvk.zzaT("Preview fails (container doesn't match the container specified by the asset)");
                    } else if (queryParameter == null || queryParameter.length() <= 0) {
                        String valueOf2 = String.valueOf(decode);
                        zzcvk.zzaT(valueOf2.length() != 0 ? "Bad preview url: ".concat(valueOf2) : new String("Bad preview url: "));
                    } else {
                        if (queryParameter3 == null || queryParameter3.length() != 0) {
                            if (queryParameter3 == null || queryParameter3.length() <= 0 || queryParameter2 == null || queryParameter2.length() <= 0) {
                                String valueOf3 = String.valueOf(decode);
                                zzcvk.zzaT(valueOf3.length() != 0 ? "Bad preview url: ".concat(valueOf3) : new String("Bad preview url: "));
                            } else {
                                this.zzbIE = zza.zzbIG;
                                this.zzbFI = uri.getQuery();
                                this.zzbDw = queryParameter;
                            }
                        } else if (!queryParameter.equals(this.zzbDw) || this.zzbIE == zza.zzbIF) {
                            zzcvk.zzaT("Error in exiting preview mode. The container is not in preview.");
                        } else {
                            String valueOf4 = String.valueOf(this.zzbDw);
                            zzcvk.v(valueOf4.length() != 0 ? "Exit preview mode for container: ".concat(valueOf4) : new String("Exit preview mode for container: "));
                            this.zzbIE = zza.zzbIF;
                            this.zzbDw = null;
                            this.zzbFI = null;
                        }
                        z = true;
                    }
                }
            } catch (UnsupportedEncodingException e) {
                String valueOf5 = String.valueOf(e);
                zzcvk.zzaT(new StringBuilder(String.valueOf(valueOf5).length() + 32).append("Error decoding the preview url: ").append(valueOf5).toString());
            }
        }
        return z;
    }

    public final boolean zzfG(String str) {
        return isPreview() && this.zzbDw.equals(str);
    }
}
