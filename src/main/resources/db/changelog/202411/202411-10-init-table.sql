-- Создание таблицы accounts
CREATE TABLE accounts
(
    id           BIGSERIAL PRIMARY KEY,
    account_type VARCHAR(255)   NOT NULL,
    balance      NUMERIC(10, 2) NOT NULL
);

COMMENT ON TABLE accounts IS 'Таблица для хранения информации об аккаунтах пользователей';
COMMENT ON COLUMN accounts.id IS 'Уникальный идентификатор аккаунта';
COMMENT ON COLUMN accounts.account_type IS 'Тип аккаунта (например: дебетовый счет или кредитный)';
COMMENT ON COLUMN accounts.balance IS 'Баланс на аккаунте';

-- Создание таблицы transactions
CREATE TABLE transactions
(
    id               BIGSERIAL PRIMARY KEY,
    amount           NUMERIC(10, 2) NOT NULL,
    transaction_time TIMESTAMP      NOT NULL
);

COMMENT ON TABLE transactions IS 'Таблица для хранения транзакций по аккаунтам';
COMMENT ON COLUMN transactions.id IS 'Уникальный идентификатор транзакции';
COMMENT ON COLUMN transactions.amount IS 'Сумма транзакции';
COMMENT ON COLUMN transactions.transaction_time IS 'Время выполнения транзакции';

-- Создание таблицы error_logs
CREATE TABLE error_logs
(
    id               BIGSERIAL PRIMARY KEY,
    stack_trace      TEXT NOT NULL,
    message          TEXT NOT NULL,
    method_signature TEXT NOT NULL
);

COMMENT ON TABLE error_logs IS 'Таблица для хранения логов ошибок';
COMMENT ON COLUMN error_logs.id IS 'Уникальный идентификатор лога ошибки';
COMMENT ON COLUMN error_logs.stack_trace IS 'Трассировка стека ошибки';
COMMENT ON COLUMN error_logs.message IS 'Сообщение об ошибке';
COMMENT ON COLUMN error_logs.method_signature IS 'Сигнатура метода, в котором произошла ошибка';
