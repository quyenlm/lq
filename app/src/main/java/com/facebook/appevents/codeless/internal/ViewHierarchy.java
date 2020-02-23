package com.facebook.appevents.codeless.internal;

import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewHierarchy {
    private static final int ADAPTER_VIEW_ITEM_BITMASK = 9;
    private static final int BUTTON_BITMASK = 2;
    private static final int CHECKBOX_BITMASK = 15;
    private static final String CHILDREN_VIEW_KEY = "childviews";
    private static final String CLASS_NAME_KEY = "classname";
    private static final String CLASS_TYPE_BITMASK_KEY = "classtypebitmask";
    private static final int CLICKABLE_VIEW_BITMASK = 5;
    private static final String DESC_KEY = "description";
    private static final String DIMENSION_HEIGHT_KEY = "height";
    private static final String DIMENSION_KEY = "dimension";
    private static final String DIMENSION_LEFT_KEY = "left";
    private static final String DIMENSION_SCROLL_X_KEY = "scrollx";
    private static final String DIMENSION_SCROLL_Y_KEY = "scrolly";
    private static final String DIMENSION_TOP_KEY = "top";
    private static final String DIMENSION_VISIBILITY_KEY = "visibility";
    private static final String DIMENSION_WIDTH_KEY = "width";
    private static final String GET_ACCESSIBILITY_METHOD = "getAccessibilityDelegate";
    private static final String HINT_KEY = "hint";
    private static final String ID_KEY = "id";
    private static final int IMAGEVIEW_BITMASK = 1;
    private static final int INPUT_BITMASK = 11;
    private static final int LABEL_BITMASK = 10;
    private static final int PICKER_BITMASK = 12;
    private static final int RADIO_GROUP_BITMASK = 14;
    private static final int RATINGBAR_BITMASK = 16;
    private static final int SWITCH_BITMASK = 13;
    private static final String TAG = ViewHierarchy.class.getCanonicalName();
    private static final String TAG_KEY = "tag";
    private static final int TEXTVIEW_BITMASK = 0;
    private static final String TEXT_KEY = "text";

    @Nullable
    public static ViewGroup getParentOfView(View view) {
        if (view == null) {
            return null;
        }
        ViewParent parent = view.getParent();
        if (parent == null || !(parent instanceof ViewGroup)) {
            return null;
        }
        return (ViewGroup) parent;
    }

    public static List<View> getChildrenOfView(View view) {
        ArrayList<View> children = new ArrayList<>();
        if (view != null && (view instanceof ViewGroup)) {
            ViewGroup viewGroup = (ViewGroup) view;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                children.add(viewGroup.getChildAt(i));
            }
        }
        return children;
    }

    public static JSONObject getDictionaryOfView(View view) {
        JSONObject json = new JSONObject();
        try {
            String text = getTextOfView(view);
            String hint = getHintOfView(view);
            Object tag = view.getTag();
            CharSequence description = view.getContentDescription();
            json.put(CLASS_NAME_KEY, view.getClass().getCanonicalName());
            json.put(CLASS_TYPE_BITMASK_KEY, getClassTypeBitmask(view));
            json.put("id", view.getId());
            if (!SensitiveUserDataUtils.isSensitiveUserData(view)) {
                json.put("text", text);
            } else {
                json.put("text", "");
            }
            json.put(HINT_KEY, hint);
            if (tag != null) {
                json.put("tag", tag.toString());
            }
            if (description != null) {
                json.put("description", description.toString());
            }
            json.put(DIMENSION_KEY, getDimensionOfView(view));
            JSONArray childviews = new JSONArray();
            List<View> children = getChildrenOfView(view);
            for (int i = 0; i < children.size(); i++) {
                childviews.put(getDictionaryOfView(children.get(i)));
            }
            json.put(CHILDREN_VIEW_KEY, childviews);
        } catch (JSONException e) {
            Log.e(TAG, "Failed to create JSONObject for view.", e);
        }
        return json;
    }

    private static int getClassTypeBitmask(View view) {
        int bitmask = 0;
        if (view instanceof ImageView) {
            bitmask = 0 | 2;
        }
        if (isClickableView(view)) {
            bitmask |= 32;
        }
        if (isAdapterViewItem(view)) {
            bitmask |= 512;
        }
        if (view instanceof TextView) {
            int bitmask2 = bitmask | 1024 | 1;
            if (view instanceof Button) {
                bitmask2 |= 4;
                if (view instanceof Switch) {
                    bitmask2 |= 8192;
                } else if (view instanceof CheckBox) {
                    bitmask2 |= 32768;
                }
            }
            if (view instanceof EditText) {
                return bitmask2 | 2048;
            }
            return bitmask2;
        } else if ((view instanceof Spinner) || (view instanceof DatePicker)) {
            return bitmask | 4096;
        } else {
            if (view instanceof RatingBar) {
                return bitmask | 65536;
            }
            if (view instanceof RadioGroup) {
                return bitmask | 16384;
            }
            return bitmask;
        }
    }

    private static boolean isClickableView(View view) {
        boolean z = true;
        try {
            Field listenerInfoField = Class.forName("android.view.View").getDeclaredField("mListenerInfo");
            if (listenerInfoField != null) {
                listenerInfoField.setAccessible(true);
            }
            Object listenerObj = listenerInfoField.get(view);
            if (listenerObj == null) {
                return false;
            }
            View.OnClickListener listener = null;
            Field listenerField = Class.forName("android.view.View$ListenerInfo").getDeclaredField("mOnClickListener");
            if (listenerField != null) {
                listener = (View.OnClickListener) listenerField.get(listenerObj);
            }
            if (listener == null) {
                z = false;
            }
            return z;
        } catch (Exception e) {
            Log.e(TAG, "Failed to check if the view is clickable.", e);
            return false;
        }
    }

    private static boolean isAdapterViewItem(View view) {
        ViewParent parent = view.getParent();
        if (parent == null || (!(parent instanceof AdapterView) && !(parent instanceof NestedScrollingChild))) {
            return false;
        }
        return true;
    }

    public static String getTextOfView(View view) {
        String textObj = null;
        if (view instanceof TextView) {
            textObj = ((TextView) view).getText();
            if (view instanceof Switch) {
                textObj = ((Switch) view).isChecked() ? "1" : "0";
            }
        } else if (view instanceof Spinner) {
            Object selectedItem = ((Spinner) view).getSelectedItem();
            if (selectedItem != null) {
                textObj = selectedItem.toString();
            }
        } else if (view instanceof DatePicker) {
            DatePicker picker = (DatePicker) view;
            textObj = String.format("%04d-%02d-%02d", new Object[]{Integer.valueOf(picker.getYear()), Integer.valueOf(picker.getMonth()), Integer.valueOf(picker.getDayOfMonth())});
        } else if (view instanceof TimePicker) {
            TimePicker picker2 = (TimePicker) view;
            textObj = String.format("%02d:%02d", new Object[]{Integer.valueOf(picker2.getCurrentHour().intValue()), Integer.valueOf(picker2.getCurrentMinute().intValue())});
        } else if (view instanceof RadioGroup) {
            RadioGroup radioGroup = (RadioGroup) view;
            int checkedId = radioGroup.getCheckedRadioButtonId();
            int childCount = radioGroup.getChildCount();
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    break;
                }
                View child = radioGroup.getChildAt(i);
                if (child.getId() == checkedId && (child instanceof RadioButton)) {
                    textObj = ((RadioButton) child).getText();
                    break;
                }
                i++;
            }
        } else if (view instanceof RatingBar) {
            textObj = String.valueOf(((RatingBar) view).getRating());
        }
        if (textObj == null) {
            return "";
        }
        return textObj.toString();
    }

    public static String getHintOfView(View view) {
        Object hintObj = null;
        if (view instanceof TextView) {
            hintObj = ((TextView) view).getHint();
        } else if (view instanceof EditText) {
            hintObj = ((EditText) view).getHint();
        }
        return hintObj == null ? "" : hintObj.toString();
    }

    private static JSONObject getDimensionOfView(View view) {
        JSONObject dimension = new JSONObject();
        try {
            dimension.put(DIMENSION_TOP_KEY, view.getTop());
            dimension.put(DIMENSION_LEFT_KEY, view.getLeft());
            dimension.put(DIMENSION_WIDTH_KEY, view.getWidth());
            dimension.put(DIMENSION_HEIGHT_KEY, view.getHeight());
            dimension.put(DIMENSION_SCROLL_X_KEY, view.getScrollX());
            dimension.put(DIMENSION_SCROLL_Y_KEY, view.getScrollY());
            dimension.put(DIMENSION_VISIBILITY_KEY, view.getVisibility());
        } catch (JSONException e) {
            Log.e(TAG, "Failed to create JSONObject for dimension.", e);
        }
        return dimension;
    }

    @Nullable
    public static View.AccessibilityDelegate getExistingDelegate(View view) {
        try {
            return (View.AccessibilityDelegate) view.getClass().getMethod(GET_ACCESSIBILITY_METHOD, new Class[0]).invoke(view, new Object[0]);
        } catch (NoSuchMethodException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        } catch (SecurityException e3) {
            return null;
        } catch (IllegalAccessException e4) {
            return null;
        } catch (InvocationTargetException e5) {
            return null;
        }
    }
}
