FROM maven:3.5-jdk-8 as BUILD
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app/
RUN mvn -f /usr/src/app/pom.xml clean package -Dmaven.test.skip

FROM openjdk:8-jre
COPY --from=BUILD /usr/src/app/target/producer-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 9090

ENTRYPOINT java -jar /app/app.jar