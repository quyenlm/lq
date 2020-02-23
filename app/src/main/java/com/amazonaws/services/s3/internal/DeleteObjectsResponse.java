package com.amazonaws.services.s3.internal;

import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.MultiObjectDeleteException;
import java.util.ArrayList;
import java.util.List;

public class DeleteObjectsResponse {
    private List<DeleteObjectsResult.DeletedObject> deletedObjects;
    private List<MultiObjectDeleteException.DeleteError> errors;

    public DeleteObjectsResponse() {
        this(new ArrayList(), new ArrayList());
    }

    public DeleteObjectsResponse(List<DeleteObjectsResult.DeletedObject> deletedObjects2, List<MultiObjectDeleteException.DeleteError> errors2) {
        this.deletedObjects = deletedObjects2;
        this.errors = errors2;
    }

    public List<DeleteObjectsResult.DeletedObject> getDeletedObjects() {
        return this.deletedObjects;
    }

    public void setDeletedObjects(List<DeleteObjectsResult.DeletedObject> deletedObjects2) {
        this.deletedObjects = deletedObjects2;
    }

    public List<MultiObjectDeleteException.DeleteError> getErrors() {
        return this.errors;
    }

    public void setErrors(List<MultiObjectDeleteException.DeleteError> errors2) {
        this.errors = errors2;
    }
}
