BEGIN
  EXECUTE IMMEDIATE 'DROP TABLE RESULT';
EXCEPTION
  WHEN OTHERS THEN
     NULL;
END;
/

BEGIN
  EXECUTE IMMEDIATE 'DROP SEQUENCE "RESULT_SEQ"';
EXCEPTION
  WHEN OTHERS THEN
     NULL;
END;
/
 CREATE SEQUENCE   "RESULT_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE
/

BEGIN
  EXECUTE IMMEDIATE 
  'DROP TABLE USERS';
EXCEPTION
  WHEN OTHERS THEN
     NULL;
END;
/

BEGIN
  EXECUTE IMMEDIATE 'DROP SEQUENCE "USERS_SEQ"';
EXCEPTION
  WHEN OTHERS THEN
     NULL;
END;
/

 CREATE SEQUENCE   "USERS_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE
/

CREATE TABLE  "USERS" 
   (	"ID" NUMBER NOT NULL ENABLE, 
	"USERNAME" VARCHAR2(50) NOT NULL ENABLE, 
	"EMAIL" VARCHAR2(50) NOT NULL ENABLE, 
	"PASSWORD" VARCHAR2(64) NOT NULL ENABLE, 
	 CONSTRAINT "USERS_PK" PRIMARY KEY ("ID") ENABLE, 
	 CONSTRAINT "USERS_UK1" UNIQUE ("USERNAME") ENABLE, 
	 CONSTRAINT "USERS_UK2" UNIQUE ("EMAIL") ENABLE
   )
/


CREATE OR REPLACE TRIGGER  "BI_USERS" 
BEFORE
insert on "USERS"
for each row
 WHEN (NEW.ID is null) begin
select "USERS_SEQ".nextval into :NEW.ID from dual;
end;
/
ALTER TRIGGER  "BI_USERS" ENABLE
/

CREATE TABLE  "RESULT" 
   (	"ID" NUMBER NOT NULL ENABLE, 
	"RESULT_DATE" DATE NOT NULL ENABLE, 
	"SECONDS" NUMBER NOT NULL ENABLE, 
	"USER_ID" NUMBER NOT NULL ENABLE, 
	 CONSTRAINT "RESULT_PK" PRIMARY KEY ("ID") ENABLE, 
	 CONSTRAINT "RESULT_FK" FOREIGN KEY ("USER_ID")
	  REFERENCES  "USERS" ("ID") ON DELETE CASCADE ENABLE
   )
/


CREATE OR REPLACE TRIGGER  "BI_RESULT" 
BEFORE
insert on "RESULT"
for each row
 WHEN (NEW.ID is null) begin
select "RESULT_SEQ".nextval into :NEW.ID from dual;
end;
/
ALTER TRIGGER  "BI_RESULT" ENABLE
/

