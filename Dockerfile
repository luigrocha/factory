FROM adoptopenjdk/openjdk11:alpine-jre

VOLUME /tmp

EXPOSE 8080

ADD build/libs/*.jar app.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
