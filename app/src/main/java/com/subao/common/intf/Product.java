package com.subao.common.intf;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.JsonReader;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.subao.common.e;
import com.subao.common.e.n;
import com.subao.common.n.f;
import com.tencent.component.net.download.multiplex.download.DownloadDBHelper;
import com.tencent.midas.oversea.api.UnityPayHelper;

public class Product implements Parcelable {
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel parcel) {
            return new Product(parcel);
        }

        public Product[] newArray(int i) {
            return new Product[i];
        }
    };
    public static final int TYPE_MONTH = 1;
    public static final int TYPE_QUARTER = 2;
    public static final int TYPE_TRIAL = 3;
    private final int accelDays;
    @Nullable
    private final String description;
    private final int flag;
    @NonNull
    private final String id;
    @NonNull
    private final String name;
    private final int price;

    public Product(@NonNull String str, @NonNull String str2, int i, @Nullable String str3, int i2, int i3) {
        this.id = str;
        this.name = str2;
        this.price = i;
        this.description = str3;
        this.accelDays = i2;
        this.flag = i3;
    }

    protected Product(@NonNull Parcel parcel) {
        this.id = parcel.readString();
        this.name = parcel.readString();
        this.price = parcel.readInt();
        this.description = parcel.readString();
        this.accelDays = parcel.readInt();
        this.flag = parcel.readInt();
    }

    @Nullable
    public static Product createFromJson(@NonNull JsonReader jsonReader) {
        float f = 0.0f;
        jsonReader.beginObject();
        int i = 0;
        int i2 = 0;
        String str = null;
        String str2 = null;
        String str3 = null;
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            if (UnityPayHelper.PRODUCTID.equals(nextName)) {
                str3 = f.a(jsonReader);
            } else if ("productName".equals(nextName)) {
                str2 = f.a(jsonReader);
            } else if ("description".equals(nextName)) {
                str = f.a(jsonReader);
            } else if (FirebaseAnalytics.Param.PRICE.equals(nextName)) {
                f = (float) jsonReader.nextDouble();
            } else if ("accelDays".equals(nextName)) {
                i2 = jsonReader.nextInt();
            } else if (DownloadDBHelper.FLAG.equals(nextName)) {
                i = jsonReader.nextInt();
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        if (TextUtils.isEmpty(str3) || TextUtils.isEmpty(str2)) {
            return null;
        }
        return new Product(str3, str2, Math.round(f * 100.0f), str, i2, i);
    }

    @NonNull
    public String getId() {
        return this.id;
    }

    @NonNull
    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public int getOriginalPrice() {
        return (this.price * 10) / 9;
    }

    public int getAccelDays() {
        return this.accelDays;
    }

    public int getType() {
        return this.flag & 255;
    }

    @Nullable
    public String getDescription() {
        return this.description;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeInt(this.price);
        parcel.writeString(this.description);
        parcel.writeInt(this.accelDays);
        parcel.writeInt(this.flag);
    }

    public String toString() {
        return String.format(n.b, "[id=%s, %s, %d, price=%d]", new Object[]{this.id, this.name, Integer.valueOf(this.flag), Integer.valueOf(this.price)});
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Product)) {
            return false;
        }
        Product product = (Product) obj;
        if (this.flag != product.flag || this.accelDays != product.accelDays || this.price != product.price || !e.a(this.id, product.id) || !e.a(this.name, product.name) || !e.a(this.description, product.description)) {
            z = false;
        }
        return z;
    }
}
