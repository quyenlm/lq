package com.amazonaws;

import com.amazonaws.internal.config.HttpClientConfig;
import com.amazonaws.internal.config.InternalConfig;

enum ServiceNameFactory {
    ;

    static String getServiceName(String httpClientName) {
        HttpClientConfig clientConfig = InternalConfig.Factory.getInternalConfig().getHttpClientConfig(httpClientName);
        if (clientConfig == null) {
            return null;
        }
        return clientConfig.getServiceName();
    }
}
