use darling;

insert into locales(type) values ('en');
insert into locales(type) values ('ru');

insert into statuses(id, type) VALUES (1, 'unread');
insert into statuses(id, type) VALUES (2, 'read');

insert into roles(id, type) VALUES (1, 'user');
insert into roles(id, type) VALUES (2, 'admin');

insert into cities(id, locale_id, name) VALUES (1, 1, 'Astana');
insert into cities(id, locale_id, name) VALUES (1, 2, 'Астана');

insert into countries(id, locale_id, name) VALUES (1, 1, 'Kazakhstan');
insert into countries(id, locale_id, name) VALUES (1, 2, 'Казахстан');

insert into genders(id, locale_id, type) VALUES (1, 1, 'female');
insert into genders(id, locale_id, type) VALUES (1, 2, 'женский');
insert into genders(id, locale_id, type) VALUES (2, 1, 'male');
insert into genders(id, locale_id, type) VALUES (2, 2, 'мужской');
