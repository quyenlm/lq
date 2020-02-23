package com.unity3d.player;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

public final class g extends Fragment {
    private final Runnable a;

    public g() {
        this.a = null;
    }

    public g(Runnable runnable) {
        this.a = runnable;
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.a == null) {
            getFragmentManager().beginTransaction().remove(this).commit();
        } else {
            requestPermissions(getArguments().getStringArray("PermissionNames"), 15881);
        }
    }

    public final void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 15881) {
            int i2 = 0;
            while (i2 < strArr.length && i2 < iArr.length) {
                e.Log(4, strArr[i2] + (iArr[i2] == 0 ? " granted" : " denied"));
                i2++;
            }
            FragmentTransaction beginTransaction = getActivity().getFragmentManager().beginTransaction();
            beginTransaction.remove(this);
            beginTransaction.commit();
            this.a.run();
        }
    }
}
