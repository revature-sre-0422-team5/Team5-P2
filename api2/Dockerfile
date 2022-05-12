FROM maven:3.8.5-jdk-8
  
COPY ./ ./

RUN mvn clean install -DskipTests

CMD ["java", "-jar", "./target/mail-0.0.1-SNAPSHOT.jar"]
