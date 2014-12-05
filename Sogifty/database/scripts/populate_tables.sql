insert into app_user (email, pwd)
values
       ('thomas@mail.com', 'pass'),
       ('valentin@mail.com', 'passValentin'),
       ('florent@mail.com', 'passFlorent');

insert into friend (name, first_name, birthdate, app_user_id)
values
       ('Dupont', 'Pierre', '1991-11-17', 1),
       ('Dupont', 'Paul', '1999-09-30', 1),
       ('Dupont', 'Jacques', '1997-10-15', 2),
       ('Orange', 'Casimir', '1993-08-12', 2);


