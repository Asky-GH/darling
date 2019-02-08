use darling;

insert into languages(locale, name) values ('en', 'English');
insert into languages(locale, name) values ('ru', 'Русский');

insert into statuses(id, type) values (1, 'unread');
insert into statuses(id, type) values (2, 'read');

insert into roles(id, type) values (1, 'user');
insert into roles(id, type) values (2, 'admin');

insert into genders(id, language_id, type) values (1, 1, 'Female');
insert into genders(id, language_id, type) values (1, 2, 'Женский');
insert into genders(id, language_id, type) values (2, 1, 'Male');
insert into genders(id, language_id, type) values (2, 2, 'Мужской');

insert into countries(id, language_id, name) values (1, 1, 'Kazakhstan');
insert into countries(id, language_id, name) values (1, 2, 'Казахстан');
insert into countries(id, language_id, name) values (2, 1, 'USA');
insert into countries(id, language_id, name) values (2, 2, 'США');
insert into countries(id, language_id, name) values (3, 1, 'Russia');
insert into countries(id, language_id, name) values (3, 2, 'Россия');
insert into countries(id, language_id, name) values (4, 1, 'Spain');
insert into countries(id, language_id, name) values (4, 2, 'Испания');

insert into cities(id, language_id, name, country_id) values (1, 1, 'Astana', 1);
insert into cities(id, language_id, name, country_id) values (1, 2, 'Астана', 1);
insert into cities(id, language_id, name, country_id) values (2, 1, 'New York', 2);
insert into cities(id, language_id, name, country_id) values (2, 2, 'Нью-Йорк', 2);
insert into cities(id, language_id, name, country_id) values (3, 1, 'Moscow', 3);
insert into cities(id, language_id, name, country_id) values (3, 2, 'Москва', 3);
insert into cities(id, language_id, name, country_id) values (4, 1, 'Madrid', 4);
insert into cities(id, language_id, name, country_id) values (4, 2, 'Мадрид', 4);
insert into cities(id, language_id, name, country_id) values (5, 1, 'Almaty', 1);
insert into cities(id, language_id, name, country_id) values (5, 2, 'Алматы', 1);
insert into cities(id, language_id, name, country_id) values (6, 1, 'Washington', 2);
insert into cities(id, language_id, name, country_id) values (6, 2, 'Вашингтон', 2);
insert into cities(id, language_id, name, country_id) values (7, 1, 'Novosibirsk', 3);
insert into cities(id, language_id, name, country_id) values (7, 2, 'Новосибирск', 3);
insert into cities(id, language_id, name, country_id) values (8, 1, 'Malaga', 4);
insert into cities(id, language_id, name, country_id) values (8, 2, 'Малага', 4);

# insert into users(email, password) values ('admin@mail.com', '$2a$10$IWgfQTROmEpgfblZaDLoh.V3jnHhTVuAATr4M43iZnsxHhiwQ/ovm');
# insert into users(email, password) values ('user1@mail.com', '$2a$10$IWgfQTROmEpgfblZaDLoh.V3jnHhTVuAATr4M43iZnsxHhiwQ/ovm');
# insert into users(email, password) values ('user2@mail.com', '$2a$10$IWgfQTROmEpgfblZaDLoh.V3jnHhTVuAATr4M43iZnsxHhiwQ/ovm');
# insert into users(email, password) values ('user3@mail.com', '$2a$10$IWgfQTROmEpgfblZaDLoh.V3jnHhTVuAATr4M43iZnsxHhiwQ/ovm');
# insert into users(email, password) values ('user4@mail.com', '$2a$10$IWgfQTROmEpgfblZaDLoh.V3jnHhTVuAATr4M43iZnsxHhiwQ/ovm');
# insert into users(email, password) values ('user5@mail.com', '$2a$10$IWgfQTROmEpgfblZaDLoh.V3jnHhTVuAATr4M43iZnsxHhiwQ/ovm');
# insert into users(email, password) values ('user6@mail.com', '$2a$10$IWgfQTROmEpgfblZaDLoh.V3jnHhTVuAATr4M43iZnsxHhiwQ/ovm');
# insert into users(email, password) values ('user7@mail.com', '$2a$10$IWgfQTROmEpgfblZaDLoh.V3jnHhTVuAATr4M43iZnsxHhiwQ/ovm');
# insert into users(email, password) values ('user8@mail.com', '$2a$10$IWgfQTROmEpgfblZaDLoh.V3jnHhTVuAATr4M43iZnsxHhiwQ/ovm');
#
# insert into profile(first_name, last_name, birthday, gender_id, country_id, city_id, user_id, image_id)
#             values ('Daniyar', 'Zhumabaev', '1988-01-29', 2, 1, 1, 2, 2);
# insert into profile(first_name, last_name, birthday, gender_id, country_id, city_id, user_id, image_id)
#             values ('Aleksandr', 'Semenov', '1989-11-09', 2, 1, 1, 3, 3);
# insert into profile(first_name, last_name, birthday, gender_id, country_id, city_id, user_id, image_id)
#             values ('John', 'Smith', '1990-10-20', 2, 2, 2, 4, 4);
# insert into profile(first_name, last_name, birthday, gender_id, country_id, city_id, user_id, image_id)
#             values ('Zurab', 'Saurov', '1987-05-12', 2, 1, 1, 5, 5);
# insert into profile(first_name, last_name, birthday, gender_id, country_id, city_id, user_id, image_id)
#             values ('Aisulu', 'Zhumabaeva', '1988-01-29', 1, 1, 1, 6, 6);
# insert into profile(first_name, last_name, birthday, gender_id, country_id, city_id, user_id, image_id)
#             values ('Elena', 'Semenova', '1989-11-09', 1, 1, 1, 7, 7);
# insert into profile(first_name, last_name, birthday, gender_id, country_id, city_id, user_id, image_id)
#             values ('Kate', 'Smith', '1990-10-20', 1, 2, 2, 8, 8);
# insert into profile(first_name, last_name, birthday, gender_id, country_id, city_id, user_id, image_id)
#             values ('Firuza', 'Saurova', '1987-05-12', 1, 1, 1, 9, 9);
