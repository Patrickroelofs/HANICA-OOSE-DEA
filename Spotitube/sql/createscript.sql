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

