package com.networkerr.core.database;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class Schema {
    String tableName;
    String references;
    String foreignKey;
    Annotation[] fields;

    public Schema(String tableName, String references, String foreignKey, Annotation[] fields) {
        this.tableName = tableName;
        this.references = references;
        this.foreignKey = foreignKey;
        this.fields = fields;
    }

    public String toString() {
        return tableName + " " + references + " " + foreignKey + Arrays.toString(fields);
    }

    public String getTableName() {
        return this.tableName;
    }
}
