package com.amazonaws.services.dynamodbv2.model;

import java.io.Serializable;

public class GlobalSecondaryIndexUpdate implements Serializable {
    private CreateGlobalSecondaryIndexAction create;
    private DeleteGlobalSecondaryIndexAction delete;
    private UpdateGlobalSecondaryIndexAction update;

    public UpdateGlobalSecondaryIndexAction getUpdate() {
        return this.update;
    }

    public void setUpdate(UpdateGlobalSecondaryIndexAction update2) {
        this.update = update2;
    }

    public GlobalSecondaryIndexUpdate withUpdate(UpdateGlobalSecondaryIndexAction update2) {
        this.update = update2;
        return this;
    }

    public CreateGlobalSecondaryIndexAction getCreate() {
        return this.create;
    }

    public void setCreate(CreateGlobalSecondaryIndexAction create2) {
        this.create = create2;
    }

    public GlobalSecondaryIndexUpdate withCreate(CreateGlobalSecondaryIndexAction create2) {
        this.create = create2;
        return this;
    }

    public DeleteGlobalSecondaryIndexAction getDelete() {
        return this.delete;
    }

    public void setDelete(DeleteGlobalSecondaryIndexAction delete2) {
        this.delete = delete2;
    }

    public GlobalSecondaryIndexUpdate withDelete(DeleteGlobalSecondaryIndexAction delete2) {
        this.delete = delete2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getUpdate() != null) {
            sb.append("Update: " + getUpdate() + ",");
        }
        if (getCreate() != null) {
            sb.append("Create: " + getCreate() + ",");
        }
        if (getDelete() != null) {
            sb.append("Delete: " + getDelete());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((getUpdate() == null ? 0 : getUpdate().hashCode()) + 31) * 31) + (getCreate() == null ? 0 : getCreate().hashCode())) * 31;
        if (getDelete() != null) {
            i = getDelete().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GlobalSecondaryIndexUpdate)) {
            return false;
        }
        GlobalSecondaryIndexUpdate other = (GlobalSecondaryIndexUpdate) obj;
        if ((other.getUpdate() == null) ^ (getUpdate() == null)) {
            return false;
        }
        if (other.getUpdate() != null && !other.getUpdate().equals(getUpdate())) {
            return false;
        }
        if (other.getCreate() == null) {
            z = true;
        } else {
            z = false;
        }
        if (z ^ (getCreate() == null)) {
            return false;
        }
        if (other.getCreate() != null && !other.getCreate().equals(getCreate())) {
            return false;
        }
        if (other.getDelete() == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 ^ (getDelete() == null)) {
            return false;
        }
        if (other.getDelete() == null || other.getDelete().equals(getDelete())) {
            return true;
        }
        return false;
    }
}
