package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.ieg.ntv.ctrl.chat.ChatMsg;
import com.vk.sdk.api.VKApiConst;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VKApiUserFull extends VKApiUser implements Parcelable {
    public static final String ABOUT = "about";
    public static final String ACTIVITIES = "activities";
    public static final String ACTIVITY = "activity";
    public static final String BDATE = "bdate";
    public static final String BLACKLISTED_BY_ME = "blacklisted_by_me";
    public static final String BOOKS = "books";
    public static final String CAN_POST = "can_post";
    public static final String CAN_SEE_ALL_POSTS = "can_see_all_posts";
    public static final String CAN_WRITE_PRIVATE_MESSAGE = "can_write_private_message";
    public static final String CITY = "city";
    public static final String CONNECTIONS = "connections";
    public static final String CONTACTS = "contacts";
    public static final String COUNTERS = "counters";
    public static final String COUNTRY = "country";
    public static Parcelable.Creator<VKApiUserFull> CREATOR = new Parcelable.Creator<VKApiUserFull>() {
        public VKApiUserFull createFromParcel(Parcel source) {
            return new VKApiUserFull(source);
        }

        public VKApiUserFull[] newArray(int size) {
            return new VKApiUserFull[size];
        }
    };
    public static final String GAMES = "games";
    public static final String INTERESTS = "interests";
    public static final String LAST_SEEN = "last_seen";
    public static final String MOVIES = "movies";
    public static final String OCCUPATION = "occupation";
    public static final String PERSONAL = "personal";
    public static final String QUOTES = "quotes";
    public static final String RELATION = "relation";
    public static final String RELATIVES = "relatives";
    public static final String SCHOOLS = "schools";
    public static final String SCREEN_NAME = "screen_name";
    public static final String SEX = "sex";
    public static final String SITE = "site";
    public static final String TV = "tv";
    public static final String UNIVERSITIES = "universities";
    public static final String VERIFIED = "verified";
    public static final String WALL_DEFAULT = "wall_default";
    public String about;
    public String activities;
    public String activity;
    public int alcohol;
    public String bdate;
    public boolean blacklisted_by_me;
    public String books;
    public boolean can_post;
    public boolean can_see_all_posts;
    public boolean can_write_private_message;
    public VKApiCity city;
    public Counters counters;
    public VKApiCountry country;
    public String facebook;
    public String facebook_name;
    public String games;
    public String home_phone;
    public String inspired_by;
    public String instagram;
    public String interests;
    public boolean is_banned;
    public boolean is_deleted;
    public String[] langs;
    public long last_seen;
    public int life_main;
    public String livejournal;
    public String mobile_phone;
    public String movies;
    public String nickname;
    public Occupation occupation;
    public int people_main;
    public int political;
    public String quotes;
    public int relation;
    public VKList<Relative> relatives;
    public String religion;
    public VKList<VKApiSchool> schools;
    public String screen_name;
    public int sex;
    public String site;
    public String skype;
    public int smoking;
    public VKApiAudio status_audio;
    public String tv;
    public String twitter;
    public VKList<VKApiUniversity> universities;
    public boolean verified;
    public boolean wall_comments;
    public boolean wall_default_owner;

    public VKApiUserFull(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKApiUserFull parse(JSONObject user) {
        JSONArray langs2;
        super.parse(user);
        this.last_seen = ParseUtils.parseLong(user.optJSONObject(LAST_SEEN), "time");
        this.bdate = user.optString(BDATE);
        JSONObject city2 = user.optJSONObject("city");
        if (city2 != null) {
            this.city = new VKApiCity().parse(city2);
        }
        JSONObject country2 = user.optJSONObject("country");
        if (country2 != null) {
            this.country = new VKApiCountry().parse(country2);
        }
        this.universities = new VKList<>(user.optJSONArray(UNIVERSITIES), VKApiUniversity.class);
        this.schools = new VKList<>(user.optJSONArray(SCHOOLS), VKApiSchool.class);
        this.activity = user.optString("activity");
        JSONObject status_audio2 = user.optJSONObject("status_audio");
        if (status_audio2 != null) {
            this.status_audio = new VKApiAudio().parse(status_audio2);
        }
        JSONObject personal = user.optJSONObject(PERSONAL);
        if (personal != null) {
            this.smoking = personal.optInt("smoking");
            this.alcohol = personal.optInt("alcohol");
            this.political = personal.optInt("political");
            this.life_main = personal.optInt("life_main");
            this.people_main = personal.optInt("people_main");
            this.inspired_by = personal.optString("inspired_by");
            this.religion = personal.optString(VKApiConst.RELIGION);
            if (personal.has("langs") && (langs2 = personal.optJSONArray("langs")) != null) {
                this.langs = new String[langs2.length()];
                for (int i = 0; i < langs2.length(); i++) {
                    this.langs[i] = langs2.optString(i);
                }
            }
        }
        this.facebook = user.optString("facebook");
        this.facebook_name = user.optString("facebook_name");
        this.livejournal = user.optString("livejournal");
        this.site = user.optString("site");
        this.screen_name = user.optString(SCREEN_NAME, "id" + this.id);
        this.skype = user.optString("skype");
        this.mobile_phone = user.optString("mobile_phone");
        this.home_phone = user.optString("home_phone");
        this.twitter = user.optString("twitter");
        this.instagram = user.optString("instagram");
        this.about = user.optString("about");
        this.activities = user.optString(ACTIVITIES);
        this.books = user.optString(BOOKS);
        this.games = user.optString(GAMES);
        this.interests = user.optString("interests");
        this.movies = user.optString(MOVIES);
        this.quotes = user.optString(QUOTES);
        this.tv = user.optString(TV);
        this.nickname = user.optString(ChatMsg.KEY_NICK_NAME, (String) null);
        this.can_post = ParseUtils.parseBoolean(user, "can_post");
        this.can_see_all_posts = ParseUtils.parseBoolean(user, "can_see_all_posts");
        this.blacklisted_by_me = ParseUtils.parseBoolean(user, BLACKLISTED_BY_ME);
        this.can_write_private_message = ParseUtils.parseBoolean(user, CAN_WRITE_PRIVATE_MESSAGE);
        this.wall_comments = ParseUtils.parseBoolean(user, WALL_DEFAULT);
        String deactivated = user.optString("deactivated");
        this.is_deleted = "deleted".equals(deactivated);
        this.is_banned = "banned".equals(deactivated);
        this.wall_default_owner = "owner".equals(user.optString(WALL_DEFAULT));
        this.verified = ParseUtils.parseBoolean(user, "verified");
        this.sex = user.optInt("sex");
        JSONObject counters2 = user.optJSONObject("counters");
        if (counters2 != null) {
            this.counters = new Counters(counters2);
        }
        JSONObject occupation2 = user.optJSONObject(OCCUPATION);
        if (occupation2 != null) {
            this.occupation = new Occupation(occupation2);
        }
        this.relation = user.optInt(RELATION);
        if (user.has(RELATIVES)) {
            if (this.relatives == null) {
                this.relatives = new VKList<>();
            }
            this.relatives.fill(user.optJSONArray(RELATIVES), Relative.class);
        }
        return this;
    }

    public static class Relative extends VKApiModel implements Parcelable, Identifiable {
        public static Parcelable.Creator<Relative> CREATOR = new Parcelable.Creator<Relative>() {
            public Relative createFromParcel(Parcel source) {
                return new Relative(source);
            }

            public Relative[] newArray(int size) {
                return new Relative[size];
            }
        };
        public int id;
        public String name;

        public int getId() {
            return this.id;
        }

        public Relative parse(JSONObject response) {
            this.id = response.optInt("id");
            this.name = response.optString("name");
            return this;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
        }

        private Relative(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
        }
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
        public int followers;
        public int friends;
        public int groups;
        public int mutual_friends;
        public int notes;
        public int online_friends;
        public int pages;
        public int photos;
        public int subscriptions;
        public int user_videos;
        public int videos;

        Counters(JSONObject counters) {
            this.albums = -1;
            this.videos = -1;
            this.audios = -1;
            this.notes = -1;
            this.friends = -1;
            this.photos = -1;
            this.groups = -1;
            this.online_friends = -1;
            this.mutual_friends = -1;
            this.user_videos = -1;
            this.followers = -1;
            this.subscriptions = -1;
            this.pages = -1;
            this.albums = counters.optInt("albums", this.albums);
            this.audios = counters.optInt("audios", this.audios);
            this.followers = counters.optInt("followers", this.followers);
            this.photos = counters.optInt("photos", this.photos);
            this.friends = counters.optInt("friends", this.friends);
            this.groups = counters.optInt("groups", this.groups);
            this.mutual_friends = counters.optInt("mutual_friends", this.mutual_friends);
            this.notes = counters.optInt("notes", this.notes);
            this.online_friends = counters.optInt("online_friends", this.online_friends);
            this.user_videos = counters.optInt("user_videos", this.user_videos);
            this.videos = counters.optInt("videos", this.videos);
            this.subscriptions = counters.optInt("subscriptions", this.subscriptions);
            this.pages = counters.optInt("pages", this.pages);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.albums);
            dest.writeInt(this.videos);
            dest.writeInt(this.audios);
            dest.writeInt(this.notes);
            dest.writeInt(this.friends);
            dest.writeInt(this.photos);
            dest.writeInt(this.groups);
            dest.writeInt(this.online_friends);
            dest.writeInt(this.mutual_friends);
            dest.writeInt(this.user_videos);
            dest.writeInt(this.followers);
            dest.writeInt(this.subscriptions);
            dest.writeInt(this.pages);
        }

        private Counters(Parcel in) {
            this.albums = -1;
            this.videos = -1;
            this.audios = -1;
            this.notes = -1;
            this.friends = -1;
            this.photos = -1;
            this.groups = -1;
            this.online_friends = -1;
            this.mutual_friends = -1;
            this.user_videos = -1;
            this.followers = -1;
            this.subscriptions = -1;
            this.pages = -1;
            this.albums = in.readInt();
            this.videos = in.readInt();
            this.audios = in.readInt();
            this.notes = in.readInt();
            this.friends = in.readInt();
            this.photos = in.readInt();
            this.groups = in.readInt();
            this.online_friends = in.readInt();
            this.mutual_friends = in.readInt();
            this.user_videos = in.readInt();
            this.followers = in.readInt();
            this.subscriptions = in.readInt();
            this.pages = in.readInt();
        }
    }

    public static class Occupation implements Parcelable {
        public static Parcelable.Creator<Occupation> CREATOR = new Parcelable.Creator<Occupation>() {
            public Occupation createFromParcel(Parcel source) {
                return new Occupation(source);
            }

            public Occupation[] newArray(int size) {
                return new Occupation[size];
            }
        };
        public static final int NO_COUNTER = -1;
        public int id;
        public String name;
        public String type;

        Occupation(JSONObject occupation) {
            this.id = -1;
            this.type = occupation.optString("type");
            this.id = occupation.optInt("id", this.id);
            this.name = occupation.optString("name");
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.type);
            dest.writeInt(this.id);
            dest.writeString(this.name);
        }

        private Occupation(Parcel in) {
            this.id = -1;
            this.type = in.readString();
            this.id = in.readInt();
            this.name = in.readString();
        }
    }

    public static class Sex {
        public static final int FEMALE = 1;
        public static final int MALE = 2;

        private Sex() {
        }
    }

    public static class Relation {
        public static final int COMPLICATED = 5;
        public static final int ENGAGED = 3;
        public static final int IN_LOVE = 7;
        public static final int MARRIED = 4;
        public static final int RELATIONSHIP = 2;
        public static final int SEARCHING = 6;
        public static final int SINGLE = 1;

        private Relation() {
        }
    }

    public static class Attitude {
        public static final int COMPROMISABLE = 3;
        public static final int NEGATIVE = 2;
        public static final int NEUTRAL = 4;
        public static final int POSITIVE = 5;
        public static final int VERY_NEGATIVE = 1;

        private Attitude() {
        }
    }

    public static class Political {
        public static final int APATHETIC = 9;
        public static final int CENTRIST = 3;
        public static final int COMMUNNIST = 1;
        public static final int CONSERVATIVE = 5;
        public static final int LIBERAL = 4;
        public static final int LIBERTARIAN = 8;
        public static final int MONARCHIST = 6;
        public static final int SOCIALIST = 2;
        public static final int ULTRACONSERVATIVE = 7;

        private Political() {
        }
    }

    public static class LifeMain {
        public static final int BEAUTY_AND_ART = 7;
        public static final int CAREER_AND_MONEY = 2;
        public static final int ENTERTAINMENT_AND_LEISURE = 3;
        public static final int FAME_AND_INFLUENCE = 8;
        public static final int FAMILY_AND_CHILDREN = 1;
        public static final int IMPROOVING_THE_WORLD = 5;
        public static final int PERSONAL_DEVELOPMENT = 6;
        public static final int SCIENCE_AND_RESEARCH = 4;

        private LifeMain() {
        }
    }

    public static class PeopleMain {
        public static final int COURAGE_AND_PERSISTENCE = 5;
        public static final int HEALTH_AND_BEAUTY = 3;
        public static final int HUMOR_AND_LOVE_FOR_LIFE = 6;
        public static final int INTELLECT_AND_CREATIVITY = 1;
        public static final int KINDNESS_AND_HONESTLY = 2;
        public static final int WEALTH_AND_POWER = 4;

        private PeopleMain() {
        }
    }

    public static class RelativeType {
        public static final String CHILD = "child";
        public static final String GRANDCHILD = "grandchild";
        public static final String GRANDPARENT = "grandparent";
        public static final String PARENT = "parent";
        public static final String PARTNER = "partner";
        public static final String SUBLING = "sibling";

        private RelativeType() {
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b;
        byte b2;
        byte b3;
        byte b4;
        byte b5;
        byte b6;
        byte b7;
        byte b8 = 1;
        super.writeToParcel(dest, flags);
        dest.writeString(this.activity);
        dest.writeParcelable(this.status_audio, flags);
        dest.writeString(this.bdate);
        dest.writeParcelable(this.city, flags);
        dest.writeParcelable(this.country, flags);
        dest.writeLong(this.last_seen);
        dest.writeParcelable(this.universities, flags);
        dest.writeParcelable(this.schools, flags);
        dest.writeInt(this.smoking);
        dest.writeInt(this.alcohol);
        dest.writeInt(this.political);
        dest.writeInt(this.life_main);
        dest.writeInt(this.people_main);
        dest.writeString(this.inspired_by);
        dest.writeStringArray(this.langs);
        dest.writeString(this.religion);
        dest.writeString(this.facebook);
        dest.writeString(this.facebook_name);
        dest.writeString(this.livejournal);
        dest.writeString(this.skype);
        dest.writeString(this.site);
        dest.writeString(this.twitter);
        dest.writeString(this.instagram);
        dest.writeString(this.mobile_phone);
        dest.writeString(this.home_phone);
        dest.writeString(this.screen_name);
        dest.writeString(this.activities);
        dest.writeString(this.interests);
        dest.writeString(this.movies);
        dest.writeString(this.tv);
        dest.writeString(this.books);
        dest.writeString(this.games);
        dest.writeString(this.about);
        dest.writeString(this.quotes);
        dest.writeByte(this.can_post ? (byte) 1 : 0);
        if (this.can_see_all_posts) {
            b = 1;
        } else {
            b = 0;
        }
        dest.writeByte(b);
        if (this.can_write_private_message) {
            b2 = 1;
        } else {
            b2 = 0;
        }
        dest.writeByte(b2);
        if (this.wall_comments) {
            b3 = 1;
        } else {
            b3 = 0;
        }
        dest.writeByte(b3);
        if (this.is_banned) {
            b4 = 1;
        } else {
            b4 = 0;
        }
        dest.writeByte(b4);
        if (this.is_deleted) {
            b5 = 1;
        } else {
            b5 = 0;
        }
        dest.writeByte(b5);
        if (this.wall_default_owner) {
            b6 = 1;
        } else {
            b6 = 0;
        }
        dest.writeByte(b6);
        if (this.verified) {
            b7 = 1;
        } else {
            b7 = 0;
        }
        dest.writeByte(b7);
        dest.writeInt(this.sex);
        dest.writeParcelable(this.counters, flags);
        dest.writeParcelable(this.occupation, flags);
        dest.writeInt(this.relation);
        dest.writeParcelable(this.relatives, flags);
        if (!this.blacklisted_by_me) {
            b8 = 0;
        }
        dest.writeByte(b8);
    }

    public VKApiUserFull() {
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public VKApiUserFull(Parcel in) {
        super(in);
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8 = true;
        this.activity = in.readString();
        this.status_audio = (VKApiAudio) in.readParcelable(VKApiAudio.class.getClassLoader());
        this.bdate = in.readString();
        this.city = (VKApiCity) in.readParcelable(VKApiCity.class.getClassLoader());
        this.country = (VKApiCountry) in.readParcelable(VKApiCountry.class.getClassLoader());
        this.last_seen = in.readLong();
        this.universities = (VKList) in.readParcelable(VKList.class.getClassLoader());
        this.schools = (VKList) in.readParcelable(VKList.class.getClassLoader());
        this.smoking = in.readInt();
        this.alcohol = in.readInt();
        this.political = in.readInt();
        this.life_main = in.readInt();
        this.people_main = in.readInt();
        this.inspired_by = in.readString();
        this.langs = in.createStringArray();
        this.religion = in.readString();
        this.facebook = in.readString();
        this.facebook_name = in.readString();
        this.livejournal = in.readString();
        this.skype = in.readString();
        this.site = in.readString();
        this.twitter = in.readString();
        this.instagram = in.readString();
        this.mobile_phone = in.readString();
        this.home_phone = in.readString();
        this.screen_name = in.readString();
        this.activities = in.readString();
        this.interests = in.readString();
        this.movies = in.readString();
        this.tv = in.readString();
        this.books = in.readString();
        this.games = in.readString();
        this.about = in.readString();
        this.quotes = in.readString();
        this.can_post = in.readByte() != 0;
        if (in.readByte() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.can_see_all_posts = z;
        if (in.readByte() != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.can_write_private_message = z2;
        if (in.readByte() != 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        this.wall_comments = z3;
        if (in.readByte() != 0) {
            z4 = true;
        } else {
            z4 = false;
        }
        this.is_banned = z4;
        if (in.readByte() != 0) {
            z5 = true;
        } else {
            z5 = false;
        }
        this.is_deleted = z5;
        if (in.readByte() != 0) {
            z6 = true;
        } else {
            z6 = false;
        }
        this.wall_default_owner = z6;
        if (in.readByte() != 0) {
            z7 = true;
        } else {
            z7 = false;
        }
        this.verified = z7;
        this.sex = in.readInt();
        this.counters = (Counters) in.readParcelable(Counters.class.getClassLoader());
        this.occupation = (Occupation) in.readParcelable(Occupation.class.getClassLoader());
        this.relation = in.readInt();
        this.relatives = (VKList) in.readParcelable(VKList.class.getClassLoader());
        this.blacklisted_by_me = in.readByte() == 0 ? false : z8;
    }
}
