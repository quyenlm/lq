package com.facebook.share.internal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.share.model.ShareCameraEffectContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.ShareMessengerGenericTemplateContent;
import com.facebook.share.model.ShareMessengerMediaTemplateContent;
import com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareStoryContent;
import com.facebook.share.model.ShareVideoContent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class NativeDialogParameters {
    public static Bundle create(UUID callId, ShareContent shareContent, boolean shouldFailOnDataError) {
        Validate.notNull(shareContent, "shareContent");
        Validate.notNull(callId, "callId");
        if (shareContent instanceof ShareLinkContent) {
            return create((ShareLinkContent) shareContent, shouldFailOnDataError);
        }
        if (shareContent instanceof SharePhotoContent) {
            SharePhotoContent photoContent = (SharePhotoContent) shareContent;
            return create(photoContent, ShareInternalUtility.getPhotoUrls(photoContent, callId), shouldFailOnDataError);
        } else if (shareContent instanceof ShareVideoContent) {
            ShareVideoContent videoContent = (ShareVideoContent) shareContent;
            return create(videoContent, ShareInternalUtility.getVideoUrl(videoContent, callId), shouldFailOnDataError);
        } else if (shareContent instanceof ShareOpenGraphContent) {
            ShareOpenGraphContent openGraphContent = (ShareOpenGraphContent) shareContent;
            try {
                return create(openGraphContent, ShareInternalUtility.removeNamespacesFromOGJsonObject(ShareInternalUtility.toJSONObjectForCall(callId, openGraphContent), false), shouldFailOnDataError);
            } catch (JSONException e) {
                throw new FacebookException("Unable to create a JSON Object from the provided ShareOpenGraphContent: " + e.getMessage());
            }
        } else if (shareContent instanceof ShareMediaContent) {
            ShareMediaContent mediaContent = (ShareMediaContent) shareContent;
            return create(mediaContent, ShareInternalUtility.getMediaInfos(mediaContent, callId), shouldFailOnDataError);
        } else if (shareContent instanceof ShareCameraEffectContent) {
            ShareCameraEffectContent cameraEffectContent = (ShareCameraEffectContent) shareContent;
            return create(cameraEffectContent, ShareInternalUtility.getTextureUrlBundle(cameraEffectContent, callId), shouldFailOnDataError);
        } else if (shareContent instanceof ShareMessengerGenericTemplateContent) {
            return create((ShareMessengerGenericTemplateContent) shareContent, shouldFailOnDataError);
        } else {
            if (shareContent instanceof ShareMessengerOpenGraphMusicTemplateContent) {
                return create((ShareMessengerOpenGraphMusicTemplateContent) shareContent, shouldFailOnDataError);
            }
            if (shareContent instanceof ShareMessengerMediaTemplateContent) {
                return create((ShareMessengerMediaTemplateContent) shareContent, shouldFailOnDataError);
            }
            if (!(shareContent instanceof ShareStoryContent)) {
                return null;
            }
            ShareStoryContent storyContent = (ShareStoryContent) shareContent;
            return create(storyContent, ShareInternalUtility.getBackgroundAssetMediaInfo(storyContent, callId), ShareInternalUtility.getStickerUrl(storyContent, callId), shouldFailOnDataError);
        }
    }

    private static Bundle create(ShareCameraEffectContent cameraEffectContent, Bundle attachmentUrlsBundle, boolean dataErrorsFatal) {
        Bundle params = createBaseParameters(cameraEffectContent, dataErrorsFatal);
        Utility.putNonEmptyString(params, ShareConstants.EFFECT_ID, cameraEffectContent.getEffectId());
        if (attachmentUrlsBundle != null) {
            params.putBundle(ShareConstants.EFFECT_TEXTURES, attachmentUrlsBundle);
        }
        try {
            JSONObject argsJSON = CameraEffectJSONUtility.convertToJSON(cameraEffectContent.getArguments());
            if (argsJSON != null) {
                Utility.putNonEmptyString(params, ShareConstants.EFFECT_ARGS, argsJSON.toString());
            }
            return params;
        } catch (JSONException e) {
            throw new FacebookException("Unable to create a JSON Object from the provided CameraEffectArguments: " + e.getMessage());
        }
    }

    private static Bundle create(ShareLinkContent linkContent, boolean dataErrorsFatal) {
        Bundle params = createBaseParameters(linkContent, dataErrorsFatal);
        Utility.putNonEmptyString(params, ShareConstants.TITLE, linkContent.getContentTitle());
        Utility.putNonEmptyString(params, ShareConstants.DESCRIPTION, linkContent.getContentDescription());
        Utility.putUri(params, ShareConstants.IMAGE_URL, linkContent.getImageUrl());
        Utility.putNonEmptyString(params, ShareConstants.QUOTE, linkContent.getQuote());
        Utility.putUri(params, ShareConstants.MESSENGER_URL, linkContent.getContentUrl());
        Utility.putUri(params, ShareConstants.TARGET_DISPLAY, linkContent.getContentUrl());
        return params;
    }

    private static Bundle create(SharePhotoContent photoContent, List<String> imageUrls, boolean dataErrorsFatal) {
        Bundle params = createBaseParameters(photoContent, dataErrorsFatal);
        params.putStringArrayList(ShareConstants.PHOTOS, new ArrayList(imageUrls));
        return params;
    }

    private static Bundle create(ShareVideoContent videoContent, String videoUrl, boolean dataErrorsFatal) {
        Bundle params = createBaseParameters(videoContent, dataErrorsFatal);
        Utility.putNonEmptyString(params, ShareConstants.TITLE, videoContent.getContentTitle());
        Utility.putNonEmptyString(params, ShareConstants.DESCRIPTION, videoContent.getContentDescription());
        Utility.putNonEmptyString(params, ShareConstants.VIDEO_URL, videoUrl);
        return params;
    }

    private static Bundle create(ShareMediaContent mediaContent, List<Bundle> mediaInfos, boolean dataErrorsFatal) {
        Bundle params = createBaseParameters(mediaContent, dataErrorsFatal);
        params.putParcelableArrayList(ShareConstants.MEDIA, new ArrayList(mediaInfos));
        return params;
    }

    private static Bundle create(ShareOpenGraphContent openGraphContent, JSONObject openGraphActionJSON, boolean dataErrorsFatal) {
        Bundle params = createBaseParameters(openGraphContent, dataErrorsFatal);
        Utility.putNonEmptyString(params, ShareConstants.PREVIEW_PROPERTY_NAME, (String) ShareInternalUtility.getFieldNameAndNamespaceFromFullName(openGraphContent.getPreviewPropertyName()).second);
        Utility.putNonEmptyString(params, ShareConstants.ACTION_TYPE, openGraphContent.getAction().getActionType());
        Utility.putNonEmptyString(params, ShareConstants.ACTION, openGraphActionJSON.toString());
        return params;
    }

    private static Bundle create(ShareMessengerGenericTemplateContent genericTemplateContent, boolean dataErrorsFatal) {
        Bundle params = createBaseParameters(genericTemplateContent, dataErrorsFatal);
        try {
            MessengerShareContentUtility.addGenericTemplateContent(params, genericTemplateContent);
            return params;
        } catch (JSONException e) {
            throw new FacebookException("Unable to create a JSON Object from the provided ShareMessengerGenericTemplateContent: " + e.getMessage());
        }
    }

    private static Bundle create(ShareMessengerOpenGraphMusicTemplateContent openGraphMusicTemplateContent, boolean dataErrorsFatal) {
        Bundle params = createBaseParameters(openGraphMusicTemplateContent, dataErrorsFatal);
        try {
            MessengerShareContentUtility.addOpenGraphMusicTemplateContent(params, openGraphMusicTemplateContent);
            return params;
        } catch (JSONException e) {
            throw new FacebookException("Unable to create a JSON Object from the provided ShareMessengerOpenGraphMusicTemplateContent: " + e.getMessage());
        }
    }

    private static Bundle create(ShareMessengerMediaTemplateContent mediaTemplateContent, boolean dataErrorsFatal) {
        Bundle params = createBaseParameters(mediaTemplateContent, dataErrorsFatal);
        try {
            MessengerShareContentUtility.addMediaTemplateContent(params, mediaTemplateContent);
            return params;
        } catch (JSONException e) {
            throw new FacebookException("Unable to create a JSON Object from the provided ShareMessengerMediaTemplateContent: " + e.getMessage());
        }
    }

    private static Bundle create(ShareStoryContent storyContent, @Nullable Bundle mediaInfo, @Nullable Bundle stickerInfo, boolean dataErrorsFatal) {
        Bundle params = createBaseParameters(storyContent, dataErrorsFatal);
        if (mediaInfo != null) {
            params.putParcelable(ShareConstants.STORY_BG_ASSET, mediaInfo);
        }
        if (stickerInfo != null) {
            params.putParcelable(ShareConstants.STORY_INTERACTIVE_ASSET_URI, stickerInfo);
        }
        List<String> backgroundColorList = storyContent.getBackgroundColorList();
        if (!Utility.isNullOrEmpty(backgroundColorList)) {
            params.putStringArrayList(ShareConstants.STORY_INTERACTIVE_COLOR_LIST, new ArrayList(backgroundColorList));
        }
        Utility.putNonEmptyString(params, ShareConstants.STORY_DEEP_LINK_URL, storyContent.getAttributionLink());
        return params;
    }

    private static Bundle createBaseParameters(ShareContent content, boolean dataErrorsFatal) {
        Bundle params = new Bundle();
        Utility.putUri(params, ShareConstants.CONTENT_URL, content.getContentUrl());
        Utility.putNonEmptyString(params, ShareConstants.PLACE_ID, content.getPlaceId());
        Utility.putNonEmptyString(params, ShareConstants.PAGE_ID, content.getPageId());
        Utility.putNonEmptyString(params, ShareConstants.REF, content.getRef());
        params.putBoolean(ShareConstants.DATA_FAILURES_FATAL, dataErrorsFatal);
        List<String> peopleIds = content.getPeopleIds();
        if (!Utility.isNullOrEmpty(peopleIds)) {
            params.putStringArrayList(ShareConstants.PEOPLE_IDS, new ArrayList(peopleIds));
        }
        ShareHashtag shareHashtag = content.getShareHashtag();
        if (shareHashtag != null) {
            Utility.putNonEmptyString(params, ShareConstants.HASHTAG, shareHashtag.getHashtag());
        }
        return params;
    }
}
