# Telegram Bot - Gestión de Gastos Domésticos

Bot de Telegram para gestionar y registrar gastos domésticos de forma simple y eficiente.

## Tecnologías

- **Java 17**
- **Spring Boot 3.2.5**
- **PostgreSQL** - Base de datos relacional
- **Flyway** - Migraciones de base de datos
- **Telegram Bots API 6.8.0** - Integración con Telegram
- **Lombok** - Reducción de código boilerplate
- **Spring Data JPA** - Persistencia de datos
- **Maven** - Gestión de dependencias

## Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- PostgreSQL 13+
- Bot de Telegram (token obtenido de [@BotFather](https://t.me/botfather))

## Configuración

### 1. Crear el Bot en Telegram

1. Hablar con [@BotFather](https://t.me/botfather) en Telegram
2. Ejecutar `/newbot` y seguir las instrucciones
3. Guardar el token que te proporciona BotFather
4. Guardar el username del bot

### 2. Configurar Base de Datos

Crear una base de datos PostgreSQL:

```sql
CREATE DATABASE homebudget;
```

### 3. Variables de Entorno

Copiar el archivo de ejemplo y configurar las variables:

```bash
cp .env.example .env
```

Editar `.env` con tus valores:

```properties
# Database
DB_HOST=localhost
DB_PORT=5432
DB_NAME=homebudget
DB_USERNAME=postgres
DB_PASSWORD=tu_password

# Telegram Bot
TELEGRAM_BOT_USERNAME=tu_bot_username
TELEGRAM_BOT_TOKEN=tu_bot_token_de_botfather

# Server (opcional)
SERVER_PORT=8080
```

## Instalación y Ejecución

### Compilar el proyecto

```bash
mvn clean install
```

### Ejecutar la aplicación

```bash
mvn spring-boot:run
```

O con variables de entorno directamente:

```bash
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=homebudget
export DB_USERNAME=postgres
export DB_PASSWORD=password
export TELEGRAM_BOT_USERNAME=mi_bot
export TELEGRAM_BOT_TOKEN=123456:ABC-DEF...

mvn spring-boot:run
```

### Ejecutar con Docker (opcional)

```bash
docker-compose up -d
```

## Estructura del Proyecto

```
src/main/java/com/homebudget/bot/
├── config/          # Configuraciones de Spring
├── entity/          # Entidades JPA
├── repository/      # Repositorios de datos
├── service/         # Lógica de negocio
├── handler/         # Manejadores de comandos del bot
├── dto/             # Data Transfer Objects
└── BotApplication.java

src/main/resources/
├── application.yml
└── db/migration/    # Migraciones Flyway
    └── V1__initial_schema.sql
```

## Base de Datos

El esquema inicial incluye las siguientes tablas:

- **users** - Usuarios del bot
- **categories** - Categorías de gastos
- **expenses** - Registro de gastos

Las migraciones se ejecutan automáticamente al iniciar la aplicación.

## Desarrollo

### Agregar nuevas migraciones

Crear archivos en `src/main/resources/db/migration/` siguiendo el patrón:

```
V{VERSION}__{DESCRIPTION}.sql
```

Ejemplo: `V2__add_budgets_table.sql`

### Testing

```bash
mvn test
```

## Comandos del Bot (Por implementar)

- `/start` - Iniciar el bot y registrar usuario
- `/addexpense` - Agregar un nuevo gasto
- `/categories` - Ver categorías disponibles
- `/report` - Ver reporte de gastos
- `/help` - Ayuda y comandos disponibles

## Contribución

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT.

## Autor

Proyecto creado para gestión de gastos domésticos personales.