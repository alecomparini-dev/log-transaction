FROM openjdk:11

EXPOSE 8080

COPY target/logtransaction-0.0.1-SNAPSHOT.jar server-1.0.0.jar

ENTRYPOINT ["java","-jar","/server-1.0.0.jar"]