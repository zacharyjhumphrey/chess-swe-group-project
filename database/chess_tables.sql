--Drop all tables
DROP TABLE player;

--Create tables
CREATE TABLE player (
	username VARCHAR (25),
	password VARBINARY (16),
	no_wins  INTEGER DEFAULT 0,
	no_losses INTEGER DEFAULT 0,
	no_ties  INTEGER DEFAULT 0
);

--Create primary key contraints
alter table player
add constraint username_pk primary key(username);