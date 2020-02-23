package com.amazonaws.services.s3.model;

import java.util.LinkedList;
import java.util.List;

public class BucketWebsiteConfiguration {
    private String errorDocument;
    private String indexDocumentSuffix;
    private RedirectRule redirectAllRequestsTo;
    private List<RoutingRule> routingRules = new LinkedList();

    public BucketWebsiteConfiguration() {
    }

    public BucketWebsiteConfiguration(String indexDocumentSuffix2) {
        this.indexDocumentSuffix = indexDocumentSuffix2;
    }

    public BucketWebsiteConfiguration(String indexDocumentSuffix2, String errorDocument2) {
        this.indexDocumentSuffix = indexDocumentSuffix2;
        this.errorDocument = errorDocument2;
    }

    public String getIndexDocumentSuffix() {
        return this.indexDocumentSuffix;
    }

    public void setIndexDocumentSuffix(String indexDocumentSuffix2) {
        this.indexDocumentSuffix = indexDocumentSuffix2;
    }

    public String getErrorDocument() {
        return this.errorDocument;
    }

    public void setErrorDocument(String errorDocument2) {
        this.errorDocument = errorDocument2;
    }

    public void setRedirectAllRequestsTo(RedirectRule redirectAllRequestsTo2) {
        this.redirectAllRequestsTo = redirectAllRequestsTo2;
    }

    public RedirectRule getRedirectAllRequestsTo() {
        return this.redirectAllRequestsTo;
    }

    public BucketWebsiteConfiguration withRedirectAllRequestsTo(RedirectRule redirectAllRequestsTo2) {
        this.redirectAllRequestsTo = redirectAllRequestsTo2;
        return this;
    }

    public void setRoutingRules(List<RoutingRule> routingRules2) {
        this.routingRules = routingRules2;
    }

    public List<RoutingRule> getRoutingRules() {
        return this.routingRules;
    }

    public BucketWebsiteConfiguration withRoutingRules(List<RoutingRule> routingRules2) {
        this.routingRules = routingRules2;
        return this;
    }
}
