#!/bin/bash
# start docker
docker-compose up -d;
# Import database existing export
sleep 45s && cat db/backup.sql | docker exec -i mysql mysql -u root --password=root networkerr;