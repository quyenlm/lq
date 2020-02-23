package com.vk.sdk.api.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import com.vk.sdk.VKObject;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.photo.VKImageParameters;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class VKUploadImage extends VKObject implements Parcelable {
    public static final Parcelable.Creator<VKUploadImage> CREATOR = new Parcelable.Creator<VKUploadImage>() {
        public VKUploadImage createFromParcel(Parcel source) {
            return new VKUploadImage(source);
        }

        public VKUploadImage[] newArray(int size) {
            return new VKUploadImage[size];
        }
    };
    public final Bitmap mImageData;
    public final VKImageParameters mParameters;

    public VKUploadImage(Bitmap data, VKImageParameters params) {
        this.mImageData = data;
        this.mParameters = params;
    }

    public File getTmpFile() {
        Context ctx = VKUIHelper.getApplicationContext();
        File outputDir = null;
        if (ctx != null && ((outputDir = ctx.getExternalCacheDir()) == null || !outputDir.canWrite())) {
            outputDir = ctx.getCacheDir();
        }
        File tmpFile = null;
        try {
            tmpFile = File.createTempFile("tmpImg", String.format(".%s", new Object[]{this.mParameters.fileExtension()}), outputDir);
            FileOutputStream fos = new FileOutputStream(tmpFile);
            if (this.mParameters.mImageType == VKImageParameters.VKImageType.Png) {
                this.mImageData.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } else {
                this.mImageData.compress(Bitmap.CompressFormat.JPEG, (int) (this.mParameters.mJpegQuality * 100.0f), fos);
            }
            fos.close();
        } catch (IOException e) {
        }
        return tmpFile;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mImageData, 0);
        dest.writeParcelable(this.mParameters, 0);
    }

    private VKUploadImage(Parcel in) {
        this.mImageData = (Bitmap) in.readParcelable(Bitmap.class.getClassLoader());
        this.mParameters = (VKImageParameters) in.readParcelable(VKImageParameters.class.getClassLoader());
    }
}
