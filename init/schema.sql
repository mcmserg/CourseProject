-- =====================================================================
--  schema.sql
--  СУБД: PostgreSQL / MySQL
--  Суммы хранятся в копейках (INT).
--  Идентификаторы — UUID в виде CHAR(36).
-- =====================================================================

-- ---------------------------------------------------------------------
--  Удаление таблиц (обратный порядок из-за внешних ключей)
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS audit_log;
DROP TABLE IF EXISTS payment_requests;
DROP TABLE IF EXISTS card_transactions;
DROP TABLE IF EXISTS cards;
DROP TABLE IF EXISTS auth_codes;
DROP TABLE IF EXISTS users;

-- ---------------------------------------------------------------------
--  Пользователи
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id       CHAR(36) PRIMARY KEY,
    login    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    status   VARCHAR(255)        NOT NULL DEFAULT 'active'
);

-- ---------------------------------------------------------------------
--  Коды подтверждения (аутентификация)
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS auth_codes;
CREATE TABLE auth_codes
(
    id      CHAR(36) PRIMARY KEY,
    user_id CHAR(36)   NOT NULL,
    code    VARCHAR(6) NOT NULL,
    created TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- ---------------------------------------------------------------------
--  Карты пользователей
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS cards;
CREATE TABLE cards
(
    id                 CHAR(36) PRIMARY KEY,
    user_id            CHAR(36)           NOT NULL,
    number             VARCHAR(19) UNIQUE NOT NULL,
    balance_in_kopecks INT                NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- ---------------------------------------------------------------------
--  Транзакции между картами
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS card_transactions;
CREATE TABLE card_transactions
(
    id                CHAR(36) PRIMARY KEY,
    source            VARCHAR(19) NOT NULL,
    target            VARCHAR(19) NOT NULL,
    amount_in_kopecks INT         NOT NULL,
    created           TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ---------------------------------------------------------------------
--  Запросы к симулятору банковских сервисов (185.119.57.197:9999)
--  APPROVED — карта 1111 2222 3333 4444
--  DECLINED — карта 5555 6666 7777 8888
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS payment_requests;
CREATE TABLE payment_requests
(
    id                CHAR(36) PRIMARY KEY,
    transaction_id    CHAR(36)    NOT NULL,
    card_number       VARCHAR(19) NOT NULL,
    amount_in_kopecks INT         NOT NULL,
    response_status   VARCHAR(16) NOT NULL,       -- APPROVED / DECLINED
    ip_address        VARCHAR(45),
    created           TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (transaction_id) REFERENCES card_transactions (id)
);

-- ---------------------------------------------------------------------
--  Журнал действий пользователя
-- ---------------------------------------------------------------------
DROP TABLE IF EXISTS audit_log;
CREATE TABLE audit_log
(
    id         CHAR(36) PRIMARY KEY,
    user_id    CHAR(36),
    action     VARCHAR(64) NOT NULL,
    entity     VARCHAR(64),
    entity_id  CHAR(36),
    ip_address VARCHAR(45),
    created    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- ---------------------------------------------------------------------
--  Индексы
-- ---------------------------------------------------------------------
CREATE INDEX idx_auth_codes_user        ON auth_codes (user_id);
CREATE INDEX idx_cards_user             ON cards (user_id);
CREATE INDEX idx_card_tx_source         ON card_transactions (source);
CREATE INDEX idx_card_tx_target         ON card_transactions (target);
CREATE INDEX idx_payment_tx             ON payment_requests (transaction_id);
CREATE INDEX idx_audit_user             ON audit_log (user_id);
CREATE INDEX idx_audit_action           ON audit_log (action);