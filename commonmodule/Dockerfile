FROM openjdk:11
ADD target/commonmodule-0.0.1-SNAPSHOT.jar commonmodule-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Deureka.client.serviceUrl.defaultZone=http://eureka:8761/eureka","-Dspring.datasource.url=jdbc:postgresql://postgres:5432/newdb","-Dspring.rabbitmq.host=rabbitmq","-jar","/commonmodule-0.0.1-SNAPSHOT.jar"]