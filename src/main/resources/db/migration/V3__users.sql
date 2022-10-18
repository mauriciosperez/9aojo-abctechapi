CREATE TABLE users
(
    id        bigint auto_increment
        primary key,
    username  varchar(20)  not null,
    email     varchar(50)  not null,
    password  varchar(120) not null
)