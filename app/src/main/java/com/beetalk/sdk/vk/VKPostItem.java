package com.beetalk.sdk.vk;

public class VKPostItem {
    public final byte[] imageData;
    public final String link;
    public final String message;

    private VKPostItem(Builder b) {
        this.message = b.message;
        this.link = b.link;
        this.imageData = b.imageData;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public byte[] imageData;
        /* access modifiers changed from: private */
        public String link;
        /* access modifiers changed from: private */
        public String message;

        public Builder message(String message2) {
            this.message = message2;
            return this;
        }

        public Builder link(String link2) {
            this.link = link2;
            return this;
        }

        public Builder imageData(byte[] imageData2) {
            this.imageData = imageData2;
            return this;
        }

        public VKPostItem build() {
            return new VKPostItem(this);
        }
    }
}
