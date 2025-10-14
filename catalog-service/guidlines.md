# Project Generation Guidelines for Gemini

This document contains the specific code generation patterns for the `my-shop` project. Please refer to these patterns when generating new features.

---

## 1. CQRS Pattern for `catalog-service`

All new features in the `catalog-service` should follow a logical CQRS pattern.

### 1.1. Command Generation

Commands are simple DTOs representing an intent to change state. They should be placed in the `org.example.catalog.command` package.

**Pattern:**

### 1.2. Command Handler Generation

Command Handlers contain the business logic for processing a command. They should be placed in `org.example.catalog.command.handler`.

**Pattern:**

### 1.3. Query and DTO Generation

Queries should not return JPA entities. They must return read-optimized DTOs from the `org.example.catalog.query.dto` package.

**DTO Pattern:**

### 1.4. Controller Generation

Controllers should be simple dispatchers, injecting and calling the appropriate handlers.

**Pattern:**
