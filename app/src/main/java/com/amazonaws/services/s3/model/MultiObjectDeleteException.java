package com.amazonaws.services.s3.model;

import com.amazonaws.services.s3.model.DeleteObjectsResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MultiObjectDeleteException extends AmazonS3Exception {
    private static final long serialVersionUID = -2004213552302446866L;
    private final List<DeleteObjectsResult.DeletedObject> deletedObjects = new ArrayList();
    private final List<DeleteError> errors = new ArrayList();

    public MultiObjectDeleteException(Collection<DeleteError> errors2, Collection<DeleteObjectsResult.DeletedObject> deletedObjects2) {
        super("One or more objects could not be deleted");
        this.deletedObjects.addAll(deletedObjects2);
        this.errors.addAll(errors2);
    }

    public List<DeleteObjectsResult.DeletedObject> getDeletedObjects() {
        return this.deletedObjects;
    }

    public List<DeleteError> getErrors() {
        return this.errors;
    }

    public static class DeleteError {
        private String code;
        private String key;
        private String message;
        private String versionId;

        public String getKey() {
            return this.key;
        }

        public void setKey(String key2) {
            this.key = key2;
        }

        public String getVersionId() {
            return this.versionId;
        }

        public void setVersionId(String versionId2) {
            this.versionId = versionId2;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code2) {
            this.code = code2;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message2) {
            this.message = message2;
        }
    }
}
