# Phone Contacts Application

This Java Spring Boot application manages phone contacts. It provides features for user registration, login, and operations to add, edit, delete, and retrieve contacts. Each contact consists of a name, email addresses, and phone numbers. 

The application also allows users to set a profile image for each contact and provides the ability to download the image as a file.

## Technology Stack and Dependencies
- Java 11
- Spring Boot
- Spring Web
- Spring Security
- Spring Data + Hibernate
- Maven Wrapper
- H2 SQL database
- Twilio API for phone validation
- Springdoc OpenAPI for Swagger documentation
- Apache Commons IO for image processing

## Testing and Quality Assurance

The application includes:

- Unit tests with JUnit and Mockito to verify component and function correctness
- Integration tests with an embedded H2 database to validate interactions between application components

## API Documentation
Once the application is up, you can explore the API documentation at `http://localhost:8080/swagger-ui.html`.

## How to Build
Clone this repository to your local machine.
```
git clone https://github.com/tetianadivnych/phone-contacts.git
```
Navigate to the root directory, set permissions on the ./mvnw file, and perform the build:
```
chmod 755 ./mvnw
```
```
./mvnw clean package 
```
After the build is completed, the folder `/target` will be created with a compiled `.jar` ready to be launched.

## How to Run
You can launch the server on port `8080`
(if the option `--server.port=8080` is not provided the default port is `8080`):
```bash
java -jar ./target/*.jar --server.port=8080
```
You might need to replace * with the actual `.jar` name, for example:
```bash
java -jar ./target/phone-contacts-0.0.1-SNAPSHOT.jar --server.port=8080
```
Access the application at `http://localhost:8080`

## Docker Support

Build the Docker image:
```
$ docker build -t phone-contacts-app .
```
Run the Docker container: 
```
$ docker run -p 8080:8080 phone-contacts-app
```
