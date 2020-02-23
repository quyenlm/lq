package com.amazonaws.mobileconnectors.s3.transfermanager.internal;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyPartRequest;
import com.amazonaws.services.s3.model.PartETag;
import java.util.concurrent.Callable;

public class CopyPartCallable implements Callable<PartETag> {
    private final CopyPartRequest request;
    private final AmazonS3 s3;

    public CopyPartCallable(AmazonS3 s32, CopyPartRequest request2) {
        this.s3 = s32;
        this.request = request2;
    }

    public PartETag call() throws Exception {
        return this.s3.copyPart(this.request).getPartETag();
    }
}
