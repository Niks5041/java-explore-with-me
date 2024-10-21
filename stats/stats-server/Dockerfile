FROM eclipse-temurin:21-jre-jammy
WORKDIR /stats
COPY target/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]
