package org.apache.http.impl.cookie;

import java.util.Date;
import org.apache.http.cookie.SetCookie2;

@Deprecated
public class BasicClientCookie2 extends BasicClientCookie implements SetCookie2 {
    public BasicClientCookie2(String name, String value) {
        super((String) null, (String) null);
        throw new RuntimeException("Stub!");
    }

    public int[] getPorts() {
        throw new RuntimeException("Stub!");
    }

    public void setPorts(int[] ports) {
        throw new RuntimeException("Stub!");
    }

    public String getCommentURL() {
        throw new RuntimeException("Stub!");
    }

    public void setCommentURL(String commentURL) {
        throw new RuntimeException("Stub!");
    }

    public void setDiscard(boolean discard) {
        throw new RuntimeException("Stub!");
    }

    public boolean isPersistent() {
        throw new RuntimeException("Stub!");
    }

    public boolean isExpired(Date date) {
        throw new RuntimeException("Stub!");
    }

    public Object clone() throws CloneNotSupportedException {
        throw new RuntimeException("Stub!");
    }
}
