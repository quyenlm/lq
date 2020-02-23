package com.vk.sdk.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.component.net.download.multiplex.http.ContentType;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.model.VKAttachments;
import org.json.JSONException;
import org.json.JSONObject;

public class VKApiPoll extends VKAttachments.VKApiAttachment implements Parcelable {
    public static Parcelable.Creator<VKApiPoll> CREATOR = new Parcelable.Creator<VKApiPoll>() {
        public VKApiPoll createFromParcel(Parcel source) {
            return new VKApiPoll(source);
        }

        public VKApiPoll[] newArray(int size) {
            return new VKApiPoll[size];
        }
    };
    public int answer_id;
    public VKList<Answer> answers;
    public long created;
    public int id;
    public int owner_id;
    public String question;
    public int votes;

    public VKApiPoll(JSONObject from) throws JSONException {
        parse(from);
    }

    public VKApiPoll parse(JSONObject source) {
        this.id = source.optInt("id");
        this.owner_id = source.optInt(VKApiConst.OWNER_ID);
        this.created = source.optLong(VKAccessToken.CREATED);
        this.question = source.optString("question");
        this.votes = source.optInt("votes");
        this.answer_id = source.optInt("answer_id");
        this.answers = new VKList<>(source.optJSONArray("answers"), Answer.class);
        return this;
    }

    public VKApiPoll(Parcel in) {
        this.id = in.readInt();
        this.owner_id = in.readInt();
        this.created = in.readLong();
        this.question = in.readString();
        this.votes = in.readInt();
        this.answer_id = in.readInt();
        this.answers = (VKList) in.readParcelable(VKList.class.getClassLoader());
    }

    public VKApiPoll() {
    }

    public CharSequence toAttachmentString() {
        return null;
    }

    public String getType() {
        return VKAttachments.TYPE_POLL;
    }

    public int getId() {
        return this.id;
    }

    public static final class Answer extends VKApiModel implements Identifiable, Parcelable {
        public static Parcelable.Creator<Answer> CREATOR = new Parcelable.Creator<Answer>() {
            public Answer createFromParcel(Parcel source) {
                return new Answer(source);
            }

            public Answer[] newArray(int size) {
                return new Answer[size];
            }
        };
        public int id;
        public double rate;
        public String text;
        public int votes;

        public Answer parse(JSONObject source) {
            this.id = source.optInt("id");
            this.text = source.optString(ContentType.TYPE_TEXT);
            this.votes = source.optInt("votes");
            this.rate = source.optDouble("rate");
            return this;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.text);
            dest.writeInt(this.votes);
            dest.writeDouble(this.rate);
        }

        public Answer(Parcel in) {
            this.id = in.readInt();
            this.text = in.readString();
            this.votes = in.readInt();
            this.rate = in.readDouble();
        }

        public int getId() {
            return this.id;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.owner_id);
        dest.writeLong(this.created);
        dest.writeString(this.question);
        dest.writeInt(this.votes);
        dest.writeInt(this.answer_id);
        dest.writeParcelable(this.answers, flags);
    }
}
