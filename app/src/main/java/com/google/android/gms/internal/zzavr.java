package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.View;
import com.google.android.gms.cast.AdBreakInfo;
import java.util.List;

public final class zzavr extends View {
    private List<AdBreakInfo> zzaqe;
    private final int zzavf;
    private int zzavg = 1;
    private Paint zzavh;

    public zzavr(Context context) {
        super(context);
        Context context2 = getContext();
        this.zzavf = context2 == null ? (int) Math.round(3.0d) : (int) Math.round(((double) context2.getResources().getDisplayMetrics().density) * 3.0d);
    }

    /* access modifiers changed from: protected */
    public final synchronized void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (this.zzaqe != null && !this.zzaqe.isEmpty()) {
            int round = Math.round(((float) getMeasuredHeight()) / 2.0f);
            int measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
            for (AdBreakInfo next : this.zzaqe) {
                if (next != null) {
                    long playbackPositionInMs = next.getPlaybackPositionInMs();
                    if (playbackPositionInMs >= 0 && playbackPositionInMs <= ((long) this.zzavg)) {
                        canvas.drawCircle((float) (((int) ((((double) playbackPositionInMs) * ((double) measuredWidth)) / ((double) this.zzavg))) + getPaddingLeft()), (float) round, (float) this.zzavf, this.zzavh);
                    }
                }
            }
        }
    }

    public final synchronized void zzac(int i) {
        this.zzavg = i;
    }

    public final synchronized void zzb(List<AdBreakInfo> list, @ColorInt int i) {
        this.zzaqe = list;
        this.zzavh = new Paint(1);
        this.zzavh.setColor(-1);
        this.zzavh.setStyle(Paint.Style.FILL);
        invalidate();
    }
}
