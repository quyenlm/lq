package com.google.android.gms.vision.face;

import android.util.SparseArray;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.FocusingProcessor;
import com.google.android.gms.vision.Tracker;

public class LargestFaceFocusingProcessor extends FocusingProcessor<Face> {

    public static class Builder {
        private LargestFaceFocusingProcessor zzbNy;

        public Builder(Detector<Face> detector, Tracker<Face> tracker) {
            this.zzbNy = new LargestFaceFocusingProcessor(detector, tracker);
        }

        public LargestFaceFocusingProcessor build() {
            return this.zzbNy;
        }

        public Builder setMaxGapFrames(int i) {
            this.zzbNy.zzbK(i);
            return this;
        }
    }

    public LargestFaceFocusingProcessor(Detector<Face> detector, Tracker<Face> tracker) {
        super(detector, tracker);
    }

    public int selectFocus(Detector.Detections<Face> detections) {
        SparseArray<Face> detectedItems = detections.getDetectedItems();
        if (detectedItems.size() == 0) {
            throw new IllegalArgumentException("No faces for selectFocus.");
        }
        int keyAt = detectedItems.keyAt(0);
        float width = detectedItems.valueAt(0).getWidth();
        int i = 1;
        while (true) {
            int i2 = i;
            if (i2 >= detectedItems.size()) {
                return keyAt;
            }
            int keyAt2 = detectedItems.keyAt(i2);
            float width2 = detectedItems.valueAt(i2).getWidth();
            if (width2 > width) {
                width = width2;
                keyAt = keyAt2;
            }
            i = i2 + 1;
        }
    }
}
