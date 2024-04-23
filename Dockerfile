FROM eclipse-temurin:22-jre-alpine
WORKDIR /app
COPY ./service/target/service-1.0-SNAPSHOT.jar /app
COPY ./provider/target/provider-1.0-SNAPSHOT.jar /app
COPY ./consumer/target/consumer-1.0-SNAPSHOT.jar /app
CMD ["java", "--module-path", "/app", "--module", "se.iths.consumer/se.iths.consumer.Main"]