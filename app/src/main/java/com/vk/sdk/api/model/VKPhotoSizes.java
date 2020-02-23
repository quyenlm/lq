package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.vk.sdk.api.model.VKList;
import java.util.Collections;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public class VKPhotoSizes extends VKList<VKApiPhotoSize> implements Parcelable {
    public static Parcelable.Creator<VKPhotoSizes> CREATOR = new Parcelable.Creator<VKPhotoSizes>() {
        public VKPhotoSizes createFromParcel(Parcel source) {
            return new VKPhotoSizes(source);
        }

        public VKPhotoSizes[] newArray(int size) {
            return new VKPhotoSizes[size];
        }
    };
    private static float sQuality = 1.0f;
    private String mHeightThumb;
    private int mLastHeight;
    private int mLastWidth;
    /* access modifiers changed from: private */
    public int mOriginalHeight;
    /* access modifiers changed from: private */
    public int mOriginalWidth;
    private String mWidthThumb;
    private final VKList.Parser<VKApiPhotoSize> parser;

    public static void setQuality(float quality) {
        sQuality = quality;
    }

    public VKPhotoSizes() {
        this.mOriginalWidth = 1;
        this.mOriginalHeight = 1;
        this.parser = new VKList.Parser<VKApiPhotoSize>() {
            public VKApiPhotoSize parseObject(JSONObject source) throws Exception {
                return VKApiPhotoSize.parse(source, VKPhotoSizes.this.mOriginalWidth, VKPhotoSizes.this.mOriginalHeight);
            }
        };
    }

    public VKPhotoSizes(JSONArray from) {
        this.mOriginalWidth = 1;
        this.mOriginalHeight = 1;
        this.parser = new VKList.Parser<VKApiPhotoSize>() {
            public VKApiPhotoSize parseObject(JSONObject source) throws Exception {
                return VKApiPhotoSize.parse(source, VKPhotoSizes.this.mOriginalWidth, VKPhotoSizes.this.mOriginalHeight);
            }
        };
        fill(from);
    }

    public void fill(JSONArray from, int width, int height) {
        setOriginalDimension(width, height);
        fill(from);
    }

    public void fill(JSONArray from) {
        fill(from, this.parser);
        sort();
    }

    public String getByType(char type) {
        Iterator it = iterator();
        while (it.hasNext()) {
            VKApiPhotoSize size = (VKApiPhotoSize) it.next();
            if (size.type == type) {
                return size.src;
            }
        }
        return null;
    }

    public void setOriginalDimension(int width, int height) {
        if (width != 0) {
            this.mOriginalWidth = width;
        }
        if (height != 0) {
            this.mOriginalHeight = height;
        }
    }

    public void sort() {
        Collections.sort(this);
    }

    public String getImageForDimension(int width, int height) {
        return width >= height ? getImageForWidth(width) : getImageForHeight(height);
    }

    private String getImageForWidth(int width) {
        if ((this.mWidthThumb != null && this.mLastWidth != width) || isEmpty()) {
            return this.mWidthThumb;
        }
        this.mLastWidth = width;
        this.mWidthThumb = null;
        int width2 = (int) (((float) width) * sQuality);
        Iterator it = iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            VKApiPhotoSize size = (VKApiPhotoSize) it.next();
            if (size.width >= width2) {
                this.mWidthThumb = size.src;
                break;
            }
        }
        return this.mWidthThumb;
    }

    private String getImageForHeight(int height) {
        if ((this.mHeightThumb != null && this.mLastHeight != height) || isEmpty()) {
            return this.mHeightThumb;
        }
        this.mLastHeight = height;
        this.mHeightThumb = null;
        int height2 = (int) (((float) height) * sQuality);
        Iterator it = iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            VKApiPhotoSize size = (VKApiPhotoSize) it.next();
            if (size.height >= height2) {
                this.mHeightThumb = size.src;
                break;
            }
        }
        return this.mHeightThumb;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mOriginalWidth);
        dest.writeInt(this.mOriginalHeight);
        dest.writeString(this.mWidthThumb);
        dest.writeInt(this.mLastWidth);
    }

    private VKPhotoSizes(Parcel in) {
        super(in);
        this.mOriginalWidth = 1;
        this.mOriginalHeight = 1;
        this.parser = new VKList.Parser<VKApiPhotoSize>() {
            public VKApiPhotoSize parseObject(JSONObject source) throws Exception {
                return VKApiPhotoSize.parse(source, VKPhotoSizes.this.mOriginalWidth, VKPhotoSizes.this.mOriginalHeight);
            }
        };
        this.mOriginalWidth = in.readInt();
        this.mOriginalHeight = in.readInt();
        this.mWidthThumb = in.readString();
        this.mLastWidth = in.readInt();
    }
}
