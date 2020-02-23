package com.google.android.gms.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.internal.zzcri;
import com.google.android.gms.plus.model.people.Person;
import java.util.List;

public final class zzcrt extends zzc implements Person {
    public zzcrt(DataHolder dataHolder, int i) {
        super(dataHolder, i);
    }

    public final /* synthetic */ Object freeze() {
        return new zzcri(getDisplayName(), getId(), (zzcri.zzc) getImage(), getObjectType(), getUrl());
    }

    public final String getAboutMe() {
        return null;
    }

    public final Person.AgeRange getAgeRange() {
        return null;
    }

    public final String getBirthday() {
        return null;
    }

    public final String getBraggingRights() {
        return null;
    }

    public final int getCircledByCount() {
        return 0;
    }

    public final Person.Cover getCover() {
        return null;
    }

    public final String getCurrentLocation() {
        return null;
    }

    public final String getDisplayName() {
        return getString("displayName");
    }

    public final int getGender() {
        return 0;
    }

    public final String getId() {
        return getString("personId");
    }

    public final Person.Image getImage() {
        return new zzcri.zzc(getString("image"));
    }

    public final String getLanguage() {
        return null;
    }

    public final Person.Name getName() {
        return null;
    }

    public final String getNickname() {
        return null;
    }

    public final int getObjectType() {
        String string = getString("objectType");
        if (string.equals("person")) {
            return 0;
        }
        if (string.equals("page")) {
            return 1;
        }
        String valueOf = String.valueOf(string);
        throw new IllegalArgumentException(valueOf.length() != 0 ? "Unknown objectType string: ".concat(valueOf) : new String("Unknown objectType string: "));
    }

    public final /* bridge */ /* synthetic */ List getOrganizations() {
        return null;
    }

    public final /* bridge */ /* synthetic */ List getPlacesLived() {
        return null;
    }

    public final int getPlusOneCount() {
        return 0;
    }

    public final int getRelationshipStatus() {
        return 0;
    }

    public final String getTagline() {
        return null;
    }

    public final String getUrl() {
        return getString("url");
    }

    public final /* bridge */ /* synthetic */ List getUrls() {
        return null;
    }

    public final boolean hasAboutMe() {
        return false;
    }

    public final boolean hasAgeRange() {
        return false;
    }

    public final boolean hasBirthday() {
        return false;
    }

    public final boolean hasBraggingRights() {
        return false;
    }

    public final boolean hasCircledByCount() {
        return false;
    }

    public final boolean hasCover() {
        return false;
    }

    public final boolean hasCurrentLocation() {
        return false;
    }

    public final boolean hasDisplayName() {
        return true;
    }

    public final boolean hasGender() {
        return false;
    }

    public final boolean hasId() {
        return true;
    }

    public final boolean hasImage() {
        return true;
    }

    public final boolean hasIsPlusUser() {
        return false;
    }

    public final boolean hasLanguage() {
        return false;
    }

    public final boolean hasName() {
        return false;
    }

    public final boolean hasNickname() {
        return false;
    }

    public final boolean hasObjectType() {
        return true;
    }

    public final boolean hasOrganizations() {
        return false;
    }

    public final boolean hasPlacesLived() {
        return false;
    }

    public final boolean hasPlusOneCount() {
        return false;
    }

    public final boolean hasRelationshipStatus() {
        return false;
    }

    public final boolean hasTagline() {
        return false;
    }

    public final boolean hasUrl() {
        return true;
    }

    public final boolean hasUrls() {
        return false;
    }

    public final boolean hasVerified() {
        return false;
    }

    public final boolean isPlusUser() {
        return false;
    }

    public final boolean isVerified() {
        return false;
    }
}
