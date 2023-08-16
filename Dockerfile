# Primeira etapa: compilação
FROM maven:3.8.5-openjdk-17 AS build

# Copia todos os arquivos do contexto para o diretório de trabalho no contêiner da compilação
COPY . .

# Executa o comando Maven para compilar e empacotar o projeto, ignorando os testes
RUN mvn clean package -DskipTests

# Segunda etapa: execução
FROM openjdk:17.0.1-jdk-slim

# Copia o arquivo JAR gerado na primeira etapa para o contêiner de execução
COPY --from=build /target/0.0.1-SNAPSHOT.jar demo.jar

# Expõe a porta 8080 para o tráfego externo
EXPOSE 8080

# Define o comando de entrada para executar o aplicativo quando o contêiner for iniciado
ENTRYPOINT ["java", "-jar", "demo.jar"]
