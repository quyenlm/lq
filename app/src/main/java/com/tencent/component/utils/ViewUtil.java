package com.tencent.component.utils;

import android.content.Context;
import android.graphics.Paint;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.security.InvalidParameterException;

public class ViewUtil {
    private static final boolean DEBUG = true;
    private static final Object TAG_DECORATE = new Object();

    public static void setTag(View view, Object tag) {
        setTagInternal(view, 0, tag, true);
    }

    public static Object getTag(View view) {
        return getTagInternal(view, 0);
    }

    public static void setTag(View view, int key, Object tag) {
        setTagInternal(view, key, tag, false);
    }

    public static Object getTag(View view, int key) {
        return getTagInternal(view, key);
    }

    private static void setTagInternal(View view, int key, Object tag, boolean ignoreKey) {
        if (view != null) {
            if (ignoreKey || (key >>> 24) >= 2) {
                Object viewTag = view.getTag();
                if (viewTag == null || !(viewTag instanceof SparseArray)) {
                    viewTag = new SparseArray();
                }
                SparseArray<Object> tagArray = (SparseArray) viewTag;
                tagArray.put(key, tag);
                view.setTag(tagArray);
                return;
            }
            throw new IllegalArgumentException("The key must be an application-specific resource id.");
        }
    }

    private static Object getTagInternal(View view, int key) {
        Object viewTag;
        if (view == null || (viewTag = view.getTag()) == null || !(viewTag instanceof SparseArray)) {
            return null;
        }
        return ((SparseArray) viewTag).get(key);
    }

    public static void decorate(View hostView, View decorView, int gravity) {
        decorate(hostView, decorView, gravity, 0, 0, 0, 0);
    }

    public static void decorate(View hostView, View decorView, int gravity, int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        FrameLayout decorContainer;
        if (hostView != null && decorView != null) {
            ViewParent parent = hostView.getParent();
            if (parent == null) {
                throw new IllegalStateException("host " + hostView + " not attached to parent");
            } else if (!(parent instanceof ViewGroup)) {
                throw new InvalidParameterException("host " + hostView + " has invalid parent " + parent);
            } else if (decorView.getParent() != null) {
                throw new IllegalStateException("decorate " + decorView + " has already be added to a ViewParent " + decorView.getParent());
            } else {
                FrameLayout.LayoutParams hostLp = generateHostLayoutParams(hostView);
                FrameLayout.LayoutParams decorLp = generateDecorLayoutParams(gravity, leftMargin, topMargin, rightMargin, bottomMargin);
                ViewGroup hostGroup = (ViewGroup) parent;
                if (!(hostGroup instanceof FrameLayout) || getTag(hostView) != TAG_DECORATE) {
                    decorContainer = new DecorateContainer(hostGroup.getContext(), hostView);
                    int index = computeChildIndex(hostGroup, hostView);
                    ViewGroup.LayoutParams lp = hostView.getLayoutParams();
                    lp.width = -2;
                    lp.height = -2;
                    hostGroup.removeView(hostView);
                    hostGroup.addView(decorContainer, index, lp);
                    setTag(decorContainer, TAG_DECORATE);
                } else {
                    decorContainer = (FrameLayout) hostGroup;
                }
                decorContainer.removeAllViews();
                if (hostLp != null) {
                    decorContainer.addView(hostView, hostLp);
                }
                if (decorLp != null) {
                    decorContainer.addView(decorView, decorLp);
                }
            }
        }
    }

    private static FrameLayout.LayoutParams generateHostLayoutParams(View hostView) {
        if (hostView == null) {
            return null;
        }
        FrameLayout.LayoutParams lp = newLayoutParams(hostView.getLayoutParams());
        lp.bottomMargin = 0;
        lp.topMargin = 0;
        lp.rightMargin = 0;
        lp.leftMargin = 0;
        return lp;
    }

    private static FrameLayout.LayoutParams generateDecorLayoutParams(int gravity, int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2, -2);
        lp.gravity = gravity;
        lp.leftMargin = leftMargin;
        lp.topMargin = topMargin;
        lp.rightMargin = rightMargin;
        lp.bottomMargin = bottomMargin;
        return lp;
    }

    private static FrameLayout.LayoutParams newLayoutParams(ViewGroup.LayoutParams source) {
        if (source == null) {
            return null;
        }
        if (source instanceof ViewGroup.MarginLayoutParams) {
            return new FrameLayout.LayoutParams((ViewGroup.MarginLayoutParams) source);
        }
        return new FrameLayout.LayoutParams(source);
    }

    private static int computeChildIndex(ViewGroup parent, View child) {
        if (parent == null || child == null) {
            return -1;
        }
        int index = 0;
        int count = parent.getChildCount();
        while (index < count && parent.getChildAt(index) != child) {
            index++;
        }
        if (index < 0 || index >= count) {
            return -1;
        }
        return index;
    }

    private static class DecorateContainer extends FrameLayout {
        private final View mHostView;

        public DecorateContainer(Context context, View hostView) {
            super(context);
            this.mHostView = hostView;
        }

        public int getVisibility() {
            return this.mHostView != null ? this.mHostView.getVisibility() : super.getVisibility();
        }
    }

    public void setLineSpacing(Context context, TextView textView, float lineSpacing) {
        float fMulValue;
        float fAddValue;
        float textSizeDp = textView.getTextSize() / context.getResources().getDisplayMetrics().density;
        float lineHeight = textSizeDp + lineSpacing;
        int textHeight = getTextHeight(textSizeDp);
        if (((float) textHeight) > lineHeight) {
            fMulValue = lineHeight / ((float) textHeight);
            fAddValue = -1.0f;
        } else {
            fMulValue = 1.0f;
            fAddValue = lineHeight - ((float) textHeight);
        }
        textView.setLineSpacing(fAddValue, fMulValue);
    }

    private int getTextHeight(float textSize) {
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil((double) (fm.descent - fm.ascent));
    }

    private ViewUtil() {
    }
}
