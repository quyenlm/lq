package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import com.amazonaws.services.s3.internal.Constants;
import com.google.android.gms.ads.internal.js.zzl;
import com.google.android.gms.ads.internal.zzax;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.common.util.zzn;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.ads.dynamite.ModuleDescriptor;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.tp.a.h;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzagz {
    public static final Handler zzZr = new zzafs(Looper.getMainLooper());
    private static AtomicReference<List<String>> zzZs = new AtomicReference<>((Object) null);
    private static AtomicReference<List<String>> zzZt = new AtomicReference<>((Object) null);
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    /* access modifiers changed from: private */
    public String zzJP;
    private zzl zzLG;
    /* access modifiers changed from: private */
    public boolean zzZu = true;
    private boolean zzZv = false;

    public static void runOnUiThread(Runnable runnable) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            runnable.run();
        } else {
            zzZr.post(runnable);
        }
    }

    public static boolean zzD(Context context) {
        boolean z;
        Intent intent = new Intent();
        intent.setClassName(context, "com.google.android.gms.ads.AdActivity");
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, 65536);
        if (resolveActivity == null || resolveActivity.activityInfo == null) {
            zzafr.zzaT("Could not find com.google.android.gms.ads.AdActivity, please make sure it is declared in AndroidManifest.xml.");
            return false;
        }
        if ((resolveActivity.activityInfo.configChanges & 16) == 0) {
            zzafr.zzaT(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"keyboard"}));
            z = false;
        } else {
            z = true;
        }
        if ((resolveActivity.activityInfo.configChanges & 32) == 0) {
            zzafr.zzaT(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"keyboardHidden"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 128) == 0) {
            zzafr.zzaT(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"orientation"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 256) == 0) {
            zzafr.zzaT(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"screenLayout"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 512) == 0) {
            zzafr.zzaT(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"uiMode"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 1024) == 0) {
            zzafr.zzaT(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"screenSize"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 2048) != 0) {
            return z;
        }
        zzafr.zzaT(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"smallestScreenSize"}));
        return false;
    }

    protected static String zzF(Context context) {
        try {
            return new WebView(context).getSettings().getUserAgentString();
        } catch (Exception e) {
            return zzhN();
        }
    }

    public static AlertDialog.Builder zzG(Context context) {
        return new AlertDialog.Builder(context);
    }

    public static zzlz zzH(Context context) {
        return new zzlz(context);
    }

    private static String zzI(Context context) {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            if (activityManager == null) {
                return null;
            }
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
            if (!(runningTasks == null || runningTasks.isEmpty() || (runningTaskInfo = runningTasks.get(0)) == null || runningTaskInfo.topActivity == null)) {
                return runningTaskInfo.topActivity.getClassName();
            }
            return null;
        } catch (Exception e) {
        }
    }

    public static boolean zzJ(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
            if (activityManager == null || keyguardManager == null) {
                return false;
            }
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            if (runningAppProcesses == null) {
                return false;
            }
            Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ActivityManager.RunningAppProcessInfo next = it.next();
                if (Process.myPid() == next.pid) {
                    if (next.importance == 100 && !keyguardManager.inKeyguardRestrictedInputMode()) {
                        PowerManager powerManager = (PowerManager) context.getSystemService("power");
                        if (powerManager == null ? false : powerManager.isScreenOn()) {
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    public static Bitmap zzK(Context context) {
        Bitmap bitmap;
        if (!(context instanceof Activity)) {
            return null;
        }
        try {
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzFi)).booleanValue()) {
                Window window = ((Activity) context).getWindow();
                if (window != null) {
                    bitmap = zzo(window.getDecorView().getRootView());
                }
                bitmap = null;
            } else {
                bitmap = zzn(((Activity) context).getWindow().getDecorView());
            }
        } catch (RuntimeException e) {
            zzafr.zzb("Fail to capture screen shot", e);
        }
        return bitmap;
    }

    public static AudioManager zzL(Context context) {
        return (AudioManager) context.getSystemService("audio");
    }

    public static float zzM(Context context) {
        AudioManager zzL = zzL(context);
        if (zzL == null) {
            return 0.0f;
        }
        int streamMaxVolume = zzL.getStreamMaxVolume(3);
        int streamVolume = zzL.getStreamVolume(3);
        if (streamMaxVolume != 0) {
            return ((float) streamVolume) / ((float) streamMaxVolume);
        }
        return 0.0f;
    }

    public static int zzN(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (applicationInfo == null) {
            return 0;
        }
        return applicationInfo.targetSdkVersion;
    }

    public static boolean zzO(Context context) {
        try {
            context.getClassLoader().loadClass("com.google.android.gms.ads.internal.ClientApi");
            return false;
        } catch (ClassNotFoundException e) {
            return true;
        } catch (Throwable th) {
            zzafr.zzb("Error loading class.", th);
            zzbs.zzbD().zza(th, "AdUtil.isLiteSdk");
            return false;
        }
    }

    public static int zzP(Context context) {
        return DynamiteModule.zzF(context, ModuleDescriptor.MODULE_ID);
    }

    public static int zzQ(Context context) {
        return DynamiteModule.zzE(context, ModuleDescriptor.MODULE_ID);
    }

    public static Bundle zza(zzgz zzgz) {
        String zzhu;
        String zzhv;
        String str;
        if (zzgz == null) {
            return null;
        }
        if (!((Boolean) zzbs.zzbL().zzd(zzmo.zzCZ)).booleanValue()) {
            if (!((Boolean) zzbs.zzbL().zzd(zzmo.zzDb)).booleanValue()) {
                return null;
            }
        }
        if (zzbs.zzbD().zzhn() && zzbs.zzbD().zzho()) {
            return null;
        }
        if (zzgz.zzcQ()) {
            zzgz.wakeup();
        }
        zzgt zzcO = zzgz.zzcO();
        if (zzcO != null) {
            zzhu = zzcO.zzcD();
            str = zzcO.zzcE();
            String zzcF = zzcO.zzcF();
            if (zzhu != null) {
                zzbs.zzbD().zzaF(zzhu);
            }
            if (zzcF != null) {
                zzbs.zzbD().zzaG(zzcF);
                zzhv = zzcF;
            } else {
                zzhv = zzcF;
            }
        } else {
            zzhu = zzbs.zzbD().zzhu();
            zzhv = zzbs.zzbD().zzhv();
            str = null;
        }
        Bundle bundle = new Bundle(1);
        if (zzhv != null) {
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzDb)).booleanValue() && !zzbs.zzbD().zzho()) {
                bundle.putString("v_fp_vertical", zzhv);
            }
        }
        if (zzhu != null) {
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzCZ)).booleanValue() && !zzbs.zzbD().zzhn()) {
                bundle.putString("fingerprint", zzhu);
                if (!zzhu.equals(str)) {
                    bundle.putString("v_fp", str);
                }
            }
        }
        if (!bundle.isEmpty()) {
            return bundle;
        }
        return null;
    }

    public static DisplayMetrics zza(WindowManager windowManager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static PopupWindow zza(View view, int i, int i2, boolean z) {
        return new PopupWindow(view, i, i2, false);
    }

    public static String zza(Context context, View view, zziv zziv) {
        if (!((Boolean) zzbs.zzbL().zzd(zzmo.zzDr)).booleanValue()) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("width", zziv.width);
            jSONObject2.put("height", zziv.height);
            jSONObject.put("size", jSONObject2);
            jSONObject.put("activity", zzI(context));
            if (!zziv.zzAt) {
                JSONArray jSONArray = new JSONArray();
                while (view != null) {
                    ViewParent parent = view.getParent();
                    if (parent != null) {
                        int i = -1;
                        if (parent instanceof ViewGroup) {
                            i = ((ViewGroup) parent).indexOfChild(view);
                        }
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("type", parent.getClass().getName());
                        jSONObject3.put("index_of_child", i);
                        jSONArray.put(jSONObject3);
                    }
                    view = (parent == null || !(parent instanceof View)) ? null : (View) parent;
                }
                if (jSONArray.length() > 0) {
                    jSONObject.put("parents", jSONArray);
                }
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            zzafr.zzc("Fail to get view hierarchy json", e);
            return null;
        }
    }

    private static String zza(Context context, zzcu zzcu, String str, View view) {
        if (zzcu == null) {
            return str;
        }
        try {
            Uri parse = Uri.parse(str);
            if (zzcu.zzd(parse)) {
                parse = zzcu.zza(parse, context, view);
            }
            return parse.toString();
        } catch (Exception e) {
            return str;
        }
    }

    public static String zza(InputStreamReader inputStreamReader) throws IOException {
        StringBuilder sb = new StringBuilder(8192);
        char[] cArr = new char[2048];
        while (true) {
            int read = inputStreamReader.read(cArr);
            if (read == -1) {
                return sb.toString();
            }
            sb.append(cArr, 0, read);
        }
    }

    private final JSONArray zza(Collection<?> collection) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (Object zza : collection) {
            zza(jSONArray, (Object) zza);
        }
        return jSONArray;
    }

    public static void zza(Activity activity, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        Window window = activity.getWindow();
        if (window != null && window.getDecorView() != null && window.getDecorView().getViewTreeObserver() != null) {
            window.getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }

    public static void zza(Activity activity, ViewTreeObserver.OnScrollChangedListener onScrollChangedListener) {
        Window window = activity.getWindow();
        if (window != null && window.getDecorView() != null && window.getDecorView().getViewTreeObserver() != null) {
            window.getDecorView().getViewTreeObserver().addOnScrollChangedListener(onScrollChangedListener);
        }
    }

    @TargetApi(18)
    public static void zza(Context context, Uri uri) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", uri);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzGo)).booleanValue()) {
                zzc(context, intent);
            }
            bundle.putString("com.android.browser.application_id", context.getPackageName());
            context.startActivity(intent);
            String valueOf = String.valueOf(uri.toString());
            zzafr.zzaC(new StringBuilder(String.valueOf(valueOf).length() + 26).append("Opening ").append(valueOf).append(" in a new browser.").toString());
        } catch (ActivityNotFoundException e) {
            zzafr.zzb("No browser is found.", e);
        }
    }

    public static void zza(Context context, String str, List<String> list) {
        for (String zzaiq : list) {
            new zzaiq(context, str, zzaiq).zzgp();
        }
    }

    public static void zza(List<String> list, String str) {
        for (String zzaiq : list) {
            new zzaiq(zzaiq, str).zzgp();
        }
    }

    private final void zza(JSONArray jSONArray, Object obj) throws JSONException {
        if (obj instanceof Bundle) {
            jSONArray.put(zzg((Bundle) obj));
        } else if (obj instanceof Map) {
            jSONArray.put(zzj((Map) obj));
        } else if (obj instanceof Collection) {
            jSONArray.put(zza((Collection<?>) (Collection) obj));
        } else if (obj instanceof Object[]) {
            JSONArray jSONArray2 = new JSONArray();
            for (Object zza : (Object[]) obj) {
                zza(jSONArray2, zza);
            }
            jSONArray.put(jSONArray2);
        } else {
            jSONArray.put(obj);
        }
    }

    private final void zza(JSONObject jSONObject, String str, Object obj) throws JSONException {
        if (obj instanceof Bundle) {
            jSONObject.put(str, zzg((Bundle) obj));
        } else if (obj instanceof Map) {
            jSONObject.put(str, zzj((Map) obj));
        } else if (obj instanceof Collection) {
            if (str == null) {
                str = Constants.NULL_VERSION_ID;
            }
            jSONObject.put(str, zza((Collection<?>) (Collection) obj));
        } else if (obj instanceof Object[]) {
            jSONObject.put(str, zza((Collection<?>) Arrays.asList((Object[]) obj)));
        } else {
            jSONObject.put(str, obj);
        }
    }

    @TargetApi(24)
    public static boolean zza(Activity activity, Configuration configuration) {
        zzji.zzds();
        int zzc = zzaiy.zzc(activity, configuration.screenHeightDp);
        int zzc2 = zzaiy.zzc(activity, configuration.screenWidthDp);
        DisplayMetrics zza = zza((WindowManager) activity.getApplicationContext().getSystemService("window"));
        int i = zza.heightPixels;
        int i2 = zza.widthPixels;
        int identifier = activity.getResources().getIdentifier("status_bar_height", "dimen", SystemMediaRouteProvider.PACKAGE_NAME);
        int dimensionPixelSize = identifier > 0 ? activity.getResources().getDimensionPixelSize(identifier) : 0;
        int intValue = ((Integer) zzbs.zzbL().zzd(zzmo.zzGy)).intValue() * ((int) Math.round(((double) activity.getResources().getDisplayMetrics().density) + 0.5d));
        return zzb(i, dimensionPixelSize + zzc, intValue) && zzb(i2, zzc2, intValue);
    }

    public static boolean zza(ClassLoader classLoader, Class<?> cls, String str) {
        try {
            return cls.isAssignableFrom(Class.forName(str, false, classLoader));
        } catch (Throwable th) {
            return false;
        }
    }

    public static String zzaI(String str) {
        return Uri.parse(str).buildUpon().query((String) null).build().toString();
    }

    public static int zzaJ(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            String valueOf = String.valueOf(e);
            zzafr.zzaT(new StringBuilder(String.valueOf(valueOf).length() + 22).append("Could not parse value:").append(valueOf).toString());
            return 0;
        }
    }

    public static boolean zzaK(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.matches("([^\\s]+(\\.(?i)(jpg|png|gif|bmp|webp))$)");
    }

    public static boolean zzaL(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (zzZs.get() == null) {
            try {
                JSONArray jSONArray = new JSONArray((String) zzbs.zzbL().zzd(zzmo.zzDw));
                ArrayList arrayList = new ArrayList(jSONArray.length());
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(jSONArray.getString(i));
                }
                zzZs.compareAndSet((Object) null, arrayList);
            } catch (JSONException e) {
                zzafr.zzaT("Could not parse click ping schema");
                return false;
            }
        }
        for (String contains : zzZs.get()) {
            if (str.contains(contains)) {
                return true;
            }
        }
        return false;
    }

    public static boolean zzaM(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (zzZt.get() == null) {
            try {
                JSONArray jSONArray = new JSONArray((String) zzbs.zzbL().zzd(zzmo.zzDx));
                ArrayList arrayList = new ArrayList(jSONArray.length());
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(jSONArray.getString(i));
                }
                zzZt.compareAndSet((Object) null, arrayList);
            } catch (JSONException e) {
                zzafr.zzaT("Could not parse impression ping schema");
                return false;
            }
        }
        for (String contains : zzZt.get()) {
            if (str.contains(contains)) {
                return true;
            }
        }
        return false;
    }

    public static Uri zzb(String str, String str2, String str3) throws UnsupportedOperationException {
        int indexOf = str.indexOf("&adurl");
        if (indexOf == -1) {
            indexOf = str.indexOf("?adurl");
        }
        return indexOf != -1 ? Uri.parse(str.substring(0, indexOf + 1) + str2 + HttpRequest.HTTP_REQ_ENTITY_MERGE + str3 + HttpRequest.HTTP_REQ_ENTITY_JOIN + str.substring(indexOf + 1)) : Uri.parse(str).buildUpon().appendQueryParameter(str2, str3).build();
    }

    public static String zzb(zzaka zzaka, String str) {
        return zza(zzaka.getContext(), zzaka.zziy(), str, zzaka.getView());
    }

    public static void zzb(Activity activity, ViewTreeObserver.OnScrollChangedListener onScrollChangedListener) {
        Window window = activity.getWindow();
        if (window != null && window.getDecorView() != null && window.getDecorView().getViewTreeObserver() != null) {
            window.getDecorView().getViewTreeObserver().removeOnScrollChangedListener(onScrollChangedListener);
        }
    }

    public static void zzb(Context context, Intent intent) {
        try {
            context.startActivity(intent);
        } catch (Throwable th) {
            intent.addFlags(DriveFile.MODE_READ_ONLY);
            context.startActivity(intent);
        }
    }

    public static void zzb(Runnable runnable) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            runnable.run();
        } else {
            zzagt.zza(runnable);
        }
    }

    private static boolean zzb(int i, int i2, int i3) {
        return Math.abs(i - i2) <= i3;
    }

    public static float zzbf() {
        zzbs.zzbT();
        zzax zzbe = zzax.zzbe();
        if (zzbe == null || !zzbe.zzbg()) {
            return 1.0f;
        }
        return zzbe.zzbf();
    }

    public static boolean zzbh() {
        zzbs.zzbT();
        zzax zzbe = zzax.zzbe();
        if (zzbe != null) {
            return zzbe.zzbh();
        }
        return false;
    }

    @TargetApi(18)
    public static void zzc(Context context, Intent intent) {
        if (intent != null && zzq.zzsb()) {
            Bundle extras = intent.getExtras() != null ? intent.getExtras() : new Bundle();
            extras.putBinder(CustomTabsIntent.EXTRA_SESSION, (IBinder) null);
            extras.putString("com.android.browser.application_id", context.getPackageName());
            intent.putExtras(extras);
        }
    }

    public static boolean zzc(Context context, String str, String str2) {
        return zzbha.zzaP(context).checkPermission(str2, str) == 0;
    }

    public static void zzd(Context context, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str2);
        zza(context, str, (List<String>) arrayList);
    }

    public static void zze(Context context, String str, String str2) {
        try {
            FileOutputStream openFileOutput = context.openFileOutput(str, 0);
            openFileOutput.write(str2.getBytes("UTF-8"));
            openFileOutput.close();
        } catch (Exception e) {
            zzafr.zzb("Error writing to file in internal storage.", e);
        }
    }

    public static int[] zzf(Activity activity) {
        View findViewById;
        Window window = activity.getWindow();
        if (window == null || (findViewById = window.findViewById(16908290)) == null) {
            return zzhR();
        }
        return new int[]{findViewById.getWidth(), findViewById.getHeight()};
    }

    public static Map<String, String> zzg(Uri uri) {
        if (uri == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        for (String next : zzbs.zzbB().zzh(uri)) {
            hashMap.put(next, uri.getQueryParameter(next));
        }
        return hashMap;
    }

    private final JSONObject zzg(Bundle bundle) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String str : bundle.keySet()) {
            zza(jSONObject, str, bundle.get(str));
        }
        return jSONObject;
    }

    private static String zzhN() {
        StringBuffer stringBuffer = new StringBuffer(256);
        stringBuffer.append("Mozilla/5.0 (Linux; U; Android");
        if (Build.VERSION.RELEASE != null) {
            stringBuffer.append(" ").append(Build.VERSION.RELEASE);
        }
        stringBuffer.append("; ").append(Locale.getDefault());
        if (Build.DEVICE != null) {
            stringBuffer.append("; ").append(Build.DEVICE);
            if (Build.DISPLAY != null) {
                stringBuffer.append(" Build/").append(Build.DISPLAY);
            }
        }
        stringBuffer.append(") AppleWebKit/533 Version/4.0 Safari/533");
        return stringBuffer.toString();
    }

    public static String zzhO() {
        return UUID.randomUUID().toString();
    }

    public static String zzhP() {
        UUID randomUUID = UUID.randomUUID();
        byte[] byteArray = BigInteger.valueOf(randomUUID.getLeastSignificantBits()).toByteArray();
        byte[] byteArray2 = BigInteger.valueOf(randomUUID.getMostSignificantBits()).toByteArray();
        String bigInteger = new BigInteger(1, byteArray).toString();
        for (int i = 0; i < 2; i++) {
            try {
                MessageDigest instance = MessageDigest.getInstance("MD5");
                instance.update(byteArray);
                instance.update(byteArray2);
                byte[] bArr = new byte[8];
                System.arraycopy(instance.digest(), 0, bArr, 0, 8);
                bigInteger = new BigInteger(1, bArr).toString();
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return bigInteger;
    }

    public static String zzhQ() {
        String str = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        return str2.startsWith(str) ? str2 : new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length()).append(str).append(" ").append(str2).toString();
    }

    private static int[] zzhR() {
        return new int[]{0, 0};
    }

    public static Bundle zzhS() {
        Bundle bundle = new Bundle();
        try {
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzCC)).booleanValue()) {
                Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
                Debug.getMemoryInfo(memoryInfo);
                bundle.putParcelable("debug_memory_info", memoryInfo);
            }
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzCD)).booleanValue()) {
                Runtime runtime = Runtime.getRuntime();
                bundle.putLong("runtime_free_memory", runtime.freeMemory());
                bundle.putLong("runtime_max_memory", runtime.maxMemory());
                bundle.putLong("runtime_total_memory", runtime.totalMemory());
            }
            bundle.putInt("web_view_count", zzbs.zzbD().zzhJ());
        } catch (Exception e) {
            zzafr.zzc("Unable to gather memory stats", e);
        }
        return bundle;
    }

    public static Bitmap zzl(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap createBitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return createBitmap;
    }

    public static Bitmap zzm(View view) {
        if (view == null) {
            return null;
        }
        Bitmap zzo = zzo(view);
        return zzo == null ? zzn(view) : zzo;
    }

    private static Bitmap zzn(@NonNull View view) {
        try {
            int width = view.getWidth();
            int height = view.getHeight();
            if (width == 0 || height == 0) {
                zzafr.zzaT("Width or height of view is zero");
                return null;
            }
            Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(createBitmap);
            view.layout(0, 0, width, height);
            view.draw(canvas);
            return createBitmap;
        } catch (RuntimeException e) {
            zzafr.zzb("Fail to capture the webview", e);
            return null;
        }
    }

    private static Bitmap zzo(@NonNull View view) {
        Bitmap bitmap = null;
        try {
            boolean isDrawingCacheEnabled = view.isDrawingCacheEnabled();
            view.setDrawingCacheEnabled(true);
            Bitmap drawingCache = view.getDrawingCache();
            if (drawingCache != null) {
                bitmap = Bitmap.createBitmap(drawingCache);
            }
            view.setDrawingCacheEnabled(isDrawingCacheEnabled);
        } catch (RuntimeException e) {
            zzafr.zzb("Fail to capture the web view", e);
        }
        return bitmap;
    }

    public static int zzp(@Nullable View view) {
        if (view == null) {
            return -1;
        }
        ViewParent parent = view.getParent();
        while (parent != null && !(parent instanceof AdapterView)) {
            parent = parent.getParent();
        }
        if (parent == null) {
            return -1;
        }
        return ((AdapterView) parent).getPositionForView(view);
    }

    public static String zzt(Context context, String str) {
        try {
            return new String(zzn.zza(context.openFileInput(str), true), "UTF-8");
        } catch (IOException e) {
            zzafr.zzaC("Error reading from internal storage.");
            return "";
        }
    }

    public final boolean zzE(Context context) {
        if (this.zzZv) {
            return false;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        context.getApplicationContext().registerReceiver(new zzahd(this, (zzaha) null), intentFilter);
        this.zzZv = true;
        return true;
    }

    public final JSONObject zza(Bundle bundle, JSONObject jSONObject) {
        try {
            return zzg(bundle);
        } catch (JSONException e) {
            zzafr.zzb("Error converting Bundle to JSON", e);
            return null;
        }
    }

    public final void zza(Context context, @Nullable String str, String str2, Bundle bundle, boolean z) {
        if (z) {
            zzbs.zzbz();
            bundle.putString("device", zzhQ());
            bundle.putString("eids", TextUtils.join(",", zzmo.zzdJ()));
        }
        zzji.zzds();
        zzaiy.zza(context, str, str2, bundle, z, new zzahc(this, context, str));
    }

    public final void zza(Context context, String str, boolean z, HttpURLConnection httpURLConnection) {
        httpURLConnection.setConnectTimeout(60000);
        httpURLConnection.setInstanceFollowRedirects(z);
        httpURLConnection.setReadTimeout(60000);
        httpURLConnection.setRequestProperty("User-Agent", zzs(context, str));
        httpURLConnection.setUseCaches(false);
    }

    public final void zza(Context context, List<String> list) {
        if (!(context instanceof Activity) || TextUtils.isEmpty(aeo.zzbU((Activity) context))) {
            return;
        }
        if (list == null) {
            zzafr.v("Cannot ping urls: empty list.");
        } else if (!zznl.zzi(context)) {
            zzafr.v("Cannot ping url because custom tabs is not supported");
        } else {
            zznl zznl = new zznl();
            zznl.zza((zznm) new zzaha(this, list, zznl, context));
            zznl.zzd((Activity) context);
        }
    }

    public final boolean zza(View view, Context context) {
        KeyguardManager keyguardManager = null;
        Context applicationContext = context.getApplicationContext();
        PowerManager powerManager = applicationContext != null ? (PowerManager) applicationContext.getSystemService("power") : null;
        Object systemService = context.getSystemService("keyguard");
        if (systemService != null && (systemService instanceof KeyguardManager)) {
            keyguardManager = (KeyguardManager) systemService;
        }
        return zza(view, powerManager, keyguardManager);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0045, code lost:
        if (r0 != false) goto L_0x0047;
     */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x009a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zza(android.view.View r6, android.os.PowerManager r7, android.app.KeyguardManager r8) {
        /*
            r5 = this;
            r3 = 0
            r2 = 1
            r1 = 0
            com.google.android.gms.internal.zzagz r0 = com.google.android.gms.ads.internal.zzbs.zzbz()
            boolean r0 = r0.zzZu
            if (r0 != 0) goto L_0x0047
            if (r8 != 0) goto L_0x008a
            r0 = r1
        L_0x000e:
            if (r0 == 0) goto L_0x0047
            com.google.android.gms.internal.zzme<java.lang.Boolean> r0 = com.google.android.gms.internal.zzmo.zzEs
            com.google.android.gms.internal.zzmm r4 = com.google.android.gms.ads.internal.zzbs.zzbL()
            java.lang.Object r0 = r4.zzd(r0)
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x0098
            android.view.View r0 = r6.getRootView()
            if (r0 == 0) goto L_0x008f
            android.content.Context r0 = r0.getContext()
            boolean r4 = r0 instanceof android.app.Activity
            if (r4 == 0) goto L_0x008f
            android.app.Activity r0 = (android.app.Activity) r0
        L_0x0032:
            if (r0 == 0) goto L_0x0096
            android.view.Window r0 = r0.getWindow()
            if (r0 != 0) goto L_0x0091
            r0 = r3
        L_0x003b:
            if (r0 == 0) goto L_0x0096
            int r0 = r0.flags
            r3 = 524288(0x80000, float:7.34684E-40)
            r0 = r0 & r3
            if (r0 == 0) goto L_0x0096
            r0 = r2
        L_0x0045:
            if (r0 == 0) goto L_0x0098
        L_0x0047:
            r0 = r2
        L_0x0048:
            int r3 = r6.getVisibility()
            if (r3 != 0) goto L_0x009c
            boolean r3 = r6.isShown()
            if (r3 == 0) goto L_0x009c
            if (r7 == 0) goto L_0x005c
            boolean r3 = r7.isScreenOn()
            if (r3 == 0) goto L_0x009a
        L_0x005c:
            r3 = r2
        L_0x005d:
            if (r3 == 0) goto L_0x009c
            if (r0 == 0) goto L_0x009c
            com.google.android.gms.internal.zzme<java.lang.Boolean> r0 = com.google.android.gms.internal.zzmo.zzEq
            com.google.android.gms.internal.zzmm r3 = com.google.android.gms.ads.internal.zzbs.zzbL()
            java.lang.Object r0 = r3.zzd(r0)
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x0089
            android.graphics.Rect r0 = new android.graphics.Rect
            r0.<init>()
            boolean r0 = r6.getLocalVisibleRect(r0)
            if (r0 != 0) goto L_0x0089
            android.graphics.Rect r0 = new android.graphics.Rect
            r0.<init>()
            boolean r0 = r6.getGlobalVisibleRect(r0)
            if (r0 == 0) goto L_0x009c
        L_0x0089:
            return r2
        L_0x008a:
            boolean r0 = r8.inKeyguardRestrictedInputMode()
            goto L_0x000e
        L_0x008f:
            r0 = r3
            goto L_0x0032
        L_0x0091:
            android.view.WindowManager$LayoutParams r0 = r0.getAttributes()
            goto L_0x003b
        L_0x0096:
            r0 = r1
            goto L_0x0045
        L_0x0098:
            r0 = r1
            goto L_0x0048
        L_0x009a:
            r3 = r1
            goto L_0x005d
        L_0x009c:
            r2 = r1
            goto L_0x0089
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzagz.zza(android.view.View, android.os.PowerManager, android.app.KeyguardManager):boolean");
    }

    public final void zzb(Context context, String str, String str2, Bundle bundle, boolean z) {
        if (((Boolean) zzbs.zzbL().zzd(zzmo.zzEv)).booleanValue()) {
            zza(context, str, str2, bundle, z);
        }
    }

    public final zzl zze(Context context, zzaje zzaje) {
        zzl zzl;
        synchronized (this.mLock) {
            if (this.zzLG == null) {
                if (context.getApplicationContext() != null) {
                    context = context.getApplicationContext();
                }
                this.zzLG = new zzl(context, zzaje, (String) zzbs.zzbL().zzd(zzmo.zzBX));
            }
            zzl = this.zzLG;
        }
        return zzl;
    }

    public final int[] zzg(Activity activity) {
        int[] zzf = zzf(activity);
        zzji.zzds();
        zzji.zzds();
        return new int[]{zzaiy.zzd(activity, zzf[0]), zzaiy.zzd(activity, zzf[1])};
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0009, code lost:
        r1 = r0.findViewById(16908290);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int[] zzh(android.app.Activity r7) {
        /*
            r6 = this;
            r5 = 2
            r4 = 1
            r3 = 0
            android.view.Window r0 = r7.getWindow()
            if (r0 == 0) goto L_0x0039
            r1 = 16908290(0x1020002, float:2.3877235E-38)
            android.view.View r1 = r0.findViewById(r1)
            if (r1 == 0) goto L_0x0039
            int[] r0 = new int[r5]
            int r2 = r1.getTop()
            r0[r3] = r2
            int r1 = r1.getBottom()
            r0[r4] = r1
        L_0x0020:
            int[] r1 = new int[r5]
            com.google.android.gms.internal.zzji.zzds()
            r2 = r0[r3]
            int r2 = com.google.android.gms.internal.zzaiy.zzd(r7, r2)
            r1[r3] = r2
            com.google.android.gms.internal.zzji.zzds()
            r0 = r0[r4]
            int r0 = com.google.android.gms.internal.zzaiy.zzd(r7, r0)
            r1[r4] = r0
            return r1
        L_0x0039:
            int[] r0 = zzhR()
            goto L_0x0020
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzagz.zzh(android.app.Activity):int[]");
    }

    public final JSONObject zzj(Map<String, ?> map) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            for (String next : map.keySet()) {
                zza(jSONObject, next, (Object) map.get(next));
            }
            return jSONObject;
        } catch (ClassCastException e) {
            String valueOf = String.valueOf(e.getMessage());
            throw new JSONException(valueOf.length() != 0 ? "Could not convert map to JSON: ".concat(valueOf) : new String("Could not convert map to JSON: "));
        }
    }

    public final String zzs(Context context, String str) {
        String str2;
        synchronized (this.mLock) {
            if (this.zzJP != null) {
                str2 = this.zzJP;
            } else if (str == null) {
                str2 = zzhN();
            } else {
                try {
                    this.zzJP = zzbs.zzbB().getDefaultUserAgent(context);
                } catch (Exception e) {
                }
                if (TextUtils.isEmpty(this.zzJP)) {
                    zzji.zzds();
                    if (!zzaiy.zzil()) {
                        this.zzJP = null;
                        zzZr.post(new zzahb(this, context));
                        while (this.zzJP == null) {
                            try {
                                this.mLock.wait();
                            } catch (InterruptedException e2) {
                                this.zzJP = zzhN();
                                String valueOf = String.valueOf(this.zzJP);
                                zzafr.zzaT(valueOf.length() != 0 ? "Interrupted, use default user agent: ".concat(valueOf) : new String("Interrupted, use default user agent: "));
                            }
                        }
                    } else {
                        this.zzJP = zzF(context);
                    }
                }
                String valueOf2 = String.valueOf(this.zzJP);
                this.zzJP = new StringBuilder(String.valueOf(valueOf2).length() + 11 + String.valueOf(str).length()).append(valueOf2).append(" (Mobile; ").append(str).append(h.b).toString();
                str2 = this.zzJP;
            }
        }
        return str2;
    }
}
