package com.facebook.login;

import com.facebook.AccessToken;
import java.util.Set;

public class LoginResult {
    private final AccessToken accessToken;
    private final Set<String> recentlyDeniedPermissions;
    private final Set<String> recentlyGrantedPermissions;

    public LoginResult(AccessToken accessToken2, Set<String> recentlyGrantedPermissions2, Set<String> recentlyDeniedPermissions2) {
        this.accessToken = accessToken2;
        this.recentlyGrantedPermissions = recentlyGrantedPermissions2;
        this.recentlyDeniedPermissions = recentlyDeniedPermissions2;
    }

    public AccessToken getAccessToken() {
        return this.accessToken;
    }

    public Set<String> getRecentlyGrantedPermissions() {
        return this.recentlyGrantedPermissions;
    }

    public Set<String> getRecentlyDeniedPermissions() {
        return this.recentlyDeniedPermissions;
    }
}
