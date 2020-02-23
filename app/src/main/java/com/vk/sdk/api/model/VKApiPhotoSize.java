package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.tencent.smtt.sdk.TbsListener;
import org.json.JSONException;
import org.json.JSONObject;

public class VKApiPhotoSize extends VKApiModel implements Comparable<VKApiPhotoSize>, Parcelable, Identifiable {
    public static Parcelable.Creator<VKApiPhotoSize> CREATOR = new Parcelable.Creator<VKApiPhotoSize>() {
        public VKApiPhotoSize createFromParcel(Parcel source) {
            return new VKApiPhotoSize(source);
        }

        public VKApiPhotoSize[] newArray(int size) {
            return new VKApiPhotoSize[size];
        }
    };
    public static final char M = 'm';
    public static final char O = 'o';
    public static final char P = 'p';
    public static final char Q = 'q';
    public static final char S = 's';
    public static final char W = 'w';
    public static final char X = 'x';
    public static final char Y = 'y';
    public static final char Z = 'z';
    public int height;
    public String src;
    public char type;
    public int width;

    private VKApiPhotoSize() {
    }

    private VKApiPhotoSize(Parcel in) {
        this.src = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.type = (char) in.readInt();
    }

    public int compareTo(VKApiPhotoSize another) {
        if (this.width < another.width) {
            return -1;
        }
        return this.width == another.width ? 0 : 1;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.src);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeInt(this.type);
    }

    public int getId() {
        return 0;
    }

    public VKApiPhotoSize(JSONObject from) throws JSONException {
        parse(from, 0, 0);
    }

    public static VKApiPhotoSize parse(JSONObject source, int originalWidth, int originalHeight) {
        VKApiPhotoSize result = new VKApiPhotoSize();
        result.src = source.optString("src");
        result.width = source.optInt("width");
        result.height = source.optInt("height");
        String type2 = source.optString("type");
        if (!TextUtils.isEmpty(type2)) {
            result.type = type2.charAt(0);
        }
        if (result.width == 0 || result.height == 0) {
            fillDimensions(result, originalWidth, originalHeight);
        }
        return result;
    }

    private static void fillDimensions(VKApiPhotoSize result, int originalWidth, int originalHeight) {
        float ratio = ((float) originalWidth) / ((float) originalHeight);
        switch (result.type) {
            case 'm':
                fillDimensionSMXY(result, ratio, Math.min(originalWidth, 130));
                return;
            case 'o':
                fillDimensionOPQ(result, ratio, Math.min(originalWidth, 130));
                return;
            case 'p':
                fillDimensionOPQ(result, ratio, Math.min(originalWidth, 200));
                return;
            case 'q':
                fillDimensionOPQ(result, ratio, Math.min(originalWidth, 320));
                return;
            case 's':
                fillDimensionSMXY(result, ratio, Math.min(originalWidth, 75));
                return;
            case 'w':
                fillDimensionZW(result, ratio, Math.min(originalWidth, 2560), Math.min(originalHeight, 2048));
                return;
            case TbsListener.ErrorCode.DOWNLOAD_HAS_LOCAL_TBS_ERROR:
                fillDimensionSMXY(result, ratio, Math.min(originalWidth, 604));
                return;
            case TbsListener.ErrorCode.THREAD_INIT_ERROR:
                fillDimensionSMXY(result, ratio, Math.min(originalWidth, 807));
                return;
            case TbsListener.ErrorCode.DOWNLOAD_HAS_COPY_TBS_ERROR:
                fillDimensionZW(result, ratio, Math.min(originalWidth, 1280), Math.min(originalHeight, 1024));
                return;
            default:
                return;
        }
    }

    private static void fillDimensionSMXY(VKApiPhotoSize result, float ratio, int width2) {
        result.width = width2;
        result.height = (int) Math.ceil((double) (((float) result.width) / ratio));
    }

    private static void fillDimensionOPQ(VKApiPhotoSize result, float ratio, int width2) {
        fillDimensionSMXY(result, Math.min(1.5f, ratio), width2);
    }

    private static void fillDimensionZW(VKApiPhotoSize result, float ratio, int allowedWidth, int allowedHeight) {
        if (ratio > 1.0f) {
            result.width = allowedWidth;
            result.height = (int) (((float) result.width) / ratio);
            return;
        }
        result.height = allowedHeight;
        result.width = (int) (((float) result.height) * ratio);
    }

    public static VKApiPhotoSize create(String url, int width2, int height2) {
        VKApiPhotoSize result = new VKApiPhotoSize();
        result.src = url;
        result.width = width2;
        result.height = height2;
        float ratio = ((float) width2) / ((float) height2);
        if (width2 <= 75) {
            result.type = S;
        } else if (width2 <= 130) {
            result.type = ratio <= 1.5f ? O : M;
        } else if (width2 <= 200 && ratio <= 1.5f) {
            result.type = P;
        } else if (width2 <= 320 && ratio <= 1.5f) {
            result.type = Q;
        } else if (width2 <= 604) {
            result.type = X;
        } else if (width2 <= 807) {
            result.type = Y;
        } else if (width2 <= 1280 && height2 <= 1024) {
            result.type = Z;
        } else if (width2 <= 2560 && height2 <= 2048) {
            result.type = W;
        }
        return result;
    }

    public static VKApiPhotoSize create(String url, char type2, int originalWidth, int originalHeight) {
        VKApiPhotoSize result = new VKApiPhotoSize();
        result.src = url;
        result.type = type2;
        fillDimensions(result, originalWidth, originalHeight);
        return result;
    }

    public static VKApiPhotoSize create(String url, int dimension) {
        return create(url, dimension, dimension);
    }
}
