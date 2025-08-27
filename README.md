# desafio-mensageria

Aplicação de mensageria composta por dois microserviços: **user** e **email**.

## Descrição

- O microserviço **user** permite o cadastro de usuários.
- Após o cadastro, o microserviço **email** envia um e-mail de confirmação para o usuário.
- Cada microserviço possui sua própria base de dados: usuários são armazenados na base de **user** e e-mails enviados na base de **email**.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **RabbitMQ** (mensageria)
- **CloudAMQP** (serviço RabbitMQ gerenciado)
- **Flyway** (migração de banco de dados)
- **JPA** (persistência de dados)
- **PostgreSQL** (banco de dados)
- **Lombok** (redução de boilerplate)
- **JavaMailSender** (envio de e-mails)

## Funcionamento

1. O usuário realiza o cadastro via microserviço **user**.
2. O serviço **user** publica uma mensagem no RabbitMQ.
3. O microserviço **email** consome a mensagem e envia o e-mail de confirmação.
4. Os dados de cadastro e envio de e-mail são persistidos em suas respectivas bases.

## Estrutura dos Microserviços

- **user**: Cadastro de usuários, publicação de eventos.
- **email**: Consumo de eventos, envio de e-mails, registro dos envios.

## Como executar

1. Configure as variáveis de ambiente para acesso ao PostgreSQL e CloudAMQP.
2. Execute as migrations com Flyway.
3. Inicie os microserviços com Spring Boot.
4. Realize um cadastro para testar o fluxo completo.

## Observações

- Certifique-se que o RabbitMQ (CloudAMQP) está acessível.
- Os microserviços são independentes e podem ser escalados separadamente.


