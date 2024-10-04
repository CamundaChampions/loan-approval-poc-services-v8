CREATE TABLE IF NOT EXISTS customer
(
    id             INTEGER auto_increment,
    first_Name     VARCHAR(25) NOT NULL,
    last_name      VARCHAR(25) NOT NULL,
    ssn            VARCHAR(10),
    dob            DATE        NOT NULL,
    marital_status VARCHAR(10) NOT NULL,
    email          VARCHAR(50),
    phone          VARCHAR(10),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS LOAN_REQUEST
(
    id          INTEGER auto_increment,
    amount      INTEGER NOT NULL,
    term        INTEGER NOT NULL,
    reason      VARCHAR(256),
    status      VARCHAR(10),
    COMMENTS    VARCHAR(256),
    workflow_id INTEGER NOT NULL,
    customer_id INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE TABLE IF NOT EXISTS Deposit_Amount
(
    id              INTEGER auto_increment,
    account_number  VARCHAR(50) NOT NULL,
    bank_name       VARCHAR(50) NOT NULL,
    amount_credited INTEGER     NOT NULL,
    account_type    VARCHAR(10) NOT NULL,
    customer_id     INTEGER     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE TABLE IF NOT EXISTS ADDRESS
(
    id          INTEGER auto_increment,
    street      VARCHAR(100),
    city        VARCHAR(50) NOT NULL,
    "state"     VARCHAR(50) NOT NULL,
    zipcode     INTEGER     NOT NULL,
    country     VARCHAR(25) NOT NULL,
    customer_id INTEGER     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE TABLE IF NOT EXISTS Employment
(
    id            INTEGER auto_increment,
    employer_name VARCHAR(80) NOT NULL,
    jobTitle      VARCHAR(50) NOT NULL,
    annual_income INTEGER     NOT NULL,
    experience    INTEGER     NOT NULL,
    customer_id   INTEGER     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE TABLE IF NOT EXISTS Loan_Documents
(
    id              INTEGER auto_increment,
    document_type   VARCHAR(25) NOT NULL,
    fileName        VARCHAR(25) NOT NULL,
    File_Path       VARCHAR(80) NOT NULL,
    loan_request_id INTEGER     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (loan_request_id) REFERENCES LOAN_REQUEST (id)
);

CREATE TABLE IF NOT EXISTS cash_deposit_collateral
(
    id              INTEGER auto_increment,
    loan_request_id INTEGER     NOT NULL,
    bank_name       VARCHAR(50) NOT NULL,
    account_number  VARCHAR(50) NOT NULL,
    amount_balance  INTEGER     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (loan_request_id) REFERENCES LOAN_REQUEST (id)
);

CREATE TABLE IF NOT EXISTS management_task
(
    id              INTEGER auto_increment,
    "type"          VARCHAR(10) NOT NULL,
    stage           VARCHAR(10) NOT NULL,
    loan_request_id INTEGER     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (loan_request_id) REFERENCES LOAN_REQUEST (id)
);

CREATE TABLE IF NOT EXISTS Risk_Assessment
(
    id              INTEGER auto_increment,
    creadit_score   INTEGER     NOT NULL,
    credit_debts    INTEGER     NOT NULL,
    category        VARCHAR(20) NOT NULL,
    reason          VARCHAR(256),
    loan_request_id INTEGER     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (loan_request_id) REFERENCES LOAN_REQUEST (id)
);