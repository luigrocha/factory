FROM adoptopenjdk/openjdk11:alpine-jre

EXPOSE 8080

RUN mkdir /app

COPY build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]