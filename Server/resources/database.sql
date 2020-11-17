DROP DATABASE IF EXISTS piano;
CREATE DATABASE piano;
USE piano;


-- creation of the tales
DROP TABLE IF EXISTS users;
CREATE TABLE users(
                      code        varchar(255) NOT NULL ,
                      nickname    VARCHAR(255),
                      email       VARCHAR(255),
                      password    VARCHAR(255),
                      PRIMARY KEY (code)
);

DROP TABLE IF EXISTS user_user;
CREATE TABLE user_user(
                          code_u1    varchar(255) NOT NULL,
                          code_u2    varchar(255) NOT NULL,
                          PRIMARY KEY (code_u1, code_u2),
                          FOREIGN KEY (code_u1) REFERENCES users(code),
                          FOREIGN KEY (code_u2) REFERENCES users(code)
);

DROP TABLE IF EXISTS songs;
CREATE TABLE songs(
                      song_id    INT NOT NULL AUTO_INCREMENT,
                      title      VARCHAR(255),
                      file       TEXT,
                      min         FLOAT,
                      user_code  varchar(255),
                      is_public   BOOLEAN,
                      times_played INT,
                      PRIMARY KEY (song_id),
                      FOREIGN KEY (user_code) REFERENCES users(code)
);

DROP TABLE IF EXISTS stats;
CREATE TABLE stats(
                      id    INT AUTO_INCREMENT,
                      date    DATE,
                      num_songs INT,
                      num_minutes FLOAT,
                      PRIMARY KEY (id)
);





-- creation of the system user that is not accessible to the client and the 3
-- default songs that are stored in the server, also we have a user called
-- user with the password user so it would be easier to login to the client

INSERT INTO users(code , nickname, email, password) VALUES ('','', '', '');
INSERT INTO users(code , nickname, email, password) VALUES ('u0','user', 'user@gmail.com', 'user');
INSERT INTO songs (title, file, min, user_code, is_public, times_played) VALUES ('Für Elise', '[{timeStamp=0, note="E5", duration=79603200},{timeStamp=432593900, note="D#5", duration=206869600},{timeStamp=927946700, note="E5", duration=143841300},{timeStamp=1360316800, note="D#5", duration=255122600},{timeStamp=1935934300, note="E5", duration=479475900},{timeStamp=2848336500, note="B4", duration=147343000},{timeStamp=3444045600, note="D5", duration=287459100},{timeStamp=4004054200, note="C5", duration=271430600},{timeStamp=4676210200, note="A4", duration=1475294900},{timeStamp=6695931600, note="C4", duration=128407200},{timeStamp=7192115600, note="E4", duration=415691600},{timeStamp=7944128400, note="A4", duration=287391800},{timeStamp=8584083000, note="B4", duration=2147409300},{timeStamp=11372638000, note="E4", duration=334742300},{timeStamp=12144045200, note="G#4", duration=399452100},{timeStamp=12864200600, note="B4", duration=367343100},{timeStamp=13552441400, note="C5", duration=1938983000},{timeStamp=15923941600, note="E4", duration=303800900},{timeStamp=16915858700, note="E5", duration=159706800},{timeStamp=17412418800, note="D#5", duration=255704200},{timeStamp=17992207300, note="E5", duration=191389700},{timeStamp=18519987300, note="D#5", duration=223435800},{timeStamp=19112247300, note="E5", duration=447230700},{timeStamp=21291970700, note="B4", duration=271487400},{timeStamp=21996128700, note="D5", duration=319313900},{timeStamp=22555965500, note="C5", duration=239500900},{timeStamp=23227941000, note="A4", duration=1491519200},{timeStamp=25423973600, note="C4", duration=255406700},{timeStamp=26224039000, note="E4", duration=319417900},{timeStamp=27140664200, note="A4", duration=270816900},{timeStamp=27732061300, note="B4", duration=2707433700},{timeStamp=31328166900, note="E4", duration=527243700},{timeStamp=32624404000, note="C5", duration=643123100},{timeStamp=33443946000, note="B4", duration=463566600},{timeStamp=34276066300, note="A4", duration=3043370500}]', 0.26, '', true, 0);
INSERT INTO songs (title, file, min, user_code, is_public, times_played) VALUES ('Happy Birthday', '[{timeStamp=0, note="D4", duration=244551500},{timeStamp=479706200, note="D4", duration=368974100},{timeStamp=1034621400, note="E4", duration=988485300},{timeStamp=2003154000, note="D4", duration=1124672000},{timeStamp=3113080100, note="G4", duration=1132971400},{timeStamp=4206667700, note="F#4", duration=1188240700},{timeStamp=5670080500, note="D4", duration=329579700},{timeStamp=6219614500, note="D4", duration=484590300},{timeStamp=6683826900, note="E4", duration=1104001000},{timeStamp=7733172700, note="D4", duration=1118665400},{timeStamp=8806951200, note="A4", duration=1064423900},{timeStamp=9786021500, note="G4", duration=1693960700},{timeStamp=11484491200, note="D4", duration=85113100},{timeStamp=11734415300, note="D4", duration=425511300},{timeStamp=12517554300, note="D5", duration=766634900},{timeStamp=13597874800, note="B4", duration=1159197000},{timeStamp=14676893700, note="G4", duration=1818701400},{timeStamp=16390338400, note="F#4", duration=984174000},{timeStamp=17289771200, note="E4", duration=1728781500},{timeStamp=19337515400, note="D5", duration=158527900},{timeStamp=19753015200, note="D5", duration=271171000},{timeStamp=20317650100, note="B4", duration=1568589300},{timeStamp=21811237200, note="G4", duration=1074491000},{timeStamp=22825078000, note="A4", duration=1104860500},{timeStamp=23810198200, note="G4", duration=2279540300}]', 0.20, '', true, 0);
INSERT INTO songs (title, file, min, user_code, is_public, times_played) VALUES ('Twinkle, Twinkle, Little Star', '[{timeStamp=0, note="D4", duration=480839100},{timeStamp=635935200, note="D4", duration=517724700},{timeStamp=1693660400, note="A4", duration=514945300},{timeStamp=2398423600, note="A4", duration=494298700},{timeStamp=3078293200, note="B4", duration=523710400},{timeStamp=3752268000, note="B4", duration=424338700},{timeStamp=4406450600, note="A4", duration=1049484100},{timeStamp=5930381100, note="G4", duration=509134100},{timeStamp=6685483500, note="G4", duration=453910500},{timeStamp=7739191100, note="F#4", duration=569649500},{timeStamp=8428895100, note="F#4", duration=473975700},{timeStamp=9163315100, note="E4", duration=474335700},{timeStamp=9897611900, note="E4", duration=640074500},{timeStamp=10631631500, note="D4", duration=1488878600},{timeStamp=14264613500, note="A4", duration=578659300},{timeStamp=15138361600, note="A4", duration=858881900},{timeStamp=16127344100, note="G4", duration=569474000},{timeStamp=16921680600, note="G4", duration=630468000},{timeStamp=18045922900, note="F#4", duration=599199700},{timeStamp=18870287200, note="F#4", duration=519628700},{timeStamp=19594673600, note="E4", duration=1588220000},{timeStamp=21514248100, note="A4", duration=578041200},{timeStamp=22282616100, note="A4", duration=419514200},{timeStamp=23046520700, note="G4", duration=579709200},{timeStamp=23811369700, note="G4", duration=509192600},{timeStamp=24605725600, note="F#4", duration=628922400},{timeStamp=25415080700, note="F#4", duration=554437800},{timeStamp=26209255100, note="E4", duration=1773372500},{timeStamp=27972601600, note="D4", duration=566049600},{timeStamp=28707507700, note="D4", duration=568898200},{timeStamp=29636535300, note="A4", duration=539229600},{timeStamp=30430661900, note="A4", duration=519741000},{timeStamp=31275192300, note="B4", duration=479299700},{timeStamp=31970045700, note="B4", duration=598836200},{timeStamp=32713835600, note="A4", duration=1873730700},{timeStamp=34876905100, note="G4", duration=634798300},{timeStamp=35731404200, note="G4", duration=1083566800},{timeStamp=37309737600, note="F#4", duration=709404300},{timeStamp=38199485800, note="F#4", duration=1108315800},{timeStamp=39449061700, note="E4", duration=568267800},{timeStamp=40232108300, note="E4", duration=984167000}]', 0.30, '', true, 0);


-- these are some statistics already generated so you can see the graphs in
-- the server graph view
INSERT INTO stats(date, num_songs, num_minutes) VALUES ('2020-04-19', 23, 18.54);
INSERT INTO stats(date, num_songs, num_minutes) VALUES ('2020-04-20', 50, 27.49);
INSERT INTO stats(date, num_songs, num_minutes) VALUES ('2020-04-21', 3, 1.65);
INSERT INTO stats(date, num_songs, num_minutes) VALUES ('2020-04-22', 15, 9.87);
INSERT INTO stats(date, num_songs, num_minutes) VALUES ('2020-04-23', 42, 20.15);
INSERT INTO stats(date, num_songs, num_minutes) VALUES ('2020-04-20', 50, 27.49);

SELECT * FROM users;
SELECT * FROM songs;


-- creation of the user to be able to access and edit the database
flush privileges ;
drop user if exists 'admin'@'localhost';
create user 'admin'@'localhost' identified by 'admin';
grant all privileges on piano.songs to 'admin'@'localhost';
grant all privileges on piano.users to 'admin'@'localhost';
grant all privileges on piano.user_user to 'admin'@'localhost';
grant all privileges on piano.stats to 'admin'@'localhost';

