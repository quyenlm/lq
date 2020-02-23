package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.model.VKAttachments;
import org.json.JSONObject;

public class VKApiAudio extends VKAttachments.VKApiAttachment implements Identifiable, Parcelable {
    public static Parcelable.Creator<VKApiAudio> CREATOR = new Parcelable.Creator<VKApiAudio>() {
        public VKApiAudio createFromParcel(Parcel source) {
            return new VKApiAudio(source);
        }

        public VKApiAudio[] newArray(int size) {
            return new VKApiAudio[size];
        }
    };
    public String access_key;
    public int album_id;
    public String artist;
    public int duration;
    public int genre;
    public int id;
    public int lyrics_id;
    public int owner_id;
    public String title;
    public String url;

    public VKApiAudio(JSONObject from) {
        parse(from);
    }

    public VKApiAudio parse(JSONObject from) {
        this.id = from.optInt("id");
        this.owner_id = from.optInt(VKApiConst.OWNER_ID);
        this.artist = from.optString("artist");
        this.title = from.optString("title");
        this.duration = from.optInt("duration");
        this.url = from.optString("url");
        this.lyrics_id = from.optInt("lyrics_id");
        this.album_id = from.optInt(VKApiConst.ALBUM_ID);
        this.genre = from.optInt("genre_id");
        this.access_key = from.optString("access_key");
        return this;
    }

    public VKApiAudio(Parcel in) {
        this.id = in.readInt();
        this.owner_id = in.readInt();
        this.artist = in.readString();
        this.title = in.readString();
        this.duration = in.readInt();
        this.url = in.readString();
        this.lyrics_id = in.readInt();
        this.album_id = in.readInt();
        this.genre = in.readInt();
        this.access_key = in.readString();
    }

    public VKApiAudio() {
    }

    public int getId() {
        return this.id;
    }

    public CharSequence toAttachmentString() {
        StringBuilder result = new StringBuilder("audio").append(this.owner_id).append('_').append(this.id);
        if (!TextUtils.isEmpty(this.access_key)) {
            result.append('_');
            result.append(this.access_key);
        }
        return result;
    }

    public String getType() {
        return "audio";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.owner_id);
        dest.writeString(this.artist);
        dest.writeString(this.title);
        dest.writeInt(this.duration);
        dest.writeString(this.url);
        dest.writeInt(this.lyrics_id);
        dest.writeInt(this.album_id);
        dest.writeInt(this.genre);
        dest.writeString(this.access_key);
    }

    public static final class Genre {
        public static final int ACOUSTIC_AND_VOCAL = 14;
        public static final int ALTERNATIVE = 21;
        public static final int CHANSON = 12;
        public static final int CLASSICAL = 16;
        public static final int DANCE_AND_HOUSE = 5;
        public static final int DRUM_AND_BASS = 10;
        public static final int DUBSTEP = 8;
        public static final int EASY_LISTENING = 4;
        public static final int ELECTROPOP_AND_DISCO = 22;
        public static final int ETHNIC = 13;
        public static final int INDIE_POP = 17;
        public static final int INSTRUMENTAL = 6;
        public static final int JAZZ_AND_BLUES = 9;
        public static final int METAL = 7;
        public static final int OTHER = 18;
        public static final int POP = 2;
        public static final int RAP_AND_HIPHOP = 3;
        public static final int REGGAE = 15;
        public static final int ROCK = 1;
        public static final int SPEECH = 19;
        public static final int TRANCE = 11;

        private Genre() {
        }
    }
}
