FROM maven:3.6.3-openjdk-17

COPY . /app

WORKDIR /app

COPY src/main/resources/log4j2.xml /app

RUN mvn clean install

CMD ["java", "-Dlog4j.configuration=file:/app/log4j.properties", "-jar", "./target/TrainingBot-1.0-SNAPSHOT.jar"]
