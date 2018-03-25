# Use an image for apline java
FROM anapsix/alpine-java

# Maintained by Ankush Dhameeja
MAINTAINER Ankush Dhameeja

# Copy artifact from target folder to home directory
COPY ./target/hello-world-0.0.1-SNAPSHOT.jar /home/hello-world-0.0.1-SNAPSHOT.jar

# Run Spring boot application
CMD ["java","-jar","/home/hello-world-0.0.1-SNAPSHOT.jar"]
