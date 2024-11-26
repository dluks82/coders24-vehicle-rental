# 🚗 Sistema de Locação de Veículos

![Status de Desenvolvimento](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)
![Licença](https://img.shields.io/badge/licença-MIT-green)
![Versão Java](https://img.shields.io/badge/java-17-orange)
![Versão Spring Boot](https://img.shields.io/badge/spring%20boot-3.4.0-brightgreen)

Sistema moderno de gerenciamento de locação de veículos construído com Spring Boot, oferecendo uma solução completa para
gestão de veículos, clientes e agências.

## 🚧 Status do Projeto

Este projeto está em desenvolvimento ativo. Funcionalidades estão sendo adicionadas continuamente e a API pode sofrer
alterações.

## 🛠️ Tecnologias

### Backend

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **PostgreSQL**
- **Flyway Migration**
- **Spring REST Docs**
- **Lombok**
- **MapStruct**

### Testes

- **JUnit 5**
- **Testcontainers**
- **Rest Assured**
- **Spring Test**

### Ferramentas de Desenvolvimento

- **Docker**
- **Maven**

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas com clara separação de responsabilidades:

- **Camada de Domínio**: Entidades e regras de negócio principais
- **Camada de Repositório**: Acesso e persistência de dados
- **Camada de Serviço**: Lógica de negócio e gerenciamento de transações
- **Camada de Controlador**: Endpoints da API REST
- **Camada de Documentação**: Documentação da API gerada automaticamente

## 🚀 Começando

### Pré-requisitos

- JDK 17 ou superior
- Maven 3.6+
- Docker e Docker Compose
- PostgreSQL (se executar sem Docker)

### Executando Localmente

1. Clone o repositório

```bash
git clone git@github.com:dluks82/coders24-vehicle-rental.git
cd coders24-vehicle-rental
```

2. Inicie o banco de dados usando Docker Compose

```bash
docker compose up -d
```

3. Entre na pasta do backend e compile a aplicação

```bash
cd backend
./mvnw clean install
```

4. Execute a aplicação

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

### Executando os Testes

```bash
./mvnw test        # Testes unitários
./mvnw verify      # Testes de integração
```

## 📊 Esquema do Banco de Dados

O sistema utiliza PostgreSQL com as seguintes entidades principais:

- `vehicles`: Gerenciamento de veículos
- `customers`: Informações dos clientes
- `agencies`: Detalhes das agências
- `addresses`: Informações de endereço
- `rentals`: Transações de locação

As migrações são gerenciadas pelo Flyway e podem ser encontradas em `src/main/resources/db/migration`.

## 🔍 Documentação da API

Com a aplicação em execução, você pode encontrar a documentação da API em:

- REST Docs: `http://localhost:8080/docs/index.html`

## 🔐 Regras de Negócio

- Veículos não podem estar duplicados
- Tipos de veículos: Carro, Moto, Caminhão
- Locações incluem local, data e hora
- Veículos alugados ficam indisponíveis
- Agências não podem estar duplicadas
- Clientes não podem estar duplicados
- Regras de desconto:
    - Pessoa Física: 5% após 5 dias
    - Pessoa Jurídica: 10% após 3 dias

## 🌱 Variáveis de Ambiente

A aplicação utiliza as seguintes variáveis de ambiente:

```properties
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5435/vehicle_rental
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
```

## 👥 Autores

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/dluks82">
        <img src="https://github.com/dluks82.png" width="100px;" alt="Foto do Diogo" style="border-radius:50%"/>
        <br />
        <sub><b>Diogo</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Isaquemz">
        <img src="https://github.com/Isaquemz.png" width="100px;" alt="Foto do Isaque" style="border-radius:50%"/>
        <br />
        <sub><b>Isaque</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/RAODomingos">
        <img src="https://github.com/RAODomingos.png" width="100px;" alt="Foto do Rômulo" style="border-radius:50%"/>
        <br />
        <sub><b>Rômulo</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/squoliver83">
        <img src="https://github.com/squoliver83.png" width="100px;" alt="Foto do Samuel" style="border-radius:50%"/>
        <br />
        <sub><b>Samuel</b></sub>
      </a>
    </td>
  </tr>
</table>

## 📝 Licença

Este projeto está licenciado sob a Licença MIT - consulte o arquivo [LICENSE](LICENSE) para obter detalhes.