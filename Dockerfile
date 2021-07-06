FROM adoptopenjdk:11.0.11_9-jdk-hotspot
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} ./app/weather.jar
ENTRYPOINT ["java", "-jar", "app/weather.jar"]