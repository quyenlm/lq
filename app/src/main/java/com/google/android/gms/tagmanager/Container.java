package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.eg;
import com.google.android.gms.internal.ek;
import com.google.android.gms.internal.eo;
import com.google.android.gms.internal.zzbn;
import com.google.android.gms.internal.zzbp;
import com.google.android.gms.internal.zzbq;
import com.google.android.gms.tagmanager.zzei;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Container {
    private final Context mContext;
    private Map<String, FunctionCallTagCallback> zzbDA = new HashMap();
    private volatile long zzbDB;
    private volatile String zzbDC = "";
    private final String zzbDw;
    private final DataLayer zzbDx;
    private zzfc zzbDy;
    private Map<String, FunctionCallMacroCallback> zzbDz = new HashMap();

    public interface FunctionCallMacroCallback {
        Object getValue(String str, Map<String, Object> map);
    }

    public interface FunctionCallTagCallback {
        void execute(String str, Map<String, Object> map);
    }

    class zza implements zzan {
        private zza() {
        }

        public final Object zzd(String str, Map<String, Object> map) {
            FunctionCallMacroCallback zzeX = Container.this.zzeX(str);
            if (zzeX == null) {
                return null;
            }
            return zzeX.getValue(str, map);
        }
    }

    class zzb implements zzan {
        private zzb() {
        }

        public final Object zzd(String str, Map<String, Object> map) {
            FunctionCallTagCallback zzeY = Container.this.zzeY(str);
            if (zzeY != null) {
                zzeY.execute(str, map);
            }
            return zzgk.zzCg();
        }
    }

    Container(Context context, DataLayer dataLayer, String str, long j, ek ekVar) {
        this.mContext = context;
        this.zzbDx = dataLayer;
        this.zzbDw = str;
        this.zzbDB = 0;
        zza(ekVar);
    }

    Container(Context context, DataLayer dataLayer, String str, long j, zzbq zzbq) {
        this.mContext = context;
        this.zzbDx = dataLayer;
        this.zzbDw = str;
        this.zzbDB = j;
        zzbn zzbn = zzbq.zzlB;
        if (zzbn == null) {
            throw new NullPointerException();
        }
        try {
            zza(eg.zza(zzbn));
        } catch (eo e) {
            String valueOf = String.valueOf(zzbn);
            String valueOf2 = String.valueOf(e.toString());
            zzdj.e(new StringBuilder(String.valueOf(valueOf).length() + 46 + String.valueOf(valueOf2).length()).append("Not loading resource: ").append(valueOf).append(" because it is invalid: ").append(valueOf2).toString());
        }
        if (zzbq.zzlA != null) {
            zza(zzbq.zzlA);
        }
    }

    private final synchronized zzfc zzAJ() {
        return this.zzbDy;
    }

    private final void zza(ek ekVar) {
        this.zzbDC = ekVar.getVersion();
        String str = this.zzbDC;
        zzei.zzBD().zzBE().equals(zzei.zza.CONTAINER_DEBUG);
        ek ekVar2 = ekVar;
        zza(new zzfc(this.mContext, ekVar2, this.zzbDx, new zza(), new zzb(), new zzdr()));
        if (getBoolean("_gtm.loadEventEnabled")) {
            this.zzbDx.pushEvent("gtm.load", DataLayer.mapOf("gtm.id", this.zzbDw));
        }
    }

    private final synchronized void zza(zzfc zzfc) {
        this.zzbDy = zzfc;
    }

    private final void zza(zzbp[] zzbpArr) {
        ArrayList arrayList = new ArrayList();
        for (zzbp add : zzbpArr) {
            arrayList.add(add);
        }
        zzAJ().zzL(arrayList);
    }

    public boolean getBoolean(String str) {
        zzfc zzAJ = zzAJ();
        if (zzAJ == null) {
            zzdj.e("getBoolean called for closed container.");
            return zzgk.zzCe().booleanValue();
        }
        try {
            return zzgk.zzf(zzAJ.zzfs(str).getObject()).booleanValue();
        } catch (Exception e) {
            String valueOf = String.valueOf(e.getMessage());
            zzdj.e(new StringBuilder(String.valueOf(valueOf).length() + 66).append("Calling getBoolean() threw an exception: ").append(valueOf).append(" Returning default value.").toString());
            return zzgk.zzCe().booleanValue();
        }
    }

    public String getContainerId() {
        return this.zzbDw;
    }

    public double getDouble(String str) {
        zzfc zzAJ = zzAJ();
        if (zzAJ == null) {
            zzdj.e("getDouble called for closed container.");
            return zzgk.zzCd().doubleValue();
        }
        try {
            return zzgk.zze(zzAJ.zzfs(str).getObject()).doubleValue();
        } catch (Exception e) {
            String valueOf = String.valueOf(e.getMessage());
            zzdj.e(new StringBuilder(String.valueOf(valueOf).length() + 65).append("Calling getDouble() threw an exception: ").append(valueOf).append(" Returning default value.").toString());
            return zzgk.zzCd().doubleValue();
        }
    }

    public long getLastRefreshTime() {
        return this.zzbDB;
    }

    public long getLong(String str) {
        zzfc zzAJ = zzAJ();
        if (zzAJ == null) {
            zzdj.e("getLong called for closed container.");
            return zzgk.zzCc().longValue();
        }
        try {
            return zzgk.zzd(zzAJ.zzfs(str).getObject()).longValue();
        } catch (Exception e) {
            String valueOf = String.valueOf(e.getMessage());
            zzdj.e(new StringBuilder(String.valueOf(valueOf).length() + 63).append("Calling getLong() threw an exception: ").append(valueOf).append(" Returning default value.").toString());
            return zzgk.zzCc().longValue();
        }
    }

    public String getString(String str) {
        zzfc zzAJ = zzAJ();
        if (zzAJ == null) {
            zzdj.e("getString called for closed container.");
            return zzgk.zzCg();
        }
        try {
            return zzgk.zzb(zzAJ.zzfs(str).getObject());
        } catch (Exception e) {
            String valueOf = String.valueOf(e.getMessage());
            zzdj.e(new StringBuilder(String.valueOf(valueOf).length() + 65).append("Calling getString() threw an exception: ").append(valueOf).append(" Returning default value.").toString());
            return zzgk.zzCg();
        }
    }

    public boolean isDefault() {
        return getLastRefreshTime() == 0;
    }

    public void registerFunctionCallMacroCallback(String str, FunctionCallMacroCallback functionCallMacroCallback) {
        if (functionCallMacroCallback == null) {
            throw new NullPointerException("Macro handler must be non-null");
        }
        synchronized (this.zzbDz) {
            this.zzbDz.put(str, functionCallMacroCallback);
        }
    }

    public void registerFunctionCallTagCallback(String str, FunctionCallTagCallback functionCallTagCallback) {
        if (functionCallTagCallback == null) {
            throw new NullPointerException("Tag callback must be non-null");
        }
        synchronized (this.zzbDA) {
            this.zzbDA.put(str, functionCallTagCallback);
        }
    }

    /* access modifiers changed from: package-private */
    public final void release() {
        this.zzbDy = null;
    }

    public void unregisterFunctionCallMacroCallback(String str) {
        synchronized (this.zzbDz) {
            this.zzbDz.remove(str);
        }
    }

    public void unregisterFunctionCallTagCallback(String str) {
        synchronized (this.zzbDA) {
            this.zzbDA.remove(str);
        }
    }

    public final String zzAI() {
        return this.zzbDC;
    }

    /* access modifiers changed from: package-private */
    public final FunctionCallMacroCallback zzeX(String str) {
        FunctionCallMacroCallback functionCallMacroCallback;
        synchronized (this.zzbDz) {
            functionCallMacroCallback = this.zzbDz.get(str);
        }
        return functionCallMacroCallback;
    }

    public final FunctionCallTagCallback zzeY(String str) {
        FunctionCallTagCallback functionCallTagCallback;
        synchronized (this.zzbDA) {
            functionCallTagCallback = this.zzbDA.get(str);
        }
        return functionCallTagCallback;
    }

    public final void zzeZ(String str) {
        zzAJ().zzeZ(str);
    }
}
