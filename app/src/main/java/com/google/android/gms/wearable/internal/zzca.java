package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.util.Log;
import com.amazonaws.services.s3.internal.Constants;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class zzca implements DataItem {
    private Uri mUri;
    private Map<String, DataItemAsset> zzbSE;
    private byte[] zzbdY;

    public zzca(DataItem dataItem) {
        this.mUri = dataItem.getUri();
        this.zzbdY = dataItem.getData();
        HashMap hashMap = new HashMap();
        for (Map.Entry next : dataItem.getAssets().entrySet()) {
            if (next.getKey() != null) {
                hashMap.put((String) next.getKey(), (DataItemAsset) ((DataItemAsset) next.getValue()).freeze());
            }
        }
        this.zzbSE = Collections.unmodifiableMap(hashMap);
    }

    public final /* bridge */ /* synthetic */ Object freeze() {
        return this;
    }

    public final Map<String, DataItemAsset> getAssets() {
        return this.zzbSE;
    }

    public final byte[] getData() {
        return this.zzbdY;
    }

    public final Uri getUri() {
        return this.mUri;
    }

    public final boolean isDataValid() {
        return true;
    }

    public final DataItem setData(byte[] bArr) {
        throw new UnsupportedOperationException();
    }

    public final String toString() {
        boolean isLoggable = Log.isLoggable("DataItem", 3);
        StringBuilder sb = new StringBuilder("DataItemEntity{ ");
        String valueOf = String.valueOf(this.mUri);
        sb.append(new StringBuilder(String.valueOf(valueOf).length() + 4).append("uri=").append(valueOf).toString());
        String valueOf2 = String.valueOf(this.zzbdY == null ? Constants.NULL_VERSION_ID : Integer.valueOf(this.zzbdY.length));
        sb.append(new StringBuilder(String.valueOf(valueOf2).length() + 9).append(", dataSz=").append(valueOf2).toString());
        sb.append(new StringBuilder(23).append(", numAssets=").append(this.zzbSE.size()).toString());
        if (isLoggable && !this.zzbSE.isEmpty()) {
            sb.append(", assets=[");
            String str = "";
            Iterator<Map.Entry<String, DataItemAsset>> it = this.zzbSE.entrySet().iterator();
            while (true) {
                String str2 = str;
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry next = it.next();
                String str3 = (String) next.getKey();
                String valueOf3 = String.valueOf(((DataItemAsset) next.getValue()).getId());
                sb.append(new StringBuilder(String.valueOf(str2).length() + 2 + String.valueOf(str3).length() + String.valueOf(valueOf3).length()).append(str2).append(str3).append(": ").append(valueOf3).toString());
                str = ", ";
            }
            sb.append("]");
        }
        sb.append(" }");
        return sb.toString();
    }
}
