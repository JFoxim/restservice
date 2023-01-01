FROM openjdk:11.0.14.1-jdk-buster
COPY build/libs/httpsgost-0.0.1-SNAPSHOT.jar httpsgost-0.0.1.jar
ENTRYPOINT ["java","-jar","/httpsgost-0.0.1.jar"]