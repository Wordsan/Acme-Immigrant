start transaction;

drop database if exists `AcmeImmigrant`;create database `AcmeImmigrant`;
use 'AcmeImmigrant';

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete on `AcmeImmigrant`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter,
create temporary tables, lock tables, create view, create routine, alter routine,
execute, trigger, show view on `AcmeImmigrant`.* to 'acme-manager'@'%';

-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: acmeimmigrant
-- ------------------------------------------------------
-- Server version	5.5.29

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
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cgls5lrufx91ufsyh467spwa3` (`userAccount_id`),
  CONSTRAINT `FK_cgls5lrufx91ufsyh467spwa3` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor`
--

LOCK TABLES `actor` WRITE;
/*!40000 ALTER TABLE `actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_idt4b4u259p6vs4pyr9lax4eg` (`userAccount_id`),
  CONSTRAINT `FK_idt4b4u259p6vs4pyr9lax4eg` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (2692,0,NULL,'jramirez@us.es','Jose',NULL,'Ramirez',2632),(2693,0,NULL,'rjimenez@us.es','Ramon',NULL,'Jimenez',2633),(2694,0,'C/ Reina Mercedes, 18','jdominguez@immigrant.es','Jesus','+34 (957) 3485','Dominguez',2634);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `closedMoment` datetime DEFAULT NULL,
  `CVVCode` int(11) NOT NULL,
  `brandName` varchar(255) DEFAULT NULL,
  `expirationMonth` varchar(255) DEFAULT NULL,
  `expirationYear` varchar(255) DEFAULT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `openedMoment` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `decision_id` int(11) DEFAULT NULL,
  `immigrant_id` int(11) NOT NULL,
  `officer_id` int(11) DEFAULT NULL,
  `personalSection_id` int(11) DEFAULT NULL,
  `visa_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sf5yamfi78enk66kjk5d6hs98` (`id`,`visa_id`),
  UNIQUE KEY `UK_lwjqty0esse0b2ud5s4d6itxb` (`ticker`),
  KEY `UK_ixyjfwd2sw5x6iih1qr8x1612` (`status`),
  KEY `FK_o5wrqyaeocy05ltdiqhd8uh7g` (`decision_id`),
  KEY `FK_gn7hg7d529v5m51e0n2vf9mfi` (`immigrant_id`),
  KEY `FK_k8kn42d2a9geayum9vvcqnhpg` (`officer_id`),
  KEY `FK_ljcrqvs9rm9fr4c9122g1xdwy` (`personalSection_id`),
  KEY `FK_87kxbeua8uijovhovrose2l98` (`visa_id`),
  CONSTRAINT `FK_87kxbeua8uijovhovrose2l98` FOREIGN KEY (`visa_id`) REFERENCES `visa` (`id`),
  CONSTRAINT `FK_gn7hg7d529v5m51e0n2vf9mfi` FOREIGN KEY (`immigrant_id`) REFERENCES `immigrant` (`id`),
  CONSTRAINT `FK_k8kn42d2a9geayum9vvcqnhpg` FOREIGN KEY (`officer_id`) REFERENCES `officer` (`id`),
  CONSTRAINT `FK_ljcrqvs9rm9fr4c9122g1xdwy` FOREIGN KEY (`personalSection_id`) REFERENCES `personalsection` (`id`),
  CONSTRAINT `FK_o5wrqyaeocy05ltdiqhd8uh7g` FOREIGN KEY (`decision_id`) REFERENCES `decision` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (2737,0,NULL,684,'VISA','08','20','Perico de los Palotes','4012888888881881','2018-07-01 08:00:00','OPENED','180701-ACBW37',NULL,2695,NULL,2649,2731),(2738,1,'2018-07-01 08:00:00',123,'MASTERCARD','02','19','Paquito el Chocolatero','4012888888881881','2018-06-01 08:00:00','ACCEPTED','180301-LKSC84',2754,2696,2707,2650,2731),(2739,1,'2018-07-08 08:00:00',123,'MASTERCARD','02','19','Paquito el Chocolatero','4012888888881881','2018-07-01 08:00:00','REJECTED','180701-LCIT24',2755,2696,2707,2651,2732),(2740,0,'2018-07-10 08:00:00',124,'AMEX','03','20','Credit card 1','4012888888881881','2018-07-02 08:00:00','AWAITING','180702-MFVU24',NULL,2697,2708,2652,2733),(2741,0,NULL,684,'VISA','08','20','Perico de los Palotes','4012888888881881','2018-07-01 08:00:00','AWAITING','180701-ZVWE48',NULL,2695,NULL,2653,2733),(2742,0,NULL,684,'VISA','08','20','Perico de los Palotes','4012888888881881','2018-07-01 08:00:00','AWAITING','180701-AVWE48',NULL,2698,NULL,2654,2733),(2743,0,NULL,684,'VISA','08','20','Perico de los Palotes','4012888888881881','2018-07-01 08:00:00','AWAITING','180701-BVWE48',NULL,2699,NULL,2655,2733),(2744,0,NULL,684,'VISA','08','20','Perico de los Palotes','4012888888881881','2018-07-01 08:00:00','AWAITING','180701-CVWE48',NULL,2695,NULL,2656,2733),(2745,0,NULL,684,'VISA','08','20','Perico de los Palotes','4012888888881881','2018-07-01 08:00:00','AWAITING','180701-DVWE48',NULL,2695,NULL,2657,2733),(2746,0,NULL,684,'VISA','08','20','Perico de los Palotes','4012888888881881','2018-07-01 08:00:00','AWAITING','180701-EVWE48',NULL,2695,NULL,2658,2733),(2747,0,NULL,684,'VISA','08','20','Perico de los Palotes','4012888888881881','2018-07-01 08:00:00','AWAITING','180701-FVWE48',NULL,2695,NULL,2659,2733),(2748,0,NULL,684,'VISA','08','20','Perico de los Palotes','4012888888881881','2018-07-01 08:00:00','AWAITING','180701-GVWE48',NULL,2700,NULL,2660,2733),(2749,0,NULL,684,'VISA','08','20','Perico de los Palotes','4012888888881881','2018-07-01 08:00:00','AWAITING','180701-HVWE48',NULL,2701,NULL,2661,2733),(2750,0,NULL,684,'VISA','08','20','Perico de los Palotes','4012888888881881','2018-07-01 08:00:00','AWAITING','180701-IVWE48',NULL,2702,NULL,2662,2733);
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_application`
--

DROP TABLE IF EXISTS `application_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_application` (
  `Application_id` int(11) NOT NULL,
  `linkedApplications_id` int(11) NOT NULL,
  KEY `FK_oep8nqis5vo4tqyics798p6si` (`linkedApplications_id`),
  KEY `FK_1y58f51hd3yjt4bwgjtt62ng6` (`Application_id`),
  CONSTRAINT `FK_1y58f51hd3yjt4bwgjtt62ng6` FOREIGN KEY (`Application_id`) REFERENCES `application` (`id`),
  CONSTRAINT `FK_oep8nqis5vo4tqyics798p6si` FOREIGN KEY (`linkedApplications_id`) REFERENCES `application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_application`
--

LOCK TABLES `application_application` WRITE;
/*!40000 ALTER TABLE `application_application` DISABLE KEYS */;
/*!40000 ALTER TABLE `application_application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_contactsection`
--

DROP TABLE IF EXISTS `application_contactsection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_contactsection` (
  `Application_id` int(11) NOT NULL,
  `contactSections_id` int(11) NOT NULL,
  UNIQUE KEY `UK_st6b12tgawp9vnfn6p3isna2e` (`contactSections_id`),
  KEY `FK_gakl1dvvw1xw8h5mv52n5y1j5` (`Application_id`),
  CONSTRAINT `FK_gakl1dvvw1xw8h5mv52n5y1j5` FOREIGN KEY (`Application_id`) REFERENCES `application` (`id`),
  CONSTRAINT `FK_st6b12tgawp9vnfn6p3isna2e` FOREIGN KEY (`contactSections_id`) REFERENCES `contactsection` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_contactsection`
--

LOCK TABLES `application_contactsection` WRITE;
/*!40000 ALTER TABLE `application_contactsection` DISABLE KEYS */;
INSERT INTO `application_contactsection` VALUES (2740,2681);
/*!40000 ALTER TABLE `application_contactsection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_educationsection`
--

DROP TABLE IF EXISTS `application_educationsection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_educationsection` (
  `Application_id` int(11) NOT NULL,
  `educationSections_id` int(11) NOT NULL,
  UNIQUE KEY `UK_k4qpcgq8o645yv827i2c15l87` (`educationSections_id`),
  KEY `FK_bftma5q3a28uek8tmvkxsacrs` (`Application_id`),
  CONSTRAINT `FK_bftma5q3a28uek8tmvkxsacrs` FOREIGN KEY (`Application_id`) REFERENCES `application` (`id`),
  CONSTRAINT `FK_k4qpcgq8o645yv827i2c15l87` FOREIGN KEY (`educationSections_id`) REFERENCES `educationsection` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_educationsection`
--

LOCK TABLES `application_educationsection` WRITE;
/*!40000 ALTER TABLE `application_educationsection` DISABLE KEYS */;
INSERT INTO `application_educationsection` VALUES (2738,2666),(2739,2665);
/*!40000 ALTER TABLE `application_educationsection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_socialsection`
--

DROP TABLE IF EXISTS `application_socialsection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_socialsection` (
  `Application_id` int(11) NOT NULL,
  `socialSections_id` int(11) NOT NULL,
  UNIQUE KEY `UK_40gsd5kpt820x3k7qc6cmexb2` (`socialSections_id`),
  KEY `FK_irpfx185v586w44l7mhdi4klc` (`Application_id`),
  CONSTRAINT `FK_irpfx185v586w44l7mhdi4klc` FOREIGN KEY (`Application_id`) REFERENCES `application` (`id`),
  CONSTRAINT `FK_40gsd5kpt820x3k7qc6cmexb2` FOREIGN KEY (`socialSections_id`) REFERENCES `socialsection` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_socialsection`
--

LOCK TABLES `application_socialsection` WRITE;
/*!40000 ALTER TABLE `application_socialsection` DISABLE KEYS */;
INSERT INTO `application_socialsection` VALUES (2737,2667),(2738,2668),(2739,2669),(2740,2670),(2741,2671),(2742,2672),(2743,2673),(2744,2674),(2745,2675),(2746,2676),(2747,2677),(2748,2678),(2749,2679),(2750,2680);
/*!40000 ALTER TABLE `application_socialsection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_worksection`
--

DROP TABLE IF EXISTS `application_worksection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_worksection` (
  `Application_id` int(11) NOT NULL,
  `workSections_id` int(11) NOT NULL,
  UNIQUE KEY `UK_nbmjeq2k371mf0urtwvdyjf7w` (`workSections_id`),
  KEY `FK_31re5rncibc390scjhq9pc6r1` (`Application_id`),
  CONSTRAINT `FK_31re5rncibc390scjhq9pc6r1` FOREIGN KEY (`Application_id`) REFERENCES `application` (`id`),
  CONSTRAINT `FK_nbmjeq2k371mf0urtwvdyjf7w` FOREIGN KEY (`workSections_id`) REFERENCES `worksection` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_worksection`
--

LOCK TABLES `application_worksection` WRITE;
/*!40000 ALTER TABLE `application_worksection` DISABLE KEYS */;
INSERT INTO `application_worksection` VALUES (2737,2663),(2740,2664);
/*!40000 ALTER TABLE `application_worksection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_p6elut499cl32in8b8j8sy2n4` (`parent_id`),
  CONSTRAINT `FK_p6elut499cl32in8b8j8sy2n4` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (2682,0,'CATEGORY',NULL),(2683,0,'Student',2682),(2684,0,'Work',2682),(2685,0,'3-Month',2683),(2686,0,'1-year',2683),(2687,0,'Permanent',2684),(2688,0,'Categoria 7',2682),(2689,0,'Categoria 8',2682),(2690,0,'Categoria 9',2682),(2691,0,'Categoria 10',2682);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configurationsystem`
--

DROP TABLE IF EXISTS `configurationsystem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configurationsystem` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `systemName` varchar(255) DEFAULT NULL,
  `welcomeMessageEN` varchar(255) DEFAULT NULL,
  `welcomeMessageES` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configurationsystem`
--

LOCK TABLES `configurationsystem` WRITE;
/*!40000 ALTER TABLE `configurationsystem` DISABLE KEYS */;
INSERT INTO `configurationsystem` VALUES (2730,0,'https://www.delengua.es/sites/default/files/2016-10/visado.jpg','Acme Immigrant','Hi! Welcome to Acme Immigrant','Hola! Bienvenido a Acme Immigrant');
/*!40000 ALTER TABLE `configurationsystem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contactsection`
--

DROP TABLE IF EXISTS `contactsection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contactsection` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `emailAddress` varchar(255) DEFAULT NULL,
  `pagerNumber` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contactsection`
--

LOCK TABLES `contactsection` WRITE;
/*!40000 ALTER TABLE `contactsection` DISABLE KEYS */;
INSERT INTO `contactsection` VALUES (2681,0,'aparrado@gmail.com','678913875','+34 (648) 134857');
/*!40000 ALTER TABLE `contactsection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `ISOCode` varchar(255) DEFAULT NULL,
  `flag` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `wikiPage` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (2709,0,'724','https://wikipedia.org/Spain/flag.jpeg','España','https://es.wikipedia.org/wiki/Spain'),(2710,0,'840','https://wikipedia.org/United_States_of_America/flag.jpg','Estados Unidos','https://es.wikipedia.org/wiki/Estados_Unidos'),(2711,0,'724','https://wikipedia.org/Spain/flag.jpeg','Australia','https://es.wikipedia.org/wiki/Spain'),(2712,0,'724','https://wikipedia.org/Spain/flag.jpeg','Inglaterra','https://es.wikipedia.org/wiki/Spain'),(2713,0,'724','https://wikipedia.org/Spain/flag.jpeg','Alemania','https://es.wikipedia.org/wiki/Spain'),(2714,0,'724','https://wikipedia.org/Spain/flag.jpeg','Italia','https://es.wikipedia.org/wiki/Spain');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `decision`
--

DROP TABLE IF EXISTS `decision`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `decision` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `accepted` bit(1) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `madeMoment` datetime DEFAULT NULL,
  `application_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7jjwf8germf2kicmxd0p8bi4j` (`application_id`),
  CONSTRAINT `FK_7jjwf8germf2kicmxd0p8bi4j` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `decision`
--

LOCK TABLES `decision` WRITE;
/*!40000 ALTER TABLE `decision` DISABLE KEYS */;
INSERT INTO `decision` VALUES (2754,0,'','Rechazado por falta de datos','2018-07-15 08:00:00',2738),(2755,0,'\0',NULL,'2018-07-12 08:00:00',2739);
/*!40000 ALTER TABLE `decision` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `educationsection`
--

DROP TABLE IF EXISTS `educationsection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `educationsection` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `awarded` date DEFAULT NULL,
  `degreeName` varchar(255) DEFAULT NULL,
  `institution` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `educationsection`
--

LOCK TABLES `educationsection` WRITE;
/*!40000 ALTER TABLE `educationsection` DISABLE KEYS */;
INSERT INTO `educationsection` VALUES (2665,0,'2015-09-01','Grado de Biologia','Universidad de Sevilla','UNIVERSITYDEGREE'),(2666,0,'2008-07-01','Bachillerato de Ciencias y Tecnologias','IES Lope de Vega','BACHELOR');
/*!40000 ALTER TABLE `educationsection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',3);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `immigrant`
--

DROP TABLE IF EXISTS `immigrant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `immigrant` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  `investigator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_agy28n7ayxk2eo0jw49svx6fq` (`userAccount_id`),
  KEY `FK_pmxjax5pid0rsv0rcftjvus3y` (`investigator_id`),
  CONSTRAINT `FK_agy28n7ayxk2eo0jw49svx6fq` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`),
  CONSTRAINT `FK_pmxjax5pid0rsv0rcftjvus3y` FOREIGN KEY (`investigator_id`) REFERENCES `investigator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `immigrant`
--

LOCK TABLES `immigrant` WRITE;
/*!40000 ALTER TABLE `immigrant` DISABLE KEYS */;
INSERT INTO `immigrant` VALUES (2695,0,NULL,'aparrado@gmail.com','Alberto','+67 (354) 1673','Parrado',2635,NULL),(2696,1,NULL,'rgonzalez@hotmail.es','Ruben',NULL,'Gonzalez',2636,2704),(2697,1,NULL,'aromero@gmail.es','Angela',NULL,'Romero',2637,2705),(2698,1,NULL,'aromero@gmail.es','Esperanza',NULL,'Romero',2644,2704),(2699,1,NULL,'aromero@gmail.es','Jesus',NULL,'Romero',2645,2704),(2700,1,NULL,'aromero@gmail.es','Amanda',NULL,'Romero',2646,2704),(2701,1,NULL,'aromero@gmail.es','Esperanza',NULL,'Romero',2647,2704),(2702,1,NULL,'aromero@gmail.es','Esperanza',NULL,'Romero',2648,2704);
/*!40000 ALTER TABLE `immigrant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `immigrant_creditcards`
--

DROP TABLE IF EXISTS `immigrant_creditcards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `immigrant_creditcards` (
  `Immigrant_id` int(11) NOT NULL,
  `CVVCode` int(11) NOT NULL,
  `brandName` varchar(255) DEFAULT NULL,
  `expirationMonth` varchar(255) DEFAULT NULL,
  `expirationYear` varchar(255) DEFAULT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  KEY `FK_qtk2aafkhlq7qcb5q7ol2gsrb` (`Immigrant_id`),
  CONSTRAINT `FK_qtk2aafkhlq7qcb5q7ol2gsrb` FOREIGN KEY (`Immigrant_id`) REFERENCES `immigrant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `immigrant_creditcards`
--

LOCK TABLES `immigrant_creditcards` WRITE;
/*!40000 ALTER TABLE `immigrant_creditcards` DISABLE KEYS */;
INSERT INTO `immigrant_creditcards` VALUES (2695,684,'VISA','08','20','Perico de los Palotes','4012888888881881'),(2695,124,'AMEX','09','18','Credit card 1','4012888888881881'),(2696,123,'MASTERCARD','02','19','Paquito el Chocolatero','4012888888881881'),(2697,124,'AMEX','03','20','Credit card 1','4012888888881881'),(2698,684,'VISA','08','20','Perico de los Palotes','4012888888881881'),(2699,684,'VISA','08','20','Perico de los Palotes','4012888888881881'),(2700,684,'VISA','08','20','Perico de los Palotes','4012888888881881'),(2701,684,'VISA','08','20','Perico de los Palotes','4012888888881881'),(2702,684,'VISA','08','20','Perico de los Palotes','4012888888881881');
/*!40000 ALTER TABLE `immigrant_creditcards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `investigator`
--

DROP TABLE IF EXISTS `investigator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `investigator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_di2p09ij3fudbrrhk1tx2ntm9` (`userAccount_id`),
  CONSTRAINT `FK_di2p09ij3fudbrrhk1tx2ntm9` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `investigator`
--

LOCK TABLES `investigator` WRITE;
/*!40000 ALTER TABLE `investigator` DISABLE KEYS */;
INSERT INTO `investigator` VALUES (2703,0,NULL,'egiraldez@yahoo.es','Esperanza',NULL,'Giraldez',2638),(2704,0,NULL,'ssartor@gmail.com','Sofia',NULL,'Sartor',2639),(2705,0,NULL,'ealvarez@yahoo.es','Ester',NULL,'Alvarez',2640);
/*!40000 ALTER TABLE `investigator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `law`
--

DROP TABLE IF EXISTS `law`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `law` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `abrogatedAt` datetime DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `country_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_is49rdvlftjviqiryjy6tbb5a` (`country_id`),
  CONSTRAINT `FK_is49rdvlftjviqiryjy6tbb5a` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `law`
--

LOCK TABLES `law` WRITE;
/*!40000 ALTER TABLE `law` DISABLE KEYS */;
INSERT INTO `law` VALUES (2718,0,NULL,'2018-06-01 08:00:00','El visado de estudiante solo es valido si se cursa algun estudio en el pais','Validez del visado',2709),(2719,0,NULL,'2018-06-01 08:00:00','Ante la autoria de delitos mayores se retirara el visado','Delitos mayores',2709),(2720,0,'2018-07-01 08:00:00','2018-06-01 08:00:00','No se permite la posesion de drogas durante la estancia en el pais','Sustancias estupefacientes',2710),(2721,0,NULL,'2018-06-01 08:00:00','Texto de descripcion','Ley 4',2709),(2722,0,NULL,'2018-06-01 08:00:00','Texto de descripcion','Ley 5',2709),(2723,0,NULL,'2018-06-01 08:00:00','Texto del visado','Ley 6',2709);
/*!40000 ALTER TABLE `law` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `law_law`
--

DROP TABLE IF EXISTS `law_law`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `law_law` (
  `Law_id` int(11) NOT NULL,
  `relatedLaws_id` int(11) NOT NULL,
  KEY `FK_82oy92l3u7mnigbdt2rm4s6rn` (`relatedLaws_id`),
  KEY `FK_2lt6lusiv8sd3p80i6rorwdgh` (`Law_id`),
  CONSTRAINT `FK_2lt6lusiv8sd3p80i6rorwdgh` FOREIGN KEY (`Law_id`) REFERENCES `law` (`id`),
  CONSTRAINT `FK_82oy92l3u7mnigbdt2rm4s6rn` FOREIGN KEY (`relatedLaws_id`) REFERENCES `law` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `law_law`
--

LOCK TABLES `law_law` WRITE;
/*!40000 ALTER TABLE `law_law` DISABLE KEYS */;
/*!40000 ALTER TABLE `law_law` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `officer`
--

DROP TABLE IF EXISTS `officer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `officer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_j2oip9hkjxaqmvwiei4ocfght` (`userAccount_id`),
  CONSTRAINT `FK_j2oip9hkjxaqmvwiei4ocfght` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `officer`
--

LOCK TABLES `officer` WRITE;
/*!40000 ALTER TABLE `officer` DISABLE KEYS */;
INSERT INTO `officer` VALUES (2706,0,NULL,'pramirez@gmail.es','Paco',NULL,'Ramirez',2641),(2707,0,NULL,'csoria@yahoo.com','Cristina',NULL,'Soria',2642),(2708,0,NULL,'rpozo@hotmail.com','Rocio',NULL,'Pozo',2643);
/*!40000 ALTER TABLE `officer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personalsection`
--

DROP TABLE IF EXISTS `personalsection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personalsection` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `birthDate` date DEFAULT NULL,
  `birthPlace` varchar(255) DEFAULT NULL,
  `fullNames` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personalsection`
--

LOCK TABLES `personalsection` WRITE;
/*!40000 ALTER TABLE `personalsection` DISABLE KEYS */;
INSERT INTO `personalsection` VALUES (2649,0,'1972-07-01','España','Aitor Luengo Lopez','https://facebook.com/ab589def'),(2650,0,'1990-07-01','Marruecos','Zacarias Marrousef','https://facebook.com/ab589def'),(2651,0,'1964-07-01','España','Dolores Carrero Mendez','https://facebook.com/ab589def'),(2652,0,'1984-07-01','España','Patxi Lopez Gurrumendi','https://facebook.com/ab589def'),(2653,0,'1984-07-01','España','Patxi Lopez Gurrumendi','https://facebook.com/ab589def'),(2654,0,'1984-07-01','España','Patxi Lopez Gurrumendi','https://facebook.com/ab589def'),(2655,0,'1984-07-01','España','Patxi Lopez Gurrumendi','https://facebook.com/ab589def'),(2656,0,'1984-07-01','España','Patxi Lopez Gurrumendi','https://facebook.com/ab589def'),(2657,0,'1984-07-01','España','Patxi Lopez Gurrumendi','https://facebook.com/ab589def'),(2658,0,'1984-07-01','España','Patxi Lopez Gurrumendi','https://facebook.com/ab589def'),(2659,0,'1984-07-01','España','Patxi Lopez Gurrumendi','https://facebook.com/ab589def'),(2660,0,'1984-07-01','España','Patxi Lopez Gurrumendi','https://facebook.com/ab589def'),(2661,0,'1984-07-01','España','Patxi Lopez Gurrumendi','https://facebook.com/ab589def'),(2662,0,'1984-07-01','España','Patxi Lopez Gurrumendi','https://facebook.com/ab589def');
/*!40000 ALTER TABLE `personalsection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `answerMoment` datetime DEFAULT NULL,
  `madeMoment` datetime DEFAULT NULL,
  `statement` varchar(255) DEFAULT NULL,
  `application_id` int(11) NOT NULL,
  `immigrant_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_nrl5xpby9aq1g9mwclvouexvt` (`application_id`),
  KEY `FK_i06tsvsp2bexky3p39507gtwd` (`immigrant_id`),
  CONSTRAINT `FK_i06tsvsp2bexky3p39507gtwd` FOREIGN KEY (`immigrant_id`) REFERENCES `immigrant` (`id`),
  CONSTRAINT `FK_nrl5xpby9aq1g9mwclvouexvt` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (2751,0,NULL,NULL,'2018-06-02 08:00:00','¿Tiene decidido donde residira?',2740,2697),(2752,0,NULL,NULL,'2018-06-01 18:00:00','¿Ha buscado lugar de residencia?',2738,2696),(2753,0,NULL,NULL,'2018-06-01 08:00:00','¿Conoce a alguien en la ciudad donde vivira?',2739,2696);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `immigrant_id` int(11) NOT NULL,
  `investigator_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fmyx1a88rrqolatfqtoudnfoe` (`immigrant_id`),
  KEY `FK_gbfl2rqn47vdispf20ltg0ofr` (`investigator_id`),
  CONSTRAINT `FK_gbfl2rqn47vdispf20ltg0ofr` FOREIGN KEY (`investigator_id`) REFERENCES `investigator` (`id`),
  CONSTRAINT `FK_fmyx1a88rrqolatfqtoudnfoe` FOREIGN KEY (`immigrant_id`) REFERENCES `immigrant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES (2715,0,NULL,'Todo correcto',2696,2704),(2716,0,NULL,'Posibles conflictos con los foraneos',2696,2704),(2717,0,NULL,'Antecedentes penales',2697,2705);
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requirement`
--

DROP TABLE IF EXISTS `requirement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requirement` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `abrogated` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `law_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1qwnx6c4ygc13a58uo6wp7x9i` (`law_id`),
  CONSTRAINT `FK_1qwnx6c4ygc13a58uo6wp7x9i` FOREIGN KEY (`law_id`) REFERENCES `law` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requirement`
--

LOCK TABLES `requirement` WRITE;
/*!40000 ALTER TABLE `requirement` DISABLE KEYS */;
INSERT INTO `requirement` VALUES (2724,0,'\0','Debe presentarse la matricula en rigor de los estudios','Matricula de los estudios',2718),(2725,0,'\0','Para concederse el visado no se pueden tener antecedentes','Ausencia de delitos anteriores',2719),(2726,0,'','En caso de registro corporal o de posesiones no pueden encontrarse ninguna droga','Posesion de drogas',2720),(2727,0,'','En caso de realizarse un test de drogas no puede dar positivo, de lo contrario se retirara el visado','Consumo de drogas',2720),(2728,0,'\0','Texto de requisito','Requisito 5',2723),(2729,0,'\0','Texto de requisito','Requisito 6',2723);
/*!40000 ALTER TABLE `requirement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requirement_visa`
--

DROP TABLE IF EXISTS `requirement_visa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requirement_visa` (
  `Requirement_id` int(11) NOT NULL,
  `visas_id` int(11) NOT NULL,
  KEY `FK_fojc8rob3k0nib212m9914fwt` (`visas_id`),
  KEY `FK_11qlo4n3sq76jyffi2che3lwc` (`Requirement_id`),
  CONSTRAINT `FK_11qlo4n3sq76jyffi2che3lwc` FOREIGN KEY (`Requirement_id`) REFERENCES `requirement` (`id`),
  CONSTRAINT `FK_fojc8rob3k0nib212m9914fwt` FOREIGN KEY (`visas_id`) REFERENCES `visa` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requirement_visa`
--

LOCK TABLES `requirement_visa` WRITE;
/*!40000 ALTER TABLE `requirement_visa` DISABLE KEYS */;
INSERT INTO `requirement_visa` VALUES (2724,2731),(2725,2732),(2726,2733),(2727,2733);
/*!40000 ALTER TABLE `requirement_visa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socialsection`
--

DROP TABLE IF EXISTS `socialsection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socialsection` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `linkProfile` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `socialNetwork` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socialsection`
--

LOCK TABLES `socialsection` WRITE;
/*!40000 ALTER TABLE `socialsection` DISABLE KEYS */;
INSERT INTO `socialsection` VALUES (2667,0,'https://twitter.com/aparrado','aparrado','Twitter'),(2668,0,'https://twitter.com/rgonzalez','rgonzalez','Twitter'),(2669,0,'https://twitter.com/aromero','aromero','Twitter'),(2670,0,'https://facebook.com/aparrado156','Alberto Parrado','Facebook'),(2671,0,'https://facebook.com/aparrado156','Alberto Parrado','Facebook'),(2672,0,'https://facebook.com/aparrado156','Alberto Parrado','Facebook'),(2673,0,'https://facebook.com/aparrado156','Alberto Parrado','Facebook'),(2674,0,'https://facebook.com/aparrado156','Alberto Parrado','Facebook'),(2675,0,'https://facebook.com/aparrado156','Alberto Parrado','Facebook'),(2676,0,'https://facebook.com/aparrado156','Alberto Parrado','Facebook'),(2677,0,'https://facebook.com/aparrado156','Alberto Parrado','Facebook'),(2678,0,'https://facebook.com/aparrado156','Alberto Parrado','Facebook'),(2679,0,'https://facebook.com/aparrado156','Alberto Parrado','Facebook'),(2680,0,'https://facebook.com/aparrado156','Alberto Parrado','Facebook');
/*!40000 ALTER TABLE `socialsection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (2632,0,'21232f297a57a5a743894a0e4a801fc3','admin'),(2633,0,'c84258e9c39059a89ab77d846ddab909','admin2'),(2634,0,'32cacb2f994f6b42183a1300d9a3e8d6','admin3'),(2635,0,'f43ad14eab021bed8ffce0550db13b54','immigrant1'),(2636,0,'e644b86d7a7a4d287813e4c48264b435','immigrant2'),(2637,0,'21520165de8d3f7641d82675ea15c14d','immigrant3'),(2638,0,'038ffd9c1cfccd215fabf99238cf84e2','investigator1'),(2639,0,'bde3c61bc57bc2dc922f0ba43dbaa009','investigator2'),(2640,0,'ebedadedf4246283d0f4447f747002d6','investigator3'),(2641,0,'7c3d0453108aefa46c07e91c8bc031d3','officer1'),(2642,0,'5cea39c4f5c604f237baebc9e440ae5f','officer2'),(2643,0,'898ed0abefe5ba5f2488ea0537e230a5','officer3'),(2644,0,'21520165de8d3f7641d82675ea15c14d','immigrant4'),(2645,0,'21520165de8d3f7641d82675ea15c14d','immigrant5'),(2646,0,'21520165de8d3f7641d82675ea15c14d','immigrant6'),(2647,0,'21520165de8d3f7641d82675ea15c14d','immigrant7'),(2648,0,'21520165de8d3f7641d82675ea15c14d','immigrant8');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount_authorities`
--

DROP TABLE IF EXISTS `useraccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount_authorities`
--

LOCK TABLES `useraccount_authorities` WRITE;
/*!40000 ALTER TABLE `useraccount_authorities` DISABLE KEYS */;
INSERT INTO `useraccount_authorities` VALUES (2632,'ADMIN'),(2633,'ADMIN'),(2634,'ADMIN'),(2635,'IMMIGRANT'),(2636,'IMMIGRANT'),(2637,'IMMIGRANT'),(2638,'INVESTIGATOR'),(2639,'INVESTIGATOR'),(2640,'INVESTIGATOR'),(2641,'OFFICER'),(2642,'OFFICER'),(2643,'OFFICER'),(2644,'IMMIGRANT'),(2645,'IMMIGRANT'),(2646,'IMMIGRANT'),(2647,'IMMIGRANT'),(2648,'IMMIGRANT');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visa`
--

DROP TABLE IF EXISTS `visa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `visa` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `abrogated` bit(1) NOT NULL,
  `clase` varchar(255) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `country_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_dv6pkgoytrlh3ali5vxllr0tb` (`category_id`),
  KEY `FK_ofsf31wkhm8xa37cy1k00lpjm` (`country_id`),
  CONSTRAINT `FK_ofsf31wkhm8xa37cy1k00lpjm` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FK_dv6pkgoytrlh3ali5vxllr0tb` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visa`
--

LOCK TABLES `visa` WRITE;
/*!40000 ALTER TABLE `visa` DISABLE KEYS */;
INSERT INTO `visa` VALUES (2731,0,'\0','Verde','€','Visado temporal de 3 meses para estudiantes',100,2685,2709),(2732,0,'\0','Rojo','$','Visado temporal de un año para estudiantes',300,2686,2710),(2733,0,'\0','Azul','$','Visado permanente por motivos de trabajo',500,2687,2710),(2734,0,'\0','Morado','$','Visado permanente para familias por trabajo',600,2687,2710),(2735,0,'\0','Naranja','$','Visado de un año para estudiantes de la union europea',200,2686,2710),(2736,0,'\0','Morado','$','Visado temporal de turismo',6,2687,2710);
/*!40000 ALTER TABLE `visa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visa_requirement`
--

DROP TABLE IF EXISTS `visa_requirement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `visa_requirement` (
  `Visa_id` int(11) NOT NULL,
  `requirements_id` int(11) NOT NULL,
  KEY `FK_9wt2phjomdfykhni56dbx7d17` (`requirements_id`),
  KEY `FK_97yj83sd5wmogjswnial9cpnw` (`Visa_id`),
  CONSTRAINT `FK_97yj83sd5wmogjswnial9cpnw` FOREIGN KEY (`Visa_id`) REFERENCES `visa` (`id`),
  CONSTRAINT `FK_9wt2phjomdfykhni56dbx7d17` FOREIGN KEY (`requirements_id`) REFERENCES `requirement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visa_requirement`
--

LOCK TABLES `visa_requirement` WRITE;
/*!40000 ALTER TABLE `visa_requirement` DISABLE KEYS */;
INSERT INTO `visa_requirement` VALUES (2731,2724),(2732,2725),(2733,2726),(2733,2727);
/*!40000 ALTER TABLE `visa_requirement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `worksection`
--

DROP TABLE IF EXISTS `worksection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `worksection` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `companyName` varchar(255) DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `worksection`
--

LOCK TABLES `worksection` WRITE;
/*!40000 ALTER TABLE `worksection` DISABLE KEYS */;
INSERT INTO `worksection` VALUES (2663,0,'Mc Donals','2016-06-01','Cocinero','2012-07-01'),(2664,0,'Philips',NULL,'Montador','2015-09-01');
/*!40000 ALTER TABLE `worksection` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-29 20:24:33

commit;
