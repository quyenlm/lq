package com.amazonaws.services.s3.model;

import java.util.LinkedList;
import java.util.List;

public class WebsiteConfiguration {
    private String errorDocument;
    private String indexDocumentSuffix;
    private String redirectAllRequestsTo;
    private List<RoutingRule> routingRules = new LinkedList();

    public void setIndexDocumentSuffix(String indexDocumentSuffix2) {
        this.indexDocumentSuffix = indexDocumentSuffix2;
    }

    public String getIndexDocumentSuffix() {
        return this.indexDocumentSuffix;
    }

    public WebsiteConfiguration withIndexDocumentSuffix(String indexDocumentSuffix2) {
        this.indexDocumentSuffix = indexDocumentSuffix2;
        return this;
    }

    public void setErrorDocument(String errorDocument2) {
        this.errorDocument = errorDocument2;
    }

    public String getErrorDocument() {
        return this.errorDocument;
    }

    public WebsiteConfiguration witherrorDocument(String errorDocument2) {
        this.errorDocument = errorDocument2;
        return this;
    }

    public void setRedirectAllRequestsTo(String redirectAllRequestsTo2) {
        this.redirectAllRequestsTo = redirectAllRequestsTo2;
    }

    public String getRedirectAllRequestsTo() {
        return this.redirectAllRequestsTo;
    }

    public WebsiteConfiguration withRedirectAllRequestsTo(String redirectAllRequestsTo2) {
        this.redirectAllRequestsTo = redirectAllRequestsTo2;
        return this;
    }

    public void setRoutingRules(List<RoutingRule> routingRules2) {
        this.routingRules = routingRules2;
    }

    public List<RoutingRule> getRoutingRule() {
        return this.routingRules;
    }

    public WebsiteConfiguration withRoutingRule(List<RoutingRule> routingRules2) {
        this.routingRules = routingRules2;
        return this;
    }
}
