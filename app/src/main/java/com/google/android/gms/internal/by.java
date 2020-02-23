package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zzf;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.vk.sdk.api.VKApiConst;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class by extends zzcxq {
    private static final String ID = zzbf.UNIVERSAL_ANALYTICS.toString();
    private static final List<String> zzbHw = Arrays.asList(new String[]{ProductAction.ACTION_DETAIL, ProductAction.ACTION_CHECKOUT, "checkout_option", "click", ProductAction.ACTION_ADD, ProductAction.ACTION_REMOVE, ProductAction.ACTION_PURCHASE, ProductAction.ACTION_REFUND});
    private static final Pattern zzbHx = Pattern.compile("dimension(\\d+)");
    private static final Pattern zzbHy = Pattern.compile("metric(\\d+)");
    private static final Set<String> zzbKl = zzf.zza("", "0", "false");
    private static final Map<String, String> zzbKm = zzf.zza("transactionId", "&ti", "transactionAffiliation", "&ta", "transactionTax", "&tt", "transactionShipping", "&ts", "transactionTotal", "&tr", "transactionCurrency", "&cu");
    private static final Map<String, String> zzbKn = zzf.zza("name", "&in", "sku", "&ic", "category", "&iv", FirebaseAnalytics.Param.PRICE, "&ip", FirebaseAnalytics.Param.QUANTITY, "&iq", FirebaseAnalytics.Param.CURRENCY, "&cu");
    private final zzcvy zzbIP;
    private final zzcxj zzbKo;
    private Map<String, Object> zzbKp;

    public by(Context context, zzcvy zzcvy) {
        this(new zzcxj(context), zzcvy);
    }

    private by(zzcxj zzcxj, zzcvy zzcvy) {
        this.zzbIP = zzcvy;
        this.zzbKo = zzcxj;
    }

    private static Double zzM(Object obj) {
        if (obj instanceof String) {
            try {
                return Double.valueOf((String) obj);
            } catch (NumberFormatException e) {
                String valueOf = String.valueOf(e.getMessage());
                throw new RuntimeException(valueOf.length() != 0 ? "Cannot convert the object to Double: ".concat(valueOf) : new String("Cannot convert the object to Double: "));
            }
        } else if (obj instanceof Integer) {
            return Double.valueOf(((Integer) obj).doubleValue());
        } else {
            if (obj instanceof Double) {
                return (Double) obj;
            }
            String valueOf2 = String.valueOf(obj.toString());
            throw new RuntimeException(valueOf2.length() != 0 ? "Cannot convert the object to Double: ".concat(valueOf2) : new String("Cannot convert the object to Double: "));
        }
    }

    private static Integer zzN(Object obj) {
        if (obj instanceof String) {
            try {
                return Integer.valueOf((String) obj);
            } catch (NumberFormatException e) {
                String valueOf = String.valueOf(e.getMessage());
                throw new RuntimeException(valueOf.length() != 0 ? "Cannot convert the object to Integer: ".concat(valueOf) : new String("Cannot convert the object to Integer: "));
            }
        } else if (obj instanceof Double) {
            return Integer.valueOf(((Double) obj).intValue());
        } else {
            if (obj instanceof Integer) {
                return (Integer) obj;
            }
            String valueOf2 = String.valueOf(obj.toString());
            throw new RuntimeException(valueOf2.length() != 0 ? "Cannot convert the object to Integer: ".concat(valueOf2) : new String("Cannot convert the object to Integer: "));
        }
    }

    private final void zza(Tracker tracker, dp<?> dpVar, dp<?> dpVar2, dp<?> dpVar3) {
        String str = (String) this.zzbKp.get("transactionId");
        if (str == null) {
            zzcvk.e("Cannot find transactionId in data layer.");
            return;
        }
        LinkedList<Map> linkedList = new LinkedList<>();
        try {
            Map<String, String> zzi = zzi(dpVar);
            zzi.put("&t", "transaction");
            for (Map.Entry next : (dpVar2 == dv.zzbLu ? zzbKm : zzh(dpVar2)).entrySet()) {
                String str2 = (String) this.zzbKp.get(next.getKey());
                if (str2 != null) {
                    zzi.put((String) next.getValue(), str2);
                }
            }
            linkedList.add(zzi);
            List<Map<String, Object>> zzfN = zzfN("transactionProducts");
            if (zzfN != null) {
                for (Map next2 : zzfN) {
                    if (next2.get("name") == null) {
                        zzcvk.e("Unable to send transaction item hit due to missing 'name' field.");
                        return;
                    }
                    Map<String, String> zzi2 = zzi(dpVar);
                    zzi2.put("&t", "item");
                    zzi2.put("&ti", str);
                    for (Map.Entry next3 : (dpVar3 == dv.zzbLu ? zzbKn : zzh(dpVar3)).entrySet()) {
                        Object obj = next2.get(next3.getKey());
                        if (obj != null) {
                            zzi2.put((String) next3.getValue(), obj.toString());
                        }
                    }
                    linkedList.add(zzi2);
                }
            }
            for (Map send : linkedList) {
                tracker.send(send);
            }
        } catch (IllegalArgumentException e) {
            zzcvk.zzb("Unable to send transaction", e);
        }
    }

    private final List<Map<String, Object>> zzfN(String str) {
        Object obj = this.zzbKp.get(str);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof List)) {
            throw new IllegalArgumentException("transactionProducts should be of type List.");
        }
        List<Map<String, Object>> list = (List) obj;
        for (Map<String, Object> map : list) {
            if (!(map instanceof Map)) {
                throw new IllegalArgumentException("Each element of transactionProducts should be of type Map.");
            }
        }
        return list;
    }

    private static Map<String, String> zzh(dp<?> dpVar) {
        zzbo.zzu(dpVar);
        zzbo.zzaf(dpVar instanceof dz);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Object zzj = ed.zzj(ed.zzk(dpVar));
        zzbo.zzae(zzj instanceof Map);
        for (Map.Entry entry : ((Map) zzj).entrySet()) {
            linkedHashMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return linkedHashMap;
    }

    private static Map<String, String> zzi(dp<?> dpVar) {
        Map<String, String> zzh = zzh(dpVar);
        String str = zzh.get("&aip");
        if (str != null && zzbKl.contains(str.toLowerCase())) {
            zzh.remove("&aip");
        }
        return zzh;
    }

    private static Product zzx(Map<String, Object> map) {
        Product product = new Product();
        Object obj = map.get("id");
        if (obj != null) {
            product.setId(String.valueOf(obj));
        }
        Object obj2 = map.get("name");
        if (obj2 != null) {
            product.setName(String.valueOf(obj2));
        }
        Object obj3 = map.get("brand");
        if (obj3 != null) {
            product.setBrand(String.valueOf(obj3));
        }
        Object obj4 = map.get("category");
        if (obj4 != null) {
            product.setCategory(String.valueOf(obj4));
        }
        Object obj5 = map.get("variant");
        if (obj5 != null) {
            product.setVariant(String.valueOf(obj5));
        }
        Object obj6 = map.get(FirebaseAnalytics.Param.COUPON);
        if (obj6 != null) {
            product.setCouponCode(String.valueOf(obj6));
        }
        Object obj7 = map.get(VKApiConst.POSITION);
        if (obj7 != null) {
            product.setPosition(zzN(obj7).intValue());
        }
        Object obj8 = map.get(FirebaseAnalytics.Param.PRICE);
        if (obj8 != null) {
            product.setPrice(zzM(obj8).doubleValue());
        }
        Object obj9 = map.get(FirebaseAnalytics.Param.QUANTITY);
        if (obj9 != null) {
            product.setQuantity(zzN(obj9).intValue());
        }
        for (String next : map.keySet()) {
            Matcher matcher = zzbHx.matcher(next);
            if (matcher.matches()) {
                try {
                    product.setCustomDimension(Integer.parseInt(matcher.group(1)), String.valueOf(map.get(next)));
                } catch (NumberFormatException e) {
                    String valueOf = String.valueOf(next);
                    zzcvk.zzaT(valueOf.length() != 0 ? "illegal number in custom dimension value: ".concat(valueOf) : new String("illegal number in custom dimension value: "));
                }
            } else {
                Matcher matcher2 = zzbHy.matcher(next);
                if (matcher2.matches()) {
                    try {
                        product.setCustomMetric(Integer.parseInt(matcher2.group(1)), zzN(map.get(next)).intValue());
                    } catch (NumberFormatException e2) {
                        String valueOf2 = String.valueOf(next);
                        zzcvk.zzaT(valueOf2.length() != 0 ? "illegal number in custom metric value: ".concat(valueOf2) : new String("illegal number in custom metric value: "));
                    }
                }
            }
        }
        return product;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x0269, code lost:
        r0 = (java.util.Map) r0.get(r1);
        r2 = (java.util.List) r0.get("products");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x0277, code lost:
        if (r2 == null) goto L_0x02b9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x0279, code lost:
        r3 = r2.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x0281, code lost:
        if (r3.hasNext() == false) goto L_0x02b9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:?, code lost:
        r4.addProduct(zzx((java.util.Map) r3.next()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x0291, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:?, code lost:
        r2 = java.lang.String.valueOf(r2.getMessage());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x02a0, code lost:
        if (r2.length() != 0) goto L_0x02a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x02a2, code lost:
        r2 = "Failed to extract a product from event data. ".concat(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x02a6, code lost:
        com.google.android.gms.internal.zzcvk.e(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x02b3, code lost:
        r2 = new java.lang.String("Failed to extract a product from event data. ");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x02bf, code lost:
        if (r0.containsKey("actionField") == false) goto L_0x0376;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x02c1, code lost:
        r0 = (java.util.Map) r0.get("actionField");
        r2 = new com.google.android.gms.analytics.ecommerce.ProductAction(r1);
        r1 = r0.get("id");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x02d4, code lost:
        if (r1 == null) goto L_0x02dd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x02d6, code lost:
        r2.setTransactionId(java.lang.String.valueOf(r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x02dd, code lost:
        r1 = r0.get(com.google.firebase.analytics.FirebaseAnalytics.Param.AFFILIATION);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x02e3, code lost:
        if (r1 == null) goto L_0x02ec;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x02e5, code lost:
        r2.setTransactionAffiliation(java.lang.String.valueOf(r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x02ec, code lost:
        r1 = r0.get(com.google.firebase.analytics.FirebaseAnalytics.Param.COUPON);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x02f2, code lost:
        if (r1 == null) goto L_0x02fb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x02f4, code lost:
        r2.setTransactionCouponCode(java.lang.String.valueOf(r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x02fb, code lost:
        r1 = r0.get(com.tencent.imsdk.framework.request.HttpRequestParams.NOTICE_LIST);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x0301, code lost:
        if (r1 == null) goto L_0x030a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x0303, code lost:
        r2.setProductActionList(java.lang.String.valueOf(r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x030a, code lost:
        r1 = r0.get("option");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x0310, code lost:
        if (r1 == null) goto L_0x0319;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x0312, code lost:
        r2.setCheckoutOptions(java.lang.String.valueOf(r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x0319, code lost:
        r1 = r0.get("revenue");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x031f, code lost:
        if (r1 == null) goto L_0x032c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x0321, code lost:
        r2.setTransactionRevenue(zzM(r1).doubleValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x032c, code lost:
        r1 = r0.get(com.google.firebase.analytics.FirebaseAnalytics.Param.TAX);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x0332, code lost:
        if (r1 == null) goto L_0x033f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:0x0334, code lost:
        r2.setTransactionTax(zzM(r1).doubleValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x033f, code lost:
        r1 = r0.get(com.google.firebase.analytics.FirebaseAnalytics.Param.SHIPPING);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x0345, code lost:
        if (r1 == null) goto L_0x0352;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x0347, code lost:
        r2.setTransactionShipping(zzM(r1).doubleValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x0352, code lost:
        r0 = r0.get("step");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:179:0x0358, code lost:
        if (r0 == null) goto L_0x0365;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x035a, code lost:
        r2.setCheckoutStep(zzN(r0).intValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x0365, code lost:
        r0 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x0366, code lost:
        r4.setProductAction(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:188:?, code lost:
        r0 = new com.google.android.gms.analytics.ecommerce.ProductAction(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:189:0x037c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:192:?, code lost:
        r0 = java.lang.String.valueOf(r0.getMessage());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:193:0x038b, code lost:
        if (r0.length() != 0) goto L_0x038d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:194:0x038d, code lost:
        r0 = "Failed to extract a product action from event data. ".concat(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:195:0x0391, code lost:
        com.google.android.gms.internal.zzcvk.e(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:196:0x0395, code lost:
        r0 = new java.lang.String("Failed to extract a product action from event data. ");
     */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x0251 A[Catch:{ RuntimeException -> 0x0103, all -> 0x011c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.dp<?> zza(com.google.android.gms.internal.zzcwa r12, com.google.android.gms.internal.dp<?>... r13) {
        /*
            r11 = this;
            r0 = 1
            com.google.android.gms.common.internal.zzbo.zzaf(r0)
            int r0 = r13.length
            if (r0 <= 0) goto L_0x0121
            r0 = 1
        L_0x0008:
            com.google.android.gms.common.internal.zzbo.zzaf(r0)
            com.google.android.gms.internal.zzcvy r0 = r11.zzbIP     // Catch:{ all -> 0x011c }
            com.google.android.gms.internal.zzcut r0 = r0.zzCy()     // Catch:{ all -> 0x011c }
            android.os.Bundle r0 = r0.zzCl()     // Catch:{ all -> 0x011c }
            java.util.Map r0 = com.google.android.gms.internal.ed.zzC(r0)     // Catch:{ all -> 0x011c }
            r11.zzbKp = r0     // Catch:{ all -> 0x011c }
            r0 = 0
            r9 = r13[r0]     // Catch:{ all -> 0x011c }
            int r0 = r13.length     // Catch:{ all -> 0x011c }
            r1 = 1
            if (r0 <= r1) goto L_0x0124
            r0 = 1
            r0 = r13[r0]     // Catch:{ all -> 0x011c }
            r8 = r0
        L_0x0026:
            int r0 = r13.length     // Catch:{ all -> 0x011c }
            r1 = 2
            if (r0 <= r1) goto L_0x0131
            r0 = 2
            r0 = r13[r0]     // Catch:{ all -> 0x011c }
            r7 = r0
        L_0x002e:
            int r0 = r13.length     // Catch:{ all -> 0x011c }
            r1 = 3
            if (r0 <= r1) goto L_0x013e
            r0 = 3
            r0 = r13[r0]     // Catch:{ all -> 0x011c }
            r6 = r0
        L_0x0036:
            int r0 = r13.length     // Catch:{ all -> 0x011c }
            r1 = 4
            if (r0 <= r1) goto L_0x0143
            r0 = 4
            r0 = r13[r0]     // Catch:{ all -> 0x011c }
            r5 = r0
        L_0x003e:
            int r0 = r13.length     // Catch:{ all -> 0x011c }
            r1 = 5
            if (r0 <= r1) goto L_0x0148
            r0 = 5
            r0 = r13[r0]     // Catch:{ all -> 0x011c }
            r4 = r0
        L_0x0046:
            int r0 = r13.length     // Catch:{ all -> 0x011c }
            r1 = 6
            if (r0 <= r1) goto L_0x0155
            r0 = 6
            r0 = r13[r0]     // Catch:{ all -> 0x011c }
            r3 = r0
        L_0x004e:
            int r0 = r13.length     // Catch:{ all -> 0x011c }
            r1 = 7
            if (r0 <= r1) goto L_0x0162
            r0 = 7
            r0 = r13[r0]     // Catch:{ all -> 0x011c }
            r2 = r0
        L_0x0056:
            int r0 = r13.length     // Catch:{ all -> 0x011c }
            r1 = 8
            if (r0 <= r1) goto L_0x0167
            r0 = 8
            r0 = r13[r0]     // Catch:{ all -> 0x011c }
            r1 = r0
        L_0x0060:
            boolean r0 = r9 instanceof com.google.android.gms.internal.dz     // Catch:{ all -> 0x011c }
            com.google.android.gms.common.internal.zzbo.zzaf(r0)     // Catch:{ all -> 0x011c }
            com.google.android.gms.internal.dv r0 = com.google.android.gms.internal.dv.zzbLu     // Catch:{ all -> 0x011c }
            if (r6 == r0) goto L_0x006d
            boolean r0 = r6 instanceof com.google.android.gms.internal.dz     // Catch:{ all -> 0x011c }
            if (r0 == 0) goto L_0x0174
        L_0x006d:
            r0 = 1
        L_0x006e:
            com.google.android.gms.common.internal.zzbo.zzaf(r0)     // Catch:{ all -> 0x011c }
            com.google.android.gms.internal.dv r0 = com.google.android.gms.internal.dv.zzbLu     // Catch:{ all -> 0x011c }
            if (r5 == r0) goto L_0x0079
            boolean r0 = r5 instanceof com.google.android.gms.internal.dz     // Catch:{ all -> 0x011c }
            if (r0 == 0) goto L_0x0177
        L_0x0079:
            r0 = 1
        L_0x007a:
            com.google.android.gms.common.internal.zzbo.zzaf(r0)     // Catch:{ all -> 0x011c }
            com.google.android.gms.internal.dv r0 = com.google.android.gms.internal.dv.zzbLu     // Catch:{ all -> 0x011c }
            if (r2 == r0) goto L_0x0085
            boolean r0 = r2 instanceof com.google.android.gms.internal.dz     // Catch:{ all -> 0x011c }
            if (r0 == 0) goto L_0x017a
        L_0x0085:
            r0 = 1
        L_0x0086:
            com.google.android.gms.common.internal.zzbo.zzaf(r0)     // Catch:{ all -> 0x011c }
            com.google.android.gms.internal.zzcxj r0 = r11.zzbKo     // Catch:{ all -> 0x011c }
            java.lang.String r10 = "_GTM_DEFAULT_TRACKER_"
            com.google.android.gms.analytics.Tracker r10 = r0.zzfv(r10)     // Catch:{ all -> 0x011c }
            boolean r0 = com.google.android.gms.internal.zzcxp.zza(r1)     // Catch:{ all -> 0x011c }
            r10.enableAdvertisingIdCollection(r0)     // Catch:{ all -> 0x011c }
            boolean r0 = com.google.android.gms.internal.zzcxp.zza(r4)     // Catch:{ all -> 0x011c }
            if (r0 == 0) goto L_0x039b
            com.google.android.gms.analytics.HitBuilders$ScreenViewBuilder r4 = new com.google.android.gms.analytics.HitBuilders$ScreenViewBuilder     // Catch:{ all -> 0x011c }
            r4.<init>()     // Catch:{ all -> 0x011c }
            java.util.Map r1 = zzi(r9)     // Catch:{ all -> 0x011c }
            r4.setAll(r1)     // Catch:{ all -> 0x011c }
            boolean r0 = com.google.android.gms.internal.zzcxp.zza(r3)     // Catch:{ all -> 0x011c }
            if (r0 == 0) goto L_0x017d
            java.util.Map<java.lang.String, java.lang.Object> r0 = r11.zzbKp     // Catch:{ all -> 0x011c }
            java.lang.String r2 = "ecommerce"
            java.lang.Object r0 = r0.get(r2)     // Catch:{ all -> 0x011c }
        L_0x00b8:
            boolean r2 = r0 instanceof java.util.Map     // Catch:{ all -> 0x011c }
            if (r2 == 0) goto L_0x0369
            java.util.Map r0 = (java.util.Map) r0     // Catch:{ all -> 0x011c }
            java.lang.String r2 = "&cu"
            java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x011c }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x011c }
            if (r1 != 0) goto L_0x00d0
            java.lang.String r1 = "currencyCode"
            java.lang.Object r1 = r0.get(r1)     // Catch:{ all -> 0x011c }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x011c }
        L_0x00d0:
            if (r1 == 0) goto L_0x00d7
            java.lang.String r2 = "&cu"
            r4.set(r2, r1)     // Catch:{ all -> 0x011c }
        L_0x00d7:
            java.lang.String r1 = "impressions"
            java.lang.Object r1 = r0.get(r1)     // Catch:{ all -> 0x011c }
            boolean r2 = r1 instanceof java.util.List     // Catch:{ all -> 0x011c }
            if (r2 == 0) goto L_0x018d
            java.util.List r1 = (java.util.List) r1     // Catch:{ all -> 0x011c }
            java.util.Iterator r2 = r1.iterator()     // Catch:{ all -> 0x011c }
        L_0x00e7:
            boolean r1 = r2.hasNext()     // Catch:{ all -> 0x011c }
            if (r1 == 0) goto L_0x018d
            java.lang.Object r1 = r2.next()     // Catch:{ all -> 0x011c }
            java.util.Map r1 = (java.util.Map) r1     // Catch:{ all -> 0x011c }
            com.google.android.gms.analytics.ecommerce.Product r3 = zzx(r1)     // Catch:{ RuntimeException -> 0x0103 }
            java.lang.String r5 = "list"
            java.lang.Object r1 = r1.get(r5)     // Catch:{ RuntimeException -> 0x0103 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ RuntimeException -> 0x0103 }
            r4.addImpression(r3, r1)     // Catch:{ RuntimeException -> 0x0103 }
            goto L_0x00e7
        L_0x0103:
            r1 = move-exception
            java.lang.String r3 = "Failed to extract a product from event data. "
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x011c }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x011c }
            int r5 = r1.length()     // Catch:{ all -> 0x011c }
            if (r5 == 0) goto L_0x0187
            java.lang.String r1 = r3.concat(r1)     // Catch:{ all -> 0x011c }
        L_0x0118:
            com.google.android.gms.internal.zzcvk.e(r1)     // Catch:{ all -> 0x011c }
            goto L_0x00e7
        L_0x011c:
            r0 = move-exception
            r1 = 0
            r11.zzbKp = r1
            throw r0
        L_0x0121:
            r0 = 0
            goto L_0x0008
        L_0x0124:
            com.google.android.gms.internal.ds r0 = new com.google.android.gms.internal.ds     // Catch:{ all -> 0x011c }
            r1 = 1
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)     // Catch:{ all -> 0x011c }
            r0.<init>(r1)     // Catch:{ all -> 0x011c }
            r8 = r0
            goto L_0x0026
        L_0x0131:
            com.google.android.gms.internal.ds r0 = new com.google.android.gms.internal.ds     // Catch:{ all -> 0x011c }
            r1 = 0
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)     // Catch:{ all -> 0x011c }
            r0.<init>(r1)     // Catch:{ all -> 0x011c }
            r7 = r0
            goto L_0x002e
        L_0x013e:
            com.google.android.gms.internal.dv r0 = com.google.android.gms.internal.dv.zzbLu     // Catch:{ all -> 0x011c }
            r6 = r0
            goto L_0x0036
        L_0x0143:
            com.google.android.gms.internal.dv r0 = com.google.android.gms.internal.dv.zzbLu     // Catch:{ all -> 0x011c }
            r5 = r0
            goto L_0x003e
        L_0x0148:
            com.google.android.gms.internal.ds r0 = new com.google.android.gms.internal.ds     // Catch:{ all -> 0x011c }
            r1 = 0
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)     // Catch:{ all -> 0x011c }
            r0.<init>(r1)     // Catch:{ all -> 0x011c }
            r4 = r0
            goto L_0x0046
        L_0x0155:
            com.google.android.gms.internal.ds r0 = new com.google.android.gms.internal.ds     // Catch:{ all -> 0x011c }
            r1 = 0
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)     // Catch:{ all -> 0x011c }
            r0.<init>(r1)     // Catch:{ all -> 0x011c }
            r3 = r0
            goto L_0x004e
        L_0x0162:
            com.google.android.gms.internal.dv r0 = com.google.android.gms.internal.dv.zzbLu     // Catch:{ all -> 0x011c }
            r2 = r0
            goto L_0x0056
        L_0x0167:
            com.google.android.gms.internal.ds r0 = new com.google.android.gms.internal.ds     // Catch:{ all -> 0x011c }
            r1 = 0
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)     // Catch:{ all -> 0x011c }
            r0.<init>(r1)     // Catch:{ all -> 0x011c }
            r1 = r0
            goto L_0x0060
        L_0x0174:
            r0 = 0
            goto L_0x006e
        L_0x0177:
            r0 = 0
            goto L_0x007a
        L_0x017a:
            r0 = 0
            goto L_0x0086
        L_0x017d:
            com.google.android.gms.internal.dp r0 = com.google.android.gms.internal.ed.zzk(r2)     // Catch:{ all -> 0x011c }
            java.lang.Object r0 = com.google.android.gms.internal.ed.zzj(r0)     // Catch:{ all -> 0x011c }
            goto L_0x00b8
        L_0x0187:
            java.lang.String r1 = new java.lang.String     // Catch:{ all -> 0x011c }
            r1.<init>(r3)     // Catch:{ all -> 0x011c }
            goto L_0x0118
        L_0x018d:
            r1 = 0
            java.lang.String r2 = "promoClick"
            boolean r2 = r0.containsKey(r2)     // Catch:{ all -> 0x011c }
            if (r2 == 0) goto L_0x021f
            java.lang.String r1 = "promoClick"
            java.lang.Object r1 = r0.get(r1)     // Catch:{ all -> 0x011c }
            java.util.Map r1 = (java.util.Map) r1     // Catch:{ all -> 0x011c }
            java.lang.String r2 = "promotions"
            java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x011c }
            java.util.List r1 = (java.util.List) r1     // Catch:{ all -> 0x011c }
        L_0x01a6:
            r3 = 1
            if (r1 == 0) goto L_0x02b1
            java.util.Iterator r5 = r1.iterator()     // Catch:{ all -> 0x011c }
        L_0x01ad:
            boolean r1 = r5.hasNext()     // Catch:{ all -> 0x011c }
            if (r1 == 0) goto L_0x023f
            java.lang.Object r1 = r5.next()     // Catch:{ all -> 0x011c }
            java.util.Map r1 = (java.util.Map) r1     // Catch:{ all -> 0x011c }
            com.google.android.gms.analytics.ecommerce.Promotion r6 = new com.google.android.gms.analytics.ecommerce.Promotion     // Catch:{ RuntimeException -> 0x0206 }
            r6.<init>()     // Catch:{ RuntimeException -> 0x0206 }
            java.lang.String r2 = "id"
            java.lang.Object r2 = r1.get(r2)     // Catch:{ RuntimeException -> 0x0206 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ RuntimeException -> 0x0206 }
            if (r2 == 0) goto L_0x01cf
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ RuntimeException -> 0x0206 }
            r6.setId(r2)     // Catch:{ RuntimeException -> 0x0206 }
        L_0x01cf:
            java.lang.String r2 = "name"
            java.lang.Object r2 = r1.get(r2)     // Catch:{ RuntimeException -> 0x0206 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ RuntimeException -> 0x0206 }
            if (r2 == 0) goto L_0x01e0
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ RuntimeException -> 0x0206 }
            r6.setName(r2)     // Catch:{ RuntimeException -> 0x0206 }
        L_0x01e0:
            java.lang.String r2 = "creative"
            java.lang.Object r2 = r1.get(r2)     // Catch:{ RuntimeException -> 0x0206 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ RuntimeException -> 0x0206 }
            if (r2 == 0) goto L_0x01f1
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ RuntimeException -> 0x0206 }
            r6.setCreative(r2)     // Catch:{ RuntimeException -> 0x0206 }
        L_0x01f1:
            java.lang.String r2 = "position"
            java.lang.Object r1 = r1.get(r2)     // Catch:{ RuntimeException -> 0x0206 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ RuntimeException -> 0x0206 }
            if (r1 == 0) goto L_0x0202
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ RuntimeException -> 0x0206 }
            r6.setPosition(r1)     // Catch:{ RuntimeException -> 0x0206 }
        L_0x0202:
            r4.addPromotion(r6)     // Catch:{ RuntimeException -> 0x0206 }
            goto L_0x01ad
        L_0x0206:
            r1 = move-exception
            java.lang.String r2 = "Failed to extract a promotion from event data. "
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x011c }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x011c }
            int r6 = r1.length()     // Catch:{ all -> 0x011c }
            if (r6 == 0) goto L_0x0239
            java.lang.String r1 = r2.concat(r1)     // Catch:{ all -> 0x011c }
        L_0x021b:
            com.google.android.gms.internal.zzcvk.e(r1)     // Catch:{ all -> 0x011c }
            goto L_0x01ad
        L_0x021f:
            java.lang.String r2 = "promoView"
            boolean r2 = r0.containsKey(r2)     // Catch:{ all -> 0x011c }
            if (r2 == 0) goto L_0x01a6
            java.lang.String r1 = "promoView"
            java.lang.Object r1 = r0.get(r1)     // Catch:{ all -> 0x011c }
            java.util.Map r1 = (java.util.Map) r1     // Catch:{ all -> 0x011c }
            java.lang.String r2 = "promotions"
            java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x011c }
            java.util.List r1 = (java.util.List) r1     // Catch:{ all -> 0x011c }
            goto L_0x01a6
        L_0x0239:
            java.lang.String r1 = new java.lang.String     // Catch:{ all -> 0x011c }
            r1.<init>(r2)     // Catch:{ all -> 0x011c }
            goto L_0x021b
        L_0x023f:
            java.lang.String r1 = "promoClick"
            boolean r1 = r0.containsKey(r1)     // Catch:{ all -> 0x011c }
            if (r1 == 0) goto L_0x02aa
            java.lang.String r1 = "&promoa"
            java.lang.String r2 = "click"
            r4.set(r1, r2)     // Catch:{ all -> 0x011c }
            r1 = 0
        L_0x024f:
            if (r1 == 0) goto L_0x0369
            java.util.List<java.lang.String> r1 = zzbHw     // Catch:{ all -> 0x011c }
            java.util.Iterator r2 = r1.iterator()     // Catch:{ all -> 0x011c }
        L_0x0257:
            boolean r1 = r2.hasNext()     // Catch:{ all -> 0x011c }
            if (r1 == 0) goto L_0x0369
            java.lang.Object r1 = r2.next()     // Catch:{ all -> 0x011c }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x011c }
            boolean r3 = r0.containsKey(r1)     // Catch:{ all -> 0x011c }
            if (r3 == 0) goto L_0x0257
            java.lang.Object r0 = r0.get(r1)     // Catch:{ all -> 0x011c }
            java.util.Map r0 = (java.util.Map) r0     // Catch:{ all -> 0x011c }
            java.lang.String r2 = "products"
            java.lang.Object r2 = r0.get(r2)     // Catch:{ all -> 0x011c }
            java.util.List r2 = (java.util.List) r2     // Catch:{ all -> 0x011c }
            if (r2 == 0) goto L_0x02b9
            java.util.Iterator r3 = r2.iterator()     // Catch:{ all -> 0x011c }
        L_0x027d:
            boolean r2 = r3.hasNext()     // Catch:{ all -> 0x011c }
            if (r2 == 0) goto L_0x02b9
            java.lang.Object r2 = r3.next()     // Catch:{ all -> 0x011c }
            java.util.Map r2 = (java.util.Map) r2     // Catch:{ all -> 0x011c }
            com.google.android.gms.analytics.ecommerce.Product r2 = zzx(r2)     // Catch:{ RuntimeException -> 0x0291 }
            r4.addProduct(r2)     // Catch:{ RuntimeException -> 0x0291 }
            goto L_0x027d
        L_0x0291:
            r2 = move-exception
            java.lang.String r5 = "Failed to extract a product from event data. "
            java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x011c }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x011c }
            int r6 = r2.length()     // Catch:{ all -> 0x011c }
            if (r6 == 0) goto L_0x02b3
            java.lang.String r2 = r5.concat(r2)     // Catch:{ all -> 0x011c }
        L_0x02a6:
            com.google.android.gms.internal.zzcvk.e(r2)     // Catch:{ all -> 0x011c }
            goto L_0x027d
        L_0x02aa:
            java.lang.String r1 = "&promoa"
            java.lang.String r2 = "view"
            r4.set(r1, r2)     // Catch:{ all -> 0x011c }
        L_0x02b1:
            r1 = r3
            goto L_0x024f
        L_0x02b3:
            java.lang.String r2 = new java.lang.String     // Catch:{ all -> 0x011c }
            r2.<init>(r5)     // Catch:{ all -> 0x011c }
            goto L_0x02a6
        L_0x02b9:
            java.lang.String r2 = "actionField"
            boolean r2 = r0.containsKey(r2)     // Catch:{ RuntimeException -> 0x037c }
            if (r2 == 0) goto L_0x0376
            java.lang.String r2 = "actionField"
            java.lang.Object r0 = r0.get(r2)     // Catch:{ RuntimeException -> 0x037c }
            java.util.Map r0 = (java.util.Map) r0     // Catch:{ RuntimeException -> 0x037c }
            com.google.android.gms.analytics.ecommerce.ProductAction r2 = new com.google.android.gms.analytics.ecommerce.ProductAction     // Catch:{ RuntimeException -> 0x037c }
            r2.<init>(r1)     // Catch:{ RuntimeException -> 0x037c }
            java.lang.String r1 = "id"
            java.lang.Object r1 = r0.get(r1)     // Catch:{ RuntimeException -> 0x037c }
            if (r1 == 0) goto L_0x02dd
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ RuntimeException -> 0x037c }
            r2.setTransactionId(r1)     // Catch:{ RuntimeException -> 0x037c }
        L_0x02dd:
            java.lang.String r1 = "affiliation"
            java.lang.Object r1 = r0.get(r1)     // Catch:{ RuntimeException -> 0x037c }
            if (r1 == 0) goto L_0x02ec
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ RuntimeException -> 0x037c }
            r2.setTransactionAffiliation(r1)     // Catch:{ RuntimeException -> 0x037c }
        L_0x02ec:
            java.lang.String r1 = "coupon"
            java.lang.Object r1 = r0.get(r1)     // Catch:{ RuntimeException -> 0x037c }
            if (r1 == 0) goto L_0x02fb
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ RuntimeException -> 0x037c }
            r2.setTransactionCouponCode(r1)     // Catch:{ RuntimeException -> 0x037c }
        L_0x02fb:
            java.lang.String r1 = "list"
            java.lang.Object r1 = r0.get(r1)     // Catch:{ RuntimeException -> 0x037c }
            if (r1 == 0) goto L_0x030a
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ RuntimeException -> 0x037c }
            r2.setProductActionList(r1)     // Catch:{ RuntimeException -> 0x037c }
        L_0x030a:
            java.lang.String r1 = "option"
            java.lang.Object r1 = r0.get(r1)     // Catch:{ RuntimeException -> 0x037c }
            if (r1 == 0) goto L_0x0319
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ RuntimeException -> 0x037c }
            r2.setCheckoutOptions(r1)     // Catch:{ RuntimeException -> 0x037c }
        L_0x0319:
            java.lang.String r1 = "revenue"
            java.lang.Object r1 = r0.get(r1)     // Catch:{ RuntimeException -> 0x037c }
            if (r1 == 0) goto L_0x032c
            java.lang.Double r1 = zzM(r1)     // Catch:{ RuntimeException -> 0x037c }
            double r6 = r1.doubleValue()     // Catch:{ RuntimeException -> 0x037c }
            r2.setTransactionRevenue(r6)     // Catch:{ RuntimeException -> 0x037c }
        L_0x032c:
            java.lang.String r1 = "tax"
            java.lang.Object r1 = r0.get(r1)     // Catch:{ RuntimeException -> 0x037c }
            if (r1 == 0) goto L_0x033f
            java.lang.Double r1 = zzM(r1)     // Catch:{ RuntimeException -> 0x037c }
            double r6 = r1.doubleValue()     // Catch:{ RuntimeException -> 0x037c }
            r2.setTransactionTax(r6)     // Catch:{ RuntimeException -> 0x037c }
        L_0x033f:
            java.lang.String r1 = "shipping"
            java.lang.Object r1 = r0.get(r1)     // Catch:{ RuntimeException -> 0x037c }
            if (r1 == 0) goto L_0x0352
            java.lang.Double r1 = zzM(r1)     // Catch:{ RuntimeException -> 0x037c }
            double r6 = r1.doubleValue()     // Catch:{ RuntimeException -> 0x037c }
            r2.setTransactionShipping(r6)     // Catch:{ RuntimeException -> 0x037c }
        L_0x0352:
            java.lang.String r1 = "step"
            java.lang.Object r0 = r0.get(r1)     // Catch:{ RuntimeException -> 0x037c }
            if (r0 == 0) goto L_0x0365
            java.lang.Integer r0 = zzN(r0)     // Catch:{ RuntimeException -> 0x037c }
            int r0 = r0.intValue()     // Catch:{ RuntimeException -> 0x037c }
            r2.setCheckoutStep(r0)     // Catch:{ RuntimeException -> 0x037c }
        L_0x0365:
            r0 = r2
        L_0x0366:
            r4.setProductAction(r0)     // Catch:{ RuntimeException -> 0x037c }
        L_0x0369:
            java.util.Map r0 = r4.build()     // Catch:{ all -> 0x011c }
            r10.send(r0)     // Catch:{ all -> 0x011c }
        L_0x0370:
            r0 = 0
            r11.zzbKp = r0
            com.google.android.gms.internal.dv r0 = com.google.android.gms.internal.dv.zzbLu
            return r0
        L_0x0376:
            com.google.android.gms.analytics.ecommerce.ProductAction r0 = new com.google.android.gms.analytics.ecommerce.ProductAction     // Catch:{ RuntimeException -> 0x037c }
            r0.<init>(r1)     // Catch:{ RuntimeException -> 0x037c }
            goto L_0x0366
        L_0x037c:
            r0 = move-exception
            java.lang.String r1 = "Failed to extract a product action from event data. "
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x011c }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x011c }
            int r2 = r0.length()     // Catch:{ all -> 0x011c }
            if (r2 == 0) goto L_0x0395
            java.lang.String r0 = r1.concat(r0)     // Catch:{ all -> 0x011c }
        L_0x0391:
            com.google.android.gms.internal.zzcvk.e(r0)     // Catch:{ all -> 0x011c }
            goto L_0x0369
        L_0x0395:
            java.lang.String r0 = new java.lang.String     // Catch:{ all -> 0x011c }
            r0.<init>(r1)     // Catch:{ all -> 0x011c }
            goto L_0x0391
        L_0x039b:
            boolean r0 = com.google.android.gms.internal.zzcxp.zza(r8)     // Catch:{ all -> 0x011c }
            if (r0 == 0) goto L_0x03a9
            java.util.Map r0 = zzi(r9)     // Catch:{ all -> 0x011c }
            r10.send(r0)     // Catch:{ all -> 0x011c }
            goto L_0x0370
        L_0x03a9:
            boolean r0 = com.google.android.gms.internal.zzcxp.zza(r7)     // Catch:{ all -> 0x011c }
            if (r0 == 0) goto L_0x03b3
            r11.zza(r10, r9, r6, r5)     // Catch:{ all -> 0x011c }
            goto L_0x0370
        L_0x03b3:
            java.lang.String r0 = "Ignoring unknown tag."
            com.google.android.gms.internal.zzcvk.zzaT(r0)     // Catch:{ all -> 0x011c }
            goto L_0x0370
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.by.zza(com.google.android.gms.internal.zzcwa, com.google.android.gms.internal.dp[]):com.google.android.gms.internal.dp");
    }
}
