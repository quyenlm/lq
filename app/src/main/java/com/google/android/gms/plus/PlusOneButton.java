package com.google.android.gms.plus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.internal.zzbz;
import com.google.android.gms.plus.internal.zzm;
import com.tencent.component.net.download.multiplex.download.DownloadDBHelper;
import com.tencent.imsdk.IMConfig;

public final class PlusOneButton extends FrameLayout {
    public static final int ANNOTATION_BUBBLE = 1;
    public static final int ANNOTATION_INLINE = 2;
    public static final int ANNOTATION_NONE = 0;
    public static final int DEFAULT_ACTIVITY_REQUEST_CODE = -1;
    public static final int SIZE_MEDIUM = 1;
    public static final int SIZE_SMALL = 0;
    public static final int SIZE_STANDARD = 3;
    public static final int SIZE_TALL = 2;
    /* access modifiers changed from: private */
    public View mContentView;
    private int mSize;
    private String zzD;
    private int zzbAt;
    /* access modifiers changed from: private */
    public int zzbAu;
    private OnPlusOneClickListener zzbAv;

    public class DefaultOnPlusOneClickListener implements View.OnClickListener, OnPlusOneClickListener {
        private final OnPlusOneClickListener zzbAw;

        public DefaultOnPlusOneClickListener(OnPlusOneClickListener onPlusOneClickListener) {
            this.zzbAw = onPlusOneClickListener;
        }

        public void onClick(View view) {
            Intent intent = (Intent) PlusOneButton.this.mContentView.getTag();
            if (this.zzbAw != null) {
                this.zzbAw.onPlusOneClick(intent);
            } else {
                onPlusOneClick(intent);
            }
        }

        public void onPlusOneClick(Intent intent) {
            Context context = PlusOneButton.this.getContext();
            if ((context instanceof Activity) && intent != null) {
                ((Activity) context).startActivityForResult(intent, PlusOneButton.this.zzbAu);
            }
        }
    }

    public interface OnPlusOneClickListener {
        void onPlusOneClick(Intent intent);
    }

    public PlusOneButton(Context context) {
        this(context, (AttributeSet) null);
    }

    public PlusOneButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSize = getSize(context, attributeSet);
        this.zzbAt = getAnnotation(context, attributeSet);
        this.zzbAu = -1;
        zzbk(getContext());
        if (isInEditMode()) {
        }
    }

    protected static int getAnnotation(Context context, AttributeSet attributeSet) {
        String zza = zzbz.zza("http://schemas.android.com/apk/lib/com.google.android.gms.plus", DownloadDBHelper.ANNOTATION, context, attributeSet, true, false, "PlusOneButton");
        if ("INLINE".equalsIgnoreCase(zza)) {
            return 2;
        }
        return !IMConfig.SERVER_CERTIFICATE_FILE_DEFAULT.equalsIgnoreCase(zza) ? 1 : 0;
    }

    protected static int getSize(Context context, AttributeSet attributeSet) {
        String zza = zzbz.zza("http://schemas.android.com/apk/lib/com.google.android.gms.plus", "size", context, attributeSet, true, false, "PlusOneButton");
        if ("SMALL".equalsIgnoreCase(zza)) {
            return 0;
        }
        if ("MEDIUM".equalsIgnoreCase(zza)) {
            return 1;
        }
        return "TALL".equalsIgnoreCase(zza) ? 2 : 3;
    }

    private final void zzbk(Context context) {
        if (this.mContentView != null) {
            removeView(this.mContentView);
        }
        this.mContentView = zzm.zza(context, this.mSize, this.zzbAt, this.zzD, this.zzbAu);
        setOnPlusOneClickListener(this.zzbAv);
        addView(this.mContentView);
    }

    public final void initialize(String str, int i) {
        zzbo.zza(getContext() instanceof Activity, (Object) "To use this method, the PlusOneButton must be placed in an Activity. Use initialize(String, OnPlusOneClickListener).");
        this.zzD = str;
        this.zzbAu = i;
        zzbk(getContext());
    }

    public final void initialize(String str, OnPlusOneClickListener onPlusOneClickListener) {
        this.zzD = str;
        this.zzbAu = 0;
        zzbk(getContext());
        setOnPlusOneClickListener(onPlusOneClickListener);
    }

    /* access modifiers changed from: protected */
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mContentView.layout(0, 0, i3 - i, i4 - i2);
    }

    /* access modifiers changed from: protected */
    public final void onMeasure(int i, int i2) {
        View view = this.mContentView;
        measureChild(view, i, i2);
        setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public final void plusOneClick() {
        this.mContentView.performClick();
    }

    public final void setAnnotation(int i) {
        this.zzbAt = i;
        zzbk(getContext());
    }

    public final void setIntent(Intent intent) {
        this.mContentView.setTag(intent);
    }

    public final void setOnPlusOneClickListener(OnPlusOneClickListener onPlusOneClickListener) {
        this.zzbAv = onPlusOneClickListener;
        this.mContentView.setOnClickListener(new DefaultOnPlusOneClickListener(onPlusOneClickListener));
    }

    public final void setSize(int i) {
        this.mSize = i;
        zzbk(getContext());
    }
}
