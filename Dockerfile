FROM amazoncorretto:17

ARG JAR_FILE_PATH=/songgpt/build/libs/*.jar

COPY $JAR_FILE_PATH app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]