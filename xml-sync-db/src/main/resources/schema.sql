DROP TABLE IF EXISTS department;
CREATE TABLE department
(
    id SERIAL,
    dep_code VARCHAR(20) NOT NULL,
    dep_job VARCHAR (100) NOT NULL,
    description VARCHAR(255)

);
CREATE UNIQUE INDEX department_unique_idx
    ON department (dep_code, dep_job)