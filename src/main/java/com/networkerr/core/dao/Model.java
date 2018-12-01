package com.networkerr.core.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.networkerr.core.routers.JsonMiddleware;
import io.netty.handler.codec.http.FullHttpRequest;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public abstract class Model<T> extends JsonMiddleware implements Schema {
    private String tableName;
    private String primaryKey;
    protected Database db = Database.getInstance();
    protected Model(String tableName, String primaryKey) {
        this.setTableName(tableName);
        this.setPrimaryKey(primaryKey);
    }

    protected String jsonStringify(Object model) {
        return this.writeModelAsJson(model);
    }
    protected JsonNode getMappable(FullHttpRequest msg) {
        Type cls = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        String payload = null;
        Class<?> schema = null;
        try {
            schema = Class.forName(cls.getTypeName());
            payload = this.mapToJson(msg, schema);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assert payload != null;
        try {
            return this.mapper.readTree(payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void execute(String sql) {
        this.db.execute(sql);
    }

    protected abstract Optional<T> get(long id);

    protected abstract List<T> getAll();

    protected abstract void save(T t);

    protected abstract void update(T t, String[] params);

    protected abstract void delete(T t);

    protected abstract T schema();

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

    public String toString() {
        return this.jsonStringify(this);
    }
}
