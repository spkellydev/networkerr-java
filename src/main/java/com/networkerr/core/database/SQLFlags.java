package com.networkerr.core.database;

public enum SQLFlags {
    NOT_NULL("NOT NULL"), AUTO_INCREMENT("AUTO_INCREMENT"), PRIMARY_KEY("PRIMARY KEY");

    private String field;
    private SQLFlags() {}
    private SQLFlags(String field) { this.field = field; }

    @Override
    public String toString() {
        return this.field;
    }
}
