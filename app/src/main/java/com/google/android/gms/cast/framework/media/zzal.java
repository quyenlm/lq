package com.google.android.gms.cast.framework.media;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import com.google.android.gms.R;
import com.google.android.gms.cast.MediaTrack;
import java.util.ArrayList;
import java.util.List;

public final class zzal extends ArrayAdapter<MediaTrack> implements View.OnClickListener {
    private final Context mContext;
    private int zzauM;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzal(Context context, List<MediaTrack> list, int i) {
        super(context, R.layout.cast_tracks_chooser_dialog_row_layout, list == null ? new ArrayList<>() : list);
        this.zzauM = -1;
        this.mContext = context;
        this.zzauM = i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x008e, code lost:
        if (android.text.TextUtils.isEmpty(r0) != false) goto L_0x0090;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.view.View getView(int r8, android.view.View r9, android.view.ViewGroup r10) {
        /*
            r7 = this;
            r4 = 0
            r2 = 1
            r3 = 0
            if (r9 != 0) goto L_0x0031
            android.content.Context r0 = r7.mContext
            java.lang.String r1 = "layout_inflater"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.view.LayoutInflater r0 = (android.view.LayoutInflater) r0
            int r1 = com.google.android.gms.R.layout.cast_tracks_chooser_dialog_row_layout
            android.view.View r5 = r0.inflate(r1, r10, r3)
            com.google.android.gms.cast.framework.media.zzan r6 = new com.google.android.gms.cast.framework.media.zzan
            int r0 = com.google.android.gms.R.id.text
            android.view.View r0 = r5.findViewById(r0)
            android.widget.TextView r0 = (android.widget.TextView) r0
            int r1 = com.google.android.gms.R.id.radio
            android.view.View r1 = r5.findViewById(r1)
            android.widget.RadioButton r1 = (android.widget.RadioButton) r1
            r6.<init>(r7, r0, r1)
            r5.setTag(r6)
        L_0x002d:
            if (r6 != 0) goto L_0x003a
            r0 = r4
        L_0x0030:
            return r0
        L_0x0031:
            java.lang.Object r0 = r9.getTag()
            com.google.android.gms.cast.framework.media.zzan r0 = (com.google.android.gms.cast.framework.media.zzan) r0
            r6 = r0
            r5 = r9
            goto L_0x002d
        L_0x003a:
            android.widget.RadioButton r0 = r6.zzauO
            java.lang.Integer r1 = java.lang.Integer.valueOf(r8)
            r0.setTag(r1)
            android.widget.RadioButton r1 = r6.zzauO
            int r0 = r7.zzauM
            if (r0 != r8) goto L_0x0076
            r0 = r2
        L_0x004a:
            r1.setChecked(r0)
            r5.setOnClickListener(r7)
            java.lang.Object r0 = r7.getItem(r8)
            com.google.android.gms.cast.MediaTrack r0 = (com.google.android.gms.cast.MediaTrack) r0
            java.lang.String r1 = r0.getName()
            boolean r4 = android.text.TextUtils.isEmpty(r1)
            if (r4 == 0) goto L_0x00a3
            int r1 = r0.getSubtype()
            r4 = 2
            if (r1 != r4) goto L_0x0078
            android.content.Context r0 = r7.mContext
            int r1 = com.google.android.gms.R.string.cast_tracks_chooser_dialog_closed_captions
            java.lang.String r0 = r0.getString(r1)
        L_0x006f:
            android.widget.TextView r1 = r6.zzauN
            r1.setText(r0)
            r0 = r5
            goto L_0x0030
        L_0x0076:
            r0 = r3
            goto L_0x004a
        L_0x0078:
            java.lang.String r1 = r0.getLanguage()
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x0090
            java.util.Locale r0 = com.google.android.gms.cast.framework.media.MediaUtils.getTrackLanguage(r0)
            java.lang.String r0 = r0.getDisplayLanguage()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x006f
        L_0x0090:
            android.content.Context r0 = r7.mContext
            int r1 = com.google.android.gms.R.string.cast_tracks_chooser_dialog_default_track_name
            java.lang.Object[] r2 = new java.lang.Object[r2]
            int r4 = r8 + 1
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r2[r3] = r4
            java.lang.String r0 = r0.getString(r1, r2)
            goto L_0x006f
        L_0x00a3:
            r0 = r1
            goto L_0x006f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.framework.media.zzal.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    public final void onClick(View view) {
        this.zzauM = ((Integer) ((zzan) view.getTag()).zzauO.getTag()).intValue();
        notifyDataSetChanged();
    }

    public final MediaTrack zzod() {
        if (this.zzauM < 0 || this.zzauM >= getCount()) {
            return null;
        }
        return (MediaTrack) getItem(this.zzauM);
    }
}
