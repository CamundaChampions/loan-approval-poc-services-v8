CREATE TABLE IF NOT EXISTS LOAN_APPLICATION
(
    Loan_application_Id          INTEGER auto_increment,
    loan_category VARCHAR(100) NOT NULL,
    amount      INTEGER NOT NULL,
    term        INTEGER NOT NULL,
    reason      VARCHAR(256),
    status      VARCHAR(100),
    COMMENTS    VARCHAR(256),
    process_instance_id VARCHAR(100),
    customer_id VARCHAR(100) NOT NULL,
    PRIMARY KEY (Loan_application_Id)
);

CREATE TABLE IF NOT EXISTS Loan_Approval_task
(
    task_id                  VARCHAR(25) NOT NULL,
    task_category VARCHAR(100) NOT NULL,
    Loan_application_Id          INTEGER NOT NULL,
    task_instance_id         VARCHAR(50) NOT NULL,
    status                    VARCHAR(100) NOT NULL,
    PRIMARY KEY (task_id),
    FOREIGN KEY (Loan_application_Id) REFERENCES LOAN_APPLICATION (Loan_application_Id)
 );