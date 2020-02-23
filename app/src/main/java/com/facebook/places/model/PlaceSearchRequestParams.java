package com.facebook.places.model;

import java.util.HashSet;
import java.util.Set;

public final class PlaceSearchRequestParams {
    private final Set<String> categories;
    private final int distance;
    private final Set<String> fields;
    private final int limit;
    private final String searchText;

    private PlaceSearchRequestParams(Builder b) {
        this.categories = new HashSet();
        this.fields = new HashSet();
        this.distance = b.distance;
        this.limit = b.limit;
        this.searchText = b.searchText;
        this.categories.addAll(b.categories);
        this.fields.addAll(b.fields);
    }

    public int getDistance() {
        return this.distance;
    }

    public int getLimit() {
        return this.limit;
    }

    public String getSearchText() {
        return this.searchText;
    }

    public Set<String> getCategories() {
        return this.categories;
    }

    public Set<String> getFields() {
        return this.fields;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public final Set<String> categories = new HashSet();
        /* access modifiers changed from: private */
        public int distance;
        /* access modifiers changed from: private */
        public final Set<String> fields = new HashSet();
        /* access modifiers changed from: private */
        public int limit;
        /* access modifiers changed from: private */
        public String searchText;

        public Builder setDistance(int distance2) {
            this.distance = distance2;
            return this;
        }

        public Builder setLimit(int limit2) {
            this.limit = limit2;
            return this;
        }

        public Builder setSearchText(String searchText2) {
            this.searchText = searchText2;
            return this;
        }

        public Builder addCategory(String category) {
            this.categories.add(category);
            return this;
        }

        public Builder addField(String field) {
            this.fields.add(field);
            return this;
        }

        public PlaceSearchRequestParams build() {
            return new PlaceSearchRequestParams(this);
        }
    }
}
