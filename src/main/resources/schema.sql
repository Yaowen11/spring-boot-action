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
)
