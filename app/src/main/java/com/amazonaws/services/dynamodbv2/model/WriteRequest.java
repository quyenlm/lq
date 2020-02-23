package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;

public class WriteRequest implements Serializable {
    private DeleteRequest deleteRequest;
    private PutRequest putRequest;

    public WriteRequest() {
    }

    public WriteRequest(PutRequest putRequest2) {
        setPutRequest(putRequest2);
    }

    public WriteRequest(DeleteRequest deleteRequest2) {
        setDeleteRequest(deleteRequest2);
    }

    public PutRequest getPutRequest() {
        return this.putRequest;
    }

    public void setPutRequest(PutRequest putRequest2) {
        this.putRequest = putRequest2;
    }

    public WriteRequest withPutRequest(PutRequest putRequest2) {
        this.putRequest = putRequest2;
        return this;
    }

    public DeleteRequest getDeleteRequest() {
        return this.deleteRequest;
    }

    public void setDeleteRequest(DeleteRequest deleteRequest2) {
        this.deleteRequest = deleteRequest2;
    }

    public WriteRequest withDeleteRequest(DeleteRequest deleteRequest2) {
        this.deleteRequest = deleteRequest2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getPutRequest() != null) {
            sb.append("PutRequest: " + getPutRequest() + ",");
        }
        if (getDeleteRequest() != null) {
            sb.append("DeleteRequest: " + getDeleteRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((getPutRequest() == null ? 0 : getPutRequest().hashCode()) + 31) * 31;
        if (getDeleteRequest() != null) {
            i = getDeleteRequest().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof WriteRequest)) {
            return false;
        }
        WriteRequest other = (WriteRequest) obj;
        if ((other.getPutRequest() == null) ^ (getPutRequest() == null)) {
            return false;
        }
        if (other.getPutRequest() != null && !other.getPutRequest().equals(getPutRequest())) {
            return false;
        }
        if (other.getDeleteRequest() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getDeleteRequest() == null)) {
            return false;
        }
        if (other.getDeleteRequest() == null || other.getDeleteRequest().equals(getDeleteRequest())) {
            return true;
        }
        return false;
    }
}
