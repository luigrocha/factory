FROM openjdk:11.0.15

EXPOSE 8080

RUN mkdir /app

COPY /build/libs/cp-core-ws-1.0.0.jar /app/app.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]
