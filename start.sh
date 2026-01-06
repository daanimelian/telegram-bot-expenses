#!/bin/bash

# Script de inicio rápido para el bot de Telegram de gastos domésticos
# Este script ayuda a configurar y ejecutar el proyecto

set -e

echo "==================================================="
echo "  Bot de Telegram - Gestión de Gastos Domésticos"
echo "==================================================="
echo ""

# Función para verificar si un comando existe
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Verificar dependencias
echo "📋 Verificando dependencias..."

if ! command_exists java; then
    echo "❌ Error: Java no está instalado"
    echo "   Por favor, instala Java 17 o superior"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d. -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ Error: Se requiere Java 17 o superior (encontrado: $JAVA_VERSION)"
    exit 1
fi
echo "✅ Java $JAVA_VERSION encontrado"

if ! command_exists mvn; then
    echo "❌ Error: Maven no está instalado"
    echo "   Por favor, instala Maven 3.6 o superior"
    exit 1
fi
echo "✅ Maven encontrado"

if ! command_exists psql; then
    echo "⚠️  Advertencia: PostgreSQL client no encontrado"
    echo "   Asegúrate de tener PostgreSQL instalado y ejecutándose"
else
    echo "✅ PostgreSQL client encontrado"
fi

echo ""

# Verificar archivo .env
if [ ! -f ".env" ]; then
    echo "📝 Creando archivo .env desde .env.example..."
    cp .env.example .env
    echo "⚠️  IMPORTANTE: Edita el archivo .env con tus credenciales antes de continuar"
    echo ""
    echo "   Necesitas configurar:"
    echo "   - DATABASE_PASSWORD (contraseña de PostgreSQL)"
    echo "   - TELEGRAM_BOT_TOKEN (token de @BotFather)"
    echo "   - TELEGRAM_BOT_USERNAME (username del bot)"
    echo ""
    read -p "¿Ya configuraste el archivo .env? (s/N): " -n 1 -r
    echo ""
    if [[ ! $REPLY =~ ^[Ss]$ ]]; then
        echo "Por favor, configura .env y ejecuta este script nuevamente"
        exit 0
    fi
fi

# Cargar variables de entorno
echo "🔧 Cargando variables de entorno..."
export $(cat .env | grep -v '^#' | xargs)

# Verificar que las variables críticas estén configuradas
if [ -z "$DATABASE_PASSWORD" ] || [ -z "$TELEGRAM_BOT_TOKEN" ] || [ -z "$TELEGRAM_BOT_USERNAME" ]; then
    echo "❌ Error: Faltan variables de entorno críticas en .env"
    echo "   Verifica que DATABASE_PASSWORD, TELEGRAM_BOT_TOKEN y TELEGRAM_BOT_USERNAME estén configuradas"
    exit 1
fi
echo "✅ Variables de entorno cargadas"

echo ""

# Verificar conexión a base de datos
echo "🔌 Verificando conexión a PostgreSQL..."
if command_exists psql; then
    if PGPASSWORD=$DATABASE_PASSWORD psql -h localhost -U $DATABASE_USER -d postgres -c '\q' 2>/dev/null; then
        echo "✅ Conexión a PostgreSQL exitosa"

        # Verificar si existe la base de datos
        DB_EXISTS=$(PGPASSWORD=$DATABASE_PASSWORD psql -h localhost -U $DATABASE_USER -d postgres -tAc "SELECT 1 FROM pg_database WHERE datname='homebudget'" 2>/dev/null)

        if [ "$DB_EXISTS" != "1" ]; then
            echo "📦 Creando base de datos 'homebudget'..."
            PGPASSWORD=$DATABASE_PASSWORD psql -h localhost -U $DATABASE_USER -d postgres -c "CREATE DATABASE homebudget;" 2>/dev/null
            echo "✅ Base de datos creada"
        else
            echo "✅ Base de datos 'homebudget' existe"
        fi
    else
        echo "⚠️  No se pudo conectar a PostgreSQL"
        echo "   Asegúrate de que PostgreSQL esté ejecutándose en localhost:5432"
        read -p "¿Deseas continuar de todas formas? (s/N): " -n 1 -r
        echo ""
        if [[ ! $REPLY =~ ^[Ss]$ ]]; then
            exit 0
        fi
    fi
fi

echo ""

# Compilar el proyecto
echo "🔨 Compilando el proyecto..."
mvn clean install -DskipTests

echo ""
echo "✅ Proyecto compilado exitosamente"
echo ""

# Ejecutar la aplicación
echo "🚀 Iniciando el bot de Telegram..."
echo ""
echo "==================================================="
echo "  Presiona Ctrl+C para detener la aplicación"
echo "==================================================="
echo ""

mvn spring-boot:run
