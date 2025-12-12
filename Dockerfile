
FROM maven:3.8.5-openjdk-17 AS build
COPY . .


WORKDIR /jobportal


RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jdk-jammy

COPY --from=build /jobportal/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]