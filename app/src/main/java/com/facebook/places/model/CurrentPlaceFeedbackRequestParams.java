package com.facebook.places.model;

public class CurrentPlaceFeedbackRequestParams {
    private final String placeId;
    private final String tracking;
    private final Boolean wasHere;

    private CurrentPlaceFeedbackRequestParams(Builder b) {
        this.tracking = b.tracking;
        this.placeId = b.placeId;
        this.wasHere = b.wasHere;
    }

    public String getTracking() {
        return this.tracking;
    }

    public String getPlaceId() {
        return this.placeId;
    }

    public Boolean wasHere() {
        return this.wasHere;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public String placeId;
        /* access modifiers changed from: private */
        public String tracking;
        /* access modifiers changed from: private */
        public Boolean wasHere;

        public Builder setWasHere(boolean wasHere2) {
            this.wasHere = Boolean.valueOf(wasHere2);
            return this;
        }

        public Builder setPlaceId(String placeId2) {
            this.placeId = placeId2;
            return this;
        }

        public Builder setTracking(String tracking2) {
            this.tracking = tracking2;
            return this;
        }

        public CurrentPlaceFeedbackRequestParams build() {
            return new CurrentPlaceFeedbackRequestParams(this);
        }
    }
}
