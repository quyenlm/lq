package com.tencent.component.db.sqlite;

import com.tencent.component.db.util.ColumnUtils;
import java.util.LinkedList;

public class SqlInfo {
    private LinkedList<Object> bindArgs;
    private String sql;

    public SqlInfo() {
    }

    public SqlInfo(String sql2) {
        this.sql = sql2;
    }

    public SqlInfo(String sql2, Object... bindArgs2) {
        this.sql = sql2;
        addBindArgs(bindArgs2);
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql2) {
        this.sql = sql2;
    }

    public LinkedList<Object> getBindArgs() {
        return this.bindArgs;
    }

    public Object[] getBindArgsAsArray() {
        if (this.bindArgs != null) {
            return this.bindArgs.toArray();
        }
        return null;
    }

    public String[] getBindArgsAsStrArray() {
        if (this.bindArgs == null) {
            return null;
        }
        String[] strings = new String[this.bindArgs.size()];
        for (int i = 0; i < this.bindArgs.size(); i++) {
            Object value = this.bindArgs.get(i);
            strings[i] = value == null ? null : value.toString();
        }
        return strings;
    }

    public void addBindArg(Object arg) {
        if (this.bindArgs == null) {
            this.bindArgs = new LinkedList<>();
        }
        this.bindArgs.add(ColumnUtils.convert2DbColumnValueIfNeeded(arg));
    }

    /* access modifiers changed from: package-private */
    public void addBindArgWithoutConverter(Object arg) {
        if (this.bindArgs == null) {
            this.bindArgs = new LinkedList<>();
        }
        this.bindArgs.add(arg);
    }

    public void addBindArgs(Object... bindArgs2) {
        if (bindArgs2 != null) {
            for (Object arg : bindArgs2) {
                addBindArg(arg);
            }
        }
    }
}
