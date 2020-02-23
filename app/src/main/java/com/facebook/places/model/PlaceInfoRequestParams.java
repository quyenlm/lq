package com.facebook.places.model;

import java.util.HashSet;
import java.util.Set;

public final class PlaceInfoRequestParams {
    private final Set<String> fields;
    private final String placeId;

    private PlaceInfoRequestParams(Builder b) {
        this.fields = new HashSet();
        this.placeId = b.placeId;
        this.fields.addAll(b.fields);
    }

    public String getPlaceId() {
        return this.placeId;
    }

    public Set<String> getFields() {
        return this.fields;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public final Set<String> fields = new HashSet();
        /* access modifiers changed from: private */
        public String placeId;

        public Builder setPlaceId(String placeId2) {
            this.placeId = placeId2;
            return this;
        }

        public Builder addField(String field) {
            this.fields.add(field);
            return this;
        }

        public Builder addFields(String[] fields2) {
            for (String field : fields2) {
                this.fields.add(field);
            }
            return this;
        }

        public PlaceInfoRequestParams build() {
            return new PlaceInfoRequestParams(this);
        }
    }
}
