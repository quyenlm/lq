package com.google.android.gms.location.places.ui;

import android.view.View;

final class zzf implements View.OnClickListener {
    private /* synthetic */ SupportPlaceAutocompleteFragment zzblu;

    zzf(SupportPlaceAutocompleteFragment supportPlaceAutocompleteFragment) {
        this.zzblu = supportPlaceAutocompleteFragment;
    }

    public final void onClick(View view) {
        this.zzblu.setText("");
    }
}
