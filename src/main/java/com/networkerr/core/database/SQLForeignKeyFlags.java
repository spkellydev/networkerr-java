package com.networkerr.core.database;
@Deprecated
public class SQLForeignKeyFlags {
    public static final String REFERENCES = "REFERENCES";
    public static final String DELETE_CASCADE = "ON DELETE CASCADE";

    private final String field;
    SQLForeignKeyFlags(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return this.field;
    }
}
