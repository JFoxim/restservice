FROM openjdk:11.0.14.1-jdk-buster
COPY build/libs/restservice-1.0.0.jar restservice.jar
ENV SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/users"
ENV SPRING_DATASOURCE_URERNAME="app_user"
ENV SPRING_DATASOURCE_PASSWORD="Postgres123"
EXPOSE 8081
ENTRYPOINT ["java","-jar","/restservice.jar"]
