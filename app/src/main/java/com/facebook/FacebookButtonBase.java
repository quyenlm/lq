package com.facebook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.common.R;
import com.facebook.internal.FragmentWrapper;

public abstract class FacebookButtonBase extends Button {
    private String analyticsButtonCreatedEventName;
    private String analyticsButtonTappedEventName;
    /* access modifiers changed from: private */
    public View.OnClickListener externalOnClickListener;
    /* access modifiers changed from: private */
    public View.OnClickListener internalOnClickListener;
    private boolean overrideCompoundPadding;
    private int overrideCompoundPaddingLeft;
    private int overrideCompoundPaddingRight;
    private FragmentWrapper parentFragment;

    /* access modifiers changed from: protected */
    public abstract int getDefaultRequestCode();

    protected FacebookButtonBase(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, String analyticsButtonCreatedEventName2, String analyticsButtonTappedEventName2) {
        super(context, attrs, 0);
        defStyleRes = defStyleRes == 0 ? getDefaultStyleResource() : defStyleRes;
        configureButton(context, attrs, defStyleAttr, defStyleRes == 0 ? R.style.com_facebook_button : defStyleRes);
        this.analyticsButtonCreatedEventName = analyticsButtonCreatedEventName2;
        this.analyticsButtonTappedEventName = analyticsButtonTappedEventName2;
        setClickable(true);
        setFocusable(true);
    }

    public void setFragment(Fragment fragment) {
        this.parentFragment = new FragmentWrapper(fragment);
    }

    public void setFragment(android.app.Fragment fragment) {
        this.parentFragment = new FragmentWrapper(fragment);
    }

    public Fragment getFragment() {
        if (this.parentFragment != null) {
            return this.parentFragment.getSupportFragment();
        }
        return null;
    }

    public android.app.Fragment getNativeFragment() {
        if (this.parentFragment != null) {
            return this.parentFragment.getNativeFragment();
        }
        return null;
    }

    public void setOnClickListener(View.OnClickListener l) {
        this.externalOnClickListener = l;
    }

    public int getRequestCode() {
        return getDefaultRequestCode();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            logButtonCreated(getContext());
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        boolean centered;
        if ((getGravity() & 1) != 0) {
            centered = true;
        } else {
            centered = false;
        }
        if (centered) {
            int compoundPaddingLeft = getCompoundPaddingLeft();
            int compoundPaddingRight = getCompoundPaddingRight();
            int inset = Math.min((((getWidth() - (compoundPaddingLeft + getCompoundDrawablePadding())) - compoundPaddingRight) - measureTextWidth(getText().toString())) / 2, (compoundPaddingLeft - getPaddingLeft()) / 2);
            this.overrideCompoundPaddingLeft = compoundPaddingLeft - inset;
            this.overrideCompoundPaddingRight = compoundPaddingRight + inset;
            this.overrideCompoundPadding = true;
        }
        super.onDraw(canvas);
        this.overrideCompoundPadding = false;
    }

    public int getCompoundPaddingLeft() {
        if (this.overrideCompoundPadding) {
            return this.overrideCompoundPaddingLeft;
        }
        return super.getCompoundPaddingLeft();
    }

    public int getCompoundPaddingRight() {
        if (this.overrideCompoundPadding) {
            return this.overrideCompoundPaddingRight;
        }
        return super.getCompoundPaddingRight();
    }

    /* access modifiers changed from: protected */
    public Activity getActivity() {
        Context context = getContext();
        while (!(context instanceof Activity) && (context instanceof ContextWrapper)) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        throw new FacebookException("Unable to get Activity.");
    }

    /* access modifiers changed from: protected */
    public int getDefaultStyleResource() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int measureTextWidth(String text) {
        return (int) Math.ceil((double) getPaint().measureText(text));
    }

    /* access modifiers changed from: protected */
    public void configureButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        parseBackgroundAttributes(context, attrs, defStyleAttr, defStyleRes);
        parseCompoundDrawableAttributes(context, attrs, defStyleAttr, defStyleRes);
        parseContentAttributes(context, attrs, defStyleAttr, defStyleRes);
        parseTextAttributes(context, attrs, defStyleAttr, defStyleRes);
        setupOnClickListener();
    }

    /* access modifiers changed from: protected */
    public void callExternalOnClickListener(View v) {
        if (this.externalOnClickListener != null) {
            this.externalOnClickListener.onClick(v);
        }
    }

    /* access modifiers changed from: protected */
    public void setInternalOnClickListener(View.OnClickListener l) {
        this.internalOnClickListener = l;
    }

    private void logButtonCreated(Context context) {
        AppEventsLogger.newLogger(context).logSdkEvent(this.analyticsButtonCreatedEventName, (Double) null, (Bundle) null);
    }

    /* access modifiers changed from: private */
    public void logButtonTapped(Context context) {
        AppEventsLogger.newLogger(context).logSdkEvent(this.analyticsButtonTappedEventName, (Double) null, (Bundle) null);
    }

    private void parseBackgroundAttributes(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (!isInEditMode()) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, new int[]{16842964}, defStyleAttr, defStyleRes);
            try {
                if (a.hasValue(0)) {
                    int backgroundResource = a.getResourceId(0, 0);
                    if (backgroundResource != 0) {
                        setBackgroundResource(backgroundResource);
                    } else {
                        setBackgroundColor(a.getColor(0, 0));
                    }
                } else {
                    setBackgroundColor(ContextCompat.getColor(context, R.color.com_facebook_blue));
                }
            } finally {
                a.recycle();
            }
        }
    }

    @SuppressLint({"ResourceType"})
    private void parseCompoundDrawableAttributes(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, new int[]{16843119, 16843117, 16843120, 16843118, 16843121}, defStyleAttr, defStyleRes);
        try {
            setCompoundDrawablesWithIntrinsicBounds(a.getResourceId(0, 0), a.getResourceId(1, 0), a.getResourceId(2, 0), a.getResourceId(3, 0));
            setCompoundDrawablePadding(a.getDimensionPixelSize(4, 0));
        } finally {
            a.recycle();
        }
    }

    private void parseContentAttributes(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, new int[]{16842966, 16842967, 16842968, 16842969}, defStyleAttr, defStyleRes);
        try {
            setPadding(a.getDimensionPixelSize(0, 0), a.getDimensionPixelSize(1, 0), a.getDimensionPixelSize(2, 0), a.getDimensionPixelSize(3, 0));
        } finally {
            a.recycle();
        }
    }

    /* JADX INFO: finally extract failed */
    private void parseTextAttributes(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray colorAttrs = context.getTheme().obtainStyledAttributes(attrs, new int[]{16842904}, defStyleAttr, defStyleRes);
        try {
            setTextColor(colorAttrs.getColorStateList(0));
            colorAttrs.recycle();
            TypedArray gravityAttrs = context.getTheme().obtainStyledAttributes(attrs, new int[]{16842927}, defStyleAttr, defStyleRes);
            try {
                setGravity(gravityAttrs.getInt(0, 17));
                gravityAttrs.recycle();
                TypedArray a = context.getTheme().obtainStyledAttributes(attrs, new int[]{16842901, 16842903, 16843087}, defStyleAttr, defStyleRes);
                try {
                    setTextSize(0, (float) a.getDimensionPixelSize(0, 0));
                    setTypeface(Typeface.defaultFromStyle(a.getInt(1, 1)));
                    setText(a.getString(2));
                } finally {
                    a.recycle();
                }
            } catch (Throwable th) {
                gravityAttrs.recycle();
                throw th;
            }
        } catch (Throwable th2) {
            colorAttrs.recycle();
            throw th2;
        }
    }

    private void setupOnClickListener() {
        super.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FacebookButtonBase.this.logButtonTapped(FacebookButtonBase.this.getContext());
                if (FacebookButtonBase.this.internalOnClickListener != null) {
                    FacebookButtonBase.this.internalOnClickListener.onClick(v);
                } else if (FacebookButtonBase.this.externalOnClickListener != null) {
                    FacebookButtonBase.this.externalOnClickListener.onClick(v);
                }
            }
        });
    }
}
