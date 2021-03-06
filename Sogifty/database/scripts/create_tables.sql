drop table if exists app_user cascade;
drop table if exists friend cascade;
drop table if exists gift cascade;
drop table if exists tag cascade;
drop table if exists enjoy;
drop table if exists match;
drop table if exists preferences;

create table app_user (
    id      serial primary key  not null,
    email   text                not null unique,
    pwd     text                not null
);

create table friend (
    id          serial primary key  not null,
    name        text                not null,
    first_name  text                not null,
    avatar_path text                null,
    birthdate   date                not null,
    app_user_id integer references app_user(id)
);

create table tag (
    id      serial primary key  not null,
    label   text                not null
);

create table enjoy (
    friend_id   integer references friend(id),
    tag_id      integer references tag(id),
    constraint "enjoy_PK" primary key(friend_id, tag_id)
);

create table gift (
    id              serial primary key  not null,
    name            text                not null,
    description     text,
    price           text,
    picture_url     text,
    url             text                not null,
    last_update     date                not null,
    creation        date                not null,
    website         text                not null
);

create table match (
     tag_id     integer references tag(id),
     gift_id    integer references gift(id),
     constraint "match_PK" primary key(tag_id, gift_id)
);

create table preferences (
    id          serial primary key  not null,
    nb_tags     integer             not null,
    nb_gifts    integer             not null
);

insert into preferences(nb_tags, nb_gifts) values
	(10, 10);