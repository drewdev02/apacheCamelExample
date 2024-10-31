FROM maven:3.9.4-amazoncorretto-20-al2023 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:20-jdk-oraclelinux7

WORKDIR /app

COPY --from=build /app/target/apacheCamel-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

