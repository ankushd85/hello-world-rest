# Spring Boot Project for REST Demo with helloworld endpoint

Table Of Contents:

- [Introduction](#introduction)
- [Pre Requisites](#pre-requisites)
- [Application Settings](#application-settings)
- [Application Build and Run](#application-build-and-run)
- [Hello World Endpoint](#hello-world-endpoint)
- [External API Call Endpoint](#external-api-call-endpoint)
- [Bonus Stuff](#bonus-stuff)

# Introduction

This is a demo spring boot project to demonstrate REST APIs.

This project features use of REST API endpoints such as GET, POST, PUT, DELETE and running APIs on inbuilt tomcat server.

It also features some addon bonus features like Calling an external API, Getting JSON array with the first N Fibonacci number etc.

# Pre Requisites

  #### 1. Java 1.8.0_161 or higher
  1. Download it from http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
  2. Install and check java -version to verify an installation

  #### 2. Maven 3.3.9 or higher.
  1. Download it from https://maven.apache.org/download.cgi
  2. Installation instruction https://maven.apache.org/install.html
  3. Install and check mvn -v to verify an installation

  #### 3. CONTAINERS (If you love Containers/Docker): Supports Docker 17.12.0-ce or higher
  1. Download Docker from https://store.docker.com/search?type=edition&offering=community
  2. Install and check docker -v to verify an installation

# Application Settings

  #### 1. Git Clone:

              git clone https://github.com/ankushd85/hello-world-rest.git

  #### 2. application.properties settings:
  1. Open/Import project in your favorite Text Editor or IDE (Optional)
  2. Inside hello-world directory, go to src/main/resources and edit below properties in application.properties if required.
      1. server.port by default its 8090
      2. proxy settings if you are behind proxy

              proxy-port=your-proxy-port

              proxy-url=proxyurl.yourorg.com

              user=your-user-id

              password=your-password

              is-proxy=true  by default its false. i.e. if this flag is false, application will not use proxy configuration

# Application Build and Run

  #### 1. Build and Run an Application:

Post git clone mentioned in above step and modifying application.properties, run below commands in project root folder inside terminal/git bash

  a. Clean and build project using maven

      mvn clean installation

  b. Build package artifact using maven

      mvn package

  c. Run application using java command

      java -jar target/hello-world-0.0.1-SNAPSHOT.jar

  #### 2. (OPTIONAL) Build Docker image and run application in docker container (OPTIONAL for Container lovers)

If you are passionate about containers and want to run an application as container on local host or any remote host follow
below docker steps.

  a. Build docker image. Execute below command in hello-world project root directory.(Don't miss "." in the end of docker command)

      docker build -t hello_world_rest .

  b. Run docker container for hello_world_rest image you just built. Make sure you are using same port you mentioned in application.properties in [Application Settings] section.

      docker run -p 8090:8090 hello_world_rest

  c. Optional docker run command to run container in detached mode if you do not want to see logs in terminal window.

      docker run -d -p 8090:8090 hello_world_rest

# Hello World Endpoint

If you reached this point, Hurray!!! Its time to TEST. You would require Curl or Postman to test api endpoints.

Curl can be downloaded from https://curl.haxx.se/download.html. Mac OS user can install using- brew install curl
Or you can download Postman from https://www.getpostman.com/apps

  #### Curl Commands:

Below users are present in Database already for testing.

{202, "Tylor Swift"}

{303, "Ankush Dhameeja"}

  #### 1. GET user

    curl -H "Accept: application/json" -H "Content-Type: application/json" http://localhost:8090/myorg/mylob/helloworld/v1/users/202

  #### 2. GET all users

    curl -H "Accept: application/json" -H "Content-Type: application/json" http://localhost:8090/myorg/mylob/helloworld/v1/users

  #### 3. POST add new user. Responds User json object with user Id.

    curl -d '{"name":"Marilyn Robertson"}' -H "Accept: application/json" -H "Content-Type: application/json" -X POST http://localhost:8090/myorg/mylob/helloworld/v1/users


  Verify by calling GET call and replacing userid 202 with the one you got in POST response.

    curl -H "Accept: application/json" -H "Content-Type: application/json" http://localhost:8090/myorg/mylob/helloworld/v1/users/202

  #### 4. PUT update user. Optional - Replace userid 202 with the one you want to update. You can use the user id you got in POST response.

    curl -d '{"name":"Marilyn Cooper"}' -H "Accept: application/json" -H "Content-Type: application/json" -X PUT http://localhost:8090/myorg/mylob/helloworld/v1/users/202


  Verify by calling GET call and replacing userid 202 with the one you just modified in PUT call.

    curl -H "Accept: application/json" -H "Content-Type: application/json" http://localhost:8090/myorg/mylob/helloworld/v1/users/202

  #### 5. DELETE user. Optional - Replace userid 202 with the one you want to update. You can use the user id you got in POST response.

    curl -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8090/myorg/mylob/helloworld/v1/users/202


  Verify by calling GET on user id you use in DELETE call

    curl -H "Accept: application/json" -H "Content-Type: application/json" http://localhost:8090/myorg/mylob/helloworld/v1/users/202


# External API Call Endpoint

  #### 1. GET - EXTERNAL API CALL

  Getting data from external api service https://jsonplaceholder.typicode.com/posts

    curl -H "Accept: application/json" -H "Content-Type: application/json" http://localhost:8090/myorg/mylob/helloworld/external/apis/v1/posts

# Bonus Stuff

  #### 2. GET - FIBONACCI for N.

  Get call to fetch JSON array with the first N Fibonacci numbers. Replace 8 in the end with number you want to fetch fibonacci array for.

    curl -H "Accept: application/json" -H "Content-Type: application/json" http://localhost:8090/myorg/mylob/helloworld/bonus/items/v1/fibonacci/8
