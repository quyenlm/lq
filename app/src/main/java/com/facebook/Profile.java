package com.facebook;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import org.json.JSONException;
import org.json.JSONObject;

public final class Profile implements Parcelable {
    public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() {
        public Profile createFromParcel(Parcel source) {
            return new Profile(source);
        }

        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };
    private static final String FIRST_NAME_KEY = "first_name";
    private static final String ID_KEY = "id";
    private static final String LAST_NAME_KEY = "last_name";
    private static final String LINK_URI_KEY = "link_uri";
    private static final String MIDDLE_NAME_KEY = "middle_name";
    private static final String NAME_KEY = "name";
    /* access modifiers changed from: private */
    public static final String TAG = Profile.class.getSimpleName();
    @Nullable
    private final String firstName;
    @Nullable
    private final String id;
    @Nullable
    private final String lastName;
    @Nullable
    private final Uri linkUri;
    @Nullable
    private final String middleName;
    @Nullable
    private final String name;

    public static Profile getCurrentProfile() {
        return ProfileManager.getInstance().getCurrentProfile();
    }

    public static void setCurrentProfile(@Nullable Profile profile) {
        ProfileManager.getInstance().setCurrentProfile(profile);
    }

    public static void fetchProfileForCurrentAccessToken() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (!AccessToken.isCurrentAccessTokenActive()) {
            setCurrentProfile((Profile) null);
        } else {
            Utility.getGraphMeRequestWithCacheAsync(accessToken.getToken(), new Utility.GraphMeRequestWithCacheCallback() {
                public void onSuccess(JSONObject userInfo) {
                    String id = userInfo.optString("id");
                    if (id != null) {
                        String link = userInfo.optString("link");
                        Profile.setCurrentProfile(new Profile(id, userInfo.optString(Profile.FIRST_NAME_KEY), userInfo.optString(Profile.MIDDLE_NAME_KEY), userInfo.optString(Profile.LAST_NAME_KEY), userInfo.optString("name"), link != null ? Uri.parse(link) : null));
                    }
                }

                public void onFailure(FacebookException error) {
                    Log.e(Profile.TAG, "Got unexpected exception: " + error);
                }
            });
        }
    }

    public Profile(String id2, @Nullable String firstName2, @Nullable String middleName2, @Nullable String lastName2, @Nullable String name2, @Nullable Uri linkUri2) {
        Validate.notNullOrEmpty(id2, "id");
        this.id = id2;
        this.firstName = firstName2;
        this.middleName = middleName2;
        this.lastName = lastName2;
        this.name = name2;
        this.linkUri = linkUri2;
    }

    public Uri getProfilePictureUri(int width, int height) {
        return ImageRequest.getProfilePictureUri(this.id, width, height);
    }

    public String getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getName() {
        return this.name;
    }

    public Uri getLinkUri() {
        return this.linkUri;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Profile)) {
            return false;
        }
        Profile o = (Profile) other;
        if (!this.id.equals(o.id) || this.firstName != null) {
            if (!this.firstName.equals(o.firstName) || this.middleName != null) {
                if (!this.middleName.equals(o.middleName) || this.lastName != null) {
                    if (!this.lastName.equals(o.lastName) || this.name != null) {
                        if (!this.name.equals(o.name) || this.linkUri != null) {
                            return this.linkUri.equals(o.linkUri);
                        }
                        if (o.linkUri != null) {
                            return false;
                        }
                        return true;
                    } else if (o.name != null) {
                        return false;
                    } else {
                        return true;
                    }
                } else if (o.lastName != null) {
                    return false;
                } else {
                    return true;
                }
            } else if (o.middleName != null) {
                return false;
            } else {
                return true;
            }
        } else if (o.firstName != null) {
            return false;
        } else {
            return true;
        }
    }

    public int hashCode() {
        int result = this.id.hashCode() + 527;
        if (this.firstName != null) {
            result = (result * 31) + this.firstName.hashCode();
        }
        if (this.middleName != null) {
            result = (result * 31) + this.middleName.hashCode();
        }
        if (this.lastName != null) {
            result = (result * 31) + this.lastName.hashCode();
        }
        if (this.name != null) {
            result = (result * 31) + this.name.hashCode();
        }
        if (this.linkUri != null) {
            return (result * 31) + this.linkUri.hashCode();
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", this.id);
            jsonObject.put(FIRST_NAME_KEY, this.firstName);
            jsonObject.put(MIDDLE_NAME_KEY, this.middleName);
            jsonObject.put(LAST_NAME_KEY, this.lastName);
            jsonObject.put("name", this.name);
            if (this.linkUri == null) {
                return jsonObject;
            }
            jsonObject.put(LINK_URI_KEY, this.linkUri.toString());
            return jsonObject;
        } catch (JSONException e) {
            return null;
        }
    }

    Profile(JSONObject jsonObject) {
        Uri uri = null;
        this.id = jsonObject.optString("id", (String) null);
        this.firstName = jsonObject.optString(FIRST_NAME_KEY, (String) null);
        this.middleName = jsonObject.optString(MIDDLE_NAME_KEY, (String) null);
        this.lastName = jsonObject.optString(LAST_NAME_KEY, (String) null);
        this.name = jsonObject.optString("name", (String) null);
        String linkUriString = jsonObject.optString(LINK_URI_KEY, (String) null);
        this.linkUri = linkUriString != null ? Uri.parse(linkUriString) : uri;
    }

    private Profile(Parcel source) {
        this.id = source.readString();
        this.firstName = source.readString();
        this.middleName = source.readString();
        this.lastName = source.readString();
        this.name = source.readString();
        String linkUriString = source.readString();
        this.linkUri = linkUriString == null ? null : Uri.parse(linkUriString);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.middleName);
        dest.writeString(this.lastName);
        dest.writeString(this.name);
        dest.writeString(this.linkUri == null ? null : this.linkUri.toString());
    }
}
