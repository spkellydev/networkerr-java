package com.networkerr.core.database;

public enum SQLTypes {
    INTEGER("INTEGER"),
    VARCHAR32("VARCHAR(32)"),
    VARCHAR64("VARCHAR(64)"),
    VARCHAR128("VARCHAR(128)"),
    VARCHAR256("VARCHAR(256)"),
    TEXT("TEXT");

    private String field;
    private SQLTypes(String field) { this.field = field; }
    public String toString() {
        return this.field;
    }
}
