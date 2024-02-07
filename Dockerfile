FROM openjdk:17
VOLUME /tmp
EXPOSE 8082
COPY target/clientesApi-0.0.1-SNAPSHOT.jar clientesApiApi-0.0.1-SNAPSHOT.jar
#ARG JAR_FILE=target/dockerApi-0.0.1-SNAPSHOT.jar
#ADD ${JAR_FILE} dockerApi.jar
ENTRYPOINT ["java", "-jar", "clientesApi-0.0.1-SNAPSHOT.jar"]