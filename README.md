# GeoGuardian API

Sistema de monitoramento de desastres naturais desenvolvido em Java com Spring Boot. A API fornece funcionalidades completas para gerenciamento de sensores IoT, alertas de emergência e monitoramento de áreas de risco.

## 📋 Sobre o Projeto

O GeoGuardian é uma plataforma de monitoramento de desastres naturais que visa prevenir e alertar sobre:
- Enchentes e alagamentos
- Deslizamentos de terra
- Rompimento de barragens
- Outros desastres naturais

A API serve como backend para aplicações web e mobile, fornecendo dados em tempo real sobre condições de risco em diferentes regiões.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA** - Para persistência de dados
- **Spring Security** - Para autenticação e autorização
- **JWT (JSON Web Token)** - Para autenticação stateless
- **Bean Validation** - Para validação de dados
- **Swagger/OpenAPI 3** - Para documentação da API
- **H2 Database** - Banco de dados em memória (desenvolvimento)
- **Oracle JDBC** - Suporte para Oracle Database (produção)
- **Maven** - Gerenciamento de dependências

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas:

```
├── Controller Layer    # Endpoints REST
├── Service Layer      # Lógica de negócio
├── Repository Layer   # Acesso a dados
├── Model Layer        # Entidades JPA
├── Security Layer     # Autenticação JWT
└── Configuration      # Configurações do Spring
```

## 📊 Modelo de Dados

### Entidades Principais

- **Localização**: País → Estado → Cidade → Bairro → Logradouro
- **Usuários**: Sistema de usuários com diferentes tipos (Admin, Operador, Cidadão)
- **Áreas de Risco**: Locais monitorados com diferentes tipos de risco
- **Sensores**: Dispositivos IoT para coleta de dados
- **Alertas**: Sistema de notificações baseado em níveis de risco
- **Ocorrências**: Registro de eventos confirmados

### Relacionamentos

- Um país possui muitos estados
- Um estado possui muitas cidades
- Uma cidade possui muitos bairros
- Um bairro possui muitos logradouros
- Um logradouro possui muitas áreas de risco
- Uma área de risco possui muitos sensores
- Sensores geram medições
- Áreas de risco geram alertas e ocorrências

## 🛠️ Instalação e Configuração

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior
- IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code)

### Clonando o Repositório

```bash
git clone https://github.com/fmelods/geoguardian-java
cd geoguardian-api
```

### Configuração do Banco de Dados

O projeto está configurado para usar H2 Database em memória por padrão. Para produção, configure o Oracle Database no arquivo `application.properties`:

```properties
# Para Oracle Database (produção)
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
```

### Executando a Aplicação

```bash
# Usando Maven
mvn spring-boot:run

# Ou compilando e executando
mvn clean package
java -jar target/geoguardian-1.0.0.jar
```

A aplicação estará disponível em: `http://localhost:8080`

## 📚 Documentação da API

### Swagger UI

Acesse a documentação interativa da API em:
```
http://localhost:8080/swagger-ui.html
```

### Endpoints Principais

#### Autenticação
- `POST /api/auth/login` - Fazer login e obter token JWT

#### Gestão de Localização
- `GET /api/paises` - Listar países
- `GET /api/estados` - Listar estados
- `GET /api/cidades` - Listar cidades

#### Gestão de Usuários
- `GET /api/usuarios` - Listar usuários
- `POST /api/usuarios` - Criar usuário
- `PUT /api/usuarios/{id}` - Atualizar usuário

#### Gestão de Sensores
- `GET /api/sensores` - Listar sensores
- `POST /api/sensores` - Cadastrar sensor
- `GET /api/sensores/uuid/{uuid}` - Buscar por UUID

#### Gestão de Alertas
- `GET /api/alertas` - Listar alertas
- `POST /api/alertas` - Criar alerta
- `GET /api/alertas/estatisticas/tipo/{id}` - Estatísticas por tipo

## 🔐 Autenticação

A API utiliza JWT (JSON Web Token) para autenticação. Para acessar endpoints protegidos:

1. Faça login no endpoint `/api/auth/login`
2. Copie o token retornado
3. Inclua o token no header das requisições:
   ```
   Authorization: Bearer <seu-token-jwt>
   ```

### Usuário Padrão

- **Email**: admin@geoguardian.com
- **Senha**: 123456

## 📝 Exemplos de Uso

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@geoguardian.com",
    "senha": "123456"
  }'
```

### Criar Sensor
```bash
curl -X POST http://localhost:8080/api/sensores \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <seu-token>" \
  -d '{
    "uuid": "SENSOR001",
    "status": "Ativo",
    "areaRisco": {"id": 1},
    "modeloSensor": {"id": 1}
  }'
```

### Criar Alerta
```bash
curl -X POST http://localhost:8080/api/alertas \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <seu-token>" \
  -d '{
    "nivelRisco": 4,
    "tipoAlerta": {"id": 1},
    "areaRisco": {"id": 1}
  }'
```

## 🔍 Funcionalidades

### Validação de Dados
- Validação automática usando Bean Validation
- Mensagens de erro personalizadas
- Tratamento global de exceções

### Paginação e Filtros
- Suporte a paginação em todos os endpoints de listagem
- Filtros por diferentes critérios (nome, status, tipo, etc.)
- Ordenação customizável

### Segurança
- Autenticação JWT stateless
- Autorização baseada em roles
- Proteção contra CSRF
- Headers de segurança configurados

### Monitoramento
- Logs detalhados de sistema
- Rastreamento de ações de usuários
- Métricas de performance

## 🧪 Dados de Teste

A aplicação inclui dados iniciais para teste:

- **Países**: Brasil, Argentina
- **Estados**: São Paulo, Rio de Janeiro
- **Cidades**: São Paulo, Rio de Janeiro
- **Usuários**: Admin e usuário comum
- **Sensores**: 2 sensores ativos
- **Alertas**: Alertas de exemplo

## 🚀 Deploy

### Variáveis de Ambiente

Para produção, configure as seguintes variáveis:

```bash
JWT_SECRET=sua-chave-secreta-jwt
JWT_EXPIRATION=86400000
DB_URL=jdbc:oracle:thin:@host:port:sid
DB_USERNAME=usuario
DB_PASSWORD=senha
```

### Docker (Opcional)

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/geoguardian-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -am 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## 👥 Equipe

- Cauã Marcelo Da Silva Machado – RM558024
- Felipe Melo de Sousa – RM556099
- Gabriel Lima Silva – RM556773

---

**GeoGuardian** - Protegendo vidas através da tecnologia 🛡️
