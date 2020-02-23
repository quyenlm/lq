package com.tencent.mtt.spcialcall;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.Toast;
import com.tencent.mtt.common.utils.MttResources;
import com.tencent.mtt.spcialcall.sdk.ExtendItem;
import com.tencent.mtt.spcialcall.sdk.MttApi;
import java.util.ArrayList;

public abstract class DialogSp extends Dialog {
    protected ArrayList<ExtendItem> mMoreItems = new ArrayList<>();
    protected IWebViewClientSp mPageController = new NullInterface();
    protected Resources mRes;

    public DialogSp(Context context, IWebViewClientSp pageController) {
        super(context, MttResources.getStyleId("DialogBase"));
        this.mPageController = pageController;
        this.mRes = context.getResources();
    }

    /* access modifiers changed from: protected */
    public void addSystemItems() {
        if (ExtraInfo.isCanShare()) {
            this.mMoreItems.add(new ExtendItem(0, this.mRes.getDrawable(MttResources.getDrawableId("thrdcall_grid_share_btn_bkg")), (CharSequence) this.mRes.getString(MttResources.getStringId("thrdcall_share"))));
        }
        if (ExtraInfo.isCanCopyLink()) {
            this.mMoreItems.add(new ExtendItem(0, this.mRes.getDrawable(MttResources.getDrawableId("thrdcall_grid_copy_btn_bkg")), (CharSequence) this.mRes.getString(MttResources.getStringId("thrdcall_copylink"))));
        }
        if (ExtraInfo.isCanOpenMtt()) {
            this.mMoreItems.add(new ExtendItem(0, this.mRes.getDrawable(MttResources.getDrawableId("thrdcall_grid_openmtt_btn_bkg")), (CharSequence) this.mRes.getString(MttResources.getStringId("thrdcall_openqbx"))));
        }
        if (ExtraInfo.isCanOpenBrowser()) {
            this.mMoreItems.add(new ExtendItem(0, this.mRes.getDrawable(MttResources.getDrawableId("thrdcall_grid_open_btn_bkg")), (CharSequence) this.mRes.getString(MttResources.getStringId("thrdcall_openbrowser"))));
        }
    }

    /* access modifiers changed from: protected */
    public void addExtendItems() {
        if (ExtraInfo.getExtraMoreItem() != null) {
            this.mMoreItems.addAll(ExtraInfo.getExtraMoreItem());
        }
    }

    /* access modifiers changed from: protected */
    public void onExtendItemOnClick(View v) {
        try {
            ExtendItem item = (ExtendItem) v.getTag();
            if (item.getID() != 0) {
                this.mPageController.sendRsp(item, MttApi.MORE_RSP);
            } else if (item.getLabel().equals(this.mRes.getString(MttResources.getStringId("thrdcall_share")))) {
                this.mPageController.sharePage((String) null);
            } else if (item.getLabel().equals(this.mRes.getString(MttResources.getStringId("thrdcall_copylink")))) {
                this.mPageController.copyLink();
            } else if (item.getLabel().equals(this.mRes.getString(MttResources.getStringId("thrdcall_openqbx")))) {
                this.mPageController.openByMtt((String) null);
            } else if (item.getLabel().equals(this.mRes.getString(MttResources.getStringId("thrdcall_openbrowser")))) {
                this.mPageController.openByBrowser((String) null);
            } else if (item.getLabel().equals(this.mRes.getString(MttResources.getStringId("thrdcall_menu_readmode")))) {
                this.mPageController.openReadMode();
            } else if (item.getLabel().equals(this.mRes.getString(MttResources.getStringId("thrdcall_menu_bookmark")))) {
                this.mPageController.addBookmark();
            } else if (item.getLabel().equals(this.mRes.getString(MttResources.getStringId("thrdcall_menu_saveflow")))) {
                this.mPageController.saveFlow();
            } else if (item.getLabel().equals(this.mRes.getString(MttResources.getStringId("thrdcall_menu_fav")))) {
                this.mPageController.favPage();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), MttResources.getStringId("thrdcall_sharepage_find_app_fail"), 0).show();
        }
    }

    public void layoutWindow() {
    }

    public boolean switchTheme() {
        return false;
    }
}
