-- Вставка данных в таблицу accounts
INSERT INTO accounts (account_type, account_status, balance, frozen_amount)
VALUES ('DEBIT', 'OPEN', 1000.00, 0.00),
       ('CREDIT', 'OPEN', 1500.00, 0.00);

-- Вставка данных в таблицу transactions
INSERT INTO transactions (transaction_status, amount, transaction_time)
VALUES ('ACCEPTED', 250.00, '2024-10-01 10:30:00'),
       ('ACCEPTED', 75.50, '2024-10-02 11:00:00'),
       ('ACCEPTED', 1000.00, '2024-10-03 11:30:00');

-- Вставка данных в таблицу client
INSERT INTO clients (first_name, last_name, middle_name, blocked_for, blocked_whom)
VALUES ('John', 'Doe', 'Michael', false, NULL),
       ('Jane', 'Smith', 'Alice', true, 'Fraud detected'),
       ('Emily', 'Davis', 'Sarah', true, 'Policy violation');
