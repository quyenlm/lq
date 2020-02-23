package com.vk.sdk.api.photo;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.component.net.download.multiplex.http.ContentType;
import com.vk.sdk.VKObject;

public class VKImageParameters extends VKObject implements Parcelable {
    public static final Parcelable.Creator<VKImageParameters> CREATOR = new Parcelable.Creator<VKImageParameters>() {
        public VKImageParameters createFromParcel(Parcel source) {
            return new VKImageParameters(source);
        }

        public VKImageParameters[] newArray(int size) {
            return new VKImageParameters[size];
        }
    };
    public VKImageType mImageType;
    public float mJpegQuality;

    enum VKImageType {
        Jpg,
        Png
    }

    public static VKImageParameters pngImage() {
        VKImageParameters result = new VKImageParameters();
        result.mImageType = VKImageType.Png;
        return result;
    }

    public static VKImageParameters jpgImage(float quality) {
        VKImageParameters result = new VKImageParameters();
        result.mImageType = VKImageType.Jpg;
        result.mJpegQuality = quality;
        return result;
    }

    public String fileExtension() {
        switch (this.mImageType) {
            case Jpg:
                return "jpg";
            case Png:
                return ContentType.SUBTYPE_PNG;
            default:
                return TransferTable.COLUMN_FILE;
        }
    }

    public String mimeType() {
        switch (this.mImageType) {
            case Jpg:
                return "image/jpeg";
            case Png:
                return "image/png";
            default:
                return "application/octet-stream";
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mImageType == null ? -1 : this.mImageType.ordinal());
        dest.writeFloat(this.mJpegQuality);
    }

    public VKImageParameters() {
        this.mImageType = VKImageType.Png;
    }

    private VKImageParameters(Parcel in) {
        this.mImageType = VKImageType.Png;
        int tmpMImageType = in.readInt();
        this.mImageType = tmpMImageType == -1 ? null : VKImageType.values()[tmpMImageType];
        this.mJpegQuality = in.readFloat();
    }
}
