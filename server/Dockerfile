# Use an official Java runtime as the base image
FROM openjdk:11

# Set the working directory in the container
WORKDIR /app

# Copy the Java project files into the container
COPY ./src /app/src

# Compile the Java source code
RUN javac -d . src/main/java/org/example/Server.java

# Set the entrypoint command to run the Java application
CMD ["java", "org.example.Server"]
