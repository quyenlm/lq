package com.google.android.gms.location.places.ui;

import android.view.View;

final class zzc implements View.OnClickListener {
    private /* synthetic */ PlaceAutocompleteFragment zzblt;

    zzc(PlaceAutocompleteFragment placeAutocompleteFragment) {
        this.zzblt = placeAutocompleteFragment;
    }

    public final void onClick(View view) {
        if (!this.zzblt.zzblp) {
            this.zzblt.zzwc();
        }
    }
}
