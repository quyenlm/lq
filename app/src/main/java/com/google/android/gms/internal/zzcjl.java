package com.google.android.gms.internal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.security.auth.x500.X500Principal;

public final class zzcjl extends zzchj {
    private static String[] zzbuD = {"firebase_"};
    private SecureRandom zzbuE;
    private final AtomicLong zzbuF = new AtomicLong(0);
    private int zzbuG;

    zzcjl(zzcgl zzcgl) {
        super(zzcgl);
    }

    public static Bundle[] zzC(Object obj) {
        if (obj instanceof Bundle) {
            return new Bundle[]{(Bundle) obj};
        } else if (obj instanceof Parcelable[]) {
            return (Bundle[]) Arrays.copyOf((Parcelable[]) obj, ((Parcelable[]) obj).length, Bundle[].class);
        } else {
            if (!(obj instanceof ArrayList)) {
                return null;
            }
            ArrayList arrayList = (ArrayList) obj;
            return (Bundle[]) arrayList.toArray(new Bundle[arrayList.size()]);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0033 A[Catch:{ IOException | ClassNotFoundException -> 0x003c }] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0038 A[Catch:{ IOException | ClassNotFoundException -> 0x003c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object zzD(java.lang.Object r5) {
        /*
            r0 = 0
            if (r5 != 0) goto L_0x0004
        L_0x0003:
            return r0
        L_0x0004:
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x002e }
            r1.<init>()     // Catch:{ all -> 0x002e }
            java.io.ObjectOutputStream r3 = new java.io.ObjectOutputStream     // Catch:{ all -> 0x002e }
            r3.<init>(r1)     // Catch:{ all -> 0x002e }
            r3.writeObject(r5)     // Catch:{ all -> 0x0040 }
            r3.flush()     // Catch:{ all -> 0x0040 }
            java.io.ObjectInputStream r2 = new java.io.ObjectInputStream     // Catch:{ all -> 0x0040 }
            java.io.ByteArrayInputStream r4 = new java.io.ByteArrayInputStream     // Catch:{ all -> 0x0040 }
            byte[] r1 = r1.toByteArray()     // Catch:{ all -> 0x0040 }
            r4.<init>(r1)     // Catch:{ all -> 0x0040 }
            r2.<init>(r4)     // Catch:{ all -> 0x0040 }
            java.lang.Object r1 = r2.readObject()     // Catch:{ all -> 0x0043 }
            r3.close()     // Catch:{ IOException -> 0x003c, ClassNotFoundException -> 0x003e }
            r2.close()     // Catch:{ IOException -> 0x003c, ClassNotFoundException -> 0x003e }
            r0 = r1
            goto L_0x0003
        L_0x002e:
            r1 = move-exception
            r2 = r0
            r3 = r0
        L_0x0031:
            if (r3 == 0) goto L_0x0036
            r3.close()     // Catch:{ IOException -> 0x003c, ClassNotFoundException -> 0x003e }
        L_0x0036:
            if (r2 == 0) goto L_0x003b
            r2.close()     // Catch:{ IOException -> 0x003c, ClassNotFoundException -> 0x003e }
        L_0x003b:
            throw r1     // Catch:{ IOException -> 0x003c, ClassNotFoundException -> 0x003e }
        L_0x003c:
            r1 = move-exception
            goto L_0x0003
        L_0x003e:
            r1 = move-exception
            goto L_0x0003
        L_0x0040:
            r1 = move-exception
            r2 = r0
            goto L_0x0031
        L_0x0043:
            r1 = move-exception
            goto L_0x0031
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcjl.zzD(java.lang.Object):java.lang.Object");
    }

    private final boolean zzJ(Context context, String str) {
        X500Principal x500Principal = new X500Principal("CN=Android Debug,O=Android,C=US");
        try {
            PackageInfo packageInfo = zzbha.zzaP(context).getPackageInfo(str, 64);
            if (!(packageInfo == null || packageInfo.signatures == null || packageInfo.signatures.length <= 0)) {
                return ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(packageInfo.signatures[0].toByteArray()))).getSubjectX500Principal().equals(x500Principal);
            }
        } catch (CertificateException e) {
            super.zzwF().zzyx().zzj("Error obtaining certificate", e);
        } catch (PackageManager.NameNotFoundException e2) {
            super.zzwF().zzyx().zzj("Package name not found", e2);
        }
        return true;
    }

    private final boolean zzP(String str, String str2) {
        if (str2 == null) {
            super.zzwF().zzyx().zzj("Name is required and can't be null. Type", str);
            return false;
        } else if (str2.length() == 0) {
            super.zzwF().zzyx().zzj("Name is required and can't be empty. Type", str);
            return false;
        } else {
            int codePointAt = str2.codePointAt(0);
            if (!Character.isLetter(codePointAt)) {
                super.zzwF().zzyx().zze("Name must start with a letter. Type, name", str, str2);
                return false;
            }
            int length = str2.length();
            int charCount = Character.charCount(codePointAt);
            while (charCount < length) {
                int codePointAt2 = str2.codePointAt(charCount);
                if (codePointAt2 == 95 || Character.isLetterOrDigit(codePointAt2)) {
                    charCount += Character.charCount(codePointAt2);
                } else {
                    super.zzwF().zzyx().zze("Name must consist of letters, digits or _ (underscores). Type, name", str, str2);
                    return false;
                }
            }
            return true;
        }
    }

    private final boolean zzQ(String str, String str2) {
        if (str2 == null) {
            super.zzwF().zzyx().zzj("Name is required and can't be null. Type", str);
            return false;
        } else if (str2.length() == 0) {
            super.zzwF().zzyx().zzj("Name is required and can't be empty. Type", str);
            return false;
        } else {
            int codePointAt = str2.codePointAt(0);
            if (Character.isLetter(codePointAt) || codePointAt == 95) {
                int length = str2.length();
                int charCount = Character.charCount(codePointAt);
                while (charCount < length) {
                    int codePointAt2 = str2.codePointAt(charCount);
                    if (codePointAt2 == 95 || Character.isLetterOrDigit(codePointAt2)) {
                        charCount += Character.charCount(codePointAt2);
                    } else {
                        super.zzwF().zzyx().zze("Name must consist of letters, digits or _ (underscores). Type, name", str, str2);
                        return false;
                    }
                }
                return true;
            }
            super.zzwF().zzyx().zze("Name must start with a letter or _ (underscore). Type, name", str, str2);
            return false;
        }
    }

    public static boolean zzR(String str, String str2) {
        if (str == null && str2 == null) {
            return true;
        }
        if (str == null) {
            return false;
        }
        return str.equals(str2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x0029 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int zza(java.lang.String r8, java.lang.Object r9, boolean r10) {
        /*
            r7 = this;
            r1 = 1
            r6 = 0
            if (r10 == 0) goto L_0x003c
            java.lang.String r2 = "param"
            com.google.android.gms.internal.zzcem.zzxm()
            boolean r0 = r9 instanceof android.os.Parcelable[]
            if (r0 == 0) goto L_0x002c
            r0 = r9
            android.os.Parcelable[] r0 = (android.os.Parcelable[]) r0
            int r0 = r0.length
        L_0x0011:
            r3 = 1000(0x3e8, float:1.401E-42)
            if (r0 <= r3) goto L_0x003a
            com.google.android.gms.internal.zzcfl r1 = super.zzwF()
            com.google.android.gms.internal.zzcfn r1 = r1.zzyz()
            java.lang.String r3 = "Parameter array is too long; discarded. Value kind, name, array length"
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r1.zzd(r3, r2, r8, r0)
            r0 = r6
        L_0x0027:
            if (r0 != 0) goto L_0x003c
            r0 = 17
        L_0x002b:
            return r0
        L_0x002c:
            boolean r0 = r9 instanceof java.util.ArrayList
            if (r0 == 0) goto L_0x0038
            r0 = r9
            java.util.ArrayList r0 = (java.util.ArrayList) r0
            int r0 = r0.size()
            goto L_0x0011
        L_0x0038:
            r0 = r1
            goto L_0x0027
        L_0x003a:
            r0 = r1
            goto L_0x0027
        L_0x003c:
            boolean r0 = zzex(r8)
            if (r0 == 0) goto L_0x0054
            java.lang.String r1 = "param"
            int r3 = com.google.android.gms.internal.zzcem.zzxl()
            r0 = r7
            r2 = r8
            r4 = r9
            r5 = r10
            boolean r0 = r0.zza((java.lang.String) r1, (java.lang.String) r2, (int) r3, (java.lang.Object) r4, (boolean) r5)
        L_0x0050:
            if (r0 == 0) goto L_0x0063
            r0 = r6
            goto L_0x002b
        L_0x0054:
            java.lang.String r1 = "param"
            int r3 = com.google.android.gms.internal.zzcem.zzxk()
            r0 = r7
            r2 = r8
            r4 = r9
            r5 = r10
            boolean r0 = r0.zza((java.lang.String) r1, (java.lang.String) r2, (int) r3, (java.lang.Object) r4, (boolean) r5)
            goto L_0x0050
        L_0x0063:
            r0 = 4
            goto L_0x002b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcjl.zza(java.lang.String, java.lang.Object, boolean):int");
    }

    private static Object zza(int i, Object obj, boolean z) {
        if (obj == null) {
            return null;
        }
        if ((obj instanceof Long) || (obj instanceof Double)) {
            return obj;
        }
        if (obj instanceof Integer) {
            return Long.valueOf((long) ((Integer) obj).intValue());
        }
        if (obj instanceof Byte) {
            return Long.valueOf((long) ((Byte) obj).byteValue());
        }
        if (obj instanceof Short) {
            return Long.valueOf((long) ((Short) obj).shortValue());
        }
        if (obj instanceof Boolean) {
            return Long.valueOf(((Boolean) obj).booleanValue() ? 1 : 0);
        } else if (obj instanceof Float) {
            return Double.valueOf(((Float) obj).doubleValue());
        } else {
            if ((obj instanceof String) || (obj instanceof Character) || (obj instanceof CharSequence)) {
                return zza(String.valueOf(obj), i, z);
            }
            return null;
        }
    }

    public static String zza(String str, int i, boolean z) {
        if (str.codePointCount(0, str.length()) <= i) {
            return str;
        }
        if (z) {
            return String.valueOf(str.substring(0, str.offsetByCodePoints(0, i))).concat("...");
        }
        return null;
    }

    @Nullable
    public static String zza(String str, String[] strArr, String[] strArr2) {
        zzbo.zzu(strArr);
        zzbo.zzu(strArr2);
        int min = Math.min(strArr.length, strArr2.length);
        for (int i = 0; i < min; i++) {
            if (zzR(str, strArr[i])) {
                return strArr2[i];
            }
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0008, code lost:
        r1 = r1.getReceiverInfo(new android.content.ComponentName(r4, r5), 2);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean zza(android.content.Context r4, java.lang.String r5, boolean r6) {
        /*
            r0 = 0
            android.content.pm.PackageManager r1 = r4.getPackageManager()     // Catch:{ NameNotFoundException -> 0x001a }
            if (r1 != 0) goto L_0x0008
        L_0x0007:
            return r0
        L_0x0008:
            android.content.ComponentName r2 = new android.content.ComponentName     // Catch:{ NameNotFoundException -> 0x001a }
            r2.<init>(r4, r5)     // Catch:{ NameNotFoundException -> 0x001a }
            r3 = 2
            android.content.pm.ActivityInfo r1 = r1.getReceiverInfo(r2, r3)     // Catch:{ NameNotFoundException -> 0x001a }
            if (r1 == 0) goto L_0x0007
            boolean r1 = r1.enabled     // Catch:{ NameNotFoundException -> 0x001a }
            if (r1 == 0) goto L_0x0007
            r0 = 1
            goto L_0x0007
        L_0x001a:
            r1 = move-exception
            goto L_0x0007
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcjl.zza(android.content.Context, java.lang.String, boolean):boolean");
    }

    private final boolean zza(String str, String str2, int i, Object obj, boolean z) {
        if (obj == null || (obj instanceof Long) || (obj instanceof Float) || (obj instanceof Integer) || (obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Boolean) || (obj instanceof Double)) {
            return true;
        }
        if ((obj instanceof String) || (obj instanceof Character) || (obj instanceof CharSequence)) {
            String valueOf = String.valueOf(obj);
            if (valueOf.codePointCount(0, valueOf.length()) <= i) {
                return true;
            }
            super.zzwF().zzyz().zzd("Value is too long; discarded. Value kind, name, value length", str, str2, Integer.valueOf(valueOf.length()));
            return false;
        } else if ((obj instanceof Bundle) && z) {
            return true;
        } else {
            if ((obj instanceof Parcelable[]) && z) {
                for (Parcelable parcelable : (Parcelable[]) obj) {
                    if (!(parcelable instanceof Bundle)) {
                        super.zzwF().zzyz().zze("All Parcelable[] elements must be of type Bundle. Value type, name", parcelable.getClass(), str2);
                        return false;
                    }
                }
                return true;
            } else if (!(obj instanceof ArrayList) || !z) {
                return false;
            } else {
                ArrayList arrayList = (ArrayList) obj;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj2 = arrayList.get(i2);
                    i2++;
                    if (!(obj2 instanceof Bundle)) {
                        super.zzwF().zzyz().zze("All ArrayList elements must be of type Bundle. Value type, name", obj2.getClass(), str2);
                        return false;
                    }
                }
                return true;
            }
        }
    }

    private final boolean zza(String str, String[] strArr, String str2) {
        boolean z;
        boolean z2;
        if (str2 == null) {
            super.zzwF().zzyx().zzj("Name is required and can't be null. Type", str);
            return false;
        }
        zzbo.zzu(str2);
        int i = 0;
        while (true) {
            if (i >= zzbuD.length) {
                z = false;
                break;
            } else if (str2.startsWith(zzbuD[i])) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (z) {
            super.zzwF().zzyx().zze("Name starts with reserved prefix. Type, name", str, str2);
            return false;
        }
        if (strArr != null) {
            zzbo.zzu(strArr);
            int i2 = 0;
            while (true) {
                if (i2 >= strArr.length) {
                    z2 = false;
                    break;
                } else if (zzR(str2, strArr[i2])) {
                    z2 = true;
                    break;
                } else {
                    i2++;
                }
            }
            if (z2) {
                super.zzwF().zzyx().zze("Name is reserved. Type, name", str, str2);
                return false;
            }
        }
        return true;
    }

    public static boolean zza(long[] jArr, int i) {
        return i < (jArr.length << 6) && (jArr[i / 64] & (1 << (i % 64))) != 0;
    }

    static byte[] zza(Parcelable parcelable) {
        if (parcelable == null) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        try {
            parcelable.writeToParcel(obtain, 0);
            return obtain.marshall();
        } finally {
            obtain.recycle();
        }
    }

    public static long[] zza(BitSet bitSet) {
        int length = (bitSet.length() + 63) / 64;
        long[] jArr = new long[length];
        int i = 0;
        while (i < length) {
            jArr[i] = 0;
            int i2 = 0;
            while (i2 < 64 && (i << 6) + i2 < bitSet.length()) {
                if (bitSet.get((i << 6) + i2)) {
                    jArr[i] = jArr[i] | (1 << i2);
                }
                i2++;
            }
            i++;
        }
        return jArr;
    }

    private static void zzb(Bundle bundle, Object obj) {
        zzbo.zzu(bundle);
        if (obj == null) {
            return;
        }
        if ((obj instanceof String) || (obj instanceof CharSequence)) {
            bundle.putLong("_el", (long) String.valueOf(obj).length());
        }
    }

    private final boolean zzb(String str, int i, String str2) {
        if (str2 == null) {
            super.zzwF().zzyx().zzj("Name is required and can't be null. Type", str);
            return false;
        } else if (str2.codePointCount(0, str2.length()) <= i) {
            return true;
        } else {
            super.zzwF().zzyx().zzd("Name is too long. Type, maximum supported length, name", str, Integer.valueOf(i), str2);
            return false;
        }
    }

    static MessageDigest zzbE(String str) {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= 2) {
                return null;
            }
            try {
                MessageDigest instance = MessageDigest.getInstance(str);
                if (instance != null) {
                    return instance;
                }
                i = i2 + 1;
            } catch (NoSuchAlgorithmException e) {
            }
        }
    }

    private static boolean zzd(Bundle bundle, int i) {
        if (bundle.getLong("_err") != 0) {
            return false;
        }
        bundle.putLong("_err", (long) i);
        return true;
    }

    @WorkerThread
    static boolean zzd(zzcez zzcez, zzceh zzceh) {
        zzbo.zzu(zzcez);
        zzbo.zzu(zzceh);
        if (!TextUtils.isEmpty(zzceh.zzboQ)) {
            return true;
        }
        zzcem.zzxE();
        return false;
    }

    @WorkerThread
    static boolean zzeC(String str) {
        zzbo.zzcF(str);
        char c = 65535;
        switch (str.hashCode()) {
            case 94660:
                if (str.equals("_in")) {
                    c = 0;
                    break;
                }
                break;
            case 95025:
                if (str.equals("_ug")) {
                    c = 2;
                    break;
                }
                break;
            case 95027:
                if (str.equals("_ui")) {
                    c = 1;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    static boolean zzeo(String str) {
        zzbo.zzcF(str);
        return str.charAt(0) != '_' || str.equals("_ep");
    }

    private final int zzet(String str) {
        if (!zzP("event param", str)) {
            return 3;
        }
        if (!zza("event param", (String[]) null, str)) {
            return 14;
        }
        return zzb("event param", zzcem.zzxj(), str) ? 0 : 3;
    }

    private final int zzeu(String str) {
        if (!zzQ("event param", str)) {
            return 3;
        }
        if (!zza("event param", (String[]) null, str)) {
            return 14;
        }
        return zzb("event param", zzcem.zzxj(), str) ? 0 : 3;
    }

    private static int zzew(String str) {
        return "_ldl".equals(str) ? zzcem.zzxo() : zzcem.zzxn();
    }

    public static boolean zzex(String str) {
        return !TextUtils.isEmpty(str) && str.startsWith("_");
    }

    static boolean zzez(String str) {
        return str != null && str.matches("(\\+|-)?([0-9]+\\.?[0-9]*|[0-9]*\\.?[0-9]+)") && str.length() <= 310;
    }

    public static boolean zzl(Intent intent) {
        String stringExtra = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        return "android-app://com.google.android.googlequicksearchbox/https/www.google.com".equals(stringExtra) || "https://www.google.com".equals(stringExtra) || "android-app://com.google.appcrawler".equals(stringExtra);
    }

    static long zzn(byte[] bArr) {
        int i = 0;
        zzbo.zzu(bArr);
        zzbo.zzae(bArr.length > 0);
        long j = 0;
        int length = bArr.length - 1;
        while (length >= 0 && length >= bArr.length - 8) {
            j += (((long) bArr[length]) & 255) << i;
            i += 8;
            length--;
        }
        return j;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0008, code lost:
        r1 = r1.getServiceInfo(new android.content.ComponentName(r4, r5), 4);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean zzw(android.content.Context r4, java.lang.String r5) {
        /*
            r0 = 0
            android.content.pm.PackageManager r1 = r4.getPackageManager()     // Catch:{ NameNotFoundException -> 0x001a }
            if (r1 != 0) goto L_0x0008
        L_0x0007:
            return r0
        L_0x0008:
            android.content.ComponentName r2 = new android.content.ComponentName     // Catch:{ NameNotFoundException -> 0x001a }
            r2.<init>(r4, r5)     // Catch:{ NameNotFoundException -> 0x001a }
            r3 = 4
            android.content.pm.ServiceInfo r1 = r1.getServiceInfo(r2, r3)     // Catch:{ NameNotFoundException -> 0x001a }
            if (r1 == 0) goto L_0x0007
            boolean r1 = r1.enabled     // Catch:{ NameNotFoundException -> 0x001a }
            if (r1 == 0) goto L_0x0007
            r0 = 1
            goto L_0x0007
        L_0x001a:
            r1 = move-exception
            goto L_0x0007
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcjl.zzw(android.content.Context, java.lang.String):boolean");
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    /* access modifiers changed from: package-private */
    public final Bundle zzB(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        if (bundle != null) {
            for (String str : bundle.keySet()) {
                Object zzk = zzk(str, bundle.get(str));
                if (zzk == null) {
                    super.zzwF().zzyz().zzj("Param value can't be null", super.zzwA().zzdX(str));
                } else {
                    zza(bundle2, str, zzk);
                }
            }
        }
        return bundle2;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final long zzI(Context context, String str) {
        super.zzjC();
        zzbo.zzu(context);
        zzbo.zzcF(str);
        PackageManager packageManager = context.getPackageManager();
        MessageDigest zzbE = zzbE("MD5");
        if (zzbE == null) {
            super.zzwF().zzyx().log("Could not get MD5 instance");
            return -1;
        }
        if (packageManager != null) {
            try {
                if (!zzJ(context, str)) {
                    PackageInfo packageInfo = zzbha.zzaP(context).getPackageInfo(super.getContext().getPackageName(), 64);
                    if (packageInfo.signatures != null && packageInfo.signatures.length > 0) {
                        return zzn(zzbE.digest(packageInfo.signatures[0].toByteArray()));
                    }
                    super.zzwF().zzyz().log("Could not get signatures");
                    return -1;
                }
            } catch (PackageManager.NameNotFoundException e) {
                super.zzwF().zzyx().zzj("Package name not found", e);
            }
        }
        return 0;
    }

    public final Bundle zza(String str, Bundle bundle, @Nullable List<String> list, boolean z, boolean z2) {
        int i;
        if (bundle == null) {
            return null;
        }
        Bundle bundle2 = new Bundle(bundle);
        zzcem.zzxg();
        int i2 = 0;
        for (String str2 : bundle.keySet()) {
            if (list == null || !list.contains(str2)) {
                i = z ? zzet(str2) : 0;
                if (i == 0) {
                    i = zzeu(str2);
                }
            } else {
                i = 0;
            }
            if (i != 0) {
                if (zzd(bundle2, i)) {
                    bundle2.putString("_ev", zza(str2, zzcem.zzxj(), true));
                    if (i == 3) {
                        zzb(bundle2, (Object) str2);
                    }
                }
                bundle2.remove(str2);
            } else {
                int zza = zza(str2, bundle.get(str2), z2);
                if (zza != 0 && !"_ev".equals(str2)) {
                    if (zzd(bundle2, zza)) {
                        bundle2.putString("_ev", zza(str2, zzcem.zzxj(), true));
                        zzb(bundle2, bundle.get(str2));
                    }
                    bundle2.remove(str2);
                } else if (!zzeo(str2) || (i2 = i2 + 1) <= 25) {
                    i2 = i2;
                } else {
                    super.zzwF().zzyx().zze(new StringBuilder(48).append("Event can't contain more then 25 params").toString(), super.zzwA().zzdW(str), super.zzwA().zzA(bundle));
                    zzd(bundle2, 5);
                    bundle2.remove(str2);
                }
            }
        }
        return bundle2;
    }

    /* access modifiers changed from: package-private */
    public final zzcez zza(String str, Bundle bundle, String str2, long j, boolean z, boolean z2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (zzeq(str) != 0) {
            super.zzwF().zzyx().zzj("Invalid conditional property event name", super.zzwA().zzdY(str));
            throw new IllegalArgumentException();
        }
        Bundle bundle2 = bundle != null ? new Bundle(bundle) : new Bundle();
        bundle2.putString("_o", str2);
        return new zzcez(str, new zzcew(zzB(zza(str, bundle2, (List<String>) Collections.singletonList("_o"), false, false))), str2, j);
    }

    public final void zza(int i, String str, String str2, int i2) {
        zza((String) null, i, str, str2, i2);
    }

    public final void zza(Bundle bundle, String str, Object obj) {
        if (bundle != null) {
            if (obj instanceof Long) {
                bundle.putLong(str, ((Long) obj).longValue());
            } else if (obj instanceof String) {
                bundle.putString(str, String.valueOf(obj));
            } else if (obj instanceof Double) {
                bundle.putDouble(str, ((Double) obj).doubleValue());
            } else if (str != null) {
                super.zzwF().zzyA().zze("Not putting event parameter. Invalid value type. name, type", super.zzwA().zzdX(str), obj != null ? obj.getClass().getSimpleName() : null);
            }
        }
    }

    public final void zza(zzcjx zzcjx, Object obj) {
        zzbo.zzu(obj);
        zzcjx.zzaIF = null;
        zzcjx.zzbvA = null;
        zzcjx.zzbuB = null;
        if (obj instanceof String) {
            zzcjx.zzaIF = (String) obj;
        } else if (obj instanceof Long) {
            zzcjx.zzbvA = (Long) obj;
        } else if (obj instanceof Double) {
            zzcjx.zzbuB = (Double) obj;
        } else {
            super.zzwF().zzyx().zzj("Ignoring invalid (type) event param value", obj);
        }
    }

    public final void zza(zzckb zzckb, Object obj) {
        zzbo.zzu(obj);
        zzckb.zzaIF = null;
        zzckb.zzbvA = null;
        zzckb.zzbuB = null;
        if (obj instanceof String) {
            zzckb.zzaIF = (String) obj;
        } else if (obj instanceof Long) {
            zzckb.zzbvA = (Long) obj;
        } else if (obj instanceof Double) {
            zzckb.zzbuB = (Double) obj;
        } else {
            super.zzwF().zzyx().zzj("Ignoring invalid (type) user attribute value", obj);
        }
    }

    public final void zza(String str, int i, String str2, String str3, int i2) {
        Bundle bundle = new Bundle();
        zzd(bundle, i);
        if (!TextUtils.isEmpty(str2)) {
            bundle.putString(str2, str3);
        }
        if (i == 6 || i == 7 || i == 2) {
            bundle.putLong("_el", (long) i2);
        }
        zzcem.zzxE();
        this.zzboe.zzwt().zzd("auto", "_err", bundle);
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public final <T extends Parcelable> T zzb(byte[] bArr, Parcelable.Creator<T> creator) {
        if (bArr == null) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        try {
            obtain.unmarshall(bArr, 0, bArr.length);
            obtain.setDataPosition(0);
            T t = (Parcelable) creator.createFromParcel(obtain);
            obtain.recycle();
            return t;
        } catch (zzc e) {
            super.zzwF().zzyx().log("Failed to load parcelable from buffer");
            obtain.recycle();
            return null;
        } catch (Throwable th) {
            obtain.recycle();
            throw th;
        }
    }

    public final byte[] zzb(zzcjy zzcjy) {
        try {
            byte[] bArr = new byte[zzcjy.zzLV()];
            adh zzc = adh.zzc(bArr, 0, bArr.length);
            zzcjy.zza(zzc);
            zzc.zzLM();
            return bArr;
        } catch (IOException e) {
            super.zzwF().zzyx().zzj("Data loss. Failed to serialize batch", e);
            return null;
        }
    }

    @WorkerThread
    public final boolean zzbv(String str) {
        super.zzjC();
        if (zzbha.zzaP(super.getContext()).checkCallingOrSelfPermission(str) == 0) {
            return true;
        }
        super.zzwF().zzyC().zzj("Permission not granted", str);
        return false;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzeA(String str) {
        return "1".equals(super.zzwC().zzM(str, "measurement.upload.blacklist_internal"));
    }

    /* access modifiers changed from: package-private */
    public final boolean zzeB(String str) {
        return "1".equals(super.zzwC().zzM(str, "measurement.upload.blacklist_public"));
    }

    public final int zzep(String str) {
        if (!zzP("event", str)) {
            return 2;
        }
        if (!zza("event", AppMeasurement.Event.zzbof, str)) {
            return 13;
        }
        return zzb("event", zzcem.zzxh(), str) ? 0 : 2;
    }

    public final int zzeq(String str) {
        if (!zzQ("event", str)) {
            return 2;
        }
        if (!zza("event", AppMeasurement.Event.zzbof, str)) {
            return 13;
        }
        return zzb("event", zzcem.zzxh(), str) ? 0 : 2;
    }

    public final int zzer(String str) {
        if (!zzP("user property", str)) {
            return 6;
        }
        if (!zza("user property", AppMeasurement.UserProperty.zzbom, str)) {
            return 15;
        }
        return zzb("user property", zzcem.zzxi(), str) ? 0 : 6;
    }

    public final int zzes(String str) {
        if (!zzQ("user property", str)) {
            return 6;
        }
        if (!zza("user property", AppMeasurement.UserProperty.zzbom, str)) {
            return 15;
        }
        return zzb("user property", zzcem.zzxi(), str) ? 0 : 6;
    }

    public final boolean zzev(String str) {
        if (TextUtils.isEmpty(str)) {
            super.zzwF().zzyx().log("Missing google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI");
            return false;
        }
        zzbo.zzu(str);
        if (str.matches("^1:\\d+:android:[a-f0-9]+$")) {
            return true;
        }
        super.zzwF().zzyx().zzj("Invalid google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI. provided id", str);
        return false;
    }

    public final boolean zzey(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String zzya = super.zzwH().zzya();
        zzcem.zzxE();
        return zzya.equals(str);
    }

    public final boolean zzf(long j, long j2) {
        return j == 0 || j2 <= 0 || Math.abs(super.zzkq().currentTimeMillis() - j) > j2;
    }

    public final /* bridge */ /* synthetic */ void zzjC() {
        super.zzjC();
    }

    /* access modifiers changed from: protected */
    public final void zzjD() {
        SecureRandom secureRandom = new SecureRandom();
        long nextLong = secureRandom.nextLong();
        if (nextLong == 0) {
            nextLong = secureRandom.nextLong();
            if (nextLong == 0) {
                super.zzwF().zzyz().log("Utils falling back to Random for random id");
            }
        }
        this.zzbuF.set(nextLong);
    }

    public final Object zzk(String str, Object obj) {
        if ("_ev".equals(str)) {
            return zza(zzcem.zzxl(), obj, true);
        }
        return zza(zzex(str) ? zzcem.zzxl() : zzcem.zzxk(), obj, false);
    }

    public final /* bridge */ /* synthetic */ zze zzkq() {
        return super.zzkq();
    }

    public final int zzl(String str, Object obj) {
        return "_ldl".equals(str) ? zza("user property referrer", str, zzew(str), obj, false) : zza("user property", str, zzew(str), obj, false) ? 0 : 7;
    }

    public final byte[] zzl(byte[] bArr) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            super.zzwF().zzyx().zzj("Failed to gzip content", e);
            throw e;
        }
    }

    public final Object zzm(String str, Object obj) {
        return "_ldl".equals(str) ? zza(zzew(str), obj, true) : zza(zzew(str), obj, false);
    }

    public final byte[] zzm(byte[] bArr) throws IOException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr2 = new byte[1024];
            while (true) {
                int read = gZIPInputStream.read(bArr2);
                if (read > 0) {
                    byteArrayOutputStream.write(bArr2, 0, read);
                } else {
                    gZIPInputStream.close();
                    byteArrayInputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (IOException e) {
            super.zzwF().zzyx().zzj("Failed to ungzip content", e);
            throw e;
        }
    }

    public final Bundle zzq(@NonNull Uri uri) {
        String str;
        String str2;
        String str3;
        String str4;
        Bundle bundle = null;
        if (uri != null) {
            try {
                if (uri.isHierarchical()) {
                    str4 = uri.getQueryParameter("utm_campaign");
                    str3 = uri.getQueryParameter("utm_source");
                    str2 = uri.getQueryParameter("utm_medium");
                    str = uri.getQueryParameter("gclid");
                } else {
                    str = null;
                    str2 = null;
                    str3 = null;
                    str4 = null;
                }
                if (!TextUtils.isEmpty(str4) || !TextUtils.isEmpty(str3) || !TextUtils.isEmpty(str2) || !TextUtils.isEmpty(str)) {
                    bundle = new Bundle();
                    if (!TextUtils.isEmpty(str4)) {
                        bundle.putString(FirebaseAnalytics.Param.CAMPAIGN, str4);
                    }
                    if (!TextUtils.isEmpty(str3)) {
                        bundle.putString("source", str3);
                    }
                    if (!TextUtils.isEmpty(str2)) {
                        bundle.putString(FirebaseAnalytics.Param.MEDIUM, str2);
                    }
                    if (!TextUtils.isEmpty(str)) {
                        bundle.putString("gclid", str);
                    }
                    String queryParameter = uri.getQueryParameter("utm_term");
                    if (!TextUtils.isEmpty(queryParameter)) {
                        bundle.putString(FirebaseAnalytics.Param.TERM, queryParameter);
                    }
                    String queryParameter2 = uri.getQueryParameter("utm_content");
                    if (!TextUtils.isEmpty(queryParameter2)) {
                        bundle.putString("content", queryParameter2);
                    }
                    String queryParameter3 = uri.getQueryParameter(FirebaseAnalytics.Param.ACLID);
                    if (!TextUtils.isEmpty(queryParameter3)) {
                        bundle.putString(FirebaseAnalytics.Param.ACLID, queryParameter3);
                    }
                    String queryParameter4 = uri.getQueryParameter(FirebaseAnalytics.Param.CP1);
                    if (!TextUtils.isEmpty(queryParameter4)) {
                        bundle.putString(FirebaseAnalytics.Param.CP1, queryParameter4);
                    }
                    String queryParameter5 = uri.getQueryParameter("anid");
                    if (!TextUtils.isEmpty(queryParameter5)) {
                        bundle.putString("anid", queryParameter5);
                    }
                }
            } catch (UnsupportedOperationException e) {
                super.zzwF().zzyz().zzj("Install referrer url isn't a hierarchical URI", e);
            }
        }
        return bundle;
    }

    public final /* bridge */ /* synthetic */ zzcfj zzwA() {
        return super.zzwA();
    }

    public final /* bridge */ /* synthetic */ zzcjl zzwB() {
        return super.zzwB();
    }

    public final /* bridge */ /* synthetic */ zzcgf zzwC() {
        return super.zzwC();
    }

    public final /* bridge */ /* synthetic */ zzcja zzwD() {
        return super.zzwD();
    }

    public final /* bridge */ /* synthetic */ zzcgg zzwE() {
        return super.zzwE();
    }

    public final /* bridge */ /* synthetic */ zzcfl zzwF() {
        return super.zzwF();
    }

    public final /* bridge */ /* synthetic */ zzcfw zzwG() {
        return super.zzwG();
    }

    public final /* bridge */ /* synthetic */ zzcem zzwH() {
        return super.zzwH();
    }

    public final /* bridge */ /* synthetic */ void zzwo() {
        super.zzwo();
    }

    public final /* bridge */ /* synthetic */ void zzwp() {
        super.zzwp();
    }

    public final /* bridge */ /* synthetic */ void zzwq() {
        super.zzwq();
    }

    public final /* bridge */ /* synthetic */ zzcec zzwr() {
        return super.zzwr();
    }

    public final /* bridge */ /* synthetic */ zzcej zzws() {
        return super.zzws();
    }

    public final /* bridge */ /* synthetic */ zzchl zzwt() {
        return super.zzwt();
    }

    public final /* bridge */ /* synthetic */ zzcfg zzwu() {
        return super.zzwu();
    }

    public final /* bridge */ /* synthetic */ zzcet zzwv() {
        return super.zzwv();
    }

    public final /* bridge */ /* synthetic */ zzcid zzww() {
        return super.zzww();
    }

    public final /* bridge */ /* synthetic */ zzchz zzwx() {
        return super.zzwx();
    }

    public final /* bridge */ /* synthetic */ zzcfh zzwy() {
        return super.zzwy();
    }

    public final /* bridge */ /* synthetic */ zzcen zzwz() {
        return super.zzwz();
    }

    public final long zzzs() {
        long andIncrement;
        if (this.zzbuF.get() == 0) {
            synchronized (this.zzbuF) {
                long nextLong = new Random(System.nanoTime() ^ super.zzkq().currentTimeMillis()).nextLong();
                int i = this.zzbuG + 1;
                this.zzbuG = i;
                andIncrement = nextLong + ((long) i);
            }
        } else {
            synchronized (this.zzbuF) {
                this.zzbuF.compareAndSet(-1, 1);
                andIncrement = this.zzbuF.getAndIncrement();
            }
        }
        return andIncrement;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final SecureRandom zzzt() {
        super.zzjC();
        if (this.zzbuE == null) {
            this.zzbuE = new SecureRandom();
        }
        return this.zzbuE;
    }
}
