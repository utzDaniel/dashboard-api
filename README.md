# API Dashboard

API REST de dashboard do sistema.

## Stack

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 4.0.6 |
| SQL Server | Banco de dados |
| Keycloak | Servidor de identidade (OAuth2/JWT) |

## Pré-requisitos

Os serviços abaixo devem estar rodando **antes** de iniciar a aplicação:

- **SQL Server** — `localhost:1433` (banco `ficance`)
- **Keycloak** — `https://localhost:9999` (realm `development`)

## Arquitetura

```
src/main/java/br/com/dashboard/
├── config/          # Segurança, Keycloak, tratamento de erros
```

## Autenticação

Todos os endpoints protegidos exigem um **Bearer Token JWT** obtido via Keycloak

```
Authorization: Bearer <token>
```

Roles aceitas: `DASHBOARD`, `ADMIN` (extraídas de `realm_access.roles` no JWT).

Endpoints públicos: `/actuator/health`, `/actuator/info`, `/api/v1/public/**`.

---
## Documentação da API

A especificação OpenAPI completa está em [docs/openapi/spec.html](docs/openapi/spec.html).

