-- Вставка данных в таблицу clients
INSERT INTO clients (first_name, last_name, middle_name, blocked_for, blocked_whom)
VALUES ('John', 'Doe', 'Michael', false, NULL),
       ('Jane', 'Smith', 'Alice', true, 'Fraud detected'),
       ('Emily', 'Davis', 'Sarah', true, 'Policy violation');

-- Вставка данных в таблицу accounts
INSERT INTO accounts (account_type, account_status, balance, frozen_amount, client_id)
VALUES ('DEBIT', 'OPEN', 1000.10, 0.00, 3),
       ('CREDIT', 'OPEN', 1500.15, 0.00, 2),
       ('CREDIT', 'OPEN', 2000.20, 0.00, 1);

-- Вставка данных в таблицу transactions
INSERT INTO transactions (transaction_status, amount, transaction_time, account_id)
VALUES ('ACCEPTED', 1000.10, '2024-10-01 10:30:00', 3),
       ('ACCEPTED', 1500.15, '2024-10-02 11:00:00', 2),
       ('ACCEPTED', 2000.20, '2024-10-03 11:30:00', 1);