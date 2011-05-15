-------------------------------------------------------------
-- Back Office
-------------------------------------------------------------

-- Create Well Table
DROP SEQUENCE IF EXISTS seq_well_id;
CREATE SEQUENCE seq_well_id START WITH 1;

DROP TABLE IF EXISTS Well CASCADE;
CREATE TABLE Well (
	id			bigint DEFAULT nextval('seq_well_id') PRIMARY KEY,
	name		varchar(128),
	number 		varchar(128),
	longtitude	float,
	latitude 	float,
	gmtOffset	int
);


-- Create Company Table
DROP SEQUENCE IF EXISTS seq_company_id;
CREATE SEQUENCE seq_company_id START WITH 1;

DROP TABLE IF EXISTS company CASCADE;
CREATE TABLE company (
	id		bigint DEFAULT nextval('seq_company_id') PRIMARY KEY,
	name	varchar(128) NOT NULL CHECK (name <> '')
);

-- Create Proposal Table
DROP SEQUENCE IF EXISTS seq_proposal_id;
CREATE SEQUENCE seq_proposal_id
	START WITH 1;
	
DROP TABLE IF EXISTS proposal CASCADE;
CREATE TABLE proposal
(
	id			bigint DEFAULT nextval('seq_proposal_id') PRIMARY KEY,
	number		varchar(128) NOT NULL,
	companyId	bigint NOT NULL REFERENCES Company(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Create MajorStageSchedule Table
DROP SEQUENCE IF EXISTS seq_schedule_id;
CREATE SEQUENCE seq_schedule_id
	START WITH 1;
	
DROP TABLE IF EXISTS MajorStageSchedule CASCADE;
CREATE TABLE MajorStageSchedule
(
	id			bigint DEFAULT nextval('seq_schedule_id') PRIMARY KEY,
	stageNumber	integer,
	wellId		bigint NOT NULL REFERENCES Well(id) ON DELETE CASCADE ON UPDATE CASCADE,
	proposalId	bigint NOT NULL REFERENCES Proposal(id) ON DELETE CASCADE ON UPDATE CASCADE
);


-------------------------------------------------------------
-- Engineering
-------------------------------------------------------------

-- Create Major Stage Execution Table
DROP SEQUENCE IF EXISTS seq_majorStageExecution_id;
CREATE SEQUENCE seq_majorStageExecution_id
	START WITH 1;
	
DROP TABLE IF EXISTS MajorStageExecution CASCADE;
CREATE TABLE MajorStageExecution
(
	id		bigint DEFAULT nextval('seq_majorStageExecution_id') PRIMARY KEY,
	majorStageScheduleId	bigint REFERENCES majorStageSchedule(id) ON DELETE CASCADE ON UPDATE CASCADE,
	startTime	TIMESTAMP,
	endTime		TIMESTAMP
);

-- Create Channel Table
DROP TABLE IF EXISTS Channel CASCADE;
CREATE TABLE Channel
(
	majorStageExecutionId 	bigint NOT NULL REFERENCES MajorStageExecution(id) ON DELETE CASCADE ON UPDATE CASCADE,
	id		bigint NOT NULL,
	unitId	bigint NOT NULL,
	description	varchar(128) NOT NULL,
	color	integer NOT NULL,
	PRIMARY KEY( majorStageExecutionId, id )
);

-- Create Snapshot Table
DROP TABLE IF EXISTS Snapshot CASCADE;
CREATE TABLE Snapshot
(
	majorStageExecutionId	bigint NOT NULL REFERENCES MajorStageExecution(id) ON DELETE CASCADE ON UPDATE CASCADE,
	sequence	integer NOT NULL,
	timeElapsedInMS	bigint NOT NULL,
	PRIMARY KEY( majorStageExecutionId, sequence )
);

-- Create SnapshotValue Table
DROP TABLE IF EXISTS SnapshotValue CASCADE;
CREATE TABLE SnapshotValue
(
	majorStageExecutionId	bigint NOT NULL,
	sequence	integer NOT NULL,
	channelId	bigint NOT NULL, 
	channelValue	double precision NOT NULL,
	PRIMARY KEY( majorStageExecutionId, sequence, channelId ),
	FOREIGN KEY( majorStageExecutionId, channelId ) REFERENCES Channel( majorStageExecutionId, id ) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY( majorStageExecutionId, sequence ) REFERENCES Snapshot( majorStageExecutionId, sequence ) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Create Schema Change Table
DROP TABLE IF EXISTS SchemaChange CASCADE;
CREATE TABLE SchemaChange
(
	majorStageExecutionId	bigint NOT NULL,
	snapshotSequence	integer NOT NULL,
	FOREIGN KEY( majorStageExecutionId, snapshotSequence ) REFERENCES Snapshot( majorStageExecutionId, sequence ) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Create Schema Channels Table
-- FOREIGN KEY( majorStageExecutionId, snapshotSequence ) REFERENCES Snapshot( majorStageExecutionId, sequence ) ON DELETE CASCADE ON UPDATE CASCADE
DROP TABLE IF EXISTS SchemaChannels CASCADE;
CREATE TABLE SchemaChannels
(
	majorStageExecutionId	bigint NOT NULL,
	snapshotSequence	integer NOT NULL,
	channelId	bigint NOT NULL,
	PRIMARY KEY( majorStageExecutionId, snapshotSequence, channelId ),
	FOREIGN KEY( majorStageExecutionId, channelId ) REFERENCES Channel( majorStageExecutionId, id ) ON DELETE CASCADE ON UPDATE CASCADE		
);


-- Create Event Table
DROP TABLE IF EXISTS Event CASCADE;
CREATE TABLE Event
(
	majorStageExecutionId	bigint NOT NULL REFERENCES MajorStageExecution(id) ON DELETE CASCADE ON UPDATE CASCADE,
	id	bigint NOT NULL,
	timeElapsedInMS	bigint NOT NULL,
	message		varchar(256) NOT NULL,		
	eventTypeId	bigint NOT NULL DEFAULT 0,
	PRIMARY KEY( majorStageExecutionId, id )
);

