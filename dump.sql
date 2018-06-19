-- MySQL dump 10.13  Distrib 5.5.55, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: kcare
-- ------------------------------------------------------
-- Server version	5.5.55-0ubuntu0.14.04.1

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
-- Table structure for table `child`
--

DROP TABLE IF EXISTS `child`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `child` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cancellationdate` date DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `picktimebegin` time DEFAULT NULL,
  `picktimeend` time DEFAULT NULL,
  `puttimebegin` time DEFAULT NULL,
  `puttimeend` time DEFAULT NULL,
  `registrationdate` date DEFAULT NULL,
  `parents` bigint(20) DEFAULT NULL,
  `person` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_s8igqhagmhp3b6fxk7pudsssm` (`person`),
  KEY `FK5ttnb8cn5aqgdincral9ntee8` (`parents`),
  CONSTRAINT `FKnu4lalc07h7b3oar2aq3p81gr` FOREIGN KEY (`person`) REFERENCES `person` (`id`),
  CONSTRAINT `FK5ttnb8cn5aqgdincral9ntee8` FOREIGN KEY (`parents`) REFERENCES `parent` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `child`
--

LOCK TABLES `child` WRITE;
/*!40000 ALTER TABLE `child` DISABLE KEYS */;
INSERT INTO `child` VALUES (1,'2017-07-01',NULL,'13:30:00','14:00:00','07:30:00','08:00:00','2017-06-01',1,30),(2,'2017-07-02',NULL,'13:30:00','14:00:00','07:15:00','08:00:00','2017-06-01',1,31),(3,'2017-07-03',NULL,'13:30:00','14:00:00','07:30:00','08:00:00','2017-06-01',2,32),(4,'2017-07-04',NULL,'13:30:00','14:00:00','07:30:00','08:00:00','2017-06-01',2,33),(5,'2017-07-05',NULL,'13:30:00','14:00:00','07:30:00','08:00:00','2017-06-01',3,34),(6,'2017-07-06',NULL,'13:30:00','14:00:00','07:30:00','08:00:00','2017-06-01',3,35),(7,'2017-07-07',NULL,'13:30:00','14:00:00','07:30:00','08:00:00','2017-06-01',3,36),(8,'2017-07-08',NULL,'13:30:00','14:00:00','07:00:00','08:00:00','2017-06-01',4,37),(9,'2017-07-09',NULL,'13:30:00','14:00:00','07:30:00','08:00:00','2017-06-01',5,38),(10,'2017-07-10',NULL,'13:30:00','14:00:00','07:30:00','08:00:00','2017-06-01',5,39),(11,'2017-07-11',NULL,'13:30:00','14:00:00','07:30:00','08:00:00','2017-06-01',6,40),(12,'2017-07-12',NULL,'13:30:00','14:00:00','07:10:00','08:00:00','2017-06-01',6,41),(13,'2017-07-13',NULL,'13:30:00','14:00:00','07:30:00','08:00:00','2017-06-01',6,42),(14,'2017-07-14',NULL,'13:30:00','14:00:00','07:30:00','08:00:00','2017-06-01',1,43),(15,'2017-07-15',NULL,'13:30:00','14:00:00','07:30:00','08:00:00','2017-06-01',2,44),(16,'2017-07-18',NULL,'13:30:00','14:00:00','07:15:00','08:00:00','2017-06-01',8,45),(17,'2017-07-17',NULL,'13:30:00','14:00:00','07:30:00','08:00:00','2017-06-01',8,46),(18,'2017-07-19',NULL,'13:30:00','14:00:00','07:30:00','08:00:00','2017-06-01',8,47),(19,'2017-07-20',NULL,'13:30:00','14:00:00','08:30:00','09:00:00','2017-06-01',9,48),(20,'2017-07-21',NULL,'13:30:00','14:00:00','07:30:00','08:00:00','2017-06-01',9,49),(21,'2017-07-22',NULL,'13:30:00','14:00:00','07:30:00','08:00:00','2017-06-01',9,50);
/*!40000 ALTER TABLE `child` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `verified` bit(1) DEFAULT NULL,
  `person` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjpgkq2qmt6qsve1y20vmuxw05` (`person`),
  CONSTRAINT `FKjpgkq2qmt6qsve1y20vmuxw05` FOREIGN KEY (`person`) REFERENCES `person` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (1,'',23),(2,'',24),(3,'',25),(4,'',26),(5,'',27),(6,'',28),(7,'\0',29);
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_for`
--

DROP TABLE IF EXISTS `contact_for`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_for` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `child` bigint(20) DEFAULT NULL,
  `contact` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtm0s23d9gv9vr31k17ta6wd2q` (`child`),
  KEY `FK4w855nbwptxcjth7wk12dctm7` (`contact`),
  CONSTRAINT `FK4w855nbwptxcjth7wk12dctm7` FOREIGN KEY (`contact`) REFERENCES `contact` (`id`),
  CONSTRAINT `FKtm0s23d9gv9vr31k17ta6wd2q` FOREIGN KEY (`child`) REFERENCES `child` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_for`
--

LOCK TABLES `contact_for` WRITE;
/*!40000 ALTER TABLE `contact_for` DISABLE KEYS */;
INSERT INTO `contact_for` VALUES (1,1,1),(2,2,2),(3,3,3),(4,4,4),(5,5,4),(6,6,5),(7,7,5),(8,8,6),(9,9,6),(10,10,6);
/*!40000 ALTER TABLE `contact_for` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_pickup`
--

DROP TABLE IF EXISTS `contact_pickup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_pickup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `child` bigint(20) DEFAULT NULL,
  `contact` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmvsmfc6ri55qexo9mta8pk6px` (`child`),
  KEY `FK1vpm1a3omeqjs0ff6hm5rq3vq` (`contact`),
  CONSTRAINT `FK1vpm1a3omeqjs0ff6hm5rq3vq` FOREIGN KEY (`contact`) REFERENCES `contact` (`id`),
  CONSTRAINT `FKmvsmfc6ri55qexo9mta8pk6px` FOREIGN KEY (`child`) REFERENCES `child` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_pickup`
--

LOCK TABLES `contact_pickup` WRITE;
/*!40000 ALTER TABLE `contact_pickup` DISABLE KEYS */;
INSERT INTO `contact_pickup` VALUES (1,'2017-06-01',1,1),(2,'2017-06-02',2,2),(3,'2017-06-03',3,2),(4,'2017-06-04',4,3),(5,'2017-06-05',5,3),(6,'2017-06-06',6,3),(7,'2017-06-07',7,1),(8,'2017-06-08',8,2),(9,'2017-06-09',9,2),(10,'2017-06-10',10,3),(11,'2017-06-11',1,3),(12,'2017-06-12',2,3),(13,'2017-06-13',3,1),(14,'2017-06-14',4,2),(15,'2017-06-15',5,2),(16,'2017-06-16',6,3),(17,'2017-06-17',7,3),(18,'2017-06-18',8,3),(19,'2017-06-19',9,4),(20,'2017-06-20',10,5),(21,'2017-06-21',1,5),(22,'2017-06-22',2,6),(23,'2017-06-23',3,6),(24,'2017-06-24',4,6),(25,'2017-06-25',5,4),(26,'2017-06-26',6,5),(27,'2017-06-27',7,5),(28,'2017-06-28',8,6),(29,'2017-06-29',9,6),(30,'2017-06-30',10,6);
/*!40000 ALTER TABLE `contact_pickup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `holiday`
--

DROP TABLE IF EXISTS `holiday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `holiday` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fromdate` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `todate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `holiday`
--

LOCK TABLES `holiday` WRITE;
/*!40000 ALTER TABLE `holiday` DISABLE KEYS */;
INSERT INTO `holiday` VALUES (1,'2000-12-24','christmas 2000','2000-12-26'),(2,'2001-12-24','christmas 2001','2001-12-26'),(3,'2002-12-24','christmas 2002','2002-12-26'),(4,'2003-12-24','christmas 2003','2003-12-26'),(5,'2004-12-24','christmas 2004','2004-12-26'),(6,'2005-12-24','christmas 2005','2005-12-26'),(7,'2006-12-24','christmas 2006','2006-12-26'),(8,'2007-12-24','christmas 2007','2007-12-26'),(9,'2008-12-24','christmas 2008','2008-12-26'),(10,'2009-12-24','christmas 2009','2009-12-26'),(11,'2010-12-24','christmas 2010','2010-12-26'),(12,'2011-12-24','christmas 2011','2011-12-26'),(13,'2012-12-24','christmas 2012','2012-12-26'),(14,'2013-12-24','christmas 2013','2013-12-26'),(15,'2014-12-24','christmas 2014','2014-12-26'),(16,'2015-12-24','christmas 2015','2015-12-26'),(17,'2016-12-24','christmas 2016','2016-12-26'),(18,'2017-12-24','christmas 2017','2017-12-26'),(19,'2018-12-24','christmas 2018','2018-12-26'),(20,'2019-12-24','christmas 2019','2019-12-26'),(21,'2020-12-24','christmas 2020','2020-12-26');
/*!40000 ALTER TABLE `holiday` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daysbefore` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `done` bit(1) DEFAULT NULL,
  `enddate` date DEFAULT NULL,
  `startdate` date DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `parents` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6m7v9v9x4tcu1gqh30trhnwwx` (`parents`),
  CONSTRAINT `FK6m7v9v9x4tcu1gqh30trhnwwx` FOREIGN KEY (`parents`) REFERENCES `parent` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES (1,5,'Den gesamten Boden putzen.','\0','2017-06-07','2017-06-01','Boden wischen',2),(2,2,'Alle Teller, Messer und Gabeln müssen einmal gründlich gereinigt werden.','\0','2017-06-07','2017-06-06','Geschirr spülen',2),(3,10,'Wir wollen am 07. Januar 2000 ein gesundes Frühstück essen. Bitte zubereiten!','','2017-06-07','2017-06-06','Frühstück mitbringen',3),(4,3,'Wir haben nächste Woche am Montag keine Küche und müssen deshalb für Mittagessen auf andere Art und Weise sorgen!','\0','2017-06-05','2017-06-05','Mittagessen besorgen',4),(5,4,'Wir brauchen neue Bälle und etwas für die Eisenbahn!','\0','2017-06-07','2017-06-06','Spielsachen kaufen',5),(6,9,'Für die neu-Bemalung der Außenwand benötigen wir hilfe!','\0','2017-06-07','2017-06-06','Hilfe beim Wand-Strich',3),(7,2,'Wir starten ein großes Zeichen-Event und benötigen dafür Unterstützung bei der Betreuung!','\0','2017-06-15','2017-06-15','Unterstützung bei Betreuung',6),(8,4,'Unsere Waschmaschine will nicht mehr laufen. Wir benötigen Hilfe bei der Reparatur!','\0','2017-06-21','2017-06-21','Gerätereparatur',7);
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lunch`
--

DROP TABLE IF EXISTS `lunch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lunch` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `child` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpf9jm38radca6pabvx8v650r4` (`child`),
  CONSTRAINT `FKpf9jm38radca6pabvx8v650r4` FOREIGN KEY (`child`) REFERENCES `child` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lunch`
--

LOCK TABLES `lunch` WRITE;
/*!40000 ALTER TABLE `lunch` DISABLE KEYS */;
INSERT INTO `lunch` VALUES (1,'2017-06-01',1),(2,'2017-06-02',2),(3,'2017-06-06',3),(4,'2017-06-07',4),(5,'2017-06-08',5),(6,'2017-06-09',6),(7,'2017-06-12',7),(8,'2017-06-13',8),(9,'2017-06-14',9),(10,'2017-06-15',10),(11,'2017-06-16',11),(12,'2017-06-19',12),(13,'2017-06-20',13),(14,'2017-06-21',14),(15,'2017-06-22',15),(16,'2017-06-23',16),(17,'2017-06-26',17),(18,'2017-06-27',18),(19,'2017-06-28',19),(20,'2017-06-29',20),(21,'2017-06-30',21);
/*!40000 ALTER TABLE `lunch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `author` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrw9tnu1sbug1yjxvw578rp0cb` (`author`),
  CONSTRAINT `FKrw9tnu1sbug1yjxvw578rp0cb` FOREIGN KEY (`author`) REFERENCES `person` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,'testcontent','2000-01-01 00:00:00','test',4);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parent`
--

DROP TABLE IF EXISTS `parent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parent` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) DEFAULT NULL,
  `father` bigint(20) DEFAULT NULL,
  `mother` bigint(20) DEFAULT NULL,
  `user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_kf1fknunpbktgt4uyt60h8rt9` (`father`),
  UNIQUE KEY `UK_tqx4srjeurhapymp5lrsalbwi` (`mother`),
  UNIQUE KEY `UK_l668n0dl4vpoimtifem52b5y8` (`user`),
  CONSTRAINT `FK40d33gedwgtajqqnj0vjcv3p0` FOREIGN KEY (`user`) REFERENCES `user` (`id`),
  CONSTRAINT `FK5btstupvc3dnqys2j0bk3f21n` FOREIGN KEY (`father`) REFERENCES `person` (`id`),
  CONSTRAINT `FKdcmsw6b6jnlbsf0b3whrwa2yy` FOREIGN KEY (`mother`) REFERENCES `person` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parent`
--

LOCK TABLES `parent` WRITE;
/*!40000 ALTER TABLE `parent` DISABLE KEYS */;
INSERT INTO `parent` VALUES (1,'',6,5,6),(2,'',8,7,7),(3,'',10,9,8),(4,'',12,11,9),(5,'',14,13,10),(6,'',16,15,11),(7,'',18,17,12),(8,'',20,19,13),(9,'\0',22,21,14);
/*!40000 ALTER TABLE `parent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `birthdate` date DEFAULT NULL,
  `businessphone` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `picture` longblob,
  `sex` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6evb3bvfe9xbijmnlh0t341a6` (`businessphone`),
  UNIQUE KEY `UK_s3w4jbamd5k3fkmo9p8lawk65` (`mail`),
  UNIQUE KEY `UK_24t7n7hhuvwaqw92b2t514m33` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'2000-01-02','1234897235','Rainer','Zufall','rz@mail.com','123456789',NULL,'\0'),(2,'2000-01-03','8472962345','Willma','Reihn','wr@mail.com','987654321',NULL,''),(3,'2000-01-04','2357893265','Karl','Toffel','kt@mail.com','123498765',NULL,'\0'),(4,'2000-01-05','4563412384','Frank','Reich','fr@mail.com','543216789',NULL,'\0'),(5,'2000-01-06','235703124513','Dennis','Schläger','ds@mail.com','213465789',NULL,'\0'),(6,'2000-01-07','238947562314','Karl','Kulator','kkulator@mail.com','124356879',NULL,'\0'),(7,'2000-01-08','78623456829','Kurt','Schluss','ks@mail.com','135792468',NULL,'\0'),(8,'2000-01-09','78623456828','Ernst','Haft','eh@mail.com','135792466',NULL,'\0'),(9,'2000-02-01','78623456827','Jana','Türlich','jn@mail.com','135792467',NULL,''),(10,'2000-02-03','18625456827','Klara','Fall','kf@mail.com','1387492367',NULL,''),(11,'2000-02-04','18625256827','Santer','Klaus','sk@mail.com','135732367',NULL,'\0'),(12,'2000-02-05','18623456828','Peter','Silie','ps@mail.com','133792367',NULL,'\0'),(13,'2000-02-06','18625556827','Klaus','Uhr','ku@mail.com','135792333',NULL,'\0'),(14,'2000-02-07','12345142','Isolde','Maduschen','im@mail.com','12345141',NULL,''),(15,'2000-02-08','12345152','Andy','Arbeit','aa@mail.com','12345151',NULL,'\0'),(16,'2000-02-09','12345162','Axel','Schweiß','as@mail.com','12345161',NULL,'\0'),(17,'2000-03-01','12345172','Andre','Seite','as2@mail.com','12345171',NULL,'\0'),(18,'2000-03-02','12345182','Franz','Ohse','fo@mail.com','12345181',NULL,'\0'),(19,'2000-03-03','12345192','Anna','Nass','an@mail.com','12345191',NULL,''),(20,'2000-03-04','12345202','Karl','Rasur','krasur@mail.com','12345201',NULL,'\0'),(21,'2000-03-05','12345212','Lee','Mone','lm@mail.com','12345211',NULL,'\0'),(22,'2000-03-06','12345222','Mario','Nette','mn@mail.com','12345221',NULL,'\0'),(23,'2000-03-07','12345232','Ali','Gator','ag@mail.com','12345231',NULL,'\0'),(24,'2000-03-08','12345242','Erhard','Pech','ep@mail.com','12345241',NULL,'\0'),(25,'2000-03-09','12345252','Jan','Uar','ju@mail.com','12345251',NULL,'\0'),(26,'2000-04-01','12345262','Ken','Stumich','ks2@mail.com','12345261',NULL,'\0'),(27,'2000-04-02','12345272','Finn','Derlohn','fd@mail.com','12345271',NULL,'\0'),(28,'2000-04-03','12345282','Ben','Deriss','bd@mail.com','12345281',NULL,'\0'),(29,'2000-04-04','12345292','Nena','Del','nd@mail.com','12345291',NULL,''),(30,'2013-04-05','12345302','Anna','Log','al@mail.com','12345301',NULL,''),(31,'2013-04-06','12345312','Anna','Gramm','ag2@mail.com','12345311',NULL,''),(32,'2013-04-07','12345322','Erkan','Alles','ea2@mail.com','12345321',NULL,'\0'),(33,'2013-04-08','12345332','Gerd','Nehr','gn2@mail.com','12345331',NULL,'\0'),(34,'2013-04-09','12345342','Ismir','Schnuppe','ischnuppe@mail.com','12345341',NULL,'\0'),(35,'2013-05-01','12345352','Sam','Urei','sauerei@mail.com','12345351',NULL,'\0'),(36,'2013-05-02','12345362','Phil','Fraß','pfrass@mail.com','12345361',NULL,'\0'),(37,'2013-05-03','12345372','Karl','Ender','kalender@mail.com','12345371',NULL,'\0'),(38,'2013-05-04','12345382','Jim','Panse','jpanse@mail.com','12345381',NULL,'\0'),(39,'2013-05-05','12345392','Johannes','Kraut','jkraut@mail.com','12345391',NULL,'\0'),(40,'2013-05-06','12345402','Ben','Zin','benzin@mail.com','12345401',NULL,'\0'),(41,'2013-05-07','12345412','Anna','Lyse','annalyse@mail.com','12345411',NULL,''),(42,'2013-05-08','12345422','Moni','Thor','monithor@mail.com','12345421',NULL,''),(43,'2013-05-09','12345432','Tino','Saurier','tinosauerier@mail.com','12345431',NULL,'\0'),(44,'2013-06-01','12345442','Johannes','Bäre','jbäre@mail.com','12345441',NULL,'\0'),(45,'2013-06-02','12345452','Roy','Ber','royber@mail.com','12345451',NULL,'\0'),(46,'2013-06-03','12345462','Ellen','Bogen','ellenbogen@mail.com','12345461',NULL,'\0'),(47,'2013-06-04','12345472','Mario','Nese','marionese@mail.com','12345471',NULL,'\0'),(48,'2013-06-05','12345482','Frank','Furt','frankfurter@mail.com','12345481',NULL,'\0'),(49,'2013-06-06','12345492','Claire','Werk','clairewerk@mail.com','12345491',NULL,''),(50,'2013-06-07','12345502','Klaus','Taler','ktbier@mail.com','12345501',NULL,'\0');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `picture`
--

DROP TABLE IF EXISTS `picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `picture` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `uploadtime` datetime DEFAULT NULL,
  `person` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfr808iidfvt4xny49dt81o6k` (`person`),
  CONSTRAINT `FKfr808iidfvt4xny49dt81o6k` FOREIGN KEY (`person`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `picture`
--

LOCK TABLES `picture` WRITE;
/*!40000 ALTER TABLE `picture` DISABLE KEYS */;
/*!40000 ALTER TABLE `picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scheduled_absent_child`
--

DROP TABLE IF EXISTS `scheduled_absent_child`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scheduled_absent_child` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `absentchild` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk8j8sidstojc509m9sgbjwvy0` (`absentchild`),
  CONSTRAINT `FKk8j8sidstojc509m9sgbjwvy0` FOREIGN KEY (`absentchild`) REFERENCES `child` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scheduled_absent_child`
--

LOCK TABLES `scheduled_absent_child` WRITE;
/*!40000 ALTER TABLE `scheduled_absent_child` DISABLE KEYS */;
INSERT INTO `scheduled_absent_child` VALUES (1,'2000-01-01',2),(2,'2000-01-02',1);
/*!40000 ALTER TABLE `scheduled_absent_child` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scheduled_child`
--

DROP TABLE IF EXISTS `scheduled_child`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scheduled_child` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `child` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6rtermym31s9ankyan22eameo` (`child`),
  CONSTRAINT `FK6rtermym31s9ankyan22eameo` FOREIGN KEY (`child`) REFERENCES `child` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scheduled_child`
--

LOCK TABLES `scheduled_child` WRITE;
/*!40000 ALTER TABLE `scheduled_child` DISABLE KEYS */;
INSERT INTO `scheduled_child` VALUES (1,'2017-06-01',NULL,1),(2,'2017-06-02',NULL,2),(3,'2017-06-06',NULL,3),(4,'2017-06-07',NULL,4),(5,'2017-06-08',NULL,5),(6,'2017-06-09',NULL,6),(7,'2017-06-12',NULL,7),(8,'2017-06-13',NULL,8),(9,'2017-06-14',NULL,9),(10,'2017-06-15',NULL,10),(11,'2017-06-16',NULL,11),(12,'2017-06-19',NULL,12),(13,'2017-06-20',NULL,13),(14,'2017-06-21',NULL,14),(15,'2017-06-22',NULL,15),(16,'2017-06-23',NULL,16),(17,'2017-06-26',NULL,17),(18,'2017-06-27',NULL,18),(19,'2017-06-28',NULL,19),(20,'2017-06-29',NULL,20),(21,'2017-06-30',NULL,21);
/*!40000 ALTER TABLE `scheduled_child` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scheduled_max_childs`
--

DROP TABLE IF EXISTS `scheduled_max_childs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scheduled_max_childs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `childslimit` bigint(20) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_adqkscs9j1tv1ebthang2rbol` (`date`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scheduled_max_childs`
--

LOCK TABLES `scheduled_max_childs` WRITE;
/*!40000 ALTER TABLE `scheduled_max_childs` DISABLE KEYS */;
INSERT INTO `scheduled_max_childs` VALUES (1,15,'2017-06-01'),(2,30,'2017-06-02'),(3,30,'2017-06-06'),(4,30,'2017-06-07'),(5,30,'2017-06-08'),(6,30,'2017-06-09'),(7,30,'2017-06-12'),(8,30,'2017-06-13'),(9,30,'2017-06-14'),(10,30,'2017-06-15'),(11,30,'2017-06-16'),(12,30,'2017-06-19'),(13,30,'2017-06-20'),(14,30,'2017-06-21'),(15,30,'2017-06-22'),(16,30,'2017-06-23'),(17,30,'2017-06-26'),(18,30,'2017-06-27'),(19,30,'2017-06-28'),(20,30,'2017-06-29'),(21,30,'2017-06-30');
/*!40000 ALTER TABLE `scheduled_max_childs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scheduled_supervisor`
--

DROP TABLE IF EXISTS `scheduled_supervisor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scheduled_supervisor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `supervisor` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4yeh8ku99c8v27p4g4jr19fo6` (`supervisor`),
  CONSTRAINT `FK4yeh8ku99c8v27p4g4jr19fo6` FOREIGN KEY (`supervisor`) REFERENCES `supervisor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scheduled_supervisor`
--

LOCK TABLES `scheduled_supervisor` WRITE;
/*!40000 ALTER TABLE `scheduled_supervisor` DISABLE KEYS */;
INSERT INTO `scheduled_supervisor` VALUES (1,'2017-06-01',1),(2,'2017-06-02',1),(3,'2017-06-06',2),(4,'2017-06-07',2),(5,'2017-06-08',2),(6,'2017-06-09',2),(7,'2017-06-12',2),(8,'2017-06-13',2),(9,'2017-06-14',2),(10,'2017-06-15',2),(11,'2017-06-16',2),(12,'2017-06-19',2),(13,'2017-06-20',2),(14,'2017-06-21',2),(15,'2017-06-22',2),(16,'2017-06-23',2),(17,'2017-06-26',2),(18,'2017-06-27',2),(19,'2017-06-28',2),(20,'2017-06-29',2),(21,'2017-06-30',2);
/*!40000 ALTER TABLE `scheduled_supervisor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supervisor`
--

DROP TABLE IF EXISTS `supervisor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `supervisor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `person` bigint(20) DEFAULT NULL,
  `user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_c1r8qxfhq99d9281cogrlyds7` (`person`),
  UNIQUE KEY `UK_tbo9hm192xbcqlw6sj5c85lvb` (`user`),
  CONSTRAINT `FK23baupmjl6no4fvh7tv408l6p` FOREIGN KEY (`user`) REFERENCES `user` (`id`),
  CONSTRAINT `FKpj6pao401mive7ackbsvqdnub` FOREIGN KEY (`person`) REFERENCES `person` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supervisor`
--

LOCK TABLES `supervisor` WRITE;
/*!40000 ALTER TABLE `supervisor` DISABLE KEYS */;
INSERT INTO `supervisor` VALUES (1,1,2),(2,2,3),(3,3,4),(4,4,5);
/*!40000 ALTER TABLE `supervisor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'30274c47903bd1bac7633bbf09743149ebab805f','admin'),(2,'30274c47903bd1bac7633bbf09743149ebab805f','user1'),(3,'30274c47903bd1bac7633bbf09743149ebab805f','sup2'),(4,'30274c47903bd1bac7633bbf09743149ebab805f','sup3'),(5,'30274c47903bd1bac7633bbf09743149ebab805f','sup4'),(6,'30274c47903bd1bac7633bbf09743149ebab805f','user2'),(7,'30274c47903bd1bac7633bbf09743149ebab805f','par2'),(8,'30274c47903bd1bac7633bbf09743149ebab805f','par3'),(9,'30274c47903bd1bac7633bbf09743149ebab805f','par4'),(10,'30274c47903bd1bac7633bbf09743149ebab805f','par5'),(11,'30274c47903bd1bac7633bbf09743149ebab805f','par6'),(12,'30274c47903bd1bac7633bbf09743149ebab805f','par7'),(13,'30274c47903bd1bac7633bbf09743149ebab805f','par8'),(14,'30274c47903bd1bac7633bbf09743149ebab805f','par9');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userrole`
--

DROP TABLE IF EXISTS `userrole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userrole` (
  `user_id` bigint(20) NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  KEY `FKtbick5dbrpnos6ll2175dt5qr` (`user_id`),
  CONSTRAINT `FKtbick5dbrpnos6ll2175dt5qr` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userrole`
--

LOCK TABLES `userrole` WRITE;
/*!40000 ALTER TABLE `userrole` DISABLE KEYS */;
INSERT INTO `userrole` VALUES (1,'ADMIN'),(2,'SUPERVISOR'),(3,'SUPERVISOR'),(4,'SUPERVISOR'),(5,'SUPERVISOR'),(6,'PARENTS'),(7,'PARENTS'),(8,'PARENTS'),(9,'PARENTS'),(10,'PARENTS'),(11,'PARENTS'),(12,'PARENTS'),(13,'PARENTS'),(14,'PARENTS');
/*!40000 ALTER TABLE `userrole` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-05 18:55:10
