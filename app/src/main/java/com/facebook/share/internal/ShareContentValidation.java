package com.facebook.share.internal;

import android.graphics.Bitmap;
import android.net.Uri;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.share.model.ShareCameraEffectContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMedia;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.ShareMessengerActionButton;
import com.facebook.share.model.ShareMessengerGenericTemplateContent;
import com.facebook.share.model.ShareMessengerMediaTemplateContent;
import com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent;
import com.facebook.share.model.ShareMessengerURLActionButton;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.ShareOpenGraphValueContainer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareStoryContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import java.util.List;
import java.util.Locale;

public class ShareContentValidation {
    private static Validator ApiValidator;
    private static Validator DefaultValidator;
    private static Validator StoryValidator;
    private static Validator WebShareValidator;

    public static void validateForMessage(ShareContent content) {
        validate(content, getDefaultValidator());
    }

    public static void validateForNativeShare(ShareContent content) {
        validate(content, getDefaultValidator());
    }

    public static void validateForWebShare(ShareContent content) {
        validate(content, getWebShareValidator());
    }

    public static void validateForApiShare(ShareContent content) {
        validate(content, getApiValidator());
    }

    public static void validateForStoryShare(ShareContent content) {
        validate(content, getStoryValidator());
    }

    private static Validator getStoryValidator() {
        if (StoryValidator == null) {
            StoryValidator = new StoryShareValidator();
        }
        return StoryValidator;
    }

    private static Validator getDefaultValidator() {
        if (DefaultValidator == null) {
            DefaultValidator = new Validator();
        }
        return DefaultValidator;
    }

    private static Validator getApiValidator() {
        if (ApiValidator == null) {
            ApiValidator = new ApiValidator();
        }
        return ApiValidator;
    }

    private static Validator getWebShareValidator() {
        if (WebShareValidator == null) {
            WebShareValidator = new WebShareValidator();
        }
        return WebShareValidator;
    }

    private static void validate(ShareContent content, Validator validator) throws FacebookException {
        if (content == null) {
            throw new FacebookException("Must provide non-null content to share");
        } else if (content instanceof ShareLinkContent) {
            validator.validate((ShareLinkContent) content);
        } else if (content instanceof SharePhotoContent) {
            validator.validate((SharePhotoContent) content);
        } else if (content instanceof ShareVideoContent) {
            validator.validate((ShareVideoContent) content);
        } else if (content instanceof ShareOpenGraphContent) {
            validator.validate((ShareOpenGraphContent) content);
        } else if (content instanceof ShareMediaContent) {
            validator.validate((ShareMediaContent) content);
        } else if (content instanceof ShareCameraEffectContent) {
            validator.validate((ShareCameraEffectContent) content);
        } else if (content instanceof ShareMessengerOpenGraphMusicTemplateContent) {
            validator.validate((ShareMessengerOpenGraphMusicTemplateContent) content);
        } else if (content instanceof ShareMessengerMediaTemplateContent) {
            validator.validate((ShareMessengerMediaTemplateContent) content);
        } else if (content instanceof ShareMessengerGenericTemplateContent) {
            validator.validate((ShareMessengerGenericTemplateContent) content);
        } else if (content instanceof ShareStoryContent) {
            validator.validate((ShareStoryContent) content);
        }
    }

    /* access modifiers changed from: private */
    public static void validateStoryContent(ShareStoryContent storyContent, Validator validator) {
        if (storyContent == null || (storyContent.getBackgroundAsset() == null && storyContent.getStickerAsset() == null)) {
            throw new FacebookException("Must pass the Facebook app a background asset, a sticker asset, or both");
        }
        if (storyContent.getBackgroundAsset() != null) {
            validator.validate(storyContent.getBackgroundAsset());
        }
        if (storyContent.getStickerAsset() != null) {
            validator.validate(storyContent.getStickerAsset());
        }
    }

    /* access modifiers changed from: private */
    public static void validateLinkContent(ShareLinkContent linkContent, Validator validator) {
        Uri imageUrl = linkContent.getImageUrl();
        if (imageUrl != null && !Utility.isWebUri(imageUrl)) {
            throw new FacebookException("Image Url must be an http:// or https:// url");
        }
    }

    /* access modifiers changed from: private */
    public static void validatePhotoContent(SharePhotoContent photoContent, Validator validator) {
        List<SharePhoto> photos = photoContent.getPhotos();
        if (photos == null || photos.isEmpty()) {
            throw new FacebookException("Must specify at least one Photo in SharePhotoContent.");
        } else if (photos.size() > 6) {
            throw new FacebookException(String.format(Locale.ROOT, "Cannot add more than %d photos.", new Object[]{6}));
        } else {
            for (SharePhoto photo : photos) {
                validator.validate(photo);
            }
        }
    }

    private static void validatePhoto(SharePhoto photo) {
        if (photo == null) {
            throw new FacebookException("Cannot share a null SharePhoto");
        }
        Bitmap photoBitmap = photo.getBitmap();
        Uri photoUri = photo.getImageUrl();
        if (photoBitmap == null && photoUri == null) {
            throw new FacebookException("SharePhoto does not have a Bitmap or ImageUrl specified");
        }
    }

    /* access modifiers changed from: private */
    public static void validatePhotoForApi(SharePhoto photo, Validator validator) {
        validatePhoto(photo);
        Bitmap photoBitmap = photo.getBitmap();
        Uri photoUri = photo.getImageUrl();
        if (photoBitmap == null && Utility.isWebUri(photoUri) && !validator.isOpenGraphContent()) {
            throw new FacebookException("Cannot set the ImageUrl of a SharePhoto to the Uri of an image on the web when sharing SharePhotoContent");
        }
    }

    /* access modifiers changed from: private */
    public static void validatePhotoForNativeDialog(SharePhoto photo, Validator validator) {
        validatePhotoForApi(photo, validator);
        if (photo.getBitmap() != null || !Utility.isWebUri(photo.getImageUrl())) {
            Validate.hasContentProvider(FacebookSdk.getApplicationContext());
        }
    }

    /* access modifiers changed from: private */
    public static void validatePhotoForWebDialog(SharePhoto photo, Validator validator) {
        validatePhoto(photo);
    }

    /* access modifiers changed from: private */
    public static void validateVideoContent(ShareVideoContent videoContent, Validator validator) {
        validator.validate(videoContent.getVideo());
        SharePhoto previewPhoto = videoContent.getPreviewPhoto();
        if (previewPhoto != null) {
            validator.validate(previewPhoto);
        }
    }

    /* access modifiers changed from: private */
    public static void validateVideo(ShareVideo video, Validator validator) {
        if (video == null) {
            throw new FacebookException("Cannot share a null ShareVideo");
        }
        Uri localUri = video.getLocalUrl();
        if (localUri == null) {
            throw new FacebookException("ShareVideo does not have a LocalUrl specified");
        } else if (!Utility.isContentUri(localUri) && !Utility.isFileUri(localUri)) {
            throw new FacebookException("ShareVideo must reference a video that is on the device");
        }
    }

    /* access modifiers changed from: private */
    public static void validateMediaContent(ShareMediaContent mediaContent, Validator validator) {
        List<ShareMedia> media = mediaContent.getMedia();
        if (media == null || media.isEmpty()) {
            throw new FacebookException("Must specify at least one medium in ShareMediaContent.");
        } else if (media.size() > 6) {
            throw new FacebookException(String.format(Locale.ROOT, "Cannot add more than %d media.", new Object[]{6}));
        } else {
            for (ShareMedia medium : media) {
                validator.validate(medium);
            }
        }
    }

    public static void validateMedium(ShareMedia medium, Validator validator) {
        if (medium instanceof SharePhoto) {
            validator.validate((SharePhoto) medium);
        } else if (medium instanceof ShareVideo) {
            validator.validate((ShareVideo) medium);
        } else {
            throw new FacebookException(String.format(Locale.ROOT, "Invalid media type: %s", new Object[]{medium.getClass().getSimpleName()}));
        }
    }

    /* access modifiers changed from: private */
    public static void validateCameraEffectContent(ShareCameraEffectContent cameraEffectContent, Validator validator) {
        if (Utility.isNullOrEmpty(cameraEffectContent.getEffectId())) {
            throw new FacebookException("Must specify a non-empty effectId");
        }
    }

    /* access modifiers changed from: private */
    public static void validateOpenGraphContent(ShareOpenGraphContent openGraphContent, Validator validator) {
        validator.validate(openGraphContent.getAction());
        String previewPropertyName = openGraphContent.getPreviewPropertyName();
        if (Utility.isNullOrEmpty(previewPropertyName)) {
            throw new FacebookException("Must specify a previewPropertyName.");
        } else if (openGraphContent.getAction().get(previewPropertyName) == null) {
            throw new FacebookException("Property \"" + previewPropertyName + "\" was not found on the action. The name of the preview property must match the name of an action property.");
        }
    }

    /* access modifiers changed from: private */
    public static void validateOpenGraphAction(ShareOpenGraphAction openGraphAction, Validator validator) {
        if (openGraphAction == null) {
            throw new FacebookException("Must specify a non-null ShareOpenGraphAction");
        } else if (Utility.isNullOrEmpty(openGraphAction.getActionType())) {
            throw new FacebookException("ShareOpenGraphAction must have a non-empty actionType");
        } else {
            validator.validate(openGraphAction, false);
        }
    }

    /* access modifiers changed from: private */
    public static void validateOpenGraphObject(ShareOpenGraphObject openGraphObject, Validator validator) {
        if (openGraphObject == null) {
            throw new FacebookException("Cannot share a null ShareOpenGraphObject");
        }
        validator.validate(openGraphObject, true);
    }

    /* access modifiers changed from: private */
    public static void validateOpenGraphValueContainer(ShareOpenGraphValueContainer valueContainer, Validator validator, boolean requireNamespace) {
        for (String key : valueContainer.keySet()) {
            validateOpenGraphKey(key, requireNamespace);
            Object o = valueContainer.get(key);
            if (o instanceof List) {
                for (Object objectInList : (List) o) {
                    if (objectInList == null) {
                        throw new FacebookException("Cannot put null objects in Lists in ShareOpenGraphObjects and ShareOpenGraphActions");
                    }
                    validateOpenGraphValueContainerObject(objectInList, validator);
                }
                continue;
            } else {
                validateOpenGraphValueContainerObject(o, validator);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void validateMessengerOpenGraphMusicTemplate(ShareMessengerOpenGraphMusicTemplateContent content) {
        if (Utility.isNullOrEmpty(content.getPageId())) {
            throw new FacebookException("Must specify Page Id for ShareMessengerOpenGraphMusicTemplateContent");
        } else if (content.getUrl() == null) {
            throw new FacebookException("Must specify url for ShareMessengerOpenGraphMusicTemplateContent");
        } else {
            validateShareMessengerActionButton(content.getButton());
        }
    }

    /* access modifiers changed from: private */
    public static void validateShareMessengerGenericTemplateContent(ShareMessengerGenericTemplateContent content) {
        if (Utility.isNullOrEmpty(content.getPageId())) {
            throw new FacebookException("Must specify Page Id for ShareMessengerGenericTemplateContent");
        } else if (content.getGenericTemplateElement() == null) {
            throw new FacebookException("Must specify element for ShareMessengerGenericTemplateContent");
        } else if (Utility.isNullOrEmpty(content.getGenericTemplateElement().getTitle())) {
            throw new FacebookException("Must specify title for ShareMessengerGenericTemplateElement");
        } else {
            validateShareMessengerActionButton(content.getGenericTemplateElement().getButton());
        }
    }

    /* access modifiers changed from: private */
    public static void validateShareMessengerMediaTemplateContent(ShareMessengerMediaTemplateContent content) {
        if (Utility.isNullOrEmpty(content.getPageId())) {
            throw new FacebookException("Must specify Page Id for ShareMessengerMediaTemplateContent");
        } else if (content.getMediaUrl() != null || !Utility.isNullOrEmpty(content.getAttachmentId())) {
            validateShareMessengerActionButton(content.getButton());
        } else {
            throw new FacebookException("Must specify either attachmentId or mediaURL for ShareMessengerMediaTemplateContent");
        }
    }

    private static void validateShareMessengerActionButton(ShareMessengerActionButton button) {
        if (button != null) {
            if (Utility.isNullOrEmpty(button.getTitle())) {
                throw new FacebookException("Must specify title for ShareMessengerActionButton");
            } else if (button instanceof ShareMessengerURLActionButton) {
                validateShareMessengerURLActionButton((ShareMessengerURLActionButton) button);
            }
        }
    }

    private static void validateShareMessengerURLActionButton(ShareMessengerURLActionButton button) {
        if (button.getUrl() == null) {
            throw new FacebookException("Must specify url for ShareMessengerURLActionButton");
        }
    }

    private static void validateOpenGraphKey(String key, boolean requireNamespace) {
        if (requireNamespace) {
            String[] components = key.split(":");
            if (components.length < 2) {
                throw new FacebookException("Open Graph keys must be namespaced: %s", key);
            }
            for (String component : components) {
                if (component.isEmpty()) {
                    throw new FacebookException("Invalid key found in Open Graph dictionary: %s", key);
                }
            }
        }
    }

    private static void validateOpenGraphValueContainerObject(Object o, Validator validator) {
        if (o instanceof ShareOpenGraphObject) {
            validator.validate((ShareOpenGraphObject) o);
        } else if (o instanceof SharePhoto) {
            validator.validate((SharePhoto) o);
        }
    }

    private static class StoryShareValidator extends Validator {
        private StoryShareValidator() {
            super();
        }

        public void validate(ShareStoryContent storyContent) {
            ShareContentValidation.validateStoryContent(storyContent, this);
        }
    }

    private static class WebShareValidator extends Validator {
        private WebShareValidator() {
            super();
        }

        public void validate(ShareVideoContent videoContent) {
            throw new FacebookException("Cannot share ShareVideoContent via web sharing dialogs");
        }

        public void validate(ShareMediaContent mediaContent) {
            throw new FacebookException("Cannot share ShareMediaContent via web sharing dialogs");
        }

        public void validate(SharePhoto photo) {
            ShareContentValidation.validatePhotoForWebDialog(photo, this);
        }
    }

    private static class ApiValidator extends Validator {
        private ApiValidator() {
            super();
        }

        public void validate(SharePhoto photo) {
            ShareContentValidation.validatePhotoForApi(photo, this);
        }

        public void validate(ShareVideoContent videoContent) {
            if (!Utility.isNullOrEmpty(videoContent.getPlaceId())) {
                throw new FacebookException("Cannot share video content with place IDs using the share api");
            } else if (!Utility.isNullOrEmpty(videoContent.getPeopleIds())) {
                throw new FacebookException("Cannot share video content with people IDs using the share api");
            } else if (!Utility.isNullOrEmpty(videoContent.getRef())) {
                throw new FacebookException("Cannot share video content with referrer URL using the share api");
            }
        }

        public void validate(ShareMediaContent mediaContent) {
            throw new FacebookException("Cannot share ShareMediaContent using the share api");
        }

        public void validate(ShareLinkContent linkContent) {
            if (!Utility.isNullOrEmpty(linkContent.getQuote())) {
                throw new FacebookException("Cannot share link content with quote using the share api");
            }
        }
    }

    private static class Validator {
        private boolean isOpenGraphContent;

        private Validator() {
            this.isOpenGraphContent = false;
        }

        public void validate(ShareLinkContent linkContent) {
            ShareContentValidation.validateLinkContent(linkContent, this);
        }

        public void validate(SharePhotoContent photoContent) {
            ShareContentValidation.validatePhotoContent(photoContent, this);
        }

        public void validate(ShareVideoContent videoContent) {
            ShareContentValidation.validateVideoContent(videoContent, this);
        }

        public void validate(ShareMediaContent mediaContent) {
            ShareContentValidation.validateMediaContent(mediaContent, this);
        }

        public void validate(ShareCameraEffectContent cameraEffectContent) {
            ShareContentValidation.validateCameraEffectContent(cameraEffectContent, this);
        }

        public void validate(ShareOpenGraphContent openGraphContent) {
            this.isOpenGraphContent = true;
            ShareContentValidation.validateOpenGraphContent(openGraphContent, this);
        }

        public void validate(ShareOpenGraphAction openGraphAction) {
            ShareContentValidation.validateOpenGraphAction(openGraphAction, this);
        }

        public void validate(ShareOpenGraphObject openGraphObject) {
            ShareContentValidation.validateOpenGraphObject(openGraphObject, this);
        }

        public void validate(ShareOpenGraphValueContainer openGraphValueContainer, boolean requireNamespace) {
            ShareContentValidation.validateOpenGraphValueContainer(openGraphValueContainer, this, requireNamespace);
        }

        public void validate(SharePhoto photo) {
            ShareContentValidation.validatePhotoForNativeDialog(photo, this);
        }

        public void validate(ShareVideo video) {
            ShareContentValidation.validateVideo(video, this);
        }

        public void validate(ShareMedia medium) {
            ShareContentValidation.validateMedium(medium, this);
        }

        public void validate(ShareMessengerOpenGraphMusicTemplateContent content) {
            ShareContentValidation.validateMessengerOpenGraphMusicTemplate(content);
        }

        public void validate(ShareMessengerGenericTemplateContent content) {
            ShareContentValidation.validateShareMessengerGenericTemplateContent(content);
        }

        public void validate(ShareMessengerMediaTemplateContent content) {
            ShareContentValidation.validateShareMessengerMediaTemplateContent(content);
        }

        public void validate(ShareStoryContent storyContent) {
            ShareContentValidation.validateStoryContent(storyContent, this);
        }

        public boolean isOpenGraphContent() {
            return this.isOpenGraphContent;
        }
    }
}
