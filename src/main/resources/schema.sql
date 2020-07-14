create table profile
(
    id int auto_increment
        primary key,
    first_name varchar(255) null,
    last_name varchar(255) null,
    username varchar(255) null,
    password varchar(64) null
);

create table user
(
    id int auto_increment primary key,
    username varchar(255),
    password varchar(64)
);

create table member
(
    id int auto_increment primary key,
    username varchar(255),
    password varchar(64),
    enable char(5)
);

create table member_authority
(
    id int auto_increment primary key,
    username varchar(255),
    authority varchar(255)
);

create table admin
(
    id int auto_increment primary key,
    username varchar(255),
    password varchar(255),
    type int
)