package com.google.android.gms.vision;

import android.util.SparseArray;
import com.google.android.gms.vision.Detector;
import java.util.ArrayList;
import java.util.List;

public class MultiDetector extends Detector<Object> {
    /* access modifiers changed from: private */
    public List<Detector<? extends Object>> zzbNb;

    public static class Builder {
        private MultiDetector zzbNc = new MultiDetector();

        public Builder add(Detector<? extends Object> detector) {
            this.zzbNc.zzbNb.add(detector);
            return this;
        }

        public MultiDetector build() {
            if (this.zzbNc.zzbNb.size() != 0) {
                return this.zzbNc;
            }
            throw new RuntimeException("No underlying detectors added to MultiDetector.");
        }
    }

    private MultiDetector() {
        this.zzbNb = new ArrayList();
    }

    public SparseArray<Object> detect(Frame frame) {
        SparseArray<Object> sparseArray = new SparseArray<>();
        for (Detector<? extends Object> detect : this.zzbNb) {
            SparseArray detect2 = detect.detect(frame);
            int i = 0;
            while (true) {
                if (i < detect2.size()) {
                    int keyAt = detect2.keyAt(i);
                    if (sparseArray.get(keyAt) != null) {
                        throw new IllegalStateException(new StringBuilder(104).append("Detection ID overlap for id = ").append(keyAt).append("  This means that one of the detectors is not using global IDs.").toString());
                    }
                    sparseArray.append(keyAt, detect2.valueAt(i));
                    i++;
                }
            }
        }
        return sparseArray;
    }

    public boolean isOperational() {
        for (Detector<? extends Object> isOperational : this.zzbNb) {
            if (!isOperational.isOperational()) {
                return false;
            }
        }
        return true;
    }

    public void receiveFrame(Frame frame) {
        for (Detector<? extends Object> receiveFrame : this.zzbNb) {
            receiveFrame.receiveFrame(frame);
        }
    }

    public void release() {
        for (Detector<? extends Object> release : this.zzbNb) {
            release.release();
        }
        this.zzbNb.clear();
    }

    public void setProcessor(Detector.Processor<Object> processor) {
        throw new UnsupportedOperationException("MultiDetector.setProcessor is not supported.  You should set a processor instance on each underlying detector instead.");
    }
}
