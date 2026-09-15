CREATE TABLE app_user (
                          id BIGSERIAL PRIMARY KEY,
                          username VARCHAR(50) NOT NULL UNIQUE,
                          password_hash VARCHAR(255) NOT NULL,
                          salt VARCHAR(255) NOT NULL,
                          created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE customer (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          surname VARCHAR(100) NOT NULL,
                          created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE account (
                         account_number VARCHAR(34) PRIMARY KEY,
                         customer_id BIGINT NOT NULL REFERENCES customer(id),
                         account_type VARCHAR(20) NOT NULL,
                         balance NUMERIC(19,2) NOT NULL,
                         created_at TIMESTAMPTZ NOT NULL DEFAULT now(),

                         CONSTRAINT chk_account_type
                             CHECK (account_type IN ('CURRENT', 'SAVINGS'))
);

CREATE TABLE transaction (
                             id BIGSERIAL PRIMARY KEY,
                             account_number VARCHAR(34) NOT NULL REFERENCES account(account_number),
                             type VARCHAR(20) NOT NULL,
                             amount NUMERIC(19,2) NOT NULL,
                             created_at TIMESTAMPTZ NOT NULL DEFAULT now(),

                             CONSTRAINT chk_amount_positive
                                 CHECK (amount > 0)
);

CREATE INDEX idx_account_customer
    ON account(customer_id);

CREATE INDEX idx_tx_account_date
    ON transaction(account_number, created_at);