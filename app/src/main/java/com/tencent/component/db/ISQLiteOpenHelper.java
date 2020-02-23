package com.tencent.component.db;

import android.content.Context;
import com.tencent.component.db.EntityManager;

public interface ISQLiteOpenHelper {
    ISQLiteDatabase getReadableDatabase();

    ISQLiteDatabase getWritableDatabase();

    void init(Context context, String str, int i);

    void init(Context context, String str, int i, EntityManager.UpdateListener updateListener);
}
