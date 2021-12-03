DROP TABLE EMPLOYEE IF EXISTS;

CREATE TABLE EMPLOYEE (
                         EMPLOYEE_ID BIGINT AUTO_INCREMENT,
                         FIRST_NAME VARCHAR2(255) NOT NULL,
                         MIDDLE_NAME VARCHAR2(255),
                         LAST_NAME VARCHAR2(255) NOT NULL ,
                         PRIMARY KEY (EMPLOYEE_ID));

