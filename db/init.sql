CREATE DATABASE IF NOT EXISTS networkerr;
use networkerr;

CREATE TABLE IF NOT EXISTS CompanyProfiles(
	CompanyProfileId int NOT NULL,
    CompanyLogo varchar(200),
    CompanyEmployeeCount int,
    CompanyOverview text,
    PRIMARY KEY (CompanyProfileId)
);

CREATE TABLE IF NOT EXISTS companies(
	CompanyId INTEGER NOT NULL AUTO_INCREMENT,
	CompanyDomain VARCHAR(32),
	CompanyName VARCHAR(64),
	CompanyProfileId INTEGER NOT NULL,
	PRIMARY KEY (CompanyId),
	FOREIGN KEY (CompanyProfileId) REFERENCES CompanyProfiles(CompanyProfileId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users(
	userId INTEGER NOT NULL AUTO_INCREMENT,
	email VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL,
    PRIMARY KEY (userId)
);
