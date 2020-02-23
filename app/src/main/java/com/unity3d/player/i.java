package com.unity3d.player;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public final class i extends Dialog implements TextWatcher, View.OnClickListener {
    private static int c = 1627389952;
    private static int d = -1;
    private static int e = 134217728;
    private static int f = 67108864;
    /* access modifiers changed from: private */
    public Context a = null;
    private UnityPlayer b = null;

    public i(Context context, UnityPlayer unityPlayer, String str, int i, boolean z, boolean z2, boolean z3, String str2) {
        super(context);
        this.a = context;
        this.b = unityPlayer;
        getWindow().setGravity(80);
        getWindow().requestFeature(1);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        setContentView(createSoftInputView());
        getWindow().setLayout(-1, -2);
        getWindow().clearFlags(2);
        if (h.a) {
            getWindow().clearFlags(e);
            getWindow().clearFlags(f);
        }
        EditText editText = (EditText) findViewById(1057292289);
        a(editText, str, i, z, z2, z3, str2);
        ((Button) findViewById(1057292290)).setOnClickListener(this);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public final void onFocusChange(View view, boolean z) {
                if (z) {
                    i.this.getWindow().setSoftInputMode(5);
                }
            }
        });
    }

    private static int a(int i, boolean z, boolean z2, boolean z3) {
        int i2 = 0;
        int i3 = (z2 ? 131072 : 0) | (z ? 32768 : 524288);
        if (z3) {
            i2 = 128;
        }
        int i4 = i2 | i3;
        if (i < 0 || i > 10) {
            return i4;
        }
        int[] iArr = {1, 16385, 12290, 17, 2, 3, 8289, 33, 1, 16417, 17};
        return (iArr[i] & 2) != 0 ? iArr[i] : i4 | iArr[i];
    }

    /* access modifiers changed from: private */
    public String a() {
        EditText editText = (EditText) findViewById(1057292289);
        if (editText == null) {
            return null;
        }
        return editText.getText().toString().trim();
    }

    private void a(EditText editText, String str, int i, boolean z, boolean z2, boolean z3, String str2) {
        editText.setImeOptions(6);
        editText.setText(str);
        editText.setHint(str2);
        editText.setHintTextColor(c);
        editText.setInputType(a(i, z, z2, z3));
        editText.setImeOptions(33554432);
        editText.addTextChangedListener(this);
        editText.setClickable(true);
        if (!z2) {
            editText.selectAll();
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, boolean z) {
        ((EditText) findViewById(1057292289)).setSelection(0, 0);
        this.b.reportSoftInputStr(str, 1, z);
    }

    public final void a(String str) {
        EditText editText = (EditText) findViewById(1057292289);
        if (editText != null) {
            editText.setText(str);
            editText.setSelection(str.length());
        }
    }

    public final void afterTextChanged(Editable editable) {
        this.b.reportSoftInputStr(editable.toString(), 0, false);
    }

    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    /* access modifiers changed from: protected */
    public final View createSoftInputView() {
        RelativeLayout relativeLayout = new RelativeLayout(this.a);
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        relativeLayout.setBackgroundColor(d);
        AnonymousClass2 r0 = new EditText(this.a) {
            public final boolean onKeyPreIme(int i, KeyEvent keyEvent) {
                if (i == 4) {
                    i.this.a(i.this.a(), true);
                    return true;
                } else if (i != 84) {
                    return super.onKeyPreIme(i, keyEvent);
                } else {
                    return true;
                }
            }

            public final void onWindowFocusChanged(boolean z) {
                super.onWindowFocusChanged(z);
                if (z) {
                    ((InputMethodManager) i.this.a.getSystemService("input_method")).showSoftInput(this, 0);
                }
            }
        };
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(15);
        layoutParams.addRule(0, 1057292290);
        r0.setLayoutParams(layoutParams);
        r0.setId(1057292289);
        relativeLayout.addView(r0);
        Button button = new Button(this.a);
        button.setText(this.a.getResources().getIdentifier("ok", "string", SystemMediaRouteProvider.PACKAGE_NAME));
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.addRule(15);
        layoutParams2.addRule(11);
        button.setLayoutParams(layoutParams2);
        button.setId(1057292290);
        button.setBackgroundColor(0);
        relativeLayout.addView(button);
        ((EditText) relativeLayout.findViewById(1057292289)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == 6) {
                    i.this.a(i.this.a(), false);
                }
                return false;
            }
        });
        relativeLayout.setPadding(16, 16, 16, 16);
        return relativeLayout;
    }

    public final void onBackPressed() {
        a(a(), true);
    }

    public final void onClick(View view) {
        a(a(), false);
    }

    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
