create schema if not exists darling collate utf8_general_ci;

use darling;

create table if not exists languages
(
  id int auto_increment primary key,
  locale char(5) not null,
  name nvarchar(50) not null
);

create table if not exists statuses
(
  id int not null primary key,
  type char(6) not null
);

create table if not exists roles
(
  id int not null primary key,
  type char(5) not null
);

create table if not exists countries
(
  id int not null,
  language_id int not null,
  name nvarchar(50) not null,
  constraint fk_countries_languages foreign key (language_id) references languages (id),
  primary key (id, language_id)
);

create table if not exists cities
(
  id int not null,
  language_id int not null,
  name nvarchar(50) not null,
  country_id int not null,
  constraint fk_cities_languages foreign key (language_id) references languages (id),
  constraint fk_cities_countries foreign key (country_id) references countries (id),
  primary key (id, language_id)
);

create table if not exists genders
(
  id int not null,
  language_id int not null,
  type nvarchar(20) not null,
  constraint fk_genders_languages foreign key (language_id) references languages (id),
  primary key (id, language_id)
);

create table if not exists users
(
  id int auto_increment primary key,
  email varchar(100) not null,
  password varchar(100) not null,
  role_id int default 1 not null,
  constraint users_email_uindex unique (email),
  constraint fk_users_roles foreign key (role_id) references roles (id)
);

create table if not exists images
(
  id int auto_increment primary key,
  data mediumblob,
  url varchar(100),
  user_id int not null,
  constraint fk_images_users foreign key (user_id) references users (id)
);

create table if not exists profile
(
  id int auto_increment primary key,
  first_name nvarchar(50) not null,
  last_name nvarchar(50) not null,
  birthday date not null,
  gender_id int not null,
  country_id int not null,
  city_id int not null,
  user_id int not null,
  image_id int not null,
  constraint fk_profile_countries foreign key (country_id) references countries (id),
  constraint fk_profile_cities foreign key (city_id) references cities (id),
  constraint fk_profile_genders foreign key (gender_id) references genders (id),
  constraint fk_profile_users foreign key (user_id) references users (id)
);

create table if not exists messages
(
  id int auto_increment primary key,
  text nvarchar(500) not null,
  created_at timestamp default CURRENT_TIMESTAMP not null,
  sender_id int not null,
  receiver_id int not null,
  status_id int default 1 not null,
  constraint fk_messages_users_receiver foreign key (receiver_id) references users (id),
  constraint fk_messages_users_sender foreign key (sender_id) references users (id),
  constraint fk_messages_statuses foreign key (status_id) references statuses (id)
);
