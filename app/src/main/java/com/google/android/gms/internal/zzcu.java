package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import com.tencent.imsdk.framework.request.HttpRequest;

public final class zzcu {
    private static final String[] zzqs = {"/aclk", "/pcs/click"};
    private String zzqo = "googleads.g.doubleclick.net";
    private String zzqp = "/pagead/ads";
    private String zzqq = "ad.doubleclick.net";
    private String[] zzqr = {".doubleclick.net", ".googleadservices.com", ".googlesyndication.com"};
    private zzcp zzqt;

    public zzcu(zzcp zzcp) {
        this.zzqt = zzcp;
    }

    private final Uri zza(Uri uri, Context context, String str, boolean z, View view) throws zzcv {
        try {
            boolean zzb = zzb(uri);
            if (zzb) {
                if (uri.toString().contains("dc_ms=")) {
                    throw new zzcv("Parameter already exists: dc_ms");
                }
            } else if (uri.getQueryParameter("ms") != null) {
                throw new zzcv("Query parameter already exists: ms");
            }
            String zza = z ? this.zzqt.zza(context, str, view) : this.zzqt.zza(context);
            if (zzb) {
                String uri2 = uri.toString();
                int indexOf = uri2.indexOf(";adurl");
                if (indexOf != -1) {
                    return Uri.parse(uri2.substring(0, indexOf + 1) + "dc_ms" + HttpRequest.HTTP_REQ_ENTITY_MERGE + zza + ";" + uri2.substring(indexOf + 1));
                }
                String encodedPath = uri.getEncodedPath();
                int indexOf2 = uri2.indexOf(encodedPath);
                return Uri.parse(uri2.substring(0, encodedPath.length() + indexOf2) + ";" + "dc_ms" + HttpRequest.HTTP_REQ_ENTITY_MERGE + zza + ";" + uri2.substring(encodedPath.length() + indexOf2));
            }
            String uri3 = uri.toString();
            int indexOf3 = uri3.indexOf("&adurl");
            if (indexOf3 == -1) {
                indexOf3 = uri3.indexOf("?adurl");
            }
            return indexOf3 != -1 ? Uri.parse(uri3.substring(0, indexOf3 + 1) + "ms" + HttpRequest.HTTP_REQ_ENTITY_MERGE + zza + HttpRequest.HTTP_REQ_ENTITY_JOIN + uri3.substring(indexOf3 + 1)) : uri.buildUpon().appendQueryParameter("ms", zza).build();
        } catch (UnsupportedOperationException e) {
            throw new zzcv("Provided Uri is not in a valid state");
        }
    }

    private final boolean zzb(Uri uri) {
        if (uri == null) {
            throw new NullPointerException();
        }
        try {
            return uri.getHost().equals(this.zzqq);
        } catch (NullPointerException e) {
            return false;
        }
    }

    public final zzcp zzB() {
        return this.zzqt;
    }

    public final Uri zza(Uri uri, Context context) throws zzcv {
        return zza(uri, context, (String) null, false, (View) null);
    }

    public final Uri zza(Uri uri, Context context, View view) throws zzcv {
        try {
            return zza(uri, context, uri.getQueryParameter("ai"), true, view);
        } catch (UnsupportedOperationException e) {
            throw new zzcv("Provided Uri is not in a valid state");
        }
    }

    public final void zza(MotionEvent motionEvent) {
        this.zzqt.zza(motionEvent);
    }

    public final boolean zza(Uri uri) {
        if (uri == null) {
            throw new NullPointerException();
        }
        try {
            return uri.getHost().equals(this.zzqo) && uri.getPath().equals(this.zzqp);
        } catch (NullPointerException e) {
            return false;
        }
    }

    public final void zzb(String str, String str2) {
        this.zzqo = str;
        this.zzqp = str2;
    }

    public final boolean zzc(Uri uri) {
        if (uri == null) {
            throw new NullPointerException();
        }
        try {
            String host = uri.getHost();
            for (String endsWith : this.zzqr) {
                if (host.endsWith(endsWith)) {
                    return true;
                }
            }
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public final boolean zzd(Uri uri) {
        if (!zzc(uri)) {
            return false;
        }
        for (String endsWith : zzqs) {
            if (uri.getPath().endsWith(endsWith)) {
                return true;
            }
        }
        return false;
    }

    public final void zzk(String str) {
        this.zzqr = str.split(",");
    }
}
