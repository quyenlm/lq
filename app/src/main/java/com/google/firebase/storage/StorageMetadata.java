package com.google.firebase.storage;

import android.net.Uri;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.abv;
import com.google.android.gms.internal.abz;
import com.google.android.gms.internal.ace;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class StorageMetadata {
    /* access modifiers changed from: private */
    public String mPath;
    /* access modifiers changed from: private */
    public String zzapZ;
    /* access modifiers changed from: private */
    public String zzcoA;
    /* access modifiers changed from: private */
    public String zzcoB;
    /* access modifiers changed from: private */
    public long zzcoC;
    /* access modifiers changed from: private */
    public String zzcoD;
    /* access modifiers changed from: private */
    public String zzcoE;
    /* access modifiers changed from: private */
    public String zzcoF;
    /* access modifiers changed from: private */
    public String zzcoG;
    /* access modifiers changed from: private */
    public String zzcoH;
    /* access modifiers changed from: private */
    public Map<String, String> zzcoI;
    private String[] zzcoJ;
    /* access modifiers changed from: private */
    public StorageReference zzcoe;
    private FirebaseStorage zzcow;
    /* access modifiers changed from: private */
    public String zzcox;
    /* access modifiers changed from: private */
    public String zzcoy;
    /* access modifiers changed from: private */
    public String zzcoz;

    public static class Builder {
        private StorageMetadata zzcoK;
        private boolean zzcoL;

        public Builder() {
            this.zzcoK = new StorageMetadata();
        }

        public Builder(StorageMetadata storageMetadata) {
            this.zzcoK = new StorageMetadata(false);
        }

        private Builder(JSONObject jSONObject) throws JSONException {
            this.zzcoK = new StorageMetadata();
            if (jSONObject != null) {
                zzt(jSONObject);
                this.zzcoL = true;
            }
        }

        Builder(JSONObject jSONObject, StorageReference storageReference) throws JSONException {
            this(jSONObject);
            StorageReference unused = this.zzcoK.zzcoe = storageReference;
        }

        private final void zzt(JSONObject jSONObject) throws JSONException {
            String unused = this.zzcoK.zzcoy = jSONObject.optString("generation");
            String unused2 = this.zzcoK.mPath = jSONObject.optString("name");
            String unused3 = this.zzcoK.zzcox = jSONObject.optString("bucket");
            String unused4 = this.zzcoK.zzcoz = jSONObject.optString("metageneration");
            String unused5 = this.zzcoK.zzcoA = jSONObject.optString("timeCreated");
            String unused6 = this.zzcoK.zzcoB = jSONObject.optString("updated");
            long unused7 = this.zzcoK.zzcoC = jSONObject.optLong("size");
            String unused8 = this.zzcoK.zzcoD = jSONObject.optString("md5Hash");
            this.zzcoK.zzhH(jSONObject.optString("downloadTokens"));
            setContentType(jSONObject.optString(HttpRequestParams.NOTICE_CONTENT_TYPE));
            if (jSONObject.has("metadata")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("metadata");
                Iterator<String> keys = jSONObject2.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    setCustomMetadata(next, jSONObject2.getString(next));
                }
            }
            setCacheControl(jSONObject.optString("cacheControl"));
            setContentDisposition(jSONObject.optString("contentDisposition"));
            setContentEncoding(jSONObject.optString("'contentEncoding"));
            setContentLanguage(jSONObject.optString("'contentLanguage"));
        }

        public StorageMetadata build() {
            return new StorageMetadata(this.zzcoL);
        }

        public Builder setCacheControl(String str) {
            String unused = this.zzcoK.zzcoE = str;
            return this;
        }

        public Builder setContentDisposition(String str) {
            String unused = this.zzcoK.zzcoF = str;
            return this;
        }

        public Builder setContentEncoding(String str) {
            String unused = this.zzcoK.zzcoG = str;
            return this;
        }

        public Builder setContentLanguage(String str) {
            String unused = this.zzcoK.zzcoH = str;
            return this;
        }

        public Builder setContentType(String str) {
            String unused = this.zzcoK.zzapZ = str;
            return this;
        }

        public Builder setCustomMetadata(String str, String str2) {
            if (this.zzcoK.zzcoI == null) {
                Map unused = this.zzcoK.zzcoI = new HashMap();
            }
            this.zzcoK.zzcoI.put(str, str2);
            return this;
        }
    }

    public StorageMetadata() {
        this.mPath = null;
        this.zzcow = null;
        this.zzcoe = null;
        this.zzcox = null;
        this.zzcoy = null;
        this.zzapZ = null;
        this.zzcoz = null;
        this.zzcoA = null;
        this.zzcoB = null;
        this.zzcoD = null;
        this.zzcoE = null;
        this.zzcoF = null;
        this.zzcoG = null;
        this.zzcoH = null;
        this.zzcoI = null;
        this.zzcoJ = null;
    }

    private StorageMetadata(@NonNull StorageMetadata storageMetadata, boolean z) {
        this.mPath = null;
        this.zzcow = null;
        this.zzcoe = null;
        this.zzcox = null;
        this.zzcoy = null;
        this.zzapZ = null;
        this.zzcoz = null;
        this.zzcoA = null;
        this.zzcoB = null;
        this.zzcoD = null;
        this.zzcoE = null;
        this.zzcoF = null;
        this.zzcoG = null;
        this.zzcoH = null;
        this.zzcoI = null;
        this.zzcoJ = null;
        zzbo.zzu(storageMetadata);
        this.mPath = storageMetadata.mPath;
        this.zzcow = storageMetadata.zzcow;
        this.zzcoe = storageMetadata.zzcoe;
        this.zzcox = storageMetadata.zzcox;
        this.zzapZ = storageMetadata.zzapZ;
        this.zzcoE = storageMetadata.zzcoE;
        this.zzcoF = storageMetadata.zzcoF;
        this.zzcoG = storageMetadata.zzcoG;
        this.zzcoH = storageMetadata.zzcoH;
        if (storageMetadata.zzcoI != null) {
            this.zzcoI = new HashMap(storageMetadata.zzcoI);
        }
        this.zzcoJ = storageMetadata.zzcoJ;
        if (z) {
            this.zzcoD = storageMetadata.zzcoD;
            this.zzcoC = storageMetadata.zzcoC;
            this.zzcoB = storageMetadata.zzcoB;
            this.zzcoA = storageMetadata.zzcoA;
            this.zzcoz = storageMetadata.zzcoz;
            this.zzcoy = storageMetadata.zzcoy;
        }
    }

    /* access modifiers changed from: private */
    public final void zzhH(@Nullable String str) {
        if (!TextUtils.isEmpty(str)) {
            this.zzcoJ = str.split(",");
        }
    }

    @Nullable
    public String getBucket() {
        return this.zzcox;
    }

    @Nullable
    public String getCacheControl() {
        return this.zzcoE;
    }

    @Nullable
    public String getContentDisposition() {
        return this.zzcoF;
    }

    @Nullable
    public String getContentEncoding() {
        return this.zzcoG;
    }

    @Nullable
    public String getContentLanguage() {
        return this.zzcoH;
    }

    public String getContentType() {
        return this.zzapZ;
    }

    public long getCreationTimeMillis() {
        return abz.zzhL(this.zzcoA);
    }

    public String getCustomMetadata(@NonNull String str) {
        if (this.zzcoI == null || TextUtils.isEmpty(str)) {
            return null;
        }
        return this.zzcoI.get(str);
    }

    @NonNull
    public Set<String> getCustomMetadataKeys() {
        return this.zzcoI == null ? Collections.emptySet() : this.zzcoI.keySet();
    }

    @Nullable
    public Uri getDownloadUrl() {
        List<Uri> downloadUrls = getDownloadUrls();
        if (downloadUrls == null || downloadUrls.size() <= 0) {
            return null;
        }
        return downloadUrls.get(0);
    }

    @Nullable
    public List<Uri> getDownloadUrls() {
        ArrayList arrayList = new ArrayList();
        if (!(this.zzcoJ == null || this.zzcoe == null)) {
            try {
                String zzv = ace.zzg(this.zzcoe.getStorage().getApp()).zzv(this.zzcoe.zzKP());
                if (!TextUtils.isEmpty(zzv)) {
                    for (String str : this.zzcoJ) {
                        if (!TextUtils.isEmpty(str)) {
                            arrayList.add(Uri.parse(new StringBuilder(String.valueOf(zzv).length() + 17 + String.valueOf(str).length()).append(zzv).append("?alt=media&token=").append(str).toString()));
                        }
                    }
                }
            } catch (RemoteException e) {
                Log.e("StorageMetadata", "Unexpected error getting DownloadUrls.", e);
            }
        }
        return arrayList;
    }

    @Nullable
    public String getGeneration() {
        return this.zzcoy;
    }

    @Nullable
    public String getMd5Hash() {
        return this.zzcoD;
    }

    @Nullable
    public String getMetadataGeneration() {
        return this.zzcoz;
    }

    @Nullable
    public String getName() {
        String path = getPath();
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        int lastIndexOf = path.lastIndexOf(47);
        return lastIndexOf != -1 ? path.substring(lastIndexOf + 1) : path;
    }

    @NonNull
    public String getPath() {
        return this.mPath != null ? this.mPath : "";
    }

    @Nullable
    public StorageReference getReference() {
        if (this.zzcoe != null || this.zzcow == null) {
            return this.zzcoe;
        }
        String bucket = getBucket();
        String path = getPath();
        if (TextUtils.isEmpty(bucket) || TextUtils.isEmpty(path)) {
            return null;
        }
        try {
            return new StorageReference(new Uri.Builder().scheme("gs").authority(bucket).encodedPath(abv.zzhI(path)).build(), this.zzcow);
        } catch (UnsupportedEncodingException e) {
            Log.e("StorageMetadata", new StringBuilder(String.valueOf(bucket).length() + 38 + String.valueOf(path).length()).append("Unable to create a valid default Uri. ").append(bucket).append(path).toString(), e);
            throw new IllegalStateException(e);
        }
    }

    public long getSizeBytes() {
        return this.zzcoC;
    }

    public long getUpdatedTimeMillis() {
        return abz.zzhL(this.zzcoB);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public final JSONObject zzKN() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (getContentType() != null) {
            jSONObject.put(HttpRequestParams.NOTICE_CONTENT_TYPE, getContentType());
        }
        if (this.zzcoI != null) {
            jSONObject.put("metadata", new JSONObject(this.zzcoI));
        }
        if (getCacheControl() != null) {
            jSONObject.put("cacheControl", getCacheControl());
        }
        if (getContentDisposition() != null) {
            jSONObject.put("contentDisposition", getContentDisposition());
        }
        if (getContentEncoding() != null) {
            jSONObject.put("'contentEncoding", getContentEncoding());
        }
        if (getContentLanguage() != null) {
            jSONObject.put("'contentLanguage", getContentLanguage());
        }
        return jSONObject;
    }
}
