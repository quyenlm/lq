package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.vision.barcode.Barcode;
import java.nio.ByteBuffer;

public final class ew extends fb<ex> {
    private final eu zzbNh;

    public ew(Context context, eu euVar) {
        super(context, "BarcodeNativeHandle");
        this.zzbNh = euVar;
        zzDR();
    }

    /* access modifiers changed from: protected */
    public final void zzDO() throws RemoteException {
        if (isOperational()) {
            ((ex) zzDR()).zzDP();
        }
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object zza(DynamiteModule dynamiteModule, Context context) throws RemoteException, DynamiteModule.zzc {
        ez faVar;
        IBinder zzcV = dynamiteModule.zzcV("com.google.android.gms.vision.barcode.ChimeraNativeBarcodeDetectorCreator");
        if (zzcV == null) {
            faVar = null;
        } else {
            IInterface queryLocalInterface = zzcV.queryLocalInterface("com.google.android.gms.vision.barcode.internal.client.INativeBarcodeDetectorCreator");
            faVar = queryLocalInterface instanceof ez ? (ez) queryLocalInterface : new fa(zzcV);
        }
        IObjectWrapper zzw = zzn.zzw(context);
        if (faVar == null) {
            return null;
        }
        return faVar.zza(zzw, this.zzbNh);
    }

    public final Barcode[] zza(Bitmap bitmap, fc fcVar) {
        if (!isOperational()) {
            return new Barcode[0];
        }
        try {
            return ((ex) zzDR()).zzb(zzn.zzw(bitmap), fcVar);
        } catch (RemoteException e) {
            Log.e("BarcodeNativeHandle", "Error calling native barcode detector", e);
            return new Barcode[0];
        }
    }

    public final Barcode[] zza(ByteBuffer byteBuffer, fc fcVar) {
        if (!isOperational()) {
            return new Barcode[0];
        }
        try {
            return ((ex) zzDR()).zza(zzn.zzw(byteBuffer), fcVar);
        } catch (RemoteException e) {
            Log.e("BarcodeNativeHandle", "Error calling native barcode detector", e);
            return new Barcode[0];
        }
    }
}
