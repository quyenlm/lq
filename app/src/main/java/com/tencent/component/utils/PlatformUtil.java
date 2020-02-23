package com.tencent.component.utils;

import android.os.Build;
import com.tencent.component.annotation.PluginApi;

@PluginApi(since = 6)
public class PlatformUtil {

    @PluginApi(since = 6)
    public static class VERSION_CODES {
        @PluginApi(since = 6)
        public static final int BASE = 1;
        @PluginApi(since = 6)
        public static final int BASE_1_1 = 2;
        @PluginApi(since = 6)
        public static final int CUPCAKE = 3;
        @PluginApi(since = 6)
        public static final int CUR_DEVELOPMENT = 10000;
        @PluginApi(since = 6)
        public static final int DONUT = 4;
        @PluginApi(since = 6)
        public static final int ECLAIR = 5;
        @PluginApi(since = 6)
        public static final int ECLAIR_0_1 = 6;
        @PluginApi(since = 6)
        public static final int ECLAIR_MR1 = 7;
        @PluginApi(since = 6)
        public static final int FROYO = 8;
        @PluginApi(since = 6)
        public static final int GINGERBREAD = 9;
        @PluginApi(since = 6)
        public static final int GINGERBREAD_MR1 = 10;
        @PluginApi(since = 6)
        public static final int HONEYCOMB = 11;
        @PluginApi(since = 6)
        public static final int HONEYCOMB_MR1 = 12;
        @PluginApi(since = 6)
        public static final int HONEYCOMB_MR2 = 13;
        @PluginApi(since = 6)
        public static final int ICE_CREAM_SANDWICH = 14;
        @PluginApi(since = 6)
        public static final int ICE_CREAM_SANDWICH_MR1 = 15;
        @PluginApi(since = 6)
        public static final int JELLY_BEAN = 16;
        @PluginApi(since = 6)
        public static final int JELLY_BEAN_MR1 = 17;
    }

    @PluginApi(since = 6)
    public static int version() {
        return Build.VERSION.SDK_INT;
    }
}
