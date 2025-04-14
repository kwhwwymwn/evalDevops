FROM openjdk:21
WORKDIR /app
COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

# A JAR file is required to build this image.
# Generate it by running 'mvn clean package' in the project root, or
# use your IDE to run 'Maven Install' manually before building the Docker image.