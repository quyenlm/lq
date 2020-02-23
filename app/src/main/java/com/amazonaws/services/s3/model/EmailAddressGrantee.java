package com.amazonaws.services.s3.model;

public class EmailAddressGrantee implements Grantee {
    private String emailAddress = null;

    public String getTypeIdentifier() {
        return "emailAddress";
    }

    public EmailAddressGrantee(String emailAddress2) {
        setIdentifier(emailAddress2);
    }

    public void setIdentifier(String emailAddress2) {
        this.emailAddress = emailAddress2;
    }

    public String getIdentifier() {
        return this.emailAddress;
    }

    public int hashCode() {
        return (this.emailAddress == null ? 0 : this.emailAddress.hashCode()) + 31;
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
        EmailAddressGrantee other = (EmailAddressGrantee) obj;
        if (this.emailAddress == null) {
            if (other.emailAddress != null) {
                return false;
            }
            return true;
        } else if (!this.emailAddress.equals(other.emailAddress)) {
            return false;
        } else {
            return true;
        }
    }

    public String toString() {
        return this.emailAddress;
    }
}
