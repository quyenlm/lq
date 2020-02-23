package com.facebook.places;

import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.internal.Utility;
import com.facebook.places.internal.BluetoothScanResult;
import com.facebook.places.internal.LocationPackage;
import com.facebook.places.internal.LocationPackageManager;
import com.facebook.places.internal.LocationPackageRequestParams;
import com.facebook.places.internal.ScannerException;
import com.facebook.places.internal.WifiScanResult;
import com.facebook.places.model.CurrentPlaceFeedbackRequestParams;
import com.facebook.places.model.CurrentPlaceRequestParams;
import com.facebook.places.model.PlaceInfoRequestParams;
import com.facebook.places.model.PlaceSearchRequestParams;
import com.vk.sdk.api.model.VKApiCommunityFull;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlaceManager {
    private static final String CURRENT_PLACE_FEEDBACK = "current_place/feedback";
    private static final String CURRENT_PLACE_RESULTS = "current_place/results";
    private static final String PARAM_ACCESS_POINTS = "access_points";
    private static final String PARAM_ACCURACY = "accuracy";
    private static final String PARAM_ALTITUDE = "altitude";
    private static final String PARAM_BLUETOOTH = "bluetooth";
    private static final String PARAM_CATEGORIES = "categories";
    private static final String PARAM_CENTER = "center";
    private static final String PARAM_COORDINATES = "coordinates";
    private static final String PARAM_CURRENT_CONNECTION = "current_connection";
    private static final String PARAM_DISTANCE = "distance";
    private static final String PARAM_ENABLED = "enabled";
    private static final String PARAM_FIELDS = "fields";
    private static final String PARAM_FREQUENCY = "frequency";
    private static final String PARAM_HEADING = "heading";
    private static final String PARAM_LATITUDE = "latitude";
    private static final String PARAM_LIMIT = "limit";
    private static final String PARAM_LONGITUDE = "longitude";
    private static final String PARAM_MAC_ADDRESS = "mac_address";
    private static final String PARAM_MIN_CONFIDENCE_LEVEL = "min_confidence_level";
    private static final String PARAM_PAYLOAD = "payload";
    private static final String PARAM_PLACE_ID = "place_id";
    private static final String PARAM_Q = "q";
    private static final String PARAM_RSSI = "rssi";
    private static final String PARAM_SCANS = "scans";
    private static final String PARAM_SIGNAL_STRENGTH = "signal_strength";
    private static final String PARAM_SPEED = "speed";
    private static final String PARAM_SSID = "ssid";
    private static final String PARAM_SUMMARY = "summary";
    private static final String PARAM_TRACKING = "tracking";
    private static final String PARAM_TYPE = "type";
    private static final String PARAM_WAS_HERE = "was_here";
    private static final String PARAM_WIFI = "wifi";
    private static final String SEARCH = "search";

    public enum LocationError {
        LOCATION_PERMISSION_DENIED,
        LOCATION_SERVICES_DISABLED,
        LOCATION_TIMEOUT,
        UNKNOWN_ERROR
    }

    public interface OnRequestReadyCallback {
        void onLocationError(LocationError locationError);

        void onRequestReady(GraphRequest graphRequest);
    }

    private PlaceManager() {
    }

    public static void newPlaceSearchRequest(final PlaceSearchRequestParams requestParams, final OnRequestReadyCallback callback) {
        LocationPackageRequestParams.Builder builder = new LocationPackageRequestParams.Builder();
        builder.setWifiScanEnabled(false);
        builder.setBluetoothScanEnabled(false);
        LocationPackageManager.requestLocationPackage(builder.build(), new LocationPackageManager.Listener() {
            public void onLocationPackage(LocationPackage locationPackage) {
                if (locationPackage.locationError == null) {
                    callback.onRequestReady(PlaceManager.newPlaceSearchRequestForLocation(requestParams, locationPackage.location));
                    return;
                }
                callback.onLocationError(PlaceManager.getLocationError(locationPackage.locationError));
            }
        });
    }

    public static GraphRequest newPlaceSearchRequestForLocation(PlaceSearchRequestParams requestParams, Location location) {
        String searchText = requestParams.getSearchText();
        if (location == null && searchText == null) {
            throw new FacebookException("Either location or searchText must be specified.");
        }
        int limit = requestParams.getLimit();
        Set<String> fields = requestParams.getFields();
        Set<String> categories = requestParams.getCategories();
        Bundle parameters = new Bundle(7);
        parameters.putString("type", VKApiCommunityFull.PLACE);
        if (location != null) {
            parameters.putString(PARAM_CENTER, String.format(Locale.US, "%f,%f", new Object[]{Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude())}));
            int distance = requestParams.getDistance();
            if (distance > 0) {
                parameters.putInt(PARAM_DISTANCE, distance);
            }
        }
        if (limit > 0) {
            parameters.putInt(PARAM_LIMIT, limit);
        }
        if (!Utility.isNullOrEmpty(searchText)) {
            parameters.putString("q", searchText);
        }
        if (categories != null && !categories.isEmpty()) {
            JSONArray array = new JSONArray();
            for (String category : categories) {
                array.put(category);
            }
            parameters.putString(PARAM_CATEGORIES, array.toString());
        }
        if (fields != null && !fields.isEmpty()) {
            parameters.putString("fields", TextUtils.join(",", fields));
        }
        return new GraphRequest(AccessToken.getCurrentAccessToken(), "search", parameters, HttpMethod.GET);
    }

    public static GraphRequest newPlaceInfoRequest(PlaceInfoRequestParams requestParams) {
        String placeId = requestParams.getPlaceId();
        if (placeId == null) {
            throw new FacebookException("placeId must be specified.");
        }
        Bundle parameters = new Bundle(1);
        Set<String> fields = requestParams.getFields();
        if (fields != null && !fields.isEmpty()) {
            parameters.putString("fields", TextUtils.join(",", fields));
        }
        return new GraphRequest(AccessToken.getCurrentAccessToken(), placeId, parameters, HttpMethod.GET);
    }

    public static void newCurrentPlaceRequest(final CurrentPlaceRequestParams requestParams, final OnRequestReadyCallback callback) {
        Location location = requestParams.getLocation();
        CurrentPlaceRequestParams.ScanMode scanMode = requestParams.getScanMode();
        LocationPackageRequestParams.Builder builder = new LocationPackageRequestParams.Builder();
        builder.setLocationScanEnabled(location == null);
        if (scanMode != null && scanMode == CurrentPlaceRequestParams.ScanMode.LOW_LATENCY) {
            builder.setWifiActiveScanAllowed(false);
        }
        LocationPackageManager.requestLocationPackage(builder.build(), new LocationPackageManager.Listener() {
            public void onLocationPackage(LocationPackage locationPackage) {
                if (locationPackage.locationError != null) {
                    callback.onLocationError(PlaceManager.getLocationError(locationPackage.locationError));
                    return;
                }
                callback.onRequestReady(new GraphRequest(AccessToken.getCurrentAccessToken(), PlaceManager.CURRENT_PLACE_RESULTS, PlaceManager.getCurrentPlaceParameters(requestParams, locationPackage), HttpMethod.GET));
            }
        });
    }

    public static GraphRequest newCurrentPlaceFeedbackRequest(CurrentPlaceFeedbackRequestParams requestParams) {
        String placeId = requestParams.getPlaceId();
        String tracking = requestParams.getTracking();
        Boolean wasHere = requestParams.wasHere();
        if (tracking == null || placeId == null || wasHere == null) {
            throw new FacebookException("tracking, placeId and wasHere must be specified.");
        }
        Bundle parameters = new Bundle(3);
        parameters.putString(PARAM_TRACKING, tracking);
        parameters.putString("place_id", placeId);
        parameters.putBoolean(PARAM_WAS_HERE, wasHere.booleanValue());
        return new GraphRequest(AccessToken.getCurrentAccessToken(), CURRENT_PLACE_FEEDBACK, parameters, HttpMethod.POST);
    }

    /* access modifiers changed from: private */
    public static Bundle getCurrentPlaceParameters(CurrentPlaceRequestParams request, LocationPackage locationPackage) throws FacebookException {
        if (request == null) {
            throw new FacebookException("Request and location must be specified.");
        }
        if (locationPackage == null) {
            locationPackage = new LocationPackage();
        }
        if (locationPackage.location == null) {
            locationPackage.location = request.getLocation();
        }
        if (locationPackage.location == null) {
            throw new FacebookException("A location must be specified");
        }
        try {
            Bundle bundle = new Bundle(6);
            bundle.putString(PARAM_SUMMARY, PARAM_TRACKING);
            int limit = request.getLimit();
            if (limit > 0) {
                bundle.putInt(PARAM_LIMIT, limit);
            }
            Set<String> fields = request.getFields();
            if (fields != null && !fields.isEmpty()) {
                bundle.putString("fields", TextUtils.join(",", fields));
            }
            Location location = locationPackage.location;
            JSONObject coordinates = new JSONObject();
            coordinates.put(PARAM_LATITUDE, location.getLatitude());
            coordinates.put(PARAM_LONGITUDE, location.getLongitude());
            if (location.hasAccuracy()) {
                coordinates.put(PARAM_ACCURACY, (double) location.getAccuracy());
            }
            if (location.hasAltitude()) {
                coordinates.put(PARAM_ALTITUDE, location.getAltitude());
            }
            if (location.hasBearing()) {
                coordinates.put(PARAM_HEADING, (double) location.getBearing());
            }
            if (location.hasSpeed()) {
                coordinates.put("speed", (double) location.getSpeed());
            }
            bundle.putString(PARAM_COORDINATES, coordinates.toString());
            CurrentPlaceRequestParams.ConfidenceLevel minConfidenceLevel = request.getMinConfidenceLevel();
            if (minConfidenceLevel == CurrentPlaceRequestParams.ConfidenceLevel.LOW || minConfidenceLevel == CurrentPlaceRequestParams.ConfidenceLevel.MEDIUM || minConfidenceLevel == CurrentPlaceRequestParams.ConfidenceLevel.HIGH) {
                bundle.putString(PARAM_MIN_CONFIDENCE_LEVEL, minConfidenceLevel.toString().toLowerCase(Locale.US));
            }
            if (locationPackage != null) {
                JSONObject wifi = new JSONObject();
                wifi.put(PARAM_ENABLED, locationPackage.isWifiScanningEnabled);
                WifiScanResult connectedWifi = locationPackage.connectedWifi;
                if (connectedWifi != null) {
                    wifi.put(PARAM_CURRENT_CONNECTION, getWifiScanJson(connectedWifi));
                }
                List<WifiScanResult> ambientWifi = locationPackage.ambientWifi;
                if (ambientWifi != null) {
                    JSONArray array = new JSONArray();
                    for (WifiScanResult wifiScanResult : ambientWifi) {
                        array.put(getWifiScanJson(wifiScanResult));
                    }
                    wifi.put(PARAM_ACCESS_POINTS, array);
                }
                bundle.putString("wifi", wifi.toString());
                JSONObject bluetooth = new JSONObject();
                bluetooth.put(PARAM_ENABLED, locationPackage.isBluetoothScanningEnabled);
                List<BluetoothScanResult> bluetoothScanResults = locationPackage.ambientBluetoothLe;
                if (bluetoothScanResults != null) {
                    JSONArray array2 = new JSONArray();
                    for (BluetoothScanResult bluetoothScanResult : bluetoothScanResults) {
                        JSONObject bluetoothData = new JSONObject();
                        bluetoothData.put("payload", bluetoothScanResult.payload);
                        bluetoothData.put(PARAM_RSSI, bluetoothScanResult.rssi);
                        array2.put(bluetoothData);
                    }
                    bluetooth.put(PARAM_SCANS, array2);
                }
                bundle.putString(PARAM_BLUETOOTH, bluetooth.toString());
            }
            return bundle;
        } catch (JSONException ex) {
            throw new FacebookException((Throwable) ex);
        }
    }

    private static JSONObject getWifiScanJson(WifiScanResult wifiScanResult) throws JSONException {
        JSONObject wifiData = new JSONObject();
        wifiData.put(PARAM_MAC_ADDRESS, wifiScanResult.bssid);
        wifiData.put(PARAM_SSID, wifiScanResult.ssid);
        wifiData.put(PARAM_SIGNAL_STRENGTH, wifiScanResult.rssi);
        wifiData.put(PARAM_FREQUENCY, wifiScanResult.frequency);
        return wifiData;
    }

    /* access modifiers changed from: private */
    public static LocationError getLocationError(ScannerException.Type type) {
        if (type == ScannerException.Type.PERMISSION_DENIED) {
            return LocationError.LOCATION_PERMISSION_DENIED;
        }
        if (type == ScannerException.Type.DISABLED) {
            return LocationError.LOCATION_SERVICES_DISABLED;
        }
        if (type == ScannerException.Type.TIMEOUT) {
            return LocationError.LOCATION_TIMEOUT;
        }
        return LocationError.UNKNOWN_ERROR;
    }
}
