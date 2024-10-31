# Insurance Service API

## Descrição

O **Insurance Service API** é um microserviço RESTful que gerencia operações relacionadas a seguros, incluindo criação, listagem, simulação de custo e contratação de seguros para clientes. Ele faz parte de um sistema maior de gerenciamento de clientes e seguros, integrando-se diretamente com o **Cliente Service API** para validar dados dos clientes antes de proceder com as operações de seguro.

## Funcionalidade e Integração no Fluxo de Seguros

O **Insurance Service API** executa operações essenciais para o fluxo de seguros, incluindo:

1. **Criação de Seguro**: Permite a criação de um novo seguro com as informações de cobertura, custo mensal e benefícios.
2. **Listagem de Seguros**: Retorna todos os seguros disponíveis para consulta.
3. **Simulação de Seguro**: Calcula e retorna uma simulação do custo do seguro para um cliente.
4. **Contratação de Seguro**: Permite a contratação de um seguro específico para um cliente.
5. **Exclusão de Seguro**: Permite a exclusão de seguros por ID.

Durante a simulação e contratação de seguros, o Insurance Service API valida o cliente e obtém o CPF através do **Cliente Service API**. Se o cliente não existir, uma mensagem de erro é retornada, garantindo que cada operação de seguro corresponda a um cliente válido.

## Endpoints Principais

Para uma descrição completa dos endpoints disponíveis e detalhes sobre cada operação, acesse a [documentação interativa do Swagger](http://localhost:3031/swagger-ui/index.html).

## Exemplo de Integração com o Cliente Service API

O Insurance Service API se conecta ao **Cliente Service API** para:

- **Verificar Existência de Cliente**: Antes de qualquer operação, o Insurance Service verifica a existência do cliente.
- **Obter CPF**: Extraído do Cliente Service para associar corretamente o seguro ao cliente.

Dessa forma, qualquer operação de simulação ou contratação é precedida de uma consulta ao Cliente Service API, garantindo a integridade dos dados.

## Configuração da Aplicação

### Variáveis de Ambiente

Configure as seguintes variáveis de ambiente para executar o Insurance Service API:

```properties
# Configurações do Banco de Dados PostgreSQL
POSTGRES_DB_URL=jdbc:postgresql://localhost:5432/client_insurance
POSTGRES_DB_USERNAME=INTERNET
POSTGRES_DB_PASSWORD=q1w2e3r4

# Client Service
CLIENT_SERVICE_URL=http://localhost:3030

# Configurações da Aplicação
APP_PORT=3031
```

### Executando a Aplicação

Após definir as variáveis de ambiente, você pode iniciar o serviço com:

```bash
./mvnw spring-boot:run
```

## Tecnologias Utilizadas

- **Spring Boot**: Framework principal para a criação da API.
- **Feign Client**: Integração direta com o Cliente Service API.
- **Spring Data JPA**: Persistência e mapeamento objeto-relacional.
- **Swagger**: Documentação dos endpoints para uma visualização interativa.