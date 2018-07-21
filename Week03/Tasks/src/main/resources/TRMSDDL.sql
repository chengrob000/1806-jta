DROP TABLE Approval 
CASCADE CONSTRAINTS;
DROP TABLE Approval_Type 
CASCADE CONSTRAINTS;
DROP TABLE Attachment 
CASCADE CONSTRAINTS;
DROP TABLE Attachment_Category
CASCADE CONSTRAINTS;
DROP TABLE Department 
CASCADE CONSTRAINTS;
DROP TABLE Employee 
CASCADE CONSTRAINTS;
DROP TABLE Event 
CASCADE CONSTRAINTS;
DROP TABLE Event_Type 
CASCADE CONSTRAINTS;
DROP TABLE Grading_Format 
CASCADE CONSTRAINTS;
DROP TABLE MIME_Type 
CASCADE CONSTRAINTS;
DROP TABLE Reimbursement 
CASCADE CONSTRAINTS;
DROP SEQUENCE app_seq;
DROP SEQUENCE app_typ_seq;
DROP SEQUENCE att_seq;
DROP SEQUENCE att_cat_seq;
DROP SEQUENCE dep_seq;
DROP SEQUENCE emp_seq;
DROP SEQUENCE eve_seq;
DROP SEQUENCE eve_typ_seq;
DROP SEQUENCE gra_for_seq;
DROP SEQUENCE mim_typ_seq;
DROP SEQUENCE rei_seq;

CREATE TABLE Approval (
    app_id NUMBER PRIMARY KEY,
    app_typ_id NUMBER,
    app_rei_id NUMBER,
    app_isApproved CHAR(1)
        CHECK (app_isApproved IN ('N', 'Y')),
    app_reason VARCHAR2(4000)
);

CREATE TABLE Approval_Type (
    app_typ_id NUMBER PRIMARY KEY,
    app_typ_value CHAR(20) NOT NULL
        CHECK (app_typ_value IN ('BENEFITS_COORDINATOR', 
                                 'DEPARTMENT_HEAD', 
                                 'DIRECT_SUPERVISOR'))
);

CREATE TABLE Attachment (
    att_id NUMBER PRIMARY KEY,
    att_fk NUMBER,
    att_category NUMBER,
    att_mime NUMBER,
    att_name VARCHAR2(4000),
    att_size NUMBER,
    att_file BLOB DEFAULT EMPTY_BLOB()
);

CREATE TABLE Attachment_Category (
    att_cat_id NUMBER PRIMARY KEY,
    att_cat_value VARCHAR2(4000) NOT NULL
        CHECK (att_cat_value IN ('ADDITIONAL_INFO',
                                 'APPROVAL',
                                 'EVENT', 
                                 'GRADING_FORMAT'))
);

CREATE TABLE Department (
    dep_id NUMBER PRIMARY KEY,
    dep_name VARCHAR2(4000) UNIQUE
);

CREATE TABLE Employee (
    emp_id NUMBER PRIMARY KEY,
    emp_department NUMBER,
    emp_supervisor NUMBER,
    emp_available_reimbursement NUMBER DEFAULT 1000,
    emp_username VARCHAR2(4000) UNIQUE,
    emp_password VARCHAR2(4000),
    emp_firstname VARCHAR2(4000),
    emp_lastname VARCHAR2(4000),
    emp_email VARCHAR(4000),
    emp_isBenCo CHAR(1) DEFAULT 'N' NOT NULL
        CHECK (emp_isBenCo IN ('N', 'Y'))
);

CREATE TABLE Event (
    eve_id NUMBER PRIMARY KEY,
    eve_typ_id NUMBER,
    eve_cost NUMBER CHECK (eve_cost > 0),
    eve_datetime TIMESTAMP WITH LOCAL TIME ZONE,
    eve_description VARCHAR2(4000),
    eve_location VARCHAR2(4000),
    eve_work_missed INTERVAL DAY(9) TO SECOND(0)
);

CREATE TABLE Event_Type (
    eve_typ_id NUMBER PRIMARY KEY,
    eve_typ_coverage NUMBER
        CHECK (eve_typ_coverage IN (0.3, 0.6, 0.75, 0.8, 1)),
    eve_typ_value VARCHAR(40) NOT NULL
        CHECK (eve_typ_value IN ('CERTIFICATION',
                                 'CERTIFICATION_PREPARATION_CLASS',
                                 'SEMINAR', 
                                 'TECHNICAL_TRAINING',
                                 'UNIVERSITY_COURSE'))
);

CREATE TABLE Grading_Format (
    gra_for_id NUMBER PRIMARY KEY,
    gra_for_confirmed CHAR(1)
        CHECK (gra_for_confirmed IN ('N', 'Y')),
    gra_for_passing_cutoff NUMBER DEFAULT NULL
);

CREATE TABLE MIME_Type (
    mim_typ_id NUMBER PRIMARY KEY,
    mim_typ_extension VARCHAR2(5),
    mim_typ_value VARCHAR2(4000)
);

CREATE TABLE Reimbursement (
  rei_id NUMBER PRIMARY KEY,
  rei_emp_id NUMBER,
  rei_awarded NUMBER DEFAULT 0,
  rei_isCancelled CHAR(1) DEFAULT 'N' NOT NULL
    CHECK (rei_isCancelled IN ('N', 'Y')),
  rei_isPending CHAR(1) DEFAULT 'Y' NOT NULL
    CHECK (rei_isPending IN ('N', 'Y')),
  rei_justification VARCHAR2(4000),
  rei_reason_exceeded_max VARCHAR2(4000)
);

ALTER TABLE Approval   
ADD CONSTRAINT fk_app_rei 
FOREIGN KEY (app_rei_id) 
REFERENCES Reimbursement(rei_id)
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE Approval   
ADD CONSTRAINT fk_app_typ 
FOREIGN KEY (app_typ_id) 
REFERENCES Approval_Type(app_typ_id)
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE Attachment
ADD CONSTRAINT fk_att_cat
FOREIGN KEY (att_fk)
REFERENCES Attachment_Category(att_cat_id)
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE Attachment
ADD CONSTRAINT fk_att_app
FOREIGN KEY (att_fk)
REFERENCES Approval(app_id)
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE Attachment
ADD CONSTRAINT fk_att_eve
FOREIGN KEY (att_fk)
REFERENCES Event(eve_id)
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE Attachment
ADD CONSTRAINT fk_att_gra_for
FOREIGN KEY (att_fk)
REFERENCES Grading_Format(gra_for_id)
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE Attachment
ADD CONSTRAINT fk_att_mim_typ
FOREIGN KEY (att_mime)
REFERENCES MIME_Type(mim_typ_id)
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE Employee    
ADD CONSTRAINT fk_emp_dep 
FOREIGN KEY (emp_department) 
REFERENCES Department(dep_id)
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE Employee    
ADD CONSTRAINT fk_emp_emp 
FOREIGN KEY (emp_supervisor) 
REFERENCES Employee(emp_id)
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE Event    
ADD CONSTRAINT fk_eve_rei 
FOREIGN KEY (eve_id) 
REFERENCES Reimbursement(rei_id)
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE Event    
ADD CONSTRAINT fk_eve_typ 
FOREIGN KEY (eve_typ_id) 
REFERENCES Event_Type(eve_typ_id)
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE Grading_Format    
ADD CONSTRAINT fk_gra_for_rei 
FOREIGN KEY (gra_for_id) 
REFERENCES Reimbursement(rei_id)
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE Reimbursement    
ADD CONSTRAINT fk_rei_emp 
FOREIGN KEY (rei_emp_id) 
REFERENCES Employee(emp_id)
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE Reimbursement    
ADD CONSTRAINT fk_rei_eve 
FOREIGN KEY (rei_id) 
REFERENCES Event(eve_id)
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE Reimbursement    
ADD CONSTRAINT fk_rei_gra_for 
FOREIGN KEY (rei_id) 
REFERENCES Grading_Format(gra_for_id)
DEFERRABLE INITIALLY DEFERRED;

CREATE OR REPLACE VIEW Attachment_View AS
SELECT att_id AS A_AttachmentID,
       att_fk AS A_AttachmentFK,
       att_name AS A_AttachmentName,
       att_size AS A_AttachmentSize,
       att_cat_value AS A_AttachmentCategory,
       mim_typ_value AS A_AttachmentMIME,
       att_file AS A_AttachmentFile
FROM Attachment 
INNER JOIN MIME_Type ON att_mime = mim_typ_id
INNER JOIN Attachment_Category ON att_category = att_cat_id;

CREATE OR REPLACE VIEW Employee_View AS
SELECT ER.emp_username AS E_Supervisor, 
       EL.emp_available_reimbursement AS E_AvailableReimbursement, 
       EL.emp_username AS E_Username, EL.emp_password AS E_Password, 
       EL.emp_firstname AS E_FirstName, EL.emp_lastname AS E_LastName, 
       EL.emp_email AS E_Email, EL.emp_isBenco AS E_IsBenCo,
       rei_id AS E_ReimbursementID, rei_awarded AS E_AwardedReimbursement,
       rei_isCancelled AS E_IsCancelled, rei_isPending AS E_IsPending,
       rei_justification AS E_ReimbursementJustification, 
       rei_reason_exceeded_max AS E_ReasonExceededMax, eve_cost AS E_Cost,
       eve_datetime AS E_Date, eve_description AS E_Description, 
       eve_location AS E_Location, eve_work_missed AS E_WorkTimeMissed,
       ET.eve_typ_coverage AS E_PercentCoverage,
       ET.eve_typ_value AS E_EventType
FROM Employee EL
LEFT JOIN Employee ER ON EL.emp_supervisor = ER.emp_id
LEFT JOIN Reimbursement ON EL.emp_id = rei_emp_id
LEFT JOIN Event E ON rei_id = eve_id
LEFT JOIN Event_Type ET ON E.eve_typ_id = ET.eve_typ_id;

CREATE SEQUENCE app_seq
START WITH 1
INCREMENT BY 1;
/

CREATE OR REPLACE TRIGGER app_tri
BEFORE INSERT ON Approval
FOR EACH ROW
BEGIN
    IF :new.app_id IS NULL THEN
        SELECT app_seq.NEXTVAL 
        INTO :new.app_id FROM dual;
    END IF;
END;
/

CREATE SEQUENCE app_typ_seq
START WITH 1
INCREMENT BY 1;
/

CREATE OR REPLACE TRIGGER app_typ_tri
BEFORE INSERT ON Approval_Type
FOR EACH ROW
BEGIN
    IF :new.app_typ_id IS NULL THEN
        SELECT app_typ_seq.NEXTVAL 
        INTO :new.app_typ_id FROM dual;
    END IF;
END;
/

CREATE SEQUENCE att_seq
START WITH 1
INCREMENT BY 1;
/

CREATE OR REPLACE TRIGGER att_tri
BEFORE INSERT ON Attachment
FOR EACH ROW
BEGIN
    IF :new.att_id IS NULL THEN
        SELECT att_seq.NEXTVAL 
        INTO :new.att_id FROM dual;
    END IF;
END;
/

CREATE SEQUENCE att_cat_seq
START WITH 1
INCREMENT BY 1;
/

CREATE OR REPLACE TRIGGER att_cat_tri
BEFORE INSERT ON Attachment_Category
FOR EACH ROW
BEGIN
    IF :new.att_cat_id IS NULL THEN
        SELECT att_cat_seq.NEXTVAL 
        INTO :new.att_cat_id FROM dual;
    END IF;
END;
/

CREATE SEQUENCE dep_seq
START WITH 1
INCREMENT BY 1;
/

CREATE OR REPLACE TRIGGER dep_tri
BEFORE INSERT ON Department
FOR EACH ROW
BEGIN
    IF :new.dep_id IS NULL THEN
        SELECT dep_seq.NEXTVAL 
        INTO :new.dep_id FROM dual;
    END IF;
END;
/

CREATE SEQUENCE emp_seq
START WITH 1
INCREMENT BY 1;
/

CREATE OR REPLACE TRIGGER emp_tri
BEFORE INSERT ON Employee
FOR EACH ROW
BEGIN
    IF :new.emp_id IS NULL THEN
        SELECT emp_seq.NEXTVAL 
        INTO :new.emp_id FROM dual;
    END IF;
END;
/

CREATE SEQUENCE eve_seq
START WITH 1
INCREMENT BY 1;
/

CREATE OR REPLACE TRIGGER eve_tri
BEFORE INSERT ON Event
FOR EACH ROW
BEGIN
    IF :new.eve_id IS NULL THEN
        SELECT eve_seq.NEXTVAL 
        INTO :new.eve_id FROM dual;
    END IF;
END;
/

CREATE SEQUENCE eve_typ_seq
START WITH 1
INCREMENT BY 1;
/

CREATE OR REPLACE TRIGGER eve_typ_tri
BEFORE INSERT ON Event_Type
FOR EACH ROW
BEGIN
    IF :new.eve_typ_id IS NULL THEN
        SELECT eve_typ_seq.NEXTVAL 
        INTO :new.eve_typ_id FROM dual;
    END IF;
END;
/

CREATE SEQUENCE gra_for_seq
START WITH 1
INCREMENT BY 1;
/

CREATE OR REPLACE TRIGGER gra_for_tri
BEFORE INSERT ON Grading_Format
FOR EACH ROW
BEGIN
    IF :new.gra_for_id IS NULL THEN
        SELECT gra_for_seq.NEXTVAL 
        INTO :new.gra_for_id FROM dual;
    END IF;
END;
/

CREATE SEQUENCE mim_typ_seq
START WITH 1
INCREMENT BY 1;
/

CREATE OR REPLACE TRIGGER mim_typ_tri
BEFORE INSERT ON MIME_Type
FOR EACH ROW
BEGIN
    IF :new.mim_typ_id IS NULL THEN
        SELECT mim_typ_seq.NEXTVAL 
        INTO :new.mim_typ_id FROM dual;
    END IF;
END;
/

CREATE SEQUENCE rei_seq
START WITH 1
INCREMENT BY 1;
/

CREATE OR REPLACE TRIGGER rei_tri
BEFORE INSERT ON Reimbursement
FOR EACH ROW
BEGIN
    IF :new.rei_id IS NULL THEN
        SELECT rei_seq.NEXTVAL 
        INTO :new.rei_id FROM dual;
    END IF;
END;
/

INSERT INTO Approval_Type(app_typ_value)
VALUES ('BENEFITS_COORDINATOR');
INSERT INTO Approval_Type(app_typ_value)
VALUES ('DEPARTMENT_HEAD');
INSERT INTO Approval_Type(app_typ_value)
VALUES ('DIRECT_SUPERVISOR');

INSERT INTO Attachment_Category(att_cat_value)
VALUES('ADDITIONAL_INFO');
INSERT INTO Attachment_Category(att_cat_value)
VALUES('APPROVAL');
INSERT INTO Attachment_Category(att_cat_value)
VALUES('EVENT');
INSERT INTO Attachment_Category(att_cat_value)
VALUES('GRADING_FORMAT');

INSERT INTO Event_Type(eve_typ_value, eve_typ_coverage)
VALUES('CERTIFICATION', 1);
INSERT INTO Event_Type(eve_typ_value, eve_typ_coverage)
VALUES('CERTIFICATION_PREPARATION_CLASS', 0.75);
INSERT INTO Event_Type(eve_typ_value, eve_typ_coverage)
VALUES('SEMINAR', 0.6);
INSERT INTO Event_Type(eve_typ_value, eve_typ_coverage)
VALUES('TECHNICAL_TRAINING', 0.3);
INSERT INTO Event_Type(eve_typ_value, eve_typ_coverage)
VALUES('UNIVERSITY_COURSE', 0.8);

INSERT INTO MIME_Type(mim_typ_extension, mim_typ_value)
VALUES('.doc', 'application/msword');
INSERT INTO MIME_Type(mim_typ_extension, mim_typ_value)
VALUES('.docx', 
       'application/vnd.openxmlformats-officedocument.wordprocessingml.document');
INSERT INTO MIME_Type(mim_typ_extension, mim_typ_value)
VALUES('.jpg', 'image/jpeg');
INSERT INTO MIME_Type(mim_typ_extension, mim_typ_value)
VALUES('.jpeg', 'image/jpeg');
INSERT INTO MIME_Type(mim_typ_extension, mim_typ_value)
VALUES('.msg', 'application/vnd.ms-outlook');
INSERT INTO MIME_Type(mim_typ_extension, mim_typ_value)
VALUES('.odp', 'application/vnd.oasis.opendocument.presentation');
INSERT INTO MIME_Type(mim_typ_extension, mim_typ_value)
VALUES('.odt', 'application/vnd.oasis.opendocument.text');
INSERT INTO MIME_Type(mim_typ_extension, mim_typ_value)
VALUES('.png', 'image/png');
INSERT INTO MIME_Type(mim_typ_extension, mim_typ_value)
VALUES('.pdf', 'application/pdf');
INSERT INTO MIME_Type(mim_typ_extension, mim_typ_value)
VALUES('.ppt', 'application/vnd.ms-powerpoint');
INSERT INTO MIME_Type(mim_typ_extension, mim_typ_value)
VALUES('.pptx',
       'application/vnd.openxmlformats-officedocument.presentationml.presentation');
INSERT INTO MIME_Type(mim_typ_extension, mim_typ_value)
VALUES('.txt', 'text/plain');
INSERT INTO MIME_Type(mim_typ_extension, mim_typ_value)
VALUES('.xls', 'application/vnd.ms-excel');
INSERT INTO MIME_Type(mim_typ_extension, mim_typ_value)
VALUES('.xlsx',
       'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');

CREATE OR REPLACE FUNCTION getEmployee(username IN VARCHAR2)
RETURN NUMBER
IS
    empID NUMBER;
    
    CURSOR cur IS
    SELECT emp_id
    FROM Employee
    WHERE emp_username = username;
BEGIN
    OPEN cur;
    FETCH cur INTO empID;
    
    IF cur%NOTFOUND THEN
      empID := 0;
    END IF;
    
    CLOSE cur;
    
    RETURN empID;
END;
/

CREATE OR REPLACE FUNCTION getEventType(typ IN VARCHAR2)
RETURN NUMBER
IS
    evetypID NUMBER;
    NONEXISTENT_EVENT_TYPE EXCEPTION;
    
    CURSOR cur IS
    SELECT eve_typ_id
    FROM Event_Type
    WHERE eve_typ_value = typ;
BEGIN
    OPEN cur;
    FETCH cur INTO evetypID;
    
    IF cur%NOTFOUND THEN
      RAISE NONEXISTENT_EVENT_TYPE;
    END IF;
    
    CLOSE cur;
    
    RETURN evetypID;
END;
/

CREATE OR REPLACE FUNCTION checkSupervisor(supervisor IN VARCHAR2)
RETURN NUMBER
IS
    supID NUMBER;
    NONEXISTENT_EMPLOYEE EXCEPTION;
BEGIN
    IF supervisor IS NULL THEN
        supID := NULL;
    ELSE
        supID := getEmployee(supervisor);
    
        IF supID = 0 THEN
            RAISE NONEXISTENT_EMPLOYEE;
        END IF;
    END IF;
    
    RETURN supID;
END;
/

CREATE OR REPLACE FUNCTION getDepartment(nam IN VARCHAR2)
RETURN NUMBER
IS
    depID NUMBER;
    
    CURSOR cur IS
    SELECT dep_id
    FROM Department
    WHERE dep_name = UPPER(nam);
BEGIN
    OPEN cur;
    FETCH cur INTO depID;
    
    IF cur%NOTFOUND THEN
      depID := 0;
    END IF;
    
    CLOSE cur;
    
    RETURN depID;
END;
/

CREATE OR REPLACE FUNCTION getCategory(cat IN VARCHAR2)
RETURN NUMBER
IS
    catID NUMBER;
    NONEXISTENT_ATTACH_CATEGORY EXCEPTION;
    
    CURSOR cur IS
    SELECT att_cat_id
    FROM Attachment_Category
    WHERE att_cat_value = cat;
BEGIN
    OPEN cur;
    FETCH cur INTO catID;
    
    IF cur%NOTFOUND THEN
      RAISE NONEXISTENT_ATTACH_CATEGORY;
    END IF;
    
    CLOSE cur;
    
    RETURN catID;
END;
/

CREATE OR REPLACE FUNCTION getMIME(extension IN VARCHAR2)
RETURN NUMBER
IS
    mimID NUMBER;
    NONEXISTENT_MIME_TYPE EXCEPTION;
    
    CURSOR cur IS
    SELECT mim_typ_id
    FROM MIME_Type
    WHERE mim_typ_extension = extension;
BEGIN
    OPEN cur;
    FETCH cur INTO mimID;
    
    IF cur%NOTFOUND THEN
      RAISE NONEXISTENT_MIME_TYPE;
    END IF;
    
    CLOSE cur;
    
    RETURN mimID;
END;
/

CREATE OR REPLACE PROCEDURE insertAttachment(cat IN VARCHAR2,
                                             fk IN NUMBER, 
                                             filename IN VARCHAR2,
                                             extension IN VARCHAR2,
                                             filesize IN NUMBER,
                                             fil IN BLOB)
IS
    catID NUMBER;
    mimID NUMBER;
BEGIN
    catID := getCategory(cat);
    mimID := getMIME(extension);
    INSERT INTO Attachment(att_fk, att_category, att_mime, att_name, att_size,
                           att_file)
    VALUES (fk, catID, mimID, filename, filesize, fil);
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE insertDepartment(department IN VARCHAR2)
IS
BEGIN
    INSERT INTO Department(dep_name)
    VALUES(UPPER(department));
END;
/

CREATE OR REPLACE PROCEDURE insertEmployee(username IN VARCHAR2, 
                                           pas IN VARCHAR2, 
                                           firstname IN VARCHAR2, 
                                           lastname IN VARCHAR2, 
                                           department IN VARCHAR2,
                                           email IN VARCHAR2,
                                           supervisor IN VARCHAR2, 
                                           isBenCo IN CHAR)
IS
    depID NUMBER;
    supID NUMBER;
BEGIN
    supID := checkSupervisor(supervisor);
    depID := getDepartment(department);
        
    IF depID = 0 THEN
        insertDepartment(department);
        depID := getDepartment(department);
    END IF;

    INSERT INTO Employee(emp_username, emp_password, emp_firstname,
                         emp_lastname, emp_department, emp_email,
                         emp_supervisor, emp_isBenco)
    VALUES(username, pas, firstname, lastname, depID, email, supID, isBenCo);
    COMMIT;
END;
/

CALL insertEmployee('swilery', 'swilery', 'Walter', 'Xia', 'Computer Science',
                    'swilery@gmail.com', NULL, 'Y');
CALL insertEmployee('walterx', 'walterx', 'Walter', 'Xia', 'Computer Science',
                    'swilery@utexas.edu', 'swilery', 'N');
--CALL insertEmployee('ryanl', 'ryanl', 'Ryan', 'Lessley', 'Computer Science',
--                    'bobbertb', 'Y');

CREATE OR REPLACE PROCEDURE insertEvent(typ IN VARCHAR2, 
                                        cos IN NUMBER, 
                                        dat IN VARCHAR2,
                                        loc IN VARCHAR2,
                                        work_missed IN VARCHAR2,
                                        des IN VARCHAR2)
IS
    evetypID NUMBER;
BEGIN
    evetypID := getEventType(typ);
    INSERT INTO Event(eve_typ_id, eve_cost, eve_datetime, eve_location, 
                    eve_work_missed, eve_description)
    VALUES(evetypID, cos, dat, loc, TO_DSINTERVAL(work_missed), des);
END;
/

CREATE OR REPLACE PROCEDURE insertGradingFormat(passingCutoff IN NUMBER)
IS
BEGIN
    INSERT INTO Grading_Format(gra_for_passing_cutoff)
    VALUES(passingCutoff);
END;
/

CREATE OR REPLACE PROCEDURE insertApproval(reiID IN NUMBER)
IS
    approvals NUMBER;
    counter NUMBER;
BEGIN
    SELECT COUNT(*) 
    INTO approvals 
    FROM Approval_Type;
    
    FOR counter IN 1..approvals LOOP
        INSERT INTO Approval(app_typ_id, app_rei_id)
        VALUES(counter, reiID);
    END LOOP;
END;
/

CREATE OR REPLACE PROCEDURE insertReimbursement(employee IN VARCHAR2, 
                                                typ IN VARCHAR2, 
                                                cos IN NUMBER, 
                                                dat IN VARCHAR2, 
                                                loc IN VARCHAR2,
                                                work_missed IN VARCHAR2,
--                                                    INTERVAL DAY TO SECOND,
                                                passingCutoff IN NUMBER,
                                                des IN VARCHAR2,
                                                justification IN VARCHAR2)
IS
    empID NUMBER;
    reiID NUMBER;
    NONEXISTENT_EMPLOYEE EXCEPTION;
BEGIN
    empID := getEmployee(employee);
    
    IF empID = 0 THEN
        RAISE NONEXISTENT_EMPLOYEE;
    END IF;
    
    INSERT INTO Reimbursement(rei_emp_id, rei_justification)
    VALUES(empID, justification);
    
    insertEvent(typ, cos, dat, loc, work_missed, des);
    insertGradingFormat(passingCutoff);
    SELECT rei_seq.CURRVAL
    INTO reiID
    FROM dual;
    insertApproval(reiID);
    COMMIT;
END;
/

CALL insertReimbursement('walterx', 'TECHNICAL_TRAINING', 20000,
                         TIMESTAMP '2018-06-18 8:30:00', 'Arlington, TX',
                         INTERVAL '70' DAY, 0.7, 'Revature training', 
                         'i dunno y');
CALL insertReimbursement('walterx', 'CERTIFICATION', 10,
                         TIMESTAMP '2018-06-25 9:00:00', 'Arlington, TX',
                         INTERVAL '70' DAY, 0.7, 'Enthuware',
                         'Not showing up on my RapidCard.');
--CALL insertEventAttachment(1, 
--    '10369913_267485166779577_5573839803579672783_n.jpg');
--CALL insertApprovalAttachment(1, 'Interview Prep Handbook.doc');
--CALL insertApprovalAdditionalInfo(1, 'Journey to the West Vocabulary.odt');
--CALL updateGradingFormatProof(1, 'What does this poem in Journey to the West mean.pdf');

COMMIT;