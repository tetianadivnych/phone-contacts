FROM openjdk:11-jdk-oracle
ADD . /phone-contacts
WORKDIR /phone-contacts
CMD ["java", "-jar", "./target/*.jar"]
