INSERT INTO users (username, first_name, last_name, email,enabled, password, phone, address, account_non_expired, account_non_locked, credentials_non_expired)
VALUES
    ('jdoe', 'John', 'Doe', 'jdoe@example.com',false, 'password1', '555-1234', '123 Main St', TRUE, TRUE, TRUE),
    ('asmith', 'Alice', 'Smith', 'asmith@example.com',false, 'password2', '555-5678', '456 Elm St', TRUE, FALSE, TRUE),
    ('bwhite', 'Bob', 'White', 'bwhite@example.com',true, 'password3', '555-9101', '789 Oak St', TRUE, TRUE, FALSE),
    ('cjohnson', 'Carol', 'Johnson', 'cjohnson@example.com',false, 'password4', '555-1122', '321 Pine St', FALSE, TRUE, TRUE),
    ('dlee', 'David', 'Lee', 'dlee@example.com',true, 'password5', '555-3344', '654 Maple St', TRUE, TRUE, TRUE);