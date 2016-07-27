/*
 Navicat MySQL Data Transfer

 Source Server         : Gaumeo
 Source Server Type    : MySQL
 Source Server Version : 50712
 Source Host           : localhost
 Source Database       : anime

 Target Server Type    : MySQL
 Target Server Version : 50712
 File Encoding         : utf-8

 Date: 07/23/2016 17:08:30 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `categoryID` int(11) NOT NULL AUTO_INCREMENT,
  `categoryName` varchar(100) NOT NULL,
  PRIMARY KEY (`categoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `chapter`
-- ----------------------------
DROP TABLE IF EXISTS `chapter`;
CREATE TABLE `chapter` (
  `chapterID` int(11) NOT NULL AUTO_INCREMENT,
  `mangaID` int(11) NOT NULL,
  `chapterUrl` varchar(300) NOT NULL,
  `chapterName` varchar(300) DEFAULT NULL,
  `chapterTitle` varchar(255) DEFAULT NULL,
  `chapterNumber` float(11,1) DEFAULT '0.0',
  `datePublish` datetime DEFAULT NULL,
  PRIMARY KEY (`chapterID`),
  KEY `fk_chapter_manga_idx` (`mangaID`),
  CONSTRAINT `fk_chapter_manga` FOREIGN KEY (`mangaID`) REFERENCES `manga` (`mangaID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `genres`
-- ----------------------------
DROP TABLE IF EXISTS `genres`;
CREATE TABLE `genres` (
  `genresID` int(11) NOT NULL AUTO_INCREMENT,
  `mangaID` int(11) DEFAULT NULL,
  `categoryID` int(11) DEFAULT NULL,
  PRIMARY KEY (`genresID`),
  KEY `fk_genres_category_idx` (`categoryID`),
  KEY `fk_genres_manga_idx` (`mangaID`),
  CONSTRAINT `fk_genres_category` FOREIGN KEY (`categoryID`) REFERENCES `category` (`categoryID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_genres_manga` FOREIGN KEY (`mangaID`) REFERENCES `manga` (`mangaID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `manga`
-- ----------------------------
DROP TABLE IF EXISTS `manga`;
CREATE TABLE `manga` (
  `mangaID` int(11) NOT NULL AUTO_INCREMENT,
  `mangaTitle` varchar(1000) DEFAULT NULL,
  `mangaName` varchar(300) NOT NULL,
  `mangaCover` varchar(200) DEFAULT NULL,
  `author` varchar(100) DEFAULT NULL,
  `artist` varchar(255) DEFAULT NULL,
  `description` varchar(5000) DEFAULT NULL,
  `genres` varchar(500) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL COMMENT '0. All\n1. Ongoing\n2. New\n3. Updated\n4. Completed',
  `views` int(11) DEFAULT NULL,
  `rating` float(11,1) DEFAULT NULL,
  `dateRelease` timestamp NULL DEFAULT NULL,
  `dateUpdated` timestamp NULL DEFAULT NULL,
  `idxMangaFox` int(11) DEFAULT NULL,
  `rankMangaFox` int(11) DEFAULT NULL,
  PRIMARY KEY (`mangaID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `page`
-- ----------------------------
DROP TABLE IF EXISTS `page`;
CREATE TABLE `page` (
  `pageID` int(11) NOT NULL AUTO_INCREMENT,
  `chapterID` int(11) DEFAULT NULL,
  `pageImage` varchar(200) DEFAULT NULL,
  `pageNumber` int(11) DEFAULT '1',
  PRIMARY KEY (`pageID`),
  KEY `fk_page_chapter_idx` (`chapterID`),
  CONSTRAINT `fk_page_chapter` FOREIGN KEY (`chapterID`) REFERENCES `chapter` (`chapterID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
