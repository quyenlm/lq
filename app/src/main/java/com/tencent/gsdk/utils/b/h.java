package com.tencent.gsdk.utils.b;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import com.tencent.gsdk.utils.Logger;
import java.util.Map;

/* compiled from: TDMReporterImpl */
final class h extends a {
    h() {
    }

    /* access modifiers changed from: package-private */
    @TargetApi(11)
    public boolean b(Context context, String str) {
        if (11 > Build.VERSION.SDK_INT || !(context instanceof Activity) || !i.a(context.getApplicationContext())) {
            Logger.d("TDM init failed");
            return false;
        }
        ((Activity) context).getFragmentManager().beginTransaction().add(new a(), "LifeCycleObserver").commitAllowingStateLoss();
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean b(int i, String str, Map<String, String> map) {
        return i.a(str, map);
    }

    @SuppressLint({"ValidFragment"})
    @TargetApi(11)
    /* compiled from: TDMReporterImpl */
    private static class a extends Fragment {
        private a() {
        }

        public void onResume() {
            super.onResume();
            i.a();
        }

        public void onStart() {
            super.onStart();
            i.b();
        }

        public void onPause() {
            super.onPause();
            i.c();
        }

        public void onStop() {
            super.onStop();
            i.d();
        }

        public void onDestroy() {
            super.onDestroy();
            i.e();
        }
    }
}
