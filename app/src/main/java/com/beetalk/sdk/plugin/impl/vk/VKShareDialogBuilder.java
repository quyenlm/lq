package com.beetalk.sdk.plugin.impl.vk;

import android.app.FragmentManager;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.api.photo.VKUploadImage;

public class VKShareDialogBuilder {
    VKUploadImage[] attachmentImages;
    CharSequence attachmentText;
    VKPhotoArray existingPhotos;
    String linkTitle;
    String linkUrl;
    VKShareDialogListener listener;

    public interface VKShareDialogListener {
        void onVkShareCancel();

        void onVkShareComplete(int i);

        void onVkShareError(VKError vKError);
    }

    public VKShareDialogBuilder setAttachmentImages(VKUploadImage[] images) {
        this.attachmentImages = images;
        return this;
    }

    public VKShareDialogBuilder setText(CharSequence textToPost) {
        this.attachmentText = textToPost;
        return this;
    }

    public VKShareDialogBuilder setAttachmentLink(String linkTitle2, String linkUrl2) {
        this.linkTitle = linkTitle2;
        this.linkUrl = linkUrl2;
        return this;
    }

    public VKShareDialogBuilder setUploadedPhotos(VKPhotoArray photos) {
        this.existingPhotos = photos;
        return this;
    }

    public VKShareDialogBuilder setShareDialogListener(VKShareDialogListener listener2) {
        this.listener = listener2;
        return this;
    }

    public void show(FragmentManager manager, String tag) {
        new VKShareDialog(this).show(manager, tag);
    }
}
