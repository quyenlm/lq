package com.amazonaws.services.s3.internal;

import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.CopyPartRequest;
import com.amazonaws.services.s3.model.CopyPartResult;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;
import java.io.File;

public abstract class S3Direct {
    public abstract void abortMultipartUpload(AbortMultipartUploadRequest abortMultipartUploadRequest);

    public abstract CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest completeMultipartUploadRequest);

    public abstract CopyPartResult copyPart(CopyPartRequest copyPartRequest);

    public abstract ObjectMetadata getObject(GetObjectRequest getObjectRequest, File file);

    public abstract S3Object getObject(GetObjectRequest getObjectRequest);

    public abstract InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest initiateMultipartUploadRequest);

    public abstract PutObjectResult putObject(PutObjectRequest putObjectRequest);

    public abstract UploadPartResult uploadPart(UploadPartRequest uploadPartRequest);
}
