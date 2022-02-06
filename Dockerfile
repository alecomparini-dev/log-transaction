FROM openjdk:11

EXPOSE 8080

COPY target/logtransaction-0.0.1-SNAPSHOT.jar server-1.0.0.jar
CMD keytool -genkey -v -keystore foo.jks -alias jwtsigning -keyalg RSA -keysize 2048 -validity 10000 -storepass password

ENTRYPOINT ["java","-jar","/server-1.0.0.jar"]