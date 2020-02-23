package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;

public class StreamSpecification implements Serializable {
    private Boolean streamEnabled;
    private String streamViewType;

    public Boolean isStreamEnabled() {
        return this.streamEnabled;
    }

    public Boolean getStreamEnabled() {
        return this.streamEnabled;
    }

    public void setStreamEnabled(Boolean streamEnabled2) {
        this.streamEnabled = streamEnabled2;
    }

    public StreamSpecification withStreamEnabled(Boolean streamEnabled2) {
        this.streamEnabled = streamEnabled2;
        return this;
    }

    public String getStreamViewType() {
        return this.streamViewType;
    }

    public void setStreamViewType(String streamViewType2) {
        this.streamViewType = streamViewType2;
    }

    public StreamSpecification withStreamViewType(String streamViewType2) {
        this.streamViewType = streamViewType2;
        return this;
    }

    public void setStreamViewType(StreamViewType streamViewType2) {
        this.streamViewType = streamViewType2.toString();
    }

    public StreamSpecification withStreamViewType(StreamViewType streamViewType2) {
        this.streamViewType = streamViewType2.toString();
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getStreamEnabled() != null) {
            sb.append("StreamEnabled: " + getStreamEnabled() + ",");
        }
        if (getStreamViewType() != null) {
            sb.append("StreamViewType: " + getStreamViewType());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((getStreamEnabled() == null ? 0 : getStreamEnabled().hashCode()) + 31) * 31;
        if (getStreamViewType() != null) {
            i = getStreamViewType().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof StreamSpecification)) {
            return false;
        }
        StreamSpecification other = (StreamSpecification) obj;
        if ((other.getStreamEnabled() == null) ^ (getStreamEnabled() == null)) {
            return false;
        }
        if (other.getStreamEnabled() != null && !other.getStreamEnabled().equals(getStreamEnabled())) {
            return false;
        }
        if (other.getStreamViewType() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getStreamViewType() == null)) {
            return false;
        }
        if (other.getStreamViewType() == null || other.getStreamViewType().equals(getStreamViewType())) {
            return true;
        }
        return false;
    }
}
