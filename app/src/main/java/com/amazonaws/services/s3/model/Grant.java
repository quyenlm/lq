package com.amazonaws.services.s3.model;

public class Grant {
    private Grantee grantee = null;
    private Permission permission = null;

    public Grant(Grantee grantee2, Permission permission2) {
        this.grantee = grantee2;
        this.permission = permission2;
    }

    public Grantee getGrantee() {
        return this.grantee;
    }

    public Permission getPermission() {
        return this.permission;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((this.grantee == null ? 0 : this.grantee.hashCode()) + 31) * 31;
        if (this.permission != null) {
            i = this.permission.hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Grant other = (Grant) obj;
        if (this.grantee == null) {
            if (other.grantee != null) {
                return false;
            }
        } else if (!this.grantee.equals(other.grantee)) {
            return false;
        }
        if (this.permission != other.permission) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "Grant [grantee=" + this.grantee + ", permission=" + this.permission + "]";
    }
}
