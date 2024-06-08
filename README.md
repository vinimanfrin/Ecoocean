### Apresentação dos Integrantes
####  Leonardo Bruno de Sousa
#### Samara de Oliveira Moreira
####  Vinícius Andrade Lopes
####  Vinicius Monteiro Manfrin
#### Marco Antônio de Araújo

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Oracle DataBase
- Bean Validation (para validações de formulários)
- Spring Data JPA
- Lombok
<br>

## Configuração da Aplicação

Para rodar a aplicação corretamente, siga os passos abaixo:
1. Requisitos de Sistema:
   - Certifique-se de ter o Java 17 instalado em sua máquina.
   - Garanta que o Oracle DataBase esteja instalado e em execução.

2. Configurações no arquivo application.properties:
   - Abra o arquivo src/main/resources/application.properties.
   - Configure as seguintes propriedades relacionadas ao banco de dados:
     

spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
spring.datasource.username=${ORACLE_DB_USERNAME:}
spring.datasource.password=${ORACLE_DB_PASSWORD:}
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.jpa.hibernate.ddl-auto=update


3. Execução da Aplicação:
   - Após configurar todas as propriedades necessárias, execute a aplicação.
   - A aplicação será iniciada em http://localhost:8080.

<br/>

## Mais detalhes
Após inicializar o projeto , consulte /swagger-ui/index.html para informações mais detalhadas dos endpoints.

## Docker 

- Utilize o comando "mvn clean package" para gerar o arquivo .jar.
- Utilize o comando "docker-compose up" para subir a sua aplicação.

##Link Vídeo: https://youtu.be/fQLI7K2_sNs?si=B7E7-Rg6FscsAVZF

##Deploy http://ecoocean-production-6824.up.railway.app/
