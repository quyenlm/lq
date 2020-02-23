package com.tencent.midas.oversea.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.midas.oversea.api.ICallback;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.utils.APFindViewUtils;

public class APUICommMethod {
    public static void successToast(Activity activity, String str, ICallback iCallback) {
        if (activity == null) {
            APLog.w("APUICommonMethod", "successToast context:" + activity + " num:" + str);
            return;
        }
        View inflate = LayoutInflater.from(activity).inflate(APCommMethod.getLayoutId(activity, "unipay_abroad_tips_suc"), (ViewGroup) null);
        TextView textView = (TextView) APFindViewUtils.findViewById(inflate, "unipay_id_succnum");
        TextView textView2 = (TextView) APFindViewUtils.findViewById(inflate, "unipay_id_name");
        if ("0".equals(str) || TextUtils.isEmpty(str)) {
            textView.setVisibility(4);
            textView2.setVisibility(4);
        } else {
            textView.setText("x" + str);
        }
        Toast makeText = Toast.makeText(activity.getApplicationContext(), "", 1);
        makeText.setGravity(23, 0, 0);
        makeText.setDuration(1);
        makeText.setView(inflate);
        makeText.show();
        if (iCallback != null) {
            iCallback.callback(0);
        }
    }
}
