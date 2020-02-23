package com.beetalk.sdk.facebook;

import com.facebook.Profile;

public class FBUserInfo {
    private Profile fbUser;

    public FBUserInfo(Profile user) {
        this.fbUser = user;
    }

    public String getLastName() {
        return this.fbUser.getLastName();
    }

    public String getFirstName() {
        return this.fbUser.getFirstName();
    }

    public String getId() {
        return this.fbUser.getId();
    }

    public String getName() {
        return this.fbUser.getName();
    }

    public String getMiddleName() {
        return this.fbUser.getMiddleName();
    }

    public String getLink() {
        return this.fbUser.getLinkUri().toString();
    }
}
