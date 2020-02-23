package com.neovisionaries.ws.client;

import com.tencent.imsdk.framework.request.HttpRequest;
import java.util.LinkedHashMap;
import java.util.Map;

public class WebSocketExtension {
    public static final String PERMESSAGE_DEFLATE = "permessage-deflate";
    private final String mName;
    private final Map<String, String> mParameters;

    public WebSocketExtension(String name) {
        if (!Token.isValid(name)) {
            throw new IllegalArgumentException("'name' is not a valid token.");
        }
        this.mName = name;
        this.mParameters = new LinkedHashMap();
    }

    public WebSocketExtension(WebSocketExtension source) {
        if (source == null) {
            throw new IllegalArgumentException("'source' is null.");
        }
        this.mName = source.getName();
        this.mParameters = new LinkedHashMap(source.getParameters());
    }

    public String getName() {
        return this.mName;
    }

    public Map<String, String> getParameters() {
        return this.mParameters;
    }

    public boolean containsParameter(String key) {
        return this.mParameters.containsKey(key);
    }

    public String getParameter(String key) {
        return this.mParameters.get(key);
    }

    public WebSocketExtension setParameter(String key, String value) {
        if (!Token.isValid(key)) {
            throw new IllegalArgumentException("'key' is not a valid token.");
        } else if (value == null || Token.isValid(value)) {
            this.mParameters.put(key, value);
            return this;
        } else {
            throw new IllegalArgumentException("'value' is not a valid token.");
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(this.mName);
        for (Map.Entry<String, String> entry : this.mParameters.entrySet()) {
            builder.append("; ").append(entry.getKey());
            String value = entry.getValue();
            if (!(value == null || value.length() == 0)) {
                builder.append(HttpRequest.HTTP_REQ_ENTITY_MERGE).append(value);
            }
        }
        return builder.toString();
    }

    /* access modifiers changed from: package-private */
    public void validate() throws WebSocketException {
    }

    public static WebSocketExtension parse(String string) {
        String value;
        WebSocketExtension extension = null;
        if (string != null) {
            String[] elements = string.trim().split("\\s*;\\s*");
            if (elements.length != 0) {
                String name = elements[0];
                if (Token.isValid(name)) {
                    extension = createInstance(name);
                    for (int i = 1; i < elements.length; i++) {
                        String[] pair = elements[i].split("\\s*=\\s*", 2);
                        if (!(pair.length == 0 || pair[0].length() == 0)) {
                            String key = pair[0];
                            if (Token.isValid(key) && ((value = extractValue(pair)) == null || Token.isValid(value))) {
                                extension.setParameter(key, value);
                            }
                        }
                    }
                }
            }
        }
        return extension;
    }

    private static String extractValue(String[] pair) {
        if (pair.length != 2) {
            return null;
        }
        return Token.unquote(pair[1]);
    }

    private static WebSocketExtension createInstance(String name) {
        if (PERMESSAGE_DEFLATE.equals(name)) {
            return new PerMessageDeflateExtension(name);
        }
        return new WebSocketExtension(name);
    }
}
