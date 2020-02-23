package com.tencent.component.db.sqlite;

import com.tencent.component.annotation.PluginApi;
import com.tencent.component.db.EntityContext;
import com.tencent.component.db.entity.TableEntity;
import com.tencent.tp.a.h;
import java.util.ArrayList;
import java.util.List;

@PluginApi(since = 4)
public class Selector {
    protected int limit = 0;
    protected int offset = 0;
    protected List<OrderBy> orderByList;
    protected WhereBuilder whereBuilder;

    private Selector() {
    }

    @PluginApi(since = 4)
    public static Selector create() {
        return new Selector();
    }

    @PluginApi(since = 4)
    public Selector where(WhereBuilder whereBuilder2) {
        this.whereBuilder = whereBuilder2;
        return this;
    }

    @PluginApi(since = 4)
    public Selector where(String columnName, String op, Object value) {
        this.whereBuilder = WhereBuilder.create(columnName, op, value);
        return this;
    }

    @PluginApi(since = 4)
    public Selector and(String columnName, String op, Object value) {
        this.whereBuilder.and(columnName, op, value);
        return this;
    }

    @PluginApi(since = 4)
    public Selector and(WhereBuilder where) {
        this.whereBuilder.expr("AND (" + where.toString() + h.b);
        return this;
    }

    @PluginApi(since = 4)
    public Selector or(String columnName, String op, Object value) {
        this.whereBuilder.or(columnName, op, value);
        return this;
    }

    @PluginApi(since = 4)
    public Selector or(WhereBuilder where) {
        this.whereBuilder.expr("OR (" + where.toString() + h.b);
        return this;
    }

    @PluginApi(since = 4)
    public Selector expr(String expr) {
        this.whereBuilder.expr(expr);
        return this;
    }

    @PluginApi(since = 4)
    public Selector expr(String columnName, String op, Object value) {
        this.whereBuilder.expr(columnName, op, value);
        return this;
    }

    @PluginApi(since = 4)
    public Selector orderBy(String columnName) {
        if (this.orderByList == null) {
            this.orderByList = new ArrayList(2);
        }
        this.orderByList.add(new OrderBy(columnName));
        return this;
    }

    @PluginApi(since = 4)
    public Selector orderBy(String columnName, boolean desc) {
        if (this.orderByList == null) {
            this.orderByList = new ArrayList(2);
        }
        this.orderByList.add(new OrderBy(columnName, desc));
        return this;
    }

    @PluginApi(since = 4)
    public Selector limit(int limit2) {
        this.limit = limit2;
        return this;
    }

    @PluginApi(since = 4)
    public Selector offset(int offset2) {
        this.offset = offset2;
        return this;
    }

    public String buildSql(Class<?> entityType, EntityContext entityContext) {
        String tableName = TableEntity.get(entityType, entityContext).getTableName();
        StringBuilder result = new StringBuilder();
        result.append("SELECT ");
        result.append("*");
        result.append(" FROM ").append(tableName);
        if (this.whereBuilder != null && this.whereBuilder.getWhereItemSize() > 0) {
            result.append(" WHERE ").append(this.whereBuilder.toString());
        }
        if (this.orderByList != null) {
            for (int i = 0; i < this.orderByList.size(); i++) {
                result.append(" ORDER BY ").append(this.orderByList.get(i).toString());
            }
        }
        if (this.limit > 0) {
            result.append(" LIMIT ").append(this.limit);
            result.append(" OFFSET ").append(this.offset);
        }
        return result.toString();
    }

    protected class OrderBy {
        private String columnName;
        private boolean desc;

        public OrderBy(String columnName2) {
            this.columnName = columnName2;
        }

        public OrderBy(String columnName2, boolean desc2) {
            this.columnName = columnName2;
            this.desc = desc2;
        }

        public String toString() {
            return this.columnName + (this.desc ? " DESC" : " ASC");
        }
    }
}
