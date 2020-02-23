package com.garena.overlay;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.GGPlatform;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.data.AuthToken;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.ImageLoader;
import com.garena.msdk.R;
import java.util.ArrayList;
import java.util.List;

public class FloatingMenuActivity extends AppCompatActivity {
    private static final String[] PERMISSIONS = {"android.permission.CAMERA", "android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    private static final String PLACEHOLDER_TOKEN = "$access_token$";
    private static final int REQUEST_FILE_CHOOSER = 33938;
    private static final int REQUEST_PERMISSION = 9025;
    private static final int REQUEST_SCREEN_CAPTURE = 47531;
    public static boolean active = false;
    public static Activity menuActivity;
    /* access modifiers changed from: private */
    public ValueCallback<Uri> mUM;
    /* access modifiers changed from: private */
    public ValueCallback<Uri[]> mUMA;
    List<FloatMenuItem> menuItemList = new ArrayList();
    private ViewPager viewPager;
    /* access modifiers changed from: private */
    public FileWebChromeClient webChromeClient = new FileWebChromeClient();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msdk_floating_menu_activity);
        menuActivity = this;
        this.viewPager = (ViewPager) findViewById(R.id.menu_pager);
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.pager_indicator);
        this.menuItemList = (List) getIntent().getSerializableExtra("menus");
        if (this.menuItemList == null || this.menuItemList.isEmpty()) {
            finish();
            FloatDotViewUtil.hide(this);
        } else {
            this.viewPager.setAdapter(new MenuPageAdapter());
            if (this.menuItemList.size() < 5) {
                indicator.setVisibility(8);
            } else {
                indicator.setVisibility(0);
                indicator.setViewPager(this.viewPager);
            }
        }
        this.webChromeClient = new FileWebChromeClient();
    }

    private class MenuPageAdapter extends PagerAdapter {
        private MenuPageAdapter() {
        }

        public int getCount() {
            if (FloatingMenuActivity.this.menuItemList == null) {
                return 0;
            }
            return (int) Math.ceil((double) (((float) FloatingMenuActivity.this.menuItemList.size()) / 4.0f));
        }

        public boolean isViewFromObject(View view, Object object) {
            return view != null && view.equals(object);
        }

        public Object instantiateItem(ViewGroup container, int position) {
            GridView gridView = new GridView(FloatingMenuActivity.this);
            gridView.setNumColumns(2);
            gridView.setCacheColorHint(0);
            gridView.setClipToPadding(false);
            gridView.setStretchMode(2);
            gridView.setHorizontalSpacing(0);
            gridView.setSelector(new StateListDrawable());
            int start = position * 4;
            gridView.setAdapter(new MenuAdapter(FloatingMenuActivity.this.menuItemList.subList(start, Math.min(start + 4, FloatingMenuActivity.this.menuItemList.size()))));
            container.addView(gridView);
            return gridView;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public class FileWebChromeClient extends WebChromeClient {
        public FileWebChromeClient() {
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            ValueCallback unused = FloatingMenuActivity.this.mUM = uploadMsg;
            Intent i = new Intent("android.intent.action.GET_CONTENT");
            i.addCategory("android.intent.category.OPENABLE");
            i.setType("image/*");
            FloatingMenuActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FloatingMenuActivity.REQUEST_FILE_CHOOSER);
        }

        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            openFileChooser(uploadMsg);
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileChooser(uploadMsg);
        }

        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            if (FloatingMenuActivity.this.mUMA != null) {
                FloatingMenuActivity.this.mUMA.onReceiveValue((Object) null);
            }
            ValueCallback unused = FloatingMenuActivity.this.mUMA = filePathCallback;
            Intent contentSelectionIntent = new Intent("android.intent.action.GET_CONTENT");
            contentSelectionIntent.addCategory("android.intent.category.OPENABLE");
            contentSelectionIntent.setType("image/*");
            Intent chooserIntent = new Intent("android.intent.action.CHOOSER");
            chooserIntent.putExtra("android.intent.extra.INTENT", contentSelectionIntent);
            chooserIntent.putExtra("android.intent.extra.TITLE", "Image Chooser");
            chooserIntent.putExtra("android.intent.extra.INITIAL_INTENTS", new Intent[0]);
            FloatingMenuActivity.this.startActivityForResult(chooserIntent, FloatingMenuActivity.REQUEST_FILE_CHOOSER);
            return true;
        }
    }

    private class MenuAdapter extends BaseAdapter {
        private List<FloatMenuItem> items;

        public MenuAdapter(List<FloatMenuItem> items2) {
            this.items = items2;
        }

        public int getCount() {
            if (this.items == null) {
                return 0;
            }
            return this.items.size();
        }

        public FloatMenuItem getItem(int position) {
            if (this.items == null) {
                return null;
            }
            return this.items.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            MenuViewHolder menuViewHolder;
            if (convertView == null) {
                convertView = FloatingMenuActivity.this.getLayoutInflater().inflate(R.layout.msdk_floating_menu_item, parent, false);
                menuViewHolder = new MenuViewHolder(convertView);
                convertView.setTag(menuViewHolder);
            } else {
                menuViewHolder = (MenuViewHolder) convertView.getTag();
            }
            final FloatMenuItem item = getItem(position);
            menuViewHolder.setData(item);
            convertView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    GGLoginSession loginSession;
                    AuthToken token;
                    GGLoginSession loginSession2;
                    AuthToken token2;
                    if (item.url.equals(SDKConstants.FLOATING_MENU.ACTION_HIDE)) {
                        FloatDotViewUtil.hide(FloatingMenuActivity.this);
                        FloatingMenuActivity.this.finish();
                    } else if (item.url.equals(SDKConstants.FLOATING_MENU.ACTION_SCREEN_RECORD)) {
                        if (Build.VERSION.SDK_INT >= 21) {
                            FloatingMenuActivity.this.startScreenRecord();
                        }
                    } else if (item.url.startsWith(SDKConstants.FLOATING_MENU.ACTION_GARENA_DEEPLINK)) {
                        FloatDotViewUtil.viewMenuItem(item);
                        String finalUrl = item.url;
                        if (!(!finalUrl.contains(FloatingMenuActivity.PLACEHOLDER_TOKEN) || (loginSession2 = GGLoginSession.getCurrentSession()) == null || (token2 = loginSession2.getTokenValue()) == null)) {
                            finalUrl = finalUrl.replace(FloatingMenuActivity.PLACEHOLDER_TOKEN, token2.getAuthToken());
                        }
                        BBLogger.i("Open url:" + finalUrl, new Object[0]);
                        try {
                            if (GGPlatform.GGIsPlatformInstalled(FloatingMenuActivity.this, 1)) {
                                GGPlatform.GGOpenUrlInGas(finalUrl);
                            } else {
                                FloatingMenuActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://mobile.garena.com")));
                            }
                        } catch (Exception e) {
                            BBLogger.e(e);
                        }
                        FloatingMenuActivity.this.finish();
                    } else if (item.url.startsWith("http://") || item.url.startsWith("https://")) {
                        FloatDotViewUtil.viewMenuItem(item);
                        String finalUrl2 = item.url;
                        if (!(!finalUrl2.contains(FloatingMenuActivity.PLACEHOLDER_TOKEN) || (loginSession = GGLoginSession.getCurrentSession()) == null || (token = loginSession.getTokenValue()) == null)) {
                            finalUrl2 = finalUrl2.replace(FloatingMenuActivity.PLACEHOLDER_TOKEN, token.getAuthToken());
                        }
                        BBLogger.i("Open url:" + finalUrl2, new Object[0]);
                        if (item.isForceEmbed()) {
                            FloatingWebDialog dialog = new FloatingWebDialog(v.getContext(), finalUrl2, FloatingMenuActivity.this.webChromeClient);
                            dialog.show();
                            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                public void onDismiss(DialogInterface dialog) {
                                    FloatingMenuActivity.this.finish();
                                }
                            });
                            return;
                        }
                        GGPlatform.GGOpenUrlInGas(finalUrl2);
                        FloatingMenuActivity.this.finish();
                    }
                }
            });
            return convertView;
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        active = true;
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        active = false;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        String dataString;
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_SCREEN_CAPTURE) {
            if (resultCode == -1) {
                startService(RecordingService.getStartVideoIntent(this, resultCode, intent));
            }
            finish();
        } else if (requestCode != REQUEST_FILE_CHOOSER) {
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                if (this.mUMA != null) {
                    Uri[] results = null;
                    if (!(resultCode != -1 || intent == null || (dataString = intent.getDataString()) == null)) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                    this.mUMA.onReceiveValue(results);
                    this.mUMA = null;
                }
            } else if (this.mUM != null) {
                this.mUM.onReceiveValue((intent == null || resultCode != -1) ? null : intent.getData());
                this.mUM = null;
            }
        }
    }

    /* access modifiers changed from: private */
    @TargetApi(21)
    public void startScreenRecord() {
        if (requestPermission()) {
            requestScreenRecord();
        }
    }

    private boolean requestPermission() {
        ArrayList<String> permissions = new ArrayList<>();
        for (String permission : PERMISSIONS) {
            if (!hasPermission(permission)) {
                permissions.add(permission);
            }
        }
        if (permissions.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(this, (String[]) permissions.toArray(new String[permissions.size()]), REQUEST_PERMISSION);
        return false;
    }

    private boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == 0;
    }

    @TargetApi(21)
    private void requestScreenRecord() {
        startActivityForResult(((MediaProjectionManager) getSystemService("media_projection")).createScreenCaptureIntent(), REQUEST_SCREEN_CAPTURE);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION /*9025*/:
                int i = 0;
                while (i < permissions.length) {
                    if (!permissions[i].equals("android.permission.WRITE_EXTERNAL_STORAGE") || grantResults[i] == 0) {
                        i++;
                    } else {
                        return;
                    }
                }
                requestScreenRecord();
                return;
            default:
                return;
        }
    }

    private static class MenuViewHolder {
        public ImageView icon;
        public View reddot;
        public TextView title;

        public MenuViewHolder(View view) {
            this.title = (TextView) view.findViewById(R.id.floating_menu_title);
            this.icon = (ImageView) view.findViewById(R.id.floating_menu_image);
            this.reddot = view.findViewById(R.id.floating_menu_reddot);
        }

        public void setData(FloatMenuItem menu) {
            int i = 0;
            if (menu != null) {
                this.title.setText(menu.title);
                if (!TextUtils.isEmpty(menu.icon)) {
                    ImageLoader.load(menu.icon).into(this.icon);
                } else {
                    this.icon.setImageResource(0);
                }
                View view = this.reddot;
                if (!menu.showRedDot) {
                    i = 8;
                }
                view.setVisibility(i);
            }
        }
    }
}
