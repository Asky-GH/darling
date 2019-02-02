use darling;

insert into locales(type) values ('en');
insert into locales(type) values ('ru');

insert into statuses(id, type) values (1, 'unread');
insert into statuses(id, type) values (2, 'read');

insert into roles(id, type) values (1, 'user');
insert into roles(id, type) values (2, 'admin');

insert into cities(id, locale_id, name) values (1, 1, 'Astana');
insert into cities(id, locale_id, name) values (1, 2, 'Астана');
insert into cities(id, locale_id, name) values (2, 1, 'New York');
insert into cities(id, locale_id, name) values (2, 2, 'Нью-Йорк');

insert into countries(id, locale_id, name) values (1, 1, 'Kazakhstan');
insert into countries(id, locale_id, name) values (1, 2, 'Казахстан');
insert into countries(id, locale_id, name) values (2, 1, 'USA');
insert into countries(id, locale_id, name) values (2, 2, 'США');

insert into genders(id, locale_id, type) values (1, 1, 'female');
insert into genders(id, locale_id, type) values (1, 2, 'женский');
insert into genders(id, locale_id, type) values (2, 1, 'male');
insert into genders(id, locale_id, type) values (2, 2, 'мужской');

insert into users(email, password) values ('admin@mail.com', '$2a$10$IWgfQTROmEpgfblZaDLoh.V3jnHhTVuAATr4M43iZnsxHhiwQ/ovm');
insert into users(email, password) values ('user1@mail.com', '$2a$10$IWgfQTROmEpgfblZaDLoh.V3jnHhTVuAATr4M43iZnsxHhiwQ/ovm');
insert into users(email, password) values ('user2@mail.com', '$2a$10$IWgfQTROmEpgfblZaDLoh.V3jnHhTVuAATr4M43iZnsxHhiwQ/ovm');
insert into users(email, password) values ('user3@mail.com', '$2a$10$IWgfQTROmEpgfblZaDLoh.V3jnHhTVuAATr4M43iZnsxHhiwQ/ovm');
insert into users(email, password) values ('user4@mail.com', '$2a$10$IWgfQTROmEpgfblZaDLoh.V3jnHhTVuAATr4M43iZnsxHhiwQ/ovm');
insert into users(email, password) values ('user5@mail.com', '$2a$10$IWgfQTROmEpgfblZaDLoh.V3jnHhTVuAATr4M43iZnsxHhiwQ/ovm');
insert into users(email, password) values ('user6@mail.com', '$2a$10$IWgfQTROmEpgfblZaDLoh.V3jnHhTVuAATr4M43iZnsxHhiwQ/ovm');
insert into users(email, password) values ('user7@mail.com', '$2a$10$IWgfQTROmEpgfblZaDLoh.V3jnHhTVuAATr4M43iZnsxHhiwQ/ovm');
insert into users(email, password) values ('user8@mail.com', '$2a$10$IWgfQTROmEpgfblZaDLoh.V3jnHhTVuAATr4M43iZnsxHhiwQ/ovm');

insert into profile(first_name, last_name, birthday, gender_id, country_id, city_id, user_id, image_id)
            values ('Daniyar', 'Zhumabaev', '1988-01-29', 2, 1, 1, 2, 2);
insert into profile(first_name, last_name, birthday, gender_id, country_id, city_id, user_id, image_id)
            values ('Aleksandr', 'Semenov', '1989-11-09', 2, 1, 1, 3, 3);
insert into profile(first_name, last_name, birthday, gender_id, country_id, city_id, user_id, image_id)
            values ('John', 'Smith', '1990-10-20', 2, 2, 2, 4, 4);
insert into profile(first_name, last_name, birthday, gender_id, country_id, city_id, user_id, image_id)
            values ('Zurab', 'Saurov', '1987-05-12', 2, 1, 1, 5, 5);
insert into profile(first_name, last_name, birthday, gender_id, country_id, city_id, user_id, image_id)
            values ('Aisulu', 'Zhumabaeva', '1988-01-29', 1, 1, 1, 6, 6);
insert into profile(first_name, last_name, birthday, gender_id, country_id, city_id, user_id, image_id)
            values ('Elena', 'Semenova', '1989-11-09', 1, 1, 1, 7, 7);
insert into profile(first_name, last_name, birthday, gender_id, country_id, city_id, user_id, image_id)
            values ('Kate', 'Smith', '1990-10-20', 1, 2, 2, 8, 8);
insert into profile(first_name, last_name, birthday, gender_id, country_id, city_id, user_id, image_id)
            values ('Firuza', 'Saurova', '1987-05-12', 1, 1, 1, 9, 9);
