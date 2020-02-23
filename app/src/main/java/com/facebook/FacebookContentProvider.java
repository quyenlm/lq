package com.facebook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.util.Pair;
import com.appsflyer.share.Constants;
import com.facebook.internal.NativeAppCallAttachmentStore;
import com.google.android.gms.drive.DriveFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

public class FacebookContentProvider extends ContentProvider {
    private static final String ATTACHMENT_URL_BASE = "content://com.facebook.app.FacebookContentProvider";
    private static final String INVALID_FILE_NAME = "..";
    private static final String TAG = FacebookContentProvider.class.getName();

    public static String getAttachmentUrl(String applicationId, UUID callId, String attachmentName) {
        return String.format("%s%s/%s/%s", new Object[]{ATTACHMENT_URL_BASE, applicationId, callId.toString(), attachmentName});
    }

    public boolean onCreate() {
        return true;
    }

    public Cursor query(Uri uri, String[] strings, String s, String[] strings2, String s2) {
        return null;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        Pair<UUID, String> callIdAndAttachmentName = parseCallIdAndAttachmentName(uri);
        if (callIdAndAttachmentName == null) {
            throw new FileNotFoundException();
        }
        try {
            File file = NativeAppCallAttachmentStore.openAttachment((UUID) callIdAndAttachmentName.first, (String) callIdAndAttachmentName.second);
            if (file != null) {
                return ParcelFileDescriptor.open(file, DriveFile.MODE_READ_ONLY);
            }
            throw new FileNotFoundException();
        } catch (FileNotFoundException exception) {
            Log.e(TAG, "Got unexpected exception:" + exception);
            throw exception;
        }
    }

    /* access modifiers changed from: package-private */
    public Pair<UUID, String> parseCallIdAndAttachmentName(Uri uri) {
        try {
            String[] parts = uri.getPath().substring(1).split(Constants.URL_PATH_DELIMITER);
            String callIdString = parts[0];
            String attachmentName = parts[1];
            if (!INVALID_FILE_NAME.contentEquals(callIdString) && !INVALID_FILE_NAME.contentEquals(attachmentName)) {
                return new Pair<>(UUID.fromString(callIdString), attachmentName);
            }
            throw new Exception();
        } catch (Exception e) {
            return null;
        }
    }
}
