drop database if exists spotitube;
create database spotitube;
use spotitube;


/* Table: users */
create table users (
    id int not null auto_increment,
    name varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null,
    token varchar(255) null,

    unique(username),
    primary key(id)
);

/* Table: Playlist */
create table playlists (
    id int not null auto_increment,
    name varchar(255) not null,
    owner varchar(255) not null,

    primary key(id),
    FOREIGN KEY (owner) REFERENCES users (username) ON DELETE CASCADE ON UPDATE CASCADE
);

/* Table: Track */
create table tracks (
    id int not null auto_increment,
    title varchar(255) not null,
    performer varchar(255) not null,
    duration int not null,
    album varchar(255) null,
    playcount int null,
    publicationDate varchar(255) null,
    description varchar(500) null,
    offlineAvailable boolean not null,

    primary key (id)
);

/* Table: Playlists_tracks */
create table playlists_tracks (
    id int not null auto_increment,
    playlistId int not null,
    trackId int not null,
    offlineAvailable boolean not null,

    primary key (id),
    FOREIGN KEY (trackId) REFERENCES tracks (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (playlistId) REFERENCES playlists (id) ON DELETE CASCADE ON UPDATE CASCADE
);
