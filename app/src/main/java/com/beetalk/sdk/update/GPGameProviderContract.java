package com.beetalk.sdk.update;

public interface GPGameProviderContract {
    public static final String AUTHORITY = "com.garena.gamecenter.GPGameProvider";

    public interface Column {
        public static final String APP_ID = "app_id";
        public static final String STATUS = "status";
        public static final String VERSION_CODE = "version_code";
    }

    public interface Path {
        public static final String UPDATE = "update";
    }

    public interface UpdateStatus {
        public static final int DOWNLOADING = 1;
        public static final int DOWNLOAD_COMPLETE = 2;
        public static final int UNAVAILABLE = 0;
    }
}
