package com.tencent.midas.oversea.comm;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class APAlertDialog extends Dialog {
    /* access modifiers changed from: private */
    public static boolean a = false;
    /* access modifiers changed from: private */
    public static boolean b = false;

    public static class Builder {
        private Context a;
        private String b;
        private String c;
        private String d;
        private String e;
        private SpannableString f = null;
        private SpannableString g = null;
        private SpannableString h = null;
        private SpannableString i = null;
        private View j;
        private View k;
        private View l;
        /* access modifiers changed from: private */
        public DialogInterface.OnClickListener m;
        /* access modifiers changed from: private */
        public DialogInterface.OnClickListener n;

        public Builder(Context context) {
            this.a = context;
        }

        private void a(View view) {
            TextView textView = (TextView) view.findViewById(APCommMethod.getId(this.a, "unipay_id_DialogTittle"));
            if (this.f != null) {
                textView.setText(this.f);
            } else if (this.b != null) {
                textView.setText(this.b);
            }
        }

        private void a(View view, APAlertDialog aPAlertDialog) {
            Button button = (Button) view.findViewById(APCommMethod.getId(this.a, "unipay_id_DialogEnsure"));
            button.setVisibility(0);
            this.k = button;
            if (this.h != null) {
                button.setText(this.h);
                if (this.m != null) {
                    button.setOnClickListener(new a(this, aPAlertDialog));
                }
            } else if (this.d != null) {
                button.setText(this.d);
                if (this.m != null) {
                    button.setOnClickListener(new b(this, aPAlertDialog));
                }
            } else {
                view.findViewById(APCommMethod.getId(this.a, "unipay_id_DialogEnsure")).setVisibility(8);
            }
        }

        private void b(View view) {
            TextView textView = (TextView) view.findViewById(APCommMethod.getId(this.a, "unipay_id_DialogMessage"));
            if (this.g != null) {
                textView.setText(this.g);
            } else if (this.c != null) {
                textView.setText(this.c);
            } else if (this.j != null) {
                ((LinearLayout) view.findViewById(APCommMethod.getId(this.a, "unipay_dialog_message_content"))).removeAllViews();
                ((LinearLayout) view.findViewById(APCommMethod.getId(this.a, "unipay_dialog_message_content"))).addView(this.j, new ViewGroup.LayoutParams(-1, -2));
            }
        }

        private void b(View view, APAlertDialog aPAlertDialog) {
            Button button = (Button) view.findViewById(APCommMethod.getId(this.a, "unipay_id_DialogCancel"));
            button.setVisibility(0);
            this.l = button;
            if (this.i != null) {
                button.setText(this.i);
                if (this.n != null) {
                    button.setOnClickListener(new c(this, aPAlertDialog));
                }
            } else if (this.e != null) {
                button.setText(this.e);
                if (this.n != null) {
                    button.setOnClickListener(new d(this, aPAlertDialog));
                }
            } else {
                view.findViewById(APCommMethod.getId(this.a, "unipay_id_DialogCancel")).setVisibility(8);
            }
        }

        public APAlertDialog create() {
            APAlertDialog aPAlertDialog = new APAlertDialog(this.a, APCommMethod.getStyleId(this.a, "unipay_customDialog"));
            View inflate = ((LayoutInflater) this.a.getSystemService("layout_inflater")).inflate(APCommMethod.getLayoutId(this.a, "unipay_abroad_layout_custom_dialog"), (ViewGroup) null);
            aPAlertDialog.addContentView(inflate, new ViewGroup.LayoutParams(-2, -2));
            aPAlertDialog.getWindow().getAttributes().width = (int) ((this.a.getResources().getConfiguration().orientation == 2 ? 0.56f : 0.8f) * ((float) ((WindowManager) this.a.getSystemService("window")).getDefaultDisplay().getWidth()));
            if (APAlertDialog.a) {
                a(inflate, aPAlertDialog);
            }
            if (APAlertDialog.b) {
                b(inflate, aPAlertDialog);
            }
            a(inflate);
            b(inflate);
            aPAlertDialog.setContentView(inflate);
            return aPAlertDialog;
        }

        public View getNegativeButtonView() {
            return this.l;
        }

        public View getPositiveButtonView() {
            return this.k;
        }

        public Builder setContentView(View view) {
            this.j = view;
            return this;
        }

        public Builder setMessage(int i2) {
            this.c = (String) this.a.getText(i2);
            return this;
        }

        public Builder setMessage(SpannableString spannableString) {
            this.g = spannableString;
            return this;
        }

        public Builder setMessage(String str) {
            this.c = str;
            return this;
        }

        public Builder setNegativeButton(int i2, DialogInterface.OnClickListener onClickListener) {
            boolean unused = APAlertDialog.b = true;
            this.e = (String) this.a.getText(i2);
            this.n = onClickListener;
            return this;
        }

        public Builder setNegativeButton(SpannableString spannableString, DialogInterface.OnClickListener onClickListener) {
            boolean unused = APAlertDialog.b = true;
            this.i = spannableString;
            this.n = onClickListener;
            return this;
        }

        public Builder setNegativeButton(String str, DialogInterface.OnClickListener onClickListener) {
            boolean unused = APAlertDialog.b = true;
            this.e = str;
            this.n = onClickListener;
            return this;
        }

        public Builder setPositiveButton(int i2, DialogInterface.OnClickListener onClickListener) {
            boolean unused = APAlertDialog.a = true;
            this.d = (String) this.a.getText(i2);
            this.m = onClickListener;
            return this;
        }

        public Builder setPositiveButton(SpannableString spannableString, DialogInterface.OnClickListener onClickListener) {
            boolean unused = APAlertDialog.a = true;
            this.h = spannableString;
            this.m = onClickListener;
            return this;
        }

        public Builder setPositiveButton(String str, DialogInterface.OnClickListener onClickListener) {
            boolean unused = APAlertDialog.a = true;
            this.d = str;
            this.m = onClickListener;
            return this;
        }

        public Builder setTitle(int i2) {
            this.b = (String) this.a.getText(i2);
            return this;
        }

        public Builder setTitle(SpannableString spannableString) {
            this.f = spannableString;
            return this;
        }

        public Builder setTitle(String str) {
            this.b = str;
            return this;
        }
    }

    public APAlertDialog(Context context) {
        super(context);
    }

    public APAlertDialog(Context context, int i) {
        super(context, i);
    }
}
