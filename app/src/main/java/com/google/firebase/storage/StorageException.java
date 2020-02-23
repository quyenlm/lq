package com.google.firebase.storage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.firebase.FirebaseException;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class StorageException extends FirebaseException {
    public static final int ERROR_BUCKET_NOT_FOUND = -13011;
    public static final int ERROR_CANCELED = -13040;
    public static final int ERROR_INVALID_CHECKSUM = -13031;
    public static final int ERROR_NOT_AUTHENTICATED = -13020;
    public static final int ERROR_NOT_AUTHORIZED = -13021;
    public static final int ERROR_OBJECT_NOT_FOUND = -13010;
    public static final int ERROR_PROJECT_NOT_FOUND = -13012;
    public static final int ERROR_QUOTA_EXCEEDED = -13013;
    public static final int ERROR_RETRY_LIMIT_EXCEEDED = -13030;
    public static final int ERROR_UNKNOWN = -13000;
    static IOException zzcos = new IOException("The operation was canceled.");
    private final int mErrorCode;
    private final int zzcot;
    private String zzcou;
    private Throwable zzcov;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    private StorageException(int i, Throwable th, int i2) {
        String str;
        switch (i) {
            case ERROR_CANCELED /*-13040*/:
                str = "The operation was cancelled.";
                break;
            case ERROR_INVALID_CHECKSUM /*-13031*/:
                str = "Object has a checksum which does not match. Please retry the operation.";
                break;
            case ERROR_RETRY_LIMIT_EXCEEDED /*-13030*/:
                str = "The operation retry limit has been exceeded.";
                break;
            case ERROR_NOT_AUTHORIZED /*-13021*/:
                str = "User does not have permission to access this object.";
                break;
            case ERROR_NOT_AUTHENTICATED /*-13020*/:
                str = "User is not authenticated, please authenticate using Firebase Authentication and try again.";
                break;
            case ERROR_QUOTA_EXCEEDED /*-13013*/:
                str = "Quota for bucket exceeded, please view quota on www.firebase.google.com/storage.";
                break;
            case ERROR_PROJECT_NOT_FOUND /*-13012*/:
                str = "Project does not exist.";
                break;
            case ERROR_BUCKET_NOT_FOUND /*-13011*/:
                str = "Bucket does not exist.";
                break;
            case ERROR_OBJECT_NOT_FOUND /*-13010*/:
                str = "Object does not exist at location.";
                break;
            case ERROR_UNKNOWN /*-13000*/:
                str = "An unknown error occurred, please check the HTTP result code and inner exception for server response.";
                break;
            default:
                str = "An unknown error occurred, please check the HTTP result code and inner exception for server response.";
                break;
        }
        this.zzcou = str;
        this.zzcov = th;
        this.mErrorCode = i;
        this.zzcot = i2;
        String str2 = this.zzcou;
        String valueOf = String.valueOf(Integer.toString(this.mErrorCode));
        String valueOf2 = String.valueOf(Integer.toString(this.zzcot));
        Log.e("StorageException", new StringBuilder(String.valueOf(str2).length() + 52 + String.valueOf(valueOf).length() + String.valueOf(valueOf2).length()).append("StorageException has occurred.\n").append(str2).append("\n Code: ").append(valueOf).append(" HttpResult: ").append(valueOf2).toString());
        if (this.zzcov != null) {
            Log.e("StorageException", this.zzcov.getMessage(), this.zzcov);
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    private StorageException(Status status) {
        this(status.isCanceled() ? ERROR_CANCELED : status == Status.zzaBp ? ERROR_RETRY_LIMIT_EXCEEDED : ERROR_UNKNOWN, (Throwable) null, 0);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private StorageException(@android.support.annotation.Nullable java.lang.Throwable r2, int r3) {
        /*
            r1 = this;
            java.io.IOException r0 = zzcos
            if (r2 != r0) goto L_0x000a
            r0 = -13040(0xffffffffffffcd10, float:NaN)
        L_0x0006:
            r1.<init>(r0, r2, r3)
            return
        L_0x000a:
            switch(r3) {
                case -2: goto L_0x0010;
                case 401: goto L_0x0013;
                case 403: goto L_0x0016;
                case 404: goto L_0x0019;
                case 409: goto L_0x001c;
                default: goto L_0x000d;
            }
        L_0x000d:
            r0 = -13000(0xffffffffffffcd38, float:NaN)
            goto L_0x0006
        L_0x0010:
            r0 = -13030(0xffffffffffffcd1a, float:NaN)
            goto L_0x0006
        L_0x0013:
            r0 = -13020(0xffffffffffffcd24, float:NaN)
            goto L_0x0006
        L_0x0016:
            r0 = -13021(0xffffffffffffcd23, float:NaN)
            goto L_0x0006
        L_0x0019:
            r0 = -13010(0xffffffffffffcd2e, float:NaN)
            goto L_0x0006
        L_0x001c:
            r0 = -13031(0xffffffffffffcd19, float:NaN)
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.storage.StorageException.<init>(java.lang.Throwable, int):void");
    }

    @NonNull
    public static StorageException fromErrorStatus(@NonNull Status status) {
        zzbo.zzu(status);
        zzbo.zzaf(!status.isSuccess());
        return new StorageException(status);
    }

    @NonNull
    public static StorageException fromException(@NonNull Throwable th) {
        return fromExceptionAndHttpCode(th, 0);
    }

    @Nullable
    public static StorageException fromExceptionAndHttpCode(@Nullable Throwable th, int i) {
        if (th instanceof StorageException) {
            return (StorageException) th;
        }
        if (!(i == 0 || (i >= 200 && i < 300)) || th != null) {
            return new StorageException(th, i);
        }
        return null;
    }

    public Throwable getCause() {
        if (this.zzcov == this) {
            return null;
        }
        return this.zzcov;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public int getHttpResultCode() {
        return this.zzcot;
    }

    public boolean getIsRecoverableException() {
        return getErrorCode() == -13030;
    }

    public String getMessage() {
        return this.zzcou;
    }
}
