package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.util.VKStringJoiner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class VKAttachments extends VKList<VKApiAttachment> implements Parcelable {
    public static Parcelable.Creator<VKAttachments> CREATOR = new Parcelable.Creator<VKAttachments>() {
        public VKAttachments createFromParcel(Parcel source) {
            return new VKAttachments(source);
        }

        public VKAttachments[] newArray(int size) {
            return new VKAttachments[size];
        }
    };
    public static final String TYPE_ALBUM = "album";
    public static final String TYPE_APP = "app";
    public static final String TYPE_AUDIO = "audio";
    public static final String TYPE_DOC = "doc";
    public static final String TYPE_LINK = "link";
    public static final String TYPE_NOTE = "note";
    public static final String TYPE_PHOTO = "photo";
    public static final String TYPE_POLL = "poll";
    public static final String TYPE_POST = "wall";
    public static final String TYPE_POSTED_PHOTO = "posted_photo";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_WIKI_PAGE = "page";
    private final VKList.Parser<VKApiAttachment> parser = new VKList.Parser<VKApiAttachment>() {
        public VKApiAttachment parseObject(JSONObject attachment) throws Exception {
            String type = attachment.optString("type");
            if ("photo".equals(type)) {
                return new VKApiPhoto().parse(attachment.getJSONObject("photo"));
            }
            if ("video".equals(type)) {
                return new VKApiVideo().parse(attachment.getJSONObject("video"));
            }
            if ("audio".equals(type)) {
                return new VKApiAudio().parse(attachment.getJSONObject("audio"));
            }
            if (VKAttachments.TYPE_DOC.equals(type)) {
                return new VKApiDocument().parse(attachment.getJSONObject(VKAttachments.TYPE_DOC));
            }
            if ("wall".equals(type)) {
                return new VKApiPost().parse(attachment.getJSONObject("wall"));
            }
            if (VKAttachments.TYPE_POSTED_PHOTO.equals(type)) {
                return new VKApiPostedPhoto().parse(attachment.getJSONObject(VKAttachments.TYPE_POSTED_PHOTO));
            }
            if ("link".equals(type)) {
                return new VKApiLink().parse(attachment.getJSONObject("link"));
            }
            if (VKAttachments.TYPE_NOTE.equals(type)) {
                return new VKApiNote().parse(attachment.getJSONObject(VKAttachments.TYPE_NOTE));
            }
            if (VKAttachments.TYPE_APP.equals(type)) {
                return new VKApiApplicationContent().parse(attachment.getJSONObject(VKAttachments.TYPE_APP));
            }
            if (VKAttachments.TYPE_POLL.equals(type)) {
                return new VKApiPoll().parse(attachment.getJSONObject(VKAttachments.TYPE_POLL));
            }
            if ("page".equals(type)) {
                return new VKApiWikiPage().parse(attachment.getJSONObject("page"));
            }
            if (VKAttachments.TYPE_ALBUM.equals(type)) {
                return new VKApiPhotoAlbum().parse(attachment.getJSONObject(VKAttachments.TYPE_ALBUM));
            }
            return null;
        }
    };

    public static abstract class VKApiAttachment extends VKApiModel implements Identifiable {
        public abstract String getType();

        public abstract CharSequence toAttachmentString();
    }

    public VKAttachments() {
    }

    public VKAttachments(VKApiAttachment... data) {
        super(Arrays.asList(data));
    }

    public VKAttachments(List<? extends VKApiAttachment> data) {
        super(data);
    }

    public VKAttachments(JSONObject from) {
        fill(from);
    }

    public VKAttachments(JSONArray from) {
        fill(from);
    }

    public void fill(JSONObject from) {
        super.fill(from, this.parser);
    }

    public void fill(JSONArray from) {
        super.fill(from, this.parser);
    }

    public String toAttachmentsString() {
        ArrayList<CharSequence> attachments = new ArrayList<>();
        Iterator it = iterator();
        while (it.hasNext()) {
            attachments.add(((VKApiAttachment) it.next()).toAttachmentString());
        }
        return VKStringJoiner.join((Collection<?>) attachments, ",");
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(size());
        Iterator it = iterator();
        while (it.hasNext()) {
            VKApiAttachment attachment = (VKApiAttachment) it.next();
            dest.writeString(attachment.getType());
            dest.writeParcelable(attachment, 0);
        }
    }

    public VKAttachments(Parcel parcel) {
        int size = parcel.readInt();
        for (int i = 0; i < size; i++) {
            String type = parcel.readString();
            if ("photo".equals(type)) {
                add((VKApiAttachment) parcel.readParcelable(VKApiPhoto.class.getClassLoader()));
            } else if ("video".equals(type)) {
                add((VKApiAttachment) parcel.readParcelable(VKApiVideo.class.getClassLoader()));
            } else if ("audio".equals(type)) {
                add((VKApiAttachment) parcel.readParcelable(VKApiAudio.class.getClassLoader()));
            } else if (TYPE_DOC.equals(type)) {
                add((VKApiAttachment) parcel.readParcelable(VKApiDocument.class.getClassLoader()));
            } else if ("wall".equals(type)) {
                add((VKApiAttachment) parcel.readParcelable(VKApiPost.class.getClassLoader()));
            } else if (TYPE_POSTED_PHOTO.equals(type)) {
                add((VKApiAttachment) parcel.readParcelable(VKApiPostedPhoto.class.getClassLoader()));
            } else if ("link".equals(type)) {
                add((VKApiAttachment) parcel.readParcelable(VKApiLink.class.getClassLoader()));
            } else if (TYPE_NOTE.equals(type)) {
                add((VKApiAttachment) parcel.readParcelable(VKApiNote.class.getClassLoader()));
            } else if (TYPE_APP.equals(type)) {
                add((VKApiAttachment) parcel.readParcelable(VKApiApplicationContent.class.getClassLoader()));
            } else if (TYPE_POLL.equals(type)) {
                add((VKApiAttachment) parcel.readParcelable(VKApiPoll.class.getClassLoader()));
            } else if ("page".equals(type)) {
                add((VKApiAttachment) parcel.readParcelable(VKApiWikiPage.class.getClassLoader()));
            } else if (TYPE_ALBUM.equals(type)) {
                add((VKApiAttachment) parcel.readParcelable(VKApiPhotoAlbum.class.getClassLoader()));
            }
        }
    }
}
