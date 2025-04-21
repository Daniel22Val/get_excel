FROM openjdk:17-jdk

#set the working directory
WORKDIR /app

#copy the application jar
COPY target/live-0.0.1-SNAPSHOT.jar /app/live-0.0.1-SNAPSHOT.jar

#port
EXPOSE 8080

#run application
ENTRYPOINT ["java", "-jar", "live-0.0.1-SNAPSHOT.jar"]
