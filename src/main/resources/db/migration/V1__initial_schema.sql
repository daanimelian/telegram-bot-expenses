-- Tabla de hogares (usuarios del bot)
-- Cada hogar representa un grupo familiar que usa el bot
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    telegram_chat_id VARCHAR(255) NOT NULL UNIQUE,
    household_name VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    encryption_salt VARCHAR(255),
    is_active BOOLEAN DEFAULT true
);

-- Tabla de miembros familiares
-- Cada hogar puede tener múltiples miembros
CREATE TABLE family_members (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de ingresos mensuales
-- Registra los ingresos del hogar por mes en ARS y USD
CREATE TABLE incomes (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    month_year VARCHAR(7) NOT NULL,
    amount_ars DECIMAL(15, 2) DEFAULT 0.00,
    amount_usd DECIMAL(15, 2) DEFAULT 0.00,
    exchange_rate DECIMAL(10, 2),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_income_user_month UNIQUE (user_id, month_year),
    CONSTRAINT check_amount_ars_positive CHECK (amount_ars >= 0),
    CONSTRAINT check_amount_usd_positive CHECK (amount_usd >= 0),
    CONSTRAINT check_exchange_rate_positive CHECK (exchange_rate IS NULL OR exchange_rate > 0)
);

-- Índices para mejorar el rendimiento de consultas

-- Índices para users
CREATE INDEX idx_users_telegram_chat_id ON users(telegram_chat_id);
CREATE INDEX idx_users_is_active ON users(is_active);

-- Índices para family_members
CREATE INDEX idx_family_members_user_id ON family_members(user_id);
CREATE INDEX idx_family_members_is_active ON family_members(is_active);

-- Índices para incomes
CREATE INDEX idx_incomes_user_id ON incomes(user_id);
CREATE INDEX idx_incomes_month_year ON incomes(month_year);
CREATE INDEX idx_incomes_user_month ON incomes(user_id, month_year);

-- Comentarios descriptivos para las tablas
COMMENT ON TABLE users IS 'Hogares registrados en el bot de Telegram';
COMMENT ON TABLE family_members IS 'Miembros familiares de cada hogar';
COMMENT ON TABLE incomes IS 'Ingresos mensuales de los hogares en ARS y USD';

COMMENT ON COLUMN users.telegram_chat_id IS 'ID del chat de Telegram asociado al hogar';
COMMENT ON COLUMN users.household_name IS 'Nombre personalizado del hogar';
COMMENT ON COLUMN users.encryption_salt IS 'Salt para encriptación de datos sensibles';

COMMENT ON COLUMN incomes.month_year IS 'Mes y año en formato YYYY-MM';
COMMENT ON COLUMN incomes.amount_ars IS 'Monto de ingresos en pesos argentinos';
COMMENT ON COLUMN incomes.amount_usd IS 'Monto de ingresos en dólares estadounidenses';
COMMENT ON COLUMN incomes.exchange_rate IS 'Tipo de cambio USD a ARS usado para el mes';
