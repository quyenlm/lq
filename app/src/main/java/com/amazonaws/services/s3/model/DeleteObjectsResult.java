package com.amazonaws.services.s3.model;

import java.util.ArrayList;
import java.util.List;

public class DeleteObjectsResult {
    private final List<DeletedObject> deletedObjects = new ArrayList();

    public DeleteObjectsResult(List<DeletedObject> deletedObjects2) {
        this.deletedObjects.addAll(deletedObjects2);
    }

    public List<DeletedObject> getDeletedObjects() {
        return this.deletedObjects;
    }

    public static class DeletedObject {
        private boolean deleteMarker;
        private String deleteMarkerVersionId;
        private String key;
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

        public boolean isDeleteMarker() {
            return this.deleteMarker;
        }

        public void setDeleteMarker(boolean deleteMarker2) {
            this.deleteMarker = deleteMarker2;
        }

        public String getDeleteMarkerVersionId() {
            return this.deleteMarkerVersionId;
        }

        public void setDeleteMarkerVersionId(String deleteMarkerVersionId2) {
            this.deleteMarkerVersionId = deleteMarkerVersionId2;
        }
    }
}
