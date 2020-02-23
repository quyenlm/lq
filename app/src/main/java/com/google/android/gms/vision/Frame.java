package com.google.android.gms.vision;

import android.graphics.Bitmap;
import android.graphics.Color;
import java.nio.ByteBuffer;

public class Frame {
    public static final int ROTATION_0 = 0;
    public static final int ROTATION_180 = 2;
    public static final int ROTATION_270 = 3;
    public static final int ROTATION_90 = 1;
    /* access modifiers changed from: private */
    public Bitmap mBitmap;
    private Metadata zzbMV;
    /* access modifiers changed from: private */
    public ByteBuffer zzbMW;

    public static class Builder {
        private Frame zzbMX = new Frame();

        public Frame build() {
            if (this.zzbMX.zzbMW != null || this.zzbMX.mBitmap != null) {
                return this.zzbMX;
            }
            throw new IllegalStateException("Missing image data.  Call either setBitmap or setImageData to specify the image");
        }

        public Builder setBitmap(Bitmap bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Bitmap unused = this.zzbMX.mBitmap = bitmap;
            Metadata metadata = this.zzbMX.getMetadata();
            int unused2 = metadata.zzrW = width;
            int unused3 = metadata.zzrX = height;
            return this;
        }

        public Builder setId(int i) {
            int unused = this.zzbMX.getMetadata().mId = i;
            return this;
        }

        public Builder setImageData(ByteBuffer byteBuffer, int i, int i2, int i3) {
            if (byteBuffer == null) {
                throw new IllegalArgumentException("Null image data supplied.");
            } else if (byteBuffer.capacity() < i * i2) {
                throw new IllegalArgumentException("Invalid image data size.");
            } else {
                switch (i3) {
                    case 16:
                    case 17:
                    case 842094169:
                        ByteBuffer unused = this.zzbMX.zzbMW = byteBuffer;
                        Metadata metadata = this.zzbMX.getMetadata();
                        int unused2 = metadata.zzrW = i;
                        int unused3 = metadata.zzrX = i2;
                        int unused4 = metadata.format = i3;
                        return this;
                    default:
                        throw new IllegalArgumentException(new StringBuilder(37).append("Unsupported image format: ").append(i3).toString());
                }
            }
        }

        public Builder setRotation(int i) {
            int unused = this.zzbMX.getMetadata().zzOa = i;
            return this;
        }

        public Builder setTimestampMillis(long j) {
            long unused = this.zzbMX.getMetadata().zzbcV = j;
            return this;
        }
    }

    public static class Metadata {
        /* access modifiers changed from: private */
        public int format = -1;
        /* access modifiers changed from: private */
        public int mId;
        /* access modifiers changed from: private */
        public int zzOa;
        /* access modifiers changed from: private */
        public long zzbcV;
        /* access modifiers changed from: private */
        public int zzrW;
        /* access modifiers changed from: private */
        public int zzrX;

        public Metadata() {
        }

        public Metadata(Metadata metadata) {
            this.zzrW = metadata.getWidth();
            this.zzrX = metadata.getHeight();
            this.mId = metadata.getId();
            this.zzbcV = metadata.getTimestampMillis();
            this.zzOa = metadata.getRotation();
        }

        public int getFormat() {
            return this.format;
        }

        public int getHeight() {
            return this.zzrX;
        }

        public int getId() {
            return this.mId;
        }

        public int getRotation() {
            return this.zzOa;
        }

        public long getTimestampMillis() {
            return this.zzbcV;
        }

        public int getWidth() {
            return this.zzrW;
        }

        public final void zzDN() {
            if (this.zzOa % 2 != 0) {
                int i = this.zzrW;
                this.zzrW = this.zzrX;
                this.zzrX = i;
            }
            this.zzOa = 0;
        }
    }

    private Frame() {
        this.zzbMV = new Metadata();
        this.zzbMW = null;
        this.mBitmap = null;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public ByteBuffer getGrayscaleImageData() {
        if (this.mBitmap == null) {
            return this.zzbMW;
        }
        int width = this.mBitmap.getWidth();
        int height = this.mBitmap.getHeight();
        int[] iArr = new int[(width * height)];
        this.mBitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        byte[] bArr = new byte[(width * height)];
        for (int i = 0; i < iArr.length; i++) {
            bArr[i] = (byte) ((int) ((((float) Color.red(iArr[i])) * 0.299f) + (((float) Color.green(iArr[i])) * 0.587f) + (((float) Color.blue(iArr[i])) * 0.114f)));
        }
        return ByteBuffer.wrap(bArr);
    }

    public Metadata getMetadata() {
        return this.zzbMV;
    }
}
