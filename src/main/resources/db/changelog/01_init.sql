CREATE TABLE users
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    username    VARCHAR(20) NOT NULL,
    password    VARCHAR(60) NOT NULL,
    CONSTRAINT unique_username UNIQUE (username)
);
CREATE INDEX ix__users_username ON users(username);
CREATE TABLE accounts
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    user_id     BIGINT NOT NULL,
    CONSTRAINT fk__accounts_user_id
      FOREIGN KEY(user_id)
	  REFERENCES users(id)
);
CREATE INDEX ix__accounts_user_id ON accounts(user_id);
CREATE TABLE transactions
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    account_id  BIGINT NOT NULL,
    amount      BIGINT NOT NULL,
    CONSTRAINT fk__transactions_account_id
      FOREIGN KEY(account_id)
	  REFERENCES accounts(id)
);
CREATE INDEX ix__transactions_account_id ON transactions(account_id);