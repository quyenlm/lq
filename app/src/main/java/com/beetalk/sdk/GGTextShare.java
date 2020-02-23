package com.beetalk.sdk;

public class GGTextShare {
    protected String caption;
    protected String desc;
    protected int gameId = 0;
    protected String mediaTag = "";
    protected int scene = 0;
    protected int thumDataLength = 0;
    protected byte[] thumbData = null;
    protected String title;
    protected String url;

    public int getScene() {
        return this.scene;
    }

    public String getMediaTag() {
        return this.mediaTag;
    }

    public int getGameId() {
        return this.gameId;
    }

    public byte[] getThumbData() {
        return this.thumbData;
    }

    public int getThumDataLength() {
        return this.thumDataLength;
    }

    public String getUrl() {
        return this.url;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getTitle() {
        return this.title;
    }

    public static class Builder {
        private GGTextShare textShare = new GGTextShare();

        public Builder(int gameId) {
            this.textShare.scene = 0;
            this.textShare.gameId = gameId;
            this.textShare.caption = "";
        }

        public Builder(byte[] thumbData, int gameId) {
            this.textShare.scene = 0;
            this.textShare.thumbData = thumbData;
            this.textShare.gameId = gameId;
            this.textShare.thumDataLength = thumbData.length;
            this.textShare.caption = "";
        }

        public Builder setUrl(String url) {
            this.textShare.url = url;
            return this;
        }

        public Builder setMediaTag(String mediaTag) {
            this.textShare.mediaTag = mediaTag;
            return this;
        }

        public Builder setTitle(String title) {
            this.textShare.title = title;
            return this;
        }

        public Builder setDesc(String desc) {
            this.textShare.desc = desc;
            return this;
        }

        public Builder setCaption(String aCaption) {
            this.textShare.caption = aCaption;
            return this;
        }

        public Builder setScene(Integer aScene) {
            this.textShare.scene = aScene.intValue();
            return this;
        }

        public GGTextShare build() {
            return this.textShare;
        }
    }
}
