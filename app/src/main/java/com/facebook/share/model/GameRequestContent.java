package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.Arrays;
import java.util.List;

public final class GameRequestContent implements ShareModel {
    public static final Parcelable.Creator<GameRequestContent> CREATOR = new Parcelable.Creator<GameRequestContent>() {
        public GameRequestContent createFromParcel(Parcel in) {
            return new GameRequestContent(in);
        }

        public GameRequestContent[] newArray(int size) {
            return new GameRequestContent[size];
        }
    };
    private final ActionType actionType;
    private final String data;
    private final Filters filters;
    private final String message;
    private final String objectId;
    private final List<String> recipients;
    private final List<String> suggestions;
    private final String title;

    public enum ActionType {
        SEND,
        ASKFOR,
        TURN
    }

    public enum Filters {
        APP_USERS,
        APP_NON_USERS
    }

    private GameRequestContent(Builder builder) {
        this.message = builder.message;
        this.recipients = builder.recipients;
        this.title = builder.title;
        this.data = builder.data;
        this.actionType = builder.actionType;
        this.objectId = builder.objectId;
        this.filters = builder.filters;
        this.suggestions = builder.suggestions;
    }

    GameRequestContent(Parcel in) {
        this.message = in.readString();
        this.recipients = in.createStringArrayList();
        this.title = in.readString();
        this.data = in.readString();
        this.actionType = (ActionType) in.readSerializable();
        this.objectId = in.readString();
        this.filters = (Filters) in.readSerializable();
        this.suggestions = in.createStringArrayList();
        in.readStringList(this.suggestions);
    }

    public String getMessage() {
        return this.message;
    }

    public String getTo() {
        if (getRecipients() != null) {
            return TextUtils.join(",", getRecipients());
        }
        return null;
    }

    public List<String> getRecipients() {
        return this.recipients;
    }

    public String getTitle() {
        return this.title;
    }

    public String getData() {
        return this.data;
    }

    public ActionType getActionType() {
        return this.actionType;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public Filters getFilters() {
        return this.filters;
    }

    public List<String> getSuggestions() {
        return this.suggestions;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.message);
        out.writeStringList(this.recipients);
        out.writeString(this.title);
        out.writeString(this.data);
        out.writeSerializable(this.actionType);
        out.writeString(this.objectId);
        out.writeSerializable(this.filters);
        out.writeStringList(this.suggestions);
    }

    public static class Builder implements ShareModelBuilder<GameRequestContent, Builder> {
        /* access modifiers changed from: private */
        public ActionType actionType;
        /* access modifiers changed from: private */
        public String data;
        /* access modifiers changed from: private */
        public Filters filters;
        /* access modifiers changed from: private */
        public String message;
        /* access modifiers changed from: private */
        public String objectId;
        /* access modifiers changed from: private */
        public List<String> recipients;
        /* access modifiers changed from: private */
        public List<String> suggestions;
        /* access modifiers changed from: private */
        public String title;

        public Builder setMessage(String message2) {
            this.message = message2;
            return this;
        }

        public Builder setTo(String to) {
            if (to != null) {
                this.recipients = Arrays.asList(to.split(","));
            }
            return this;
        }

        public Builder setRecipients(List<String> recipients2) {
            this.recipients = recipients2;
            return this;
        }

        public Builder setData(String data2) {
            this.data = data2;
            return this;
        }

        public Builder setTitle(String title2) {
            this.title = title2;
            return this;
        }

        public Builder setActionType(ActionType actionType2) {
            this.actionType = actionType2;
            return this;
        }

        public Builder setObjectId(String objectId2) {
            this.objectId = objectId2;
            return this;
        }

        public Builder setFilters(Filters filters2) {
            this.filters = filters2;
            return this;
        }

        public Builder setSuggestions(List<String> suggestions2) {
            this.suggestions = suggestions2;
            return this;
        }

        public GameRequestContent build() {
            return new GameRequestContent(this);
        }

        /* Debug info: failed to restart local var, previous not found, register: 2 */
        public Builder readFrom(GameRequestContent content) {
            if (content == null) {
                return this;
            }
            return setMessage(content.getMessage()).setRecipients(content.getRecipients()).setTitle(content.getTitle()).setData(content.getData()).setActionType(content.getActionType()).setObjectId(content.getObjectId()).setFilters(content.getFilters()).setSuggestions(content.getSuggestions());
        }

        /* access modifiers changed from: package-private */
        public Builder readFrom(Parcel parcel) {
            return readFrom((GameRequestContent) parcel.readParcelable(GameRequestContent.class.getClassLoader()));
        }
    }
}
