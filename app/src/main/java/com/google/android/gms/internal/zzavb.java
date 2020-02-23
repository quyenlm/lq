package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class zzavb extends zzaup {
    private final MediaRouter zzapE;
    private final Map<MediaRouteSelector, Set<MediaRouter.Callback>> zzasO = new HashMap();

    public zzavb(MediaRouter mediaRouter) {
        this.zzapE = mediaRouter;
    }

    public final void setMediaSessionCompat(MediaSessionCompat mediaSessionCompat) {
        this.zzapE.setMediaSessionCompat(mediaSessionCompat);
    }

    public final void zza(Bundle bundle, int i) {
        MediaRouteSelector fromBundle = MediaRouteSelector.fromBundle(bundle);
        for (MediaRouter.Callback addCallback : this.zzasO.get(fromBundle)) {
            this.zzapE.addCallback(fromBundle, addCallback, i);
        }
    }

    public final void zza(Bundle bundle, zzauq zzauq) {
        MediaRouteSelector fromBundle = MediaRouteSelector.fromBundle(bundle);
        if (!this.zzasO.containsKey(fromBundle)) {
            this.zzasO.put(fromBundle, new HashSet());
        }
        this.zzasO.get(fromBundle).add(new zzava(zzauq));
    }

    public final boolean zzb(Bundle bundle, int i) {
        return this.zzapE.isRouteAvailable(MediaRouteSelector.fromBundle(bundle), i);
    }

    public final void zzce(String str) {
        for (MediaRouter.RouteInfo next : this.zzapE.getRoutes()) {
            if (next.getId().equals(str)) {
                this.zzapE.selectRoute(next);
                return;
            }
        }
    }

    public final Bundle zzcf(String str) {
        for (MediaRouter.RouteInfo next : this.zzapE.getRoutes()) {
            if (next.getId().equals(str)) {
                return next.getExtras();
            }
        }
        return null;
    }

    public final void zzk(Bundle bundle) {
        for (MediaRouter.Callback removeCallback : this.zzasO.get(MediaRouteSelector.fromBundle(bundle))) {
            this.zzapE.removeCallback(removeCallback);
        }
    }

    public final void zznI() {
        this.zzapE.selectRoute(this.zzapE.getDefaultRoute());
    }

    public final boolean zznJ() {
        return this.zzapE.getSelectedRoute().getId().equals(this.zzapE.getDefaultRoute().getId());
    }

    public final String zznK() {
        return this.zzapE.getSelectedRoute().getId();
    }
}
