package com.google.android.gms.cast;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.internal.zzaye;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ApplicationMetadata extends zza {
    public static final Parcelable.Creator<ApplicationMetadata> CREATOR = new zzd();
    private String mName;
    private List<WebImage> zzHC;
    private String zzaoM;
    private List<String> zzaoN;
    private String zzaoO;
    private Uri zzaoP;

    private ApplicationMetadata() {
        this.zzHC = new ArrayList();
        this.zzaoN = new ArrayList();
    }

    ApplicationMetadata(String str, String str2, List<WebImage> list, List<String> list2, String str3, Uri uri) {
        this.zzaoM = str;
        this.mName = str2;
        this.zzHC = list;
        this.zzaoN = list2;
        this.zzaoO = str3;
        this.zzaoP = uri;
    }

    public boolean areNamespacesSupported(List<String> list) {
        return this.zzaoN != null && this.zzaoN.containsAll(list);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ApplicationMetadata)) {
            return false;
        }
        ApplicationMetadata applicationMetadata = (ApplicationMetadata) obj;
        return zzaye.zza(this.zzaoM, applicationMetadata.zzaoM) && zzaye.zza(this.zzHC, applicationMetadata.zzHC) && zzaye.zza(this.mName, applicationMetadata.mName) && zzaye.zza(this.zzaoN, applicationMetadata.zzaoN) && zzaye.zza(this.zzaoO, applicationMetadata.zzaoO) && zzaye.zza(this.zzaoP, applicationMetadata.zzaoP);
    }

    public String getApplicationId() {
        return this.zzaoM;
    }

    public List<WebImage> getImages() {
        return this.zzHC;
    }

    public String getName() {
        return this.mName;
    }

    public String getSenderAppIdentifier() {
        return this.zzaoO;
    }

    public List<String> getSupportedNamespaces() {
        return Collections.unmodifiableList(this.zzaoN);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzaoM, this.mName, this.zzHC, this.zzaoN, this.zzaoO, this.zzaoP});
    }

    public boolean isNamespaceSupported(String str) {
        return this.zzaoN != null && this.zzaoN.contains(str);
    }

    public String toString() {
        int i = 0;
        StringBuilder append = new StringBuilder("applicationId: ").append(this.zzaoM).append(", name: ").append(this.mName).append(", images.count: ").append(this.zzHC == null ? 0 : this.zzHC.size()).append(", namespaces.count: ");
        if (this.zzaoN != null) {
            i = this.zzaoN.size();
        }
        return append.append(i).append(", senderAppIdentifier: ").append(this.zzaoO).append(", senderAppLaunchUrl: ").append(this.zzaoP).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, getApplicationId(), false);
        zzd.zza(parcel, 3, getName(), false);
        zzd.zzc(parcel, 4, getImages(), false);
        zzd.zzb(parcel, 5, getSupportedNamespaces(), false);
        zzd.zza(parcel, 6, getSenderAppIdentifier(), false);
        zzd.zza(parcel, 7, (Parcelable) this.zzaoP, i, false);
        zzd.zzI(parcel, zze);
    }
}
