insert into app_user (email, pwd)
values
       ('thomas@mail.com', 'pass'),
       ('valentin@mail.com', 'passValentin'),
       ('florent@mail.com', 'passFlorent');

insert into friend (name, birthdate, app_user_id)
values
       ('ami', '1991-11-17', 1),
       ('bff', '1999-09-30', 1),
       ('tata', '1997-10-15', 2),
       ('titi', '1993-08-12', 2);


