package com.beetalk.sdk;

public class GGPhotoShare {
    protected String file;
    protected int gameId = 0;
    protected byte[] imgData = null;
    protected int imgDataLength = 0;
    protected String mediaTag = "";
    protected String messageAction = "";
    protected String messageExt = "";
    protected int scene = 0;

    public int getScene() {
        return this.scene;
    }

    public String getMediaTag() {
        return this.mediaTag;
    }

    public int getGameId() {
        return this.gameId;
    }

    public byte[] getImgData() {
        return this.imgData;
    }

    public int getImgDataLength() {
        return this.imgDataLength;
    }

    public String getMessageAction() {
        return this.messageAction;
    }

    public String getMessageExt() {
        return this.messageExt;
    }

    public String getFile() {
        return this.file;
    }

    public static class Builder {
        private GGPhotoShare photoShare = new GGPhotoShare();

        public Builder(int scene, byte[] imgData, int gameId) {
            this.photoShare.scene = scene;
            this.photoShare.imgData = imgData;
            this.photoShare.gameId = gameId;
            this.photoShare.imgDataLength = imgData.length;
        }

        public Builder(int scene, String file, int gameId) {
            this.photoShare.scene = scene;
            this.photoShare.file = file;
            this.photoShare.gameId = gameId;
        }

        public Builder setImgLength(int length) {
            this.photoShare.imgDataLength = length;
            return this;
        }

        public Builder setMediaTag(String mediaTag) {
            this.photoShare.mediaTag = mediaTag;
            return this;
        }

        public Builder setMessageAction(String action) {
            this.photoShare.messageAction = action;
            return this;
        }

        public Builder setMessageExt(String messageExt) {
            this.photoShare.messageExt = messageExt;
            return this;
        }

        public Builder setFile(String file) {
            this.photoShare.file = file;
            return this;
        }

        public GGPhotoShare build() {
            return this.photoShare;
        }
    }
}
