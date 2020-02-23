package com.google.android.gms.ads.internal.overlay;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import com.google.android.gms.internal.zzaiy;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzzn;

@zzzn
public final class zzae extends FrameLayout implements View.OnClickListener {
    private final ImageButton zzPG;
    private final zzaj zzPH;

    public zzae(Context context, zzaf zzaf, zzaj zzaj) {
        super(context);
        this.zzPH = zzaj;
        setOnClickListener(this);
        this.zzPG = new ImageButton(context);
        this.zzPG.setImageResource(17301527);
        this.zzPG.setBackgroundColor(0);
        this.zzPG.setOnClickListener(this);
        ImageButton imageButton = this.zzPG;
        zzji.zzds();
        int zzc = zzaiy.zzc(context, zzaf.paddingLeft);
        zzji.zzds();
        int zzc2 = zzaiy.zzc(context, 0);
        zzji.zzds();
        int zzc3 = zzaiy.zzc(context, zzaf.paddingRight);
        zzji.zzds();
        imageButton.setPadding(zzc, zzc2, zzc3, zzaiy.zzc(context, zzaf.paddingBottom));
        this.zzPG.setContentDescription("Interstitial close button");
        zzji.zzds();
        zzaiy.zzc(context, zzaf.size);
        ImageButton imageButton2 = this.zzPG;
        zzji.zzds();
        int zzc4 = zzaiy.zzc(context, zzaf.size + zzaf.paddingLeft + zzaf.paddingRight);
        zzji.zzds();
        addView(imageButton2, new FrameLayout.LayoutParams(zzc4, zzaiy.zzc(context, zzaf.size + zzaf.paddingBottom), 17));
    }

    public final void onClick(View view) {
        if (this.zzPH != null) {
            this.zzPH.zzfJ();
        }
    }

    public final void zza(boolean z, boolean z2) {
        if (!z2) {
            this.zzPG.setVisibility(0);
        } else if (z) {
            this.zzPG.setVisibility(4);
        } else {
            this.zzPG.setVisibility(8);
        }
    }
}
