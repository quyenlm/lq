package com.facebook.internal;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

public class FragmentWrapper {
    private Fragment nativeFragment;
    private android.support.v4.app.Fragment supportFragment;

    public FragmentWrapper(android.support.v4.app.Fragment fragment) {
        Validate.notNull(fragment, "fragment");
        this.supportFragment = fragment;
    }

    public FragmentWrapper(Fragment fragment) {
        Validate.notNull(fragment, "fragment");
        this.nativeFragment = fragment;
    }

    public Fragment getNativeFragment() {
        return this.nativeFragment;
    }

    public android.support.v4.app.Fragment getSupportFragment() {
        return this.supportFragment;
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        if (this.supportFragment != null) {
            this.supportFragment.startActivityForResult(intent, requestCode);
        } else {
            this.nativeFragment.startActivityForResult(intent, requestCode);
        }
    }

    public final Activity getActivity() {
        if (this.supportFragment != null) {
            return this.supportFragment.getActivity();
        }
        return this.nativeFragment.getActivity();
    }
}
