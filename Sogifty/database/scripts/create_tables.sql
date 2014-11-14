drop table if exists app_user cascade;
drop table if exists friend cascade;
drop table if exists gift cascade;
drop table if exists tag cascade;
drop table if exists enjoy;
drop table if exists correspond;
drop table if exists match;

create table app_user (
    id serial primary key not null,
    email text not null unique,
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
    friend_id integer references friend(id),
    tag_id integer references tag(id),
    constraint "enjoy_PK" primary key(friend_id, tag_id)
);

create table correspond (
     friend_id integer references friend(id),
     gift_id integer references gift(id),
     constraint "correspond_PK" primary key(friend_id, gift_id)
);

create table match (
     tag_id integer references tag(id),
     gift_id integer references gift(id),
     constraint "match_PK" primary key(tag_id, gift_id)
);
