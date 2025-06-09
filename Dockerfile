# Multi-stage build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar apenas os arquivos necessários para o build
COPY pom.xml .
COPY src ./src

# Fazer o build da aplicação
RUN mvn clean package -DskipTests

# Stage final - runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Criar usuário não-root para segurança
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Copiar o JAR da aplicação
COPY --from=build /app/target/*.jar app.jar

# Mudar ownership dos arquivos
RUN chown -R appuser:appgroup /app

# Mudar para usuário não-root
USER appuser

# Expor a porta
EXPOSE 8080

# Configurar healthcheck usando um endpoint simples
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/swagger-ui.html || exit 1

# Comando para executar a aplicação
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]