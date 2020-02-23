package com.facebook.share.internal;

import android.net.Uri;
import android.os.Bundle;
import com.facebook.internal.Utility;
import com.facebook.share.model.ShareMessengerActionButton;
import com.facebook.share.model.ShareMessengerGenericTemplateContent;
import com.facebook.share.model.ShareMessengerGenericTemplateElement;
import com.facebook.share.model.ShareMessengerMediaTemplateContent;
import com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent;
import com.facebook.share.model.ShareMessengerURLActionButton;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessengerShareContentUtility {
    public static final String ATTACHMENT = "attachment";
    public static final String ATTACHMENT_ID = "attachment_id";
    public static final String ATTACHMENT_PAYLOAD = "payload";
    public static final String ATTACHMENT_TEMPLATE_TYPE = "template";
    public static final String ATTACHMENT_TYPE = "type";
    public static final String BUTTONS = "buttons";
    public static final String BUTTON_TYPE = "type";
    public static final String BUTTON_URL_TYPE = "web_url";
    public static final String DEFAULT_ACTION = "default_action";
    public static final String ELEMENTS = "elements";
    public static final Pattern FACEBOOK_DOMAIN = Pattern.compile("^(.+)\\.(facebook\\.com)$");
    public static final String FALLBACK_URL = "fallback_url";
    public static final String IMAGE_ASPECT_RATIO = "image_aspect_ratio";
    public static final String IMAGE_RATIO_HORIZONTAL = "horizontal";
    public static final String IMAGE_RATIO_SQUARE = "square";
    public static final String IMAGE_URL = "image_url";
    public static final String MEDIA_IMAGE = "image";
    public static final String MEDIA_TYPE = "media_type";
    public static final String MEDIA_VIDEO = "video";
    public static final String MESSENGER_EXTENSIONS = "messenger_extensions";
    public static final String PREVIEW_DEFAULT = "DEFAULT";
    public static final String PREVIEW_OPEN_GRAPH = "OPEN_GRAPH";
    public static final String SHARABLE = "sharable";
    public static final String SHARE_BUTTON_HIDE = "hide";
    public static final String SUBTITLE = "subtitle";
    public static final String TEMPLATE_GENERIC_TYPE = "generic";
    public static final String TEMPLATE_MEDIA_TYPE = "media";
    public static final String TEMPLATE_OPEN_GRAPH_TYPE = "open_graph";
    public static final String TEMPLATE_TYPE = "template_type";
    public static final String TITLE = "title";
    public static final String URL = "url";
    public static final String WEBVIEW_RATIO = "webview_height_ratio";
    public static final String WEBVIEW_RATIO_COMPACT = "compact";
    public static final String WEBVIEW_RATIO_FULL = "full";
    public static final String WEBVIEW_RATIO_TALL = "tall";
    public static final String WEBVIEW_SHARE_BUTTON = "webview_share_button";

    public static void addGenericTemplateContent(Bundle params, ShareMessengerGenericTemplateContent content) throws JSONException {
        addGenericTemplateElementForPreview(params, content.getGenericTemplateElement());
        Utility.putJSONValueInBundle(params, ShareConstants.MESSENGER_PLATFORM_CONTENT, serializeGenericTemplateContent(content));
    }

    public static void addOpenGraphMusicTemplateContent(Bundle params, ShareMessengerOpenGraphMusicTemplateContent content) throws JSONException {
        addOpenGraphMusicTemplateContentForPreview(params, content);
        Utility.putJSONValueInBundle(params, ShareConstants.MESSENGER_PLATFORM_CONTENT, serializeOpenGraphMusicTemplateContent(content));
    }

    public static void addMediaTemplateContent(Bundle params, ShareMessengerMediaTemplateContent content) throws JSONException {
        addMediaTemplateContentForPreview(params, content);
        Utility.putJSONValueInBundle(params, ShareConstants.MESSENGER_PLATFORM_CONTENT, serializeMediaTemplateContent(content));
    }

    private static void addGenericTemplateElementForPreview(Bundle params, ShareMessengerGenericTemplateElement element) throws JSONException {
        if (element.getButton() != null) {
            addActionButton(params, element.getButton(), false);
        } else if (element.getDefaultAction() != null) {
            addActionButton(params, element.getDefaultAction(), true);
        }
        Utility.putUri(params, ShareConstants.IMAGE_URL, element.getImageUrl());
        Utility.putNonEmptyString(params, ShareConstants.PREVIEW_TYPE, PREVIEW_DEFAULT);
        Utility.putNonEmptyString(params, ShareConstants.TITLE, element.getTitle());
        Utility.putNonEmptyString(params, ShareConstants.SUBTITLE, element.getSubtitle());
    }

    private static void addOpenGraphMusicTemplateContentForPreview(Bundle params, ShareMessengerOpenGraphMusicTemplateContent content) throws JSONException {
        addActionButton(params, content.getButton(), false);
        Utility.putNonEmptyString(params, ShareConstants.PREVIEW_TYPE, PREVIEW_OPEN_GRAPH);
        Utility.putUri(params, ShareConstants.OPEN_GRAPH_URL, content.getUrl());
    }

    private static void addMediaTemplateContentForPreview(Bundle params, ShareMessengerMediaTemplateContent content) throws JSONException {
        addActionButton(params, content.getButton(), false);
        Utility.putNonEmptyString(params, ShareConstants.PREVIEW_TYPE, PREVIEW_DEFAULT);
        Utility.putNonEmptyString(params, ShareConstants.ATTACHMENT_ID, content.getAttachmentId());
        if (content.getMediaUrl() != null) {
            Utility.putUri(params, getMediaUrlKey(content.getMediaUrl()), content.getMediaUrl());
        }
        Utility.putNonEmptyString(params, "type", getMediaType(content.getMediaType()));
    }

    private static void addActionButton(Bundle params, ShareMessengerActionButton button, boolean isDefaultAction) throws JSONException {
        if (button != null && (button instanceof ShareMessengerURLActionButton)) {
            addURLActionButton(params, (ShareMessengerURLActionButton) button, isDefaultAction);
        }
    }

    private static void addURLActionButton(Bundle params, ShareMessengerURLActionButton button, boolean isDefaultAction) throws JSONException {
        String actionForDisplay;
        if (isDefaultAction) {
            actionForDisplay = Utility.getUriString(button.getUrl());
        } else {
            actionForDisplay = button.getTitle() + " - " + Utility.getUriString(button.getUrl());
        }
        Utility.putNonEmptyString(params, ShareConstants.TARGET_DISPLAY, actionForDisplay);
        Utility.putUri(params, ShareConstants.ITEM_URL, button.getUrl());
    }

    private static JSONObject serializeGenericTemplateContent(ShareMessengerGenericTemplateContent content) throws JSONException {
        return new JSONObject().put(ATTACHMENT, new JSONObject().put("type", ATTACHMENT_TEMPLATE_TYPE).put(ATTACHMENT_PAYLOAD, new JSONObject().put(TEMPLATE_TYPE, TEMPLATE_GENERIC_TYPE).put(SHARABLE, content.getIsSharable()).put(IMAGE_ASPECT_RATIO, getImageRatioString(content.getImageAspectRatio())).put(ELEMENTS, new JSONArray().put(serializeGenericTemplateElement(content.getGenericTemplateElement())))));
    }

    private static JSONObject serializeOpenGraphMusicTemplateContent(ShareMessengerOpenGraphMusicTemplateContent content) throws JSONException {
        return new JSONObject().put(ATTACHMENT, new JSONObject().put("type", ATTACHMENT_TEMPLATE_TYPE).put(ATTACHMENT_PAYLOAD, new JSONObject().put(TEMPLATE_TYPE, "open_graph").put(ELEMENTS, new JSONArray().put(serializeOpenGraphMusicTemplateElement(content)))));
    }

    private static JSONObject serializeMediaTemplateContent(ShareMessengerMediaTemplateContent content) throws JSONException {
        return new JSONObject().put(ATTACHMENT, new JSONObject().put("type", ATTACHMENT_TEMPLATE_TYPE).put(ATTACHMENT_PAYLOAD, new JSONObject().put(TEMPLATE_TYPE, "media").put(ELEMENTS, new JSONArray().put(serializeMediaTemplateElement(content)))));
    }

    private static JSONObject serializeGenericTemplateElement(ShareMessengerGenericTemplateElement element) throws JSONException {
        JSONObject object = new JSONObject().put("title", element.getTitle()).put(SUBTITLE, element.getSubtitle()).put(IMAGE_URL, Utility.getUriString(element.getImageUrl()));
        if (element.getButton() != null) {
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(serializeActionButton(element.getButton()));
            object.put(BUTTONS, jsonArray);
        }
        if (element.getDefaultAction() != null) {
            object.put(DEFAULT_ACTION, serializeActionButton(element.getDefaultAction(), true));
        }
        return object;
    }

    private static JSONObject serializeOpenGraphMusicTemplateElement(ShareMessengerOpenGraphMusicTemplateContent element) throws JSONException {
        JSONObject object = new JSONObject().put("url", Utility.getUriString(element.getUrl()));
        if (element.getButton() != null) {
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(serializeActionButton(element.getButton()));
            object.put(BUTTONS, jsonArray);
        }
        return object;
    }

    private static JSONObject serializeMediaTemplateElement(ShareMessengerMediaTemplateContent element) throws JSONException {
        JSONObject object = new JSONObject().put(ATTACHMENT_ID, element.getAttachmentId()).put("url", Utility.getUriString(element.getMediaUrl())).put(MEDIA_TYPE, getMediaType(element.getMediaType()));
        if (element.getButton() != null) {
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(serializeActionButton(element.getButton()));
            object.put(BUTTONS, jsonArray);
        }
        return object;
    }

    private static JSONObject serializeActionButton(ShareMessengerActionButton button) throws JSONException {
        return serializeActionButton(button, false);
    }

    private static JSONObject serializeActionButton(ShareMessengerActionButton button, boolean isDefault) throws JSONException {
        if (button instanceof ShareMessengerURLActionButton) {
            return serializeURLActionButton((ShareMessengerURLActionButton) button, isDefault);
        }
        return null;
    }

    private static JSONObject serializeURLActionButton(ShareMessengerURLActionButton button, boolean isDefault) throws JSONException {
        String title;
        JSONObject put = new JSONObject().put("type", BUTTON_URL_TYPE);
        if (isDefault) {
            title = null;
        } else {
            title = button.getTitle();
        }
        return put.put("title", title).put("url", Utility.getUriString(button.getUrl())).put(WEBVIEW_RATIO, getWebviewHeightRatioString(button.getWebviewHeightRatio())).put(MESSENGER_EXTENSIONS, button.getIsMessengerExtensionURL()).put(FALLBACK_URL, Utility.getUriString(button.getFallbackUrl())).put(WEBVIEW_SHARE_BUTTON, getShouldHideShareButton(button));
    }

    private static String getMediaUrlKey(Uri url) {
        String host = url.getHost();
        return (Utility.isNullOrEmpty(host) || !FACEBOOK_DOMAIN.matcher(host).matches()) ? ShareConstants.IMAGE_URL : ShareConstants.MEDIA_URI;
    }

    private static String getWebviewHeightRatioString(ShareMessengerURLActionButton.WebviewHeightRatio webviewHeightRatio) {
        if (webviewHeightRatio == null) {
            return WEBVIEW_RATIO_FULL;
        }
        switch (webviewHeightRatio) {
            case WebviewHeightRatioCompact:
                return WEBVIEW_RATIO_COMPACT;
            case WebviewHeightRatioTall:
                return WEBVIEW_RATIO_TALL;
            default:
                return WEBVIEW_RATIO_FULL;
        }
    }

    private static String getImageRatioString(ShareMessengerGenericTemplateContent.ImageAspectRatio imageAspectRatio) {
        if (imageAspectRatio == null) {
            return IMAGE_RATIO_HORIZONTAL;
        }
        switch (imageAspectRatio) {
            case SQUARE:
                return IMAGE_RATIO_SQUARE;
            default:
                return IMAGE_RATIO_HORIZONTAL;
        }
    }

    private static String getMediaType(ShareMessengerMediaTemplateContent.MediaType mediaType) {
        if (mediaType == null) {
            return "image";
        }
        switch (mediaType) {
            case VIDEO:
                return "video";
            default:
                return "image";
        }
    }

    private static String getShouldHideShareButton(ShareMessengerURLActionButton button) {
        if (button.getShouldHideWebviewShareButton()) {
            return SHARE_BUTTON_HIDE;
        }
        return null;
    }
}
