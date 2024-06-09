FROM openjdk:17-alpine
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} /super-position-admin.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "/super-position-admin.jar"]