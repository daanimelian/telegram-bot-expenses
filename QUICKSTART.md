# Guía de Inicio Rápido

Esta guía te ayudará a ejecutar el bot de Telegram para gestión de gastos domésticos en menos de 5 minutos.

## Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

- ✅ **Java 17 o superior**
  ```bash
  java -version  # Debe mostrar versión 17+
  ```

- ✅ **Maven 3.6+**
  ```bash
  mvn -version
  ```

- ✅ **PostgreSQL 13+** (ejecutándose en localhost:5432)
  ```bash
  psql --version
  ```

- ✅ **Bot de Telegram** (token de @BotFather)

## Configuración en 3 Pasos

### 1. Crear tu Bot en Telegram

1. Abre Telegram y busca **@BotFather**
2. Envía el comando `/newbot`
3. Sigue las instrucciones para crear tu bot
4. Guarda el **token** que te proporciona (ejemplo: `123456789:ABCdefGHIjklMNOpqrsTUVwxyz`)
5. Guarda el **username** de tu bot (ejemplo: `@mi_bot_gastos`)

### 2. Configurar Variables de Entorno

Copia el archivo de ejemplo y edítalo:

```bash
cp .env.example .env
```

Edita `.env` con tus valores:

```properties
# Base de datos (ajusta la contraseña según tu instalación de PostgreSQL)
DATABASE_URL=jdbc:postgresql://localhost:5432/homebudget
DATABASE_USER=postgres
DATABASE_PASSWORD=TU_PASSWORD_DE_POSTGRES

# Bot de Telegram (usa los valores de @BotFather)
TELEGRAM_BOT_USERNAME=tu_bot_username
TELEGRAM_BOT_TOKEN=123456789:ABCdefGHIjklMNOpqrsTUVwxyz
```

### 3. Ejecutar la Aplicación

#### Opción A: Usando el script de inicio (Recomendado)

```bash
./start.sh
```

Este script:
- ✅ Verifica todas las dependencias
- ✅ Crea la base de datos si no existe
- ✅ Compila el proyecto
- ✅ Ejecuta la aplicación

#### Opción B: Manualmente

1. **Crear la base de datos** (solo la primera vez):
   ```bash
   psql -U postgres -c "CREATE DATABASE homebudget;"
   ```

2. **Cargar variables de entorno**:
   ```bash
   export $(cat .env | grep -v '^#' | xargs)
   ```

3. **Compilar y ejecutar**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## Verificar que Funciona

1. **Ver los logs**: La aplicación debe mostrar:
   ```
   Bot de Telegram inicializado: @tu_bot_username
   Started BotApplication in X.XXX seconds
   ```

2. **Probar el bot**:
   - Abre Telegram
   - Busca tu bot por el username
   - Envía cualquier mensaje
   - Deberías ver el mensaje logueado en la consola

## Solución de Problemas

### Error: "Cannot connect to database"

```bash
# Verifica que PostgreSQL esté ejecutándose
sudo systemctl status postgresql

# O en macOS
brew services list | grep postgresql
```

### Error: "Failed to initialize bot"

- Verifica que `TELEGRAM_BOT_TOKEN` sea correcto
- Verifica que `TELEGRAM_BOT_USERNAME` no incluya el símbolo @

### Error: "Java version 17 required"

```bash
# Instala Java 17 (Ubuntu/Debian)
sudo apt update
sudo apt install openjdk-17-jdk

# O usando SDKMAN
sdk install java 17.0.9-tem
```

## Siguiente Paso

Una vez que la aplicación esté ejecutándose:

1. Revisa la documentación completa en `README.md`
2. Explora la estructura del proyecto
3. Implementa los handlers para comandos del bot (en `src/main/java/com/homebudget/bot/handler/`)

## Detener la Aplicación

Presiona `Ctrl + C` en la terminal donde está ejecutándose.

## Estructura del Proyecto

```
telegram-bot-expenses/
├── src/main/java/com/homebudget/bot/
│   ├── config/          ✅ Configuración del bot (TelegramBotConfig)
│   ├── entity/          ✅ Entidades JPA (User, FamilyMember, Income)
│   ├── repository/      ✅ Repositorios de datos
│   ├── service/         🔜 Lógica de negocio (por implementar)
│   ├── handler/         🔜 Manejadores de comandos (por implementar)
│   ├── dto/             🔜 DTOs (por implementar)
│   └── BotApplication.java ✅
├── src/main/resources/
│   ├── application.yml  ✅ Configuración de Spring
│   └── db/migration/    ✅ Migraciones de Flyway
├── pom.xml              ✅ Dependencias Maven
├── .env.example         ✅ Plantilla de variables
└── start.sh             ✅ Script de inicio
```

## Variables de Entorno Disponibles

| Variable | Descripción | Requerida | Default |
|----------|-------------|-----------|---------|
| `DATABASE_URL` | URL de PostgreSQL | ❌ | `jdbc:postgresql://localhost:5432/homebudget` |
| `DATABASE_USER` | Usuario de PostgreSQL | ❌ | `postgres` |
| `DATABASE_PASSWORD` | Contraseña de PostgreSQL | ✅ | - |
| `TELEGRAM_BOT_USERNAME` | Username del bot | ✅ | - |
| `TELEGRAM_BOT_TOKEN` | Token del bot | ✅ | - |
| `SERVER_PORT` | Puerto del servidor | ❌ | `8080` |
| `LOG_LEVEL` | Nivel de logs de la app | ❌ | `DEBUG` |

## Comandos Útiles

```bash
# Compilar sin ejecutar
mvn clean install

# Ejecutar sin compilar
mvn spring-boot:run

# Ejecutar tests
mvn test

# Limpiar build
mvn clean

# Ver logs de Flyway
mvn flyway:info

# Ejecutar con perfil específico
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## Soporte

Si tienes problemas:

1. Revisa los logs en la consola
2. Verifica que todas las variables en `.env` estén correctas
3. Asegúrate de que PostgreSQL esté ejecutándose
4. Revisa que el token del bot sea válido en @BotFather
