Drop table if exists notes;

use authdb;

CREATE TABLE notes
(
	userid varchar(32) not null,
	content MEDIUMTEXT not null,
    title varchar(100),
    category varchar(32) not null,
    date_time DATETIME not null,
	primary key (userid,date_time),
	FOREIGN KEY (userid) REFERENCES users(userid)
)