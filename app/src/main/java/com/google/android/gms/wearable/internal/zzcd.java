package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.util.Log;
import com.amazonaws.services.s3.internal.Constants;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.http.cookie.ClientCookie;

public final class zzcd extends zzc implements DataItem {
    private final int zzbcP;

    public zzcd(DataHolder dataHolder, int i, int i2) {
        super(dataHolder, i);
        this.zzbcP = i2;
    }

    public final /* synthetic */ Object freeze() {
        return new zzca(this);
    }

    public final Map<String, DataItemAsset> getAssets() {
        HashMap hashMap = new HashMap(this.zzbcP);
        for (int i = 0; i < this.zzbcP; i++) {
            zzbz zzbz = new zzbz(this.zzaCX, this.zzaFx + i);
            if (zzbz.getDataItemKey() != null) {
                hashMap.put(zzbz.getDataItemKey(), zzbz);
            }
        }
        return hashMap;
    }

    public final byte[] getData() {
        return getByteArray(ShareConstants.WEB_DIALOG_PARAM_DATA);
    }

    public final Uri getUri() {
        return Uri.parse(getString(ClientCookie.PATH_ATTR));
    }

    public final DataItem setData(byte[] bArr) {
        throw new UnsupportedOperationException();
    }

    public final String toString() {
        boolean isLoggable = Log.isLoggable("DataItem", 3);
        byte[] data = getData();
        Map<String, DataItemAsset> assets = getAssets();
        StringBuilder sb = new StringBuilder("DataItemInternal{ ");
        String valueOf = String.valueOf(getUri());
        sb.append(new StringBuilder(String.valueOf(valueOf).length() + 4).append("uri=").append(valueOf).toString());
        String valueOf2 = String.valueOf(data == null ? Constants.NULL_VERSION_ID : Integer.valueOf(data.length));
        sb.append(new StringBuilder(String.valueOf(valueOf2).length() + 9).append(", dataSz=").append(valueOf2).toString());
        sb.append(new StringBuilder(23).append(", numAssets=").append(assets.size()).toString());
        if (isLoggable && !assets.isEmpty()) {
            sb.append(", assets=[");
            String str = "";
            Iterator<Map.Entry<String, DataItemAsset>> it = assets.entrySet().iterator();
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
