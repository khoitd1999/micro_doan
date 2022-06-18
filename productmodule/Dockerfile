FROM openjdk:11
ADD target/productmodule-0.0.1-SNAPSHOT.jar productmodule-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Deureka.client.serviceUrl.defaultZone=http://eureka:8761/eureka","-Dspring.datasource.url=jdbc:postgresql://postgres:5432/newdb","-Dspring.rabbitmq.host=rabbitmq","-jar","/productmodule-0.0.1-SNAPSHOT.jar"]