
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .

WORKDIR /app/jobportal

RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=build /app/jobportal/target/jobportal-0.0.1-SNAPSHOT.jar demo.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]