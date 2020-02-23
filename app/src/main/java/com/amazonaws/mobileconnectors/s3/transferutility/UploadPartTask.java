package com.amazonaws.mobileconnectors.s3.transferutility;

import android.util.Log;
import com.amazonaws.retry.RetryUtils;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;
import java.util.concurrent.Callable;

class UploadPartTask implements Callable<Boolean> {
    private static final String TAG = "UploadPartTask";
    private final TransferDBUtil dbUtil;
    private final UploadPartRequest request;
    private final AmazonS3 s3;

    public UploadPartTask(UploadPartRequest request2, AmazonS3 s32, TransferDBUtil dbUtil2) {
        this.request = request2;
        this.s3 = s32;
        this.dbUtil = dbUtil2;
    }

    public Boolean call() throws Exception {
        try {
            UploadPartResult putPartResult = this.s3.uploadPart(this.request);
            this.dbUtil.updateState(this.request.getId(), TransferState.PART_COMPLETED);
            this.dbUtil.updateETag(this.request.getId(), putPartResult.getETag());
            return true;
        } catch (Exception e) {
            this.dbUtil.updateState(this.request.getId(), TransferState.FAILED);
            if (RetryUtils.isInterrupted(e)) {
                return false;
            }
            Log.e(TAG, "Encountered error uploading part " + e.getMessage());
            throw e;
        }
    }
}
