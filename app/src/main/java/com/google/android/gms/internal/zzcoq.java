package com.google.android.gms.internal;

import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.nearby.connection.Payload;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class zzcoq {
    static Pair<zzcoo, Pair<ParcelFileDescriptor, ParcelFileDescriptor>> zza(Payload payload) throws IOException {
        switch (payload.getType()) {
            case 1:
                return Pair.create(new zzcoo(payload.getId(), payload.getType(), payload.asBytes(), (ParcelFileDescriptor) null, (String) null, -1, (ParcelFileDescriptor) null), (Object) null);
            case 2:
                return Pair.create(new zzcoo(payload.getId(), payload.getType(), (byte[]) null, payload.asFile().asParcelFileDescriptor(), payload.asFile().asJavaFile() == null ? null : payload.asFile().asJavaFile().getAbsolutePath(), payload.asFile().getSize(), (ParcelFileDescriptor) null), (Object) null);
            case 3:
                try {
                    ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
                    ParcelFileDescriptor[] createPipe2 = ParcelFileDescriptor.createPipe();
                    return Pair.create(new zzcoo(payload.getId(), payload.getType(), (byte[]) null, createPipe[0], (String) null, -1, createPipe2[0]), Pair.create(createPipe[1], createPipe2[1]));
                } catch (IOException e) {
                    Log.e("NearbyConnections", String.format("Unable to create PFD pipe for streaming payload %d from client to service.", new Object[]{Long.valueOf(payload.getId())}), e);
                    throw e;
                }
            default:
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException(String.format("Outgoing Payload %d has unknown type %d", new Object[]{Long.valueOf(payload.getId()), Integer.valueOf(payload.getType())}));
                Log.wtf("NearbyConnections", "Unknown payload type!", illegalArgumentException);
                throw illegalArgumentException;
        }
    }

    static Payload zza(zzcoo zzcoo) {
        long id = zzcoo.getId();
        switch (zzcoo.getType()) {
            case 1:
                return Payload.zza(zzcoo.getBytes(), id);
            case 2:
                String zzzO = zzcoo.zzzO();
                if (zzzO != null) {
                    try {
                        return Payload.zza(Payload.File.zza(new File(zzzO), zzcoo.zzzP()), id);
                    } catch (FileNotFoundException e) {
                        FileNotFoundException fileNotFoundException = e;
                        String valueOf = String.valueOf(zzzO);
                        Log.w("NearbyConnections", valueOf.length() != 0 ? "Failed to create Payload from ParcelablePayload: Java file not found at ".concat(valueOf) : new String("Failed to create Payload from ParcelablePayload: Java file not found at "), fileNotFoundException);
                    }
                }
                return Payload.zza(Payload.File.zzb(zzcoo.zzzN()), id);
            case 3:
                return Payload.zza(Payload.Stream.zzc(zzcoo.zzzN()), id);
            default:
                Log.w("NearbyConnections", String.format("Incoming ParcelablePayload %d has unknown type %d", new Object[]{Long.valueOf(zzcoo.getId()), Integer.valueOf(zzcoo.getType())}));
                return null;
        }
    }
}
