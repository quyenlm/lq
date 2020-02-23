package com.subao.common.intf;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface InstalledApplicationsSupplier {
    @Nullable
    Iterable<InstalledApplication> getInstalledApplications(@NonNull Context context);
}
