# imagem base que ele vai utilizar.
FROM openjdk:8-jdk-alpine

#criar um usuário nesse sistema operacional. Nós estamos 
#criando um usuário com um grupo chamado Spring justamente 
#para rodar a aplicação com o usuário Spring ao invés de 
#rodar a aplicação com o usuário root
RUN addgroup -S spring && adduser -S spring -G spring

#usuário Spring
USER spring:spring

#vai pegar o nosso JAR que foi gerado pelo Build do Maven e
#vai colocar o nome desse arquivo nessa variável chamada JAR_FILE
ARG JAR_FILE=target/*.jar

#Copiar esse arquivo JAR. E tem um asterisco porque eu não sei o
#nome do arquivo, vai depender de como está configurado no Maven.
#Mas vamos copiá-lo para o sistema operacional com o nome app.jar.
COPY ${JAR_FILE} app.jar

#o comando que vai ser executado no nosso contêiner, quando 
#criarmos o contêiner de fato.
ENTRYPOINT ["java", "-Xmx512m", "-Dserver.port=${PORT}", "-jar", "/app.jar"]
#Xmx512m limitar o tanaho da memoria ram, rode no maximo 512 mega