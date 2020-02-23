package com.tencent.midas.oversea.api;

import com.tencent.midas.oversea.data.channel.APPayReceipt;
import java.util.List;

public interface IGetPurchaseCallback {
    void callback(List<APPayReceipt> list);
}
