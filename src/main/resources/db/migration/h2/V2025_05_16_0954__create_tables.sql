-- Create tables for Patient Trial database --

CREATE TABLE dbo.PATIENT
(
    ID              BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NAME            VARCHAR(255),
    DATE_OF_BIRTH   TIMESTAMP,
    GENDER          CHAR(1),
    MEDICAL_HISTORY VARCHAR(255),
    CREATED_AT      TIMESTAMP NOT NULL,
    UPDATED_AT      TIMESTAMP NOT NULL
);

CREATE TABLE dbo.DRUG
(
    ID               BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NAME             VARCHAR(255),
    CHEMICAL_FORMULA VARCHAR(255),
    MANUFACTURER     VARCHAR(255),
    CREATED_AT       TIMESTAMP NOT NULL,
    UPDATED_AT       TIMESTAMP NOT NULL
);

CREATE TABLE dbo.TRIAL
(
    ID                   BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NAME                 VARCHAR(255),
    START_DATE           TIMESTAMP,
    END_DATE             TIMESTAMP,
    PROTOCOL_DESCRIPTION VARCHAR(255),
    CREATED_AT           TIMESTAMP NOT NULL,
    UPDATED_AT           TIMESTAMP NOT NULL
);

CREATE TABLE dbo.PATIENT_TRIAL
(
    ID               BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    PATIENT_ID       BIGINT    NOT NULL,
    TRIAL_ID         BIGINT    NOT NULL,
    ENROLLMENT_DATE  TIMESTAMP,
    TREATMENT_STATUS VARCHAR(255),
    CREATED_AT       TIMESTAMP NOT NULL,
    UPDATED_AT       TIMESTAMP NOT NULL,
    CONSTRAINT FK_PATIENT_TRIALS_PATIENT_ID FOREIGN KEY (PATIENT_ID) REFERENCES PATIENT (ID),
    CONSTRAINT FK_PATIENT_TRIALS_TRIAL_ID FOREIGN KEY (TRIAL_ID) REFERENCES TRIAL (ID)
);

CREATE TABLE dbo.PATIENT_TRIAL_DRUG
(
    ID                      BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    PATIENT_TRIAL_ID        BIGINT    NOT NULL,
    DRUG_ID                 BIGINT    NOT NULL,
    DOSAGE                  NUMERIC   NOT NULL,
    DOSAGE_MEASUREMENT_TYPE VARCHAR(5),
    CREATED_AT              TIMESTAMP NOT NULL,
    UPDATED_AT              TIMESTAMP NOT NULL,
    CONSTRAINT FK_PATIENT_TRIAL_DRUG_PATIENT_TRIAL_ID FOREIGN KEY (PATIENT_TRIAL_ID) REFERENCES PATIENT_TRIAL (ID),
    CONSTRAINT FK_PATIENT_TRIAL_DRUG_DRUG_ID FOREIGN KEY (DRUG_ID) REFERENCES DRUG (ID)
);

CREATE TABLE dbo.ADVERSE_EVENT
(
    ID                 BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    PATIENT_ID         BIGINT    NOT NULL,
    TRIAL_ID           BIGINT    NOT NULL,
    EVENT_DESCRIPTION  VARCHAR(255),
    EVENT_SEVERITY     VARCHAR(255),
    DATE_OF_OCCURRENCE TIMESTAMP,
    CREATED_AT         TIMESTAMP NOT NULL,
    UPDATED_AT         TIMESTAMP NOT NULL,
    CONSTRAINT FK_ADVERSE_EVENTS_PATIENT_ID FOREIGN KEY (PATIENT_ID) REFERENCES PATIENT (ID),
    CONSTRAINT FK_ADVERSE_EVENTS_TRIAL_ID FOREIGN KEY (TRIAL_ID) REFERENCES TRIAL (ID)
);