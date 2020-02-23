package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import org.json.JSONObject;

public class VKApiCommunityFull extends VKApiCommunity implements Parcelable {
    public static final String ACTIVITY = "activity";
    public static final String BLACKLISTED = "blacklisted";
    public static final String CAN_POST = "can_post";
    public static final String CAN_SEE_ALL_POSTS = "can_see_all_posts";
    public static final String CITY = "city";
    public static final String CONTACTS = "contacts";
    public static final String COUNTERS = "counters";
    public static final String COUNTRY = "country";
    public static Parcelable.Creator<VKApiCommunityFull> CREATOR = new Parcelable.Creator<VKApiCommunityFull>() {
        public VKApiCommunityFull createFromParcel(Parcel source) {
            return new VKApiCommunityFull(source);
        }

        public VKApiCommunityFull[] newArray(int size) {
            return new VKApiCommunityFull[size];
        }
    };
    public static final String DESCRIPTION = "description";
    public static final String END_DATE = "end_date";
    public static final String FIXED_POST = "fixed_post";
    public static final String LINKS = "links";
    public static final String MEMBERS_COUNT = "members_count";
    public static final String PLACE = "place";
    public static final String SITE = "site";
    public static final String START_DATE = "start_date";
    public static final String STATUS = "status";
    public static final String VERIFIED = "verified";
    public static final String WIKI_PAGE = "wiki_page";
    public boolean blacklisted;
    public boolean can_post;
    public boolean can_see_all_posts;
    public VKApiCity city;
    public VKList<Contact> contacts;
    public Counters counters;
    public VKApiCountry country;
    public String description;
    public long end_date;
    public int fixed_post;
    public VKList<Link> links;
    public int members_count;
    public VKApiPlace place;
    public String site;
    public long start_date;
    public String status;
    public VKApiAudio status_audio;
    public boolean verified;
    public String wiki_page;

    public VKApiCommunityFull() {
    }

    public VKApiCommunityFull parse(JSONObject jo) {
        super.parse(jo);
        JSONObject city2 = jo.optJSONObject("city");
        if (city2 != null) {
            this.city = new VKApiCity().parse(city2);
        }
        JSONObject country2 = jo.optJSONObject("country");
        if (country2 != null) {
            this.country = new VKApiCountry().parse(country2);
        }
        JSONObject place2 = jo.optJSONObject(PLACE);
        if (place2 != null) {
            this.place = new VKApiPlace().parse(place2);
        }
        this.description = jo.optString("description");
        this.wiki_page = jo.optString(WIKI_PAGE);
        this.members_count = jo.optInt(MEMBERS_COUNT);
        JSONObject counters2 = jo.optJSONObject("counters");
        if (counters2 != null) {
            this.counters = new Counters(counters2);
        }
        this.start_date = jo.optLong("start_date");
        this.end_date = jo.optLong("end_date");
        this.can_post = ParseUtils.parseBoolean(jo, "can_post");
        this.can_see_all_posts = ParseUtils.parseBoolean(jo, "can_see_all_posts");
        this.status = jo.optString("status");
        JSONObject status_audio2 = jo.optJSONObject("status_audio");
        if (status_audio2 != null) {
            this.status_audio = new VKApiAudio().parse(status_audio2);
        }
        this.contacts = new VKList<>(jo.optJSONArray("contacts"), Contact.class);
        this.links = new VKList<>(jo.optJSONArray(LINKS), Link.class);
        this.fixed_post = jo.optInt(FIXED_POST);
        this.verified = ParseUtils.parseBoolean(jo, "verified");
        this.blacklisted = ParseUtils.parseBoolean(jo, "verified");
        this.site = jo.optString("site");
        return this;
    }

    public static class Counters implements Parcelable {
        public static Parcelable.Creator<Counters> CREATOR = new Parcelable.Creator<Counters>() {
            public Counters createFromParcel(Parcel source) {
                return new Counters(source);
            }

            public Counters[] newArray(int size) {
                return new Counters[size];
            }
        };
        public static final int NO_COUNTER = -1;
        public int albums;
        public int audios;
        public int docs;
        public int photos;
        public int topics;
        public int videos;

        public Counters(JSONObject from) {
            this.photos = -1;
            this.albums = -1;
            this.audios = -1;
            this.videos = -1;
            this.topics = -1;
            this.docs = -1;
            this.photos = from.optInt("photos", this.photos);
            this.albums = from.optInt("albums", this.albums);
            this.audios = from.optInt("audios", this.audios);
            this.videos = from.optInt("videos", this.videos);
            this.topics = from.optInt("topics", this.topics);
            this.docs = from.optInt("docs", this.docs);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.photos);
            dest.writeInt(this.albums);
            dest.writeInt(this.audios);
            dest.writeInt(this.videos);
            dest.writeInt(this.topics);
            dest.writeInt(this.docs);
        }

        private Counters(Parcel in) {
            this.photos = -1;
            this.albums = -1;
            this.audios = -1;
            this.videos = -1;
            this.topics = -1;
            this.docs = -1;
            this.photos = in.readInt();
            this.albums = in.readInt();
            this.audios = in.readInt();
            this.videos = in.readInt();
            this.topics = in.readInt();
            this.docs = in.readInt();
        }
    }

    public static class Contact extends VKApiModel implements Parcelable, Identifiable {
        public static Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
            public Contact createFromParcel(Parcel source) {
                return new Contact(source);
            }

            public Contact[] newArray(int size) {
                return new Contact[size];
            }
        };
        public String desc;
        public String email;
        public VKApiUser user;
        public int user_id;

        public Contact(JSONObject from) {
            parse(from);
        }

        public Contact parse(JSONObject from) {
            this.user_id = from.optInt("user_id");
            this.desc = from.optString("desc");
            this.email = from.optString("email");
            return this;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.user_id);
            dest.writeString(this.desc);
        }

        private Contact(Parcel in) {
            this.user_id = in.readInt();
            this.desc = in.readString();
        }

        public int getId() {
            return this.user_id;
        }

        public String toString() {
            if (this.user != null) {
                return this.user.toString();
            }
            if (this.email != null) {
                return this.email;
            }
            return null;
        }
    }

    public static class Link extends VKApiModel implements Parcelable, Identifiable {
        public static Parcelable.Creator<Link> CREATOR = new Parcelable.Creator<Link>() {
            public Link createFromParcel(Parcel source) {
                return new Link(source);
            }

            public Link[] newArray(int size) {
                return new Link[size];
            }
        };
        public String desc;
        public String name;
        public VKPhotoSizes photo = new VKPhotoSizes();
        public String url;

        public Link(JSONObject from) {
            parse(from);
        }

        public Link parse(JSONObject from) {
            this.url = from.optString("url");
            this.name = from.optString("name");
            this.desc = from.optString("desc");
            String photo_50 = from.optString(VKApiUser.FIELD_PHOTO_50);
            if (!TextUtils.isEmpty(photo_50)) {
                this.photo.add(VKApiPhotoSize.create(photo_50, 50));
            }
            String photo_100 = from.optString(VKApiUser.FIELD_PHOTO_100);
            if (!TextUtils.isEmpty(photo_100)) {
                this.photo.add(VKApiPhotoSize.create(photo_100, 100));
            }
            this.photo.sort();
            return this;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.url);
            dest.writeString(this.name);
            dest.writeString(this.desc);
            dest.writeParcelable(this.photo, flags);
        }

        public Link(Parcel in) {
            this.url = in.readString();
            this.name = in.readString();
            this.desc = in.readString();
            this.photo = (VKPhotoSizes) in.readParcelable((ClassLoader) null);
        }

        public int getId() {
            return 0;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b;
        byte b2;
        byte b3 = 1;
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.city, flags);
        dest.writeParcelable(this.country, flags);
        dest.writeParcelable(this.status_audio, flags);
        dest.writeParcelable(this.place, flags);
        dest.writeString(this.description);
        dest.writeString(this.wiki_page);
        dest.writeInt(this.members_count);
        dest.writeParcelable(this.counters, flags);
        dest.writeLong(this.start_date);
        dest.writeLong(this.end_date);
        dest.writeByte(this.can_post ? (byte) 1 : 0);
        if (this.can_see_all_posts) {
            b = 1;
        } else {
            b = 0;
        }
        dest.writeByte(b);
        dest.writeString(this.status);
        dest.writeParcelable(this.contacts, flags);
        dest.writeParcelable(this.links, flags);
        dest.writeInt(this.fixed_post);
        if (this.verified) {
            b2 = 1;
        } else {
            b2 = 0;
        }
        dest.writeByte(b2);
        dest.writeString(this.site);
        if (!this.blacklisted) {
            b3 = 0;
        }
        dest.writeByte(b3);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public VKApiCommunityFull(Parcel in) {
        super(in);
        boolean z;
        boolean z2;
        boolean z3 = true;
        this.city = (VKApiCity) in.readParcelable(VKApiCity.class.getClassLoader());
        this.country = (VKApiCountry) in.readParcelable(VKApiCountry.class.getClassLoader());
        this.status_audio = (VKApiAudio) in.readParcelable(VKApiAudio.class.getClassLoader());
        this.place = (VKApiPlace) in.readParcelable(VKApiPlace.class.getClassLoader());
        this.description = in.readString();
        this.wiki_page = in.readString();
        this.members_count = in.readInt();
        this.counters = (Counters) in.readParcelable(Counters.class.getClassLoader());
        this.start_date = in.readLong();
        this.end_date = in.readLong();
        this.can_post = in.readByte() != 0;
        if (in.readByte() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.can_see_all_posts = z;
        this.status = in.readString();
        this.contacts = (VKList) in.readParcelable(VKList.class.getClassLoader());
        this.links = (VKList) in.readParcelable(VKList.class.getClassLoader());
        this.fixed_post = in.readInt();
        if (in.readByte() != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.verified = z2;
        this.site = in.readString();
        this.blacklisted = in.readByte() == 0 ? false : z3;
    }
}
