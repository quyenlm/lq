package com.tencent.mtt.spcialcall.sdk;

import android.graphics.Bitmap;
import java.io.Serializable;

public class RspContent implements Serializable {
    private static final long serialVersionUID = -6589091102292710140L;
    private String Content;
    private int ID;
    private byte[] Image;
    private String ImagePath;
    private String Title;
    private DownLoadInfo WebViewDownloadInfo;

    public RspContent(int id, Bitmap image, String content) {
        this.ID = id;
        if (image != null) {
            this.Image = BitmapTools.Bitmap2Bytes(image);
        }
        this.Content = content;
    }

    public RspContent(int id, String title, String content) {
        this.ID = id;
        setTitle(title);
        this.Content = content;
    }

    public Bitmap getImage() {
        if (this.Image != null) {
            return BitmapTools.Bytes2Bimap(this.Image);
        }
        return null;
    }

    public String getContent() {
        return this.Content;
    }

    public int getID() {
        return this.ID;
    }

    public String getImagePath() {
        return this.ImagePath;
    }

    public void setImagePath(String imagePath) {
        this.ImagePath = imagePath;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public void setDownloadInfo(String url, String userAgent, String contentDisposition, String mimetype, long contentLength, String referUrl) {
        this.WebViewDownloadInfo = new DownLoadInfo(url, userAgent, contentDisposition, mimetype, contentLength, referUrl);
    }

    public DownLoadInfo getDownloadInfo() {
        return this.WebViewDownloadInfo;
    }

    public class DownLoadInfo implements Serializable {
        private static final long serialVersionUID = 3759841938734585077L;
        private String ContentDisposition;
        private long ContentLength;
        private String DownLoadUrl;
        private String Mimetype;
        private String ReferUrl;
        private String UserAgent;

        public DownLoadInfo(String url, String userAgent, String contentDisposition, String mimetype, long contentLength, String referUrl) {
            this.DownLoadUrl = url;
            this.UserAgent = userAgent;
            this.ContentDisposition = contentDisposition;
            this.Mimetype = mimetype;
            this.ContentLength = contentLength;
            this.ReferUrl = referUrl;
        }

        public String getDownLoadUrl() {
            return this.DownLoadUrl;
        }

        public void setDownLoadUrl(String downLoadUrl) {
            this.DownLoadUrl = downLoadUrl;
        }

        public String getUserAgent() {
            return this.UserAgent;
        }

        public void setUserAgent(String userAgent) {
            this.UserAgent = userAgent;
        }

        public String getContentDisposition() {
            return this.ContentDisposition;
        }

        public void setContentDisposition(String contentDisposition) {
            this.ContentDisposition = contentDisposition;
        }

        public String getMimetype() {
            return this.Mimetype;
        }

        public void setMimetype(String mimetype) {
            this.Mimetype = mimetype;
        }

        public long getContentLength() {
            return this.ContentLength;
        }

        public void setContentLength(long contentLength) {
            this.ContentLength = contentLength;
        }

        public String getReferUrl() {
            return this.ReferUrl;
        }

        public void setReferUrl(String referUrl) {
            this.ReferUrl = referUrl;
        }
    }
}
