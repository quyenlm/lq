package com.beetalk.sdk.line;

import android.content.Context;

public class LineUtils {
    public static final String KEY_CHANNEL_ID = "me_line_sdk_ChannelId";

    public static int lookUpChannelId(Context context) {
        int id = context.getResources().getIdentifier(KEY_CHANNEL_ID, "integer", context.getPackageName());
        if (id > 0) {
            return context.getResources().getInteger(id);
        }
        throw new IllegalStateException("Forget add <integer name=\"me_line_sdk_ChannelId\">your_channel_id</integer> in your values dir?");
    }
}
