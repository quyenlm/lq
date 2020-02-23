package com.tencent.wetest;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class g {
    private static Class b;
    private String a;

    public g() {
        b();
        c();
    }

    private void a(ArrayList arrayList, ViewGroup viewGroup, boolean z) {
        if (viewGroup != null) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < viewGroup.getChildCount()) {
                    View childAt = viewGroup.getChildAt(i2);
                    arrayList.add(childAt);
                    if (childAt instanceof ViewGroup) {
                        a(arrayList, (ViewGroup) childAt, z);
                    }
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        }
    }

    private final View b(View[] viewArr) {
        View view = null;
        long j = 0;
        int i = 0;
        while (i < viewArr.length) {
            View view2 = viewArr[i];
            if (view2 == null || !view2.isShown() || !view2.hasWindowFocus() || view2.getDrawingTime() <= j) {
                view2 = view;
            } else {
                j = view2.getDrawingTime();
            }
            i++;
            view = view2;
        }
        return view;
    }

    private void b() {
        if (Build.VERSION.SDK_INT >= 17) {
            this.a = "sDefaultWindowManager";
        } else if (Build.VERSION.SDK_INT >= 13) {
            this.a = "sWindowManager";
        } else {
            this.a = "mWindowManager";
        }
    }

    private void c() {
        try {
            b = Class.forName(Build.VERSION.SDK_INT >= 17 ? "android.view.WindowManagerGlobal" : "android.view.WindowManagerImpl");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SecurityException e2) {
            throw new RuntimeException(e2);
        }
    }

    private final View[] c(View[] viewArr) {
        int i = 0;
        if (viewArr == null) {
            return null;
        }
        View[] viewArr2 = new View[viewArr.length];
        int i2 = 0;
        while (true) {
            int i3 = i;
            if (i2 >= viewArr.length) {
                return viewArr2;
            }
            View view = viewArr[i2];
            if (view != null && !view.getClass().getName().equals("com.android.internal.policy.impl.PhoneWindow$DecorView")) {
                viewArr2[i3] = view;
                i3++;
            }
            i = i3;
            i2++;
        }
    }

    public final View a(View[] viewArr) {
        int i = 0;
        if (viewArr == null) {
            return null;
        }
        View[] viewArr2 = new View[viewArr.length];
        int i2 = 0;
        while (true) {
            int i3 = i;
            if (i2 >= viewArr.length) {
                return b(viewArr2);
            }
            View view = viewArr[i2];
            if (view != null) {
                String name = view.getClass().getName();
                if (name.equals("com.android.internal.policy.impl.PhoneWindow$DecorView") || name.equals("com.android.internal.policy.impl.MultiPhoneWindow$MultiPhoneDecorView")) {
                    viewArr2[i3] = view;
                    i3++;
                }
            }
            i = i3;
            i2++;
        }
    }

    public ArrayList a(boolean z) {
        View[] a2 = a();
        ArrayList arrayList = new ArrayList();
        View[] c = c(a2);
        if (c != null) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= c.length) {
                    break;
                }
                View view = c[i2];
                try {
                    a(arrayList, (ViewGroup) view, z);
                } catch (Exception e) {
                }
                if (view != null) {
                    arrayList.add(view);
                }
                i = i2 + 1;
            }
        }
        if (a2 != null && a2.length > 0) {
            View a3 = a(a2);
            try {
                a(arrayList, (ViewGroup) a3, z);
            } catch (Exception e2) {
            }
            if (a3 != null) {
                arrayList.add(a3);
            }
        }
        return arrayList;
    }

    public View[] a() {
        try {
            Field declaredField = b.getDeclaredField("mViews");
            Field declaredField2 = b.getDeclaredField(this.a);
            declaredField.setAccessible(true);
            declaredField2.setAccessible(true);
            Object obj = declaredField2.get((Object) null);
            return Build.VERSION.SDK_INT >= 19 ? (View[]) ((ArrayList) declaredField.get(obj)).toArray(new View[0]) : (View[]) declaredField.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
