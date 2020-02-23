package com.vk.sdk.dialogs;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.vk.sdk.dialogs.VKShareDialogDelegate;

@TargetApi(11)
public class VKShareDialogNative extends DialogFragment implements VKShareDialogDelegate.DialogFragmentI {
    private VKShareDialogDelegate mDelegate = new VKShareDialogDelegate(this);

    public VKShareDialogNative() {
    }

    @SuppressLint({"ValidFragment"})
    VKShareDialogNative(VKShareDialogBuilder builder) {
        this.mDelegate.setAttachmentImages(builder.attachmentImages);
        this.mDelegate.setText(builder.attachmentText);
        if (!(builder.linkTitle == null || builder.linkUrl == null)) {
            this.mDelegate.setAttachmentLink(builder.linkTitle, builder.linkUrl);
        }
        this.mDelegate.setUploadedPhotos(builder.existingPhotos);
        this.mDelegate.setShareDialogListener(builder.listener);
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return this.mDelegate.onCreateDialog(savedInstanceState);
    }

    @SuppressLint({"NewApi"})
    public void onStart() {
        super.onStart();
        this.mDelegate.onStart();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.mDelegate.onSaveInstanceState(outState);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        this.mDelegate.onCancel(dialog);
    }
}
