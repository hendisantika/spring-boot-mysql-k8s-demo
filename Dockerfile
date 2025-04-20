FROM amazoncorretto:21-alpine
LABEL authors="hendisantika"
ADD target/spring-boot-mysql-k8s-demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
