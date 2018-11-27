package com.networkerr.app.models;

import com.networkerr.core.annotations.SqlSchemaColumn;
import com.networkerr.core.annotations.SqlSchemaTable;
import com.networkerr.core.database.Model;
import com.networkerr.core.database.SQLFlags;
import com.networkerr.core.database.SQLTypes;

@SqlSchemaTable(table="companies", references = "companyProfiles", foreignKey = "CompanyProfileId")
public class CompanyModel extends Model {
    @SqlSchemaColumn(column="id", dataType = SQLTypes.INTEGER, properties = {SQLFlags.PRIMARY_KEY, SQLFlags.NOT_NULL, SQLFlags.AUTO_INCREMENT})
    private long id;
    @SqlSchemaColumn(column = "CompanyDomain", dataType = SQLTypes.VARCHAR64)
    private String domain;
    @SqlSchemaColumn(column = "CompanyName", dataType = SQLTypes.VARCHAR128)
    private String name;
    @SqlSchemaColumn(column = "CompanyProfileId", dataType = SQLTypes.INTEGER, properties = {SQLFlags.NOT_NULL})
    private long profileId;
    public CompanyModel() {
        String tableSql = this.createTableBegin("companies")
            .createTableColumn("CompanyId", false, SQLTypes.INTEGER, SQLFlags.NOT_NULL, SQLFlags.AUTO_INCREMENT, SQLFlags.PRIMARY_KEY)
            .createTableColumn("CompanyDomain", false, SQLTypes.VARCHAR32)
            .createTableColumn("CompanyName", false, SQLTypes.VARCHAR64)
            .createTableColumn("CompanyProfileId", true, SQLTypes.INTEGER, SQLFlags.NOT_NULL)
            .createTableEnd()
            .getStatement();
//        this.db.execute(tableSql);
    }
}
