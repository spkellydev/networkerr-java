package com.networkerr.core.database;

@Deprecated
public class Model extends MySqlReaderWriter {
    protected MySqlORM db = MySqlORM.getInstance();
    public Model() {
        this.db.getMapFromMethods();
    }
}
