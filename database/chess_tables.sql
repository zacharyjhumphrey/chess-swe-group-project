--Drop all tables
DROP TABLE has_friends;
DROP TABLE player;
DROP TABLE game;


--Create tables
CREATE TABLE player
	(player_id	INTEGER,
	 username	VARCHAR		(25),
	 password	VARBINARY	(16),
	 no_wins	INTEGER,
	 no_loses	INTEGER,
	 no_ties	INTEGER
	);
		
CREATE TABLE has_friends
	(player_id	INTEGER	(25),
	 friend_id	INTEGER	(25)
	);
		 
CREATE TABLE game
	(game_id	BINARY(12),
	 winner_id	INTEGER,
	 loser_id	INTEGER
	);


--Create primary key contraints
alter table player
 add constraint player_id_pk primary key(player_id);
 
alter table has_friends
 add constraint has_friends_playe_id_friend_id_pk primary key(player_id, friend_id);
 
alter table game
 add constraint game_id_pk primary key(game_id);
  

--Create foreign key constraints 
alter table has_friends
 add constraint has_friends_player_id_fk foreign key(player_id)
 references player(player_id);
 
alter table has_friends
 add constraint has_friends_friend_id_fk foreign key(friend_id)
 references player(player_id);
 
alter table game
 add constraint game_winner_id_fk foreign key(winner_id)
 references player(player_id);
 
alter table game
 add constraint game_winner_id_fk foreign key(loser_id)
 references player(player_id);
 