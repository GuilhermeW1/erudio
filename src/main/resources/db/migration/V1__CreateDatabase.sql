CREATE DATABASE  IF NOT EXISTS `erudio`;
USE `erudio`;

CREATE TABLE IF NOT EXISTS `person` (
  `id` serial,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)

