package com.tencent.midas.oversea.network.http;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.qqgamemi.util.TimeUtils;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.conn.util.InetAddressUtils;

public class APIPList {
    private static APIPList c = null;
    HashMap<String, APIPState> a = new HashMap<>();
    HashMap<String, APIPState> b = new HashMap<>();
    private APIPDatabase d;
    private String e;

    private APIPList(Context context) {
        HashMap hashMap = new HashMap();
        this.e = APAppDataInterface.singleton().getEnv();
        this.d = new APIPDatabase(context.getApplicationContext());
        if (this.d.getCount(this.e, APIPDatabase.DB_IPTABLE) == 0) {
            a(hashMap);
        } else {
            hashMap.clear();
            this.d.getIPStateMap(hashMap, this.e, APIPDatabase.DB_IPTABLE);
        }
        this.b.clear();
        this.a.clear();
        for (Map.Entry entry : hashMap.entrySet()) {
            String str = (String) entry.getKey();
            APIPState aPIPState = (APIPState) entry.getValue();
            if (aPIPState.seqFailTimes >= 3) {
                this.b.put(str, aPIPState);
            } else {
                this.a.put(str, aPIPState);
            }
        }
    }

    private void a() {
        APNetworkManager.getInstance().getIpList(new e(this));
    }

    private void a(HashMap<String, APIPState> hashMap) {
        hashMap.clear();
        for (int i = 0; i < APNetCfg.getIPList().length; i++) {
            APIPState aPIPState = new APIPState();
            aPIPState.ip = APNetCfg.getIPList()[i].split(",")[0];
            aPIPState.province = APNetCfg.getIPList()[i].split(",")[1];
            aPIPState.city = APNetCfg.getIPList()[i].split(",")[2];
            aPIPState.ipEnv = this.e;
            this.d.insertIP(aPIPState, APIPDatabase.DB_IPTABLE);
            hashMap.put(aPIPState.ip, aPIPState);
        }
    }

    public static APIPList getInstance() {
        if (c != null) {
            return c;
        }
        return null;
    }

    public static APIPList getInstance(Context context) {
        if (c == null) {
            c = new APIPList(context.getApplicationContext());
        }
        return c;
    }

    public static void initIPList(Context context) {
        if (context != null) {
            c = null;
            c = new APIPList(context.getApplicationContext());
        }
    }

    public static void release() {
        c = null;
    }

    public boolean checkIpInList(String str) {
        for (Map.Entry<String, APIPState> value : this.b.entrySet()) {
            if (((APIPState) value.getValue()).ip.equals(str)) {
                return true;
            }
        }
        for (Map.Entry<String, APIPState> value2 : this.a.entrySet()) {
            if (((APIPState) value2.getValue()).ip.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public void close() {
        if (this.d != null) {
            this.d.closeDB();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getRandomIP(java.lang.String r7) {
        /*
            r6 = this;
            java.lang.String r2 = ""
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ Exception -> 0x0080 }
            r3.<init>()     // Catch:{ Exception -> 0x0080 }
            java.util.HashMap<java.lang.String, com.tencent.midas.oversea.network.http.APIPState> r0 = r6.a     // Catch:{ Exception -> 0x0080 }
            java.util.Set r0 = r0.entrySet()     // Catch:{ Exception -> 0x0080 }
            java.util.Iterator r4 = r0.iterator()     // Catch:{ Exception -> 0x0080 }
        L_0x0011:
            boolean r0 = r4.hasNext()     // Catch:{ Exception -> 0x0080 }
            if (r0 == 0) goto L_0x0034
            java.lang.Object r0 = r4.next()     // Catch:{ Exception -> 0x0080 }
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ Exception -> 0x0080 }
            java.lang.Object r1 = r0.getKey()     // Catch:{ Exception -> 0x0080 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Exception -> 0x0080 }
            boolean r1 = r1.equals(r7)     // Catch:{ Exception -> 0x0080 }
            if (r1 != 0) goto L_0x0088
            java.lang.Object r0 = r0.getKey()     // Catch:{ Exception -> 0x0080 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x0080 }
            r3.add(r0)     // Catch:{ Exception -> 0x0083 }
        L_0x0032:
            r2 = r0
            goto L_0x0011
        L_0x0034:
            int r0 = r3.size()     // Catch:{ Exception -> 0x0080 }
            double r4 = java.lang.Math.random()     // Catch:{ Exception -> 0x0080 }
            double r0 = (double) r0     // Catch:{ Exception -> 0x0080 }
            double r0 = r0 * r4
            int r0 = (int) r0     // Catch:{ Exception -> 0x0080 }
            java.lang.String r1 = "APIPList"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0080 }
            r4.<init>()     // Catch:{ Exception -> 0x0080 }
            java.lang.String r5 = "random = "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0080 }
            java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Exception -> 0x0080 }
            java.lang.String r5 = " ipArrayList.size = "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0080 }
            int r5 = r3.size()     // Catch:{ Exception -> 0x0080 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0080 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0080 }
            com.tencent.midas.oversea.comm.APLog.i(r1, r4)     // Catch:{ Exception -> 0x0080 }
            int r1 = r3.size()     // Catch:{ Exception -> 0x0080 }
            if (r1 <= 0) goto L_0x0086
            java.lang.Object r0 = r3.get(r0)     // Catch:{ Exception -> 0x0080 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x0080 }
        L_0x0071:
            if (r0 == 0) goto L_0x007b
            java.lang.String r1 = ""
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x007f
        L_0x007b:
            java.lang.String r0 = com.tencent.midas.oversea.network.http.APNetCfg.getDomin()
        L_0x007f:
            return r0
        L_0x0080:
            r0 = move-exception
        L_0x0081:
            r0 = r2
            goto L_0x0071
        L_0x0083:
            r1 = move-exception
            r2 = r0
            goto L_0x0081
        L_0x0086:
            r0 = r2
            goto L_0x0071
        L_0x0088:
            r0 = r2
            goto L_0x0032
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.midas.oversea.network.http.APIPList.getRandomIP(java.lang.String):java.lang.String");
    }

    public void init() {
        APAppDataInterface.singleton().setSysServerIP(getRandomIP((String) null));
        if (this.b.size() >= this.a.size() || needUpdateIp()) {
            a();
        }
    }

    public boolean isIPAddress(String str) {
        return str != null && (InetAddressUtils.isIPv4Address(str) || InetAddressUtils.isIPv6Address(str));
    }

    public boolean needUpdateIp() {
        if (APAppDataInterface.singleton().isNewCGI()) {
            return false;
        }
        APMidasPayAPI.singleton();
        Context context = APMidasPayAPI.applicationContext;
        if (context == null) {
            return false;
        }
        return System.currentTimeMillis() - context.getSharedPreferences(APGlobalInfo.SharedPreferencesTag, 0).getLong("updateIPPreTime", 0) > TimeUtils.MILLIS_IN_DAY;
    }

    public void saveUpdateTime() {
        APMidasPayAPI.singleton();
        Context context = APMidasPayAPI.applicationContext;
        if (context != null) {
            SharedPreferences.Editor edit = context.getSharedPreferences(APGlobalInfo.SharedPreferencesTag, 0).edit();
            edit.putLong("updateIPPreTime", System.currentTimeMillis());
            edit.commit();
        }
    }

    public void setIPState(String str, boolean z) {
        APIPState aPIPState = this.a.get(str);
        if (aPIPState != null) {
            aPIPState.accessTimes++;
            if (z) {
                aPIPState.succTimes++;
                aPIPState.seqFailTimes = 0;
            } else {
                aPIPState.failTimes++;
                aPIPState.seqFailTimes++;
                if (aPIPState.seqFailTimes >= 3) {
                    this.a.remove(str);
                    this.b.put(str, aPIPState);
                }
            }
            updateToDB();
        }
    }

    public void updateIPList(HashMap<String, APIPState> hashMap) {
        this.a.clear();
        this.b.clear();
        this.d.clearAll(APIPDatabase.DB_IPTABLE);
        for (Map.Entry next : hashMap.entrySet()) {
            this.a.put(next.getKey(), next.getValue());
            APIPState aPIPState = (APIPState) next.getValue();
            aPIPState.ipEnv = this.e;
            this.d.insertIP(aPIPState, APIPDatabase.DB_IPTABLE);
        }
    }

    public void updateToDB() {
        for (Map.Entry<String, APIPState> value : this.a.entrySet()) {
            this.d.updateIP((APIPState) value.getValue(), APIPDatabase.DB_IPTABLE);
        }
        for (Map.Entry<String, APIPState> value2 : this.b.entrySet()) {
            this.d.updateIP((APIPState) value2.getValue(), APIPDatabase.DB_IPTABLE);
        }
    }
}
