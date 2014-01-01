CREATE DATABASE  IF NOT EXISTS `thirdparty` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `thirdparty`;
-- MySQL dump 10.13  Distrib 5.5.34, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: thirdparty
-- ------------------------------------------------------
-- Server version	5.5.32

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
-- Table structure for table `line_item`
--

DROP TABLE IF EXISTS `line_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `line_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` bigint(20) NOT NULL,
  `dateTime` bigint(20) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `rounding` bigint(20) DEFAULT NULL,
  `electric_power_usage_summary_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bdcc7fff549f43de9c07ff7057e` (`electric_power_usage_summary_id`),
  CONSTRAINT `FK_bdcc7fff549f43de9c07ff7057e` FOREIGN KEY (`electric_power_usage_summary_id`) REFERENCES `electric_power_usage_summaries` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_information_scopes`
--

DROP TABLE IF EXISTS `application_information_scopes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_information_scopes` (
  `application_information_id` bigint(20) NOT NULL,
  `scope` varchar(255) DEFAULT NULL,
  KEY `FK_d727e6ae602c41169a4941ffa6e` (`application_information_id`),
  CONSTRAINT `FK_d727e6ae602c41169a4941ffa6e` FOREIGN KEY (`application_information_id`) REFERENCES `application_information` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `interval_readings`
--

DROP TABLE IF EXISTS `interval_readings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interval_readings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cost` bigint(20) DEFAULT NULL,
  `duration` bigint(20) DEFAULT NULL,
  `start` bigint(20) DEFAULT NULL,
  `value` bigint(20) DEFAULT NULL,
  `interval_block_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_748673cd4a134cb9baadc856aa6` (`interval_block_id`),
  CONSTRAINT `FK_748673cd4a134cb9baadc856aa6` FOREIGN KEY (`interval_block_id`) REFERENCES `interval_blocks` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subscriptions_usage_points`
--

DROP TABLE IF EXISTS `subscriptions_usage_points`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscriptions_usage_points` (
  `subscriptions_id` bigint(20) NOT NULL,
  `usagePoints_id` bigint(20) NOT NULL,
  PRIMARY KEY (`subscriptions_id`,`usagePoints_id`),
  KEY `FK_9e0c6ce489b349b087c34d4cf1d` (`usagePoints_id`),
  KEY `FK_d5d3dd36054d4e9a8c113a9e24a` (`subscriptions_id`),
  CONSTRAINT `FK_d5d3dd36054d4e9a8c113a9e24a` FOREIGN KEY (`subscriptions_id`) REFERENCES `subscriptions` (`id`),
  CONSTRAINT `FK_9e0c6ce489b349b087c34d4cf1d` FOREIGN KEY (`usagePoints_id`) REFERENCES `usage_points` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usage_points`
--

DROP TABLE IF EXISTS `usage_points`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usage_points` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `roleFlags` tinyblob,
  `status` smallint(6) DEFAULT NULL,
  `uri` varchar(255) DEFAULT NULL,
  `local_time_parameters_id` bigint(20) DEFAULT NULL,
  `retail_customer_id` bigint(20) DEFAULT NULL,
  `serviceCategory_kind` bigint(20) NOT NULL,
  `serviceDeliveryPoint_id` bigint(20) DEFAULT NULL,
  `subscription_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_0a95ac0cd0334d50ae2083fa9d8` (`uuid`),
  KEY `FK_d897e553cc63448fb7d7990fc1b` (`local_time_parameters_id`),
  KEY `FK_43c158efb2df41df8bbd3da31fe` (`retail_customer_id`),
  KEY `FK_4a2058e348524726aa217859989` (`serviceCategory_kind`),
  KEY `FK_9f858847bb7a4babb376b7abbf9` (`serviceDeliveryPoint_id`),
  KEY `FK_8b298f6321a74452b841eaea88f` (`subscription_id`),
  CONSTRAINT `FK_8b298f6321a74452b841eaea88f` FOREIGN KEY (`subscription_id`) REFERENCES `subscriptions` (`id`),
  CONSTRAINT `FK_43c158efb2df41df8bbd3da31fe` FOREIGN KEY (`retail_customer_id`) REFERENCES `retail_customers` (`id`),
  CONSTRAINT `FK_4a2058e348524726aa217859989` FOREIGN KEY (`serviceCategory_kind`) REFERENCES `service_categories` (`kind`),
  CONSTRAINT `FK_9f858847bb7a4babb376b7abbf9` FOREIGN KEY (`serviceDeliveryPoint_id`) REFERENCES `service_delivery_points` (`id`),
  CONSTRAINT `FK_d897e553cc63448fb7d7990fc1b` FOREIGN KEY (`local_time_parameters_id`) REFERENCES `time_configurations` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service_categories`
--

DROP TABLE IF EXISTS `service_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service_categories` (
  `kind` bigint(20) NOT NULL,
  PRIMARY KEY (`kind`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `meter_readings`
--

DROP TABLE IF EXISTS `meter_readings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meter_readings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `reading_type_id` bigint(20) DEFAULT NULL,
  `usage_point_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_af083fa9508d4967be2f673eb20` (`uuid`),
  KEY `FK_d97631730e244a8b8c224b87afe` (`reading_type_id`),
  KEY `FK_2fa78fe5040e42b3ade812bbb57` (`usage_point_id`),
  CONSTRAINT `FK_2fa78fe5040e42b3ade812bbb57` FOREIGN KEY (`usage_point_id`) REFERENCES `usage_points` (`id`),
  CONSTRAINT `FK_d97631730e244a8b8c224b87afe` FOREIGN KEY (`reading_type_id`) REFERENCES `reading_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reading_types`
--

DROP TABLE IF EXISTS `reading_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reading_types` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `accumulationBehaviour` varchar(255) DEFAULT NULL,
  `rational_denominator` decimal(19,2) DEFAULT NULL,
  `rational_numerator` decimal(19,2) DEFAULT NULL,
  `commodity` varchar(255) DEFAULT NULL,
  `consumptionTier` varchar(255) DEFAULT NULL,
  `cpp` varchar(255) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `dataQualifier` varchar(255) DEFAULT NULL,
  `defaultQuality` varchar(255) DEFAULT NULL,
  `flowDirection` varchar(255) DEFAULT NULL,
  `interharmonic_denominator` decimal(19,2) DEFAULT NULL,
  `interharmonic_numerator` decimal(19,2) DEFAULT NULL,
  `intervalLength` bigint(20) DEFAULT NULL,
  `kind` varchar(255) DEFAULT NULL,
  `measuringPeriod` varchar(255) DEFAULT NULL,
  `phase` varchar(255) DEFAULT NULL,
  `powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `timeAttribute` varchar(255) DEFAULT NULL,
  `tou` varchar(255) DEFAULT NULL,
  `uom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_173396c4fc324010bb0e5d86577` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `batchlist`
--

DROP TABLE IF EXISTS `batchlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batchlist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `interval_blocks`
--

DROP TABLE IF EXISTS `interval_blocks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interval_blocks` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `duration` bigint(20) DEFAULT NULL,
  `start` bigint(20) DEFAULT NULL,
  `meter_reading_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_425b1ddd7891434087b12b606b1` (`uuid`),
  KEY `FK_ec77d3afb7854056ac33704e412` (`meter_reading_id`),
  CONSTRAINT `FK_ec77d3afb7854056ac33704e412` FOREIGN KEY (`meter_reading_id`) REFERENCES `meter_readings` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `electric_power_quality_summaries`
--

DROP TABLE IF EXISTS `electric_power_quality_summaries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `electric_power_quality_summaries` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `flickerPlt` bigint(20) DEFAULT NULL,
  `flickerPst` bigint(20) DEFAULT NULL,
  `harmonicVoltage` bigint(20) DEFAULT NULL,
  `longInterruptions` bigint(20) DEFAULT NULL,
  `mainsVoltage` bigint(20) DEFAULT NULL,
  `measurementProtocol` smallint(6) DEFAULT NULL,
  `powerFrequency` bigint(20) DEFAULT NULL,
  `rapidVoltageChanges` bigint(20) DEFAULT NULL,
  `shortInterruptions` bigint(20) DEFAULT NULL,
  `duration` bigint(20) DEFAULT NULL,
  `start` bigint(20) DEFAULT NULL,
  `supplyVoltageDips` bigint(20) DEFAULT NULL,
  `supplyVoltageImbalance` bigint(20) DEFAULT NULL,
  `supplyVoltageVariations` bigint(20) DEFAULT NULL,
  `tempOvervoltage` bigint(20) DEFAULT NULL,
  `usage_point_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9871bf069e9d423ead69afd6c86` (`uuid`),
  KEY `FK_141b7ab8835b47929b75e7a8628` (`usage_point_id`),
  CONSTRAINT `FK_141b7ab8835b47929b75e7a8628` FOREIGN KEY (`usage_point_id`) REFERENCES `usage_points` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `meter_reading_related_links`
--

DROP TABLE IF EXISTS `meter_reading_related_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meter_reading_related_links` (
  `meter_reading_id` bigint(20) NOT NULL,
  `href` varchar(255) DEFAULT NULL,
  `rel` varchar(255) DEFAULT NULL,
  KEY `FK_7e33a480564845a190e65e04f54` (`meter_reading_id`),
  CONSTRAINT `FK_7e33a480564845a190e65e04f54` FOREIGN KEY (`meter_reading_id`) REFERENCES `meter_readings` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `time_configurations`
--

DROP TABLE IF EXISTS `time_configurations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `time_configurations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `dstEndRule` tinyblob,
  `dstOffset` bigint(20) NOT NULL,
  `dstStartRule` tinyblob,
  `tzOffset` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_76ecb96f39514ff69e2908bc1f7` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `authorizations`
--

DROP TABLE IF EXISTS `authorizations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authorizations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `access_token` varchar(255) DEFAULT NULL,
  `authorization_uri` varchar(255) DEFAULT NULL,
  `ap_duration` bigint(20) DEFAULT NULL,
  `ap_start` bigint(20) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `error` int(11) DEFAULT NULL,
  `errorDescription` varchar(255) DEFAULT NULL,
  `errorUri` varchar(255) DEFAULT NULL,
  `expiresIn` bigint(20) DEFAULT NULL,
  `grantType` int(11) DEFAULT NULL,
  `pp_duration` bigint(20) DEFAULT NULL,
  `pp_start` bigint(20) DEFAULT NULL,
  `refreshToken` varchar(255) DEFAULT NULL,
  `resourceURI` varchar(255) DEFAULT NULL,
  `responseType` int(11) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `subscriptionURI` varchar(255) DEFAULT NULL,
  `third_party` varchar(255) DEFAULT NULL,
  `tokenType` int(11) DEFAULT NULL,
  `application_information_id` bigint(20) DEFAULT NULL,
  `retail_customer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fb1cda8fcdd44bf392f5cb1c73c` (`application_information_id`),
  KEY `FK_22e197ff014f42f6b48b66487d4` (`retail_customer_id`),
  CONSTRAINT `FK_22e197ff014f42f6b48b66487d4` FOREIGN KEY (`retail_customer_id`) REFERENCES `retail_customers` (`id`),
  CONSTRAINT `FK_fb1cda8fcdd44bf392f5cb1c73c` FOREIGN KEY (`application_information_id`) REFERENCES `application_information` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reading_qualities`
--

DROP TABLE IF EXISTS `reading_qualities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reading_qualities` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `quality` varchar(255) DEFAULT NULL,
  `interval_reading_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_97c871835f1f46aca93c7e97503` (`interval_reading_id`),
  CONSTRAINT `FK_97c871835f1f46aca93c7e97503` FOREIGN KEY (`interval_reading_id`) REFERENCES `interval_readings` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subscriptions`
--

DROP TABLE IF EXISTS `subscriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscriptions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `hashedId` varchar(255) DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  `applicationInformation_id` bigint(20) NOT NULL,
  `authorization_id` bigint(20) DEFAULT NULL,
  `retail_customer_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_f3fd6642f226406fab39c52ad05` (`applicationInformation_id`),
  KEY `FK_86dc4fa295244d7faa22b02cc8d` (`authorization_id`),
  KEY `FK_deadaa535a9249b2ac0d9255829` (`retail_customer_id`),
  CONSTRAINT `FK_deadaa535a9249b2ac0d9255829` FOREIGN KEY (`retail_customer_id`) REFERENCES `retail_customers` (`id`),
  CONSTRAINT `FK_86dc4fa295244d7faa22b02cc8d` FOREIGN KEY (`authorization_id`) REFERENCES `authorizations` (`id`),
  CONSTRAINT `FK_f3fd6642f226406fab39c52ad05` FOREIGN KEY (`applicationInformation_id`) REFERENCES `application_information` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usage_point_related_links`
--

DROP TABLE IF EXISTS `usage_point_related_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usage_point_related_links` (
  `usage_point_id` bigint(20) NOT NULL,
  `href` varchar(255) DEFAULT NULL,
  `rel` varchar(255) DEFAULT NULL,
  KEY `FK_e45147476a5b4f0389a709ff6b4` (`usage_point_id`),
  CONSTRAINT `FK_e45147476a5b4f0389a709ff6b4` FOREIGN KEY (`usage_point_id`) REFERENCES `usage_points` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `electric_power_usage_summaries`
--

DROP TABLE IF EXISTS `electric_power_usage_summaries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `electric_power_usage_summaries` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `billLastPeriod` bigint(20) DEFAULT NULL,
  `billToDate` bigint(20) DEFAULT NULL,
  `billingPeriod_duration` bigint(20) DEFAULT NULL,
  `billingPeriod_start` bigint(20) DEFAULT NULL,
  `costAdditionalLastPeriod` bigint(20) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `currentBillingPeriodOverAllConsumption_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `currentBillingPeriodOverAllConsumption_timeStamp` bigint(20) DEFAULT NULL,
  `currentBillingPeriodOverAllConsumption_uom` varchar(255) DEFAULT NULL,
  `currentBillingPeriodOverAllConsumption_value` bigint(20) DEFAULT NULL,
  `currentDayLastYearNetConsumption_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `currentDayLastYearNetConsumption_timeStamp` bigint(20) DEFAULT NULL,
  `currentDayLastYearNetConsumption_uom` varchar(255) DEFAULT NULL,
  `currentDayLastYearNetConsumption_value` bigint(20) DEFAULT NULL,
  `currentDayNetConsumption_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `currentDayNetConsumption_timeStamp` bigint(20) DEFAULT NULL,
  `currentDayNetConsumption_uom` varchar(255) DEFAULT NULL,
  `currentDayNetConsumption_value` bigint(20) DEFAULT NULL,
  `currentDayOverallConsumption_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `currentDayOverallConsumption_timeStamp` bigint(20) DEFAULT NULL,
  `currentDayOverallConsumption_uom` varchar(255) DEFAULT NULL,
  `currentDayOverallConsumption_value` bigint(20) DEFAULT NULL,
  `overallConsumptionLastPeriod_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `overallConsumptionLastPeriod_timeStamp` bigint(20) DEFAULT NULL,
  `overallConsumptionLastPeriod_uom` varchar(255) DEFAULT NULL,
  `overallConsumptionLastPeriod_value` bigint(20) DEFAULT NULL,
  `peakDemand_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `peakDemand_timeStamp` bigint(20) DEFAULT NULL,
  `peakDemand_uom` varchar(255) DEFAULT NULL,
  `peakDemand_value` bigint(20) DEFAULT NULL,
  `previousDayLastYearOverallConsumption_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `previousDayLastYearOverallConsumption_timeStamp` bigint(20) DEFAULT NULL,
  `previousDayLastYearOverallConsumption_uom` varchar(255) DEFAULT NULL,
  `previousDayLastYearOverallConsumption_value` bigint(20) DEFAULT NULL,
  `previousDayNetConsumption_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `previousDayNetConsumption_timeStamp` bigint(20) DEFAULT NULL,
  `previousDayNetConsumption_uom` varchar(255) DEFAULT NULL,
  `previousDayNetConsumption_value` bigint(20) DEFAULT NULL,
  `previousDayOverallConsumption_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `previousDayOverallConsumption_timeStamp` bigint(20) DEFAULT NULL,
  `previousDayOverallConsumption_uom` varchar(255) DEFAULT NULL,
  `previousDayOverallConsumption_value` bigint(20) DEFAULT NULL,
  `qualityOfReading` varchar(255) DEFAULT NULL,
  `ratchetDemand_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `ratchetDemand_timeStamp` bigint(20) DEFAULT NULL,
  `ratchetDemand_uom` varchar(255) DEFAULT NULL,
  `ratchetDemand_value` bigint(20) DEFAULT NULL,
  `ratchetDemandPeriod_duration` bigint(20) DEFAULT NULL,
  `ratchetDemandPeriod_start` bigint(20) DEFAULT NULL,
  `statusTimeStamp` bigint(20) NOT NULL,
  `usage_point_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_a9cf6ab8e3ac450895b8d3eb35b` (`uuid`),
  KEY `FK_fd859b902ffd44688b9cbf068f0` (`usage_point_id`),
  CONSTRAINT `FK_fd859b902ffd44688b9cbf068f0` FOREIGN KEY (`usage_point_id`) REFERENCES `usage_points` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `retail_customers`
--

DROP TABLE IF EXISTS `retail_customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `retail_customers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enabled` BOOLEAN NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `role` varchar(255) NOT NULL,
  `username` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service_delivery_points`
--

DROP TABLE IF EXISTS `service_delivery_points`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service_delivery_points` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customerAgreement` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `tariffProfile` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_information`
--

DROP TABLE IF EXISTS `application_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_information` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `authorizationServerAuthorizationEndpoint` varchar(255) DEFAULT NULL,
  `authorizationServerRegistrationEndpoint` varchar(255) DEFAULT NULL,
  `authorizationServerTokenEndpoint` varchar(255) DEFAULT NULL,
  `authorizationServerUri` varchar(255) DEFAULT NULL,
  `clientId` varchar(64) NOT NULL,
  `clientIdIssuedAt` bigint(20) DEFAULT NULL,
  `clientName` varchar(255) DEFAULT NULL,
  `clientSecret` varchar(255) DEFAULT NULL,
  `clientSecretExpiresAt` bigint(20) DEFAULT NULL,
  `clientUri` varchar(255) DEFAULT NULL,
  `contacts` tinyblob,
  `dataCustodianApplicationStatus` varchar(255) DEFAULT NULL,
  `dataCustodianBulkRequestURI` varchar(255) DEFAULT NULL,
  `dataCustodianDefaultBatchResource` varchar(255) DEFAULT NULL,
  `dataCustodianDefaultSubscriptionResource` varchar(255) DEFAULT NULL,
  `dataCustodianId` varchar(64) DEFAULT NULL,
  `dataCustodianResourceEndpoint` varchar(255) DEFAULT NULL,
  `dataCustodianThirdPartySelectionScreenURI` varchar(255) DEFAULT NULL,
  `grantTypes` tinyblob,
  `logoUri` varchar(255) DEFAULT NULL,
  `policyUri` varchar(255) DEFAULT NULL,
  `redirectUri` varchar(255) DEFAULT NULL,
  `registrationAccessToken` varchar(255) DEFAULT NULL,
  `registrationClientUri` varchar(255) DEFAULT NULL,
  `responseTypes` int(11) DEFAULT NULL,
  `softwareId` varchar(255) DEFAULT NULL,
  `softwareVersion` varchar(255) DEFAULT NULL,
  `thirdPartyApplicationDescription` varchar(255) DEFAULT NULL,
  `thirdPartyApplicationName` varchar(64) NOT NULL,
  `thirdPartyApplicationStatus` varchar(255) DEFAULT NULL,
  `thirdPartyApplicationType` varchar(255) DEFAULT NULL,
  `thirdPartyApplicationUse` varchar(255) DEFAULT NULL,
  `thirdPartyDataCustodianSelectionScreenURI` varchar(255) DEFAULT NULL,
  `thirdPartyLoginScreenURI` varchar(255) DEFAULT NULL,
  `thirdPartyNotifyUri` varchar(255) DEFAULT NULL,
  `thirdPartyPhone` varchar(255) DEFAULT NULL,
  `thirdPartyScopeSelectionScreenURI` varchar(255) DEFAULT NULL,
  `thirdPartyUserPortalScreenURI` varchar(255) DEFAULT NULL,
  `tokenEndpointAuthMethod` varchar(255) DEFAULT NULL,
  `tosUri` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_45f65bb6e8e441a0b58a27fa584` (`dataCustodianId`,`clientId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `resources`
--

DROP TABLE IF EXISTS `resources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resources` (
  `id` bigint(20) NOT NULL,
  `uri` varchar(255) DEFAULT NULL,
  KEY `FK_c03e35fa0d2942be9bb9b214da6` (`id`),
  CONSTRAINT `FK_c03e35fa0d2942be9bb9b214da6` FOREIGN KEY (`id`) REFERENCES `batchlist` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-12-28 16:45:40
