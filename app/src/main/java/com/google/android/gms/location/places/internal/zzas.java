package com.google.android.gms.location.places.internal;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.internal.PlaceEntity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.vk.sdk.api.VKApiConst;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class zzas extends zzav implements Place {
    private final String zzbjI = zzD(VKApiConst.PLACE_ID, "");
    private final zzae zzbkD;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzas(DataHolder dataHolder, int i) {
        super(dataHolder, i);
        zzae zzae;
        String str = null;
        if (getPlaceTypes().size() > 0 || (getPhoneNumber() != null && getPhoneNumber().length() > 0) || ((getWebsiteUri() != null && !getWebsiteUri().equals(Uri.EMPTY)) || getRating() >= 0.0f || getPriceLevel() >= 0)) {
            zzae = new zzae(getPlaceTypes(), getPhoneNumber() != null ? getPhoneNumber().toString() : str, getWebsiteUri(), getRating(), getPriceLevel());
        } else {
            zzae = null;
        }
        this.zzbkD = zzae;
    }

    private final List<String> zzwa() {
        return zzb("place_attributions", (List<String>) Collections.emptyList());
    }

    public final /* synthetic */ Object freeze() {
        PlaceEntity zzvZ = new PlaceEntity.zza().zzdz(getAddress().toString()).zzE(zzwa()).zzdx(getId()).zzaj((!zzcv("place_is_permanently_closed") || zzcx("place_is_permanently_closed")) ? false : getBoolean("place_is_permanently_closed")).zza(getLatLng()).zzc(zza("place_level_number", 0.0f)).zzdy(getName().toString()).zzdA(getPhoneNumber().toString()).zzbm(getPriceLevel()).zzd(getRating()).zzD(getPlaceTypes()).zza(getViewport()).zzp(getWebsiteUri()).zza((zzal) zza("place_opening_hours", zzal.CREATOR)).zza(this.zzbkD).zzvZ();
        zzvZ.setLocale(getLocale());
        return zzvZ;
    }

    public final CharSequence getAddress() {
        return zzD("place_address", "");
    }

    public final CharSequence getAttributions() {
        return zzg.zzi(zzwa());
    }

    public final String getId() {
        return this.zzbjI;
    }

    public final LatLng getLatLng() {
        return (LatLng) zza("place_lat_lng", LatLng.CREATOR);
    }

    public final Locale getLocale() {
        String zzD = zzD("place_locale_language", "");
        if (!TextUtils.isEmpty(zzD)) {
            return new Locale(zzD, zzD("place_locale_country", ""));
        }
        String zzD2 = zzD("place_locale", "");
        return !TextUtils.isEmpty(zzD2) ? new Locale(zzD2) : Locale.getDefault();
    }

    public final CharSequence getName() {
        return zzD("place_name", "");
    }

    public final CharSequence getPhoneNumber() {
        return zzD("place_phone_number", "");
    }

    public final List<Integer> getPlaceTypes() {
        return zza("place_types", (List<Integer>) Collections.emptyList());
    }

    public final int getPriceLevel() {
        return zzu("place_price_level", -1);
    }

    public final float getRating() {
        return zza("place_rating", -1.0f);
    }

    public final LatLngBounds getViewport() {
        return (LatLngBounds) zza("place_viewport", LatLngBounds.CREATOR);
    }

    public final Uri getWebsiteUri() {
        String zzD = zzD("place_website_uri", (String) null);
        if (zzD == null) {
            return null;
        }
        return Uri.parse(zzD);
    }
}
