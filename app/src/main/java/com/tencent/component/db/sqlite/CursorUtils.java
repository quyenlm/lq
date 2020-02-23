package com.tencent.component.db.sqlite;

import android.database.Cursor;
import android.text.TextUtils;
import com.tencent.component.db.EntityContext;
import com.tencent.component.db.entity.ColumnEntity;
import com.tencent.component.db.entity.IdEntity;
import com.tencent.component.db.entity.TableEntity;
import com.tencent.component.utils.log.LogUtil;
import java.util.ArrayList;
import java.util.Iterator;

public class CursorUtils {
    private static final String TAG = "CursorUtils";

    public static <T> T getEntity(Cursor cursor, Class<T> entityType, EntityContext entityContext) {
        T t;
        if (cursor == null) {
            return null;
        }
        try {
            TableEntity table = TableEntity.get(entityType, entityContext);
            T entity = null;
            if (table.isDynamicClass()) {
                String className = cursor.getString(cursor.getColumnIndex(ColumnEntity.RESERVED_COLUMN_DYNAMIC_CLASS));
                if (!TextUtils.isEmpty(className)) {
                    ClassLoader classLoader = entityContext.getClassLoader();
                    if (classLoader == null) {
                        classLoader = CursorUtils.class.getClassLoader();
                    }
                    if (classLoader != null) {
                        entity = classLoader.loadClass(className).newInstance();
                    }
                }
            }
            if (entity == null) {
                t = entityType.newInstance();
            } else {
                t = entity;
            }
            ArrayList<IdEntity> idEntities = table.getIdList();
            if (idEntities != null) {
                Iterator i$ = idEntities.iterator();
                while (i$.hasNext()) {
                    IdEntity id = i$.next();
                    id.setValue2Entity(t, cursor, cursor.getColumnIndex(id.getColumnName()), entityContext.getClassLoader());
                }
            }
            int columnCount = cursor.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                ColumnEntity column = table.columnMap.get(cursor.getColumnName(i));
                if (column != null) {
                    column.setValue2Entity(t, cursor, i, entityContext.getClassLoader());
                }
            }
            return t;
        } catch (Throwable e) {
            LogUtil.e(TAG, e.getMessage(), e);
            return null;
        }
    }
}
