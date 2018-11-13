package com.networkerr.app.models;

import com.networkerr.core.annotations.SqlSchemaColumn;
import com.networkerr.core.annotations.SqlSchemaTable;
import com.networkerr.core.database.Model;
import com.networkerr.core.database.SQLFlags;
import com.networkerr.core.database.SQLTypes;

@SqlSchemaTable(table="companies")
public class CompanyModel extends Model {
    @SqlSchemaColumn(column="Companies", dataType = SQLTypes.VARCHAR32, properties = {SQLFlags.PRIMARY_KEY, SQLFlags.NOT_NULL})
    String name = "Companies";
    public CompanyModel() {
        String tableSql = this.createTableBegin("companies")
            .createTableColumn("CompanyId", false, SQLTypes.INTEGER, SQLFlags.NOT_NULL, SQLFlags.AUTO_INCREMENT, SQLFlags.PRIMARY_KEY)
            .createTableColumn("CompanyDomain", false, SQLTypes.VARCHAR32)
            .createTableColumn("CompanyName", false, SQLTypes.VARCHAR64)
            .createTableColumn("CompanyProfileId", true, SQLTypes.INTEGER, SQLFlags.NOT_NULL)
            .createTableEnd()
            .getStatement();
        this.db.execute(tableSql);
    }
}
