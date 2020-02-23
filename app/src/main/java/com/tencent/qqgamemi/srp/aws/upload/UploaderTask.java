package com.tencent.qqgamemi.srp.aws.upload;

import com.tencent.component.db.annotation.Column;
import com.tencent.component.db.annotation.Table;

@Table(name = "UploaderTask", version = 1)
public class UploaderTask {
    public static final String AUTHORTOKEN = "awsAuthorToken";
    public static final String FILESIZE = "fileSize";
    public static final String IDENTITYID = "awsIdentityID";
    public static final String SENDSIZE = "sendSize";
    public static final String TASKID = "taskID";
    public static final String UPLOADFILEPATH = "uploadFilePath";
    public static final String UPLOADSTATE = "uploadState";
    @Column(name = "awsAuthorToken")
    public String awsAuthorToken;
    @Column(name = "awsIdentityID")
    public String awsIdentityID;
    @Column(name = "fileSize")
    public long fileSize;
    @Column(name = "sendSize")
    public long sendSize;
    @Column(name = "taskID")
    public int taskid;
    @Column(name = "uploadFilePath")
    public String uploadFilePath;
    @Column(name = "uploadState")
    public int uploadState;
}
