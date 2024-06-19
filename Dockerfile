FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/itau-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8082

# Comando para executar a aplicação quando o container for iniciado
CMD ["java", "-jar", "app.jar"]
