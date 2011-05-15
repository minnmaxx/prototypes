-- Create Company Table
DROP SEQUENCE IF EXISTS company_id_seq CASCADE;
DROP SEQUENCE IF EXISTS seq_company_id CASCADE;
CREATE SEQUENCE seq_company_id
	START WITH 1;

DROP TABLE IF EXISTS company CASCADE;
CREATE TABLE company (
	id		bigint PRIMARY KEY DEFAULT nextval('seq_company_id'),
	name	varchar(128) NOT NULL CHECK (name <> '')
);



-- Create Proposal Table
DROP SEQUENCE IF EXISTS proposal_id_seq CASCADE;
DROP SEQUENCE IF EXISTS seq_proposal_id CASCADE;
CREATE SEQUENCE seq_proposal_id
	START WITH 1;
	
DROP TABLE IF EXISTS proposal CASCADE;
CREATE TABLE proposal
(
	id		bigint PRIMARY KEY DEFAULT nextval('seq_proposal_id'),
	"number"	varchar(128) NOT NULL,
	"companyId"	bigint NOT NULL REFERENCES Company(id) ON DELETE CASCADE ON UPDATE CASCADE
);
