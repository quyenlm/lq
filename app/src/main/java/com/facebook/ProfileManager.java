package com.facebook;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;

public final class ProfileManager {
    public static final String ACTION_CURRENT_PROFILE_CHANGED = "com.facebook.sdk.ACTION_CURRENT_PROFILE_CHANGED";
    public static final String EXTRA_NEW_PROFILE = "com.facebook.sdk.EXTRA_NEW_PROFILE";
    public static final String EXTRA_OLD_PROFILE = "com.facebook.sdk.EXTRA_OLD_PROFILE";
    private static volatile ProfileManager instance;
    private Profile currentProfile;
    private final LocalBroadcastManager localBroadcastManager;
    private final ProfileCache profileCache;

    ProfileManager(LocalBroadcastManager localBroadcastManager2, ProfileCache profileCache2) {
        Validate.notNull(localBroadcastManager2, "localBroadcastManager");
        Validate.notNull(profileCache2, "profileCache");
        this.localBroadcastManager = localBroadcastManager2;
        this.profileCache = profileCache2;
    }

    static ProfileManager getInstance() {
        if (instance == null) {
            synchronized (ProfileManager.class) {
                if (instance == null) {
                    instance = new ProfileManager(LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext()), new ProfileCache());
                }
            }
        }
        return instance;
    }

    /* access modifiers changed from: package-private */
    public Profile getCurrentProfile() {
        return this.currentProfile;
    }

    /* access modifiers changed from: package-private */
    public boolean loadCurrentProfile() {
        Profile profile = this.profileCache.load();
        if (profile == null) {
            return false;
        }
        setCurrentProfile(profile, false);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void setCurrentProfile(@Nullable Profile currentProfile2) {
        setCurrentProfile(currentProfile2, true);
    }

    private void setCurrentProfile(@Nullable Profile currentProfile2, boolean writeToCache) {
        Profile oldProfile = this.currentProfile;
        this.currentProfile = currentProfile2;
        if (writeToCache) {
            if (currentProfile2 != null) {
                this.profileCache.save(currentProfile2);
            } else {
                this.profileCache.clear();
            }
        }
        if (!Utility.areObjectsEqual(oldProfile, currentProfile2)) {
            sendCurrentProfileChangedBroadcast(oldProfile, currentProfile2);
        }
    }

    private void sendCurrentProfileChangedBroadcast(Profile oldProfile, Profile currentProfile2) {
        Intent intent = new Intent(ACTION_CURRENT_PROFILE_CHANGED);
        intent.putExtra(EXTRA_OLD_PROFILE, oldProfile);
        intent.putExtra(EXTRA_NEW_PROFILE, currentProfile2);
        this.localBroadcastManager.sendBroadcast(intent);
    }
}
