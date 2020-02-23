package com.facebook.appevents.codeless.internal;

import bolts.MeasurementEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventBinding {
    private final String activityName;
    private final String appVersion;
    private final String componentId;
    private final String eventName;
    private final MappingMethod method;
    private final List<ParameterComponent> parameters;
    private final List<PathComponent> path;
    private final String pathType;
    private final ActionType type;

    public enum ActionType {
        CLICK,
        SELECTED,
        TEXT_CHANGED
    }

    public enum MappingMethod {
        MANUAL,
        INFERENCE
    }

    public EventBinding(String eventName2, MappingMethod method2, ActionType type2, String appVersion2, List<PathComponent> path2, List<ParameterComponent> parameters2, String componentId2, String pathType2, String activityName2) {
        this.eventName = eventName2;
        this.method = method2;
        this.type = type2;
        this.appVersion = appVersion2;
        this.path = path2;
        this.parameters = parameters2;
        this.componentId = componentId2;
        this.pathType = pathType2;
        this.activityName = activityName2;
    }

    public static List<EventBinding> parseArray(JSONArray array) {
        int length;
        List<EventBinding> eventBindings = new ArrayList<>();
        if (array != null) {
            try {
                length = array.length();
            } catch (JSONException e) {
            }
        } else {
            length = 0;
        }
        for (int i = 0; i < length; i++) {
            eventBindings.add(getInstanceFromJson(array.getJSONObject(i)));
        }
        return eventBindings;
    }

    public static EventBinding getInstanceFromJson(JSONObject mapping) throws JSONException {
        String eventName2 = mapping.getString(MeasurementEvent.MEASUREMENT_EVENT_NAME_KEY);
        MappingMethod method2 = MappingMethod.valueOf(mapping.getString("method").toUpperCase());
        ActionType type2 = ActionType.valueOf(mapping.getString("event_type").toUpperCase());
        String appVersion2 = mapping.getString("app_version");
        JSONArray jsonPathArray = mapping.getJSONArray(ClientCookie.PATH_ATTR);
        List<PathComponent> path2 = new ArrayList<>();
        for (int i = 0; i < jsonPathArray.length(); i++) {
            path2.add(new PathComponent(jsonPathArray.getJSONObject(i)));
        }
        String pathType2 = mapping.optString(Constants.EVENT_MAPPING_PATH_TYPE_KEY, Constants.PATH_TYPE_ABSOLUTE);
        JSONArray jsonParameterArray = mapping.optJSONArray("parameters");
        List<ParameterComponent> parameters2 = new ArrayList<>();
        if (jsonParameterArray != null) {
            for (int i2 = 0; i2 < jsonParameterArray.length(); i2++) {
                parameters2.add(new ParameterComponent(jsonParameterArray.getJSONObject(i2)));
            }
        }
        return new EventBinding(eventName2, method2, type2, appVersion2, path2, parameters2, mapping.optString("component_id"), pathType2, mapping.optString("activity_name"));
    }

    public List<PathComponent> getViewPath() {
        return Collections.unmodifiableList(this.path);
    }

    public List<ParameterComponent> getViewParameters() {
        return Collections.unmodifiableList(this.parameters);
    }

    public String getEventName() {
        return this.eventName;
    }

    public ActionType getType() {
        return this.type;
    }

    public MappingMethod getMethod() {
        return this.method;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public String getComponentId() {
        return this.componentId;
    }

    public String getPathType() {
        return this.pathType;
    }

    public String getActivityName() {
        return this.activityName;
    }
}
