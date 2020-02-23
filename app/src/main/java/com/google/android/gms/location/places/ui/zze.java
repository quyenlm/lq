package com.google.android.gms.location.places.ui;

import android.view.View;

final class zze implements View.OnClickListener {
    private /* synthetic */ SupportPlaceAutocompleteFragment zzblu;

    zze(SupportPlaceAutocompleteFragment supportPlaceAutocompleteFragment) {
        this.zzblu = supportPlaceAutocompleteFragment;
    }

    public final void onClick(View view) {
        if (!this.zzblu.zzblp) {
            this.zzblu.zzwc();
        }
    }
}
