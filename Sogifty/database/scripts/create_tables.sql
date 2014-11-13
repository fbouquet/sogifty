drop table if exists app_user cascade;
drop table if exists friend cascade;
drop table if exists gift cascade;
drop table if exists tag cascade;
drop table if exists enjoy;
drop table if exists correspond;
drop table if exists match;

create table app_user (
    id serial primary key not null,
    name text not null,
    email text not null,
    pwd text not null
);

create table friend (
    id serial primary key not null,
    name text not null,
    birthdate date not null,
    app_user integer references app_user(id)
);

create table gift (
    id serial primary key not null,
    characteristic json not null
);

create table tag (
    id serial primary key not null,
    label text not null
);

create table enjoy (
       id serial primary key not null,
       friend integer references friend(id),
       tag integer references tag(id)
);

create table correspond (
       id serial primary key not null,
       friend integer references friend(id),
       gift integer references gift(id)
);

create table match (
       id serial primary key not null,
       tag integer references tag(id),
       gift integer references gift(id)
);
