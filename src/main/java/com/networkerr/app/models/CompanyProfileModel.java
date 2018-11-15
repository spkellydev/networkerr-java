package com.networkerr.app.models;

import com.networkerr.core.annotations.SqlSchemaColumn;
import com.networkerr.core.annotations.SqlSchemaTable;
import com.networkerr.core.database.SQLFlags;
import com.networkerr.core.database.SQLTypes;

@SqlSchemaTable(table="companyProfiles")
public class CompanyProfileModel {
    @SqlSchemaColumn(column="CompanyProfileId", dataType = SQLTypes.INTEGER, properties = {SQLFlags.PRIMARY_KEY, SQLFlags.NOT_NULL})
    private long id;
    @SqlSchemaColumn(column = "CompanyLogo", dataType = SQLTypes.VARCHAR256)
    private String domain;
    @SqlSchemaColumn(column = "CompanyEmployeeCount", dataType = SQLTypes.INTEGER)
    private String name;
    @SqlSchemaColumn(column = "CompanyOverview", dataType = SQLTypes.TEXT)
    private long profileId;
}
