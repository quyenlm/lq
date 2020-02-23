package com.tencent.midas.oversea.comm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.midas.oversea.comm.APAlertDialog;
import com.tencent.midas.oversea.utils.APFindViewUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

public class APUICommonMethod {
    private static Stack<Activity> a = null;
    private static HashMap<ProgressDialog, Context> b = null;
    private static HashMap<Context, APAlertDialog> c = null;

    public interface OnError {
        void onError();
    }

    public static void delActivity(Activity activity) {
        if (a != null) {
            a.remove(activity);
        }
    }

    public static int dip2px(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
    }

    public static void dismissWaitDialog() {
        if (b != null) {
            try {
                for (Map.Entry<ProgressDialog, Context> key : b.entrySet()) {
                    ProgressDialog progressDialog = (ProgressDialog) key.getKey();
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        b.remove(progressDialog);
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public static void dissMessageDialog(Activity activity) {
        if (c != null) {
            try {
                APAlertDialog aPAlertDialog = c.get(activity);
                if (aPAlertDialog != null) {
                    aPAlertDialog.cancel();
                    c.remove(aPAlertDialog);
                }
            } catch (Exception e) {
            }
        }
    }

    public static BitmapDrawable getAppResDrawable(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return new BitmapDrawable(BitmapFactory.decodeByteArray(bArr, 0, bArr.length));
    }

    public static void popActivity() {
        try {
            if (a != null) {
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 >= a.size()) {
                        break;
                    }
                    if (a.get(i2) != null) {
                        ((Activity) a.get(i2)).finish();
                    }
                    i = i2 + 1;
                }
                releaseActivityStack();
            }
        } catch (Exception e) {
        }
    }

    public static void popToActivity(Activity activity) {
        if (activity == null) {
            try {
                popActivity();
            } catch (Exception e) {
            }
        } else {
            int size = a.size();
            for (int i = size - 1; i < size; i--) {
                Activity activity2 = (Activity) a.get(i);
                if (activity2 != null) {
                    if (!activity2.equals(activity)) {
                        activity2.finish();
                        a.remove(i);
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public static void pushActivity(Activity activity) {
        if (a == null) {
            a = new Stack<>();
        }
        a.push(activity);
    }

    public static int px2dip(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static void releaseActivityStack() {
        if (a != null) {
            a.clear();
        }
        a = null;
    }

    public static void releaseLoadingMap() {
        if (b != null) {
            b.clear();
        }
        b = null;
    }

    public static void releaseManageDlgMap() {
        if (c != null) {
            c.clear();
        }
        c = null;
    }

    public static void showErrorMsg(Activity activity, String str, OnError onError) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(APCommMethod.getStringId(activity, "unipay_hints"));
        builder.setMessage(str);
        builder.setCancelable(false);
        builder.setPositiveButton(APCommMethod.getStringId(activity, "unipay_sure"), new i(onError));
        AlertDialog create = builder.create();
        if (create != null) {
            create.setOnKeyListener(new j(onError));
            try {
                create.show();
            } catch (Exception e) {
            }
        }
    }

    public static void showMessageDialog(Activity activity, String str) {
        APAlertDialog aPAlertDialog;
        if (c == null) {
            c = new HashMap<>();
        }
        if (c.containsKey((APBaseActivity) activity) && (aPAlertDialog = c.get(activity)) != null) {
            try {
                aPAlertDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            c.remove(activity);
        }
        APAlertDialog.Builder builder = new APAlertDialog.Builder(activity);
        builder.setTitle(APCommMethod.getStringId(activity, "unipay_dlg_tip"));
        builder.setMessage(str);
        builder.setPositiveButton(APCommMethod.getStringId(activity, "unipay_sure"), (DialogInterface.OnClickListener) new k(activity));
        APAlertDialog create = builder.create();
        if (create != null) {
            c.put((APBaseActivity) activity, create);
            try {
                create.show();
            } catch (Exception e2) {
            }
        }
    }

    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, 0).show();
    }

    public static void showToast(Context context, String str, int i) {
        if (str != null && !str.equals("")) {
            View inflate = LayoutInflater.from(context).inflate(APCommMethod.getLayoutId(context, "unipay_layout_toast_custom"), (ViewGroup) null);
            ((TextView) inflate.findViewById(APCommMethod.getId(context, "unipay_id_apToastText"))).setText(str);
            ImageView imageView = (ImageView) inflate.findViewById(APCommMethod.getId(context, "unipay_id_apToastImg"));
            imageView.setBackgroundDrawable((Drawable) null);
            imageView.setImageResource(i);
            Toast makeText = Toast.makeText(context.getApplicationContext(), str, 1);
            makeText.setGravity(17, 0, 0);
            makeText.setDuration(500);
            makeText.setView(inflate);
            makeText.show();
        }
    }

    public static void showToast(Context context, String str, String str2, boolean z) {
        if (str != null && !str.equals("")) {
            View inflate = LayoutInflater.from(context).inflate(APCommMethod.getLayoutId(context, "unipay_layout_toast_custom"), (ViewGroup) null);
            ((TextView) inflate.findViewById(APCommMethod.getId(context, "unipay_id_apToastText"))).setText(str);
            View findViewById = inflate.findViewById(APCommMethod.getId(context, "unipay_id_apToastImg"));
            if (!TextUtils.isEmpty(str2)) {
                findViewById.setBackgroundDrawable(APCommMethod.getDrawable(context, str2));
            } else {
                findViewById.setVisibility(8);
            }
            Toast makeText = Toast.makeText(context.getApplicationContext(), str, 1);
            if (z) {
                makeText.setGravity(49, 0, 0);
            } else {
                makeText.setGravity(17, 0, 0);
            }
            makeText.setDuration(500);
            makeText.setView(inflate);
            makeText.show();
        }
    }

    public static void showToastWithTimesAndImage(Context context, String str, int i, int i2) {
        View inflate = LayoutInflater.from(context).inflate(APCommMethod.getLayoutId(context, "unipay_layout_toast_custom"), (ViewGroup) null);
        ((TextView) inflate.findViewById(APCommMethod.getId(context, "unipay_id_apToastText"))).setText(str);
        ((ImageView) inflate.findViewById(APCommMethod.getId(context, "unipay_id_apToastImg"))).setBackgroundResource(i2);
        Toast makeText = Toast.makeText(context.getApplicationContext(), str, 1);
        makeText.setGravity(17, 0, 0);
        makeText.setDuration(i);
        makeText.setView(inflate);
        makeText.show();
    }

    public static void showWaitDialog(Context context, String str, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        boolean z2;
        try {
            if (b == null) {
                b = new HashMap<>();
            }
            Iterator<Map.Entry<ProgressDialog, Context>> it = b.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    z2 = false;
                    break;
                }
                ProgressDialog progressDialog = (ProgressDialog) it.next().getKey();
                progressDialog.setCancelable(z);
                if (progressDialog.isShowing()) {
                    z2 = true;
                    break;
                }
            }
            if (!z2) {
                APProgressDialog aPProgressDialog = new APProgressDialog(context, z);
                aPProgressDialog.setMessage(APCommMethod.getStringId(context, "unipay_waiting"));
                b.put(aPProgressDialog, context);
                aPProgressDialog.setOnCancelListener(new l(onCancelListener));
                aPProgressDialog.show();
            }
        } catch (Exception e) {
        }
    }

    public static void successToast(Context context, String str) {
        if (context == null) {
            APLog.w("APUICommonMethod", "successToast context:" + context + " num:" + str);
            return;
        }
        View inflate = LayoutInflater.from(context).inflate(APCommMethod.getLayoutId(context, "unipay_abroad_tips_suc"), (ViewGroup) null);
        TextView textView = (TextView) APFindViewUtils.findViewById(inflate, "unipay_id_succnum");
        TextView textView2 = (TextView) APFindViewUtils.findViewById(inflate, "unipay_id_name");
        if ("0".equals(str) || TextUtils.isEmpty(str)) {
            textView.setVisibility(4);
            textView2.setVisibility(4);
        } else {
            textView.setText("x" + str);
        }
        Toast makeText = Toast.makeText(context.getApplicationContext(), "", 1);
        makeText.setGravity(23, 0, 0);
        makeText.setDuration(1);
        makeText.setView(inflate);
        makeText.show();
    }
}
