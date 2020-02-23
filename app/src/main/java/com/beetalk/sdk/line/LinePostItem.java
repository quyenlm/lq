package com.beetalk.sdk.line;

public class LinePostItem {
    public final String imagePath;
    public final String link;
    public final String message;
    public final PostType type;

    public enum PostType {
        IMAGE,
        TEXT_LINK
    }

    private LinePostItem(Builder b) {
        this.type = b.type;
        this.message = b.message;
        this.link = b.link;
        this.imagePath = b.imagePath;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public String imagePath;
        /* access modifiers changed from: private */
        public String link;
        /* access modifiers changed from: private */
        public String message;
        /* access modifiers changed from: private */
        public PostType type;

        public Builder(PostType type2) {
            this.type = type2;
        }

        public Builder message(String message2) {
            this.message = message2;
            return this;
        }

        public Builder link(String link2) {
            this.link = link2;
            return this;
        }

        public Builder imagePath(String imagePath2) {
            this.imagePath = imagePath2;
            return this;
        }

        public LinePostItem build() {
            return new LinePostItem(this);
        }
    }
}
