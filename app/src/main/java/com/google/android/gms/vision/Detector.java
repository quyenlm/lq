package com.google.android.gms.vision;

import android.util.SparseArray;
import com.google.android.gms.vision.Frame;

public abstract class Detector<T> {
    private final Object zzbML = new Object();
    private Processor<T> zzbMM;

    public static class Detections<T> {
        private SparseArray<T> zzbMN;
        private Frame.Metadata zzbMO;
        private boolean zzbMP;

        public Detections(SparseArray<T> sparseArray, Frame.Metadata metadata, boolean z) {
            this.zzbMN = sparseArray;
            this.zzbMO = metadata;
            this.zzbMP = z;
        }

        public boolean detectorIsOperational() {
            return this.zzbMP;
        }

        public SparseArray<T> getDetectedItems() {
            return this.zzbMN;
        }

        public Frame.Metadata getFrameMetadata() {
            return this.zzbMO;
        }
    }

    public interface Processor<T> {
        void receiveDetections(Detections<T> detections);

        void release();
    }

    public abstract SparseArray<T> detect(Frame frame);

    public boolean isOperational() {
        return true;
    }

    public void receiveFrame(Frame frame) {
        synchronized (this.zzbML) {
            if (this.zzbMM == null) {
                throw new IllegalStateException("Detector processor must first be set with setProcessor in order to receive detection results.");
            }
            Frame.Metadata metadata = new Frame.Metadata(frame.getMetadata());
            metadata.zzDN();
            this.zzbMM.receiveDetections(new Detections(detect(frame), metadata, isOperational()));
        }
    }

    public void release() {
        synchronized (this.zzbML) {
            if (this.zzbMM != null) {
                this.zzbMM.release();
                this.zzbMM = null;
            }
        }
    }

    public boolean setFocus(int i) {
        return true;
    }

    public void setProcessor(Processor<T> processor) {
        this.zzbMM = processor;
    }
}
