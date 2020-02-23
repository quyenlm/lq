package com.subao.common.intf;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.JsonReader;
import java.util.ArrayList;
import java.util.List;

public class ProductList implements Parcelable {
    public static final Parcelable.Creator<ProductList> CREATOR = new Parcelable.Creator<ProductList>() {
        public ProductList createFromParcel(Parcel parcel) {
            return new ProductList(parcel);
        }

        public ProductList[] newArray(int i) {
            return new ProductList[i];
        }
    };
    private final List<Product> list;

    private ProductList(List<Product> list2) {
        this.list = list2;
    }

    protected ProductList(Parcel parcel) {
        ArrayList arrayList;
        int readInt = parcel.readInt();
        if (readInt > 0) {
            arrayList = new ArrayList(readInt);
            while (true) {
                int i = readInt - 1;
                if (readInt <= 0) {
                    break;
                }
                arrayList.add(new Product(parcel));
                readInt = i;
            }
        } else {
            arrayList = null;
        }
        this.list = arrayList;
    }

    @Nullable
    public static ProductList createFromJson(JsonReader jsonReader) {
        ProductList productList = null;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            if (productList != null || !"products".equals(jsonReader.nextName())) {
                jsonReader.skipValue();
            } else {
                productList = new ProductList(createListFromJson(jsonReader));
            }
        }
        jsonReader.endObject();
        return productList;
    }

    @NonNull
    private static List<Product> createListFromJson(JsonReader jsonReader) {
        ArrayList arrayList = new ArrayList(8);
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            arrayList.add(Product.createFromJson(jsonReader));
        }
        jsonReader.endArray();
        return arrayList;
    }

    public int getCount() {
        if (this.list == null) {
            return 0;
        }
        return this.list.size();
    }

    public Product get(int i) {
        return this.list.get(i);
    }

    @Nullable
    public Product findByType(int i) {
        if (this.list != null) {
            for (Product next : this.list) {
                if (next.getType() == i) {
                    return next;
                }
            }
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof ProductList)) {
            return false;
        }
        ProductList productList = (ProductList) obj;
        if (this.list == null) {
            if (productList.list == null || productList.list.isEmpty()) {
                return true;
            }
            return false;
        } else if (productList.list == null) {
            return this.list.isEmpty();
        } else {
            return this.list.equals(productList.list);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (this.list == null) {
            parcel.writeInt(-1);
            return;
        }
        parcel.writeInt(this.list.size());
        for (Product writeToParcel : this.list) {
            writeToParcel.writeToParcel(parcel, i);
        }
    }
}
