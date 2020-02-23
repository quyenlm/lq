package com.beetalk.sdk.plugin.impl.fb.data;

public class FBMessageData {
    public final String contentUrl;
    public final String data;
    public final String description;
    public final String imageUrl;
    public final String title;
    public final long uid;

    private FBMessageData(Builder b) {
        this.uid = b.uid;
        this.title = b.title;
        this.description = b.description;
        this.contentUrl = b.contentUrl;
        this.imageUrl = b.imageUrl;
        this.data = b.data;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public String contentUrl;
        /* access modifiers changed from: private */
        public String data;
        /* access modifiers changed from: private */
        public String description;
        /* access modifiers changed from: private */
        public String imageUrl;
        /* access modifiers changed from: private */
        public String title;
        /* access modifiers changed from: private */
        public long uid;

        public Builder uid(long uid2) {
            this.uid = uid2;
            return this;
        }

        public Builder title(String title2) {
            this.title = title2;
            return this;
        }

        public Builder description(String description2) {
            this.description = description2;
            return this;
        }

        public Builder contentUrl(String contentUrl2) {
            this.contentUrl = contentUrl2;
            return this;
        }

        public Builder imageUrl(String imageUrl2) {
            this.imageUrl = imageUrl2;
            return this;
        }

        public Builder data(String data2) {
            this.data = data2;
            return this;
        }

        public FBMessageData build() {
            return new FBMessageData(this);
        }
    }
}
