CREATE DATABASE  IF NOT EXISTS `darling` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `darling`;
-- MySQL dump 10.13  Distrib 5.7.25, for Linux (x86_64)
--
-- Host: localhost    Database: darling
-- ------------------------------------------------------
-- Server version	5.7.25-0ubuntu0.16.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cities`
--

DROP TABLE IF EXISTS `cities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cities` (
  `id` int(11) NOT NULL,
  `language_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `country_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`language_id`),
  KEY `fk_cities_languages` (`language_id`),
  KEY `fk_cities_countries` (`country_id`),
  CONSTRAINT `fk_cities_countries` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`),
  CONSTRAINT `fk_cities_languages` FOREIGN KEY (`language_id`) REFERENCES `languages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cities`
--

LOCK TABLES `cities` WRITE;
/*!40000 ALTER TABLE `cities` DISABLE KEYS */;
INSERT INTO `cities` (`id`, `language_id`, `name`, `country_id`) VALUES (1,1,'Astana',1),(1,2,'Астана',1),(2,1,'New York',2),(2,2,'Нью-Йорк',2),(3,1,'Moscow',3),(3,2,'Москва',3),(4,1,'Madrid',4),(4,2,'Мадрид',4),(5,1,'Almaty',1),(5,2,'Алматы',1),(6,1,'Washington',2),(6,2,'Вашингтон',2),(7,1,'Novosibirsk',3),(7,2,'Новосибирск',3),(8,1,'Malaga',4),(8,2,'Малага',4),(9,1,'Pavlodar',1),(9,2,'Павлодар',1);
/*!40000 ALTER TABLE `cities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `countries`
--

DROP TABLE IF EXISTS `countries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `countries` (
  `id` int(11) NOT NULL,
  `language_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`,`language_id`),
  KEY `fk_countries_languages` (`language_id`),
  CONSTRAINT `fk_countries_languages` FOREIGN KEY (`language_id`) REFERENCES `languages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `countries`
--

LOCK TABLES `countries` WRITE;
/*!40000 ALTER TABLE `countries` DISABLE KEYS */;
INSERT INTO `countries` (`id`, `language_id`, `name`) VALUES (1,1,'Kazakhstan'),(1,2,'Казахстан'),(2,1,'USA'),(2,2,'США'),(3,1,'Russia'),(3,2,'Россия'),(4,1,'Spain'),(4,2,'Испания');
/*!40000 ALTER TABLE `countries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genders`
--

DROP TABLE IF EXISTS `genders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genders` (
  `id` int(11) NOT NULL,
  `language_id` int(11) NOT NULL,
  `type` varchar(20) NOT NULL,
  PRIMARY KEY (`id`,`language_id`),
  KEY `fk_genders_languages` (`language_id`),
  CONSTRAINT `fk_genders_languages` FOREIGN KEY (`language_id`) REFERENCES `languages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genders`
--

LOCK TABLES `genders` WRITE;
/*!40000 ALTER TABLE `genders` DISABLE KEYS */;
INSERT INTO `genders` (`id`, `language_id`, `type`) VALUES (1,1,'Female'),(1,2,'Женский'),(2,1,'Male'),(2,2,'Мужской');
/*!40000 ALTER TABLE `genders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` mediumblob,
  `url` varchar(100) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_images_users` (`user_id`),
  CONSTRAINT `fk_images_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
INSERT INTO `images` (`id`, `data`, `url`, `user_id`) VALUES (1,NULL,NULL,1),(2,NULL,NULL,2),(3,NULL,NULL,3),(4,NULL,NULL,4),(5,NULL,NULL,5),(6,NULL,NULL,6),(7,NULL,NULL,7),(8,NULL,NULL,8),(9,NULL,NULL,9),(10,NULL,NULL,10);
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `languages`
--

DROP TABLE IF EXISTS `languages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `languages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `locale` char(5) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `languages`
--

LOCK TABLES `languages` WRITE;
/*!40000 ALTER TABLE `languages` DISABLE KEYS */;
INSERT INTO `languages` (`id`, `locale`, `name`) VALUES (1,'en-US','English'),(2,'ru-RU','Русский');
/*!40000 ALTER TABLE `languages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(500) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sender_id` int(11) NOT NULL,
  `receiver_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_messages_statuses` (`status_id`),
  KEY `fk_messages_users_receiver` (`receiver_id`),
  KEY `fk_messages_users_sender` (`sender_id`),
  CONSTRAINT `fk_messages_statuses` FOREIGN KEY (`status_id`) REFERENCES `statuses` (`id`),
  CONSTRAINT `fk_messages_users_receiver` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_messages_users_sender` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` (`id`, `text`, `created_at`, `sender_id`, `receiver_id`, `status_id`) VALUES (49,'Hi!','2019-02-13 10:16:39',2,3,1),(54,'Тест длинных сообщений. Привет! Как дела? Что делаешь? Давай знакомиться.','2019-02-21 05:58:54',4,1,2),(55,'Привет! Хорошо. А у тебя как? Да вот, сижу здесь чтобы помочь тебе тестировать...','2019-02-21 06:00:18',1,4,2),(68,'Yo!','2019-02-26 11:29:42',3,4,2),(71,'Как дела чувак?','2019-03-02 08:13:42',4,7,1),(73,'Hi man!','2019-03-02 08:29:17',4,9,1),(74,'How are you?','2019-03-02 08:30:07',4,9,1),(75,'Hi! You\'re not Aliya, bye','2019-03-02 08:31:17',9,1,1),(76,'Привет!','2019-03-26 09:00:18',4,3,1),(77,'Hi!','2019-03-28 17:48:11',4,3,1);
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `birthday` date NOT NULL,
  `gender_id` int(11) NOT NULL,
  `country_id` int(11) NOT NULL,
  `city_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `image_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_profile_countries` (`country_id`),
  KEY `fk_profile_cities` (`city_id`),
  KEY `fk_profile_genders` (`gender_id`),
  KEY `fk_profile_users` (`user_id`),
  CONSTRAINT `fk_profile_cities` FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`),
  CONSTRAINT `fk_profile_countries` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`),
  CONSTRAINT `fk_profile_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
INSERT INTO `profile` (`id`, `first_name`, `last_name`, `birthday`, `gender_id`, `country_id`, `city_id`, `user_id`, `image_id`) VALUES (1,'Christine','Montgomery','1976-12-04',1,2,2,1,1),(2,'Marcia','Graves','1971-08-11',1,4,4,2,2),(3,'Suzanne','Tucker','1974-03-01',1,2,6,3,3),(4,'John','Rodriguez','1977-11-03',2,4,8,4,4),(5,'Tyler','Robinson','1982-03-02',2,2,2,5,5),(6,'Askhat','Abishev','1988-03-07',2,1,1,6,6),(7,'Saturday','Sunday','1985-06-27',2,1,1,7,7),(8,'Kolya','Vasilev','1996-02-23',1,1,1,8,8),(9,'Mads','Mickelson','1965-03-02',2,1,1,9,9),(10,'asd','asd','1918-03-29',2,1,1,10,10);
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `type` char(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`, `type`) VALUES (1,'user'),(2,'admin');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statuses`
--

DROP TABLE IF EXISTS `statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `statuses` (
  `id` int(11) NOT NULL,
  `type` char(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statuses`
--

LOCK TABLES `statuses` WRITE;
/*!40000 ALTER TABLE `statuses` DISABLE KEYS */;
INSERT INTO `statuses` (`id`, `type`) VALUES (1,'unread'),(2,'read');
/*!40000 ALTER TABLE `statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role_id` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_email_uindex` (`email`),
  KEY `fk_users_roles` (`role_id`),
  CONSTRAINT `fk_users_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `email`, `password`, `role_id`) VALUES (1,'christine.montgomery64@example.com','$2a$10$qCaLPFeHNjljqqIC1.lX.egjmCdzd0Wu9r6v.S3DT9.7.Bc/fX266',1),(2,'marcia.graves54@example.com','$2a$10$6SRlvNOF5yudP7U7f0LUwOJ0RDEXbHhooUrOE.GLYP5t3r.nOzb/.',1),(3,'suzanne.tucker68@example.com','$2a$10$02g1duGqN2AIRpz/s1aDROCAgxSdI5OXTSi.0ovJGz2IBiKnQMQxm',1),(4,'john.rodriguez85@example.com','$2a$10$yib74L6miOOLj8/6QeWDOuBpZc4Uc684f3iA6/uEJqsP/IkPtkfzq',1),(5,'tyler.robinson65@example.com','$2a$10$2IIKCccvOWWkoYLMEtozheBPjHS5cRY2wi54j9dYNPzVn/hPKw0T.',1),(6,'asky.gm@gmail.com','$2a$10$Ug9aYI.E2IpopxhLXbVK7eq9RT3CStTkONxP42vIqtABnVffJMyXm',2),(7,'Saturday@gmail.com','$2a$10$HOn.WOjXyShT6MlpkgZajOoYnJUZ0Tm4WXAqV79TGWfNVVWxh.M2O',1),(8,'vasilev@gmail.com','$2a$10$KoutP03l8D6Tm.eJQ1BNpuLC.ZHODL2BWuE4K/AOzHH9msb1rK/tq',1),(9,'mads@mail.com','$2a$10$KQQE19UNPDzQBu8ae9gS9eVdFZjbIAajUs1wJYp20VIPlZL9WSQwS',1),(10,'qwe@example.com','$2a$10$3Clpw4sXjrWhRNCWNJtINuV81TzNjMNXqI3UpDqcEPUU7S4HaigP2',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-29 11:26:34
