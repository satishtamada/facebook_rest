-- MySQL dump 10.13  Distrib 5.5.44, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: facebook
-- ------------------------------------------------------
-- Server version	5.5.44-0ubuntu0.14.04.1

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
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user1_id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  `comment` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user1_id` (`user1_id`,`post_id`),
  KEY `id` (`id`),
  KEY `user2_id` (`post_id`),
  KEY `user1_id_2` (`user1_id`),
  KEY `post_id` (`post_id`),
  KEY `user1_id_3` (`user1_id`),
  KEY `user1_id_4` (`user1_id`),
  KEY `post_id_2` (`post_id`),
  KEY `user1_id_5` (`user1_id`),
  KEY `post_id_3` (`post_id`),
  CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`user1_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (3,58,357,'nice post','2015-09-01 17:17:57'),(5,58,355,'nice post','2015-09-01 17:18:17'),(6,57,355,'nice post','2015-09-01 17:18:27'),(8,61,355,'nice post','2015-09-01 17:18:54'),(9,61,357,'nice haha post','2015-09-01 17:19:37'),(10,104,341,'nice karthik','2015-09-02 10:03:54'),(11,101,341,'looking good  karthik','2015-09-02 10:04:30'),(12,57,341,'thanks to all','2015-09-02 10:04:57');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend_requests`
--

DROP TABLE IF EXISTS `friend_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friend_requests` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user1_id` int(11) NOT NULL,
  `user2_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user1_id` (`user1_id`,`user2_id`),
  KEY `user2_id` (`user2_id`),
  CONSTRAINT `friend_requests_ibfk_1` FOREIGN KEY (`user1_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `friend_requests_ibfk_2` FOREIGN KEY (`user2_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend_requests`
--

LOCK TABLES `friend_requests` WRITE;
/*!40000 ALTER TABLE `friend_requests` DISABLE KEYS */;
/*!40000 ALTER TABLE `friend_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friends` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user1_id` int(11) NOT NULL,
  `user2_id` int(11) NOT NULL,
  `status` int(1) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user1_id` (`user1_id`,`user2_id`),
  KEY `user2_id` (`user2_id`),
  CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`user1_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `friends_ibfk_2` FOREIGN KEY (`user2_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
INSERT INTO `friends` VALUES (3,58,61,0,'2015-07-12 07:50:17'),(5,60,58,1,'2015-07-12 15:36:05'),(8,57,58,NULL,'2015-07-12 20:12:25'),(9,57,61,0,'2015-07-13 06:58:14'),(10,57,60,0,'2015-07-13 06:59:07'),(11,58,104,0,'2015-08-30 16:40:53');
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `likes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  `like_status` int(11) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user1_id` (`user_id`,`post_id`),
  KEY `user2_id` (`post_id`),
  CONSTRAINT `likes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `likes_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notifications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user1_id` int(11) NOT NULL,
  `user2_id` int(11) NOT NULL,
  `type` int(1) NOT NULL,
  `status` int(1) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user1_id` (`user1_id`,`user2_id`),
  KEY `user2_id` (`user2_id`),
  CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`user1_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `notifications_ibfk_2` FOREIGN KEY (`user2_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `posts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `text` text NOT NULL,
  `image` text,
  `image_width` int(4) DEFAULT NULL,
  `image_height` int(4) DEFAULT NULL,
  `likes` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=362 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (274,54,'iam ravi ','20150711081944.png',800,600,NULL,'2015-07-11 14:49:44'),(278,54,'With my friends','20150711111129.png',800,600,NULL,'2015-07-11 17:41:29'),(279,54,'With my friends','',0,0,NULL,'2015-07-11 17:42:01'),(283,32,'this is my new post','',0,0,NULL,'2015-07-13 07:38:32'),(284,62,'hello','',0,0,NULL,'2015-08-19 06:41:32'),(285,62,'hello','',0,0,NULL,'2015-08-19 06:41:38'),(286,65,'hello','',0,0,NULL,'2015-08-19 06:41:43'),(287,65,'hello','20150819121159.png',128,128,NULL,'2015-08-19 06:41:59'),(288,65,'hello','20150819121237.png',128,128,NULL,'2015-08-19 06:42:37'),(289,68,'hello','20150819121450.png',128,128,NULL,'2015-08-19 06:44:50'),(290,68,'hello','20150819121539.png',128,128,NULL,'2015-08-19 06:45:39'),(291,68,'hello','',0,0,NULL,'2015-08-19 06:53:53'),(292,68,'hello','',0,0,NULL,'2015-08-19 06:54:24'),(293,55,'hello','',0,0,NULL,'2015-08-19 06:54:31'),(294,55,'hello','20150819122635.png',128,128,NULL,'2015-08-19 06:56:35'),(295,55,'hello','20150819122748.png',128,128,NULL,'2015-08-19 06:57:48'),(296,55,'hello','20150819122925.png',128,128,NULL,'2015-08-19 06:59:25'),(297,55,'hello','20150819123017.png',128,128,NULL,'2015-08-19 07:00:17'),(298,55,'hello','20150819123117.png',128,128,NULL,'2015-08-19 07:01:17'),(299,55,'hello','20150819123230.png',128,128,NULL,'2015-08-19 07:02:30'),(300,55,'hello','20150819123350.png',128,128,NULL,'2015-08-19 07:03:50'),(301,55,'hello','20150819123401.png',128,128,NULL,'2015-08-19 07:04:01'),(302,55,'hello','20150819123512.png',128,128,NULL,'2015-08-19 07:05:12'),(303,55,'hello','20150819123838.png',128,128,NULL,'2015-08-19 07:08:38'),(304,55,'hello','20150819124054.png',128,128,NULL,'2015-08-19 07:10:54'),(305,55,'hello','20150819124132.png',128,128,NULL,'2015-08-19 07:11:32'),(306,55,'hello','20150819124253.png',128,128,NULL,'2015-08-19 07:12:53'),(307,55,'hello','20150819124418.png',128,128,NULL,'2015-08-19 07:14:18'),(308,55,'hello','20150819124444.png',128,128,NULL,'2015-08-19 07:14:44'),(309,55,'hello','20150819124558.png',128,128,NULL,'2015-08-19 07:15:58'),(310,55,'hello','20150819031427.png',128,128,NULL,'2015-08-19 09:44:28'),(311,55,'hello','20150819031949.png',128,128,NULL,'2015-08-19 09:49:49'),(312,56,'hello','20150819031957.png',128,128,NULL,'2015-08-19 09:49:57'),(313,56,'hello iam 56','20150819032234.png',128,128,NULL,'2015-08-19 09:52:35'),(314,56,'hello iam 56','',0,0,NULL,'2015-08-19 09:53:43'),(316,60,'hello iam 56','',0,0,NULL,'2015-08-19 09:55:41'),(317,60,'hello iam 56','',0,0,NULL,'2015-08-19 10:03:22'),(321,60,'hello iam 56','',0,0,NULL,'2015-08-19 14:18:40'),(323,55,'hello','',0,0,NULL,'2015-08-20 05:58:09'),(324,55,'hello','20150820112826.png',1366,768,NULL,'2015-08-20 05:58:26'),(327,56,'hello','',0,0,NULL,'2015-08-21 14:46:59'),(328,59,'hello','20150821081707.png',1366,768,NULL,'2015-08-21 14:47:07'),(338,58,'hai ra how ru','20150825123415.jpg',1280,956,NULL,'2015-08-24 19:04:15'),(339,58,'hai friends how ru..have a nice day..this is from karthik tamada','',0,0,NULL,'2015-08-24 19:05:06'),(340,57,'my new selfie ','20150825123546.jpg',642,667,NULL,'2015-08-24 19:05:46'),(341,57,'this for you man...','20150825123646.jpg',638,960,NULL,'2015-08-24 19:06:46'),(343,58,'hello man how ru..aflter very long time..how ru mana..','',0,0,NULL,'2015-08-24 19:07:30'),(344,58,'hello man how ru..aflter very long time..how ru mana..','',0,0,NULL,'2015-08-24 19:07:32'),(345,58,'hello man how ru..aflter very long time..how ru mana..','',0,0,NULL,'2015-08-27 05:54:45'),(346,67,'hello man how ru..aflter very long time..how ru mana..','',0,0,NULL,'2015-08-27 05:54:54'),(347,67,'hello man how ru..aflter very long time..how ru mana..','',0,0,NULL,'2015-08-27 06:26:06'),(348,67,'hello man how ru..aflter very long time..how ru mana..','',0,0,NULL,'2015-08-27 06:30:09'),(349,0,'hello man how ru..aflter very long time..how ru mana..','',0,0,NULL,'2015-08-27 06:30:46'),(350,67,'hello man how ru..aflter very long time..how ru mana..','',0,0,NULL,'2015-08-27 06:31:29'),(351,67,'adt','',0,0,NULL,'2015-08-27 06:56:49'),(352,67,'tst','',0,0,NULL,'2015-08-27 06:57:13'),(353,67,'tst','',0,0,NULL,'2015-08-27 07:12:19'),(354,67,'tst','',0,0,NULL,'2015-08-27 07:13:09'),(355,58,'satish hello my brother ','',0,0,NULL,'2015-08-27 12:20:36'),(357,58,'satish hello my brother//how ru man','20150901023918.jpg',1080,1920,NULL,'2015-09-01 09:09:18'),(358,67,'tst','',0,0,NULL,'2015-09-03 06:09:15'),(359,58,'my new creativity','20150903111233.jpg',960,716,NULL,'2015-09-03 17:42:33'),(360,58,'','20150903111252.jpg',956,1280,NULL,'2015-09-03 17:42:52'),(361,67,'tst','',0,0,NULL,'2015-09-06 05:40:34');
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  `email` varchar(250) NOT NULL,
  `password_hash` text NOT NULL,
  `gender` varchar(1) DEFAULT NULL,
  `dob` datetime DEFAULT NULL,
  `profile_image` text,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `api_key` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `created_at` (`created_at`),
  UNIQUE KEY `api_key` (`api_key`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (57,'satish','satish@gmail.com','hsGYEeD+SMR5IaApVGxSd2ZunN7yP6mOik+qnzFVLTE=$lQBKxUrpvMS6VdWPa4bURPtPVEK25ncJQUo54CQmI9A=',NULL,NULL,NULL,'2015-07-09 19:36:10',NULL),(58,'karthik','karthik@gmail.com','gCtvT5c1YrCMfJNW55/fK09MuTfKMfNa21VLKlpVBXw=$/zFK0x6qyuORpmAZ29FhVnk0ZfQTlFdw8w2bAv63RbY=',NULL,NULL,'20150903120838.jpg','2015-07-09 19:39:18',NULL),(60,'tirumala','amma@gmail.com','D33AZtDXzJVdK7orMQPd/RIifwr24oHuNFTaTDBflSk=$XcoMRAMdUHcwwzI66ar9NtFKdw6nbGGBtczJ7HZzRMY=',NULL,NULL,NULL,'2015-07-09 20:28:43','66613e529e134252bb634a95ead7723f'),(61,'dad','nanna@gmail.com','zk0QU2Q4sCeKGQn0Zibb5DZUrcW5JdwUcjzABICDF8k=$Oi+9bC50mB0myrma4c2AHoRoi4lvMcwroG8CFgbx7LY=',NULL,NULL,NULL,'2015-07-09 20:29:02','9cc061806af7401eb7f6436240ddcccc'),(67,'sat','sat@gmail.com','jyfokUGaC+CyrohPG5DsSXQFPtCHrw1i9fsNvHX6bXw=$zVz3yKDrmMIhPB3GnSttNZWkCcpZDS/YI6wutYg5bNM=',NULL,NULL,NULL,'2015-08-08 06:19:41','05d4a2f9836444a88cc437cfc6c08b33'),(86,'satishkumar','mouni@gmail.com','iPrzCDx1G6VFEIqru3InYt2KQw5fom44yQQW8pQ2G+0=$cXvvPCdFdpUYYbiEt3V5PziBi2bIulm/4sASgbOysJg=',NULL,NULL,NULL,'2015-08-16 18:36:39','a723ffde90cb4297bb159ab5812399c0'),(87,'lion','liond@gmail.comead','c+fispHX0Ce5RySxbA1TR4NtmKj/ioxzL9Q7RLH+r9Y=$5eHOZYJK/ovM356Lmmk0YKq3hKbvGXmddbwDMRFsIf4=',NULL,NULL,NULL,'2015-08-17 04:07:47','ada363ea21a84dcc9f403e374a655444'),(88,'satish','kat@gmail.com','w0zWLWs6aUdoWdgDztyvIMpuL+2MQWeZyN/nWMjDj5k=$bRv3AqRn8uYvt7mnffYE40F86j/duxl8EPojOezfTUg=',NULL,NULL,NULL,'2015-08-17 06:07:55','0cd3cb3b05e94297b3aa44378bed6383'),(89,'amma','kumma@gmail.com','uR0IkpL/xky8OUL8SBYzfohUnK//HDHGoiZgLz3Wplo=$GaMtbVNWBFp4X13r/lQ5YhfBO7TvP0WkAqAnqd3ws1I=',NULL,NULL,NULL,'2015-08-17 12:33:28','91c3e9e47ebb4610abed751e2e787e60'),(90,'kedi','kedi@gmail.com','AKx9w2Iu5lAk/eDMY7eP7Nd9s5R7G9o7CyXCZe8EZNA=$UZMYmDpL2F2D2OiEw4GoEM9dfBZnDtUW1uiOqtM6tqg=',NULL,NULL,NULL,'2015-08-17 17:43:23','0cc22686219a439aba0285927f820461'),(92,'lion','liond@gmail.cdomead','TsSeN5stxhGVwQOZ+WOXVMzK9ogtqOYi0S8B3QwbnSY=$Cjp6WKcDNd5Ze0HuNQ1PfDqm0kIyVt2rf/I6+51O3NQ=',NULL,NULL,NULL,'2015-08-17 17:46:56','8aae7aebb103463888eaa5a9cd117f0f'),(93,'kedi','tag@gmail.com','duuFYAshuf2DJsheqqdf9mwy4vVbwYspNx165sQt+ns=$jREoHRdxJf/fkhpi7w/YubPQR1OtSsx3bNFuzBdaXac=',NULL,NULL,NULL,'2015-08-17 17:49:21','8d6313e2c5d1426ebcc6b66c6948bde7'),(94,'manoj','manoj@gmail.com','FACgLmeKvIw8Fm3ppf1DabBnqYxpd+6wI94tSLTT3Lk=$TVNrQM/Dyo3AF3QdTgzDwh1kMUD5bQRINRWpBZtdBKo=',NULL,NULL,NULL,'2015-08-17 18:19:50','2e1371028124454491d5936e952d2006'),(95,'lion','liond@gmail.cdomeadvz','jzDT/RjNxaIZ+AWx85KlsReLNfFlQnpDbgmByyrSvwc=$j0dE76jV8CPy5DNu4+o5ypyVemK/YsVlLv6y6i+Ljr4=',NULL,NULL,NULL,'2015-08-18 14:40:44','3c30ce4d05814451a6fc112a0e1fe44d'),(97,'ima','iam@gmail.coma','p07Kp1nOAnVu7Rjlg1c3E7ogS6JVXqExMdDKgyvlP2g=$x02onvU6JNZaXE8oFXXm88yhqqRLHoNafn8/xd3UXFs=',NULL,NULL,'20150819054145.png','2015-08-19 12:11:45','4681de83ad204f6bab12e4ee6ad38b4a'),(98,'ima','iam@gmail.rag','hBRY9EpBHG5FPDuFGGzAQu8BZ99haqLbaisMbutXxm0=$7DHrpgGZOOmXjfNRO+CwNN047U541KgHUiyATpMAdmM=',NULL,NULL,'20150819054348.png','2015-08-19 12:13:48','ea301826ff324091a476cbd1abd162f1'),(99,'ima','iam@gmail.ragsat','D/iWpEhXsZaeuiDIeQiEJB9pBbosKIAVHdONx+Hjaqw=$XF7kdFuu8sSOA8R1q61OwRWt/rZNcZFvJEen6CAXmPA=',NULL,NULL,'20150819055700.png','2015-08-19 12:27:00','5ad79d1d4a2c4fed93aad1e070874321'),(100,'ima','iam@gmail.ragsatsdt','CyeqcKb6zNUq5xBQEPgZiVvWZ/OA08TQpfVNfgjmP5U=$cWK9pzvZlwUe2Mgz/4HGFJWiVEa3Wn4UExMKipbtmK0=',NULL,NULL,'20150819055844.png','2015-08-19 12:28:44','41cea370e9264fdaa6b04cde79f7dd6a'),(101,'ima','iam@gmail.dat','0SUB6xaKZKHEAgntfX1wyNG8pXP/+QZ8qcMsoWIsQxw=$S7XHldRpQa+Vbv+qBvbQkmlX8pcX2WJ7hxh9VeYrynY=',NULL,NULL,'20150819060237.jpg','2015-08-19 12:32:37','c9b81ea863d243eeb5a60c27b1673e58'),(103,'satish','jan@gmail.com','hzCGDc+UpPJ+jEiVkn1WsJSNoA3lEo7YIxvi27J6cys=$hv5sHnXENsyyOW0wATp+IGC4TrVvEci7AvLT3fZ5M+Y=',NULL,NULL,'20150821102246.jpg','2015-08-21 16:52:48','137a532d48d94a549e8b169b8bca32de'),(104,'satish','jan@gmail.coma','+Y2tyY8OIsZuo6yn9plU2ereG071reCgHd1AdDAEvzs=$bA35P5SibtmMKUXIvXjbB3d3jD4NY0uXBUw/exQzuGE=',NULL,NULL,'20150821102811.jpg','2015-08-21 16:58:11','7cdd8974f7b341b99c9e1bc919709a10'),(105,'raju','raju@gmail.com','JJii0wmiz8nTq3lL7l9gvZkgPtfZn6zlFDJgl24Ba38=$bUGU7YgDm/qN3OXfelFJiKB79FL7tcMZZUY6GCsS/sQ=',NULL,NULL,'','2015-08-25 17:13:24','0ddf18a23ec44879b183cea7f5f11c12'),(106,'raju','sraju@gmail.com','zzmBTUCq+reuGbqloq8fmPOsr3UnAVH1VrbfVQLaAp8=$biDnIbPu2+M1peweVFq7LcOqf6zEfb6r+/H5lRYMnfA=',NULL,NULL,'','2015-08-26 07:27:31','b417742e9d55428c838097388aae7856'),(107,'raju','sraju@gmail.comd','y9S505DNeqUCFy9D78wVDxA063q0Wf2aJOel4M7M4HQ=$VdYz+Ym04R1yMfsk5cd67JrPMU3M9Lo2Nfcn9oxFtMM=',NULL,NULL,'','2015-08-26 07:43:55','a9222688b8cd4cc0af92d113f5582bd8'),(108,'karthik','rock@gmail.com','t86k2oYO3ep+Vg6XwhCnWBXpq9vqnehFrM0TM9wkdtI=$JFFJYNFNg1GU4sSk741JMyTFYr9i1JjnD5ZnI0+B/Y0=',NULL,NULL,'20150903120232.jpg','2015-09-03 06:32:32','d9ed2897d0ae4547a27b673692097015'),(109,'karthik','rock@gmail.coma','tunJynOPc7k1YBz1RnNev+1CC6yM6NviMzG/2HcUbCM=$eqmiGFrY8VZsH4YgCQtw7PxVVo/wslu7s+wc54/2OQk=',NULL,NULL,'20150903120839.jpg','2015-09-03 06:38:39','5ddf50cf97ce445aa6a2bbf499d36d86'),(110,'raju','sraju@gmail.fgs','CQUeCMSa5VLdopdM67rmxG4XtuYYhSuXmT6oLRjK3pY=$HU7nkFsgIfdEZVufN1eGpUb9tvOdI7beMdCMdoHSjec=',NULL,NULL,'','2015-09-05 10:24:27','3d8643641e694ed6972f7851cd59e77c'),(111,'raju','sraju@gmail.fgsfd','S27+6L8p/HVe/jTqnvh6oCfmJGAntRrsWhu4IVBuCzU=$attKSKlK9tgNAuHmWiTiU4fwjB4BqpygNhL0qzXtpEU=',NULL,NULL,'','2015-09-05 10:26:55','ec41498d68fc4eadb2475516d86b833b');
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

-- Dump completed on 2015-09-06 19:47:29
