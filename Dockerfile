FROM java:8
# communicate over port 8080
# run `docker-machine ip default` to get IP of the docker container
# add that IP to hosts file
EXPOSE 8080
# add artifact to app directory
ADD /out/artifacts/networkerr_jar/networkerr.jar /app/app.jar
# start server
ENTRYPOINT ["java", "-jar", "/app/app.jar"]