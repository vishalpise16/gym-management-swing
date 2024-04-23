show databases;

CREATE DATABASE `management_system`

use management_system;

CREATE TABLE `member_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_of_joining` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `renewal_date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
