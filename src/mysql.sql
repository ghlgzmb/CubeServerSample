CREATE DATABASE  IF NOT EXISTS `cubeserver` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `cubeserver`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: cubeserver
-- ------------------------------------------------------
-- Server version	5.7.14-log

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
-- Table structure for table `constanttype`
--

DROP TABLE IF EXISTS `constanttype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `constanttype` (
  `type` varchar(50) NOT NULL,
  `classname` varchar(255) NOT NULL,
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `constanttype`
--

LOCK TABLES `constanttype` WRITE;
/*!40000 ALTER TABLE `constanttype` DISABLE KEYS */;
INSERT INTO `constanttype` VALUES ('boolean','com.viridisio.cubeserver.simplecube.TempBoolean'),('number','com.viridisio.cubeserver.simplecube.TempNumber'),('string','com.viridisio.cubeserver.simplecube.TempString');
/*!40000 ALTER TABLE `constanttype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cube`
--

DROP TABLE IF EXISTS `cube`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cube` (
  `cid` varchar(36) NOT NULL,
  `type` varchar(200) DEFAULT NULL,
  `pid` varchar(36) DEFAULT NULL,
  `modifydate` varchar(50) DEFAULT NULL,
  `content` longtext,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cube`
--

LOCK TABLES `cube` WRITE;
/*!40000 ALTER TABLE `cube` DISABLE KEYS */;
INSERT INTO `cube` VALUES ('1c2e7117-d6d5-4a87-b625-95028bf83165','JavaFunctionCube','null','2017-06-21','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<cube><functionClassName>com.viridisio.cubeserver.javafunctioncube.CurrencyExchangeFunction</functionClassName></cube>'),('3f28beb0-e1c0-43f3-b310-030914fd6fa5','SimpleCube','null','2017-06-21','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<cube><content>3</content><contentType>number</contentType></cube>'),('43dd7555-f4b5-4f87-a3b0-988d7dad06de','FomularCube','null','2017-06-21','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<cube><function_call_expression><functionExpression><cube_attribute_expression><attributeName>function</attributeName><cubeId>1c2e7117-d6d5-4a87-b625-95028bf83165</cubeId></cube_attribute_expression></functionExpression><inputPara><constant_expression><type>string</type><content>USD</content></constant_expression></inputPara><inputPara><constant_expression><type>string</type><content>TWD</content></constant_expression></inputPara><inputPara><operator_expression><sign>+</sign><left><cube_attribute_expression><attributeName>value</attributeName><cubeId>3f28beb0-e1c0-43f3-b310-030914fd6fa5</cubeId></cube_attribute_expression></left><right><cube_attribute_expression><attributeName>value</attributeName><cubeId>aa81aba5-9936-4183-b135-740de801231a</cubeId></cube_attribute_expression></right></operator_expression></inputPara><outputPara>toNumber</outputPara></function_call_expression></cube>'),('aa81aba5-9936-4183-b135-740de801231a','SimpleCube','null','2017-06-21','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<cube><content>20</content><contentType>number</contentType></cube>');
/*!40000 ALTER TABLE `cube` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cubetype`
--

DROP TABLE IF EXISTS `cubetype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cubetype` (
  `type` varchar(100) NOT NULL,
  `classname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cubetype`
--

LOCK TABLES `cubetype` WRITE;
/*!40000 ALTER TABLE `cubetype` DISABLE KEYS */;
INSERT INTO `cubetype` VALUES ('ComposedFunctionCube','com.viridisio.cubeserver.composedfuncitoncube.ComposedFunctionCube'),('FomularCube','com.viridisio.cubeserver.core.FomularCube'),('JavaFunctionCube','com.viridisio.cubeserver.javafunctioncube.JavaFunctionCube'),('LocalTableCube','com.viridisio.cubeserver.localtablecube.LocalTableCube'),('RecipeCube','com.gmors.recipe.RecipeCube'),('SimpleCube','com.viridisio.cubeserver.simplecube.SimpleCube');
/*!40000 ALTER TABLE `cubetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operator`
--

DROP TABLE IF EXISTS `operator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operator` (
  `sign` varchar(4) NOT NULL,
  `lefttype` varchar(100) NOT NULL,
  `righttype` varchar(100) NOT NULL,
  `resulttype` varchar(100) NOT NULL,
  `classname` varchar(255) NOT NULL,
  PRIMARY KEY (`sign`,`righttype`,`lefttype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operator`
--

LOCK TABLES `operator` WRITE;
/*!40000 ALTER TABLE `operator` DISABLE KEYS */;
INSERT INTO `operator` VALUES ('+','number','number','number','com.viridisio.cubeserver.simplecube.NumberAddNumberOperator'),('-','recipe','recipe','recipe','com.gmors.recipe.RecipeReduceRecipeOperator');
/*!40000 ALTER TABLE `operator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xmlfile`
--

DROP TABLE IF EXISTS `xmlfile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xmlfile` (
  `id` varchar(36) NOT NULL,
  `xml` longtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xmlfile`
--

LOCK TABLES `xmlfile` WRITE;
/*!40000 ALTER TABLE `xmlfile` DISABLE KEYS */;
/*!40000 ALTER TABLE `xmlfile` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-21 12:21:09
