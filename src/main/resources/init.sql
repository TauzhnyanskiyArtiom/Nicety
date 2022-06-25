create table usr
(
    id       bigint generated by default as identity,
    email    varchar(255) not null unique,
    password varchar(255),
    role     varchar(255),
    username varchar(255) not null unique,
    primary key (id)
);
create table schedule
(
    id      bigint generated by default as identity,
    name    varchar(255) not null,
    author_id bigint,
    primary key (id),
    foreign key (author_id) references usr
);
create table event
(
    id          bigint generated by default as identity,
    color       varchar(255),
    day         varchar(255),
    description varchar(255),
    name        varchar(255) not null,
    smiles      varchar(255),
    time        time,
    schedule_id bigint,
    primary key (id),
    foreign key (schedule_id) references schedule
)

