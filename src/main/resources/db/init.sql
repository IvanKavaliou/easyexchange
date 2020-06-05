DROP TABLE transactions IF EXISTS ;
DROP TABLE accounts IF EXISTS ;
DROP TABLE users IF EXISTS ;
DROP SEQUENCE global_seq IF EXISTS ;

CREATE SEQUENCE global_seq AS INTEGER START WITH 100000;

CREATE TABLE users (
                       id INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
                       email VARCHAR(100) NOT NULL,
                       password VARCHAR(20) NOT NULL,
                       registred TIMESTAMP DEFAULT  now(),
                       enabled BOOLEAN DEFAULT true
);

INSERT INTO users (email, password) VALUES
('user@user.com', 'user'),
('user2@user.com', 'user2');

CREATE TABLE accounts (
                          id INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
                          id_user INTEGER NOT NULL,
                          currency VARCHAR(3) NOT NULL,
                          value DECIMAL NOT NULL,
                          FOREIGN KEY (id_user) REFERENCES users (id) ON DELETE CASCADE

);

INSERT INTO accounts (id_user, currency, value) VALUES
(100000, 'USD', 200),
(100000, 'EUR', 530),
(100001, 'USD', 100);

CREATE TABLE transactions (
                              id INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
                              id_account INTEGER NOT NULL,
                              transaction_type VARCHAR(20) NOT NULL,
                              value DECIMAL NOT NULL,
                              date TIMESTAMP DEFAULT  now(),
                              FOREIGN KEY (id_account) REFERENCES accounts (id) ON DELETE CASCADE
);

INSERT INTO transactions (id_account, transaction_type, value) VALUES
(100002, 'TOP_UP', 200),
(100003, 'TOP_UP', 530),
(100004, 'TOP_UP', 100);