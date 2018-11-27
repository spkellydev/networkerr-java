#!/bin/bash
# Export database to version controlled file
docker exec mysql mysqldump -u root --password=root networkerr > db/backup.sql;
#tear down docker images
docker-compose down;