package com.google.android.gms.drive;

import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.drive.metadata.CustomPropertyKey;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.AppVisibleCustomProperties;
import com.google.android.gms.internal.zzbrc;
import com.google.android.gms.internal.zzbrp;
import com.google.android.gms.internal.zzbrx;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public abstract class Metadata implements Freezable<Metadata> {
    public static final int CONTENT_AVAILABLE_LOCALLY = 1;
    public static final int CONTENT_NOT_AVAILABLE_LOCALLY = 0;

    public String getAlternateLink() {
        return (String) zza(zzbrc.zzaPR);
    }

    public int getContentAvailability() {
        Integer num = (Integer) zza(zzbrx.zzaQP);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public Date getCreatedDate() {
        return (Date) zza(zzbrp.zzaQI);
    }

    public Map<CustomPropertyKey, String> getCustomProperties() {
        AppVisibleCustomProperties appVisibleCustomProperties = (AppVisibleCustomProperties) zza(zzbrc.zzaPS);
        return appVisibleCustomProperties == null ? Collections.emptyMap() : appVisibleCustomProperties.zztl();
    }

    public String getDescription() {
        return (String) zza(zzbrc.zzaPT);
    }

    public DriveId getDriveId() {
        return (DriveId) zza(zzbrc.zzaPQ);
    }

    public String getEmbedLink() {
        return (String) zza(zzbrc.zzaPU);
    }

    public String getFileExtension() {
        return (String) zza(zzbrc.zzaPV);
    }

    public long getFileSize() {
        return ((Long) zza(zzbrc.zzaPW)).longValue();
    }

    public Date getLastViewedByMeDate() {
        return (Date) zza(zzbrp.zzaQJ);
    }

    public String getMimeType() {
        return (String) zza(zzbrc.zzaQn);
    }

    public Date getModifiedByMeDate() {
        return (Date) zza(zzbrp.zzaQL);
    }

    public Date getModifiedDate() {
        return (Date) zza(zzbrp.zzaQK);
    }

    public String getOriginalFilename() {
        return (String) zza(zzbrc.zzaQo);
    }

    public long getQuotaBytesUsed() {
        return ((Long) zza(zzbrc.zzaQt)).longValue();
    }

    public Date getSharedWithMeDate() {
        return (Date) zza(zzbrp.zzaQM);
    }

    public String getTitle() {
        return (String) zza(zzbrc.zzaQw);
    }

    public String getWebContentLink() {
        return (String) zza(zzbrc.zzaQy);
    }

    public String getWebViewLink() {
        return (String) zza(zzbrc.zzaQz);
    }

    public boolean isEditable() {
        Boolean bool = (Boolean) zza(zzbrc.zzaQc);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isExplicitlyTrashed() {
        Boolean bool = (Boolean) zza(zzbrc.zzaQd);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isFolder() {
        return DriveFolder.MIME_TYPE.equals(getMimeType());
    }

    public boolean isInAppFolder() {
        Boolean bool = (Boolean) zza(zzbrc.zzaQa);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isPinnable() {
        Boolean bool = (Boolean) zza(zzbrx.zzaQQ);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isPinned() {
        Boolean bool = (Boolean) zza(zzbrc.zzaQf);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isRestricted() {
        Boolean bool = (Boolean) zza(zzbrc.zzaQh);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isShared() {
        Boolean bool = (Boolean) zza(zzbrc.zzaQi);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isStarred() {
        Boolean bool = (Boolean) zza(zzbrc.zzaQu);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isTrashable() {
        Boolean bool = (Boolean) zza(zzbrc.zzaQl);
        if (bool == null) {
            return true;
        }
        return bool.booleanValue();
    }

    public boolean isTrashed() {
        Boolean bool = (Boolean) zza(zzbrc.zzaQx);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isViewed() {
        Boolean bool = (Boolean) zza(zzbrc.zzaQm);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public abstract <T> T zza(MetadataField<T> metadataField);
}
