package com.amazonaws.mobileconnectors.s3.transfermanager.internal;

import com.amazonaws.mobileconnectors.s3.transfermanager.Transfer;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MultipleFileTransferMonitor implements TransferMonitor {
    private final Future<?> future = new Future<Object>() {
        public boolean cancel(boolean mayInterruptIfRunning) {
            return true;
        }

        public Object get() throws InterruptedException, ExecutionException {
            Object result = null;
            for (AbstractTransfer download : MultipleFileTransferMonitor.this.subTransfers) {
                result = download.getMonitor().getFuture().get();
            }
            return result;
        }

        public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            Object result = null;
            for (AbstractTransfer subTransfer : MultipleFileTransferMonitor.this.subTransfers) {
                result = subTransfer.getMonitor().getFuture().get(timeout, unit);
            }
            return result;
        }

        public boolean isCancelled() {
            return MultipleFileTransferMonitor.this.transfer.getState() == Transfer.TransferState.Canceled;
        }

        public boolean isDone() {
            return MultipleFileTransferMonitor.this.isDone();
        }
    };
    /* access modifiers changed from: private */
    public final Collection<? extends AbstractTransfer> subTransfers;
    /* access modifiers changed from: private */
    public final AbstractTransfer transfer;

    public MultipleFileTransferMonitor(AbstractTransfer transfer2, Collection<? extends AbstractTransfer> subTransfers2) {
        this.subTransfers = subTransfers2;
        this.transfer = transfer2;
    }

    public Future<?> getFuture() {
        return this.future;
    }

    public synchronized boolean isDone() {
        boolean z;
        Iterator<? extends AbstractTransfer> it = this.subTransfers.iterator();
        while (true) {
            if (it.hasNext()) {
                if (!((Transfer) it.next()).isDone()) {
                    z = false;
                    break;
                }
            } else {
                z = true;
                break;
            }
        }
        return z;
    }
}
