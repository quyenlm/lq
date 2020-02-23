package com.amazonaws.mobileconnectors.s3.transferutility;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.appsflyer.share.Constants;

class TransferDBBase {
    private static final String BASE_PATH = "transfers";
    private static final int TRANSFERS = 10;
    private static final int TRANSFER_ID = 20;
    private static final int TRANSFER_PART = 30;
    private static final int TRANSFER_STATE = 40;
    private final Uri contentUri;
    private final Context context;
    private final TransferDatabaseHelper databaseHelper = new TransferDatabaseHelper(this.context);
    private final UriMatcher uriMatcher;

    public TransferDBBase(Context context2) {
        this.context = context2;
        String mAuthority = context2.getApplicationContext().getPackageName();
        this.contentUri = Uri.parse("content://" + mAuthority + Constants.URL_PATH_DELIMITER + BASE_PATH);
        this.uriMatcher = new UriMatcher(-1);
        this.uriMatcher.addURI(mAuthority, BASE_PATH, 10);
        this.uriMatcher.addURI(mAuthority, "transfers/#", 20);
        this.uriMatcher.addURI(mAuthority, "transfers/part/#", 30);
        this.uriMatcher.addURI(mAuthority, "transfers/state/*", 40);
    }

    public void closeDBHelper() {
        this.databaseHelper.close();
    }

    public Uri getContentUri() {
        return this.contentUri;
    }

    public Uri insert(Uri uri, ContentValues values) {
        int uriType = this.uriMatcher.match(uri);
        SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        switch (uriType) {
            case 10:
                return Uri.parse("transfers/" + db.insert(TransferTable.TABLE_TRANSFER, (String) null, values));
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TransferTable.TABLE_TRANSFER);
        switch (this.uriMatcher.match(uri)) {
            case 10:
                queryBuilder.appendWhere("part_num=0");
                break;
            case 20:
                queryBuilder.appendWhere("_id=" + uri.getLastPathSegment());
                break;
            case 30:
                queryBuilder.appendWhere("main_upload_id=" + uri.getLastPathSegment());
                break;
            case 40:
                queryBuilder.appendWhere("state=");
                queryBuilder.appendWhereEscapeString(uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return queryBuilder.query(this.databaseHelper.getWritableDatabase(), projection, selection, selectionArgs, (String) null, (String) null, sortOrder);
    }

    public synchronized int update(Uri uri, ContentValues values, String whereClause, String[] whereArgs) {
        int rowsUpdated;
        int uriType = this.uriMatcher.match(uri);
        SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        switch (uriType) {
            case 10:
                rowsUpdated = db.update(TransferTable.TABLE_TRANSFER, values, whereClause, whereArgs);
                break;
            case 20:
                String id = uri.getLastPathSegment();
                if (!TextUtils.isEmpty(whereClause)) {
                    rowsUpdated = db.update(TransferTable.TABLE_TRANSFER, values, "_id=" + id + " and " + whereClause, whereArgs);
                    break;
                } else {
                    rowsUpdated = db.update(TransferTable.TABLE_TRANSFER, values, "_id=" + id, (String[]) null);
                    break;
                }
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return rowsUpdated;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = this.uriMatcher.match(uri);
        SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        switch (uriType) {
            case 10:
                return db.delete(TransferTable.TABLE_TRANSFER, selection, selectionArgs);
            case 20:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    return db.delete(TransferTable.TABLE_TRANSFER, "_id=" + id, (String[]) null);
                }
                return db.delete(TransferTable.TABLE_TRANSFER, "_id=" + id + " and " + selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    public int bulkInsert(Uri uri, ContentValues[] valuesArray) {
        int uriType = this.uriMatcher.match(uri);
        SQLiteDatabase db = this.databaseHelper.getWritableDatabase();
        int mainUploadId = 0;
        switch (uriType) {
            case 10:
                try {
                    db.beginTransaction();
                    mainUploadId = (int) db.insertOrThrow(TransferTable.TABLE_TRANSFER, (String) null, valuesArray[0]);
                    for (int i = 1; i < valuesArray.length; i++) {
                        valuesArray[i].put(TransferTable.COLUMN_MAIN_UPLOAD_ID, Integer.valueOf(mainUploadId));
                        db.insertOrThrow(TransferTable.TABLE_TRANSFER, (String) null, valuesArray[i]);
                    }
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    Log.e(TransferDBBase.class.getSimpleName(), "bulkInsert error : " + e.getMessage());
                } finally {
                    db.endTransaction();
                }
                return mainUploadId;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
