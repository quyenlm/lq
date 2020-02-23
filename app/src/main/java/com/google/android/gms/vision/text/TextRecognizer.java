package com.google.android.gms.vision.text;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.SparseArray;
import com.beetalk.sdk.ShareConstants;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fk;
import com.google.android.gms.internal.fm;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.fr;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.tencent.rtmp2.TXLiveConstants;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public final class TextRecognizer extends Detector<TextBlock> {
    private final fq zzbNU;

    public static class Builder {
        private Context mContext;
        private fr zzbNV = new fr();

        public Builder(Context context) {
            this.mContext = context;
        }

        public TextRecognizer build() {
            return new TextRecognizer(new fq(this.mContext, this.zzbNV));
        }
    }

    private TextRecognizer() {
        throw new IllegalStateException("Default constructor called");
    }

    private TextRecognizer(fq fqVar) {
        this.zzbNU = fqVar;
    }

    private static SparseArray<TextBlock> zza(fk[] fkVarArr) {
        SparseArray sparseArray = new SparseArray();
        for (fk fkVar : fkVarArr) {
            SparseArray sparseArray2 = (SparseArray) sparseArray.get(fkVar.zzbOf);
            if (sparseArray2 == null) {
                sparseArray2 = new SparseArray();
                sparseArray.append(fkVar.zzbOf, sparseArray2);
            }
            sparseArray2.append(fkVar.zzbOg, fkVar);
        }
        SparseArray<TextBlock> sparseArray3 = new SparseArray<>(sparseArray.size());
        for (int i = 0; i < sparseArray.size(); i++) {
            sparseArray3.append(sparseArray.keyAt(i), new TextBlock((SparseArray) sparseArray.valueAt(i)));
        }
        return sparseArray3;
    }

    public final SparseArray<TextBlock> detect(Frame frame) {
        byte[] bArr;
        Bitmap decodeByteArray;
        Rect rect;
        int i;
        fm fmVar = new fm(new Rect());
        if (frame == null) {
            throw new IllegalArgumentException("No frame supplied.");
        }
        fc zzc = fc.zzc(frame);
        if (frame.getBitmap() != null) {
            decodeByteArray = frame.getBitmap();
        } else {
            Frame.Metadata metadata = frame.getMetadata();
            ByteBuffer grayscaleImageData = frame.getGrayscaleImageData();
            int format = metadata.getFormat();
            int i2 = zzc.width;
            int i3 = zzc.height;
            if (!grayscaleImageData.hasArray() || grayscaleImageData.arrayOffset() != 0) {
                bArr = new byte[grayscaleImageData.capacity()];
                grayscaleImageData.get(bArr);
            } else {
                bArr = grayscaleImageData.array();
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new YuvImage(bArr, format, i2, i3, (int[]) null).compressToJpeg(new Rect(0, 0, i2, i3), 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            decodeByteArray = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        int width = decodeByteArray.getWidth();
        int height = decodeByteArray.getHeight();
        if (zzc.rotation != 0) {
            Matrix matrix = new Matrix();
            switch (zzc.rotation) {
                case 0:
                    i = 0;
                    break;
                case 1:
                    i = 90;
                    break;
                case 2:
                    i = ShareConstants.ERROR_CODE.GG_RESULT_UNKNOWN_ERROR;
                    break;
                case 3:
                    i = TXLiveConstants.RENDER_ROTATION_LANDSCAPE;
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported rotation degree.");
            }
            matrix.postRotate((float) i);
            decodeByteArray = Bitmap.createBitmap(decodeByteArray, 0, 0, width, height, matrix, false);
        }
        if (zzc.rotation == 1 || zzc.rotation == 3) {
            zzc.width = height;
            zzc.height = width;
        }
        if (!fmVar.zzbOh.isEmpty()) {
            Rect rect2 = fmVar.zzbOh;
            int width2 = frame.getMetadata().getWidth();
            int height2 = frame.getMetadata().getHeight();
            switch (zzc.rotation) {
                case 1:
                    rect = new Rect(height2 - rect2.bottom, rect2.left, height2 - rect2.top, rect2.right);
                    break;
                case 2:
                    rect = new Rect(width2 - rect2.right, height2 - rect2.bottom, width2 - rect2.left, height2 - rect2.top);
                    break;
                case 3:
                    rect = new Rect(rect2.top, width2 - rect2.right, rect2.bottom, width2 - rect2.left);
                    break;
                default:
                    rect = rect2;
                    break;
            }
            fmVar.zzbOh.set(rect);
        }
        zzc.rotation = 0;
        return zza(this.zzbNU.zza(decodeByteArray, zzc, fmVar));
    }

    public final boolean isOperational() {
        return this.zzbNU.isOperational();
    }

    public final void release() {
        super.release();
        this.zzbNU.zzDQ();
    }
}
