package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareContent.Builder;
import com.facebook.share.model.ShareHashtag;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ShareContent<P extends ShareContent, E extends Builder> implements ShareModel {
    private final Uri contentUrl;
    private final ShareHashtag hashtag;
    private final String pageId;
    private final List<String> peopleIds;
    private final String placeId;
    private final String ref;

    protected ShareContent(Builder builder) {
        this.contentUrl = builder.contentUrl;
        this.peopleIds = builder.peopleIds;
        this.placeId = builder.placeId;
        this.pageId = builder.pageId;
        this.ref = builder.ref;
        this.hashtag = builder.hashtag;
    }

    protected ShareContent(Parcel in) {
        this.contentUrl = (Uri) in.readParcelable(Uri.class.getClassLoader());
        this.peopleIds = readUnmodifiableStringList(in);
        this.placeId = in.readString();
        this.pageId = in.readString();
        this.ref = in.readString();
        this.hashtag = new ShareHashtag.Builder().readFrom(in).build();
    }

    @Nullable
    public Uri getContentUrl() {
        return this.contentUrl;
    }

    @Nullable
    public List<String> getPeopleIds() {
        return this.peopleIds;
    }

    @Nullable
    public String getPlaceId() {
        return this.placeId;
    }

    @Nullable
    public String getPageId() {
        return this.pageId;
    }

    @Nullable
    public String getRef() {
        return this.ref;
    }

    @Nullable
    public ShareHashtag getShareHashtag() {
        return this.hashtag;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(this.contentUrl, 0);
        out.writeStringList(this.peopleIds);
        out.writeString(this.placeId);
        out.writeString(this.pageId);
        out.writeString(this.ref);
        out.writeParcelable(this.hashtag, 0);
    }

    private List<String> readUnmodifiableStringList(Parcel in) {
        List<String> list = new ArrayList<>();
        in.readStringList(list);
        if (list.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }

    public static abstract class Builder<P extends ShareContent, E extends Builder> implements ShareModelBuilder<P, E> {
        /* access modifiers changed from: private */
        public Uri contentUrl;
        /* access modifiers changed from: private */
        public ShareHashtag hashtag;
        /* access modifiers changed from: private */
        public String pageId;
        /* access modifiers changed from: private */
        public List<String> peopleIds;
        /* access modifiers changed from: private */
        public String placeId;
        /* access modifiers changed from: private */
        public String ref;

        public E setContentUrl(@Nullable Uri contentUrl2) {
            this.contentUrl = contentUrl2;
            return this;
        }

        public E setPeopleIds(@Nullable List<String> peopleIds2) {
            this.peopleIds = peopleIds2 == null ? null : Collections.unmodifiableList(peopleIds2);
            return this;
        }

        public E setPlaceId(@Nullable String placeId2) {
            this.placeId = placeId2;
            return this;
        }

        public E setPageId(@Nullable String pageId2) {
            this.pageId = pageId2;
            return this;
        }

        public E setRef(@Nullable String ref2) {
            this.ref = ref2;
            return this;
        }

        public E setShareHashtag(@Nullable ShareHashtag shareHashtag) {
            this.hashtag = shareHashtag;
            return this;
        }

        public E readFrom(P content) {
            if (content == null) {
                return this;
            }
            return setContentUrl(content.getContentUrl()).setPeopleIds(content.getPeopleIds()).setPlaceId(content.getPlaceId()).setPageId(content.getPageId()).setRef(content.getRef());
        }
    }
}
