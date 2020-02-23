package com.amazonaws.services.s3.model;

import java.util.Date;

public class Bucket {
    private static final long serialVersionUID = -8646831898339939580L;
    private Date creationDate = null;
    private String name = null;
    private Owner owner = null;

    public Bucket() {
    }

    public Bucket(String name2) {
        this.name = name2;
    }

    public String toString() {
        return "S3Bucket [name=" + getName() + ", creationDate=" + getCreationDate() + ", owner=" + getOwner() + "]";
    }

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner2) {
        this.owner = owner2;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate2) {
        this.creationDate = creationDate2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }
}
