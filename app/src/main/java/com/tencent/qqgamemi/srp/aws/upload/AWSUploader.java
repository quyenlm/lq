package com.tencent.qqgamemi.srp.aws.upload;

import android.text.TextUtils;
import android.util.Log;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.tencent.qqgamemi.srp.aws.upload.UploaderTask;
import com.tencent.qqgamemi.srp.aws.util.FileUtil;
import com.tencent.qqgamemi.srp.aws.util.GlobalConfig;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class AWSUploader<T extends UploaderTask> {
    /* access modifiers changed from: private */
    public String TAG = "AWSUploader";
    public TransferListener awsTransferListener = new TransferListener() {
        public void onStateChanged(int id, TransferState state) {
            Log.d(AWSUploader.this.TAG, "onStateChanged =" + state.name());
            AWSUploader.this.notifyOnStateChangedListener(id, state);
        }

        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
            AWSUploader.this.notifyOnProgressChangedListener(id, bytesCurrent, bytesTotal);
        }

        public void onError(int id, Exception ex) {
            AWSUploader.this.notifyOnErrorListener(id);
            if (ex != null && ex.getMessage() != null) {
                Log.e(AWSUploader.this.TAG, ex.getMessage());
            }
        }
    };
    private TransferUtilityWrap transferUtilityWrap;
    private UploaderListener<T> uploaderListener;
    private Map<String, T> uploaderTasks;

    public interface UploaderListener<T> {
        void onError(T t, String str);

        void onProgressChanged(T t, String str, long j, long j2, int i);

        void onStateChanged(T t, String str, UploadState uploadState);
    }

    /* access modifiers changed from: protected */
    public abstract String getMetaContentType();

    /* access modifiers changed from: protected */
    public abstract String getUploadKey(String str);

    public AWSUploader(TransferUtilityWrap transferUtilityWrap2) {
        this.transferUtilityWrap = transferUtilityWrap2;
        this.uploaderTasks = new HashMap();
    }

    private boolean checkTransferUtilityWrap() {
        if (this.transferUtilityWrap == null) {
            Log.e(this.TAG, "upload fail because transferUtilityWrap is null");
            return false;
        } else if (this.transferUtilityWrap.getTransferUtility() != null) {
            return true;
        } else {
            Log.e(this.TAG, "upload fail because transferUtility is null");
            return false;
        }
    }

    public int upload(T uploaderTask, String localFilePath, String remoteFilePath, UploaderListener uploaderListener2) {
        int taskId = -1;
        if (checkTransferUtilityWrap() && FileUtil.isLegalFile(localFilePath)) {
            this.uploaderListener = uploaderListener2;
            File file = new File(localFilePath);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(getMetaContentType());
            TransferObserver transferObserver = this.transferUtilityWrap.getTransferUtility().upload(GlobalConfig.AWS_BUKET_NAME, getUploadKey(remoteFilePath), file, objectMetadata);
            transferObserver.setTransferListener(this.awsTransferListener);
            taskId = transferObserver.getId();
            if (uploaderTask != null && !this.uploaderTasks.containsKey(localFilePath)) {
                uploaderTask.taskid = taskId;
                this.uploaderTasks.put(localFilePath, uploaderTask);
            }
        }
        return taskId;
    }

    public int resume(String filePath) {
        T uploaderTask;
        int taskId;
        if (checkTransferUtilityWrap() && (uploaderTask = getUploadTaskByFilePath(filePath)) != null && (taskId = uploaderTask.taskid) >= 0) {
            resumeTaskById(taskId);
        }
        return -1;
    }

    private void resumeTaskById(int taskId) {
        this.transferUtilityWrap.getTransferUtility().resume(taskId).setTransferListener(this.awsTransferListener);
    }

    public int resume(T uploaderTask) {
        if (checkTransferUtilityWrap() && uploaderTask != null) {
            int taskId = uploaderTask.taskid;
            if (taskId < 0) {
                taskId = getTaskId(uploaderTask.uploadFilePath);
            }
            if (taskId >= 0) {
                resumeTaskById(taskId);
            }
        }
        return -1;
    }

    private int getTaskId(String filePath) {
        T task = getUploadTaskByFilePath(filePath);
        if (task != null) {
            return task.taskid;
        }
        return -1;
    }

    public String getAWSUploadFileUrl(String filePath) {
        return GlobalConfig.getInstance().getAWSUploadFileUrl(getUploadKey(filePath));
    }

    private T getUploadTaskByFilePath(String filePath) {
        if (filePath == null || TextUtils.isEmpty(filePath) || !this.uploaderTasks.containsKey(filePath)) {
            return null;
        }
        return (UploaderTask) this.uploaderTasks.get(filePath);
    }

    /* access modifiers changed from: private */
    public void notifyOnStateChangedListener(int id, TransferState state) {
        if (checkTransferUtilityWrap()) {
            String filePath = this.transferUtilityWrap.getFilePathByTransferObserverID(id);
            UploadState uploadState = UploadState.changeToUploadState(state);
            T uploadTask = getUploadTaskByFilePath(filePath);
            if (uploadTask != null) {
                uploadTask.uploadState = uploadState.getValue();
            }
            this.uploaderListener.onStateChanged(uploadTask, filePath, uploadState);
        }
    }

    /* access modifiers changed from: private */
    public void notifyOnProgressChangedListener(int id, long bytesCurrent, long bytesTotal) {
        if (checkTransferUtilityWrap()) {
            int percentage = (int) (((((double) bytesCurrent) * 1.0d) / ((double) bytesTotal)) * 100.0d);
            String filePath = this.transferUtilityWrap.getFilePathByTransferObserverID(id);
            T uploadTask = getUploadTaskByFilePath(filePath);
            if (uploadTask != null) {
                uploadTask.sendSize = bytesCurrent;
            }
            this.uploaderListener.onProgressChanged(uploadTask, filePath, bytesCurrent, bytesTotal, percentage);
        }
    }

    /* access modifiers changed from: private */
    public void notifyOnErrorListener(int id) {
        if (checkTransferUtilityWrap()) {
            String filePath = this.transferUtilityWrap.getFilePathByTransferObserverID(id);
            this.uploaderListener.onError(getUploadTaskByFilePath(filePath), filePath);
        }
    }
}
