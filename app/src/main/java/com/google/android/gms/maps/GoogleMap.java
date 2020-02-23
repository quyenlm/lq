package com.google.android.gms.maps;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.RemoteException;
import android.support.annotation.RequiresPermission;
import android.view.View;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.ILocationSourceDelegate;
import com.google.android.gms.maps.internal.zzab;
import com.google.android.gms.maps.internal.zzad;
import com.google.android.gms.maps.internal.zzaf;
import com.google.android.gms.maps.internal.zzaj;
import com.google.android.gms.maps.internal.zzal;
import com.google.android.gms.maps.internal.zzan;
import com.google.android.gms.maps.internal.zzar;
import com.google.android.gms.maps.internal.zzat;
import com.google.android.gms.maps.internal.zzav;
import com.google.android.gms.maps.internal.zzax;
import com.google.android.gms.maps.internal.zzaz;
import com.google.android.gms.maps.internal.zzbb;
import com.google.android.gms.maps.internal.zzbd;
import com.google.android.gms.maps.internal.zzd;
import com.google.android.gms.maps.internal.zzh;
import com.google.android.gms.maps.internal.zzl;
import com.google.android.gms.maps.internal.zzn;
import com.google.android.gms.maps.internal.zzr;
import com.google.android.gms.maps.internal.zzt;
import com.google.android.gms.maps.internal.zzv;
import com.google.android.gms.maps.internal.zzx;
import com.google.android.gms.maps.internal.zzz;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.internal.zzg;
import com.google.android.gms.maps.model.internal.zzj;
import com.google.android.gms.maps.model.internal.zzp;
import com.google.android.gms.maps.model.internal.zzw;

public final class GoogleMap {
    public static final int MAP_TYPE_HYBRID = 4;
    public static final int MAP_TYPE_NONE = 0;
    public static final int MAP_TYPE_NORMAL = 1;
    public static final int MAP_TYPE_SATELLITE = 2;
    public static final int MAP_TYPE_TERRAIN = 3;
    private final IGoogleMapDelegate zzblx;
    private UiSettings zzbly;

    public interface CancelableCallback {
        void onCancel();

        void onFinish();
    }

    public interface InfoWindowAdapter {
        View getInfoContents(Marker marker);

        View getInfoWindow(Marker marker);
    }

    public interface OnCameraIdleListener {
        void onCameraIdle();
    }

    public interface OnCameraMoveCanceledListener {
        void onCameraMoveCanceled();
    }

    public interface OnCameraMoveListener {
        void onCameraMove();
    }

    public interface OnCameraMoveStartedListener {
        public static final int REASON_API_ANIMATION = 2;
        public static final int REASON_DEVELOPER_ANIMATION = 3;
        public static final int REASON_GESTURE = 1;

        void onCameraMoveStarted(int i);
    }

    public interface OnCircleClickListener {
        void onCircleClick(Circle circle);
    }

    public interface OnGroundOverlayClickListener {
        void onGroundOverlayClick(GroundOverlay groundOverlay);
    }

    public interface OnIndoorStateChangeListener {
        void onIndoorBuildingFocused();

        void onIndoorLevelActivated(IndoorBuilding indoorBuilding);
    }

    public interface OnInfoWindowClickListener {
        void onInfoWindowClick(Marker marker);
    }

    public interface OnInfoWindowCloseListener {
        void onInfoWindowClose(Marker marker);
    }

    public interface OnInfoWindowLongClickListener {
        void onInfoWindowLongClick(Marker marker);
    }

    public interface OnMapClickListener {
        void onMapClick(LatLng latLng);
    }

    public interface OnMapLoadedCallback {
        void onMapLoaded();
    }

    public interface OnMapLongClickListener {
        void onMapLongClick(LatLng latLng);
    }

    public interface OnMarkerClickListener {
        boolean onMarkerClick(Marker marker);
    }

    public interface OnMarkerDragListener {
        void onMarkerDrag(Marker marker);

        void onMarkerDragEnd(Marker marker);

        void onMarkerDragStart(Marker marker);
    }

    public interface OnMyLocationButtonClickListener {
        boolean onMyLocationButtonClick();
    }

    public interface OnPoiClickListener {
        void onPoiClick(PointOfInterest pointOfInterest);
    }

    public interface OnPolygonClickListener {
        void onPolygonClick(Polygon polygon);
    }

    public interface OnPolylineClickListener {
        void onPolylineClick(Polyline polyline);
    }

    public interface SnapshotReadyCallback {
        void onSnapshotReady(Bitmap bitmap);
    }

    static final class zza extends zzd {
        private final CancelableCallback zzblY;

        zza(CancelableCallback cancelableCallback) {
            this.zzblY = cancelableCallback;
        }

        public final void onCancel() {
            this.zzblY.onCancel();
        }

        public final void onFinish() {
            this.zzblY.onFinish();
        }
    }

    @Deprecated
    public interface OnCameraChangeListener {
        void onCameraChange(CameraPosition cameraPosition);
    }

    @Deprecated
    public interface OnMyLocationChangeListener {
        void onMyLocationChange(Location location);
    }

    public GoogleMap(IGoogleMapDelegate iGoogleMapDelegate) {
        this.zzblx = (IGoogleMapDelegate) zzbo.zzu(iGoogleMapDelegate);
    }

    public final Circle addCircle(CircleOptions circleOptions) {
        try {
            return new Circle(this.zzblx.addCircle(circleOptions));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final GroundOverlay addGroundOverlay(GroundOverlayOptions groundOverlayOptions) {
        try {
            zzg addGroundOverlay = this.zzblx.addGroundOverlay(groundOverlayOptions);
            if (addGroundOverlay != null) {
                return new GroundOverlay(addGroundOverlay);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Marker addMarker(MarkerOptions markerOptions) {
        try {
            zzp addMarker = this.zzblx.addMarker(markerOptions);
            if (addMarker != null) {
                return new Marker(addMarker);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Polygon addPolygon(PolygonOptions polygonOptions) {
        try {
            return new Polygon(this.zzblx.addPolygon(polygonOptions));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Polyline addPolyline(PolylineOptions polylineOptions) {
        try {
            return new Polyline(this.zzblx.addPolyline(polylineOptions));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final TileOverlay addTileOverlay(TileOverlayOptions tileOverlayOptions) {
        try {
            zzw addTileOverlay = this.zzblx.addTileOverlay(tileOverlayOptions);
            if (addTileOverlay != null) {
                return new TileOverlay(addTileOverlay);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate cameraUpdate) {
        try {
            this.zzblx.animateCamera(cameraUpdate.zzwe());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate cameraUpdate, int i, CancelableCallback cancelableCallback) {
        try {
            this.zzblx.animateCameraWithDurationAndCallback(cameraUpdate.zzwe(), i, cancelableCallback == null ? null : new zza(cancelableCallback));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate cameraUpdate, CancelableCallback cancelableCallback) {
        try {
            this.zzblx.animateCameraWithCallback(cameraUpdate.zzwe(), cancelableCallback == null ? null : new zza(cancelableCallback));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void clear() {
        try {
            this.zzblx.clear();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final CameraPosition getCameraPosition() {
        try {
            return this.zzblx.getCameraPosition();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final IndoorBuilding getFocusedBuilding() {
        try {
            zzj focusedBuilding = this.zzblx.getFocusedBuilding();
            if (focusedBuilding != null) {
                return new IndoorBuilding(focusedBuilding);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final int getMapType() {
        try {
            return this.zzblx.getMapType();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final float getMaxZoomLevel() {
        try {
            return this.zzblx.getMaxZoomLevel();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final float getMinZoomLevel() {
        try {
            return this.zzblx.getMinZoomLevel();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Deprecated
    public final Location getMyLocation() {
        try {
            return this.zzblx.getMyLocation();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Projection getProjection() {
        try {
            return new Projection(this.zzblx.getProjection());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final UiSettings getUiSettings() {
        try {
            if (this.zzbly == null) {
                this.zzbly = new UiSettings(this.zzblx.getUiSettings());
            }
            return this.zzbly;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isBuildingsEnabled() {
        try {
            return this.zzblx.isBuildingsEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isIndoorEnabled() {
        try {
            return this.zzblx.isIndoorEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isMyLocationEnabled() {
        try {
            return this.zzblx.isMyLocationEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isTrafficEnabled() {
        try {
            return this.zzblx.isTrafficEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void moveCamera(CameraUpdate cameraUpdate) {
        try {
            this.zzblx.moveCamera(cameraUpdate.zzwe());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void resetMinMaxZoomPreference() {
        try {
            this.zzblx.resetMinMaxZoomPreference();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setBuildingsEnabled(boolean z) {
        try {
            this.zzblx.setBuildingsEnabled(z);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setContentDescription(String str) {
        try {
            this.zzblx.setContentDescription(str);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean setIndoorEnabled(boolean z) {
        try {
            return this.zzblx.setIndoorEnabled(z);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setInfoWindowAdapter(InfoWindowAdapter infoWindowAdapter) {
        if (infoWindowAdapter == null) {
            try {
                this.zzblx.setInfoWindowAdapter((zzh) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setInfoWindowAdapter(new zzg(this, infoWindowAdapter));
        }
    }

    public final void setLatLngBoundsForCameraTarget(LatLngBounds latLngBounds) {
        try {
            this.zzblx.setLatLngBoundsForCameraTarget(latLngBounds);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setLocationSource(LocationSource locationSource) {
        if (locationSource == null) {
            try {
                this.zzblx.setLocationSource((ILocationSourceDelegate) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setLocationSource(new zzl(this, locationSource));
        }
    }

    public final boolean setMapStyle(MapStyleOptions mapStyleOptions) {
        try {
            return this.zzblx.setMapStyle(mapStyleOptions);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setMapType(int i) {
        try {
            this.zzblx.setMapType(i);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setMaxZoomPreference(float f) {
        try {
            this.zzblx.setMaxZoomPreference(f);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setMinZoomPreference(float f) {
        try {
            this.zzblx.setMinZoomPreference(f);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public final void setMyLocationEnabled(boolean z) {
        try {
            this.zzblx.setMyLocationEnabled(z);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Deprecated
    public final void setOnCameraChangeListener(OnCameraChangeListener onCameraChangeListener) {
        if (onCameraChangeListener == null) {
            try {
                this.zzblx.setOnCameraChangeListener((zzl) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnCameraChangeListener(new zzs(this, onCameraChangeListener));
        }
    }

    public final void setOnCameraIdleListener(OnCameraIdleListener onCameraIdleListener) {
        if (onCameraIdleListener == null) {
            try {
                this.zzblx.setOnCameraIdleListener((zzn) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnCameraIdleListener(new zzw(this, onCameraIdleListener));
        }
    }

    public final void setOnCameraMoveCanceledListener(OnCameraMoveCanceledListener onCameraMoveCanceledListener) {
        if (onCameraMoveCanceledListener == null) {
            try {
                this.zzblx.setOnCameraMoveCanceledListener((com.google.android.gms.maps.internal.zzp) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnCameraMoveCanceledListener(new zzv(this, onCameraMoveCanceledListener));
        }
    }

    public final void setOnCameraMoveListener(OnCameraMoveListener onCameraMoveListener) {
        if (onCameraMoveListener == null) {
            try {
                this.zzblx.setOnCameraMoveListener((zzr) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnCameraMoveListener(new zzu(this, onCameraMoveListener));
        }
    }

    public final void setOnCameraMoveStartedListener(OnCameraMoveStartedListener onCameraMoveStartedListener) {
        if (onCameraMoveStartedListener == null) {
            try {
                this.zzblx.setOnCameraMoveStartedListener((zzt) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnCameraMoveStartedListener(new zzt(this, onCameraMoveStartedListener));
        }
    }

    public final void setOnCircleClickListener(OnCircleClickListener onCircleClickListener) {
        if (onCircleClickListener == null) {
            try {
                this.zzblx.setOnCircleClickListener((zzv) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnCircleClickListener(new zzn(this, onCircleClickListener));
        }
    }

    public final void setOnGroundOverlayClickListener(OnGroundOverlayClickListener onGroundOverlayClickListener) {
        if (onGroundOverlayClickListener == null) {
            try {
                this.zzblx.setOnGroundOverlayClickListener((zzx) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnGroundOverlayClickListener(new zzk(this, onGroundOverlayClickListener));
        }
    }

    public final void setOnIndoorStateChangeListener(OnIndoorStateChangeListener onIndoorStateChangeListener) {
        if (onIndoorStateChangeListener == null) {
            try {
                this.zzblx.setOnIndoorStateChangeListener((zzz) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnIndoorStateChangeListener(new zza(this, onIndoorStateChangeListener));
        }
    }

    public final void setOnInfoWindowClickListener(OnInfoWindowClickListener onInfoWindowClickListener) {
        if (onInfoWindowClickListener == null) {
            try {
                this.zzblx.setOnInfoWindowClickListener((zzab) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnInfoWindowClickListener(new zzd(this, onInfoWindowClickListener));
        }
    }

    public final void setOnInfoWindowCloseListener(OnInfoWindowCloseListener onInfoWindowCloseListener) {
        if (onInfoWindowCloseListener == null) {
            try {
                this.zzblx.setOnInfoWindowCloseListener((zzad) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnInfoWindowCloseListener(new zzf(this, onInfoWindowCloseListener));
        }
    }

    public final void setOnInfoWindowLongClickListener(OnInfoWindowLongClickListener onInfoWindowLongClickListener) {
        if (onInfoWindowLongClickListener == null) {
            try {
                this.zzblx.setOnInfoWindowLongClickListener((zzaf) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnInfoWindowLongClickListener(new zze(this, onInfoWindowLongClickListener));
        }
    }

    public final void setOnMapClickListener(OnMapClickListener onMapClickListener) {
        if (onMapClickListener == null) {
            try {
                this.zzblx.setOnMapClickListener((zzaj) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnMapClickListener(new zzx(this, onMapClickListener));
        }
    }

    public final void setOnMapLoadedCallback(OnMapLoadedCallback onMapLoadedCallback) {
        if (onMapLoadedCallback == null) {
            try {
                this.zzblx.setOnMapLoadedCallback((zzal) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnMapLoadedCallback(new zzj(this, onMapLoadedCallback));
        }
    }

    public final void setOnMapLongClickListener(OnMapLongClickListener onMapLongClickListener) {
        if (onMapLongClickListener == null) {
            try {
                this.zzblx.setOnMapLongClickListener((zzan) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnMapLongClickListener(new zzy(this, onMapLongClickListener));
        }
    }

    public final void setOnMarkerClickListener(OnMarkerClickListener onMarkerClickListener) {
        if (onMarkerClickListener == null) {
            try {
                this.zzblx.setOnMarkerClickListener((zzar) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnMarkerClickListener(new zzb(this, onMarkerClickListener));
        }
    }

    public final void setOnMarkerDragListener(OnMarkerDragListener onMarkerDragListener) {
        if (onMarkerDragListener == null) {
            try {
                this.zzblx.setOnMarkerDragListener((zzat) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnMarkerDragListener(new zzc(this, onMarkerDragListener));
        }
    }

    public final void setOnMyLocationButtonClickListener(OnMyLocationButtonClickListener onMyLocationButtonClickListener) {
        if (onMyLocationButtonClickListener == null) {
            try {
                this.zzblx.setOnMyLocationButtonClickListener((zzav) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnMyLocationButtonClickListener(new zzi(this, onMyLocationButtonClickListener));
        }
    }

    @Deprecated
    public final void setOnMyLocationChangeListener(OnMyLocationChangeListener onMyLocationChangeListener) {
        if (onMyLocationChangeListener == null) {
            try {
                this.zzblx.setOnMyLocationChangeListener((zzax) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnMyLocationChangeListener(new zzh(this, onMyLocationChangeListener));
        }
    }

    public final void setOnPoiClickListener(OnPoiClickListener onPoiClickListener) {
        if (onPoiClickListener == null) {
            try {
                this.zzblx.setOnPoiClickListener((zzaz) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnPoiClickListener(new zzr(this, onPoiClickListener));
        }
    }

    public final void setOnPolygonClickListener(OnPolygonClickListener onPolygonClickListener) {
        if (onPolygonClickListener == null) {
            try {
                this.zzblx.setOnPolygonClickListener((zzbb) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnPolygonClickListener(new zzo(this, onPolygonClickListener));
        }
    }

    public final void setOnPolylineClickListener(OnPolylineClickListener onPolylineClickListener) {
        if (onPolylineClickListener == null) {
            try {
                this.zzblx.setOnPolylineClickListener((zzbd) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.zzblx.setOnPolylineClickListener(new zzp(this, onPolylineClickListener));
        }
    }

    public final void setPadding(int i, int i2, int i3, int i4) {
        try {
            this.zzblx.setPadding(i, i2, i3, i4);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setTrafficEnabled(boolean z) {
        try {
            this.zzblx.setTrafficEnabled(z);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void snapshot(SnapshotReadyCallback snapshotReadyCallback) {
        snapshot(snapshotReadyCallback, (Bitmap) null);
    }

    public final void snapshot(SnapshotReadyCallback snapshotReadyCallback, Bitmap bitmap) {
        try {
            this.zzblx.snapshot(new zzq(this, snapshotReadyCallback), (com.google.android.gms.dynamic.zzn) (bitmap != null ? com.google.android.gms.dynamic.zzn.zzw(bitmap) : null));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void stopAnimation() {
        try {
            this.zzblx.stopAnimation();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
