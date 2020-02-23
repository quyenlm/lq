package com.tencent.mtt.spcialcall;

import android.graphics.Bitmap;
import com.tencent.mtt.engine.IWebViewClient;
import com.tencent.mtt.spcialcall.sdk.ExtendItem;
import java.util.ArrayList;

public interface IWebViewClientSp extends IWebViewClient {
    void addBookmark();

    void copyLink();

    void downloadVideoByMtt(String str, String str2, String str3, int i, int i2);

    void exitFullScreen();

    void exiteBrowser();

    void favPage();

    void fitScreen();

    void onDownload(String str, String str2, String str3, String str4, long j, String str5);

    void openByBrowser(String str);

    void openByMtt(String str);

    void openInternalUrl(String str);

    void openReadMode();

    void requesetFullScreen();

    void saveFlow();

    void sendRsp(ExtendItem extendItem, String str);

    void sharePage(String str);

    void showImageSaveDialog(String str, Bitmap bitmap);

    void showMore();

    void updateTitleItem(ArrayList<ExtendItem> arrayList);
}
