start transaction;

drop database if exists `AcmeImmigrant`;create database `AcmeImmigrant`;
use 'AcmeImmigrant';

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete on `AcmeImmigrant`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter,
create temporary tables, lock tables, create view, create routine, alter routine,
execute, trigger, show view on `AcmeImmigrant`.* to 'acme-manager'@'%';

-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
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
INSERT INTO `administrator` VALUES (916,0,NULL,'jramirez@us.es','Jose',NULL,'Ramirez',885);
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
  `CVVCode` varchar(255) DEFAULT NULL,
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
INSERT INTO `category` VALUES (910,0,'CATEGORY',NULL);
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
INSERT INTO `configurationsystem` VALUES (940,0,'https://www.delengua.es/sites/default/files/2016-10/visado.jpg','Acme Immigrant','Hi! Welcome to Acme Immigrant','Hola! Bienvenido a Acme Immigrant');
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
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',2);
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
  `CVVCode` varchar(255) DEFAULT NULL,
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
INSERT INTO `useraccount` VALUES (885,0,'21232f297a57a5a743894a0e4a801fc3','admin');
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
INSERT INTO `useraccount_authorities` VALUES (885,'ADMIN');
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

-- Dump completed on 2018-08-18 17:15:43

commit;
