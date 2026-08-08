FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/Cinema-Service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-Dspring.cloud.nacos.config.server-addr=host.docker.internal:8848", "-Dspring.cloud.nacos.discovery.server-addr=host.docker.internal:8848", "-jar", "app.jar"]
