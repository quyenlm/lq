package com.vk.sdk.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.vk.sdk.R;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.httpClient.VKHttpClient;
import com.vk.sdk.api.httpClient.VKImageOperation;

public class VKCaptchaDialog {
    static final /* synthetic */ boolean $assertionsDisabled = (!VKCaptchaDialog.class.desiredAssertionStatus());
    private EditText mCaptchaAnswer;
    /* access modifiers changed from: private */
    public final VKError mCaptchaError;
    /* access modifiers changed from: private */
    public ImageView mCaptchaImage;
    private float mDensity;
    /* access modifiers changed from: private */
    public ProgressBar mProgressBar;

    public VKCaptchaDialog(VKError captchaError) {
        this.mCaptchaError = captchaError;
    }

    public void show(@NonNull Context context, @Nullable DialogInterface.OnDismissListener onDismissListener) {
        View innerView = View.inflate(context, R.layout.vk_captcha_dialog, (ViewGroup) null);
        if ($assertionsDisabled || innerView != null) {
            this.mCaptchaAnswer = (EditText) innerView.findViewById(R.id.captchaAnswer);
            this.mCaptchaImage = (ImageView) innerView.findViewById(R.id.imageView);
            this.mProgressBar = (ProgressBar) innerView.findViewById(R.id.progressBar);
            this.mDensity = context.getResources().getDisplayMetrics().density;
            final AlertDialog dialog = new AlertDialog.Builder(context).setView(innerView).create();
            this.mCaptchaAnswer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        dialog.getWindow().setSoftInputMode(5);
                    }
                }
            });
            this.mCaptchaAnswer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (actionId != 4) {
                        return false;
                    }
                    VKCaptchaDialog.this.sendAnswer();
                    dialog.dismiss();
                    return true;
                }
            });
            dialog.setButton(-2, context.getString(17039370), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    VKCaptchaDialog.this.sendAnswer();
                    dialog.dismiss();
                }
            });
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialogInterface) {
                    dialog.dismiss();
                    VKCaptchaDialog.this.mCaptchaError.request.cancel();
                }
            });
            if (onDismissListener != null) {
                dialog.setOnDismissListener(onDismissListener);
            }
            loadImage();
            dialog.show();
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: private */
    public void sendAnswer() {
        this.mCaptchaError.answerCaptcha(this.mCaptchaAnswer.getText() != null ? this.mCaptchaAnswer.getText().toString() : "");
    }

    /* access modifiers changed from: private */
    public void loadImage() {
        VKImageOperation imageOperation = new VKImageOperation(this.mCaptchaError.captchaImg);
        imageOperation.imageDensity = this.mDensity;
        imageOperation.setImageOperationListener(new VKImageOperation.VKImageOperationListener() {
            public void onComplete(VKImageOperation operation, Bitmap image) {
                VKCaptchaDialog.this.mCaptchaImage.setImageBitmap(image);
                VKCaptchaDialog.this.mCaptchaImage.setVisibility(0);
                VKCaptchaDialog.this.mProgressBar.setVisibility(8);
            }

            public void onError(VKImageOperation operation, VKError error) {
                VKCaptchaDialog.this.loadImage();
            }
        });
        VKHttpClient.enqueueOperation(imageOperation);
    }
}
