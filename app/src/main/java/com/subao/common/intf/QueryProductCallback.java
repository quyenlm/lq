package com.subao.common.intf;

import android.support.annotation.Nullable;

public interface QueryProductCallback {
    void onQueryProductResult(int i, @Nullable ProductList productList);
}
