FROM java:8
# communicate over port 8080
# run `docker-machine ip default` to get IP of the docker container
# add that IP to hosts file
EXPOSE 8080
# add artifact to app directory
ADD target/networkerr-1.0-SNAPSHOT-jar-with-dependencies.jar /app/app.jar
# start server
CMD ["java", "-jar", "/app/app.jar"]