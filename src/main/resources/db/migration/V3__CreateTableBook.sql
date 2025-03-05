CREATE TABLE IF NOT EXISTS `book` (
  `id` serial,
  `author` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `publish_date` date DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

