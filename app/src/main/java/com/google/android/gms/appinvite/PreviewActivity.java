package com.google.android.gms.appinvite;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import com.google.android.gms.common.annotation.KeepForSdkWithMembers;
import java.util.ArrayList;

@KeepForSdkWithMembers
public class PreviewActivity extends Activity {
    public static final String ACTION_PREVIEW = "com.google.android.gms.appinvite.ACTION_PREVIEW";
    public static final String EXTRA_LAYOUT_RES_ID = "com.google.android.gms.appinvite.LAYOUT_RES_ID";
    public static final String EXTRA_TABS = "com.google.android.gms.appinvite.TABS";
    public static final String EXTRA_VIEWS = "com.google.android.gms.appinvite.VIEWS";
    public static final String KEY_TAB_CONTENT_ID = "tabContentId";
    public static final String KEY_TAB_TAG = "tabTag";
    public static final String KEY_TEXT_VIEW_IS_TITLE = "TextView_isTitle";
    public static final String KEY_TEXT_VIEW_TEXT = "TextView_text";
    public static final String KEY_TEXT_VIEW_TEXT_COLOR = "TextView_textColor";
    public static final String KEY_VIEW_BACKGROUND_COLOR = "View_backgroundColor";
    public static final String KEY_VIEW_ID = "View_id";
    public static final String KEY_VIEW_MIN_HEIGHT = "View_minHeight";
    public static final String KEY_VIEW_ON_CLICK_LISTENER = "View_onClickListener";
    public static final String KEY_WEB_VIEW_DATA = "WebView_data";
    public static final String ON_CLICK_LISTENER_CLOSE = "close";

    private final View zza(Context context, ViewGroup viewGroup, Bundle bundle) {
        View inflate = LayoutInflater.from(context).inflate(bundle.getInt(EXTRA_LAYOUT_RES_ID), viewGroup, false);
        ArrayList parcelableArrayList = bundle.getParcelableArrayList(EXTRA_VIEWS);
        if (parcelableArrayList != null) {
            ArrayList arrayList = parcelableArrayList;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                int i2 = i + 1;
                Bundle bundle2 = (Bundle) arrayList.get(i);
                View findViewById = inflate.findViewById(bundle2.getInt(KEY_VIEW_ID));
                for (String str : bundle2.keySet()) {
                    char c = 65535;
                    switch (str.hashCode()) {
                        case -1829808865:
                            if (str.equals(KEY_VIEW_MIN_HEIGHT)) {
                                c = 1;
                                break;
                            }
                            break;
                        case -499175494:
                            if (str.equals(KEY_TEXT_VIEW_TEXT)) {
                                c = 3;
                                break;
                            }
                            break;
                        case -111184848:
                            if (str.equals(KEY_WEB_VIEW_DATA)) {
                                c = 6;
                                break;
                            }
                            break;
                        case 573559753:
                            if (str.equals(KEY_TEXT_VIEW_TEXT_COLOR)) {
                                c = 4;
                                break;
                            }
                            break;
                        case 966644059:
                            if (str.equals(KEY_VIEW_BACKGROUND_COLOR)) {
                                c = 0;
                                break;
                            }
                            break;
                        case 1729346977:
                            if (str.equals(KEY_TEXT_VIEW_IS_TITLE)) {
                                c = 5;
                                break;
                            }
                            break;
                        case 1920443715:
                            if (str.equals(KEY_VIEW_ON_CLICK_LISTENER)) {
                                c = 2;
                                break;
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                            findViewById.setBackgroundColor(bundle2.getInt(str));
                            break;
                        case 1:
                            findViewById.setMinimumHeight(bundle2.getInt(str));
                            break;
                        case 2:
                            String string = bundle2.getString(str);
                            char c2 = 65535;
                            switch (string.hashCode()) {
                                case 94756344:
                                    if (string.equals(ON_CLICK_LISTENER_CLOSE)) {
                                        c2 = 0;
                                        break;
                                    }
                                    break;
                            }
                            switch (c2) {
                                case 0:
                                    findViewById.setOnClickListener(new zzb(this));
                                    break;
                            }
                        case 3:
                            if (!(findViewById instanceof TextView)) {
                                break;
                            } else {
                                ((TextView) findViewById).setText(bundle2.getCharSequence(str));
                                break;
                            }
                        case 4:
                            if (!(findViewById instanceof TextView)) {
                                break;
                            } else {
                                ((TextView) findViewById).setTextColor(bundle2.getInt(str));
                                break;
                            }
                        case 5:
                            if ((findViewById instanceof TextView) && bundle2.getBoolean(str)) {
                                setTitle(((TextView) findViewById).getText());
                                break;
                            }
                        case 6:
                            if (!(findViewById instanceof ViewGroup)) {
                                break;
                            } else {
                                WebView webView = new WebView(this);
                                webView.loadData(bundle2.getString(str), "text/html; charset=utf-8", "UTF-8");
                                ((ViewGroup) findViewById).addView(webView, new ViewGroup.LayoutParams(-1, -1));
                                break;
                            }
                    }
                }
                i = i2;
            }
        }
        return inflate;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getCallingActivity() == null || !"com.google.android.gms".equals(getCallingActivity().getPackageName())) {
            finish();
            return;
        }
        try {
            Context createPackageContext = createPackageContext("com.google.android.gms", 0);
            Bundle extras = getIntent().getExtras();
            View zza = zza(createPackageContext, (ViewGroup) null, extras);
            if (zza == null) {
                finish();
                return;
            }
            TabHost tabHost = (TabHost) zza.findViewById(16908306);
            TabWidget tabWidget = (TabWidget) zza.findViewById(16908307);
            ArrayList parcelableArrayList = extras.getParcelableArrayList(EXTRA_TABS);
            if (!(tabHost == null || tabWidget == null || parcelableArrayList == null)) {
                tabHost.setup();
                ArrayList arrayList = parcelableArrayList;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    Bundle bundle2 = (Bundle) obj;
                    TabHost.TabSpec newTabSpec = tabHost.newTabSpec(bundle2.getString(KEY_TAB_TAG));
                    newTabSpec.setContent(bundle2.getInt(KEY_TAB_CONTENT_ID));
                    newTabSpec.setIndicator(zza(createPackageContext, tabWidget, bundle2));
                    tabHost.addTab(newTabSpec);
                }
            }
            setContentView(zza);
        } catch (PackageManager.NameNotFoundException e) {
            finish();
        }
    }
}
