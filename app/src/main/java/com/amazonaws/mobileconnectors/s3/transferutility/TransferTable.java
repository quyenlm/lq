package com.amazonaws.mobileconnectors.s3.transferutility;

import android.database.sqlite.SQLiteDatabase;

class TransferTable {
    public static final String COLUMN_BUCKET_NAME = "bucket_name";
    public static final String COLUMN_BYTES_CURRENT = "bytes_current";
    public static final String COLUMN_BYTES_TOTAL = "bytes_total";
    public static final String COLUMN_CANNED_ACL = "canned_acl";
    public static final String COLUMN_CONTENT_MD5 = "content_md5";
    public static final String COLUMN_DATA_RANGE_LAST = "range_last";
    public static final String COLUMN_DATA_RANGE_START = "range_start";
    public static final String COLUMN_ETAG = "etag";
    public static final String COLUMN_EXPIRATION_TIME_RULE_ID = "expiration_time_rule_id";
    public static final String COLUMN_FILE = "file";
    public static final String COLUMN_FILE_OFFSET = "file_offset";
    public static final String COLUMN_HEADER_CACHE_CONTROL = "header_cache_control";
    public static final String COLUMN_HEADER_CONTENT_DISPOSITION = "header_content_disposition";
    public static final String COLUMN_HEADER_CONTENT_ENCODING = "header_content_encoding";
    public static final String COLUMN_HEADER_CONTENT_LANGUAGE = "header_content_language";
    public static final String COLUMN_HEADER_CONTENT_TYPE = "header_content_type";
    public static final String COLUMN_HEADER_EXPIRE = "header_expire";
    public static final String COLUMN_HTTP_EXPIRES_DATE = "http_expires_date";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IS_ENCRYPTED = "is_encrypted";
    public static final String COLUMN_IS_LAST_PART = "is_last_part";
    public static final String COLUMN_IS_MULTIPART = "is_multipart";
    public static final String COLUMN_IS_REQUESTER_PAYS = "is_requester_pays";
    public static final String COLUMN_KEY = "key";
    public static final String COLUMN_MAIN_UPLOAD_ID = "main_upload_id";
    public static final String COLUMN_MULTIPART_ID = "multipart_id";
    public static final String COLUMN_PART_NUM = "part_num";
    public static final String COLUMN_SPEED = "speed";
    public static final String COLUMN_SSE_ALGORITHM = "sse_algorithm";
    public static final String COLUMN_SSE_KMS_KEY = "kms_key";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_USER_METADATA = "user_metadata";
    public static final String COLUMN_VERSION_ID = "version_id";
    private static final String DATABASE_CREATE = "create table awstransfer(_id integer primary key autoincrement, main_upload_id integer, type text not null, state text not null, bucket_name text not null, key text not null, version_id text, bytes_total bigint, bytes_current bigint, speed bigint, is_requester_pays integer, is_encrypted integer, file text not null, file_offset bigint, is_multipart int, part_num int not null, is_last_part integer, multipart_id text, etag text, range_start bigint, range_last bigint, header_content_type text, header_content_language text, header_content_disposition text, header_content_encoding text, header_cache_control text, header_expire text);";
    public static final String TABLE_TRANSFER = "awstransfer";

    TransferTable() {
    }

    public static void onCreate(SQLiteDatabase database, int version) {
        database.execSQL(DATABASE_CREATE);
        onUpgrade(database, 1, version);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        if (oldVersion < 2 && newVersion >= 2) {
            addVersion2Columns(database);
        }
        if (oldVersion < 3 && newVersion >= 3) {
            addVersion3Columns(database);
        }
        if (oldVersion < 4 && newVersion >= 4) {
            addVersion4Columns(database);
        }
    }

    private static void addVersion2Columns(SQLiteDatabase database) {
        database.execSQL("ALTER TABLE awstransfer ADD COLUMN user_metadata text;");
        database.execSQL("ALTER TABLE awstransfer ADD COLUMN expiration_time_rule_id text;");
        database.execSQL("ALTER TABLE awstransfer ADD COLUMN http_expires_date text;");
        database.execSQL("ALTER TABLE awstransfer ADD COLUMN sse_algorithm text;");
        database.execSQL("ALTER TABLE awstransfer ADD COLUMN content_md5 text;");
    }

    private static void addVersion3Columns(SQLiteDatabase database) {
        database.execSQL("ALTER TABLE awstransfer ADD COLUMN kms_key text;");
    }

    private static void addVersion4Columns(SQLiteDatabase database) {
        database.execSQL("ALTER TABLE awstransfer ADD COLUMN canned_acl text;");
    }
}
