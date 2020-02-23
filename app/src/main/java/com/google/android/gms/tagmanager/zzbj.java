package com.google.android.gms.tagmanager;

import java.util.Map;

final class zzbj extends zzcf {
    zzbj() {
    }

    public final void zze(String str, Map map) {
        CustomTagProvider customTagProvider;
        if (!zzbf.zzbEC.containsKey(str)) {
            customTagProvider = (CustomTagProvider) zzbf.zzb(str, CustomTagProvider.class);
            zzbf.zzbEC.put(str, customTagProvider);
        } else {
            customTagProvider = (CustomTagProvider) zzbf.zzbEC.get(str);
        }
        if (customTagProvider != null) {
            customTagProvider.execute(map);
        }
    }

    public final String zzf(String str, Map map) {
        CustomVariableProvider customVariableProvider;
        if (!zzbf.zzbED.containsKey(str)) {
            customVariableProvider = (CustomVariableProvider) zzbf.zzb(str, CustomVariableProvider.class);
            zzbf.zzbED.put(str, customVariableProvider);
        } else {
            customVariableProvider = (CustomVariableProvider) zzbf.zzbED.get(str);
        }
        if (customVariableProvider != null) {
            return customVariableProvider.getValue(map);
        }
        return null;
    }
}
