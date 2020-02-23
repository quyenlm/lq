package com.google.android.gms.vision;

import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.vision.Detector;

public abstract class FocusingProcessor<T> implements Detector.Processor<T> {
    private Detector<T> zzbMB;
    private Tracker<T> zzbMQ;
    private int zzbMR = 3;
    private boolean zzbMS = false;
    private int zzbMT;
    private int zzbMU = 0;

    public FocusingProcessor(Detector<T> detector, Tracker<T> tracker) {
        this.zzbMB = detector;
        this.zzbMQ = tracker;
    }

    public void receiveDetections(Detector.Detections<T> detections) {
        SparseArray<T> detectedItems = detections.getDetectedItems();
        if (detectedItems.size() == 0) {
            if (this.zzbMU == this.zzbMR) {
                this.zzbMQ.onDone();
                this.zzbMS = false;
            } else {
                this.zzbMQ.onMissing(detections);
            }
            this.zzbMU++;
            return;
        }
        this.zzbMU = 0;
        if (this.zzbMS) {
            T t = detectedItems.get(this.zzbMT);
            if (t != null) {
                this.zzbMQ.onUpdate(detections, t);
                return;
            } else {
                this.zzbMQ.onDone();
                this.zzbMS = false;
            }
        }
        int selectFocus = selectFocus(detections);
        T t2 = detectedItems.get(selectFocus);
        if (t2 == null) {
            Log.w("FocusingProcessor", new StringBuilder(35).append("Invalid focus selected: ").append(selectFocus).toString());
            return;
        }
        this.zzbMS = true;
        this.zzbMT = selectFocus;
        this.zzbMB.setFocus(this.zzbMT);
        this.zzbMQ.onNewItem(this.zzbMT, t2);
        this.zzbMQ.onUpdate(detections, t2);
    }

    public void release() {
        this.zzbMQ.onDone();
    }

    public abstract int selectFocus(Detector.Detections<T> detections);

    /* access modifiers changed from: protected */
    public final void zzbK(int i) {
        if (i < 0) {
            throw new IllegalArgumentException(new StringBuilder(28).append("Invalid max gap: ").append(i).toString());
        }
        this.zzbMR = i;
    }
}
