package com.beetalk.sdk.facebook;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class FBPostItem {
    public String caption;
    public byte[] data;
    public String description;
    public String link;
    public String mediaTagName;
    public String mediaUrl;
    public String name;

    public FBPostItem(String name2, String caption2, String description2, String link2, String mediaUrl2) {
        this.name = name2;
        this.caption = caption2;
        this.description = description2;
        this.link = link2;
        this.mediaUrl = mediaUrl2;
    }

    public FBPostItem(String name2, String caption2, String description2, String link2) {
        this.name = name2;
        this.caption = caption2;
        this.description = description2;
        this.link = link2;
    }

    public void show(Activity activity) {
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token == null || token.isExpired()) {
            Toast.makeText(activity, "No Active Session Available", 0).show();
            return;
        }
        Uri contentUri = null;
        Uri imageUri = null;
        try {
            if (!TextUtils.isEmpty(this.link)) {
                contentUri = Uri.parse(this.link);
            }
            if (!TextUtils.isEmpty(this.mediaUrl)) {
                imageUri = Uri.parse(this.mediaUrl);
            }
            ShareLinkContent shareLinkContent = ((ShareLinkContent.Builder) new ShareLinkContent.Builder().setContentTitle(this.name).setContentDescription(this.description).setImageUrl(imageUri).setContentUrl(contentUri)).build();
            if (activity != null && !activity.isFinishing()) {
                new ShareDialog(activity).show(shareLinkContent);
            }
        } catch (Exception e) {
            Toast.makeText(activity, "Uri error", 0).show();
        }
    }
}
