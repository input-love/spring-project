-- Вставка данных в таблицу accounts
INSERT INTO accounts (account_type, balance)
VALUES ('DEBIT', 1000.00),
       ('CREDIT', 1500.00);

-- Вставка данных в таблицу transactions
INSERT INTO transactions (amount, transaction_time)
VALUES (250.00, '2024-10-01 10:30:00'),
       (75.50, '2024-10-02 11:00:00'),
       (1000.00, '2024-10-03 11:30:00');