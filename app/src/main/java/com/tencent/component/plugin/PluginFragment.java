package com.tencent.component.plugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.tencent.component.annotation.PluginApi;
import com.tencent.component.app.BaseFragment;

@PluginApi(since = 4)
public class PluginFragment extends BaseFragment {
    private Plugin mPlugin;

    @PluginApi(since = 500)
    public void beforeAddActivity(Activity activity) {
    }

    public void onAttach(Activity activity) {
        if (activity != null && (activity instanceof PluginShellActivity)) {
            ((PluginShellActivity) activity).beforeAttachFragment(this);
        }
        super.onAttach(activity);
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 4)
    public boolean onBackPressed() {
        return false;
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 4)
    public void onNewIntent(Intent intent) {
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 4)
    public void onUserInteraction() {
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 4)
    public void onUserLeaveHint() {
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 4)
    public void onWindowFocusChanged(boolean hasFocus) {
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 4)
    public boolean dispatchTouchEvent(MotionEvent event) {
        return false;
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 4)
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 4)
    public boolean dispatchKeyEvent(KeyEvent event) {
        return false;
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 4)
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 4)
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 4)
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return false;
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 4)
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return false;
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 4)
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        return false;
    }

    @PluginApi(since = 4)
    public Plugin getPlugin() {
        return this.mPlugin;
    }

    public void startActivity(Intent intent) {
        Class<?> clazz = PluginHelper.findIntentClass(intent, getClass().getClassLoader());
        if (clazz == null || !PluginFragment.class.isAssignableFrom(clazz)) {
            super.startActivity(intent);
        } else {
            startActivity(clazz, intent);
        }
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        Class<?> clazz = PluginHelper.findIntentClass(intent, getClass().getClassLoader());
        if (clazz == null || !clazz.isAssignableFrom(PluginFragment.class)) {
            super.startActivityForResult(intent, requestCode);
        } else {
            startActivityForResult(clazz, intent, requestCode);
        }
    }

    @PluginApi(since = 4)
    public void startActivity(Class<? extends PluginFragment> fragmentClass, Intent args) {
        Intent intent = generateIntentForFragment(fragmentClass, args);
        if (intent != null) {
            startActivity(intent);
        }
    }

    @PluginApi(since = 4)
    public void startActivityForResult(Class<? extends PluginFragment> fragmentClass, Intent args, int requestCode) {
        Intent intent = generateIntentForFragment(fragmentClass, args);
        if (intent != null) {
            startActivityForResult(intent, requestCode);
        }
    }

    private Intent generateIntentForFragment(Class<? extends PluginFragment> fragmentClass, Intent args) {
        if (fragmentClass == null) {
            return null;
        }
        Activity activity = getActivity();
        if (activity == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        } else if (!(activity instanceof PluginShellActivity)) {
            throw new IllegalStateException("Fragment " + this + " not attached to correct Activity to perform this");
        } else {
            PluginInfo pluginInfo = ((PluginShellActivity) activity).getPluginInfo();
            if (pluginInfo != null) {
                return this.mPlugin.getPluginHelper().generateInnerIntent(activity, pluginInfo, fragmentClass.getName(), args);
            }
            throw new IllegalStateException("Fragment's Activity " + activity + " not prepared");
        }
    }

    @PluginApi(since = 4)
    public static PluginFragment instantiate(Plugin plugin, Bundle args) {
        return instantiate(plugin, (String) null, args);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: com.tencent.component.plugin.PluginFragment} */
    /* JADX WARNING: Multi-variable type inference failed */
    @com.tencent.component.annotation.PluginApi(since = 4)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.tencent.component.plugin.PluginFragment instantiate(com.tencent.component.plugin.Plugin r8, java.lang.String r9, android.os.Bundle r10) {
        /*
            r3 = 0
            if (r8 != 0) goto L_0x0004
        L_0x0003:
            return r3
        L_0x0004:
            com.tencent.component.plugin.PluginInfo r4 = r8.getPluginInfo()
            if (r4 == 0) goto L_0x0003
            boolean r5 = android.text.TextUtils.isEmpty(r9)
            if (r5 == 0) goto L_0x0012
            java.lang.String r9 = r4.launchFragment
        L_0x0012:
            boolean r5 = android.text.TextUtils.isEmpty(r9)
            if (r5 != 0) goto L_0x0003
            r3 = 0
            java.lang.Class r5 = r8.getClass()     // Catch:{ ClassNotFoundException -> 0x0040, InstantiationException -> 0x0060, IllegalAccessException -> 0x0080 }
            java.lang.ClassLoader r5 = r5.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x0040, InstantiationException -> 0x0060, IllegalAccessException -> 0x0080 }
            java.lang.Class r1 = r5.loadClass(r9)     // Catch:{ ClassNotFoundException -> 0x0040, InstantiationException -> 0x0060, IllegalAccessException -> 0x0080 }
            java.lang.Object r5 = r1.newInstance()     // Catch:{ ClassNotFoundException -> 0x0040, InstantiationException -> 0x0060, IllegalAccessException -> 0x0080 }
            r0 = r5
            com.tencent.component.plugin.PluginFragment r0 = (com.tencent.component.plugin.PluginFragment) r0     // Catch:{ ClassNotFoundException -> 0x0040, InstantiationException -> 0x0060, IllegalAccessException -> 0x0080 }
            r3 = r0
            r3.mPlugin = r8     // Catch:{ ClassNotFoundException -> 0x0040, InstantiationException -> 0x0060, IllegalAccessException -> 0x0080 }
            if (r10 == 0) goto L_0x0003
            java.lang.Class r5 = r3.getClass()     // Catch:{ ClassNotFoundException -> 0x0040, InstantiationException -> 0x0060, IllegalAccessException -> 0x0080 }
            java.lang.ClassLoader r5 = r5.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x0040, InstantiationException -> 0x0060, IllegalAccessException -> 0x0080 }
            r10.setClassLoader(r5)     // Catch:{ ClassNotFoundException -> 0x0040, InstantiationException -> 0x0060, IllegalAccessException -> 0x0080 }
            r3.setArguments(r10)     // Catch:{ ClassNotFoundException -> 0x0040, InstantiationException -> 0x0060, IllegalAccessException -> 0x0080 }
            goto L_0x0003
        L_0x0040:
            r2 = move-exception
            com.tencent.component.plugin.PluginFragment$InstantiationException r5 = new com.tencent.component.plugin.PluginFragment$InstantiationException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Unable to instantiate fragment "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r9)
            java.lang.String r7 = ": make sure class name exists, is public, and has an empty constructor that is public"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6, r2)
            throw r5
        L_0x0060:
            r2 = move-exception
            com.tencent.component.plugin.PluginFragment$InstantiationException r5 = new com.tencent.component.plugin.PluginFragment$InstantiationException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Unable to instantiate fragment "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r9)
            java.lang.String r7 = ": make sure class name exists, is public, and has an empty constructor that is public"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6, r2)
            throw r5
        L_0x0080:
            r2 = move-exception
            com.tencent.component.plugin.PluginFragment$InstantiationException r5 = new com.tencent.component.plugin.PluginFragment$InstantiationException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Unable to instantiate fragment "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r9)
            java.lang.String r7 = ": make sure class name exists, is public, and has an empty constructor that is public"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6, r2)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.plugin.PluginFragment.instantiate(com.tencent.component.plugin.Plugin, java.lang.String, android.os.Bundle):com.tencent.component.plugin.PluginFragment");
    }

    public static class InstantiationException extends RuntimeException {
        public InstantiationException(String msg, Exception cause) {
            super(msg, cause);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
