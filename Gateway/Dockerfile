FROM openjdk:11
ADD target/Gateway-0.0.1-SNAPSHOT.jar Gateway-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-Deureka.client.serviceUrl.defaultZone=http://eureka:8761/eureka", "-Dredis.host=rds", "-jar", "/Gateway-0.0.1-SNAPSHOT.jar"]