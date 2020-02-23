package com.tencent.rtmp2.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.tencent.rtmp2.TXLiveBase;
import com.tencent.rtmp2.TXLiveConstants;
import java.text.SimpleDateFormat;

/* compiled from: TXLogView */
public class a extends LinearLayout {
    StringBuffer a;
    private TextView b;
    private TextView c;
    private ScrollView d;
    private ScrollView e;
    private final int f;
    private boolean g;

    public a(Context context) {
        this(context, (AttributeSet) null);
    }

    public a(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = new StringBuffer("");
        this.f = 3000;
        this.g = false;
        setOrientation(1);
        this.b = new TextView(context);
        this.c = new TextView(context);
        this.d = new ScrollView(context);
        this.e = new ScrollView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0);
        layoutParams.weight = 0.2f;
        this.d.setLayoutParams(layoutParams);
        this.d.setBackgroundColor(1627389951);
        this.d.setVerticalScrollBarEnabled(true);
        this.d.setScrollbarFadingEnabled(true);
        this.b.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        this.b.setTextSize(2, 11.0f);
        this.b.setTextColor(-16777216);
        this.b.setTypeface(Typeface.MONOSPACE, 1);
        this.b.setLineSpacing(4.0f, 1.0f);
        this.b.setPadding(a(context, 2.0f), a(context, 2.0f), a(context, 2.0f), a(context, 2.0f));
        this.d.addView(this.b);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, 0);
        layoutParams2.weight = 0.8f;
        layoutParams2.topMargin = a(context, 2.0f);
        this.e.setLayoutParams(layoutParams2);
        this.e.setBackgroundColor(1627389951);
        this.e.setVerticalScrollBarEnabled(true);
        this.e.setScrollbarFadingEnabled(true);
        this.c.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.c.setTextSize(2, 13.0f);
        this.c.setTextColor(-16777216);
        this.c.setPadding(a(context, 2.0f), a(context, 2.0f), a(context, 2.0f), a(context, 2.0f));
        this.e.addView(this.c);
        addView(this.d);
        addView(this.e);
        setVisibility(8);
    }

    public static int a(Context context, float f2) {
        return (int) ((context.getResources().getDisplayMetrics().density * f2) + 0.5f);
    }

    public void a(Bundle bundle, Bundle bundle2, int i) {
        String string;
        if (!this.g && i != 2011 && i != 2012) {
            if (bundle != null && getVisibility() == 0) {
                this.b.setText(a(bundle));
            }
            if (this.a.length() <= 0) {
                this.a.append("liteav sdk version:" + TXLiveBase.getSDKVersionStr());
            }
            if (bundle2 != null && (string = bundle2.getString(TXLiveConstants.EVT_DESCRIPTION)) != null && !string.isEmpty()) {
                a(i, string);
                if (getVisibility() == 0) {
                    this.c.setText(this.a.toString());
                    a(this.e, (View) this.c);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(int i, String str) {
        if (i != 1020) {
            String format = new SimpleDateFormat("HH:mm:ss.SSS").format(Long.valueOf(System.currentTimeMillis()));
            while (this.a.length() > 3000) {
                int indexOf = this.a.indexOf("\n");
                if (indexOf == 0) {
                    indexOf = 1;
                }
                this.a = this.a.delete(0, indexOf);
            }
            this.a = this.a.append("\n[" + format + "]" + str);
        }
    }

    /* access modifiers changed from: protected */
    public String a(Bundle bundle) {
        return String.format("%-16s %-16s %-16s\n%-12s %-12s %-12s %-12s\n%-14s %-14s %-14s\n%-16s %-16s", new Object[]{"CPU:" + bundle.getString(TXLiveConstants.NET_STATUS_CPU_USAGE), "RES:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH) + "*" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT), "SPD:" + bundle.getInt(TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps", "JIT:" + bundle.getInt(TXLiveConstants.NET_STATUS_NET_JITTER), "FPS:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS), "GOP:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_GOP) + "s", "ARA:" + bundle.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps", "QUE:" + bundle.getInt(TXLiveConstants.NET_STATUS_CODEC_CACHE) + " | " + bundle.getInt(TXLiveConstants.NET_STATUS_CACHE_SIZE) + "," + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_CACHE_SIZE) + "," + bundle.getInt(TXLiveConstants.NET_STATUS_V_DEC_CACHE_SIZE) + " | " + bundle.getInt(TXLiveConstants.NET_STATUS_AV_RECV_INTERVAL) + "," + bundle.getInt(TXLiveConstants.NET_STATUS_AV_PLAY_INTERVAL) + "," + String.format("%.1f", new Object[]{Float.valueOf(bundle.getFloat(TXLiveConstants.NET_STATUS_AUDIO_PLAY_SPEED))}).toString(), "VRA:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps", "DRP:" + bundle.getInt(TXLiveConstants.NET_STATUS_CODEC_DROP_CNT) + "|" + bundle.getInt(TXLiveConstants.NET_STATUS_DROP_SIZE), "SVR:" + bundle.getString(TXLiveConstants.NET_STATUS_SERVER_IP), "AUDIO:" + bundle.getString(TXLiveConstants.NET_STATUS_AUDIO_INFO)});
    }

    public void a() {
        this.a.setLength(0);
        this.b.setText("");
        this.c.setText("");
    }

    public void a(boolean z) {
        setVisibility(z ? 0 : 8);
        if (z) {
            this.c.setText(this.a.toString());
            a(this.e, (View) this.c);
            return;
        }
        this.c.setText("");
    }

    public void b(boolean z) {
        this.g = z;
    }

    private void a(ScrollView scrollView, View view) {
        if (scrollView != null && view != null) {
            int measuredHeight = view.getMeasuredHeight() - scrollView.getMeasuredHeight();
            if (measuredHeight < 0) {
                measuredHeight = 0;
            }
            scrollView.scrollTo(0, measuredHeight);
        }
    }
}
