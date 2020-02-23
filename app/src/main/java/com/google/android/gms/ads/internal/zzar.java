package com.google.android.gms.ads.internal;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import com.amazonaws.services.s3.util.Mimetypes;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.internal.zzaff;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzaka;
import com.google.android.gms.internal.zzakf;
import com.google.android.gms.internal.zzks;
import com.google.android.gms.internal.zznn;
import com.google.android.gms.internal.zznq;
import com.google.android.gms.internal.zzns;
import com.google.android.gms.internal.zzos;
import com.google.android.gms.internal.zzot;
import com.google.android.gms.internal.zzrd;
import com.google.android.gms.internal.zzuh;
import com.google.android.gms.internal.zzvc;
import com.google.android.gms.internal.zzvf;
import com.google.android.gms.internal.zzzn;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzar {
    static zzrd zza(@Nullable zzvc zzvc, @Nullable zzvf zzvf, zzab zzab) {
        return new zzaw(zzvc, zzab, zzvf);
    }

    private static String zza(@Nullable Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap == null) {
            zzafr.zzaT("Bitmap is null. Returning empty string");
            return "";
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        String encodeToString = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
        String valueOf = String.valueOf("data:image/png;base64,");
        String valueOf2 = String.valueOf(encodeToString);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    static String zza(@Nullable zzos zzos) {
        if (zzos == null) {
            zzafr.zzaT("Image is null. Returning empty string");
            return "";
        }
        try {
            Uri uri = zzos.getUri();
            if (uri != null) {
                return uri.toString();
            }
        } catch (RemoteException e) {
            zzafr.zzaT("Unable to get image uri. Trying data uri next");
        }
        return zzb(zzos);
    }

    /* access modifiers changed from: private */
    public static JSONObject zza(@Nullable Bundle bundle, String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (bundle == null || TextUtils.isEmpty(str)) {
            return jSONObject;
        }
        JSONObject jSONObject2 = new JSONObject(str);
        Iterator<String> keys = jSONObject2.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            if (bundle.containsKey(next)) {
                if ("image".equals(jSONObject2.getString(next))) {
                    Object obj = bundle.get(next);
                    if (obj instanceof Bitmap) {
                        jSONObject.put(next, zza((Bitmap) obj));
                    } else {
                        zzafr.zzaT("Invalid type. An image type extra should return a bitmap");
                    }
                } else if (bundle.get(next) instanceof Bitmap) {
                    zzafr.zzaT("Invalid asset type. Bitmap should be returned only for image type");
                } else {
                    jSONObject.put(next, String.valueOf(bundle.get(next)));
                }
            }
        }
        return jSONObject;
    }

    public static boolean zza(zzaka zzaka, zzuh zzuh, CountDownLatch countDownLatch) {
        boolean z;
        try {
            View view = zzaka.getView();
            if (view == null) {
                zzafr.zzaT("AdWebView is null");
                z = false;
            } else {
                view.setVisibility(4);
                List<String> list = zzuh.zzMG.zzLV;
                if (list == null || list.isEmpty()) {
                    zzafr.zzaT("No template ids present in mediation response");
                    z = false;
                } else {
                    zzaka.zziw().zza("/nativeExpressAssetsLoaded", (zzrd) new zzau(countDownLatch));
                    zzaka.zziw().zza("/nativeExpressAssetsLoadingFailed", (zzrd) new zzav(countDownLatch));
                    zzvc zzfq = zzuh.zzMH.zzfq();
                    zzvf zzfr = zzuh.zzMH.zzfr();
                    if (list.contains("2") && zzfq != null) {
                        zzaka.zziw().zza((zzakf) new zzas(new zznq(zzfq.getHeadline(), zzfq.getImages(), zzfq.getBody(), zzfq.zzeh(), zzfq.getCallToAction(), zzfq.getStarRating(), zzfq.getStore(), zzfq.getPrice(), (zznn) null, zzfq.getExtras(), (zzks) null, (View) null), zzuh.zzMG.zzLU, zzaka));
                    } else if (!list.contains("1") || zzfr == null) {
                        zzafr.zzaT("No matching template id and mapper");
                        z = false;
                    } else {
                        zzaka.zziw().zza((zzakf) new zzat(new zzns(zzfr.getHeadline(), zzfr.getImages(), zzfr.getBody(), zzfr.zzem(), zzfr.getCallToAction(), zzfr.getAdvertiser(), (zznn) null, zzfr.getExtras(), (zzks) null, (View) null), zzuh.zzMG.zzLU, zzaka));
                    }
                    String str = zzuh.zzMG.zzLS;
                    String str2 = zzuh.zzMG.zzLT;
                    if (str2 != null) {
                        zzaka.loadDataWithBaseURL(str2, str, Mimetypes.MIMETYPE_HTML, "UTF-8", (String) null);
                    } else {
                        zzaka.loadData(str, Mimetypes.MIMETYPE_HTML, "UTF-8");
                    }
                    z = true;
                }
            }
        } catch (RemoteException e) {
            zzafr.zzc("Unable to invoke load assets", e);
            z = false;
        } catch (RuntimeException e2) {
            countDownLatch.countDown();
            throw e2;
        }
        if (!z) {
            countDownLatch.countDown();
        }
        return z;
    }

    private static String zzb(zzos zzos) {
        try {
            IObjectWrapper zzeg = zzos.zzeg();
            if (zzeg == null) {
                zzafr.zzaT("Drawable is null. Returning empty string");
                return "";
            }
            Drawable drawable = (Drawable) zzn.zzE(zzeg);
            if (drawable instanceof BitmapDrawable) {
                return zza(((BitmapDrawable) drawable).getBitmap());
            }
            zzafr.zzaT("Drawable is not an instance of BitmapDrawable. Returning empty string");
            return "";
        } catch (RemoteException e) {
            zzafr.zzaT("Unable to get drawable. Returning empty string");
            return "";
        }
    }

    /* access modifiers changed from: private */
    public static void zzb(zzaka zzaka) {
        View.OnClickListener zziL = zzaka.zziL();
        if (zziL != null) {
            zziL.onClick(zzaka.getView());
        }
    }

    @Nullable
    public static View zzd(@Nullable zzaff zzaff) {
        if (zzaff == null) {
            zzafr.e("AdState is null");
            return null;
        } else if (zze(zzaff) && zzaff.zzPg != null) {
            return zzaff.zzPg.getView();
        } else {
            try {
                IObjectWrapper view = zzaff.zzMH != null ? zzaff.zzMH.getView() : null;
                if (view != null) {
                    return (View) zzn.zzE(view);
                }
                zzafr.zzaT("View in mediation adapter is null.");
                return null;
            } catch (RemoteException e) {
                zzafr.zzc("Could not get View from mediation adapter.", e);
                return null;
            }
        }
    }

    /* access modifiers changed from: private */
    @Nullable
    public static zzos zzd(Object obj) {
        if (obj instanceof IBinder) {
            return zzot.zzi((IBinder) obj);
        }
        return null;
    }

    public static boolean zze(@Nullable zzaff zzaff) {
        return (zzaff == null || !zzaff.zzTo || zzaff.zzMG == null || zzaff.zzMG.zzLS == null) ? false : true;
    }
}
