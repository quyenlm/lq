package com.tencent.mtt.spcialcall;

import android.graphics.Bitmap;
import android.graphics.Picture;
import com.tencent.mtt.engine.IWebView;
import com.tencent.mtt.engine.IWebViewClient;
import com.tencent.mtt.spcialcall.sdk.ExtendItem;
import java.util.ArrayList;

public class NullInterface implements IWebView, IWebViewClientSp {
    public void setWebViewClient(IWebViewClient client) {
    }

    public boolean canGoBack() {
        return false;
    }

    public boolean canGoForward() {
        return false;
    }

    public void back(boolean isKeyBack) {
    }

    public void forward() {
    }

    public void active() {
    }

    public void deactive() {
    }

    public void destroy() {
    }

    public String getTitle() {
        return null;
    }

    public String getUrl() {
        return null;
    }

    public void loadUrl(String url) {
    }

    public void reload() {
    }

    public void stopLoading() {
    }

    public Bitmap snapshotPostSynchronously() {
        return null;
    }

    public Bitmap snapshotPostWholePageSynchronously() {
        return null;
    }

    public void onReceivedTitle(IWebView view, String title) {
    }

    public boolean shouldOverrideUrlLoading(IWebView view, String url) {
        return false;
    }

    public void onBackOrForwardChanged(IWebView view) {
    }

    public void onPageStarted(IWebView view, String url, Bitmap favicon) {
    }

    public void onPageFinished(IWebView view, String url) {
    }

    public void onReceivedError(IWebView view, int errorCode, String description, String failingUrl) {
    }

    public void sharePage(String url) {
    }

    public void favPage() {
    }

    public void fitScreen() {
    }

    public void copyLink() {
    }

    public void openByMtt(String url) {
    }

    public void sendRsp(ExtendItem extendItem, String rspType) {
    }

    public void openByBrowser(String url) {
    }

    public void downloadVideoByMtt(String url, String referUrl, String title, int len, int videoType) {
    }

    public Picture snapshotWholePage(int recommendedWidth, int recommendedHeight, IWebView.RatioRespect respect, int flags) {
        return null;
    }

    public void requesetFullScreen() {
    }

    public void exitFullScreen() {
    }

    public void updateTitleItem(ArrayList<ExtendItem> arrayList) {
    }

    public void exiteBrowser() {
    }

    public void showImageSaveDialog(String url, Bitmap bitmap) {
    }

    public void showMore() {
    }

    public void openReadMode() {
    }

    public void addBookmark() {
    }

    public void openInternalUrl(String url) {
    }

    public void postUrl(String url, byte[] postData) {
    }

    public void configFontSize(int fontSize) {
    }

    public void saveFlow() {
    }

    public void onDownload(String url, String userAgent, String contentDisposition, String mimetype, long contentLength, String referUrl) {
    }
}
