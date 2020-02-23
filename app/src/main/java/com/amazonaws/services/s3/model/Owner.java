package com.amazonaws.services.s3.model;

import java.io.Serializable;

public class Owner implements Serializable {
    private static final long serialVersionUID = -8916731456944569115L;
    private String displayName;
    private String id;

    public Owner() {
    }

    public Owner(String id2, String displayName2) {
        this.id = id2;
        this.displayName = displayName2;
    }

    public String toString() {
        return "S3Owner [name=" + getDisplayName() + ",id=" + getId() + "]";
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String name) {
        this.displayName = name;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Owner)) {
            return false;
        }
        Owner otherOwner = (Owner) obj;
        String otherOwnerId = otherOwner.getId();
        String otherOwnerName = otherOwner.getDisplayName();
        String thisOwnerId = getId();
        String thisOwnerName = getDisplayName();
        if (otherOwnerId == null) {
            otherOwnerId = "";
        }
        if (otherOwnerName == null) {
            otherOwnerName = "";
        }
        if (thisOwnerId == null) {
            thisOwnerId = "";
        }
        if (thisOwnerName == null) {
            thisOwnerName = "";
        }
        if (!otherOwnerId.equals(thisOwnerId) || !otherOwnerName.equals(thisOwnerName)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (this.id != null) {
            return this.id.hashCode();
        }
        return 0;
    }
}
