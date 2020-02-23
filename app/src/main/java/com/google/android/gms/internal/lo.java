package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbo;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.GithubAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.vk.sdk.api.model.VKApiUserFull;
import java.util.Map;

public final class lo implements AdditionalUserInfo {
    private final String zzbVY;
    private final Map<String, Object> zzbXE;

    public lo(@NonNull String str, @NonNull String str2) {
        zzbo.zzcF(str);
        zzbo.zzcF(str2);
        this.zzbVY = str;
        this.zzbXE = lx.zzgx(str2);
    }

    public final Map<String, Object> getProfile() {
        return this.zzbXE;
    }

    public final String getProviderId() {
        return this.zzbVY;
    }

    public final String getUsername() {
        if (GithubAuthProvider.PROVIDER_ID.equals(this.zzbVY)) {
            return (String) this.zzbXE.get(FirebaseAnalytics.Event.LOGIN);
        }
        if (TwitterAuthProvider.PROVIDER_ID.equals(this.zzbVY)) {
            return (String) this.zzbXE.get(VKApiUserFull.SCREEN_NAME);
        }
        return null;
    }
}
