package com.tencent.qqgamemi.srp.aws.upload;

import android.content.Context;
import com.amazonaws.auth.AWSCognitoIdentityProvider;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferType;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.tencent.qqgamemi.srp.aws.util.GlobalConfig;
import java.util.ArrayList;
import java.util.List;

public class TransferUtilityWrap {
    private DeveloperAuthenticationProvider developerAuthenticationProvider;
    private Regions region;
    private TransferUtility transferUtility;

    public TransferUtility refreshTransferUtility(Context context, String token, String identityId) {
        if (context == null) {
            return null;
        }
        if (this.region == null) {
            try {
                this.region = Regions.fromName(GlobalConfig.AWS_REGION);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        if (this.developerAuthenticationProvider == null) {
            this.developerAuthenticationProvider = new DeveloperAuthenticationProvider(GlobalConfig.AWS_OPEN_ID, GlobalConfig.AWS_POOL_ID, this.region);
        }
        if (!(token == null || identityId == null)) {
            this.developerAuthenticationProvider.refresh(token, identityId);
        }
        AmazonS3 s3 = new AmazonS3Client((AWSCredentialsProvider) new CognitoCachingCredentialsProvider(context.getApplicationContext(), (AWSCognitoIdentityProvider) this.developerAuthenticationProvider, this.region));
        s3.setRegion(Region.getRegion(this.region));
        this.transferUtility = new TransferUtility(s3, context.getApplicationContext());
        return this.transferUtility;
    }

    private TransferUtility getTransferUtility(Context context) {
        return refreshTransferUtility(context, (String) null, (String) null);
    }

    public TransferUtility getTransferUtility() {
        return this.transferUtility;
    }

    public List<UploaderTask> getUploaderTasks() {
        if (this.transferUtility == null) {
            return null;
        }
        List<UploaderTask> tasks = new ArrayList<>();
        for (TransferObserver transferObserver : this.transferUtility.getTransfersWithType(TransferType.UPLOAD)) {
            UploaderTask task = new UploaderTask();
            task.fileSize = transferObserver.getBytesTotal();
            task.sendSize = transferObserver.getBytesTransferred();
            task.taskid = transferObserver.getId();
            task.uploadState = UploadState.changeToUploadState(transferObserver.getState()).getValue();
            task.uploadFilePath = transferObserver.getAbsoluteFilePath();
            tasks.add(task);
        }
        return tasks;
    }

    public String getFilePathByTransferObserverID(int id) {
        TransferObserver transferObserver = getTransferObserver((Context) null, id);
        if (transferObserver != null) {
            return transferObserver.getAbsoluteFilePath();
        }
        return null;
    }

    private TransferObserver getTransferObserver(Context context, int id) {
        if (this.transferUtility == null) {
            this.transferUtility = getTransferUtility(context);
        }
        if (this.transferUtility == null || id == -1) {
            return null;
        }
        return this.transferUtility.getTransferById(id);
    }

    public UploaderTask getUploaderTaskById(Context context, int id) {
        TransferObserver transferObserver = getTransferObserver(context, id);
        UploaderTask uploaderTask = new UploaderTask();
        if (transferObserver != null) {
            uploaderTask.uploadState = UploadState.changeToUploadState(transferObserver.getState()).getValue();
            uploaderTask.fileSize = transferObserver.getBytesTotal();
            uploaderTask.sendSize = transferObserver.getBytesTransferred();
        } else {
            uploaderTask.uploadState = UploadState.NULL.getValue();
        }
        return uploaderTask;
    }

    public void pause(int id) {
        if (this.transferUtility != null && id != -1) {
            this.transferUtility.pause(id);
        }
    }

    public void resume(int id) {
        if (this.transferUtility != null && id != -1) {
            this.transferUtility.resume(id);
        }
    }

    public void cancle(int id) {
        if (this.transferUtility != null && id != -1) {
            this.transferUtility.cancel(id);
        }
    }

    public void pauseAllUploadingTasks() {
        if (this.transferUtility != null) {
            this.transferUtility.pauseAllWithType(TransferType.UPLOAD);
        }
    }
}
