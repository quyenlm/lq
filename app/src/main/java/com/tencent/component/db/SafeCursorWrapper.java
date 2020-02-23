package com.tencent.component.db;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.tencent.component.db.exception.DbCacheExceptionHandler;
import com.tencent.component.utils.AssertUtil;

public class SafeCursorWrapper extends CursorWrapper {
    private boolean mClosed;

    private SafeCursorWrapper(Cursor cursor) {
        super(cursor);
        AssertUtil.assertTrue(cursor != null);
    }

    public static SafeCursorWrapper create(Cursor cursor) {
        if (cursor != null) {
            return new SafeCursorWrapper(cursor);
        }
        return null;
    }

    public void close() {
        try {
            super.close();
            this.mClosed = true;
        } catch (Throwable e) {
            handleException(e);
        }
    }

    public boolean isClosed() {
        try {
            return super.isClosed();
        } catch (Throwable e) {
            handleException(e);
            return this.mClosed;
        }
    }

    public int getCount() {
        try {
            return super.getCount();
        } catch (Throwable e) {
            handleException(e);
            return 0;
        }
    }

    public void deactivate() {
        try {
            super.deactivate();
        } catch (Throwable e) {
            handleException(e);
        }
    }

    public boolean moveToFirst() {
        try {
            return super.moveToFirst();
        } catch (Throwable e) {
            handleException(e);
            return false;
        }
    }

    public int getColumnCount() {
        try {
            return super.getColumnCount();
        } catch (Throwable e) {
            handleException(e);
            return 0;
        }
    }

    public int getColumnIndex(String columnName) {
        try {
            return super.getColumnIndex(columnName);
        } catch (Throwable e) {
            handleException(e);
            return -1;
        }
    }

    public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
        try {
            return super.getColumnIndexOrThrow(columnName);
        } catch (Throwable e) {
            handleException(e);
            return -1;
        }
    }

    public String getColumnName(int columnIndex) {
        try {
            return super.getColumnName(columnIndex);
        } catch (Throwable e) {
            handleException(e);
            return null;
        }
    }

    public String[] getColumnNames() {
        try {
            return super.getColumnNames();
        } catch (Throwable e) {
            handleException(e);
            return null;
        }
    }

    public double getDouble(int columnIndex) {
        try {
            return ((Double) processGet(Double.valueOf(super.getDouble(columnIndex)), columnIndex)).doubleValue();
        } catch (Throwable e) {
            handleException(e);
            return FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        }
    }

    public Bundle getExtras() {
        try {
            return super.getExtras();
        } catch (Throwable e) {
            handleException(e);
            return null;
        }
    }

    public float getFloat(int columnIndex) {
        try {
            return ((Float) processGet(Float.valueOf(super.getFloat(columnIndex)), columnIndex)).floatValue();
        } catch (Throwable e) {
            handleException(e);
            return 0.0f;
        }
    }

    public int getInt(int columnIndex) {
        try {
            return ((Integer) processGet(Integer.valueOf(super.getInt(columnIndex)), columnIndex)).intValue();
        } catch (Throwable e) {
            handleException(e);
            return 0;
        }
    }

    public long getLong(int columnIndex) {
        try {
            return ((Long) processGet(Long.valueOf(super.getLong(columnIndex)), columnIndex)).longValue();
        } catch (Throwable e) {
            handleException(e);
            return 0;
        }
    }

    public short getShort(int columnIndex) {
        try {
            return ((Short) processGet(Short.valueOf(super.getShort(columnIndex)), columnIndex)).shortValue();
        } catch (Throwable e) {
            handleException(e);
            return 0;
        }
    }

    public String getString(int columnIndex) {
        try {
            return (String) processGet(super.getString(columnIndex), columnIndex);
        } catch (Throwable e) {
            handleException(e);
            return null;
        }
    }

    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
        try {
            super.copyStringToBuffer(columnIndex, buffer);
        } catch (Throwable e) {
            handleException(e);
        }
    }

    public byte[] getBlob(int columnIndex) {
        try {
            return (byte[]) processGet(super.getBlob(columnIndex), columnIndex);
        } catch (Throwable e) {
            handleException(e);
            return null;
        }
    }

    public boolean getWantsAllOnMoveCalls() {
        try {
            return super.getWantsAllOnMoveCalls();
        } catch (Throwable e) {
            handleException(e);
            return false;
        }
    }

    public boolean isAfterLast() {
        try {
            return super.isAfterLast();
        } catch (Throwable e) {
            handleException(e);
            return false;
        }
    }

    public boolean isBeforeFirst() {
        try {
            return super.isBeforeFirst();
        } catch (Throwable e) {
            handleException(e);
            return false;
        }
    }

    public boolean isFirst() {
        try {
            return super.isFirst();
        } catch (Throwable e) {
            handleException(e);
            return false;
        }
    }

    public boolean isLast() {
        try {
            return super.isLast();
        } catch (Throwable e) {
            handleException(e);
            return false;
        }
    }

    @SuppressLint({"NewApi"})
    public int getType(int columnIndex) {
        try {
            return super.getType(columnIndex);
        } catch (Throwable e) {
            handleException(e);
            return 0;
        }
    }

    public boolean isNull(int columnIndex) {
        try {
            return super.isNull(columnIndex);
        } catch (Throwable e) {
            handleException(e);
            return true;
        }
    }

    public boolean moveToLast() {
        try {
            return super.moveToLast();
        } catch (Throwable e) {
            handleException(e);
            return false;
        }
    }

    public boolean move(int offset) {
        try {
            return super.move(offset);
        } catch (Throwable e) {
            handleException(e);
            return false;
        }
    }

    public boolean moveToPosition(int position) {
        try {
            return super.moveToPosition(position);
        } catch (Throwable e) {
            handleException(e);
            return false;
        }
    }

    public boolean moveToNext() {
        try {
            return super.moveToNext();
        } catch (Throwable e) {
            handleException(e);
            return false;
        }
    }

    public int getPosition() {
        try {
            return super.getPosition();
        } catch (Throwable e) {
            handleException(e);
            return -1;
        }
    }

    public boolean moveToPrevious() {
        try {
            return super.moveToPrevious();
        } catch (Throwable e) {
            handleException(e);
            return false;
        }
    }

    public void registerContentObserver(ContentObserver observer) {
        try {
            super.registerContentObserver(observer);
        } catch (Throwable e) {
            handleException(e);
        }
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        try {
            super.registerDataSetObserver(observer);
        } catch (Throwable e) {
            handleException(e);
        }
    }

    public boolean requery() {
        try {
            return super.requery();
        } catch (Throwable e) {
            handleException(e);
            return false;
        }
    }

    public Bundle respond(Bundle extras) {
        try {
            return super.respond(extras);
        } catch (Throwable e) {
            handleException(e);
            return null;
        }
    }

    public void setNotificationUri(ContentResolver cr, Uri uri) {
        try {
            super.setNotificationUri(cr, uri);
        } catch (Throwable e) {
            handleException(e);
        }
    }

    public void unregisterContentObserver(ContentObserver observer) {
        try {
            super.unregisterContentObserver(observer);
        } catch (Throwable e) {
            handleException(e);
        }
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        try {
            super.unregisterDataSetObserver(observer);
        } catch (Throwable e) {
            handleException(e);
        }
    }

    private <T> T processGet(T value, int columnIndex) {
        return value;
    }

    private void handleException(Throwable e) {
        DbCacheExceptionHandler.getInstance().handleException(e);
    }
}
