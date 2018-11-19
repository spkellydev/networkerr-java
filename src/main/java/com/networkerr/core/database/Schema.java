package com.networkerr.core.database;

public class Schema {
    String tableName;
    String references;
    String foreignKey;

    public Schema(String tableName, String references, String foreignKey) {
        this.tableName = tableName;
        this.references = references;
        this.foreignKey = foreignKey;
    }

    public String toString() {
        return tableName + " " + references + " " + foreignKey;
    }

    public String getTableName() {
        return this.tableName;
    }
}
