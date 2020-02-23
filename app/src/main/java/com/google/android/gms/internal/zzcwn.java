package com.google.android.gms.internal;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Pair;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.tagmanager.zzce;
import com.google.android.gms.tagmanager.zzcn;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class zzcwn {
    private static final Pattern zzbJe = Pattern.compile("(gtm-[a-z0-9]{1,10})\\.json", 2);
    private static volatile zzcwn zzbJf;
    private static zzc zzbJo = new zzcwo();
    /* access modifiers changed from: private */
    public final Context mContext;
    private String zzbDw;
    /* access modifiers changed from: private */
    public final ExecutorService zzbHL;
    private final ScheduledExecutorService zzbHM;
    /* access modifiers changed from: private */
    public final zzcn zzbHN;
    private final zzce zzbHW;
    /* access modifiers changed from: private */
    public final zzcxi zzbJg;
    /* access modifiers changed from: private */
    public final zzcvs zzbJh;
    private final zza zzbJi;
    /* access modifiers changed from: private */
    public final Object zzbJj = new Object();
    private String zzbJk;
    /* access modifiers changed from: private */
    public int zzbJl = 1;
    /* access modifiers changed from: private */
    public final Queue<Runnable> zzbJm = new LinkedList();
    private volatile boolean zzbJn = false;
    /* access modifiers changed from: private */
    public volatile boolean zzuH = false;

    public static class zza {
        private final Context mContext;

        public zza(Context context) {
            this.mContext = context;
        }

        public final String[] zzCC() throws IOException {
            return this.mContext.getAssets().list("");
        }

        public final String[] zzfL(String str) throws IOException {
            return this.mContext.getAssets().list(str);
        }
    }

    class zzb extends zzcve {
        private zzb() {
        }

        /* synthetic */ zzb(zzcwn zzcwn, zzcwo zzcwo) {
            this();
        }

        public final void zza(boolean z, String str) throws RemoteException {
            zzcwn.this.zzbHL.execute(new zzcwz(this, z, str));
        }
    }

    public interface zzc {
        zzcwn zzb(Context context, zzcn zzcn, zzce zzce);
    }

    zzcwn(Context context, zzcn zzcn, zzce zzce, zzcxi zzcxi, ExecutorService executorService, ScheduledExecutorService scheduledExecutorService, zzcvs zzcvs, zza zza2) {
        zzbo.zzu(context);
        zzbo.zzu(zzcn);
        this.mContext = context;
        this.zzbHN = zzcn;
        this.zzbHW = zzce;
        this.zzbJg = zzcxi;
        this.zzbHL = executorService;
        this.zzbHM = scheduledExecutorService;
        this.zzbJh = zzcvs;
        this.zzbJi = zza2;
    }

    public static zzcwn zza(Context context, zzcn zzcn, zzce zzce) {
        zzbo.zzu(context);
        zzbo.zzu(context);
        zzcwn zzcwn = zzbJf;
        if (zzcwn == null) {
            synchronized (zzcwn.class) {
                zzcwn = zzbJf;
                if (zzcwn == null) {
                    zzcwn = zzbJo.zzb(context, zzcn, zzce);
                    zzbJf = zzcwn;
                }
            }
        }
        return zzcwn;
    }

    private static boolean zza(Context context, Class<? extends Service> cls) {
        try {
            ServiceInfo serviceInfo = context.getPackageManager().getServiceInfo(new ComponentName(context, cls), 4);
            return serviceInfo != null && serviceInfo.enabled;
        } catch (PackageManager.NameNotFoundException e) {
        }
    }

    /* access modifiers changed from: private */
    public final Pair<String, String> zzf(String[] strArr) {
        zzcvk.v("Looking up container asset.");
        if (this.zzbDw != null && this.zzbJk != null) {
            return Pair.create(this.zzbDw, this.zzbJk);
        }
        try {
            String[] zzfL = this.zzbJi.zzfL("containers");
            boolean z = false;
            for (int i = 0; i < zzfL.length; i++) {
                Matcher matcher = zzbJe.matcher(zzfL[i]);
                if (!matcher.matches()) {
                    zzcvk.zzaT(String.format("Ignoring container asset %s (does not match %s)", new Object[]{zzfL[i], zzbJe.pattern()}));
                } else if (!z) {
                    this.zzbDw = matcher.group(1);
                    String valueOf = String.valueOf("containers");
                    String valueOf2 = String.valueOf(File.separator);
                    String valueOf3 = String.valueOf(zzfL[i]);
                    this.zzbJk = new StringBuilder(String.valueOf(valueOf).length() + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length()).append(valueOf).append(valueOf2).append(valueOf3).toString();
                    String valueOf4 = String.valueOf(this.zzbDw);
                    zzcvk.v(valueOf4.length() != 0 ? "Asset found for container ".concat(valueOf4) : new String("Asset found for container "));
                    z = true;
                } else {
                    String valueOf5 = String.valueOf(zzfL[i]);
                    zzcvk.zzaT(valueOf5.length() != 0 ? "Extra container asset found, will not be loaded: ".concat(valueOf5) : new String("Extra container asset found, will not be loaded: "));
                }
            }
            if (!z) {
                zzcvk.zzaT("No container asset found in /assets/containers. Checking top level /assets directory for container assets.");
                try {
                    String[] zzCC = this.zzbJi.zzCC();
                    for (int i2 = 0; i2 < zzCC.length; i2++) {
                        Matcher matcher2 = zzbJe.matcher(zzCC[i2]);
                        if (matcher2.matches()) {
                            if (!z) {
                                this.zzbDw = matcher2.group(1);
                                this.zzbJk = zzCC[i2];
                                String valueOf6 = String.valueOf(this.zzbDw);
                                zzcvk.v(valueOf6.length() != 0 ? "Asset found for container ".concat(valueOf6) : new String("Asset found for container "));
                                zzcvk.zzaT("Loading container assets from top level /assets directory. Please move the container asset to /assets/containers");
                                z = true;
                            } else {
                                String valueOf7 = String.valueOf(zzCC[i2]);
                                zzcvk.zzaT(valueOf7.length() != 0 ? "Extra container asset found, will not be loaded: ".concat(valueOf7) : new String("Extra container asset found, will not be loaded: "));
                            }
                        }
                    }
                } catch (IOException e) {
                    zzcvk.zzb("Failed to enumerate assets.", e);
                    return Pair.create((Object) null, (Object) null);
                }
            }
            return Pair.create(this.zzbDw, this.zzbJk);
        } catch (IOException e2) {
            zzcvk.zzb(String.format("Failed to enumerate assets in folder %s", new Object[]{"containers"}), e2);
            return Pair.create((Object) null, (Object) null);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00d2, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00d3, code lost:
        com.google.android.gms.internal.zzcup.zza("Error communicating with measurement proxy: ", r0, r8.mContext);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void initialize() {
        /*
            r8 = this;
            java.lang.String r0 = "Initializing Tag Manager."
            com.google.android.gms.internal.zzcvk.v(r0)
            long r4 = java.lang.System.currentTimeMillis()
            java.lang.Object r3 = r8.zzbJj
            monitor-enter(r3)
            boolean r0 = r8.zzuH     // Catch:{ all -> 0x0026 }
            if (r0 == 0) goto L_0x0012
            monitor-exit(r3)     // Catch:{ all -> 0x0026 }
        L_0x0011:
            return
        L_0x0012:
            android.content.Context r0 = r8.mContext     // Catch:{ all -> 0x00c4 }
            java.lang.Class<com.google.android.gms.tagmanager.TagManagerService> r1 = com.google.android.gms.tagmanager.TagManagerService.class
            boolean r0 = zza((android.content.Context) r0, (java.lang.Class<? extends android.app.Service>) r1)     // Catch:{ all -> 0x00c4 }
            if (r0 != 0) goto L_0x0029
            java.lang.String r0 = "Tag Manager fails to initialize (TagManagerService not enabled in the manifest)"
            com.google.android.gms.internal.zzcvk.zzaT(r0)     // Catch:{ all -> 0x00c4 }
            r0 = 1
            r8.zzuH = r0     // Catch:{ all -> 0x0026 }
            monitor-exit(r3)     // Catch:{ all -> 0x0026 }
            goto L_0x0011
        L_0x0026:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0026 }
            throw r0
        L_0x0029:
            r0 = 0
            android.util.Pair r1 = r8.zzf((java.lang.String[]) r0)     // Catch:{ all -> 0x00c4 }
            java.lang.Object r0 = r1.first     // Catch:{ all -> 0x00c4 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x00c4 }
            java.lang.Object r1 = r1.second     // Catch:{ all -> 0x00c4 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x00c4 }
            if (r0 == 0) goto L_0x00db
            if (r1 == 0) goto L_0x00db
            java.lang.String r6 = "Loading container "
            java.lang.String r2 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x00c4 }
            int r7 = r2.length()     // Catch:{ all -> 0x00c4 }
            if (r7 == 0) goto L_0x00be
            java.lang.String r2 = r6.concat(r2)     // Catch:{ all -> 0x00c4 }
        L_0x004a:
            com.google.android.gms.internal.zzcvk.zzaS(r2)     // Catch:{ all -> 0x00c4 }
            java.util.concurrent.ExecutorService r2 = r8.zzbHL     // Catch:{ all -> 0x00c4 }
            com.google.android.gms.internal.zzcwt r6 = new com.google.android.gms.internal.zzcwt     // Catch:{ all -> 0x00c4 }
            r7 = 0
            r6.<init>(r8, r0, r1, r7)     // Catch:{ all -> 0x00c4 }
            r2.execute(r6)     // Catch:{ all -> 0x00c4 }
            java.util.concurrent.ScheduledExecutorService r0 = r8.zzbHM     // Catch:{ all -> 0x00c4 }
            com.google.android.gms.internal.zzcwu r1 = new com.google.android.gms.internal.zzcwu     // Catch:{ all -> 0x00c4 }
            r1.<init>(r8)     // Catch:{ all -> 0x00c4 }
            r6 = 5000(0x1388, double:2.4703E-320)
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x00c4 }
            r0.schedule(r1, r6, r2)     // Catch:{ all -> 0x00c4 }
            boolean r0 = r8.zzbJn     // Catch:{ all -> 0x00c4 }
            if (r0 != 0) goto L_0x0095
            java.lang.String r0 = "Installing Tag Manager event handler."
            com.google.android.gms.internal.zzcvk.zzaS(r0)     // Catch:{ all -> 0x00c4 }
            r0 = 1
            r8.zzbJn = r0     // Catch:{ all -> 0x00c4 }
            com.google.android.gms.tagmanager.zzcn r0 = r8.zzbHN     // Catch:{ RemoteException -> 0x00c9 }
            com.google.android.gms.internal.zzcwp r1 = new com.google.android.gms.internal.zzcwp     // Catch:{ RemoteException -> 0x00c9 }
            r1.<init>(r8)     // Catch:{ RemoteException -> 0x00c9 }
            r0.zza((com.google.android.gms.tagmanager.zzck) r1)     // Catch:{ RemoteException -> 0x00c9 }
        L_0x007c:
            com.google.android.gms.tagmanager.zzcn r0 = r8.zzbHN     // Catch:{ RemoteException -> 0x00d2 }
            com.google.android.gms.internal.zzcwr r1 = new com.google.android.gms.internal.zzcwr     // Catch:{ RemoteException -> 0x00d2 }
            r1.<init>(r8)     // Catch:{ RemoteException -> 0x00d2 }
            r0.zza((com.google.android.gms.tagmanager.zzch) r1)     // Catch:{ RemoteException -> 0x00d2 }
        L_0x0086:
            android.content.Context r0 = r8.mContext     // Catch:{ all -> 0x00c4 }
            com.google.android.gms.internal.zzcww r1 = new com.google.android.gms.internal.zzcww     // Catch:{ all -> 0x00c4 }
            r1.<init>(r8)     // Catch:{ all -> 0x00c4 }
            r0.registerComponentCallbacks(r1)     // Catch:{ all -> 0x00c4 }
            java.lang.String r0 = "Tag Manager event handler installed."
            com.google.android.gms.internal.zzcvk.zzaS(r0)     // Catch:{ all -> 0x00c4 }
        L_0x0095:
            r0 = 1
            r8.zzuH = r0     // Catch:{ all -> 0x0026 }
            monitor-exit(r3)     // Catch:{ all -> 0x0026 }
            long r0 = java.lang.System.currentTimeMillis()
            long r0 = r0 - r4
            r2 = 53
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r2)
            java.lang.String r2 = "Tag Manager initilization took "
            java.lang.StringBuilder r2 = r3.append(r2)
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r1 = "ms"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.google.android.gms.internal.zzcvk.zzaS(r0)
            goto L_0x0011
        L_0x00be:
            java.lang.String r2 = new java.lang.String     // Catch:{ all -> 0x00c4 }
            r2.<init>(r6)     // Catch:{ all -> 0x00c4 }
            goto L_0x004a
        L_0x00c4:
            r0 = move-exception
            r1 = 1
            r8.zzuH = r1     // Catch:{ all -> 0x0026 }
            throw r0     // Catch:{ all -> 0x0026 }
        L_0x00c9:
            r0 = move-exception
            java.lang.String r1 = "Error communicating with measurement proxy: "
            android.content.Context r2 = r8.mContext     // Catch:{ all -> 0x00c4 }
            com.google.android.gms.internal.zzcup.zza(r1, r0, r2)     // Catch:{ all -> 0x00c4 }
            goto L_0x007c
        L_0x00d2:
            r0 = move-exception
            java.lang.String r1 = "Error communicating with measurement proxy: "
            android.content.Context r2 = r8.mContext     // Catch:{ all -> 0x00c4 }
            com.google.android.gms.internal.zzcup.zza(r1, r0, r2)     // Catch:{ all -> 0x00c4 }
            goto L_0x0086
        L_0x00db:
            java.lang.String r0 = "Tag Manager's event handler WILL NOT be installed (no container loaded)"
            com.google.android.gms.internal.zzcvk.zzaT(r0)     // Catch:{ all -> 0x00c4 }
            goto L_0x0095
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcwn.initialize():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00d2, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00d3, code lost:
        com.google.android.gms.internal.zzcup.zza("Error communicating with measurement proxy: ", r0, r8.mContext);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zze(java.lang.String[] r9) {
        /*
            r8 = this;
            java.lang.String r0 = "Initializing Tag Manager."
            com.google.android.gms.internal.zzcvk.v(r0)
            long r4 = java.lang.System.currentTimeMillis()
            java.lang.Object r3 = r8.zzbJj
            monitor-enter(r3)
            boolean r0 = r8.zzuH     // Catch:{ all -> 0x0026 }
            if (r0 == 0) goto L_0x0012
            monitor-exit(r3)     // Catch:{ all -> 0x0026 }
        L_0x0011:
            return
        L_0x0012:
            android.content.Context r0 = r8.mContext     // Catch:{ all -> 0x00c4 }
            java.lang.Class<com.google.android.gms.tagmanager.TagManagerService> r1 = com.google.android.gms.tagmanager.TagManagerService.class
            boolean r0 = zza((android.content.Context) r0, (java.lang.Class<? extends android.app.Service>) r1)     // Catch:{ all -> 0x00c4 }
            if (r0 != 0) goto L_0x0029
            java.lang.String r0 = "Tag Manager fails to initialize (TagManagerService not enabled in the manifest)"
            com.google.android.gms.internal.zzcvk.zzaT(r0)     // Catch:{ all -> 0x00c4 }
            r0 = 1
            r8.zzuH = r0     // Catch:{ all -> 0x0026 }
            monitor-exit(r3)     // Catch:{ all -> 0x0026 }
            goto L_0x0011
        L_0x0026:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0026 }
            throw r0
        L_0x0029:
            r0 = 0
            android.util.Pair r1 = r8.zzf((java.lang.String[]) r0)     // Catch:{ all -> 0x00c4 }
            java.lang.Object r0 = r1.first     // Catch:{ all -> 0x00c4 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x00c4 }
            java.lang.Object r1 = r1.second     // Catch:{ all -> 0x00c4 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x00c4 }
            if (r0 == 0) goto L_0x00db
            if (r1 == 0) goto L_0x00db
            java.lang.String r6 = "Loading container "
            java.lang.String r2 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x00c4 }
            int r7 = r2.length()     // Catch:{ all -> 0x00c4 }
            if (r7 == 0) goto L_0x00be
            java.lang.String r2 = r6.concat(r2)     // Catch:{ all -> 0x00c4 }
        L_0x004a:
            com.google.android.gms.internal.zzcvk.zzaS(r2)     // Catch:{ all -> 0x00c4 }
            java.util.concurrent.ExecutorService r2 = r8.zzbHL     // Catch:{ all -> 0x00c4 }
            com.google.android.gms.internal.zzcwt r6 = new com.google.android.gms.internal.zzcwt     // Catch:{ all -> 0x00c4 }
            r7 = 0
            r6.<init>(r8, r0, r1, r7)     // Catch:{ all -> 0x00c4 }
            r2.execute(r6)     // Catch:{ all -> 0x00c4 }
            java.util.concurrent.ScheduledExecutorService r0 = r8.zzbHM     // Catch:{ all -> 0x00c4 }
            com.google.android.gms.internal.zzcwu r1 = new com.google.android.gms.internal.zzcwu     // Catch:{ all -> 0x00c4 }
            r1.<init>(r8)     // Catch:{ all -> 0x00c4 }
            r6 = 5000(0x1388, double:2.4703E-320)
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x00c4 }
            r0.schedule(r1, r6, r2)     // Catch:{ all -> 0x00c4 }
            boolean r0 = r8.zzbJn     // Catch:{ all -> 0x00c4 }
            if (r0 != 0) goto L_0x0095
            java.lang.String r0 = "Installing Tag Manager event handler."
            com.google.android.gms.internal.zzcvk.zzaS(r0)     // Catch:{ all -> 0x00c4 }
            r0 = 1
            r8.zzbJn = r0     // Catch:{ all -> 0x00c4 }
            com.google.android.gms.tagmanager.zzcn r0 = r8.zzbHN     // Catch:{ RemoteException -> 0x00c9 }
            com.google.android.gms.internal.zzcwp r1 = new com.google.android.gms.internal.zzcwp     // Catch:{ RemoteException -> 0x00c9 }
            r1.<init>(r8)     // Catch:{ RemoteException -> 0x00c9 }
            r0.zza((com.google.android.gms.tagmanager.zzck) r1)     // Catch:{ RemoteException -> 0x00c9 }
        L_0x007c:
            com.google.android.gms.tagmanager.zzcn r0 = r8.zzbHN     // Catch:{ RemoteException -> 0x00d2 }
            com.google.android.gms.internal.zzcwr r1 = new com.google.android.gms.internal.zzcwr     // Catch:{ RemoteException -> 0x00d2 }
            r1.<init>(r8)     // Catch:{ RemoteException -> 0x00d2 }
            r0.zza((com.google.android.gms.tagmanager.zzch) r1)     // Catch:{ RemoteException -> 0x00d2 }
        L_0x0086:
            android.content.Context r0 = r8.mContext     // Catch:{ all -> 0x00c4 }
            com.google.android.gms.internal.zzcww r1 = new com.google.android.gms.internal.zzcww     // Catch:{ all -> 0x00c4 }
            r1.<init>(r8)     // Catch:{ all -> 0x00c4 }
            r0.registerComponentCallbacks(r1)     // Catch:{ all -> 0x00c4 }
            java.lang.String r0 = "Tag Manager event handler installed."
            com.google.android.gms.internal.zzcvk.zzaS(r0)     // Catch:{ all -> 0x00c4 }
        L_0x0095:
            r0 = 1
            r8.zzuH = r0     // Catch:{ all -> 0x0026 }
            monitor-exit(r3)     // Catch:{ all -> 0x0026 }
            long r0 = java.lang.System.currentTimeMillis()
            long r0 = r0 - r4
            r2 = 53
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r2)
            java.lang.String r2 = "Tag Manager initilization took "
            java.lang.StringBuilder r2 = r3.append(r2)
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r1 = "ms"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.google.android.gms.internal.zzcvk.zzaS(r0)
            goto L_0x0011
        L_0x00be:
            java.lang.String r2 = new java.lang.String     // Catch:{ all -> 0x00c4 }
            r2.<init>(r6)     // Catch:{ all -> 0x00c4 }
            goto L_0x004a
        L_0x00c4:
            r0 = move-exception
            r1 = 1
            r8.zzuH = r1     // Catch:{ all -> 0x0026 }
            throw r0     // Catch:{ all -> 0x0026 }
        L_0x00c9:
            r0 = move-exception
            java.lang.String r1 = "Error communicating with measurement proxy: "
            android.content.Context r2 = r8.mContext     // Catch:{ all -> 0x00c4 }
            com.google.android.gms.internal.zzcup.zza(r1, r0, r2)     // Catch:{ all -> 0x00c4 }
            goto L_0x007c
        L_0x00d2:
            r0 = move-exception
            java.lang.String r1 = "Error communicating with measurement proxy: "
            android.content.Context r2 = r8.mContext     // Catch:{ all -> 0x00c4 }
            com.google.android.gms.internal.zzcup.zza(r1, r0, r2)     // Catch:{ all -> 0x00c4 }
            goto L_0x0086
        L_0x00db:
            java.lang.String r0 = "Tag Manager's event handler WILL NOT be installed (no container loaded)"
            com.google.android.gms.internal.zzcvk.zzaT(r0)     // Catch:{ all -> 0x00c4 }
            goto L_0x0095
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcwn.zze(java.lang.String[]):void");
    }

    /* access modifiers changed from: package-private */
    public final void zzs(Uri uri) {
        this.zzbHL.execute(new zzcwy(this, uri));
    }
}
