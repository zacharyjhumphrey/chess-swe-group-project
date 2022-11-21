--Drop all tables
DROP TABLE game;
DROP TABLE player;

--Create tables
CREATE TABLE player (
	username VARCHAR (25),
	password VARBINARY (16),
	no_wins INTEGER,
	no_loses INTEGER,
	no_ties INTEGER
);
CREATE TABLE game (
	game_id VARCHAR(36),
	winner_username VARCHAR (25),
	loser_username VARCHAR (25)
);

--Create primary key contraints
alter table player
add constraint username_pk primary key(username);
alter table game
add constraint game_id_pk primary key(game_id);

--Create foreign key constraints
alter table game
add constraint game_winner_id_fk foreign key(winner_username) references player(username);
alter table game
add constraint game_loser_id_fk foreign key(loser_username) references player(username);