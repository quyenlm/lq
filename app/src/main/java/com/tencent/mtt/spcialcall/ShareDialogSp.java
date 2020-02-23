package com.tencent.mtt.spcialcall;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.tencent.mtt.common.utils.MttResources;
import com.tencent.mtt.spcialcall.sdk.ExtendItem;
import com.tencent.mtt.spcialcall.sdk.MttApi;
import java.util.ArrayList;

public class ShareDialogSp extends DialogSp {
    private Button mCancel = ((Button) findViewById(MttResources.getId("cancel")));
    /* access modifiers changed from: private */
    public String mShareInfo;
    private ListView mShareListView = ((ListView) findViewById(MttResources.getId(HttpRequestParams.NOTICE_LIST)));

    public ShareDialogSp(Context context, IWebViewClientSp pageController) {
        super(context, pageController);
        initWindow();
        setContentView(MttResources.getLayoutId("thrdcall_share_item"));
        this.mCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShareDialogSp.this.dismiss();
            }
        });
        addExtendItems();
        addSystemItems();
    }

    public void setShareUrl(String url) {
        this.mShareInfo = url;
    }

    public void show() {
        super.show();
        layoutWindow();
    }

    public void layoutWindow() {
        int width = (int) (((float) Math.min(BrowserWindowSP.sWidth, BrowserWindowSP.sHeight)) * 0.9f);
        int height = getContext().getResources().getDimensionPixelSize(MttResources.getDimensId("thrdcall_more_window_height"));
        if (BrowserWindowSP.sWidth > BrowserWindowSP.sHeight) {
            height = -1;
        }
        getWindow().setLayout(width, height);
    }

    private void initWindow() {
        requestWindowFeature(1);
        Window window = getWindow();
        window.setGravity(17);
        window.setBackgroundDrawableResource(MttResources.getColorId("thrdcall_transparent"));
        window.addFlags(2);
        window.clearFlags(1048576);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0.5f;
        window.setAttributes(lp);
    }

    /* access modifiers changed from: private */
    public Intent getShareIntent(String shareInfo) {
        Intent shareIntent = new Intent("android.intent.action.SEND");
        shareIntent.setType("text/plain");
        shareIntent.putExtra("android.intent.extra.TEXT", shareInfo);
        return shareIntent;
    }

    private class ShareGridAdapter extends BaseAdapter {
        /* access modifiers changed from: private */
        public ArrayList<ExtendItem> shareItems;

        public ShareGridAdapter(ArrayList<ExtendItem> shareItems2) {
            this.shareItems = shareItems2;
        }

        public int getCount() {
            return this.shareItems.size();
        }

        public Object getItem(int position) {
            return this.shareItems.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View convertView2 = createListItem(ThemeSwitcher.initTransDrawable(ShareDialogSp.this.getContext(), this.shareItems.get(position)), this.shareItems.get(position).getLabel());
            convertView2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        ExtendItem shareItem = (ExtendItem) ShareGridAdapter.this.shareItems.get(position);
                        if (shareItem.getID() == -1) {
                            Intent shareIntent = ShareDialogSp.this.getShareIntent(ShareDialogSp.this.mShareInfo);
                            shareIntent.setComponent(shareItem.getComponentName());
                            ShareDialogSp.this.getContext().startActivity(shareIntent);
                        } else {
                            ShareDialogSp.this.mPageController.sendRsp(shareItem, MttApi.SHARE_RSP);
                        }
                    } catch (Exception e) {
                        Toast.makeText(ShareDialogSp.this.getContext(), MttResources.getStringId("thrdcall_sharepage_find_app_fail"), 0).show();
                    }
                    ShareDialogSp.this.dismiss();
                }
            });
            return convertView2;
        }

        public View createListItem(Drawable icon, CharSequence label) {
            int iconWidth = ShareDialogSp.this.mRes.getDimensionPixelSize(MttResources.getDimensId("thrdcall_list_header_item_size"));
            int iconHeight = ShareDialogSp.this.mRes.getDimensionPixelSize(MttResources.getDimensId("thrdcall_list_header_item_size"));
            TextView tv = new TextView(ShareDialogSp.this.getContext());
            tv.setClickable(true);
            tv.setFocusable(false);
            tv.setBackgroundResource(MttResources.getDrawableId("thrdcall_more_item_bkg"));
            tv.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
            tv.setGravity(1);
            int itemPadding = ShareDialogSp.this.mRes.getDimensionPixelSize(MttResources.getDimensId("thrdcall_list_header_item_margine"));
            tv.setPadding(0, itemPadding, 0, itemPadding);
            tv.setTextAppearance(ShareDialogSp.this.getContext(), MttResources.getStyleId("thrdcall_more_text"));
            tv.setSingleLine(true);
            tv.setText(label);
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setTextSize(0, (float) ShareDialogSp.this.mRes.getDimensionPixelSize(MttResources.getDimensId("thrdcall_more_header_text_size")));
            tv.setShadowLayer(2.0f, 0.0f, 2.0f, ShareDialogSp.this.mRes.getColor(MttResources.getColorId("thrdcall_more_text_shadow_color")));
            if (icon != null) {
                icon.setBounds(0, 0, iconWidth, iconHeight);
                tv.setCompoundDrawables((Drawable) null, icon, (Drawable) null, (Drawable) null);
                tv.setCompoundDrawablePadding(ShareDialogSp.this.mRes.getDimensionPixelSize(MttResources.getDimensId("thrdcall_list_header_item_padding")));
            }
            return tv;
        }
    }

    private class ShareListAdapter extends BaseAdapter {
        /* access modifiers changed from: private */
        public ArrayList<ExtendItem> shareItems;

        public ShareListAdapter(ArrayList<ExtendItem> shareItems2) {
            this.shareItems = shareItems2;
        }

        public int getCount() {
            return this.shareItems.size();
        }

        public Object getItem(int position) {
            return this.shareItems.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View convertView2 = createListItem(new BitmapDrawable(ShareDialogSp.this.getContext().getResources(), this.shareItems.get(position).getImage()), this.shareItems.get(position).getLabel());
            convertView2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        ExtendItem shareItem = (ExtendItem) ShareListAdapter.this.shareItems.get(position);
                        if (shareItem.getID() == 0) {
                            Intent shareIntent = ShareDialogSp.this.getShareIntent(ShareDialogSp.this.mShareInfo);
                            shareIntent.setComponent(shareItem.getComponentName());
                            ShareDialogSp.this.getContext().startActivity(shareIntent);
                        } else {
                            ShareDialogSp.this.mPageController.sendRsp(shareItem, MttApi.SHARE_RSP);
                        }
                    } catch (Exception e) {
                        Toast.makeText(ShareDialogSp.this.getContext(), MttResources.getStringId("thrdcall_sharepage_find_app_fail"), 0).show();
                    }
                    ShareDialogSp.this.dismiss();
                }
            });
            return convertView2;
        }

        public View createListItem(Drawable icon, CharSequence label) {
            icon.setBounds(0, 0, ShareDialogSp.this.mRes.getDimensionPixelSize(MttResources.getDimensId("thrdcall_share_dialog_icon_width")), ShareDialogSp.this.mRes.getDimensionPixelSize(MttResources.getDimensId("thrdcall_share_dialog_icon_height")));
            TextView tv = new TextView(ShareDialogSp.this.getContext());
            int itemPadding = ShareDialogSp.this.mRes.getDimensionPixelSize(MttResources.getDimensId("thrdcall_share_to_item_margin"));
            tv.setClickable(true);
            tv.setFocusable(false);
            tv.setBackgroundResource(MttResources.getDrawableId("thrdcall_more_item_bkg"));
            tv.setLayoutParams(new AbsListView.LayoutParams(-1, ShareDialogSp.this.mRes.getDimensionPixelSize(MttResources.getDimensId("thrdcall_search_engine_dialog_item_height"))));
            tv.setGravity(16);
            tv.setTextAppearance(ShareDialogSp.this.getContext(), MttResources.getStringId("thrdcall_more_text"));
            tv.setText(label);
            tv.setTextSize(0, (float) ShareDialogSp.this.mRes.getDimensionPixelSize(MttResources.getDimensId("thrdcall_more_text_size")));
            tv.setShadowLayer(2.0f, 0.0f, 2.0f, ShareDialogSp.this.mRes.getColor(MttResources.getColorId("thrdcall_more_text_shadow_color")));
            tv.setCompoundDrawables(icon, (Drawable) null, (Drawable) null, (Drawable) null);
            tv.setCompoundDrawablePadding(ShareDialogSp.this.mRes.getDimensionPixelSize(MttResources.getDimensId("thrdcall_share_to_item_margin")));
            tv.setPadding(itemPadding, 0, itemPadding, 0);
            return tv;
        }
    }

    public boolean switchTheme() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void addSystemItems() {
        ArrayList<ExtendItem> systemShareItem = new ArrayList<>();
        Intent shareIntent = getShareIntent(this.mShareInfo);
        PackageManager packageManager = getContext().getPackageManager();
        for (ResolveInfo rf : packageManager.queryIntentActivities(shareIntent, 0)) {
            ActivityInfo activityInfo = rf.activityInfo;
            Drawable icon = activityInfo.loadIcon(packageManager);
            CharSequence label = activityInfo.loadLabel(packageManager);
            ExtendItem shareItem = new ExtendItem(0);
            shareItem.setImage(icon);
            shareItem.setLabel(label);
            shareItem.setComponentName(new ComponentName(activityInfo.packageName, activityInfo.name));
            systemShareItem.add(shareItem);
        }
        this.mShareListView.setAdapter(new ShareListAdapter(systemShareItem));
    }

    /* access modifiers changed from: protected */
    public void addExtendItems() {
        if (ExtraInfo.getExtraShareItem() != null) {
            addExtraItem(ExtraInfo.getExtraShareItem());
        }
    }

    private void addExtraItem(ArrayList<ExtendItem> extraShareItems) {
        GridView gridView = new GridView(getContext());
        gridView.setLayoutParams(new AbsListView.LayoutParams(-1, (((extraShareItems.size() - 1) / 3) + 1) * this.mRes.getDimensionPixelSize(MttResources.getDimensId("thrdcall_list_header_height"))));
        gridView.setNumColumns(3);
        gridView.setVerticalScrollBarEnabled(false);
        gridView.setHorizontalScrollBarEnabled(false);
        gridView.setStretchMode(2);
        gridView.setGravity(17);
        gridView.setAdapter(new ShareGridAdapter(extraShareItems));
        this.mShareListView.addHeaderView(gridView);
        TextView divide = new TextView(getContext());
        divide.setLayoutParams(new AbsListView.LayoutParams(-1, this.mRes.getDimensionPixelSize(MttResources.getDimensId("thrdcall_list_divider_height"))));
        divide.setText(getContext().getResources().getText(MttResources.getStringId("thrdcall_share_more")));
        divide.setTextSize(0, (float) this.mRes.getDimensionPixelSize(MttResources.getDimensId("thrdcall_more_divider_text_size")));
        divide.setTextColor(-6250336);
        divide.setGravity(17);
        divide.setEnabled(false);
        divide.setFocusable(true);
        divide.setBackgroundColor(16777215);
        this.mShareListView.addHeaderView(divide);
    }
}
