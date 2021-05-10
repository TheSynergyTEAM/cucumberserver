FROM openjdk:15

WORKDIR /cucumber-app

COPY . .

RUN ./gradlew build -x test --no-daemon

EXPOSE 8080

COPY . .

CMD ["java", "-jar", "/cucumber-app/build/libs/cucumbermarket-spring-0.0.1-SNAPSHOT.jar"]
