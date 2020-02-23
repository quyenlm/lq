package com.amazonaws.mobileconnectors.s3.transfermanager.internal;

import com.amazonaws.event.ProgressListenerChain;
import com.amazonaws.mobileconnectors.s3.transfermanager.Download;
import com.amazonaws.mobileconnectors.s3.transfermanager.PersistableDownload;
import com.amazonaws.mobileconnectors.s3.transfermanager.Transfer;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferProgress;
import com.amazonaws.mobileconnectors.s3.transfermanager.exception.PauseException;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import java.io.File;
import java.io.IOException;

public class DownloadImpl extends AbstractTransfer implements Download {
    private final PersistableDownload persistableDownload;
    S3Object s3Object;

    public DownloadImpl(String description, TransferProgress transferProgress, ProgressListenerChain progressListenerChain, S3Object s3Object2, TransferStateChangeListener listener, GetObjectRequest getObjectRequest, File file) {
        super(description, transferProgress, progressListenerChain, listener);
        this.s3Object = s3Object2;
        this.persistableDownload = captureDownloadState(getObjectRequest, file);
        S3ProgressPublisher.publishTransferPersistable(progressListenerChain, this.persistableDownload);
    }

    public ObjectMetadata getObjectMetadata() {
        return this.s3Object.getObjectMetadata();
    }

    public String getBucketName() {
        return this.s3Object.getBucketName();
    }

    public String getKey() {
        return this.s3Object.getKey();
    }

    public synchronized void abort() throws IOException {
        this.monitor.getFuture().cancel(true);
        if (this.s3Object != null) {
            this.s3Object.getObjectContent().abort();
        }
        setState(Transfer.TransferState.Canceled);
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:653)
        	at java.util.ArrayList.get(ArrayList.java:429)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    public synchronized void abortWithoutNotifyingStateChangeListener() throws java.io.IOException {
        /*
            r2 = this;
            monitor-enter(r2)
            com.amazonaws.mobileconnectors.s3.transfermanager.internal.TransferMonitor r0 = r2.monitor     // Catch:{ all -> 0x0016 }
            java.util.concurrent.Future r0 = r0.getFuture()     // Catch:{ all -> 0x0016 }
            r1 = 1
            r0.cancel(r1)     // Catch:{ all -> 0x0016 }
            monitor-enter(r2)     // Catch:{ all -> 0x0016 }
            com.amazonaws.mobileconnectors.s3.transfermanager.Transfer$TransferState r0 = com.amazonaws.mobileconnectors.s3.transfermanager.Transfer.TransferState.Canceled     // Catch:{ all -> 0x0013 }
            r2.state = r0     // Catch:{ all -> 0x0013 }
            monitor-exit(r2)     // Catch:{ all -> 0x0013 }
            monitor-exit(r2)
            return
        L_0x0013:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0013 }
            throw r0     // Catch:{ all -> 0x0016 }
        L_0x0016:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.mobileconnectors.s3.transfermanager.internal.DownloadImpl.abortWithoutNotifyingStateChangeListener():void");
    }

    public synchronized void setS3Object(S3Object s3Object2) {
        this.s3Object = s3Object2;
    }

    public void setState(Transfer.TransferState state) {
        super.setState(state);
        if (state == Transfer.TransferState.Completed) {
            fireProgressEvent(4);
        }
    }

    private PersistableDownload captureDownloadState(GetObjectRequest getObjectRequest, File file) {
        if (getObjectRequest.getSSECustomerKey() == null) {
            return new PersistableDownload(getObjectRequest.getBucketName(), getObjectRequest.getKey(), getObjectRequest.getVersionId(), getObjectRequest.getRange(), getObjectRequest.getResponseHeaders(), getObjectRequest.isRequesterPays(), file.getAbsolutePath());
        }
        return null;
    }

    public PersistableDownload pause() throws PauseException {
        Transfer.TransferState currentState = getState();
        this.monitor.getFuture().cancel(true);
        if (this.persistableDownload != null) {
            return this.persistableDownload;
        }
        throw new PauseException(TransferManagerUtils.determinePauseStatus(currentState, true));
    }
}
