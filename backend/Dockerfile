FROM maven:3.8.5-openjdk-17
WORKDIR /app
COPY src ./src
COPY pom.xml ./

EXPOSE 8085

RUN mvn clean install

ENTRYPOINT ["java","-jar","target/onlineChatTele2-0.0.1-SNAPSHOT.jar"]