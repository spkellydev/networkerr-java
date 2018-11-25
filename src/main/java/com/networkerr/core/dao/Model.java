package com.networkerr.core.dao;

import java.util.List;
import java.util.Optional;

public abstract class Model<T> implements Schema {
    private String tableName;
    private String primaryKey;
    protected Database db = Database.getInstance();
    protected Model(String tableName, String primaryKey) {
        this.setTableName(tableName);
        this.setPrimaryKey(primaryKey);
    }

    protected void execute(String sql) {
        this.db.execute(sql);
    }

    protected abstract Optional<T> get(long id);

    protected abstract List<T> getAll();

    protected abstract void save(T t);

    protected abstract void update(T t, String[] params);

    protected abstract void delete(T t);

    protected void setTableName(String tableName) {
        this.tableName = tableName;
    }
    protected String getTableName() {
        return this.tableName;
    }

    protected void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    protected String getPrimaryKey() {
        return this.primaryKey;
    }
}
