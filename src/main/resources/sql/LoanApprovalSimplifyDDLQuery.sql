CREATE TABLE IF NOT EXISTS LOAN_APPLICATION
(
    Loan_application_Id          INTEGER auto_increment,
    loan_category VARCHAR(100) NOT NULL,
    amount      INTEGER NOT NULL,
    term        INTEGER NOT NULL,
    reason      VARCHAR(256),
    status      VARCHAR(10),
    COMMENTS    VARCHAR(256),
    process_instance_id INTEGER NOT NULL,
    customer_id INTEGER NOT NULL,
    PRIMARY KEY (Loan_application_Id)
);

CREATE TABLE IF NOT EXISTS LOAN_APPROVAL_TASK
(
    task_id VARCHAR(30),
    task_category VARCHAR(100) NOT NULL,
    Loan_application_Id          INTEGER NOT NULL,
    amount      INTEGER NOT NULL,
    term        INTEGER NOT NULL,
    reason      VARCHAR(256),
    status      VARCHAR(10),
    COMMENTS    VARCHAR(256),
    workflow_id INTEGER NOT NULL,
    customer_id INTEGER NOT NULL,
    PRIMARY KEY (task_id),
    FOREIGN KEY (Loan_application_Id) REFERENCES LOAN_APPLICATION (Loan_application_Id)
);
CREATE TABLE IF NOT EXISTS Loan_Assessment_task
(
    task_id                  VARCHAR(25) NOT NULL,
    task_instance_id         VARCHAR(50) NOT NULL,
    role                     VARCHAR(10) NOT NULL,
    stage                    VARCHAR(10) NOT NULL,
    loan_request_id          INTEGER     NOT NULL,
    PRIMARY KEY (task_id),
    FOREIGN KEY (loan_request_id) REFERENCES LOAN_REQUEST (id)
    );