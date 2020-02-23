package com.appsflyer.cache;

import java.util.Scanner;

public class RequestCacheData {

    /* renamed from: ˊ  reason: contains not printable characters */
    private String f74;

    /* renamed from: ˋ  reason: contains not printable characters */
    private String f75;

    /* renamed from: ˏ  reason: contains not printable characters */
    private String f76;

    /* renamed from: ॱ  reason: contains not printable characters */
    private String f77;

    public RequestCacheData(String str, String str2, String str3) {
        this.f76 = str;
        this.f74 = str2;
        this.f75 = str3;
    }

    public RequestCacheData(char[] cArr) {
        Scanner scanner = new Scanner(new String(cArr));
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            if (nextLine.startsWith("url=")) {
                this.f76 = nextLine.substring(4).trim();
            } else if (nextLine.startsWith("version=")) {
                this.f75 = nextLine.substring(8).trim();
            } else if (nextLine.startsWith("data=")) {
                this.f74 = nextLine.substring(5).trim();
            }
        }
        scanner.close();
    }

    public String getVersion() {
        return this.f75;
    }

    public void setVersion(String str) {
        this.f75 = str;
    }

    public String getPostData() {
        return this.f74;
    }

    public void setPostData(String str) {
        this.f74 = str;
    }

    public String getRequestURL() {
        return this.f76;
    }

    public void setRequestURL(String str) {
        this.f76 = str;
    }

    public String getCacheKey() {
        return this.f77;
    }

    public void setCacheKey(String str) {
        this.f77 = str;
    }
}
