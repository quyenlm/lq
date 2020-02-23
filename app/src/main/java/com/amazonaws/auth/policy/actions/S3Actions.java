package com.amazonaws.auth.policy.actions;

import com.amazonaws.auth.policy.Action;

public enum S3Actions implements Action {
    AllS3Actions("s3:*"),
    GetObject("s3:GetObject"),
    GetObjectVersion("s3:GetObjectVersion"),
    PutObject("s3:PutObject"),
    GetObjectAcl("s3:GetObjectAcl"),
    GetObjectVersionAcl("s3:GetObjectVersionAcl"),
    SetObjectAcl("s3:PutObjectAcl"),
    SetObjectVersionAcl("s3:PutObjectAclVersion"),
    DeleteObject("s3:DeleteObject"),
    DeleteObjectVersion("s3:DeleteObjectVersion"),
    CreateBucket("s3:CreateBucket"),
    DeleteBucket("s3:DeleteBucket"),
    ListObjects("s3:ListBucket"),
    ListObjectVersions("s3:ListBucketVersions"),
    ListBuckets("s3:ListAllMyBuckets"),
    GetBucketAcl("s3:GetBucketAcl"),
    SetBucketAcl("s3:PutBucketAcl"),
    GetBucketVersioningConfiguration("s3:GetBucketVersioning"),
    SetBucketVersioningConfiguration("s3:PutBucketVersioning"),
    GetBucketRequesterPays("s3:GetBucketRequesterPays"),
    SetBucketRequesterPays("s3:PutBucketRequesterPays"),
    GetBucketLocation("s3:GetBucketLocation"),
    GetBucketPolicy("s3:GetBucketPolicy"),
    SetBucketPolicy("s3:PutBucketPolicy"),
    GetBucketNotificationConfiguration("s3:GetBucketNotification"),
    SetBucketNotificationConfiguration("s3:PutBucketNotification");
    
    private final String action;

    private S3Actions(String action2) {
        this.action = action2;
    }

    public String getActionName() {
        return this.action;
    }
}
