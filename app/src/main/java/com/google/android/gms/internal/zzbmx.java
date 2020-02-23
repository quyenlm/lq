package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.metadata.internal.zzk;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.drive.zzm;
import com.google.android.gms.drive.zzo;

public final class zzbmx extends zzbnn implements DriveFolder {
    public zzbmx(DriveId driveId) {
        super(driveId);
    }

    private final PendingResult<DriveFolder.DriveFileResult> zza(GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet, DriveContents driveContents, zzm zzm) {
        int requestId;
        zzm zzm2 = zzm == null ? (zzm) new zzo().build() : zzm;
        if (metadataChangeSet == null) {
            throw new IllegalArgumentException("MetadataChangeSet must be provided.");
        }
        zzk zzcS = zzk.zzcS(metadataChangeSet.getMimeType());
        if (zzcS == null || !zzcS.isFolder()) {
            zzm2.zze(googleApiClient);
            if (driveContents != null) {
                if (!(driveContents instanceof zzbmn)) {
                    throw new IllegalArgumentException("Only DriveContents obtained from the Drive API are accepted.");
                } else if (driveContents.getDriveId() != null) {
                    throw new IllegalArgumentException("Only DriveContents obtained through DriveApi.newDriveContents are accepted for file creation.");
                } else if (driveContents.zzsO()) {
                    throw new IllegalArgumentException("DriveContents are already closed.");
                }
            }
            zzk zzcS2 = zzk.zzcS(metadataChangeSet.getMimeType());
            if (driveContents == null) {
                requestId = (zzcS2 == null || !zzcS2.zzts()) ? 1 : 0;
            } else {
                requestId = driveContents.zzsM().getRequestId();
                driveContents.zzsN();
            }
            String zzsU = zzm2.zzsU();
            MetadataChangeSet zza = zzsU != null ? metadataChangeSet.zza(zzbrc.zzaQA, zzsU) : metadataChangeSet;
            zzk zzcS3 = zzk.zzcS(zza.getMimeType());
            return googleApiClient.zze(new zzbmy(this, googleApiClient, zza, requestId, (zzcS3 == null || !zzcS3.zzts()) ? 0 : 1, zzm2));
        }
        throw new IllegalArgumentException("May not create folders using this method. Use DriveFolder.createFolder() instead of mime type application/vnd.google-apps.folder");
    }

    private static void zzb(MetadataChangeSet metadataChangeSet) {
        if (metadataChangeSet == null) {
            throw new IllegalArgumentException("MetadataChangeSet must be provided.");
        }
        zzk zzcS = zzk.zzcS(metadataChangeSet.getMimeType());
        if (zzcS != null) {
            if (!(!zzcS.zzts() && !zzcS.isFolder())) {
                throw new IllegalArgumentException("May not create shortcut files using this method. Use DriveFolder.createShortcutFile() instead.");
            }
        }
    }

    public final PendingResult<DriveFolder.DriveFileResult> createFile(GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet, DriveContents driveContents) {
        zzb(metadataChangeSet);
        return zza(googleApiClient, metadataChangeSet, driveContents, (zzm) null);
    }

    public final PendingResult<DriveFolder.DriveFileResult> createFile(GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet, DriveContents driveContents, ExecutionOptions executionOptions) {
        zzb(metadataChangeSet);
        zzo zzo = new zzo();
        if (executionOptions != null) {
            if (executionOptions.zzsR() != 0) {
                throw new IllegalStateException("May not set a conflict strategy for new file creation.");
            }
            String zzsP = executionOptions.zzsP();
            if (zzsP != null) {
                zzo.setTrackingTag(zzsP);
            }
            zzo.setNotifyOnCompletion(executionOptions.zzsQ());
        }
        return zza(googleApiClient, metadataChangeSet, driveContents, (zzm) zzo.build());
    }

    public final PendingResult<DriveFolder.DriveFolderResult> createFolder(GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet) {
        if (metadataChangeSet == null) {
            throw new IllegalArgumentException("MetadataChangeSet must be provided.");
        } else if (metadataChangeSet.getMimeType() == null || metadataChangeSet.getMimeType().equals(DriveFolder.MIME_TYPE)) {
            return googleApiClient.zze(new zzbmz(this, googleApiClient, metadataChangeSet));
        } else {
            throw new IllegalArgumentException("The mimetype must be of type application/vnd.google-apps.folder");
        }
    }

    public final PendingResult<DriveApi.MetadataBufferResult> listChildren(GoogleApiClient googleApiClient) {
        return queryChildren(googleApiClient, (Query) null);
    }

    public final PendingResult<DriveApi.MetadataBufferResult> queryChildren(GoogleApiClient googleApiClient, Query query) {
        zzblo zzblo = new zzblo();
        Query.Builder addFilter = new Query.Builder().addFilter(Filters.in(SearchableField.PARENTS, getDriveId()));
        if (query != null) {
            if (query.getFilter() != null) {
                addFilter.addFilter(query.getFilter());
            }
            addFilter.setPageToken(query.getPageToken());
            addFilter.setSortOrder(query.getSortOrder());
        }
        return zzblo.query(googleApiClient, addFilter.build());
    }
}
