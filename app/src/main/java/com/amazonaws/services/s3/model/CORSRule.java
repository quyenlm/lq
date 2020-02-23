package com.amazonaws.services.s3.model;

import java.util.Arrays;
import java.util.List;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPut;

public class CORSRule {
    private List<String> allowedHeaders;
    private List<AllowedMethods> allowedMethods;
    private List<String> allowedOrigins;
    private List<String> exposedHeaders;
    private String id;
    private int maxAgeSeconds;

    public void setId(String id2) {
        this.id = id2;
    }

    public String getId() {
        return this.id;
    }

    public CORSRule withId(String id2) {
        this.id = id2;
        return this;
    }

    public void setAllowedMethods(List<AllowedMethods> allowedMethods2) {
        this.allowedMethods = allowedMethods2;
    }

    public void setAllowedMethods(AllowedMethods... allowedMethods2) {
        this.allowedMethods = Arrays.asList(allowedMethods2);
    }

    public List<AllowedMethods> getAllowedMethods() {
        return this.allowedMethods;
    }

    public CORSRule withAllowedMethods(List<AllowedMethods> allowedMethods2) {
        this.allowedMethods = allowedMethods2;
        return this;
    }

    public void setAllowedOrigins(List<String> allowedOrigins2) {
        this.allowedOrigins = allowedOrigins2;
    }

    public void setAllowedOrigins(String... allowedOrigins2) {
        this.allowedOrigins = Arrays.asList(allowedOrigins2);
    }

    public List<String> getAllowedOrigins() {
        return this.allowedOrigins;
    }

    public CORSRule withAllowedOrigins(List<String> allowedOrigins2) {
        this.allowedOrigins = allowedOrigins2;
        return this;
    }

    public void setMaxAgeSeconds(int maxAgeSeconds2) {
        this.maxAgeSeconds = maxAgeSeconds2;
    }

    public int getMaxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    public CORSRule withMaxAgeSeconds(int maxAgeSeconds2) {
        this.maxAgeSeconds = maxAgeSeconds2;
        return this;
    }

    public void setExposedHeaders(List<String> exposedHeaders2) {
        this.exposedHeaders = exposedHeaders2;
    }

    public void setExposedHeaders(String... exposedHeaders2) {
        this.exposedHeaders = Arrays.asList(exposedHeaders2);
    }

    public List<String> getExposedHeaders() {
        return this.exposedHeaders;
    }

    public CORSRule withExposedHeaders(List<String> exposedHeaders2) {
        this.exposedHeaders = exposedHeaders2;
        return this;
    }

    public void setAllowedHeaders(List<String> allowedHeaders2) {
        this.allowedHeaders = allowedHeaders2;
    }

    public void setAllowedHeaders(String... allowedHeaders2) {
        this.allowedHeaders = Arrays.asList(allowedHeaders2);
    }

    public List<String> getAllowedHeaders() {
        return this.allowedHeaders;
    }

    public CORSRule withAllowedHeaders(List<String> allowedHeaders2) {
        this.allowedHeaders = allowedHeaders2;
        return this;
    }

    public enum AllowedMethods {
        GET("GET"),
        PUT(HttpPut.METHOD_NAME),
        HEAD(HttpHead.METHOD_NAME),
        POST("POST"),
        DELETE(HttpDelete.METHOD_NAME);
        
        private final String AllowedMethod;

        private AllowedMethods(String AllowedMethod2) {
            this.AllowedMethod = AllowedMethod2;
        }

        public String toString() {
            return this.AllowedMethod;
        }

        public static AllowedMethods fromValue(String allowedMethod) throws IllegalArgumentException {
            for (AllowedMethods method : values()) {
                String methodString = method.toString();
                if ((methodString == null && allowedMethod == null) || (methodString != null && methodString.equals(allowedMethod))) {
                    return method;
                }
            }
            throw new IllegalArgumentException("Cannot create enum from " + allowedMethod + " value!");
        }
    }
}
