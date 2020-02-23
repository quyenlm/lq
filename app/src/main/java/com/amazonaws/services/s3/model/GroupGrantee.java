package com.amazonaws.services.s3.model;

import com.facebook.share.internal.ShareConstants;

public enum GroupGrantee implements Grantee {
    AllUsers("http://acs.amazonaws.com/groups/global/AllUsers"),
    AuthenticatedUsers("http://acs.amazonaws.com/groups/global/AuthenticatedUsers"),
    LogDelivery("http://acs.amazonaws.com/groups/s3/LogDelivery");
    
    private String groupUri;

    public String getTypeIdentifier() {
        return ShareConstants.MEDIA_URI;
    }

    private GroupGrantee(String groupUri2) {
        this.groupUri = groupUri2;
    }

    public String getIdentifier() {
        return this.groupUri;
    }

    public void setIdentifier(String id) {
        throw new UnsupportedOperationException("Group grantees have preset identifiers that cannot be modified.");
    }

    public String toString() {
        return "GroupGrantee [" + this.groupUri + "]";
    }

    public static GroupGrantee parseGroupGrantee(String groupUri2) {
        for (GroupGrantee grantee : values()) {
            if (grantee.groupUri.equals(groupUri2)) {
                return grantee;
            }
        }
        return null;
    }
}
