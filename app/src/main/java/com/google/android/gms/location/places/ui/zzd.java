package com.google.android.gms.location.places.ui;

import android.view.View;

final class zzd implements View.OnClickListener {
    private /* synthetic */ PlaceAutocompleteFragment zzblt;

    zzd(PlaceAutocompleteFragment placeAutocompleteFragment) {
        this.zzblt = placeAutocompleteFragment;
    }

    public final void onClick(View view) {
        this.zzblt.setText("");
    }
}
