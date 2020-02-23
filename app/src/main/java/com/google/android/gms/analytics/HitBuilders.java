package com.google.android.gms.analytics;

import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.internal.zzaob;
import com.google.android.gms.internal.zzaos;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HitBuilders {

    public static class EventBuilder extends HitBuilder<EventBuilder> {
        public EventBuilder() {
            set("&t", "event");
        }

        public EventBuilder(String str, String str2) {
            this();
            setCategory(str);
            setAction(str2);
        }

        public EventBuilder setAction(String str) {
            set("&ea", str);
            return this;
        }

        public EventBuilder setCategory(String str) {
            set("&ec", str);
            return this;
        }

        public EventBuilder setLabel(String str) {
            set("&el", str);
            return this;
        }

        public EventBuilder setValue(long j) {
            set("&ev", Long.toString(j));
            return this;
        }
    }

    public static class ExceptionBuilder extends HitBuilder<ExceptionBuilder> {
        public ExceptionBuilder() {
            set("&t", "exception");
        }

        public ExceptionBuilder setDescription(String str) {
            set("&exd", str);
            return this;
        }

        public ExceptionBuilder setFatal(boolean z) {
            set("&exf", zzaos.zzI(z));
            return this;
        }
    }

    public static class HitBuilder<T extends HitBuilder> {
        private Map<String, String> zzadJ = new HashMap();
        private ProductAction zzadK;
        private Map<String, List<Product>> zzadL = new HashMap();
        private List<Promotion> zzadM = new ArrayList();
        private List<Product> zzadN = new ArrayList();

        protected HitBuilder() {
        }

        private final T zzk(String str, String str2) {
            if (str2 != null) {
                this.zzadJ.put(str, str2);
            }
            return this;
        }

        public T addImpression(Product product, String str) {
            if (product == null) {
                zzaob.zzaT("product should be non-null");
            } else {
                if (str == null) {
                    str = "";
                }
                if (!this.zzadL.containsKey(str)) {
                    this.zzadL.put(str, new ArrayList());
                }
                this.zzadL.get(str).add(product);
            }
            return this;
        }

        public T addProduct(Product product) {
            if (product == null) {
                zzaob.zzaT("product should be non-null");
            } else {
                this.zzadN.add(product);
            }
            return this;
        }

        public T addPromotion(Promotion promotion) {
            if (promotion == null) {
                zzaob.zzaT("promotion should be non-null");
            } else {
                this.zzadM.add(promotion);
            }
            return this;
        }

        public Map<String, String> build() {
            HashMap hashMap = new HashMap(this.zzadJ);
            if (this.zzadK != null) {
                hashMap.putAll(this.zzadK.build());
            }
            int i = 1;
            for (Promotion zzbl : this.zzadM) {
                hashMap.putAll(zzbl.zzbl(zzf.zzI(i)));
                i++;
            }
            int i2 = 1;
            for (Product zzbl2 : this.zzadN) {
                hashMap.putAll(zzbl2.zzbl(zzf.zzG(i2)));
                i2++;
            }
            int i3 = 1;
            for (Map.Entry next : this.zzadL.entrySet()) {
                String zzL = zzf.zzL(i3);
                int i4 = 1;
                for (Product product : (List) next.getValue()) {
                    String valueOf = String.valueOf(zzL);
                    String valueOf2 = String.valueOf(zzf.zzK(i4));
                    hashMap.putAll(product.zzbl(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)));
                    i4++;
                }
                if (!TextUtils.isEmpty((CharSequence) next.getKey())) {
                    String valueOf3 = String.valueOf(zzL);
                    String valueOf4 = String.valueOf("nm");
                    hashMap.put(valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3), (String) next.getKey());
                }
                i3++;
            }
            return hashMap;
        }

        /* access modifiers changed from: protected */
        public String get(String str) {
            return this.zzadJ.get(str);
        }

        public final T set(String str, String str2) {
            if (str != null) {
                this.zzadJ.put(str, str2);
            } else {
                zzaob.zzaT("HitBuilder.set() called with a null paramName.");
            }
            return this;
        }

        public final T setAll(Map<String, String> map) {
            if (map != null) {
                this.zzadJ.putAll(new HashMap(map));
            }
            return this;
        }

        public T setCampaignParamsFromUrl(String str) {
            String zzbD = zzaos.zzbD(str);
            if (!TextUtils.isEmpty(zzbD)) {
                Map<String, String> zzbB = zzaos.zzbB(zzbD);
                zzk("&cc", zzbB.get("utm_content"));
                zzk("&cm", zzbB.get("utm_medium"));
                zzk("&cn", zzbB.get("utm_campaign"));
                zzk("&cs", zzbB.get("utm_source"));
                zzk("&ck", zzbB.get("utm_term"));
                zzk("&ci", zzbB.get("utm_id"));
                zzk("&anid", zzbB.get("anid"));
                zzk("&gclid", zzbB.get("gclid"));
                zzk("&dclid", zzbB.get("dclid"));
                zzk("&aclid", zzbB.get(FirebaseAnalytics.Param.ACLID));
                zzk("&gmob_t", zzbB.get("gmob_t"));
            }
            return this;
        }

        public T setCustomDimension(int i, String str) {
            set(zzf.zzC(i), str);
            return this;
        }

        public T setCustomMetric(int i, float f) {
            set(zzf.zzE(i), Float.toString(f));
            return this;
        }

        /* access modifiers changed from: protected */
        public T setHitType(String str) {
            set("&t", str);
            return this;
        }

        public T setNewSession() {
            set("&sc", "start");
            return this;
        }

        public T setNonInteraction(boolean z) {
            set("&ni", zzaos.zzI(z));
            return this;
        }

        public T setProductAction(ProductAction productAction) {
            this.zzadK = productAction;
            return this;
        }

        public T setPromotionAction(String str) {
            this.zzadJ.put("&promoa", str);
            return this;
        }
    }

    public static class ScreenViewBuilder extends HitBuilder<ScreenViewBuilder> {
        public ScreenViewBuilder() {
            set("&t", "screenview");
        }
    }

    public static class SocialBuilder extends HitBuilder<SocialBuilder> {
        public SocialBuilder() {
            set("&t", NotificationCompat.CATEGORY_SOCIAL);
        }

        public SocialBuilder setAction(String str) {
            set("&sa", str);
            return this;
        }

        public SocialBuilder setNetwork(String str) {
            set("&sn", str);
            return this;
        }

        public SocialBuilder setTarget(String str) {
            set("&st", str);
            return this;
        }
    }

    public static class TimingBuilder extends HitBuilder<TimingBuilder> {
        public TimingBuilder() {
            set("&t", "timing");
        }

        public TimingBuilder(String str, String str2, long j) {
            this();
            setVariable(str2);
            setValue(j);
            setCategory(str);
        }

        public TimingBuilder setCategory(String str) {
            set("&utc", str);
            return this;
        }

        public TimingBuilder setLabel(String str) {
            set("&utl", str);
            return this;
        }

        public TimingBuilder setValue(long j) {
            set("&utt", Long.toString(j));
            return this;
        }

        public TimingBuilder setVariable(String str) {
            set("&utv", str);
            return this;
        }
    }

    @Deprecated
    public static class AppViewBuilder extends HitBuilder<AppViewBuilder> {
        public AppViewBuilder() {
            set("&t", "screenview");
        }
    }

    @Deprecated
    public static class ItemBuilder extends HitBuilder<ItemBuilder> {
        public ItemBuilder() {
            set("&t", "item");
        }

        public ItemBuilder setCategory(String str) {
            set("&iv", str);
            return this;
        }

        public ItemBuilder setCurrencyCode(String str) {
            set("&cu", str);
            return this;
        }

        public ItemBuilder setName(String str) {
            set("&in", str);
            return this;
        }

        public ItemBuilder setPrice(double d) {
            set("&ip", Double.toString(d));
            return this;
        }

        public ItemBuilder setQuantity(long j) {
            set("&iq", Long.toString(j));
            return this;
        }

        public ItemBuilder setSku(String str) {
            set("&ic", str);
            return this;
        }

        public ItemBuilder setTransactionId(String str) {
            set("&ti", str);
            return this;
        }
    }

    @Deprecated
    public static class TransactionBuilder extends HitBuilder<TransactionBuilder> {
        public TransactionBuilder() {
            set("&t", "transaction");
        }

        public TransactionBuilder setAffiliation(String str) {
            set("&ta", str);
            return this;
        }

        public TransactionBuilder setCurrencyCode(String str) {
            set("&cu", str);
            return this;
        }

        public TransactionBuilder setRevenue(double d) {
            set("&tr", Double.toString(d));
            return this;
        }

        public TransactionBuilder setShipping(double d) {
            set("&ts", Double.toString(d));
            return this;
        }

        public TransactionBuilder setTax(double d) {
            set("&tt", Double.toString(d));
            return this;
        }

        public TransactionBuilder setTransactionId(String str) {
            set("&ti", str);
            return this;
        }
    }
}
