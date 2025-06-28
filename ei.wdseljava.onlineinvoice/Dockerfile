# Use an official Maven image with OpenJDK 11 as the base image
FROM maven:3.8.4-openjdk-11

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project into the container
COPY . /app

# Install project dependencies using Maven
RUN mvn clean install

# Run TestNG tests with Maven
CMD ["mvn", "test"]