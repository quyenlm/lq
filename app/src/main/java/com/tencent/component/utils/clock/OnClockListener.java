package com.tencent.component.utils.clock;

import com.tencent.component.annotation.PluginApi;

@PluginApi(since = 6)
public interface OnClockListener {
    @PluginApi(since = 6)
    boolean onClockArrived(Clock clock);
}
