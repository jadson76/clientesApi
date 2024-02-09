FROM openjdk:17
VOLUME /tmp
EXPOSE 8082
ARG JAR_FILE=target/clientesApi-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} api.jar
ENTRYPOINT ["java", "-jar", "/api.jar"]