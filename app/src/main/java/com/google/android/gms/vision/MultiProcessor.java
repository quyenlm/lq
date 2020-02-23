package com.google.android.gms.vision;

import android.util.SparseArray;
import com.google.android.gms.vision.Detector;
import java.util.HashSet;

public class MultiProcessor<T> implements Detector.Processor<T> {
    /* access modifiers changed from: private */
    public int zzbMR;
    /* access modifiers changed from: private */
    public Factory<T> zzbNd;
    private SparseArray<zza> zzbNe;

    public static class Builder<T> {
        private MultiProcessor<T> zzbNf = new MultiProcessor<>();

        public Builder(Factory<T> factory) {
            if (factory == null) {
                throw new IllegalArgumentException("No factory supplied.");
            }
            Factory unused = this.zzbNf.zzbNd = factory;
        }

        public MultiProcessor<T> build() {
            return this.zzbNf;
        }

        public Builder<T> setMaxGapFrames(int i) {
            if (i < 0) {
                throw new IllegalArgumentException(new StringBuilder(28).append("Invalid max gap: ").append(i).toString());
            }
            int unused = this.zzbNf.zzbMR = i;
            return this;
        }
    }

    public interface Factory<T> {
        Tracker<T> create(T t);
    }

    class zza {
        /* access modifiers changed from: private */
        public Tracker<T> zzbMQ;
        /* access modifiers changed from: private */
        public int zzbMU;

        private zza(MultiProcessor multiProcessor) {
            this.zzbMU = 0;
        }

        static /* synthetic */ int zzb(zza zza) {
            int i = zza.zzbMU;
            zza.zzbMU = i + 1;
            return i;
        }
    }

    private MultiProcessor() {
        this.zzbNe = new SparseArray<>();
        this.zzbMR = 3;
    }

    private final void zza(Detector.Detections<T> detections) {
        SparseArray<T> detectedItems = detections.getDetectedItems();
        for (int i = 0; i < detectedItems.size(); i++) {
            int keyAt = detectedItems.keyAt(i);
            T valueAt = detectedItems.valueAt(i);
            zza zza2 = this.zzbNe.get(keyAt);
            int unused = zza2.zzbMU = 0;
            zza2.zzbMQ.onUpdate(detections, valueAt);
        }
    }

    public void receiveDetections(Detector.Detections<T> detections) {
        SparseArray<T> detectedItems = detections.getDetectedItems();
        for (int i = 0; i < detectedItems.size(); i++) {
            int keyAt = detectedItems.keyAt(i);
            T valueAt = detectedItems.valueAt(i);
            if (this.zzbNe.get(keyAt) == null) {
                zza zza2 = new zza();
                Tracker unused = zza2.zzbMQ = this.zzbNd.create(valueAt);
                zza2.zzbMQ.onNewItem(keyAt, valueAt);
                this.zzbNe.append(keyAt, zza2);
            }
        }
        SparseArray<T> detectedItems2 = detections.getDetectedItems();
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i2 = 0; i2 < this.zzbNe.size(); i2++) {
            int keyAt2 = this.zzbNe.keyAt(i2);
            if (detectedItems2.get(keyAt2) == null) {
                zza valueAt2 = this.zzbNe.valueAt(i2);
                zza.zzb(valueAt2);
                if (valueAt2.zzbMU >= this.zzbMR) {
                    valueAt2.zzbMQ.onDone();
                    hashSet.add(Integer.valueOf(keyAt2));
                } else {
                    valueAt2.zzbMQ.onMissing(detections);
                }
            }
        }
        for (Integer intValue : hashSet) {
            this.zzbNe.delete(intValue.intValue());
        }
        zza(detections);
    }

    public void release() {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < this.zzbNe.size()) {
                this.zzbNe.valueAt(i2).zzbMQ.onDone();
                i = i2 + 1;
            } else {
                this.zzbNe.clear();
                return;
            }
        }
    }
}
