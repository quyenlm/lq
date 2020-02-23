package com.google.android.gms.vision.face;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.internal.fc;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.internal.client.zza;
import com.google.android.gms.vision.zzc;
import java.nio.ByteBuffer;
import java.util.HashSet;

public final class FaceDetector extends Detector<Face> {
    public static final int ACCURATE_MODE = 1;
    public static final int ALL_CLASSIFICATIONS = 1;
    public static final int ALL_LANDMARKS = 1;
    public static final int FAST_MODE = 0;
    public static final int NO_CLASSIFICATIONS = 0;
    public static final int NO_LANDMARKS = 0;
    private final Object mLock;
    private final zzc zzbNq;
    private final zza zzbNr;
    private boolean zzbNs;

    public static class Builder {
        private final Context mContext;
        private int zzaLU = 0;
        private int zzbNt = 0;
        private boolean zzbNu = false;
        private int zzbNv = 0;
        private boolean zzbNw = true;
        private float zzbNx = -1.0f;

        public Builder(Context context) {
            this.mContext = context;
        }

        public FaceDetector build() {
            com.google.android.gms.vision.face.internal.client.zzc zzc = new com.google.android.gms.vision.face.internal.client.zzc();
            zzc.mode = this.zzaLU;
            zzc.zzbNG = this.zzbNt;
            zzc.zzbNH = this.zzbNv;
            zzc.zzbNI = this.zzbNu;
            zzc.zzbNJ = this.zzbNw;
            zzc.zzbNK = this.zzbNx;
            return new FaceDetector(new zza(this.mContext, zzc));
        }

        public Builder setClassificationType(int i) {
            if (i == 0 || i == 1) {
                this.zzbNv = i;
                return this;
            }
            throw new IllegalArgumentException(new StringBuilder(40).append("Invalid classification type: ").append(i).toString());
        }

        public Builder setLandmarkType(int i) {
            if (i == 0 || i == 1) {
                this.zzbNt = i;
                return this;
            }
            throw new IllegalArgumentException(new StringBuilder(34).append("Invalid landmark type: ").append(i).toString());
        }

        public Builder setMinFaceSize(float f) {
            if (f < 0.0f || f > 1.0f) {
                throw new IllegalArgumentException(new StringBuilder(47).append("Invalid proportional face size: ").append(f).toString());
            }
            this.zzbNx = f;
            return this;
        }

        public Builder setMode(int i) {
            switch (i) {
                case 0:
                case 1:
                    this.zzaLU = i;
                    return this;
                default:
                    throw new IllegalArgumentException(new StringBuilder(25).append("Invalid mode: ").append(i).toString());
            }
        }

        public Builder setProminentFaceOnly(boolean z) {
            this.zzbNu = z;
            return this;
        }

        public Builder setTrackingEnabled(boolean z) {
            this.zzbNw = z;
            return this;
        }
    }

    private FaceDetector() {
        this.zzbNq = new zzc();
        this.mLock = new Object();
        this.zzbNs = true;
        throw new IllegalStateException("Default constructor called");
    }

    private FaceDetector(zza zza) {
        this.zzbNq = new zzc();
        this.mLock = new Object();
        this.zzbNs = true;
        this.zzbNr = zza;
    }

    public final SparseArray<Face> detect(Frame frame) {
        Face[] zzb;
        if (frame == null) {
            throw new IllegalArgumentException("No frame supplied.");
        }
        ByteBuffer grayscaleImageData = frame.getGrayscaleImageData();
        synchronized (this.mLock) {
            if (!this.zzbNs) {
                throw new RuntimeException("Cannot use detector after release()");
            }
            zzb = this.zzbNr.zzb(grayscaleImageData, fc.zzc(frame));
        }
        HashSet hashSet = new HashSet();
        SparseArray<Face> sparseArray = new SparseArray<>(zzb.length);
        int i = 0;
        for (Face face : zzb) {
            int id = face.getId();
            int max = Math.max(i, id);
            if (hashSet.contains(Integer.valueOf(id))) {
                max++;
                id = max;
            }
            i = max;
            hashSet.add(Integer.valueOf(id));
            sparseArray.append(this.zzbNq.zzbL(id), face);
        }
        return sparseArray;
    }

    /* access modifiers changed from: protected */
    public final void finalize() throws Throwable {
        try {
            synchronized (this.mLock) {
                if (this.zzbNs) {
                    Log.w("FaceDetector", "FaceDetector was not released with FaceDetector.release()");
                    release();
                }
            }
        } finally {
            super.finalize();
        }
    }

    public final boolean isOperational() {
        return this.zzbNr.isOperational();
    }

    public final void release() {
        super.release();
        synchronized (this.mLock) {
            if (this.zzbNs) {
                this.zzbNr.zzDQ();
                this.zzbNs = false;
            }
        }
    }

    public final boolean setFocus(int i) {
        boolean zzbN;
        int zzbM = this.zzbNq.zzbM(i);
        synchronized (this.mLock) {
            if (!this.zzbNs) {
                throw new RuntimeException("Cannot use detector after release()");
            }
            zzbN = this.zzbNr.zzbN(zzbM);
        }
        return zzbN;
    }
}
