FROM java:8
EXPOSE 8080
ADD /out/artifacts/networkerr_jar/networkerr.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]