package com.tencent.midas.oversea.comm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.tencent.midas.oversea.api.APMidasPayAPI;

public class APProgressDialog extends ProgressDialog {
    private String a = APCommMethod.getStringId(APMidasPayAPI.applicationContext, "unipay_waiting");
    private TextView b = null;
    private Context c = null;
    private boolean d;

    public APProgressDialog(Context context, boolean z) {
        super(context);
        APMidasPayAPI.singleton();
        this.c = context;
        this.d = z;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(APCommMethod.getLayoutId(this.c, "unipay_abroad_loadding"));
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.2f);
        alphaAnimation.setDuration(600);
        alphaAnimation.setRepeatCount(-1);
        alphaAnimation.setRepeatMode(2);
        ((ProgressBar) findViewById(APCommMethod.getId(this.c, "unipay_progress"))).setAnimation(alphaAnimation);
        alphaAnimation.start();
        this.b = (TextView) findViewById(APCommMethod.getId(this.c, "unipay_id_LoadingTxt"));
        this.b.setText(this.a);
        setCancelable(this.d);
    }

    public void setMessage(CharSequence charSequence) {
        super.setMessage(charSequence);
        this.a = String.valueOf(charSequence);
    }
}
