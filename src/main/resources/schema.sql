//DROP TABLE IF EXISTS FILMS, FRIENDS, GENRE_FILMS, GENRES, LIKE_FILMS, MPA,USERS CASCADE;
CREATE TABLE IF NOT EXISTS USERS (
                                     ID_USER INT PRIMARY KEY AUTO_INCREMENT,
                                     NAME VARCHAR(255) NOT NULL,
                                     EMAIL VARCHAR(255) NOT NULL,
                                     LOGIN VARCHAR(255) NOT NULL,
                                     BIRTHDAY DATE
);

CREATE TABLE IF NOT EXISTS FILMS
(
    ID_FILM      INT PRIMARY KEY AUTO_INCREMENT,
    NAME         varchar(50)  NOT NULL,
    DESCRIPTION  varchar(200) NOT NULL,
    RELEASE_DATA date         NOT NULL,
    DURATION     integer      NOT NULL,
    MPA_ID       integer      NOT NULL
);
CREATE TABLE IF NOT EXISTS GENRES
(
    ID_GENRE   INT PRIMARY KEY AUTO_INCREMENT,
    NAME varchar(100) NOT NULL
);
CREATE TABLE IF NOT EXISTS GENRE_FILMS
(
    ID_GENRE INT,
    ID_FILM INT,
    PRIMARY KEY (ID_GENRE,ID_FILM),
    FOREIGN KEY (ID_GENRE) REFERENCES GENRES(ID_GENRE) ON DELETE CASCADE,
    FOREIGN KEY (ID_FILM) REFERENCES FILMS(ID_FILM) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS MPA
(
    ID_MPA INT PRIMARY KEY AUTO_INCREMENT,
    NAME varchar(100) NOT NULL
);
CREATE TABLE IF NOT EXISTS FRIENDS
(
    FIRST_FRIEND  INT NOT NULL,
    SECOND_FRIEND INT NOT NULL,
    PRIMARY KEY (FIRST_FRIEND,SECOND_FRIEND),
    FOREIGN KEY (FIRST_FRIEND) REFERENCES USERS(ID_USER) ON DELETE CASCADE,
    FOREIGN KEY (SECOND_FRIEND) REFERENCES USERS(ID_USER) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS LIKE_FILMS
(
    ID_LIKE  INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    ID_FILM int NOT NULL,
    ID_USER int NOT NULL,
    FOREIGN KEY (ID_FILM) REFERENCES FILMS (ID_FILM) ON DELETE CASCADE,
    FOREIGN KEY (ID_USER) REFERENCES USERS (ID_USER) ON DELETE CASCADE
);