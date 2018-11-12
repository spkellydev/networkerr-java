package com.networkerr.core.database;

public enum SQLForeignKeyFlags {
    REFERENCES("REFERENCES"), DELETE_CASCADE("ON DELETE CASCADE");

    private final String field;
    SQLForeignKeyFlags(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return this.field;
    }
}
