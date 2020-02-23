package com.amazonaws.services.s3.model;

public class RedirectRule {
    private String hostName;
    private String httpRedirectCode;
    private String protocol;
    private String replaceKeyPrefixWith;
    private String replaceKeyWith;

    public void setProtocol(String protocol2) {
        this.protocol = protocol2;
    }

    public String getprotocol() {
        return this.protocol;
    }

    public RedirectRule withProtocol(String protocol2) {
        setProtocol(protocol2);
        return this;
    }

    public void setHostName(String hostName2) {
        this.hostName = hostName2;
    }

    public String getHostName() {
        return this.hostName;
    }

    public RedirectRule withHostName(String hostName2) {
        setHostName(hostName2);
        return this;
    }

    public void setReplaceKeyPrefixWith(String replaceKeyPrefixWith2) {
        this.replaceKeyPrefixWith = replaceKeyPrefixWith2;
    }

    public String getReplaceKeyPrefixWith() {
        return this.replaceKeyPrefixWith;
    }

    public RedirectRule withReplaceKeyPrefixWith(String replaceKeyPrefixWith2) {
        setReplaceKeyPrefixWith(replaceKeyPrefixWith2);
        return this;
    }

    public void setReplaceKeyWith(String replaceKeyWith2) {
        this.replaceKeyWith = replaceKeyWith2;
    }

    public String getReplaceKeyWith() {
        return this.replaceKeyWith;
    }

    public RedirectRule withReplaceKeyWith(String replaceKeyWith2) {
        setReplaceKeyWith(replaceKeyWith2);
        return this;
    }

    public void setHttpRedirectCode(String httpRedirectCode2) {
        this.httpRedirectCode = httpRedirectCode2;
    }

    public String getHttpRedirectCode() {
        return this.httpRedirectCode;
    }

    public RedirectRule withHttpRedirectCode(String httpRedirectCode2) {
        this.httpRedirectCode = httpRedirectCode2;
        return this;
    }
}
