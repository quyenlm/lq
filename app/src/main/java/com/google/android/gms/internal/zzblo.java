package com.google.android.gms.internal;

import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.drive.CreateFileActivityBuilder;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.google.android.gms.drive.query.Query;
import java.util.List;

public final class zzblo implements DriveApi {
    public final PendingResult<Status> cancelPendingActions(GoogleApiClient googleApiClient, List<String> list) {
        boolean z = true;
        zzbmh zzbmh = (zzbmh) googleApiClient.zza(Drive.zzajR);
        zzbo.zzaf(list != null);
        if (list.isEmpty()) {
            z = false;
        }
        zzbo.zzaf(z);
        zzbo.zza(zzbmh.isConnected(), (Object) "Client must be connected");
        return googleApiClient.zze(new zzbmm(zzbmh, googleApiClient, list));
    }

    public final PendingResult<DriveApi.DriveIdResult> fetchDriveId(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.zzd(new zzblr(this, googleApiClient, str));
    }

    public final DriveFolder getAppFolder(GoogleApiClient googleApiClient) {
        zzbmh zzbmh = (zzbmh) googleApiClient.zza(Drive.zzajR);
        if (!zzbmh.zzth()) {
            throw new IllegalStateException("Client is not yet connected");
        }
        DriveId zztg = zzbmh.zztg();
        if (zztg != null) {
            return new zzbmx(zztg);
        }
        return null;
    }

    public final DriveFile getFile(GoogleApiClient googleApiClient, DriveId driveId) {
        if (driveId == null) {
            throw new IllegalArgumentException("Id must be provided.");
        } else if (googleApiClient.isConnected()) {
            return new zzbms(driveId);
        } else {
            throw new IllegalStateException("Client must be connected");
        }
    }

    public final DriveFolder getFolder(GoogleApiClient googleApiClient, DriveId driveId) {
        if (driveId == null) {
            throw new IllegalArgumentException("Id must be provided.");
        } else if (googleApiClient.isConnected()) {
            return new zzbmx(driveId);
        } else {
            throw new IllegalStateException("Client must be connected");
        }
    }

    public final DriveFolder getRootFolder(GoogleApiClient googleApiClient) {
        zzbmh zzbmh = (zzbmh) googleApiClient.zza(Drive.zzajR);
        if (!zzbmh.zzth()) {
            throw new IllegalStateException("Client is not yet connected");
        }
        DriveId zztf = zzbmh.zztf();
        if (zztf != null) {
            return new zzbmx(zztf);
        }
        return null;
    }

    public final PendingResult<BooleanResult> isAutobackupEnabled(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zzblt(this, googleApiClient));
    }

    public final CreateFileActivityBuilder newCreateFileActivityBuilder() {
        return new CreateFileActivityBuilder();
    }

    public final PendingResult<DriveApi.DriveContentsResult> newDriveContents(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zzblq(this, googleApiClient, DriveFile.MODE_WRITE_ONLY));
    }

    public final OpenFileActivityBuilder newOpenFileActivityBuilder() {
        return new OpenFileActivityBuilder();
    }

    public final PendingResult<DriveApi.MetadataBufferResult> query(GoogleApiClient googleApiClient, Query query) {
        if (query != null) {
            return googleApiClient.zzd(new zzblp(this, googleApiClient, query));
        }
        throw new IllegalArgumentException("Query must be provided.");
    }

    public final PendingResult<Status> requestSync(GoogleApiClient googleApiClient) {
        return googleApiClient.zze(new zzbls(this, googleApiClient));
    }
}
