package com.amazonaws.services.s3.model;

public enum Permission {
    FullControl("FULL_CONTROL", "x-amz-grant-full-control"),
    Read("READ", "x-amz-grant-read"),
    Write("WRITE", "x-amz-grant-write"),
    ReadAcp("READ_ACP", "x-amz-grant-read-acp"),
    WriteAcp("WRITE_ACP", "x-amz-grant-write-acp");
    
    private String headerName;
    private String permissionString;

    private Permission(String permissionString2, String headerName2) {
        this.permissionString = permissionString2;
        this.headerName = headerName2;
    }

    public String getHeaderName() {
        return this.headerName;
    }

    public String toString() {
        return this.permissionString;
    }

    public static Permission parsePermission(String str) {
        for (Permission permission : values()) {
            if (permission.permissionString.equals(str)) {
                return permission;
            }
        }
        return null;
    }
}
