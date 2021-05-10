
--ORACLE


-- Table
CREATE TABLE "TEST"."Person" 
(
	"PersonID" NUMBER(*,0), 
	"FirstName" VARCHAR2(255 BYTE), 
	"LastName" VARCHAR2(255 BYTE), 
	"Address" VARCHAR2(255 BYTE), 
	"City" VARCHAR2(255 BYTE) 
)

-- INSERTS
INSERT INTO TEST.Person (PersonID, FirstName, LastName, Address, City) VALUES (1, 'aaa', 'bbb', 'ccc', 'ddd');
INSERT INTO TEST.Person (PersonID, FirstName, LastName, Address, City) VALUES (2, 'bbb', 'ccc', 'ddd', 'eee');
INSERT INTO TEST.Person (PersonID, FirstName, LastName, Address, City) VALUES (3, 'ccc', 'ddd', 'eee', 'fff');
INSERT INTO TEST.Person (PersonID, FirstName, LastName, Address, City) VALUES (4, 'ddd', 'eee', 'fff', 'ggg');
INSERT INTO TEST.Person (PersonID, FirstName, LastName, Address, City) VALUES (5, 'eee', 'fff', 'ggg', 'hhh');


--- BEFORE FUNCTION

-- TYPE Object
CREATE OR REPLACE TYPE Type_Person_Row AS OBJECT (
    PersonID int,
    FirstName varchar(255),
    LastName varchar(255),
    Address varchar(255),
    City varchar(255)
);

-- TYPE Table
CREATE OR REPLACE TYPE Type_Person_Tab IS TABLE OF Type_Person_Row;

--- Function
CREATE OR REPLACE FUNCTION Firsts_Persons (p_rows IN NUMBER)
   RETURN Type_Person_Tab
   IS v_ret Type_Person_Tab;

BEGIN

	v_ret := Type_Person_Tab();

	SELECT Type_Person_Row(PersonID, FirstName,LastName, Address, City)
	BULK COLLECT INTO v_ret
	FROM TEST.Person
	WHERE PersonId <= p_rows;
  
	RETURN v_ret;

END;

-- SELECT
SELECT * FROM TABLE(Firsts_Persons(2))
