package com.facebook.appevents.codeless.internal;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ParameterComponent {
    private static final String PARAMETER_NAME_KEY = "name";
    private static final String PARAMETER_PATH_KEY = "path";
    private static final String PARAMETER_VALUE_KEY = "value";
    public final String name;
    public final List<PathComponent> path;
    public final String pathType;
    public final String value;

    public ParameterComponent(JSONObject component) throws JSONException {
        this.name = component.getString("name");
        this.value = component.optString("value");
        ArrayList<PathComponent> pathComponents = new ArrayList<>();
        JSONArray jsonPathArray = component.optJSONArray("path");
        if (jsonPathArray != null) {
            for (int i = 0; i < jsonPathArray.length(); i++) {
                pathComponents.add(new PathComponent(jsonPathArray.getJSONObject(i)));
            }
        }
        this.path = pathComponents;
        this.pathType = component.optString(Constants.EVENT_MAPPING_PATH_TYPE_KEY, Constants.PATH_TYPE_ABSOLUTE);
    }
}
