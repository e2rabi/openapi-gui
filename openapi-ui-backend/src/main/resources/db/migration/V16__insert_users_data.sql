INSERT INTO users (username, first_name, last_name, email, enabled, password, phone, address, account_non_expired, account_non_locked, credentials_non_expired, expiry_date,first_login_change_password,created,workspace_id,version)
VALUES
    ('user1', 'FirstName1', 'LastName1', 'user1@example.com', TRUE, 'password1', '1234567890', '123 Address St', TRUE, TRUE, TRUE, '2024-12-31',false,'2024-11-11 17:58:41.855271', 1,1),
    ('user2', 'FirstName2', 'LastName2', 'user2@example.com', FALSE, 'password2', '1234567891', '124 Address St', TRUE, TRUE, TRUE, '2024-12-31',false,'2024-11-24 16:58:41.855271', 2,1),
    ('user3', 'FirstName3', 'LastName3', 'user3@example.com', FALSE, 'password3', '1234567892', '125 Address St', FALSE, TRUE, TRUE, '2024-04-22',false,'2024-11-23 16:58:41.855271', 3,1),
    ('user4', 'FirstName4', 'LastName4', 'user4@example.com', FALSE, 'password4', '1234567893', '126 Address St', TRUE, TRUE, TRUE, '2024-12-31',false,'2024-11-22 16:58:41.855271', 4,1),
    ('user5', 'FirstName5', 'LastName5', 'user5@example.com', FALSE, 'password5', '1234567894', '127 Address St', TRUE, TRUE, TRUE, '2024-12-31',false,'2024-11-20 16:58:41.855271', 5,1),
    ('user6', 'FirstName6', 'LastName6', 'user6@example.com', TRUE, 'password6', '1234567895', '128 Address St', FALSE, TRUE, TRUE, '2024-05-03',false,'2024-10-20 16:58:41.855271', 6,1),
    ('user7', 'FirstName7', 'LastName7', 'user7@example.com', TRUE, 'password7', '1234567896', '129 Address St', TRUE, TRUE, TRUE, '2024-12-31',false,'2024-09-20 16:58:41.855271', 7,1),
    ('user8', 'FirstName8', 'LastName8', 'user8@example.com', FALSE, 'password8', '1234567897', '130 Address St', TRUE, TRUE, TRUE, '2024-12-31',false,'2024-01-20 16:58:41.855271', 8,1),
    ('user9', 'FirstName9', 'LastName9', 'user9@example.com', TRUE, 'password9', '1234567898', '131 Address St', FALSE, TRUE, TRUE, '2024-02-15',false,'2024-02-20 16:58:41.855271', 9,1),
    ('user10', 'FirstName10', 'LastName10', 'user10@example.com', TRUE, 'password10', '1234567899', '132 Address St', TRUE, TRUE, TRUE, '2024-12-31',false,'2024-03-20 16:58:41.855271', 10,1),
    ('user11', 'FirstName11', 'LastName11', 'user11@example.com', FALSE, 'password11', '1234567800', '133 Address St', TRUE, TRUE, TRUE, '2024-12-31',false,'2024-04-20 16:58:41.855271', 11,1),
    ('user12', 'FirstName12', 'LastName12', 'user12@example.com', TRUE, 'password12', '1234567801', '134 Address St', TRUE, TRUE, TRUE, '2024-12-31',false,'2024-05-20 16:58:41.855271', 12,1),
    ('user13', 'FirstName13', 'LastName13', 'user13@example.com', TRUE, 'password13', '1234567802', '135 Address St', FALSE, TRUE, TRUE, '2024-01-12',false,'2024-06-20 16:58:41.855271', 13,1),
    ('user14', 'FirstName14', 'LastName14', 'user14@example.com', FALSE, 'password14', '1234567803', '136 Address St', TRUE, TRUE, TRUE, '2024-12-31',false,'2024-07-20 16:58:41.855271', 14,1),
    ('user15', 'FirstName15', 'LastName15', 'user15@example.com', TRUE, 'password15', '1234567804', '137 Address St', FALSE, TRUE, TRUE, '2024-07-14',false, '2024-08-20 16:58:41.855271',15,1),
    ('user16', 'FirstName16', 'LastName16', 'user16@example.com', TRUE, 'password16', '1234567805', '138 Address St', FALSE, TRUE, TRUE, '2024-03-17',false,'2024-08-19 16:58:41.855271', 16,1),
    ('user17', 'FirstName17', 'LastName17', 'user17@example.com', FALSE, 'password17', '1234567806', '139 Address St', TRUE, TRUE, TRUE, '2024-12-31',false,'2024-08-18 16:58:41.855271', 17,1),
    ('user18', 'FirstName18', 'LastName18', 'user18@example.com', TRUE, 'password18', '1234567807', '140 Address St', TRUE, TRUE, TRUE, '2024-12-31',false, '2024-08-17 16:58:41.855271',18,1),
    ('user19', 'FirstName19', 'LastName19', 'user19@example.com', FALSE, 'password19', '1234567808', '141 Address St', TRUE, TRUE, TRUE, '2024-12-31',false,'2024-08-16 16:58:41.855271', 19,1),
    ('user20', 'FirstName20', 'LastName20', 'user20@example.com', TRUE, 'password20', '1234567809', '142 Address St', TRUE, TRUE, TRUE, '2024-12-31',false,'2024-08-15 16:58:41.855271', 20,1),
    ('user21', 'FirstName21', 'LastName21', 'user21@example.com', TRUE, 'password21', '1234567810', '143 Address St', FALSE, TRUE, TRUE, '2024-03-19',false,'2024-08-14 16:58:41.855271', 21,1),
    ('user22', 'FirstName22', 'LastName22', 'user22@example.com', FALSE, 'password22', '1234567811', '144 Address St', TRUE, TRUE, TRUE, '2024-12-31',false,'2024-08-13 16:58:41.855271', 22,1),
    ('user23', 'FirstName23', 'LastName23', 'user23@example.com', TRUE, 'password23', '1234567812', '145 Address St', FALSE, TRUE, TRUE, '2024-06-20',false,'2024-08-12 16:58:41.855271', 23,1),
    ('user24', 'FirstName24', 'LastName24', 'user24@example.com', FALSE, 'password24', '1234567813', '146 Address St', TRUE, TRUE, TRUE, '2024-12-31',false,'2024-08-11 16:58:41.855271', 24,1),
    ('user25', 'FirstName25', 'LastName25', 'user25@example.com', TRUE, 'password25', '1234567814', '147 Address St', TRUE, TRUE, TRUE, '2024-12-31',false,'2024-08-10 16:48:41.855271', 25,1);