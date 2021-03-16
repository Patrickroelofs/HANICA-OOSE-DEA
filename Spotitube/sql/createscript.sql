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

    primary key(id)
);

/* Table: Performer */
create table performer (
    id int not null auto_increment,
    name varchar(255) not null,

    primary key(id),
    unique(name)
);

/* Table: Track */
create table track (
    id int not null auto_increment,
    title varchar(255) not null,
    performer int not null,
    duration int not null,
    album varchar(255) null,
    playcount int null,
    publicationDate date null,
    description varchar(500) null,
    offlineAvailable boolean not null,

    primary key (id),
    foreign key (id) references performer (id)
)

