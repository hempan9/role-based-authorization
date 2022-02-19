#FROM adoptopenjdk:11-jre-hotspot
#ARG JAR_FILE=target/role-based-docker-app.jar
#COPY ${JAR_FILE} role-based-docker-app.jar
#ENTRYPOINT ["java", "-jar", "role-based-docker-app.jar"]
FROM maven:3.8.2-jdk-8

WORKDIR .
COPY . .
RUN mvn clean install -Dmaven.test.skip=true

CMD mvn spring-boot:run