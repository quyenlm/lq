package com.tencent.mtt.spcialcall;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.net.http.Headers;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.appsflyer.share.Constants;
import com.tencent.mtt.collect.CollectManager;
import com.tencent.mtt.common.utils.FileUtils;
import com.tencent.mtt.common.utils.MttResources;
import com.tencent.mtt.engine.AppEngine;
import com.tencent.mtt.engine.IWebView;
import com.tencent.mtt.spcialcall.sdk.ExtendItem;
import com.tencent.mtt.spcialcall.sdk.MttApi;
import com.tencent.mtt.spcialcall.sdk.RecommendParams;
import com.tencent.mtt.spcialcall.sdk.RspContent;
import com.tencent.mtt.ui.util.MttCtrlUtil;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BrowserWindowSP extends RelativeLayout implements IWebViewClientSp, View.OnClickListener {
    private static final String QR_CATCH_PATH = FileUtils.getShareCacheDir().getPath();
    public static int sHeight = 0;
    public static int sWidth = 0;
    private ImageButton mBack;
    private String mCustomJs;
    /* access modifiers changed from: private */
    public boolean mDestroy = false;
    String mEntryUrl;
    private ImageButton mFav;
    private ImageButton mForward;
    Handler mHandler;
    private Button mMore;
    private DialogSp mMoreDialog;
    private String mNotifyUrlPrefix;
    private ImageButton mPlus;
    private ProgressBar mProgressBar;
    private int mPv = 0;
    private ImageButton mRefresh;
    private Button mRetrun;
    private DialogSp mShareDialog;
    private SpecialCallActivity mSpActivity;
    private ImageButton mStop;
    private TextView mTitle;
    private RelativeLayout mTitleBar;
    private FrameLayout mToolBarHolder;
    private LinearLayout mToolbar;
    private int mWebThumbHeight = 600;
    private int mWebThumbWidth = 600;
    private IWebView mWebView = new NullInterface();
    private long mWebViewId = -1;

    public BrowserWindowSP(SpecialCallActivity context, String entryUrl, Intent intent) {
        super(context);
        this.mSpActivity = context;
        this.mEntryUrl = entryUrl;
        this.mWebViewId = intent.getLongExtra(MttApi.WEBVIEW_ID, System.currentTimeMillis());
        this.mCustomJs = intent.getStringExtra("js");
        this.mNotifyUrlPrefix = intent.getStringExtra("notifyUrlPrefix");
        this.mDestroy = false;
        this.mHandler = new Handler(Looper.myLooper());
        initTitleBar(context);
        initBottomBar(context);
        if (ExtraInfo.isWebApp() || ExtraInfo.getDisplayType() == 1) {
            this.mToolBarHolder.setVisibility(8);
            this.mToolbar.setVisibility(8);
            this.mTitleBar.setVisibility(8);
        } else if (ExtraInfo.getDisplayType() == 0) {
            this.mToolBarHolder.setVisibility(0);
            this.mToolbar.setVisibility(0);
            this.mTitleBar.setVisibility(8);
        } else {
            this.mToolBarHolder.setVisibility(0);
            this.mToolbar.setVisibility(0);
            this.mTitleBar.setVisibility(0);
        }
        switchTheme();
    }

    public static void updateScreenSize(Activity activity) {
        if (activity != null) {
            sWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
            sHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
        }
    }

    public void delayInitWebView() {
        this.mHandler.post(new Runnable() {
            public void run() {
                try {
                    BrowserWindowSP.this.initWebView(BrowserWindowSP.this.getContext());
                    BrowserWindowSP.this.mHandler.postDelayed(new Runnable() {
                        public void run() {
                            if (!BrowserWindowSP.this.mDestroy) {
                                try {
                                    byte[] postData = ExtraInfo.getPostData();
                                    if (postData != null) {
                                        BrowserWindowSP.this.postUrl(BrowserWindowSP.this.mEntryUrl, postData);
                                    } else {
                                        BrowserWindowSP.this.openUrl(BrowserWindowSP.this.mEntryUrl);
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }, 100);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void initTitleBar(Context context) {
        this.mTitleBar = (RelativeLayout) this.mSpActivity.findViewById(MttResources.getId("titlebar"));
        this.mRetrun = (Button) this.mSpActivity.findViewById(MttResources.getId("return_app"));
        this.mMore = (Button) this.mSpActivity.findViewById(MttResources.getId("more"));
        this.mTitle = (TextView) this.mSpActivity.findViewById(MttResources.getId("title"));
        this.mProgressBar = (ProgressBar) this.mSpActivity.findViewById(MttResources.getId("progress"));
        this.mRetrun.setOnClickListener(this);
        this.mMore.setOnClickListener(this);
        if (ExtraInfo.getExtraTitleItem() == null || ExtraInfo.getExtraTitleItem().size() <= 0) {
            this.mTitle.setVisibility(0);
        } else {
            this.mTitle.setVisibility(8);
            updateTitleItem(ExtraInfo.getExtraTitleItem());
        }
        if (!TextUtils.isEmpty(ExtraInfo.getTitle())) {
            this.mTitle.setText(ExtraInfo.getTitle());
        }
        if (ExtraInfo.getTitleBarHeight() != -1) {
            ViewGroup.LayoutParams titlebarLayoutParams = this.mTitleBar.getLayoutParams();
            titlebarLayoutParams.height = ExtraInfo.getTitleBarHeight();
            this.mTitleBar.setLayoutParams(titlebarLayoutParams);
        }
        if (!ExtraInfo.isHasReadMode() && !ExtraInfo.isHasFav() && !ExtraInfo.isHasBookmark() && !ExtraInfo.isCanShare() && !ExtraInfo.isCanCopyLink() && !ExtraInfo.isCanOpenMtt() && !ExtraInfo.isCanOpenBrowser()) {
            if (ExtraInfo.getExtraMoreItem() == null || ExtraInfo.getExtraMoreItem().size() < 1) {
                this.mMore.setVisibility(4);
            }
        }
    }

    public void updateTitleItem(ArrayList<ExtendItem> arrayList) {
        ArrayList<ExtendItem> extraTitleItem = ExtraInfo.getExtraTitleItem();
        List<String> nameList = new ArrayList<>();
        Iterator<ExtendItem> it = extraTitleItem.iterator();
        while (it.hasNext()) {
            nameList.add(it.next().getLabel().toString());
        }
        new ArrayAdapter<>(getContext(), MttResources.getLayoutId("thrdcall_spinner_title"), nameList).setDropDownViewResource(MttResources.getLayoutId("thrdcall_spinner_list"));
    }

    /* access modifiers changed from: package-private */
    public void initWebView(Context context) {
        this.mWebView = WebViewSpBase.createWebView(context, this);
        WebViewSpManager.addWebView(this.mWebViewId, this.mWebView);
        ((WebViewSp) this.mWebView).setWebViewId(this.mWebViewId);
        ((WebViewSp) this.mWebView).init();
        if (this.mCustomJs != null) {
            ((WebViewSp) this.mWebView).initJs(this.mCustomJs);
        }
        addViewToBrowserWindow();
    }

    private void addViewToBrowserWindow() {
        ((FrameLayout) this.mSpActivity.findViewById(MttResources.getId("webview"))).addView((View) this.mWebView);
    }

    private void initBottomBar(Context context) {
        this.mToolBarHolder = (FrameLayout) this.mSpActivity.findViewById(MttResources.getId("toolbar_holder"));
        this.mToolbar = (LinearLayout) this.mSpActivity.findViewById(MttResources.getId("toolbar"));
        this.mBack = (ImageButton) this.mSpActivity.findViewById(MttResources.getId("back"));
        this.mBack.setAlpha(128);
        this.mBack.setOnClickListener(this);
        this.mForward = (ImageButton) this.mSpActivity.findViewById(MttResources.getId("forward"));
        this.mForward.setAlpha(128);
        this.mForward.setOnClickListener(this);
        this.mRefresh = (ImageButton) this.mSpActivity.findViewById(MttResources.getId(Headers.REFRESH));
        this.mRefresh.setOnClickListener(this);
        this.mStop = (ImageButton) this.mSpActivity.findViewById(MttResources.getId("stop"));
        this.mStop.setOnClickListener(this);
        this.mFav = (ImageButton) this.mSpActivity.findViewById(MttResources.getId("fav"));
        this.mFav.setOnClickListener(this);
        this.mPlus = (ImageButton) this.mSpActivity.findViewById(MttResources.getId("plus"));
        this.mPlus.setOnClickListener(this);
        if (ExtraInfo.getExtraFavItem() != null) {
            this.mFav.setImageDrawable(ThemeSwitcher.initTransDrawable(getContext(), ExtraInfo.getExtraFavItem()));
        } else if (!ExtraInfo.isHasFav()) {
            this.mFav.setVisibility(4);
        }
        if (ExtraInfo.getExtraPlusItem() != null) {
            this.mPlus.setVisibility(0);
            this.mPlus.setBackgroundDrawable(ThemeSwitcher.initTransDrawable(getContext(), ExtraInfo.getExtraPlusItem()));
        } else {
            this.mPlus.setVisibility(4);
        }
        if (ExtraInfo.getToolBarHeight() != -1) {
            ViewGroup.LayoutParams holderLayoutParams = this.mToolBarHolder.getLayoutParams();
            holderLayoutParams.height = ExtraInfo.getToolBarHeight();
            ViewGroup.LayoutParams toolbarLayoutParams = this.mToolbar.getLayoutParams();
            toolbarLayoutParams.height = ExtraInfo.getToolBarHeight();
            this.mToolBarHolder.setLayoutParams(holderLayoutParams);
            this.mToolbar.setLayoutParams(toolbarLayoutParams);
        }
    }

    private void switchTheme() {
        ArrayList<ExtendItem> extraThemeItem = ExtraInfo.getExtraThemeItem();
        if (extraThemeItem != null && extraThemeItem.size() != 0) {
            int padding = getResources().getDimensionPixelOffset(MttResources.getDimensId("thrdcall_titlebar_btn_padding"));
            Iterator<ExtendItem> it = extraThemeItem.iterator();
            while (it.hasNext()) {
                ExtendItem item = it.next();
                switch (item.getID()) {
                    case 0:
                        ThemeSwitcher.doSwitch(this.mTitleBar, item);
                        if (item.getTextColor() != 0) {
                            this.mTitle.setTextColor(item.getTextColor());
                        }
                        if (item.getTextSize() == 0) {
                            break;
                        } else {
                            this.mTitle.setTextSize(1, (float) item.getTextSize());
                            break;
                        }
                    case 1:
                        ThemeSwitcher.doSwitch(this.mRetrun, item);
                        this.mRetrun.setPadding(padding * 2, padding, padding, padding);
                        this.mRetrun.setHeight(getResources().getDimensionPixelOffset(MttResources.getDimensId("thrdcall_back_btn_height")));
                        break;
                    case 2:
                        ThemeSwitcher.doSwitch(this.mMore, item);
                        this.mMore.setPadding(padding * 2, padding, padding * 2, padding);
                        this.mRetrun.setHeight(getResources().getDimensionPixelOffset(MttResources.getDimensId("thrdcall_back_btn_height")));
                        break;
                    case 10:
                        ThemeSwitcher.doSwitch(this.mToolbar, item);
                        break;
                    case 11:
                        ThemeSwitcher.doSwitch(this.mBack, item);
                        break;
                    case 12:
                        ThemeSwitcher.doSwitch(this.mForward, item);
                        break;
                    case 14:
                        ThemeSwitcher.doSwitch(this.mRefresh, item);
                        break;
                    case 15:
                        ThemeSwitcher.doSwitch(this.mFav, item);
                        break;
                    case 16:
                        ThemeSwitcher.doSwitch(this.mStop, item);
                        break;
                    case 30:
                        ThemeSwitcher.doSwitch((ImageView) this.mSpActivity.findViewById(MttResources.getId("splash_logo")), item);
                        break;
                }
            }
        }
    }

    public void onClick(View v) {
        if (v == this.mRetrun) {
            this.mSpActivity.doExit();
        } else if (v == this.mMore) {
            showMore();
        } else if (v == this.mBack) {
            this.mWebView.back(true);
        } else if (v == this.mForward) {
            this.mWebView.forward();
        } else if (v == this.mRefresh) {
            this.mWebView.reload();
        } else if (v == this.mStop) {
            this.mWebView.stopLoading();
        } else if (v == this.mFav) {
            favPage();
            if (ExtraInfo.getExtraFavItem() != null) {
                sendRsp(ExtraInfo.getExtraFavItem(), MttApi.PLUS_RSP);
            } else {
                Toast.makeText(getContext(), MttResources.getStringId("thrdcall_menu_fav"), 0).show();
            }
        } else if (v == this.mPlus) {
            sendRsp(ExtraInfo.getExtraPlusItem(), MttApi.PLUS_RSP);
        }
    }

    public void onSreenOritationChage() {
        if (this.mShareDialog != null && this.mShareDialog.isShowing()) {
            this.mShareDialog.layoutWindow();
        }
    }

    public void showMore() {
        getMoreDialog().show();
    }

    /* access modifiers changed from: private */
    public void openUrl(String url) {
        try {
            if (isUrlValid(url)) {
                this.mWebView.loadUrl(url);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void postUrl(String url, byte[] postData) {
        try {
            if (isUrlValid(url)) {
                this.mWebView.postUrl(url, postData);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean isUrlValid(String url) {
        if (!TextUtils.isEmpty(url)) {
            return true;
        }
        onPageFinished((IWebView) null, (String) null);
        Toast.makeText(getContext(), MttResources.getStringId("thrdcall_unfetch_url"), 0).show();
        return false;
    }

    private void refreshToolBar() {
        boolean canGoBack = this.mWebView.canGoBack();
        if (canGoBack) {
            this.mBack.setAlpha(255);
        } else {
            this.mBack.setAlpha(128);
        }
        this.mBack.setClickable(canGoBack);
        boolean canGoForward = this.mWebView.canGoForward();
        if (canGoForward) {
            this.mForward.setAlpha(255);
        } else {
            this.mForward.setAlpha(128);
        }
        this.mForward.setClickable(canGoForward);
    }

    private void doAfterStartLoading() {
        this.mProgressBar.setVisibility(0);
        this.mRefresh.setVisibility(8);
        this.mStop.setVisibility(0);
    }

    private void doAfterStop() {
        this.mProgressBar.setVisibility(8);
        this.mStop.setVisibility(8);
        this.mRefresh.setVisibility(0);
    }

    private void doAfterTitleChange(String title) {
        if (ExtraInfo.isEnableChangeTitle()) {
            this.mTitle.setText(title);
        }
    }

    public void destroy() {
        this.mWebView.destroy();
        this.mDestroy = true;
        WebViewSpManager.removeWebView(this.mWebViewId);
        WebViewProxyManager.getInstance().onWebViewDestroy(this.mWebViewId);
    }

    public void deActive() {
        this.mWebView.deactive();
    }

    public void active() {
        this.mWebView.active();
    }

    public void onReceivedTitle(IWebView view, String title) {
        doAfterTitleChange(title);
    }

    public void onBackOrForwardChanged(IWebView view) {
        refreshToolBar();
    }

    public boolean shouldOverrideUrlLoading(IWebView view, String url) {
        if (this.mNotifyUrlPrefix == null || url == null || !url.startsWith(this.mNotifyUrlPrefix)) {
            return false;
        }
        return WebViewProxyManager.getInstance().shouldOverrideUrlLoading(this.mWebViewId, url);
    }

    public void onPageStarted(IWebView view, String url, Bitmap favicon) {
        WebViewProxyManager.getInstance().onPageStarted(this.mWebViewId, url);
        doAfterStartLoading();
    }

    public void onPageFinished(IWebView view, String url) {
        WebViewProxyManager.getInstance().onPageFinished(this.mWebViewId, url);
        doAfterStop();
        refreshToolBar();
        this.mPv++;
    }

    public int getPv() {
        return this.mPv;
    }

    public void onDownload(String url, String userAgent, String contentDisposition, String mimetype, long contentLength, String referUrl) {
        ExtendItem downloadItem = ExtraInfo.getDownloadItem();
        if (downloadItem != null) {
            RspContent shareContent = new RspContent(downloadItem.getID(), this.mWebView.getTitle(), this.mWebView.getUrl());
            shareContent.setDownloadInfo(url, userAgent, contentDisposition, mimetype, contentLength, referUrl);
            doSendRsp(MttApi.DOWNLOAD_RSP, downloadItem, shareContent);
            return;
        }
        openByBrowser(url);
    }

    public void onReceivedError(IWebView view, int errorCode, String description, String failingUrl) {
        WebViewProxyManager.getInstance().onReceivedError(this.mWebViewId, errorCode, description, failingUrl);
    }

    private DialogSp getMoreDialog() {
        if (this.mMoreDialog == null) {
            if (ExtraInfo.getMenuStyle() == 0) {
                this.mMoreDialog = new MoreDialogGridView(getContext(), this);
                this.mMoreDialog.getWindow().setGravity(80);
            } else if (ExtraInfo.getMenuStyle() == 1) {
                this.mMoreDialog = new MoreDialogActionSheet(getContext(), this);
                this.mMoreDialog.getWindow().setGravity(80);
            }
        }
        return this.mMoreDialog;
    }

    private DialogSp getShareDialog(String url) {
        if (url == null) {
            url = this.mWebView.getUrl();
        }
        if (this.mShareDialog == null) {
            this.mShareDialog = new ShareDialogSp(getContext(), this);
        }
        ((ShareDialogSp) this.mShareDialog).setShareUrl(url);
        return this.mShareDialog;
    }

    public void sharePage(String url) {
        getShareDialog(url).show();
    }

    public void favPage() {
        if (ExtraInfo.getExtraParams() != null) {
            try {
                CollectManager.getInstane().addFavoriteUrl(this.mWebView.getTitle(), this.mWebView.getUrl(), ExtraInfo.getExtraParams().getString(RecommendParams.KEY_UIN), ExtraInfo.getExtraParams().getString("sid"), ExtraInfo.getExtraParams().getInt("channel"));
            } catch (Exception e) {
                e.printStackTrace();
            } catch (NoClassDefFoundError e2) {
                e2.printStackTrace();
            }
        }
    }

    public void saveFlow() {
        new AlertDialog.Builder(getContext()).setTitle(getResources().getString(MttResources.getStringId("thrdcall_recom_mtt_title"))).setMessage(getResources().getString(MttResources.getStringId("thrdcall_recom_mtt_content"))).setNegativeButton(getResources().getString(MttResources.getStringId("thrdcall_cancel")), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton(getResources().getString(MttResources.getStringId("thrdcall_confirm")), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                BrowserWindowSP.this.openUrl("http://mdc.html5.qq.com/d/directdown.jsp?channel_id=");
                dialog.dismiss();
            }
        }).show();
    }

    public void fitScreen() {
        this.mWebView.reload();
    }

    public void copyLink() {
        ((ClipboardManager) getContext().getSystemService("clipboard")).setText(this.mWebView.getUrl());
        Toast.makeText(getContext(), MttResources.getStringId("thrdcall_copy_sucsess"), 0).show();
    }

    public void openByMtt(String url) {
        String desUrl = getValidUrl(url);
        if (TextUtils.isEmpty(desUrl)) {
            Toast.makeText(getContext(), MttResources.getStringId("thrdcall_unfetch_url"), 0).show();
            return;
        }
        Intent intent = new Intent();
        intent.setData(Uri.parse(desUrl));
        intent.setAction("com.tencent.QQBrowser.action.VIEW");
        try {
            getContext().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            new AlertDialog.Builder(getContext()).setTitle(getResources().getString(MttResources.getStringId("thrdcall_recom_mtt_title"))).setMessage(getResources().getString(MttResources.getStringId("thrdcall_recom_mtt_content"))).setNegativeButton(getResources().getString(MttResources.getStringId("thrdcall_cancel")), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setPositiveButton(getResources().getString(MttResources.getStringId("thrdcall_confirm")), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    BrowserWindowSP.this.openUrl("http://mdc.html5.qq.com/d/directdown.jsp?channel_id=");
                    dialog.dismiss();
                }
            }).show();
        }
    }

    public void downloadVideoByMtt(String url, String referUrl, String title, int len, int videoType) {
        Intent intent = new Intent();
        intent.setAction("com.tencent.QQBrowser.action.VIEW");
        intent.setData(Uri.parse(url));
        intent.putExtra("VIDEO_URL", url);
        intent.putExtra("VIDEO_REFER_URL", referUrl);
        intent.putExtra("VIDEO_TITLE", title);
        intent.putExtra("VIDEO_LENGTH", len);
        intent.putExtra("VIDEO_TYPE", videoType);
        getContext().startActivity(intent);
    }

    public void openByBrowser(String url) {
        try {
            String desUrl = getValidUrl(url);
            if (TextUtils.isEmpty(desUrl)) {
                Toast.makeText(getContext(), MttResources.getStringId("thrdcall_unfetch_url"), 0).show();
                return;
            }
            Intent intent = new Intent();
            intent.setData(Uri.parse(desUrl));
            intent.setAction("android.intent.action.VIEW");
            getContext().startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void doSendRsp(String rspType, ExtendItem extendItem, RspContent shareContent) {
        Intent rspIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(rspType, shareContent);
        rspIntent.putExtras(bundle);
        try {
            rspIntent.setComponent(extendItem.getComponentName());
            getContext().startActivity(rspIntent);
        } catch (Exception e) {
            this.mSpActivity.setResult(MttApi.RESULT_SHARE_RSP, rspIntent);
            this.mSpActivity.doExit();
        }
    }

    public void sendRsp(ExtendItem extendItem, String rspType) {
        if (extendItem != null) {
            String desUrl = extendItem.getDesUrl();
            if (!TextUtils.isEmpty(desUrl)) {
                openInternalUrl(desUrl);
                return;
            }
            Bitmap bitmap = null;
            if (extendItem.isNeedSnapshot()) {
                bitmap = MttCtrlUtil.getScaleBitmap(this.mWebView.snapshotWholePage(this.mWebThumbWidth, this.mWebThumbHeight, IWebView.RatioRespect.RESPECT_WIDTH, 1), this.mWebThumbWidth, this.mWebThumbHeight, true, true, Bitmap.Config.RGB_565);
            }
            initRspIntent(extendItem, rspType, bitmap);
        }
    }

    public void openInternalUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            this.mWebView.loadUrl(url);
        }
    }

    private void initRspIntent(ExtendItem extendItem, String rspType, Bitmap bitmap) {
        File saveBitmap;
        try {
            String path = String.valueOf(QR_CATCH_PATH) + Constants.URL_PATH_DELIMITER + System.currentTimeMillis() + ".png";
            Intent rspIntent = new Intent();
            String imagePath = null;
            if (!(bitmap == null || (saveBitmap = saveBitmap(path, bitmap)) == null)) {
                imagePath = saveBitmap.getAbsolutePath();
            }
            RspContent shareContent = new RspContent(extendItem.getID(), this.mWebView.getTitle(), this.mWebView.getUrl());
            shareContent.setImagePath(imagePath);
            Bundle bundle = new Bundle();
            bundle.putSerializable(rspType, shareContent);
            rspIntent.putExtras(bundle);
            try {
                rspIntent.setComponent(extendItem.getComponentName());
                getContext().startActivity(rspIntent);
            } catch (Exception e) {
                this.mSpActivity.setResult(MttApi.RESULT_SHARE_RSP, rspIntent);
                this.mSpActivity.doExit();
            }
        } catch (Exception e2) {
        }
    }

    private String getValidUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            return url;
        }
        String desUrl = this.mWebView.getUrl();
        if (TextUtils.isEmpty(desUrl)) {
            return this.mEntryUrl;
        }
        return desUrl;
    }

    private File saveBitmap(String destPath, Bitmap pic) {
        File file;
        if (pic == null) {
            return null;
        }
        try {
            File file2 = new File(destPath);
            try {
                file2.createNewFile();
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file2));
                pic.compress(Bitmap.CompressFormat.PNG, 100, bos);
                bos.flush();
                bos.close();
                file = file2;
            } catch (Exception e) {
                File file3 = file2;
                file = null;
                return file;
            }
        } catch (Exception e2) {
            file = null;
            return file;
        }
        return file;
    }

    public void requesetFullScreen() {
        Activity activity = (Activity) getContext();
        if (activity != null) {
            activity.getWindow().setFlags(1024, 1024);
            activity.setRequestedOrientation(0);
            this.mTitleBar.setVisibility(8);
            this.mToolbar.setVisibility(8);
            this.mToolBarHolder.setVisibility(8);
        }
    }

    public void exitFullScreen() {
        Activity activity = (Activity) getContext();
        activity.getWindow().clearFlags(1024);
        activity.setRequestedOrientation(-1);
        this.mTitleBar.setVisibility(0);
        this.mToolbar.setVisibility(0);
        this.mToolBarHolder.setVisibility(0);
    }

    public void exiteBrowser() {
        this.mSpActivity.doExit();
    }

    public boolean restoreWebView() {
        return false;
    }

    public void back() {
        this.mWebView.loadUrl("javascript:try { if(typeof(eval(window.back))==\"function\"){window.back();}else {history.go(-1);}}catch(e){history.go(-1); } ");
    }

    public void showImageSaveDialog(String url, Bitmap bitmap) {
        Dialog imageSavedialog;
        if (ExtraInfo.getMenuStyle() == 1) {
            imageSavedialog = new ImageSaveDialogActionSheet(getContext(), (IWebViewClientSp) null, bitmap, url);
        } else {
            imageSavedialog = new ImageSaveDialogPupUp(getContext(), bitmap, url);
        }
        imageSavedialog.show();
    }

    private static class ImageSaveDialogPupUp extends Dialog {
        public ImageSaveDialogPupUp(Context context, final Bitmap bitmap, final String url) {
            super(context);
            Window window = getWindow();
            window.addFlags(2);
            window.clearFlags(1048576);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.dimAmount = 0.5f;
            window.setAttributes(attributes);
            setTitle(MttResources.getStringId("thrdcall_save_pic_title"));
            setCanceledOnTouchOutside(true);
            Resources resources = AppEngine.getInstance().getContext().getResources();
            TextView tv = new TextView(AppEngine.getInstance().getContext());
            tv.setClickable(true);
            tv.setText(MttResources.getStringId("thrdcall_save_pic_item"));
            tv.setTextColor(resources.getColor(MttResources.getColorId("thrdcall_black")));
            StateListDrawable stateList = new StateListDrawable();
            stateList.addState(new int[]{16842910, 16842908}, resources.getDrawable(17170456));
            stateList.addState(new int[]{16842919, 16842910}, resources.getDrawable(17170456));
            stateList.addState(new int[0], resources.getDrawable(17170443));
            tv.setBackgroundDrawable(stateList);
            int padding = (int) resources.getDimension(MttResources.getDimensId("thrdcall_more_text_leftmargin"));
            tv.setPadding(padding, padding * 2, padding, padding * 2);
            tv.setTextSize(1, 16.0f);
            tv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    SpecialCallUtility.saveImage(url, bitmap);
                    ImageSaveDialogPupUp.this.dismiss();
                }
            });
            addContentView(tv, new RelativeLayout.LayoutParams(-1, -2));
        }

        public void show() {
            super.show();
            getWindow().setLayout((int) (((float) BrowserWindowSP.sWidth) * 0.8f), -2);
        }
    }

    private static class ImageSaveDialogActionSheet extends MoreDialogActionSheet {
        private Bitmap mBitmap;
        private String mUrl;

        public ImageSaveDialogActionSheet(Context context, IWebViewClientSp pageController, Bitmap bitmap, String url) {
            super(context, pageController);
            this.mBitmap = bitmap;
            this.mUrl = url;
        }

        /* access modifiers changed from: protected */
        public void addExtendItems() {
        }

        /* access modifiers changed from: protected */
        public void addSystemItems() {
            this.mMoreItems.add(new ExtendItem(0, this.mRes.getString(MttResources.getStringId("thrdcall_save_pic_item"))));
        }

        public void onClick(View v) {
            if (v.getTag() instanceof ExtendItem) {
                ExtendItem item = (ExtendItem) v.getTag();
                if (item.getID() == 0 && item.getLabel().toString().equals(this.mRes.getString(MttResources.getStringId("thrdcall_save_pic_item")))) {
                    SpecialCallUtility.saveImage(this.mUrl, this.mBitmap);
                }
            }
            dismiss();
        }
    }

    public void openReadMode() {
        new AlertDialog.Builder(getContext()).setTitle(getResources().getString(MttResources.getStringId("thrdcall_recom_mtt_title"))).setMessage(getResources().getString(MttResources.getStringId("thrdcall_recom_mtt_content"))).setNegativeButton(getResources().getString(MttResources.getStringId("thrdcall_cancel")), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton(getResources().getString(MttResources.getStringId("thrdcall_confirm")), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                BrowserWindowSP.this.openUrl("http://mdc.html5.qq.com/d/directdown.jsp?channel_id=");
                dialog.dismiss();
            }
        }).show();
    }

    public void addBookmark() {
    }
}
