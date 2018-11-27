package com.networkerr.core.database;
@Deprecated
public class MySqlWriter {
    private String pk = null;    // primary key
    private String fk = null;    // foreign key
    private StringBuilder statementBuilder = new StringBuilder();
    public String getStatement() {
        return this.statementBuilder.toString();
    }
    private StringBuilder tables = new StringBuilder();

    public MySqlWriter createTableBegin(String tableName) {
        this.statementBuilder.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append("(");
        return this;
    }

    public MySqlWriter createTableEnd() {
        boolean fkExists = this.fk != null;
        if(this.pk != null) {
            this.statementBuilder.append(", ");
            this.statementBuilder.append(this.pk);
        }
        if(fkExists) {
            this.statementBuilder.append(", ");
            this.statementBuilder.append(this.fk);
        }
        this.statementBuilder.append(");");
        return this;
    }

    public MySqlWriter createTableColumn(String columnName, boolean isLast, SQLTypes type, String... flags) {
        StringBuilder columnBuilder;
        columnBuilder = new StringBuilder();
        columnBuilder.append(columnName).append(" ").append(type.toString());
        if(flags.length != 0) {
            for(int i = 0; i < flags.length; i++) {
                if(i == flags.length - 1) {
                    if(!flags[i].equals(SQLFlags.PRIMARY_KEY)) {
                        columnBuilder.append(" ");
                        columnBuilder.append(flags[i]);
                    } else if(!isLast) {
                        this.pk = flags[i] + " ("+columnName+")";
                        columnBuilder.append(",");
                    }
                } else {
                    if(flags[i].equals(SQLFlags.PRIMARY_KEY)) {
                        if(!isLast) {
                            if(i == flags.length - 1) {
                                columnBuilder.append(",");
                            }
                        }
                        break;
                    } else {
                        columnBuilder.append(" ");
                        columnBuilder.append(flags[i].toString());
                    }
                }
            }
        } else {
            if(!isLast) {
                columnBuilder.append(",");
            }
        }
        System.out.println(columnBuilder.toString());
        this.statementBuilder.append(columnBuilder.toString().trim());
        return this;
    }

    public MySqlWriter createForeignKey(String columnName, String tableName, String tableCol, String... flags) {
        StringBuilder fkey = new StringBuilder();
        fkey.append("FOREIGN KEY (").append(columnName).append(")").append(" ")
                .append(SQLForeignKeyFlags.REFERENCES).append(" ")
                .append(tableName).append("(")
                .append(tableCol).append(")")
                .append(" ");
        for(int i = 0; i < flags.length; i++) {
            fkey.append(flags[i].toString());
            if(i != flags.length - 1) {
                fkey.append(" ");
            }
        }
        this.fk = fkey.toString();
        return this;
    }

    public void addToTables(String tableQuery) {
        tables.append(tableQuery);
    }

    public String getTableQuery() {
        return tables.toString();
    }
}
