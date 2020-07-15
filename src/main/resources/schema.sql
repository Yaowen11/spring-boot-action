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

create table jdbc_users
(
    id int auto_increment primary key,
    username varchar(50),
    password varchar(255),
    enabled varchar(10)
);

create table jdbc_user_authorities
(
    id int auto_increment primary key,
    username varchar(50),
    authority varchar(255)
);

create table jdbc_groups
(
    id int auto_increment primary key,
    group_name varchar(50),
    authority varchar(255)
);

create table jdbc_user_groups
(
    id int auto_increment primary key key,
    group_id int,
    username varchar(50)
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