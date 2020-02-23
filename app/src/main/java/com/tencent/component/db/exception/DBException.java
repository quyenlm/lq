package com.tencent.component.db.exception;

import com.tencent.component.annotation.PluginApi;

@PluginApi(since = 4)
public class DBException extends RuntimeException {
    private static final long serialVersionUID = 1;

    @PluginApi(since = 4)
    public DBException() {
    }

    @PluginApi(since = 4)
    public DBException(String detailMessage) {
        super(detailMessage);
    }

    @PluginApi(since = 4)
    public DBException(Throwable throwable) {
        super(throwable);
    }
}
