FROM openjdk:11
EXPOSE 8080
ARG JAR_TARGET=target/*.jar
COPY ${JAR_TARGET} role-based-docker-app.jar
ENTRYPOINT ["java", "-jar", "/role-based-docker-app.jar"]

