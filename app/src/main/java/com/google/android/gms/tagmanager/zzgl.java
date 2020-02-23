package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.internal.zzbf;
import com.google.android.gms.internal.zzbg;
import com.google.android.gms.internal.zzbr;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.vk.sdk.api.VKApiConst;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class zzgl extends zzgi {
    private static final String ID = zzbf.UNIVERSAL_ANALYTICS.toString();
    private static Map<String, String> zzbHA;
    private static final String zzbHn = zzbg.ACCOUNT.toString();
    private static final String zzbHo = zzbg.ANALYTICS_PASS_THROUGH.toString();
    private static final String zzbHp = zzbg.ENABLE_ECOMMERCE.toString();
    private static final String zzbHq = zzbg.ECOMMERCE_USE_DATA_LAYER.toString();
    private static final String zzbHr = zzbg.ECOMMERCE_MACRO_DATA.toString();
    private static final String zzbHs = zzbg.ANALYTICS_FIELDS.toString();
    private static final String zzbHt = zzbg.TRACK_TRANSACTION.toString();
    private static final String zzbHu = zzbg.TRANSACTION_DATALAYER_MAP.toString();
    private static final String zzbHv = zzbg.TRANSACTION_ITEM_DATALAYER_MAP.toString();
    private static final List<String> zzbHw = Arrays.asList(new String[]{ProductAction.ACTION_DETAIL, ProductAction.ACTION_CHECKOUT, "checkout_option", "click", ProductAction.ACTION_ADD, ProductAction.ACTION_REMOVE, ProductAction.ACTION_PURCHASE, ProductAction.ACTION_REFUND});
    private static final Pattern zzbHx = Pattern.compile("dimension(\\d+)");
    private static final Pattern zzbHy = Pattern.compile("metric(\\d+)");
    private static Map<String, String> zzbHz;
    private final DataLayer zzbDx;
    private final Set<String> zzbHB;
    private final zzgg zzbHC;

    public zzgl(Context context, DataLayer dataLayer) {
        this(context, dataLayer, new zzgg(context));
    }

    private zzgl(Context context, DataLayer dataLayer, zzgg zzgg) {
        super(ID, new String[0]);
        this.zzbDx = dataLayer;
        this.zzbHC = zzgg;
        this.zzbHB = new HashSet();
        this.zzbHB.add("");
        this.zzbHB.add("0");
        this.zzbHB.add("false");
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

    private final void zza(Tracker tracker, Map<String, zzbr> map) {
        Map<String, String> map2;
        Map<String, String> map3;
        String zzfA = zzfA("transactionId");
        if (zzfA == null) {
            zzdj.e("Cannot find transactionId in data layer.");
            return;
        }
        LinkedList<Map> linkedList = new LinkedList<>();
        try {
            Map<String, String> zzi = zzi(map.get(zzbHs));
            zzi.put("&t", "transaction");
            zzbr zzbr = map.get(zzbHu);
            if (zzbr != null) {
                map2 = zzh(zzbr);
            } else {
                if (zzbHz == null) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("transactionId", "&ti");
                    hashMap.put("transactionAffiliation", "&ta");
                    hashMap.put("transactionTax", "&tt");
                    hashMap.put("transactionShipping", "&ts");
                    hashMap.put("transactionTotal", "&tr");
                    hashMap.put("transactionCurrency", "&cu");
                    zzbHz = hashMap;
                }
                map2 = zzbHz;
            }
            for (Map.Entry next : map2.entrySet()) {
                zzd(zzi, (String) next.getValue(), zzfA((String) next.getKey()));
            }
            linkedList.add(zzi);
            List<Map<String, String>> zzfB = zzfB("transactionProducts");
            if (zzfB != null) {
                for (Map next2 : zzfB) {
                    if (next2.get("name") == null) {
                        zzdj.e("Unable to send transaction item hit due to missing 'name' field.");
                        return;
                    }
                    Map<String, String> zzi2 = zzi(map.get(zzbHs));
                    zzi2.put("&t", "item");
                    zzi2.put("&ti", zzfA);
                    zzbr zzbr2 = map.get(zzbHv);
                    if (zzbr2 != null) {
                        map3 = zzh(zzbr2);
                    } else {
                        if (zzbHA == null) {
                            HashMap hashMap2 = new HashMap();
                            hashMap2.put("name", "&in");
                            hashMap2.put("sku", "&ic");
                            hashMap2.put("category", "&iv");
                            hashMap2.put(FirebaseAnalytics.Param.PRICE, "&ip");
                            hashMap2.put(FirebaseAnalytics.Param.QUANTITY, "&iq");
                            hashMap2.put(FirebaseAnalytics.Param.CURRENCY, "&cu");
                            zzbHA = hashMap2;
                        }
                        map3 = zzbHA;
                    }
                    for (Map.Entry next3 : map3.entrySet()) {
                        zzd(zzi2, (String) next3.getValue(), (String) next2.get(next3.getKey()));
                    }
                    linkedList.add(zzi2);
                }
            }
            for (Map send : linkedList) {
                tracker.send(send);
            }
        } catch (IllegalArgumentException e) {
            zzdj.zzb("Unable to send transaction", e);
        }
    }

    private static void zzd(Map<String, String> map, String str, String str2) {
        if (str2 != null) {
            map.put(str, str2);
        }
    }

    private final String zzfA(String str) {
        Object obj = this.zzbDx.get(str);
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    private final List<Map<String, String>> zzfB(String str) {
        Object obj = this.zzbDx.get(str);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof List)) {
            throw new IllegalArgumentException("transactionProducts should be of type List.");
        }
        for (Object obj2 : (List) obj) {
            if (!(obj2 instanceof Map)) {
                throw new IllegalArgumentException("Each element of transactionProducts should be of type Map.");
            }
        }
        return (List) obj;
    }

    private static boolean zzg(Map<String, zzbr> map, String str) {
        zzbr zzbr = map.get(str);
        if (zzbr == null) {
            return false;
        }
        return zzgk.zzf(zzbr).booleanValue();
    }

    private static Map<String, String> zzh(zzbr zzbr) {
        Object zzg = zzgk.zzg(zzbr);
        if (!(zzg instanceof Map)) {
            return null;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : ((Map) zzg).entrySet()) {
            linkedHashMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return linkedHashMap;
    }

    private final Map<String, String> zzi(zzbr zzbr) {
        if (zzbr == null) {
            return new HashMap();
        }
        Map<String, String> zzh = zzh(zzbr);
        if (zzh == null) {
            return new HashMap();
        }
        String str = zzh.get("&aip");
        if (str != null && this.zzbHB.contains(str.toLowerCase())) {
            zzh.remove("&aip");
        }
        return zzh;
    }

    private static Product zzt(Map<String, Object> map) {
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
                    zzdj.zzaT(valueOf.length() != 0 ? "illegal number in custom dimension value: ".concat(valueOf) : new String("illegal number in custom dimension value: "));
                }
            } else {
                Matcher matcher2 = zzbHy.matcher(next);
                if (matcher2.matches()) {
                    try {
                        product.setCustomMetric(Integer.parseInt(matcher2.group(1)), zzN(map.get(next)).intValue());
                    } catch (NumberFormatException e2) {
                        String valueOf2 = String.valueOf(next);
                        zzdj.zzaT(valueOf2.length() != 0 ? "illegal number in custom metric value: ".concat(valueOf2) : new String("illegal number in custom metric value: "));
                    }
                }
            }
        }
        return product;
    }

    public final /* bridge */ /* synthetic */ boolean zzAE() {
        return super.zzAE();
    }

    public final /* bridge */ /* synthetic */ String zzBk() {
        return super.zzBk();
    }

    public final /* bridge */ /* synthetic */ Set zzBl() {
        return super.zzBl();
    }

    public final /* bridge */ /* synthetic */ zzbr zzo(Map map) {
        return super.zzo(map);
    }

    /* JADX WARNING: Removed duplicated region for block: B:67:0x0182  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzq(java.util.Map<java.lang.String, com.google.android.gms.internal.zzbr> r9) {
        /*
            r8 = this;
            r1 = 0
            com.google.android.gms.tagmanager.zzgg r0 = r8.zzbHC
            java.lang.String r2 = "_GTM_DEFAULT_TRACKER_"
            com.google.android.gms.analytics.Tracker r4 = r0.zzfv(r2)
            java.lang.String r0 = "collect_adid"
            boolean r0 = zzg(r9, r0)
            r4.enableAdvertisingIdCollection(r0)
            java.lang.String r0 = zzbHp
            boolean r0 = zzg(r9, r0)
            if (r0 == 0) goto L_0x02c8
            com.google.android.gms.analytics.HitBuilders$ScreenViewBuilder r5 = new com.google.android.gms.analytics.HitBuilders$ScreenViewBuilder
            r5.<init>()
            java.lang.String r0 = zzbHs
            java.lang.Object r0 = r9.get(r0)
            com.google.android.gms.internal.zzbr r0 = (com.google.android.gms.internal.zzbr) r0
            java.util.Map r2 = r8.zzi(r0)
            r5.setAll(r2)
            java.lang.String r0 = zzbHq
            boolean r0 = zzg(r9, r0)
            if (r0 == 0) goto L_0x00a5
            com.google.android.gms.tagmanager.DataLayer r0 = r8.zzbDx
            java.lang.String r3 = "ecommerce"
            java.lang.Object r0 = r0.get(r3)
            boolean r3 = r0 instanceof java.util.Map
            if (r3 == 0) goto L_0x02f8
            java.util.Map r0 = (java.util.Map) r0
        L_0x0044:
            r3 = r0
        L_0x0045:
            if (r3 == 0) goto L_0x029a
            java.lang.String r0 = "&cu"
            java.lang.Object r0 = r2.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 != 0) goto L_0x0059
            java.lang.String r0 = "currencyCode"
            java.lang.Object r0 = r3.get(r0)
            java.lang.String r0 = (java.lang.String) r0
        L_0x0059:
            if (r0 == 0) goto L_0x0060
            java.lang.String r2 = "&cu"
            r5.set(r2, r0)
        L_0x0060:
            java.lang.String r0 = "impressions"
            java.lang.Object r0 = r3.get(r0)
            boolean r2 = r0 instanceof java.util.List
            if (r2 == 0) goto L_0x00bf
            java.util.List r0 = (java.util.List) r0
            java.util.Iterator r2 = r0.iterator()
        L_0x0070:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x00bf
            java.lang.Object r0 = r2.next()
            java.util.Map r0 = (java.util.Map) r0
            com.google.android.gms.analytics.ecommerce.Product r6 = zzt(r0)     // Catch:{ RuntimeException -> 0x008c }
            java.lang.String r7 = "list"
            java.lang.Object r0 = r0.get(r7)     // Catch:{ RuntimeException -> 0x008c }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ RuntimeException -> 0x008c }
            r5.addImpression(r6, r0)     // Catch:{ RuntimeException -> 0x008c }
            goto L_0x0070
        L_0x008c:
            r0 = move-exception
            java.lang.String r6 = "Failed to extract a product from DataLayer. "
            java.lang.String r0 = r0.getMessage()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            int r7 = r0.length()
            if (r7 == 0) goto L_0x00b9
            java.lang.String r0 = r6.concat(r0)
        L_0x00a1:
            com.google.android.gms.tagmanager.zzdj.e(r0)
            goto L_0x0070
        L_0x00a5:
            java.lang.String r0 = zzbHr
            java.lang.Object r0 = r9.get(r0)
            com.google.android.gms.internal.zzbr r0 = (com.google.android.gms.internal.zzbr) r0
            java.lang.Object r0 = com.google.android.gms.tagmanager.zzgk.zzg(r0)
            boolean r3 = r0 instanceof java.util.Map
            if (r3 == 0) goto L_0x02f5
            java.util.Map r0 = (java.util.Map) r0
            r3 = r0
            goto L_0x0045
        L_0x00b9:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r6)
            goto L_0x00a1
        L_0x00bf:
            java.lang.String r0 = "promoClick"
            boolean r0 = r3.containsKey(r0)
            if (r0 == 0) goto L_0x0150
            java.lang.String r0 = "promoClick"
            java.lang.Object r0 = r3.get(r0)
            java.util.Map r0 = (java.util.Map) r0
            java.lang.String r1 = "promotions"
            java.lang.Object r0 = r0.get(r1)
            java.util.List r0 = (java.util.List) r0
        L_0x00d7:
            r2 = 1
            if (r0 == 0) goto L_0x01e2
            java.util.Iterator r6 = r0.iterator()
        L_0x00de:
            boolean r0 = r6.hasNext()
            if (r0 == 0) goto L_0x0170
            java.lang.Object r0 = r6.next()
            java.util.Map r0 = (java.util.Map) r0
            com.google.android.gms.analytics.ecommerce.Promotion r7 = new com.google.android.gms.analytics.ecommerce.Promotion     // Catch:{ RuntimeException -> 0x0137 }
            r7.<init>()     // Catch:{ RuntimeException -> 0x0137 }
            java.lang.String r1 = "id"
            java.lang.Object r1 = r0.get(r1)     // Catch:{ RuntimeException -> 0x0137 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ RuntimeException -> 0x0137 }
            if (r1 == 0) goto L_0x0100
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ RuntimeException -> 0x0137 }
            r7.setId(r1)     // Catch:{ RuntimeException -> 0x0137 }
        L_0x0100:
            java.lang.String r1 = "name"
            java.lang.Object r1 = r0.get(r1)     // Catch:{ RuntimeException -> 0x0137 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ RuntimeException -> 0x0137 }
            if (r1 == 0) goto L_0x0111
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ RuntimeException -> 0x0137 }
            r7.setName(r1)     // Catch:{ RuntimeException -> 0x0137 }
        L_0x0111:
            java.lang.String r1 = "creative"
            java.lang.Object r1 = r0.get(r1)     // Catch:{ RuntimeException -> 0x0137 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ RuntimeException -> 0x0137 }
            if (r1 == 0) goto L_0x0122
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ RuntimeException -> 0x0137 }
            r7.setCreative(r1)     // Catch:{ RuntimeException -> 0x0137 }
        L_0x0122:
            java.lang.String r1 = "position"
            java.lang.Object r0 = r0.get(r1)     // Catch:{ RuntimeException -> 0x0137 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ RuntimeException -> 0x0137 }
            if (r0 == 0) goto L_0x0133
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ RuntimeException -> 0x0137 }
            r7.setPosition(r0)     // Catch:{ RuntimeException -> 0x0137 }
        L_0x0133:
            r5.addPromotion(r7)     // Catch:{ RuntimeException -> 0x0137 }
            goto L_0x00de
        L_0x0137:
            r0 = move-exception
            java.lang.String r1 = "Failed to extract a promotion from DataLayer. "
            java.lang.String r0 = r0.getMessage()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            int r7 = r0.length()
            if (r7 == 0) goto L_0x016a
            java.lang.String r0 = r1.concat(r0)
        L_0x014c:
            com.google.android.gms.tagmanager.zzdj.e(r0)
            goto L_0x00de
        L_0x0150:
            java.lang.String r0 = "promoView"
            boolean r0 = r3.containsKey(r0)
            if (r0 == 0) goto L_0x02f2
            java.lang.String r0 = "promoView"
            java.lang.Object r0 = r3.get(r0)
            java.util.Map r0 = (java.util.Map) r0
            java.lang.String r1 = "promotions"
            java.lang.Object r0 = r0.get(r1)
            java.util.List r0 = (java.util.List) r0
            goto L_0x00d7
        L_0x016a:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r1)
            goto L_0x014c
        L_0x0170:
            java.lang.String r0 = "promoClick"
            boolean r0 = r3.containsKey(r0)
            if (r0 == 0) goto L_0x01db
            java.lang.String r0 = "&promoa"
            java.lang.String r1 = "click"
            r5.set(r0, r1)
            r0 = 0
        L_0x0180:
            if (r0 == 0) goto L_0x029a
            java.util.List<java.lang.String> r0 = zzbHw
            java.util.Iterator r1 = r0.iterator()
        L_0x0188:
            boolean r0 = r1.hasNext()
            if (r0 == 0) goto L_0x029a
            java.lang.Object r0 = r1.next()
            java.lang.String r0 = (java.lang.String) r0
            boolean r2 = r3.containsKey(r0)
            if (r2 == 0) goto L_0x0188
            java.lang.Object r1 = r3.get(r0)
            java.util.Map r1 = (java.util.Map) r1
            java.lang.String r2 = "products"
            java.lang.Object r2 = r1.get(r2)
            java.util.List r2 = (java.util.List) r2
            if (r2 == 0) goto L_0x01ea
            java.util.Iterator r3 = r2.iterator()
        L_0x01ae:
            boolean r2 = r3.hasNext()
            if (r2 == 0) goto L_0x01ea
            java.lang.Object r2 = r3.next()
            java.util.Map r2 = (java.util.Map) r2
            com.google.android.gms.analytics.ecommerce.Product r2 = zzt(r2)     // Catch:{ RuntimeException -> 0x01c2 }
            r5.addProduct(r2)     // Catch:{ RuntimeException -> 0x01c2 }
            goto L_0x01ae
        L_0x01c2:
            r2 = move-exception
            java.lang.String r6 = "Failed to extract a product from DataLayer. "
            java.lang.String r2 = r2.getMessage()
            java.lang.String r2 = java.lang.String.valueOf(r2)
            int r7 = r2.length()
            if (r7 == 0) goto L_0x01e4
            java.lang.String r2 = r6.concat(r2)
        L_0x01d7:
            com.google.android.gms.tagmanager.zzdj.e(r2)
            goto L_0x01ae
        L_0x01db:
            java.lang.String r0 = "&promoa"
            java.lang.String r1 = "view"
            r5.set(r0, r1)
        L_0x01e2:
            r0 = r2
            goto L_0x0180
        L_0x01e4:
            java.lang.String r2 = new java.lang.String
            r2.<init>(r6)
            goto L_0x01d7
        L_0x01ea:
            java.lang.String r2 = "actionField"
            boolean r2 = r1.containsKey(r2)     // Catch:{ RuntimeException -> 0x02a9 }
            if (r2 == 0) goto L_0x02a2
            java.lang.String r2 = "actionField"
            java.lang.Object r1 = r1.get(r2)     // Catch:{ RuntimeException -> 0x02a9 }
            java.util.Map r1 = (java.util.Map) r1     // Catch:{ RuntimeException -> 0x02a9 }
            com.google.android.gms.analytics.ecommerce.ProductAction r2 = new com.google.android.gms.analytics.ecommerce.ProductAction     // Catch:{ RuntimeException -> 0x02a9 }
            r2.<init>(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            java.lang.String r0 = "id"
            java.lang.Object r0 = r1.get(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            if (r0 == 0) goto L_0x020e
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            r2.setTransactionId(r0)     // Catch:{ RuntimeException -> 0x02a9 }
        L_0x020e:
            java.lang.String r0 = "affiliation"
            java.lang.Object r0 = r1.get(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            if (r0 == 0) goto L_0x021d
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            r2.setTransactionAffiliation(r0)     // Catch:{ RuntimeException -> 0x02a9 }
        L_0x021d:
            java.lang.String r0 = "coupon"
            java.lang.Object r0 = r1.get(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            if (r0 == 0) goto L_0x022c
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            r2.setTransactionCouponCode(r0)     // Catch:{ RuntimeException -> 0x02a9 }
        L_0x022c:
            java.lang.String r0 = "list"
            java.lang.Object r0 = r1.get(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            if (r0 == 0) goto L_0x023b
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            r2.setProductActionList(r0)     // Catch:{ RuntimeException -> 0x02a9 }
        L_0x023b:
            java.lang.String r0 = "option"
            java.lang.Object r0 = r1.get(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            if (r0 == 0) goto L_0x024a
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            r2.setCheckoutOptions(r0)     // Catch:{ RuntimeException -> 0x02a9 }
        L_0x024a:
            java.lang.String r0 = "revenue"
            java.lang.Object r0 = r1.get(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            if (r0 == 0) goto L_0x025d
            java.lang.Double r0 = zzM(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            double r6 = r0.doubleValue()     // Catch:{ RuntimeException -> 0x02a9 }
            r2.setTransactionRevenue(r6)     // Catch:{ RuntimeException -> 0x02a9 }
        L_0x025d:
            java.lang.String r0 = "tax"
            java.lang.Object r0 = r1.get(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            if (r0 == 0) goto L_0x0270
            java.lang.Double r0 = zzM(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            double r6 = r0.doubleValue()     // Catch:{ RuntimeException -> 0x02a9 }
            r2.setTransactionTax(r6)     // Catch:{ RuntimeException -> 0x02a9 }
        L_0x0270:
            java.lang.String r0 = "shipping"
            java.lang.Object r0 = r1.get(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            if (r0 == 0) goto L_0x0283
            java.lang.Double r0 = zzM(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            double r6 = r0.doubleValue()     // Catch:{ RuntimeException -> 0x02a9 }
            r2.setTransactionShipping(r6)     // Catch:{ RuntimeException -> 0x02a9 }
        L_0x0283:
            java.lang.String r0 = "step"
            java.lang.Object r0 = r1.get(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            if (r0 == 0) goto L_0x0296
            java.lang.Integer r0 = zzN(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            int r0 = r0.intValue()     // Catch:{ RuntimeException -> 0x02a9 }
            r2.setCheckoutStep(r0)     // Catch:{ RuntimeException -> 0x02a9 }
        L_0x0296:
            r0 = r2
        L_0x0297:
            r5.setProductAction(r0)     // Catch:{ RuntimeException -> 0x02a9 }
        L_0x029a:
            java.util.Map r0 = r5.build()
            r4.send(r0)
        L_0x02a1:
            return
        L_0x02a2:
            com.google.android.gms.analytics.ecommerce.ProductAction r1 = new com.google.android.gms.analytics.ecommerce.ProductAction     // Catch:{ RuntimeException -> 0x02a9 }
            r1.<init>(r0)     // Catch:{ RuntimeException -> 0x02a9 }
            r0 = r1
            goto L_0x0297
        L_0x02a9:
            r0 = move-exception
            java.lang.String r1 = "Failed to extract a product action from DataLayer. "
            java.lang.String r0 = r0.getMessage()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            int r2 = r0.length()
            if (r2 == 0) goto L_0x02c2
            java.lang.String r0 = r1.concat(r0)
        L_0x02be:
            com.google.android.gms.tagmanager.zzdj.e(r0)
            goto L_0x029a
        L_0x02c2:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r1)
            goto L_0x02be
        L_0x02c8:
            java.lang.String r0 = zzbHo
            boolean r0 = zzg(r9, r0)
            if (r0 == 0) goto L_0x02e0
            java.lang.String r0 = zzbHs
            java.lang.Object r0 = r9.get(r0)
            com.google.android.gms.internal.zzbr r0 = (com.google.android.gms.internal.zzbr) r0
            java.util.Map r0 = r8.zzi(r0)
            r4.send(r0)
            goto L_0x02a1
        L_0x02e0:
            java.lang.String r0 = zzbHt
            boolean r0 = zzg(r9, r0)
            if (r0 == 0) goto L_0x02ec
            r8.zza(r4, r9)
            goto L_0x02a1
        L_0x02ec:
            java.lang.String r0 = "Ignoring unknown tag."
            com.google.android.gms.tagmanager.zzdj.zzaT(r0)
            goto L_0x02a1
        L_0x02f2:
            r0 = r1
            goto L_0x00d7
        L_0x02f5:
            r3 = r1
            goto L_0x0045
        L_0x02f8:
            r0 = r1
            goto L_0x0044
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzgl.zzq(java.util.Map):void");
    }
}
