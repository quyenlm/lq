package com.google.android.gms.cast.framework.media;

import android.app.Dialog;
import android.content.DialogInterface;

final class zzaj implements DialogInterface.OnClickListener {
    private /* synthetic */ TracksChooserDialogFragment zzauJ;

    zzaj(TracksChooserDialogFragment tracksChooserDialogFragment) {
        this.zzauJ = tracksChooserDialogFragment;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        if (this.zzauJ.mDialog != null) {
            this.zzauJ.mDialog.cancel();
            Dialog unused = this.zzauJ.mDialog = null;
        }
    }
}
