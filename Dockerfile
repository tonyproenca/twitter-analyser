# Start with a base image containing Java runtime
FROM adoptopenjdk/openjdk11:latest
# Add Maintainer Info
LABEL maintainer="antproenca92@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 10222 available to the world outside this container
EXPOSE 10222

# The application's jar file
ARG JAR_FILE=build/libs/twitter-analyser-1.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} twitter-analyser-1.0.1-SNAPSHOT.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","twitter-analyser-1.0.1-SNAPSHOT.jar", "-Dspring.profiles.active=container"]